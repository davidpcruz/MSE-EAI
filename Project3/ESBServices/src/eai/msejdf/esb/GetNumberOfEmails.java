package eai.msejdf.esb;


import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import java.util.HashMap;

public class GetNumberOfEmails extends AbstractActionLifecycle {

	protected ConfigTree _config;

	public GetNumberOfEmails(ConfigTree config) {
		_config = config;
	}

	public Message noOperation(Message message) {
		return message;
	}

	/*
	 * Convert the message into a webservice request map.
	 */
	public Message process(Message message) throws Exception {
		logHeader();
		String msgBody = (String) message.getBody().get();
		HashMap requestMap = new HashMap();

		// add paramaters to the web service request map
		System.out.println("################### GetNumberOfEmails ######################\n");
		System.out.println("userId = " + msgBody);
		
		requestMap.put("userId", msgBody);

		message.getBody().add(requestMap);
		System.out.println("Request map is: " + requestMap.toString());
		System.out.println("################### GetNumberOfEmails ######################\n");
		logFooter();
		return message;
	}

	public void exceptionHandler(Message message, Throwable exception) {
		logHeader();
		System.out.println("!ERROR!");
		System.out.println(exception.getMessage());
		System.out.println("For Message: ");
		System.out.println(message.getBody().get());
		logFooter();
	}

	// This makes it easier to read on the console
	private void logHeader() {
		System.out.println("&&&&&&&&&&&&&&&&&&&&&& GetNumberOfEmails &&&&&&&&&&&&&&&&&&&&&&\n");
	}

	private void logFooter() {
		System.out.println("&&&&&&&&&&&&&&&&&&&&&& GetNumberOfEmails &&&&&&&&&&&&&&&&&&&&&&\n");
	}

}