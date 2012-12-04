package eai.msejdf.jbpm.actions;


import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import eai.msejdf.utils.SOAMessageConstants;

public class IncCompaniesProcessed implements ActionHandler {

	private static final long serialVersionUID = 1L;
	
	public void execute(ExecutionContext context) throws Exception {

		String key = SOAMessageConstants.STATUS_REPORT_COMPANIES_PROCESSED;
		Integer count = (Integer)context.getContextInstance().getVariable(key);
		
		if (null == count)
		{
			count = new Integer(0); 
		}
		count++;
		
		context.getContextInstance().createVariable(key, count);
	}

}
