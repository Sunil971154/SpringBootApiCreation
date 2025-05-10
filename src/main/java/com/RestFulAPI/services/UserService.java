package com.RestFulAPI.services;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.RestFulAPI.entity.JournalEntry;
import com.RestFulAPI.entity.User;
import com.RestFulAPI.repo.JeRepo;
import com.RestFulAPI.repo.UserRepo;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserService {
	
	@Autowired
	private UserRepo userRepo;

	private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	// private static final  org.slf4j.Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public boolean saveUEntry(User user) {
		try {			
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setRoles(Arrays.asList("USER"));
			userRepo.save(user);
			return true;
			
		} catch (Exception e) 
		{
			log.error("error ocoured {} ");
			log.warn("hhahaahahahhhaahah");
			log.info("hhahaahahahhhaahah");
			log.debug("hhahaahahahhhaahah");
			log.trace("hhahaahahahhhaahah");
			return false;
		}		

	}
	
	public void saveAdmin(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(Arrays.asList("USER","ADMIN"));
		userRepo.save(user);

	}

	public void saveUser(User user) 
	{	 
		userRepo.save(user);

	}
		
	public List<User> getAllUser() {

		return userRepo.findAll();
	}

	public Optional<User> findByID(ObjectId id) {

		return userRepo.findById(id);

	}

	public void deletById(ObjectId id) {
		userRepo.deleteById(id);

	}

	public User findByUserName(String userName) {

		return userRepo.findByUserName(userName);
	}

}
