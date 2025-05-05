package com.RestFulAPI.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.RestFulAPI.entity.User;
import com.RestFulAPI.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	// 1.0 Read
	@GetMapping
	public List<User> getAllUsers(User user) {

		return userService.getAllUser();
	}

	// 2.0 Create
	@PostMapping
	public void createUser(@RequestBody User user) {

		userService.saveUEntry(user);

	};

	// 3.1 Update
	@PutMapping("/{userName}")
	public ResponseEntity<?> update_User(@RequestBody User user, @PathVariable String userName) {

		User userInDb = userService.findByUserName(userName);

		if (userInDb != null) {

			userInDb.setUserName(user.getUserName());
			userInDb.setPassword(user.getPassword());
			userService.saveUEntry(userInDb);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
