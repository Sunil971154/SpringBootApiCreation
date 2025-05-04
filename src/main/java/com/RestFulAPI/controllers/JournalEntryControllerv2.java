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
import org.springframework.web.bind.annotation.RestController;

import com.RestFulAPI.entity.JournalEntry;
import com.RestFulAPI.services.JournalEntryService;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerv2 {

	@Autowired
	private JournalEntryService jEntryService;

	//pOST CREATE
	@PostMapping // variable daaal do me data dalo ya teble me
	public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry) {

		try {

			myEntry.setLocalDateTime(LocalDateTime.now());
			jEntryService.saveJEntry(myEntry);
			return new ResponseEntity<>(myEntry, HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<>(myEntry, HttpStatus.BAD_REQUEST);
		}
	}

	//gET alL
	@GetMapping // variable ya table se data lawo or dikhwo
	public ResponseEntity<?> getSimpalAll() {
		List<JournalEntry> allJe = jEntryService.getAllJe();
		if (allJe != null && !allJe.isEmpty()) {
			return new ResponseEntity<>(allJe, HttpStatus.OK);
		}
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

	
	
	@DeleteMapping("id/{myId}")
	public ResponseEntity<?> deljournalEntry(@PathVariable ObjectId myId) {
		jEntryService.deletById(myId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	
	
	@PutMapping("id/{id}")
	public ResponseEntity<?> updatJEById(@PathVariable ObjectId id, @RequestBody JournalEntry updatedEntry) {
		JournalEntry oldEntry = jEntryService.findByID(id).orElse(null);

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
