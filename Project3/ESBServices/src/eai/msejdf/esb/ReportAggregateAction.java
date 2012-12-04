package eai.msejdf.esb;

import java.util.List;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.actions.AbstractActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

public class ReportAggregateAction extends AbstractActionPipelineProcessor {

	protected static final Logger logger = Logger.getLogger(ReportAggregateAction.class);

    public ReportAggregateAction(ConfigTree config) {
    }

    public Message process(Message message) throws ActionProcessingException {
    	
		@SuppressWarnings("unchecked")
		List<Report> repItems = (List<Report>) message.getBody().get();
		int nreports = 0;
		int nusersaut = 0;
		int nusersman = 0;
		
		for (Report rep : repItems) {
			nreports ++;
			nusersaut += rep.getUsersWarnedAutomatically();
			nusersman += rep.getUsersWarnedByManager();
		}
		
    	// the aggregated report
    	ReportAggregated report = new ReportAggregated();
		report.setCompaniesProcessed(nreports);
		report.setUsersWarnedAutomatically(nusersaut);
		report.setUsersWarnedByManager(nusersman);
    	
		// Set the message report
		message.getBody().add(report);             

        return message;
    }

}
