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
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.LayoutManager;
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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import java.util.Vector;

//project imports
import impresario.IModel;

public class StudentBorrowerCheckInSearchView extends ViewL{
	//GUI Components
	private JButton cancelButton;
	private JButton searchButton;

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

	private JCheckBox includedInactiveCheckBox;

	private MessageView statusLog;

	public StudentBorrowerCheckInSearchView(IModel trans){
		super(trans, "StudentBorrowerCheckInSearchView");

		///
		setPreferredSize(prefSize);
		setBackground(MainFrame.SEL_COLOR);
		setLayout(null);
      validateVec = new Vector<JLabel>();
      validateVecMessage = new Vector<JLabel>();
		///
		constructVars();
		constructFields();
		populateFields();

		myModel.subscribe("TransactionError", this);
		viewTitle = "    Search Borrowers";
		refreshLogoTitle();
	}
	@Override
	public void onPostSetVisible(boolean isVisible){
		if (isVisible){
			bannerIDTextField.requestFocusInWindow();
		}
	}
	@Override
	public void updateState(String key, Object value){
		if (key.equals("TransactionError") == true){
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

		bannerIDLabel = new JLabel ("Banner ID:");
		add(bannerIDLabel);
		bannerIDLabel.setBounds(rt[1].x + aMarginLeft + aValidDim,
				rt[1].y + aMarginTop,
				aWidthC0,
				aHeight);

		bannerIDTextField = new JTextField();
		bannerIDTextField.addKeyListener(this);
		add(bannerIDTextField);
		bannerIDTextField.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim,
				rt[1].y + aMarginTop,
				aWidthC1,
				aHeight);
      
      createValidateMessage(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim,
				rt[1].y + aMarginTop + aHeight + aVertSp,
				aWidthC2,
				aValidMessageHeight, "bannerID.errorMessage");
     
		JLabel OrLabel = new JLabel ("OR:");
		add(OrLabel);
		OrLabel.setBounds(rt[1].x + aMarginLeft + aValidDim ,
				rt[1].y + aMarginTop + aHeight + + aVertSp + aValidMessageHeight,
				aWidthC0,
				aHeight);

      createValidate(rt[1].x + aMarginLeft,
				rt[1].y + aMarginTop + aHeight * 2 + aVertSp * 2 + aValidMessageHeight * 2,
				aValidDim,
				aValidDim, "firstName.errorMessage");
      
		firstNameLabel = new JLabel ("First Name:");
		add(firstNameLabel);
		firstNameLabel.setBounds(rt[1].x + aMarginLeft + aValidDim,
				rt[1].y + aMarginTop + aHeight * 2 + aVertSp * 2 + aValidMessageHeight * 2,
				aWidthC0,
				aHeight);

		firstNameTextField = new JTextField();
		firstNameTextField.addKeyListener(this);
		add(firstNameTextField);
		firstNameTextField.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim,
				rt[1].y + aMarginTop + aHeight * 2 + aVertSp * 2 + aValidMessageHeight * 2,
				aWidthC1,
				aHeight);
      
      createValidateMessage(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim,
				rt[1].y + aMarginTop + aHeight * 3 + aVertSp * 3 + aValidMessageHeight * 2,
				aWidthC2,
				aValidMessageHeight, "firstName.errorMessage");
      
      createValidate(rt[1].x + aMarginLeft,
				rt[1].y + aMarginTop + aHeight * 3 + aVertSp * 4 + aValidMessageHeight * 3,
				aValidDim,
				aValidDim, "lastName.errorMessage");
      
		lastNameLabel = new JLabel("Last Name:");
		add(lastNameLabel);
		lastNameLabel.setBounds(rt[1].x + aMarginLeft + aValidDim,
				rt[1].y + aMarginTop + aHeight * 3 + aVertSp * 4 + aValidMessageHeight * 3,
				aWidthC0,
				aHeight);

		lastNameTextField = new JTextField();
		lastNameTextField.addKeyListener(this);
		add(lastNameTextField);
		lastNameTextField.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim,
				rt[1].y + aMarginTop + aHeight * 3 + aVertSp * 4 + aValidMessageHeight * 3,
				aWidthC1,
				aHeight);

      createValidateMessage(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim,
				rt[1].y + aMarginTop + aHeight * 4 + aVertSp * 5 + aValidMessageHeight * 3,
				aWidthC2,
				aValidMessageHeight, "lastName.errorMessage");
            
      createValidate(rt[1].x + aMarginLeft,
				rt[1].y + aMarginTop + aHeight * 4 + aVertSp * 6 + aValidMessageHeight * 4,
				aValidDim,
				aValidDim, "phoneNumber.errorMessage");
      
		contactPhoneLabel = new JLabel("Contact Phone:");
		add(contactPhoneLabel);
		contactPhoneLabel.setBounds(rt[1].x + aMarginLeft + aValidDim,
				rt[1].y + aMarginTop + aHeight * 4 + aVertSp * 6 + aValidMessageHeight * 4,
				aWidthC0,
				aHeight);

		contactPhoneTextField = new JTextField();
		contactPhoneTextField.addKeyListener(this);
		add(contactPhoneTextField);
		contactPhoneTextField.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim,
				rt[1].y + aMarginTop + aHeight * 4 + aVertSp * 6 + aValidMessageHeight * 4,
				aWidthC1,
				aHeight);

      createValidateMessage(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim,
				rt[1].y + aMarginTop + aHeight * 5  + aVertSp * 7 + aValidMessageHeight * 4,
				aWidthC2,
				aValidMessageHeight, "phoneNumber.errorMessage");
      
      createValidate(rt[1].x + aMarginLeft,
				rt[1].y + aMarginTop + aHeight * 5  + aVertSp * 8 + aValidMessageHeight * 5,
				aValidDim,
				aValidDim, "email.errorMessage");

		emailLabel = new JLabel("Email:");
		add(emailLabel);
		emailLabel.setBounds(rt[1].x + aMarginLeft + aValidDim,
				rt[1].y + aMarginTop + aHeight * 5  + aVertSp * 8 + aValidMessageHeight * 5,
				aWidthC0,
				aHeight);

		emailTextField = new JTextField();
		emailTextField.addKeyListener(this);
		add(emailTextField);
		emailTextField.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim,
				rt[1].y + aMarginTop + aHeight * 5  + aVertSp * 8 + aValidMessageHeight * 5,
				aWidthC1,
				aHeight);

      createValidateMessage(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim,
				rt[1].y + aMarginTop + aHeight * 6  + aVertSp * 9 + aValidMessageHeight * 5,
				aWidthC2,
				aValidMessageHeight, "email.errorMessage");

		includedInactiveCheckBox = new JCheckBox("Include Inactive Student Borrowers");
		includedInactiveCheckBox.setOpaque(false);
		add(includedInactiveCheckBox);
		includedInactiveCheckBox.setBounds(rt[1].x + aMarginLeft + aValidDim,
				rt[1].y + aMarginTop + aHeight * 6  + aVertSp * 10 + aValidMessageHeight * 6,
				includedInactiveCheckBox.getPreferredSize().width,
				aHeight);

		searchButton = new JButton();
		searchButton.addActionListener(this);
		add(searchButton);
		searchButton.setBounds((rt[1].x + rt[1].width) - (ImageHolder.ICB_SEARCH.getIconWidth() + aMarginRight),
				(rt[1].y + rt[1].height) - (aMarginBottom + ImageHolder.ICB_SEARCH.getIconHeight()),
				ImageHolder.ICB_SEARCH.getIconWidth(),
				ImageHolder.ICB_SEARCH.getIconHeight());
      searchButton.setBackground(bkColor);
		searchButton.setMargin(new Insets(0, 0, 0, 0));
		searchButton.setBorder(null);
		searchButton.setIcon(ImageHolder.ICB_SEARCH);
		searchButton.setOpaque(false);
		searchButton.setRolloverIcon(ImageHolder.ICB_SEARCH_HOVER);

		cancelButton = new JButton();
		cancelButton.addActionListener(this);
		add(cancelButton);
		cancelButton.setBounds((rt[1].x + rt[1].width) - (ImageHolder.ICB_SEARCH.getIconWidth() + aHorzSp + ImageHolder.ICB_CANCEL.getIconWidth() + aMarginRight),
				(rt[1].y + rt[1].height) - (aMarginBottom + ImageHolder.ICB_CANCEL.getIconHeight()),
				ImageHolder.ICB_CANCEL.getIconWidth(),
				ImageHolder.ICB_CANCEL.getIconHeight());
      cancelButton.setBackground(bkColor);
		cancelButton.setMargin(new Insets(0, 0, 0, 0));
		cancelButton.setBorder(null);
		cancelButton.setIcon(ImageHolder.ICB_CANCEL);
		cancelButton.setRolloverIcon(ImageHolder.ICB_CANCEL_HOVER);
		cancelButton.setOpaque(false);

		JPanel slog = createStatusLog("   ");
		slog.setBounds(rt[1].x, 
				(rt[1].y + rt[1].height) - (aHeight + 5 + slog.getInsets().top + slog.getInsets().bottom), 
				rt[1].width + aWidthC0, 
				aHeight + 5 + slog.getInsets().top + slog.getInsets().bottom);
		add(slog);
	}

	//create our status log
	private JPanel createStatusLog(String initialMessage){
		statusLog = new MessageView(initialMessage);
		return statusLog;
	}

	//populate fields
	public void populateFields(){
		if(((String)myModel.getState("TransactionType")).equals("DeleteStudentBorrowerTransaction")){
			includedInactiveCheckBox.setEnabled(false);
		}
	}

	//get the worker information for our information panel
	public String getFullName(){
		String firstNameString = (String)myModel.getState("FirstName");
		String lastNameString = (String)myModel.getState("LastName");
		return firstNameString + " " + lastNameString;
	}

	//process the button click
	public void processAction(EventObject e){
		clearErrorMessage();
		if(e.getSource() == cancelButton){
			processCancel();
		}
		if(e.getSource() == searchButton){
			processSearch();
		}
	}

	public void processSearch(){
      setValidationMarkersOff();
		String searchBannerID = bannerIDTextField.getText();
		if(!(searchBannerID.equals(""))){
			processBannerID();
		}
		else{
			Properties mySearchItems = new Properties();
			mySearchItems.setProperty("FirstName", firstNameTextField.getText());
			mySearchItems.setProperty("LastName", lastNameTextField.getText());
			mySearchItems.setProperty("ContactPhone", contactPhoneTextField.getText());
			mySearchItems.setProperty("Email", emailTextField.getText());
			if(includedInactiveCheckBox.isSelected()){
				mySearchItems.setProperty("IncludedInactive", "true");
			}
			else{
				mySearchItems.setProperty("IncludedInactive", "false");
			}
			myModel.stateChangeRequest("Search", mySearchItems);
			String noResults = (String)myModel.getState("TransactionError");
			if(!(noResults.equals(""))){
				clearErrorMessage();
				displayErrorMessage(noResults);
            //markValidationErrors(allErrorMessages);
			}
			else{
				firstNameTextField.setText("");
				lastNameTextField.setText("");
				contactPhoneTextField.setText("");
				emailTextField.setText("");
				includedInactiveCheckBox.setSelected(false);
			}
		}
	}

	public void processBannerID(){
      setValidationMarkersOff();
		Properties allErrorMessages = new Properties();
		allErrorMessages = validateFields();
		String myErrorMessage = allErrorMessages.getProperty("error");
		if(myErrorMessage.equals("")){
			myModel.stateChangeRequest("GetStudentBorrowerByBannerID", bannerIDTextField.getText());
			String noResult = (String)myModel.getState("TransactionError");

			if(!(noResult.equals(""))){
				clearErrorMessage();
				displayErrorMessage(noResult);
            markValidationErrors(allErrorMessages);
			}
			else{
				bannerIDTextField.setText("");
				firstNameTextField.setText("");
				lastNameTextField.setText("");
				contactPhoneTextField.setText("");
				emailTextField.setText("");
				includedInactiveCheckBox.setSelected(false);
			}
		}
		else{
			clearErrorMessage();
			displayErrorMessage("Validation Error");
         markValidationErrors(allErrorMessages);
			//acknowledgeErrorsPopUp("Search Screen", myErrorMessage);
		}
	}

	public Properties validateFields(){
		Properties errorMessages = new Properties();
		Properties myFieldsToValidate = new Properties();
		myFieldsToValidate.setProperty("bannerID", bannerIDTextField.getText());
		
		errorMessages = validateAllFields(myFieldsToValidate);
		
		return errorMessages;
	}

	public void processCancel(){
		myModel.stateChangeRequest("CancelCheckInABook", null);
	}

	public void displayErrorMessage(String message){
		statusLog.displayErrorMessage(message);
	}

	public void clearErrorMessage(){
		statusLog.clearErrorMessage();
	}

	@Override
	protected void processListSelection(EventObject evt){
		//This method is not needed for this view
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 10){
			if (e.getSource() == bannerIDTextField ||
					e.getSource() == firstNameTextField ||
					e.getSource() == lastNameTextField ||
					e.getSource() == contactPhoneTextField ||
					e.getSource() == emailTextField){
				searchButton.doClick();
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