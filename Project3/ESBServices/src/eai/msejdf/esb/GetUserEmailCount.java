package eai.msejdf.esb;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import eai.msejdf.utils.SOAMessageConstants;

import java.util.HashMap;

public class GetUserEmailCount extends AbstractActionLifecycle {

	protected static final Logger logger = Logger.getLogger(GetUserEmailCount.class);
	protected ConfigTree _config;

	public GetUserEmailCount(ConfigTree config) {
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
		Long msgBody = (Long) message.getBody().get();
		@SuppressWarnings("rawtypes")
		HashMap requestMap = new HashMap();

		// add parameters to the web service request map
		requestMap.put("getUserEmailCount." + SOAMessageConstants.ESB_USER_ID, msgBody);

		message.getBody().add(requestMap);

		logFooter();
		return message;
	}

	public void exceptionHandler(Message message, Throwable exception) {
		logHeader();
		logger.debug("GetNumberOfEmails: " + "!ERROR!");
		logger.debug("GetNumberOfEmails: " + exception.getMessage());
		logger.debug("GetNumberOfEmails: " + exception.getMessage());
		logger.debug("GetNumberOfEmails: " + "For Message: ");
		logger.debug("GetNumberOfEmails: " + message.getBody().get());
		logFooter();
	}

	// This makes it easier to read on the console
	private void logHeader() {
		logger.debug("&&&&&&&&&&&&&&&&&&&&&& GetNumberOfEmails &&&&&&&&&&&&&&&&&&&&&&\n");
	}

	private void logFooter() {
		logger.debug("&&&&&&&&&&&&&&&&&&&&&& GetNumberOfEmails &&&&&&&&&&&&&&&&&&&&&&\n");
	}

}