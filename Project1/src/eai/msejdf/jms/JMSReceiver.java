package eai.msejdf.jms;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Topic;

import org.hornetq.api.jms.HornetQJMSClient;

import eai.msejdf.config.Configuration;

public class JMSReceiver extends JMSBase
{

	protected MessageConsumer messConsumer;

	/**
	 * Instantiates a new jMS receiver.
	 *
	 * @throws JMSException the jMS exception
	 */
	public JMSReceiver() throws JMSException
	{
		super();
		this.conn.setClientID(Configuration.getJmsConnId());
	}

	/* (non-Javadoc)
	 * @see eai.msejdf.jms.JMSBase#createTopic(java.lang.String)
	 */
	@Override
	public void createTopic(String topicName) throws JMSException
	{
		this.dest = HornetQJMSClient.createTopic(topicName);
		this.messConsumer = session.createDurableSubscriber((Topic) dest, "MyTopic","Type = 1",false);		
	}

	/**
	 * Sets the message listener.
	 *
	 * @param listener the new message listener
	 * @throws JMSException the jMS exception
	 */
	public void setMessageListener(MessageListener listener) throws JMSException
	{
		messConsumer.setMessageListener(listener);		
	}
	
}
