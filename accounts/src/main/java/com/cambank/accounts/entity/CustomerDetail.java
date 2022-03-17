package com.cambank.accounts.entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CustomerDetail {

	private Account accounts;
	private List<Loan> loans;
	private List<Card> cards;
	
	
}
