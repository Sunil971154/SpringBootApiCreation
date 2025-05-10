package com.RestFulAPI.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.management.RuntimeErrorException;

import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.RestFulAPI.entity.JournalEntry;
import com.RestFulAPI.entity.User;
import com.RestFulAPI.repo.JeRepo;

@Component
public class JournalEntryService {

	@Autowired
	private JeRepo jeRepo;

	@Autowired
	private UserService userService;
	
	

	@Transactional
	public void saveJEntry(JournalEntry journalEntry, String userName) {

		try {

			User user = userService.findByUserName(userName);
			JournalEntry saved = jeRepo.save(journalEntry); // journal entry save ho rahi hai
															// agr yaha exception aagai iska matlab je save ho jayegai
															// but user me add nahi hogi
															// user me un je ki entry nahi aayegai to yah inconsistensy
															// aajegai
			user.getJournalEntries().add(saved);
			userService.saveUser(user);

		} catch (Exception e) {
			
			// Throw a RuntimeException so that Spring can trigger rollback
			throw new RuntimeException("An error occurred while saving the entry", e);
		}

	}

	public void saveJEntry(JournalEntry journalEntry) {

		jeRepo.save(journalEntry);
	}

	public List<JournalEntry> getAllJe() {

		return jeRepo.findAll();
	}

	public Optional<JournalEntry> findByID(ObjectId id) {
		return jeRepo.findById(id);
	}

	@Transactional
	public boolean deletById(ObjectId id, String userName) {

		boolean removed = false;
		try {

			User user = userService.findByUserName(userName);
			removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));

			if (removed) {
				userService.saveUser(user);
				jeRepo.deleteById(id);
			}

		} catch (Exception e) {
			System.out.println(e);
			throw new RuntimeException("An error occurrd While deleting the entry . ", e);
		}
		return removed;
	}

	public List<JournalEntry> findByUserName(String userName) {

		return null;

	}

}
