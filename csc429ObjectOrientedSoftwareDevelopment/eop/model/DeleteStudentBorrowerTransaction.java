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

public class DeleteStudentBorrowerTransaction extends TransactionL{
	//GUI Components
	private String transactionErrorMessage = "";

	StudentBorrowerCollection myStudentBorrowerCollection;

	StudentBorrower myStudentBorrowerToDelete;

	Vector<StudentBorrower> myCollection;

	int myStudentBorrowerIndex;

	//keep track of our search information so that we can update the table
	String myStudentBorrowerFN;
	String myStudentBorrowerLN;
	String myStudentBorrowerPhone;
	String myStudentBorrowerEmail;
	String includeInactive;

	//Constructor
	public DeleteStudentBorrowerTransaction(Worker worker) throws Exception{
		super(worker);

		myHistory = new Stack<ViewL>();
	}

	protected void setDependencies(){
		dependencies = new Properties();
		dependencies.setProperty("CancelModifyStudentBorrower", "CancelTransaction");
		dependencies.setProperty("DoWorkerSearch", "TransactionErrorMessage");
		myRegistry.setDependencies(dependencies);
	}

	public Object getState(String key){
		System.out.println("DeleteStudentBorrower.getState(...) " + key);
		if(key.equals("TransactionError") == true){
			return transactionErrorMessage;
		}
		if(key.equals("Worker") == true){
			return myWorker;
		}
		if(key.equals("StudentBorrowerList") == true){
			System.out.println("!NO! DeleteStudentBorrowerTransaction.getState(...) " + key);
			System.exit(-1);
			return null;
		}
		if(key.equals("MyStudentBorrowerToDelete") == true){
			System.out.println("!NO! DeleteStudentBorrowerTransaction.getState(...) " + key);
			System.exit(-1);
			return null;
		}
		if(key.equals("TransactionType") == true){
			return "DeleteStudentBorrowerTransaction";
		}
		if(key.equals("deep_props_vec_student_borrower_list")){
			return myStudentBorrowerCollection.getState("deep_props_vec");
		}
		if(key.equals("deep_props_student_borrower_to_delete")){
			return myStudentBorrowerToDelete.getState("deep_props");
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
			myStudentBorrowerFN = mySearchProperties.getProperty("FirstName");
			myStudentBorrowerLN = mySearchProperties.getProperty("LastName");
			myStudentBorrowerPhone = mySearchProperties.getProperty("ContactPhone");
			myStudentBorrowerEmail = mySearchProperties.getProperty("Email");
			includeInactive = mySearchProperties.getProperty("IncludedInactive");

			searchForStudentBorrowers(myStudentBorrowerFN, myStudentBorrowerLN, myStudentBorrowerPhone, myStudentBorrowerEmail, includeInactive);
		}
		if(key.equals("GetStudentBorrowerByBannerID")){
			showStudentBorrowerToDelete((String)value);
		}
		if(key.equals("StudentBorrowerSelected")){
			showSelectedStudentBorrower((int)value);
		}
		if(key.equals("CancelThisView")){
			showHistoricalView();
		}
		myRegistry.updateSubscribers(key, this);
	}

	//Our search logic is in this method
	public void searchForStudentBorrowers(String fName, String lName, String phone, String email, String iInactive){
		myStudentBorrowerCollection = new StudentBorrowerCollection();
		myCollection = myStudentBorrowerCollection.findStudentBorrowers(fName, lName, phone, email, iInactive);
		int numWorkers = (Integer)myStudentBorrowerCollection.getState("StudentBorrowerCount");

		if(numWorkers > 0){
			transactionErrorMessage = "";
			createAndShowStudentBorrowerCollectionView();
		}
		else{
			transactionErrorMessage = "No student borrowers found matching search.";
		}
	}

	public void showSelectedStudentBorrower(int whichStudentBorrower){
		myStudentBorrowerIndex = whichStudentBorrower;
		myStudentBorrowerToDelete = myCollection.get(whichStudentBorrower);
		createAndShowDeleteStudentBorrowerView();
	}

	public void showStudentBorrowerToDelete(String myStudentBorrowerId){
		try{
			myStudentBorrowerToDelete = new StudentBorrower(myStudentBorrowerId);
			transactionErrorMessage = "";
			createAndShowDeleteStudentBorrowerView();
		}
		catch(InvalidPrimaryKeyException ex){
			transactionErrorMessage = "No student borrower found with this banner ID.";
		}
	}

	//Our main logic is in this method
	public void processTransaction(Properties propsToUpdate){

		propsToUpdate.setProperty("Status", "Inactive");
		StudentBorrower borrower = new StudentBorrower(propsToUpdate);

		//try and update the worker
		borrower.update();

		//Let's get our update message
		transactionErrorMessage = (String)borrower.getState("UpdateStatusMessage");
		//DEBUG System.out.println(transactionErrorMessage);

		//Let's update the worker in our collection as well if history requires it
		ViewL myCurrentView = myHistory.pop();
		ViewL previousView = myHistory.peek();
		if(previousView == null){
			myStudentBorrowerCollection = new StudentBorrowerCollection();
			myCollection = myStudentBorrowerCollection.findStudentBorrowers(myStudentBorrowerFN, myStudentBorrowerLN, myStudentBorrowerPhone, myStudentBorrowerEmail, includeInactive);
		}

		myHistory.push(myCurrentView);
	}

	@Override
	public ViewL createTransactionView(){
		ViewL localView = (ViewL)myViews.get("StudentBorrowerSearchView");

		if(localView == null){
			localView = ViewFactory.createView("StudentBorrowerSearchView", this);
			localView.setViewTitle("Search for a Student Borrower to Delete");
			localView.refreshLogoTitle();
			myViews.put("StudentBorrowerSearchView", localView);

			return localView;
		}
		else{
			return localView;
		}
	}

	protected void createAndShowDeleteStudentBorrowerView(){
		ViewL localView = ViewFactory.createView("DeleteStudentBorrowerView", this);
		localView.setViewTitle("Delete a Student Borrower");
		localView.refreshLogoTitle();
		myViews.put("DeleteStudentBorrowerView", localView);
		swapToView(localView);
		myHistory.push(localView);
	}

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

	protected void createAndShowStudentBorrowerCollectionView(){
		ViewL localView = ViewFactory.createView("StudentBorrowerCollectionView", this);
		myViews.put("StudentBorrowerCollectionView", localView);
		localView.setViewTitle("Pick a Student Borrower to Delete");
		localView.refreshLogoTitle();
		swapToView(localView);
		myHistory.push(null);
	}
}