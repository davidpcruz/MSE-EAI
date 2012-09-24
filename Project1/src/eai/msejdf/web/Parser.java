package eai.msejdf.web;

import eai.msejdf.data.Stocks;

public abstract class Parser
{	
	/**
	 * Executes the parser's implementation, returning an object representing the parsed data
	 * 
	 * @return An object representing the stock information
	 */
	public abstract Stocks parse() throws Exception; 
}
