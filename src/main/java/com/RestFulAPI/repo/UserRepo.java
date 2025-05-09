package com.RestFulAPI.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.RestFulAPI.entity.User;

public interface UserRepo extends MongoRepository<User, ObjectId> {

	User findByUserName(String userName);

	void deleteByuserName(String userName);

}
