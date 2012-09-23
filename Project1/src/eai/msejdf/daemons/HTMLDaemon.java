package eai.msejdf.daemons;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import eai.msejdf.config.Configuration;
import eai.msejdf.jms.JMSReceiver;

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

		} 
		catch (InterruptedException ex)
		{
			// exiting the Thread
		} 
		catch (JMSException ex)
		{
			logger.error("run", ex); //$NON-NLS-1$
		} 
		finally
		{
			try
			{
				receiver.close();
			} 
			catch (JMSException ex)
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

			java.io.BufferedReader stdin = new java.io.BufferedReader(
					new java.io.InputStreamReader(System.in));
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

	@Override
	public void onMessage(Message msg)
	{
		TextMessage tm = (TextMessage) msg;

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
