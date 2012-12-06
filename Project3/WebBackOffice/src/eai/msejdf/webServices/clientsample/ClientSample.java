package eai.msejdf.webServices.clientsample;

import java.util.List;

import eai.msejdf.webServices.*;
import eai.msejdf.webServices.User;

public class ClientSample {

	public static void main(String[] args) throws ConfigurationException_Exception {

		final Long userId = (long) 1;
		final String companyName = "BES";

		System.out.println("***********************");
		System.out.println("Create Web Service Client...");
		WebServicesService service1 = new WebServicesService();
		System.out.println("Create Web Service...");
		ListUserInterface port1 = service1.getListUserInterfacePort();
		System.out.println("Call Web Service Operation...");
		System.out.println("Server said: " + port1.getUserEmailCount(userId));
		// Please input the parameters instead of 'null' for the upper method!

		System.out.println("Server said: port1.incrementUserEmailCountFromId() is a void method!");
		System.out.println("Server said: port1.incrementUserEmailCountFromList() is a void method!");

		System.out.println("######### getUsersFollowingCompany(companyName) ########");
		List<User> userList = port1.getUsersFollowingCompany(companyName);
		for (User user : userList) {
			System.out.println("Server said: " + user.getName() + " " + user.getMailAddress());
		}

		System.out.println("Create Web Service...");
		ListUserInterface port2 = service1.getListUserInterfacePort();
		System.out.println("Call Web Service Operation...");
		System.out.println("Server said: " + port2.getUserEmailCount(userId));
		// Please input the parameters instead of 'null' for the upper method!

		System.out.println("Server said: port2.incrementUserEmailCountFromId() is a void method!");
		System.out.println("Server said: port2.incrementUserEmailCountFromList() is a void method!");
		System.out.println("Server said: " + port2.getUsersFollowingCompany(companyName));
		// Please input the parameters instead of 'null' for the upper method!

		System.out.println("***********************");
		System.out.println("Call Over!");
	}
}
