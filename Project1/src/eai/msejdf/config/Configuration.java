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

	/** The Constant containing the address to XLST file */
	private static final String XSLT_FILE = "C:\\Users\\joaofcr\\workspace\\Sender\\src\\StockMarket.xsl";
	
	/** The Constant containing the directory for the HTML files */
	private static final String HTML_FILE_DIRECTORY = "C:\\";
	
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
	/**
	 * Gets the XSLT filer.
	 *
	 * @return the XSLT_FILE
	 */
	public static String getXsltFile()
	{
		return XSLT_FILE;
	}
	/**
	 * Gets the HTML directory.
	 *
	 * @return the HTML_FILE_DIRECTORY
	 */
	public static String getHtmlDirectory()
	{
		return HTML_FILE_DIRECTORY;
	}
}
