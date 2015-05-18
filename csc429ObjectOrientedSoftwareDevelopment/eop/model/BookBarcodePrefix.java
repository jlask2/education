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

public class BookBarcodePrefix extends EntityBase implements IView{
	private static String myTableName = "bookbarcodeprefix";

	protected Properties dependencies;

	private String updateStatusMessage = "";

	//Constructor to get 1 book barcode prefix from the database
	public BookBarcodePrefix(String PrefixValue) throws InvalidPrimaryKeyException{
		super(myTableName);

		setDependencies();

		String query = "SELECT * FROM " + myTableName + " WHERE (PrefixValue = " + PrefixValue + ")";
		//DEBUG System.out.println(query);

		Vector allDataRetrieved = getSelectQueryResult(query);

		//you must get one barcode prefix at least
		if(allDataRetrieved != null){
			int size = allDataRetrieved.size();

			//There should be exactly one barcode prefix
			if(size != 1){
				throw new InvalidPrimaryKeyException("Multiple barcodes matching prefix :"
						+ PrefixValue + " found.");
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
			throw new InvalidPrimaryKeyException("No discipline matching prefix: "
					+ PrefixValue + " found.");
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

	public void updateState(String key, Object value){
		stateChangeRequest(key, value);
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