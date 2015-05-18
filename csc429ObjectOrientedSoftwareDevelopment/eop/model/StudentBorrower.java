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
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JFrame;






//project imports
import exception.InvalidPrimaryKeyException;
import exception.PasswordMismatchException;
import database.*;
import impresario.IView;

public class StudentBorrower extends EntityBase implements IView{
	private static final String myTableName = "studentborrower";

	protected Properties dependencies;

	private String updateStatusMessage = "";

	private Properties udpateStatusProps;

	private static Properties SCHEMA_PROPS = null;

	public StudentBorrower(String BannerID) throws InvalidPrimaryKeyException{
		super(myTableName);

		setDependencies();

		String query = "SELECT * FROM " + myTableName + " WHERE (BannerID = " + BannerID + ")";

		Vector allDataRetrieved = getSelectQueryResult(query);

		if(allDataRetrieved != null){
			int size = allDataRetrieved.size();

			if(size != 1){
				throw new InvalidPrimaryKeyException("Multiple student borrowers found.");
			}
			else{
				//copy data into persistent state
				Properties retrievedStudentBorrowerData = (Properties)allDataRetrieved.elementAt(0);
				persistentState = new Properties();

				Enumeration allKeys = retrievedStudentBorrowerData.propertyNames();
				while(allKeys.hasMoreElements() == true){
					String nextKey = (String)allKeys.nextElement();
					String nextValue = retrievedStudentBorrowerData.getProperty(nextKey);

					if(nextValue != null){
						persistentState.setProperty(nextKey, nextValue);
					}
				}
			}
		}
		//if no book found, throw an exception
		else{
			throw new InvalidPrimaryKeyException("No studentborrower found.");
		}
	}

	public StudentBorrower(Properties props){
		super(myTableName);
		setDependencies();
		persistentState = new Properties();
		Enumeration allKeys = SCHEMA_PROPS.propertyNames();
		while(allKeys.hasMoreElements()){
			String nextKey = (String)allKeys.nextElement();
			String nextValue = props.getProperty(nextKey);
			if(nextValue != null){
				persistentState.setProperty(nextKey, nextValue);
			}
		}
		persistentState.remove("TableName");
	}

	public void insert(){
		insertStateInDatabase();
	}

	private void insertStateInDatabase(){
		try{
			insertPersistentState(mySchema, persistentState);
			updateStatusMessage = "Student Borrower inserted successfully.";
		}
		catch(Exception ex){
			updateStatusMessage = "Error inserting student borrower into database";
		}
	}
	// ----------------------------------------------------
	public void updateMonetaryPenalty(double penalty){
		String monetaryPenalty = String.format("%.2f", penalty);
		persistentState.setProperty("MonetaryPenalty", monetaryPenalty);
	}
	// ----------------------------------------------------

	public void update(){
		updateStateInDatabase();
	}

	private void updateStateInDatabase(){
		try{
			Properties whereClause = new Properties();
			whereClause.setProperty("BannerID", persistentState.getProperty("BannerID"));
			updatePersistentState(mySchema, persistentState, whereClause);
			updateStatusMessage = "Student Borrower updated successfully.";
			udpateStatusProps = new Properties();
			udpateStatusProps.setProperty("Status", "0");
			udpateStatusProps.setProperty("Message", "student borrower update success");
		}
		catch(SQLException ex){
			updateStatusMessage = "Error updating student borrower.";
			udpateStatusProps = new Properties();
			udpateStatusProps.setProperty("Status", "-1");
			udpateStatusProps.setProperty("Message", "student borrower update failed");
		}
	}

	private void setDependencies(){
		dependencies = new Properties();
		myRegistry.setDependencies(dependencies);
	}

	public Object getState(String key){
		if(key.equals("UpdateStatusMessage") == true){
			return updateStatusMessage;
		}
		else if(key.equals("BannerID")) {
			return persistentState.getProperty("BannerID");
		} else if (key.equals("deep_props")){
			return TransactionL.deepCopy(persistentState);
		} else if (key.equals("ref_props")){
			return persistentState;
		} else if (key.equals("update_status")){
			return udpateStatusProps;
		}
		return persistentState.getProperty(key);
	}

	public void updateState(String key, Object value){
		stateChangeRequest(key, value);
	}

	public void stateChangeRequest(String key, Object value){
		persistentState.setProperty(key, (String) value);
		myRegistry.updateSubscribers(key, this);
	}

	protected void initializeSchema(String tableName){
		if(SCHEMA_PROPS == null){
			SCHEMA_PROPS = getSchemaInfo(tableName);
		}
		mySchema = SCHEMA_PROPS;
	}
}