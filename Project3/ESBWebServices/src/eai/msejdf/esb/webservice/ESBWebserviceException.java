package eai.msejdf.esb.webservice;

import javax.xml.ws.WebFault;

@WebFault(name = "ESBWebserviceFault", targetNamespace="http://msejdf/EsbWebservice")
public class ESBWebserviceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ESBWebserviceFault faultBean;

	public ESBWebserviceException(Exception e) {
		super(e);
	}

    public ESBWebserviceException() {
        super();
    }

    public ESBWebserviceException(String message, ESBWebserviceFault faultBean, Throwable cause) {
        super(message, cause);
        this.faultBean = faultBean;
    }

    public ESBWebserviceException(String message, ESBWebserviceFault faultBean) {
        super(message);
        this.faultBean = faultBean;
    }

    public ESBWebserviceFault getFaultInfo() {
        return faultBean;
    }
}
