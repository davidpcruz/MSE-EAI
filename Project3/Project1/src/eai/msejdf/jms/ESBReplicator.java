package eai.msejdf.jms;

import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.QueueSession;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import eai.msejdf.config.Configuration;

public class ESBReplicator
{
	private static final String ENV_LOOKUP_FACTORY = "ConnectionFactory";
	private static final String ENV_URL_PKG_PREFIXES = "org.jboss.naming:org.jnp.interfaces";
	private static final String ENV_INITIAL_CONTEXT_FACTORY = "org.jnp.interfaces.NamingContextFactory";
	private TopicConnection conn;
	private TopicSession session;
	private Topic esbtopic;

    public void start() throws JMSException, NamingException 
    {
    	
        Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY, ENV_INITIAL_CONTEXT_FACTORY);
		env.put(Context.URL_PKG_PREFIXES, ENV_URL_PKG_PREFIXES);
		env.put(Context.PROVIDER_URL, Configuration.getESBReplicatorProvider());

		InitialContext iniCtx;
		try {
			iniCtx = new InitialContext(env);
	    	Object tmp = iniCtx.lookup(ENV_LOOKUP_FACTORY);
	    	TopicConnectionFactory qcf = (TopicConnectionFactory) tmp;
	    	conn = qcf.createTopicConnection();
	    	esbtopic = (Topic) iniCtx.lookup(Configuration.getESBReplicatorTopic());
	    	session = conn.createTopicSession(false, QueueSession.AUTO_ACKNOWLEDGE);
	    	conn.start();
    	
		} catch (NamingException | JMSException ex) {
			if (conn != null)
			{
		        conn.stop();
		        if (session != null)
		        	session.close();	        
		        conn.close();
			}
	        throw ex;
		}    	
    }	
    
    public void close() throws JMSException 
    { 
        conn.stop();
        session.close();
        conn.close();
    }
    
    public void sendMessage(String msg) throws JMSException {
    	    	
        TopicPublisher send = session.createPublisher(esbtopic);        
        ObjectMessage tm = session.createObjectMessage(msg);
        
        send.send(tm);        
        send.close();
        
    }
	
}
