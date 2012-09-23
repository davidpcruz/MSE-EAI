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

	/**
	 * @return the jmsconnUser
	 */
	public static String getJmsConnUser()
	{
		return JMSCONN_USER;
	}

	/**
	 * @return the jmsconnPass
	 */
	public static String getJmsconnPass()
	{
		return JMSCONN_PASS;
	}

}
