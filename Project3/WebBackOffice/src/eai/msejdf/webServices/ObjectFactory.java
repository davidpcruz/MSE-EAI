
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

    private final static QName _IncrementUserEmailCountFromIdResponse_QNAME = new QName("http://www.eai.org/msejdf", "incrementUserEmailCountFromIdResponse");
    private final static QName _GetUserEmailCount_QNAME = new QName("http://www.eai.org/msejdf", "getUserEmailCount");
    private final static QName _GetUsersFollowingCompanyResponse_QNAME = new QName("http://www.eai.org/msejdf", "getUsersFollowingCompanyResponse");
    private final static QName _GetUsersFollowingCompany_QNAME = new QName("http://www.eai.org/msejdf", "getUsersFollowingCompany");
    private final static QName _IncrementUserEmailCountFromId_QNAME = new QName("http://www.eai.org/msejdf", "incrementUserEmailCountFromId");
    private final static QName _GetUserEmailCountResponse_QNAME = new QName("http://www.eai.org/msejdf", "getUserEmailCountResponse");
    private final static QName _IncrementUserEmailCountFromList_QNAME = new QName("http://www.eai.org/msejdf", "incrementUserEmailCountFromList");
    private final static QName _IncrementUserEmailCountFromListResponse_QNAME = new QName("http://www.eai.org/msejdf", "incrementUserEmailCountFromListResponse");
    private final static QName _ConfigurationException_QNAME = new QName("http://www.eai.org/msejdf", "ConfigurationException");

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
     * Create an instance of {@link IncrementUserEmailCountFromIdResponse }
     * 
     */
    public IncrementUserEmailCountFromIdResponse createIncrementUserEmailCountFromIdResponse() {
        return new IncrementUserEmailCountFromIdResponse();
    }

    /**
     * Create an instance of {@link IncrementUserEmailCountFromId }
     * 
     */
    public IncrementUserEmailCountFromId createIncrementUserEmailCountFromId() {
        return new IncrementUserEmailCountFromId();
    }

    /**
     * Create an instance of {@link GetUsersFollowingCompany }
     * 
     */
    public GetUsersFollowingCompany createGetUsersFollowingCompany() {
        return new GetUsersFollowingCompany();
    }

    /**
     * Create an instance of {@link IncrementUserEmailCountFromList }
     * 
     */
    public IncrementUserEmailCountFromList createIncrementUserEmailCountFromList() {
        return new IncrementUserEmailCountFromList();
    }

    /**
     * Create an instance of {@link GetUserEmailCountResponse }
     * 
     */
    public GetUserEmailCountResponse createGetUserEmailCountResponse() {
        return new GetUserEmailCountResponse();
    }

    /**
     * Create an instance of {@link ConfigurationException }
     * 
     */
    public ConfigurationException createConfigurationException() {
        return new ConfigurationException();
    }

    /**
     * Create an instance of {@link IncrementUserEmailCountFromListResponse }
     * 
     */
    public IncrementUserEmailCountFromListResponse createIncrementUserEmailCountFromListResponse() {
        return new IncrementUserEmailCountFromListResponse();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IncrementUserEmailCountFromIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.eai.org/msejdf", name = "incrementUserEmailCountFromIdResponse")
    public JAXBElement<IncrementUserEmailCountFromIdResponse> createIncrementUserEmailCountFromIdResponse(IncrementUserEmailCountFromIdResponse value) {
        return new JAXBElement<IncrementUserEmailCountFromIdResponse>(_IncrementUserEmailCountFromIdResponse_QNAME, IncrementUserEmailCountFromIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserEmailCount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.eai.org/msejdf", name = "getUserEmailCount")
    public JAXBElement<GetUserEmailCount> createGetUserEmailCount(GetUserEmailCount value) {
        return new JAXBElement<GetUserEmailCount>(_GetUserEmailCount_QNAME, GetUserEmailCount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUsersFollowingCompanyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.eai.org/msejdf", name = "getUsersFollowingCompanyResponse")
    public JAXBElement<GetUsersFollowingCompanyResponse> createGetUsersFollowingCompanyResponse(GetUsersFollowingCompanyResponse value) {
        return new JAXBElement<GetUsersFollowingCompanyResponse>(_GetUsersFollowingCompanyResponse_QNAME, GetUsersFollowingCompanyResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUsersFollowingCompany }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.eai.org/msejdf", name = "getUsersFollowingCompany")
    public JAXBElement<GetUsersFollowingCompany> createGetUsersFollowingCompany(GetUsersFollowingCompany value) {
        return new JAXBElement<GetUsersFollowingCompany>(_GetUsersFollowingCompany_QNAME, GetUsersFollowingCompany.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IncrementUserEmailCountFromId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.eai.org/msejdf", name = "incrementUserEmailCountFromId")
    public JAXBElement<IncrementUserEmailCountFromId> createIncrementUserEmailCountFromId(IncrementUserEmailCountFromId value) {
        return new JAXBElement<IncrementUserEmailCountFromId>(_IncrementUserEmailCountFromId_QNAME, IncrementUserEmailCountFromId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserEmailCountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.eai.org/msejdf", name = "getUserEmailCountResponse")
    public JAXBElement<GetUserEmailCountResponse> createGetUserEmailCountResponse(GetUserEmailCountResponse value) {
        return new JAXBElement<GetUserEmailCountResponse>(_GetUserEmailCountResponse_QNAME, GetUserEmailCountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IncrementUserEmailCountFromList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.eai.org/msejdf", name = "incrementUserEmailCountFromList")
    public JAXBElement<IncrementUserEmailCountFromList> createIncrementUserEmailCountFromList(IncrementUserEmailCountFromList value) {
        return new JAXBElement<IncrementUserEmailCountFromList>(_IncrementUserEmailCountFromList_QNAME, IncrementUserEmailCountFromList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IncrementUserEmailCountFromListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.eai.org/msejdf", name = "incrementUserEmailCountFromListResponse")
    public JAXBElement<IncrementUserEmailCountFromListResponse> createIncrementUserEmailCountFromListResponse(IncrementUserEmailCountFromListResponse value) {
        return new JAXBElement<IncrementUserEmailCountFromListResponse>(_IncrementUserEmailCountFromListResponse_QNAME, IncrementUserEmailCountFromListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConfigurationException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.eai.org/msejdf", name = "ConfigurationException")
    public JAXBElement<ConfigurationException> createConfigurationException(ConfigurationException value) {
        return new JAXBElement<ConfigurationException>(_ConfigurationException_QNAME, ConfigurationException.class, null, value);
    }

}
