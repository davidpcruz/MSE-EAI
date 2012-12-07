package eai.msejdf.esb;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import eai.msejdf.utils.SOAMessageConstants;

public class IncrementUserEmailCount extends AbstractActionLifecycle {
	protected static final Logger logger = Logger.getLogger(IncrementUserEmailCount.class);
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
		logger.debug("####################### original message response start ###################\n ");
		logger.debug("message Items: " + message.toString() + "\n");
		logger.debug("####################### original message response end ###################\n ");

		// Get the user Id list
		List<Long> userList = (List<Long>) message.getBody().get(SOAMessageConstants.ESB_USER_ID_LIST);

		// Convert the list of users to a Map
		int i = 0;
		for (Long userId : userList) {
			requestMap.put("incrementUserEmailCountFromList." + SOAMessageConstants.ESB_USER_ID + "[" + i + "]", userId);
			i++;
		}

		// add parameters to the web service request map
		message.getBody().add(requestMap);

		logFooter();
		return message;
	}

	public void exceptionHandler(Message message, Throwable exception) {
		logHeader();
		logger.debug("IncrementUserEmailCount: " + "!ERROR!");
		logger.debug("IncrementUserEmailCount: " + exception.getMessage());
		logger.debug("IncrementUserEmailCount: " + exception.getMessage());
		logger.debug("IncrementUserEmailCount: " + "For Message: ");
		logger.debug("IncrementUserEmailCount: " + message.getBody().get());
		logFooter();
	}

	// This makes it easier to read on the console
	private void logHeader() {
		logger.debug("&&&&&&&&&&&&&&&&&&&&&& IncrementUserEmailCount start &&&&&&&&&&&&&&&&&&&&&&\n");
	}

	private void logFooter() {
		logger.debug("&&&&&&&&&&&&&&&&&&&&&& IncrementUserEmailCount end &&&&&&&&&&&&&&&&&&&&&&\n");
	}

}