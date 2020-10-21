package in.theinsanetechie.batch.email.job;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;

import in.theinsanetechie.batch.email.config.GmailConfig;

/**
 * 
 * @author dhanoopbhaskar
 *
 */

@Service("emailService")
public class EmailService {

	private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

	@Autowired
	private Environment environment;

	/**
	 * 
	 * @param to
	 * @param subject
	 * @param body
	 * 
	 *                Compose and send email
	 * @throws MessagingException
	 * @throws AddressException
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public void sendMail(String to, String subject, String body)
			throws AddressException, MessagingException, IOException, GeneralSecurityException {

		String userName = environment.getProperty("spring.mail.username", "dhanoopbhaskar@gmail.com");

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		/**
		 * Create a MimeMessage using the parameters provided.
		 */
		MimeMessage email = new MimeMessage(session);
		email.setFrom(new InternetAddress(userName));
		email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
		email.setSubject(subject);
		email.setText(body);
		logger.debug(">>>>>>>> " + to + "|" + subject + "|" + body);

		/**
		 * Create a message from an email.
		 */
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		email.writeTo(buffer);
		byte[] bytes = buffer.toByteArray();
		String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
		Message message = new Message();
		message.setRaw(encodedEmail);

		/**
		 * Send an email from the user's mailbox to its recipient.
		 */
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Gmail service = new Gmail.Builder(HTTP_TRANSPORT, GmailConfig.JSON_FACTORY,
				GmailConfig.getCredentials(HTTP_TRANSPORT)).setApplicationName(GmailConfig.APPLICATION_NAME).build();

		message = service.users().messages().send(userName, message).execute();

		logger.debug("Message id: " + message.getId());
		logger.debug(message.toPrettyString());
	}
}
