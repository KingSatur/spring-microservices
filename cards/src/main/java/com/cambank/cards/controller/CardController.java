package com.cambank.cards.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	public CardController(CardRepository cardRepository, CardsConfigurationProps cardsConfigurationProps) {
		this.cardRepository = cardRepository;
		this.cardsConfigurationProps = cardsConfigurationProps;
	}
	
	
	@GetMapping("/user/{customerId}")
	public ResponseEntity<List<Card>> getLoans(@PathVariable int customerId) {
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
