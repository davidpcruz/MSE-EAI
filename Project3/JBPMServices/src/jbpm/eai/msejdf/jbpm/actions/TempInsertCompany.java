package eai.msejdf.jbpm.actions;


import java.math.BigDecimal;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import eai.msejdf.esb.Company;
import eai.msejdf.utils.SOAMessageConstants;

public class TempInsertCompany implements ActionHandler {

	private static final long serialVersionUID = 1L;
	
	public void execute(ExecutionContext context) throws Exception {
		
		//TODO: Remove++ tmp for debug
		String body = (String)context.getContextInstance().getVariable(SOAMessageConstants.JBPM_MSG_BODY);
		//if (null == body)
		{
			context.getContextInstance().deleteVariable(SOAMessageConstants.JBPM_MSG_BODY);
			Company comp = new Company();
			comp.setAddress("fnorte@dei.uc.pt");
			comp.setName("BES");
			comp.setVariation( BigDecimal.valueOf(Float.parseFloat(body.replace("%", ""))));	
			context.getContextInstance().setVariable(SOAMessageConstants.JBPM_MSG_BODY, comp);			
		}
		//TODO: Remove-- tmp for debug		
	
	}
}