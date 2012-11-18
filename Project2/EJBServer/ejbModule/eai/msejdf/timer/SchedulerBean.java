package eai.msejdf.timer;

import java.util.Date;
import java.util.List;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import eai.msejdf.config.Configuration;
import eai.msejdf.persistence.Company;
import eai.msejdf.persistence.StockInfo;
import eai.msejdf.persistence.User;
import eai.msejdf.utils.EmailUtils;
import eai.msejdf.utils.StringUtils;

/**
 * Session Bean implementation class Timer
 */
@Stateless(name="SchedulerBean")
public class SchedulerBean  {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SchedulerBean.class);
	@PersistenceContext(unitName = "JPAEAI")
	private EntityManager entityManager;
			
    /**
     * Default constructor. 
     */
    public SchedulerBean() {        
    }
    
	/**
	 * Daily mail company digest, get all the users subscribed and send them emails abou the comapnies.
	 */
	@Schedule(hour="0")
	public void dailyMailCompanyDigest() {

		if (logger.isDebugEnabled()) {
			logger.debug("dailyMailCompanyDigest - start"); 
		}

		// get all users with associated companies (fetch get all the lazy connections)
		Query query = entityManager.createQuery("SELECT u FROM User u join fetch u.subscribedCompanies");
		
		@SuppressWarnings("unchecked")
		List<User> userList = query.getResultList();
				
		if (true == userList.isEmpty()) 
		{
			logger.info("dailyMailCompanyDigest - no users to process"); 
		} 
		else
		{
			// set the email subject for all the users
			String subject = String.format("Daily Company Digest %1$te/%1$tm/%1$tY at %1$tH:%1$tM:%1$tS%n", new Date());
			for (User user : userList)
			{
				if (StringUtils.IsNullOrWhiteSpace(user.getEmail()))
				{
					logger.info(String.format("dailyMailCompanyDigest user %s has no email", user.getUsername())); 
				}
				else 
				{
					String messageCompanies = buildSubjectCompanies(user.getSubscribedCompanies());
					boolean status = EmailUtils.sendEmail(Configuration.getEmailFromAddress(), user.getEmail(), subject, messageCompanies);
					
					if(!status)
					{
						logger.info(String.format("dailyMailCompanyDigest error sending email user %s", user.getUsername()));
					}
				}
			}
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("dailyMailCompanyDigest - end"); 
		}

	}

	/**
	 * Prepares the email subject to send to the user
	 * @param subscribedCompanies
	 * @return
	 */
	private String buildSubjectCompanies(List<Company> subscribedCompanies)
	{
		StringBuffer buff = new StringBuffer();
		
		for (Company company : subscribedCompanies)
		{
			StockInfo data = company.getStockInfo();
			if (data != null){
				buff.append(String.format("Company: %s \n", company.getName()));
				buff.append("\n");
			
				buff.append(String.format("Last: %s \n", (data.getLastQuotation() == null ? "" : data.getLastQuotation())));
				buff.append(String.format("Time: %s \n", (data.getTime() == null ? "" : data.getTime())));
				buff.append(String.format("Variation: %s \n", (data.getVariation() == null ? "" : data.getVariation())));
				buff.append(String.format("Quantity: %s \n", (data.getQuantity() == null ? "" : data.getQuantity())));
				buff.append(String.format("Maximum: %s \n", (data.getMaximum() == null ? "" : data.getMaximum())));
				buff.append(String.format("Minimum: %s \n", (data.getMinimum() == null ? "" : data.getMinimum())));
				buff.append(String.format("Purchase: %s \n", (data.getPurchase() == null ? "" : data.getPurchase())));
				buff.append(String.format("Sell: %s \n", (data.getSell() == null ? "" : data.getSell())));
	
				buff.append("------------------------------------------------------\n");
			}
			else
			{
				logger.info(String.format("buildSubjectCompanies no StockInfo for company %s", company.getName()));	
			}
		}
		
		return buff.toString();
	}

}
