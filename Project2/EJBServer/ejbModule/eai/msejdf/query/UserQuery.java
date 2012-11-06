package eai.msejdf.query;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import eai.msejdf.persistence.User;
import eai.msejdf.query.IUserQuery;


/**
 * Bean implementing interface for user information related queries 
 */
@Stateless
@LocalBean
public class UserQuery implements IUserQuery{

	public User getUserInfo(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

}
