package eai.msejdf.esb;

public class ReportAggregated {

	private int usersWarnedAutomatically;
	
	private int usersWarnedByManager;
	
	private int companiesProcessed;
	
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

	public int getCompaniesProcessed() {
		return companiesProcessed;
	}

	public void setCompaniesProcessed(int companiesProcessed) {
		this.companiesProcessed = companiesProcessed;
	}
}
