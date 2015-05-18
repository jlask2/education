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
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.Properties;
import java.util.EventObject;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.text.html.Option;

import model.StudentBorrower;
import model.Worker;

import java.util.Vector;
import java.util.regex.Pattern;



//project imports
import impresario.IModel;

public class ModifyStudentBorrowerView extends ViewL{
	//GUI components
	private JButton backButton;
	private JButton cancelButton;
	private JButton submitButton;
	private JLabel bannerIDLabel;
	private JTextField bannerIDTextField;
	private JLabel firstNameLabel;
	private JTextField firstNameTextField;
	private JLabel lastNameLabel;
	private JTextField lastNameTextField;
	private JLabel contactPhoneLabel;
	private JTextField contactPhoneTextField;
	private JLabel emailLabel;
	private JTextField emailTextField;
	private JLabel borrowerStatusLabel;
	private JComboBox borrowerStatusComboBox;
	private JLabel dateOfFirstRegistrationLabel;
	private JLabel statusLabel;
	private JComboBox statusComboBox;
	private JLabel notesLabel;
	private JTextArea notesTextArea;
	private JLabel monetaryPenaltyLabel;
	private JTextField monetaryPenaltyTextField;

	private JComboBox monthComboBox;
	private JComboBox dayComboBox;
	private JComboBox yearComboBox;

	private MessageView statusLog;

	private Properties studentBorrowerToModifyProps;

	private Properties modifiedProperties;

	private Properties guiStatus;

	public ModifyStudentBorrowerView(IModel trans){
		super(trans, "ModifyStudentBorrowerView");
		setPreferredSize(prefSize);
		setBackground(MainFrame.SEL_COLOR);
		setLayout(null);
		///
		guiStatus = new Properties();
		guiStatus.setProperty("Type", "none");
		modifiedProperties = new Properties();
		validateVec = new Vector<JLabel>();
		validateVecMessage = new Vector<JLabel>();
		constructVars();
		constructFields();
		populateFields();
		myModel.subscribe("TransactionError", this);
		viewTitle = "    Modify Student Borrower";
		refreshLogoTitle();
	}
	@Override
	public void onPostSetVisible(boolean isVisible){
		if (isVisible){
			firstNameTextField.requestFocusInWindow();
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
				aValidDim, "bannerID.errorMessage");

		bannerIDLabel = new JLabel (" * Banner ID:");
		add(bannerIDLabel);
		bannerIDLabel.setBounds(rt[1].x + aMarginLeft + aValidDim,
				rt[1].y + aMarginTop,
				aWidthC0,
				aHeight);

		bannerIDTextField = new JTextField();
		bannerIDTextField.addKeyListener(this);
		add(bannerIDTextField);
		bannerIDTextField.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim * 2,
				rt[1].y + aMarginTop,
				aWidthC1,
				aHeight);

		createValidateMessage(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim * 2,
				rt[1].y + aMarginTop + aHeight + aVertSp,
				aWidthC2,
				aValidMessageHeight, "bannerID.errorMessage");

		createValidate(rt[1].x + aMarginLeft,
				rt[1].y + aMarginTop + aHeight + aVertSp * 2 + aValidMessageHeight,
				aValidDim,
				aValidDim, "firstName.errorMessage");      

		firstNameLabel = new JLabel (" * First Name:");
		add(firstNameLabel);
		firstNameLabel.setBounds(rt[1].x + aMarginLeft + aValidDim,
				rt[1].y + aMarginTop + aHeight + aVertSp * 2+ aValidMessageHeight,
				aWidthC0,
				aHeight);

		firstNameTextField = new JTextField();
		firstNameTextField.addKeyListener(this);
		add(firstNameTextField);
		firstNameTextField.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim * 2,
				rt[1].y + aMarginTop + aHeight + aVertSp * 2 + aValidMessageHeight,
				aWidthC1,
				aHeight);

		createValidateMessage(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim * 2,
				rt[1].y + aMarginTop + aHeight * 2 + aVertSp * 3 + aValidMessageHeight,
				aWidthC2,
				aValidMessageHeight, "firstName.errorMessage");

		createValidate(rt[1].x + aMarginLeft,
				rt[1].y + aMarginTop + aHeight * 2 + aVertSp * 4 + aValidMessageHeight * 2,
				aValidDim,
				aValidDim, "lastName.errorMessage");

		lastNameLabel = new JLabel(" * Last Name:");
		add(lastNameLabel);
		lastNameLabel.setBounds(rt[1].x + aMarginLeft + aValidDim,
				rt[1].y + aMarginTop + aHeight * 2 + aVertSp * 4 + aValidMessageHeight * 2,
				aWidthC0,
				aHeight);

		lastNameTextField = new JTextField();
		lastNameTextField.addKeyListener(this);
		add(lastNameTextField);
		lastNameTextField.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim * 2,
				rt[1].y + aMarginTop + aHeight * 2 + aVertSp * 4 + aValidMessageHeight * 2,
				aWidthC1,
				aHeight);

		createValidateMessage(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim * 2,
				rt[1].y + aMarginTop + aHeight * 3 + aVertSp * 5 + aValidMessageHeight * 2,
				aWidthC2,
				aValidMessageHeight, "lastName.errorMessage");

		createValidate(rt[1].x + aMarginLeft,
				rt[1].y + aMarginTop + aHeight * 3 + aVertSp * 6 + aValidMessageHeight * 3,
				aValidDim,
				aValidDim, "phoneNumber.errorMessage");

		contactPhoneLabel = new JLabel(" * Phone Number:");
		add(contactPhoneLabel);
		contactPhoneLabel.setBounds(rt[1].x + aMarginLeft + aValidDim,
				rt[1].y + aMarginTop + aHeight * 3 + aVertSp * 6 + aValidMessageHeight * 3,
				aWidthC0,
				aHeight);

		contactPhoneTextField = new JTextField();
		contactPhoneTextField.addKeyListener(this);
		add(contactPhoneTextField);
		contactPhoneTextField.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim * 2,
				rt[1].y + aMarginTop + aHeight * 3 + aVertSp * 6 + aValidMessageHeight * 3,
				aWidthC1,
				aHeight);

		createValidateMessage(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim * 2,
				rt[1].y + aMarginTop + aHeight * 4  + aVertSp * 7 + aValidMessageHeight * 3,
				aWidthC2,
				aValidMessageHeight, "phoneNumber.errorMessage");

		createValidate(rt[1].x + aMarginLeft,
				rt[1].y + aMarginTop + aHeight * 4  + aVertSp * 8 + aValidMessageHeight * 4,
				aValidDim,
				aValidDim, "email.errorMessage");

		emailLabel = new JLabel(" * Email:");
		add(emailLabel);
		emailLabel.setBounds(rt[1].x + aMarginLeft + aValidDim,
				rt[1].y + aMarginTop + aHeight * 4 + aVertSp * 8 + aValidMessageHeight * 4,
				aWidthC0,
				aHeight);

		emailTextField = new JTextField();
		emailTextField.addKeyListener(this);
		add(emailTextField);
		emailTextField.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim * 2,
				rt[1].y + aMarginTop + aHeight * 4 + aVertSp * 8 + aValidMessageHeight * 4,
				aWidthC1,
				aHeight);

		createValidateMessage(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim * 2,
				rt[1].y + aMarginTop + aHeight * 5  + aVertSp * 9 + aValidMessageHeight * 4,
				aWidthC2,
				aValidMessageHeight, "email.errorMessage");

		createValidate(rt[1].x + aMarginLeft,
				rt[1].y + aMarginTop + aHeight * 5 + aVertSp * 10 + aValidMessageHeight * 5,
				aValidDim,
				aValidDim, "borrowerStatus.errorMessage");

		borrowerStatusLabel = new JLabel(" * Borrower Status:");
		add(borrowerStatusLabel);
		borrowerStatusLabel.setBounds(rt[1].x + aMarginLeft + aValidDim,
				rt[1].y + aMarginTop + aHeight * 5 + aVertSp * 10 + aValidMessageHeight * 5,
				aWidthC0,
				aHeight);

		borrowerStatusComboBox = new JComboBox();
		add(borrowerStatusComboBox);
		borrowerStatusComboBox.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim * 2,
				rt[1].y + aMarginTop + aHeight * 5 + aVertSp * 10 + aValidMessageHeight * 5,
				aWidthC1,
				aHeight);

		createValidateMessage(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim * 2,
				rt[1].y + aMarginTop + aHeight * 6  + aVertSp * 11 + aValidMessageHeight * 5,
				aWidthC2,
				aValidMessageHeight, "borrowerStatus.errorMessage");

		createValidate(rt[1].x + aMarginLeft,
				rt[1].y + aMarginTop + aHeight * 6 + aVertSp * 12 + aValidMessageHeight * 6,
				aValidDim,
				aValidDim, "dateOfFirstRegistration.errorMessage");

		dateOfFirstRegistrationLabel = new JLabel(" * Date of Registration:");
		add(dateOfFirstRegistrationLabel);
		dateOfFirstRegistrationLabel.setBounds(rt[1].x + aMarginLeft + aValidDim,
				rt[1].y + aMarginTop + aHeight * 6 + aVertSp * 12 + aValidMessageHeight * 6,
				aWidthC3,
				aHeight);

		monthComboBox = new JComboBox();
		//monthComboBox.addActionListener(this);
		add(monthComboBox);
		monthComboBox.setBounds(rt[1].x + (aMarginLeft) + aWidthC0 + aHorzSp + aValidDim * 2,
				rt[1].y + aMarginTop + aHeight * 6 + aVertSp * 12 + aValidMessageHeight * 6,
				(aWidthC1-100),
				(aHeight));

		dayComboBox = new JComboBox();
		//dayComboBox.addActionListener(this);
		add(dayComboBox);
		dayComboBox.setBounds(rt[1].x + (aMarginLeft) + aWidthC0 + aHorzSp +110 + aValidDim * 2,
				rt[1].y + aMarginTop + aHeight * 6 + aVertSp * 12 + aValidMessageHeight * 6,
				(aWidthC1-140),
				(aHeight));

		yearComboBox = new JComboBox();
		//yearComboBox.addActionListener(this);
		add(yearComboBox);
		yearComboBox.setBounds(rt[1].x + (aMarginLeft) + aWidthC0 + aHorzSp + 180 + aValidDim * 2,
				rt[1].y + aMarginTop + aHeight * 6 + aVertSp * 12 + aValidMessageHeight * 6,
				(aWidthC1-140),
				(aHeight));

		createValidateMessage(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim * 2,
				rt[1].y + aMarginTop + aHeight * 7  + aVertSp * 13 + aValidMessageHeight * 6,
				aWidthC2,
				aValidMessageHeight, "dateOfFirstRegistration.errorMessage");

		createValidate(rt[1].x + aMarginLeft,
				rt[1].y + aMarginTop + aHeight * 7 + aVertSp * 14 + aValidMessageHeight * 7,
				aValidDim,
				aValidDim, "monetaryPenalty.errorMessage");

		monetaryPenaltyLabel = new JLabel(" * Monetary Penalty:");
		add(monetaryPenaltyLabel);
		monetaryPenaltyLabel.setBounds(rt[1].x + aMarginLeft + aValidDim,
				rt[1].y + aMarginTop + aHeight * 7 + aVertSp * 14 + aValidMessageHeight * 7,
				aWidthC0,
				aHeight);

		monetaryPenaltyTextField = new JTextField();
		monetaryPenaltyTextField.addKeyListener(this);
		add(monetaryPenaltyTextField);
		monetaryPenaltyTextField.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim * 2,
				rt[1].y + aMarginTop + aHeight * 7 + aVertSp * 14 + aValidMessageHeight * 7,
				aWidthC1,
				aHeight);

		createValidateMessage(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim * 2,
				rt[1].y + aMarginTop + aHeight * 8  + aVertSp * 15 + aValidMessageHeight * 7,
				aWidthC2,
				aValidMessageHeight, "monetaryPenalty.errorMessage");

		createValidate(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aWidthC1 + aHorzSp * 3 + aValidDim * 2,
				rt[1].y + aMarginTop + aHeight * 0 + aVertSp * 0 + aValidMessageHeight * 0,
				aValidDim,
				aValidDim, "status.errorMessage");

		statusLabel = new JLabel(" * Status:");
		add(statusLabel);
		statusLabel.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aWidthC1 + aHorzSp * 3 + aValidDim * 3,
				rt[1].y + aMarginTop + aHeight * 0 + aVertSp * 0 + aValidMessageHeight * 0,
				aWidthC0,
				aHeight);

		statusComboBox = new JComboBox();
		add(statusComboBox);
		statusComboBox.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aWidthC1 + aHorzSp * 3 + aWidthC0 + aHorzSp + aValidDim * 3,
				rt[1].y + aMarginTop + aHeight * 0 + aVertSp * 0 + aValidMessageHeight * 0,
				aWidthC1,
				aHeight);

		createValidateMessage(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aWidthC1 + aHorzSp * 3 + aWidthC0 + aHorzSp + aValidDim * 3,
				rt[1].y + aMarginTop + aHeight * 1  + aVertSp * 1 + aValidMessageHeight * 0,
				aWidthC2,
				aValidMessageHeight, "status.errorMessage");

		createValidate(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aWidthC1 + aHorzSp * 3 + aValidDim * 2,
				rt[1].y + aMarginTop + aHeight * 1 + aVertSp * 2 + aValidMessageHeight * 1,
				aValidDim,
				aValidDim, "notes.errorMessage");

		notesLabel = new JLabel("   Notes:");
		add(notesLabel);
		notesLabel.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aWidthC1 + aHorzSp * 3 + aValidDim * 3,
				rt[1].y + aMarginTop + aHeight * 1 + aVertSp * 2 + aValidMessageHeight * 1,
				aWidthC0,
				aHeight);

		notesTextArea = new JTextArea();
		add(notesTextArea);
		notesTextArea.setBounds(rt[1].x + aMarginLeft + aWidthC0 * 2 + aHorzSp * 5 + aWidthC1 + aValidDim * 3,
				rt[1].y + aMarginTop + aHeight * 1 + aVertSp * 2 + aValidMessageHeight * 1,
				aWidthC1,
				aHeight * 4);

		createValidateMessage(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aWidthC1 + aHorzSp * 3 + aWidthC0 + aHorzSp + aValidDim * 3,
				rt[1].y + aMarginTop + aHeight * 5  + aVertSp * 3 + aValidMessageHeight * 1,
				aWidthC2,
				aValidMessageHeight, "notes.errorMessage");

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

		backButton = new JButton();
		backButton.addActionListener(this);
		add(backButton);
		backButton.setBounds((rt[1].x + rt[1].width) - (ImageHolder.ICB_SUBMIT.getIconWidth() + aHorzSp + ImageHolder.ICB_CANCEL.getIconWidth() + aHorzSp + ImageHolder.ICB_BACK.getIconWidth()+ aMarginRight),
				(rt[1].y + rt[1].height) - (aMarginBottom + ImageHolder.ICB_BACK.getIconHeight()),
				ImageHolder.ICB_BACK.getIconWidth(),
				ImageHolder.ICB_BACK.getIconHeight());
		backButton.setBackground(bkColor);
		backButton.setMargin(new Insets(0, 0, 0, 0));
		backButton.setBorder(null);
		backButton.setIcon(ImageHolder.ICB_BACK);
		backButton.setRolloverIcon(ImageHolder.ICB_BACK_HOVER);
		backButton.setOpaque(false);

		JPanel slog = createStatusLog("    ");
		slog.setBounds(rt[1].x, 
				(rt[1].y + rt[1].height) - (aHeight + 5 + slog.getInsets().top + slog.getInsets().bottom), 
				rt[1].width, 
				aHeight + 5 + slog.getInsets().top + slog.getInsets().bottom);
		add(slog);

		requiredMessage = new JLabel("* Indicates a required field. (Note: Requires either a phone number OR an email addresss OR both)");
		requiredMessage.setForeground(Color.black);
		add(requiredMessage);
		requiredMessage.setBounds(rt[1].x + aMarginLeft + aHorzSp + aValidDim, 
				(rt[1].y + rt[1].height) - (aHeight * 2 + 15 + slog.getInsets().top + slog.getInsets().bottom), 
				aWidthC1 * 3, 
				aHeight);
	}

	@Override
	public void updateState(String key, Object value){
		if(key.equals("TransactionError") == true){
			displayErrorMessage((String)value);
		}
	}

	//Create our status log
	private JPanel createStatusLog(String initialMessage){
		statusLog = new MessageView(initialMessage);
		return statusLog;
	}

	//populate fields / drop downs
	public void populateFields(){
		borrowerStatusComboBox.addItem("Good Standing");
		borrowerStatusComboBox.addItem("Delinquent");

		statusComboBox.addItem("Active");
		statusComboBox.addItem("Inactive");

		studentBorrowerToModifyProps = (Properties)myModel.getState("deep_props_student_borrower_to_modify");

		bannerIDTextField.setText(studentBorrowerToModifyProps.getProperty("BannerID"));
		bannerIDTextField.setEnabled(false);

		firstNameTextField.setText(studentBorrowerToModifyProps.getProperty("FirstName"));
		lastNameTextField.setText(studentBorrowerToModifyProps.getProperty("LastName"));
		contactPhoneTextField.setText(studentBorrowerToModifyProps.getProperty("ContactPhone"));
		emailTextField.setText(studentBorrowerToModifyProps.getProperty("Email"));
		monetaryPenaltyTextField.setText(studentBorrowerToModifyProps.getProperty("MonetaryPenalty"));
		notesTextArea.setText(studentBorrowerToModifyProps.getProperty("Notes"));

		if(myModel.getState("Credentials").equals("Administrator")){
			if(studentBorrowerToModifyProps.getProperty("BorrowerStatus").equals("Good Standing")){
				borrowerStatusComboBox.setSelectedItem("Good Standing");
			}
			else{
				borrowerStatusComboBox.setSelectedItem("Delinquent");
			}
		}
		else{
			if(studentBorrowerToModifyProps.getProperty("BorrowerStatus").equals("Good Standing")){
				borrowerStatusComboBox.setSelectedItem("Good Standing");
				borrowerStatusComboBox.setEnabled(false);
			}
			else{
				borrowerStatusComboBox.setSelectedItem("Ordinary");
				borrowerStatusComboBox.setEnabled(false);
			}
			monetaryPenaltyTextField.setEditable(false);
		}

		if(studentBorrowerToModifyProps.getProperty("Status").equals("Active")){
			statusComboBox.setSelectedItem("Active");
			statusComboBox.setEnabled(false);
		}
		else{
			statusComboBox.setSelectedItem("Inactive");
			statusComboBox.setEnabled(true);
		}

		String dateOfFirstRegistration = studentBorrowerToModifyProps.getProperty("DateOfFirstRegistration");
		String year = dateOfFirstRegistration.substring(0,4);
		int ourYear = 0;
		try{
			ourYear = Integer.parseInt(year);
		}
		catch(NumberFormatException e){

		}
		String month = dateOfFirstRegistration.substring(5,7);
		Calendar today = Calendar.getInstance();
		int thisYear = today.get(Calendar.YEAR);
		for(int i = 10; i > 0; i--){
			yearComboBox.addItem(thisYear);
			thisYear--;
		}

		monthComboBox.addItem("January");
		monthComboBox.addItem("February");
		monthComboBox.addItem("March");
		monthComboBox.addItem("April");
		monthComboBox.addItem("May");
		monthComboBox.addItem("June");
		monthComboBox.addItem("July");
		monthComboBox.addItem("August");
		monthComboBox.addItem("September");
		monthComboBox.addItem("October");
		monthComboBox.addItem("November");
		monthComboBox.addItem("December");

		yearComboBox.setSelectedItem(ourYear);
		if(month.equals("01") || month.equals("1"))
			monthComboBox.setSelectedItem("January");
		if(month.equals("02") || month.equals("2"))
			monthComboBox.setSelectedItem("February");
		if(month.equals("03") || month.equals("3"))
			monthComboBox.setSelectedItem("March");
		if(month.equals("04") || month.equals("4"))
			monthComboBox.setSelectedItem("April");
		if(month.equals("05") || month.equals("5"))
			monthComboBox.setSelectedItem("May");
		if(month.equals("06") || month.equals("6"))
			monthComboBox.setSelectedItem("June");
		if(month.equals("07") || month.equals("7"))
			monthComboBox.setSelectedItem("July");
		if(month.equals("08") || month.equals("8"))
			monthComboBox.setSelectedItem("August");
		if(month.equals("09") || month.equals("9"))
			monthComboBox.setSelectedItem("September");
		if(month.equals("10") || month.equals("10"))
			monthComboBox.setSelectedItem("October");
		if(month.equals("11") || month.equals("11"))
			monthComboBox.setSelectedItem("November");
		if(month.equals("12") || month.equals("12"))
			monthComboBox.setSelectedItem("December");

		resetDayArrayWithLeadingZeros(dayComboBox, month, ourYear);
		dayComboBox.setSelectedItem(dateOfFirstRegistration.substring(8,10));

	}

	//Get the worker information for our information panel
	public String getFullName(){
		String firstNameString = (String)myModel.getState("FirstName");
		String lastNameString = (String)myModel.getState("LastName");
		return firstNameString + " " + lastNameString;
	}

	public void processAction(EventObject e){
		clearErrorMessage();
		if(e.getSource() == backButton){
			myModel.stateChangeRequest("CancelThisView", null);
		}
		if(e.getSource() == cancelButton){
			myModel.stateChangeRequest("CancelTransaction", null);
		}
		if (e.getSource() == submitButton){
			processSubmit();
		}
		if(e.getSource() == dayComboBox || e.getSource() == monthComboBox || e.getSource() == yearComboBox){
			setComboBoxValues();
		}
	}

	public void setComboBoxValues(){
		int year = (int) yearComboBox.getSelectedItem();
		String month = (String) monthComboBox.getSelectedItem();

		//Add this line for the first time through setting the values...
		if(month == null){
			month = "January";
		}
		resetDayArrayWithLeadingZeros(dayComboBox, month, year);
	}

	public void processSubmit(){
		setValidationMarkersOff();
		Properties allErrorMessages = new Properties();
		allErrorMessages = validateFields();
		String myErrorMessage = allErrorMessages.getProperty("error");	
		if(myErrorMessage.equals("")){
			//successfully completed validation
			guiStatus.setProperty("Type", "modified");
			getInputInformation();

			//confirm the modification
			setGUIOptions();
			int confirmation = confirmPopUp("Modify Student Borrower", 
					"Are you sure you want to modify this student borrower?");
			if(confirmation == 0){
				myModel.stateChangeRequest("Submit", modifiedProperties);
				String myMessage = (String)myModel.getState("TransactionError");

				if(myMessage.equals("Student Borrower updated successfully.")){
					acknowledgePopUp("Modify Worker",
							"Student Borrower updated successfully!");
					myModel.stateChangeRequest("CancelThisView", null);
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
			//acknowledgeErrorsPopUp("Modify Student Borrower", myErrorMessage);
		}
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

	//Create pop up to confirm worker update
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

	public void setGUIOptions(){
		if(guiStatus.getProperty("Type").equals("modified")){
			cancelButton.setEnabled(false);
			submitButton.setEnabled(false);
			bannerIDTextField.setEditable(false);
			firstNameTextField.setEditable(false);
			lastNameTextField.setEditable(false);
			contactPhoneTextField.setEditable(false);
			emailTextField.setEditable(false);
			notesTextArea.setEditable(false);
			borrowerStatusComboBox.setEnabled(false);
			monthComboBox.setEnabled(false);
			dayComboBox.setEnabled(false);
			yearComboBox.setEnabled(false);
			statusComboBox.setEnabled(false);
		}
		if(guiStatus.getProperty("Type").equals("cancelled")){
			cancelButton.setEnabled(true);
			submitButton.setEnabled(true);
			bannerIDTextField.setEditable(true);
			firstNameTextField.setEditable(true);
			lastNameTextField.setEditable(true);
			contactPhoneTextField.setEditable(true);
			emailTextField.setEditable(true);
			monthComboBox.setEnabled(true);
			dayComboBox.setEnabled(true);
			yearComboBox.setEnabled(true);
			notesTextArea.setEditable(true);
			borrowerStatusComboBox.setEnabled(true);
			if(studentBorrowerToModifyProps.getProperty("Status").equals("Active")){
				statusComboBox.setSelectedItem("Active");
				statusComboBox.setEnabled(false);
			}
			else{
				statusComboBox.setSelectedItem("Inactive");
				statusComboBox.setEnabled(true);
			}
		}
	}

	public Properties validateFields(){
		Properties errorMessages = new Properties();
		Properties myFieldsToValidate = new Properties();
		//myFieldsToValidate.setProperty("bannerID", bannerIDTextField.getText());
		myFieldsToValidate.setProperty("firstName", firstNameTextField.getText());
		myFieldsToValidate.setProperty("lastName", lastNameTextField.getText());
		myFieldsToValidate.setProperty("phoneNumber", contactPhoneTextField.getText());
		myFieldsToValidate.setProperty("email", emailTextField.getText());
		myFieldsToValidate.setProperty("monetaryPenalty", monetaryPenaltyTextField.getText());

		errorMessages = validateAllFields(myFieldsToValidate);

		String emailInput = emailTextField.getText();
		String contactPhoneInput = contactPhoneTextField.getText();
		if(emailInput.equals("") && contactPhoneInput.equals("")){
			String errMsg = errorMessages.getProperty("error");
			errMsg = errMsg + "\n" + "Please enter either a phone number or an email.";
			errorMessages.setProperty("error", errMsg);
		}
		return errorMessages;
	}

	public void getInputInformation(){
		modifiedProperties.setProperty("BannerID", bannerIDTextField.getText());
		modifiedProperties.setProperty("FirstName", firstNameTextField.getText());
		modifiedProperties.setProperty("LastName", lastNameTextField.getText());
		modifiedProperties.setProperty("ContactPhone", contactPhoneTextField.getText());
		modifiedProperties.setProperty("Email", emailTextField.getText());
		modifiedProperties.setProperty("BorrowerStatus", (String)borrowerStatusComboBox.getSelectedItem());
		modifiedProperties.setProperty("DateOfLatestBorrowerStatus", getLastBorrowerStatusDate());
		modifiedProperties.setProperty("DateOfFirstRegistration", convertDateToString(yearComboBox.getSelectedItem(), monthComboBox.getSelectedItem(), dayComboBox.getSelectedItem()));
		modifiedProperties.setProperty("MonetaryPenalty", monetaryPenaltyTextField.getText());
		modifiedProperties.setProperty("Notes", notesTextArea.getText());
		modifiedProperties.setProperty("Status", (String)statusComboBox.getSelectedItem());
		modifiedProperties.setProperty("DateOfLastUpdate", getTodaysDate());
	}

	public String convertDateToString(Object year, Object month, Object day){
		String ourMonth = mapMonthToInt(month.toString());
		return year.toString() + "-" + ourMonth + "-" + day.toString();
	}

	public String mapMonthToInt(String inputMonth){
		if (inputMonth.equals("January")){
			return "01";
		}
		else if (inputMonth.equals("February")){
			return "02";
		}
		else if (inputMonth.equals("March")){
			return "03";
		}
		else if (inputMonth.equals("April")){
			return "04";
		}
		else if (inputMonth.equals("May")){
			return "05";
		}
		else if (inputMonth.equals("June")){
			return "06";
		}
		else if (inputMonth.equals("July")){
			return "07";
		}
		else if (inputMonth.equals("August")){
			return "08";
		}
		else if (inputMonth.equals("September")){
			return "09";
		}
		else if (inputMonth.equals("October")){
			return "10";
		}
		else if (inputMonth.equals("November")){
			return "11";
		}
		else{
			return "12";
		}
	}

	public String getLastBorrowerStatusDate(){
		if(borrowerStatusComboBox.getSelectedItem().equals(studentBorrowerToModifyProps.getProperty("BorrowerStatus"))){
			return studentBorrowerToModifyProps.getProperty("DateOfLatestBorrowerStatus");
		}
		else{
			return getTodaysDate();
		}
	}

	public String getTodaysDate(){
		String myDate;
		Calendar today = Calendar.getInstance();
		int year = today.get(Calendar.YEAR);
		int month = (today.get(Calendar.MONTH) +1);
		int day = today.get(Calendar.DATE);

		if(day < 10 && month < 10){
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

	public void displayErrorMessage(String message){
		statusLog.displayErrorMessage(message);
	}

	public void clearErrorMessage(){
		statusLog.clearErrorMessage();
	}

	@Override
	protected void processListSelection(EventObject evt) {
		// TODO Auto-generated method stub
		//This method is not needed for this view
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == 10){
			if (e.getSource() == bannerIDTextField){
				submitButton.doClick();
			} else if (e.getSource() == firstNameTextField){
				submitButton.doClick();
			} else if (e.getSource() == lastNameTextField){
				submitButton.doClick();
			} else if (e.getSource() == contactPhoneTextField){
				submitButton.doClick();
			}else if (e.getSource() == emailTextField){
				submitButton.doClick();
			}else if (e.getSource() == monetaryPenaltyTextField){
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
