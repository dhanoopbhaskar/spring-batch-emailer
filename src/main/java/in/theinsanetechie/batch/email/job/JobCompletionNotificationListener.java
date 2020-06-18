package in.theinsanetechie.batch.email.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

/**
 * 
 * @author dhanoopbhaskar
 *
 */

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	private static final Logger logger = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

	@Override
	public void beforeJob(JobExecution jobExecution) {
		logger.info(">>>>>>>> JOB STARTING!");
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			logger.info(">>>>>>>> JOB FINISHED! Time to verify the results!");
		}
	}

}
