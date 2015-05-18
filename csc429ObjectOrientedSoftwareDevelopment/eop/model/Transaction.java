//************************************************************
//	COPYRIGHT 2014 Sandeep Mitra and students 
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
import java.awt.BorderLayout;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Stack;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

//projects imports
import exception.InvalidPrimaryKeyException;
import event.Event;
import impresario.*;
import userinterface.MainFrame;
import userinterface.ViewL;
import userinterface.WindowPosition;

abstract public class Transaction implements IView, IModel, ISlideShow{
	protected Properties dependencies;
	protected ModelRegistry myRegistry;

	protected JFrame myFrame;
	protected Hashtable myViews;

	protected Stack<ViewL> myHistory;

	protected Worker myWorker;

	/**
	 * Constructor for this class.
	 * 
	 * Transaction remembers the logged in worker.
	 */
	//------------------------------------------------------------------------------------------------
	protected Transaction(Worker worker)throws Exception{
		myFrame = MainFrame.getInstance();
		myViews = new Hashtable();
		myHistory = new Stack<ViewL>();

		myWorker = worker;

		myRegistry = new ModelRegistry("Transaction");
		if (myRegistry == null){
			new Event(Event.getLeafLevelClassName(this), "Transaction",
					"Could not instantiate Registry", Event.ERROR);
		}
		setDependencies();
	}

	//------------------------------------------------------------------------------------------------
	protected abstract void setDependencies();

	//------------------------------------------------------------------------------------------------
	public abstract Object getState(String key);

	//------------------------------------------------------------------------------------------------
	public abstract void stateChangeRequest(String key, Object value);

	//------------------------------------------------------------------------------------------------
	public void updateState(String key, Object value){
		stateChangeRequest(key, value);
	}

	//------------------------------------------------------------------------------------------------
	public void subscribe(String key, IView subscriber){
		myRegistry.subscribe(key, subscriber);
	}

	//------------------------------------------------------------------------------------------------
	public void unSubscribe(String key, IView subscriber){
		myRegistry.unSubscribe(key, subscriber);
	}

	//------------------------------------------------------------------------------------------------
	protected void doYourJob(){
		ViewL v = createTransactionView();
		swapToView(v);
		myHistory.push(v);
	}	

	//------------------------------------------------------------------------------------------------
	protected abstract ViewL createTransactionView();

	//------------------------------------------------------------------------------------------------
	protected void swapToPanelView(JPanel otherView)
	{
		// MITRA: Component #2 is being accessed here because component #1 is the Logo Panel
		JPanel currentView = (JPanel)myFrame.getContentPane().getComponent( 2 );
		// and remove it
		if (currentView != null)
		{
			myFrame.getContentPane().remove(currentView);
		}

		// add our view into the CENTER of the MainFrame
		myFrame.getContentPane().add( otherView, BorderLayout.CENTER );

		//pack the frame and show it
		myFrame.pack();

		//Place in center
		WindowPosition.placeCenter(myFrame);
	}

	//------------------------------------------------------------------------------------------------
	public void swapToView(IView otherView){
		if(otherView == null){
			new Event(Event.getLeafLevelClassName(this), "swapToView", 
					"Missing view for display", Event.ERROR);
			return;
		}


		if(otherView instanceof JPanel){
			swapToPanelView((JPanel)otherView);
		}
		else{
			new Event(Event.getLeafLevelClassName(this), "swapToView",
					"Non-displayable view object sent", Event.ERROR);
		}
	}
}