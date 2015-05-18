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

public class RentalCollection extends EntityBase implements IView{
	private static final String myTableName = "rental";
	private Vector<Rental> rentalList;

	private int numRentals;



	public Vector<Rental> findCheckedOutRentals(String borrowerID){
		String query = "";
		query = "SELECT * FROM "+myTableName+" WHERE (BorrowerID = '"+borrowerID+"') AND (CheckinDate IS NULL)";
		//DEBUG System.out.println(query);

		Vector allDataRetrieved = getSelectQueryResult(query);
		if(allDataRetrieved != null){
			rentalList = new Vector<Rental>();
			for(int count = 0; count < allDataRetrieved.size(); count++){
				Properties nextRentalData = (Properties)allDataRetrieved.elementAt(count);
				//System.out.println("\n\nnextRentalData--> "+nextRentalData+"\n\n\n");
				Rental r = new Rental(nextRentalData);
				if(r != null){
					rentalList.add(r);
					numRentals++;
				}
			}
		}
		return rentalList;
	}

	//Constructor
	// ----------------------------------------------------
	public RentalCollection(){
		super(myTableName);
		rentalList = new Vector();
		numRentals = 0;
	}
	// ----------------------------------------------------
	public Vector<Rental> findRentals(String borrowerID){
		String query = "";
		query = "SELECT * FROM "+myTableName+
				" WHERE (BorrowerID = '"+borrowerID+"')"+
				" AND ( (CheckinDate IS NULL) OR (CheckinDate = '') )"+
				" ORDER BY BookID";
		//DEBUG System.out.println("Query " +query);

		Vector allDataRetrieved = getSelectQueryResult(query);
		if(allDataRetrieved != null){
			rentalList = new Vector<Rental>();
			for(int count = 0; count < allDataRetrieved.size(); count++){
				Properties nextRentalData = (Properties)allDataRetrieved.elementAt(count);
				Rental rental = new Rental(nextRentalData);
				if(rental != null){
					rentalList.add(rental);
					numRentals++;
				}
			}
		}
		return rentalList;
	}
	// ----------------------------------------------------
	public void updateState(String key, Object value){
		stateChangeRequest(key, value);
	}
	// ----------------------------------------------------
	public Object getState(String key){
		if(key.equals("Rentals")){
			return rentalList;
		} else if(key.equals("RentalCount")){
			return numRentals;
		} else if(key.equals("deep_props_vec")){
			return TransactionL.modelCollectionToProperties(rentalList);
		}
		return null;
	}
	// ----------------------------------------------------
	public void stateChangeRequest(String key, Object value){
		myRegistry.updateSubscribers(key, this);
	}
	// ----------------------------------------------------
	protected void initializeSchema(String tableName){
		if(mySchema == null){
			mySchema = getSchemaInfo(tableName);
		}
	}
	// ----------------------------------------------------
}