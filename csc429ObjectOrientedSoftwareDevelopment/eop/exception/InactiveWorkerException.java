// tabs=4
//************************************************************
//	COPYRIGHT 2013 Sandeep Mitra and students (Andrew Allen,
//  Randy Pawlikowski, Cory Beer-Cunningham, Evan Fleischer,
//  Edward Mallow, Kevin Kelly, Philip Kneupfer, Jonathan H Shields,
//  Kyle Root, Jason La Mendola, Anthony Morse, Brian Humphrey,
//  Loi Truong, Kevin Murphy) 
//    The College at Brockport, State University of New York. -
//	  ALL RIGHTS RESERVED
//
// This file is the product of The College at Brockport and cannot
// be reproduced, copied, or used in any shape or form without
// the express written consent of The College at Brockport.
//************************************************************
// specify the package
package exception;

// system imports

// local imports

/** 
 * This class indicates another exception that is thrown if the login
 * process fails
 *
 */
//--------------------------------------------------------------
public class InactiveWorkerException
	extends Exception
{	
	/**
	 * Constructor with message
	 *
	 * @param mesg The message associated with the exception
	 */
	//--------------------------------------------------------
	public InactiveWorkerException(String message)
	{
		super(message);
	}
}

		


