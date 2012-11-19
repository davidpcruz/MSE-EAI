package eai.msejdf.pagedata;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import eai.msejdf.exception.ConfigurationException;
import eai.msejdf.persistence.Company;
import eai.msejdf.user.IUserBean;
import eai.msejdf.utils.EJBLookupConstants;
import eai.msejdf.web.session.SessionManager;

public class CompanyPageData {
	private final static String SUBSCRIBE_ACTION_NAME = "Subscribe";
	private final static String UNSUBSCRIBE_ACTION_NAME = "Unsubscribe";

	private IUserBean userBean;
	private Company company;
	private List<Company> followedCompanyList;
	private List<Company> companyList;
	private Long userId;
	private String filterPattern;

	public CompanyPageData() throws NamingException, ConfigurationException {
		InitialContext ctx = new InitialContext();

		this.userBean = (IUserBean) ctx.lookup(EJBLookupConstants.EJB_I_USER);

		this.userId = this.userBean.getUser(SessionManager.getProperty(SessionManager.USERNAME_PROPERTY)).getId();
	}

	/**
	 * Gets the followed company list.
	 *
	 * @return the followed company list
	 * @throws ConfigurationException the configuration exception
	 */
	public List<Company> getFollowedCompanyList() throws ConfigurationException {
		if (FacesContext.getCurrentInstance().getRenderResponse()) {
			// Reload to get most recent data.
			this.followedCompanyList = this.userBean.getfollowedCompanyList(this.userId);
		}
		return this.followedCompanyList;
	}

	/**
	 * Gets the company list.
	 *
	 * @return the company list
	 * @throws ConfigurationException the configuration exception
	 */
	public List<Company> getCompanyList() throws ConfigurationException {
		if (FacesContext.getCurrentInstance().getRenderResponse()) {
			// Reload to get most recent data.			
			this.companyList = this.userBean.getCompanyList(this.getFilterPattern());
			// Get also the followed list, as we need to know the subscription
			// status
			this.followedCompanyList = this.userBean.getfollowedCompanyList(this.userId);
		}
		return this.companyList;
	}

	/**
	 * Gets the company.
	 *
	 * @param companyId the company id
	 * @return the company
	 * @throws ConfigurationException the configuration exception
	 */
	public Company getCompany(Long companyId) throws ConfigurationException {
		// Reload to get most recent data.
		if (FacesContext.getCurrentInstance().getRenderResponse()) {
			this.company = this.userBean.getCompany(companyId);

			// Get also the followed list, as we need to know the subscription
			// status
			this.followedCompanyList = this.userBean.getfollowedCompanyList(this.userId);
		}
		return this.company;
	}

	
	/**
	 * Gets the subscription change action.
	 *
	 * @param company the company
	 * @return the subscription change action
	 */
	public String getSubscriptionChangeAction(Company company) {
		// If the followedCompanyList has data, it was already loaded
		if ((null == this.followedCompanyList)
				|| (false == this.listContainsCompanyById(this.followedCompanyList, company))) {
			return CompanyPageData.SUBSCRIBE_ACTION_NAME;
		}
		return CompanyPageData.UNSUBSCRIBE_ACTION_NAME;
	}

	/**
	 * Subscription change action.
	 *
	 * @param company the company
	 * @return true, if successful
	 */
	public boolean subscriptionChangeAction(Company company) {
		try {
			// If the followedCompanyList has data, it was already loaded
			if ((null == this.followedCompanyList)
					|| (false == this.listContainsCompanyById(this.followedCompanyList, company))) {
				this.userBean.followCompany(this.userId, company.getId());
			} else {
				this.userBean.unfollowCompany(this.userId, company.getId());
			}
			return true;
		} catch (ConfigurationException exception) {
			return false;
		}
	}

	/**
	 * Gets the filter pattern.
	 *
	 * @return the filter pattern
	 */
	public String getFilterPattern() {
		return filterPattern;
	}

	/**
	 * Sets the filter pattern.
	 *
	 * @param filterPattern the new filter pattern
	 */
	public void setFilterPattern(String filterPattern) {
		this.filterPattern = filterPattern;
	}

	/**
	 * List contains company by id.
	 *
	 * @param list the list
	 * @param company the company
	 * @return true, if successful
	 */
	private boolean listContainsCompanyById(List<Company> list, Company company) {
		Long id = company.getId();

		for (Company comp : list) {
			if (comp.getId().equals(id)) {
				return true;
			}
		}
		return false;
	}
}
