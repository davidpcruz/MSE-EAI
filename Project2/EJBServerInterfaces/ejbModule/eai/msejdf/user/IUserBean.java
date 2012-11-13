package eai.msejdf.user;

import java.util.List;

import eai.msejdf.exception.ConfigurationException;
import eai.msejdf.persistence.Address;
import eai.msejdf.persistence.BankTeller;
import eai.msejdf.persistence.Company;
import eai.msejdf.persistence.User;

import javax.ejb.Remote;

@Remote
public interface IUserBean {
	public final static int SORT_BY_NAME = 0;

	// public void addUser(User user); // TODO: Move to ISecurity ?!
	// public void removeUser(User user); // TODO: Move to ISecurity ?!
	public void updateUser(User user) throws ConfigurationException;

	public User getUser(String name) throws ConfigurationException;

	public User getUser(Long userId) throws ConfigurationException;

	public Company getCompany(Long userId) throws ConfigurationException;

	public List<Company> getCompanyList(String filterPattern);

	public List<String> getCompanyNameList(String filterPattern);

	public void followCompany(Long userId, Long companyId)
			throws ConfigurationException;

	public void unfollowCompany(Long userId, Long companyId)
			throws ConfigurationException;

	public List<Company> getfollowedCompanyList(Long id)
			throws ConfigurationException;

	public void setBankTeller(Long userId, Long companyId)
			throws ConfigurationException;

	public void setBankTeller(Long userId, BankTeller bankTeller)
			throws ConfigurationException;

	public BankTeller getBankTeller(Long userId) throws ConfigurationException;

	public List<BankTeller> getBankTellerList(String filterPattern);

	public List<String> getBankTellerNameList(String filterPattern);
	public BankTeller getUserBankTeller(User user) throws ConfigurationException;

	void setBankTellerAddress(BankTeller bankTellerId);


}
