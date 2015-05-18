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

public class AddStudentBorrowerTransaction extends TransactionL{
	//GUI Components
	private String transactionErrorMessage = "";

	//Constructor
	public AddStudentBorrowerTransaction(Worker worker) throws Exception{
		super(worker);
	}

	protected void setDependencies(){
		dependencies = new Properties();
		dependencies.setProperty("DoYourJob", "TransactionError");
		dependencies.setProperty("CancelAddStudentBorrower", "CancelTransaction");
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
			processTransaction((Properties)value);
		}
		myRegistry.updateSubscribers(key, this);
	}

	//our main logic
	public void processTransaction(Properties propsToSubmit){
		StudentBorrower studentBorrower = new StudentBorrower(propsToSubmit);

		studentBorrower.insert();

		transactionErrorMessage = (String)studentBorrower.getState("UpdateStatusMessage");
	}

	protected ViewL createTransactionView(){
		ViewL localView = (ViewL)myViews.get("AddStudentBorrowerView");

		if (localView == null){
			localView = ViewFactory.createView("AddStudentBorrowerView", this);
			localView.setViewTitle("Add a Student Borrower");
			localView.refreshLogoTitle();
			myViews.put("AddStudentBorrowerView", localView);

			return localView;
		}
		else{
			return localView;
		}
	}

}