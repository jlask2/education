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

import impresario.IModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;
import java.util.EventObject;
import java.util.Properties;
import java.util.Vector;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import model.Book;
import model.BookCollection;
import model.CSVHandler;

public class AvailableBooksView extends ViewL implements ActionListener, MouseListener{
	protected JTable tableOfBooks;
	protected JButton doneButton;
	protected JButton exportButton;
	protected MessageView statusLog;
	protected Vector<Vector<String>> booksVector;

	//constructor
	public AvailableBooksView(IModel trans){
		super(trans, "AvailableBooksView");
		setBackground(bkColor);
		setBackground(MainFrame.SEL_COLOR);
		setPreferredSize(prefSize);
		setLayout(null);
		booksVector = new Vector<Vector<String>>();
		constructVars();
		createCenterPanel();

		populateFields();

		myModel.subscribe("TransactionError", this);
		viewTitle = "    Available Book(s)";
		refreshLogoTitle();
	}
	@Override
	public void onPostSetVisible(boolean isVisible){
		if (isVisible){
			getRootPane().setDefaultButton(doneButton);
		}
	}   
	protected void populateFields(){
		//call the method to populate the table 
		getEntryTableModelValues();
	}

	protected void getEntryTableModelValues(){		
		//make sure our initial vector is empty
		booksVector.removeAllElements();
		//try to get the values to go in the table
		Vector<Properties> vecPropsB = (Vector<Properties>)myModel.getState("deep_props_vec_book_list");
		Enumeration<Properties> entriesProps = vecPropsB.elements();
		while(entriesProps.hasMoreElements()){
			Properties propsB = entriesProps.nextElement();
			Vector<String> v = new Vector<String>();
			v.addElement(propsB.getProperty("Barcode"));
			v.addElement(propsB.getProperty("Title"));
			v.addElement(propsB.getProperty("Author1"));
			v.addElement(propsB.getProperty("Discipline"));
			v.addElement(propsB.getProperty("Status"));
			booksVector.add(v);
		}
	}

	private Rectangle[] rs;
	private Rectangle[] rt;
	private void constructVars(){
		rs = ALayout.generate(this);
		rt = ALayout.generateHdrMain(rs);
	}

	protected void createCenterPanel(){
		int aHeight = Math.max(ImageHolder.ICB_DONE.getIconHeight(), ImageHolder.ICB_EXPORT.getIconHeight());
		int aWidthC0 = 125;
		int aVertSp = 5;
		int aHorzSp = 5;
		int aMarginLeft = 5;
		int aMarginBottom = 5;
		int aMarginRight = 5;
		JPanel entries = new JPanel();
		Rectangle er = new Rectangle(rt[1]);
		er.height -= (aHeight + aMarginBottom + aVertSp);
		entries.setBounds(er);
		entries.setLayout(new BoxLayout(entries, BoxLayout.Y_AXIS));
		JPanel tablePan = new JPanel();
		TableModel myData = new BookTableModel(booksVector);
		tableOfBooks = new JTable(myData);
		tableOfBooks.addMouseListener(this);
		tableOfBooks.setRowHeight(20);
		TableColumn column;
		for(int i = 0; i < myData.getColumnCount(); i++){
			column = tableOfBooks.getColumnModel().getColumn(i);
			column.setPreferredWidth(40);
		}
		tablePan.add(tableOfBooks);
		JScrollPane scrollPane = new JScrollPane(tableOfBooks);
		scrollPane.setPreferredSize(new Dimension(entries.getBounds().width, entries.getBounds().height));
		tablePan.add(scrollPane);
		entries.add(tablePan);
		add(entries);

		doneButton = new JButton();
		doneButton.addActionListener(this);
		add(doneButton);
		doneButton.setBounds((rt[1].x + rt[1].width) - (ImageHolder.ICB_DONE.getIconWidth() + aMarginRight),
				(rt[1].y + rt[1].height) - (aMarginBottom + ImageHolder.ICB_DONE.getIconHeight()),
				ImageHolder.ICB_DONE.getIconWidth(),
				ImageHolder.ICB_DONE.getIconHeight());
		doneButton.setBackground(MainFrame.BK_COLOR);
		doneButton.setMargin(new Insets(0, 0, 0, 0));
		doneButton.setBorder(null);
		doneButton.setIcon(ImageHolder.ICB_DONE);
		doneButton.setRolloverIcon(ImageHolder.ICB_DONE_HOVER);
		doneButton.setOpaque(false);
		
		exportButton = new JButton();
		exportButton.addActionListener(this);
		add(exportButton);
		exportButton.setBounds((rt[1].x + rt[1].width) - (ImageHolder.ICB_DONE.getIconWidth() + aHorzSp + ImageHolder.ICB_EXPORT.getIconWidth() + aMarginRight),
				(rt[1].y + rt[1].height) - (aMarginBottom + ImageHolder.ICB_EXPORT.getIconHeight()),
				ImageHolder.ICB_EXPORT.getIconWidth(),
				ImageHolder.ICB_EXPORT.getIconHeight());
		exportButton.setBackground(MainFrame.BK_COLOR);
		exportButton.setMargin(new Insets(0, 0, 0, 0));
		exportButton.setBorder(null);
		exportButton.setIcon(ImageHolder.ICB_EXPORT);
		exportButton.setRolloverIcon(ImageHolder.ICB_EXPORT_HOVER);
		exportButton.setOpaque(false);

		JPanel slog = createStatusLog("   ");
		slog.setBounds(rt[1].x, 
				(rt[1].y + rt[1].height) - (aHeight + 5 + slog.getInsets().top + slog.getInsets().bottom), 
				rt[1].width, 
				aHeight + 5 + slog.getInsets().top + slog.getInsets().bottom);
		add(slog);
	}

	public void updateState(String key, Object value){
		//this method is not needed at this time
	}

	//get the worker information for our information panel
	public String getFullName(){
		String firstNameString = (String)myModel.getState("FirstName");
		String lastNameString = (String)myModel.getState("LastName");
		return firstNameString + " " + lastNameString;
	}

	protected void processAction(EventObject evt){
		statusLog.clearErrorMessage();
		if(evt.getSource() == doneButton){
			processCancel();
		} else if(evt.getSource() == exportButton){
			myModel.stateChangeRequest("ExportReport", null);
			Properties exportStatusProps = (Properties)myModel.getState("ExportStatus");
			if (exportStatusProps.getProperty("Status").equals("0")){
				displayMessage(exportStatusProps.getProperty("Message"));
			} else {
				displayErrorMessage(exportStatusProps.getProperty("Message"));
			}
		}
	}

	private void processCancel(){
		myModel.stateChangeRequest("CancelBooks", null);
	}

	protected JPanel createStatusLog(String initialMessage){
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

	protected void processListSelection(EventObject evt) {
		//TODO
	}

	//These are not used
	public void mouseClicked(MouseEvent click){}
	public void mousePressed(MouseEvent click){}
	public void mouseExited(MouseEvent click){}
	public void mouseEntered(MouseEvent click){}
	public void mouseReleased(MouseEvent click){}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

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
}