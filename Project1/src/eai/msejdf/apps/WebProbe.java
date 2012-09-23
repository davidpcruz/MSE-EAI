package eai.msejdf.apps;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

import eai.msejdf.web.ParseStocksPlugin;
import eai.msejdf.web.Parser;

public class WebProbe
{
	private final static int PROGRAM_ARG_INDEX__URL = 0;
	private final static String DIRECTORY__PENDING_MESSAGES = "./pending/";
	
	private String webUrl = null;
	
	/**
	 * Main application entry point
	 * @param args Parameters to supply to the application
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{
		validateArgs(args);

		WebProbe probe = new WebProbe(args[PROGRAM_ARG_INDEX__URL]);
		
		probe.run();
	}

	/**
	 * Validates a parameter list against what is expected/valid
	 *   
	 * @param args Parameter list to be validated
	 */
	public static void validateArgs(String[] args)
	{
		// Expected call syntax: "java WebProbe <web url>"
		
		if (0 == args.length())
		{
			printHelp();			
			System.exit(-1);			
			//Does not reach this point
		}
		// Parameters are assumed ok (no url validation is performed)
	}
	
	/**
	 * Prints some help information on how to call the program
	 */
	public static void printHelp()
	{
		System.out.println("Call usage:");
		System.out.println("java WebProbe <web url>");
	}
	
	public WebProbe(String url)
	{
		this.webUrl = url;
	}
	
	public void run()
	{
		Parser webParser = new ParseStocksPlugin(this.webUrl); 

		webParser.parse(); //TODO: this has a return type. Handle it
		
		//TODO: Convert to xml
		
		// TODO: Write pending requests to topic
		
		// TODO: Write xml to topic. If down, write to pending requests file
		
	}
	
	private void writePendingMessages()
	{
		File[] fileList = getFileList(this.DIRECTORY__PENDING_MESSAGES);
	
		try
		{
			for (File messageFile : fileList)
			{
				// TODO: Write file contents
				// TODO: Eliminate file if write OK
			}
		}
		catch(Exception exception)
		{
			// TODO: 
		}
	}
	
	private void saveMessageAsPending(String message)
	{
		
	}
	
	private File[] getFileList(String directoryPath)
	{
		File directory = new File(directoryPath);
		File[] fileList = directory.listFiles();

		// Ensure return file list is ordered chronologically
		Arrays.sort(fileList, new Comparator<File>() 
			{
				public int compare(File file1, File file2)
				{
					// This comparer does not return 0 if modification time is the same, as it is irrelevant for this application
					return (file1.lastModified() < file2.lastModified()) ? -1 : 1;		
				}
			});
		
		return fileList;
	}
	
}
