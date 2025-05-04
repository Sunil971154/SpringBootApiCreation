package com.RestFulAPI.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.RestFulAPI.entity.JournalEntry;
import com.RestFulAPI.repo.JeRepo;

@Component
public class JournalEntryService {

	@Autowired
	private JeRepo jeRepo;

	public void saveJEntry(JournalEntry journalEntry) {

		jeRepo.save(journalEntry);

	}

	public List<JournalEntry> getAllJe() {

		return jeRepo.findAll();
	}
	
	
	
	public Optional<JournalEntry> findByID(ObjectId id){
		
		return jeRepo.findById(id);
		
		
	}
	
	
	public void deletById(ObjectId id)
	{
		jeRepo.deleteById(id);
		
	}
	
	
	
	
	
	
	

}
