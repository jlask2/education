// tabs=4
//************************************************************
//	COPYRIGHT 2009 Sandeep Mitra and Josh Swanson/Anna Migitskiy, The
//    College at Brockport, State University of New York. - 
//	  ALL RIGHTS RESERVED
//
// This file is the product of The College at Brockport and cannot 
// be reproduced, copied, or used in any shape or form without 
// the express written consent of The College at Brockport.
//************************************************************
//
// specify the package
package userinterface;

// system imports
import java.util.Calendar;
import java.util.Date;
import java.util.EventObject;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.io.File;
import java.text.SimpleDateFormat;

// project imports
import impresario.IView;
import impresario.IModel;
import impresario.IControl;
import impresario.ControlRegistry;

//==============================================================
public abstract class View extends JPanel implements IView, IControl, 
ActionListener, FocusListener, ListSelectionListener
{


	// private data
	protected IModel myModel;
	protected ControlRegistry myRegistry;

	// forward declaration
	protected abstract void processAction(EventObject evt);

	// forward declaration
	protected abstract void processListSelection(EventObject evt);

	// GUI components
	protected final Color bkColor = MainFrame.BK_COLOR;
	//protected final Color blue =  Color.white;

	// preferred empty box size, used to position components
	final Dimension size = new Dimension( 200, 15 );

	// indicate preferred size of a Button 
	final Dimension sizeButton = new Dimension( 175, 25 );

	// indicate preferred size of a Combo Box 
	final Dimension smallDropDown = new Dimension( 85, 25 );

	// indicate preferred size of a Button 
	final Dimension sizeButtonSmall = new Dimension( 100, 25 );

	// indicates preferred size of a Text Area 
	final Dimension sizeArea = new Dimension( 300, 70 );

	// indicates preferred size of a Label  
	final Dimension sizeLabel = new Dimension( 102, 30 );

	// indicates preferred size of a Large Label  
	final Dimension sizeMidLabel = new Dimension( 153, 30 );

	// indicates preferred size of a Large Label  
	final Dimension sizeLargeLabel = new Dimension( 204, 30 );

	// indicates preferred size for a form
	final Dimension sizeFormSpace = new Dimension( 100, 5 );

	// indicate the font for a View's Title, will be used 
	// to format all Views Titles
	final Font myTitleFont = new Font("Arial", Font.BOLD, 13 );


	// indicate the font for all components that will be used 
	// in the program views, i.e. JButtons, JTextFields, JLabels and etc.
	final Font myComponentsFont = new Font( "Arial", Font.TYPE1_FONT, 12 );

	final Border formBorder = BorderFactory.createEmptyBorder( 20, 50, 20, 20 );

	// Class constructor
	//----------------------------------------------------------
	public View(IModel model, String classname)
	{
		myModel = model;


		myRegistry = new ControlRegistry(classname);
	}

	// process events generated from our GUI components
	//-------------------------------------------------------------
	public void actionPerformed(ActionEvent evt) 
	{
		// DEBUG: System.out.println("View.actionPerformed(): " + evt.toString());

		processAction(evt);
	}

	// Same as hitting return in a field, fire postStateChange
	//----------------------------------------------------------
	public void focusLost(FocusEvent evt)
	{
		// DEBUG: System.out.println("CustomerView.focusLost()");
		// ignore temporary events
		if(evt.isTemporary() == true)
			return;

		processAction(evt);
	}

	//----------------------------------------------------------
	public void focusGained(FocusEvent evt)
	{
		// We don't need this for now
	}

	// Listen the value changes (selection etc.) 
	//----------------------------------------------------------
	public void valueChanged(ListSelectionEvent evt)
	{
		processListSelection(evt);
	}

	//----------------------------------------------------------
	public void setRegistry(ControlRegistry registry)
	{
		myRegistry = registry;
	}

	// Allow models to register for state updates
	//----------------------------------------------------------
	public void subscribe(String key,  IModel subscriber)
	{
		myRegistry.subscribe(key, subscriber);
	}


	// Allow models to unregister for state updates
	//----------------------------------------------------------
	public void unSubscribe(String key, IModel subscriber)
	{
		myRegistry.unSubscribe(key, subscriber);
	}

	//----------------------------------------------------------
	protected String mapMonthToString(int month)
	{
		if (month == Calendar.JANUARY)
		{
			return "January";
		}
		else
			if (month == Calendar.FEBRUARY)
			{
				return "February";
			}
			else
				if (month == Calendar.MARCH)
				{
					return "March";
				}
				else
					if (month == Calendar.APRIL)
					{
						return "April";
					}
					else
						if (month == Calendar.MAY)
						{
							return "May";
						}
						else
							if (month == Calendar.JUNE)
							{
								return "June";
							}
							else
								if (month == Calendar.JULY)
								{
									return "July";
								}
								else
									if (month == Calendar.AUGUST)
									{
										return "August";
									}
									else
										if (month == Calendar.SEPTEMBER)
										{
											return "September";
										}
										else
											if (month == Calendar.OCTOBER)
											{
												return "October";
											}
											else
												if (month == Calendar.NOVEMBER)
												{
													return "November";
												}
												else
													if (month == Calendar.DECEMBER)
													{
														return "December";
													}

		return "";
	}

	//----------------------------------------------------------
	protected int mapMonthNameToIndex(String monthName)
	{
		if (monthName.equals("January") == true)
		{
			return Calendar.JANUARY;
		}
		else
			if (monthName.equals("February") == true)
			{
				return Calendar.FEBRUARY;
			}
			else
				if (monthName.equals("March") == true)
				{
					return Calendar.MARCH;
				}
				else
					if (monthName.equals("April") == true)
					{
						return Calendar.APRIL;
					}
					else
						if (monthName.equals("May") == true)
						{
							return Calendar.MAY;
						}
						else
							if (monthName.equals("June") == true)
							{
								return Calendar.JUNE;
							}
							else
								if (monthName.equals("July") == true)
								{
									return Calendar.JULY;
								}
								else
									if (monthName.equals("August") == true)
									{
										return Calendar.AUGUST;
									}
									else
										if (monthName.equals("September") == true)
										{
											return Calendar.SEPTEMBER;
										}
										else
											if (monthName.equals("October") == true)
											{
												return Calendar.OCTOBER;
											}
											else
												if (monthName.equals("November") == true)
												{
													return Calendar.NOVEMBER;
												}
												else
													if (monthName.equals("December") == true)
													{
														return Calendar.DECEMBER;
													}

		return -1;
	}


	//----------------------------------------------------
	protected boolean checkProperLetters(String value)
	{
		for (int cnt = 0; cnt < value.length(); cnt++)
		{
			char ch = value.charAt(cnt);

			if ((ch >= 'A') && (ch <= 'Z') || (ch >= 'a') && (ch <= 'z'))
			{
			}
			else
				if ((ch == '-') || (ch == ',') || (ch == '.') || (ch == ' '))
				{
				}
				else
				{
					return false;
				}
		}

		return true;
	}

	/* aozgo1: We do not need this know. We have more efficient solution!
	 * See what I did @AddScoutView, @UpdateScoutView or @SearchScoutView. */
	//----------------------------------------------------
	protected boolean checkProperPhoneNumber(String value)
	{
		if ((value == null) || (value.length() < 7))
		{
			return false;
		}

		for (int cnt = 0; cnt < value.length(); cnt++)
		{
			char ch = value.charAt(cnt);

			if ((ch >= '0') && (ch <= '9'))
			{
			}
			else
				if ((ch == '-') || (ch == '(') || (ch == ')') || (ch == ' '))
				{
				}
				else
				{
					return false;
				}
		}

		return true;
	}

	/*
	 * aozgo1: Checks if the Troop ID or ID entered by user is valid 
	 * 		   or not.  
	 */
	//----------------------------------------------------------
	protected boolean checkProperTId(String value)
	{	
		for (int cnt = 0; cnt < value.length(); cnt++)
		{
			char ch = value.charAt(cnt);

			if ((ch >= '0') && (ch <= '9')) 
			{ 
				// Do nothing
			}
			else
				if ((ch < '0') || (ch > '9'))
				{
					return false;
				}
		}

		return true;
	}

	//----------------------------------------------------------
	protected String convertToDefaultDateFormat(Date theDate)
	{

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

		String valToReturn = formatter.format(theDate);	

		return valToReturn;

	}

	//----------------------------------------------------------------
	// This method receives a "raw" JButton and formats it to the 
	// format, that will be used for all the buttons that appear in
	// the program. Returns the formatted button to the caller.
	// The method may be called by any of the class, that 
	// extended View class.
	//-----------------------------------------------------------------
	protected JButton formatButton ( JButton buttonToFormat )
	{

		buttonToFormat.setBorder ( new LineBorder ( Color.black, 1 ) );
		buttonToFormat.setFont( myComponentsFont );  
		buttonToFormat.setPreferredSize( sizeButton );
		buttonToFormat.setMaximumSize(  sizeButton );
		buttonToFormat.setAlignmentX( CENTER_ALIGNMENT );

		buttonToFormat.addActionListener( this );

		return buttonToFormat;

	}

	//----------------------------------------------------------------
	// JOSH ADDED FOR SMALL BUTTONS
	// This method receives a "raw" JButton and formats it to the 
	// format, that will be used for all the buttons that appear in
	// the program. Returns the formatted button to the caller.
	// The method may be called by any of the class, that 
	// extended View class.
	//-----------------------------------------------------------------
	protected JButton formatButtonSmall ( JButton buttonToFormat )
	{

		buttonToFormat.setBorder ( new LineBorder ( Color.black, 1 ) );
		buttonToFormat.setFont( myComponentsFont );  
		buttonToFormat.setPreferredSize( sizeButtonSmall );
		buttonToFormat.setMaximumSize(  sizeButtonSmall );
		buttonToFormat.setAlignmentX( CENTER_ALIGNMENT );

		buttonToFormat.addActionListener( this );

		return buttonToFormat;

	}

	//-------------------------------------------------------------------
	// This method receives a String viewTitle and formats it to the 
	// format, that will be used for all the Views' titles that appear in
	// the program. Returns the formatted JPanel to the caller.
	// The method may be called by any child of View class.
	//--------------------------------------------------------------------
	protected JPanel formatViewTitle ( String viewTitle )
	{
		JPanel container = new JPanel( );
		container.setBackground ( bkColor );

		JLabel viewTitleLabel = new JLabel( viewTitle );	
		viewTitleLabel.setFont( myTitleFont );
		viewTitleLabel.setForeground( Color.red.darker().darker() );
		viewTitleLabel.setAlignmentX( CENTER_ALIGNMENT );	

		container.add( Box.createRigidArea( size ));

		container.add( viewTitleLabel );

		container.add( Box.createRigidArea( size ));	

		return container;
	}

	//------------------------------------------------------------------------------------
	// ANNA UPDATED ON 3.31.12: this method will take over job of formatButton
	// ANNA UPDATED ON 4.23.12: this method will perform a job of a switching 

	// The main idea is: when any child of View is created, all the components (such as
	// buttons, text fields, combo drop down boxes and tables) are sent to this method 
	// to get formatting that is consistent throughout the entire application.
	// @param is JComponent componentToFormat - any component that needs formating
	// @return JComponent componentToFormat the same component that was sent to this 
	// method, but already formatted.	
	//------------------------------------------------------------------------------------
	protected JComponent formatComponent ( JComponent componentToFormat )
	{
		// all the components will be of uniform Font and Alignment
		componentToFormat.setFont( myComponentsFont );
		componentToFormat.setAlignmentX( CENTER_ALIGNMENT );

		// if the component is JLabel call the method 
		// formatLabel ( JLabel labelToFormat ), then return it
		if ( componentToFormat instanceof JLabel ) 
		{
			return formatLabel ( (JLabel) componentToFormat );

		}


		if (!( componentToFormat instanceof JLabel ) && 
				!( componentToFormat instanceof JRadioButton ))
		{
			componentToFormat.setBorder ( new LineBorder ( Color.black, 1 ) );
			componentToFormat.setPreferredSize( componentToFormat instanceof JScrollPane ? sizeArea : sizeButton );
			componentToFormat.setMaximumSize(  componentToFormat instanceof JScrollPane ? sizeArea : sizeButton );
		}


		// if the component is a JButton then call formatButton method 
		// to add additional formats it needs, such as action listener
		if ( componentToFormat instanceof JButton )
		{
			return formatButton ( ( JButton )componentToFormat );
		}

		return componentToFormat;
	}

	//------------------------------------------------------------------------------------

	// The main idea is: when any child of View is created, all the components (such as
	// buttons, text fields, combo drop down boxes and tables) are sent to this method 
	// to get formatting that is consistent throughout the entire application.
	// @param is JComponent componentToFormat - any component that needs formating
	// @return JComponent componentToFormat the same component that was sent to this 
	// method, but already formatted.	
	//------------------------------------------------------------------------------------
	protected JComponent formatComponentLarge ( JComponent componentToFormat )
	{
		// all the components will be of uniform Font and Alignment
		componentToFormat.setFont( myComponentsFont );
		componentToFormat.setAlignmentX( CENTER_ALIGNMENT );

		// if the component is JLabel call the method 
		// formatLabel ( JLabel labelToFormat ), then return it
		if ( componentToFormat instanceof JLabel ) 
		{
			return formatLargeLabel ( (JLabel) componentToFormat );

		}


		if (!( componentToFormat instanceof JLabel ) && 
				!( componentToFormat instanceof JRadioButton ))
		{
			componentToFormat.setBorder ( new LineBorder ( Color.black, 1 ) );
			componentToFormat.setPreferredSize( componentToFormat instanceof JScrollPane ? sizeArea : sizeButton );
			componentToFormat.setMaximumSize(  componentToFormat instanceof JScrollPane ? sizeArea : sizeButton );
		}


		// if the component is a JButton then call formatButton method 
		// to add additional formats it needs, such as action listener
		if ( componentToFormat instanceof JButton )
		{
			return formatButton ( ( JButton )componentToFormat );
		}

		return componentToFormat;
	}

	//---------------------------------------------------------------------------
	//
	//---------------------------------------------------------------------------
	protected JLabel formatLabel ( JLabel labelToFormat )
	{
		labelToFormat.setPreferredSize( sizeLabel );
		labelToFormat.setMaximumSize(  sizeLabel );

		return labelToFormat;

	}

	//---------------------------------------------------------------------------
	//
	//---------------------------------------------------------------------------
	protected JLabel formatLargeLabel ( JLabel labelToFormat )
	{
		labelToFormat.setPreferredSize( sizeMidLabel );
		labelToFormat.setMaximumSize(  sizeLargeLabel );

		return labelToFormat;

	}

	//---------------------------------------------------------------------------
	//
	//---------------------------------------------------------------------------
	protected JComboBox formatDropDown ( JComboBox dropDown )
	{
		dropDown.setPreferredSize( smallDropDown );
		dropDown.setMaximumSize(  smallDropDown );		

		dropDown.setBorder ( new LineBorder ( Color.black, 1 ) );
		dropDown.setFont( myComponentsFont ); 
		dropDown.setAlignmentX( CENTER_ALIGNMENT );		

		return dropDown;
	}

	//---------------------------------------------------------------------------
	// This method is called from any of the children of View class when there 
	// is a need for combination/layout in the form of:
	// Label:      Text_Field
	//---------------------------------------------------------------------------
	protected JPanel formatCurrentPanel ( String labelName, JComponent component )
	{
		JPanel currentPanel = new JPanel ( );
		currentPanel.setLayout(new FlowLayout( FlowLayout.LEFT ));

		currentPanel.setBackground ( bkColor );
		JLabel currentLabel = new JLabel ( labelName );

		currentLabel.setFont( myComponentsFont );
		currentPanel.add( formatComponent ( currentLabel ) );

		currentPanel.add( formatComponent ( component ) );
		return currentPanel;
	}	

	//---------------------------------------------------------------------------
	// This method is called from any of the children of View class when there 
	// is a need for combination/layout in the form of:
	// Label:      Text_Field
	//---------------------------------------------------------------------------
	protected JPanel formatCurrentPanelLarge ( String labelName, JComponent component )
	{
		JPanel currentPanel = new JPanel ( );
		currentPanel.setLayout(new FlowLayout( FlowLayout.LEFT ));

		currentPanel.setBackground ( bkColor );
		JLabel currentLabel = new JLabel ( labelName );

		currentLabel.setFont( myComponentsFont );
		currentPanel.add( formatComponentLarge ( currentLabel ) );

		currentPanel.add( formatComponent ( component ) );
		return currentPanel;
	}	

	//---------------------------------------------------------------------------
	// This method is called from any of the children of View class when there 
	// is a need for combination/layout in the form of:
	// Label:      Text_Field
	// Plus, the contents of the whole panel is centered
	//---------------------------------------------------------------------------
	protected JPanel formatCurrentPanelCenter ( String labelName, JComponent component )
	{
		JPanel currentPanel = new JPanel ( );
		currentPanel.setLayout(new FlowLayout( FlowLayout.CENTER ));

		currentPanel.setBackground ( bkColor );
		JLabel currentLabel = new JLabel ( labelName );

		currentLabel.setFont( myComponentsFont );
		currentPanel.add( formatComponent ( currentLabel ) );

		currentPanel.add( formatComponent ( component ) );
		return currentPanel;
	}	

	// ADDED BY S. MITRA 2013/03/03
	//---------------------------------------------------------------------------
	// This method is called from any of the children of View class when there 
	// is a need for combination/layout in the form of:
	// Label:      Text_Field
	// This sends the actual JLabel object from the client to this method
	//---------------------------------------------------------------------------
	protected JPanel formatCurrentPanel ( JLabel lbl, JComponent component )
	{
		JPanel currentPanel = new JPanel ( );
		currentPanel.setLayout(new FlowLayout( FlowLayout.LEFT ));

		currentPanel.setBackground ( bkColor );

		lbl.setFont( myComponentsFont );
		currentPanel.add( formatComponent ( lbl) );

		currentPanel.add( formatComponent ( component ) );
		return currentPanel;
	}	

	// ADDED BY ANDREW ALLEN 2013/04/05
	//---------------------------------------------------------------------------
	// This method is called from any of the children of View class when there 
	// is a need for combination/layout in the form of:
	// Label:      Text_Field
	// This sends the actual JLabel object from the client to this method
	//---------------------------------------------------------------------------
	protected JPanel formatCurrentPanelLarge ( JLabel lbl, JComponent component )
	{
		JPanel currentPanel = new JPanel ( );
		currentPanel.setLayout(new FlowLayout( FlowLayout.LEFT ));

		currentPanel.setBackground ( bkColor );

		lbl.setFont( myComponentsFont );
		currentPanel.add( formatComponentLarge ( lbl) );

		currentPanel.add( formatComponent ( component ) );
		return currentPanel;
	}	

	//---------------------------------------------------------------------------
	// MONEY
	//---------------------------------------------------------------------------
	protected JPanel formatMoneyPanel ( String labelName, JComponent component )
	{
		// indicates preferred size of a Label that is used in conjunction
		// with the $ money sign on the money panel
		final Dimension shortLabel = new Dimension( 90, 30 );


		JPanel currentPanel = new JPanel ( );
		currentPanel.setLayout(new FlowLayout( FlowLayout.LEFT ));

		currentPanel.setBackground ( bkColor );
		JLabel currentLabel = new JLabel ( labelName );
		JLabel dollarLabel = new JLabel("$");

		currentLabel.setFont( myComponentsFont );
		dollarLabel.setFont( myComponentsFont );

		currentLabel.setPreferredSize( shortLabel );
		currentLabel.setMaximumSize( shortLabel );

		currentPanel.add( currentLabel );		
		currentPanel.add( dollarLabel );

		currentPanel.add( formatComponent ( component ) );
		return currentPanel;
	}


	// ADDED BY ANDREW ALLEN 4/5/13
	//---------------------------------------------------------------------------
	// MONEY
	//---------------------------------------------------------------------------
	protected JPanel formatMoneyPanelLarge ( String labelName, JComponent component )
	{
		// indicates preferred size of a long Label that is used in conjunction
		// with the $ money sign on the money panel
		final Dimension longLabel = new Dimension( 141, 30 );

		JPanel currentPanel = new JPanel ( );
		currentPanel.setLayout(new FlowLayout( FlowLayout.LEFT ));

		currentPanel.setBackground ( bkColor );
		JLabel currentLabel = new JLabel ( labelName );
		JLabel dollarLabel = new JLabel("$");

		currentLabel.setFont( myComponentsFont );
		dollarLabel.setFont( myComponentsFont );

		currentLabel.setPreferredSize( longLabel );
		currentLabel.setMaximumSize( longLabel );

		currentPanel.add( currentLabel );		
		currentPanel.add( dollarLabel );

		currentPanel.add( formatComponent ( component ) );
		return currentPanel;
	}


	//---------------------------------------------------------------------------
	// Method is overloaded: program will modify the appearance of the Panel 
	// based on the number of components sent to it.
	// This method is called from any of the children of View class when there 
	// is a need for combination/layout in the form of:
	// Label:      Comp_1	Comp_2	Comp_3
	//---------------------------------------------------------------------------
	protected JPanel formatCurrentPanel ( String labelName, JComponent comp_1, 
			JComponent comp_2, JComponent comp_3 )
	{
		JPanel currentPanel = new JPanel ();
		currentPanel.setBackground ( bkColor );
		JLabel currentLabel = new JLabel ( labelName );

		currentLabel.setFont( myComponentsFont );
		currentPanel.add( formatComponent ( currentLabel ));

		if ( comp_1 instanceof JTextField )
		{
			currentPanel.add( formatComponent ( comp_1 ) );
		}

		currentPanel.add( comp_1 instanceof JComboBox ? formatDropDown ( (JComboBox) comp_1 ) : formatComponent ( comp_1 ) );
		currentPanel.add( comp_2 instanceof JComboBox ? formatDropDown ( (JComboBox) comp_2 ) : formatComponent ( comp_2 ) );
		currentPanel.add( comp_3 instanceof JComboBox ? formatDropDown ( (JComboBox) comp_3 ) : formatComponent ( comp_3 ) );

		return currentPanel;
	}	


	//---------------------------------------------------------------------------
	// Method is overloaded: program will modify the appearance of the Panel 
	// based on the number of components sent to it.
	// This method is called from any of the children of View class when there 
	// is a need for combination/layout in the form of:
	// Label:      Comp_1	Comp_2	Comp_3
	//---------------------------------------------------------------------------
	protected JPanel formatCurrentPanelLarge ( String labelName, JComponent comp_1, 
			JComponent comp_2, JComponent comp_3 )
	{
		JPanel currentPanel = new JPanel ();
		currentPanel.setBackground ( bkColor );
		JLabel currentLabel = new JLabel ( labelName );

		currentLabel.setFont( myComponentsFont );
		currentPanel.add( formatComponentLarge ( currentLabel ));

		if ( comp_1 instanceof JTextField )
		{
			currentPanel.add( formatComponent ( comp_1 ) );
		}

		currentPanel.add( comp_1 instanceof JComboBox ? formatDropDown ( (JComboBox) comp_1 ) : formatComponent ( comp_1 ) );
		currentPanel.add( comp_2 instanceof JComboBox ? formatDropDown ( (JComboBox) comp_2 ) : formatComponent ( comp_2 ) );
		currentPanel.add( comp_3 instanceof JComboBox ? formatDropDown ( (JComboBox) comp_3 ) : formatComponent ( comp_3 ) );

		return currentPanel;
	}	

	//---------------------------------------------------------------------------
	protected JPanel formatCurrentPanelLeft ( String labelName, JComponent comp_1, 
			JComponent comp_2, JComponent comp_3 )
	{
		JPanel currentPanel = new JPanel ();
		currentPanel.setBackground ( bkColor );
		currentPanel.setLayout(new FlowLayout( FlowLayout.LEFT ));
		JLabel currentLabel = new JLabel ( labelName );

		currentLabel.setFont( myComponentsFont );
		currentPanel.add( formatComponent ( currentLabel ));

		if ( comp_1 instanceof JTextField )
		{
			currentPanel.add( formatComponent ( comp_1 ) );
		}

		currentPanel.add( comp_1 instanceof JComboBox ? formatDropDown ( (JComboBox) comp_1 ) : formatComponent ( comp_1 ) );
		currentPanel.add( comp_2 instanceof JComboBox ? formatDropDown ( (JComboBox) comp_2 ) : 
			(comp_2 instanceof JLabel ? formatLargeLabel((JLabel) comp_2) : formatComponent ( comp_2 ) ));
		currentPanel.add( comp_3 instanceof JComboBox ? formatDropDown ( (JComboBox) comp_3 ) : formatComponent ( comp_3 ) );

		return currentPanel;
	}	




	//---------------------------------------------------------------------------
	// This method is called from any of the children of View class when there 
	// is a need for a label in the middle   EX
	//                     OR                       
	//---------------------------------------------------------------------------
	protected JPanel formatMiddleLabel( String label )
	{
		JPanel currentPanel = new BluePanel();
		JLabel newLabel = new JLabel( label );

		newLabel.setFont( myComponentsFont );
		newLabel.setForeground( Color.red.darker() );
		//currentPanel.add(formatComponent(newLabel));
		currentPanel.add( newLabel );
		//currentPanel.setAlignmentY( LEFT_ALIGNMENT );

		return currentPanel;
	}

	/*
	 * 
	 */
	public void resetDayArray(JComboBox theBox, String month, int year)
	{
		String[] dayArray;
		int numDays = 31;
		//Adjust for numbers
		if(month.equals("01") || month.equals("1"))
			month = "January";
		if(month.equals("02") || month.equals("2"))
			month = "February";
		if(month.equals("03") || month.equals("3"))
			month = "March";
		if(month.equals("04") || month.equals("4"))
			month = "April";
		if(month.equals("05") || month.equals("5"))
			month = "May";
		if(month.equals("06") || month.equals("6"))
			month = "June";
		if(month.equals("07") || month.equals("7"))
			month = "July";
		if(month.equals("08") || month.equals("8"))
			month = "August";
		if(month.equals("09") || month.equals("9"))
			month = "September";
		if(month.equals("10") || month.equals("10"))
			month = "October";
		if(month.equals("11") || month.equals("11"))
			month = "November";
		if(month.equals("12") || month.equals("12"))
			month = "December";
		//------------End Adjustments



		//Sep, Ap, June, Nov
		//Jan, Mar, May, July, Aug, Oct, Dec
		//Feb
		if(month.equals("April") || month.equals("June") || month.equals("September") || month.equals("November"))
		{
			numDays = 30;
		}
		else if(month.equals("January") || month.equals("March") || month.equals("May") || month.equals("August") || month.equals("October") || month.equals("December"))
		{
			numDays = 31;
		}
		else if(month.equals("February"))
		{
			boolean leapYear = getLeapYear(year);
			if(leapYear)
				numDays = 29;
			else
				numDays = 28;
		}
		else //Default to 31 in case
		{
			numDays = 31;
		}

		dayArray = new String[numDays];
		for(int x = 1; x <= numDays; x++)
		{
			String prefix = "";
			if ((1 <= x) && (x <=9))
				prefix = "0";
			dayArray[x-1] = "" + prefix + x;
		}
		theBox.removeAllItems();
		theBox.setModel(new DefaultComboBoxModel (dayArray));
	}

	private boolean getLeapYear(int year)
	{
		if(year%4 == 0 && (year%100 != 0 || year%400 == 0))
			return true;
		else
			return false;
	}


	// If you need days with leading zeros, use this method.
	//-----------------------------------------------------------------
	public void resetDayArrayWithLeadingZeros(JComboBox theBox, String month, int year)
	{
		String[] dayArray;
		int numDays = 31;
		//Adjust for numbers
		if(month.equals("01") || month.equals("1"))
			month = "January";
		if(month.equals("02") || month.equals("2"))
			month = "February";
		if(month.equals("03") || month.equals("3"))
			month = "March";
		if(month.equals("04") || month.equals("4"))
			month = "April";
		if(month.equals("05") || month.equals("5"))
			month = "May";
		if(month.equals("06") || month.equals("6"))
			month = "June";
		if(month.equals("07") || month.equals("7"))
			month = "July";
		if(month.equals("08") || month.equals("8"))
			month = "August";
		if(month.equals("09") || month.equals("9"))
			month = "September";
		if(month.equals("10") || month.equals("10"))
			month = "October";
		if(month.equals("11") || month.equals("11"))
			month = "November";
		if(month.equals("12") || month.equals("12"))
			month = "December";
		//------------End Adjustments



		//Sep, Ap, June, Nov
		//Jan, Mar, May, July, Aug, Oct, Dec
		//Feb
		if(month.equals("April") || month.equals("June") || month.equals("September") || month.equals("November"))
		{
			numDays = 30;
		}
		else if(month.equals("January") || month.equals("March") || month.equals("May") || month.equals("August") || month.equals("October") || month.equals("December"))
		{
			numDays = 31;
		}
		else if(month.equals("February"))
		{
			boolean leapYear = getLeapYear(year);
			if(leapYear)
				numDays = 29;
			else
				numDays = 28;
		}
		else //Default to 31 in case
		{
			numDays = 31;
		}

		dayArray = new String[numDays];

		for(int x = 1; x <= 9; x++)
			dayArray[x-1] = "0" + x;

		for(int x = 10; x <= numDays; x++)
			dayArray[x-1] = "" + x;

		theBox.removeAllItems();
		theBox.setModel(new DefaultComboBoxModel (dayArray));
	}

	//-------------------------------------------------------------
	protected void writeToFile(String fName)
	{

	}

//	//--------------------------------------------------------------------------
//	protected void saveToExcelFile()
//	{
//		// Put up JFileChooser
//		// Retrieve full path name of file user selects
//		// Create the file appropriately if it does not exist
//		String reportsPath = System.getProperty("user.dir") + "\\reports";
//		File reportsDir = new File(reportsPath);
//
//		// if the directory does not exist, create it
//		if (!reportsDir.exists())
//		{
//			reportsDir.mkdir();  
//		}
//
//		JFileChooser chooser = new JFileChooser(reportsDir);
//
//		//JFileChooser chooser = new JFileChooser("." + File.separator +
//		//		"ReportFiles");
//
//		CSVFileFilter filter = new CSVFileFilter();
//
//		chooser.setFileFilter(filter);
//
//		try
//		{
//			String fileName = "";
//
//			int returnVal = chooser.showOpenDialog(this);
//
//			if(returnVal == JFileChooser.APPROVE_OPTION)
//			{
//				File selectedFile = chooser.getSelectedFile();
//				//selectedFile.mkdirs();
//				fileName = selectedFile.getCanonicalPath();
//
//				String tempName = fileName.toLowerCase();
//
//				// Check if user specified the file type. If not, add the type.
//				if(tempName.endsWith(".csv"))
//				{
//					writeToFile(fileName);
//				}
//				else
//				{
//					fileName += ".csv";
//					writeToFile(fileName);
//				}
//
//				Desktop desktop = null;
//
//				// Before more Desktop API is used, first check 
//				// whether the API is supported by this particular 
//				// virtual machine (VM) on this particular host.
//				if(Desktop.isDesktopSupported()) 
//				{
//					desktop = Desktop.getDesktop();
//
//					if(desktop.isSupported(Desktop.Action.OPEN))
//					{
//
//						// Custom button text
//						Object[] options = {"Open reports folder",
//								"Open this report",
//						"Do nothing, continue"};
//
//						// Ask user what (s)he wants to do
//						int n = JOptionPane.showOptionDialog(this,
//								"What do you want to do next?",
//								"Export to Excel",
//								JOptionPane.YES_NO_CANCEL_OPTION,
//								JOptionPane.QUESTION_MESSAGE,
//								null,
//								options,
//								options[2]);
//
//						if(n == JOptionPane.YES_OPTION)
//						{
//							desktop.open(reportsDir);
//						}
//						else 
//							if(n == JOptionPane.NO_OPTION)
//							{
//								File reportPath = new File(fileName);
//								desktop.open(reportPath);
//							}
//					}
//				}
//			}
//		}
//		catch (Exception ex)
//		{
//			JOptionPane.showMessageDialog(this, "Error in saving to file: "
//					+ ex.toString());
//		}
//	}
}

