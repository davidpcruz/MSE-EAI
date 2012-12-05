package eai.msejdf.esb;

import java.util.HashMap;
import java.util.List;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import eai.msejdf.utils.SOAMessageConstants;

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
		@SuppressWarnings("rawtypes")
		HashMap requestMap = new HashMap();
		
		logHeader();
		System.out.println("####################### original message response start ###################\n ");
		System.out.println("message Items: " + message.toString() + "\n");
		System.out.println("####################### original message response end ###################\n ");

		// message.getBody().get(SOAMessageConstants.ESB_USER_ID_LIST );
		List<Long> userList = (List<Long>) message.getBody().get(); // to be used on tests
		
		int i = 0;
		for( Long userId : userList ) {
			requestMap.put("incrementUserEmailCountFromList."+ SOAMessageConstants.ESB_USER_ID + "[" + i + "]", userId);
			i++;
		}															
	
		// add parameters to the web service request map
		message.getBody().add(requestMap);

//		System.out.println("###############  message response start ############");
		System.out.println("Request map is: " + requestMap.toString());
//		System.out.println("message Items: " + message.toString() + "\n");
//		System.out.println("################### IncrementUserEmailCount ###########");
		logFooter();
		return message;
	}

	public void exceptionHandler(Message message, Throwable exception) {
		logHeader();
		System.out.println("IncrementUserEmailCount: " + "!ERROR!");
		System.out
				.println("IncrementUserEmailCount: " + exception.getMessage());
		System.out
				.println("IncrementUserEmailCount: " + exception.getMessage());
		System.out.println("IncrementUserEmailCount: " + "For Message: ");
		System.out.println("IncrementUserEmailCount: "
				+ message.getBody().get());
		logFooter();
	}

	// This makes it easier to read on the console
	private void logHeader() {
		System.out
				.println("&&&&&&&&&&&&&&&&&&&&&& IncrementUserEmailCount start &&&&&&&&&&&&&&&&&&&&&&\n");
	}

	private void logFooter() {
		System.out
				.println("&&&&&&&&&&&&&&&&&&&&&& IncrementUserEmailCount end &&&&&&&&&&&&&&&&&&&&&&\n");
	}

}