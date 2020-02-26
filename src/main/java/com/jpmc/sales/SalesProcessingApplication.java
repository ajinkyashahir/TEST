package com.jpmc.sales;

import com.jpmc.sales.service.MessageProcessingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SalesProcessingApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SalesProcessingApplication.class, args);
		}

		@Bean
		public ExternalCompanyRandomMessageProducer getExternalCompanyMessageProducer(){
			return  new ExternalCompanyRandomMessageProducer();
		}

		@Bean
		public MessageProcessingService getMessageProcessingService(){
			return  new MessageProcessingService();
		}

		@Override
		public void run(String... args) throws Exception {
			for (int i = 0; i < 60; i++) {
				getMessageProcessingService().processMessage(getExternalCompanyMessageProducer().produceMessage());
			}
		}

}
