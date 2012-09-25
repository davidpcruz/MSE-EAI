package eai.msejdf.daemons;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;

import eai.msejdf.config.Configuration;
import eai.msejdf.jms.JMSReceiver;
import eai.msejdf.utils.Transform;

public class HTMLDaemon extends DaemonBase implements MessageListener
{

	/** The Constant DAEMON_CLIENTID. */
	private static final String DAEMON_CLIENTID = "HTMLDaemon";

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(HTMLDaemon.class);

	/** The jms receiver. */
	private JMSReceiver receiver;

	/**
	 * Instantiates a new HTML daemon.
	 * 
	 * @throws JMSException
	 *             the jMS exception
	 */
	public HTMLDaemon() throws JMSException
	{
		receiver = new JMSReceiver(Configuration.getJmsTopicName(), DAEMON_CLIENTID);
		receiver.setMessageListener(this);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("main(String[]) - start"); //$NON-NLS-1$
		}

		HTMLDaemon daemon = null;

		try
		{
			daemon = new HTMLDaemon();
			daemon.run();
		} catch (JMSException ex)
		{
			logger.error("main", ex); //$NON-NLS-1$			
		}
	}

	private void saveHtmlFile(String message) throws IOException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("saveHtmlFile(String) - start"); //$NON-NLS-1$
		}

		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");

		File outputFile = null;
		File directory = new File(".");

		// Create a unique file based on the current time/date
		do
		{
			outputFile = new File(directory.getAbsolutePath() + "/bin/" + dateFormatter.format(new Date()) + ".html");
		} while (false == outputFile.createNewFile());

		// Save the message to the file
		BufferedWriter fileWriter = new BufferedWriter(new FileWriter(outputFile));
		if (logger.isDebugEnabled())
		{
			logger.debug("Writing HTML file: " + directory.getAbsolutePath() + "/bin/" + dateFormatter.format(new Date()) + ".html"); //$NON-NLS-1$
		}
		fileWriter.write(message);
		fileWriter.close();

		if (logger.isDebugEnabled())
		{
			logger.debug("saveHtmlFile(String) - end"); //$NON-NLS-1$
		}
	}

	@Override
	public void onMessage(Message msg)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("onMessage(Message) - start"); //$NON-NLS-1$
		}

		TextMessage tm = (TextMessage) msg;
		String xsltFile = Configuration.getXsltFile();

		String resultFile = null;
		String xmlFile = null;

		try
		{
			xmlFile = tm.getText();
		} catch (JMSException e2)
		{
			logger.error("onMessage(Message)", e2); //$NON-NLS-1$
			e2.printStackTrace();
		}

		try
		{
			resultFile = Transform.xmlTransformation(xmlFile, xsltFile);
			saveHtmlFile(resultFile); // save HTML file
		} catch (TransformerException | IOException e1)
		{
			logger.error("onMessage(Message)", e1); //$NON-NLS-1$
			e1.printStackTrace();
		}

		try
		{
			if (logger.isInfoEnabled())
			{
				logger.info("onMessage(Message) - onMessage, recv text=" + tm.getText()); //$NON-NLS-1$
			}
		} catch (JMSException e)
		{
			logger.error("onMessage(Message)", e); //$NON-NLS-1$
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("onMessage(Message) - end"); //$NON-NLS-1$
		}
	}

	@Override
    public void startDaemon() throws JMSException
    {
	    receiver.start();
	    
    }

	@Override
    public void stopDaemon() throws JMSException
    {
	    receiver.close();	    
    }

}
