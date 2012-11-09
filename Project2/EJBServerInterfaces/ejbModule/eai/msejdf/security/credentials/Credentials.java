package eai.msejdf.security.credentials;

import java.io.Serializable;

/**
 * 
 */
public abstract class Credentials implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public final static int USER_CREDENTIAL = 0;
	public final static int ADMIN_CREDENTIAL = 1;
	
	private String username;
	private String password;
	private int credentialType;
	
	protected Credentials(int credentialType)
	{
		this.setCredentialType(credentialType);
	}	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}	
	public int getCredentialType() {
		return credentialType;
	}
	protected void setCredentialType(int credentialType) {
		this.credentialType = credentialType;
	}	
}
