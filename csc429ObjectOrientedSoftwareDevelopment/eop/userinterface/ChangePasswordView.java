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
package userinterface;

//system imports
import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.util.Calendar;
import java.util.Properties;
import java.util.EventObject;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import java.util.Vector;

//project imports
import impresario.IModel;

public class ChangePasswordView extends ViewL{
	//GUI Components
	private JButton cancelButton;
	private JButton submitButton;

	private JLabel oldPasswordLabel;
	private JPasswordField oldPasswordTextField;
	private JLabel newPasswordLabel;
	private JPasswordField newPasswordTextField;
	private JLabel confirmPasswordLabel;
	private JPasswordField confirmPasswordTextField;

	private Properties updatedWorker;
	private Properties guiStatus;

	private MessageView statusLog;

	public ChangePasswordView(IModel trans){
		super(trans, "ChangePasswordView");
		setBackground(bkColor);
		setBackground(MainFrame.SEL_COLOR);
		setPreferredSize(prefSize);
		setLayout(null);
		updatedWorker = new Properties();
		guiStatus = new Properties();
		guiStatus.setProperty("Type", "none");
		validateVec = new Vector<JLabel>();
		validateVecMessage = new Vector<JLabel>();
		constructVars();
		constructFields();
		populateFields();

		myModel.subscribe("TransactionError", this);
		viewTitle = "    Change Password";
		refreshLogoTitle();
	}
	@Override
	public void onPostSetVisible(boolean isVisible){
		if (isVisible){
			oldPasswordTextField.requestFocusInWindow();
		}
	}   
	public void updateState(String key, Object value){
		if(key.equals("TransactionError") == true){
			displayErrorMessage((String)value);
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
		int aWidthC3 = 150;
		int aHorzSp = 5;
		int aVertSp = 5;
		int aMarginTop = 5;
		int aMarginLeft = 5;
		int aMarginRight = 5;
		int aMarginBottom = 5;
		int aValidDim = 25;
		int aValidMessageHeight = 20;

		createValidate(rt[1].x + aMarginLeft,
				rt[1].y + aMarginTop,
				aValidDim,
				aValidDim, "oldPassword.errorMessage");

		oldPasswordLabel = new JLabel (" * Enter old password:");
		add(oldPasswordLabel);
		oldPasswordLabel.setBounds(rt[1].x + aMarginLeft + aValidDim,
				rt[1].y + aMarginTop,
				aWidthC0,
				aHeight);

		oldPasswordTextField = new JPasswordField();
		oldPasswordTextField.addKeyListener(this);
		add(oldPasswordTextField);
		oldPasswordTextField.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim * 2,
				rt[1].y + aMarginTop,
				aWidthC1,
				aHeight);

		createValidateMessage(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim * 2,
				rt[1].y + aMarginTop + aHeight + aVertSp,
				aWidthC2,
				aValidMessageHeight, "oldPassword.errorMessage");

		createValidate(rt[1].x + aMarginLeft,
				rt[1].y + aMarginTop + aHeight + aVertSp * 2 + aValidMessageHeight,
				aValidDim,
				aValidDim, "newConfirmPassword.errorMessage");

		newPasswordLabel = new JLabel(" * Enter new password:");
		add(newPasswordLabel);
		newPasswordLabel.setBounds(rt[1].x + aMarginLeft + aValidDim,
				rt[1].y + aMarginTop + aHeight + aVertSp * 2+ aValidMessageHeight,
				aWidthC3,
				aHeight);

		newPasswordTextField = new JPasswordField();
		newPasswordTextField.addKeyListener(this);
		add(newPasswordTextField);
		newPasswordTextField.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim * 2,
				rt[1].y + aMarginTop + aHeight + aVertSp * 2 + aValidMessageHeight,
				aWidthC1,
				aHeight);

		createValidateMessage(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim * 2,
				rt[1].y + aMarginTop + aHeight * 2 + aVertSp * 3 + aValidMessageHeight,
				aWidthC2,
				aValidMessageHeight, "newConfirmPassword.errorMessage");

		createValidate(rt[1].x + aMarginLeft,
				rt[1].y + aMarginTop + aHeight * 2 + aVertSp * 4 + aValidMessageHeight * 2,
				aValidDim,
				aValidDim, "newConfirmPassword.errorMessage");

		confirmPasswordLabel = new JLabel("* Confirm new password:");
		add(confirmPasswordLabel);
		confirmPasswordLabel.setBounds(rt[1].x + aMarginLeft + aValidDim,
				rt[1].y + aMarginTop + aHeight * 2 + aVertSp * 4 + aValidMessageHeight * 2,
				aWidthC3,
				aHeight);

		confirmPasswordTextField = new JPasswordField();
		confirmPasswordTextField.addKeyListener(this);
		add(confirmPasswordTextField);
		confirmPasswordTextField.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim * 2,
				rt[1].y + aMarginTop + aHeight * 2 + aVertSp * 4 + aValidMessageHeight * 2,
				aWidthC1,
				aHeight);

		createValidateMessage(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim * 2,
				rt[1].y + aMarginTop + aHeight * 3 + aVertSp * 5 + aValidMessageHeight * 2,
				aWidthC2,
				aValidMessageHeight, "newConfirmPassword.errorMessage");

		submitButton = new JButton();
		submitButton.addActionListener(this);
		add(submitButton);
		submitButton.setBounds((rt[1].x + rt[1].width) - (ImageHolder.ICB_SUBMIT.getIconWidth() + aMarginRight),
				(rt[1].y + rt[1].height) - (aMarginBottom + ImageHolder.ICB_SUBMIT.getIconHeight()),
				ImageHolder.ICB_SUBMIT.getIconWidth(),
				ImageHolder.ICB_SUBMIT.getIconHeight());
		submitButton.setBackground(MainFrame.BK_COLOR);
		submitButton.setMargin(new Insets(0, 0, 0, 0));
		submitButton.setBorder(null);
		submitButton.setIcon(ImageHolder.ICB_SUBMIT);
		submitButton.setRolloverIcon(ImageHolder.ICB_SUBMIT_HOVER);
		submitButton.setOpaque(false);

		cancelButton = new JButton();
		cancelButton.addActionListener(this);
		add(cancelButton);
		cancelButton.setBounds((rt[1].x + rt[1].width) - (ImageHolder.ICB_SUBMIT.getIconWidth() + aHorzSp + ImageHolder.ICB_CANCEL.getIconWidth() + aMarginRight),
				(rt[1].y + rt[1].height) - (aMarginBottom + ImageHolder.ICB_CANCEL.getIconHeight()),
				ImageHolder.ICB_CANCEL.getIconWidth(),
				ImageHolder.ICB_CANCEL.getIconHeight());
		cancelButton.setBackground(MainFrame.BK_COLOR);
		cancelButton.setBackground(bkColor);
		cancelButton.setMargin(new Insets(0, 0, 0, 0));
		cancelButton.setBorder(null);
		cancelButton.setIcon(ImageHolder.ICB_CANCEL);
		cancelButton.setRolloverIcon(ImageHolder.ICB_CANCEL_HOVER);
		cancelButton.setOpaque(false);

		JPanel slog = createStatusLog("   ");
		slog.setBounds(rt[1].x, 
				(rt[1].y + rt[1].height) - (aHeight + 5 + slog.getInsets().top + slog.getInsets().bottom), 
				rt[1].width, 
				aHeight + 5 + slog.getInsets().top + slog.getInsets().bottom);
		add(slog);

		requiredMessage = new JLabel("   * Indicates a required field.");
		requiredMessage.setForeground(Color.black);
		add(requiredMessage);
		requiredMessage.setBounds(rt[1].x + aMarginLeft + aHorzSp + aValidDim, 
				(rt[1].y + rt[1].height) - (aHeight * 2 + 15 + slog.getInsets().top + slog.getInsets().bottom), 
				aWidthC1, 
				aHeight);
	}

	private JPanel createStatusLog(String initialMessage){
		statusLog = new MessageView(initialMessage);
		return statusLog;
	}

	public void populateFields(){
		//not needed for this view
	}

	public String getFullName(){
		String firstNameString = (String)myModel.getState("FirstName");
		String lastNameString = (String)myModel.getState("LastName");
		return firstNameString + " " + lastNameString;
	}

	public void processAction(EventObject e){
		clearErrorMessage();
		if(e.getSource() == cancelButton){
			processCancel();
		}
		if(e.getSource() == submitButton){
			processSubmit();
		}
	}

	public void processSubmit(){
		setValidationMarkersOff();
		Properties allErrorMessages = new Properties();
		allErrorMessages = validateFields();
		String myErrorMessage = allErrorMessages.getProperty("error");
		if(myErrorMessage.equals("")){
			guiStatus.setProperty("Type", "modified");
			getInsertValues();

			//confirm the password change
			setGUIOptions();
			int confirmation = confirmPopUp("Change Password", 
					"Are you sure you want to change your password?");
			if(confirmation == 0){
				myModel.stateChangeRequest("Submit", updatedWorker);
				String myMessage = (String)myModel.getState("TransactionError");

				if(myMessage.equals("Worker updated successfully.")){
					acknowledgePopUp("Password Change",
							"Password updated successfully!");
					myModel.stateChangeRequest("CancelChangePassword", null);
				}
				else{
					guiStatus.setProperty("Type", "cancelled");
					setGUIOptions();
				}
			}
			else{
				guiStatus.setProperty("Type", "cancelled");
				setGUIOptions();
			}
		}
		else{
			clearErrorMessage();
			displayErrorMessage("Validation Error");
			markValidationErrors(allErrorMessages);
			//acknowledgeErrorsPopUp("Change Password", myErrorMessage);
		}
	}

	public void setGUIOptions(){
		if(guiStatus.getProperty("Type").equals("modified")){
			cancelButton.setEnabled(false);
			submitButton.setEnabled(false);
			oldPasswordTextField.setEditable(false);
			newPasswordTextField.setEditable(false);
			confirmPasswordTextField.setEditable(false);
		}
		if(guiStatus.getProperty("Type").equals("cancelled")){
			cancelButton.setEnabled(true);
			submitButton.setEnabled(true);
			oldPasswordTextField.setEditable(true);
			newPasswordTextField.setEditable(true);
			confirmPasswordTextField.setEditable(true);
		}
	}

	//Create pop up to confirm worker insertion
	private int confirmPopUp(String PU_Title, String PU_Message){
		Object[] options = {"Confirm", "Cancel"};
		return JOptionPane.showOptionDialog(MainFrame.getInstance(), 
				PU_Message, PU_Title,
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[1]);
	}

	//Create pop up to acknowledge worker update
	private int acknowledgePopUp(String PU_title, String PU_message){
		Object[] options = {"Ok"};
		return JOptionPane.showOptionDialog(MainFrame.getInstance(),
				PU_message,
				PU_title,
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]);
	}

	public void getInsertValues(){
		char[] myPassword = newPasswordTextField.getPassword();
		String stringPassword = new String(myPassword);
		updatedWorker.setProperty("BannerID", (String)myModel.getState("BannerID"));
		updatedWorker.setProperty("Password", stringPassword);
		updatedWorker.setProperty("FirstName", (String)myModel.getState("FirstName"));
		updatedWorker.setProperty("LastName", (String)myModel.getState("LastName"));
		updatedWorker.setProperty("ContactPhone", (String)myModel.getState("ContactPhone"));
		updatedWorker.setProperty("Email", (String)myModel.getState("Email"));
		updatedWorker.setProperty("Credentials", (String)myModel.getState("Credentials"));
		updatedWorker.setProperty("DateOfLatestCredentialsStatus", (String)myModel.getState("DateOfLatestCredentialsStatus"));
		updatedWorker.setProperty("DateOfHire", (String)myModel.getState("DateOfHire"));
		updatedWorker.setProperty("Status", (String)myModel.getState("Status"));
		updatedWorker.setProperty("DateOfLastUpdate", getTodaysDate());
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

	public Properties validateFields(){
		Properties errorMessages = new Properties();
		Properties myFieldsToValidate = new Properties();

		char[] oldPasswordInput = oldPasswordTextField.getPassword();
		String opswd = new String(oldPasswordInput);
		char[] newPasswordInput = newPasswordTextField.getPassword();
		String npswd = new String(newPasswordInput);
		char[] confirmPasswordInput = confirmPasswordTextField.getPassword();
		String cpswd = new String (confirmPasswordInput);

		myFieldsToValidate.setProperty("password", npswd);

		errorMessages = validateAllFields(myFieldsToValidate); 

		String workerPassword = (String)myModel.getState("Password");

		String errMsg = "";

		if(!(workerPassword.equals(opswd))){
			errMsg = errorMessages.getProperty("error");
			errMsg = errMsg + "\n" + "Old password incorrect.";
			errorMessages.setProperty("oldPassword.errorMessage", "Old password incorrect.");
		}
		if(!(npswd.equals(cpswd))){
			errMsg = errorMessages.getProperty("error");
			errMsg = errMsg + "\n" + "New Password and Confirm Password must match.";
			errorMessages.setProperty("newConfirmPassword.errorMessage", "New Password and Confirm Password must match.");
		}
		errorMessages.setProperty("error", errMsg);
		return errorMessages;
	}

	public void processCancel(){
		myModel.stateChangeRequest("CancelChangePassword", null);
	}

	public void displayErrorMessage(String message){
		statusLog.displayErrorMessage(message);
	}

	public void clearErrorMessage(){
		statusLog.clearErrorMessage();
	}

	@Override
	protected void processListSelection(EventObject evt){
		//not needed
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == 10){
			if (e.getSource() == oldPasswordTextField){
				submitButton.doClick();
			}else if (e.getSource() == newPasswordTextField){
				submitButton.doClick();
			}else if (e.getSource() == confirmPasswordTextField){
				submitButton.doClick();
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