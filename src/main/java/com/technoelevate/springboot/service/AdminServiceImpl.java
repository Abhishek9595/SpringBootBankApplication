package com.technoelevate.springboot.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.technoelevate.springboot.dao.CustomerDao;
import com.technoelevate.springboot.dto.Customer;
import com.technoelevate.springboot.dto.CustomerUserPass;
import com.technoelevate.springboot.message.Message;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private CustomerDao dao;

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);

	@Override
	public Message getAllCustomer() {
		List<CustomerUserPass> list = new ArrayList<>();
		List<Customer> listOfCustomers = dao.findAll();
		listOfCustomers.stream().forEach(data -> list.add(new CustomerUserPass(data.getName(), data.getAccountNo())));
		LOGGER.info("SUCCESSFULLY FETCHED....");
		return new Message(HttpStatus.OK.value(), new Date(), false, "SUCCESSFULLY FETCHED....", list);
	}

}
