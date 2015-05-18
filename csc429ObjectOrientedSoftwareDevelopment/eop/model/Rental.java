// tabs=4
// 
//************************************************************
//	COPYRIGHT 2014 Sandeep Mitra
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

public class Rental extends EntityBase implements IView {
	private static final String myTableName = "rental";
	protected Properties dependencies;
	private String updateStatusMessage = "";

	private Properties udpateStatusProps;

	private static Properties SCHEMA_PROPS = null;

	Book bookTitleToShow;

	//Constructor to get 1 rental from the database
	private void rentalBarCode(String bookBarcode) throws InvalidPrimaryKeyException{


		String query = query = "SELECT * From " + myTableName + " WHERE (BookID = " + bookBarcode + " AND CheckinDate IS NULL);";

		Vector allDataRetrieved = getSelectQueryResult(query);

		//you must get one worker at least
		if(allDataRetrieved != null){
			int size = allDataRetrieved.size();

			//There should be exactly one worker
			if(size != 1){
				throw new InvalidPrimaryKeyException("Multiple rentals matching book barcode :"
						+ bookBarcode + " found.");
			}
			else{
				//copy all data into persistent state
				Properties retrievedRentalData = (Properties)allDataRetrieved.elementAt(0);
				persistentState = new Properties();

				Enumeration allKeys = retrievedRentalData.propertyNames();
				while (allKeys.hasMoreElements() == true){
					String nextKey = (String)allKeys.nextElement();
					String nextValue = retrievedRentalData.getProperty(nextKey);

					if(nextValue != null){
						persistentState.setProperty(nextKey, nextValue);
					}
				}
			}
		}
		//if no worker found for this banner id, throw an exception
		else{
			throw new InvalidPrimaryKeyException("No rental matching book barcode: "
					+ bookBarcode + " found.");
		}
	}

	public Rental(String value, String fnType) throws InvalidPrimaryKeyException{
		super(myTableName);
		setDependencies();
		if (fnType.equals("bookBarcode")){
			rentalBarCode(value);
		} else if (fnType.equals("bookID")){
			rentalBookID(value);
		} else if (fnType.equals("rentalID")){
			rentalID(value);
		}

	}

	public void rentalID(String rentalID) throws InvalidPrimaryKeyException {

		String query = "SELECT * FROM " + myTableName + " WHERE (ID = "
				+ rentalID + ")";

		Vector allDataRetrieved = getSelectQueryResult(query);

		// you must get one rental at least
		if (allDataRetrieved != null) {
			int size = allDataRetrieved.size();

			// there should be exactly one rental
			if (size != 1) {
				throw new InvalidPrimaryKeyException("Multiple rentals found.");
			} else {
				// copy data into persistent state
				Properties retrievedRentalData = (Properties) allDataRetrieved
						.elementAt(0);
				persistentState = new Properties();

				Enumeration allKeys = retrievedRentalData.propertyNames();
				while (allKeys.hasMoreElements() == true) {
					String nextKey = (String) allKeys.nextElement();
					String nextValue = retrievedRentalData.getProperty(nextKey);

					if (nextValue != null) {
						persistentState.setProperty(nextKey, nextValue);
					}
				}
			}
		}
		// if no rental found, throw an exception
		else {
			throw new InvalidPrimaryKeyException("No rental found.");
		}
	}



	// Constructor to get 1 rental from database
	// ----------------------------------------------------
	public Rental(int rentalID) throws InvalidPrimaryKeyException {
		super(myTableName);
		setDependencies();

		String query = "SELECT * FROM " + myTableName + " WHERE (ID = "
				+ rentalID + ")";

		Vector allDataRetrieved = getSelectQueryResult(query);

		// you must get one rental at least
		if (allDataRetrieved != null) {
			int size = allDataRetrieved.size();

			// there should be exactly one rental
			if (size != 1) {
				throw new InvalidPrimaryKeyException("Multiple rentals found.");
			} else {
				// copy data into persistent state
				Properties retrievedRentalData = (Properties) allDataRetrieved
						.elementAt(0);
				persistentState = new Properties();

				Enumeration allKeys = retrievedRentalData.propertyNames();
				while (allKeys.hasMoreElements() == true) {
					String nextKey = (String) allKeys.nextElement();
					String nextValue = retrievedRentalData.getProperty(nextKey);

					if (nextValue != null) {
						persistentState.setProperty(nextKey, nextValue);
					}
				}
			}
		}
		// if no rental found, throw an exception
		else {
			throw new InvalidPrimaryKeyException("No rental found.");
		}
	}
	// ----------------------------------------------------
	public Rental(String bookID, String workerID, String dateIn) throws Exception{
		super(myTableName);
		setDependencies();

		String query = "SELECT * FROM " + myTableName + " WHERE (BookID = "
				+ bookID + ") AND CheckinDate IS NULL";

		Vector allDataRetrieved = getSelectQueryResult(query);

		// you must get one rental at least
		if (allDataRetrieved != null) {
			int size = allDataRetrieved.size();

			// there should be exactly one rental
			if (size != 1) {
				throw new Exception("Multiple rentals found.");
			} else {
				// copy data into persistent state
				Properties retrievedRentalData = (Properties) allDataRetrieved
						.elementAt(0);
				persistentState = new Properties();

				Enumeration allKeys = retrievedRentalData.propertyNames();
				while (allKeys.hasMoreElements() == true) {
					String nextKey = (String) allKeys.nextElement();
					String nextValue = retrievedRentalData.getProperty(nextKey);

					if (nextValue != null) {
						persistentState.setProperty(nextKey, nextValue);
					}
				}
				persistentState.setProperty("CheckinDate",dateIn);
				persistentState.setProperty("CheckinWorkerId",workerID);
				updateProcessLostBook();
			}
		}
		// if no rental found, throw an exception
		else {
			throw new Exception("No rental found.");
		}
	}
	//Constructor to check for existing bookID and check in status in rental ledger
	private void rentalBookID(String bookID)throws InvalidPrimaryKeyException{

		String query = "";

		query = "SELECT * From " + myTableName + " WHERE (BookID = " + bookID + " AND CheckinDate IS NULL);";

		Vector allDataRetrieved = null;
		allDataRetrieved = getSelectQueryResult(query);
		/*DEBUG*///System.out.println("query: "+ query);

		int size = allDataRetrieved.size();
		/*DEBUG*///System.out.println("size: "+ size);

		//if multiple rentals found, throw an exception
		if(size > 1){
			updateStatusMessage = "Multiple rentals found with bookID";
			throw new InvalidPrimaryKeyException("Multiple rentals found with bookID: " 
					+ bookID + " and check in date is null");
		}else if(size == 1){
			//copy data into persistent state
			Properties retrievedRentalData = (Properties)allDataRetrieved.elementAt(0);
			persistentState = new Properties();

			Enumeration allKeys = retrievedRentalData.propertyNames();
			while(allKeys.hasMoreElements() == true){
				String nextKey = (String)allKeys.nextElement();
				String nextValue = retrievedRentalData.getProperty(nextKey);

				if(nextValue != null){
					persistentState.setProperty(nextKey, nextValue);
				}
			}
			updateStatusMessage = "There is a bookID where check in date is null";
		}else if(size == 0){
			updateStatusMessage = "No rental found with bookID where the check in date is null";
		}
	}
	// ----------------------------------------------------
	public Rental(Properties props){
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

	/*public void verify(String wkrPassword, String entPassword) throws PasswordMismatchException{
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
	}*/

	public void insert(){
		insertStateInDatabase();
	}

	private void insertStateInDatabase(){
		try{
			insertPersistentState(mySchema, persistentState);
			updateStatusMessage = "Rental inserted successfully.";
		}
		catch(Exception ex){

			updateStatusMessage = "Error inserting new rental in the database.";
		}
	}



	private void updateStateInDatabaseCheckInBook(){
		try{
			Properties whereClause = new Properties();
			whereClause.setProperty("ID", persistentState.getProperty("ID"));
			updatePersistentState(mySchema, persistentState, whereClause);
			updateStatusMessage = "";
		}
		catch(SQLException ex){
			updateStatusMessage = "Error updating rental.";
		}
	}

	// ----------------------------------------------------
	private void updateStateInDatabaseProcessLostBook(){
		try{
			Properties whereClause = new Properties();
			whereClause.setProperty("ID", persistentState.getProperty("ID"));
			updatePersistentState(mySchema, persistentState, whereClause);
			updateStatusMessage = "Rental updated successfully.";
		}
		catch(SQLException ex){
			updateStatusMessage = "Error updating rental.";
		}
	}

	public void update(){
		try{
			Properties whereClause = new Properties();
			whereClause.setProperty("ID", persistentState.getProperty("ID"));
			updatePersistentState(mySchema, persistentState, whereClause);
			updateStatusMessage = "Rental updated successfully.";
			udpateStatusProps = new Properties();
			udpateStatusProps.setProperty("Status", "0");
			udpateStatusProps.setProperty("Message", "rental update success");
		}
		catch(SQLException ex){
			updateStatusMessage = "Error updating rental.";
			udpateStatusProps = new Properties();
			udpateStatusProps.setProperty("Status", "-1");
			udpateStatusProps.setProperty("Message", "rental update failed");
		}
	}

	public void updateCheckInBook(){
		updateStateInDatabaseCheckInBook();
	}

	public void updateProcessLostBook(){
		updateStateInDatabaseProcessLostBook();
	}
	// ----------------------------------------------------
	private void setDependencies(){
		dependencies = new Properties();
		myRegistry.setDependencies(dependencies);
	}

	// ----------------------------------------------------
	public Object getState(String key){
		if(key.equals("UpdateStatusMessage") == true){
			return updateStatusMessage;
		}
		else if (key.equals("RentalProps")) {
			return persistentState;
		}
		else if (key.equals("RentalBorrowerID")){
			return persistentState.getProperty("BorrowerID");
		}
		else if (key.equals("RentalBorrowerBarcode")) {
			return persistentState.getProperty("BookID");
		}
		else if (key.equals("RentalBorrowerCheckoutDate"))
		{
			return persistentState.getProperty("CheckoutDate");
		}
		else if (key.equals("RentalBorrowerDueDate"))
		{
			return persistentState.getProperty("DueDate");
		}
		else if (key.equals("MyRentalCheckinDate"))
		{
			return persistentState.getProperty("CheckinDate");
		} else if (key.equals("deep_props")){
			return TransactionL.deepCopy(persistentState);
		} else if (key.equals("ref_props")){
			return persistentState;
		} else if (key.equals("update_status")){
			return udpateStatusProps;
		}
		return persistentState.getProperty(key);
	}

	public void setAttributes(Properties p)
	{
		Enumeration allKeys = p.propertyNames();
		while (allKeys.hasMoreElements() == true)
		{
			String nextKey = (String)allKeys.nextElement();
			String nextValue = p.getProperty(nextKey);
			if (nextValue != null)
			{
				persistentState.setProperty(nextKey, nextValue);
			}
		}
	}



	// ----------------------------------------------------
	public void updateState(String key, Object value){
		stateChangeRequest(key, value);
	}
	// ----------------------------------------------------
	public void stateChangeRequest(String key, Object value){
		myRegistry.updateSubscribers(key, this);
	}
	// ----------------------------------------------------
	protected void initializeSchema(String tableName){
		if(SCHEMA_PROPS == null){
			SCHEMA_PROPS = getSchemaInfo(tableName);
		}
		mySchema = SCHEMA_PROPS;
	}

}