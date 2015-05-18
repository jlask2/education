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

public class StudentBorrowerCollection extends EntityBase implements IView{
	private static final String myTableName = "studentborrower";
	private Vector<StudentBorrower> studentBorrowerList;

	private int numStudentBorrowers;

	public StudentBorrowerCollection(){
		super(myTableName);
		studentBorrowerList = new Vector();
		numStudentBorrowers = 0;
	}

	public Vector<StudentBorrower> findStudentBorrowers(String frstName, String lstName, String pNumber, String eAddress, String includeInac){
		String query = "";
		if(includeInac.equals("true")){
			query = "SELECT * FROM studentborrower WHERE FirstName LIKE '%" + frstName +
					"%' AND LastName LIKE '%" + lstName + "%' AND ContactPhone LIKE '%" + pNumber +
					"%' AND Email LIKE '%" + eAddress + "%' ORDER BY LastName ASC, FirstName ASC";
		}
		else{
			query = "SELECT * FROM studentborrower WHERE FirstName LIKE '%" + frstName +
					"%' AND LastName LIKE '%" + lstName + "%' AND ContactPhone LIKE '%" + pNumber +
					"%' AND Email LIKE '%" + eAddress + "%' AND Status = 'Active' ORDER BY LastName ASC, FirstName ASC";
		}
		//DEBUG System.out.println(query);

		Vector allDataRetrieved = getSelectQueryResult(query);
		if(allDataRetrieved != null){
			studentBorrowerList = new Vector<StudentBorrower>();
			for(int count = 0; count < allDataRetrieved.size(); count++){
				Properties nextStudentBorrowerData = (Properties)allDataRetrieved.elementAt(count);
				StudentBorrower borrower = new StudentBorrower(nextStudentBorrowerData);
				if(borrower != null){
					studentBorrowerList.add(borrower);
					numStudentBorrowers++;
				}
			}
		}
		return studentBorrowerList;
	}

	public Vector<StudentBorrower> findSBWithCheckedOutBooks(String frstName, String lstName, String pNumber, String eAddress){
		String query = "";
		query = "SELECT * FROM studentborrower WHERE BannerID IN (SELECT BorrowerID FROM " +
				"rental WHERE CheckinDate IS NULL) AND FirstName LIKE '%" + frstName +
				"%' AND LastName LIKE '%" + lstName + "%' AND ContactPhone LIKE '%" + pNumber +
				"%' AND Email LIKE '%" + eAddress + "%'";

		Vector allDataRetrieved = getSelectQueryResult(query);
		if(allDataRetrieved != null){
			studentBorrowerList = new Vector<StudentBorrower>();
			for(int count = 0; count < allDataRetrieved.size(); count++){
				Properties nextStudentBorrowerData = (Properties)allDataRetrieved.elementAt(count);
				StudentBorrower borrower = new StudentBorrower(nextStudentBorrowerData);
				if(borrower != null){
					studentBorrowerList.add(borrower);
					numStudentBorrowers++;
				}
			}
		}
		return studentBorrowerList;
	}

	public Vector<StudentBorrower> findSBWithCheckedOutBooks(){
		String query = "";
		query = "SELECT * FROM studentborrower WHERE BannerID IN (SELECT BorrowerID FROM " +
				"rental WHERE CheckinDate IS NULL);";

		Vector allDataRetrieved = getSelectQueryResult(query);
		if(allDataRetrieved != null){
			studentBorrowerList = new Vector<StudentBorrower>();
			for(int count = 0; count < allDataRetrieved.size(); count++){
				Properties nextStudentBorrowerData = (Properties)allDataRetrieved.elementAt(count);
				StudentBorrower borrower = new StudentBorrower(nextStudentBorrowerData);
				if(borrower != null){
					studentBorrowerList.add(borrower);
					numStudentBorrowers++;
				}
			}
		}
		return studentBorrowerList;
	}

	public Vector<StudentBorrower> findDelinquentBorrowers(){
		String query = "";
		query = "SELECT * FROM studentborrower WHERE BannerID IN (SELECT BorrowerId FROM rental " +
				"WHERE CheckinDate IS NULL AND DueDate < curdate());";

		Vector allDataRetrieved = getSelectQueryResult(query);
		if(allDataRetrieved != null){
			studentBorrowerList = new Vector<StudentBorrower>();
			for(int count = 0; count < allDataRetrieved.size(); count++){
				Properties nextStudentBorrowerData = (Properties)allDataRetrieved.elementAt(count);
				StudentBorrower borrower = new StudentBorrower(nextStudentBorrowerData);
				if(borrower != null){
					studentBorrowerList.add(borrower);
					numStudentBorrowers++;
				}
			}
		}
		return studentBorrowerList;
	}
	
	public Vector<StudentBorrower> findStudentBorrowersWithCheckedOutBooksByBannerId(String bannerId){
		String query = "";
		query = "SELECT * FROM studentborrower WHERE BannerID IN (SELECT BorrowerId FROM rental " +
				"WHERE CheckinDate IS NULL) AND BannerID='" + bannerId + "';";

		Vector allDataRetrieved = getSelectQueryResult(query);
		if(allDataRetrieved != null){
			studentBorrowerList = new Vector<StudentBorrower>();
			for(int count = 0; count < allDataRetrieved.size(); count++){
				Properties nextStudentBorrowerData = (Properties)allDataRetrieved.elementAt(count);
				StudentBorrower borrower = new StudentBorrower(nextStudentBorrowerData);
				if(borrower != null){
					studentBorrowerList.add(borrower);
					numStudentBorrowers++;
				}
			}
		}
		return studentBorrowerList;
	}
	
	public Vector<StudentBorrower> findStudentBorrowerBannerId(String bannerId){
		String query = "";
		query = "SELECT * FROM studentborrower WHERE BannerID='" + bannerId + "';";

		Vector allDataRetrieved = getSelectQueryResult(query);
		if(allDataRetrieved != null){
			studentBorrowerList = new Vector<StudentBorrower>();
			for(int count = 0; count < allDataRetrieved.size(); count++){
				Properties nextStudentBorrowerData = (Properties)allDataRetrieved.elementAt(count);
				StudentBorrower borrower = new StudentBorrower(nextStudentBorrowerData);
				if(borrower != null){
					studentBorrowerList.add(borrower);
					numStudentBorrowers++;
				}
			}
		}
		return studentBorrowerList;
	}

	public void updateState(String key, Object value){
		stateChangeRequest(key, value);
	}

	public Object getState(String key){
		if(key.equals("StudentBorrowerCount")){
			return numStudentBorrowers;
		} else if(key.equals("deep_props_vec")){
			return TransactionL.modelCollectionToProperties(studentBorrowerList);
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