package com.cambank.accounts.configuration;

import java.util.HashMap;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@Configuration
@ConfigurationProperties(prefix = "accounts")
@Getter
@Setter
@ToString
public class AccountsConfigurationProps {

	private String msg;
	private String buildVersion;
	private HashMap<String, String> mailDetails;
	private List<String> activeBranches;
	
	
}

