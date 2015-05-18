// tabs=4

// specify the package
package exception;

// system imports

// local imports

/** 
 * This class indicates an exception that is thrown if the primary
 * key is not properly supplied to the data access model object as
 * it seeks to retrieve a record from the database
 * 
 */
//--------------------------------------------------------------
public class InvalidPrimaryKeyException
	extends Exception
{	
	/**
	 * Constructor with message
	 *
	 * @param mesg The message associated with the exception
	 */
	//--------------------------------------------------------
	public InvalidPrimaryKeyException(String message)
	{
		super(message);
	}
}

		


