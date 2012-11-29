import java.util.List;
import java.util.Map;

import org.jboss.soa.esb.actions.AbstractActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import eai.msejdf.data.Company;


public class StocksAction extends AbstractActionPipelineProcessor {

	public StocksAction(ConfigTree configTree) {
		
	}
	
	public Message process(Message message) throws ActionProcessingException {
		StringBuffer results = new StringBuffer();
        @SuppressWarnings("rawtypes")
		Map javaResultMap = (Map) message.getBody().get();

//        Stock stockObj = (Stock) javaResultMap.get("stock");
//        Company companyObj = (Company) javaResultMap.get("company");
        @SuppressWarnings("unchecked")
		List<Company> stockItems = (List<Company>) javaResultMap.get("stocksList");

        results.append("Demonstrates Smooks ability to rip the XML into Objects\n");
        results.append("********* StocksAction - Objects Populated *********\n");
//        results.append("stock: " + stockObj + "\n");
//        results.append("company name: " + companyObj.getName() + "\n");
        if(stockItems != null) {
            results.append("Stock Items (" + stockItems.size() + "):\n");
            for(int i = 0; i < stockItems.size(); i++) {
                results.append("\t" + i + ": " + stockItems.get(i).getName() + "\n");
            }
        }
        results.append("\n****************************************************************** ");

		message.getBody().add(results.toString());

		return message;
	}



}
