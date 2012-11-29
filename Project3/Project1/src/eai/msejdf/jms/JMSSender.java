/**
 * 
 */
package eai.msejdf.jms;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

/**
 * The Class JMSSender.
 *
 * @author dcruz
 */
public class JMSSender extends JMSBase
{
	
	/** Logger for this class. */
	private static final Logger logger = Logger.getLogger(JMSSender.class);

	/** The message producer. */
	protected MessageProducer messProducer;

	/**
	 * Instantiates a new jMS sender.
	 *
	 * @param topicName the topic name
	 * @throws JMSException the jMS exception
	 */
	public JMSSender(String topicName) throws JMSException
	{
		super(topicName, null);
		// set the message Producer
		this.messProducer = this.session.createProducer(this.dest);				
	}
	
	/**
	 * Send message msg through the JMS system.
	 *
	 * @param msg the msg
	 * @throws JMSException the jMS exception
	 */
	public void sendMessage(String msg) throws JMSException 
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("sendMessage(String) - start"); //$NON-NLS-1$
		}

		TextMessage tm = this.session.createTextMessage(msg);
		this.messProducer.send(tm);

		if (logger.isDebugEnabled())
		{
			logger.debug("sendMessage(String) - end"); //$NON-NLS-1$
		}
	}


	/* (non-Javadoc)
	 * @see eai.msejdf.jms.JMSBase#close()
	 */
	@Override
	public void close() throws JMSException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("close() - start"); //$NON-NLS-1$
		}

		this.messProducer.close();
		this.connClose();

		if (logger.isDebugEnabled())
		{
			logger.debug("close() - end"); //$NON-NLS-1$
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

}
