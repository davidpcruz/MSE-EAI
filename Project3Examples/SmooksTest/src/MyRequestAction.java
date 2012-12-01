import java.util.HashMap;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

public class MyRequestAction extends AbstractActionLifecycle
{
   protected ConfigTree _config;

   public MyRequestAction(ConfigTree config)
   {
      _config = config;
   }

   public Message noOperation(Message message)
   {
      return message;
   }

 
   public Message process(Message message) throws Exception
   {
      logHeader();
      String msgBody = (String) message.getBody().get();
      HashMap requestMap = new HashMap();

      // add paramaters to the web service request map
      requestMap.put("getUserListAll", "");


      message.getBody().add(requestMap);
      System.out.println("Request map is: " + requestMap.toString());

      logFooter();
      return message;
   }

   public void exceptionHandler(Message message, Throwable exception)
   {
      logHeader();
      System.out.println("!ERROR!");
      System.out.println(exception.getMessage());
      System.out.println("For Message: ");
      System.out.println(message.getBody().get());
      logFooter();
   }

   // This makes it easier to read on the console
   private void logHeader()
   {
      System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&\n");
   }

   private void logFooter()
   {
      System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&\n");
   }

}            

