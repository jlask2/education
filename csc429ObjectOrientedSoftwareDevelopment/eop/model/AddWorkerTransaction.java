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

public class AddWorkerTransaction extends TransactionL{
	//GUI Components
	private String transactionErrorMessage = "";

	//Constructor
	public AddWorkerTransaction(Worker worker) throws Exception{
		super(worker);
	}

	protected void setDependencies(){
		dependencies = new Properties();
		dependencies.setProperty("DoYourJob", "TransactionError");
		dependencies.setProperty("CancelAddWorker", "CancelTransaction");
		myRegistry.setDependencies(dependencies);
	}

	public Object getState(String key){
		if (key.equals("TransactionError") == true){
			return transactionErrorMessage;
		}
		if (key.equals("FirstName") == true){
			return myWorker.getState("FirstName");
		}
		if (key.equals("LastName") == true){
			return myWorker.getState("LastName");
		}

		if (key.equals("Credentials") == true){
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

	//Our main logic
	public void processTransaction(Properties propsToSubmit){
		//Create a new worker using the properties provided by the user
		Worker worker = new Worker(propsToSubmit);

		//Try to insert the worker
		worker.insert();

		//Let's get our status message
		transactionErrorMessage = (String)worker.getState("UpdateStatusMessage");
	}

	protected ViewL createTransactionView(){
		ViewL localView = (ViewL)myViews.get("AddWorkerView");

		if(localView == null){
			//create our initial view
			localView = ViewFactory.createView("AddWorkerView", this);
			localView.setViewTitle("Add a Worker");
			localView.refreshLogoTitle();
			myViews.put("AddWorkerView", localView);

			return localView;
		}
		else{
			return localView;
		}
	}
}