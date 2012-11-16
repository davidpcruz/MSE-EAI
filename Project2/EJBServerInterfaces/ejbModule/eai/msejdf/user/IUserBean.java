package eai.msejdf.user;

import java.util.List;

import javax.ejb.Remote;

import eai.msejdf.exception.ConfigurationException;
import eai.msejdf.persistence.BankTeller;
import eai.msejdf.persistence.Company;
import eai.msejdf.persistence.User;

@Remote
public interface IUserBean {
	public final static int SORT_BY_NAME = 0;

	public void updateUser(User user) throws ConfigurationException;

	public User getUser(String name) throws ConfigurationException;

	public User getUser(Long userId) throws ConfigurationException;

	public Company getCompany(Long companyId) throws ConfigurationException;

	public List<Company> getCompanyList(String filterPattern);

	public void followCompany(Long userId, Long companyId) throws ConfigurationException;

	public void unfollowCompany(Long userId, Long companyId) throws ConfigurationException;

	public List<Company> getfollowedCompanyList(Long userId) throws ConfigurationException;

	public void setBankTeller(Long userId, Long companyId) throws ConfigurationException;

	public void setBankTeller(Long userId, BankTeller bankTeller) throws ConfigurationException;

	public List<BankTeller> getBankTellerList(String filterPattern);

	public BankTeller getBankTeller(Long userId) throws ConfigurationException;
	
}
