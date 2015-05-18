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
import java.util.Enumeration;
import java.util.EventObject;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.swing.border.LineBorder;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import java.util.Vector;

//project imports
import exception.InvalidPrimaryKeyException;
import model.Rental;
import model.RentalCollection;
import impresario.IModel;

public class RequestBarcodeView extends ViewL implements ActionListener, MouseListener{
	//GUI Components 
	protected JTable tableOfRentalsToCheckOut;
	private JButton cancelButton;
	private JButton searchButton;
	private JButton doneButton;
	private JButton printButton;
	private RentalBooksToCheckOutTableModel rentalBooksToCheckOutTableModel;
	private Vector<Vector<String>> rentalsToCheckOutVectorString;
	//private Vector<Vector<String>> rentalsAlreadyCheckedOutVectorString;
	private boolean shouldPrint;
	private boolean shouldCommit;
	private boolean isAllDone;
	private int heightMinusButtons = 40;

	private JLabel barcodeLabel;
	private JTextField barcodeTextField;

	private MessageView statusLog;

	public RequestBarcodeView(IModel trans){
		super(trans, "RequestBarcodeView");
		rentalsToCheckOutVectorString = new Vector<Vector<String>>();
		///
		setPreferredSize(prefSize);
		setBackground(MainFrame.SEL_COLOR);
		setLayout(null);
		///
		constructVars();
		getEntryTableModelValuesBooksToCheckOut();
		//getEntryTableModelValuesAlreadyCheckedOut();
		createBooksToCheckOutTable();
		//createBooksAlreadyCheckedOutTable();
		constructFields();
		//populateFields();
		myModel.subscribe("TransactionError", this);
		viewTitle = "    Barcode";
		refreshLogoTitle();
		setGuiState("constructor");
	}
	@Override
	public void onPostSetVisible(boolean isVisible){
		if (isVisible){
			barcodeTextField.requestFocusInWindow();
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

	private void setGuiState(String state){
		if (state.equals("constructor")){
			doneButton.setEnabled(false);
			cancelButton.setEnabled(true);
			searchButton.setEnabled(true);
			printButton.setEnabled(false);
			shouldPrint = false;
			shouldCommit = false;
			isAllDone = false;
		} else if (state.equals("update_move")){
			int rco = rentalsToCheckOutVectorString.size();
			//int rac = rentalsAlreadyCheckedOutVectorString.size();
			if (rco > 0){
				shouldCommit = true;
				if (rco == 0){
					doneButton.setEnabled(false);//no print, no books ready to commit
					doneButton.setVisible(false);          
					printButton.setEnabled(false);
					printButton.setVisible(false);
					cancelButton.setEnabled(true);
					cancelButton.setVisible(true);
					searchButton.setEnabled(true);
					searchButton.setVisible(true);
					shouldPrint = false;
					shouldCommit = false;
					//isAllDone = false;
				} else {
					searchButton.setEnabled(true);//can print, books to check out exist
					searchButton.setVisible(true);
					doneButton.setEnabled(false);
					doneButton.setVisible(false);
					cancelButton.setEnabled(false);
					cancelButton.setVisible(false);
					printButton.setVisible(true);
					printButton.setEnabled(true);
					shouldPrint = true;
					//isAllDone = false;
				}
			} else {
				doneButton.setEnabled(false);//no print, no books checked out
				doneButton.setVisible(false);
				cancelButton.setEnabled(true);
				cancelButton.setVisible(true);
				printButton.setVisible(false);
				printButton.setEnabled(false);
				searchButton.setVisible(true);
				searchButton.setEnabled(true);
				shouldPrint = false;
				shouldCommit = false;
				isAllDone = false;
			}
		} else if (state.equals("disable")){
			doneButton.setEnabled(false);
			printButton.setEnabled(false);
		} else if (state.equals("commit_done_print_in_prog")){
			printButton.setEnabled(false);
			searchButton.setEnabled(false);
			searchButton.setVisible(false);
			doneButton.setVisible(true);
			doneButton.setEnabled(true);
			isAllDone = true;
			shouldCommit = false;
		} else if (state.equals("commit_print_done")){
			doneButton.setEnabled(true);//no need to print, books already checked out
			doneButton.setVisible(true);
			barcodeTextField.setEnabled(false);
			isAllDone = true;
			shouldCommit = false;
			shouldPrint = false;
		}
	}

	private void constructVars(){
		rs = ALayout.generate(this);
		rt = ALayout.generateHdrMain(rs);
	}

	protected void populateFields(){
		getEntryTableModelValuesBooksToCheckOut();
		//getEntryTableModelValuesAlreadyCheckedOut();
		setGuiState("update_move");
	}

	protected void getEntryTableModelValuesBooksToCheckOut(){
		rentalsToCheckOutVectorString.removeAllElements();
		Vector<Properties> vecPropsRCo = (Vector<Properties>)myModel.getState("deep_props_vec_rentals_to_check_out");
		Vector<Properties> vecPropsBCo = (Vector<Properties>)myModel.getState("deep_props_vec_rentals_book_to_check_out");
		for(int i = 0;i < vecPropsRCo.size();i++){
			Properties propsR = vecPropsRCo.get(i);
			Properties propsB = vecPropsBCo.get(i);
			Vector<String> v = new Vector<String>();//check fields below for null
			v.add(propsR.getProperty("BookID"));
			v.add(propsB.getProperty("Title"));
			v.add(propsR.getProperty("DueDate"));
			rentalsToCheckOutVectorString.add(v);
		}
	}

	protected void createBooksToCheckOutTable(){
		JPanel entries = new JPanel();
		Font borderFont = new Font("Helvetica", Font.BOLD, 24);
		entries.setBorder(BorderFactory.createTitledBorder(getBorder(), "Books To Check Out", 0, 0, borderFont));
		Rectangle er = new Rectangle(rt[1]);
		er.height -= heightMinusButtons;//(aHeight + aMarginBottom + aVertSp + aHeight + aVertSp);
		entries.setBounds(er);
		entries.setLayout(new BoxLayout(entries, BoxLayout.Y_AXIS));
		JPanel tablePan = new JPanel();
		rentalBooksToCheckOutTableModel = new RentalBooksToCheckOutTableModel(rentalsToCheckOutVectorString);
		tableOfRentalsToCheckOut = new JTable(rentalBooksToCheckOutTableModel);
		tableOfRentalsToCheckOut.addMouseListener(this);
		tableOfRentalsToCheckOut.setRowHeight(20);
		TableColumn column;
		for(int i = 0; i < rentalBooksToCheckOutTableModel.getColumnCount(); i++){
			column = tableOfRentalsToCheckOut.getColumnModel().getColumn(i);
			column.setPreferredWidth(40);
		}
		tablePan.add(tableOfRentalsToCheckOut);
		JScrollPane scrollPane = new JScrollPane(tableOfRentalsToCheckOut);
		scrollPane.setPreferredSize(new Dimension(entries.getBounds().width, entries.getBounds().height));
		tablePan.add(scrollPane);
		entries.add(tablePan);
		add(entries);
	}

	private void constructFields(){
		int aHeight = 25;
		int aWidthC0 = 125;
		int aWidthC1 = 200;
		int aWidthC2 = 50;
		int aWidthC3 = 75;
		int aHorzSp = 5;
		int aVertSp = 7;
		int aMarginTop = 5;
		int aMarginLeft = 5;
		int aMarginRight = 5;
		int aMarginBottom = 5;

		barcodeLabel = new JLabel ("Barcode:");
		add(barcodeLabel);
		barcodeLabel.setBounds((rt[1].x + rt[1].width) - (ImageHolder.ICB_SUBMIT.getIconWidth() * 2 + aHorzSp * 3 + aMarginRight + aWidthC3 + aWidthC0),
				(rt[1].y + rt[1].height) - (aMarginBottom * 2 + aHeight),
				aWidthC3,
				aHeight);

		barcodeTextField = new JTextField();
		barcodeTextField.addKeyListener(this);
		add(barcodeTextField);
		barcodeTextField.setBounds((rt[1].x + rt[1].width) - (ImageHolder.ICB_DONE.getIconWidth()* 2 + aHorzSp * 2 + aMarginRight + aWidthC0),
				(rt[1].y + rt[1].height) - (aMarginBottom * 2 + aHeight),
				aWidthC0,
				aHeight);

		searchButton = new JButton();
		searchButton.addActionListener(this);
		add(searchButton);
		searchButton.setBounds((rt[1].x + rt[1].width) - (ImageHolder.ICB_PROCESS_BARCODE.getIconWidth() + aMarginRight),
				(rt[1].y + rt[1].height) - (aMarginBottom + ImageHolder.ICB_PROCESS_BARCODE.getIconHeight()),
				ImageHolder.ICB_PROCESS_BARCODE.getIconWidth(),
				ImageHolder.ICB_PROCESS_BARCODE.getIconHeight());
		searchButton.setBackground(bkColor);
		searchButton.setMargin(new Insets(0, 0, 0, 0));
		searchButton.setBorder(null);
		searchButton.setIcon(ImageHolder.ICB_PROCESS_BARCODE);
		searchButton.setRolloverIcon(ImageHolder.ICB_PROCESS_BARCODE_HOVER);
		searchButton.setOpaque(false);
		searchButton.setVisible(true);
		searchButton.setEnabled(true);

		doneButton = new JButton();
		doneButton.addActionListener(this);
		add(doneButton);
		doneButton.setBounds((rt[1].x + rt[1].width) - (ImageHolder.ICB_DONE.getIconWidth() + aMarginRight),
				(rt[1].y + rt[1].height) - (aMarginBottom + ImageHolder.ICB_DONE.getIconHeight()),
				ImageHolder.ICB_DONE.getIconWidth(),
				ImageHolder.ICB_DONE.getIconHeight());
		doneButton.setBackground(bkColor);
		doneButton.setMargin(new Insets(0, 0, 0, 0));
		doneButton.setBorder(null);
		doneButton.setIcon(ImageHolder.ICB_DONE);
		doneButton.setRolloverIcon(ImageHolder.ICB_DONE_HOVER);
		doneButton.setOpaque(false);
		doneButton.setVisible(false);
		doneButton.setEnabled(false);

		printButton = new JButton();
		printButton.addActionListener(this);
		add(printButton);
		printButton.setBounds((rt[1].x + rt[1].width) - (ImageHolder.ICB_DONE.getIconWidth() + aHorzSp + ImageHolder.ICB_DONE.getIconWidth() + aMarginRight),
				(rt[1].y + rt[1].height) - (aMarginBottom + ImageHolder.ICB_DONE.getIconHeight()),
				ImageHolder.ICB_DONE.getIconWidth(),
				ImageHolder.ICB_DONE.getIconHeight());
		printButton.setBackground(bkColor);
		printButton.setMargin(new Insets(0, 0, 0, 0));
		printButton.setBorder(null);
		printButton.setIcon(ImageHolder.ICB_DONE);
		printButton.setRolloverIcon(ImageHolder.ICB_DONE_HOVER);
		printButton.setOpaque(false);
		printButton.setVisible(false);
		printButton.setOpaque(false);
		printButton.setEnabled(false);

		cancelButton = new JButton();
		cancelButton.addActionListener(this);
		add(cancelButton);
		cancelButton.setBounds((rt[1].x + rt[1].width) - (ImageHolder.ICB_CANCEL.getIconWidth() + aHorzSp + ImageHolder.ICB_CANCEL.getIconWidth() + aMarginRight),
				(rt[1].y + rt[1].height) - (aMarginBottom + ImageHolder.ICB_CANCEL.getIconHeight()),
				ImageHolder.ICB_CANCEL.getIconWidth(),
				ImageHolder.ICB_CANCEL.getIconHeight());
		//cancelButton.setBackground(MainFrame.BK_COLOR);
		cancelButton.setBackground(bkColor);
		cancelButton.setMargin(new Insets(0, 0, 0, 0));
		cancelButton.setBorder(null);
		cancelButton.setIcon(ImageHolder.ICB_CANCEL);
		cancelButton.setRolloverIcon(ImageHolder.ICB_CANCEL_HOVER);
		cancelButton.setVisible(true);
		cancelButton.setOpaque(false);
		cancelButton.setEnabled(true);


		JPanel slog = createStatusLog("   ");
		slog.setBounds(rt[1].x, 
				(rt[1].y + rt[1].height) - (aHeight + 5 + slog.getInsets().top + slog.getInsets().bottom), 
				rt[1].width, 
				aHeight + 5 + slog.getInsets().top + slog.getInsets().bottom);
		add(slog);
	}

	@Override
	protected void processAction(EventObject evt) {
		statusLog.clearErrorMessage();
		if(evt.getSource() == cancelButton){
			myModel.stateChangeRequest("CancelThisView", null);
		}
		if(evt.getSource() == doneButton){
			myModel.stateChangeRequest("done", null);
		}
		if(evt.getSource() == printButton){
			clearErrorMessage();
			if (shouldCommit){
				//disable buttons
				setGuiState("disable");
				myModel.stateChangeRequest("commit_check_out", null);//do not need to pass a value, transaction already knows
				Properties statusProps = (Properties)myModel.getState("commit_check_out_status");
				/*DEBUG*///System.out.println(statusProps.toString());
				//if successful then set isalldone
				if (statusProps.getProperty("Status").equals("0")){
					if (shouldPrint){
						setGuiState("commit_done_print_in_prog");
						processPrint();
                  //myModel.stateChangeRequest("PrintReceipt", null);//no need to pass an object
						setGuiState("commit_print_done");
					} else {
						setGuiState("commit_print_done");//sets isAllDone to true
					}
					statusLog.displayMessage("Books checked out!");
				} else {
					displayErrorMessage("error checking books out");
					setGuiState("update_move");
				}
				//get status
				//re-enable buttons as necessary
			} else {
				displayErrorMessage("unable to commit, selected books for check out");
				setGuiState("update_move");
			}
		}
		if(evt.getSource() == searchButton){
			processSearch();
         populateFields();
			rentalBooksToCheckOutTableModel.fireTableDataChanged();
			barcodeTextField.setText("");
		}
	}   

	public void processSearch(){
		String searchBarcode = barcodeTextField.getText();
		if(!(searchBarcode.equals(""))){
			processBarcode();
		}else{
			displayErrorMessage("Please enter a barcode.");
		}
	}

	public void processBarcode(){
		clearErrorMessage();
		myModel.stateChangeRequest("rental_move_rental_to_co", barcodeTextField.getText());
		String noResult = (String)myModel.getState("TransactionError");
		/*DEBUG*///System.out.println("no result: "+noResult);
		if((!(noResult.equals("Barcode not found")))&&
				(!(noResult.equals("This book is already checked out")))&&
				(!(noResult.equals("This is not a valid book to check out")))){
			statusLog.displayMessage(noResult);
		}else if(!(noResult.equals(""))){
			displayErrorMessage(noResult);
		}
	}

	public void processPrint(){
		statusLog.displayMessage("Printing Receipt...");
		myModel.stateChangeRequest("PrintReceipt", null);
	}

	public void processCancel(){
		myModel.stateChangeRequest("CancelThisView", null);
	}

	//create our status log
	private JPanel createStatusLog(String initialMessage){
		statusLog = new MessageView(initialMessage);
		return statusLog;
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
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {//NOTE EACH TABLE SHOULD HAVE ITS ON MOUSE CLICK METHOD, IF BOTH NEED TO TRAP MOUSE EVENTS
		if (isAllDone){
			return;
		}
		if(e.getClickCount() >= 2){
			myModel.stateChangeRequest("rental_remove_rental_from_co", tableOfRentalsToCheckOut.getSelectedRow());
			Properties statusProps = (Properties)myModel.getState("rental_remove_rental_from_co_status");
			if (statusProps.getProperty("Status").equals("0")){
				populateFields();
				rentalBooksToCheckOutTableModel.fireTableDataChanged();
				clearErrorMessage();
			} else {
				displayErrorMessage(statusProps.getProperty("Message"));
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == 10){
			if (e.getSource() == barcodeTextField){
				clearErrorMessage();
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

	public class RentalBooksToCheckOutTableModel extends AbstractTableModel implements TableModel{
		private Vector myState;

		public RentalBooksToCheckOutTableModel(Vector rentalData){
			myState = rentalData;
		}

		public int getColumnCount(){
			return 3;
		}

		public int getRowCount(){
			return myState.size();
		}

		public Object getValueAt(int rowIndex, int columnIndex){
			Vector rental = (Vector)myState.elementAt(rowIndex);
			return "  " + rental.elementAt(columnIndex);
		}

		public String getColumnName(int columnIndex){
			if(columnIndex == 0){
				return "Book Barcode";
			}
			else if(columnIndex == 1){
				return "Book Title";
			}
			else if(columnIndex == 2){
				return "Due Date";
			}
			else{
				return "??";
			}
		}
	}
}