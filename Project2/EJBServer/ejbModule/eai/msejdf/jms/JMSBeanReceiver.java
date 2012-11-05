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

import org.apache.log4j.Logger;


import eai.msejdf.data.Company;
//import eai.msejdf.persistence.Company;
import eai.msejdf.data.Quotation;
import eai.msejdf.data.Stock;
import eai.msejdf.data.Stocks;


import eai.msejdf.utils.XmlObjConv;

//import eai.msejdf.config.Configuration;

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

	public eai.msejdf.persistence.Company getCompany(String company) {

		Query query = entityManager
				.createQuery("SELECT Company FROM Company company WHERE company.name=:name");
		query.setParameter("name", company);

		@SuppressWarnings("unchecked")
		List<eai.msejdf.persistence.Company> companyList = query.getResultList();

		if (true == companyList.isEmpty()) {
			// The company doesn't seem to exist
			logger.info("The company doesn not seem to exist in DB: " + company );
			return null;
			
		} else {
			logger.info("The company exists in DB: " + company );
			return companyList.get(0);
		}
	}

	@Override
	public void onMessage(Message inMessage) {

		TextMessage msg = null;
		Company company = null;
		Quotation quotation = null;
		eai.msejdf.persistence.Company persistenceCompany = null;


		try {
			if (inMessage instanceof TextMessage) {
				msg = (TextMessage) inMessage;
				logger.debug("MESSAGE BEAN: Message received: " + msg.getText());

				Stocks objMsg = XmlObjConv.convertToObject(msg.getText(),
						Stocks.class);
				for (Stock quote : objMsg.getStock()) {
					company = quote.getCompany();
					quotation = quote.getQuotation();
					System.out.println(company.getName() + " "
							+ quotation.getLastQuotation());
					
					persistenceCompany = getCompany(company.getName());
					if(null == persistenceCompany){
						//add company
						logger.info("Adding company to DB: " + company );
						persistenceCompany = new eai.msejdf.persistence.Company();

					}
					persistenceCompany.setName(company.getName());
					//persistenceCompany.setLastQuotation(quote.ge);
					persistenceCompany.setWebsite(company.getWebsite());
					logger.info("Persist company: " + company );
					logger.info("Persist PEr company: " + persistenceCompany.getName() );
					entityManager.persist(persistenceCompany);
					
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
}
