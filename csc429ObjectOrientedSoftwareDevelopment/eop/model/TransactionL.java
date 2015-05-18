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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.Enumeration;
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

public abstract class TransactionL extends Transaction{
	public static boolean VIEW_DBG = true;
	protected TransactionL(Worker worker) throws Exception {
		super(worker);
	}
	@Override
	protected void swapToPanelView(JPanel otherView){
		TransactionL.SWAP_TO_PANEL(myFrame, otherView);
	}
	@Override
	public void swapToView(IView otherView){
		TransactionL.SWAP_TO_VIEW(myFrame, otherView);
	}
	public static void SWAP_TO_PANEL(JFrame mainFrame, JPanel otherView){
		ViewL vl = null;
		if (otherView instanceof ViewL){
			vl = (ViewL)otherView;
		}
		JPanel currentView = (JPanel)mainFrame.getContentPane().getComponent(2);
		ViewL cvl = null;
		if (currentView instanceof ViewL){
			cvl = (ViewL)currentView;
		}
		if (cvl != null){
			cvl.onPreSetVisible(false);
		}
		currentView.getRootPane().setDefaultButton(null);
		currentView.setVisible(false);
		if (cvl != null){
			cvl.onPostSetVisible(false);
		}
		if (currentView != null){
			mainFrame.getContentPane().remove(currentView);
		}
		mainFrame.getContentPane().add(otherView);
		if (vl != null){
			vl.onPreSetVisible(true);
		}
		otherView.setVisible(true);
		//mainFrame.pack();
		MainFrame mf = (MainFrame)mainFrame;
		Insets mfInsets = mainFrame.getInsets();
		Rectangle r = new Rectangle(vl.getPreferredSize());
		r.x = mf.getX();
		r.y = mf.getY();
		r.width += (mfInsets.left + mfInsets.right);
		r.height += mf.getLogoPanel().getHeight() + mf.getCopyRightPanel().getHeight();
		r.height += (mfInsets.top + mfInsets.bottom);
		//
		if (mainFrame.getWidth() != r.getWidth() || mainFrame.getHeight() != r.getHeight()){
			mainFrame.setBounds(r);
		}
		//WindowPosition.placeCenter(mainFrame);
		if (vl != null){
			vl.onPostSetVisible(true);
		}
	}
	public static void SWAP_TO_VIEW(JFrame mainFrame, IView otherView){
		if(otherView == null){
			System.out.println("TransactionL.SWAP_TO_VIEW IView otherView " + otherView + " Missing view for display");
		}
		if (otherView instanceof ViewL){
			ViewL vl = (ViewL)otherView;
			MainFrame mf = (MainFrame)mainFrame;
			mf.getLogoPanel().getLeftLabel().setText(vl.getViewTitle());
			mf.getLogoPanel().setLogoTitleBar(vl.getLogoTitleBar());
			mf.getLogoPanel().repaint();
			if (TransactionL.VIEW_DBG){
				System.out.println("TransactionL.swapToView(...) " + vl.getViewClassName());
			}
		}
		if(otherView instanceof JPanel){
			SWAP_TO_PANEL(mainFrame, (JPanel)otherView);
		} else{
			System.out.println("TransactionL.SWAP_TO_VIEW IView otherView " + otherView + " Non-displayable view object sent");
		}
	}
	public static Properties deepCopy(Properties in_properties){
		Properties p = new Properties();
		Enumeration<?> keys = in_properties.propertyNames();
		while(keys.hasMoreElements()){
			String keyName = (String)keys.nextElement();
			String keyVal = in_properties.getProperty(keyName);
			p.setProperty(keyName, keyVal);
		}
		return p;
	}
	public static void deepCopy(Properties in_properties, Properties out_properties){
		Enumeration<?> keys = in_properties.propertyNames();
		while(keys.hasMoreElements()){
			String keyName = (String)keys.nextElement();
			String keyVal = new String(in_properties.getProperty(keyName));
			out_properties.setProperty(keyName, keyVal);
		}
	}
	public static Vector<Properties> modelCollectionToProperties(Vector<?> v){
		Vector<Properties> vp = new Vector<Properties>();
		for(int i = 0;i < v.size();i++){
			IModel im = (IModel)v.get(i);
			vp.add((Properties)im.getState("deep_props"));
		}
		return vp;
	}


}