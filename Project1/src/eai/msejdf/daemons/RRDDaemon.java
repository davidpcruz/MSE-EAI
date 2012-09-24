package eai.msejdf.daemons;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Random;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import eai.msejdf.config.Configuration;
import eai.msejdf.data.Company;
import eai.msejdf.data.Cotation;
import eai.msejdf.data.Stock;
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
		receiver = new JMSReceiver(Configuration.getJmsTopicName(), DAEMON_CLIENTID);
		receiver.setMessageListener(this);
	}

	public void run()
	{

		try
		{
			receiver.start();

			/// TESTING
			Random generator = new Random();
			Stock stock = new Stock();
			
			Company company = new Company();
			company.setName("RRDDaemon");
			
			Cotation quote = new Cotation();			
			
			stock.setCompany(company);
			stock.setCotation(quote);
			/// TESTING
			while (true)
			{
				Thread.sleep(1000);
				quote.setLastCotation(new BigDecimal(generator.nextInt(100)));
				addStockToRRD(System.currentTimeMillis(), stock);
				
				logger.debug("done"); //$NON-NLS-1$
			}

		} catch (InterruptedException ex)
		{
			// exiting the Thread
		} catch (JMSException | IOException ex)
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
		RRDDaemon daemon = null;

		try
		{
			daemon = new RRDDaemon();
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
		} catch (InterruptedException | JMSException | IOException ex)
		{
			logger.error("main", ex); //$NON-NLS-1$			
			daemon.interrupt();
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

	/**
	 * Adds the stock to rrd database.
	 * 
	 * @param timestamp
	 *            the timestamp
	 * @param stockValue
	 *            the stock value
	 * @throws IOException
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
}
