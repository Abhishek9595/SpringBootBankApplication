package com.technoelevate.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

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
import com.technoelevate.springboot.controller.AdminController;
import com.technoelevate.springboot.dto.CustomerUserPass;
import com.technoelevate.springboot.message.Message;
import com.technoelevate.springboot.service.AdminService;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class AdminControllerTest {
	@InjectMocks
	private AdminController adminController;
	@Mock
	private AdminService adminService;
	private MockMvc mockMvc;
	private ObjectMapper mapper;

	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
		this.mapper = new ObjectMapper();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testControllerReadAll() throws Exception {
		CustomerUserPass customerUserPass = new CustomerUserPass("Abhishek", 4565456);
		Message message = new Message();
		message.setData(customerUserPass);
		Mockito.when(adminService.getAllCustomer()).thenReturn(message);
		String jsonObject = mapper.writeValueAsString(customerUserPass);
		String result = mockMvc
				.perform(get("/api/v1/admin/customers").contentType(MediaType.APPLICATION_JSON).content(jsonObject))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		Message message2 = mapper.readValue(result, Message.class);
		Map<String, Long> map = (Map<String, Long>) message2.getData();
		for (Map.Entry<String, Long> mapData : map.entrySet()) {
			assertEquals(customerUserPass.getName(), mapData.getValue());
			break;
		}
		
	}
}
