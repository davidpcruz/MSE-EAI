package eai.msejdf.security;

import javax.ejb.Remote;

import eai.msejdf.exception.SecurityException;
import eai.msejdf.security.credentials.Credentials;

/**
 * Interface class for security related operations
 */
@Remote
public interface ISecurity {
	
	/** Registers a user in the system
	 * @param credentials The credentials identifying a user
	 * @throws SecurityException Exception in case the registration failed
	 */
	public void registerUser(Credentials credentials) throws SecurityException;
	
	/** Checks if a the supplied user credentials are valid
	 * @param credentials The credentials identifying a user
	 * @throws SecurityException Exception in case the validation failed
	 */
	public boolean checkUser(Credentials credentials) throws SecurityException;

	/** Changes the credentials associated with a user (password)
	 * @param credentials The credentials identifying a user and to be used
	 * @throws SecurityException Exception in case the update failed
	 */
	public void updateUserCredentials(Credentials credentials) throws SecurityException;

}
