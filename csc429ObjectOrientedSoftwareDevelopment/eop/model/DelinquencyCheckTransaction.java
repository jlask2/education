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
import impresario.IView;

import javax.swing.JFrame;

import java.io.File;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Stack;
import java.util.Vector;






//project imports
import event.Event;
import exception.InvalidPrimaryKeyException;
import userinterface.ViewL;
import userinterface.ViewFactory;

public class DelinquencyCheckTransaction extends TransactionL{
	//GUI Components
	private String transactionErrorMessage = "";

	StudentBorrowerCollection myDelinquentBorrowers;

	private Properties exportStatusProps;

	Vector<StudentBorrower> myCollection;

	//Constructor
	public DelinquencyCheckTransaction(Worker worker) throws Exception{
		super(worker);
	}

	protected void setDependencies(){
		dependencies = new Properties();
		dependencies.setProperty("DoYourJob", "TransactionError");
		dependencies.setProperty("CancelReport" , "CancelTransaction");
		myRegistry.setDependencies(dependencies);
	}

	public Object getState(String key){
		if(key.equals("TransactionError") == true){
			return transactionErrorMessage;
		}
		if(key.equals("FirstName") == true){
			return myWorker.getState("FirstName");
		}
		if(key.equals("LastName") == true){
			return myWorker.getState("LastName");
		}
		if(key.equals("Credentials") == true){
			return myWorker.getState("Credentials");
		}
		if(key.equals("StudentBorrowerList") == true){
			System.out.println("!NO! DeliqunecyCheckTransaction.getState(...) " + key);
			System.exit(-1);
			return null;
		}
		if(key.equals("deep_props_vec_student_borrower_list")){
			return myDelinquentBorrowers.getState("deep_props_vec");
		}
		if(key.equals("TransactionType") == true){
			return("The Following Student Borrowers Are Delinquent");
		}
		if(key.equals("ExportStatus")){
			return exportStatusProps;
		}
		return null;
	}

	public void stateChangeRequest(String key, Object value){
		if(key.equals("DoYourJob") == true){
			doYourJob();
		} else if(key.equals("GetStudentBorrowers") == true){
			processTransaction();
		} else if(key.equals("ExportReport")){
			exportCsv();
		}
		myRegistry.updateSubscribers(key, this);
	}

	public void exportCsv(){
		CSVHandler csvh = new CSVHandler(new String[] {"BannerID", "FirstName", "LastName", "ContactPhone", "Email", "Status"}, 
				(Vector<Properties>)myDelinquentBorrowers.getState("deep_props_vec"), 
				true, 
				true);
		String ReportsDelinquentStudentBorrowersDirectoryName = Librarian.REPORTS_DIRECTORY + File.separator + "Delinquent_Student_Borrowers";
		File EOPReportsDelinquentStudentBorrowersDirectory = new File(ReportsDelinquentStudentBorrowersDirectoryName);
		if (EOPReportsDelinquentStudentBorrowersDirectory.exists() == false)
		{
			boolean EOPReportsDelinquentStudentBorrowersFlag = EOPReportsDelinquentStudentBorrowersDirectory.mkdirs();
		}
		exportStatusProps = new Properties();
		exportStatusProps.setProperty("FileName", ReportsDelinquentStudentBorrowersDirectoryName + File.separator + "delinquent_borrowers_" + csvh.getTodaysDateAndTime() + ".csv");
		if (!csvh.writeCsvToFile(exportStatusProps.getProperty("FileName"))){
			exportStatusProps.setProperty("Status", "-1");
			exportStatusProps.setProperty("Message", "error exporting report");
		} else {
			exportStatusProps.setProperty("Status", "0");
			exportStatusProps.setProperty("Message", "export to file: " + exportStatusProps.getProperty("FileName"));
		}
	}

	public void processTransaction(){
		myDelinquentBorrowers = new StudentBorrowerCollection();
		myCollection = myDelinquentBorrowers.findDelinquentBorrowers();
		int numStudents = (Integer)myDelinquentBorrowers.getState("StudentBorrowerCount");
		if(numStudents > 0){
			transactionErrorMessage = "";
			processDelinquents(myCollection);
		}
		else{
			transactionErrorMessage = "No students found matching search.";
		}
	}

	public void processDelinquents(Vector<StudentBorrower> myDelinquents){
		for(int i = 0; i < myDelinquents.size(); i++){
			StudentBorrower myBorrowerToProcess = myDelinquents.get(i);
			myBorrowerToProcess.updateState("BorrowerStatus", "Delinquent");
			myBorrowerToProcess.updateState("DateOfLatestBorrowerStatus", getTodaysDate());
			myBorrowerToProcess.update();
		}
	}

	public String getTodaysDate(){
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

	@Override
	protected void doYourJob(){
		stateChangeRequest("GetStudentBorrowers", null);
		ViewL v = createTransactionView();
		swapToView(v);
		myHistory.push(v);
	}

	@Override
	protected ViewL createTransactionView() {
		ViewL localView = (ViewL)myViews.get("StudentBorrowerReports");

		if(localView == null){
			localView = ViewFactory.createView("StudentBorrowerReports", this);
			localView.setViewTitle("Delinquent Students");
			localView.refreshLogoTitle();
			return localView;
		}
		else{
			return localView;
		}
	}
}