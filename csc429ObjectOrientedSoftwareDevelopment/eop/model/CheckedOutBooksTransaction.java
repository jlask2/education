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
package model;//initial testpush

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

public class CheckedOutBooksTransaction extends TransactionL{
	//GUI Components
	private String transactionErrorMessage = "";

	BookCollection myCheckedOutBooks;

	private Properties exportStatusProps;

	Vector<Book> myCollection;

	//Constructor
	public CheckedOutBooksTransaction(Worker worker) throws Exception{
		super(worker);
	}

	protected void setDependencies(){
		dependencies = new Properties();
		dependencies.setProperty("DoYourJob", "TransactionError");
		dependencies.setProperty("CancelBooks" , "CancelTransaction");
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
		if(key.equals("BookList") == true){
			System.out.println("!NO! CheckedOutBookTransaction.getState(...) " + key);
			System.exit(-1);
			return null;
		}
		if(key.equals("deep_props_vec_book_list")){
			return myCheckedOutBooks.getState("deep_props_vec");
		}
		if(key.equals("TransactionType") == true){
			return("Checked Out Books");
		}
		if(key.equals("ExportStatus")){
			return exportStatusProps;
		}
		return null;
	}

	public void stateChangeRequest(String key, Object value){
		if(key.equals("DoYourJob") == true){
			doYourJob();
		} else if(key.equals("GetBooks") == true){
			processTransaction();
		} else if(key.equals("ExportReport")){
			exportCsv();
		}
		myRegistry.updateSubscribers(key, this);
	}
	public void exportCsv(){
		CSVHandler csvh = new CSVHandler(new String[] {"Barcode", "Title", "Author1", "Discipline", "Status"}, 
				(Vector<Properties>)myCheckedOutBooks.getState("deep_props_vec"), 
				true, 
				true);
		String ReportsCheckedOutBooksDirectoryName = Librarian.REPORTS_DIRECTORY + File.separator + "Checked_Out_Books";
		File EOPReportsCheckedOutBooksDirectory = new File(ReportsCheckedOutBooksDirectoryName);
		if (EOPReportsCheckedOutBooksDirectory.exists() == false)
		{
			boolean EOPReportsCheckedOutBooksFlag = EOPReportsCheckedOutBooksDirectory.mkdirs();
		}
		exportStatusProps = new Properties();
		exportStatusProps.setProperty("FileName", ReportsCheckedOutBooksDirectoryName + File.separator + "checked_out_books_" + csvh.getTodaysDateAndTime() + ".csv");
		if (!csvh.writeCsvToFile(exportStatusProps.getProperty("FileName"))){
			exportStatusProps.setProperty("Status", "-1");
			exportStatusProps.setProperty("Message", "error exporting report");
		} else {
			exportStatusProps.setProperty("Status", "0");
			exportStatusProps.setProperty("Message", "export to file: " + exportStatusProps.getProperty("FileName"));
		}
	}
	public void processTransaction(){
		myCheckedOutBooks = new BookCollection();
		myCollection = myCheckedOutBooks.findCheckedOutBooks();
		int numBooks = (Integer)myCheckedOutBooks.getState("BookCount");

		if(numBooks > 0){
			transactionErrorMessage = "";
		}
		else{
			transactionErrorMessage = "No books found matching search.";
		}
	}

	@Override
	protected void doYourJob(){
		stateChangeRequest("GetBooks", null);
		ViewL v = createTransactionView();
		swapToView(v);
		myHistory.push(v);
	}

	@Override
	protected ViewL createTransactionView() {
		ViewL localView = (ViewL)myViews.get("CheckedOutBooksView");

		if(localView == null){
			localView = ViewFactory.createView("CheckedOutBooksView", this);

			return localView;
		}
		else{
			return localView;
		}
	}
}