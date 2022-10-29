package com.CRMService.CRMService;

import crm.InternalCRM;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class CrmServiceApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(CrmServiceApplication.class, args);
		InternalCRM internalCRM = InternalCRM.getInstance();

		internalCRM.getLeads(10000, 1000000000, null);
	}

}
