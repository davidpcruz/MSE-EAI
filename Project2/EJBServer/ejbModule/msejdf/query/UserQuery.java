package msejdf.query;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Bean implementing interface for user information related queries 
 */
@Stateless
@LocalBean
public class UserQuery implements IUserQuery{

	public Object getUserInfo(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

}
