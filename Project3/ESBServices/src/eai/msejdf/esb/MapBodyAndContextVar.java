package eai.msejdf.esb;

import java.util.ArrayList;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.actions.Aggregator;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import eai.msejdf.utils.SOAMessageConstants;

public class MapBodyAndContextVar extends AbstractActionLifecycle {

	protected ConfigTree config;

	/**
	 * @param configTree
	 */
	public MapBodyAndContextVar(ConfigTree configTree) {
		config = configTree;
	}

	public Message MapBodyToContextVar(Message message) throws ActionProcessingException {
		
		System.out.println("MapBodyToContextVar");
		
		// Save a copy of the context information in a field of the body, as there is no other
		// way to pass that information
		@SuppressWarnings("unchecked")
		ArrayList<String> contextInfo = (ArrayList<String>) message.getBody().get(SOAMessageConstants.ESB_MSG_CONTEXT_INFO);
		message.getBody().remove(SOAMessageConstants.ESB_MSG_CONTEXT_INFO); // Clean body
		
		message.getContext().setContext(Aggregator.AGGEGRATOR_TAG, contextInfo);
		
		return message;
	}
	
	public Message MapContextToBodyVar(Message message) throws ActionProcessingException {

		System.out.println("MapContextToBodyVar");

		// Save a copy of the context information in a field of the body, as there is no other
		// way to pass that information
		@SuppressWarnings("unchecked")
		ArrayList<String> contextInfo = (ArrayList<String>)message.getContext().getContext(Aggregator.AGGEGRATOR_TAG);
		
		//Backup the original body and save it at an dedicated variable (required for proper restore in BPM)
		Object origBody = message.getBody().get();
		message.getBody().add(SOAMessageConstants.ESB_MSG_CONTEXT_INFO, contextInfo);
		message.getBody().add(SOAMessageConstants.ESB_COMPANY, origBody);
		
		return message;
	}
	
}
