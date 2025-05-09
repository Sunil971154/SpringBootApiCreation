package com.RestFulAPI.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.RestFulAPI.entity.User;
import com.RestFulAPI.services.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserService userService;

	@RequestMapping("/all-users")
	public ResponseEntity<?> getAllUser() {
		List<User> allUser = userService.getAllUser();

		if (allUser != null && !allUser.isEmpty()) {

			return new ResponseEntity<>(allUser, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}
	
	
	@PostMapping("/create-admin-user")
	public void createUser(@RequestBody User user) {
		userService.saveAdmin(user);
		
	}
	
	

}
