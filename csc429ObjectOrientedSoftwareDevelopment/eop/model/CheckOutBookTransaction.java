// tabs=4
// 
//************************************************************
//	COPYRIGHT 2014 Sandeep Mitra and Stephanie Cruz
//    The College at Brockport, State University of New York. -
//	  ALL RIGHTS RESERVED
//
// This file is the product of The College at Brockport and cannot
// be reproduced, copied, or used in any shape or form without
// the express written consent of The College at Brockport.
//************************************************************
//specify the package
package model;

//system imports
import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.print.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Stack;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//GUI imports
import javax.swing.JOptionPane;


//project imports
import model.Book;
import model.Receipts;
import userinterface.MainFrame;
import userinterface.ViewL;
import userinterface.ViewFactory;
import exception.InvalidPrimaryKeyException;

public class CheckOutBookTransaction extends TransactionL{

	private String transactionErrorMessage = "";

	private StudentBorrower myStudentBorrowerToVerify;

	private MaxDueDate maxDueDate;

	private Rental myRental;

	private String mdd;

	private Receipts receipts;

	private Properties receipt;
	private Vector<Rental> receiptRentals;

	private Vector<Properties> rentalsAlreadyCheckedOutVecProps;
	private Vector<Properties>	rentalsBooksAlreadyCheckedOutVecProps;
	private Vector<Properties> rentalsToCheckOutVecProps;
	private Vector<Properties> rentalsBooksToCheckOutVecProps;
	private Vector<Properties> rentalCollectionVecProps;
	private Vector<Properties> rentalsBooksVecProps;
	private Properties rentalBookProps;
	private Properties validateBarcodeProps;
	private Properties commitCheckOutStatusProps;
	private Properties rentalsAddToCoStatusProps;
	private Properties rentalsRemoveFromCoStatusProps;
	private Vector<Properties> studentBorrowerCollectionVecProps;
	private Properties studentBorrowerProps;
	private int sbSelectedIndex;
	private Properties workerProps;
	private Properties searchProps;
	private boolean isAllDone;
	private static Pattern patternBarcode = Pattern.compile("^[0-9]{5}$");
	private static Pattern patternBarcodePartial = Pattern.compile("^[0-9]*$");

	//Constructor
	//----------------------------------------------------------------
	public CheckOutBookTransaction(Worker worker) throws Exception{
		super(worker);
		isAllDone = false;
		myHistory = new Stack<ViewL>();
		workerProps = (Properties)worker.getState("deep_props");
	}

	//----------------------------------------------------------------
	protected void setDependencies(){
		dependencies = new Properties();
		dependencies.setProperty("DoCheckOutBook", "TransactionErrorMessage");
		dependencies.setProperty("CancelCheckOutBook", "CancelTransaction");
		dependencies.setProperty("CancelModifyStudentBorrower", "CancelTransaction");
		myRegistry.setDependencies(dependencies);
	}

	//----------------------------------------------------------------
	public Object getState(String key){
		if(key.equals("TransactionError") == true){
			return transactionErrorMessage;
		}else if(key.equals("StudentBorrowerList") == true){
			System.out.println("!NO! CheckOutBookTransaction.getState(...) " + key);
			System.exit(-1);
			return null;
		}else if(key.equals("deep_props_vec_student_borrower_list")){
			return studentBorrowerCollectionVecProps;//;("deep_props_vec");
		}else if(key.equals("MyStudentBorrowerToVerify") == true){
			System.out.println("!NO! CheckOutBookTransaction.getState(...) " + key);
			System.exit(-1);
			return null;
		}else if(key.equals("deep_props_student_borrower_to_verify")){
			return myStudentBorrowerToVerify.getState("deep_props");
		}else if(key.equals("NumberOfRentals") == true){
			try{
				return receiptRentals.size();
			}catch(NullPointerException e){
				e.printStackTrace();
				transactionErrorMessage = "NoRentals";
			}
		}else if(key.equals("TransactionType") == true){
			return "CheckOutBookTransaction";
		}else if (key.equals("deep_props_vec_rentals_to_check_out")){
			return rentalsToCheckOutVecProps;
		} else if (key.equals("deep_props_vec_rentals_book_to_check_out")){
			return rentalsBooksToCheckOutVecProps;
		} else if (key.equals("deep_props_vec_rentals_already_checked_out")){
			return rentalsAlreadyCheckedOutVecProps;
		} else if (key.equals("deep_props_vec_rentals_book_already_checked_out")){
			return rentalsBooksAlreadyCheckedOutVecProps;
		} else if (key.equals("deep_props_vec_rental_list")){
			return rentalCollectionVecProps;
		} else if (key.equals("deep_props_vec_rental_list_books")){
			return rentalsBooksVecProps;
		} else if (key.equals("get_book")){
			return rentalBookProps;
		} else if (key.equals("barcode_status")){
			return validateBarcodeProps;
		} else if (key.equals("rental_remove_rental_from_co_status")){
			return rentalsRemoveFromCoStatusProps;
		} else if (key.equals("rental_move_rental_to_co_status")){
			return rentalsAddToCoStatusProps;
		}  else if (key.equals("deep_props_vec_rentals_to_check_out_size")){
			Properties props = new Properties();
			props.setProperty("Size", Integer.toString(rentalsToCheckOutVecProps.size()));
			return props;
		} else if (key.equals("deep_props_vec_rentals_book_to_check_out_size")){
			Properties props = new Properties();
			props.setProperty("Size", Integer.toString(rentalsBooksToCheckOutVecProps.size()));
			return props;
		} else if (key.equals("commit_check_out_status")){
			return commitCheckOutStatusProps;   
		}else{
			return myWorker.getState(key);
		}
		return null;
	}

	//----------------------------------------------------------------
	public void stateChangeRequest(String key, Object value) {
		if(key.equals("DoYourJob")){
			doYourJob();
		} else if(key.equals("Search")){
			searchProps = (Properties)value;
			searchForStudentBorrowers();
		} else if(key.equals("StudentBorrowerSelected")){
			sbSelectedIndex = (Integer)value;
			showSelectedStudentBorrower();
		} else if(key.equals("CancelThisView")){
			showHistoricalView();
		} else if(key.equals("PrintReceipt")){
			if (isAllDone){
				Date theDate = null;
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				try {
					theDate = formatter.parse(getDate());
					/*DEBUG*///System.out.println("getDate: "+ getDate() +" theDate: " + theDate.toString());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				receipt = new Properties();
				receipt.setProperty("BorrowerName", (String)studentBorrowerProps.getProperty("LastName") +", "+ (String)studentBorrowerProps.getProperty("FirstName"));
				receipt.setProperty("BorrowerID", (String)studentBorrowerProps.getProperty("BannerID"));
				receipt.setProperty("CheckOutWorkerName", (String)workerProps.getProperty("LastName") +", "+ (String)workerProps.getProperty("FirstName"));
				receipt.setProperty("CheckOutWorkerBannerID", (String)workerProps.getProperty("BannerID"));
				receipt.setProperty("ReceiptTime", getDate());
				receipt.setProperty("PrintTransaction", "CheckOut");

				receipts = new Receipts(rentalsToCheckOutVecProps);
				receipts.printReceipt(theDate, receipt);
			}
		} else if (key.equals("done")){
			stateChangeRequest("CancelCheckOutBook", null);
		} else if (key.equals("commit_check_out")){
			commit();
		} else if (key.equals("rental_move_rental_to_co")){
			addToCo((String)value);
		} else if (key.equals("rental_remove_rental_from_co")){
			removeFromCo((Integer)value);
		} else if (key.equals("GetStudentBorrowerByBannerID")){
			String bId = (String)value;
			transactionErrorMessage = "";
			StudentBorrowerCollection studentBorrowerCollection = new StudentBorrowerCollection();
			studentBorrowerCollection.findStudentBorrowerBannerId(bId);
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
		transactionErrorMessage = "";
		commitCheckOutStatusProps = new Properties();
		if (isAllDone){
			commitCheckOutStatusProps.setProperty("Status", "-1");
			commitCheckOutStatusProps.setProperty("Message", "transaction already completed");
			commitCheckOutStatusProps.setProperty("RentalsWritten", "0");
			commitCheckOutStatusProps.setProperty("RentalsNotWritten", "0");
			transactionErrorMessage = "transaction already completed";
		} else {
			int rentalsWritten = 0;
			int rentalsNotWritten = 0;
			if (rentalsToCheckOutVecProps.size() < 1){
				commitCheckOutStatusProps.setProperty("Status", "-1");
				commitCheckOutStatusProps.setProperty("Message", "no books selected for check out");
				transactionErrorMessage = "no books selected for check out";
			} else {
				for(int i = 0;i < rentalsToCheckOutVecProps.size();i++){//slight attempt to be concurrent (potentially if worker walked away mid use case), also want clean copy
					Rental rental = null;
					Properties propsRi = rentalsToCheckOutVecProps.get(i);
					propsRi.remove("_status_msg_");
					try{
						rental = new Rental(propsRi);
					} catch (Exception e){
						rental = null;
					}
					if (rental == null){
						propsRi.setProperty("_status_msg_", "error checking out this book");
						transactionErrorMessage = "error checking out this book";
						rentalsNotWritten++;
					} else {
						Properties propsRo = (Properties)rental.getState("ref_props");
						/*DEBUG*///System.out.println(propsRo.getProperty("BorrowerID")); 
						/*DEBUG*///System.out.println(propsRo.getProperty("BookID"));
						/*DEBUG*///System.out.println(propsRo.getProperty("CheckoutWorkerID"));
						/*DEBUG*///System.out.println(propsRo.getProperty("CheckoutDate"));
						/*DEBUG*///System.out.println(propsRo.getProperty("DueDate"));
						rental.insert();
						String roStatus = (String)rental.getState("UpdateStatusMessage");
						transactionErrorMessage = (String)rental.getState("UpdateStatusMessage");
						/*DEBUG*///System.out.println("roStatus: "+roStatus);
						if (!roStatus.equals("Rental inserted successfully.")){
							rentalsNotWritten++;
							propsRi.setProperty("_status_msg_", "error updating rental");
						} else {
							rentalsWritten++;
						}
					}
				}
				if (rentalsWritten > 0){ 
					commitCheckOutStatusProps.setProperty("Status", "0");
					commitCheckOutStatusProps.setProperty("Message", "");
					isAllDone = true;
				} else {
					commitCheckOutStatusProps.setProperty("Status", "-1");
					commitCheckOutStatusProps.setProperty("Message", "no checked out books were checked in");
				}
			}
			commitCheckOutStatusProps.setProperty("RentalsWritten", Integer.toString(rentalsWritten));
			commitCheckOutStatusProps.setProperty("RentalsNotWritten", Integer.toString(rentalsNotWritten));
		}
	}

	public void removeFromCo(int rentalCoSelectedIndex){
		rentalsRemoveFromCoStatusProps = new Properties();
		if (isAllDone){
			rentalsRemoveFromCoStatusProps.setProperty("Status", "-1");
			rentalsRemoveFromCoStatusProps.setProperty("Message", "transaction already completed");
		} else if (rentalCoSelectedIndex < 0 || rentalCoSelectedIndex >= rentalsToCheckOutVecProps.size()){
			rentalsRemoveFromCoStatusProps.setProperty("Status", "-1");
			rentalsRemoveFromCoStatusProps.setProperty("Message", "invalid index");
		} else {
			rentalsToCheckOutVecProps.remove(rentalCoSelectedIndex);
			rentalsBooksToCheckOutVecProps.remove(rentalCoSelectedIndex);
			rentalsRemoveFromCoStatusProps.setProperty("Status", "0");
			rentalsRemoveFromCoStatusProps.setProperty("Message", "");
		}
	}

	public void addToCo(String barcodeToMove){
		transactionErrorMessage = "";
		rentalsAddToCoStatusProps = new Properties();
		if (isAllDone){
			rentalsAddToCoStatusProps.setProperty("Status", "-1");
			rentalsAddToCoStatusProps.setProperty("Message", "transaction already completed");
			return;
		}
		searchForBooks(barcodeToMove);
		/*DEBUG*///System.out.println("Exception: "+transactionErrorMessage);
		if(transactionErrorMessage.equals("No rental found with bookID where the check in date is null")){
			rentalsAddToCoStatusProps.setProperty("Status", "0");
			rentalsAddToCoStatusProps.setProperty("Message", "Added book to check out");
			transactionErrorMessage = "Added book to check out";
		} else {
			rentalsAddToCoStatusProps.setProperty("Status", "-1");
			rentalsAddToCoStatusProps.setProperty("Message", "barcode not found");

			if(transactionErrorMessage.equals("This book is already checked out")){
				transactionErrorMessage = "This book is already checked out";
			}else if(transactionErrorMessage.equals("No Book found with this barcode")){
				transactionErrorMessage = "Barcode not found";
			}
		}
	}

	public void searchForStudentBorrowers(){
		StudentBorrowerCollection studentBorrowerCollection = new StudentBorrowerCollection();
		studentBorrowerCollection.findStudentBorrowers(searchProps.getProperty("FirstName"), 
				searchProps.getProperty("LastName"), 
				searchProps.getProperty("ContactPhone"), 
				searchProps.getProperty("Email"),
				searchProps.getProperty("IncludedInactive"));
		studentBorrowerCollectionVecProps = (Vector<Properties>)studentBorrowerCollection.getState("deep_props_vec");
		if (studentBorrowerCollectionVecProps == null || studentBorrowerCollectionVecProps.size() < 1){
			transactionErrorMessage = "no results";
		} else {
			transactionErrorMessage = "";
			createAndShowStudentBorrowerCollectionView();
		}
	}

	public void showSelectedStudentBorrower(){
		if (sbSelectedIndex < 0 || sbSelectedIndex >= studentBorrowerCollectionVecProps.size()){
			transactionErrorMessage = "sb selected index out of bounds";
			return;
		}
		studentBorrowerProps = studentBorrowerCollectionVecProps.get(sbSelectedIndex);
		RentalCollection rentalCollection = new RentalCollection();
		rentalCollection.findCheckedOutRentals(studentBorrowerProps.getProperty("BannerID"));
		rentalCollectionVecProps = (Vector<Properties>)rentalCollection.getState("deep_props_vec");
		rentalsBooksVecProps = new Vector<Properties>();//instantiate anyways
		rentalsToCheckOutVecProps = new Vector<Properties>();//instantiate anyways
		rentalsBooksToCheckOutVecProps = new Vector<Properties>();//instantiate anyways
		//rentalsAlreadyCheckedOutVecProps = new Vector<Properties>();//instantiate anyways, empty to start
		//rentalsBooksAlreadyCheckedOutVecProps = new Vector<Properties>();//instantiate anyways, empty to start
		//for(int i = 0;i < rentalCollectionVecProps.size();i++){
		//Properties propsR = rentalCollectionVecProps.get(i);
		//Properties propsB = getBook(propsR.getProperty("BookID"));
		//rentalsBooksVecProps.add(propsB);
		//Not implemented. For Future Alteration
		//rentalsAlreadyCheckedOutVecProps.add(propsR);//start with all rentals in already checked out
		//rentalsBooksAlreadyCheckedOutVecProps.add(propsB);
		//}
		/*if(rentalCollectionVecProps.size() < 1){
			transactionErrorMessage = "";
		} else {
			transactionErrorMessage = "";
		}*/
		boolean verified = verifiedStudentBorrower(studentBorrowerProps);
		updateState("transactionErrorMessage", transactionErrorMessage);
		if(verified){
			createAndShowRequestBarcodeView();
		}
	}

	//----------------------------------------------------------------
	protected boolean verifiedStudentBorrower(Properties sbv){
		if(((String)sbv.getProperty("BorrowerStatus")).equals("Delinquent")){
			int confirm = 2;
			transactionErrorMessage = "This student borrower is delinquent.";
			/*DEBUG*///System.out.println("Verified Student Borrower delinquent");	
			String cred = (String)workerProps.getProperty("Credentials");
			if(cred.equals("Administrator")){
				confirm = confirmPopUp("This student is delinquent.", "Do you want to override?");

			}
			if(confirm == 0){
				return true;
			}else{
				return false;
			}
		}
		/*DEBUG*///System.out.println("Verified Student Borrower not delinquent");
		return true;
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

	//
	//----------------------------------------------------------------
	protected void searchForBooks(String barcode){
		rentalBookProps = getBook(barcode);

		transactionErrorMessage = (String)rentalBookProps.getProperty("Error");

		if(transactionErrorMessage == null){
			if((rentalBookProps.getProperty("Status").equals("Inactive"))||((rentalBookProps.getProperty("Status").equals("Lost")))){
				transactionErrorMessage = "This is not a valid book to check out";
			}else{
				rentalsBooksVecProps.add(rentalBookProps);
				checkForExistingRental(rentalBookProps.getProperty("Barcode"));
			}
		}
	}

	//----------------------------------------------------------------
	protected void checkForExistingRental(String barcode){
		try {
			transactionErrorMessage = "";
			myRental = new Rental(barcode, "bookID");
			transactionErrorMessage = (String)myRental.getState("UpdateStatusMessage");
		} catch (Exception e){//(InvalidPrimaryKeyException e) {
			transactionErrorMessage = (String)myRental.getState("UpdateStatusMessage");
			/*DEBUG*///System.out.println("Exception: "+transactionErrorMessage);
			e.printStackTrace();
		}
		/*DEBUG*///System.out.println("B4: "+transactionErrorMessage);
		if(transactionErrorMessage.equals("There is a bookID where check in date is null")){
			transactionErrorMessage = "This book is already checked out";
		}else if(transactionErrorMessage.equals("No rental found with bookID where the check in date is null")){
			try {									 
				maxDueDate = new MaxDueDate();
			} catch (InvalidPrimaryKeyException e) {
				e.printStackTrace();
				transactionErrorMessage = "Error";
			}
			mdd = (String)maxDueDate.getState("CurrentMaxDueDate");
			/*DEBUG*///System.out.println("No rental:"+ transactionErrorMessage);
			confirmedRental();
		}else{
			/*DEBUG*///System.out.println("else: "+ transactionErrorMessage);
		}
	}

	//----------------------------------------------------------------
	protected void confirmedRental(){
		/*DEBUG*///System.out.println("1.)Checkout book confirm book ");
		Properties rentalPropsToInsert = new Properties();
		rentalPropsToInsert.setProperty("BorrowerID", (String)studentBorrowerProps.getProperty("BannerID"));
		rentalPropsToInsert.setProperty("BookID", rentalBookProps.getProperty("Barcode"));
		rentalPropsToInsert.setProperty("CheckoutWorkerID", (String)workerProps.getProperty("BannerID"));
		rentalPropsToInsert.setProperty("CheckoutDate", getTodaysDate());
		rentalPropsToInsert.setProperty("DueDate", mdd);
		/*DEBUG*///System.out.println("2.)Checkout book confirm book ");
		try{
			myRental = new Rental((String)rentalPropsToInsert.getProperty("BookID"),"bookID");
		} catch (InvalidPrimaryKeyException e) {
			transactionErrorMessage = "No book found with this barcode";
		}
		/*DEBUG*///System.out.println("3.)Checkout book confirm book ");
		/*DEBUG*///System.out.println(rentalPropsToInsert.getProperty("BorrowerID")); 
		/*DEBUG*///System.out.println(rentalPropsToInsert.getProperty("BookID"));
		/*DEBUG*///System.out.println(rentalPropsToInsert.getProperty("CheckoutWorkerID"));
		/*DEBUG*///System.out.println(rentalPropsToInsert.getProperty("CheckoutDate"));
		/*DEBUG*///System.out.println(rentalPropsToInsert.getProperty("DueDate"));	
		int i = 0;
		boolean already = false;
		while(i < rentalsToCheckOutVecProps.size()){
			Properties propsB = rentalsToCheckOutVecProps.get(i);
			String propsBarcode = propsB.getProperty("BookID");
			if (((String)rentalPropsToInsert.getProperty("BookID")).equals(propsBarcode)){
				already = true;
				break;
			} else {
				i++;
			}
		}
		transactionErrorMessage = (String)myRental.getState("UpdateStatusMessage");
		/*DEBUG*///System.out.println("confirmed message: "+ transactionErrorMessage);
		if((transactionErrorMessage.equals("No rental found with bookID where the check in date is null"))&&(already == false)){
			rentalsToCheckOutVecProps.add(rentalPropsToInsert);
			rentalsBooksToCheckOutVecProps.add(rentalBookProps);
		}else if(already == true){
			transactionErrorMessage = "This book is already checked out";
		}else{
			transactionErrorMessage = "No book found with this barcode";
		}
	}

	//Create pop up to Override delinquency Status. For Admin only 
	//----------------------------------------------------------------
	private int confirmPopUp(String PU_Title, String PU_Message){
		Object[] options = {"Confirm", "Cancel"};
		return JOptionPane.showOptionDialog(MainFrame.getInstance(), 
				PU_Message, PU_Title,
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]);
	}

	@Override
	//----------------------------------------------------------------
	protected ViewL createTransactionView(){
		ViewL localView = (ViewL)myViews.get("StudentBorrowerSearchView");

		if(localView == null){
			localView = ViewFactory.createView("StudentBorrowerSearchView", this);
			localView.setViewTitle("Student Borrower to Check Out Book(s)");
			localView.refreshLogoTitle();
			myViews.put("StudentBorrowerSearchView", localView);
			myHistory.push(localView);
			return localView;
		}
		else{
			myHistory.push(localView);
			return localView;
		}
	}

	//----------------------------------------------------------------
	protected void showHistoricalView(){
		myHistory.pop();
		ViewL historicalView = myHistory.peek();
		if(historicalView == null){
			myHistory.pop();
			createAndShowStudentBorrowerCollectionView();
		}
		else{
			swapToView(historicalView);
		}
	}

	//----------------------------------------------------------------
	protected void createAndShowStudentBorrowerCollectionView(){
		ViewL localView = ViewFactory.createView("StudentBorrowerCollectionView", this);
		localView.setViewTitle("Select Borrower to Check Out Book(s)");
		localView.refreshLogoTitle();
		myViews.put("StudentBorrowerCollectionView", localView);
		swapToView(localView);
		myHistory.push(null);
	}

	//----------------------------------------------------------------
	protected void createAndShowRequestBarcodeView(){
		ViewL localView = ViewFactory.createView("RequestBarcodeView", this);
		localView.setViewTitle("Scan Book to Check Out");
		localView.refreshLogoTitle();
		myViews.put("RequestBarcodeView", localView);
		swapToView(localView);
		myHistory.push(localView);
	}

	//---------------------------------------------------------
	protected String getTodaysDate(){
		String myDate;
		Calendar today = Calendar.getInstance();
		int year = today.get(Calendar.YEAR);
		int month = (today.get(Calendar.MONTH) +1);
		int day = today.get(Calendar.DATE);

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