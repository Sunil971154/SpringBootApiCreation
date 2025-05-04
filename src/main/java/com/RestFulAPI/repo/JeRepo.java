package com.RestFulAPI.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.RestFulAPI.entity.JournalEntry;

public interface JeRepo extends MongoRepository<JournalEntry, ObjectId> {

}
