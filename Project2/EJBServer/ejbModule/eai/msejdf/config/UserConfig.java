package eai.msejdf.config;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import eai.msejdf.config.IUserConfig;
import eai.msejdf.exception.ConfigurationException;
import eai.msejdf.persistence.User;


/**
 * Bean implementing interface for user information related configuration calls
 */
@Stateful
@LocalBean
public class UserConfig implements IUserConfig{
	private static final String EXCEPTION_USER_NOT_SET = "The user was not set for this session."; 
	private static final String EXCEPTION_USER_NOT_FOUND = "The user was not found."; 
	
	@PersistenceContext(unitName = "JPAEAI")  //TODO: Check if it can be placed in a config file and update name
	private EntityManager entityManager;

	private String username = null;
	private Integer userid = 0;
	private User userInfo = null;
	
	public void configureUser(String username) 
	{
		this.username = username;		
	}

	public User getUserInfo() throws ConfigurationException 
	{
		// Ensure user associated with this session is valid
		this.validateUserSession();
		
		Query query = entityManager.createQuery("SELECT User FROM User user WHERE user.name=:name");
		query.setParameter("name", this.username);
		
        @SuppressWarnings("unchecked")
		List<User> userList= query.getResultList();
        if (true == userList.isEmpty())
        {
        	// The user doesn't seem to exist
        	throw new ConfigurationException(UserConfig.EXCEPTION_USER_NOT_FOUND);
        }
		// Keep the id of the user 
        this.userid = userList.get(0).getId();
        
		return userList.get(0);
	}

	//TODO: We may flag this as non-transactional
	public void setUserInfo(User userInfo) throws ConfigurationException 
	{
		// Ensure user associated with this session is valid
		this.validateUserSession();

		this.validateUserInfo(userInfo);
		
		// Save a copy to save it later to the database
		this.userInfo = userInfo;
		
		// The info being configured might be new (not based on a user we have retrieved)
		// or based on a user structure we have retrieved.
		// Ensure the supplied user info has the id properly initialized to ensure consistency
		// once persisted
		this.userInfo.setId(this.userid);
		this.userInfo.setUsername(this.username);
	}

	public void saveConfig() 
	{
		if (null != this.userInfo)
		{
			entityManager.persist(this.userInfo);
		}		
	}
	
	/**
	 * Check's whenever a user was already set for this session for subsequent operations 
	 * @throws ConfigurationException 
	 */
	private void validateUserSession() throws ConfigurationException
	{
		if (null != this.username)
		{
			// Already set. Nothing to do
			return;
		}
		throw new ConfigurationException(UserConfig.EXCEPTION_USER_NOT_SET);
	}
	
	/**
	 * Checks that the user information provided is consistent
	 * @param userInfo User information to verify
	 */
	private void validateUserInfo(User userInfo)
	{
		// TODO: Add validations
	}
}
