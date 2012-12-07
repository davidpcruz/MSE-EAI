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

import org.apache.log4j.Logger;
import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import eai.msejdf.utils.SOAMessageConstants;

public class GetUsersFollowingCompanyResponseAction extends AbstractActionLifecycle {
	protected static final Logger logger = Logger.getLogger(GetUsersFollowingCompanyResponseAction.class);

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
		// StringBuffer results = new StringBuffer();

		logHeader();
		logger.debug("####################### original message response start ###################\n ");
		logger.debug("message Items: " + message.toString() + "\n");
		logger.debug("####################### original message response end ###################\n ");

		@SuppressWarnings("unchecked")
		List<User> userList = (List<User>) message.getBody().get();
		logger.debug("Response Map is: " + userList.getClass());
		for (User user : userList) {
			logger.debug("\t" + user + ": " + user.getName() + "\t: " + user.getMailAddress() + "\t: " + user.getUsername() + "\n");
		}

		message.getBody().add(SOAMessageConstants.ESB_USER_LIST, userList);

		logFooter();
		return message;
	}

	public void exceptionHandler(Message message, Throwable exception) {
		logHeader();
		logger.debug("MyResponseAction: " + "!ERROR!");
		logger.debug("MyResponseAction: " + exception.getMessage());
		logger.debug("MyResponseAction: " + "For Message: ");
		logger.debug("MyResponseAction: " + message.getBody().get());
		logFooter();
	}

	// This makes it easier to read on the console
	private void logHeader() {
		logger.debug("&&&&&&&&&&&&&&&&&&&& MyResponseAction start &&&&&&&&&&&&&&&&&&&&&&&&\n");
	}

	private void logFooter() {
		logger.debug("&&&&&&&&&&&&&&&&&&& MyResponseAction end &&&&&&&&&&&&&&&&&&&&&&&&&\n");
	}

}