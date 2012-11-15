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

@ManagedBean(name="adminW")
@ViewScoped
public class AdminBackOfficeBean
{
	private IAdmin adminBean;

	private List<Company> companyList;
	private List<User> userList;
	private List<User> subscribedUserList;
	
	// the selected company to search
	private Long searchCompanySelect;
	// the age to search the users
	private Integer searchAge;

	/**
	 * Creates a AdminW bean to handle the list queries 
	 * @throws NamingException
	 * @throws ConfigurationException
	 */
	public AdminBackOfficeBean() throws NamingException, ConfigurationException {
		InitialContext ctx = new InitialContext();

		this.adminBean = (IAdmin) ctx.lookup(EJBLookupConstants.EJB_I_ADMIN);
	}

	public Long getSearchCompanySelect()
	{
		return searchCompanySelect;
	}

	public void setSearchCompanySelect(Long searchCompanySelect)
	{
		this.searchCompanySelect = searchCompanySelect;
	}

	
	/**
	 * searches for a users associated to the companies
	 * @return
	 * @throws SecurityException
	 */
	public boolean searchUsersCompanies() 
	{
		// basic validations
		if (this.getSearchCompanySelect() == null)
		{
			return false;
		}
		
		this.subscribedUserList = this.adminBean.getUserFollowCompanyList(this.getSearchCompanySelect(), UserSort.NAME_ASC);
		
		return true;
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

	public List<User> getSubscribedUserList()
	{
		return subscribedUserList;
	}

	/**
	 * @return the searchAge
	 */
	public Integer getSearchAge()
	{
		return searchAge;
	}

	/**
	 * @param searchAge the searchAge to set
	 */
	public void setSearchAge(Integer searchAge)
	{
		this.searchAge = searchAge;
	}


}
