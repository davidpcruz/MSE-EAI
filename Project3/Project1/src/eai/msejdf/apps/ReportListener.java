package eai.msejdf.apps;

import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import eai.msejdf.config.Configuration;
import eai.msejdf.esb.ReportAggregated;

public class ReportListener implements MessageListener
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ReportListener.class);

	private static final String ENV_LOOKUP_FACTORY = "ConnectionFactory";
	private static final String ENV_URL_PKG_PREFIXES = "org.jboss.naming:org.jnp.interfaces";
	private static final String ENV_INITIAL_CONTEXT_FACTORY = "org.jnp.interfaces.NamingContextFactory";
	private QueueConnection conn;
	private QueueSession session;
	private Queue esbqueue;
	private QueueReceiver queueReceiver;
	

	public static void main(String[] args) throws Exception
	{
		logger.info("ReportListener: Running..."); //$NON-NLS-1$

		ReportListener listner = new ReportListener();

		listner.run();

		logger.info("ReportListener: Running...COMPLETE"); //$NON-NLS-1$
	}

	/**
	 * Main processing function. It parses a web page to extract specific
	 * content, sending a transformed representation to remote clients for
	 * further processing
	 * 
	 * @throws Exception
	 */
	public void run() throws Exception
	{
		Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY, ENV_INITIAL_CONTEXT_FACTORY);
		env.put(Context.URL_PKG_PREFIXES, ENV_URL_PKG_PREFIXES);
		env.put(Context.PROVIDER_URL, Configuration.getESBReplicatorProvider());

		InitialContext iniCtx;
		try
		{
			iniCtx = new InitialContext(env);
			Object tmp = iniCtx.lookup(ENV_LOOKUP_FACTORY);
			QueueConnectionFactory qcf = (QueueConnectionFactory) tmp;
			this.conn = qcf.createQueueConnection();
			this.esbqueue = (Queue) iniCtx.lookup(Configuration.getESBReportQueue());
			this.session = conn.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
			this.queueReceiver = session.createReceiver(this.esbqueue);
			this.queueReceiver.setMessageListener(this);

			conn.start();
			
			java.io.BufferedReader stdin = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
			String lineIn = "";

			do
			{
				System.out.println("Press (q) to exit report viewer");
				lineIn = stdin.readLine();

			} while (!lineIn.startsWith("q"));			

		} catch (NamingException | JMSException ex)
		{
			logger.error("run", ex); //$NON-NLS-1$
			throw ex;
		} finally
		{
			if (conn != null)
			{
				conn.stop();
				if (session != null)
					session.close();
				conn.close();
			}
		}

	}

	@Override
	public void onMessage(Message msg)
	{
		ObjectMessage om = (ObjectMessage) msg;

		try
        {
			ReportAggregated report = (ReportAggregated) om.getObject(); 
			
			printAggregatedReport(report);
			
        } catch (JMSException ex)
        {
        	logger.error("run", ex); //$NON-NLS-1$
        }
	}

	private void printAggregatedReport(ReportAggregated report)
    {
		System.out.println("\n&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		System.out.println(String.format("%s Companies Processed", report.getCompaniesProcessed()));
		System.out.println(String.format("%s Users Automatically Warned", report.getUsersWarnedAutomatically()));
		System.out.println(String.format("%s Users Warned by Manager", report.getUsersWarnedByManager()));
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
	    
    }
}
