package eai.msejdf.webServices.clientsample;

import eai.msejdf.webServices.*;

public class ClientSample {

	public static void main(String[] args) throws ESBWebserviceFaultESB {
			Long userId = (long) 1;
	        System.out.println("***********************");
	        System.out.println("Create Web Service Client...");
	        EsbWebserviceService service1 = new EsbWebserviceService();
	        System.out.println("Create Web Service...");
	        EsbWebservice port1 = service1.getEsbWebservicePort();
	        System.out.println("Call Web Service Operation...");
	        System.out.println("Server said: " + port1.getUsersFollowingCompany("BES"));
	        //Please input the parameters instead of 'null' for the upper method!
	
	        System.out.println("Server said: " + port1.getUserEmailCount(userId));
	        //Please input the parameters instead of 'null' for the upper method!
	
	        System.out.println("Create Web Service...");
	        EsbWebservice port2 = service1.getEsbWebservicePort();
	        System.out.println("Call Web Service Operation...");
	        System.out.println("Server said: " + port2.getUsersFollowingCompany("BES"));
	        //Please input the parameters instead of 'null' for the upper method!
	
	        System.out.println("Server said: " + port2.getUserEmailCount(userId));
	        //Please input the parameters instead of 'null' for the upper method!
	
	        System.out.println("***********************");
	        System.out.println("Call Over!");
	}
}
