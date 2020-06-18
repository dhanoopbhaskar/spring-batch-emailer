package in.theinsanetechie.batch.email.config;

import java.io.File;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import in.theinsanetechie.batch.email.bean.Email;
import in.theinsanetechie.batch.email.job.EmailItemProcessor;
import in.theinsanetechie.batch.email.job.JobCompletionNotificationListener;

/**
 * 
 * @author dhanoopbhaskar
 *
 */

@Configuration
@EnableBatchProcessing
public class EmailBatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Value("${input.filepath}")
	private String filePath;

	@Bean
	public FlatFileItemReader<Email> reader() {
		File file = new File(filePath);
		return new FlatFileItemReaderBuilder<Email>().name("emailItemReader").resource(new FileSystemResource(file))
				.delimited().delimiter(DelimitedLineTokenizer.DELIMITER_COMMA)
				.names(new String[] { "toAddress", "subject", "emailBody" })
				.fieldSetMapper(new BeanWrapperFieldSetMapper<Email>() {
					{
						setTargetType(Email.class);
					}
				}).build();
	}

	@Bean
	public EmailItemProcessor processor() {
		return new EmailItemProcessor();
	}

	@Bean
	public JdbcBatchItemWriter<Email> writer(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Email>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.sql("INSERT INTO email (to_address, subject, email_body) VALUES (:toAddress, :subject, :emailBody)")
				.dataSource(dataSource).build();
	}

	@Bean
	public Job emailJob(JobCompletionNotificationListener listener, Step step1) {
		return jobBuilderFactory.get("emailJob").incrementer(new RunIdIncrementer()).listener(listener).flow(step1)
				.end().build();
	}

	@Bean
	public Step step1(JdbcBatchItemWriter<Email> writer) {
		return stepBuilderFactory.get("step1").<Email, Email>chunk(10).reader(reader()).processor(processor())
				.writer(writer).build();
	}
}
