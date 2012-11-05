package eai.msejdf.jms;

import javax.annotation.Resource;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.ejb.ActivationConfigProperty;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import org.apache.log4j.Logger;

import eai.msejdf.data.Stock;
import eai.msejdf.data.Stocks;
import eai.msejdf.utils.XmlObjConv;

//import eai.msejdf.config.Configuration;



@MessageDriven(activationConfig = { 
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "topic/EAIProject1") 
		// TODO get Topic from configuration file
})

public class JMSBeanReceiver implements MessageListener{
	@Resource private MessageDrivenContext mdc;
	

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(JMSBeanReceiver.class);
	@Override
	public void onMessage(Message inMessage) {
		
		TextMessage msg = null;
		try {
			if (inMessage instanceof TextMessage) {
				msg = (TextMessage) inMessage;
				logger.debug("MESSAGE BEAN: Message received: " + msg.getText());
				
				Stocks objMsg = XmlObjConv.convertToObject(msg.getText(), Stocks.class);  
				for (Stock quote : objMsg.getStock())
				{
					System.out.println(quote);
				}
				
			} else {
				logger.warn("Message of wrong type: "
						+ inMessage.getClass().getName());
			}
		} catch (JMSException e) {
			e.printStackTrace();
			mdc.setRollbackOnly();
		} catch (Throwable te) {
			te.printStackTrace();
		}
	}
}
