package com.sample.action;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

public class MessageActionHandler2 implements ActionHandler {

	private static final long serialVersionUID = 1L;

	/**
	 * The message member gets its value from the configuration in the
	 * processdefinition. The value is injected directly by the engine.
	 */
	String message;

	/**
	 * A message process variable is assigned the value of the message member.
	 * The process variable is created if it doesn't exist yet.
	 */
	public void execute(ExecutionContext context) throws Exception {
		// context.getContextInstance().setVariable("message", message);
		System.out.println("Inside MessageActionHandler2");
		System.out.println(context.getContextInstance().getVariable("theBody"));
		
		ContextInstance ctx = context.getContextInstance();
		
		Map<String, String> map = ctx.getVariables();
		
		Set<String> keys = map.keySet();
		for(String k : keys)
		{
			System.out.println("[" + k + "] = " + map.get(k));
		}
	}

}
