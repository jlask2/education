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

public class MaxDueDate extends EntityBase implements IView{
	private static String myTableName = "maxDueDate";

	protected Properties dependencies;

	private String updateStatusMessage = "";
	
	private Properties udpateStatusProps;

	private static Properties SCHEMA_PROPS = null;
	
	public MaxDueDate(Properties props){
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

	//Constructor to get the CurrentMaxDueDate from the database
	public MaxDueDate() throws InvalidPrimaryKeyException{
		super(myTableName);

		setDependencies();

//		String query = "SELECT * FROM " + myTableName + " WHERE (CurrentMaxDueDate = '2014-12-17'/*'% %' + currentMaxDueDate +*/);";
		String query = "SELECT * FROM " + myTableName + ";";
		//DEBUG System.out.println(query);

		Vector allDataRetrieved = getSelectQueryResult(query);

		//you must get one Current Max Due Date at least
		if(allDataRetrieved != null){
			int size = allDataRetrieved.size();

			//There should be exactly one Current Max Due Date
			if(size != 1){
				throw new InvalidPrimaryKeyException("Multiple current Due Dates found.");
			}
			else{
				Properties retrievedBarcodeData = (Properties)allDataRetrieved.elementAt(0);
				persistentState = new Properties();

				Enumeration allKeys = retrievedBarcodeData.propertyNames();
				while(allKeys.hasMoreElements() == true){
					String nextKey = (String)allKeys.nextElement();
					String nextValue = retrievedBarcodeData.getProperty(nextKey);

					if(nextValue != null){
						persistentState.setProperty(nextKey, nextValue);
					}
				}
			}
		}
		else{
			throw new InvalidPrimaryKeyException("No current due date ");
		}
	}

	private void setDependencies(){
		dependencies = new Properties();
		myRegistry.setDependencies(dependencies);
	}
	
	public void update(String dbDueDate){
		updateStateInDatabase(dbDueDate);
	}

	private void updateStateInDatabase(String dbDueDate){
		try{
			Properties whereClause = new Properties();
			whereClause.setProperty("CurrentMaxDueDate", dbDueDate);
			updatePersistentState(mySchema, persistentState, whereClause);
			updateStatusMessage = "MaxDudeDate updated successfully.";
			udpateStatusProps = new Properties();
			udpateStatusProps.setProperty("Status", "0");
			udpateStatusProps.setProperty("Message", "MaxDueDate update success");
		}
		catch(SQLException ex){
			updateStatusMessage = "Error updating MaxDueDate.";
			udpateStatusProps = new Properties();
			udpateStatusProps.setProperty("Status", "-1");
			udpateStatusProps.setProperty("Message", "MaxDueDate update failed");
		}
	}

	public Object getState(String key){
		if(key.equals("UpdateStatusMessage") == true){
			return updateStatusMessage;
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
		myRegistry.updateSubscribers(key, this);
	}

	protected void initializeSchema(String tableName){
		if(SCHEMA_PROPS == null){
			SCHEMA_PROPS = getSchemaInfo(tableName);
		}
		mySchema = SCHEMA_PROPS;
	}
}