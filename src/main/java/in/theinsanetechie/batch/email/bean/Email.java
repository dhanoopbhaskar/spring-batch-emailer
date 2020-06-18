package in.theinsanetechie.batch.email.bean;

/**
 * 
 * @author dhanoopbhaskar
 *
 */
public class Email {

	private String toAddress;
	private String subject;
	private String emailBody;

	public Email() {
	}

	public Email(String toAddress, String subject, String emailBody) {
		this.toAddress = toAddress;
		this.subject = subject;
		this.emailBody = emailBody;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getEmailBody() {
		return emailBody;
	}

	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}

	@Override
	public String toString() {
		return "Email [toAddress=" + toAddress + ", subject=" + subject + ", emailBody=" + emailBody + "]";
	}

}
