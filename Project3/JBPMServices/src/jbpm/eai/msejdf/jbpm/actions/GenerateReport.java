package eai.msejdf.jbpm.actions;


import org.apache.log4j.Logger;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import eai.msejdf.esb.Report;
import eai.msejdf.utils.SOAMessageConstants;

public class GenerateReport implements ActionHandler {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(GenerateReport.class);

	private static final long serialVersionUID = 1L;
	
	public void execute(ExecutionContext context) throws Exception {
		if (logger.isDebugEnabled())
		{
			logger.debug("execute - start"); //$NON-NLS-1$
		}
		
		// These objects hold integer counters if not null
		Object objWarnedAuto = context.getContextInstance().getVariable(SOAMessageConstants.STATUS_REPORT_USERS_WARNED_AUTOMATICALLY);
		Object objWarnedManager = context.getContextInstance().getVariable(SOAMessageConstants.STATUS_REPORT_USERS_WARNED_BY_MANAGER);
		
		int warnedAuto = (int) ((null == objWarnedAuto) ? 0 : (int) objWarnedAuto);
		int warnedManager = (int) ((null == objWarnedManager) ? 0 : (int) objWarnedManager);

		// Create the report
		Report report = new Report();		
		report.setUsersWarnedAutomatically(warnedAuto);
		report.setUsersWarnedByManager(warnedManager);
		
		if (logger.isInfoEnabled())
		{
			logger.info("Users warned automatically = " + warnedAuto); //$NON-NLS-1$
			logger.info("Users warned by manager = " + warnedManager); //$NON-NLS-1$
		}
		
		//Replace the message body with the report
		context.getContextInstance().setVariable(SOAMessageConstants.JBPM_MSG_BODY, report);

		if (logger.isDebugEnabled())
		{
			logger.debug("execute - end"); //$NON-NLS-1$
		}
	}

}
