
package eai.msejdf.webServices;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the eai.msejdf.webServices package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ESBWebserviceFault_QNAME = new QName("http://msejdf/EsbWebservice", "ESBWebserviceFault");
    private final static QName _GetUserEmailCountResponse_QNAME = new QName("http://msejdf/EsbWebservice", "getUserEmailCountResponse");
    private final static QName _ESBWebServiceException_QNAME = new QName("http://msejdf/EsbWebservice", "ESBWebServiceException");
    private final static QName _GetUsersFollowingCompany_QNAME = new QName("http://msejdf/EsbWebservice", "getUsersFollowingCompany");
    private final static QName _GetUserEmailCount_QNAME = new QName("http://msejdf/EsbWebservice", "getUserEmailCount");
    private final static QName _GetUsersFollowingCompanyResponse_QNAME = new QName("http://msejdf/EsbWebservice", "getUsersFollowingCompanyResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: eai.msejdf.webServices
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetUsersFollowingCompanyResponse }
     * 
     */
    public GetUsersFollowingCompanyResponse createGetUsersFollowingCompanyResponse() {
        return new GetUsersFollowingCompanyResponse();
    }

    /**
     * Create an instance of {@link GetUserEmailCount }
     * 
     */
    public GetUserEmailCount createGetUserEmailCount() {
        return new GetUserEmailCount();
    }

    /**
     * Create an instance of {@link GetUsersFollowingCompany }
     * 
     */
    public GetUsersFollowingCompany createGetUsersFollowingCompany() {
        return new GetUsersFollowingCompany();
    }

    /**
     * Create an instance of {@link EsbWebserviceFault }
     * 
     */
    public EsbWebserviceFault createEsbWebserviceFault() {
        return new EsbWebserviceFault();
    }

    /**
     * Create an instance of {@link GetUserEmailCountResponse }
     * 
     */
    public GetUserEmailCountResponse createGetUserEmailCountResponse() {
        return new GetUserEmailCountResponse();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EsbWebserviceFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://msejdf/EsbWebservice", name = "ESBWebserviceFault")
    public JAXBElement<EsbWebserviceFault> createESBWebserviceFault(EsbWebserviceFault value) {
        return new JAXBElement<EsbWebserviceFault>(_ESBWebserviceFault_QNAME, EsbWebserviceFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserEmailCountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://msejdf/EsbWebservice", name = "getUserEmailCountResponse")
    public JAXBElement<GetUserEmailCountResponse> createGetUserEmailCountResponse(GetUserEmailCountResponse value) {
        return new JAXBElement<GetUserEmailCountResponse>(_GetUserEmailCountResponse_QNAME, GetUserEmailCountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EsbWebserviceFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://msejdf/EsbWebservice", name = "ESBWebServiceException")
    public JAXBElement<EsbWebserviceFault> createESBWebServiceException(EsbWebserviceFault value) {
        return new JAXBElement<EsbWebserviceFault>(_ESBWebServiceException_QNAME, EsbWebserviceFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUsersFollowingCompany }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://msejdf/EsbWebservice", name = "getUsersFollowingCompany")
    public JAXBElement<GetUsersFollowingCompany> createGetUsersFollowingCompany(GetUsersFollowingCompany value) {
        return new JAXBElement<GetUsersFollowingCompany>(_GetUsersFollowingCompany_QNAME, GetUsersFollowingCompany.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserEmailCount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://msejdf/EsbWebservice", name = "getUserEmailCount")
    public JAXBElement<GetUserEmailCount> createGetUserEmailCount(GetUserEmailCount value) {
        return new JAXBElement<GetUserEmailCount>(_GetUserEmailCount_QNAME, GetUserEmailCount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUsersFollowingCompanyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://msejdf/EsbWebservice", name = "getUsersFollowingCompanyResponse")
    public JAXBElement<GetUsersFollowingCompanyResponse> createGetUsersFollowingCompanyResponse(GetUsersFollowingCompanyResponse value) {
        return new JAXBElement<GetUsersFollowingCompanyResponse>(_GetUsersFollowingCompanyResponse_QNAME, GetUsersFollowingCompanyResponse.class, null, value);
    }

}
