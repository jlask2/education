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

public class AddBookTransaction extends TransactionL{
	//GUI Components
	private String transactionErrorMessage = "";

	private BookBarcodePrefix ourPrefix;

	//Constructor
	public AddBookTransaction(Worker worker) throws Exception{
		super(worker);
	}

	protected void setDependencies(){
		dependencies = new Properties();
		dependencies.setProperty("DoYourJob", "TransactionError");
		dependencies.setProperty("CancelAddBook", "CancelTransaction");
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
		return null;
	}

	public void stateChangeRequest(String key, Object value){
		if(key.equals("DoYourJob") == true){
			doYourJob();
		}
		if(key.equals("Submit") == true){
			processTransaction((Properties) value);
		}
		myRegistry.updateSubscribers(key, this);
	}

	public boolean getDiscipline(String prefix){
		try{
			ourPrefix = new BookBarcodePrefix(prefix.substring(0,3));
			return true;
		}
		catch(InvalidPrimaryKeyException e){
			transactionErrorMessage = "Invalid barcode prefix.";
			return false;
		}
	}

	public void processTransaction(Properties propsToSubmit){
		Properties myPropertiesToSubmit = propsToSubmit;
		boolean result = getDiscipline((String)myPropertiesToSubmit.getProperty("Barcode"));
		if(result == true){
			myPropertiesToSubmit.setProperty("Discipline", (String)ourPrefix.getState("Discipline"));
			//DEBUG System.out.println(myPropertiesToSubmit.getProperty("Discipline"));

			//create a new book using the properties provided by the user

			Book book = new Book(myPropertiesToSubmit);

			//try to insert the worker
			book.insert();

			//let's get our status message
			transactionErrorMessage = (String)book.getState("UpdateStatusMessage");
		}
	}

	protected ViewL createTransactionView(){
		ViewL localView = (ViewL)myViews.get("AddBookView");

		if(localView == null){
			//create our initial view
			localView = ViewFactory.createView("AddBookView", this);

			myViews.put("AddBookView", localView);

			return localView;
		}
		else{
			return localView;
		}
	}
}