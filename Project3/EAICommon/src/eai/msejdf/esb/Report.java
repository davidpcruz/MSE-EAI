package eai.msejdf.esb;

import java.io.Serializable;

public class Report implements Serializable{

	private static final long serialVersionUID = 1L;

	private int usersWarnedAutomatically;
	
	private int usersWarnedByManager;

	public int getUsersWarnedAutomatically() {
		return usersWarnedAutomatically;
	}

	public void setUsersWarnedAutomatically(int usersWarnedAutomatically) {
		this.usersWarnedAutomatically = usersWarnedAutomatically;
	}

	public int getUsersWarnedByManager() {
		return usersWarnedByManager;
	}

	public void setUsersWarnedByManager(int usersWarnedByManager) {
		this.usersWarnedByManager = usersWarnedByManager;
	}
	
}
