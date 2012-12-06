package eai.msejdf.esb;

import java.util.Random;

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

		// TEMP translate the message to reports (simulat the jbpm)
//		Report repTemp = new Report();
//		Random rand = new Random();
//		repTemp.setUsersWarnedAutomatically(rand.nextInt(10));
//		repTemp.setUsersWarnedByManager(rand.nextInt(10));
//
//		// Set the message report
//		message.getBody().add(repTemp);

		return message;
	}

	public void exceptionHandler(Message message, Throwable exception) {
		logger.debug("!ERROR!");
		logger.debug(exception.getMessage());
		logger.debug("For Message: ");
		logger.debug(message.getBody().get());
	}

}
