package eai.msejdf.admin;

import java.util.List;

import javax.ejb.Remote;

import eai.msejdf.persistence.Company;
import eai.msejdf.persistence.User;

/**
 * Interface class for administration related operations
 */
@Remote
//TODO: Set annotation for admin previliges only
public interface IAdmin {
	public final static int SORT_BY_NAME = 0;	

	public List<User> getUserList(int sortType, int ageThreshold);	
	
	public List<User> getUserFollowCompanyList(String companyName, int sortType, int ageThreshold);
	
	public List<Company> getCompanyList(String filterPattern, int sortType); 
}
