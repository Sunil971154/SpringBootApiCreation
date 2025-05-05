package com.RestFulAPI.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
	private  UserService userService;

	//pOST CREATE
	@PostMapping("{userName}")  // variable daaal do me data dalo ya teble me
	public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String userName ) {

		try {
			User user = userService.findByUserName(userName);
			myEntry.setLocalDateTime(LocalDateTime.now());
			jEntryService.saveJEntry(myEntry,userName);
			return new ResponseEntity<>(myEntry, HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<>(myEntry, HttpStatus.BAD_REQUEST);
		}
	}

	//gET alL
	@GetMapping("{userName}")  // variable ya table se data lawo or dikhwo	
	public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName) {	
		User user = userService.findByUserName(userName); // database se user isme aagya
		
		//List<JournalEntry> allJe = jEntryService.getAllJe();
		List<JournalEntry> allJe = user.getJournalEntries();
		if (allJe != null && !allJe.isEmpty()) {
			System.out.println("enter in geting all ");
			return new ResponseEntity<>(allJe, HttpStatus.OK);
		}
		System.out.println("Khali hai ");
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	// gET by id 
	@GetMapping("id/{myId}")
	public ResponseEntity<JournalEntry> getJournalEntrybyId(@PathVariable ObjectId myId) {
		Optional<JournalEntry> journalEntry = jEntryService.findByID(myId);

		if (journalEntry.isPresent()) {
			return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
		}

		return new ResponseEntity<>(journalEntry.get(), HttpStatus.NOT_FOUND);
	}

	
	
	@DeleteMapping("id/{userName}/{myId}")
	public ResponseEntity<?> deljournalEntry(@PathVariable ObjectId myId, @PathVariable String userName ) {
		jEntryService.deletById(myId, userName);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	
	
	@PutMapping("id/{userName}/{myid}")
	public ResponseEntity<?> updatJEById(
			@PathVariable ObjectId myid,
			@RequestBody JournalEntry updatedEntry,
			@PathVariable String userName) {
		JournalEntry oldEntry = jEntryService.findByID(myid).orElse(null);

		if (oldEntry != null) {

			oldEntry.setTitle(updatedEntry.getTitle() != null && !updatedEntry.equals("") ? updatedEntry.getTitle()
					: oldEntry.getTitle());
			oldEntry.setContent(
					updatedEntry.getContent() != null && !updatedEntry.equals("") ? updatedEntry.getContent()
							: oldEntry.getContent());
			jEntryService.saveJEntry(oldEntry);
			return new ResponseEntity<>(oldEntry, HttpStatus.OK);
		}
		return new ResponseEntity<>(oldEntry, HttpStatus.NOT_FOUND);

	}

}
