package eai.msejdf.admin;

import java.util.List;

import javax.ejb.Remote;

import eai.msejdf.persistence.Company;
import eai.msejdf.persistence.User;
import eai.msejdf.sort.CompanySort;
import eai.msejdf.sort.UserSort;

/**
 * Interface class for administration related operations
 */
@Remote
// TODO: Set annotation for admin previliges only
public interface IAdmin {

	public List<User> getUserList(UserSort sortType);

	public List<User> getUserList(Integer ageThreshold, UserSort sortType);

	public List<User> getUserFollowCompanyList(Long companyId, UserSort sortType);

	public List<Company> getCompanyList(String filterPattern, CompanySort sortType);
}
