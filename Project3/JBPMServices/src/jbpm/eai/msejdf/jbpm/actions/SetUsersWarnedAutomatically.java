package eai.msejdf.jbpm.actions;


import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import eai.msejdf.utils.SOAMessageConstants;

public class SetUsersWarnedAutomatically implements ActionHandler {

	private static final long serialVersionUID = 1L;
	
	public void execute(ExecutionContext context) throws Exception {

		context.getContextInstance().createVariable(SOAMessageConstants.STATUS_REPORT_USERS_WARNED_AUTOMATICALLY, true);
	}

}
