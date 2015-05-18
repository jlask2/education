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
import java.util.Enumeration;
import java.util.Properties;
import java.util.Stack;
import java.util.Vector;





//project imports
import event.Event;
import exception.InvalidPrimaryKeyException;
import userinterface.ViewL;
import userinterface.ViewFactory;

public class SBWithCheckedOutBooksTransaction extends TransactionL{
	//GUI Components
	private String transactionErrorMessage = "";

	StudentBorrowerCollection myStudentBorrowers;

	private Properties exportStatusProps;

	Vector<StudentBorrower> myCollection;

	//Constructor
	public SBWithCheckedOutBooksTransaction(Worker worker) throws Exception{
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
			System.out.println("!NO! SBWithCheckedOutBooks.getState(...) " + key);
			System.exit(-1);
			return null;
		}
		if(key.equals("deep_props_vec_student_borrower_list")){
			return myStudentBorrowers.getState("deep_props_vec");
		}
		if(key.equals("TransactionType") == true){
			return("Student Borrowers With Checked Out Books");
		}
		if(key.equals("ExportStatus")){
			return exportStatusProps;
		}
		return null;
	}

	public void stateChangeRequest(String key, Object value){
		if(key.equals("DoYourJob") == true){
			doYourJob();
		}
		if(key.equals("GetStudentBorrowers") == true){
			processTransaction();
		} else if(key.equals("ExportReport")){
			exportCsv();
		}
		myRegistry.updateSubscribers(key, this);
	}

	public void exportCsv(){
		CSVHandler csvh = new CSVHandler(new String[] {"BannerID", "FirstName", "LastName", "ContactPhone", "Email", "Status"}, 
				(Vector<Properties>)myStudentBorrowers.getState("deep_props_vec"), 
				true, 
				true);
		String ReportsSBWCheckedOutBooksDirectoryName = Librarian.REPORTS_DIRECTORY + File.separator + "Student_Borrowers_With_Checked_Out_Books";
		File EOPReportsSBWCheckedOutBooksDirectory = new File(ReportsSBWCheckedOutBooksDirectoryName);
		if (EOPReportsSBWCheckedOutBooksDirectory.exists() == false)
		{
			boolean EOPReportsSBWCheckedOutBooksFlag = EOPReportsSBWCheckedOutBooksDirectory.mkdirs();
		}
		exportStatusProps = new Properties();
		exportStatusProps.setProperty("FileName", ReportsSBWCheckedOutBooksDirectoryName + File.separator + "borrowers_books_" + csvh.getTodaysDateAndTime() + ".csv");
		if (!csvh.writeCsvToFile(exportStatusProps.getProperty("FileName"))){
			exportStatusProps.setProperty("Status", "-1");
			exportStatusProps.setProperty("Message", "error exporting report");
		} else {
			exportStatusProps.setProperty("Status", "0");
			exportStatusProps.setProperty("Message", "export to file: " + exportStatusProps.getProperty("FileName"));
		}
	}

	public void processTransaction(){
		myStudentBorrowers = new StudentBorrowerCollection();
		myCollection = myStudentBorrowers.findSBWithCheckedOutBooks();
		int numStudents = (Integer)myStudentBorrowers.getState("StudentBorrowerCount");

		if(numStudents > 0){
			transactionErrorMessage = "";
		}
		else{
			transactionErrorMessage = "No students found matching search.";
		}
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
			localView.setViewTitle("Students with Checked Out Books");
			localView.refreshLogoTitle();
			return localView;
		}
		else{
			return localView;
		}
	}
}