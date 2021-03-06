package eai.msejdf.jms;

import java.util.HashMap;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.log4j.Logger;
import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.jms.HornetQJMSClient;
import org.hornetq.api.jms.JMSFactoryType;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;

import eai.msejdf.config.Configuration;
import eai.msejdf.utils.StringUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class JMSHandler. Simplifies and handles all the JMS communication
 */
abstract class JMSBase
{

	/** Logger for this class. */
	private static final Logger logger = Logger.getLogger(JMSBase.class);

	/** The conn factory. */
	protected ConnectionFactory connFactory;
	
	/** The conn. */
	protected Connection conn;
	
	/** The dest. */
	protected Destination dest;
	
	/** The session. */
	protected Session session;

	/**
	 * Instantiates a new jMS handler.
	 *
	 * @param topicName the topic name
	 * @param clientID the client id
	 * @throws JMSException the jMS exception
	 */
	protected JMSBase(String topicName, String clientID) throws JMSException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("JMSBase() - start"); //$NON-NLS-1$
		}

		// basic validations
		if (StringUtils.isNullOrEmpty(topicName))
		{
			throw new IllegalArgumentException("topicName");
		}

		// Add server configurations
		HashMap<String,Object> map = new HashMap<String,Object>();
//		map.put("host", Configuration.getJmsConnServer());
//		map.put("port", Configuration.getJmsConnPort());		
		
		TransportConfiguration transportConfiguration = new TransportConfiguration(
		        NettyConnectorFactory.class.getName(), map);
		this.connFactory = (ConnectionFactory) HornetQJMSClient.createConnectionFactoryWithoutHA(
		        JMSFactoryType.TOPIC_CF, transportConfiguration);
		this.conn = connFactory.createConnection(Configuration.getJmsConnUser(), Configuration.getJmsConnPass());
		if (!StringUtils.IsNullOrWhiteSpace(clientID))
		{
			logger.debug("JMSBase() - setting clientID " + clientID);
			this.conn.setClientID(clientID);
		}

		this.session = this.conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		this.dest = HornetQJMSClient.createTopic(topicName);

		if (logger.isDebugEnabled())
		{
			logger.debug("JMSBase() - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Starts the Connection with the JMS server.
	 *
	 * @throws JMSException the jMS exception
	 */
	protected void connStart() throws JMSException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("connStart() - start"); //$NON-NLS-1$
		}

		this.conn.start();

		if (logger.isDebugEnabled())
		{
			logger.debug("connStart() - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Closes the Connection with the JMS server.
	 *
	 * @throws JMSException the jMS exception
	 */
	protected void connClose() throws JMSException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("connClose() - start"); //$NON-NLS-1$
		}

		if (this.conn != null)
		{
			this.conn.stop();
			this.conn.close();
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("connClose() - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Opens the connection.
	 *
	 * @throws JMSException the jMS exception
	 */
	// public abstract void createTopic(String topicName) throws JMSException;
	public abstract void start() throws JMSException;

	/**
	 * Closes the connection .
	 * 
	 * @throws JMSException
	 *             the jMS exception
	 */
	public abstract void close() throws JMSException;

}
