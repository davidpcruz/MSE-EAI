package eai.msejdf.timer;

import javax.ejb.Schedule;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class Timer
 */
@Stateless
public class Timer implements ITimer {

    /**
     * Default constructor. 
     */
    public Timer() {        
    }
    
	@Schedule(second = "*", minute = "*", hour="0")
	protected void dailyMailCompanyDigest() {
		System.out.println("\nSchedule timeout occurred !!!! ");
	}

}
