/**
 * 
 */
package eai.msejdf.config;

import java.util.Properties;

import eai.msejdf.utils.PropertyLoader;

/**
 * @author dcruz
 *
 */
public final class Configuration
{
	
    /** The Constant PROPSFILE. defining the name of the configuration file */
    public final static String PROPSFILE = "config.properties";

	/** The Constant JMSCONN_USER. defining the JMS username for the connection. */
	private static final String JMSCONN_USER = "app.jms.applicationuser";

	/** The Constant JMSCONN_PASS defining the JMS password for the connection. */
	private static final String JMSCONN_PASS = "app.jms.applicationpassword";

	/** The Constant JMSTOPIC_NAME. */
	private static final String JMSTOPIC_NAME = "app.jms.topicbasename";
	
	/** The Constant containing the address to XLST file */
	private static final String XSLT_FILE = "app.jms.xsltfile"; //"C:\\Users\\joaofcr\\workspace\\Sender\\src\\StockMarket.xsl";
	
	/** The Constant containing the directory for the HTML files */
	private static final String HTML_FILE_DIRECTORY = "app.jms.htmlfiledirectory";  // "C:\\";
	
    private static Properties props;

    /**
     * Gets the properties.
     *
     * @return the properties
     */
    private static Properties getProperties()
    {
        if(props == null)
        {
                props = PropertyLoader.loadProperties(PROPSFILE) ;
        }
        return props;
    }
	
	

	
	/**
	 * Gets the jms conn user.
	 *
	 * @return the jmsconnUser
	 */
	public static String getJmsConnUser()
	{
		return getProperties().getProperty(JMSCONN_USER);
	}

	/**
	 * Gets the jmsconn pass.
	 *
	 * @return the jmsconnPass
	 */
	public static String getJmsConnPass()
	{
		return getProperties().getProperty(JMSCONN_PASS);
	}

	/**
	 * Gets the jms topic name.
	 *
	 * @return the jmstopicName
	 */
	public static String getJmsTopicName()
	{
		return getProperties().getProperty(JMSTOPIC_NAME);
	}
	
	/**
	 * Gets the XSLT filer.
	 *
	 * @return the XSLT_FILE
	 */
	public static String getXsltFile()
	{
		return getProperties().getProperty(XSLT_FILE);
	}
	/**
	 * Gets the HTML directory.
	 *
	 * @return the HTML_FILE_DIRECTORY
	 */
	public static String getHtmlDirectory()
	{
		return getProperties().getProperty(HTML_FILE_DIRECTORY);
	}
}
