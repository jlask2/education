// tabs=4
// 
//************************************************************
//	COPYRIGHT 2014 Sandeep Mitra and Lior Shahverdi
//    The College at Brockport, State University of New York. -
//	  ALL RIGHTS RESERVED
//
// This file is the product of The College at Brockport and cannot
// be reproduced, copied, or used in any shape or form without
// the express written consent of The College at Brockport.
//************************************************************
//specify the package
package userinterface;

import impresario.IModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.*;
import java.util.Enumeration;
import java.util.EventObject;
import java.util.Calendar;
import java.util.Properties;
import java.util.Vector;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import exception.InvalidPrimaryKeyException;
import model.Rental;
import model.RentalCollection;

public class RentalCheckInCollectionView extends ViewL implements ActionListener, MouseListener{
	protected JTable tableOfRentalsStillCheckedOut;
	protected JTable tableOfRentalsToCheckIn;
	protected JButton action1Button;
	protected JButton action0Button;
	private JLabel barcodeLabel;
	private JTextField barcodeTextField;
	protected MessageView statusLog;
	private Rectangle[] rs;
	private Rectangle[] rt;
	private int aHeight;
	private int aWidthC0;
	private int aVertSp;
	private int aHorzSp;
	private int aMarginLeft;
	private int aMarginBottom;
	private int aMarginRight;
	private int b0h;
	private int b1h;
	private int heightMinusButtons;
	private RentalBooksStillCheckedOutTableModel rentalBooksStillCheckedOutTableModel;
	private RentalBooksToCheckInTableModel rentalBooksToCheckInTableModel;
	private Vector<Vector<String>> rentalsStillCheckedOutVectorString;
	private Vector<Vector<String>> rentalsToCheckInVectorString;
	private boolean shouldPrint;
	private boolean shouldCommit;
	private boolean isAllDone;
	private boolean latePopupFired;
	private boolean hasOverdueRentals;
	public RentalCheckInCollectionView(IModel model) {
		super(model, "RentalCheckInCollectionView");
		latePopupFired = false;
		hasOverdueRentals = false;
		rentalsStillCheckedOutVectorString = new Vector<Vector<String>>();
		rentalsToCheckInVectorString = new Vector<Vector<String>>();
		setPreferredSize(prefSize);
		setLayout(null);
		constructVars();
		getEntryTableModelValuesStillCheckedOut();
		getEntryTableModelValuesToCheckIn();
		createBooksStillCheckedOutTable();
		createBooksToCheckInTable();
		createFields();
		addKeyListenerBarcode();
		myModel.subscribe("TransactionError", this);
		viewTitle = "Rental Collection";
		refreshLogoTitle();
		setGuiState("constructor");
	}
	@Override
	public void onPostSetVisible(boolean isVisible){
		if (isVisible){
			handleLateRentals();
			barcodeTextField.requestFocusInWindow();
		}
	}
	private void setGuiState(String state){
		if (state.equals("constructor")){
			action0Button.setEnabled(false);
			action1Button.setEnabled(true);
			action0Button.setIcon(ImageHolder.ICB_DONE);
			action0Button.setRolloverIcon(ImageHolder.ICB_DONE_HOVER);
			shouldPrint = false;
			shouldCommit = false;
			isAllDone = false;
		} else if (state.equals("update_move")){
			int rco = rentalsStillCheckedOutVectorString.size();
			int rci = rentalsToCheckInVectorString.size();
			if (rci > 0){
				shouldCommit = true;
				if (rco == 0){
					action0Button.setEnabled(true);//no need to print, all books returned
					action1Button.setEnabled(true);
					action0Button.setIcon(ImageHolder.ICB_DONE);
					action0Button.setRolloverIcon(ImageHolder.ICB_DONE_HOVER);
					shouldPrint = false;
					isAllDone = false;
				} else {
					action0Button.setEnabled(true);//need to print, not all books returned
					action1Button.setEnabled(true);
					action0Button.setIcon(ImageHolder.ICB_PRINT);
					action0Button.setRolloverIcon(ImageHolder.ICB_PRINT_HOVER);
					shouldPrint = true;
					isAllDone = false;
				}
			} else {
				action0Button.setEnabled(false);//no need to print, no books returned
				action1Button.setEnabled(true);
				action0Button.setIcon(ImageHolder.ICB_SUBMIT);
				action0Button.setRolloverIcon(ImageHolder.ICB_SUBMIT_HOVER);
				shouldPrint = false;
				shouldCommit = false;
				isAllDone = false;
			}
		} else if (state.equals("disable")){
			action0Button.setEnabled(false);
			action1Button.setEnabled(false);
		} else if (state.equals("commit_done_print_in_prog")){
			action0Button.setEnabled(false);
			action1Button.setEnabled(false);
			action0Button.setIcon(ImageHolder.ICB_DONE);
			action0Button.setRolloverIcon(ImageHolder.ICB_DONE_HOVER);
			isAllDone = true;
			shouldCommit = false;
		} else if (state.equals("commit_print_done")){
			action0Button.setEnabled(true);
			action1Button.setEnabled(false);
			action0Button.setIcon(ImageHolder.ICB_DONE);
			action0Button.setRolloverIcon(ImageHolder.ICB_DONE_HOVER);
			isAllDone = true;
			shouldCommit = false;
			shouldPrint = false;
		}
	}
	private void constructVars(){
		rs = ALayout.generate(this);
		rt = ALayout.generateHdrMain(rs);
		aHeight = Math.max(ImageHolder.ICB_SUBMIT.getIconHeight(), ImageHolder.ICB_CANCEL.getIconHeight());
		aWidthC0 = 125;
		aVertSp = 5;
		aHorzSp = 5;
		aMarginLeft = 5;
		aMarginBottom = 5;
		aMarginRight = 5;
		int h = rt[1].height;
		b0h = aVertSp + aHeight + aVertSp;
		b1h = aMarginBottom + aHeight + aVertSp;
		h -= (b0h + b1h);
		heightMinusButtons = h / 2;
	}
	protected void populateFields(){
		getEntryTableModelValuesStillCheckedOut();
		getEntryTableModelValuesToCheckIn();
		setGuiState("update_move");
		handleLateRentals();
	}
	private void handleLateRentals(){
		if (hasOverdueRentals && !latePopupFired){
			latePopupFired = true;
			lateRentalsPopUp();
		}
	}
	private int lateRentalsPopUp() {
		String PU_title = "Late Rental(s)";
		String PU_message = "Borrower has late rentals";
		Object[] options = { "Ok" };
		return JOptionPane.showOptionDialog(MainFrame.getInstance(),
				PU_message, PU_title, JOptionPane.DEFAULT_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	}
	protected void createBooksStillCheckedOutTable(){
		JPanel entries = new JPanel();
		Font borderFont = new Font("Helvetica", Font.BOLD, 24);
		entries.setBorder(BorderFactory.createTitledBorder(getBorder(), "Books Still Checked Out", 0, 0, borderFont));
		Rectangle er = new Rectangle(rt[1]);
		er.height = heightMinusButtons;//(aHeight + aMarginBottom + aVertSp + aHeight + aVertSp);
		entries.setBounds(er);
		entries.setLayout(new BoxLayout(entries, BoxLayout.Y_AXIS));
		JPanel tablePan = new JPanel();
		rentalBooksStillCheckedOutTableModel = new RentalBooksStillCheckedOutTableModel(rentalsStillCheckedOutVectorString);
		tableOfRentalsStillCheckedOut = new JTable(rentalBooksStillCheckedOutTableModel);
		tableOfRentalsStillCheckedOut.setRowHeight(20);
		TableColumn column;
		for(int i = 0; i < rentalBooksStillCheckedOutTableModel.getColumnCount(); i++){
			column = tableOfRentalsStillCheckedOut.getColumnModel().getColumn(i);
			column.setPreferredWidth(40);
		}
		tablePan.add(tableOfRentalsStillCheckedOut);
		JScrollPane scrollPane = new JScrollPane(tableOfRentalsStillCheckedOut);
		scrollPane.setPreferredSize(new Dimension(entries.getBounds().width - (entries.getInsets().left + entries.getInsets().right), 
				entries.getBounds().height - (entries.getInsets().top + entries.getInsets().bottom)));
		tablePan.add(scrollPane);
		entries.add(tablePan);
		add(entries);
	}
	protected void createBooksToCheckInTable(){
		JPanel entries = new JPanel();
		Font borderFont = new Font("Helvetica", Font.BOLD, 24);
		entries.setBorder(BorderFactory.createTitledBorder(getBorder(), "Books To Check In", 0, 0, borderFont));
		Rectangle er = new Rectangle(rt[1]);
		er.y += heightMinusButtons + b0h;
		er.height = heightMinusButtons;//(aHeight + aMarginBottom + aVertSp + aHeight + aVertSp);
		entries.setBounds(er);
		entries.setLayout(new BoxLayout(entries, BoxLayout.Y_AXIS));
		JPanel tablePan = new JPanel();
		rentalBooksToCheckInTableModel = new RentalBooksToCheckInTableModel(rentalsToCheckInVectorString);
		tableOfRentalsToCheckIn = new JTable(rentalBooksToCheckInTableModel);
		tableOfRentalsToCheckIn.addMouseListener(this);
		tableOfRentalsToCheckIn.setRowHeight(20);
		TableColumn column;
		for(int i = 0; i < rentalBooksToCheckInTableModel.getColumnCount(); i++){
			column = tableOfRentalsToCheckIn.getColumnModel().getColumn(i);
			column.setPreferredWidth(40);
		}
		tablePan.add(tableOfRentalsToCheckIn);
		JScrollPane scrollPane = new JScrollPane(tableOfRentalsToCheckIn);
		scrollPane.setPreferredSize(new Dimension(entries.getBounds().width - (entries.getInsets().left + entries.getInsets().right), 
				entries.getBounds().height - (entries.getInsets().top + entries.getInsets().bottom)));
		tablePan.add(scrollPane);
		entries.add(tablePan);
		add(entries);
	}
	protected void createFields(){
		barcodeTextField = new JTextField();
		barcodeTextField.addActionListener(this);
		add(barcodeTextField);
		barcodeTextField.setBounds((rt[1].x + rt[1].width) - (ImageHolder.ICB_SUBMIT.getIconWidth() + aMarginRight),
				rt[1].y + heightMinusButtons + aVertSp,
				ImageHolder.ICB_CANCEL.getIconWidth(),
				aHeight);

		barcodeLabel = new JLabel("Barcode:");
		int bclw = barcodeLabel.getPreferredSize().width;
		add(barcodeLabel);
		barcodeLabel.setBounds((rt[1].x + rt[1].width) - (ImageHolder.ICB_SUBMIT.getIconWidth() + aMarginRight + aHorzSp + bclw),
				rt[1].y + heightMinusButtons + aVertSp,
				bclw,
				aHeight);
		action0Button = new JButton();
		action0Button.addActionListener(this);
		add(action0Button);
		action0Button.setBounds((rt[1].x + rt[1].width) - (ImageHolder.ICB_SUBMIT.getIconWidth() + aMarginRight),
				(rt[1].y + rt[1].height) - (aMarginBottom + ImageHolder.ICB_SUBMIT.getIconHeight()),
				ImageHolder.ICB_SUBMIT.getIconWidth(),
				ImageHolder.ICB_SUBMIT.getIconHeight());
		action0Button.setBackground(bkColor);
		action0Button.setOpaque(false);
		action0Button.setMargin(new Insets(0, 0, 0, 0));
		action0Button.setBorder(null);
		action0Button.setIcon(ImageHolder.ICB_SUBMIT);
		action0Button.setRolloverIcon(ImageHolder.ICB_SUBMIT_HOVER);
		action1Button = new JButton();
		action1Button.addActionListener(this);
		add(action1Button);
		action1Button.setBounds((rt[1].x + rt[1].width) - (ImageHolder.ICB_CANCEL.getIconWidth() + aHorzSp + ImageHolder.ICB_SUBMIT.getIconWidth() + aMarginRight),
				(rt[1].y + rt[1].height) - (aMarginBottom + ImageHolder.ICB_CANCEL.getIconHeight()),
				ImageHolder.ICB_CANCEL.getIconWidth(),
				ImageHolder.ICB_CANCEL.getIconHeight());
		action1Button.setBackground(bkColor);
		action1Button.setOpaque(false);
		action1Button.setMargin(new Insets(0, 0, 0, 0));
		action1Button.setBorder(null);
		action1Button.setIcon(ImageHolder.ICB_CANCEL);
		action1Button.setRolloverIcon(ImageHolder.ICB_CANCEL_HOVER);
		JPanel slog = createStatusLog("   ");
		slog.setBounds(rt[1].x, 
				(rt[1].y + rt[1].height) - (aHeight + 5 + slog.getInsets().top + slog.getInsets().bottom), 
				rt[1].width, 
				aHeight + 5 + slog.getInsets().top + slog.getInsets().bottom);
		add(slog);
	}
	protected void getEntryTableModelValuesStillCheckedOut(){
		rentalsStillCheckedOutVectorString.removeAllElements();
		Vector<Properties> vecPropsRCo = (Vector<Properties>)myModel.getState("deep_props_vec_rentals_still_checked_out");
		Vector<Properties> vecPropsBCo = (Vector<Properties>)myModel.getState("deep_props_vec_rentals_book_still_checked_out");
		for(int i = 0;i < vecPropsRCo.size();i++){
			Properties propsR = vecPropsRCo.get(i);
			Properties propsB = vecPropsBCo.get(i);
			Vector<String> v = new Vector<String>();//check fields below for null
			v.add(propsR.getProperty("BookID"));
			v.add(propsB.getProperty("Title"));
			v.add(propsR.getProperty("DueDate"));
			v.add(propsR.getProperty("Overdue"));
			if (propsR.getProperty("Overdue").equals("Y")){
				hasOverdueRentals = true;
			}
			rentalsStillCheckedOutVectorString.add(v);
		}
	}
	protected void getEntryTableModelValuesToCheckIn(){
		rentalsToCheckInVectorString.removeAllElements();
		Vector<Properties> vecPropsRCo = (Vector<Properties>)myModel.getState("deep_props_vec_rentals_to_check_in");
		Vector<Properties> vecPropsBCo = (Vector<Properties>)myModel.getState("deep_props_vec_rentals_book_to_check_in");
		for(int i = 0;i < vecPropsRCo.size();i++){
			Properties propsR = vecPropsRCo.get(i);
			Properties propsB = vecPropsBCo.get(i);
			Vector<String> v = new Vector<String>();//check fields below for null
			v.add(propsR.getProperty("BookID"));
			v.add(propsB.getProperty("Title"));
			v.add(propsR.getProperty("DueDate"));
			v.add(propsR.getProperty("Overdue"));
			if (propsR.getProperty("Overdue").equals("Y")){
				hasOverdueRentals = true;
			}
			rentalsToCheckInVectorString.add(v);
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if (isAllDone){
			return;
		}
		if(e.getClickCount() >= 2){
			myModel.stateChangeRequest("rental_move_rental_ci_to_co", tableOfRentalsToCheckIn.getSelectedRow());
			Properties statusProps = (Properties)myModel.getState("rental_move_rental_ci_to_co_status");
			if (statusProps.getProperty("Status").equals("0")){
				populateFields();
				rentalBooksStillCheckedOutTableModel.fireTableDataChanged();
				rentalBooksToCheckInTableModel.fireTableDataChanged();
				clearErrorMessage();
			} else {
				displayErrorMessage(statusProps.getProperty("Message"));
			}
		}
	}
	private void addKeyListenerBarcode(){
		barcodeTextField.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if (isAllDone){
					return;
				}
				myModel.stateChangeRequest("rental_move_rental_co_to_ci", barcodeTextField.getText());
				Properties statusProps = (Properties)myModel.getState("rental_move_rental_co_to_ci_status");
				if (statusProps.getProperty("Status").equals("0")){
					populateFields();
					rentalBooksStillCheckedOutTableModel.fireTableDataChanged();
					rentalBooksToCheckInTableModel.fireTableDataChanged();
					barcodeTextField.setText("");
					clearErrorMessage();
				} else {
					displayErrorMessage(statusProps.getProperty("Message"));
				}
			}
			@Override
			public void keyTyped(KeyEvent e) {
			}});
	}
	protected JPanel createStatusLog(String initialMessage){
		statusLog = new MessageView(initialMessage);
		return statusLog;
	}
	public void displayErrorMessage(String message){
		statusLog.displayErrorMessage(message);
	}
	public void displayMessage(String message){
		statusLog.displayMessage(message);
	}
	public void clearErrorMessage(){
		statusLog.clearErrorMessage();
	}
	@Override
	public void updateState(String key, Object value) {
		// TODO Auto-generated method stub
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
	protected void processAction(EventObject evt) {
		statusLog.clearErrorMessage();
		if(evt.getSource() == action1Button){
			myModel.stateChangeRequest("CancelThisView", null);
		}
		if(evt.getSource() == action0Button){
			if (isAllDone){
				myModel.stateChangeRequest("done", null);
			} else {
				clearErrorMessage();
				if (shouldCommit){
					setGuiState("disable");
					myModel.stateChangeRequest("commit_check_in", null);
					Properties statusProps = (Properties)myModel.getState("commit_check_in_status");
					System.out.println(statusProps.toString());
					if (statusProps.getProperty("Status").equals("0")){
						if (shouldPrint){
							setGuiState("commit_done_print_in_prog");
							myModel.stateChangeRequest("print_receipt", null);
							setGuiState("commit_print_done");
						} else {
							setGuiState("commit_print_done");
						}
						displayMessage("books checked in!");
					} else {
						displayMessage("error checking books in");
					}
				} else {
					displayErrorMessage("unable to commit, selected books for check in");
				}
			}
		}
	}
	@Override
	protected void processListSelection(EventObject evt) {
		// TODO Auto-generated method stub
	}
	public class RentalBooksStillCheckedOutTableModel extends AbstractTableModel implements TableModel{
		private Vector myState;
		public RentalBooksStillCheckedOutTableModel(Vector rentalData){
			myState = rentalData;
		}
		public int getColumnCount(){
			return 4;
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
			else if (columnIndex == 3){
				return "Overdue";
			}
			else{
				return "??";
			}
		}
	}
	public class RentalBooksToCheckInTableModel extends AbstractTableModel implements TableModel{
		private Vector myState;
		public RentalBooksToCheckInTableModel(Vector rentalData){
			myState = rentalData;
		}
		public int getColumnCount(){
			return 4;
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
			else if (columnIndex == 3){
				return "Overdue";
			}
			else{
				return "??";
			}
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}
}

/*

 */