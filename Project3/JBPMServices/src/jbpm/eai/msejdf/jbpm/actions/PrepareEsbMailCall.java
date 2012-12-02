package eai.msejdf.jbpm.actions;


import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import eai.msejdf.utils.SOAMessageConstants;

public class PrepareEsbMailCall implements ActionHandler {

	private static final long serialVersionUID = 1L;
	
	public void execute(ExecutionContext context) throws Exception {

		context.getContextInstance().createVariable(SOAMessageConstants.JBPM_MAIL_TO, "fnorte@dei.uc.pt,fjdnorte@gmail.com");
		context.getContextInstance().createVariable(SOAMessageConstants.JBPM_MAIL_SUBJECT, "Test Mail");
	}

}
