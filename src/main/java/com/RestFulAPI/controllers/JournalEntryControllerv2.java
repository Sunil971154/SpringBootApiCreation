package com.RestFulAPI.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
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

import com.RestFulAPI.entity.JournalEntry;
import com.RestFulAPI.entity.User;
import com.RestFulAPI.services.JournalEntryService;
import com.RestFulAPI.services.UserService;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerv2 {

	@Autowired
	private JournalEntryService jEntryService;

	@Autowired
	private UserService userService;

	// pOST CREATE journal Entry
	@PostMapping // variable daaal do me data dalo ya teble me
	public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry) {

		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String userName = authentication.getName();
			myEntry.setLocalDateTime(LocalDateTime.now());
			jEntryService.saveJEntry(myEntry, userName);
			return new ResponseEntity<>(myEntry, HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<>(myEntry, HttpStatus.BAD_REQUEST);
		}
	}

	// gET alL
	@GetMapping // variable ya table se data lawo or dikhwo
	public ResponseEntity<?> getAllJournalEntriesOfUser() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		User user = userService.findByUserName(userName); // database se user isme aagya

		// List<JournalEntry> allJe = jEntryService.getAllJe();
		List<JournalEntry> allJe = user.getJournalEntries();
		if (allJe != null && !allJe.isEmpty()) {
			return new ResponseEntity<>(allJe, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	// gET by id
	@GetMapping("/id/{myId}")
	public ResponseEntity<JournalEntry> getJournalEntrybyId(@PathVariable ObjectId myId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		User user = userService.findByUserName(userName); // database se user isme aagya
		List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId))
				.collect(Collectors.toList());

		if (!collect.isEmpty()) {
			Optional<JournalEntry> journalEntry = jEntryService.findByID(myId);
			if (journalEntry.isPresent())

			{
				return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
			}
		}
		System.out.println("Khali hai getJournalEntrybyId ");
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("id/{myId}")
	public ResponseEntity<?> deljournalEntry(@PathVariable ObjectId myId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		boolean removed = jEntryService.deletById(myId, userName);

		if (removed) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("id/{myid}")
	public ResponseEntity<?> updatJEById(@PathVariable ObjectId myid, @RequestBody JournalEntry updatedEntry) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();

		User user = userService.findByUserName(userName); // database se user isme aagya
		List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myid))
				.collect(Collectors.toList());

		if (!collect.isEmpty()) {
			Optional<JournalEntry> journalEntry = jEntryService.findByID(myid);
			if (journalEntry.isPresent())
			{
				JournalEntry oldEntry = journalEntry.get();
				oldEntry.setTitle(updatedEntry.getTitle() != null && !updatedEntry.equals("") ? updatedEntry.getTitle(): oldEntry.getTitle());
				oldEntry.setContent(updatedEntry.getContent() != null && !updatedEntry.equals("") ? updatedEntry.getContent(): oldEntry.getContent());
				jEntryService.saveJEntry(oldEntry);
				return new ResponseEntity<>(oldEntry,HttpStatus.OK);
			}
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

}
