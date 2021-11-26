package com.technoelevate.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.technoelevate.springboot.dao.BalanceDao;
import com.technoelevate.springboot.dao.CustomerDao;
import com.technoelevate.springboot.dto.Customer;
import com.technoelevate.springboot.service.CustomerServiceImpl;

@SpringBootTest
public class CustomerTestService {
	@Mock
	private CustomerDao customerDao;
	@Mock
	private BalanceDao balanceDao;
	@InjectMocks
	private CustomerServiceImpl customerServiceImpl;

	@Test
	public void depositeTestService() {
		Customer customer = new Customer("Abhi", "Abhishek", 4567567, "Abhi@4444", 10000, 0);
		Mockito.when(customerDao.findByUsername(customer.getUsername())).thenReturn(customer);
		customerServiceImpl.findByUserName(customer.getUsername());
		Customer customer1 = (Customer) customerServiceImpl.deposit(1300).getData();
		assertEquals(customer.getUsername(), customer1.getUsername());
	}

	@Test
	public void withdrawTestService() {
		Customer customer = new Customer("Abhi", "Abhishek", 4567567, "Abhi@4444", 10000, 0);
		Mockito.when(customerDao.findByUsername(customer.getUsername())).thenReturn(customer);
		customerServiceImpl.findByUserName(customer.getUsername());
		Customer customer1 =  (Customer) customerServiceImpl.withdraw(1300).getData();
		assertEquals(customer.getUsername(), customer1.getUsername());
	}

	@Test
	public void balanceTestService() {
		Customer customer = new Customer("Abhi", "Abhishek", 4567567, "Abhi@4444", 10000, 0);
		Mockito.when(customerDao.findByUsername(customer.getUsername())).thenReturn(customer);
		Customer customer1 = (Customer) customerServiceImpl.findByUserName(customer.getUsername());
		assertEquals(customer.getUsername(), customer1.getUsername());
	}

}
