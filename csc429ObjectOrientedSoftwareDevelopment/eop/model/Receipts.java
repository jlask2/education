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
package model;

//system imports
import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.print.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Stack;
import java.util.Vector;

//GUI imports
import javax.swing.JOptionPane;
import javax.swing.JFrame;

//project imports
import model.Book;
import userinterface.MainFrame;
import userinterface.ViewL;
import userinterface.ViewFactory;
import exception.InvalidPrimaryKeyException;

public class Receipts{

	private String transactionErrorMessage = "";

	protected JFrame myFrame = MainFrame.getInstance();

	MaxDueDate maxDueDate;

	String dueDate;

	Properties receipt;
	Vector<Rental> receiptRentals;
	Vector<Properties> rentalsToCheckOutVecProps;
	Vector<Properties> rentalsToCheckInVecProps;
	Vector<Properties> rentalsStillCheckedOutVecProps;
	private static String receiptFileName = "";

	//===============================================================		 
	private String receiptHomeDirectory = Librarian.RECEIPTS_DIRECTORY;

	//Constructor for check out a book
	//----------------------------------------------------------------
	public Receipts(Vector<Properties> rentalsToCheckOutVecProps){
		this.rentalsToCheckOutVecProps = rentalsToCheckOutVecProps;
		//this.rentalsStillCheckedOutVecProps = rentalsStillCheckedOutVecProps;   
	}

	//Constructor for check in a book
	//----------------------------------------------------------------
	public Receipts(Vector<Properties> rentalsToCheckInVecProps, Vector<Properties> rentalsStillCheckedOutVecProps){
		this.rentalsToCheckInVecProps = rentalsToCheckInVecProps;
		this.rentalsStillCheckedOutVecProps = rentalsStillCheckedOutVecProps;   
	}

	/*public void addRental(Rental myRental){
		receiptRentals.addElement(myRental);
	}*/

	//----------------------------------------------------------------
	public void printReceipt(Date nowTime, Properties receipt)
	{	
		/*DEBUG*///System.out.println("receiptHomeDirectory: " + receiptHomeDirectory);
		String receiptType = "";
		String transType = "";
		if(((String)receipt.getProperty("PrintTransaction")).equals("CheckIn")){
			receiptType = "CheckinReceipts";
			transType = "Checkin";
		} else if(((String)receipt.getProperty("PrintTransaction")).equals("CheckOut")){
			receiptType = "CheckoutReceipts";
			transType = "Checkout";
		} 		
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowTime);

		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm");

		int nowYear = cal.get(Calendar.YEAR);
		int nowMonth = cal.get(Calendar.MONTH);
		/*DEBUG*///System.out.println("nowYear: "+ nowYear + "nowMonth: " + nowMonth);

		String yearValue = (new Integer(nowYear)).toString();
		String monthName = /*CalendarUtilities.*/mapMonthToString(nowMonth);
		/*DEBUG*///System.out.println("yearValue: "+ yearValue + "monthName: " + monthName);
		String receiptDirectoryName = receiptHomeDirectory
				+ File.separator + receiptType + File.separator + yearValue + File.separator + monthName;
		/*DEBUG*///System.out.println("receiptDirectoryName: "+ receiptDirectoryName);
		String nowTimeText = dateFormatter.format(nowTime);     
		String borrowerName = (String)receipt.get("BorrowerName");
		/*DEBUG*///System.out.println("nowTimeText: " + nowTimeText + " borrowerName: " + borrowerName);

		File receiptDirectory = new File(receiptDirectoryName);
		if (receiptDirectory.exists() == false)
		{
			boolean flag = receiptDirectory.mkdirs();
			/*DEBUG*///System.out.println("flag: " + flag);
			if (flag == true)
			{
				receiptFileName = receiptDirectoryName + File.separator +
						borrowerName + transType + nowTimeText + ".txt";
				/*DEBUG*///System.out.println("receiptFileName: "+ receiptFileName);
			}
			else
			{
				JOptionPane.showMessageDialog(myFrame,
						"ERROR: Could not create directory to store printed "+ transType +"."
								+ "\nYou may need to check the permissions on the directory path."
								+ "\n\nAn attempt will be made to store the receipt in the current working directory."
								, "Directory Creation Error", JOptionPane.ERROR_MESSAGE);
				receiptFileName = borrowerName +
						transType + nowTimeText + ".txt";
				/*DEBUG*///System.out.println("receiptFileName else: "+ receiptFileName);
			}
		}
		else
		{
			receiptFileName = receiptDirectoryName + File.separator +
					borrowerName + transType + nowTimeText + ".txt";
			/*DEBUG*///System.out.println("receiptFileName else2: " + receiptFileName);
		}
		try{
			maxDueDate = new MaxDueDate();
			dueDate = (String)maxDueDate.getState("CurrentMaxDueDate");
		}catch (InvalidPrimaryKeyException e) {
			transactionErrorMessage = "No Max Due Date Found.";
		}
		writeReceiptDataToFile(receiptFileName, receipt);
		printFileToDefaultPrinter(receiptFileName);   
	}

	//---------------------------------------------------------
	private void writeReceiptDataToFile(String fileName, Properties receipt) {              
		String receiptType = "";
		String transType = "";
		if(((String)receipt.getProperty("PrintTransaction")).equals("CheckIn")){
			receiptType = "CheckinReceipts";
			transType = "Checkin";
		} else if(((String)receipt.getProperty("PrintTransaction")).equals("CheckOut")){
			receiptType = "CheckoutReceipts";
			transType = "Checkout";
		}
		BufferedWriter outputFile = null;

		try
		{
			outputFile = new BufferedWriter(new FileWriter(new File(fileName)));

			outputFile.newLine();
			outputFile.newLine();
			outputFile.write("                          The Educational Opportunity Program\n");
			outputFile.write("                                  Book Loan Contract\n");
			outputFile.newLine();
			outputFile.write("I have received the following listed items from the Educational Opportunity Program.\n"
					+ "I understand that these items are being loaned to me for my personal use during\n"
					+ "spring 2014.\n" );
			outputFile.newLine();
			outputFile.write("__________________________________________________________________________________\n");
			outputFile.write("Date: " + (String)receipt.get("ReceiptTime") + "\n");
			outputFile.newLine();
			outputFile.write("(BannerID) Student Borrower Name: \t\t" + " (" + (String)receipt.get("BorrowerID") + ") "
					+ (String)receipt.get("BorrowerName") + "\n");
			outputFile.newLine();
			if(transType.equals("Checkout")){
				outputFile.write("(BannerID) Check-Out Worker Name: \t\t" + " (" + (String)receipt.get("CheckOutWorkerBannerID") + ") "
						+ (String)receipt.get("CheckOutWorkerName") + "\n") ;
			}else if(transType.equals("Checkin")){            
				outputFile.write("(BannerID) Check-In Worker Name: \t\t" + " (" + (String)receipt.get("CheckInWorkerBannerID") + ") "
						+ (String)receipt.get("CheckInWorkerName") + "\n");
			}
			outputFile.newLine();
			if(transType.equals("Checkin")){ 
				outputFile.write("\nThese are the books you checked in.\n");
			}
			outputFile.write("__________________________________________________________________________________\n");
			outputFile.write("Barcode\t\tBook Title\t\t\t\tSubject\n");
			outputFile.write("__________________________________________________________________________________\n");
			outputFile.newLine();

			// Put all the rentals in
			if(transType.equals("Checkin")){
				writeAllRentalsRetrieved(rentalsToCheckInVecProps, outputFile, fileName);
			}else if(transType.equals("Checkout")){
				writeAllRentalsRetrieved(rentalsToCheckOutVecProps, outputFile, fileName);
			}
			if(transType.equals("Checkin")){
				writeAllRentalsRetrieved(rentalsStillCheckedOutVecProps, outputFile, fileName);
			}	

			outputFile.write("__________________________________________________________________________________\n");
			outputFile.newLine();
			outputFile.write("By signing below. I acknowledge that i have received these items and I agree to\n" 
					+ "return the books at the end of the spring no later than " + dueDate + " I am responsible\n" 
					+ "for the replacement of those books that are not returned by this date.\n");
			outputFile.newLine();
			outputFile.write("                        THE ONLY PERSON WHO CAN RETURN YOUR\n"+
					"                                    BOOKS IS YOU!\n");
			outputFile.newLine();
			outputFile.newLine();
			outputFile.write("A hold may be placed on your Brockport account if these books are not returned.\n" 
					+ "This hold will remain on your account until either the books are returned or you\n" 
					+ "have paid to replace the books in question. Please note this hold will prevent \n"
					+ "you from registering or acquiring a transcript.\n");
			outputFile.newLine();
			outputFile.newLine();
			outputFile.newLine();
			outputFile.write("_____________________________________         _________________________________\n");
			outputFile.write("             Signature                                      Date\n");
			outputFile.newLine();
			outputFile.newLine();
			outputFile.write("_____________________________________\n");
			outputFile.write("             Local Phone\n");

			outputFile.close();   
		}
		catch(IOException e)
		{
			JOptionPane.showMessageDialog(myFrame,
					"ERROR: Could not write to receipt file: " + fileName,
					"Receipt File Write Error", JOptionPane.ERROR_MESSAGE); 
		}
	}

	//----------------------------------------------------------------
	protected void printFileToDefaultPrinter(String fileName)
	{
		/*DEBUG*///System.out.println("Inside printToFileDefaultPrinter");
		Runtime rt = Runtime.getRuntime();
		String cmd = "notepad /p " + fileName;
		try
		{
			rt.exec(cmd);
			//PrintFileToPrinter printToFile = new PrintFileToPrinter();
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(myFrame,
					"ERROR: Printer error: " + ex.toString(),
					"Printer Error", JOptionPane.ERROR_MESSAGE); 
		}
	}


	protected void writeAllRentalsRetrieved(Vector rentalsInReceipt, BufferedWriter outputFile, String fileName){
		try{  
			Vector allRentalsInReceipt = rentalsInReceipt;
			/*DEBUG*///System.out.println("allRentalsInReceipt.size(): " + allRentalsInReceipt.size());
			if(allRentalsInReceipt != null)
			{
				if(rentalsInReceipt == rentalsStillCheckedOutVecProps){
					outputFile.write("\nThese books are still outstanding and are due back by: " + dueDate + "\n");  
					outputFile.write("__________________________________________________________________________________\n");
					outputFile.write("Barcode\t\tBook Title\t\t\t\tSubject\n");
					outputFile.write("__________________________________________________________________________________\n\n");
				}
				/*DEBUG*///System.out.println("Inside allRentals IF");
				for (int cnt = 0; cnt < allRentalsInReceipt.size(); cnt++)
				{
					Properties rentalInfo = (Properties)allRentalsInReceipt.elementAt(cnt);

					String barcodeID = (String)rentalInfo.getProperty("BookID");
					outputFile.write(barcodeID + "\t\t");
					/*DEBUG*/System.out.println("barcodeID length: " + barcodeID.length());
					try{
						/*DEBUG*///System.out.println("BookID: "+ barcodeID);
						Book bTitle = new Book((String)rentalInfo.getProperty("BookID"));

						String bookTitle = (String)bTitle.getState("Title");
						/*DEBUG*///System.out.println("bookTitle length: " + bookTitle.length());
						/*DEBUG*///System.out.println("Book Title: "+ bookTitle);
						if(bookTitle.length()>40){
							outputFile.write(bookTitle.substring(0,35) + "...  ");
						}else{
							int fillSpace = 39 - bookTitle.length();
							outputFile.write(bookTitle + " ");
							for(int i = 0; i < fillSpace; i++){
								outputFile.write(" ");
							}
						}
						String subject = (String)bTitle.getState("Discipline");
						/*DEBUG*/System.out.println("subject length: " + subject.length());
						if(subject.length()>30){
							outputFile.write(subject.substring(0,25) + "... \n");
						}else{
							outputFile.write(subject + "\n");
						}
					} catch (InvalidPrimaryKeyException e) {
						transactionErrorMessage = "No Book found with this barcode.";
					}         
					outputFile.newLine();
				}
			}
		}
		catch(IOException e)
		{
			JOptionPane.showMessageDialog(myFrame,
					"ERROR: Could not write to receipt file: " + fileName,
					"Receipt File Write Error", JOptionPane.ERROR_MESSAGE); 
		}
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
}