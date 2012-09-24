package eai.msejdf.jms;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Topic;

import org.apache.log4j.Logger;

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
	public JMSReceiver(String topicName, String connectionID) throws JMSException
	{
		super(topicName, connectionID);
		// set the message consumer
		this.messConsumer = session.createDurableSubscriber((Topic) dest, SUBCRIBER_NAME, null, false);
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

	/* (non-Javadoc)
	 * @see eai.msejdf.jms.JMSBase#start()
	 */
	@Override
    public void start() throws JMSException
    {
		if (logger.isDebugEnabled())
		{
			logger.debug("start() - start"); //$NON-NLS-1$
		}

		this.connStart();
		
		if (logger.isDebugEnabled())
		{
			logger.debug("start() - end"); //$NON-NLS-1$
		}	    
    }

	/**
	 * Close.
	 *
	 * @throws JMSException the jMS exception
	 */
	@Override
	public void close() throws JMSException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("close() - start"); //$NON-NLS-1$
		}

		try
		{
			this.session.unsubscribe(SUBCRIBER_NAME);
		} 
		catch (Throwable e)
		{
			logger.error("close()", e); //$NON-NLS-1$
		}

		this.connClose();
		
		if (logger.isDebugEnabled())
		{
			logger.debug("close() - end"); //$NON-NLS-1$
		}
	}

	
}
