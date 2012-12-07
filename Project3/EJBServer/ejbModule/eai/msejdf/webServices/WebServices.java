package eai.msejdf.webServices;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import eai.msejdf.admin.Admin;
import eai.msejdf.exception.ConfigurationException;
import eai.msejdf.persistence.BankTeller;
import eai.msejdf.persistence.User;
import eai.msejdf.sort.UserSort;
import eai.msejdf.utils.SOAMessageConstants;

/**
 * Bean implementing interface for webServices calls related calls
 */
@WebService(name = "ListUserInterface", targetNamespace = "http://www.eai.org/msejdf")
@Stateless(name = "WebServices")
@LocalBean
public class WebServices implements IWebServices {
	private static final String EXCEPTION_USER_NOT_FOUND = "The user was not found.";

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Admin.class);
	@PersistenceContext(unitName = "JPAEAI")
	private EntityManager entityManager;



	/**
	 * WebMethod Gets the user list that follow company name=companyName sorted by
	 * user sort type.
	 * 
	 * @param companyId
	 * @return
	 */
	@Override
	@WebMethod
	public @WebResult(name = "user") List<eai.msejdf.esb.User> getUsersFollowingCompany(
			@WebParam(name = "companyName") String companyName) {
		return this.getUsersFollowingCompany(companyName, UserSort.NAME_ASC);
		// return this.getUserList(null, UserSort.NAME_ASC);
	}

	/**
	 * WebMethod Gets the user numbers of sent emails
	 * 
	 * @param userId
	 * @return
	 * @throws ConfigurationException
	 */
	@Override
	@WebMethod
	public @WebResult(name = "user") eai.msejdf.esb.User getUserEmailCount(@WebParam(name = SOAMessageConstants.ESB_USER_ID) Long userId)
			throws ConfigurationException {
		if (logger.isDebugEnabled()) {
			logger.debug("getUser(String) - start"); //$NON-NLS-1$
		}

		if (null == userId) {
			throw new InvalidParameterException();
		}

		Query query = entityManager
				.createQuery("SELECT User FROM User user WHERE user.id=:id");
		query.setParameter("id", userId);

		@SuppressWarnings("unchecked")
		List<User> userList = query.getResultList();
		if (true == userList.isEmpty()) {
			// The user doesn't seem to exist
			throw new ConfigurationException(
					WebServices.EXCEPTION_USER_NOT_FOUND);
		}

		
		if (logger.isDebugEnabled()) {
			logger.debug("getUser(String) - end"); //$NON-NLS-1$
		}
		return mapToEsbUser(userList.get(0));
	}

	/**
	 * WebMethod Sets the user numbers of sent emails by one
	 * 
	 * @param userId
	 * @return
	 * @throws ConfigurationException
	 */
	@Override
	@WebMethod
	public void incrementUserEmailCountFromId(@WebParam(name = SOAMessageConstants.ESB_USER_ID) Long userId)
			throws ConfigurationException {
		if (logger.isDebugEnabled()) {
			logger.debug("incrementUserEmailCountFromId(List<eai.msejdf.esb.User>) - start"); //$NON-NLS-1$
		}

		if ((null == userId)) {
			throw new InvalidParameterException();
		}

		User user = getUser(userId);

		// increments user EmailCount by one
		user.setEmailCount(user.getEmailCount() + 1);

		entityManager.persist(user);

		if (logger.isDebugEnabled()) {
			logger.debug("incrementUserEmailCountFromId(List<eai.msejdf.esb.User>) - end"); //$NON-NLS-1$
		}
	}


	/** (non-Javadoc)
	 * Increments the user number of email count
	 * @see eai.msejdf.webServices.IWebServices#incrementUserEmailCountFromList(java.util.List)
	 */

	@Override
	@WebMethod
	public void incrementUserEmailCountFromList(@WebParam(name = SOAMessageConstants.ESB_USER_ID) List<Long> userList)
			throws ConfigurationException {
		if (logger.isDebugEnabled()) {
			logger.debug("incrementUserEmailCountFromList(List<eai.msejdf.esb.User>) - start"); //$NON-NLS-1$
		}
		System.out.println("###########################################################" );
		System.out.println("UserList: " + userList );
		System.out.println("###########################################################" );
		
		if ((null == userList)) {
			throw new InvalidParameterException();
		}

		for (Long userId : userList)
		{
			User dbUser = getUser(userId);
			// increments user EmailCount by one
			dbUser.setEmailCount(dbUser.getEmailCount() + 1);
	
			entityManager.persist(dbUser);
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("incrementUserEmailCountFromList(List<eai.msejdf.esb.User>) - end"); //$NON-NLS-1$
		}
	}
	

	/**
	 * Gets the user by user id
	 * 
	 * @param userId
	 * @return
	 * @throws ConfigurationException
	 */
	private User getUser(Long userId) throws ConfigurationException {
		if (logger.isDebugEnabled()) {
			logger.debug("getUser(Long) - start"); //$NON-NLS-1$
		}

		if (null == userId) {
			throw new InvalidParameterException();
		}

		Query query = entityManager
				.createQuery("SELECT User FROM User user WHERE user.id=:id");
		query.setParameter("id", userId);

		@SuppressWarnings("unchecked")
		List<User> userList = query.getResultList();
		if (true == userList.isEmpty()) {
			// The user doesn't seem to exist
			throw new ConfigurationException(
					WebServices.EXCEPTION_USER_NOT_FOUND);
		}

		User user = userList.get(0);
		BankTeller bankTeller = user.getBankTeller();

		if (null != bankTeller) {
			bankTeller.getId(); // To overcome Lazzy parameter
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getUser(Long) - end"); //$NON-NLS-1$
		}
		return user;
	}

	/**
	 * Builds the user sort type.
	 * 
	 * @param sortType
	 *            the sort type
	 * @return the string
	 */
	private String buildUserSortType(UserSort sortType) {
		String sortBy = "";
		switch (sortType) {
		case BIRTHDAY_ASC:
			sortBy = "ORDER BY user.birthDate ASC";
			break;
		case BIRTHDAY_DESC:
			sortBy = "ORDER BY user.birthDate DESC";
			break;
		case NAME_ASC:
			sortBy = "ORDER BY user.name ASC";
			break;
		case NAME_DESC:
			sortBy = "ORDER BY user.name DESC";
			break;
		default:
			sortBy = "";
			break;
		}
		return sortBy;
	}

	/**
	 * (non-Javadoc)
	 * get the users that have subscribed a company with name = companyName
	 * 
	 * @see eai.msejdf.admin.IAdmin#getUserFollowCompanyList(java.lang.Long,
	 * eai.msejdf.sort.UserSort)
	 */
	private List<eai.msejdf.esb.User> getUsersFollowingCompany(String companyName,
			UserSort sortType) {
		if (logger.isDebugEnabled()) {
			logger.debug("getUserFollowCompanyList(String, int, int) - start"); //$NON-NLS-1$
		}

		// basic validations
		if (null == companyName) {
			throw new IllegalArgumentException("companyName");
		}

		String sortBy = buildUserSortType(sortType);

		ArrayList<eai.msejdf.esb.User> listOfUsers = new ArrayList<eai.msejdf.esb.User>();

		Query query = entityManager
				.createQuery("SELECT user FROM User user join fetch user.subscribedCompanies as comp "
						+ "WHERE comp.name=:companyName " + sortBy);

		query.setParameter("companyName", companyName);

		@SuppressWarnings("unchecked")
		List<User> userList = query.getResultList();
		for (User user : userList) {
			listOfUsers.add(mapToEsbUser(user));
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getUserFollowCompanyList(String, int, int) - end"); //$NON-NLS-1$
		}
		return listOfUsers;
	}

	/**
	 * Maps a persistnet user to a esb one
	 * @param user
	 * @return
	 */
	private eai.msejdf.esb.User mapToEsbUser(User user) {
		
		eai.msejdf.esb.User responseUser = new eai.msejdf.esb.User();
		responseUser.setId(user.getId());
		responseUser.setUsername(user.getUsername());
		responseUser.setName(user.getName());
		responseUser.setMailAddress(user.getEmail());
		responseUser.setEmailCount(user.getEmailCount());

		return responseUser;
	}

}
