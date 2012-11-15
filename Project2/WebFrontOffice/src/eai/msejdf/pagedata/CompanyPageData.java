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

	public CompanyPageData() throws NamingException, ConfigurationException {
		InitialContext ctx = new InitialContext();

		this.userBean = (IUserBean) ctx.lookup(EJBLookupConstants.EJB_I_USER);

		this.userId = this.userBean.getUser(
				SessionManager.getProperty(SessionManager.USERNAME_PROPERTY))
				.getId();
	}

	public List<Company> getFollowedCompanyList() throws ConfigurationException {
		if (FacesContext.getCurrentInstance().getRenderResponse()) {
			// Reload to get most recent data.
			this.followedCompanyList = this.userBean
					.getfollowedCompanyList(this.userId);
		}
		return this.followedCompanyList;
	}

	public List<Company> getCompanyList() throws ConfigurationException {
		if (FacesContext.getCurrentInstance().getRenderResponse()) {
			// Reload to get most recent data.
			this.companyList = this.userBean.getCompanyList("%");
			// Get also the followed list, as we need to know the subscription
			// status
			this.followedCompanyList = this.userBean
					.getfollowedCompanyList(this.userId);
		}
		return this.companyList;
	}

	public Company getCompany(Long companyId) throws ConfigurationException {
		// Reload to get most recent data.
		if (FacesContext.getCurrentInstance().getRenderResponse()) {
			this.company = this.userBean.getCompany(companyId);

			// Get also the followed list, as we need to know the subscription
			// status
			this.followedCompanyList = this.userBean
					.getfollowedCompanyList(this.userId);
		}
		return this.company;
	}

	public String getSubscriptionChangeAction(Company company) {
		// If the followedCompanyList has data, it was already loaded
		if ((null == this.followedCompanyList) || 
			(false == this.listContainsCompanyById(this.followedCompanyList, company))) {
			return CompanyPageData.SUBSCRIBE_ACTION_NAME;
		}
		return CompanyPageData.UNSUBSCRIBE_ACTION_NAME;
	}

	public boolean subscriptionChangeAction(Company company) {
		try {
			// If the followedCompanyList has data, it was already loaded
			if ((null == this.followedCompanyList) || 
				(false == this.listContainsCompanyById(this.followedCompanyList, company))) {
				this.userBean.followCompany(this.userId, company.getId());
			} else {
				this.userBean.unfollowCompany(this.userId, company.getId());
			}
			return true;
		} catch (ConfigurationException exception) {
			return false;
		}
	}

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
