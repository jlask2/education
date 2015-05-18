// tabs=4
// 
//************************************************************
//	COPYRIGHT 2014 Sandeep Mitra, Stephanie Cruz, and Sarah Periard
//    The College at Brockport, State University of New York. -
//	  ALL RIGHTS RESERVED
//
// This file is the product of The College at Brockport and cannot
// be reproduced, copied, or used in any shape or form without
// the express written consent of The College at Brockport.
//************************************************************
// specify the package
package userinterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.util.Properties;
import java.util.EventObject;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import java.util.Vector;

import impresario.IModel;

public class TransactionChoiceView extends ViewL{
	private JButton logOutButton;
	private JButton addBookButton;
	private JButton modifyBookButton;
	private JButton deleteBookButton;
	private JButton addStudentBorrowerButton;
	private JButton modifyStudentBorrowerButton;
	private JButton deleteStudentBorrowerButton;
	private JButton addWorkerButton;
	private JButton modifyWorkerButton;
	private JButton deleteWorkerButton;
	private JButton allAvailableBooksButton;
	private JButton checkInButton;
	private JButton checkOutButton;
	private JButton borrowersWithCheckedOutButton;
	private JButton processLostButton;
	private JButton delinquencyCheckButton;
	private JButton allCheckedOutButton;
	private JButton changePasswordButton;//
	private MessageView statusLog;
	private JButton[] menuButtons;
	private static String[] MENU_NAMES = {"Rentals", "Books", "Borrowers", "Workers", "Reports"};
	private static String[] MENU_TITLES = {"Rental Options", "Book Options", "Student Borrower Options", "Worker Options", "Reports"};
	private int bMarginLeft;
	private int bMarginRight;
	private int bMarginTop;
	private int bVertSp;
	private int bVertSpSz;
	private int bVertUsbl;
	private int bHeight;
	private int bWidth;
	private Insets viewInsets;
	private Rectangle[] rs;
	private Rectangle[] rt;
	private JPanel[] menuPanels;
	private int activeMenu;
	private int mpWidth;
	private int mpHeight;
	private int mpMarginLeft;
	private int mpMarginRight;
	private int mpMarginTop;
	private int mpMarginBottom;
	private int mpVertSp;
	private int mpHorzSp;
	private int infoBHeight;
	public TransactionChoiceView(IModel librarian)	{
		super(librarian, "TransactionChoiceView");
		viewTitle = "";
		logoTitleBar = "tcv";
		setPreferredSize(prefSize);
		setLayout(null);
		this.setOpaque(false);
		constructVars();
		constructMenuButtons();
		constructMenuPanels();
		constructInfo();
		populateFields();
		myModel.subscribe("TransactionError", this);
		repaint();
	}
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		if (ImageHolder.BK_IMG_0 != null){
			g2d.drawImage(ImageHolder.BK_IMG_0, 0, 0, null);
		}
	}
	private void constructVars(){
		bWidth = ImageHolder.ICS[0].getIconWidth();
		bHeight = ImageHolder.ICS[0].getIconHeight();
		mpWidth = 135;
		mpHeight = 35;
		infoBHeight = 30;
		mpMarginLeft = 50;
		mpMarginRight = 5;
		mpMarginTop = 50;
		mpMarginBottom = 5;
		mpVertSp = 20;
		mpHorzSp = 20;
		activeMenu = 0;
		viewInsets = getInsets();
		rs = ALayout.generate(this);
		rt = ALayout.generateHdrBtnMenu(rs, MENU_NAMES.length, bWidth, bHeight);
	}
	public class JPInfo extends JPanel{
		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D)g;
			if (ImageHolder.JPI_BK_IMG_0 != null){
				g2d.drawImage(ImageHolder.JPI_BK_IMG_0, 0, 0, null);
			}
		}
	}
	private void constructInfo(){
		JPanel jpi = new JPInfo();
		jpi.setLayout(null);
		jpi.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), getFullName()));
		jpi.setBounds(rt[3]);
		jpi.setBackground(Color.white);
		logOutButton = new JButton();
		logOutButton.addActionListener(this);
		logOutButton.setBounds(jpi.getInsets().left + 3,
				rt[3].height - (jpi.getInsets().bottom + 3 + mpHeight + 3 + mpHeight),
				ImageHolder.ICS_LOGOUT.getIconWidth(),
				ImageHolder.ICS_LOGOUT.getIconHeight());
		jpi.add(logOutButton);
		logOutButton.setBackground(bkColor);
		logOutButton.setOpaque(false);
		logOutButton.setMargin(new Insets(0, 0, 0, 0));
		logOutButton.setBorder(null);
		logOutButton.setIcon(ImageHolder.ICS_LOGOUT);
		logOutButton.setRolloverIcon(ImageHolder.ICS_LOGOUT_HOVER);
		changePasswordButton = new JButton();
		changePasswordButton.addActionListener(this);
		changePasswordButton.setBounds(jpi.getInsets().left + 3,
				rt[3].height - (jpi.getInsets().bottom + 3 + mpHeight),
				ImageHolder.ICS_CHANGE_PASSWORD.getIconWidth(),
				ImageHolder.ICS_CHANGE_PASSWORD.getIconHeight());
		jpi.add(changePasswordButton);
		changePasswordButton.setBackground(bkColor);
		changePasswordButton.setMargin(new Insets(0, 0, 0, 0));
		changePasswordButton.setBorder(null);
		changePasswordButton.setIcon(ImageHolder.ICS_CHANGE_PASSWORD);
		changePasswordButton.setRolloverIcon(ImageHolder.ICS_CHANGE_PASSWORD_HOVER);
		changePasswordButton.setOpaque(false);
		add(jpi);
	}
	private void constructMenuButtons(){
		menuButtons = new JButton[MENU_NAMES.length];
		for(int i = 0;i < menuButtons.length;i++){
			menuButtons[i] = new JButton();
			int x = rt[1].x;
			int y = rt[1].y + (bHeight * i);
			menuButtons[i].setBounds(x, y, 
					bWidth,
					bHeight);
			add(menuButtons[i]);
			menuButtons[i].addActionListener(this);
			menuButtons[i].setBackground(bkColor);
			menuButtons[i].setOpaque(true);
		}
	}
	private void constructMenuPanels(){
		menuPanels = new JPanel[MENU_NAMES.length];
		for(int i = 0;i < menuPanels.length;i++){
			menuPanels[i] = new JPanel();
			menuPanels[i].setBounds(rt[2]);
			menuPanels[i].setLayout(null);
			menuPanels[i].setOpaque(false);
			add(menuPanels[i]);
			switch(i){
			case 0:{
				constructRentals(i);
				menuButtons[i].setMargin(new Insets(0, 0, 0, 0));
				menuButtons[i].setBorder(null);
				menuButtons[i].setIcon(ImageHolder.ICS[i]);
				menuButtons[i].setRolloverIcon(ImageHolder.ICSH[i]);
				break;
			}
			case 1:{
				constructMenuBooks(i);
				menuButtons[i].setMargin(new Insets(0, 0, 0, 0));
				menuButtons[i].setBorder(null);
				menuButtons[i].setIcon(ImageHolder.ICS[i]);
				menuButtons[i].setRolloverIcon(ImageHolder.ICSH[i]);
				break;
			}
			case 2:{
				constructMenuBorrowers(i);
				menuButtons[i].setMargin(new Insets(0, 0, 0, 0));
				menuButtons[i].setBorder(null);
				menuButtons[i].setIcon(ImageHolder.ICS[i]);
				menuButtons[i].setRolloverIcon(ImageHolder.ICSH[i]);
				break;
			}
			case 3:{
				constructMenuWorkers(i);
				menuButtons[i].setMargin(new Insets(0, 0, 0, 0));
				menuButtons[i].setBorder(null);
				menuButtons[i].setIcon(ImageHolder.ICS[i]);
				menuButtons[i].setRolloverIcon(ImageHolder.ICSH[i]);
				break;
			}
			case 4:{
				constructMenuReports(i);
				menuButtons[i].setMargin(new Insets(0, 0, 0, 0));
				menuButtons[i].setBorder(null);
				menuButtons[i].setIcon(ImageHolder.ICS[i]);
				menuButtons[i].setRolloverIcon(ImageHolder.ICSH[i]);
				break;
			}
			}
			if (i == activeMenu){
				menuPanels[i].setVisible(true);
				menuButtons[i].setBackground(MainFrame.SEL_COLOR);
				menuButtons[i].setIcon(ImageHolder.ICSP[i]);
				viewTitle = MENU_TITLES[activeMenu];
				refreshLogoTitle();
			} else {
				menuPanels[i].setVisible(false);
				menuButtons[i].setBackground(bkColor);
			}
		}
	}
	private void constructRentals(int menuIdx){
		Rectangle[] rp = ALayout.generate(menuPanels[menuIdx]);
		int bw = ImageHolder.IC_CHECK_IN_BOOK.getIconWidth();
		int bh = ImageHolder.IC_CHECK_IN_BOOK.getIconHeight();
		int dw = rp[1].width - bw;
		int dw2 = dw / 2;
		int dhb = bh * 3;
		int dh = rp[1].height - dhb;
		int dh2 = dh / 4;
		checkInButton = new JButton ();
		checkInButton.addActionListener(this);
		checkInButton.setBounds(rp[1].x + dw2,
				rp[1].y + dh2,
				ImageHolder.IC_CHECK_IN_BOOK.getIconWidth(),
				ImageHolder.IC_CHECK_IN_BOOK.getIconHeight());
		checkInButton.setBackground(bkColor);
		checkInButton.setMargin(new Insets(0, 0, 0, 0));
		checkInButton.setBorder(null);
		checkInButton.setIcon(ImageHolder.IC_CHECK_IN_BOOK);
		checkInButton.setRolloverIcon(ImageHolder.IC_CHECK_IN_BOOK_HOVER);
		menuPanels[menuIdx].add(checkInButton);
		checkOutButton = new JButton ();
		checkOutButton.addActionListener(this);
		checkOutButton.setBounds(rp[1].x + dw2,
				rp[1].y + dh2 * 2 + bh,
				ImageHolder.IC_CHECK_OUT_BOOK.getIconWidth(),
				ImageHolder.IC_CHECK_OUT_BOOK.getIconHeight());
		checkOutButton.setBackground(bkColor);
		checkOutButton.setMargin(new Insets(0, 0, 0, 0));
		checkOutButton.setBorder(null);
		checkOutButton.setIcon(ImageHolder.IC_CHECK_OUT_BOOK);
		checkOutButton.setRolloverIcon(ImageHolder.IC_CHECK_OUT_BOOK_HOVER);
		menuPanels[menuIdx].add(checkOutButton);
		processLostButton = new JButton ();
		processLostButton.addActionListener(this);
		processLostButton.setBounds(rp[1].x + dw2,
				rt[1].y + dh2 * 3 + bh * 2,
				ImageHolder.IC_PROCESS_LOST_BOOK.getIconWidth(),
				ImageHolder.IC_PROCESS_LOST_BOOK.getIconHeight());
		processLostButton.setBackground(bkColor);
		processLostButton.setMargin(new Insets(0, 0, 0, 0));
		processLostButton.setBorder(null);
		processLostButton.setIcon(ImageHolder.IC_PROCESS_LOST_BOOK);
		processLostButton.setRolloverIcon(ImageHolder.IC_PROCESS_LOST_BOOK_HOVER);
		menuPanels[menuIdx].add(processLostButton);
	}
	private void constructMenuReports(int menuIdx){
		Rectangle[] rp = ALayout.generate(menuPanels[menuIdx]);
		int bw = ImageHolder.IC_AVAILABLE_BOOKS.getIconWidth();
		int bh = ImageHolder.IC_AVAILABLE_BOOKS.getIconHeight();
		int dw = rp[1].width - bw;
		int dw2 = dw / 2;
		int dhb = bh * 4;
		int dh = rp[1].height - dhb;
		int dh2 = dh / 5;
		allAvailableBooksButton = new JButton ();
		allAvailableBooksButton.addActionListener(this);
		allAvailableBooksButton.setBounds(rp[1].x + dw2,
				rp[1].y + dh2,
				ImageHolder.IC_AVAILABLE_BOOKS.getIconWidth(),
				ImageHolder.IC_AVAILABLE_BOOKS.getIconHeight());
		allAvailableBooksButton.setBackground(bkColor);
		allAvailableBooksButton.setMargin(new Insets(0, 0, 0, 0));
		allAvailableBooksButton.setBorder(null);
		allAvailableBooksButton.setIcon(ImageHolder.IC_AVAILABLE_BOOKS);
		allAvailableBooksButton.setRolloverIcon(ImageHolder.IC_AVAILABLE_BOOKS_HOVER);
		menuPanels[menuIdx].add(allAvailableBooksButton);
		allCheckedOutButton = new JButton ();
		allCheckedOutButton.addActionListener(this);
		allCheckedOutButton.setBounds(rp[1].x + dw2,
				rp[1].y + dh2 * 2 + bh,
				ImageHolder.IC_CHECKED_OUT_BOOKS.getIconWidth(),
				ImageHolder.IC_CHECKED_OUT_BOOKS.getIconHeight());
		allCheckedOutButton.setBackground(bkColor);
		allCheckedOutButton.setMargin(new Insets(0, 0, 0, 0));
		allCheckedOutButton.setBorder(null);
		allCheckedOutButton.setIcon(ImageHolder.IC_CHECKED_OUT_BOOKS);
		allCheckedOutButton.setRolloverIcon(ImageHolder.IC_CHECKED_OUT_BOOKS_HOVER);
		menuPanels[menuIdx].add(allCheckedOutButton);
		borrowersWithCheckedOutButton = new JButton ();
		borrowersWithCheckedOutButton.addActionListener(this);
		borrowersWithCheckedOutButton.setBounds(rp[1].x + dw2,
				rt[1].y + dh2 * 3 + bh * 2,
				ImageHolder.IC_SB_WITH_CO_BOOKS.getIconWidth(),
				ImageHolder.IC_SB_WITH_CO_BOOKS.getIconHeight());
		borrowersWithCheckedOutButton.setBackground(bkColor);
		borrowersWithCheckedOutButton.setMargin(new Insets(0, 0, 0, 0));
		borrowersWithCheckedOutButton.setBorder(null);
		borrowersWithCheckedOutButton.setIcon(ImageHolder.IC_SB_WITH_CO_BOOKS);
		borrowersWithCheckedOutButton.setRolloverIcon(ImageHolder.IC_SB_WITH_CO_BOOKS_HOVER);
		menuPanels[menuIdx].add(borrowersWithCheckedOutButton);
		delinquencyCheckButton = new JButton ();
		delinquencyCheckButton.addActionListener(this);
		delinquencyCheckButton.setBounds(rp[1].x + dw2,
				rt[1].y + dh2 * 4 + bh * 3,
				ImageHolder.IC_DELINQUNECY_CHECK.getIconWidth(),
				ImageHolder.IC_DELINQUNECY_CHECK.getIconHeight());
		delinquencyCheckButton.setBackground(bkColor);
		delinquencyCheckButton.setMargin(new Insets(0, 0, 0, 0));
		delinquencyCheckButton.setBorder(null);
		delinquencyCheckButton.setIcon(ImageHolder.IC_DELINQUNECY_CHECK);
		delinquencyCheckButton.setRolloverIcon(ImageHolder.IC_DELINQUNECY_CHECK_HOVER);
		menuPanels[menuIdx].add(delinquencyCheckButton);
	}
	private void constructMenuWorkers(int menuIdx){
		Rectangle[] rp = ALayout.generate(menuPanels[menuIdx]);
		int bw = ImageHolder.IC_ADD_WORKER.getIconWidth();
		int bh = ImageHolder.IC_ADD_WORKER.getIconHeight();
		int dw = rp[1].width - bw;
		int dw2 = dw / 2;
		int dhb = bh * 3;
		int dh = rp[1].height - dhb;
		int dh2 = dh / 4;
		addWorkerButton = new JButton ();
		addWorkerButton.addActionListener(this);
		addWorkerButton.setBounds(rp[1].x + dw2,
				rp[1].y + dh2,
				ImageHolder.IC_ADD_WORKER.getIconWidth(),
				ImageHolder.IC_ADD_WORKER.getIconHeight());
		addWorkerButton.setBackground(bkColor);
		addWorkerButton.setMargin(new Insets(0, 0, 0, 0));
		addWorkerButton.setBorder(null);
		addWorkerButton.setIcon(ImageHolder.IC_ADD_WORKER);
		addWorkerButton.setRolloverIcon(ImageHolder.IC_ADD_WORKER_HOVER);
		menuPanels[menuIdx].add(addWorkerButton);
		modifyWorkerButton = new JButton ();
		modifyWorkerButton.addActionListener(this);
		modifyWorkerButton.setBounds(rp[1].x + dw2,
				rp[1].y + dh2 * 2 + bh,
				ImageHolder.IC_MODIFY_WORKER.getIconWidth(),
				ImageHolder.IC_MODIFY_WORKER.getIconHeight());
		modifyWorkerButton.setBackground(bkColor);
		modifyWorkerButton.setMargin(new Insets(0, 0, 0, 0));
		modifyWorkerButton.setBorder(null);
		modifyWorkerButton.setIcon(ImageHolder.IC_MODIFY_WORKER);
		modifyWorkerButton.setRolloverIcon(ImageHolder.IC_MODIFY_WORKER_HOVER);
		menuPanels[menuIdx].add(modifyWorkerButton);
		deleteWorkerButton = new JButton ();
		deleteWorkerButton.addActionListener(this);
		deleteWorkerButton.setBounds(rp[1].x + dw2,
				rt[1].y + dh2 * 3 + bh * 2,
				ImageHolder.IC_DELETE_WORKER.getIconWidth(),
				ImageHolder.IC_DELETE_WORKER.getIconHeight());
		deleteWorkerButton.setBackground(bkColor);
		deleteWorkerButton.setMargin(new Insets(0, 0, 0, 0));
		deleteWorkerButton.setBorder(null);
		deleteWorkerButton.setIcon(ImageHolder.IC_DELETE_WORKER);
		deleteWorkerButton.setRolloverIcon(ImageHolder.IC_DELETE_WORKER_HOVER);
		menuPanels[menuIdx].add(deleteWorkerButton);
	}
	private void constructMenuBorrowers(int menuIdx){
		Rectangle[] rp = ALayout.generate(menuPanels[menuIdx]);
		int bw = ImageHolder.IC_ADD_BORROWER.getIconWidth();
		int bh = ImageHolder.IC_ADD_BORROWER.getIconHeight();
		int dw = rp[1].width - bw;
		int dw2 = dw / 2;
		int dhb = bh * 3;
		int dh = rp[1].height - dhb;
		int dh2 = dh / 4;
		addStudentBorrowerButton = new JButton ();
		addStudentBorrowerButton.addActionListener(this);
		addStudentBorrowerButton.setBounds(rp[1].x + dw2,
				rp[1].y + dh2,
				ImageHolder.IC_ADD_BORROWER.getIconWidth(),
				ImageHolder.IC_ADD_BORROWER.getIconHeight());
		addStudentBorrowerButton.setBackground(bkColor);
		addStudentBorrowerButton.setMargin(new Insets(0, 0, 0, 0));
		addStudentBorrowerButton.setBorder(null);
		addStudentBorrowerButton.setIcon(ImageHolder.IC_ADD_BORROWER);
		addStudentBorrowerButton.setRolloverIcon(ImageHolder.IC_ADD_BORROWER_HOVER);
		menuPanels[menuIdx].add(addStudentBorrowerButton);
		modifyStudentBorrowerButton = new JButton ();
		modifyStudentBorrowerButton.addActionListener(this);
		modifyStudentBorrowerButton.setBounds(rp[1].x + dw2,
				rp[1].y + dh2 * 2 + bh,
				ImageHolder.IC_MODIFY_BORROWER.getIconWidth(),
				ImageHolder.IC_MODIFY_BORROWER.getIconHeight());
		modifyStudentBorrowerButton.setBackground(bkColor);
		modifyStudentBorrowerButton.setMargin(new Insets(0, 0, 0, 0));
		modifyStudentBorrowerButton.setBorder(null);
		modifyStudentBorrowerButton.setIcon(ImageHolder.IC_MODIFY_BORROWER);
		modifyStudentBorrowerButton.setRolloverIcon(ImageHolder.IC_MODIFY_BORROWER_HOVER);
		menuPanels[menuIdx].add(modifyStudentBorrowerButton);
		deleteStudentBorrowerButton = new JButton ();
		deleteStudentBorrowerButton.addActionListener(this);
		deleteStudentBorrowerButton.setBounds(rp[1].x + dw2,
				rt[1].y + dh2 * 3 + bh * 2,
				ImageHolder.IC_DELETE_BORROWER.getIconWidth(),
				ImageHolder.IC_DELETE_BORROWER.getIconHeight());
		deleteStudentBorrowerButton.setBackground(bkColor);
		deleteStudentBorrowerButton.setMargin(new Insets(0, 0, 0, 0));
		deleteStudentBorrowerButton.setBorder(null);
		deleteStudentBorrowerButton.setIcon(ImageHolder.IC_DELETE_BORROWER);
		deleteStudentBorrowerButton.setRolloverIcon(ImageHolder.IC_DELETE_BORROWER_HOVER);
		menuPanels[menuIdx].add(deleteStudentBorrowerButton);
	}
	private void constructMenuBooks(int menuIdx){
		Rectangle[] rp = ALayout.generate(menuPanels[menuIdx]);
		int bw = ImageHolder.IC_ADD_BOOK.getIconWidth();
		int bh = ImageHolder.IC_ADD_BOOK.getIconHeight();
		int dw = rp[1].width - bw;
		int dw2 = dw / 2;
		int dhb = bh * 3;
		int dh = rp[1].height - dhb;
		int dh2 = dh / 4;
		addBookButton = new JButton ();
		addBookButton.addActionListener(this);
		addBookButton.setBounds(rp[1].x + dw2,
				rp[1].y + dh2,
				ImageHolder.IC_ADD_BOOK.getIconWidth(),
				ImageHolder.IC_ADD_BOOK.getIconHeight());
		addBookButton.setBackground(bkColor);
		menuPanels[menuIdx].add(addBookButton);
		addBookButton.setMargin(new Insets(0, 0, 0, 0));
		addBookButton.setBorder(null);
		addBookButton.setIcon(ImageHolder.IC_ADD_BOOK);
		addBookButton.setRolloverIcon(ImageHolder.IC_ADD_BOOK_HOVER);
		modifyBookButton = new JButton();
		modifyBookButton.addActionListener(this);
		modifyBookButton.setBounds(rp[1].x + dw2,
				rp[1].y + dh2 * 2 + bh,
				ImageHolder.IC_MODIFY_BOOK.getIconWidth(),
				ImageHolder.IC_MODIFY_BOOK.getIconHeight());
		modifyBookButton.setBackground(bkColor);
		menuPanels[menuIdx].add(modifyBookButton);
		modifyBookButton.setMargin(new Insets(0, 0, 0, 0));
		modifyBookButton.setBorder(null);
		modifyBookButton.setIcon(ImageHolder.IC_MODIFY_BOOK);
		modifyBookButton.setRolloverIcon(ImageHolder.IC_MODIFY_BOOK_HOVER);
		deleteBookButton = new JButton();
		deleteBookButton.addActionListener(this);
		deleteBookButton.setBounds(rp[1].x + dw2,
				rt[1].y + dh2 * 3 + bh * 2,
				ImageHolder.IC_DELETE_BOOK.getIconWidth(),
				ImageHolder.IC_DELETE_BOOK.getIconHeight());
		deleteBookButton.setBackground(bkColor);
		menuPanels[menuIdx].add(deleteBookButton);
		deleteBookButton.setMargin(new Insets(0, 0, 0, 0));
		deleteBookButton.setBorder(null);
		deleteBookButton.setIcon(ImageHolder.IC_DELETE_BOOK);
		deleteBookButton.setRolloverIcon(ImageHolder.IC_DELETE_BOOK_HOVER);
	}
	protected void processAction(EventObject e){
		if(e.getSource() == logOutButton){
			processCancel();
		} else if(e.getSource() == addWorkerButton){
			myModel.stateChangeRequest("PerformTransaction", "AddWorker");
		} else if (e.getSource() == addStudentBorrowerButton){
			myModel.stateChangeRequest("PerformTransaction", "AddStudentBorrower");
		} else if(e.getSource() == addBookButton){
			myModel.stateChangeRequest("PerformTransaction", "AddBook");
		} else if(e.getSource() == modifyWorkerButton){
			myModel.stateChangeRequest("PerformTransaction", "ModifyWorker");
		} else if(e.getSource() == modifyStudentBorrowerButton){
			myModel.stateChangeRequest("PerformTransaction", "ModifyStudentBorrower");
		} else if(e.getSource() == modifyBookButton){
			myModel.stateChangeRequest("PerformTransaction", "ModifyBook");
		} else if(e.getSource() == deleteWorkerButton){
			myModel.stateChangeRequest("PerformTransaction", "DeleteWorker");
		} else if(e.getSource() == deleteStudentBorrowerButton){
			myModel.stateChangeRequest("PerformTransaction", "DeleteStudentBorrower");
		} else if(e.getSource() == deleteBookButton){
			myModel.stateChangeRequest("PerformTransaction", "DeleteBook");
		} else if(e.getSource() == checkInButton) {
			myModel.stateChangeRequest("PerformTransaction", "CheckInABook");
		} else if(e.getSource() == processLostButton){
			String myCredentials = (String)myModel.getState("Credentials");
			if(myCredentials.equals("Administrator")){
				myModel.stateChangeRequest("PerformTransaction", "ProcessLostBook");
			} else{
				displayErrorMessage("Only administrators can process lost books.");
			}
		} else if(e.getSource() == checkInButton){
			myModel.stateChangeRequest("PerformTransaction", "CheckInBook");
		} else if(e.getSource() == checkOutButton){
			myModel.stateChangeRequest("PerformTransaction", "CheckOutBook");
		} else if(e.getSource() == changePasswordButton){
			myModel.stateChangeRequest("PerformTransaction", "ChangePassword");
		} else if(e.getSource() == allAvailableBooksButton){
			myModel.stateChangeRequest("PerformTransaction", "AvailableBooks");
		} else if(e.getSource() == allCheckedOutButton){
			myModel.stateChangeRequest("PerformTransaction", "CheckedOutBooks");
		} else if(e.getSource() == borrowersWithCheckedOutButton){
			myModel.stateChangeRequest("PerformTransaction", "SBWithCheckedOutBooks");
		} else if(e.getSource() == delinquencyCheckButton){
			myModel.stateChangeRequest("PerformTransaction", "DelinquencyCheck");
		} else {
			int i = 0;
			while(i < MENU_NAMES.length){
				if (menuButtons[i] == e.getSource()){
					for(int j = 0;j < menuButtons.length;j++){
						if (j != i){
							menuPanels[j].setVisible(false);
							menuButtons[j].setBackground(bkColor);
							menuButtons[j].setIcon(ImageHolder.ICS[j]);
						}
					}
					menuPanels[i].setVisible(true);
					menuButtons[i].setBackground(MainFrame.SEL_COLOR);
					menuButtons[i].setIcon(ImageHolder.ICSP[i]);
					viewTitle = "    " + MENU_TITLES[i];
					refreshLogoTitle();
					break;
				}
				i++;
			}
		}
	}
	public void updateState(String key, Object value){
		if (key.equals("TransactionError") == true){
			displayErrorMessage((String)value);
		}
	}
	private JPanel createStatusLog(String initialMessage){
		statusLog = new MessageView(initialMessage);
		return statusLog;
	}
	//Not necessary for this view
	public void populateFields(){
	}
	public String getFullName(){
		String firstNameString = (String)myModel.getState("FirstName");
		String lastNameString = (String)myModel.getState("LastName");
		return firstNameString + " " + lastNameString;
	}
	private void processCancel(){
		System.out.println("logging out...");
		this.setVisible(false);
		myModel.stateChangeRequest("Logout", null);
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
	}
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
