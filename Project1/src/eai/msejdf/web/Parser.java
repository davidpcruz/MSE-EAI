package eai.msejdf.web;

public abstract class Parser
{	
	/**
	 * Executes the parser's implementation, returning an object representing the parsed data
	 * 
	 * @return An object representing the stock information
	 */
	public abstract Object parse() throws Exception; 
}
