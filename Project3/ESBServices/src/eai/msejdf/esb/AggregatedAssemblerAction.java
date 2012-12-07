package eai.msejdf.esb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.actions.AbstractActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Attachment;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.util.Util;

public class AggregatedAssemblerAction extends AbstractActionPipelineProcessor {

	protected static final Logger logger = Logger.getLogger(AggregatedAssemblerAction.class);

    public AggregatedAssemblerAction(ConfigTree config) {
    }

    public Message process(Message message) throws ActionProcessingException {
        Attachment attachments = message.getAttachment();
        int attachmentCount = attachments.getUnnamedCount();
        List<Object> listObjects = new ArrayList<Object>();

        logger.debug("process - start");
        logger.debug("attachment count (named) = " + attachments.getNamedCount());
        logger.debug("attachment count (unnamed) = " + attachments.getUnnamedCount());
        
        for (int i = 0; i < attachmentCount; i++) {
            try {
        		Message aggrMessage = Util.deserialize((Serializable) attachments.itemAt(i));
        		
        		logger.debug("aggregated content: " + aggrMessage.getBody().get());
        		
            	listObjects.add(aggrMessage.getBody().get());
            } catch (Exception e) {
            	logger.warn("Not an aggregated message attachment at attachment " + i);
            }
        }
        
        message.getBody().add(listObjects);

        // remove the attachments (no more need for them)
        for (int i = attachmentCount - 1; i >= 0; i--) {
            message.getAttachment().removeItemAt(i);
        }                

        logger.debug("process - end");

        return message;
    }

}
