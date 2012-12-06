package eai.msejdf.jbpm.actions;


import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import eai.msejdf.esb.Company;
import eai.msejdf.utils.SOAMessageConstants;

public class ExtractCompanyInfo implements ActionHandler {

	private static final long serialVersionUID = 1L;
	
	public void execute(ExecutionContext context) throws Exception {

		Company company = (Company)context.getContextInstance().getVariable(SOAMessageConstants.JBPM_MSG_BODY);
		
		if (null == company)
		{
			return;
		}
		context.getContextInstance().createVariable(SOAMessageConstants.JBPM_COMPANY_NAME, company.getName());
		context.getContextInstance().createVariable(SOAMessageConstants.JBPM_COMPANY_STOCK_VARIATION, company.getVariation());
	}

}
