package eai.msejdf.jms;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.ejb.ActivationConfigProperty;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import eai.msejdf.persistence.Address;

import org.apache.log4j.Logger;

import eai.msejdf.data.Company;
import eai.msejdf.data.Stock;
import eai.msejdf.data.Stocks;

import eai.msejdf.utils.XmlObjConv;

/**
 * Bean that reads JMS topic with companies information and updates DB with that
 * information
 */

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "topic/EAIProject1")
// TODO get Topic from configuration file
})
public class JMSBeanReceiver implements MessageListener {
	@Resource
	private MessageDrivenContext mdc;
	@PersistenceContext(unitName = "JPAEAI")
	// TODO: Check if it can be placed in a config file and update name
	private EntityManager entityManager;

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(JMSBeanReceiver.class);

	/*
	 * Get Company information If the company does not exist then returns null
	 */
	public eai.msejdf.persistence.Company getCompany(String company) {

		Query query = entityManager
				.createQuery("SELECT Company FROM Company company WHERE company.name=:name");
		query.setParameter("name", company);

		@SuppressWarnings("unchecked")
		List<eai.msejdf.persistence.Company> companyList = query
				.getResultList();

		if (true == companyList.isEmpty()) {
			// The company doesn't seem to exist
			logger.debug("The company doesn not seem to exist in DB: "
					+ company);
			return null;

		} else {
			logger.debug("The company exists in DB: " + company);
			return companyList.get(0);
		}
	}

	/*
	 * Method to read JMS TOPIC (non-Javadoc)
	 * 
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@Override
	public void onMessage(Message inMessage) {
		TextMessage msg = null;

		try {
			if (inMessage instanceof TextMessage) {
				msg = (TextMessage) inMessage;
				logger.debug("MESSAGE BEAN: Message received: " + msg.getText());
				// MArshall XML to a Stocks object
				Stocks objMsg = XmlObjConv.convertToObject(msg.getText(),
						Stocks.class);
				for (Stock quote : objMsg.getStock()) {
					// Update Company data
					// If the company does not exist it will be created
					updateCompany(quote);
				}

			} else {
				logger.warn("Message of wrong type: "
						+ inMessage.getClass().getName());
			}
		} catch (JMSException e) {
			e.printStackTrace();
			mdc.setRollbackOnly();
		} catch (Throwable te) {
			te.printStackTrace();
		}
	}

	/*
	 * Updates company information Update Company data If the company does not
	 * exist it will be created
	 */
	private void updateCompany(Stock quote) {

		Company company = null;
		eai.msejdf.persistence.Company persistenceCompany = null;
		company = quote.getCompany();

		// Get Company object
		persistenceCompany = getCompany(company.getName());
		if (null == persistenceCompany) {
			// add company
			logger.info("Adding company to DB: " + company.getName());
			persistenceCompany = new eai.msejdf.persistence.Company();

		}

		// set Company Address
		Address addressNew = new Address();
		addressNew.setAddress(company.getAddress());
		// first we need to persist the company address and then we can persist
		// the company
		entityManager.persist(addressNew);
		persistenceCompany.setAddress(addressNew);
		// set Company Name
		persistenceCompany.setName(company.getName());
		// set Last Quotation
		persistenceCompany.setLastQuotation(quote.getQuotation()
				.getLastQuotation());
		// set Company Website
		persistenceCompany.setWebsite(company.getWebsite());
		// set Time
		persistenceCompany.setTime(quote.getQuotation().getTime());
		// set Variation
		persistenceCompany.setVariation(quote.getQuotation().getVariation());
		// set Quantity
		persistenceCompany.setQuantity(quote.getQuotation().getQuantity());
		// set Maximum
		persistenceCompany.setMaximum(quote.getQuotation().getMaximum());
		// set Minimum
		persistenceCompany.setMinimum(quote.getQuotation().getMinimum());
		// set Purchase
		persistenceCompany.setPurchase(quote.getQuotation().getPurchase());
		// set Sell
		persistenceCompany.setSell(quote.getQuotation().getSell());

		logger.info("Persist company: " + company.getName());
		logger.debug("Persist persistence company: "
				+ persistenceCompany.getName());

		// persist the company information to DB
		entityManager.persist(persistenceCompany);

	}

}
