package eai.msejdf.esb;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import eai.msejdf.config.Configuration;
import eai.msejdf.utils.SOAMessageConstants;
import eai.msejdf.utils.EmailUtils;
import eai.msejdf.utils.StringUtils;

/**
 * Esb Service that sends an e-mail to a list of e-mail addresses
 */
public class SendMail extends AbstractActionLifecycle {

	private static final Logger logger = Logger.getLogger(SendMail.class);

	protected ConfigTree _config;

	public Message process(Message message) throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("SendMail - start");
		}

		// Retrieve the supplied mail parameter
		String mailTo = (String) message.getBody().get(SOAMessageConstants.ESB_MAIL_TO);
		String mailSubject = (String) message.getBody().get(SOAMessageConstants.ESB_MAIL_SUBJECT);
		String mailMessage = (String) message.getBody().get(SOAMessageConstants.ESB_MAIL_MESSAGE);

		if (logger.isInfoEnabled()) 
		{
			logger.info("mailTo:" + mailTo);
			logger.info("mailSubject:" + mailSubject);
			logger.info("mailMessage:" + mailMessage);
		}
		
		if (StringUtils.IsNullOrWhiteSpace(mailTo)) 
		{
			logger.info("Invalid destination address");
		} 
		else 
		{
			// Send an e-mail to an address
			// Note: mailTo may contain multiple addresses. Extract each to send separate mails
			List<String> addressList = Arrays.asList(mailTo.split(SOAMessageConstants.MAIL_ADDRESS_SEPARATOR));
			boolean status;
			
			for(String address : addressList)
			{
				if (StringUtils.IsNullOrWhiteSpace(address))
				{
					// Ignore empty addresses (may be due two consecutive mail separators 
					continue;
				}
				status = EmailUtils.sendEmail(Configuration.getEmailFromAddress(), 
											  address,
											  mailSubject, 
											  mailMessage);
				if (false == status) {
					logger.info("Error sending e-mail to " + address);
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("SendMail - end");
		}

		return message;
	}

	public SendMail(ConfigTree config) {
		_config = config;
	}
}
