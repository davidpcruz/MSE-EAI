package eai.msejdf.daemons;

import java.io.IOException;
import java.util.Random;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import eai.msejdf.jms.JMSReceiver;
import eai.msejdf.rrd.RrdDatabase;

public class RRDDaemon extends Thread implements MessageListener
{

	/** The Constant DAEMON_CLIENTID. */
	private static final String DAEMON_CLIENTID = "RRDDaemon";

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RRDDaemon.class);

	/** The jms receiver. */
	private JMSReceiver receiver;

	/**
	 * Instantiates a new RRD daemon.
	 * 
	 * @throws JMSException
	 *             the jMS exception
	 */
	public RRDDaemon() throws JMSException
	{
//		receiver = new JMSReceiver(Configuration.getJmsTopicName(), DAEMON_CLIENTID);
//		receiver.setMessageListener(this);
	}

	public void run()
	{
		
		Random generator = new Random();
		RrdDatabase dbase;
        try
        {
	        dbase = new RrdDatabase("test.rrd", "euros");
	        
			long startTimestamp = System.currentTimeMillis() - 30l*24l*60l*60l*1000l;
			long currentTimestamp = startTimestamp;
			
			for (int dayCount = 0; dayCount < 30; dayCount ++) {
				for (int hourCount = 0; hourCount < 24; hourCount ++) {
					for (int minuteCount = 0; minuteCount < 60; minuteCount ++) {
						currentTimestamp = currentTimestamp + 60l*1000l;
						float value = generator.nextInt(100);
						
						dbase.updateData(currentTimestamp/1000, value/100f);
						//System.out.println(currentTimestamp/1000 + ":" + value);
					}
				}
			}
			
			dbase.createRRDGraph( "test_hour.gif", 60l*60l);
			dbase.createRRDGraph( "test_day.gif", 24l*60l*60l);
			dbase.createRRDGraph( "test_week.gif", 7l*24l*60l*60l);
			dbase.createRRDGraph( "test_month.gif", 30l*24l*60l*60l);

        } catch (IOException e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
		


		
		
//		try
//		{
//			receiver.start();
//
//			while (true)
//			{
//				Thread.sleep(500);
//			}
//
//		} 
//		catch (InterruptedException ex)
//		{
//			// exiting the Thread
//		} 
//		catch (JMSException ex)
//		{
//			logger.error("run", ex); //$NON-NLS-1$
//		} 
//		finally
//		{
//			try
//			{
//				receiver.close();
//			} 
//			catch (JMSException ex)
//			{
//				logger.error("run", ex); //$NON-NLS-1$
//			}
//		}
	}


	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		RRDDaemon daemon = null;

		try
		{
			daemon = new RRDDaemon();
			daemon.setDaemon(true);
			daemon.start();

//			java.io.BufferedReader stdin = new java.io.BufferedReader(
//					new java.io.InputStreamReader(System.in));
//			String lineIn = "";

//			do
//			{
//				Thread.sleep(500);
//				System.out.println("Press (q) to exit daemon");
//				lineIn = stdin.readLine();
//				
//			} while (!lineIn.startsWith("q"));
//
//			daemon.interrupt();
			daemon.join();
		} catch (InterruptedException | JMSException ex)
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

