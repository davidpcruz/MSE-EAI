package msejdf.query;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Bean implementing interface for company information related queries
 */
@Stateless
@LocalBean
public class CompanyQuery implements ICompanyQuery{

	public List<String> getCompanyNames() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getCompanyStockInfo(String companyName) {
		// TODO Auto-generated method stub
		return null;
	}

}
