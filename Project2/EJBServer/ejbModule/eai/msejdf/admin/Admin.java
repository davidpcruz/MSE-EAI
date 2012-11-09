package eai.msejdf.admin;

import org.apache.log4j.Logger;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import eai.msejdf.admin.IAdmin;
import eai.msejdf.persistence.Company;
import eai.msejdf.persistence.User;

/**
 * Bean implementing interface for administration related calls
 */
@Stateful
@LocalBean
public class Admin implements IAdmin {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Admin.class);
	@PersistenceContext(unitName = "JPAEAI")
	// TODO: Check if it can be placed in a config file and update name
	private EntityManager entityManager;

	/*
	 * (non-Javadoc) Get a list of Users in the system (sorttype and
	 * ageThreshold are not implemented yet)
	 * 
	 * @param int sortType, int ageThreshold
	 * 
	 * @return List<User>
	 * 
	 * @see eai.msejdf.admin.IAdmin#getUserList(int, int)
	 */
	@Override
	public List<User> getUserList(int sortType, int ageThreshold) {
		if (logger.isDebugEnabled()) {
			logger.debug("getUserList(int, int) - start"); //$NON-NLS-1$
		}

		Query query = entityManager
				.createQuery("SELECT user FROM  User AS user  ORDER BY user.name");

		@SuppressWarnings("unchecked")
		List<User> userList = query.getResultList();

		if (logger.isDebugEnabled()) {
			logger.debug("getUserList(int, int) - end"); //$NON-NLS-1$
		}
		return userList;
	}

	/*
	 * (non-Javadoc) Get a list of Users that follow the Companay companyId
	 * (sorttype and ageThreshold are not implemented yet)
	 * 
	 * @param Long companyId, int sortType, int ageThreshold
	 * 
	 * @return List<User>
	 * 
	 * @see eai.msejdf.admin.IAdmin#getUserFollowCompanyList(java.lang.Long,
	 * int, int)
	 */
	@Override
	public List<User> getUserFollowCompanyList(Long companyId, int sortType,
			int ageThreshold) {
		if (logger.isDebugEnabled()) {
			logger.debug("getUserFollowCompanyList(String, int, int) - start"); //$NON-NLS-1$
		}

		// Query query = entityManager
		// .createQuery("SELECT u FROM User AS u JOIN u.subscribedCompanies AS c WHERE c.id=:id");
		Query query = entityManager
				.createQuery("SELECT comp.subscribedUsers FROM Company as comp WHERE comp.id=:id");

		query.setParameter("id", companyId);

		@SuppressWarnings("unchecked")
		List<User> userList = query.getResultList();

		for (User user : userList) {
			System.out.println("\t Result " + user.getName());
			break;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getUserFollowCompanyList(String, int, int) - end"); //$NON-NLS-1$
		}
		return userList;
	}

	/*
	 * (non-Javadoc)Get a list of Companies in the system that mach the
	 * filterPattern (sorttype are not implemented yet)
	 * 
	 * @param String filterPattern, int sortType
	 * 
	 * @return List<Company>
	 * 
	 * @see eai.msejdf.admin.IAdmin#getCompanyList(java.lang.String, int)
	 */
	@Override
	public List<Company> getCompanyList(String filterPattern, int sortType) {
		if (logger.isDebugEnabled()) {
			logger.debug("getCompanyList(String, int) - start"); //$NON-NLS-1$
		}

		// Query query = entityManager
		// .createQuery("SELECT u FROM User AS u JOIN u.subscribedCompanies AS c WHERE c.id=:id");
		Query query = entityManager
				.createQuery("SELECT comp FROM Company as comp WHERE comp.name LIKE :filterPattern");

		query.setParameter("filterPattern", filterPattern);

		@SuppressWarnings("unchecked")
		List<Company> companyList = query.getResultList();

		if (logger.isDebugEnabled()) {
			logger.debug("getCompanyList(String, int) - end"); //$NON-NLS-1$
		}
		return companyList;
	}

}
