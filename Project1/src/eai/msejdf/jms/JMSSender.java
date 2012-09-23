/**
 * 
 */
package eai.msejdf.jms;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;

import org.hornetq.api.jms.HornetQJMSClient;

/**
 * @author dcruz
 *
 */
public class JMSSender extends JMSBase
{

	protected MessageProducer messProducer;

	/**
	 * Instantiates a new jMS sender.
	 *
	 * @throws JMSException the jMS exception
	 */
	public JMSSender() throws JMSException
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see eai.msejdf.jms.JMSBase#createTopic(java.lang.String)
	 */
	@Override
	public void createTopic(String topicName) throws JMSException
	{
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

}
