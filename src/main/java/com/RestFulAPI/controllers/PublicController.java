package com.RestFulAPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.RestFulAPI.Utilis.JwtUtil;
import com.RestFulAPI.entity.User;
import com.RestFulAPI.services.UserDetailsServiceImpl;
import com.RestFulAPI.services.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Autowired
	private JwtUtil jwtUtilis;

	@GetMapping("/health-check")
	public String healthCheck() {
		return "Ok";

	}

	// 2.0 Create e public hai isko koi bhi call kar sakta hai
	@PostMapping("/signup")
	public Boolean signup(@RequestBody User user) {
		userService.saveUEntry(user);

		return true;

	};

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody User user) 
	{
		try {
			Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
			UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(user.getUserName());
			String jwt = jwtUtilis.generateToken(userDetails.getUsername());
			return new ResponseEntity<>(jwt, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception occured while createAuthentication", e);
			return new ResponseEntity<>("Incorrect UserName And Pasword ", HttpStatus.BAD_REQUEST);
		}
	};

}
