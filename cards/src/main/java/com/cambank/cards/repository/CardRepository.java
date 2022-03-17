package com.cambank.cards.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cambank.cards.entity.Card;

public interface CardRepository extends CrudRepository<Card, Integer> {

	List<Card> findByCustomerId(int customerId);
}
