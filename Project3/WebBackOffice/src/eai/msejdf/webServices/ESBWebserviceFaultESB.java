
package eai.msejdf.webServices;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.4.6
 * 2012-12-07T11:53:57.153Z
 * Generated source version: 2.4.6
 */

@WebFault(name = "ESBWebserviceFault", targetNamespace = "http://msejdf/EsbWebservice")
public class ESBWebserviceFaultESB extends Exception {
    
    private String esbWebserviceFault;

    public ESBWebserviceFaultESB() {
        super();
    }
    
    public ESBWebserviceFaultESB(String message) {
        super(message);
    }
    
    public ESBWebserviceFaultESB(String message, Throwable cause) {
        super(message, cause);
    }

    public ESBWebserviceFaultESB(String message, String esbWebserviceFault) {
        super(message);
        this.esbWebserviceFault = esbWebserviceFault;
    }

    public ESBWebserviceFaultESB(String message, String esbWebserviceFault, Throwable cause) {
        super(message, cause);
        this.esbWebserviceFault = esbWebserviceFault;
    }

    public String getFaultInfo() {
        return this.esbWebserviceFault;
    }
}
