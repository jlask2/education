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
import impresario.IModel;
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

public class ModifyWorkerTransaction extends TransactionL{
	//GUI Components
	private String transactionErrorMessage = "";

	WorkerCollection myWorkerCollection;

	Worker myWorkerToModify;

	Vector<Worker> myCollection;

	int myWorkerIndex;

	//keep track of our search information so that we can update the table
	String myWorkerFN;
	String myWorkerLN;
	String myWorkerPhone;
	String myWorkerEmail;
	String includeInactive;
	String bIDToOmit;

	//Constructor
	public ModifyWorkerTransaction(Worker worker) throws Exception{
		super(worker);

		myHistory = new Stack<ViewL>();
	}

	protected void setDependencies(){
		dependencies = new Properties();
		dependencies.setProperty("CancelSearchWorker", "CancelTransaction");
		dependencies.setProperty("DoWorkerSearch", "TransactionErrorMessage");
		myRegistry.setDependencies(dependencies);
	}

	public Object getState(String key){
		if(key.equals("TransactionError") == true){
			return transactionErrorMessage;
		}
		if(key.equals("WorkerList") == true){
			System.out.println("!NO! ModifyWorkerTransaction.getState(...) " + key);
			System.exit(-1);
			return null;
		}
		if(key.equals("deep_props_vec_worker_list")){
			return myWorkerCollection.getState("deep_props_vec");
		}
		if(key.equals("MyWorkerToModify") == true){
			System.out.println("!NO! ModifyWorkerTransaction.getState(...) " + key);
			System.exit(-1);
			return null;
		}
		if(key.equals("deep_props_worker_to_modify")){
			return myWorkerToModify.getState("deep_props");
		}
		if(key.equals("TransactionType") == true){
			return "ModifyWorkerTransaction";
		}
		else{
			return myWorker.getState(key);
		}
	}

	public void stateChangeRequest(String key, Object value) {
		if(key.equals("DoYourJob")){
			doYourJob();
		}
		if(key.equals("Submit")){
			processTransaction((Properties)value);
		}
		if(key.equals("Search")){
			Properties mySearchProperties = (Properties)value;
			myWorkerFN = mySearchProperties.getProperty("FirstName");
			myWorkerLN = mySearchProperties.getProperty("LastName");
			myWorkerPhone = mySearchProperties.getProperty("ContactPhone");
			myWorkerEmail = mySearchProperties.getProperty("Email");
			includeInactive = mySearchProperties.getProperty("IncludedInactive");
			bIDToOmit = (String) myWorker.getState("BannerID");

			searchForWorkers(myWorkerFN, myWorkerLN, myWorkerPhone, myWorkerEmail, includeInactive, bIDToOmit);
		}
		if(key.equals("GetWorkerByBannerID")){
			showWorkerToModify((String)value);
		}
		if(key.equals("WorkerSelected")){
			showSelectedWorker((int)value);
		}
		if(key.equals("CancelThisView")){
			showHistoricalView();
		}
		myRegistry.updateSubscribers(key, this);
	}

	//Our search logic is in this method
	public void searchForWorkers(String fName, String lName, String phone, String email, String iInactive, String omit){
		myWorkerCollection = new WorkerCollection();
		myCollection = myWorkerCollection.findWorkers(fName, lName, phone, email, iInactive, omit);
		int numWorkers = (Integer)myWorkerCollection.getState("WorkerCount");

		if(numWorkers > 0){
			transactionErrorMessage = "";
			createAndShowWorkerCollectionView();
		}
		else{
			transactionErrorMessage = "No workers found matching search.";
		}
	}

	public void showSelectedWorker(int whichWorker){
		myWorkerIndex = whichWorker;
		myWorkerToModify = myCollection.get(whichWorker);
		createAndShowModifyWorkerView();
	}

	public void showWorkerToModify(String myWorkerId){
		try{
			myWorkerToModify = new Worker(myWorkerId);
			transactionErrorMessage = "";
			createAndShowModifyWorkerView();
		}
		catch(InvalidPrimaryKeyException ex){
			transactionErrorMessage = "No worker found with this banner ID.";
		}
	}

	//Our main logic is in this method
	public void processTransaction(Properties propsToUpdate){
		Worker worker = new Worker(propsToUpdate);

		//try and update the worker
		worker.update();

		//Let's get our update message
		transactionErrorMessage = (String)worker.getState("UpdateStatusMessage");
		//DEBUG System.out.println(transactionErrorMessage);

		//Let's update the worker in our collection as well if history requires it
		ViewL myCurrentView = myHistory.pop();
		ViewL previousView = myHistory.peek();
		if(previousView == null){
			myWorkerCollection = new WorkerCollection();
			myCollection = myWorkerCollection.findWorkers(myWorkerFN, myWorkerLN, myWorkerPhone, myWorkerEmail, includeInactive, bIDToOmit);
		}

		myHistory.push(myCurrentView);
	}

	@Override
	public ViewL createTransactionView(){
		ViewL localView = (ViewL)myViews.get("WorkerSearchView");

		if(localView == null){
			localView = ViewFactory.createView("WorkerSearchView", this);

			myViews.put("WorkerSearchView", localView);
			localView.setViewTitle("Search for a Worker To Modify");
			localView.refreshLogoTitle();
			return localView;
		}
		else{
			return localView;
		}
	}

	protected void createAndShowModifyWorkerView(){
		ViewL localView = ViewFactory.createView("ModifyWorkerView", this);
		myViews.put("ModifyWorkerView", localView);
		localView.setViewTitle("Modify this Worker's Information");
		localView.refreshLogoTitle();
		swapToView(localView);
		myHistory.push(localView);
	}

	protected void showHistoricalView(){
		myHistory.pop();
		ViewL historicalView = myHistory.peek();
		if(historicalView == null){
			myHistory.pop();
			createAndShowWorkerCollectionView();
		}
		else{
			swapToView(historicalView);
		}
	}

	protected void createAndShowWorkerCollectionView(){
		ViewL localView = ViewFactory.createView("WorkerCollectionView", this);
		myViews.put("WorkerCollectionView", localView);
		localView.setViewTitle("Pick a Worker to Modify");
		localView.refreshLogoTitle();
		swapToView(localView);
		myHistory.push(null);
	}
}