package eai.msejdf.daemons;

import org.apache.log4j.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class DaemonBase.
 */
abstract class DaemonBase
{

	/** Logger for this class. */
	private static final Logger logger = Logger.getLogger(DaemonBase.class);

	/**
	 * Run.
	 */
	public void run()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("run() - start"); //$NON-NLS-1$
		}

		try
		{
			startDaemon();

			java.io.BufferedReader stdin = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
			String lineIn = "";

			do
			{
				System.out.println("Press (q) to exit daemon");
				lineIn = stdin.readLine();

			} while (!lineIn.startsWith("q"));

		} catch (Exception ex)
		{
			logger.error("run", ex); //$NON-NLS-1$
		} finally
		{			
			try
			{
				stopDaemon();
			} 
			catch (Exception ex)
			{
				logger.error("run", ex); //$NON-NLS-1$
			}
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("run() - end"); //$NON-NLS-1$
		}
	}
	
	/**
	 * Start the daemon.
	 *
	 * @throws Exception the exception
	 */
	public abstract void startDaemon() throws Exception;
	
	/**
	 * Stop the daemon.
	 *
	 * @throws Exception the exception
	 */
	public abstract void stopDaemon() throws Exception;
}
