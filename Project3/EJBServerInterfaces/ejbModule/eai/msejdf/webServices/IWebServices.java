package eai.msejdf.webServices;

import java.util.List;

import javax.ejb.Remote;

import eai.msejdf.exception.ConfigurationException;
import eai.msejdf.persistence.User;
import eai.msejdf.sort.UserSort;

/**
 * Interface class for webServices related operations
 */
/**
 * @author joaofcr
 * 
 */
@Remote
public interface IWebServices {

	/**
	 * WebMethod Gets the user list sorted by user sort type.
	 * 
	 * @param sortType
	 *            the sort type
	 * @return the user list
	 */
	public List<eai.msejdf.esb.User> getUserListAll();

	/**
	 * WebMethod Gets the user numbers of sent emails
	 * 
	 * @param userId
	 * @return
	 * @throws ConfigurationException
	 */
	Integer getUserEmailCount(Long userId) throws ConfigurationException;

	/**
	 * WebMethod Sets the user numbers of sent emails
	 * 
	 * @param userId
	 * @return
	 * @throws ConfigurationException
	 */
	void setUserEmailCount(Long userId, Integer emailCount)
			throws ConfigurationException;

	/**
	 * Gets the user by user id
	 * @param userId
	 * @return
	 * @throws ConfigurationException
	 */
	User getUser(Long userId) throws ConfigurationException;

	/**
	 * Gets the user list sorted by user sort type.
	 * 
	 * @param ageThreshold
	 * @param sortType
	 * @return
	 */
	List<eai.msejdf.esb.User> getUserList(Integer ageThreshold, UserSort sortType);

	List<eai.msejdf.esb.User> getUsersFollowingCompany(Long companyId, UserSort sortType);
	/**
	 * WebMethod Gets the user list that follow company id=companyId sorted by user sort type.
	 * 
	 * @param companyId
	 * @return
	 */
	List<eai.msejdf.esb.User> getUsersFollowingCompany(Long companyId);

}
