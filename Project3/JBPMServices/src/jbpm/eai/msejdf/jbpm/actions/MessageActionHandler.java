package eai.msejdf.jbpm.actions;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import eai.msejdf.utils.SOAMessageConstants;

public class MessageActionHandler implements ActionHandler {

	private static final long serialVersionUID = 1L;
	
	/**
	 * The message member gets its value from the configuration in the 
	 * processdefinition. The value is injected directly by the engine. 
	 */
	String message;

	/**
	 * A message process variable is assigned the value of the message
	 * member. The process variable is created if it doesn't exist yet.
	 */
	public void execute(ExecutionContext context) throws Exception {
		System.out.println("MessageActionHandler...");
		System.out.println("MessageActionHandler: " + message);
		
//		Integer count1 = (Integer)context.getContextInstance().getVariable(SOAMessageConstants.STATUS_REPORT_COMPANIES_PROCESSED);
//		Integer count2 = (Integer)context.getContextInstance().getVariable(SOAMessageConstants.STATUS_REPORT_USERS_WARNED_BY_MANAGER);
//		Integer count3 = (Integer)context.getContextInstance().getVariable(SOAMessageConstants.STATUS_REPORT_USERS_WARNED_AUTOMATICALLY);
//		System.out.println("Companies Processed = " + count1);
//		System.out.println("Users Warned Mng = " + count2);
//		System.out.println("Users Warned Auto = " + count3);
		
		System.out.println("MessageActionHandler...Complete");
	}

}
