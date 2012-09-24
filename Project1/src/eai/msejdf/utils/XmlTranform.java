/**
 * 
 */
package eai.msejdf.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.NamingException;
import javax.xml.transform.TransformerException;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.jms.HornetQJMSClient;
import org.hornetq.api.jms.JMSFactoryType;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.Source;
import javax.xml.transform.Result;
import javax.xml.transform.OutputKeys;

/**
 * @author joaofcr
 *
 */
public class XmlTranform
{
	private ConnectionFactory cf;
	private Connection c;
	private Session s;
	private Destination d;
	private MessageConsumer mc;
	
	
	
	public void Receiver() throws NamingException, JMSException {
		TransportConfiguration transportConfiguration = 
		                new TransportConfiguration(NettyConnectorFactory.class.getName());                
		cf = (ConnectionFactory) HornetQJMSClient.createConnectionFactoryWithoutHA(JMSFactoryType.CF,transportConfiguration);
		c = cf.createConnection("user", "pass");
		d = HornetQJMSClient.createQueue("PlayQueue");
		s = c.createSession(false, Session.CLIENT_ACKNOWLEDGE);
		c.start();
		mc = s.createConsumer(d);
	}
	
	
	
	private String receive() throws JMSException {
		TextMessage msg = (TextMessage) mc.receive();
		msg.acknowledge();
		return msg.getText();
		
	}
	
	
	private void close() throws JMSException {
		this.c.close();
	}
	
	

    public static void xmlTransformation (String xmlFile, String xsltFile) throws TransformerException, UnsupportedEncodingException{
       
    	  //String resultFile = null;
 
 

    	InputStream xmlStream = new ByteArrayInputStream(xmlFile.getBytes("UTF8"));
    	StreamSource source = new StreamSource(xmlStream);
    	
    	 // Source source = (Source) new ByteArrayInputStream(xmlFile.getBytes("UTF8")); 

    	  Source xsl = new StreamSource(xsltFile);
		  Result result = new StreamResult(System.out);

		  TransformerFactory factory = TransformerFactory.newInstance();
		  Transformer transformer = factory.newTransformer(xsl);
		  transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		  transformer.transform(source, result);
  
    }
	/**
	* @param args
	* @throws JMSException 
	* @throws NamingException 
	 * @throws TransformerException 
	 * @throws UnsupportedEncodingException 
	*/
	public static void main(String[] args) throws NamingException, JMSException, TransformerException, UnsupportedEncodingException {
		XmlTranform r = new XmlTranform();
		String xsltFile = "C:\\Users\\joaofcr\\workspace\\Sender\\src\\StockMarket.xsl";
		
		
		String xmlMsg = r.receive();
		//System.out.println("XML:\n " + xmlMsg);
		//System.out.println("XSLT:\n " + xsltFile);
		
	    xmlTransformation(xmlMsg,xsltFile);
        
        
		r.close();

	}

}
