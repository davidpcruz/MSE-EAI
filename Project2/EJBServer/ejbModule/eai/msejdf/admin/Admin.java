package eai.msejdf.admin;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;

import eai.msejdf.admin.IAdmin;
import eai.msejdf.persistence.Company;
import eai.msejdf.persistence.User;


//TODO-fjn: Check whenever we'll keep this or have it distributed in other beans with method level security permissions
/**
 * Bean implementing interface for administration related calls
 */
@Stateful
@LocalBean
public class Admin implements IAdmin{

	@Override
	public List<User> getUserList(int sortType, int ageThreshold) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getUserFollowCompanyList(String companyName,
			int sortType, int ageThreshold) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Company> getCompanyList(String filterPattern, int sortType) {
		// TODO Auto-generated method stub
		return null;
	}

}
