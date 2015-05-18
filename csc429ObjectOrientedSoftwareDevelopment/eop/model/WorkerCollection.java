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

public class WorkerCollection extends EntityBase implements IView{
	private static final String myTableName = "worker";
	private Vector<Worker> workerList;

	private int numWorkers;

	//constructor for this class
	public WorkerCollection(){
		super(myTableName);
		workerList = new Vector();
		numWorkers = 0;
	}

	public Vector<Worker> findWorkers(String frstName, String lstName, String pNumber, String eAddress, String includeInac, String omitWorker){
		String query = "";
		if(includeInac.equals("true")){
			query = "SELECT * FROM worker WHERE FirstName LIKE '%" + frstName +
					"%' AND LastName LIKE '%" + lstName + "%' AND ContactPhone LIKE '%" + pNumber +
					"%' AND Email LIKE '%" + eAddress + "%' AND BannerID != '" + omitWorker + 
					"' ORDER BY LastName ASC, FirstName ASC";
		}
		else{
			query = "SELECT * FROM worker WHERE FirstName LIKE '%" + frstName +
					"%' AND LastName LIKE '%" + lstName + "%' AND ContactPhone LIKE '%" + pNumber +
					"%' AND Email LIKE '%" + eAddress + "%' AND Status = 'Active' AND BannerID != '" + omitWorker +
					"' ORDER BY LastName ASC, FirstName ASC";
		}
		//DEBUG System.out.println(query);

		Vector allDataRetrieved = getSelectQueryResult(query);
		if(allDataRetrieved != null){
			workerList = new Vector<Worker>();
			for(int count = 0; count < allDataRetrieved.size(); count++){
				Properties nextWorkerData = (Properties)allDataRetrieved.elementAt(count);
				Worker worker = new Worker(nextWorkerData);
				if(worker != null){
					workerList.add(worker);
					numWorkers++;
				}
			}
		}
		return workerList;
	}

	public void updateState(String key, Object value){
		stateChangeRequest(key, value);
	}

	public Object getState(String key){
		if(key.equals("WorkerCount")){
			return numWorkers;
		} else if(key.equals("deep_props_vec")){
			return TransactionL.modelCollectionToProperties(workerList);
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