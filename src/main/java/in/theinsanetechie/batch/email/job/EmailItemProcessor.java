package in.theinsanetechie.batch.email.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import in.theinsanetechie.batch.email.bean.Email;

/**
 * 
 * @author dhanoopbhaskar
 *
 */
public class EmailItemProcessor implements ItemProcessor<Email, Email> {

	private static final Logger logger = LoggerFactory.getLogger(EmailItemProcessor.class);

	@Autowired
	private EmailService emailService;

	@Override
	public Email process(Email email) throws Exception {		
		logger.debug("Sending email: " + email);
		emailService.sendMail(email.getToAddress(), email.getSubject(), email.getEmailBody());
		logger.debug("Email has been sent! " + email);
		return email;
	}

}
