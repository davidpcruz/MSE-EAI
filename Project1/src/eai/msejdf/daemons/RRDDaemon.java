package eai.msejdf.daemons;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

import eai.msejdf.config.Configuration;
import eai.msejdf.data.Company;
import eai.msejdf.data.Cotation;
import eai.msejdf.data.Stock;
import eai.msejdf.data.Stocks;
import eai.msejdf.jms.JMSReceiver;
import eai.msejdf.rrd.RrdDatabase;
import eai.msejdf.utils.XmlObjConv;

// TODO: Auto-generated Javadoc
/**
 * The Class RRDDaemon.
 */
public class RRDDaemon extends DaemonBase implements MessageListener
{

	/** The Constant DAEMON_CLIENTID. */
	private static final String DAEMON_CLIENTID = "RRDDaemon";

	/** Logger for this class. */
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
		receiver = new JMSReceiver(Configuration.getJmsTopicName(), DAEMON_CLIENTID);
		receiver.setMessageListener(this);
	}

	/**
 * The main method.
 *
 * @param args the arguments
 */
	public static void main(String[] args)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("main(String[]) - start"); //$NON-NLS-1$
		}
		
		try
		{
			RRDDaemon daemon = new RRDDaemon();
			daemon.run();
		} 
		catch (JMSException ex)
		{
			logger.error("main", ex); //$NON-NLS-1$			
		}
	}		

	/* (non-Javadoc)
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@Override
	public void onMessage(Message msg)
	{
		TextMessage tm = (TextMessage) msg;

		try
		{
			String msgSent = tm.getText();
			if (logger.isInfoEnabled())
			{
				logger.info("onMessage(Message) - onMessage, recv text=" + msgSent); //$NON-NLS-1$
			}
			
			// try to get the object
			Stocks objMsg = XmlObjConv.convertToObject(msgSent, Stocks.class);
			
			for (Stock quote : objMsg.getStock())
			{
				addStockToRRD(objMsg.getTimestamp().longValue(), quote);
			}
			
		} catch (JMSException | JAXBException | IOException e)
		{
			logger.error("onMessage(Message)", e); //$NON-NLS-1$
		}

	}

	/**
	 * Adds the stock to rrd database.
	 *
	 * @param timestamp the timestamp
	 * @param stockValue the stock value
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void addStockToRRD(long timestamp, Stock stockValue) throws IOException
	{
		// basic validations
		if (stockValue == null)
		{
			throw new IllegalArgumentException("stockValue");
		}
		if (stockValue.getCompany() == null)
		{
			throw new IllegalArgumentException("stockValue.getCompany()");
		}
		if (stockValue.getCotation() == null)
		{
			throw new IllegalArgumentException("stockValue.getCotation()");
		}

		Company comp = stockValue.getCompany();
		Cotation quote = stockValue.getCotation();
		String dbfilename = String.format("%s.rrd", comp.getName());

		RrdDatabase dbase = new RrdDatabase(dbfilename, "euros");

		dbase.updateData(timestamp, quote.getLastCotation().floatValue());

		// TODO
		// dbase.createRRDGraph("test_hour.gif", 60l * 60l);
		// dbase.createRRDGraph("test_day.gif", 24l * 60l * 60l);
		// dbase.createRRDGraph("test_week.gif", 7l * 24l * 60l * 60l);
		// dbase.createRRDGraph("test_month.gif", 30l * 24l * 60l * 60l);

	}
	
	/* (non-Javadoc)
	 * @see eai.msejdf.daemons.DaemonBase#startDaemon()
	 */
	@Override
    public void startDaemon() throws JMSException
    {
	    receiver.start();
	    
    }

	/* (non-Javadoc)
	 * @see eai.msejdf.daemons.DaemonBase#stopDaemon()
	 */
	@Override
    public void stopDaemon() throws JMSException
    {
	    receiver.close();	    
    }	
}
