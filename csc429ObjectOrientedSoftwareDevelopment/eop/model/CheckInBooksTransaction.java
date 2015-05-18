package model;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.Stack;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.*;

import exception.InvalidPrimaryKeyException;
import model.Receipts;
import userinterface.ViewFactory;
import userinterface.ViewL;

public class CheckInBooksTransaction extends TransactionL{
	private Vector<Properties> studentBorrowerCollectionVecProps;
	private Properties studentBorrowerProps;
	private Vector<Properties> rentalCollectionVecProps;
	private Properties searchProps;
	private Vector<Properties> rentalsBooksVecProps;
	private Vector<Properties> rentalsStillCheckedOutVecProps;
	private Vector<Properties> rentalsToCheckInVecProps;
	private Vector<Properties> rentalsBookStillCheckedOutVecProps;
	private Vector<Properties> rentalsBookToCheckInVecProps;
	private Properties maxDueDateProps;
	private Properties rentalBookProps;
	private Properties validateBarcodeProps;
	private Properties rentalsCiToCoStatusProps;
	private Properties rentalsCoToCiStatusProps;
	private Properties commitCheckInStatusProps;
	private Properties workerProps;
	private Properties receipt;
	private Receipts receipts;
	private boolean isAllDone;
	private int sbSelectedIndex;
	private int maxDueDateInt;
	private int todaysDateInt;
	private String transactionErrorMessage = "";
	private static Pattern patternBarcode = Pattern.compile("^[0-9]{5}$");
	private static Pattern patternBarcodePartial = Pattern.compile("^[0-9]*$");
	public CheckInBooksTransaction(Worker worker) throws Exception{
		super(worker);
		isAllDone = false;
		myHistory = new Stack<ViewL>();
		workerProps = (Properties)worker.getState("deep_props");
		MaxDueDate mdd = new MaxDueDate();
		maxDueDateProps = (Properties)mdd.getState("deep_props");
		maxDueDateProps.setProperty("MaxDueDate", maxDueDateProps.getProperty("CurrentMaxDueDate").replaceAll("\\-", ""));
		maxDueDateProps.setProperty("TodaysDate", getTodaysDate().replaceAll("\\-", ""));
		maxDueDateInt = Integer.parseInt(maxDueDateProps.getProperty("MaxDueDate"));
		todaysDateInt = Integer.parseInt(maxDueDateProps.getProperty("TodaysDate"));
	}
	protected void setDependencies(){
		dependencies = new Properties();
		dependencies.setProperty("CancelCheckInABook", "CancelTransaction");
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
		} else if (key.equals("deep_props_vec_rentals_to_check_in")){
			return rentalsToCheckInVecProps;
		} else if (key.equals("deep_props_vec_rentals_book_still_checked_out")){
			return rentalsBookStillCheckedOutVecProps;
		} else if (key.equals("deep_props_vec_rentals_book_to_check_in")){
			return rentalsBookToCheckInVecProps;
		} else if (key.equals("get_book")){
			return rentalBookProps;
		} else if (key.equals("barcode_status")){
			return validateBarcodeProps;
		} else if (key.equals("rental_move_rental_co_to_ci_status")){
			return rentalsCoToCiStatusProps;
		} else if (key.equals("rental_move_rental_ci_to_co_status")){
			return rentalsCiToCoStatusProps;
		}  else if (key.equals("deep_props_vec_rentals_still_checked_out_size")){
			Properties props = new Properties();
			props.setProperty("Size", Integer.toString(rentalsStillCheckedOutVecProps.size()));
			return props;
		} else if (key.equals("deep_props_vec_rentals_to_check_in_size")){
			Properties props = new Properties();
			props.setProperty("Size", Integer.toString(rentalsToCheckInVecProps.size()));
			return props;
		} else if (key.equals("deep_props_vec_rentals_book_still_checked_out_size")){
			Properties props = new Properties();
			props.setProperty("Size", Integer.toString(rentalsBookStillCheckedOutVecProps.size()));
			return props;
		} else if (key.equals("deep_props_vec_rentals_book_to_check_in_size")){
			Properties props = new Properties();
			props.setProperty("Size", Integer.toString(rentalsBookToCheckInVecProps.size()));
			return props;
		} else if (key.equals("commit_check_in_status")){
			return commitCheckInStatusProps;
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
		} else if (key.equals("rental_move_rental_co_to_ci")){
			moveCoToCi((String)value);
		} else if (key.equals("rental_move_rental_ci_to_co")){
			moveCiToCo((Integer)value);
		} else if (key.equals("validate_barcode")){
			validateBarcode((String)value);
		} else if(key.equals("get_book")){
			rentalBookProps = getBook(((Properties)value).getProperty("Barcode"));
		} else if(key.equals("CancelThisView")){
			showHistoricalView();
		} else if (key.equals("done")){
			stateChangeRequest("CancelCheckInABook", null);
		} else if (key.equals("commit_check_in")){
			commit();
		} else if (key.equals("print_receipt")){
			if (isAllDone){
				Date theDate = null;
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				try {
					theDate = formatter.parse(getDate());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				receipt = new Properties();
				receipt.setProperty("BorrowerName", (String)studentBorrowerProps.getProperty("LastName") +", "+ (String)studentBorrowerProps.getProperty("FirstName"));
				receipt.setProperty("BorrowerID", (String)studentBorrowerProps.getProperty("BannerID"));
				receipt.setProperty("CheckInWorkerName", (String)workerProps.getProperty("LastName") +", "+ (String)workerProps.getProperty("FirstName"));
				receipt.setProperty("CheckInWorkerBannerID", (String)workerProps.getProperty("BannerID"));
				receipt.setProperty("ReceiptTime", getDate());
				receipt.setProperty("PrintTransaction", "CheckIn");
				receipts = new Receipts(rentalsToCheckInVecProps, rentalsStillCheckedOutVecProps);
				receipts.printReceipt(theDate, receipt);
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
		commitCheckInStatusProps = new Properties();
		if (isAllDone){
			commitCheckInStatusProps.setProperty("Status", "-1");
			commitCheckInStatusProps.setProperty("Message", "transaction already completed");
			commitCheckInStatusProps.setProperty("RentalsWritten", "0");
			commitCheckInStatusProps.setProperty("RentalsNotWritten", "0");
		} else {
			int rentalsWritten = 0;
			int rentalsNotWritten = 0;
			if (rentalsToCheckInVecProps.size() < 1){
				commitCheckInStatusProps.setProperty("Status", "-1");
				commitCheckInStatusProps.setProperty("Message", "no books selected for check in");
			} else {
				for(int i = 0;i < rentalsToCheckInVecProps.size();i++){//slight attempt to be transactional
					Rental rental = null;
					Properties propsRi = rentalsToCheckInVecProps.get(i);
					propsRi.remove("_status_msg_");
					try{
						rental = new Rental(propsRi.getProperty("ID"), "rentalID");
					} catch (Exception e){
						rental = null;
					}
					if (rental == null){
						propsRi.setProperty("_status_msg_", "error checking in this book");
						rentalsNotWritten++;
					} else {
						Properties propsRo = (Properties)rental.getState("ref_props");
						if (propsRo.getProperty("CheckinDate") != null && !propsRo.getProperty("CheckinDate").equals("")){
							propsRi.setProperty("_status_msg_", "check in date is not null");
							rentalsNotWritten++;
						} else {
							propsRo.setProperty("CheckinDate", getTodaysDate());
							propsRo.setProperty("CheckinWorkerId", workerProps.getProperty("BannerID"));
							rental.updateCheckInBook();
							String roStatus = (String)rental.getState("UpdateStatusMessage");
							if (!roStatus.equals("")){
								rentalsNotWritten++;
								propsRi.setProperty("_status_msg_", "error updating rental");
							} else {
								rentalsWritten++;
							}
						}
					}
				}
				if (rentalsWritten > 0){ 
					commitCheckInStatusProps.setProperty("Status", "0");
					commitCheckInStatusProps.setProperty("Message", "");
					isAllDone = true;
				} else {
					commitCheckInStatusProps.setProperty("Status", "-1");
					commitCheckInStatusProps.setProperty("Message", "no checked out books were checked in");
				}

			}
			commitCheckInStatusProps.setProperty("RentalsWritten", Integer.toString(rentalsWritten));
			commitCheckInStatusProps.setProperty("RentalsNotWritten", Integer.toString(rentalsNotWritten));
		}
	}
	public void moveCiToCo(int rentalCiSelectedIndex){
		rentalsCiToCoStatusProps = new Properties();
		if (isAllDone){
			rentalsCiToCoStatusProps.setProperty("Status", "-1");
			rentalsCiToCoStatusProps.setProperty("Message", "transaction already completed");
		} else if (rentalCiSelectedIndex < 0 || rentalCiSelectedIndex >= rentalsToCheckInVecProps.size()){
			rentalsCiToCoStatusProps.setProperty("Status", "-1");
			rentalsCiToCoStatusProps.setProperty("Message", "invalid index");
		} else {
			Properties propsR = rentalsToCheckInVecProps.remove(rentalCiSelectedIndex);
			Properties propsB = rentalsBookToCheckInVecProps.remove(rentalCiSelectedIndex);
			rentalsStillCheckedOutVecProps.add(propsR);
			rentalsBookStillCheckedOutVecProps.add(propsB);
			rentalsCiToCoStatusProps.setProperty("Status", "0");
			rentalsCiToCoStatusProps.setProperty("Message", "");
		}
	}
	public void moveCoToCi(String barcodeToMove){
		rentalsCoToCiStatusProps = new Properties();
		if (isAllDone){
			rentalsCoToCiStatusProps.setProperty("Status", "-1");
			rentalsCoToCiStatusProps.setProperty("Message", "transaction already completed");
			return;
		}
		validateBarcode(barcodeToMove);
		if (validateBarcodeProps.getProperty("Status").equals("0")){
			int i = 0;
			while(i < rentalsStillCheckedOutVecProps.size()){
				Properties propsB = rentalsStillCheckedOutVecProps.get(i);
				String propsBarcode = propsB.getProperty("BookID");
				if (barcodeToMove.equals(propsBarcode)){
					break;
				} else {
					i++;
				}
			}
			if (i < rentalsStillCheckedOutVecProps.size()){
				Properties propsRrem = rentalsStillCheckedOutVecProps.remove(i);
				Properties propsBrem = rentalsBookStillCheckedOutVecProps.remove(i);
				rentalsToCheckInVecProps.add(propsRrem);
				rentalsBookToCheckInVecProps.add(propsBrem);
				rentalsCoToCiStatusProps.setProperty("Status", "0");
				rentalsCoToCiStatusProps.setProperty("Message", "move from still checked out to check in");
			} else {
				rentalsCoToCiStatusProps.setProperty("Status", "-1");
				rentalsCoToCiStatusProps.setProperty("Message", "barcode not found in checked out");
			}
		} else {
			rentalsCoToCiStatusProps.setProperty("Status", validateBarcodeProps.getProperty("Status"));
			rentalsCoToCiStatusProps.setProperty("Message", validateBarcodeProps.getProperty("Message"));
		} 
	}
	public void validateBarcode(String barcodeToValidate){
		validateBarcodeProps = new Properties();
		validateBarcodeProps.setProperty("Barcode", barcodeToValidate);
		if (validateBarcodeProps.getProperty("Barcode").length() < 1){
			validateBarcodeProps.setProperty("Status", "1");
			validateBarcodeProps.setProperty("Message", "");//field is blank, nothing entered yet
			return;
		}
		Matcher m = patternBarcodePartial.matcher(barcodeToValidate);
		if (m.find()){
			m = patternBarcode.matcher(barcodeToValidate);
			if (m.find()){
				validateBarcodeProps.setProperty("Status", "0");
				validateBarcodeProps.setProperty("Message", "valid barcode");
			} else {
				validateBarcodeProps.setProperty("Status", "1");
				validateBarcodeProps.setProperty("Message", "incomplete barcode");
			}
		} else {
			validateBarcodeProps.setProperty("Status", "-1");
			validateBarcodeProps.setProperty("Message", "invalid barcode");
		}
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
		rentalsToCheckInVecProps = new Vector<Properties>();
		rentalsBookStillCheckedOutVecProps = new Vector<Properties>();
		rentalsBookToCheckInVecProps = new Vector<Properties>();
		for(int i = 0;i < rentalCollectionVecProps.size();i++){
			Properties propsR = rentalCollectionVecProps.get(i);
			propsR.setProperty("RentalDueDate", propsR.getProperty("DueDate").replaceAll("\\-", ""));
			int rentalDueDateInt = Integer.parseInt(propsR.getProperty("RentalDueDate"));
			if (todaysDateInt > rentalDueDateInt){
				propsR.setProperty("Overdue", "Y");
			} else {
				propsR.setProperty("Overdue", "N");
			}
			Properties propsB = getBook(propsR.getProperty("BookID"));
			rentalsBooksVecProps.add(propsB);
			rentalsStillCheckedOutVecProps.add(propsR);
			rentalsBookStillCheckedOutVecProps.add(propsB);
		}
		createAndShowRentalCheckInCollectionView();
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
	protected void createAndShowRentalCheckInCollectionView() {
		ViewL localView = ViewFactory.createView("RentalCheckInCollectionView", this);
		myViews.put("RentalCheckInCollectionView", localView);
		localView.setViewTitle("Check In Book, Select Books");
		localView.refreshLogoTitle();
		swapToView(localView);
		myHistory.push(null);

	}
	protected void createAndShowStudentBorrowerCollectionView(){
		ViewL localView = ViewFactory.createView("StudentBorrowerCollectionView", this);
		myViews.put("StudentBorrowerCollectionView", localView);
		localView.setViewTitle("Check In Book, Select Borrowers");
		localView.refreshLogoTitle();
		swapToView(localView);
		myHistory.push(localView);
	}
	@Override
	public ViewL createTransactionView(){
		ViewL localView = (ViewL)myViews.get("StudentBorrowerCheckInSearchView");
		if(localView == null){
			localView = ViewFactory.createView("StudentBorrowerCheckInSearchView", this);
			localView.setViewTitle("Check In Book, Search Borrowers");
			localView.refreshLogoTitle();
			myViews.put("StudentBorrowerCheckInSearchView", localView);
		}
		return localView;
	}
	protected void showHistoricalView(){
		myHistory.pop();
		ViewL historicalView = myHistory.peek();
		if(historicalView == null){
			myHistory.pop();
		} else {
			swapToView(historicalView);
		}
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
	//---------------------------------------------------------
	protected String getDate(){
		String myDate;
		String myTime;
		Calendar today = Calendar.getInstance();
		int year = today.get(Calendar.YEAR);
		int month = (today.get(Calendar.MONTH) +1);
		int day = today.get(Calendar.DATE);
		int hour = today.get(Calendar.HOUR);
		int minute = (today.get(Calendar.MINUTE));

		if(day < 10 && month < 10){
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

		if(minute < 10 && hour < 10){
			myTime = "0" + hour + ":0" + minute;
		}
		else if(hour < 10){
			myTime = "0" + hour + ":" + minute;
		}
		else if(minute < 10){
			myTime = "" + hour + ":0" + minute;
		}
		else{
			myTime = "" + hour + ":" + minute;
		}
		myDate = myDate + " " + myTime;

		return myDate;
	}
}
