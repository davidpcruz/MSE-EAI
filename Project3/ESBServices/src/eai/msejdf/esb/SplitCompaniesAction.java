package eai.msejdf.esb;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.ConfigurationException;
import org.jboss.soa.esb.Service;
import org.jboss.soa.esb.actions.AbstractActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.actions.AggregationDetails;
import org.jboss.soa.esb.actions.Aggregator;
import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.listeners.message.MessageDeliverException;
import org.jboss.soa.esb.message.Message;

public class SplitCompaniesAction extends AbstractActionPipelineProcessor {

	// property where to redirect
	public static final String SERVICE_CATEGORY_TAG = "service-category";
	public static final String SERVICE_NAME_TAG = "service-name";

	protected ConfigTree config;
	protected static final Logger logger = Logger.getLogger(SplitCompaniesAction.class);

	private String serviceCategory;
	private String serviceName;

	/**
	 * @param configTree
	 * @throws ConfigurationException
	 */
	public SplitCompaniesAction(ConfigTree configTree)
			throws ConfigurationException {
		config = configTree;

		serviceCategory = configTree.getRequiredAttribute(SERVICE_CATEGORY_TAG);
		serviceName = configTree.getRequiredAttribute(SERVICE_NAME_TAG);

		if (null == serviceCategory || serviceCategory.length() < 1) {
			logger.error("Missing or empty serviceCategory property");
			throw new ConfigurationException(
					"Missing or empty serviceCategory property");
		}
		if (null == serviceName || serviceName.length() < 1) {
			logger.error("Missing or empty serviceName property");
			throw new ConfigurationException(
					"Missing or empty serviceName property");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jboss.soa.esb.actions.ActionPipelineProcessor#process(org.jboss.soa
	 * .esb.message.Message)
	 */
	public Message process(Message message) throws ActionProcessingException {

		// series configuration
		String seriesUUID = UUID.randomUUID().toString();
		long seriesTimestamp = System.currentTimeMillis();

		@SuppressWarnings("unchecked")
		List<Company> compItems = (List<Company>) message.getBody().get();

		if (compItems != null) {
			int companiesCount = compItems.size();

			for (int i = 0; i < companiesCount; i++) {
				Service service = new Service(serviceCategory, serviceName);
				try {
					ServiceInvoker invoker = new ServiceInvoker(service);

					if (companiesCount > 1) {
						// Only add aggregation if we have more tan one company
						addAggregationDetails(message, seriesUUID, companiesCount,
								seriesTimestamp, i + 1);
					}
					
					// add the company to this message
					message.getBody().add(compItems.get(i));
					
					invoker.deliverAsync(message);
				} catch (MessageDeliverException e) {
					logger.error("Failed to deliver message to Service '"
							+ service
							+ "'. Throwing ActionProcessingException exception.");
					throw new ActionProcessingException(e);
				}
			}
		}

		return null;
	}

	/**
	 * @param message
	 * @param uuId
	 * @param recipientCount
	 * @param seriesTimestamp
	 * @param messageIndex
	 */
	protected void addAggregationDetails(Message message, String uuId,
			int recipientCount, long seriesTimestamp, int messageIndex) {
		AggregationDetails aggrDetails = new AggregationDetails(uuId,
				messageIndex, recipientCount, seriesTimestamp);

		// get the context Aggregator Tags
		@SuppressWarnings("unchecked")
		ArrayList<String> aggregatorTags = (ArrayList<String>) message
				.getContext().getContext(Aggregator.AGGEGRATOR_TAG);

		// fail check
		if (aggregatorTags == null) {
			aggregatorTags = new ArrayList<String>();
		}

		// This is useful during aggregation - as a way of id'ing where the
		// split occurred.
		aggrDetails.setSplitId(SplitCompaniesAction.class.getSimpleName());
		// add the detail
		aggregatorTags.add(aggrDetails.toString());
		
		// set the tag on the message
		message.getContext().setContext(Aggregator.AGGEGRATOR_TAG, aggregatorTags);

		if (logger.isDebugEnabled()) {
			logger.debug(Aggregator.AGGEGRATOR_TAG + "=" + aggrDetails);
		}
	}
}
