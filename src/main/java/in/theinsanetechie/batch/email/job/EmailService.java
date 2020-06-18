package in.theinsanetechie.batch.email.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * 
 * @author dhanoopbhaskar
 *
 */

@Service("emailService")
public class EmailService {

	private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

	@Autowired
	private JavaMailSender mailSender;

	/**
	 * 
	 * @param to
	 * @param subject
	 * @param body
	 * 
	 *                Compose and send email
	 */
	public void sendMail(String to, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		logger.info(">>>>>>>> " + to + "|" + subject + "|" + body);
		mailSender.send(message);
	}
}
