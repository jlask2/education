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
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JFrame;




//project imports
import exception.InvalidPrimaryKeyException;
import event.Event;
import database.*;
import impresario.IView;
import userinterface.ViewL;
import userinterface.ViewFactory;
import userinterface.MainFrame;

public class BookCollection extends EntityBase implements IView{
	private static final String myTableName = "book";
	private Vector<Book> bookList;

	private Vector<Book> disciplineList;

	private int numBooks;

	public BookCollection(){
		super(myTableName);
		bookList = new Vector();
		numBooks = 0;
	}

	public Vector<Book> findBooks(String title, String author, String discipline, String includeInac){
		String query = "";
		if(includeInac.equals("true")){
			query = "SELECT * FROM book WHERE Title LIKE '%" + title +
					"%' AND Discipline LIKE '%" + discipline + "%' AND Author1 LIKE '%" + author + "%' ORDER BY Discipline ASC, Title ASC";
		}
		else{
			query = "SELECT * FROM book WHERE Title LIKE '%" + title +
					"%' AND Discipline LIKE '%" + discipline + "%' AND Status = 'Active' AND Author1 LIKE '%" + author + "%' ORDER BY Discipline ASC, Title ASC";
		}
		//DEBUG System.out.println(query);

		Vector allDataRetrieved = getSelectQueryResult(query);
		if(allDataRetrieved != null){
			bookList = new Vector<Book>();
			for(int count = 0; count < allDataRetrieved.size(); count++){
				Properties nextBookData = (Properties)allDataRetrieved.elementAt(count);
				Book book = new Book(nextBookData);
				if(book != null){
					bookList.add(book);
					numBooks++;
				}
			}
		}
		return bookList;
	}

	public Vector<Book> findAvailableBooks(){
		String query = "";
		query = "SELECT * FROM book WHERE barcode NOT IN (SELECT BookID FROM rental " + 
				" WHERE CheckinDate IS NULL) AND Status = 'Active'";

		Vector allDataRetrieved = getSelectQueryResult(query);
		if(allDataRetrieved != null){
			bookList = new Vector<Book>();
			for (int count = 0; count < allDataRetrieved.size(); count++){
				Properties nextBookData = (Properties)allDataRetrieved.elementAt(count);
				Book book = new Book(nextBookData);
				if(book != null){
					bookList.add(book);
					numBooks++;
				}
			}
		}
		return bookList;
	}

	public Vector<Book> findCheckedOutBooks(){
		String query = "";
		query = "SELECT * FROM book WHERE barcode IN (SELECT BookID FROM rental " + 
				" WHERE CheckinDate IS NULL)";

		Vector allDataRetrieved = getSelectQueryResult(query);
		if(allDataRetrieved != null){
			bookList = new Vector<Book>();
			for (int count = 0; count < allDataRetrieved.size(); count++){
				Properties nextBookData = (Properties)allDataRetrieved.elementAt(count);
				Book book = new Book(nextBookData);
				if(book != null){
					bookList.add(book);
					numBooks++;
				}
			}
		}
		return bookList;
	}

	public Vector<Book> findDisciplines(){
		String query = "";
		query = "SELECT DISTINCT discipline FROM book ORDER BY discipline ASC";
		//DEBUG System.out.println(query);

		Vector allDataRetrieved = getSelectQueryResult(query);
		if(allDataRetrieved != null){
			disciplineList = new Vector<Book>();
			for(int count = 0; count < allDataRetrieved.size(); count++){
				Properties nextBookData = (Properties)allDataRetrieved.elementAt(count);
				Book book = new Book(nextBookData);
				if(book != null){
					disciplineList.add(book);
				}
			}
		}
		return disciplineList;
	}

	public void updateState(String key, Object value){
		stateChangeRequest(key, value);
	}

	public Object getState(String key){
		if(key.equals("BookCount")){
			return numBooks;
		}
		else if(key.equals("DisciplineList")){
			return disciplineList;
		} else if(key.equals("deep_props_vec")){
			return TransactionL.modelCollectionToProperties(bookList);
		} else if(key.equals("deep_props_vec_discipline")){
			return TransactionL.modelCollectionToProperties(disciplineList);
		}
		return null;
	}

	public void stateChangeRequest(String key, Object value){
		myRegistry.updateSubscribers(key, this);
	}

	protected void initializeSchema(String tableName){
		if(mySchema == null){
			mySchema = getSchemaInfo(tableName);
		}
	}

}