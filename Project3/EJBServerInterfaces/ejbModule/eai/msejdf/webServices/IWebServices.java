package eai.msejdf.webServices;

import java.util.List;

import javax.ejb.Remote;

import eai.msejdf.exception.ConfigurationException;
import eai.msejdf.persistence.User;
import eai.msejdf.sort.UserSort;

/**
 * Interface class for webServices related operations
 */
@Remote
public interface IWebServices {

	/**
	 * Gets the user list sorted by user sort type.
	 * 
	 * @param sortType
	 *            the sort type
	 * @return the user list
	 */
	public List<User> getUserListAll();

	List<User> getUserList(Integer ageThreshold, UserSort sortType);

	Integer getUserEmailCount(Long userId) throws ConfigurationException;

	void setUserEmailCount(Long userId, Integer emailCount) throws ConfigurationException;

	User getUser(Long userId) throws ConfigurationException;

	

}
