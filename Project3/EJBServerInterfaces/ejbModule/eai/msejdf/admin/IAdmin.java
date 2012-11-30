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
public interface IAdmin {

	/**
	 * Gets the user list sorted by user sort type.
	 *
	 * @param sortType the sort type
	 * @return the user list
	 */
	public List<User> getUserListAll();

	/**
	 * Gets the user list sorted by user sort type.
	 *
	 * @param sortType the sort type
	 * @return the user list
	 */
	public List<User> getUserList(UserSort sortType);

	/**
	 * Gets the user list based on a minimum age and sorted by user sort type.
	 *
	 * @param ageThreshold the age threshold
	 * @param sortType the sort type
	 * @return the user list
	 */
	public List<User> getUserList(Integer ageThreshold, UserSort sortType);

	/**
	 * Gets the users list that follow a certain company.
	 *
	 * @param companyId the company id
	 * @param sortType the sort type
	 * @return the user follow company list
	 */
	public List<User> getUserFollowCompanyList(Long companyId, UserSort sortType);

	/**
	 * Gets all the company based on sort and string search pattern.
	 *
	 * @param filterPattern the filter pattern
	 * @param sortType the sort type
	 * @return the company list
	 */
	public List<Company> getCompanyList(String filterPattern, CompanySort sortType);
}
