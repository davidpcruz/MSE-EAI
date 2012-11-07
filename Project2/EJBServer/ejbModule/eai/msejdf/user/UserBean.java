package eai.msejdf.user;

import org.apache.log4j.Logger;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import eai.msejdf.exception.ConfigurationException;
import eai.msejdf.persistence.BankTeller;
import eai.msejdf.persistence.Company;
import eai.msejdf.persistence.User;

/**
 * Bean implementing operations supported by users
 */
@Stateful
@LocalBean
public class UserBean implements IUserBean {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UserBean.class);

	private static final String EXCEPTION_USER_NOT_FOUND = "The user was not found.";
	private static final String EXCEPTION_COMPANY_NOT_FOUND = "The company was not found.";

	@PersistenceContext(unitName = "JPAEAI")
	// TODO: Check if it can be placed in a config file and update name
	private EntityManager entityManager;

	@Override
	public void updateUser(User user) throws ConfigurationException {
		if (logger.isDebugEnabled()) {
			logger.debug("updateUser(User) - start"); //$NON-NLS-1$
		}

		// TODO: Validate parameters
		entityManager.merge(user);

		if (logger.isDebugEnabled()) {
			logger.debug("updateUser(User) - end"); //$NON-NLS-1$
		}
	}

	@Override
	public User getUser(String name) throws ConfigurationException {
		if (logger.isDebugEnabled()) {
			logger.debug("getUser(String) - start"); //$NON-NLS-1$
		}

		// TODO: Validate parameters
		Query query = entityManager
				.createQuery("SELECT User FROM User user WHERE user.username=:name");
		query.setParameter("name", name);

		@SuppressWarnings("unchecked")
		List<User> userList = query.getResultList();
		if (true == userList.isEmpty()) {
			// The user doesn't seem to exist
			throw new ConfigurationException(UserBean.EXCEPTION_USER_NOT_FOUND);
		}

		User returnUser = userList.get(0);
		if (logger.isDebugEnabled()) {
			logger.debug("getUser(String) - end"); //$NON-NLS-1$
		}
		return returnUser;
	}

	@Override
	public Company getCompany(String name) throws ConfigurationException {
		if (logger.isDebugEnabled()) {
			logger.debug("getCompany(String) - start"); //$NON-NLS-1$
		}

		// TODO Auto-generated method stub
		// TODO: Validate parameters
		Query query = entityManager
				.createQuery("SELECT Company FROM Company company WHERE company.name=:name");
		query.setParameter("name", name);

		@SuppressWarnings("unchecked")
		List<Company> companyList = query.getResultList();
		if (true == companyList.isEmpty()) {
			// The user doesn't seem to exist
			throw new ConfigurationException(
					UserBean.EXCEPTION_COMPANY_NOT_FOUND);
		}

		Company returnCompany = companyList.get(0);
		if (logger.isDebugEnabled()) {
			logger.debug("getCompany(String) - end"); //$NON-NLS-1$
		}
		return returnCompany;
	}

	@Override
	public List<Company> getCompanyList(String filterPattern) {
		if (logger.isDebugEnabled()) {
			logger.debug("getCompanyList(String) - start"); //$NON-NLS-1$
		}

		Query query = entityManager
				.createQuery("SELECT company FROM  Company AS company WHERE company.name LIKE :filterPattern");
		query.setParameter("filterPattern", filterPattern);

		@SuppressWarnings("unchecked")
		List<Company> companyList = query.getResultList();

		if (logger.isDebugEnabled()) {
			logger.debug("getCompanyList(String) - end"); //$NON-NLS-1$
		}
		return companyList;
	}

	@Override
	public List<String> getCompanyNameList(String filterPattern) {
		if (logger.isDebugEnabled()) {
			logger.debug("getCompanyNameList(String) - start"); //$NON-NLS-1$
		}

		Query query = entityManager
				.createQuery("SELECT company.name FROM  Company AS company WHERE company.name LIKE :filterPattern");
		query.setParameter("filterPattern", filterPattern);

		@SuppressWarnings("unchecked")
		List<String> companyList = query.getResultList();

		if (logger.isDebugEnabled()) {
			logger.debug("getCompanyNameList(String) - end"); //$NON-NLS-1$
		}
		return companyList;
	}

	@Override
	public void followCompany(String userName, String companyName)
			throws ConfigurationException {
		if (logger.isDebugEnabled()) {
			logger.debug("followCompany(String, String) - start"); //$NON-NLS-1$
		}

		User user = getUser(userName);
		Company company = getCompany(companyName);
		List<Company> companies = user.getSubscribedCompanies();
		if (companies.contains(company)) {
			logger.debug("User: "+userName +"already follows Company: " + company);
		}
		else{
			logger.debug("Add company to followed companies" + company);
			user.getSubscribedCompanies().add(company);
			entityManager.persist(user);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("followCompany(String, String) - end"); //$NON-NLS-1$
		}
	}

	@Override
	public void unfollowCompany(String userName, String companyName)
			throws ConfigurationException {
		if (logger.isDebugEnabled()) {
			logger.debug("unfollowCompany(String, String) - start"); //$NON-NLS-1$
		}

		User user = getUser(userName);
		Company company = getCompany(companyName);

		user.getSubscribedCompanies().remove(company);

		entityManager.persist(user);

		if (logger.isDebugEnabled()) {
			logger.debug("unfollowCompany(String, String) - end"); //$NON-NLS-1$
		}
	}

	@Override
	public List<Company> getfollowedCompanyList(String userName)
			throws ConfigurationException {
		if (logger.isDebugEnabled()) {
			logger.debug("getfollowedCompanyList(String) - start"); //$NON-NLS-1$
		}

		User user = getUser(userName);
		List<Company> companyList = user.getSubscribedCompanies();

		if (logger.isDebugEnabled()) {
			logger.debug("getfollowedCompanyList(String) - end"); //$NON-NLS-1$
		}
		return companyList;
	}

	@Override
	public void setBankTeller(String userName, String tellerName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBankTeller(String userName, BankTeller bankTeller) {
		// TODO Auto-generated method stub

	}

	@Override
	public BankTeller getBankTeller(String name) {
		if (logger.isDebugEnabled()) {
			logger.debug("getBankTeller(String) - start"); //$NON-NLS-1$
		}

		// TODO Auto-generated method stub

		if (logger.isDebugEnabled()) {
			logger.debug("getBankTeller(String) - end"); //$NON-NLS-1$
		}
		return null;
	}

	@Override
	public List<BankTeller> getBankTellerList(String filterPattern) {
		if (logger.isDebugEnabled()) {
			logger.debug("getBankTellerList(String) - start"); //$NON-NLS-1$
		}

		// TODO Auto-generated method stub

		if (logger.isDebugEnabled()) {
			logger.debug("getBankTellerList(String) - end"); //$NON-NLS-1$
		}
		return null;
	}

	@Override
	public List<String> getBankTellerNameList(String filterPattern) {
		if (logger.isDebugEnabled()) {
			logger.debug("getBankTellerNameList(String) - start"); //$NON-NLS-1$
		}

		// TODO Auto-generated method stub

		if (logger.isDebugEnabled()) {
			logger.debug("getBankTellerNameList(String) - end"); //$NON-NLS-1$
		}
		return null;
	}

}
