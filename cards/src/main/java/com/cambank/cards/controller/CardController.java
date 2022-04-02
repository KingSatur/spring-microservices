package com.cambank.cards.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cambank.cards.entity.Card;
import com.cambank.cards.entity.Properties;
import com.cambank.cards.configuration.CardsConfigurationProps;
import com.cambank.cards.repository.CardRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@RestController
public class CardController {
	
	private final CardRepository cardRepository;
	private final CardsConfigurationProps cardsConfigurationProps;
	private static final Logger logger = LoggerFactory.getLogger(CardController.class);
	
	public CardController(CardRepository cardRepository, CardsConfigurationProps cardsConfigurationProps) {
		this.cardRepository = cardRepository;
		this.cardsConfigurationProps = cardsConfigurationProps;
	}
	
	
	@GetMapping("/user/{customerId}")
	public ResponseEntity<List<Card>> getLoans(
			@RequestHeader("cambank-correlation-id") String correlationId,
			@PathVariable int customerId) {
		logger.info("Request " + correlationId + " arrives to cards microservices");
		return ResponseEntity.ok(this.cardRepository.findByCustomerId(customerId));
	}
	
	@GetMapping("/props")
	public ResponseEntity<String> getProps() throws JsonProcessingException{
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		Properties properties = new Properties(cardsConfigurationProps.getMsg(), cardsConfigurationProps.getBuildVersion(),
				cardsConfigurationProps.getMailDetails(), cardsConfigurationProps.getActiveBranches());
		String jsonStr = ow.writeValueAsString(properties);
		return ResponseEntity.ok( jsonStr);
	}
}
