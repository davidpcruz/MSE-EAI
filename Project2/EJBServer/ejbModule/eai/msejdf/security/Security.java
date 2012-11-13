package eai.msejdf.security;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import eai.msejdf.persistence.BackOfficeUser;
import eai.msejdf.persistence.User;
import eai.msejdf.security.ISecurity;
import eai.msejdf.security.credentials.Credentials;


/**
 * Bean implementing interface for security related calls, such as login, registration, etc.
 */
@Stateless
@LocalBean
public class Security implements ISecurity{
	private static final String EXCEPTION_USER_ALREADY_EXISTS = "The user already exists."; 
	private static final String EXCEPTION_USER_DOES_NOT_EXISTS = "The user does not exist."; 
	private static final String EXCEPTION_INVALID_CREDENTIAL_PARAMETER = "Invalid credential parameter";
		
	@PersistenceContext(unitName = "JPAEAI") 
	private EntityManager entityManager;
	
	@Override
	public void registerUser(Credentials credentials) {
		Query query;
		
		validateCredentialsParameters(credentials);
				
		// Check if the user already exists		
		if (Credentials.USER_CREDENTIAL == credentials.getCredentialType())
		{
			query = entityManager.createQuery("SELECT user FROM User user WHERE user.username=:name");
		}
		else if (Credentials.ADMIN_CREDENTIAL == credentials.getCredentialType())
		{
			query = entityManager.createQuery("SELECT user FROM BackOfficeUser user WHERE user.username=:name");
		}
		else
		{
			throw new IllegalArgumentException(Security.EXCEPTION_INVALID_CREDENTIAL_PARAMETER);
		}
		
		query.setParameter("name", credentials.getUsername());
		
        @SuppressWarnings("unchecked")
		List<Object> userList= query.getResultList();
        if (false == userList.isEmpty())
        {
        	// The user already exists
        	throw new SecurityException(Security.EXCEPTION_USER_ALREADY_EXISTS);
        }
        
        // Add new user
		if (Credentials.USER_CREDENTIAL == credentials.getCredentialType())
		{
	        User newUser = new User();
	        
	        newUser.setUsername(credentials.getUsername());
	        newUser.setPassword(credentials.getPassword());
	        newUser.setName(""); // TODO: Should we receive the name of the user as a parameter?

	        entityManager.persist(newUser);
	        
	        return;
		}

		if (Credentials.ADMIN_CREDENTIAL == credentials.getCredentialType())
		{
	        BackOfficeUser newUser = new BackOfficeUser();
	        
	        newUser.setUsername(credentials.getUsername());
	        newUser.setPassword(credentials.getPassword());
	        
	        entityManager.persist(newUser);
	        
	        return;
		}
	}

	@Override
	public boolean checkUser(Credentials credentials) {
		Query query;
		
		validateCredentialsParameters(credentials);
		
		// Try to find a user that matches the user name and password to see if it is valid
		if (Credentials.USER_CREDENTIAL == credentials.getCredentialType())
		{
			query = entityManager.createQuery("SELECT user FROM User user WHERE user.username=:name AND user.password=:pass");
		}
		else if (Credentials.ADMIN_CREDENTIAL == credentials.getCredentialType())
		{
			query = entityManager.createQuery("SELECT user FROM BackOfficeUser user WHERE user.username=:name AND user.password=:pass");
		}
		else
		{
			throw new IllegalArgumentException(Security.EXCEPTION_INVALID_CREDENTIAL_PARAMETER);
		}
		query.setParameter("name", credentials.getUsername());
		query.setParameter("pass", credentials.getPassword());
		
        if (false == query.getResultList().isEmpty())
        {
        	// Correct credentials supplied
        	return true;
        }
        return false;
	}

	@Override
	public void updateUserCredentials(Credentials credentials)
			throws eai.msejdf.exception.SecurityException {
		Query query;
		
		validateCredentialsParameters(credentials);
				
		// Check if the user already exists		
		if (Credentials.USER_CREDENTIAL == credentials.getCredentialType())
		{
			query = entityManager.createQuery("SELECT user FROM User user WHERE user.username=:name");
		}
		else if (Credentials.ADMIN_CREDENTIAL == credentials.getCredentialType())
		{
			query = entityManager.createQuery("SELECT user FROM BackOfficeUser user WHERE user.username=:name");
		}
		else
		{
			throw new IllegalArgumentException(Security.EXCEPTION_INVALID_CREDENTIAL_PARAMETER);
		}
		
		query.setParameter("name", credentials.getUsername());
		
        @SuppressWarnings("unchecked")
		List<Object> userList= query.getResultList();
        if (true == userList.isEmpty())
        {
        	// The user does not exists
        	throw new SecurityException(Security.EXCEPTION_USER_DOES_NOT_EXISTS);
        }
        
        
        // Add new user
		if (Credentials.USER_CREDENTIAL == credentials.getCredentialType())
		{
	        User user = (User)userList.get(0);
	        
	        user.setPassword(credentials.getPassword());

	        entityManager.merge(user);	        
	        return;
		}

		if (Credentials.ADMIN_CREDENTIAL == credentials.getCredentialType())
		{
	        BackOfficeUser backOfficeUser = (BackOfficeUser)userList.get(0);
	        
	        backOfficeUser.setPassword(credentials.getPassword());

	        entityManager.merge(backOfficeUser);	        
	        return;
		}
	}

	private void validateCredentialsParameters(Credentials credentials)
	{
		if ((null == credentials) || 
			(null == credentials.getUsername()) ||
			(null == credentials.getPassword()))
		{
			throw new IllegalArgumentException(Security.EXCEPTION_INVALID_CREDENTIAL_PARAMETER);
		}
		if ((Credentials.ADMIN_CREDENTIAL != credentials.getCredentialType()) &&
			(Credentials.USER_CREDENTIAL != credentials.getCredentialType()))
		{
			throw new IllegalArgumentException(Security.EXCEPTION_INVALID_CREDENTIAL_PARAMETER);
		}
	}	
}
