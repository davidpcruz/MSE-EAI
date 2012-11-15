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
import javax.xml.bind.JAXBException;

import eai.msejdf.persistence.Address;
import eai.msejdf.persistence.StockInfo;

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
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "topic/EAIProject1"),
		@ActivationConfigProperty(propertyName = "maxSession", propertyValue = "1")
// TODO get Topic from configuration file
})
// @Pool(value=PoolDefaults.POOL_IMPLEMENTATION_STRICTMAX,maxSize=50,timeout=1800000)
public class JMSBeanReceiver implements MessageListener {
	@Resource
	private MessageDrivenContext mdc;
	@PersistenceContext(unitName = "JPAEAI")
	// TODO: Check if it can be placed in a config file and update name
	private EntityManager entityManager;

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(JMSBeanReceiver.class);

	/**
	 * Get Company information If the company does not exist then returns null
	 * 
	 * @param company
	 * @return
	 */
	public eai.msejdf.persistence.Company getCompany(String company) {
		if (logger.isDebugEnabled()) {
			logger.debug("getCompany(String) - start"); //$NON-NLS-1$
		}

		Query query = entityManager.createQuery("SELECT Company FROM Company company WHERE company.name=:name");
		query.setParameter("name", company);

		@SuppressWarnings("unchecked")
		List<eai.msejdf.persistence.Company> companyList = query.getResultList();

		if (true == companyList.isEmpty()) {
			// The company doesn't seem to exist
			logger.debug("The company doesn not seem to exist in DB: " + company);
			return null;

		} else {
			logger.debug("The company exists in DB: " + company);
			return companyList.get(0);
		}
	}

	/*
	 * (non-Javadoc) Method to read JMS TOPIC (non-Javadoc)
	 * 
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */

	@Override
	public void onMessage(Message inMessage) {
		if (logger.isDebugEnabled()) {
			logger.debug("onMessage(Message) - start"); //$NON-NLS-1$
		}

		TextMessage msg = null;

		try {
			if (inMessage instanceof TextMessage) {
				msg = (TextMessage) inMessage;
				logger.debug("MESSAGE BEAN: Message received: " + msg.getText());
				// Marshall XML to a Stocks object
				Stocks objMsg = XmlObjConv.convertToObject(msg.getText(), Stocks.class);
				for (Stock quote : objMsg.getStock()) {
					// Update Company data
					// If the company does not exist it will be created
					updateCompany(quote);
				}

			} else {
				logger.warn("Message of wrong type: " + inMessage.getClass().getName());
			}
		} catch (JMSException e) {
			logger.error("onMessage(Message)", e); //$NON-NLS-1$
			mdc.setRollbackOnly();
		}
		// catch (Throwable te) {
		// te.printStackTrace();
		// }
		catch (JAXBException e) {
			logger.error("onMessage(Message)", e); //$NON-NLS-1$
		}
		if (logger.isDebugEnabled()) {
			logger.debug("onMessage(Message) - end"); //$NON-NLS-1$
		}
	}

	/*
	 * Updates company information Update Company data If the company does not
	 * exist it will be created
	 * 
	 * @param quote
	 */

	private void updateCompany(Stock quote) {
		if (logger.isDebugEnabled()) {
			logger.debug("updateCompany(Stock) - start"); //$NON-NLS-1$
		}

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

		if (null != company.getAddress()) {
			logger.info("company.getAddress(): " +company.getAddress());

			// set Company Address
			Address address = new Address();
			address.setAddress(company.getAddress());
			// first we need to persist the company address and then we can
			// persist
			// the company
			// entityManager.persist(address);
			persistenceCompany.setAddress(address);
		}
		// set Company Name
		persistenceCompany.setName(company.getName());
		// set Company Website
		persistenceCompany.setWebsite(company.getWebsite());

		// set Last Quotation
		StockInfo stockInfo = new StockInfo();
		stockInfo.setLastQuotation(quote.getQuotation().getLastQuotation());
		// set Time
		stockInfo.setTime(quote.getQuotation().getTime());
		// set Variation
		stockInfo.setVariation(quote.getQuotation().getVariation());
		// set Quantity
		stockInfo.setQuantity(quote.getQuotation().getQuantity());
		// set Maximum
		stockInfo.setMaximum(quote.getQuotation().getMaximum());
		// set Minimum
		stockInfo.setMinimum(quote.getQuotation().getMinimum());
		// set Purchase
		stockInfo.setPurchase(quote.getQuotation().getPurchase());
		// set Sell
		stockInfo.setSell(quote.getQuotation().getSell());

		// set Company StockInfo
		persistenceCompany.setStockInfo(stockInfo);

		logger.info("Persist company: " + company.getName());
		logger.debug("Persist persistence company: " + persistenceCompany.getName());

		// persist the company information to DB
		entityManager.persist(persistenceCompany);

		if (logger.isDebugEnabled()) {
			logger.debug("updateCompany(Stock) - end"); //$NON-NLS-1$
		}
	}

}
