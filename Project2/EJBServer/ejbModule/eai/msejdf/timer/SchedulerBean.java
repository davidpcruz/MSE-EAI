package eai.msejdf.timer;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TimerService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import eai.msejdf.persistence.Company;
import eai.msejdf.persistence.StockInfo;
import eai.msejdf.persistence.User;
import eai.msejdf.utils.EmailUtils;
import eai.msejdf.utils.StringUtils;

/**
 * Session Bean implementation class Timer
 */
@Stateless
//@LocalBean
public class SchedulerBean implements ITimer {

	private static final String EMAIL_FROM = "davidpc@dei.uc.pt";
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SchedulerBean.class);
	@PersistenceContext(unitName = "JPAEAI")
	private EntityManager entityManager;
		
	@Resource
	TimerService timerService;
	
    /**
     * Default constructor. 
     */
    public SchedulerBean() {        
    }
        
//	@Override
//	public void triggerEmail()
//	{
//		boolean status = EmailUtils.sendEmail(EMAIL_FROM, "david.cruz@gmail.com", "testing", "testing");
//
////		System.out.println("\n\tinside createTimer() ....GOT the injected TimerService: "+ timerService);
////		System.out.println("\n\ttimerService [" + timerService + "]");
////		timerService.createTimer(20000, "Created new email timer Method");
//	}

//	@Timeout
//	public void timeout(Timer timer)
//	{
//		System.out.println("\n\tTimeout occurred - " + timer.getInfo());
//		EmailUtils.sendEmail("test@dei.uc.pt", EMAIL_FROM, "testing", "test message");
//	}

    
//	@Schedule(second = "*", minute = "*", hour="0")
    @Override
	public void dailyMailCompanyDigest() {

		if (logger.isDebugEnabled()) {
			logger.debug("dailyMailCompanyDigest - start"); 
		}

		Query query = entityManager.createQuery("SELECT u FROM User u join fetch u.subscribedCompanies");
		
		@SuppressWarnings("unchecked")
		List<User> userList = query.getResultList();
		
		
		if (true == userList.isEmpty()) 
		{
			logger.info("dailyMailCompanyDigest - no users to process"); 
		} 
		else
		{
			for (User user : userList)
			{
				if (StringUtils.IsNullOrWhiteSpace(user.getEmail()))
				{
					logger.info(String.format("dailyMailCompanyDigest user %s has no email", user.getUsername())); 
				}
				else 
				{
					String subjectCompanies = buildSubjectCompanies(user.getSubscribedCompanies()); 
					boolean status = EmailUtils.sendEmail(EMAIL_FROM, user.getEmail(), "testing", subjectCompanies);
					
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
			buff.append(String.format("Company: %s \n", company.getName()));
			buff.append("\n");
			StockInfo data = company.getStockInfo();
			if (data != null){
			
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
		}
		
		return buff.toString();
	}

}
