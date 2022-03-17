package com.cambank.accounts.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cambank.accounts.entity.Loan;

@FeignClient("loans")
public interface LoansClient {
	
	@RequestMapping(method = RequestMethod.GET, value = "loans/user/{customerId}", consumes = "application/json" )
	List<Loan> getLoansByCustomer(@PathVariable("customerId") long customerId);

}
