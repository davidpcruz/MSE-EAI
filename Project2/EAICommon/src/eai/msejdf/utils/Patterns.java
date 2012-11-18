package eai.msejdf.utils;

public final class Patterns {

	/**
	 * Translates a filter pattern/query into a syntax understood by lower levels of business logic/data tear 
	 * 
	 * @param pattern Pattern string to translate
	 * @return Pattern string specific for the data tier (database) for data filtering
	 */
	public static String getTranslatedFilterPattern(String pattern)
	{
		if ((null == pattern) || (pattern.equals("")))
		{
			return "%";
		}
		return pattern;
	}
	
}
