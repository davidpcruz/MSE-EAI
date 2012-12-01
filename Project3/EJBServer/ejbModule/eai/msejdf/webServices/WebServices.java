package eai.msejdf.webServices;

import java.security.InvalidParameterException;
import java.util.Calendar;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
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
@WebService(name = "ListUserInterface", targetNamespace = "http://www.eai.org/mssjdf", serviceName = "ListUserService")
// @Remote(IAdmin.class)
@Stateless(name = "WebServices")
@LocalBean
public class WebServices implements IWebServices {
	private static final String EXCEPTION_USER_NOT_FOUND = "The user was not found.";

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Admin.class);
	@PersistenceContext(unitName = "JPAEAI", type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

	/**
	 * WebMethod Gets the user list sorted by user sort type.
	 * 
	 * @param sortType
	 *            the sort type
	 * @return the user list
	 */
	@Override
	@WebMethod
	public List<User> getUserListAll() {
		return this.getUserList(null, UserSort.NAME_ASC);
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
	public Integer getUserEmailCount(Long userId) throws ConfigurationException {
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
	 * WebMethod Sets the user numbers of sent emails
	 * 
	 * @param userId
	 * @return
	 * @throws ConfigurationException
	 */
	@Override
	public void setUserEmailCount(Long userId, Integer emailCount)
			throws ConfigurationException {
		if (logger.isDebugEnabled()) {
			logger.debug("setBankTeller(Long, Long) - start"); //$NON-NLS-1$
		}

		if ((null == userId)) {
			throw new InvalidParameterException();
		}

		User user = getUser(userId);

		// Setting user EmailCount
		user.setEmailCount(emailCount);

		entityManager.persist(user);

		if (logger.isDebugEnabled()) {
			logger.debug("setBankTeller(Long, Long) - end"); //$NON-NLS-1$
		}
	}

	// TODO decide if it should use Admin getUserList method instead of
	// implementing it again
	/**
	 * Gets the user by user id
	 * @param userId
	 * @return
	 * @throws ConfigurationException
	 */
	@Override
	@WebMethod(exclude = true)
	public User getUser(Long userId) throws ConfigurationException {
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
			throw new ConfigurationException(WebServices.EXCEPTION_USER_NOT_FOUND);
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
	@Override
	@WebMethod(exclude = true)
	public List<User> getUserList(Integer ageThreshold, UserSort sortType) {
		if (logger.isDebugEnabled()) {
			logger.debug("getUserList(Integer, UserSort) - start"); //$NON-NLS-1$
		}

		String sortBy = buildUserSortType(sortType);

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

		if (logger.isDebugEnabled()) {
			logger.debug("getUserList(Integer, UserSort) - end"); //$NON-NLS-1$
		}
		return userList;
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

}