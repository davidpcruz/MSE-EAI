package eai.msejdf.exception;

public class ConfigurationException extends Exception {
	private static final long serialVersionUID = 2440464316790191087L;

	/**
	 * Default constructor 
	 */
	public ConfigurationException()
	{
		
	}
	
	/** Constructor with specific exception message
	 * @param message User specific message associated with the exception
	 */
	public ConfigurationException(String message)
	{
		super(message);
	}

}
