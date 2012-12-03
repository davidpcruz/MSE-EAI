package eai.msejdf.esb;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

public class MyDelay2 extends AbstractActionLifecycle {
	protected ConfigTree _config;
	
	public Integer delayValue = 5000;

	public Message process(Message message) throws Exception {
		System.out.println("Delaying for " + delayValue + "...");
		Thread.sleep(delayValue);
		System.out.println("Delaying for " + delayValue + "... Complete");
		return message;
	}

	public MyDelay2(ConfigTree config) {
		_config = config;
	}
}