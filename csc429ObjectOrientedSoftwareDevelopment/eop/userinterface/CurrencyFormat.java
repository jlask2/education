// tabs=4
//************************************************************
//	COPYRIGHT 2009 Sandeep Mitra and Michael Steves, The
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
import java.text.NumberFormat;
import java.text.ParseException;


// project imports


//==============================================================
public class CurrencyFormat
{
	// private data


	// Class constructor
	//----------------------------------------------------------
	public CurrencyFormat()
	{

	}


	//-------------------------------------------------------------
	public static String toCurrencyDisplay(String moneyStr)
	{
		// Declare and create the converter
		NumberFormat formatter = NumberFormat.getCurrencyInstance();

		// First, convert to a double
		double money = Double.parseDouble(moneyStr);
		return formatter.format(money);

	} 

	//-------------------------------------------------------------
	public static String checkCurrencyInput(String moneyStr) throws
	NumberFormatException
	{
		// Declare and create the converter
		NumberFormat formatter = NumberFormat.getCurrencyInstance();

		double money = 0.0;
		try
		{
			money = formatter.parse(moneyStr).doubleValue();
			return Double.toString(money);	
		}
		catch (ParseException ex)
		{
			try
			{
				money = Double.parseDouble(moneyStr);
				return Double.toString(money);	
			}
			catch (NumberFormatException excep)
			{
				throw excep;
			}

		}

	}
}


