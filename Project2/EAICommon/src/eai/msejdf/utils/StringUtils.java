/**
 * 
 */
package eai.msejdf.utils;

/**
 * @author dcruz
 *
 */
public class StringUtils
{
	
	/**
	 * Checks if is null or empty.
	 *
	 * @param string the string
	 * @return true, if is null or empty
	 */
	public static boolean isNullOrEmpty(final String string)
	{
	   return string == null || string.isEmpty();
	}
	
	/**
	 * Checks if is null or white space.
	 *
	 * @param string the string
	 * @return true, if successful
	 */
	public static boolean IsNullOrWhiteSpace(final String string)
	{
	   return string == null || string.isEmpty() || string.trim().isEmpty();
	}
}
