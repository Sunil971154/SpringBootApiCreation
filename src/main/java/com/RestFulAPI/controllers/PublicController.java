package com.RestFulAPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.RestFulAPI.entity.User;
import com.RestFulAPI.services.UserService;

@RestController
@RequestMapping("/public")
public class PublicController {

	@Autowired
	private UserService userService;

	@GetMapping("/health-check")
	public String healthCheck() {
		return "Ok";

	}
		

	// 2.0 Create e public hai isko koi bhi call kar sakta hai
	@PostMapping("/create-user")
	public Boolean createUser(@RequestBody User user) {
		   userService.saveUEntry(user);
		
		return true ;
				
	};

}
