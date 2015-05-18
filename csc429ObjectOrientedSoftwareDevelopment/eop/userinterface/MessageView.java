// tabs=4
//************************************************************
//	COPYRIGHT 2010 Sandeep Mitra and Students, The
//    College at Brockport, State University of New York. - 
//	  ALL RIGHTS RESERVED
//
// This file is the product of The College at Brockport and cannot 
// be reproduced, copied, or used in any shape or form without 
// the express written consent of The College at Brockport.
//************************************************************
//
// specify the package
package userinterface;

// system imports
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.JLabel;


//==============================================================
public class MessageView extends JPanel
{

	// GUI elements
	private JLabel statusLog;
	private String message;
	private String initialMessage;
	private int maxWidth;

	private Color bkColor;

	// Class constructor
	//----------------------------------------------------------
	public MessageView(String initMessage)
	{
		maxWidth = 450;
		initialMessage = initMessage;
		statusLog = null;
		bkColor = MainFrame.BK_COLOR;
		setLayout(null);
		setBounds(new Rectangle(0, 0, 450, 100));
		setBackground (bkColor);
		setOpaque(false);
		createStatusLog();
		
	}


	public JLabel getStatusLog() {
		return statusLog;
	}


	// Overide the paint method to ensure we can set the focus when made visible
	//-------------------------------------------------------------
	public void paint(Graphics g)
	{
		super.paint(g);
	}
	
	protected void createStatusLog(){
		if (statusLog != null){
			message = statusLog.getText();
			remove(statusLog);
		} else {
			message = initialMessage;
		}
		statusLog = new JLabel(message);
		statusLog.setBounds(ALayout.generate(this)[1]);
		Font myFont = new Font("Arial", Font.BOLD, 16);
		statusLog.setFont(myFont);
		statusLog.setForeground(Color.blue);
		add(statusLog);
	}

	@Override
	public void setBounds(Rectangle bounds){
		if (bounds.width > maxWidth){
			bounds.width = maxWidth;
		}
		super.setBounds(bounds);
		createStatusLog();
	}
	
	@Override
	public void setBounds(int x, int y, int width, int height){
		if (width > maxWidth){
			width = maxWidth;
		}
		super.setBounds(x, y, width, height);
		createStatusLog();
	}


	/**
	 * Display ordinary message
	 */
	//----------------------------------------------------------
	public void displayMessage(String message)
	{
		// display the passed text in red
		statusLog.setForeground(Color.blue);
		statusLog.setText(message);
	}

	/**
	 * Display error message
	 */
	//----------------------------------------------------------
	public void displayErrorMessage(String message)
	{
		// display the passed text in red
		statusLog.setForeground(Color.red);
		statusLog.setText(message);
	}

	/**
	 * Clear error message
	 */
	//----------------------------------------------------------
	public void clearErrorMessage()
	{
		statusLog.setText("                           ");
	}	
}

