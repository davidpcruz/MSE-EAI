package eai.msejdf.jbpm.actions;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

public class CheckStockVariationDecision implements DecisionHandler  {
	public static final String APPROVE_NOTIFICATION = "Approve Stock Variation Notification";
	public static final String NO_ACTION_REQUIRED = "No Action Required";
	public static final String NOTIFY_USER = "Notify User";
	
	
	private static final long serialVersionUID = 1L;

	public String decide(ExecutionContext executionContext) throws Exception 
	{
		String message = executionContext.getContextInstance().getVariable("msgBody").toString();
		
		//TODO: Extract variation from the message (or variable carrying it)
		String variation = "0.5%";

		// TODO: Implement decision logic
		{
			return CheckStockVariationDecision.APPROVE_NOTIFICATION;
		}
	}
}
