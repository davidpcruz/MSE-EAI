package eai.msejdf.esb;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

public class PrintAggregatorTagAction extends AbstractActionLifecycle {

	public PrintAggregatorTagAction(ConfigTree configTree) {
		
	}
	
	  public Message noOperation(Message message) { return message; } 

	  public Message displayMessage(Message message) throws Exception{		
			  logHeader();
			  System.out.println("\nAgregatorTag (" + message.getContext().getContext("aggregatorTag") + ")");
			  System.out.println("\nMessage " + message.getBody().get());
			  logFooter();
			  return message;         	
		}
	  
	   public void exceptionHandler(Message message, Throwable exception) {
		   logHeader();
		   System.out.println("!ERROR!");
		   System.out.println(exception.getMessage());
		   System.out.println("For Message: ");
		   System.out.println(message.getBody().get());
		   logFooter();
	   }
	   
	   // This makes it easier to read on the console
	   private void logHeader() {
		   System.out.println("\n&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
	   }
	   private void logFooter() {
		   System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&\n");
	   }	   

}
