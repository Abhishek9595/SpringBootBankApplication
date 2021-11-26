package com.technoelevate.springboot.exception;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomerAuthEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.setHeader("error", authException.getMessage());
		response.setStatus(HttpStatus.FORBIDDEN.value());
		HashMap<String, String> error= new HashMap<>();
		error.put("statusCode", HttpStatus.FORBIDDEN.toString());
		error.put("timestamp", LocalDateTime.now().toString());
		error.put("error", authException.getMessage());
		error.put("message", "ACCESS DENIED....");
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		new ObjectMapper().writeValue(response.getOutputStream(), error);
	}

}
