package com.technoelevate.springboot.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CustomerUserPass implements Serializable {
	private String name;
	private long accountNum;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(long accountNum) {
		this.accountNum = accountNum;
	}

	public CustomerUserPass(String name, long accountNum) {
		this.name = name;
		this.accountNum = accountNum;
	}

	public CustomerUserPass() {
	}

}
