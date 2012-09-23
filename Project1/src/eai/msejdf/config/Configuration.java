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
	private static final String JMSCONN_USER = "joao";

	/** The Constant JMSCONN_PASS defining the JMS password for the connection. */
	private static final String JMSCONN_PASS = "pedro";

	/** The Constant JMSCONN_CONNID defining the JMS connection ID for the connection. */
	private static final String JMSCONN_CONNID = "MyClientId";

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
	 * Gets the jmsconn id.
	 *
	 * @return the jmsconn id
	 */
	public static String getJmsConnId()
	{
		return JMSCONN_CONNID;
	}
}
