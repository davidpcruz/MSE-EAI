/**
 * 
 */
package eai.msejdf.jms;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;

import org.hornetq.api.jms.HornetQJMSClient;

import eai.msejdf.utils.StringUtils;

/**
 * @author dcruz
 *
 */
public class JMSSender extends JMSBase
{

	/** The message producer. */
	protected MessageProducer messProducer;

	/**
	 * Instantiates a new jMS sender.
	 *
	 * @throws JMSException the jMS exception
	 */
	public JMSSender() throws JMSException
	{
		super(null);
	}

	/* (non-Javadoc)
	 * @see eai.msejdf.jms.JMSBase#createTopic(java.lang.String)
	 */
	@Override
	public void createTopic(String topicName) throws JMSException
	{
		// basic validations
		if (StringUtils.isNullOrEmpty(topicName))
		{
			throw new IllegalArgumentException("topicName");
		}
		
		this.dest = HornetQJMSClient.createTopic(topicName);
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
		TextMessage tm = this.session.createTextMessage(msg);
		this.messProducer.send(tm);
	}

	/**
	 * Close.
	 *
	 * @throws JMSException the jMS exception
	 */
	@Override
	public void closeConn() throws JMSException
	{
		if(this.conn != null)
		{
			this.conn.stop();
			this.conn.close();
		}
	}

}
