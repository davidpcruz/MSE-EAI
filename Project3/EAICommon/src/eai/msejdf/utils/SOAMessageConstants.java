package eai.msejdf.utils;

public final class SOAMessageConstants {
	// ESB specific messages

	public static final String ESB_MSG_BODY = "BODY_CONTENT";

	public static final String ESB_MAIL_TO = "mailTo";

	public static final String ESB_MAIL_FROM = "mailFrom";

	public static final String ESB_MAIL_SUBJECT = "mailSubject";

	// JBPM specific messages

	public static final String JBPM_MAIL_TO = SOAMessageConstants.ESB_MAIL_TO;

	public static final String JBPM_MAIL_FROM = SOAMessageConstants.ESB_MAIL_FROM;

	public static final String JBPM_MAIL_SUBJECT = SOAMessageConstants.ESB_MAIL_SUBJECT;

	// General support messages

	public static final String MAIL_ADDRESS_SEPARATOR = ",";

	// General variable names

	public static final String COMPANY_NAME= "companyName";
	public static final String USER_ID= "userId";
}
