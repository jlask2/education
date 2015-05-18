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
// specify the package
package model;

// project imports
import impresario.IModel;
import impresario.ISlideShow;
import impresario.IView;
import impresario.ModelRegistry;
import userinterface.ImageHolder;
import userinterface.MainFrame;
import userinterface.ViewL;
import userinterface.ViewFactory;
import userinterface.WindowPosition;




// system imports
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

import event.Event;
import exception.InactiveWorkerException;
import exception.InvalidPrimaryKeyException;
import exception.PasswordMismatchException;

/** Overall class Librarian represents the System controller**/
/** MODEL MAPPING: Librarian - Main Interface Agent */
//==============================================================
public class Librarian implements IView, IModel, ISlideShow
{

	// Constants

	// Impresario dependencies
	private Properties dependencies;
	private ModelRegistry myRegistry;

	private Worker myLoggedInWorker;

	// Keep track of views this Controller manages
	protected Hashtable myViews;
	// Access to singleton main display frame
	protected JFrame myFrame;

	// Factory used to reduce coupling between the Controller and the views it manages
	protected ViewFactory myFactory;

	// FOR USING IMPRESARIO: Declare the State variables held in this class
	// Much of the state can be held in 'persistableState', a Properties object
	// holding data got from the database. Since this is not a Persistable object,
	// you hold the state in other instance variables, like the one below.
	//protected Worker loggedinWorker;

	// To manage updates of the managed views -- messages displayed on the views are held in these attributes
	protected String loginErrorMessage = "";
	protected String transactionErrorMessage = "";
	public static String IMAGE_LOCATION = "images\\";

	public static String EOP_DEFAULT_PATH = "C:" + File.separator + "EOPLibrary" + File.separator +"EOP_Receipts_Reports";
	public static String EOP_DEFAULT_PATH_STRING = "C:\\EOPLibrary\\EOP_Receipts_Reports";
	public static String EOP_HOME_DIRECTORY = directorySetUp();
	public static String RECEIPTS_DIRECTORY = EOP_HOME_DIRECTORY + File.separator +"Receipts";
	public static String REPORTS_DIRECTORY = EOP_HOME_DIRECTORY + File.separator +"Reports";
	//---------------------------------------------------------------------
	public Librarian(JFrame frm)
	{
		new ImageHolder(IMAGE_LOCATION);//should be here
		myFrame = frm;
		myViews = new Hashtable();

		myFactory = new ViewFactory();


		// STEP 3.1: Create the Registry object - if you inherit from
		// EntityBase, this is done for you. Otherwise, you do it yourself
		myRegistry = new ModelRegistry("Librarian");
		if(myRegistry == null)
		{
			new Event(Event.getLeafLevelClassName(this), "Librarian",
					"Could not instantiate Registry", Event.ERROR);
		}

		// STEP 3.2: Be sure to set the dependencies correctly
		setDependencies();

		// Set up the initial view
		// MODEL MAPPING: Transition from initial state to Main Menu shown in Menu state diagram
		// NOTE: The display of the first view has to be done differently
		createAndShowLoginView();
	}

	public static String directorySetUp(){
      String EOPHomeDirectoryTextName = "EOP_Receipts_Reports_File_Path.txt";
		String EOPDirectoryName = EOP_DEFAULT_PATH;
		String EOPDirectoryNameNew = "";
      BufferedReader pathFileIn = null;
      BufferedWriter pathFileOut = null;
		try
		{
			pathFileIn = new BufferedReader(new FileReader(new File(EOPHomeDirectoryTextName)));
         EOPDirectoryNameNew = pathFileIn.readLine();
         pathFileIn.close();
         return EOPDirectoryNameNew;
      }
      catch(IOException e)
		{
        try{
         pathFileOut = new BufferedWriter(new FileWriter(new File(EOPHomeDirectoryTextName)));
         String change = (String)inputPopUp("Set Up The Directory", 
   				"              Would you like to change the directory that you save receipts and reports to?\n"
   						+"(If you choose 'cancel' the default location will be on the desktop, in folder EOP_Receipts_Reports)");
   		/*DEBUG*///System.out.println("change: " + change);
   		if(change == null){ 
            pathFileOut.write(EOPDirectoryName);
            pathFileOut.close();
   			return EOPDirectoryName; 
   		}else if(!(change.equals(EOPDirectoryName))){
   			EOPDirectoryNameNew = change;
   			/*DEBUG*///System.out.println("EOPDirectoryNameNew: " + EOPDirectoryNameNew);
   			File EOPDirectoryNew = new File(EOPDirectoryNameNew);
   			if(EOPDirectoryNew.exists() == false)
   			{
   				boolean EOPFlagNew = EOPDirectoryNew.mkdirs();
   				/*DEBUG*///System.out.println("EOPFlagNew: " + EOPFlagNew);
   				/*DEBUG*///System.out.println("EOPDirectoryNew.exists(): " + EOPDirectoryNew.exists());
   				if(EOPFlagNew == true){
                  pathFileOut.write(EOPDirectoryNameNew);
                  pathFileOut.close();
   					return EOPDirectoryNameNew;
   				}else{
   					JOptionPane.showMessageDialog(null,
   							"ERROR: Could not create directory to store receipts and reports."
   									+ "\nYou may need to check the permissions on the directory path."
   									+ "\n\nUsing the default path to store receipts and reports."
   									, "Directory Creation Error", JOptionPane.ERROR_MESSAGE);
   					pathFileOut.write(EOPDirectoryName);
                  pathFileOut.close();
                  return EOPDirectoryName;
   				}
   			}else if(EOPDirectoryNew.exists() == true){
   				pathFileOut.write(EOPDirectoryNameNew);
               pathFileOut.close();
               return EOPDirectoryNameNew;
   			}else{
   				JOptionPane.showMessageDialog(null,
   						"ERROR: Could not create directory to store receipts and reports."
   								+ "\nYou may need to check the permissions on the directory path."
   								+ "\n\nUsing the default path to store receipts and reports."
   								, "Directory Creation Error", JOptionPane.ERROR_MESSAGE);
   				pathFileOut.write(EOPDirectoryName);
               pathFileOut.close();
               return EOPDirectoryName;
   			}      
   		}else{
            pathFileOut.write(EOPDirectoryName);
            pathFileOut.close();
   			return EOPDirectoryName;
   		}
        }catch(IOException ex){
            ex.printStackTrace();
            return EOPDirectoryName;     
        }
      }           
	}

	/**
	 * Required by the Impresario framework
	 */
	//-----------------------------------------------------------------------------------
	private void setDependencies()
	{
		dependencies = new Properties();

		dependencies.setProperty("Login", "LoginError");

		myRegistry.setDependencies(dependencies);
	}

	/**
	 * Method called from client to get the value of a particular field
	 * held by the objects encapsulated by this object.
	 *
	 * @param	key	Name of database column (field) for which the client wants the value
	 *
	 * @return	Value associated with the field
	 */
	//----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("LoginError") == true)
		{
			return loginErrorMessage;
		}
		if(key.equals("TransactionError") == true){
			return transactionErrorMessage;
		}
		else{
			if(myLoggedInWorker != null){
				return myLoggedInWorker.getState(key);
			}
		}
		return null;
	}

	/**
	 * Method called from client if the full state of the object is desired - i.e.,
	 * the values associated with all fields.
	 *
	 * @return	null, since there is no 'persistent state' associated with this class.
	 */
	//----------------------------------------------------------
	public Properties getCompleteState()
	{
		return null;
	}
	public Properties dueDatePrep(){
		MaxDueDate maxDueDate = null;
		Properties maxDueDateProps = null;
		String message = "";
		String status = "0";
		int oldMaxDueDateInt = 0;
		int todaysDateInt = 0;
		try {
			maxDueDate = new MaxDueDate();
			maxDueDateProps = (Properties)maxDueDate.getState("deep_props");
			maxDueDateProps.setProperty("MaxDueDateInt", maxDueDateProps.getProperty("CurrentMaxDueDate").replaceAll("\\-", ""));
			maxDueDateProps.setProperty("TodaysDateInt", getTodaysDate().replaceAll("\\-", ""));
			oldMaxDueDateInt = Integer.parseInt(maxDueDateProps.getProperty("MaxDueDateInt"));
			todaysDateInt = Integer.parseInt(maxDueDateProps.getProperty("TodaysDateInt"));
			if (todaysDateInt > oldMaxDueDateInt){
				maxDueDateProps.setProperty("Expired", "true");
				status = "1";
				Properties loggedInWorkerProps = (Properties)myLoggedInWorker.getState("ref_props");
				if (loggedInWorkerProps.getProperty("Credentials").equals("Administrator")){
					maxDueDateProps.setProperty("IsAdmin", "true");
				} else {
					maxDueDateProps.setProperty("IsAdmin", "false");
					status = "-1";
					message = "notify administrator due date needs to be updated";
				}
			} else {
				maxDueDateProps.setProperty("Expired", "false");
				status = "0";
			}
		} catch (InvalidPrimaryKeyException e) {
			message = "can not load max due date '" + e.getMessage() + "'";
			status = "-1";
		} catch(NumberFormatException e){
			message = "date parse error";
			status = "-1";
		} catch(NullPointerException e){
			message = "date is null";
			status = "-1";
		}
		if (maxDueDateProps == null){
			maxDueDateProps = new Properties();
		}
		maxDueDateProps.setProperty("Status", status);
		maxDueDateProps.setProperty("Message", message);
		return maxDueDateProps;
	}
//	private static Pattern patternDate = Pattern.compile("^[0-9]{4}\\-[0-9]{2}\\-[0-9]{2}$");
	private static Pattern patternDate = Pattern.compile("^[0-9]{4}\\-((0[1-9])|(1[0-2]))\\-((0[1-9])|([1-2][0-9])|(3[0-1]))$");
	public String validateDate(String dateStr){
		String errMsg = "";
		if (dateStr == null){
			errMsg = "must supply a valid due date";
		} else {
			Matcher m = patternDate.matcher(dateStr);
			if (m.find()){
			} else {
				errMsg = "must supply a valid due date";
			}
		}
		return errMsg;
	}
	private String datePopUp(String message){
		String s = (String)JOptionPane.showInputDialog(
				myFrame,
				"Due date\n",
				message,
				JOptionPane.PLAIN_MESSAGE,
				null,
				null,
				"");
		return s;
	}
	//--------------------------------------------------------------------------
	//
	//----------------------------------------------------------------------------
	@SuppressWarnings("deprecation")
	public void stateChangeRequest(String key, Object value){
		if ( key.equals( "Login" )){
			if(value != null){
				loginErrorMessage = "";
				boolean flag = loginWorker((Properties)value);
				if(flag == true){
					Properties dueDateProps = dueDatePrep();
					if (dueDateProps.getProperty("Status").equals("0")){
						createAndShowTransactionChoiceView();
					} else if (dueDateProps.getProperty("Status").equals("1")){//expired max due date
						String s = datePopUp("Enter new max due date (YYYY-MM-DD)");
						String e = validateDate(s);
						if (e.equals("")){
							String newMaxDueDate = s.replaceAll("\\-", "");
							int newMaxDueDateInt = Integer.parseInt(newMaxDueDate);
							int todaysDateInt = Integer.parseInt(dueDateProps.getProperty("TodaysDateInt"));
							if (newMaxDueDateInt > todaysDateInt){
								String oldMaxDueDate = dueDateProps.getProperty("CurrentMaxDueDate");
								dueDateProps.setProperty("CurrentMaxDueDate", s);
								MaxDueDate maxDueDate = new MaxDueDate(dueDateProps);
								maxDueDate.update(oldMaxDueDate);
								Properties mddStatusProps = (Properties)maxDueDate.getState("update_status");
								if (mddStatusProps.getProperty("Status").equals("0")){
									createAndShowTransactionChoiceView();
								} else {
									loginErrorMessage = "error updating max due date";
								}
							} else {
								loginErrorMessage = "due date must be after today";
							}
						} else {
							loginErrorMessage = e;
						}
					} else {//error occured
						loginErrorMessage = dueDateProps.getProperty("Message");
					}
				}
			}
		}
		else if(key.equals("CancelTransaction")){
			createAndShowTransactionChoiceView();
		}
		else if(key.equals("Logout")){
			myLoggedInWorker = null;
			myViews.clear();
			createAndShowLoginView();
		}
		else if(key.equals("PerformTransaction")){
			if(value != null){
				if(myLoggedInWorker != null){
					String trans = (String)value;
					System.out.println("Librarian.stateChangeRequest(...) PerformTransaction " + trans);
					doTransaction(trans);
				}
				else{
					transactionErrorMessage = "Transaction impossible: worker not logged in.";
				}
			}
		}
		else
			// MODEL MAPPING: Receipt of "Dismiss Window" user action shown on
			// Menu State Diagram (from Main Menu Screen to final state)
			if ( key.equals( "ExitSystem" ) )
			{
				exitSystem();
			}

		myRegistry.updateSubscribers( key, this );
	}


	/**
	 * Method called from external client (typically, a view). This method will
	 * cause an exit from the system, and is likely to be called as a result of
	 * a click on a button labeled something like 'Exit Application'.
	 */
	//-----------------------------------------------------------------------------------
	protected void exitSystem()
	{
		System.exit(0);
	}

	public boolean loginWorker(Properties props){
		String userName = props.getProperty("Name");
		String enteredPassword = props.getProperty("Password");

		try{
			myLoggedInWorker = new Worker(userName);
			//String myWorkerPassword = (String) myWorker.getState("Password");
			//System.out.println(myWorkerPassword);
		}
		catch(InvalidPrimaryKeyException ex){
			loginErrorMessage = "Error: " + ex.getMessage();
			return false;
		}
		try{
			myLoggedInWorker.verifyPassword((String) myLoggedInWorker.getState("Password"), enteredPassword);
		}
		catch(PasswordMismatchException e){
			loginErrorMessage = "Error: " + e.getMessage();
			return false;
		}
		if(!myLoggedInWorker.getState("Status").equals("Active")){
			loginErrorMessage = "Unable to log worker in; worker is inactive.";
			return false;
		}
		return true;
	}

	public void doTransaction(String transactionType){
		//DEBUG System.out.println("do transaction "+transactionType);
		try{
			TransactionL trans = TransactionFactory.createTransaction(transactionType, myLoggedInWorker);

			//DEBUG System.out.println("Transaction created ok...");
			trans.subscribe("CancelTransaction", this);
			trans.stateChangeRequest("DoYourJob", "");
		}
		catch(Exception ex){
			transactionErrorMessage = "FATAL ERROR: TRANSACTION FAILURE - Unrecognized transaction!";
			new Event(Event.getLeafLevelClassName(this), "createTransaction",
					"Transaction Creation Failure: Unrecognized transaction " + ex.toString(),
					Event.ERROR);
		}
	}

	/** Register objects to receive state updates. */
	//----------------------------------------------------------
	public void subscribe(String key, IView subscriber)
	{
		// DEBUG: System.out.println("Cager[" + myTableName + "].subscribe");
		// forward to our registry
		myRegistry.subscribe(key, subscriber);
	}

	/** Unregister previously registered objects. */
	//----------------------------------------------------------
	public void unSubscribe(String key, IView subscriber)
	{
		// DEBUG: System.out.println("Cager.unSubscribe");
		// forward to our registry
		myRegistry.unSubscribe(key, subscriber);
	}

	//-----------------------------------------------------------------
	public void updateState(String key, Object value)
	{
		// Every update will be handled in stateChangeRequest
		stateChangeRequest(key, value);
	}

	//Create pop up to input a new directory if desired
	private static Object inputPopUp(String PU_Title, String PU_Message){
		return JOptionPane.showInputDialog(null, 
				PU_Message, PU_Title,
				JOptionPane.QUESTION_MESSAGE,
				null, null,
				EOP_DEFAULT_PATH_STRING);
	}

	//-----------------------------------------------------------------------------
	protected void createAndShowLoginView()
	{
		System.out.println("createAndShowLoginView()");
		ViewL localView = ViewFactory.createView("LoginView", this); // USE VIEW FACTORY

		// swap the contents of current frame to this view
		swapToView(localView);
	}

	private void createAndShowTransactionChoiceView(){
		ViewL localView = (ViewL)myViews.get("TransactionChoiceView");
		if(localView == null){
			//create our initial view
			localView = ViewFactory.createView("TransactionChoiceView", this);

			myViews.put("TransactionChoiceView", localView);

			//make the view visible by installing it into the frame
			swapToView(localView);
		}
		else{
			swapToView(localView);
		}
	}

	private String getTodaysDate(){
		String myDate;
		Calendar today = Calendar.getInstance();
		int year = today.get(Calendar.YEAR);
		int month = (today.get(Calendar.MONTH) +1);
		int day = today.get(Calendar.DATE);
		if(month < 10 && day < 10){
			myDate = year + "-0" + month + "-0" + day;
		}
		else if(month < 10){
			myDate = year + "-0" + month + "-" + day;
		}
		else if(day < 10){
			myDate = year + "-" + month + "-0" + day;
		}
		else{
			myDate = year + "-" + month + "-" + day;
		}

		return myDate;
	}

	//----------------------------------------------------------------------------
	private void swapToPanelView(JPanel otherView){
		TransactionL.SWAP_TO_PANEL(myFrame, otherView);
	}

	//-----------------------------------------------------------------------------
	public void swapToView( IView otherView){
		TransactionL.SWAP_TO_VIEW(myFrame, otherView);
	}


}//end of class