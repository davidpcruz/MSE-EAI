package eai.msejdf.query;

import javax.ejb.Remote;

/**
 * Interface class for user related query operations
 */
@Remote
public interface IUserQuery {
	/**
	 * Returns information related with a given information
	 * @param userName The user identifier
	 * @return An object with the requested user information
	 */
	Object getUserInfo(String userName); //TODO: Set correct return object type
	
}
