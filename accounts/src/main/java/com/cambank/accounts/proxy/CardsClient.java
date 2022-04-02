package com.cambank.accounts.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cambank.accounts.entity.Card;

@FeignClient("cards")
public interface CardsClient {

	@RequestMapping(method = RequestMethod.GET, value = "user/{customerId}", consumes = "application/json" )
	List<Card> getCardsByCustomer(
			@RequestHeader("cambank-correlation-id") String correlationId,
			@PathVariable("customerId") long customerId);
	
}
