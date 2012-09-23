package eai.msejdf.utils;

import eai.msejdf.config.Configuration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.log4j.Logger;
import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.jms.HornetQJMSClient;
import org.hornetq.api.jms.JMSFactoryType;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;

/**
 * The Class JMSHandler.
 * Simplifies and handles all the JMS communication  
 */
public class JMSHandler {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(JMSHandler.class);

	private ConnectionFactory connFactory;
	private Connection conn;
	private Destination dest;
	private Session session;
	private MessageProducer messProducer;
	
	/**
	 * Instantiates a new jMS handler.
	 * @throws JMSException 
	 */	
	public JMSHandler() throws JMSException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("JMSHandler() - start"); //$NON-NLS-1$
		}

		TransportConfiguration transportConfiguration = new TransportConfiguration(NettyConnectorFactory.class.getName());  
		this.connFactory = (ConnectionFactory) HornetQJMSClient.createConnectionFactoryWithoutHA(JMSFactoryType.TOPIC_CF,transportConfiguration);
		this.conn = connFactory.createConnection(Configuration.getJmsConnUser(), Configuration.getJmsconnPass());
		
		this.session = this.conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		this.conn.start();


		if (logger.isDebugEnabled())
		{
			logger.debug("JMSHandler() - end"); //$NON-NLS-1$
		}
	}
	
	/**
	 * Creates the topic on the JMS system.
	 *
	 * @param topicName the topic name
	 * @throws JMSException 
	 */
	public void createTopic (String topicName) throws JMSException
	{
		this.dest = HornetQJMSClient.createTopic(topicName);
		this.messProducer = this.session.createProducer(this.dest);

	}

}
