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
import eai.msejdf.web.SessionManager;

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

	/**
	 * @return
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * @return BankTeller
	 */
	public BankTeller getBankTeller() {
		return bankTeller;
	}

	/**
	 * Sets the user bank teller
	 * @param bankTeller
	 */
	public void setBankTeller(BankTeller bankTeller) {
		this.user.setBankTeller(bankTeller);
	}

	/**
	 * Sets the temporary bankTeller as the current user's bank teller
	 * 
	 * @return
	 */
	public boolean addBankTeller() {

		try {
			this.userBean.setBankTeller(this.user.getId(), this.bankTeller);
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * Gets the List of Bank Tellers in DB
	 * 
	 * @return List<BankTeller>
	 * @throws ConfigurationException
	 */
	public List<BankTeller> getBankTellerList() throws ConfigurationException {
		if (FacesContext.getCurrentInstance().getRenderResponse()) {
			// Reload to get most recent data.
			this.bankTellerList = this.userBean.getBankTellerList("%");
		}
		return this.bankTellerList;
	}

	/**
	 * Checks if bankTeller is he current user Bank Teller Return
	 * SET_ACTION_NAME if the bankteller is not the current user's bank teller
	 * 
	 * @param bankTeller
	 * @return String
	 */
	public String getSubscriptionChangeAction(BankTeller bankTeller) {

		try {
			return (!(user.getBankTeller().getId().equals(bankTeller.getId())) ? BankTellerPageData.SET_ACTION_NAME
					: BankTellerPageData.UNSET_ACTION_NAME);
		} catch (Exception exception) {
			return BankTellerPageData.SET_ACTION_NAME;
		}
	}

	/**
	 * Sets the bankTeller as the user's Bank Teller returns true if the
	 * operation succeeds
	 * 
	 * @param bankTeller
	 * @return boolean
	 */
	public boolean subscriptionChangeAction(BankTeller bankTeller) {
		boolean result = false;

		try {
			this.userBean.setBankTeller(this.user.getId(), bankTeller.getId());
			result = true;
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}

		return result;
	}

}
