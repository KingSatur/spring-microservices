package com.cambank.loans.configuration;

import java.util.HashMap;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Configuration
@ConfigurationProperties(prefix = "loans")
@Getter
@Setter
@ToString
public class LoansConfigurationProps {

	private String msg;
	private String buildVersion;
	private HashMap<String, String> mailDetails;
	private List<String> activeBranches;
	
	
}