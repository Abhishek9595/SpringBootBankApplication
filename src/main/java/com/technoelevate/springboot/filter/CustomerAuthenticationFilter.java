package com.technoelevate.springboot.filter;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomerAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerAuthenticationFilter.class);

	private String username;

	public CustomerAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		username = request.getParameter("username");
		String password = request.getParameter("password");
		LOGGER.info("Username is " + username + " And Password Is " + password);
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);
		System.out.println(authenticationToken);
		Authentication authenticate = authenticationManager.authenticate(authenticationToken);
		System.out.println(authenticate);
		return authenticate;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) throws IOException, ServletException {
		User user = (User) authentication.getPrincipal();
		System.out.println(user);
		Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
		String access_token = JWT.create().withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
				.withIssuer(request.getRequestURI().toString())
				.withClaim("roles",
						user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algorithm);

		String refresh_token = JWT.create().withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
				.withIssuer(request.getRequestURI().toString()).sign(algorithm);
		LinkedHashMap<String, Object> message = new LinkedHashMap<>();
		LinkedHashMap<String, Object> tokens = new LinkedHashMap<>();
		message.put("error", false);
		message.put("message", username + " SUCCESSFULLY LOGGEDIN....");
		tokens.put("access_token", access_token);
		tokens.put("refresh_token", refresh_token);
		message.put("data", tokens);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		new ObjectMapper().writeValue(response.getOutputStream(), message);
	}

}
