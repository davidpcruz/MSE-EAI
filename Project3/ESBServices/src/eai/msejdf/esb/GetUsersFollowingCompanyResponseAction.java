/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, JBoss Inc., and others contributors as indicated 
 * by the @authors tag. All rights reserved. 
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors. 
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 
 * MA  02110-1301, USA.
 * 
 * (C) 2005-2006,
 * @author JBoss Inc.
 */
package eai.msejdf.esb;

import java.util.List;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import eai.msejdf.utils.SOAMessageConstants;

public class GetUsersFollowingCompanyResponseAction extends AbstractActionLifecycle {
	protected ConfigTree _config;

	public GetUsersFollowingCompanyResponseAction(ConfigTree config) {
		_config = config;
	}

	public Message noOperation(Message message) {
		return message;
	}

	/*
	 * Retrieve and output the webservice response.
	 */
	public Message process(Message message) throws Exception {
		//StringBuffer results = new StringBuffer();

		logHeader();
		System.out
				.println("####################### original message response start ###################\n ");
		System.out.println("message Items: " + message.toString() + "\n");
		System.out
				.println("####################### original message response end ###################\n ");

		@SuppressWarnings("unchecked")
		List<User> userList = (List<User>) message.getBody().get();
		System.out.println("Response Map is: " + userList.getClass());
		for (User user : userList) {
			System.out.println("\t" + user + ": " + user.getName() + "\t: "
					+ user.getMailAddress() + "\t: " + user.getUsername()
					+ "\n");
		}

		message.getBody().add(SOAMessageConstants.ESB_USER_LIST, userList);

		
		logFooter();
		return message;
	}

	public void exceptionHandler(Message message, Throwable exception) {
		logHeader();
		System.out.println("MyResponseAction: " + "!ERROR!");
		System.out.println("MyResponseAction: " + exception.getMessage());
		System.out.println("MyResponseAction: " + "For Message: ");
		System.out.println("MyResponseAction: " + message.getBody().get());
		logFooter();
	}

	// This makes it easier to read on the console
	private void logHeader() {
		System.out
				.println("&&&&&&&&&&&&&&&&&&&& MyResponseAction start &&&&&&&&&&&&&&&&&&&&&&&&\n");
	}

	private void logFooter() {
		System.out
				.println("&&&&&&&&&&&&&&&&&&& MyResponseAction end &&&&&&&&&&&&&&&&&&&&&&&&&\n");
	}

}