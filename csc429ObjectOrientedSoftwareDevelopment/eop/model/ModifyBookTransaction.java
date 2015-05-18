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
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Stack;
import java.util.Vector;





//project imports
import event.Event;
import exception.InvalidPrimaryKeyException;
import userinterface.ViewL;
import userinterface.ViewFactory;

public class ModifyBookTransaction extends TransactionL{
	//GUI Components
	private String transactionErrorMessage = "";

	BookCollection myBookCollection;

	Book myBookToModify;

	Vector<Book> myCollection;

	ArrayList<String> myDisciplines;

	int myBookIndex;

	//keep track of our search information so that we can update the table
	String myBookTitle;
	String myBookAuthor;
	String myBookDiscipline;
	String includeInactive;

	//Constructor
	public ModifyBookTransaction(Worker worker) throws Exception{
		super(worker);

		myHistory = new Stack<ViewL>();
	}

	protected void setDependencies(){
		dependencies = new Properties();
		dependencies.setProperty("CancelModifyBook", "CancelTransaction");
		dependencies.setProperty("DoBookSearch", "TransactionErrorMessage");
		myRegistry.setDependencies(dependencies);
	}

	public Object getState(String key){
		if(key.equals("TransactionError") == true){
			return transactionErrorMessage;
		}
		if(key.equals("BookList") == true){
			System.out.println("!NO! ModifyBookTransaction.getState(...) " + key);
			System.exit(-1);
			return null;
		}
		if(key.equals("deep_props_vec_book_list")){
			return myBookCollection.getState("deep_props_vec");
		}
		if(key.equals("MyBookToModify") == true){
			System.out.println("!NO! ModifyBookTransaction.getState(...) " + key);
			System.exit(-1);
			return null;
		}
		if(key.equals("deep_props_book_to_modify")){
			return myBookToModify.getState("deep_props");
		}
		if(key.equals("TransactionType") == true){
			return "ModifyBookTransaction";
		}
		if(key.equals("GetDisciplines") == true){
			return myDisciplines;
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
			myBookTitle = mySearchProperties.getProperty("Title");
			myBookAuthor = mySearchProperties.getProperty("Author");
			myBookDiscipline = mySearchProperties.getProperty("Discipline");
			includeInactive = mySearchProperties.getProperty("IncludedInactive");

			searchForBooks(myBookTitle, myBookAuthor, myBookDiscipline, includeInactive);
		}
		if(key.equals("GetBookByBarcode")){
			showBookToModify((String)value);
		}
		if(key.equals("BookSelected")){
			showSelectedBook((int)value);
		}
		if(key.equals("CancelThisView")){
			showHistoricalView();
		}
		if(key.equals("GetMyDisciplines")){
			getDisciplines();
		}
		myRegistry.updateSubscribers(key, this);
	}

	public void getDisciplines(){
		myDisciplines = new ArrayList<String>();
		BookCollection myTempCollection = new BookCollection();
		Vector<Book> books = myTempCollection.findDisciplines();
		for(int i = 0; i < books.size(); i++){
			Book tempBook = books.get(i);
			myDisciplines.add((String)tempBook.getState("Discipline"));
			//DEBUG System.out.println((String)tempBook.getState("Discipline"));
		}
	}

	//Our search logic is in this method
	public void searchForBooks(String title, String author, String discipline, String iInactive){
		myBookCollection = new BookCollection();
		myCollection = myBookCollection.findBooks(title, author, discipline, iInactive);
		int numStudentBorrowers = (Integer)myBookCollection.getState("BookCount");

		if(numStudentBorrowers > 0){
			transactionErrorMessage = "";
			createAndShowBookCollectionView();
		}
		else{
			transactionErrorMessage = "No books found matching search.";
		}
	}

	public void showSelectedBook(int whichBook){
		myBookIndex = whichBook;
		myBookToModify = myCollection.get(whichBook);
		createAndShowModifyBookView();
	}

	public void showBookToModify(String myBookId){
		try{
			myBookToModify = new Book(myBookId);
			transactionErrorMessage = "";
			createAndShowModifyBookView();
		}
		catch(InvalidPrimaryKeyException ex){
			transactionErrorMessage = "No book found with this barcode.";
		}
	}

	//Our main logic is in this method
	public void processTransaction(Properties propsToUpdate){
		Book book = new Book(propsToUpdate);

		//try and update the worker
		book.update();

		//Let's get our update message
		transactionErrorMessage = (String)book.getState("UpdateStatusMessage");
		//DEBUG System.out.println(transactionErrorMessage);

		//Let's update the worker in our collection as well if history requires it
		ViewL myCurrentView = myHistory.pop();
		ViewL previousView = myHistory.peek();
		if(previousView == null){
			myBookCollection = new BookCollection();
			myCollection = myBookCollection.findBooks(myBookTitle, myBookAuthor, myBookDiscipline, includeInactive);
		}

		myHistory.push(myCurrentView);
	}

	@Override
	public ViewL createTransactionView(){
		ViewL localView = (ViewL)myViews.get("BookSearchView");

		if(localView == null){
			localView = ViewFactory.createView("BookSearchView", this);
			localView.setViewTitle("Search for a Book to Modify");
			localView.refreshLogoTitle();
			myViews.put("BookSearchView", localView);

			return localView;
		}
		else{
			return localView;
		}
	}

	protected void createAndShowModifyBookView(){
		ViewL localView = ViewFactory.createView("ModifyBookView", this);
		localView.setViewTitle("Modify this Book's Information");
		localView.refreshLogoTitle();
		myViews.put("ModifyBookView", localView);
		swapToView(localView);
		myHistory.push(localView);
	}

	protected void showHistoricalView(){
		myHistory.pop();
		ViewL historicalView = myHistory.peek();
		if(historicalView == null){
			myHistory.pop();
			createAndShowBookCollectionView();
		}
		else{
			swapToView(historicalView);
		}
	}

	protected void createAndShowBookCollectionView(){
		ViewL localView = ViewFactory.createView("BookCollectionView", this);
		localView.setViewTitle("Pick a Book to Modify");
		localView.refreshLogoTitle();
		myViews.put("BookCollectionView", localView);
		swapToView(localView);
		myHistory.push(null);
	}
}