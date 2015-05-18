// tabs=4

// specify the package
package exception;

// system imports

// local imports

/** 
 * This class indicates an exception that is thrown if the login
 * process fails
 *
 */
//--------------------------------------------------------------
public class PasswordMismatchException
	extends Exception
{	
	/**
	 * Constructor with message
	 *
	 * @param mesg The message associated with the exception
	 */
	//--------------------------------------------------------
	public PasswordMismatchException(String message)
	{
		super(message);
	}
}

		


