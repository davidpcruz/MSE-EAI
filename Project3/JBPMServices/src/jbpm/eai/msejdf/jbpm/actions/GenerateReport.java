package eai.msejdf.jbpm.actions;


import org.apache.log4j.Logger;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import eai.msejdf.utils.SOAMessageConstants;

public class GenerateReport implements ActionHandler {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CheckStockVariationDecisionHandler.class);

	private static final long serialVersionUID = 1L;
	
	public void execute(ExecutionContext context) throws Exception {
		if (logger.isDebugEnabled())
		{
			logger.debug("execute - start"); //$NON-NLS-1$
		}
		
		boolean warnedAuto = (boolean)context.getContextInstance().getVariable(SOAMessageConstants.STATUS_REPORT_USERS_WARNED_AUTOMATICALLY);
		boolean warnedManager = (boolean)context.getContextInstance().getVariable(SOAMessageConstants.STATUS_REPORT_USERS_WARNED_BY_MANAGER);

		// TODO: Create status object and replace body
		
		
		if (logger.isDebugEnabled())
		{
			logger.debug("execute - end"); //$NON-NLS-1$
		}
	}

}
