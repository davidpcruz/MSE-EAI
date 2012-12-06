package eai.msejdf.esb;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

public class PrintAggregatorTagAction extends AbstractActionLifecycle {

	protected static final Logger logger = Logger
			.getLogger(PrintAggregatorTagAction.class);

	public PrintAggregatorTagAction(ConfigTree configTree) {

	}

	public Message noOperation(Message message) {
		return message;
	}

	public Message displayMessage(Message message) throws Exception {
		logger.debug("\nAgregatorTag ("
				+ message.getContext().getContext("aggregatorTag") + ")");
		logger.debug("\nMessage " + message.getBody().get());

		return message;
	}

	public void exceptionHandler(Message message, Throwable exception) {
		logger.debug("!ERROR!");
		logger.debug(exception.getMessage());
		logger.debug("For Message: ");
		logger.debug(message.getBody().get());
	}

}
