package eai.msejdf.daemons;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

public class HTMLDaemon extends Thread implements MessageListener
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

	public void run()
	{
		try
		{
			receiver.start();

			while (true)
			{
				Thread.sleep(500);
			}

		} catch (InterruptedException ex)
		{
			// exiting the Thread
		} catch (JMSException ex)
		{
			logger.error("run", ex); //$NON-NLS-1$
		} finally
		{
			try
			{
				receiver.close();
			} catch (JMSException ex)
			{
				logger.error("run", ex); //$NON-NLS-1$
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		HTMLDaemon daemon = null;

		try
		{
			daemon = new HTMLDaemon();
			daemon.setDaemon(true);
			daemon.start();

			java.io.BufferedReader stdin = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
			String lineIn = "";

			do
			{
				Thread.sleep(500);
				System.out.println("Press (q) to exit daemon");
				lineIn = stdin.readLine();

			} while (!lineIn.startsWith("q"));

			daemon.interrupt();
			daemon.join();
		} catch (InterruptedException | IOException | JMSException ex)
		{
			daemon.interrupt();
			logger.error("main", ex); //$NON-NLS-1$			
		}

	}
	private void saveHtmlFile(String message) throws IOException
	{
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
		String htmlDirectory = Configuration.getHtmlDirectory();
		File outputFile = null; 
		
		// Create a unique file based on the current time/date
		do
		{
			outputFile = new File(htmlDirectory + "/" + dateFormatter.format(new Date()) + ".html"); 
		}
		while (false == outputFile.createNewFile());

		// Save the message to the file
		BufferedWriter fileWriter = new BufferedWriter(new FileWriter(outputFile));
		fileWriter.write(message);
		fileWriter.close();
	}
	@Override
	public void onMessage(Message msg)
	{
		TextMessage tm = (TextMessage) msg;
		String xsltFile = Configuration.getXsltFile();

		String resultFile = null;
		String xmlFile = null;
		
		try
        {
	        xmlFile=tm.getText();
        } catch (JMSException e2)
        {
	        // TODO Auto-generated catch block
	        e2.printStackTrace();
        }
		
		try
        {
	        resultFile = Transform.xmlTransformation(xmlFile, xsltFile);
        } catch (UnsupportedEncodingException | TransformerException  e1)
        {
	        // TODO Auto-generated catch block
	        e1.printStackTrace();
        }
		
		//save the html file
		try
        {
	        saveHtmlFile(resultFile); 
        } catch (IOException e1)
        {
        	logger.error("impossible to write html file ",   e1); //$NON-NLS-1$
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

	}

}
