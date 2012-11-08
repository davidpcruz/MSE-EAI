package eai.msejdf.user;

import java.util.List;

import eai.msejdf.exception.ConfigurationException;
import eai.msejdf.persistence.BankTeller;
import eai.msejdf.persistence.Company;
import eai.msejdf.persistence.User;

import javax.ejb.Remote;

@Remote
public interface  IUserBean {
	public final static int SORT_BY_NAME = 0;	
	
	//public void addUser(User user);  // TODO: Move to ISecurity ?! 
	//public void removeUser(User user);	// TODO: Move to ISecurity ?!
	public void updateUser(User user) throws ConfigurationException;
	public User getUser(String name) throws ConfigurationException;
	
	public Company getCompany(String name) throws ConfigurationException;
	public List<Company> getCompanyList(String filterPattern);	
	public List<String> getCompanyNameList(String filterPattern);	
	
	public void followCompany(String userName, String companyName) throws ConfigurationException;
	public void unfollowCompany(String userName, String companyName) throws ConfigurationException;
	public List <Company> getfollowedCompanyList(String userName) throws ConfigurationException;
	
	public void setBankTeller(String userName, String tellerName);
	public void setBankTeller(String userName, BankTeller bankTeller);

	public BankTeller getBankTeller(String name);
	public List<BankTeller> getBankTellerList(String filterPattern);
	public List<String> getBankTellerNameList(String filterPattern);
	public List<Company> getfollowedCompanyList2(String userName)
			throws ConfigurationException;
	
	public User getUser(Long id) throws ConfigurationException;
	
}
