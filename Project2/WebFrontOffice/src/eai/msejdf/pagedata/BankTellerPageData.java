package eai.msejdf.pagedata;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import eai.msejdf.exception.ConfigurationException;
import eai.msejdf.persistence.Address;
import eai.msejdf.persistence.BankTeller;
import eai.msejdf.persistence.User;
import eai.msejdf.user.IUserBean;
import eai.msejdf.utils.EJBLookupConstants;
import eai.msejdf.web.session.SessionManager;

public class BankTellerPageData {
	private final static String SET_ACTION_NAME = "Set";
	private final static String UNSET_ACTION_NAME = "Actual";

	private IUserBean userBean;
	private BankTeller bankTeller;
	private Address address;
	protected User user;
	List<BankTeller> bankTellerList;

	public BankTellerPageData() throws NamingException, ConfigurationException {
		InitialContext ctx = new InitialContext();

		this.userBean = (IUserBean) ctx.lookup(EJBLookupConstants.EJB_I_USER);
		this.bankTeller = new BankTeller();
		this.user = this.userBean.getUser(SessionManager.getProperty(SessionManager.USERNAME_PROPERTY));

		this.address = this.bankTeller.getAddress();
		this.address = new Address();
		this.bankTeller.setAddress(address);

	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public BankTeller getBankTeller() {
		return bankTeller;
	}

	public void setBankTeller(BankTeller bankTeller) {
		this.user.setBankTeller(bankTeller);
	}

	public boolean updateBankTeller() {
		// TODO: The exception should cause the user interface to be informed
		try {
			this.userBean.setBankTeller(this.user.getId(), this.user.getBankTeller());
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean addBankTeller() {
		// TODO: The exception should cause the user interface to be informed
		try {
			this.userBean.setBankTeller(this.user.getId(), this.bankTeller);
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}

		return true;
	}

	public List<BankTeller> getBankTellerList() throws ConfigurationException {
		if (FacesContext.getCurrentInstance().getRenderResponse()) {
			// Reload to get most recent data.
			this.bankTellerList = this.userBean.getBankTellerList("%");
		}
		return this.bankTellerList;
	}

	public String getSubscriptionChangeAction(BankTeller bankTeller) {
		// TODO: This is tmp for debug. Replace by a method that checks the
		// subscription
		try {
			return (!(user.getBankTeller().getId().equals(bankTeller.getId())) ? BankTellerPageData.SET_ACTION_NAME
					: BankTellerPageData.UNSET_ACTION_NAME);
		} catch (Exception exception) {
			return BankTellerPageData.SET_ACTION_NAME;
		}
	}

	public boolean subscriptionChangeAction(BankTeller bankTeller) {
		boolean result = false;

		// if (!user.getBankTeller().getId().equals(bankTeller.getId())) {

		try {
			this.userBean.setBankTeller(this.user.getId(), bankTeller.getId());
			result = true;
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO: Return code must match action result
		return result;
	}

}
