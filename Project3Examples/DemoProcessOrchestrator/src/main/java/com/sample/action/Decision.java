package com.sample.action;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

public class Decision implements DecisionHandler  {

	private static final long serialVersionUID = 1L;

	public String decide(ExecutionContext executionContext) throws Exception 
	{
		String name = executionContext.getContextInstance().getVariable("myname").toString();
		
		System.out.println("decide: name = " + name);
		
		if (name.startsWith("Filipe"))
		{
			return "to_state1";
		}
		else
		{
			return "to_state2";
		}
	}
}
