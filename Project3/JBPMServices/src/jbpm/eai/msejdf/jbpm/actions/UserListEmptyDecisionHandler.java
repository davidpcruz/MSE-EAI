
package eai.msejdf.jbpm.actions;

import java.util.List;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;
import eai.msejdf.esb.User;
import eai.msejdf.utils.SOAMessageConstants;

public class UserListEmptyDecisionHandler implements DecisionHandler  {

	public static final String LIST_HAS_NO_USERS = "HasNoUsers";
	public static final String LIST_HAS_USERS = "HasUsers";
	
	private static final long serialVersionUID = 1L;

	public String decide(ExecutionContext executionContext) throws Exception 
	{
		@SuppressWarnings("unchecked")
		List<User> userList = (List<User>)executionContext.getContextInstance().getVariable(SOAMessageConstants.JBPM_USER_LIST);
		
		if ((null == userList) || (userList.isEmpty()))
		{
			return UserListEmptyDecisionHandler.LIST_HAS_NO_USERS;
		}
		return UserListEmptyDecisionHandler.LIST_HAS_USERS;
	}
}
