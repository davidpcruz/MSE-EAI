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
import eai.msejdf.webServices.*;

@ManagedBean(name="adminW")
@ViewScoped
public class AdminBackOfficeBean
{
	private IAdmin adminBean;

	private List<Company> companyList;
	private List<User> userList;
	private List<User> subscribedUserList;
	private List<eai.msejdf.webServices.User> subscribedUserListWS;
	// the selected company Id to search
	private Long searchCompanyIdSelect;
	// the selected company Name to search
	private String searchCompanyNameSelect;
	// the age to search the users
	private Integer searchAge;

	WebServicesService serviceESB = new WebServicesService();
	ListUserInterface portESB = serviceESB.getListUserInterfacePort();

	/**
	 * Creates a AdminW bean to handle the list queries 
	 * @throws NamingException
	 * @throws ConfigurationException
	 */
	public AdminBackOfficeBean() throws NamingException, ConfigurationException {
		InitialContext ctx = new InitialContext();

		this.adminBean = (IAdmin) ctx.lookup(EJBLookupConstants.EJB_I_ADMIN);
	}

	public Long getSearchCompanyIdSelect()
	{
		return searchCompanyIdSelect;
	}

	public void setSearchCompanyIdSelect(Long searchCompanySelect)
	{
		this.searchCompanyIdSelect = searchCompanySelect;
	}

	public String getSearchCompanyNameSelect()
	{
		return searchCompanyNameSelect;
	}

	public void setSearchCompanyNameSelect(String searchCompanyNameSelect)
	{
		this.searchCompanyNameSelect = searchCompanyNameSelect;
	}
	/**
	 * searches for a users associated to the companies
	 * @return
	 * @throws SecurityException
	 */
	public boolean searchUsersCompanies() 
	{
		// basic validations
		if (this.getSearchCompanyIdSelect() == null)
		{
			return false;
		}
		
		this.subscribedUserList = this.adminBean.getUserFollowCompanyList(this.getSearchCompanyIdSelect(), UserSort.NAME_ASC);
		
		return true;
	}
	
	
	/**
	 * searches for a users associated to the companies using a WS -> ESB -> WS 
	 * @return
	 * @throws SecurityException
	 */
	public boolean searchUsersCompaniesWS() 
	{
		// basic validations
		if (this.getSearchCompanyNameSelect() == null)
		{
			return false;
		}
		
		this.subscribedUserListWS = getUserFollowCompanyListUsingWS(this.getSearchCompanyNameSelect(), UserSort.NAME_ASC);
		
		return true;
	}
	
	
	private List<eai.msejdf.webServices.User> getUserFollowCompanyListUsingWS(String searchCompanyNameSelect, UserSort nameAsc) {
		List<eai.msejdf.webServices.User> userList = portESB.getUsersFollowingCompany(searchCompanyNameSelect);
		for (eai.msejdf.webServices.User user : userList) {
			System.out.println("Server said: " + user.getName() + " " + user.getMailAddress());
		}
		
		return userList;
	}

	/**
	 * Search users greater than age.
	 *
	 * @return true, if successful
	 */
	public boolean searchUsersByAge() 
	{
		// basic validations
		if (this.getSearchAge() == null)
		{
			return false;
		}
		
		this.subscribedUserList = this.adminBean.getUserList(this.getSearchAge(),  UserSort.BIRTHDAY_ASC);
		
		return true;
	}
	
	/**
	 * Returns all the companies order by name 
	 * @return
	 */
	public List<Company> getCompanyList()
	{
		if (FacesContext.getCurrentInstance().getRenderResponse()) {
			// Reload to get most recent data.
			this.companyList = this.adminBean.getCompanyList("%", CompanySort.NAME_ASC);
		}
		return this.companyList;
	}
	
	/**
	 * Returns all the users order by age 
	 *
	 * @return the user list
	 */
	public List<User> getUserList()
	{
		if (FacesContext.getCurrentInstance().getRenderResponse()) {
			// Reload to get most recent data.
			this.userList = this.adminBean.getUserList(UserSort.BIRTHDAY_ASC);
		}		
		return userList;
	}

	/**
	 * Gets the subscribed user list.
	 *
	 * @return the subscribed user list
	 */
	public List<User> getSubscribedUserList()
	{
		return subscribedUserList;
	}

	/**
	 * Gets the subscribed user list on the WebService.
	 *
	 * @return the subscribed user list
	 */
	public List<eai.msejdf.webServices.User> getSubscribedUserListWS()
	{
		return subscribedUserListWS;
	}
	
	/**
	 * Gets the search age.
	 *
	 * @return the searchAge
	 */
	public Integer getSearchAge()
	{
		return searchAge;
	}

	/**
	 * Sets the search age.
	 *
	 * @param searchAge the searchAge to set
	 */
	public void setSearchAge(Integer searchAge)
	{
		this.searchAge = searchAge;
	}


}
