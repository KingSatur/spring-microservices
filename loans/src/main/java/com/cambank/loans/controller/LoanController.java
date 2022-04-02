package com.cambank.loans.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cambank.loans.entity.Loan;
import com.cambank.loans.entity.Properties;
import com.cambank.loans.configuration.LoansConfigurationProps;
import com.cambank.loans.repository.LoanRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@RestController
public class LoanController {
	
	private final LoanRepository loanRepository;
	private final LoansConfigurationProps loansConfigurationProps;
	private static final Logger logger = LoggerFactory.getLogger(LoanController.class);
	
	public LoanController(LoanRepository loanRepository, LoansConfigurationProps loansConfigurationProps) {
		this.loanRepository = loanRepository;
		this.loansConfigurationProps = loansConfigurationProps;
	}
	
	
	@GetMapping("/user/{customerId}")
	public ResponseEntity<List<Loan>> getLoans(
			@RequestHeader("cambank-correlation-id") String correlationId,
			@PathVariable long customerId) {
		logger.info("Request " + correlationId + " arrives to cards microservices");
		return ResponseEntity.of(Optional.of(this.loanRepository.findByCustomerIdOrderByStartedAtDesc(customerId)));
	}
	
	@GetMapping("/props")
	public ResponseEntity<String> getProps() throws JsonProcessingException{
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		Properties properties = new Properties(loansConfigurationProps.getMsg(), loansConfigurationProps.getBuildVersion(),
				loansConfigurationProps.getMailDetails(), loansConfigurationProps.getActiveBranches());
		String jsonStr = ow.writeValueAsString(properties);
		return ResponseEntity.ok( jsonStr);
	}

}
