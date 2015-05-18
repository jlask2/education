// tabs=4
//************************************************************
//	COPYRIGHT 2014 Sandeep Mitra and Stephanie Cruz, The
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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.NumberFormat;
import java.util.EventObject;
import java.util.Properties;

import java.util.Vector;

// project imports
import common.PropertyFile;
import impresario.IModel;

/** The class containing the Login View  for the EOP Library application */
//==============================================================
public class LoginView extends ViewL
{
	// GUI stuff
	private JButton loginButton;
	private JButton cancelButton;

	private JTextField userName;
	private JPasswordField password;
	private JLabel userNameLabel;
	private JLabel passwordLabel;

	// For showing error message
	private MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public LoginView( IModel lib)
	{
		super(lib, "LoginView");
		///
		setPreferredSize(new Dimension(800, 300));
		setBackground(MainFrame.SEL_COLOR);
		setLayout(null);
		validateVec = new Vector<JLabel>();
		validateVecMessage = new Vector<JLabel>();
		///
		constructVars();
		constructFields();

		myModel.subscribe("LoginError", this);
		viewTitle = "Login";
		refreshLogoTitle();

//		tempQuickLogin();
	}

	@Override
	public void onPostSetVisible(boolean isVisible){
		if (isVisible){
			userName.requestFocusInWindow();
		}
	}

	private Rectangle[] rs;
	private Rectangle[] rt;

	private void constructVars(){
		rs = ALayout.generate(this);
		rt = ALayout.generateHdrMain(rs);
	}

	private void constructFields(){
		int aHeight = 25;
		int aWidthC0 = 125;
		int aWidthC1 = 200;
		int aWidthC2 = 250;
		int aHorzSp = 5;
		int aVertSp = 5;
		int aMarginTop = 5;
		int aMarginLeft = 5;
		int aMarginRight = 5;
		int aMarginBottom = 5;
		int aValidDim = 25;
		int aValidMessageHeight = 20;
		/////
		createValidate(rt[1].x + aMarginLeft,
				rt[1].y + aMarginTop,
				aValidDim,
				aValidDim, "bannerID.errorMessage");

		userNameLabel = new JLabel("Banner Id:");
		add(userNameLabel);
		userNameLabel.setBounds(rt[1].x + aMarginLeft + aValidDim,
				rt[1].y + aMarginTop + aHeight * 0 + aVertSp * 0,
				aWidthC0,
				aHeight);

		userName = new JTextField(10);
		userName.addKeyListener(this);
		add(userName);
		userName.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim,
				rt[1].y + aMarginTop + aHeight * 0 + aVertSp * 0,
				aWidthC1,
				aHeight);

		createValidateMessage(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim,
				rt[1].y + aMarginTop + aHeight + aVertSp,
				aWidthC2,
				aValidMessageHeight, "bannerID.errorMessage");

		createValidate(rt[1].x + aMarginLeft,
				rt[1].y + aMarginTop + aHeight + aVertSp * 2 + aValidMessageHeight,
				aValidDim,
				aValidDim, ".errorMessage");

		passwordLabel = new JLabel("Password:");
		add(passwordLabel);
		passwordLabel.setBounds(rt[1].x + aMarginLeft + aValidDim,
				rt[1].y + aMarginTop + aHeight + aVertSp * 2 + aValidMessageHeight,
				aWidthC0,
				aHeight);

		password = new JPasswordField(25);
		password.addKeyListener(this);
		add(password);
		password.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim,
				rt[1].y + aMarginTop + aHeight + aVertSp * 2 + aValidMessageHeight,
				aWidthC1,
				aHeight);

		createValidateMessage(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim,
				rt[1].y + aMarginTop + aHeight * 2 + aVertSp * 3 + aValidMessageHeight,
				aWidthC2,
				aValidMessageHeight, "password.errorMessage");

		loginButton = new JButton();
		loginButton.addActionListener(this);
		add(loginButton);
		loginButton.setBounds((rt[1].x + rt[1].width) - (ImageHolder.ICB_LOGIN.getIconWidth() + aMarginRight),
				(rt[1].y + rt[1].height) - (aMarginBottom + ImageHolder.ICB_LOGIN.getIconHeight()),
				ImageHolder.ICB_LOGIN.getIconWidth(),
				ImageHolder.ICB_LOGIN.getIconHeight());
		loginButton.setBackground(bkColor);
		//loginButton.setBackground(MainFrame.BK_COLOR);
		loginButton.setMargin(new Insets(0, 0, 0, 0));
		loginButton.setBorder(null);
		loginButton.setIcon(ImageHolder.ICB_LOGIN);
		loginButton.setRolloverIcon(ImageHolder.ICB_LOGIN_HOVER);
		loginButton.setOpaque(false);

		cancelButton = new JButton();
		cancelButton.addActionListener(this);
		add(cancelButton);
		cancelButton.setBounds((rt[1].x + rt[1].width) - (ImageHolder.ICB_LOGIN.getIconWidth() + aHorzSp + ImageHolder.ICB_EXIT.getIconWidth() + aMarginRight),
				(rt[1].y + rt[1].height) - (aMarginBottom + ImageHolder.ICB_EXIT.getIconHeight()),
				ImageHolder.ICB_EXIT.getIconWidth(),
				ImageHolder.ICB_EXIT.getIconHeight());
		//cancelButton.setBackground(MainFrame.BK_COLOR);
		cancelButton.setBackground(bkColor);
		cancelButton.setMargin(new Insets(0, 0, 0, 0));
		cancelButton.setBorder(null);
		cancelButton.setIcon(ImageHolder.ICB_EXIT);
		cancelButton.setRolloverIcon(ImageHolder.ICB_EXIT_HOVER);
		cancelButton.setOpaque(false);

		/////
		JPanel slog = createStatusLog("   ");
		slog.setBounds(rt[1].x, 
				(rt[1].y + rt[1].height) - (aHeight + 5 + slog.getInsets().top + slog.getInsets().bottom), 
				rt[1].width, 
				aHeight + 5 + slog.getInsets().top + slog.getInsets().bottom);
		add(slog);
	}

	private void tempQuickLogin(){
		userName.addKeyListener(new KeyListener(){//FOR total convenience
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == 10){
					if (userName.getText().equals("")){
						userName.setText("800539252");
						password.setText("1");
						loginButton.doClick();
					}
				}
			}
			@Override
			public void keyReleased(KeyEvent arg0) {

			}
			@Override
			public void keyTyped(KeyEvent arg0) {				
			}});
	}

	// Overide the paint method to ensure we can set the focus when made visible
	//-------------------------------------------------------------
	public void paint(Graphics g)
	{
		super.paint(g);

	}


	// Create the status log field
	//-------------------------------------------------------------
	private JPanel createStatusLog(String initialMessage)
	{

		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

	/**
	 *
	 * This simply checks to see that the Strings are in proper
	 * FORMAT - i.e., not empty
	 */
	//-------------------------------------------------------------
	private boolean validate(String userName, String password)
	{
		if ((userName == null) || (userName.length() == 0))
		{
			displayErrorMessage("Please enter a valid user name!");
			return false;
		}
		if ((password == null) || (password.length() == 0))
		{
			displayErrorMessage("Please enter a valid password!");
			return false;
		}

		return true;
	}

	public Properties validateFields(){
		Properties errorMessages = new Properties();
		Properties myFieldsToValidate = new Properties();
		myFieldsToValidate.setProperty("bannerID", userName.getText());
		myFieldsToValidate.setProperty("password", password.getText());

		errorMessages = validateAllFields(myFieldsToValidate);

		return errorMessages;
	}

	// IMPRESARIO: Note how we use this method name instead of 'actionPerformed()'
	// now. This is because the super-class View has methods for both action and
	// focus listeners, and both of them delegate to this method. So this method
	// is called when you either have an action (like a button click) or a loss
	// of focus (like tabbing out of a textfield, moving your cursor to something
	// else in the view, etc.)
	// process events generated from our GUI components
	//-------------------------------------------------------------
	//process the button click
	public void processAction(EventObject e){
		clearErrorMessage();
		if (e.getSource() == cancelButton){
			processCancel();
		}
		if (e.getSource() == loginButton){
			processLogin();
		}
	}

	public void processLogin(){
		setValidationMarkersOff();
		clearErrorMessage();
		Properties allErrorMessages = new Properties();
		allErrorMessages = validateFields();
		String myErrorMessage = allErrorMessages.getProperty("error");
		if(myErrorMessage.equals("")){
			// STEP 1: Gather and VALIDATE user entered data
			String uName = userName.getText();
			char[] pwdChars = password.getPassword();


			String pwd = "";
			if (pwdChars != null)
			{
				pwd = new String(pwdChars);
			}
			boolean flag = validate(uName, pwd);
			if (flag == true)
			{
				// STEP 1.1: Create the OBJECT in which you 
				// will send data over to the model.
				Properties props = new Properties();
				props.setProperty("Name", uName);
				props.setProperty("Password", pwd);

				userName.setText("");
				password.setText("");

				// Call the method in the Model (Librarian) to process the login data,
				// sending the Properties object above as an actual parameter with it
				// The view's registry makes sure it gets to the right model who has
				// subscribed to it.
				myModel.stateChangeRequest("Login", props);
			}
		} else {
			clearErrorMessage();
			displayErrorMessage("Validation Error");
			markValidationErrors(allErrorMessages);
		}
	}

	public void processCancel(){
		myModel.stateChangeRequest("ExitSystem", "");
	}

	/**
	 * Update view methods - methods invoked to call the view back when something
	 * needs to be updated on it. For example, if you want to update the message
	 * field with a "Login Error" message if the password and user name don't match.
	 * Should the method(s) written here follow the Observer pattern?
	 */
	//---------------------------------------------------------
	public void updateState(String key, Object value)
	{
		// STEP 6: Be sure to finish the end of the 'perturbation'
		// by indicating how the view state gets updated.
		if (key.equals("LoginError") == true)
		{
			// display the passed text
			displayErrorMessage((String)value);
		}

	}

	/**
	 * Display error message
	 */
	//----------------------------------------------------------
	public void displayErrorMessage(String message)
	{
		statusLog.displayErrorMessage(message);
	}

	/**
	 * Clear error message
	 */
	//----------------------------------------------------------
	public void clearErrorMessage()
	{
		statusLog.clearErrorMessage();
	}

	//-----------------------------------------------------------
	protected void processListSelection(EventObject evt){}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == 10){
			if (e.getSource() == password){
				loginButton.doClick();
			}else if (e.getSource() == userName){
				loginButton.doClick();
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
