package eai.msejdf.jbpm.actions;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

public class CheckStockVariationDecisionHandler implements DecisionHandler  {
	public static final String APPROVE_NOTIFICATION = "Approve Stock Variation Notification";
	public static final String NO_ACTION_REQUIRED = "No Action Required";
	public static final String NOTIFY_USER = "Notify User";
	
	private static final float VARIATION_THRESHOLD_APPROVAL_REQURIED_LOW_INCL = (float)0.5; /* lower threshold (inclusive) */
	private static final float VARIATION_THRESHOLD_APPROVAL_REQURIED_HIGH_INCL = (float)1; /* upper threshold (inclusive) */
	
	private static final long serialVersionUID = 1L;

	public String decide(ExecutionContext executionContext) throws Exception 
	{
		String message = executionContext.getContextInstance().getVariable("msgBody").toString();
		
		//TODO: Extract variation from the message (or variable carrying it) and remove this
		String variationStr = message;

		float variation = Math.abs(Float.parseFloat(variationStr));
		
		if (variation < CheckStockVariationDecisionHandler.VARIATION_THRESHOLD_APPROVAL_REQURIED_LOW_INCL)
		{
			/* No action required */ 
			return CheckStockVariationDecisionHandler.NO_ACTION_REQUIRED;
		}
		if (variation <= CheckStockVariationDecisionHandler.VARIATION_THRESHOLD_APPROVAL_REQURIED_HIGH_INCL)
		{
			/* User approval required */
			return CheckStockVariationDecisionHandler.APPROVE_NOTIFICATION;
		}
		/* Automatic notification */
		return CheckStockVariationDecisionHandler.NOTIFY_USER;
	}
}
