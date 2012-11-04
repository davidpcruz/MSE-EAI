package eai.msejdf.query;

import java.util.List;

import javax.ejb.Remote;

/**
 * Interface class for company related query operations
 */
@Remote
public interface ICompanyQuery {
	/**
	 * Returns a list with available company names
	 * @return List with name of companies 
	 */
	List<String> getCompanyNames();
	
	/**
	 * Returns stock information related with a given company
	 * @param companyName Name of company for which we want to retrieve stock information
	 * @return Stock information for a given company
	 */
	Object getCompanyStockInfo(String companyName); //TODO: Change return type to correct object!
}
