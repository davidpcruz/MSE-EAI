/**
 * 
 */
package eai.msejdf.config;

import java.io.File;
import java.net.URL;
import java.util.Properties;

import eai.msejdf.utils.PropertyLoader;

/**
 * @author dcruz
 * 
 */
public final class Configuration
{

	/** The Constant PROPSFILE. defining the name of the configuration file */
	private static final String PROPSFILE = "config.properties";

	/** The Constant JMSCONN_USER. defining the JMS username for the connection. */
	private static final String JMSCONN_USER = "app.jms.applicationuser";

	/** The Constant JMSCONN_PASS defining the JMS password for the connection. */
	private static final String JMSCONN_PASS = "app.jms.applicationpassword";

	/** The Constant JMSTOPIC_NAME. */
	private static final String JMSTOPIC_NAME = "app.jms.topicbasename";

	/** The Constant containing the address to XLST file */
	private static final String XSLT_FILE = "app.xml.xsltfile";

	/** The Constant containing the directory for the HTML files */
	private static final String HTML_FILE_DIRECTORY = "app.xml.htmlfiledirectory"; 

	/** The Constant containing the default output dir */
	private static final String DIRECTORY_BASE_OUTPUT = "app.base.outputdir";  

	/** The Constant containing the directory for the rrd outputs */
	private static final String DIRECTORY_RRD_OUTPUT = "app.rrd.outputdir"; 
	

	/** The Constant containing the directory for the pending messages directory */
	private static final String DIRECTORY_PENDING_MESSAGES = "app.xml.pendingmessagesdirectory"; 

	/** The Constant containing the  connection timeout time */
	private static final String CONNECTION_TIMEOUT = "app.web.connectiontimeout"; 

	/** The Constant containing the  connection timeout time */
	private static final String SMTP_HOST = "mail.smtp.host"; 	
	
	private static Properties props;

	/**
	 * Gets the properties.
	 * 
	 * @return the properties
	 */
	private static Properties getProperties()
	{
		if (props == null)
		{
			props = PropertyLoader.loadProperties(PROPSFILE);
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
	 * Gets the XSLT file
	 * related to the bin directory
	 * 
	 * @return the XSLT_FILE
	 */
	public static String getXsltFile()
	{
		URL url = Thread.currentThread().getContextClassLoader().getResource(".");
		String xsltSetting = getProperties().getProperty(XSLT_FILE);

		File xsltFile = new File(url.getPath() + xsltSetting);

		return xsltFile.getPath();
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
	
	/**
	 * Gets the directoy for pending messages.
	 * 
	 * @return the DATA_RECEIVER_NAME
	 */
	public static String getPendingMessagesDirectory()
	{
		return getProperties().getProperty(DIRECTORY_PENDING_MESSAGES);
	}
	
	/**
	 * Gets the directoy for pending messages.
	 * 
	 * @return the DATA_RECEIVER_NAME
	 */
	public static int getConnectionTimeOut()
	{
		return  Integer.parseInt(getProperties().getProperty(CONNECTION_TIMEOUT));
	}
	

	/**
	 * Gets the directory for the RRD databases.
	 *
	 * @return the DIRECTORY_RRD_OUTPUT
	 */
	public static String getRrdDirectory()
	{
		return getProperties().getProperty(DIRECTORY_RRD_OUTPUT);
	}
		
	
	/**
	 * Gets the default output dir.
	 *
	 * @return the default output dir
	 */
	public static File getDefaultOutputDir()
	{
		URL url = Thread.currentThread().getContextClassLoader().getResource(".");
		
		File directory = new File(url.getPath() + getProperties().getProperty(DIRECTORY_BASE_OUTPUT));
		if (!directory.exists())
		{
			directory.mkdir();
		}
		
		return directory;

	}
	
	/**
	 * Gets the smtp host.
	 *
	 * @return the smtp host
	 */
	public static String getSmtpHost()
	{
		return getProperties().getProperty(SMTP_HOST);
	}

}
