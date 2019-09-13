package com.tax.verify;

import com.tax.verify.mailSender.EmailSender;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import static com.tax.verify.mailSender.EmailSender.gmail_config;

@SpringBootApplication
@EnableScheduling
public class VerifyApplication{
	private static EmailSender mailer;

	static {
		try {
			mailer = new EmailSender(gmail_config, ImmutablePair.of("errorverifyvkn@gmail.com","gvgGroup!!*"));
		} catch (AddressException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws MessagingException {
		SpringApplication.run(VerifyApplication.class, args);
		mailer.sendEmail("gizemelif.atalay@gvg.com.tr", "Verify", "Scheduler has started");

	}

}
