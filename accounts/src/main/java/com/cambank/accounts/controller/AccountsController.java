package com.cambank.accounts.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cambank.accounts.configuration.AccountsConfigurationProps;
import com.cambank.accounts.entity.Account;
import com.cambank.accounts.entity.Card;
import com.cambank.accounts.entity.CustomerDetail;
import com.cambank.accounts.entity.Loan;
import com.cambank.accounts.entity.Properties;
import com.cambank.accounts.proxy.CardsClient;
import com.cambank.accounts.proxy.LoansClient;
import com.cambank.accounts.repository.AccountsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@RestController
public class AccountsController {

	private final AccountsRepository accountsRepository;
	private final AccountsConfigurationProps accountsConfigurationProps;
	private final LoansClient loansClient;
	private final CardsClient cardsClient;
	
	public AccountsController(
			AccountsRepository accountsRepository,  
			AccountsConfigurationProps accountsConfigurationProps,
			LoansClient loansClient,
			CardsClient cardsClient) {
		this.accountsRepository = accountsRepository;
		this.accountsConfigurationProps = accountsConfigurationProps;
		this.loansClient = loansClient;
		this.cardsClient = cardsClient;
	}
	
	@GetMapping("/user/{customer-id}")
	public ResponseEntity<CustomerDetail> getCustomerDetails(@PathVariable("customer-id") long customerId){
		Account account = this.accountsRepository.findByCustomerId((int)customerId);
		List<Loan> loans = this.loansClient.getLoansByCustomer(customerId);
		List<Card> cards = this.cardsClient.getCardsByCustomer((int) customerId);
		
		CustomerDetail customerDetails = new CustomerDetail();
		customerDetails.setAccounts(account);
		customerDetails.setLoans(loans);
		customerDetails.setCards(cards);
		
		return ResponseEntity.ok(customerDetails);
	}
	
	
	@GetMapping("/props")
	public ResponseEntity<String> getProps() throws JsonProcessingException{
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		Properties properties = new Properties(accountsConfigurationProps.getMsg(), accountsConfigurationProps.getBuildVersion(),
				accountsConfigurationProps.getMailDetails(), accountsConfigurationProps.getActiveBranches());
		String jsonStr = ow.writeValueAsString(properties);
		return ResponseEntity.ok( jsonStr);
	}
	
	
}