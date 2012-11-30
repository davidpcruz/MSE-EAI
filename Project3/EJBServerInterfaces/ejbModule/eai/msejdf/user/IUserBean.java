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

	/**
	 * Update user.
	 *
	 * @param user the user
	 * @throws ConfigurationException the configuration exception
	 */
	public void updateUser(User user) throws ConfigurationException;

	/**
	 * Gets the user by username.
	 *
	 * @param name the name
	 * @return the user
	 * @throws ConfigurationException the configuration exception
	 */
	public User getUser(String name) throws ConfigurationException;

	/**
	 * Gets the user by user id.
	 *
	 * @param userId the user id
	 * @return the user
	 * @throws ConfigurationException the configuration exception
	 */
	public User getUser(Long userId) throws ConfigurationException;

	/**
	 * Gets the company.
	 *
	 * @param companyId the company id
	 * @return the company
	 * @throws ConfigurationException the configuration exception
	 */
	public Company getCompany(Long companyId) throws ConfigurationException;

	/**
	 * Gets the company list.
	 *
	 * @param filterPattern the filter pattern
	 * @return the company list
	 */
	public List<Company> getCompanyList(String filterPattern);

	/**
	 * Follow company.
	 *
	 * @param userId the user id
	 * @param companyId the company id
	 * @throws ConfigurationException the configuration exception
	 */
	public void followCompany(Long userId, Long companyId) throws ConfigurationException;

	/**
	 * Unfollow company.
	 *
	 * @param userId the user id
	 * @param companyId the company id
	 * @throws ConfigurationException the configuration exception
	 */
	public void unfollowCompany(Long userId, Long companyId) throws ConfigurationException;

	/**
	 * Gets the followed company list.
	 *
	 * @param userId the user id
	 * @return the followed company list
	 * @throws ConfigurationException the configuration exception
	 */
	public List<Company> getfollowedCompanyList(Long userId) throws ConfigurationException;

	/**
	 * Sets the bank teller.
	 *
	 * @param userId the user id
	 * @param companyId the company id
	 * @throws ConfigurationException the configuration exception
	 */
	public void setBankTeller(Long userId, Long companyId) throws ConfigurationException;

	/**
	 * Sets the bank teller.
	 *
	 * @param userId the user id
	 * @param bankTeller the bank teller
	 * @throws ConfigurationException the configuration exception
	 */
	public void setBankTeller(Long userId, BankTeller bankTeller) throws ConfigurationException;

	/**
	 * Gets the bank teller list.
	 *
	 * @param filterPattern the filter pattern
	 * @return the bank teller list
	 */
	public List<BankTeller> getBankTellerList(String filterPattern);

	/**
	 * Gets the bank teller.
	 *
	 * @param userId the user id
	 * @return the bank teller
	 * @throws ConfigurationException the configuration exception
	 */
	public BankTeller getBankTeller(Long userId) throws ConfigurationException;
	
}
