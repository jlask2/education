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
//specify the package//lstestpush
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
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;
import java.util.EventObject;
import java.util.Properties;
import java.util.Vector;

import javax.swing.BorderFactory;
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

import model.Rental;
import model.StudentBorrower;
import model.StudentBorrowerCollection;

public class StudentBorrowerCollectionView extends ViewL implements ActionListener, MouseListener{
	private JLabel titleLabel;
	private JLabel loggedInWorkerLabel;
	private JLabel workerNameLabel;
	protected JTable tableOfStudentBorrowers;
	
	protected JButton backButton;
	
	protected JButton doneButton;
	protected JButton submitButton;
	protected MessageView statusLog;
	protected Vector<Vector<String>> studentBorrowersVector;

	//constructor
	public StudentBorrowerCollectionView(IModel trans){
		super(trans, "StudentBorrowerCollectionView");

		setBackground(bkColor);
		///
		setPreferredSize(prefSize);
		setLayout(null);
		

		studentBorrowersVector = new Vector<Vector<String>>();

		constructVars();
		createCenterPanel();
		populateFields();

		myModel.subscribe("TransactionError", this);
		viewTitle = "    Borrowers";
		refreshLogoTitle();
	}
	private Rectangle[] rs;
	private Rectangle[] rt;
	private void constructVars(){
		rs = ALayout.generate(this);
		rt = ALayout.generateHdrMain(rs);
	}
	protected void createCenterPanel(){
		int aHeight = Math.max(ImageHolder.ICB_SUBMIT.getIconHeight(), ImageHolder.ICB_CANCEL.getIconHeight());
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
		TableModel myData = new StudentBorrowerTableModel(studentBorrowersVector);
		tableOfStudentBorrowers = new JTable(myData);
		tableOfStudentBorrowers.addMouseListener(this);
		tableOfStudentBorrowers.setRowHeight(20);
		TableColumn column;
		for(int i = 0; i < myData.getColumnCount(); i++){
			column = tableOfStudentBorrowers.getColumnModel().getColumn(i);
			column.setPreferredWidth(40);
		}
		tablePan.add(tableOfStudentBorrowers);
		JScrollPane scrollPane = new JScrollPane(tableOfStudentBorrowers);
		scrollPane.setPreferredSize(new Dimension(entries.getBounds().width - (entries.getInsets().left + entries.getInsets().right), 
				entries.getBounds().height - (entries.getInsets().top + entries.getInsets().bottom)));
		tablePan.add(scrollPane);
		entries.add(tablePan);
		add(entries);
		submitButton = new JButton();
		submitButton.addActionListener(this);
		add(submitButton);
		submitButton.setBounds((rt[1].x + rt[1].width) - (ImageHolder.ICB_SUBMIT.getIconWidth() + aMarginRight),
				(rt[1].y + rt[1].height) - (aMarginBottom + ImageHolder.ICB_SUBMIT.getIconHeight()),
				ImageHolder.ICB_SUBMIT.getIconWidth(),
				ImageHolder.ICB_SUBMIT.getIconHeight());
      submitButton.setBackground(bkColor);
		submitButton.setMargin(new Insets(0, 0, 0, 0));
		submitButton.setBorder(null);
		submitButton.setOpaque(false);
		submitButton.setIcon(ImageHolder.ICB_SUBMIT);
		submitButton.setRolloverIcon(ImageHolder.ICB_SUBMIT_HOVER);
		
		doneButton = new JButton();
		doneButton.addActionListener(this);
		add(doneButton);
		doneButton.setBounds((rt[1].x + rt[1].width) - (ImageHolder.ICB_CANCEL.getIconWidth() + aHorzSp + ImageHolder.ICB_SUBMIT.getIconWidth() + aMarginRight),
				(rt[1].y + rt[1].height) - (aMarginBottom + ImageHolder.ICB_CANCEL.getIconHeight()),
				ImageHolder.ICB_CANCEL.getIconWidth(),
				ImageHolder.ICB_CANCEL.getIconHeight());
      doneButton.setBackground(bkColor);
		doneButton.setOpaque(false);
		doneButton.setMargin(new Insets(0, 0, 0, 0));
		doneButton.setBorder(null);
		doneButton.setIcon(ImageHolder.ICB_CANCEL);
		doneButton.setRolloverIcon(ImageHolder.ICB_CANCEL_HOVER);
		
		
		////
		backButton= new JButton();
		backButton.addActionListener(this);
		add(backButton);
		backButton.setBounds((rt[1].x + rt[1].width) - (ImageHolder.ICB_BACK.getIconWidth() + aHorzSp +ImageHolder.ICB_CANCEL.getIconWidth() + aHorzSp + ImageHolder.ICB_SUBMIT.getIconWidth() + aMarginRight),
				(rt[1].y + rt[1].height) - (aMarginBottom + ImageHolder.ICB_BACK.getIconHeight()),
				ImageHolder.ICB_BACK.getIconWidth(),
				ImageHolder.ICB_BACK.getIconHeight());
		backButton.setBackground(bkColor);
		backButton.setOpaque(false);
		backButton.setMargin(new Insets(0, 0, 0, 0));
		backButton.setBorder(null);
		backButton.setIcon(ImageHolder.ICB_BACK);
		backButton.setRolloverIcon(ImageHolder.ICB_BACK_HOVER);
		///
		
		JPanel slog = createStatusLog("   ");
		slog.setBounds(rt[1].x, 
				(rt[1].y + rt[1].height) - (aHeight + 5 + slog.getInsets().top + slog.getInsets().bottom), 
				rt[1].width, 
				aHeight + 5 + slog.getInsets().top + slog.getInsets().bottom);
		add(slog);
	}
	protected void populateFields(){
		//call the method to populate the table 
		getEntryTableModelValues();
	}

	protected void getEntryTableModelValues(){
		//make sure our initial vector is empty
		studentBorrowersVector.removeAllElements();
		//try to get the values to go in the table
		Vector<Properties> vecPropsSB = (Vector<Properties>)myModel.getState("deep_props_vec_student_borrower_list");
		Enumeration<Properties> entriesProps = vecPropsSB.elements();
		while(entriesProps.hasMoreElements()){
			Properties propsSB = entriesProps.nextElement();
			Vector<String> v = new Vector<String>();
			v.addElement(propsSB.getProperty("BannerID"));
			v.addElement(propsSB.getProperty("FirstName"));
			v.addElement(propsSB.getProperty("LastName"));
			v.addElement(propsSB.getProperty("ContactPhone"));
			v.addElement(propsSB.getProperty("Email"));
			v.addElement(propsSB.getProperty("Status"));
			studentBorrowersVector.add(v);
		}
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
		if (evt.getSource() == backButton){
			myModel.stateChangeRequest("CancelThisView", null);
		}
		if(evt.getSource() == doneButton){
			myModel.stateChangeRequest("CancelTransaction", null);
		}
		if(evt.getSource() == submitButton){
			if (myModel.getState("TransactionType").equals("CheckInABookTransaction")){

				processRentalsForStudentBorrowerSelected();
			}

			else processStudentBorrowerSelected();
		}
	}

	protected void processStudentBorrowerSelected(){
		int selectedIndex = tableOfStudentBorrowers.getSelectedRow();
		if(selectedIndex >= 0){
			//Vector selectedWorker = (Vector)workersVector.elementAt(selectedIndex);

			//DEBUG System.out.println(selectedWorker.elementAt(0));

			//myModel.stateChangeRequest("WorkerSelected", selectedWorker.elementAt(0));

			//System.out.println("Our transtype is -->"+ myModel.getState("TransactionType"));

			myModel.stateChangeRequest("StudentBorrowerSelected", selectedIndex);
			String noResult = (String)myModel.getState("TransactionError");

			if(!(noResult.equals(""))){
				displayErrorMessage(noResult);
			}else{
				displayErrorMessage("");
			}
		}
	}

	protected void processRentalsForStudentBorrowerSelected(){
		int selectedIndex = tableOfStudentBorrowers.getSelectedRow();
		if (selectedIndex >= 0){
			myModel.stateChangeRequest("StudentBorrowerSelected", selectedIndex);
			String myMessage = (String)myModel.getState("TransactionError");
			statusLog.displayErrorMessage(myMessage);
		}
	}

	protected JPanel createStatusLog(String initialMessage){
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

	public void displayErrorMessage(String message){
		statusLog.displayErrorMessage(message);
	}

	public void clearErrorMessage(){
		statusLog.clearErrorMessage();
	}

	protected void processListSelection(EventObject evt) {
		//TODO
	}

	public void mouseClicked(MouseEvent click) {
		if(click.getClickCount() >= 2){
			clearErrorMessage();
			processStudentBorrowerSelected();
		}

	}

	//These are not used
	public void mousePressed(MouseEvent click){}
	public void mouseExited(MouseEvent click){}
	public void mouseEntered(MouseEvent click){}
	public void mouseReleased(MouseEvent click){}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
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