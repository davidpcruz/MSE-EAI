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
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ReceiveJMSMessage {
	QueueConnection conn = null;
	QueueSession receiverSession = null;
	Queue receiverQueue = null;
	QueueReceiver queueReceiver = null;
	InitialContext iniCtx = null;
	QueueConnectionFactory qcf = null;
	String receiveQueueName = "queue/queue_simple_transformation_Response";
	private boolean initialised;

	public ReceiveJMSMessage() {

	}

	public void receiveOne() throws JMSException, NamingException {
		if (!initialised) {
			System.out.println("not initialized");
			initialise();
		}

		if (receiverQueue != null) {
			try {
				TextMessage msg = (TextMessage) queueReceiver.receive();
				if (msg != null) {
					System.out.println("*********************************************************");
					System.out.println(msg.getText());
					System.out.println("*********************************************************");
				}
			} catch (final Exception ex) {
				cleanup();
				System.out.println(ex.getMessage());
			}
		} else {
			System.out.println("receiverQueue = null");
		}

		if (!initialised) {
			System.out.println("Pausing before reinitialising");
			try {
				Thread.sleep(5000);
			} catch (final InterruptedException ie) {
				// do nothing
			}
		}
	}

	//
	// private void initialise() {
	// try {
	// if (iniCtx == null)
	// iniCtx = new InitialContext();
	// if (qcf == null)
	// qcf = (QueueConnectionFactory) iniCtx.lookup("ConnectionFactory");
	// if (conn == null) {
	// conn = qcf.createQueueConnection();
	// conn.start();
	// }
	// receiverQueue = (Queue) iniCtx.lookup(receiveQueueName);
	// receiverSession = conn.createQueueSession(false,
	// QueueSession.AUTO_ACKNOWLEDGE);
	// queueReceiver = receiverSession.createReceiver(receiverQueue);
	// initialised = true;
	// System.out.println("Initialised");
	// } catch (final Exception ex) {
	// cleanup();
	// }
	// }

	public void initialise() {
		try {
			System.out.println("Initializing .....");
			Properties properties1 = new Properties();
			properties1.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
			properties1.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
			properties1.put(Context.PROVIDER_URL, "jnp://127.0.0.1:1199");
			InitialContext iniCtx = new InitialContext(properties1);

			Object tmp = iniCtx.lookup("ConnectionFactory");
			QueueConnectionFactory qcf = (QueueConnectionFactory) tmp;
			conn = qcf.createQueueConnection();
			receiverQueue = (Queue) iniCtx.lookup(receiveQueueName);
			receiverSession = conn.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
			conn.start();
			System.out.println("Connection Started");
		} catch (final Exception ex) {
			cleanup();
		}
	}

	public void cleanup() {
		System.out.println("Closing connections");
		queueReceiver = null;
		receiverSession = null;
		receiverQueue = null;
		if (receiverSession != null) {
			try {
				receiverSession.close();
			} catch (final Exception ex) {
				// Do nothing ;
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (final Exception ex) {
				// Do nothing ;
			}
		}
		conn = null;
		qcf = null;
		iniCtx = null;
		initialised = false;
		System.out.println("Closing completed");
	}

	public static void main(String[] args) {
		final ReceiveJMSMessage receiver = new ReceiveJMSMessage();

		// check if default queue name is being overridden

		if ((args.length > 0) && (args[0] != null)) {
			receiver.receiveQueueName = args[0];
		} else {
			// receiver.receiveQueueName =
			// "queue_simple_transformation_Response";
		}

		System.out.println("Receiving on: " + receiver.receiveQueueName);
		// Runtime.getRuntime().addShutdownHook(new Thread() {
		// public void run() {
		// receiver.cleanup();
		// }
		// });
		while (true) { // loop until I'm killed

			try {
				receiver.receiveOne();
			} catch (JMSException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NamingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// System.out.println("Receiving on: " + receiver.receiveQueueName);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				break;
			}
		}
	}
}
