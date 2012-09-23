package eai.msejdf.jms;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Topic;

import org.apache.log4j.Logger;
import org.hornetq.api.jms.HornetQJMSClient;

import eai.msejdf.utils.StringUtils;

public class JMSReceiver extends JMSBase 
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(JMSReceiver.class);

	/** The Constant SUBCRIBER_NAME. */
	private static final String SUBCRIBER_NAME = "Subcriber";	
	/** The message consumer. */
	protected MessageConsumer messConsumer;

	/**
	 * Instantiates a new jMS receiver.
	 *
	 * @throws JMSException the jMS exception
	 */
	public JMSReceiver(String connectionID) throws JMSException
	{
		super(connectionID);
	}

	/* (non-Javadoc)
	 * @see eai.msejdf.jms.JMSBase#createTopic(java.lang.String)
	 */
	@Override
	public void createTopic(String topicName) throws JMSException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("createTopic(String) - start"); //$NON-NLS-1$
		}

		// basic validations
		if (StringUtils.isNullOrEmpty(topicName))
		{
			throw new IllegalArgumentException("topicName");
		}
		
		this.dest = HornetQJMSClient.createTopic(topicName); 
		this.messConsumer = session.createDurableSubscriber((Topic) dest, SUBCRIBER_NAME, null, false);		

		if (logger.isDebugEnabled())
		{
			logger.debug("createTopic(String) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Sets the message listener.
	 *
	 * @param listener the new message listener
	 * @throws JMSException the jMS exception
	 */
	public void setMessageListener(MessageListener listener) throws JMSException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("setMessageListener(MessageListener) - start"); //$NON-NLS-1$
		}

		messConsumer.setMessageListener(listener);		

		if (logger.isDebugEnabled())
		{
			logger.debug("setMessageListener(MessageListener) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Close.
	 *
	 * @throws JMSException the jMS exception
	 */
	public void closeConn() throws JMSException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("closeConn() - start"); //$NON-NLS-1$
		}

		if(this.conn != null)
		{
			this.conn.stop();
			try
			{
				this.session.unsubscribe(SUBCRIBER_NAME);
			} 
			catch (Exception e)
			{
				logger.error("closeConn()", e); //$NON-NLS-1$
			}
			this.conn.close();
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("closeConn() - end"); //$NON-NLS-1$
		}
	}
	
}
