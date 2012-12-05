package eai.msejdf.webServices;

import java.util.ArrayList;
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
	 * WebMethod Gets the user numbers of sent emails
	 * 
	 * @param userId
	 * @return
	 * @throws ConfigurationException
	 */
	Integer getUserEmailCount(Long userId) throws ConfigurationException;

	/**
	 * WebMethod Increments the number of e-mails sent by each user in the list by one
	 * 
	 * @param userId Id of user for which the e-mail count shall be incremented
	 * @throws ConfigurationException
	 */
	void incrementUserEmailCountFromId(Long userId)
			throws ConfigurationException;
	
	/**
	 * WebMethod Increments the number of e-mails sent by each user in the list by one
	 * 
	 * @param userList List of users for which the e-mail count shall be incremented
	 * @throws ConfigurationException
	 */
	void incrementUserEmailCountFromList(List<Long> userList)
			throws ConfigurationException;	
	
	/**
	 * WebMethod Gets the user list that follow company id=companyId sorted by user sort type.
	 * 
	 * @param companyId
	 * @return
	 */
	List<eai.msejdf.esb.User> getUsersFollowingCompany(String companyName);



}
