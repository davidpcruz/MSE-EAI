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
package org.jboss.soa.esb.samples.quickstart.transformxml2pojo.test;

import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SendJMSMessage {

	TopicConnectionFactory tcf;
	TopicSession session;
	Topic topic;
	TopicConnection tc;

	public void setupConnection() throws JMSException, NamingException {
		Properties properties1 = new Properties();
		properties1.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.jnp.interfaces.NamingContextFactory");
		properties1.put(Context.URL_PKG_PREFIXES,
				"org.jboss.naming:org.jnp.interfaces");
		properties1.put(Context.PROVIDER_URL, "jnp://127.0.0.1:1199");
		InitialContext iniCtx = new InitialContext(properties1);

		tcf = (TopicConnectionFactory) iniCtx.lookup("ConnectionFactory");
		topic = (Topic) iniCtx.lookup("/topic/EAIProj3_gateway");
	}

	public void stop() throws JMSException {
		tc.stop();
		session.close();
		tc.close();
	}

	public void sendAMessage(String msg) throws JMSException {
		tc = tcf.createTopicConnection();
		session = tc.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
		TopicPublisher tp = session.createPublisher(topic);
		ObjectMessage tm = session.createObjectMessage(msg);
		tp.publish(tm);
	}

	public static void main(String args[]) throws Exception {
		Integer userId = 1;

		SendJMSMessage sm = new SendJMSMessage();
		sm.setupConnection();
		sm.sendAMessage(userId.toString());
		sm.stop();

	}

}