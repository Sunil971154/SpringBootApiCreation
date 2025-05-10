package com.RestFulAPI.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.RestFulAPI.entity.User;
import com.RestFulAPI.repo.UserRepo;
import com.RestFulAPI.response.WeatherResponse;
import com.RestFulAPI.services.UserService;
import com.RestFulAPI.services.WeatherService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private  WeatherService weatherService;
	

	// 1.0 Read
	/*
	 * /@GetMapping public List<User> getAllUsers(User user) {
	 * 
	 * return userService.getAllUser(); }
	 */

	// 3.1 Update
	@PutMapping
	public ResponseEntity<?> update_User(@RequestBody User user) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();

		User userInDb = userService.findByUserName(userName);

		if (userInDb != null) {
			userInDb.setUserName(user.getUserName());
			userInDb.setPassword(user.getPassword());
			userService.saveUEntry(userInDb);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	// 3.1 Update
	@DeleteMapping
	public ResponseEntity<?> deleteUserById(@RequestBody User user) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		userRepo.deleteByuserName(authentication.getName());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	
	@GetMapping
	public ResponseEntity<?> greeting() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		WeatherResponse  weatherResponse = weatherService.getWeather("Mumbai");
		
		String greeting = "";
		
		if(weatherResponse!=null) {
			
			greeting =" , Weather feels like  " + weatherResponse.getCurrent().getFeelslike();
		}
		return new ResponseEntity<>("Hi " + authentication.getName()+ greeting , HttpStatus.OK);
	} 
	
	
	
	

}
