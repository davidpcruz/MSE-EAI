package msejdf.security;

import msejdf.exception.SecurityException;

/**
 * Interface class for security related operations
 */
public interface ISecurity {
	
	/** Registers a user in the system
	 * @param username The user identifier
	 * @param password The password associated with the user
	 * @throws SecurityException Exception in case the registration failed
	 */
	public void RegisterUser(String username, String password) throws SecurityException;
	
	/** Log in a user into the system
	 * @param username The user identifier
	 * @param password The password associated with the user
	 * @throws SecurityException Exception in case the registration failed
	 */
	public void Login(String username, String password) throws SecurityException;

	/** Log out a user from the system
	 * @param username The user identifier
	 * @throws SecurityException Exception in case the logout failed
	 */
	public void Logout(String username) throws SecurityException;
}
