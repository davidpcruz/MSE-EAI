package eai.msejdf.user;

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
	private static final String EXCEPTION_USER_NOT_FOUND = "The user was not found."; 
	
	@PersistenceContext(unitName = "JPAEAI")  //TODO: Check if it can be placed in a config file and update name
	private EntityManager entityManager;

	@Override
	public void updateUser(User user) throws ConfigurationException {
		//TODO: Validate parameters
		entityManager.merge(user); 
	}

	@Override
	public User getUser(String name) throws ConfigurationException {

		//TODO: Validate parameters
		Query query = entityManager.createQuery("SELECT User FROM User user WHERE user.username=:name");
		query.setParameter("name", name);
		
        @SuppressWarnings("unchecked")
		List<User> userList= query.getResultList();
        if (true == userList.isEmpty())
        {
        	// The user doesn't seem to exist
        	throw new ConfigurationException(UserBean.EXCEPTION_USER_NOT_FOUND);
        }
        
		return userList.get(0);
	}

	@Override
	public Company getCompany(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Company> getCompanyList(String filterPattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getCompanyNameList(String filterPattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void followCompany(String userName, String companyName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unfollowCompany(String userName, String companyName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Company> getfollowedCompanyList(String userName) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BankTeller> getBankTellerList(String filterPattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getBankTellerNameList(String filterPattern) {
		// TODO Auto-generated method stub
		return null;
	}

}
