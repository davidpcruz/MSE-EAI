package eai.msejdf.utils;

public final class SOAMessageConstants
{
	// ESB specific messages
	
	public static final String ESB_MSG_BODY = "BODY_CONTENT";

	public static final String ESB_MSG_CONTEXT_INFO = "contextInfo";
	
	public static final String ESB_MAIL_TO = "mailTo";

	public static final String ESB_MAIL_FROM = "mailFrom";

	public static final String ESB_MAIL_SUBJECT = "mailSubject";

	public static final String ESB_MAIL_MESSAGE = "mailMessage";

	public static final String ESB_COMPANY_NAME = "companyName";
	
	public static final String ESB_USER_ID = "userId";

	public static final String ESB_USER_LIST = "userList";
	
	public static final String ESB_USER_ID_LIST = "userIdList";

	// JBPM specific messages
	
	public static final String JBPM_MSG_BODY = "msgBody";

	public static final String JBPM_MSG_CONTEXT_INFO = SOAMessageConstants.ESB_MSG_CONTEXT_INFO;

	public static final String JBPM_MAIL_TO = SOAMessageConstants.ESB_MAIL_TO;

	public static final String JBPM_MAIL_FROM = SOAMessageConstants.ESB_MAIL_FROM;

	public static final String JBPM_MAIL_SUBJECT = SOAMessageConstants.ESB_MAIL_SUBJECT;

	public static final String JBPM_MAIL_MESSAGE = SOAMessageConstants.ESB_MAIL_MESSAGE;
	
	public static final String JBPM_COMPANY_NAME = SOAMessageConstants.ESB_COMPANY_NAME;

	public static final String JBPM_USER_LIST = SOAMessageConstants.ESB_USER_LIST;

	public static final String JBPM_USER_ID_LIST = SOAMessageConstants.ESB_USER_ID_LIST;

	// Status report variables
	
	public static final String STATUS_REPORT_USERS_WARNED_AUTOMATICALLY = "usersWarnedAutomatically";

	public static final String STATUS_REPORT_USERS_WARNED_BY_MANAGER = "usersWarnedByManager";

	public static final String STATUS_REPORT_COMPANIES_PROCESSED = "companiesProcessed";
	
	// General support messages
	
	public static final String MAIL_ADDRESS_SEPARATOR = ",";
	
}
