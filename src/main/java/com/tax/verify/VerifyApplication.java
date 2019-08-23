package com.tax.verify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VerifyApplication{

	public static void main(String[] args)
	{
		SpringApplication.run(VerifyApplication.class, args);
	}

}
