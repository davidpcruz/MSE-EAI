package eai.msejdf.jbpm.actions;


import java.util.List;

import org.apache.log4j.Logger;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import eai.msejdf.esb.Report;
import eai.msejdf.esb.User;
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
		
		// These objects hold boolean if not null
		Object objWarnedAuto = context.getContextInstance().getVariable(SOAMessageConstants.STATUS_REPORT_USERS_WARNED_AUTOMATICALLY);
		Object objWarnedManager = context.getContextInstance().getVariable(SOAMessageConstants.STATUS_REPORT_USERS_WARNED_BY_MANAGER);
		
		boolean warnedAuto = (boolean) ((null == objWarnedAuto) ? false : (boolean) objWarnedAuto);
		boolean warnedManager = (boolean) ((null == objWarnedManager) ? false : (boolean) objWarnedManager);

		@SuppressWarnings("unchecked")
		List<User> userList = (List<User>)context.getContextInstance().getVariable(SOAMessageConstants.JBPM_USER_LIST);
		
		int userCount = (null == userList) ? 0 : userList.size();
		
		// Set the count for the number of users warned automatically or by the manager. They are mutually exclusive
		int warnedAutoCount = warnedAuto ? userCount : 0;
		int warnedManagerCount = warnedManager ? userCount : 0;
		
		// Create the report
		Report report = new Report();		
		report.setUsersWarnedAutomatically(warnedAutoCount);
		report.setUsersWarnedByManager(warnedManagerCount);
		
		if (logger.isInfoEnabled())
		{
			logger.info("Users warned automatically = " + warnedAutoCount); //$NON-NLS-1$
			logger.info("Users warned by manager = " + warnedManagerCount); //$NON-NLS-1$
		}
		
		//Replace the message body with the report
		context.getContextInstance().setVariable(SOAMessageConstants.JBPM_MSG_BODY, report);

		if (logger.isDebugEnabled())
		{
			logger.debug("execute - end"); //$NON-NLS-1$
		}
	}
}
