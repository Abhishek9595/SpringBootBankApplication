package com.technoelevate.springboot.dto;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@SuppressWarnings("serial")
@Entity
@JsonIgnoreProperties({ "username", "password", "balance", "count" })
@Table(name = "customer_details", uniqueConstraints = @UniqueConstraint(columnNames = { "accountNo" }))
public class Customer implements Serializable {
	@Id
	private String username;
	private String name;
	private long accountNo;
	private String password;
	private double balance;
	private int count;
	@OneToMany(mappedBy = "customer")
	private List<BalanceDetails> balanceDetails;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(long accountNo) {
		this.accountNo = accountNo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<BalanceDetails> getBalanceDetails() {
		return balanceDetails;
	}

	public void setBalanceDetails(List<BalanceDetails> balanceDetails) {
		this.balanceDetails = balanceDetails;
	}

	

	public Customer(String username, String name, long accountNo, String password, double balance, int count) {
		this.username = username;
		this.name = name;
		this.accountNo = accountNo;
		this.password = password;
		this.balance = balance;
		this.count = count;
	}

//	public Customer(String username, String name, long accountNo, String password, double balance, int count,
//			List<BalanceDetails> balanceDetails) {
//		super();
//		this.username = username;
//		this.name = name;
//		this.accountNo = accountNo;
//		this.password = password;
//		this.balance = balance;
//		this.count = count;
//		this.balanceDetails = balanceDetails;
//	}

	public Customer() {
	}

	public Customer(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public Customer(String username, long accountNo) {
		this.username = username;
		this.accountNo = accountNo;
	}
	

}
