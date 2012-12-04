package eai.msejdf.esb;

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
//        Attachment attachments = message.getAttachment();
//        int attachmentCount = attachments.getUnnamedCount();
//        List<Company> listCompanies = new ArrayList<Company>();
//
//        for (int i = 0; i < attachmentCount; i++) {
//            try {
//        		Message aggrMessage = Util.deserialize((Serializable) attachments.itemAt(i));
//            	listCompanies.add((Company) aggrMessage.getBody().get());
//            } catch (Exception e) {
//            	logger.warn("Not an aggregated message attachment at attachment " + i);
//            }
//        }
//        
//        message.getBody().add(listCompanies);
//
//        // remove the attachments (no more nead)
//        for (int i = attachmentCount - 1; i >= 0; i--) {
//            message.getAttachment().removeItemAt(i);
//        }                

        return message;
    }

}
