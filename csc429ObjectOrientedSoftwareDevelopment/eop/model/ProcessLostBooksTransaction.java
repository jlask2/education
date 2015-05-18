package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Stack;
import java.util.Vector;

import exception.InvalidPrimaryKeyException;
import userinterface.ViewFactory;
import userinterface.ViewL;

public class ProcessLostBooksTransaction extends TransactionL {
	private Vector<Properties> studentBorrowerCollectionVecProps;
	private Properties studentBorrowerProps;
	private Vector<Properties> rentalCollectionVecProps;
	private Properties searchProps;
	private Vector<Properties> rentalsBooksVecProps;
	private Vector<Properties> rentalsStillCheckedOutVecProps;
	private Vector<Properties> rentalsToProcessAsLostVecProps;
	private Vector<Properties> rentalsBookStillCheckedOutVecProps;
	private Vector<Properties> rentalsBookToProcessAsLostVecProps;
	private Properties rentalBookProps;
	private Properties rentalsLbToCoStatusProps;
	private Properties rentalsCoToLbStatusProps;
	private Properties commitLostBooksStatusProps;
	private Properties lostBookToModifyProps;
	private Properties submitModifyLostBook;
	private Properties workerProps;
	private boolean isAllDone;
	private int sbSelectedIndex;
	private int checkoutRentalSelectedIndex;
	private String transactionErrorMessage = "";
	public ProcessLostBooksTransaction(Worker worker) throws Exception{
		super(worker);
		isAllDone = false;
		myHistory = new Stack<ViewL>();
		workerProps = (Properties)worker.getState("deep_props");
	}
	protected void setDependencies(){
		dependencies = new Properties();
		dependencies.setProperty("CancelModifyStudentBorrower", "CancelTransaction");
		dependencies.setProperty("DoStudentBorrowerSearch", "TransactionErrorMessage");
		myRegistry.setDependencies(dependencies);
	}
	@Override
	public Object getState(String key) {
		if(key.equals("TransactionError") == true){
			return transactionErrorMessage;
		} else if(key.equals("TransactionType") == true){
			return "CheckInBooksTransaction";
		} else if(key.equals("deep_props_vec_student_borrower_list")){
			return studentBorrowerCollectionVecProps;
		} else if(key.equals("deep_props_student_borrower")){
			return studentBorrowerProps;
		} else if (key.equals("deep_props_vec_rental_list")){
			return rentalCollectionVecProps;
		} else if (key.equals("deep_props_vec_rental_list_books")){
			return rentalsBooksVecProps;
		} else if (key.equals("deep_props_vec_rentals_still_checked_out")){
			return rentalsStillCheckedOutVecProps;
		} else if (key.equals("deep_props_vec_rentals_to_process_as_lost")){
			return rentalsToProcessAsLostVecProps;
		} else if (key.equals("deep_props_vec_rentals_book_still_checked_out")){
			return rentalsBookStillCheckedOutVecProps;
		} else if (key.equals("deep_props_vec_rentals_book_to_process_as_lost")){
			return rentalsBookToProcessAsLostVecProps;
		} else if (key.equals("get_book")){
			return rentalBookProps;
		}else if (key.equals("rental_move_rental_co_to_lb_status")){
			return rentalsCoToLbStatusProps;
		} else if (key.equals("rental_move_rental_lb_to_co_status")){
			return rentalsLbToCoStatusProps;
		} else if (key.equals("commit_lost_books_status")){
			return commitLostBooksStatusProps;
		} else if (key.equals("lost_book_to_modify")){
			return lostBookToModifyProps;
		}
		return null;
	}
	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals("DoYourJob")){
			doYourJob();
			transactionErrorMessage = "";
		} else if(key.equals("Search")){//search for student borrower
			searchProps = (Properties)value;
			searchForStudentBorrowers();
		} else if(key.equals("StudentBorrowerSelected")){
			sbSelectedIndex = (Integer)value;
			showSelectedStudentBorrowersRentals();
		} else if (key.equals("rental_move_rental_co_to_lb")){
			preMoveCoToLb((Integer)value);
		} else if (key.equals("rental_move_rental_lb_to_co")){
			moveLbToCo((Integer)value);
		} else if(key.equals("get_book")){
			rentalBookProps = getBook(((Properties)value).getProperty("Barcode"));
		} else if(key.equals("CancelThisView")){
			showHistoricalView();
		} else if (key.equals("done")){
			stateChangeRequest("CancelModifyStudentBorrower", null);
		} else if (key.equals("cancel")){
			stateChangeRequest("CancelModifyStudentBorrower", null);
		} else if (key.equals("commit_lost_books")){
			commit();
		} else if (key.equals("SubmitModifyLostBook")){
			if (value == null){
				submitModifyLostBook = null;
				showHistoricalView();
			} else {
				submitModifyLostBook = (Properties)value;
				Properties propsRrem = rentalsStillCheckedOutVecProps.remove(checkoutRentalSelectedIndex);
				rentalsBookStillCheckedOutVecProps.remove(checkoutRentalSelectedIndex);
				rentalsToProcessAsLostVecProps.add(propsRrem);
				rentalsBookToProcessAsLostVecProps.add(submitModifyLostBook);
				showHistoricalView();
			}
		} else if (key.equals("GetStudentBorrowerByBannerID")){
			String bId = (String)value;
			transactionErrorMessage = "";
			StudentBorrowerCollection studentBorrowerCollection = new StudentBorrowerCollection();
			studentBorrowerCollection.findStudentBorrowersWithCheckedOutBooksByBannerId(bId);
			studentBorrowerCollectionVecProps = (Vector<Properties>)studentBorrowerCollection.getState("deep_props_vec");
			if (studentBorrowerCollectionVecProps == null || studentBorrowerCollectionVecProps.size() < 1){
				transactionErrorMessage = "no results";
			} else {
				transactionErrorMessage = "";
				createAndShowStudentBorrowerCollectionView();
			}
		}
		myRegistry.updateSubscribers(key, this);
	}
	public void commit(){
		commitLostBooksStatusProps = new Properties();
		if (isAllDone){
			commitLostBooksStatusProps.setProperty("Status", "-1");
			commitLostBooksStatusProps.setProperty("Message", "transaction already completed");
			commitLostBooksStatusProps.setProperty("LongMessage", "");
			return;
		}
		if (rentalsToProcessAsLostVecProps.size() < 1){
			commitLostBooksStatusProps.setProperty("Status", "-1");
			commitLostBooksStatusProps.setProperty("Message", "no lost books specified");
			commitLostBooksStatusProps.setProperty("LongMessage", "");
			return;
		}
		commitLostBooksStatusProps.setProperty("Status", "0");
		commitLostBooksStatusProps.setProperty("Message", "");
		commitLostBooksStatusProps.setProperty("LongMessage", "");
		double feeSum = 0;
		for(int i = 0;i < rentalsToProcessAsLostVecProps.size();i++){
			Properties propsR = rentalsToProcessAsLostVecProps.get(i);
			Properties propsB = rentalsBookToProcessAsLostVecProps.get(i);
			propsB.setProperty("Status", "Lost");
			Book book = new Book(propsB);
			book.update();
			Properties bookUpdateStatusProps = (Properties)book.getState("update_status");
			if (!bookUpdateStatusProps.getProperty("Status").equals("0")){
				commitLostBooksStatusProps.setProperty("LongMessage", commitLostBooksStatusProps.getProperty("LongMessage") + 
						" unable to set book " + propsB.getProperty("Barcode") + " status to Lost\n");
				commitLostBooksStatusProps.setProperty("Status", "1");
			} else {
				String priceStr = propsB.getProperty("SuggestedPrice");
				double price = 0.0;
				try{
					price = Double.parseDouble(priceStr);
				} catch(NumberFormatException e){
					price = 0.0;
					commitLostBooksStatusProps.setProperty("LongMessage", commitLostBooksStatusProps.getProperty("LongMessage") + 
							" unable to parse price " + priceStr + "\n");
					commitLostBooksStatusProps.setProperty("Status", "1");
				} catch(NullPointerException e){
					price = 0.0;
					commitLostBooksStatusProps.setProperty("LongMessage", commitLostBooksStatusProps.getProperty("LongMessage") + 
							" unable to price is null " + priceStr + "\n");
					commitLostBooksStatusProps.setProperty("Status", "1");
				}
				feeSum += price;
			}
			propsR.setProperty("CheckinDate", getTodaysDate());
			propsR.setProperty("CheckinWorkerId", workerProps.getProperty("BannerID"));
			Rental rental = new Rental(propsR);
			rental.update();
			Properties rentalUpdateStatusProps = (Properties)rental.getState("update_status");
			if (!rentalUpdateStatusProps.getProperty("Status").equals("0")){
				commitLostBooksStatusProps.setProperty("LongMessage", commitLostBooksStatusProps.getProperty("LongMessage") + 
						" unable to close rental " + propsB.getProperty("ID") + "\n");
				commitLostBooksStatusProps.setProperty("Status", "1");
			}
		}
		String monetaryPenaltyStr = studentBorrowerProps.getProperty("MonetaryPenalty");
		double monetaryPenalty = 0.0;
		try{
			monetaryPenalty = Double.parseDouble(monetaryPenaltyStr);
		} catch(NumberFormatException e){
			commitLostBooksStatusProps.setProperty("LongMessage", commitLostBooksStatusProps.getProperty("LongMessage") + 
					" unable to parse monetary penalty " + monetaryPenaltyStr + "\n");
			monetaryPenalty = 0.0;
			commitLostBooksStatusProps.setProperty("Status", "1");
		} catch(NullPointerException e){
			commitLostBooksStatusProps.setProperty("LongMessage", commitLostBooksStatusProps.getProperty("LongMessage") + 
					" unable to monetary penalty null" + monetaryPenaltyStr + "\n");
			monetaryPenalty = 0.0;
			commitLostBooksStatusProps.setProperty("Status", "1");
		}
		monetaryPenalty += feeSum;
		studentBorrowerProps.setProperty("MonetaryPenalty", String.format("%.2f", monetaryPenalty));
		commitLostBooksStatusProps.setProperty("MonetaryPenalty", String.format("%.2f", monetaryPenalty));
		commitLostBooksStatusProps.setProperty("TotalFee", String.format("%.2f", feeSum));
		StudentBorrower studentBorrower = new StudentBorrower(studentBorrowerProps);
		studentBorrower.update();
		Properties studentBorrowerUpdateStatusProps = (Properties)studentBorrower.getState("update_status");
		if (!studentBorrowerUpdateStatusProps.getProperty("Status").equals("0")){
			commitLostBooksStatusProps.setProperty("LongMessage", commitLostBooksStatusProps.getProperty("LongMessage") + 
					" unable to update student borrower monetary penalty " + studentBorrowerProps.getProperty("BannerID") + "\n" +
					"(most likely cause monetary penalty exceed six characters)\nstudent borrower monetary penalty can only have 6 characters\n");
			commitLostBooksStatusProps.setProperty("Status", "1");
		}
		isAllDone = true;
	}
	public void moveLbToCo(int rentalLbSelectedIndex){
		rentalsLbToCoStatusProps = new Properties();
		if (isAllDone){
			rentalsLbToCoStatusProps.setProperty("Status", "-1");
			rentalsLbToCoStatusProps.setProperty("Message", "transaction already completed");
		} else if (rentalLbSelectedIndex < 0 || rentalLbSelectedIndex >= rentalsToProcessAsLostVecProps.size()){
			rentalsLbToCoStatusProps.setProperty("Status", "-1");
			rentalsLbToCoStatusProps.setProperty("Message", "invalid index");
		} else {
			Properties propsR = rentalsToProcessAsLostVecProps.remove(rentalLbSelectedIndex);
			Properties propsB = rentalsBookToProcessAsLostVecProps.remove(rentalLbSelectedIndex);
			rentalsStillCheckedOutVecProps.add(propsR);
			rentalsBookStillCheckedOutVecProps.add(propsB);
			rentalsLbToCoStatusProps.setProperty("Status", "0");
			rentalsLbToCoStatusProps.setProperty("Message", "");
		}
	}
	public void preMoveCoToLb(int rentalCoSelectedIndex){
		checkoutRentalSelectedIndex = rentalCoSelectedIndex;
		rentalsCoToLbStatusProps = new Properties();
		if (isAllDone){
			rentalsCoToLbStatusProps.setProperty("Status", "-1");
			rentalsCoToLbStatusProps.setProperty("Message", "transaction already completed");
			return;
		} else if (rentalCoSelectedIndex < 0 || rentalCoSelectedIndex >= rentalsStillCheckedOutVecProps.size()){
			rentalsCoToLbStatusProps.setProperty("Status", "-1");
			rentalsCoToLbStatusProps.setProperty("Message", "invalid index");
		} else {
			lostBookToModifyProps = rentalsBookStillCheckedOutVecProps.get(rentalCoSelectedIndex);
			createAndShowModifyLostBookView();
		}
	}
	public void searchForStudentBorrowers(){
		StudentBorrowerCollection studentBorrowerCollection = new StudentBorrowerCollection();
		studentBorrowerCollection.findSBWithCheckedOutBooks(searchProps.getProperty("FirstName"), 
				searchProps.getProperty("LastName"), 
				searchProps.getProperty("ContactPhone"), 
				searchProps.getProperty("Email"));
		studentBorrowerCollectionVecProps = (Vector<Properties>)studentBorrowerCollection.getState("deep_props_vec");
		if (studentBorrowerCollectionVecProps == null || studentBorrowerCollectionVecProps.size() < 1){
			transactionErrorMessage = "no results";
		} else {
			transactionErrorMessage = "";
			createAndShowStudentBorrowerCollectionView();
		}
	}
	public void showSelectedStudentBorrowersRentals(){
		if (sbSelectedIndex < 0 || sbSelectedIndex >= studentBorrowerCollectionVecProps.size()){
			transactionErrorMessage = "sb selected index out of bounds";
			return;
		}
		studentBorrowerProps = studentBorrowerCollectionVecProps.get(sbSelectedIndex);
		RentalCollection rentalCollection = new RentalCollection();
		rentalCollection.findCheckedOutRentals(studentBorrowerProps.getProperty("BannerID"));
		rentalCollectionVecProps = (Vector<Properties>)rentalCollection.getState("deep_props_vec");
		rentalsBooksVecProps = new Vector<Properties>();
		rentalsStillCheckedOutVecProps = new Vector<Properties>();
		rentalsToProcessAsLostVecProps = new Vector<Properties>();
		rentalsBookStillCheckedOutVecProps = new Vector<Properties>();
		rentalsBookToProcessAsLostVecProps = new Vector<Properties>();
		int numLateRentals = 0;
		for(int i = 0;i < rentalCollectionVecProps.size();i++){
			Properties propsR = rentalCollectionVecProps.get(i);
			Properties propsB = getBook(propsR.getProperty("BookID"));
			rentalsBooksVecProps.add(propsB);
			rentalsStillCheckedOutVecProps.add(propsR);
			rentalsBookStillCheckedOutVecProps.add(propsB);
		}
		createAndShowRentalProcessLostBookCollectionView();
	}
	public Properties getBook(String bookBarcode){
		Properties props = null;
		try{
			Book book = new Book(bookBarcode);
			props = (Properties)book.getState("deep_props");
		} catch (InvalidPrimaryKeyException e) {
			props = new Properties();
			props.setProperty("Error", "No Book found with this barcode");
		}
		return props;
	}
	protected void createAndShowStudentBorrowerCollectionView(){
		ViewL localView = ViewFactory.createView("StudentBorrowerCollectionView", this);
		myViews.put("StudentBorrowerCollectionView", localView);
		localView.setViewTitle("Lost Book, Select Borrowers");
		localView.refreshLogoTitle();
		swapToView(localView);
		myHistory.push(localView);
	}
	protected void createAndShowRentalProcessLostBookCollectionView() {
		ViewL localView = ViewFactory.createView("RentalProcessLostBookCollectionView", this);
		myViews.put("RentalProcessLostBookCollectionView", localView);
		localView.setViewTitle("Lost Book, Select Books");
		localView.refreshLogoTitle();
		swapToView(localView);
		myHistory.push(localView);

	}
	protected void createAndShowModifyLostBookView() {
		ViewL localView = ViewFactory.createView("ModifyLostBookView", this);
		myViews.put("ModifyLostBookView", localView);
		localView.setViewTitle("Lost Book, Modify Book");
		localView.refreshLogoTitle();
		swapToView(localView);
		myHistory.push(localView);
	}
	@Override
	public ViewL createTransactionView(){
		ViewL localView = (ViewL)myViews.get("StudentBorrowerSearchView");
		if(localView == null){
			localView = ViewFactory.createView("StudentBorrowerSearchView", this);
			localView.setViewTitle("Lost Book, Search Borrowers");
			localView.refreshLogoTitle();
			myViews.put("StudentBorrowerSearchView", localView);
		}
		return localView;
	}
	public void showHistoricalView(){
		myHistory.pop();
		ViewL viewL = myHistory.peek();
		if (viewL.getViewClassName().equals("RentalProcessLostBookCollectionView")){
			viewL.updateState("refresh", null);
		}
		swapToView(viewL);
	}
	private String getTodaysDate(){
		String myDate;
		Calendar today = Calendar.getInstance();
		int year = today.get(Calendar.YEAR);
		int month = (today.get(Calendar.MONTH) +1);
		int day = today.get(Calendar.DATE);
		if(month < 10 && day < 10){
			myDate = year + "-0" + month + "-0" + day;
		}
		else if(month < 10){
			myDate = year + "-0" + month + "-" + day;
		}
		else if(day < 10){
			myDate = year + "-" + month + "-0" + day;
		}
		else{
			myDate = year + "-" + month + "-" + day;
		}

		return myDate;
	}
}
