package eai.msejdf.jbpm.actions;


import java.util.ArrayList;
import java.util.List;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import eai.msejdf.esb.User;
import eai.msejdf.utils.SOAMessageConstants;

public class FilterUsersByValidMailAddress implements ActionHandler {

	private static final long serialVersionUID = 1L;
	
	public void execute(ExecutionContext context) throws Exception {
		
		@SuppressWarnings("unchecked")
		List<User> userList = (List<User>)context.getContextInstance().getVariable(SOAMessageConstants.JBPM_USER_LIST);

		if (null == userList)
		{
			// No users to filter
			return;
		}
		
		List<User> filteredUserList = new ArrayList<User>();
		
		// Fill filtered user list with users that have a valid e-mail address
		for(User user : userList)
		{
			if (false != validateMail(user.getMailAddress()))
			{
				filteredUserList.add(user);
			}
		}	
		context.getContextInstance().setVariable(SOAMessageConstants.JBPM_USER_LIST, filteredUserList);			
	}
	
	private boolean validateMail(String mailAddress)
	{
		// For now, the validation consists in checking only that an e-mail address is supplied, but
		// additional checks (syntax, etc.) could be included if necessary
		return (null == mailAddress);
	}
}
