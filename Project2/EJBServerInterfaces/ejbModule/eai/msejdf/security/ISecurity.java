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
	public void RegisterUser(Credentials credentials) throws SecurityException;
	
	/** Checks if a the supplied user credentials are valid
	 * @param credentials The credentials identifying a user
	 * @throws SecurityException Exception in case the registration failed
	 */
	public boolean CheckUser(Credentials credentials) throws SecurityException;

}
