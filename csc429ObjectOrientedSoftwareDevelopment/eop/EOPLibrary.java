// tabs=4
//************************************************************
//	COPYRIGHT 2014 Sandeep Mitra and students, The
//    College at Brockport, State University of New York. - 
//	  ALL RIGHTS RESERVED
//
// This file is the product of The College at Brockport and cannot 
// be reproduced, copied, or used in any shape or form without 
// the express written consent of The College at Brockport.
//************************************************************
//
// specify the package
// This file is in the default package

// system imports
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import javax.swing.ImageIcon;
import java.util.Locale;
import java.util.ResourceBundle;


// project imports
import event.Event;
import event.EventLog;
import common.PropertyFile;

import model.Librarian;
import userinterface.MainFrame;
import userinterface.WindowPosition;

/**The class containing the main program for the EOP Library application */
//====================================================================================
public class EOPLibrary {

	private Librarian myLibrarian;     // the main behavior (main interface agent) for the application

	/** Main frame of the application */
	private MainFrame mainFrame;


	// constructor for this class, the main application object
	//--------------------------------------------------------
	public EOPLibrary(){
		System.out.println("EOP Library System Version 3.1");
/*
		//figure out the desired look and feel
		String LookNFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

		// See if you can set the look and feel requested, if not indicate error
		try{
			UIManager.setLookAndFeel(LookNFeel);
		}
		catch(Exception e){
			LookNFeel = UIManager.getCrossPlatformLookAndFeelClassName();
			try{
				UIManager.setLookAndFeel(LookNFeel);
			}
			catch(Exception f){
				System.err.println("EOPLibrary.<init> - Unable to set look and feel");
				//new Event(Event.getLeafLevelClassName(this), "FastTrax.<init>",
				//		"Unable to set look and feel", Event.ERROR);
				//f.printStackTrace();
			}
		}
*/
		//Create the top-level container (main frame) and add contents to it.
		mainFrame = MainFrame.getInstance("EOP Library System v1.0");

		// put in icon for window border and toolbar

		Toolkit myToolKit = Toolkit.getDefaultToolkit();

		File iconFile = new File("EOP.jpg");

		if (iconFile.exists() == true){
			Image myImage = myToolKit.getImage("EOP.jpg"); // DO WE HAVE AN ICON/JPG SYMBOLIZING EOP?
			mainFrame.setIconImage(myImage);
		}

		// Finish setting up the frame, and show it.
		//mainFrame.addWindowListener(new WindowAdapter(){
			// event handler for window close events
		//	public void windowClosing(WindowEvent event){
				//System.exit(0);

		//	}
		//});

		mainFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		try{
			myLibrarian = new Librarian(mainFrame);
		}
		catch(Exception exc){
			System.err.println("EOPLibrary.<init> - could not create Librarian!");
			new Event(Event.getLeafLevelClassName(this), "EOPLibrary.<init>",
					"Unable to create Librarian object", Event.ERROR);
			exc.printStackTrace();
		}

		mainFrame.pack();

		WindowPosition.placeCenter(mainFrame);

		mainFrame.setVisible(true);
	}

	/** The "main" entry point for the application. Carries out actions to
	 *  set up the application */
	//---------------------------------------------------------------------------
	public static void main(String[] args){
		//crank up an instance of this object
		try{
			new EOPLibrary();
		}
		catch(Exception e){
			new Event("EOPLibrary", "EOPLibrary.main", "Unhandled Exception: " + e, Event.FATAL);
			e.printStackTrace();
		}
	}
}

//----------------------------------------------------------------------------------------
// Revision History
//
