package eai.msejdf.jbpm.actions;


import java.util.Date;
import java.util.List;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import eai.msejdf.esb.Company;
import eai.msejdf.esb.User;
import eai.msejdf.utils.SOAMessageConstants;

public class PrepareEsbMailSendCall implements ActionHandler {

	private static final long serialVersionUID = 1L;
	
	public void execute(ExecutionContext context) throws Exception {

		//TODO: Remove ++ tmp for debug
//		String subject2 = String.format("%1$tH:%1$tM:%1$tS%n", new Date());
//		
//		if (new Date().getSeconds() < 30)
//		{
//			context.getContextInstance().createVariable(SOAMessageConstants.JBPM_MAIL_TO, "fnorte@dei.uc.pt");			
//		}
//		else
//		{
//			context.getContextInstance().createVariable(SOAMessageConstants.JBPM_MAIL_TO, "fjdnorte@gmail.com");			
//		}
//		context.getContextInstance().createVariable(SOAMessageConstants.JBPM_MAIL_SUBJECT, subject2);
//		
//		String message = buildMailMessage(context);
//		if (null == message)
//		{
//			message = new String("Default mail message");
//		}
//		context.getContextInstance().createVariable(SOAMessageConstants.JBPM_MAIL_MESSAGE, message);
//		return;
		//TODO: Remove --
	
		String subject = String.format("Company stock values on %1$te/%1$tm/%1$tY at %1$tH:%1$tM:%1$tS%n", new Date());
		
		context.getContextInstance().createVariable(SOAMessageConstants.JBPM_MAIL_TO, buildMailAddressList(context));
		context.getContextInstance().createVariable(SOAMessageConstants.JBPM_MAIL_SUBJECT, subject);
		context.getContextInstance().createVariable(SOAMessageConstants.JBPM_MAIL_MESSAGE, buildMailMessage(context));
		
	}

	/**
	 * Creates a string containing the e-mail addresses of all users present in the execution context
	 * @param context The BPM execution context
	 * @return String with e-mail address list
	 */
	private String buildMailAddressList(ExecutionContext context)
	{
		StringBuffer buffer = new StringBuffer();
		@SuppressWarnings("unchecked")
		List<User> userList =  (List<User>)context.getContextInstance().getVariable(SOAMessageConstants.JBPM_USER_LIST);
		
		// Create a mail address list separated by the mail separator character
		for(User user : userList)
		{
			System.out.println("Mail : " + user.getMailAddress());
			buffer.append(user.getMailAddress());
			buffer.append(SOAMessageConstants.MAIL_ADDRESS_SEPARATOR);
		}
		// Remove trailing mail separator added by the loop (if the list is not empty)
		if (buffer.length() > 0)
		{
			buffer.deleteCharAt(buffer.length() - 1);
		}
		return buffer.toString();
	}
	
	/**
	 * Builds a mail message string with company information
	 * @param context The BPM execution context
	 * @return Mail message string
	 */
	private String buildMailMessage(ExecutionContext context)
	{
		StringBuffer buffer = new StringBuffer();
		
		Company company = (Company)context.getContextInstance().getVariable(SOAMessageConstants.JBPM_MSG_BODY);
		if (null == company)
		{
			return null;
		}
		
		buffer.append("Hello");
		buffer.append("\n");
		buffer.append("\n");
		buffer.append("The stock variation threshold violation triggered this notification. Please check the info below.");
		buffer.append("\n");
		buffer.append(String.format("Company: %s \n", company.getName()));
		buffer.append("\n");
	
		buffer.append(String.format("Last: %s \n", (company.getLastQuotation() == null ? "" : company.getLastQuotation())));
		buffer.append(String.format("Time: %s \n", (company.getTime() == null ? "" : company.getTime())));
		buffer.append(String.format("Variation: %s \n", (company.getVariation() == null ? "" : company.getVariation())));
		buffer.append(String.format("Quantity: %s \n", (company.getQuantity() == null ? "" : company.getQuantity())));
		buffer.append(String.format("Maximum: %s \n", (company.getMaximum() == null ? "" : company.getMaximum())));
		buffer.append(String.format("Minimum: %s \n", (company.getMinimum() == null ? "" : company.getMinimum())));
		buffer.append(String.format("Purchase: %s \n", (company.getPurchase() == null ? "" : company.getPurchase())));
		buffer.append(String.format("Sell: %s \n", (company.getSell() == null ? "" : company.getSell())));

		return buffer.toString();
	}	
}
