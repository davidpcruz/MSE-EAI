package eai.msejdf.exception;

/**
 * Implementation for security specific exceptions
 */
public class SecurityException extends Exception{
	private static final long serialVersionUID = 8718157429064486545L;
	
	/**
	 * Default constructor 
	 */
	public SecurityException()
	{
		
	}
	
	/** Constructor with specific exception message
	 * @param message User specific message associated with the exception
	 */
	public SecurityException(String message)
	{
		super(message);
	}
}
