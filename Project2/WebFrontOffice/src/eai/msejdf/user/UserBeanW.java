package eai.msejdf.user;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import eai.msejdf.session.SessionManager;
import eai.msejdf.exception.ConfigurationException;
import eai.msejdf.persistence.Address;
import eai.msejdf.persistence.BankTeller;
import eai.msejdf.persistence.Company;
import eai.msejdf.persistence.User;

@ManagedBean
@ViewScoped
public class UserBeanW {

	private final static String SUBSCRIBE_ACTION_NAME = "Subscribe";
	private final static String UNSUBSCRIBE_ACTION_NAME = "Unsubscribe";
	private final static String SET_ACTION_NAME = "Set";
	private final static String UNSET_ACTION_NAME = "Actual";

	private IUserBean bean;
	private User user;
	private BankTeller bankTeller;
	private Address address;
	private String birthDate;
	List<Company> followedCompanyList;
	List<Company> companyList;
	List<BankTeller> bankTellerList;

	public UserBeanW() throws NamingException, ConfigurationException {
		InitialContext ctx = new InitialContext();

		// TODO: Remove hardcoded
		this.bean = (IUserBean) ctx
				.lookup("ejb:P2EARDeploy/EJBServer/UserBean!eai.msejdf.user.IUserBean");

		// TODO
		this.user = bean.getUser(SessionManager
				.getProperty(SessionManager.USERNAME_PROPERTY));
		this.bankTeller = new BankTeller();
		this.address = new Address();
		this.bankTeller.setAddress(address);
	}

	public IUserBean bean() {
		return this.bean;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BankTeller getBankTeller() {
		return bankTeller;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public BankTeller getBankTeller(User user) {
		BankTeller bankTeller;

		try {
			bankTeller = this.bean.getUserBankTeller(user);
		} catch (ConfigurationException e) {
			bankTeller = new BankTeller();
			e.printStackTrace();
		}

		return bankTeller;
	}

	public void setBankTeller(BankTeller bankTeller) {
		this.user.setBankTeller(bankTeller);
	}

	public boolean updateUser() {
		// TODO: The exception should cause the user interface to be informed
		try {
			this.bean.updateUser(this.user);
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean updateBankTeller() {
		// TODO: The exception should cause the user interface to be informed
		try {
			this.bean.setBankTeller(this.user.getId(),
					this.user.getBankTeller());
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean addBankTeller() {
		// TODO: The exception should cause the user interface to be informed
		try {
			this.bean.setBankTeller(this.user.getId(), this.bankTeller);
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}

		return true;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
		// this.user.setBirthDate(new Date(birthDate));
	}

	public List<Company> getFollowedCompanyList() throws ConfigurationException {
		if (FacesContext.getCurrentInstance().getRenderResponse()) {
			this.followedCompanyList = this.bean.getfollowedCompanyList(user
					.getId()); // Reload to get most recent data.
		}
		return this.followedCompanyList;
	}

	public List<Company> getCompanyList() throws ConfigurationException {
		if (FacesContext.getCurrentInstance().getRenderResponse()) {
			// Reload to get most recent data.
			this.companyList = this.bean.getCompanyList("%");
			// Get also the followed list, as we need to know the subscription status
			this.followedCompanyList = this.bean.getfollowedCompanyList(user.getId()); 
		}
		return this.companyList;
	}

	public List<BankTeller> getBankTellerList() throws ConfigurationException {
		if (FacesContext.getCurrentInstance().getRenderResponse()) {
			this.bankTellerList = this.bean.getBankTellerList("%"); // Reload to get most recent data.
		}
		return this.bankTellerList;
	}

	public String getSubscriptionChangeAction(Company company) {
		// If the followedCompanyList has data, it was already loaded
		if ((null == this.followedCompanyList)
				|| (false == this.listContainsCompanyById(this.followedCompanyList, company))) {
			return UserBeanW.SUBSCRIBE_ACTION_NAME;
		}		
		return UserBeanW.UNSUBSCRIBE_ACTION_NAME;
	}

	public boolean subscriptionChangeAction(Company company) {
		try {
			// If the followedCompanyList has data, it was already loaded
			if ((null == this.followedCompanyList)
					|| (false == this.listContainsCompanyById(this.followedCompanyList, company))) {
				this.bean.followCompany(this.user.getId(), company.getId());
			} else {
				this.bean.unfollowCompany(this.user.getId(), company.getId());
			}
			return true;
		} catch (ConfigurationException exception) {
			return false;
		}
	}

	public String getSubscriptionChangeActionBankTeller(BankTeller bankTeller) {
		// TODO: This is tmp for debug. Replace by a method that checks the
		// subscription

		return (!(user.getBankTeller().getId().equals(bankTeller.getId())) ? UserBeanW.SET_ACTION_NAME
				: UserBeanW.UNSET_ACTION_NAME);
	}

	public boolean subscriptionChangeActionBankTeller(BankTeller bankTeller) {
		boolean result = false;

		// if (!user.getBankTeller().getId().equals(bankTeller.getId())) {

		try {
			this.bean.setBankTeller(this.user.getId(), bankTeller.getId());
			result = true;
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO: Return code must match action result
		return result;
	}

	private boolean listContainsCompanyById(List<Company> list, Company company)
	{
		Long id = company.getId();
		
		for (Company comp : list)
		{
			if (comp.getId().equals(id))
			{
				return true;
			}
		}
		return false;
	}
	
}
