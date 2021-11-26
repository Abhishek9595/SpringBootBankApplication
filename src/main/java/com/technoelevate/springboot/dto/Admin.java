package com.technoelevate.springboot.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@SuppressWarnings("serial")
@Entity
@JsonIgnoreProperties({ "username", "password" })
public class Admin implements Serializable {
	@Id
	private String name;
	private String username;
	private String password;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Admin [name=" + name + ", username=" + username + ", password=" + password + "]";
	}
	public Admin(String name, String username, String password) {
		this.name = name;
		this.username = username;
		this.password = password;
	}
	public Admin() {
	}
	public Admin(String username, String password) {
		this.username = username;
		this.password = password;
	}
	

}
