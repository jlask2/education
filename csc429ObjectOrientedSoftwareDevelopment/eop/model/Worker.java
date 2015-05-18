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

public class Worker extends EntityBase implements IView{
	private static final String myTableName = "worker";

	protected Properties dependencies;

	private String updateStatusMessage = "";

	private static Properties SCHEMA_PROPS = null;

	//Constructor to get 1 worker from the database
	public Worker(String BannerID) throws InvalidPrimaryKeyException{
		super(myTableName);

		setDependencies();

		String query = "SELECT * FROM " + myTableName + " WHERE (BannerID = " + BannerID + ")";

		Vector allDataRetrieved = getSelectQueryResult(query);

		//you must get one worker at least
		if(allDataRetrieved != null){
			int size = allDataRetrieved.size();

			//There should be exactly one worker
			if(size != 1){
				throw new InvalidPrimaryKeyException("Multiple workers matching banner id :"
						+ BannerID + " found.");
			}
			else{
				//copy all data into persistent state
				Properties retrievedWorkerData = (Properties)allDataRetrieved.elementAt(0);
				persistentState = new Properties();

				Enumeration allKeys = retrievedWorkerData.propertyNames();
				while (allKeys.hasMoreElements() == true){
					String nextKey = (String)allKeys.nextElement();
					String nextValue = retrievedWorkerData.getProperty(nextKey);

					if(nextValue != null){
						persistentState.setProperty(nextKey, nextValue);
					}
				}
			}
		}
		//if no worker found for this banner id, throw an exception
		else{
			throw new InvalidPrimaryKeyException("No worker matching banner id: "
					+ BannerID + " found.");
		}
	}

	public Worker(Properties props){
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

	public void verifyPassword(String wkrPassword, String entPassword) throws PasswordMismatchException{
		String enteredPassword = entPassword;
		String workerPassword = (String) wkrPassword;

		if(workerPassword != null){
			boolean passwordCheck = workerPassword.equals(enteredPassword);
			if(passwordCheck == false){
				throw new PasswordMismatchException("Password Mismatch!");
			}
		}
		else{
			throw new PasswordMismatchException("Password missing for worker.");
		}
	}

	public void insert(){
		insertStateInDatabase();
	}

	private void insertStateInDatabase(){
		try{
			insertPersistentState(mySchema, persistentState);
			updateStatusMessage = "Worker inserted successfully.";
		}
		catch(Exception ex){
			updateStatusMessage = "Error inserting new worker in the database.";
		}
	}

	public void update(){
		updateStateInDatabase();
	}

	private void updateStateInDatabase(){
		try{
			Properties whereClause = new Properties();
			whereClause.setProperty("BannerID", persistentState.getProperty("BannerID"));
			updatePersistentState(mySchema, persistentState, whereClause);
			updateStatusMessage = "Worker updated successfully.";
		}
		catch(SQLException ex){
			updateStatusMessage = "Error updating worker.";
		}
	}

	private void setDependencies(){
		dependencies = new Properties();
		myRegistry.setDependencies(dependencies);
	}

	public Object getState(String key){
		if(key.equals("UpdateStatusMessage") == true){
			return updateStatusMessage;
		} else if (key.equals("deep_props")){
			return TransactionL.deepCopy(persistentState);
		} else if (key.equals("ref_props")){
			return persistentState;
		}
		return persistentState.getProperty(key);
	}


	public void updateState(String key, Object value) {
		stateChangeRequest(key,value);
	}

	public void stateChangeRequest(String key, Object value) {
		myRegistry.updateSubscribers(key, this);
	}

	protected void initializeSchema(String tableName){
		if(SCHEMA_PROPS == null){
			SCHEMA_PROPS = getSchemaInfo(tableName);
		}
		mySchema = SCHEMA_PROPS;
	}


}