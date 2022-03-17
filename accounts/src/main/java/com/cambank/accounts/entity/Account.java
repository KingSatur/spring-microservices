package com.cambank.accounts.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Data
@Table(name = "accounts")
public class Account {

	@Column(name = "customer_id")
	private int customerId;
	
	@Column(name = "account_number")
	@Id
	private Long accountNumber;
	
	@Column(name = "account_type")
	private String accountType;
	
	@Column(name = "branch_address")
	private String branchAddress;
	
	@Column(name = "created_at")
	private LocalDate createdAt;
	
}
