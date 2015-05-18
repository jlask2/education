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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.Properties;
import java.util.EventObject;

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

import model.Book;
import model.Worker;

import java.util.Vector;
import java.util.regex.Pattern;



//project imports
import impresario.IModel;

public class DeleteBookView extends ViewL{
	//GUI Components
	private JButton backButton;
	private JButton cancelButton;
	private JButton submitButton;
	private JLabel barcodeLabel;
	private JTextField barcodeTextField;
	private JLabel bookTitleLabel;
	private JTextField bookTitleTextField;
	private JLabel author1Label;
	private JTextField author1TextField;
	private JLabel author2Label;
	private JTextField author2TextField;
	private JLabel author3Label;
	private JTextField author3TextField;
	private JLabel author4Label;
	private JTextField author4TextField;
	private JLabel publisherLabel;
	private JTextField publisherTextField;
	private JLabel yearOfPublicationLabel;
	private JTextField yearOfPublicationTextField;
	private JLabel isbnLabel;
	private JTextField isbnTextField;
	private JLabel bookConditionLabel;
	private JComboBox bookConditionComboBox;
	private JLabel suggestedPriceLabel;
	private JTextField suggestedPriceTextField;
	private JLabel statusLabel;
	private JComboBox statusComboBox;
	private JLabel notesLabel;
	private JTextArea notesTextArea;
   private JLabel reasonLabel;
	private JComboBox reasonComboBox;
	private JLabel disciplineLabel;
	private JTextField disciplineTextField;

	private MessageView statusLog;

	private Properties bookToDeleteProps;

	private Properties deletedProperties;

	private Properties guiStatus;

	public DeleteBookView(IModel trans){
		super(trans, "DeleteBookView");		
		///
		setPreferredSize(prefSize);
		setBackground(MainFrame.SEL_COLOR);
		setLayout(null);
		///
		guiStatus = new Properties();
		guiStatus.setProperty("Type", "none");
		deletedProperties = new Properties();
		validateVec = new Vector<JLabel>();
		validateVecMessage = new Vector<JLabel>();
		constructVars();
		constructFields();
		populateFields();
		///
		myModel.subscribe("TransactionError", this);
		viewTitle = "    Delete Book";
		refreshLogoTitle();
	}
	@Override
	public void onPostSetVisible(boolean isVisible){
		if (isVisible){
			getRootPane().setDefaultButton(submitButton);
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

		barcodeLabel = new JLabel (" * Barcode:");
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

		createValidate(rt[1].x + aMarginLeft,
				rt[1].y + aMarginTop + aHeight + aVertSp * 2 + aValidMessageHeight,
				aValidDim,
				aValidDim, "title.errorMessage");

		bookTitleLabel = new JLabel (" * Title:");
		add(bookTitleLabel);
		bookTitleLabel.setBounds(rt[1].x + aMarginLeft + aValidDim,
				rt[1].y + aMarginTop + aHeight + aVertSp * 2+ aValidMessageHeight,
				aWidthC0,
				aHeight);

		bookTitleTextField = new JTextField();
		bookTitleTextField.addKeyListener(this);
		add(bookTitleTextField);
		bookTitleTextField.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim,
				rt[1].y + aMarginTop + aHeight + aVertSp * 2 + aValidMessageHeight,
				aWidthC1,
				aHeight);

		createValidateMessage(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim,
				rt[1].y + aMarginTop + aHeight * 2 + aVertSp * 3 + aValidMessageHeight,
				aWidthC2,
				aValidMessageHeight, "title.errorMessage");

		createValidate(rt[1].x + aMarginLeft,
				rt[1].y + aMarginTop + aHeight * 2 + aVertSp * 4 + aValidMessageHeight * 2,
				aValidDim,
				aValidDim, "discipline.errorMessage");

		disciplineLabel = new JLabel(" * Discipline:");
		add(disciplineLabel);
		disciplineLabel.setBounds(rt[1].x + aMarginLeft + aValidDim,
				rt[1].y + aMarginTop + aHeight * 2 + aVertSp * 4 + aValidMessageHeight * 2,
				aWidthC0,
				aHeight);

		disciplineTextField = new JTextField(10);
		disciplineTextField.addKeyListener(this);
		add(disciplineTextField);
		disciplineTextField.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim,
				rt[1].y + aMarginTop + aHeight * 2 + aVertSp * 4 + aValidMessageHeight * 2,
				aWidthC1,
				aHeight);

		createValidateMessage(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim,
				rt[1].y + aMarginTop + aHeight * 3 + aVertSp * 5 + aValidMessageHeight * 2,
				aWidthC2,
				aValidMessageHeight, "discipline.errorMessage");

		createValidate(rt[1].x + aMarginLeft,
				rt[1].y + aMarginTop + aHeight * 3 + aVertSp * 6 + aValidMessageHeight * 3,
				aValidDim,
				aValidDim, "author1.errorMessage");      

		author1Label = new JLabel(" * Author 1:");
		add(author1Label);
		author1Label.setBounds(rt[1].x + aMarginLeft + aValidDim,
				rt[1].y + aMarginTop + aHeight * 3 + aVertSp * 6 + aValidMessageHeight * 3,
				aWidthC0,
				aHeight);

		author1TextField = new JTextField();
		author1TextField.addKeyListener(this);
		add(author1TextField);
		author1TextField.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim,
				rt[1].y + aMarginTop + aHeight * 3 + aVertSp * 6 + aValidMessageHeight * 3,
				aWidthC1,
				aHeight);

		createValidateMessage(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim,
				rt[1].y + aMarginTop + aHeight * 4  + aVertSp * 7 + aValidMessageHeight * 3,
				aWidthC2,
				aValidMessageHeight, "author1.errorMessage");

		createValidate(rt[1].x + aMarginLeft,
				rt[1].y + aMarginTop + aHeight * 4  + aVertSp * 8 + aValidMessageHeight * 4,
				aValidDim,
				aValidDim, "author2.errorMessage");

		author2Label = new JLabel("   Author 2:");
		add(author2Label);
		author2Label.setBounds(rt[1].x + aMarginLeft + aValidDim,
				rt[1].y + aMarginTop + aHeight * 4 + aVertSp * 8 + aValidMessageHeight * 4,
				aWidthC0,
				aHeight);

		author2TextField = new JTextField();
		author2TextField.addKeyListener(this);
		add(author2TextField);
		author2TextField.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim,
				rt[1].y + aMarginTop + aHeight * 4 + aVertSp * 8 + aValidMessageHeight * 4,
				aWidthC1,
				aHeight);

		createValidateMessage(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim,
				rt[1].y + aMarginTop + aHeight * 5  + aVertSp * 9 + aValidMessageHeight * 4,
				aWidthC2,
				aValidMessageHeight, "author2.errorMessage");

		createValidate(rt[1].x + aMarginLeft,
				rt[1].y + aMarginTop + aHeight * 5 + aVertSp * 10 + aValidMessageHeight * 5,
				aValidDim,
				aValidDim, "author3.errorMessage");

		author3Label = new JLabel("   Author 3:");
		add(author3Label);
		author3Label.setBounds(rt[1].x + aMarginLeft + aValidDim,
				rt[1].y + aMarginTop + aHeight * 5 + aVertSp * 10 + aValidMessageHeight * 5,
				aWidthC0,
				aHeight);

		author3TextField = new JTextField();
		author3TextField.addKeyListener(this);
		add(author3TextField);
		author3TextField.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim,
				rt[1].y + aMarginTop + aHeight * 5 + aVertSp * 10 + aValidMessageHeight * 5,
				aWidthC1,
				aHeight);

		createValidateMessage(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim,
				rt[1].y + aMarginTop + aHeight * 6  + aVertSp * 11 + aValidMessageHeight * 5,
				aWidthC2,
				aValidMessageHeight, "author3.errorMessage");

		createValidate(rt[1].x + aMarginLeft,
				rt[1].y + aMarginTop + aHeight * 6 + aVertSp * 12 + aValidMessageHeight * 6,
				aValidDim,
				aValidDim, "author4.errorMessage");

		author4Label = new JLabel("   Author 4:");
		add(author4Label);
		author4Label.setBounds(rt[1].x + aMarginLeft + aValidDim,
				rt[1].y + aMarginTop + aHeight * 6 + aVertSp * 12 + aValidMessageHeight * 6,
				aWidthC0,
				aHeight);

		author4TextField = new JTextField();
		author4TextField.addKeyListener(this);
		add(author4TextField);
		author4TextField.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim,
				rt[1].y + aMarginTop + aHeight * 6 + aVertSp * 12 + aValidMessageHeight * 6,
				aWidthC1,
				aHeight);

		createValidateMessage(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim,
				rt[1].y + aMarginTop + aHeight * 7  + aVertSp * 13 + aValidMessageHeight * 6,
				aWidthC2,
				aValidMessageHeight, "author4.errorMessage");

		createValidate(rt[1].x + aMarginLeft,
				rt[1].y + aMarginTop + aHeight * 7 + aVertSp * 14 + aValidMessageHeight * 7,
				aValidDim,
				aValidDim, "publisher.errorMessage");

		publisherLabel = new JLabel(" * Publisher:");
		add(publisherLabel);
		publisherLabel.setBounds(rt[1].x + aMarginLeft + aValidDim,
				rt[1].y + aMarginTop + aHeight * 7 + aVertSp * 14 + aValidMessageHeight * 7,
				aWidthC0,
				aHeight);

		publisherTextField = new JTextField();
		publisherTextField.addKeyListener(this);
		add(publisherTextField);
		publisherTextField.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim,
				rt[1].y + aMarginTop + aHeight * 7 + aVertSp * 14 + aValidMessageHeight * 7,
				aWidthC1,
				aHeight);

		createValidateMessage(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aValidDim,
				rt[1].y + aMarginTop + aHeight * 8  + aVertSp * 15 + aValidMessageHeight * 7,
				aWidthC2,
				aValidMessageHeight, "publisher.errorMessage");

		createValidate(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aWidthC1 + aHorzSp * 3 + aValidDim * 2,
				rt[1].y + aMarginTop + aHeight * 0 + aVertSp * 0 + aValidMessageHeight * 0,
				aValidDim,
				aValidDim, "publicationYear.errorMessage");

		yearOfPublicationLabel = new JLabel(" * Year of Publication:");
		add(yearOfPublicationLabel);
		yearOfPublicationLabel.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aWidthC1 + aHorzSp * 3 + aValidDim * 3,
				rt[1].y + aMarginTop + aHeight * 0 + aVertSp * 0 + aValidMessageHeight * 0,
				aWidthC0,
				aHeight);

		yearOfPublicationTextField = new JTextField();
		yearOfPublicationTextField.addKeyListener(this);
		add(yearOfPublicationTextField);
		yearOfPublicationTextField.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aWidthC1 + aHorzSp * 3 + aWidthC0 + aHorzSp + aValidDim * 3,
				rt[1].y + aMarginTop + aHeight * 0 + aVertSp * 0 + aValidMessageHeight * 0,
				aWidthC1,
				aHeight);

		createValidateMessage(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aWidthC1 + aHorzSp * 3 + aWidthC0 + aHorzSp + aValidDim * 3,

				rt[1].y + aMarginTop + aHeight * 1  + aVertSp * 1 + aValidMessageHeight * 0,
				aWidthC2,
				aValidMessageHeight, "publicationYear.errorMessage");

		createValidate(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aWidthC1 + aHorzSp * 3 + aValidDim * 2,
				rt[1].y + aMarginTop + aHeight * 1 + aVertSp * 2 + aValidMessageHeight * 1,
				aValidDim,
				aValidDim, "isbn.errorMessage");

		isbnLabel = new JLabel(" * ISBN Number:");
		add(isbnLabel);
		isbnLabel.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aWidthC1 + aHorzSp * 3 + aValidDim * 3,
				rt[1].y + aMarginTop + aHeight * 1 + aVertSp * 2 + aValidMessageHeight * 1,
				aWidthC0,
				aHeight);

		isbnTextField = new JTextField();
		isbnTextField.addKeyListener(this);
		add(isbnTextField);
		isbnTextField.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aWidthC1 + aHorzSp * 3 + aWidthC0 + aHorzSp + aValidDim * 3,
				rt[1].y + aMarginTop + aHeight * 1 + aVertSp * 2 + aValidMessageHeight * 1,
				aWidthC1,
				aHeight);

		createValidateMessage(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aWidthC1 + aHorzSp * 3 + aWidthC0 + aHorzSp + aValidDim * 3,
				rt[1].y + aMarginTop + aHeight * 2  + aVertSp * 3 + aValidMessageHeight * 1,
				aWidthC2,
				aValidMessageHeight, "isbn.errorMessage");

		createValidate(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aWidthC1 + aHorzSp * 3 + aValidDim * 2,
				rt[1].y + aMarginTop + aHeight * 2 + aVertSp * 4 + aValidMessageHeight * 2,
				aValidDim,
				aValidDim, "bookCondition.errorMessage");

		bookConditionLabel = new JLabel(" * Book Condition:");
		add(bookConditionLabel);
		bookConditionLabel.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aWidthC1 + aHorzSp * 3 + aValidDim * 3,
				rt[1].y + aMarginTop + aHeight * 2 + aVertSp *4 + aValidMessageHeight * 2,
				aWidthC0,
				aHeight);

		bookConditionComboBox = new JComboBox();
		add(bookConditionComboBox);
		bookConditionComboBox.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aWidthC1 + aHorzSp * 3 + aWidthC0 + aHorzSp + aValidDim * 3,
				rt[1].y + aMarginTop + aHeight * 2 + aVertSp * 4 + aValidMessageHeight * 2,
				aWidthC1,
				aHeight);

		createValidateMessage(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aWidthC1 + aHorzSp * 3 + aWidthC0 + aHorzSp + aValidDim * 3,
				rt[1].y + aMarginTop + aHeight * 3  + aVertSp * 5 + aValidMessageHeight * 2,
				aWidthC2,
				aValidMessageHeight, "bookCondition.errorMessage");
		////
		createValidate(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aWidthC1 + aHorzSp * 3 + aValidDim * 2,
				rt[1].y + aMarginTop + aHeight * 3 + aVertSp * 6 + aValidMessageHeight * 3,
				aValidDim,
				aValidDim, "suggestedPrice.errorMessage");

		suggestedPriceLabel = new JLabel(" * Suggested Price:");
		add(suggestedPriceLabel);
		suggestedPriceLabel.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aWidthC1 + aHorzSp * 3 + aValidDim * 3,
				rt[1].y + aMarginTop + aHeight * 3 + aVertSp * 6 + aValidMessageHeight * 3,
				aWidthC0,
				aHeight);

		suggestedPriceTextField = new JTextField(10);
		suggestedPriceTextField.addKeyListener(this);
		add(suggestedPriceTextField);
		suggestedPriceTextField.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aWidthC1 + aHorzSp * 3 + aWidthC0 + aHorzSp + aValidDim * 3,
				rt[1].y + aMarginTop + aHeight * 3 + aVertSp * 6 + aValidMessageHeight * 3,
				aWidthC1,
				aHeight);

		createValidateMessage(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aWidthC1 + aHorzSp * 3 + aWidthC0 + aHorzSp + aValidDim * 3,
				rt[1].y + aMarginTop + aHeight * 4  + aVertSp * 7 + aValidMessageHeight * 3,
				aWidthC2,
				aValidMessageHeight, "suggestedPrice.errorMessage");

		createValidate(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aWidthC1 + aHorzSp * 3 + aValidDim * 2,
				rt[1].y + aMarginTop + aHeight * 4 + aVertSp * 8 + aValidMessageHeight * 4,
				aValidDim,
				aValidDim, "status.errorMessage");

		statusLabel = new JLabel(" * Status:");
		add(statusLabel);
		statusLabel.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aWidthC1 + aHorzSp * 3 + aValidDim * 3,
				rt[1].y + aMarginTop + aHeight * 4 + aVertSp * 8 + aValidMessageHeight * 4,
				aWidthC0,
				aHeight);

		statusComboBox = new JComboBox();
		add(statusComboBox);
		statusComboBox.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aWidthC1 + aHorzSp * 3 + aWidthC0 + aHorzSp + aValidDim * 3,
				rt[1].y + aMarginTop + aHeight * 4 + aVertSp * 8 + aValidMessageHeight * 4,
				aWidthC1,
				aHeight);

		createValidateMessage(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aWidthC1 + aHorzSp * 3 + aWidthC0 + aHorzSp + aValidDim * 3,
				rt[1].y + aMarginTop + aHeight * 5  + aVertSp * 9 + aValidMessageHeight * 4,
				aWidthC2,
				aValidMessageHeight, "status.errorMessage");

		createValidate(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aWidthC1 + aHorzSp * 3 + aValidDim * 2,
				rt[1].y + aMarginTop + aHeight * 5 + aVertSp * 10 + aValidMessageHeight * 5,
				aValidDim,
				aValidDim, "notes.errorMessage");

		notesLabel = new JLabel("   Notes:");
		add(notesLabel);
		notesLabel.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aWidthC1 + aHorzSp * 3 + aValidDim * 3,
				rt[1].y + aMarginTop + aHeight * 5 + aVertSp * 10 + aValidMessageHeight * 5,
				aWidthC0,
				aHeight);

		notesTextArea = new JTextArea(5, 36);
		add(notesTextArea);
		notesTextArea.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aWidthC1 + aHorzSp * 3 + aWidthC0 + aHorzSp + aValidDim * 3,
				rt[1].y + aMarginTop + aHeight * 5 + aVertSp * 10 + aValidMessageHeight * 5,
				aWidthC1,
				aHeight * 4);
				
				
		reasonLabel = new JLabel("* Reason: ");
		add(reasonLabel);
		reasonLabel.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aWidthC1 + aHorzSp * 3 + aValidDim * 3,
				rt[1].y + aMarginTop + aHeight * 7 + aVertSp * 14 + aValidMessageHeight * 7,
				aWidthC0,
				aHeight);
		
		reasonComboBox = new JComboBox();
		reasonComboBox.addActionListener(this);
		add(reasonComboBox);
		reasonComboBox.setBounds(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aWidthC1 + aHorzSp * 3 + aWidthC0 + aHorzSp + aValidDim * 3,
				rt[1].y + aMarginTop + aHeight * 7 + aVertSp * 14 + aValidMessageHeight * 7,
				aWidthC1,
				aHeight);
           
      createValidateMessage(rt[1].x + aMarginLeft + aWidthC0 + aHorzSp + aWidthC1 + aHorzSp * 3 + aWidthC0 + aHorzSp + aValidDim * 3,
				rt[1].y + aMarginTop + aHeight * 9  + aVertSp * 11 + aValidMessageHeight * 5,
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

		JPanel slog = createStatusLog("   ");
		slog.setBounds(rt[1].x, 
				(rt[1].y + rt[1].height) - (aHeight + 5 + slog.getInsets().top + slog.getInsets().bottom), 
				rt[1].width, 
				aHeight + 5 + slog.getInsets().top + slog.getInsets().bottom);
		add(slog);

		requiredMessage = new JLabel("* Indicates a required field.");
		requiredMessage.setForeground(Color.black);
		add(requiredMessage);
		requiredMessage.setBounds(rt[1].x + aMarginLeft + aHorzSp + aValidDim, 
				(rt[1].y + rt[1].height) - (aHeight * 2 + 15 + slog.getInsets().top + slog.getInsets().bottom), 
				aWidthC1, 
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
		bookConditionComboBox.addItem("Good");
		bookConditionComboBox.addItem("Fair");
		bookConditionComboBox.addItem("Damaged");

		statusComboBox.addItem("Active");
		statusComboBox.addItem("Inactive");
		statusComboBox.addItem("Lost");
      
      reasonComboBox.addItem("No Reason");
		reasonComboBox.addItem("Cursed");
		reasonComboBox.addItem("Posessed");
		reasonComboBox.addItem("Eaten");
		reasonComboBox.addItem("Shredded"); 
		reasonComboBox.addItem("Burned for heating");

		bookToDeleteProps = (Properties)myModel.getState("deep_props_book_to_delete");

		barcodeTextField.setText(bookToDeleteProps.getProperty("Barcode"));
		bookTitleTextField.setText(bookToDeleteProps.getProperty("Title"));
		author1TextField.setText(bookToDeleteProps.getProperty("Author1"));
		author2TextField.setText(bookToDeleteProps.getProperty("Author2"));
		author3TextField.setText(bookToDeleteProps.getProperty("Author3"));
		author4TextField.setText(bookToDeleteProps.getProperty("Author4"));
		publisherTextField.setText(bookToDeleteProps.getProperty("Publisher"));
		yearOfPublicationTextField.setText(bookToDeleteProps.getProperty("YearOfPublication"));
		isbnTextField.setText(bookToDeleteProps.getProperty("ISBN"));
		bookConditionComboBox.setSelectedItem(bookToDeleteProps.getProperty("BookCondition"));
		suggestedPriceTextField.setText(bookToDeleteProps.getProperty("SuggestedPrice"));
		notesTextArea.setText(bookToDeleteProps.getProperty("Notes"));
		statusComboBox.setSelectedItem(bookToDeleteProps.getProperty("Status"));
		disciplineTextField.setText(bookToDeleteProps.getProperty("Discipline"));

		disciplineTextField.setEditable(false);
		barcodeTextField.setEditable(false);
		bookTitleTextField.setEditable(false);
		author1TextField.setEditable(false);
		author2TextField.setEditable(false);
		author3TextField.setEditable(false);
		author4TextField.setEditable(false);
		publisherTextField.setEditable(false);
		yearOfPublicationTextField.setEditable(false);
		isbnTextField.setEditable(false);
		bookConditionComboBox.setEnabled(false);
		suggestedPriceTextField.setEditable(false);
		notesTextArea.setEditable(true);
		statusComboBox.setEnabled(false);
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
      if (e.getSource() == reasonComboBox){
			appendReason();
		}
	}

	public void processSubmit(){
		getInputInformation();
		String myMessage = "";
		int confirmation = confirmPopUp("Delete Book", 
				"Are you sure you want to delete this book?");
		if(confirmation == 0){
			myModel.stateChangeRequest("Submit", deletedProperties);
			myMessage = (String)myModel.getState("TransactionError");
			acknowledgePopUp("Delete Book", myMessage);
			myModel.stateChangeRequest("CancelThisView", null);
		}
		else{
			myMessage = "Book deletion cancelled.";
		}
	}

	//Create pop up to acknowledge book deletion
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

	//Create pop up to confirm book deletion
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

	public void getInputInformation(){
		deletedProperties.setProperty("Barcode", barcodeTextField.getText());
		deletedProperties.setProperty("Title", bookTitleTextField.getText());
		deletedProperties.setProperty("Discipline", disciplineTextField.getText());
		deletedProperties.setProperty("Author1", author1TextField.getText());
		deletedProperties.setProperty("Author2", author2TextField.getText());
		deletedProperties.setProperty("Author3", author3TextField.getText());
		deletedProperties.setProperty("Author4", author4TextField.getText());
		deletedProperties.setProperty("Publisher", publisherTextField.getText());
		deletedProperties.setProperty("YearOfPublication", yearOfPublicationTextField.getText());
		deletedProperties.setProperty("ISBN", isbnTextField.getText());
		deletedProperties.setProperty("BookCondition", (String)bookConditionComboBox.getSelectedItem());
		deletedProperties.setProperty("SuggestedPrice", suggestedPriceTextField.getText());
		deletedProperties.setProperty("Notes", notesTextArea.getText());
		deletedProperties.setProperty("Status", (String)statusComboBox.getSelectedItem());
		deletedProperties.setProperty("DateOfLastUpdate", getTodaysDate());
	}
   
   public void appendReason() {
		if (!reasonComboBox.getSelectedItem().equals("No Reason"))
		{
			String noteText = notesTextArea.getText(); 
			notesTextArea.setText(noteText + "\nThis book was " + reasonComboBox.getSelectedItem().toString());
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
		//This method is not needed for this view
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == 10){
			if (e.getSource() == barcodeTextField){
				submitButton.doClick();
			} else if (e.getSource() == bookTitleTextField){
				submitButton.doClick();
			} else if (e.getSource() == disciplineTextField){
				submitButton.doClick();
			} else if (e.getSource() == author1TextField){
				submitButton.doClick();
			} else if (e.getSource() == author2TextField){
				submitButton.doClick();
			}else if (e.getSource() == author3TextField){
				submitButton.doClick();
			}else if (e.getSource() == author4TextField){
				submitButton.doClick();
			}else if (e.getSource() == publisherTextField){
				submitButton.doClick();
			}else if (e.getSource() == yearOfPublicationTextField){
				submitButton.doClick();
			}else if (e.getSource() == isbnTextField){
				submitButton.doClick();
			}else if (e.getSource() == suggestedPriceTextField){
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