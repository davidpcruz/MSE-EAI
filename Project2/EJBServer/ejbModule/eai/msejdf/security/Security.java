package eai.msejdf.security;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import eai.msejdf.persistence.User;
import eai.msejdf.security.ISecurity;


/**
 * Bean implementing interface for security related calls, such as login, registration, etc.
 */
@Stateless
@LocalBean
public class Security implements ISecurity{
	private static final String EXCEPTION_USER_ALREADY_EXISTS = "The user already exists."; 
	private static final String EXCEPTION_INVALID_USER_OR_PASS = "The user or password are not correct."; 
	
	@PersistenceContext(unitName = "JPAEAI")  //TODO: Check if it can be placed in a config file and update name
	private EntityManager entityManager;
	
	// TODO: Check transaction use (bean level, method level)
	public void RegisterUser(String username, String password) {
		// Try to register the new user. If it already exists, raise an exception		
		Query query = entityManager.createQuery("SELECT User FROM User user WHERE user.username=:name");
		query.setParameter("name", username);
		
        @SuppressWarnings("unchecked")
		List<User> userList= query.getResultList();
        if (false == userList.isEmpty())
        {
        	// The user already exists
        	throw new SecurityException(Security.EXCEPTION_USER_ALREADY_EXISTS);
        }
        User newUser = new User();
        
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setName(""); // TODO: Should we receive the name of the user as a parameter?
        
        entityManager.persist(newUser);
	}

	public void Login(String username, String password) {
		// Try to find a user that matches the user name and password. Raise an exception if not found
		Query query = entityManager.createQuery("SELECT User FROM User user WHERE user.username=:name AND user.password=:pass");
		query.setParameter("name", username);
		query.setParameter("pass", password);
		
        @SuppressWarnings("unchecked")
		List<User> userList= query.getResultList();
        if (false == userList.isEmpty())
        {
        	// The user already exists
        	throw new SecurityException(Security.EXCEPTION_INVALID_USER_OR_PASS);
        }
        
        // TODO: Should we mark that we are logged in?
	}

	public void Logout(String username)
	{
		// Nothing to do
		
		//TODO: If we are marking that we are logged in in the Login method, we must update that state here
	}
}
