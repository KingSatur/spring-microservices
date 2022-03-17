package com.cambank.accounts.entity;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@Entity
@Table(name = "loan")
public class Loan {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "loan_number")
	private int loanNumber;
	
	@Column(name = "customer_id")
	private Long customerId;
	
	@Column(name = "loan_type")
	private String loanType;
	
	@Column(name = "total_loan")
	private int totalLoan;
	
	@Column(name = "amount_paid")
	private int amountPaid;
	
	@Column(name = "outstanding_amount")
	private int outstandingAmount;
	
	@Column(name = "created_at")
	private Date createdAt;
	
	@Column(name = "started_at")
	private Date startedAt;
}
