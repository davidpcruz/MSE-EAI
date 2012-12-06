package eai.msejdf.esb.webservice;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.couriers.FaultMessageException;
import org.jboss.soa.esb.listeners.message.MessageDeliverException;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;
import org.jboss.soa.esb.services.registry.RegistryException;

import eai.msejdf.esb.User;
import eai.msejdf.utils.SOAMessageConstants;

@WebService(name = "EsbWebservice", targetNamespace="http://msejdf/EsbWebservice")
public class EsbWebservice {

	private static final int ESB_CALL_TIMEOUT = 5000;
	private static final String SERVICE_GET_USERS_FOLLOWING_COMPANY = "GetUsersFollowingCompany";
	private static final String ESB_SERVICE_CATEGORY = "EAI_ESB";

	@SuppressWarnings("unchecked")
	@WebMethod
    public List<User> getUsersCompany(@WebParam(name="companyName") String companyName) throws ESBWebserviceException {
    	
    	List<User> userList = new ArrayList<User>();
    	
    	Message esbMessage = MessageFactory.getInstance().getMessage();

    	esbMessage.getBody().add(SOAMessageConstants.ESB_COMPANY_NAME, companyName);
    	    	    	
    	ServiceInvoker service;
		try {
			service = new ServiceInvoker(ESB_SERVICE_CATEGORY, SERVICE_GET_USERS_FOLLOWING_COMPANY);
	    	Message response = service.deliverSync(esbMessage, ESB_CALL_TIMEOUT);
	    		    	
	        userList = (List<User>) response.getBody().get(SOAMessageConstants.ESB_USER_LIST);
		} catch (MessageDeliverException | FaultMessageException | RegistryException e) {
			throw new ESBWebserviceException(e);
		}
    	    	
    	return userList;   			
    			
    }

}
