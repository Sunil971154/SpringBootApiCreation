package com.RestFulAPI.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.RestFulAPI.entity.User;

public interface UserRepo extends MongoRepository<User , ObjectId> {

	public User findByUserName(String userName);

	
	
}
