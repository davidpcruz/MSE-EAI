package eai.msejdf.esb;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import java.util.HashMap;
import eai.msejdf.utils.SOAMessageConstants;

public class GetUsersFollowingCompany extends AbstractActionLifecycle {
	protected static final Logger logger = Logger.getLogger(GetUsersFollowingCompany.class);
	protected ConfigTree _config;

	public GetUsersFollowingCompany(ConfigTree config) {
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

		String msgBody = (String) message.getBody().get(SOAMessageConstants.ESB_COMPANY_NAME);
		// String msgBody = (String) message.getBody().get(); //to be used on
		// tests
		logger.debug("####################### original message response start ###################\n ");
		logger.debug("message Items: " + message.toString() + "\n");
		logger.debug("####################### original message response end ###################\n ");
		@SuppressWarnings("rawtypes")
		HashMap requestMap = new HashMap();

		// add parameters to the web service request map

		requestMap.put("getUsersFollowingCompany." + SOAMessageConstants.ESB_COMPANY_NAME, msgBody);
		message.getBody().add(requestMap);

		
		logFooter();
		return message;
	}

	public void exceptionHandler(Message message, Throwable exception) {
		logHeader();
		logger.debug("GetUsersFollowingCompany: " + "!ERROR!");
		logger.debug("GetUsersFollowingCompany: " + exception.getMessage());
		logger.debug("GetUsersFollowingCompany: " + exception.getMessage());
		logger.debug("GetUsersFollowingCompany: " + "For Message: ");
		logger.debug("GetUsersFollowingCompany: " + message.getBody().get());
		logFooter();
	}

	// This makes it easier to read on the console
	private void logHeader() {
		logger.debug("&&&&&&&&&&&&&&&&&&&&&& GetUsersFollowingCompany &&&&&&&&&&&&&&&&&&&&&&\n");
	}

	private void logFooter() {
		logger.debug("&&&&&&&&&&&&&&&&&&&&&& GetUsersFollowingCompany &&&&&&&&&&&&&&&&&&&&&&\n");
	}

}