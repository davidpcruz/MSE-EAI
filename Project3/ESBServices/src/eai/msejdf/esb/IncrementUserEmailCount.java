package eai.msejdf.esb;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import eai.msejdf.utils.SOAMessageConstants;

import java.util.HashMap;

public class IncrementUserEmailCount extends AbstractActionLifecycle {

	protected ConfigTree _config;

	public IncrementUserEmailCount(ConfigTree config) {
		_config = config;
	}

	public Message noOperation(Message message) {
		return message;
	}

	/*
	 * Convert the message into a webservice request map.
	 */
	@SuppressWarnings("unchecked")
	public Message process(Message message) throws Exception {
		logHeader();
		String msgBody = (String) message.getBody().get();
		@SuppressWarnings("rawtypes")
		HashMap requestMap = new HashMap();

		// add parameters to the web service request map
		System.out.println("################### IncrementUserEmailCount ######################\n");
		System.out.println("IncrementUserEmailCount: " + "userId = " + msgBody);
		
		
		requestMap.put("incrementUserEmailCount."+ SOAMessageConstants.ESB_USER_ID, msgBody);

		message.getBody().add(requestMap);
		System.out.println("Request map is: " + requestMap.toString());
		System.out.println("################### IncrementUserEmailCount ######################\n");
		logFooter();
		return message;
	}

	public void exceptionHandler(Message message, Throwable exception) {
		logHeader();
		System.out.println("IncrementUserEmailCount: " + "!ERROR!");
		System.out.println("IncrementUserEmailCount: " + exception.getMessage());
		System.out.println("IncrementUserEmailCount: " + exception.getMessage());
		System.out.println("IncrementUserEmailCount: " + "For Message: ");
		System.out.println("IncrementUserEmailCount: " + message.getBody().get());
		logFooter();
	}

	// This makes it easier to read on the console
	private void logHeader() {
		System.out
				.println("&&&&&&&&&&&&&&&&&&&&&& IncrementUserEmailCount &&&&&&&&&&&&&&&&&&&&&&\n");
	}

	private void logFooter() {
		System.out
				.println("&&&&&&&&&&&&&&&&&&&&&& IncrementUserEmailCount &&&&&&&&&&&&&&&&&&&&&&\n");
	}

}