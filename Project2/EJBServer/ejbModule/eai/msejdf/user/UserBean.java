package eai.msejdf.user;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import eai.msejdf.exception.ConfigurationException;
import eai.msejdf.persistence.Address;
import eai.msejdf.persistence.BankTeller;
import eai.msejdf.persistence.Company;
import eai.msejdf.persistence.User;

/*
 * 
 * Bean implementing operations supported by users
 */
@Stateless
@LocalBean
public class UserBean implements IUserBean {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UserBean.class);

	private static final String EXCEPTION_USER_NOT_FOUND = "The user was not found.";
	private static final String EXCEPTION_COMPANY_NOT_FOUND = "The company was not found.";
	private static final String EXCEPTION_BANKTELLER_NOT_FOUND = "The Bank Teller was not found.";
	private static final String EXCEPTION_BANKTELLER_ALREADY_EXISTS = "The Bank Teller already exists.";
	private static final String EXCEPTION_BANKTELLER_NAME_EMPTY = "The Bank Teller name cannot be empty.";

	@PersistenceContext(unitName = "JPAEAI")
	// TODO: Check if it can be placed in a config file and update name
	private EntityManager entityManager;

	/*
	 * (non-Javadoc) Update User object
	 * 
	 * @param User user
	 * 
	 * @return User
	 * 
	 * @see eai.msejdf.user.IUserBean#updateUser(eai.msejdf.persistence.User)
	 */
	@Override
	public void updateUser(User user) throws ConfigurationException {
		if (logger.isDebugEnabled()) {
			logger.debug("updateUser(User) - start"); //$NON-NLS-1$
		}

		// TODO: Validate parameters
		BankTeller bankTeller = user.getBankTeller();

		if (null == bankTeller) {
			// Nothing to do
		} else if (null == bankTeller.getId()) {
			entityManager.persist(bankTeller);
		} else {
			entityManager.merge(bankTeller);
		}

		entityManager.merge(user);

		if (logger.isDebugEnabled()) {
			logger.debug("updateUser(User) - end"); //$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc) Get User object knowing the user name
	 * 
	 * @param String userName
	 * 
	 * @return User
	 * 
	 * @see eai.msejdf.user.IUserBean#getUser(java.lang.String)
	 */
	@Override
	public User getUser(String userName) throws ConfigurationException {
		if (logger.isDebugEnabled()) {
			logger.debug("getUser(String) - start"); //$NON-NLS-1$
		}

		Query query = entityManager
				.createQuery("SELECT User FROM User user WHERE user.username=:name");
		query.setParameter("name", userName);

		@SuppressWarnings("unchecked")
		List<User> userList = query.getResultList();
		if (true == userList.isEmpty()) {
			// The user doesn't exists
			throw new ConfigurationException(UserBean.EXCEPTION_USER_NOT_FOUND);
		}

		User user = userList.get(0);

		// TODO: Review this
		if (null != user.getAddress()) {
			// Force load
			user.getAddress().getAddress();
		}
		if (null != user.getBankTeller()) {

			BankTeller bankTeller = user.getBankTeller();

			bankTeller.getId(); // To overcome Lazzy parameter
			if (null != bankTeller.getAddress()) {
				bankTeller.getAddress().getAddress(); // To overcome Lazzy
														// parameter
			}

		}
		if (logger.isDebugEnabled()) {
			logger.debug("getUser(String) - end"); //$NON-NLS-1$
		}
		return user;
	}

	/*
	 * (non-Javadoc) Get User object giving the user ID
	 * 
	 * @param Long userId
	 * 
	 * @return User
	 * 
	 * @see eai.msejdf.user.IUserBean#getUser(java.lang.Long)
	 */
	@Override
	public User getUser(Long userId) throws ConfigurationException {
		if (logger.isDebugEnabled()) {
			logger.debug("getUser(String) - start"); //$NON-NLS-1$
		}

		Query query = entityManager
				.createQuery("SELECT User FROM User user WHERE user.id=:id");
		query.setParameter("id", userId);

		@SuppressWarnings("unchecked")
		List<User> userList = query.getResultList();
		if (true == userList.isEmpty()) {
			// The user doesn't seem to exist
			throw new ConfigurationException(UserBean.EXCEPTION_USER_NOT_FOUND);
		}

		User user = userList.get(0);
		BankTeller bankTeller = user.getBankTeller();
		if (null != bankTeller) {
			bankTeller.getId(); // To overcome Lazzy parameter
			if (null != bankTeller.getAddress()) {
				bankTeller.getAddress().getAddress(); // To overcome Lazzy
														// parameter
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getUser(String) - end"); //$NON-NLS-1$
		}
		return user;
	}

	/*
	 * (non-Javadoc) Get Company information Get the object Company giving the
	 * company ID
	 * 
	 * @param Long companyId
	 * 
	 * @return Company Object
	 * 
	 * @see eai.msejdf.user.IUserBean#getCompany(java.lang.Long)
	 */
	@Override
	public Company getCompany(Long companyId) throws ConfigurationException {
		if (logger.isDebugEnabled()) {
			logger.debug("getCompany(String) - start"); //$NON-NLS-1$
		}

		System.out.println("CompanyId: " + companyId);
		// TODO Auto-generated method stub
		// TODO: Validate parameters
		Query query = entityManager
				.createQuery("SELECT Company FROM Company company WHERE company.id=:id ORDER BY company.name");
		query.setParameter("id", companyId);

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

	/*
	 * (non-Javadoc) Get a List of <Company> that match the filterPattern
	 * 
	 * @param String filterPattern
	 * 
	 * @return List<Company>
	 * 
	 * @see eai.msejdf.user.IUserBean#getCompanyList(java.lang.String)
	 */
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

	/*
	 * (non-Javadoc) Get a List of Company names that match the filterPattern
	 * 
	 * @param String filterPattern
	 * 
	 * @return List<String>
	 * 
	 * @see eai.msejdf.user.IUserBean#getCompanyNameList(java.lang.String)
	 */
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

	/*
	 * (non-Javadoc) Update the User userId to follow the Company companyId
	 * 
	 * @param Long userId, Long companyId
	 * 
	 * @see eai.msejdf.user.IUserBean#followCompany(java.lang.Long,
	 * java.lang.Long)
	 */
	@Override
	public void followCompany(Long userId, Long companyId)
			throws ConfigurationException {
		if (logger.isDebugEnabled()) {
			logger.debug("followCompany(String, String) - start"); //$NON-NLS-1$
		}

		User user = getUser(userId);
		Company company = getCompany(companyId);
		List<Company> companies = user.getSubscribedCompanies();
		if (companies.contains(company)) {
			logger.info("User: " + userId + " already follows Company: "
					+ company);
		} else {
			logger.info("Add company to followed companies " + company);
			user.getSubscribedCompanies().add(company);
			entityManager.persist(user);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("followCompany(String, String) - end"); //$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc) Remove Company companyId from the list of companies
	 * followed by User userId
	 * 
	 * @param String filterPattern
	 * 
	 * @return void
	 * 
	 * @see eai.msejdf.user.IUserBean#unfollowCompany(java.lang.Long,
	 * java.lang.Long)
	 */
	@Override
	public void unfollowCompany(Long userId, Long companyId)
			throws ConfigurationException {
		if (logger.isDebugEnabled()) {
			logger.debug("unfollowCompany(String, String) - start"); //$NON-NLS-1$
		}

		User user = getUser(userId);
		Company company = getCompany(companyId);

		user.getSubscribedCompanies().remove(company);

		entityManager.persist(user);

		if (logger.isDebugEnabled()) {
			logger.debug("unfollowCompany(String, String) - end"); //$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc) Get the list of companies followed by User userId
	 * 
	 * @param String filterPattern
	 * 
	 * @return List<Company>
	 * 
	 * @see eai.msejdf.user.IUserBean#getfollowedCompanyList(java.lang.Long)
	 */
	@Override
	public List<Company> getfollowedCompanyList(Long userId)
			throws ConfigurationException {
		if (logger.isDebugEnabled()) {
			logger.debug("getfollowedCompanyList(String) - start"); //$NON-NLS-1$
		}

		User user = getUser(userId);

		List<Company> companyList = user.getSubscribedCompanies();

		companyList.size();

		if (logger.isDebugEnabled()) {
			logger.debug("getfollowedCompanyList(Long) - end"); //$NON-NLS-1$
		}
		return companyList;
	}

	@Override
	public void setBankTeller(Long userId, Long bankTellerId)
			throws ConfigurationException {
		if (logger.isDebugEnabled()) {
			logger.debug("setBankTeller(Long, Long) - start"); //$NON-NLS-1$
		}

		User user = getUser(userId);
		BankTeller bankTeller = getBankTeller(bankTellerId);

		// Setting user BankTeller
		user.setBankTeller(bankTeller);

		entityManager.persist(user);

		if (logger.isDebugEnabled()) {
			logger.debug("setBankTeller(Long, Long) - end"); //$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc) Set BankTeller bankTeller as User userId Bank Teller
	 * 
	 * @param Long userId, BankTeller bankTeller
	 * 
	 * @return void
	 * 
	 * @see eai.msejdf.user.IUserBean#setBankTeller(java.lang.Long,
	 * eai.msejdf.persistence.BankTeller)
	 */
	@Override
	public void setBankTeller(Long userId, BankTeller bankTeller)
			throws ConfigurationException {
		if (logger.isDebugEnabled()) {
			logger.debug("setBankTeller(Long, BankTeller) - start"); //$NON-NLS-1$

		}

		// Test the BankTeller name empty or null

		try {
			if ("" == bankTeller.getName()) {
				throw new ConfigurationException(
						UserBean.EXCEPTION_BANKTELLER_NAME_EMPTY);
			}
		} catch (Exception ex) {
			// The bankteller name already exists
			throw new ConfigurationException(
					UserBean.EXCEPTION_BANKTELLER_NAME_EMPTY);
		}

		// Check if bankteller name already exists
		Query query = entityManager
				.createQuery("SELECT bankTeller.name FROM  BankTeller AS bankTeller WHERE bankTeller.name=:bankTellerName");
		query.setParameter("bankTellerName", bankTeller.getName());

		@SuppressWarnings("unchecked")
		List<BankTeller> bankTellerList = query.getResultList(); // get all
																	// banktellers
																	// with the
																	// same name
		if (false == bankTellerList.isEmpty()) {
			// The bankteller name already exists
			throw new ConfigurationException(
					UserBean.EXCEPTION_BANKTELLER_ALREADY_EXISTS);
		}

		User user = getUser(userId);

		if (null == bankTeller.getId()) {
			// If BankTeller is new we need to persist it first
			logger.info("Persist bankTeller: " + bankTeller.toString());
			entityManager.persist(bankTeller);
			entityManager.flush();
			// Setting user teller
			user.setBankTeller(bankTeller);

			logger.info("Persist user: " + user.toString());
			entityManager.persist(user);
		} else {
			logger.info("Persist bankTeller: " + bankTeller.toString());
			System.out.println(bankTeller.getAddress().getAddress());
			// Merge the difference in bankTeller
			entityManager.merge(bankTeller);
			entityManager.flush();
			// Setting user teller
			user.setBankTeller(bankTeller);

			logger.info("Persist user: " + user.toString());
			entityManager.persist(user);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setBankTeller(Long, BankTeller) - end"); //$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc) Get the user BankTeller object knowing the user
	 * 
	 * @param userId
	 * 
	 * @return BankTeller
	 * 
	 * @throws ConfigurationException
	 */
	public BankTeller getUserBankTeller(User user)
			throws ConfigurationException {
		if (logger.isDebugEnabled()) {
			logger.debug("getUserBankTellerList(Long) - start"); //$NON-NLS-1$
		}

		BankTeller bankTeller = user.getBankTeller();
		if (null != bankTeller) {
			bankTeller.getId(); // To overcome Lazzy parameter
			if (null != bankTeller.getAddress()) {
				bankTeller.getAddress().getAddress(); // To overcome Lazzy
														// parameter
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getUserBankTellerList(Long) - end"); //$NON-NLS-1$
		}
		System.out.println(bankTeller);
		return bankTeller;
	}

	/*
	 * (non-Javadoc) Get BankTeller object knowing the bankTellerId
	 * 
	 * @param Long bankTellerId
	 * 
	 * @return BankTeller
	 * 
	 * @see eai.msejdf.user.IUserBean#getBankTeller(java.lang.Long)
	 */
	@Override
	public BankTeller getBankTeller(Long bankTellerId)
			throws ConfigurationException {
		if (logger.isDebugEnabled()) {
			logger.debug("getBankTeller(String) - start"); //$NON-NLS-1$
		}

		Query query = entityManager
				.createQuery("SELECT BankTeller FROM BankTeller bankTeller WHERE bankTeller.id=:id ORDER BY bankTeller.id");
		query.setParameter("id", bankTellerId);

		@SuppressWarnings("unchecked")
		List<BankTeller> bankTellerList = query.getResultList();
		if (true == bankTellerList.isEmpty()) {
			// The user doesn't seem to exist
			throw new ConfigurationException(
					UserBean.EXCEPTION_BANKTELLER_NOT_FOUND);
		}

		BankTeller bankTeller = bankTellerList.get(0);

		if (null != bankTeller.getAddress()) {
			bankTeller.getAddress().getAddress();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getBankTeller(String) - end"); //$NON-NLS-1$
		}
		return bankTeller;
	}

	/*
	 * (non-Javadoc) Get a list of BankTellers that match filterPattern
	 * 
	 * @param String filterPattern
	 * 
	 * @return List<BankTeller>
	 * 
	 * @see eai.msejdf.user.IUserBean#getBankTellerList(java.lang.String)
	 */
	@Override
	public List<BankTeller> getBankTellerList(String filterPattern) {
		if (logger.isDebugEnabled()) {
			logger.debug("getBankTellerList(String) - start"); //$NON-NLS-1$
		}

		Query query = entityManager
				.createQuery("SELECT bankTeller FROM  BankTeller AS bankTeller WHERE bankTeller.name LIKE :filterPattern");
		query.setParameter("filterPattern", filterPattern);

		@SuppressWarnings("unchecked")
		List<BankTeller> bankTellerList = query.getResultList();

		if (logger.isDebugEnabled()) {
			logger.debug("getBankTellerNameList(String) - end"); //$NON-NLS-1$
		}
		return bankTellerList;
	}

	/*
	 * (non-Javadoc) Get a list of BankTellers names that match filterPattern
	 * 
	 * @param String filterPattern
	 * 
	 * @return List<String>
	 * 
	 * @see eai.msejdf.user.IUserBean#getBankTellerNameList(java.lang.String)
	 */
	@Override
	public List<String> getBankTellerNameList(String filterPattern) {
		if (logger.isDebugEnabled()) {
			logger.debug("getBankTellerNameList(String) - start"); //$NON-NLS-1$
		}

		Query query = entityManager
				.createQuery("SELECT bankTeller.name FROM  BankTeller AS bankTeller WHERE bankTeller.name LIKE :filterPattern");
		query.setParameter("filterPattern", filterPattern);

		@SuppressWarnings("unchecked")
		List<String> bankTellerList = query.getResultList();

		if (logger.isDebugEnabled()) {
			logger.debug("getBankTellerNameList(String) - end"); //$NON-NLS-1$
		}
		return bankTellerList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eai.msejdf.user.IUserBean#setBankTellerAddress(eai.msejdf.persistence
	 * .BankTeller, eai.msejdf.persistence.Address)
	 */
	@Override
	public void setBankTellerAddress(BankTeller bankTeller) {
		System.out.println("backTeller is: " + bankTeller);
		// bankTeller.setAddress(address);
		Address bankTellerAdress = bankTeller.getAddress();
		if (null == bankTellerAdress) {
			// Nothing to do
		} else if (null == bankTeller.getId()) {
			entityManager.persist(bankTellerAdress);
		} else {
			entityManager.merge(bankTeller);
		}
		entityManager.persist(bankTellerAdress);
		entityManager.persist(bankTeller);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eai.msejdf.user.IUserBean#setBankTellerAddressAddress(eai.msejdf.persistence
	 * .BankTeller, java.lang.String)
	 */
	// @Override
	// public void setBankTellerAddressAddress(BankTeller bankTeller,
	// String address) {
	// bankTeller.getAddress().setAddress(address);
	// entityManager.persist(bankTeller);
	// }
	//
	// @Override
	// public String getBankTellerAddressAddress(BankTeller bankTeller) {
	// return bankTeller.getAddress().getAddress();
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eai.msejdf.user.IUserBean#setBankTellerAddressCity(eai.msejdf.persistence
	 * .BankTeller, java.lang.String)
	 */
	// @Override
	// public void setBankTellerAddressCity(BankTeller bankTeller, String city)
	// {
	// bankTeller.getAddress().setCity(city);
	// entityManager.persist(bankTeller);
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eai.msejdf.user.IUserBean#setBankTellerAddressZipCode(eai.msejdf.persistence
	 * .BankTeller, java.lang.String)
	 */
	// @Override
	// public void setBankTellerAddressZipCode(BankTeller bankTeller,
	// String zipCode) {
	// bankTeller.getAddress().setZipCode(zipCode);
	// entityManager.persist(bankTeller);
	// }

}
