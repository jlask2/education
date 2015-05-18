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

public class ChangePasswordTransaction extends TransactionL{
	//GUI Components
	private String transactionErrorMessage = "";

	//Constructor
	public ChangePasswordTransaction(Worker worker) throws Exception{
		super(worker);
	}

	protected void setDependencies(){
		dependencies = new Properties();
		dependencies.setProperty("DoYourJob", "TransactionError");
		dependencies.setProperty("CancelChangePassword", "CancelTransaction");
		myRegistry.setDependencies(dependencies);
	}

	public Object getState(String key){
		if(key.equals("TransactionError") == true){
			return transactionErrorMessage;
		}
		else{
			return myWorker.getState(key);
		}
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

	public void processTransaction(Properties propsToUpdate){
		Worker worker = new Worker(propsToUpdate);

		//try and update the worker
		worker.update();

		//Let's get your update message
		transactionErrorMessage = (String)worker.getState("UpdateStatusMessage");
	}

	public ViewL createTransactionView(){
		ViewL localView = (ViewL)myViews.get("ChangePasswordView");

		if(localView == null){
			localView = ViewFactory.createView("ChangePasswordView", this);

			myViews.put("ChangePasswordView", localView);

			return localView;
		}
		else{
			return localView;
		}
	}
}