package com.cambank.accounts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cambank.accounts.entity.Account;

@Repository
public interface AccountsRepository extends JpaRepository<Account,Long>  {
	
	Account findByCustomerId(int customerId);

}
