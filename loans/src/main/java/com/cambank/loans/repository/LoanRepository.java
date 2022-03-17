package com.cambank.loans.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cambank.loans.entity.Loan;

public interface LoanRepository extends CrudRepository<Loan, Integer> {
	
	List<Loan> findByCustomerIdOrderByStartedAtDesc(long customerId);
	
}
