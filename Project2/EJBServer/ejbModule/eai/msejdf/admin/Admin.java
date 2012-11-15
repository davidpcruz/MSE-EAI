package eai.msejdf.admin;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import eai.msejdf.persistence.Company;
import eai.msejdf.persistence.User;
import eai.msejdf.sort.CompanySort;
import eai.msejdf.sort.UserSort;
import eai.msejdf.utils.StringUtils;

/**
 * Bean implementing interface for administration related calls
 */
@Stateless
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
	 * (non-Javadoc) Get a list of Users in the system (
	 * ageThreshold are not implemented yet)
	 * 
	 * @param int sortType, int ageThreshold
	 * 
	 * @return List<User>
	 * 
	 * @see eai.msejdf.admin.IAdmin#getUserList(int, int)
	 */

	@Override
	public List<User> getUserList(UserSort sortType)
	{
		return this.getUserList(null, sortType);
	}
	
	/* (non-Javadoc)
	 * @see eai.msejdf.admin.IAdmin#getUserList(java.lang.Integer, eai.msejdf.admin.UserSort)
	 */
	@Override
	public List<User> getUserList(Integer ageThreshold, UserSort sortType) {
		if (logger.isDebugEnabled()) {
			logger.debug("getUserList(Integer, UserSort) - start"); //$NON-NLS-1$
		}

		String sortBy = buildUserSortType(sortType);
		
		Query query = entityManager
				.createQuery("SELECT user FROM  User AS user " + sortBy);

		@SuppressWarnings("unchecked")
		List<User> userList = query.getResultList();
		
		if (logger.isDebugEnabled()) {
			logger.debug("getUserList(Integer, UserSort) - end"); //$NON-NLS-1$
		}
		return userList;
	}


	/*
	 * (non-Javadoc) Get a list of Users that follow the Company companyId
	 * 
	 * @param Long companyId, UserSort sortType
	 * 
	 * @return List<User>
	 * 
	 * @see eai.msejdf.admin.IAdmin#getUserFollowCompanyList(java.lang.Long,UserSort)
	 */
	@Override
	public List<User> getUserFollowCompanyList(Long companyId, UserSort sortType) {
		if (logger.isDebugEnabled()) {
			logger.debug("getUserFollowCompanyList(String, int, int) - start"); //$NON-NLS-1$
		}

		// basic validations
		if (null == companyId)
		{
			throw new IllegalArgumentException("filterPattern");
		}		

		String sortBy = buildUserSortType(sortType);
		
		Query query = entityManager.createQuery("SELECT user FROM User user join fetch user.subscribedCompanies as comp " +
				"WHERE comp.id=:id " + sortBy);

		query.setParameter("id", companyId);

		@SuppressWarnings("unchecked")
		List<User> userList = query.getResultList();

		if (logger.isDebugEnabled()) {
			logger.debug("getUserFollowCompanyList(String, int, int) - end"); //$NON-NLS-1$
		}
		return userList;
	}

	/*
	 * (non-Javadoc)Get a list of Companies in the system that match the
	 * filterPattern 
	 * 
	 * @param String filterPattern, int sortType
	 * 
	 * @return List<Company>
	 * 
	 * @see eai.msejdf.admin.IAdmin#getCompanyList(java.lang.String, int)
	 */
	@Override
	public List<Company> getCompanyList(String filterPattern, CompanySort sortType) {
		if (logger.isDebugEnabled()) {
			logger.debug("getCompanyList(String, int) - start"); //$NON-NLS-1$
		}

		// basic validations
		if (StringUtils.isNullOrEmpty(filterPattern))
		{
			throw new IllegalArgumentException("filterPattern");
		}		
		
		String sortBy = buildCompanySortType(sortType);		
		
		// Query query = entityManager
		Query query = entityManager
				.createQuery("SELECT comp FROM Company as comp WHERE comp.name LIKE :filterPattern " + sortBy);

		query.setParameter("filterPattern", filterPattern);

		@SuppressWarnings("unchecked")
		List<Company> companyList = query.getResultList();

		if (logger.isDebugEnabled()) {
			logger.debug("getCompanyList(String, int) - end"); //$NON-NLS-1$
		}
		return companyList;
	}

	/**
	 * Builds the user sort type.
	 *
	 * @param sortType the sort type
	 * @return the string
	 */
	private String buildUserSortType(UserSort sortType)
	{
		String sortBy = "";
		switch (sortType)
		{
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
	 * Builds the company sort type.
	 *
	 * @param sortType the sort type
	 * @return the string
	 */
	private String buildCompanySortType(CompanySort sortType)
	{
		String sortBy = "";
		switch (sortType)
		{
			case NAME_ASC:
				sortBy = "ORDER BY comp.name ASC";
				break;
			case NAME_DESC:
				sortBy = "ORDER BY comp.name DESC";
				break;
			default:
				sortBy = "";
				break;
		}
		return sortBy;
	}

}
