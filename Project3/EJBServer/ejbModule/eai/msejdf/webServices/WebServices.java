package eai.msejdf.webServices;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
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

/**
 * Bean implementing interface for webServices calls related calls
 */
// @WebService(name = "ListUserInterface", targetNamespace =
// "http://www.eai.org/mssjdf", serviceName = "ListUserService")
// @Remote(IAdmin.class)
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
	public List<eai.msejdf.esb.User> getUsersFollowingCompany(
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
	public Integer getUserEmailCount(@WebParam(name = "userId") Long userId)
			throws ConfigurationException {
		if (logger.isDebugEnabled()) {
			logger.debug("getUser(String) - start"); //$NON-NLS-1$
		}

		if (null == userId) {
			throw new InvalidParameterException();
		}

		Query query = entityManager
				.createQuery("SELECT User.emailCount FROM User user WHERE user.id=:id");
		query.setParameter("id", userId);

		@SuppressWarnings("unchecked")
		List<Integer> userList = query.getResultList();
		if (true == userList.isEmpty()) {
			// The user doesn't seem to exist
			throw new ConfigurationException(
					WebServices.EXCEPTION_USER_NOT_FOUND);
		}

		Integer userEmailCount = userList.get(0);

		if (logger.isDebugEnabled()) {
			logger.debug("getUser(String) - end"); //$NON-NLS-1$
		}
		return userEmailCount;
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
	public void incrementUserEmailCountFromId(@WebParam(name = "userId") Long userId)
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


	@Override
	@WebMethod
	public void incrementUserEmailCountFromList(@WebParam(name = "userList") List<eai.msejdf.esb.User> userList)
			throws ConfigurationException {
		if (logger.isDebugEnabled()) {
			logger.debug("incrementUserEmailCountFromList(List<eai.msejdf.esb.User>) - start"); //$NON-NLS-1$
		}

		if ((null == userList)) {
			throw new InvalidParameterException();
		}

		for (eai.msejdf.esb.User user : userList)
		{
			User dbUser = getUser(user.getUsername());
	
			// increments user EmailCount by one
			dbUser.setEmailCount(dbUser.getEmailCount() + 1);
	
			entityManager.persist(dbUser);
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("incrementUserEmailCountFromList(List<eai.msejdf.esb.User>) - end"); //$NON-NLS-1$
		}
	}
	
	// TODO decide if it should use Admin getUserList method instead of
	// implementing it again
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

	// TODO decide if it should use Admin getUserList method instead of
	// implementing it again
	/**
	 * Gets the user by user name
	 * 
	 * @param userName Name of user
	 * @return User object 
	 * @throws ConfigurationException
	 */
	private User getUser(String userName) throws ConfigurationException {
		if (logger.isDebugEnabled()) {
			logger.debug("getUser(String) - start"); //$NON-NLS-1$
		}

		if (null == userName) {
			throw new InvalidParameterException();
		}

		Query query = entityManager
				.createQuery("SELECT User FROM User user WHERE user.username=:username");
		query.setParameter("username", userName);

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
			logger.debug("getUser(String) - end"); //$NON-NLS-1$
		}
		return user;
	}
	
	// TODO decide if it should use Admin getUserList method instead of
	// implementing it again
	/**
	 * Gets the user list sorted by user sort type.
	 * 
	 * @param ageThreshold
	 * @param sortType
	 * @return
	 */
	private List<eai.msejdf.esb.User> getUserList(Integer ageThreshold,
			UserSort sortType) {
		if (logger.isDebugEnabled()) {
			logger.debug("getUserList(Integer, UserSort) - start"); //$NON-NLS-1$
		}

		String sortBy = buildUserSortType(sortType);
		eai.msejdf.esb.User responseUser = new eai.msejdf.esb.User();
		ArrayList<eai.msejdf.esb.User> listOfUsers = new ArrayList<eai.msejdf.esb.User>();
		Query query;

		// Different query based on the age restriction
		if (null == ageThreshold) {
			query = entityManager.createQuery("SELECT user FROM  User AS user "
					+ sortBy);
		} else {

			Calendar now = Calendar.getInstance();
			now.add(Calendar.YEAR, (-1) * ageThreshold);

			query = entityManager
					.createQuery("SELECT user FROM  User AS user where user.birthDate <=:ageDate "
							+ sortBy);
			query.setParameter("ageDate", now.getTime());
		}

		@SuppressWarnings("unchecked")
		List<User> userList = query.getResultList();
		for (User user : userList) {
			responseUser.setUsername(user.getUsername());
			responseUser.setName(user.getName());
			responseUser.setMailAddress(user.getEmail());
			listOfUsers.add(responseUser);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getUserList(Integer, UserSort) - end"); //$NON-NLS-1$
		}
		return listOfUsers;
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

	/*
	 * (non-Javadoc)
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

		eai.msejdf.esb.User responseUser = new eai.msejdf.esb.User();
		ArrayList<eai.msejdf.esb.User> listOfUsers = new ArrayList<eai.msejdf.esb.User>();

		Query query = entityManager
				.createQuery("SELECT user FROM User user join fetch user.subscribedCompanies as comp "
						+ "WHERE comp.name=:companyName " + sortBy);

		query.setParameter("companyName", companyName);

		@SuppressWarnings("unchecked")
		List<User> userList = query.getResultList();
		for (User user : userList) {
			responseUser.setUsername(user.getUsername());
			responseUser.setName(user.getName());
			responseUser.setMailAddress(user.getEmail());
			listOfUsers.add(responseUser);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getUserFollowCompanyList(String, int, int) - end"); //$NON-NLS-1$
		}
		return listOfUsers;
	}

}
