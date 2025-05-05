package com.RestFulAPI.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.bson.types.ObjectId;
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
	public void saveJEntry(JournalEntry journalEntry, String Username) {

		try {

			User user = userService.findByUserName(Username);

			JournalEntry saved = jeRepo.save(journalEntry); // journal entry save ho rahi hai
															// agr yaha exception aagai iska matlab je save ho jayegai
															// but user me add nahi hogi
															// user me un je ki entry nahi aayegai to yah inconsistensy
															// aajegai
			user.getJournalEntries().add(saved);

			userService.saveUEntry(user);

		} catch (Exception e) {
				System.out.println(e);
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

	public void deletById(ObjectId id, String userName) {
		User user = userService.findByUserName(userName);
		user.getJournalEntries().removeIf(x -> x.getId().equals(id));
		userService.saveUEntry(user);
		jeRepo.deleteById(id);

	}

}
