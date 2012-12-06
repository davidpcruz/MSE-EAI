package eai.msejdf.esb.webservice;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "ESBWebServiceException", namespace = "http://msejdf/EsbWebservice")
@XmlType(propOrder = { "message" })
public class ESBWebserviceFault {

	  private String message;

	  public ESBWebserviceFault() {		  
	  }
	  
	  public ESBWebserviceFault(String message) {
	    this.message = message;
	  }

	  public String getMessage() {
	    return message;
	  }

	  public void setMessage(String message) {
	    this.message = message;
	  }
}
