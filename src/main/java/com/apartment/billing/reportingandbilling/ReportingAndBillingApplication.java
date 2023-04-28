package com.apartment.billing.reportingandbilling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ReportingAndBillingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportingAndBillingApplication.class, args);
	}

}
