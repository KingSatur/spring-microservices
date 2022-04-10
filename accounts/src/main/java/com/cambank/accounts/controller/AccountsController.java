package com.cambank.accounts.controller;


import java.util.List;

import io.micrometer.core.annotation.Timed;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
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

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

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
	
//	@CircuitBreaker(name = "customerDetailsCircuitBreaker", fallbackMethod = "customerDetailsError")
//	@CircuitBreaker(name = "customerDetailsCircuitBreaker", fallbackMethod = "retrygetCustomerDetailsFallback")
//	@Retry(name = "retryCustomerDetail")
	@GetMapping("/user/{customer-id}")
	@Timed(value = "getCusomterDetails.time", description = "Time token to get customer details")
	public ResponseEntity<CustomerDetail> getCustomerDetails(
			@RequestHeader("cambank-correlation-id") String correlationId,
			@PathVariable("customer-id") long customerId){
		Account account = this.accountsRepository.findByCustomerId((int)customerId);
		List<Loan> loans = this.loansClient.getLoansByCustomer(correlationId, customerId);
		List<Card> cards = this.cardsClient.getCardsByCustomer(correlationId, (int) customerId);
		
		CustomerDetail customerDetails = new CustomerDetail();
		customerDetails.setAccounts(account);
		customerDetails.setLoans(loans);
		customerDetails.setCards(cards);
		
		return ResponseEntity.ok(customerDetails);
	}
	
//	public ResponseEntity retrygetCustomerDetailsFallback(long customerId, Throwable exception) {
//		
//	}
	
//	public ResponseEntity<CustomerDetail> getCustomerDetailsFallbackMethod(long customerId, Throwable exception){
//		
//	}
	
	
	@GetMapping("/props")
	public ResponseEntity<String> getProps() throws JsonProcessingException{
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		Properties properties = new Properties(accountsConfigurationProps.getMsg(), accountsConfigurationProps.getBuildVersion(),
				accountsConfigurationProps.getMailDetails(), accountsConfigurationProps.getActiveBranches());
		String jsonStr = ow.writeValueAsString(properties);
		return ResponseEntity.ok( jsonStr);
	}
	
	@GetMapping("/say-hello")
	@RateLimiter(name = "sayHello", fallbackMethod = "sayHelloFallback")
	public String sayHello() {
		return "hellow woman";
	}
	
	public String sayHelloFallback( Throwable exception) {
		return "There are too many request";
	}
	
}
