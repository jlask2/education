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

public class DeleteBookTransaction extends TransactionL{
	//GUI Components
	private String transactionErrorMessage = "";

	BookCollection myBookCollection;

	Book myBookToDelete;

	Vector<Book> myCollection;

	ArrayList<String>myDisciplines;

	int myBookIndex;

	//keep track of our search information so that we can update the table
	String myBookTitle;
	String myBookAuthor;
	String myBookDiscipline;
	String includeInactive;

	//Constructor
	public DeleteBookTransaction(Worker worker) throws Exception{
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
			System.out.println("!NO! DeleteBookTransaction.getState(...) " + key);
			System.exit(-1);
			return null;
		}
		if(key.equals("deep_props_vec_book_list")){
			return myBookCollection.getState("deep_props_vec");
		}
		if(key.equals("MyBookToDelete") == true){
			System.out.println("!NO! DeleteBookTransaction.getState(...) " + key);
			System.exit(-1);
			return null;
		}
		if(key.equals("deep_props_book_to_delete")){
			return myBookToDelete.getState("deep_props");
		}
		if(key.equals("TransactionType") == true){
			return "DeleteBook";
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
			showBookToDelete((String)value);
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
	public void searchForBooks(String bTitle, String bAuthor, String bDiscipline, String iInactive){
		myBookCollection = new BookCollection();
		myCollection = myBookCollection.findBooks(bTitle, bAuthor, bDiscipline, iInactive);
		int numWorkers = (Integer)myBookCollection.getState("BookCount");

		if(numWorkers > 0){
			transactionErrorMessage = "";
			createAndShowBookCollectionView();
		}
		else{
			transactionErrorMessage = "No books found matching search.";
		}
	}

	public void showSelectedBook(int whichBook){
		myBookIndex = whichBook;
		myBookToDelete = myCollection.get(whichBook);
		createAndShowDeleteBookView();
	}

	public void showBookToDelete(String myBookId){
		try{
			myBookToDelete = new Book(myBookId);
			transactionErrorMessage = "";
			createAndShowDeleteBookView();
		}
		catch(InvalidPrimaryKeyException ex){
			transactionErrorMessage = "No book found with this barcode.";
		}
	}

	//Our main logic is in this method
	public void processTransaction(Properties propsToUpdate){

		propsToUpdate.setProperty("Status", "Inactive");
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
			localView.setViewTitle("Search for a Book to Delete");
			localView.refreshLogoTitle();
			myViews.put("BookSearchView", localView);

			return localView;
		}
		else{
			return localView;
		}
	}

	protected void createAndShowDeleteBookView(){
		ViewL localView = ViewFactory.createView("DeleteBookView", this);
		localView.setViewTitle("Delete Book");
		localView.refreshLogoTitle();
		myViews.put("DeleteBookView", localView);
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
		localView.setViewTitle("Pick a Book to Delete");
		localView.refreshLogoTitle();
		myViews.put("BookCollectionView", localView);
		swapToView(localView);
		myHistory.push(null);
	}
}