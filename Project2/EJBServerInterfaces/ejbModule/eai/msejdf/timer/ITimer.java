package eai.msejdf.timer;

import javax.ejb.Remote;

@Remote
public interface ITimer {

	public abstract void triggerEmail();

}
