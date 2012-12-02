package eai.msejdf.jbpm.actions;


import java.util.Date;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import eai.msejdf.utils.SOAMessageConstants;

public class PrepareEsbMailCall implements ActionHandler {

	private static final long serialVersionUID = 1L;
	
	public void execute(ExecutionContext context) throws Exception {

		//TODO: Remove ++ tmp for debug
		String subject2 = String.format("%1$tH:%1$tM:%1$tS%n", new Date());
		
		//TODO: We need a user address list (separated by SOAMessageConstants.MAIL_ADDRESS_SEPARATOR)
		
		if (new Date().getSeconds() < 30)
		{
			context.getContextInstance().createVariable(SOAMessageConstants.JBPM_MAIL_TO, "fnorte@dei.uc.pt");			
		}
		else
		{
			context.getContextInstance().createVariable(SOAMessageConstants.JBPM_MAIL_TO, "fjdnorte@gmail.com");			
		}
		context.getContextInstance().createVariable(SOAMessageConstants.JBPM_MAIL_SUBJECT, subject2);
		return;
		//TODO: Remove --
		
		
//		String subject = String.format("Company stock values on %1$te/%1$tm/%1$tY at %1$tH:%1$tM:%1$tS%n", new Date());
//		
//		//TODO: We need a user address list (separated by SOAMessageConstants.MAIL_ADDRESS_SEPARATOR)
//		
//		context.getContextInstance().createVariable(SOAMessageConstants.JBPM_MAIL_TO, "fnorte@dei.uc.pt,fjdnorte@gmail.com");
//		context.getContextInstance().createVariable(SOAMessageConstants.JBPM_MAIL_SUBJECT, subject);
	}

}
