package eai.msejdf.esb.webservice;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.jboss.soa.esb.Service;

@WebService(name = "EsbWebservice", targetNamespace="http://msejdf/EsbWebservice")
public class EsbWebservice {

    @WebMethod
    public String sayGoodbye(@WebParam(name="message") String message) {
    	
		Service service = new Service("TESTE", "TESTE");

//    	Message esbMessage = 
        System.out.println("Web Service Parameter - message=" + message);
        return "... Ah Goodbye then!!!! - " + message;
    }

    @WebMethod
    public String sayAdios(String message) {
        System.out.println("Web Service Parameter - message=" + message);
        return "... Adios Amigo!!!! - " + message;
    }
    
    @WebMethod
    @Oneway
    public void sayGoodbyeWithoutResponse(@WebParam(name="message") String message) {
        System.out.println("Web Service Parameter - message=" + message);
    }
}
