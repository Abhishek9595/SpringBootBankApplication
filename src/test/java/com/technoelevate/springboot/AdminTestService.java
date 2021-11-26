package com.technoelevate.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.technoelevate.springboot.dao.AdminDao;
import com.technoelevate.springboot.dao.CustomerDao;
import com.technoelevate.springboot.dto.Customer;
import com.technoelevate.springboot.dto.CustomerUserPass;
import com.technoelevate.springboot.message.Message;
import com.technoelevate.springboot.service.AdminServiceImpl;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class AdminTestService {
	@Mock
	private AdminDao adminDao;
	@Mock
	private CustomerDao customerDao;
	@InjectMocks
	private AdminServiceImpl adminServiceImpl;

	@SuppressWarnings("unchecked")
	@Test
	public void testServiceReadAll() {
		List<Customer> list = new ArrayList<Customer>();
		Customer customer = new Customer("shami20", "Shami", 34567867, "Shami@123", 10000, 0);
		list.add(customer);
		Customer customer1 = new Customer("rutu31", "Ruturaj", 4567654, "ruturaj@123", 10000, 0);
		list.add(customer1);
		Mockito.when(customerDao.findAll()).thenReturn(list);
		Message message = adminServiceImpl.getAllCustomer();
		ArrayList<CustomerUserPass> customerDetails = (ArrayList<CustomerUserPass>) message.getData();
		assertEquals(customer.getAccountNo(), customerDetails.get(0).getAccountNum());
		assertEquals(customer1.getAccountNo(), customerDetails.get(1).getAccountNum());
	}

}
