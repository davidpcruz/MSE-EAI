package eai.msejdf.config;

import javax.ejb.Remote;

import eai.msejdf.exception.ConfigurationException;
import eai.msejdf.persistence.User;

/**
 * Interface class for user related configuration operations
 */
@Remote
public interface IUserConfig {

	/**
	 * Set username for which configuration operations will be performed 
	 * @param username The user identifier
	 */
	public void configureUser(String username);
	
	/** Returns information related with a specific user
	 * @return User information object
	 */
	public User getUserInfo() throws ConfigurationException;
	
	/**
	 * Set's the user information to be stored
	 * @param userInfo User information to store (upon save call)
	 */
	public void setUserInfo(User userInfo) throws ConfigurationException;
	
	/**
	 * Saves information currently associated with the user
	 */
	public void saveConfig();
}
