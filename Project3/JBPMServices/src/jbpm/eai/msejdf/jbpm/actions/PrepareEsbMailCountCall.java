package eai.msejdf.jbpm.actions;


import java.util.ArrayList;
import java.util.List;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import eai.msejdf.esb.User;
import eai.msejdf.utils.SOAMessageConstants;

public class PrepareEsbMailCountCall implements ActionHandler {

	private static final long serialVersionUID = 1L;
	
	public void execute(ExecutionContext context) throws Exception {

		@SuppressWarnings("unchecked")
		List<User> userList =  (List<User>)context.getContextInstance().getVariable(SOAMessageConstants.JBPM_USER_LIST);
		List<Long> userIdList = new ArrayList<Long>();
		
		// Create a list of user ids
		for(User user : userList)
		{
			userIdList.add(user.getId());
		}
		
		context.getContextInstance().setVariable(SOAMessageConstants.JBPM_USER_ID_LIST, userIdList);				
	}
}
