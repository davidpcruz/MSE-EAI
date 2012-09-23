/**
 * 
 */
package eai.msejdf.config;

/**
 * @author dcruz
 *
 */
public final class Configuration
{
		
	/** The Constant JMSCONN_USER. defining the JMS username for the connection. */
	private static final String JMSCONN_USER = "eaiuser";

	/** The Constant JMSCONN_PASS defining the JMS password for the connection. */
	private static final String JMSCONN_PASS = "1";

	/** The Constant JMSTOPIC_NAME. */
	private static final String JMSTOPIC_NAME = "EAIProject1";

	
	/**
	 * Gets the jms conn user.
	 *
	 * @return the jmsconnUser
	 */
	public static String getJmsConnUser()
	{
		return JMSCONN_USER;
	}

	/**
	 * Gets the jmsconn pass.
	 *
	 * @return the jmsconnPass
	 */
	public static String getJmsConnPass()
	{
		return JMSCONN_PASS;
	}

	/**
	 * Gets the jms topic name.
	 *
	 * @return the jmstopicName
	 */
	public static String getJmsTopicName()
	{
		return JMSTOPIC_NAME;
	}
}
