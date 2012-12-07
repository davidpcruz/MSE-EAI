package eai.msejdf.web.backoffice.admin;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import eai.msejdf.admin.IAdmin;
import eai.msejdf.exception.ConfigurationException;
import eai.msejdf.exception.SecurityException;
import eai.msejdf.persistence.Company;
import eai.msejdf.persistence.User;
import eai.msejdf.sort.CompanySort;
import eai.msejdf.sort.UserSort;
import eai.msejdf.utils.EJBLookupConstants;
import eai.msejdf.webServices.ESBWebserviceFaultESB;
import eai.msejdf.webServices.EsbWebservice;
import eai.msejdf.webServices.EsbWebserviceService;

@ManagedBean(name = "adminWS")
@ViewScoped
public class AdminBackOfficeBeanWS {

	private IAdmin adminBean;
	private List<Company> companyList;

	private List<User> userList;

	private List<eai.msejdf.webServices.User> subscribedUserListWS;

	// the selected company Name to search
	private String searchCompanyNameSelect;

	EsbWebserviceService serviceESB = new EsbWebserviceService();

	EsbWebservice portESB = serviceESB.getEsbWebservicePort();

	/**
	 * Creates a AdminWS bean to handle the list queries
	 * 
	 * @throws NamingException
	 * @throws ConfigurationException
	 */
	public AdminBackOfficeBeanWS() throws NamingException, ConfigurationException {
		InitialContext ctx = new InitialContext();

		this.adminBean = (IAdmin) ctx.lookup(EJBLookupConstants.EJB_I_ADMIN);
	}

	public String getSearchCompanyNameSelect() {
		return searchCompanyNameSelect;
	}

	public void setSearchCompanyNameSelect(String searchCompanyNameSelect) {
		this.searchCompanyNameSelect = searchCompanyNameSelect;
	}

	/**
	 * searches for a users associated to the companies using a WS -> ESB -> WS
	 * 
	 * @return
	 * @throws ESBWebserviceFaultESB
	 * @throws SecurityException
	 */
	public boolean searchUsersCompaniesWS() throws ESBWebserviceFaultESB {
		// basic validations
		if (this.getSearchCompanyNameSelect() == null) {
			return false;
		}

		this.subscribedUserListWS = getUserFollowCompanyListUsingWS(this.getSearchCompanyNameSelect(), UserSort.NAME_ASC);

		return true;
	}

	private List<eai.msejdf.webServices.User> getUserFollowCompanyListUsingWS(String searchCompanyNameSelect, UserSort nameAsc) throws ESBWebserviceFaultESB {
		List<eai.msejdf.webServices.User> userListWS = portESB.getUsersFollowingCompany(searchCompanyNameSelect);
		for (eai.msejdf.webServices.User user : userListWS) {
			System.out.println("Server said: " + user.getName() + " " + user.getMailAddress());
		}

		return userListWS;
	}

	/**
	 * Gets the subscribed user list on the WebService.
	 * 
	 * @return the subscribed user list
	 */
	public List<eai.msejdf.webServices.User> getSubscribedUserListWS() {
		return subscribedUserListWS;
	}

	/**
	 * Returns all the users order by age
	 * 
	 * @return the user list
	 */
	public List<User> getUserList() {
		if (FacesContext.getCurrentInstance().getRenderResponse()) {
			// Reload to get most recent data.
			this.userList = this.adminBean.getUserList(UserSort.BIRTHDAY_ASC);
		}
		return userList;
	}

	/**
	 * Gets a List of user using the ESB WebService
	 * 
	 * @return userListWS
	 * @throws ESBWebserviceFaultESB
	 */
	public eai.msejdf.webServices.User getUserWS(Long userId) throws ESBWebserviceFaultESB {
		
		eai.msejdf.webServices.User userWS = portESB.getUserEmailCount(userId);

		return userWS;

	}

	public List<Company> getCompanyList() {
		if (FacesContext.getCurrentInstance().getRenderResponse()) {
			// Reload to get most recent data.
			this.companyList = this.adminBean.getCompanyList("%", CompanySort.NAME_ASC);
		}
		return this.companyList;

	}

	public void setCompanyList(List<Company> companyList) {
		this.companyList = companyList;
	}

}
