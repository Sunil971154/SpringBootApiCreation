package com.RestFulAPI.services;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.RestFulAPI.entity.JournalEntry;
import com.RestFulAPI.entity.User;
import com.RestFulAPI.repo.JeRepo;
import com.RestFulAPI.repo.UserRepo;

@Component
public class UserService {
	@Autowired
	private UserRepo userRepo;

	public void saveUEntry(User user) {

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
