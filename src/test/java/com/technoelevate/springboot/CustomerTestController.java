package com.technoelevate.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.technoelevate.springboot.controller.CustomerController;
import com.technoelevate.springboot.dto.Customer;
import com.technoelevate.springboot.message.Message;
import com.technoelevate.springboot.service.CustomerService;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class CustomerTestController {
	@InjectMocks
	private CustomerController customerController;
	@Mock
	private CustomerService customerService;
	private MockMvc mockMvc;
	private ObjectMapper mapper;

	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
		this.mapper = new ObjectMapper();
	}

	@SuppressWarnings({ "unchecked" })
	@Test
	public void testDeposit() throws Exception {
		Customer customer = new Customer("Abhi", "Abhishek", 4567567, "Abhi@4444", 10000, 0);
		Message message = new Message();
		message.setData(customer);
		Mockito.when(customerService.deposit(Mockito.anyDouble())).thenReturn(message);
		String jsonObject = mapper.writeValueAsString(customer);
		String result = mockMvc
				.perform(put("/api/v1/customer/deposit/" + 1500).contentType(MediaType.APPLICATION_JSON)
						.content(jsonObject))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		System.out.println(result);
		Message message2 = mapper.readValue(result, Message.class);
		Map<String, String> map = (Map<String, String>) message2.getData();
		for (Map.Entry<String, String> mapData : map.entrySet()) {
			assertEquals(customer.getName(), mapData.getValue());
			break;
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Test
	public void testWithdraw() throws Exception {
		Customer customer = new Customer("Abhi", "Abhishek", 4567567, "Abhi@4444", 10000, 0);
		Message message = new Message();
		message.setData(customer);
		Mockito.when(customerService.withdraw(Mockito.anyDouble())).thenReturn(message);
		String jsonObject = mapper.writeValueAsString(customer);
		String result = mockMvc
				.perform(put("/api/v1/customer/withdraw/" + 1500).contentType(MediaType.APPLICATION_JSON)
						.content(jsonObject))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		Message message2 = mapper.readValue(result, Message.class);
		Map<String, String> map = (Map<String, String>) message2.getData();
		for (Map.Entry<String, String> mapData : map.entrySet()) {
			assertEquals(customer.getName(), mapData.getValue());
			break;
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Test
	public void testFetchBalance() throws Exception {
		Customer customer = new Customer("Abhi", "Abhishek", 4567567, "Abhi@4444", 10000, 0);
		Message message = new Message();
		message.setData(customer);
		Mockito.when(customerService.getBalance()).thenReturn(message);
		String jsonObject = mapper.writeValueAsString(customer);
		String result = mockMvc
				.perform(get("/api/v1/customer/balance").contentType(MediaType.APPLICATION_JSON).content(jsonObject))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		Message message2 = mapper.readValue(result, Message.class);
		Map<String, String> map = (Map<String, String>) message2.getData();
		for (Map.Entry<String, String> mapData : map.entrySet()) {
			assertEquals(customer.getName(), mapData.getValue());
			break;
		}
	}

}
