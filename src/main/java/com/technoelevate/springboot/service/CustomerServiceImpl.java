package com.technoelevate.springboot.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.technoelevate.springboot.dao.AdminDao;
import com.technoelevate.springboot.dao.BalanceDao;
import com.technoelevate.springboot.dao.CustomerDao;
import com.technoelevate.springboot.dto.Admin;
import com.technoelevate.springboot.dto.BalanceDetails;
import com.technoelevate.springboot.dto.Customer;
import com.technoelevate.springboot.exception.CustomerException;
import com.technoelevate.springboot.message.Message;

@Service
public class CustomerServiceImpl implements CustomerService, UserDetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private BalanceDao balanceDao;
	@Autowired
	private AdminDao adminDao;
	private static Customer customer;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		if (!username.equals("Abhi")) {
			customer = customerDao.findByUsername(username);
			if (customer == null) {
				LOGGER.error("PLEASE ENTER YOUR CORRECT USER NAME....");
				throw new CustomerException("PLEASE ENTER YOUR CORRECT USER NAME....");
			}
			authorities.add(new SimpleGrantedAuthority("USER"));
			return new User(customer.getUsername(), customer.getPassword(), authorities);
		} else {
			Admin admin = adminDao.findByUsername(username);
			if (admin == null) {
				LOGGER.error("ADMIN NOT FOUND....");
				throw new CustomerException("ADMIN NOT FOUND....");
			}
			authorities.add(new SimpleGrantedAuthority("ADMIN"));
			return new User(admin.getUsername(), admin.getPassword(), authorities);
		}
	}

//	@Override
//	public Message findByUserName(String userName, String password) {
//		Customer customer =(Customer) repository.findByUserName(userName);
//		String encode = encoder.encode(password);
//		System.out.println(encode);
//		System.out.println(customer);
//
//		if (customer != null) {
//			if (customer.getPassword().equals(encode)) {
//				log.info("Successfully Logged in " + userName);
//				return new Message(HttpStatus.OK.value(), new Date(), false, "Successfully Logged in " + userName,
//						customer);
//			}
//			log.error("Please Enter your Correct Password");
//			throw new CustomerException("Please Enter your Correct Password");
//		}
//		log.error("Please Enter your Correct User Name");
//		throw new CustomerException("Please Enter your Correct User Name");
//	}

	@Value("${deposit.tax}")
	double deposit_tax;

	@Override
	public Message deposit(double amount) {
		double availableAmount = (double) Math.round((customer.getBalance() + amount * (1 - deposit_tax)) * 1000.0)
				/ 1000.0;
		if (amount % 100 == 0 && amount > 0) {
			customer.setBalance(availableAmount);
			this.customerDao.save(customer);
			this.balanceDao.save(new BalanceDetails(amount, 0, new Date(), availableAmount, customer));
			System.out.println(this.customerDao);
			Customer customer2 = (Customer) this.customerDao.findByUsername(customer.getUsername());
			return new Message(HttpStatus.OK.value(), new Date(), false, amount + " SUCCESSFULLY DEPOSITED  ",
					customer2);
		}
		LOGGER.error("AMOUNT MUST BE MULTIPLE OF 100....");
		throw new CustomerException("AMOUNT MUST BE MULTIPLE OF 100....");
	}

	@Value("${withdraw.tax}")
	double withdraw_tax;

	@Override
	public Message withdraw(double amount) {
		double availableAmount = (double) Math.round((customer.getBalance() - amount * (1 + withdraw_tax)) * 1000.0)
				/ 1000.0;
		if (amount % 100 != 0) {
			LOGGER.error("AMOUNT MUST BE MULTIPLE OF 100....");
			throw new CustomerException("AMOUNT MUST BE MULTIPLE OF 100....");
		}
		if (availableAmount > 0 && customer.getBalance() > 500) {
			if (customer.getCount() < 3) {
				customer.setBalance(availableAmount);
				customer.setCount(customer.getCount() + 1);
				customerDao.save(customer);
				balanceDao.save(new BalanceDetails(amount, 0, new Date(), availableAmount, customer));
				Customer customer2 = (Customer) customerDao.findByUsername(customer.getUsername());
				return new Message(HttpStatus.OK.value(), new Date(), false, amount + " SUCCESSFULLY WITHDRAWN ",
						customer2);
			}
			LOGGER.error(" WITHDRAWAL LIMIT CANNOT EXCEED BY 3....");
			throw new CustomerException(" WITHDRAWAL LIMIT CANNOT EXCEED BY 3....");
		}
		LOGGER.error("INSUFFICIENT FUNDS!!!");
		throw new CustomerException("INSUFFICIENT FUNDS....");
	}

	@Override
	public Message getBalance() {
		if (customer == null || customer.getUsername() == null) {
			throw new CustomerException("PLEASE LOGIN....");
		}
		Customer customer2 = (Customer) customerDao.findByUsername(customer.getUsername());
		return new Message(HttpStatus.OK.value(), new Date(), false, "YOUR BALANCE IS : " + customer2.getBalance(),
				customer2);
	}

	@Override
	public Customer findByUserName(String userName) {
		customer = (Customer) customerDao.findByUsername(userName);
		LOGGER.info("SUCCESSFULLY LOGGED IN " + userName);
		return customer;
	}

	public Customer getCustomer() {
		return customer;
	}
}
