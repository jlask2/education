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
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Properties;
import java.util.EventObject;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import java.util.Vector;



//project imports
import impresario.IModel;

public class BookSearchView extends ViewL{
	//GUI Components
	private JButton cancelButton;
	private JButton searchButton;

	private JLabel barcodeLabel;
	private JTextField barcodeTextField;
	private JLabel bookTitleLabel;
	private JTextField bookTitleTextField;
	private JLabel authorLabel;
	private JTextField authorTextField;
	private JLabel disciplineLabel;
	private JComboBox disciplineComboBox;

	private JCheckBox includedInactiveCheckBox;

	private MessageView statusLog;

	public BookSearchView(IModel trans){
		super(trans, "BookSearchView");
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
		viewTitle = "    Book Search";
		refreshLogoTitle();
	}
	@Override
	public void onPostSetVisible(boolean isVisible){
		if (isVisible){
			barcodeTextField.requestFocusInWindow();
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
				aValidDim, "barcode.errorMessage");

		barcodeLabel = new JLabel ("Barcode:");
		add(barcodeLabel);
		barcodeLabel.setBounds(rt[1].x + aMarginLeft + aValidDim,
				rt[1].y + aMarginTop,
				aWidthC0,
				aHeight);

		barcodeTextField = new JTextField();
		barcodeTextField.addKeyListener(this);
		add(barcodeTextField);
		barcodeTextField.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim,
				rt[1].y + aMarginTop,
				aWidthC1,
				aHeight);
      
      createValidateMessage(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim,
				rt[1].y + aMarginTop + aHeight + aVertSp,
				aWidthC2,
				aValidMessageHeight, "barcode.errorMessage");
     
		JLabel OrLabel = new JLabel ("OR:");
		add(OrLabel);
		OrLabel.setBounds(rt[1].x + aMarginLeft + aValidDim ,
				rt[1].y + aMarginTop + aHeight + + aVertSp + aValidMessageHeight,
				aWidthC0,
				aHeight);

      createValidate(rt[1].x + aMarginLeft,
				rt[1].y + aMarginTop + aHeight * 2 + aVertSp * 2 + aValidMessageHeight * 2,
				aValidDim,
				aValidDim, "title.errorMessage");
      
		bookTitleLabel = new JLabel ("Book Title:");
		add(bookTitleLabel);
		bookTitleLabel.setBounds(rt[1].x + aMarginLeft + aValidDim,
				rt[1].y + aMarginTop + aHeight * 2 + aVertSp * 2 + aValidMessageHeight * 2,
				aWidthC0,
				aHeight);

		bookTitleTextField = new JTextField();
		bookTitleTextField.addKeyListener(this);
		add(bookTitleTextField);
		bookTitleTextField.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim,
				rt[1].y + aMarginTop + aHeight * 2 + aVertSp * 2 + aValidMessageHeight * 2,
				aWidthC1,
				aHeight);
      
      createValidateMessage(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim,
				rt[1].y + aMarginTop + aHeight * 3 + aVertSp * 3 + aValidMessageHeight * 2,
				aWidthC2,
				aValidMessageHeight, "title.errorMessage");
      
      createValidate(rt[1].x + aMarginLeft,
				rt[1].y + aMarginTop + aHeight * 3 + aVertSp * 4 + aValidMessageHeight * 3,
				aValidDim,
				aValidDim, "author1.errorMessage");
      
		authorLabel = new JLabel("Author:");
		add(authorLabel);
		authorLabel.setBounds(rt[1].x + aMarginLeft + aValidDim,
				rt[1].y + aMarginTop + aHeight * 3 + aVertSp * 4 + aValidMessageHeight * 3,
				aWidthC0,
				aHeight);

		authorTextField = new JTextField();
		authorTextField.addKeyListener(this);
		add(authorTextField);
		authorTextField.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim,
				rt[1].y + aMarginTop + aHeight * 3 + aVertSp * 4 + aValidMessageHeight * 3,
				aWidthC1,
				aHeight);

      createValidateMessage(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim,
				rt[1].y + aMarginTop + aHeight * 4 + aVertSp * 5 + aValidMessageHeight * 3,
				aWidthC2,
				aValidMessageHeight, "author1.errorMessage");
            
      createValidate(rt[1].x + aMarginLeft,
				rt[1].y + aMarginTop + aHeight * 4 + aVertSp * 6 + aValidMessageHeight * 4,
				aValidDim,
				aValidDim, "discipline.errorMessage");
      
		disciplineLabel = new JLabel("Discipline:");
		add(disciplineLabel);
		disciplineLabel.setBounds(rt[1].x + aMarginLeft + aValidDim,
				rt[1].y + aMarginTop + aHeight * 4 + aVertSp * 6 + aValidMessageHeight * 4,
				aWidthC0,
				aHeight);

		disciplineComboBox = new JComboBox();
		add(disciplineComboBox);
		disciplineComboBox.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim,
				rt[1].y + aMarginTop + aHeight * 4 + aVertSp * 6 + aValidMessageHeight * 4,
				aWidthC1,
				aHeight);

      createValidateMessage(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim,
				rt[1].y + aMarginTop + aHeight * 5  + aVertSp * 7 + aValidMessageHeight * 4,
				aWidthC2,
				aValidMessageHeight, "discipline.errorMessage");

		includedInactiveCheckBox = new JCheckBox("Include Inactive Books");
		includedInactiveCheckBox.setOpaque(false);
		add(includedInactiveCheckBox);
		includedInactiveCheckBox.setBounds(rt[1].x + aMarginLeft + aValidDim,
				rt[1].y + aMarginTop + aHeight * 5  + aVertSp * 8 + aValidMessageHeight * 5,
				includedInactiveCheckBox.getPreferredSize().width,
				aHeight);

		searchButton = new JButton();
		searchButton.addActionListener(this);
		add(searchButton);
		searchButton.setBounds((rt[1].x + rt[1].width) - (ImageHolder.ICB_SEARCH.getIconWidth() + aMarginRight),
				(rt[1].y + rt[1].height) - (aMarginBottom + ImageHolder.ICB_SEARCH.getIconHeight()),
				ImageHolder.ICB_SEARCH.getIconWidth(),
				ImageHolder.ICB_SEARCH.getIconHeight());
		//searchButton.setBackground(MainFrame.BK_COLOR);
		searchButton.setBackground(bkColor);
      searchButton.setMargin(new Insets(0, 0, 0, 0));
		searchButton.setBorder(null);
		searchButton.setIcon(ImageHolder.ICB_SEARCH);
      searchButton.setRolloverIcon(ImageHolder.ICB_SEARCH_HOVER);
      searchButton.setOpaque(false);
		
		cancelButton = new JButton();
		cancelButton.addActionListener(this);
		add(cancelButton);
		cancelButton.setBounds((rt[1].x + rt[1].width) - (ImageHolder.ICB_SEARCH.getIconWidth() + aHorzSp + ImageHolder.ICB_CANCEL.getIconWidth() + aMarginRight),
				(rt[1].y + rt[1].height) - (aMarginBottom + ImageHolder.ICB_CANCEL.getIconHeight()),
				ImageHolder.ICB_CANCEL.getIconWidth(),
				ImageHolder.ICB_CANCEL.getIconHeight());
		//cancelButton.setBackground(MainFrame.BK_COLOR);
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
	}

	@Override
	public void updateState(String key, Object value){
		if (key.equals("TransactionError") == true){
			displayErrorMessage((String)value);
		}
	}

	//create our status log
	private JPanel createStatusLog(String initialMessage){
		statusLog = new MessageView(initialMessage);
		return statusLog;
	}

	//populate fields
	public void populateFields(){
		myModel.stateChangeRequest("GetMyDisciplines", null);
		ArrayList<String> myList = (ArrayList<String>) myModel.getState("GetDisciplines");
		disciplineComboBox.addItem("");
		for(int i = 0; i < myList.size(); i++){
			disciplineComboBox.addItem(myList.get(i));
		}
		if(((String)myModel.getState("TransactionType")).equals("DeleteBook")){
			includedInactiveCheckBox.setEnabled(false);
         includedInactiveCheckBox.setVisible(false);
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
		String searchBarcode = barcodeTextField.getText();
		if(!(searchBarcode.equals(""))){
			processBarcode();
		}
		else{
			Properties mySearchItems = new Properties();
			mySearchItems.setProperty("Title", bookTitleTextField.getText());
			mySearchItems.setProperty("Author", authorTextField.getText());
			mySearchItems.setProperty("Discipline", (String) disciplineComboBox.getSelectedItem());
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
				bookTitleTextField.setText("");
				authorTextField.setText("");
				disciplineComboBox.setSelectedIndex(0);
				includedInactiveCheckBox.setSelected(false);
			}
		}
	}

	public void processBarcode(){
      setValidationMarkersOff();
		Properties allErrorMessages = new Properties();
		allErrorMessages = validateFields();
		String myErrorMessage = allErrorMessages.getProperty("error");
		if(myErrorMessage.equals("")){
			myModel.stateChangeRequest("GetBookByBarcode", barcodeTextField.getText());
			String noResult = (String)myModel.getState("TransactionError");

			if(!(noResult.equals(""))){
				displayErrorMessage(noResult);
			}
			else{
				barcodeTextField.setText("");
				bookTitleTextField.setText("");
				authorTextField.setText("");
				disciplineComboBox.setSelectedIndex(0);
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
		myFieldsToValidate.setProperty("barcode", barcodeTextField.getText());
		
		errorMessages = validateAllFields(myFieldsToValidate);
		
		return errorMessages;
	}

	public void processCancel(){
		myModel.stateChangeRequest("CancelModifyBook", null);
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
		// TODO Auto-generated method stub
		if (e.getKeyCode() == 10){
         if (e.getSource() == barcodeTextField){
            searchButton.doClick();
         } else if (e.getSource() == bookTitleTextField){
            searchButton.doClick();
         } else if (e.getSource() == authorTextField){
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