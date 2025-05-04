package com.RestFulAPI.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
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
	private JournalEntryService jEntryService ;
	
	

	@PostMapping // variable daaal do me data dalo ya teble me
	public boolean createEntry(@RequestBody JournalEntry journalEntry) {
		journalEntry.setLocalDateTime(LocalDateTime.now());
		jEntryService.saveJEntry(journalEntry);		
		return true;
	}
	
	@GetMapping // variable ya table se data lawo or dikhwo
	public List<JournalEntry> getSimpalAll() 
	{
		return jEntryService.getAllJe();

	}
	
	@GetMapping("id/{myId}")
	public JournalEntry getJournalEntrybyId(@PathVariable ObjectId myId) {

		return jEntryService.findByID(myId).orElse(null);

	}

	@DeleteMapping("id/{myId}")
	public boolean deljournalEntry(@PathVariable ObjectId myId) {
		 jEntryService.deletById(myId);
		 return true;
	}

	@PutMapping("id/{id}")
	public JournalEntry updatJEById(@PathVariable ObjectId id, @RequestBody JournalEntry updatedEntry) {
		JournalEntry oldEntry = jEntryService.findByID(id).orElse(null);

		if (oldEntry != null) {

			oldEntry.setTitle(updatedEntry.getTitle() != null && !updatedEntry.equals("") ? updatedEntry.getTitle()
					: oldEntry.getTitle());
			oldEntry.setContent(
					updatedEntry.getContent() != null && !updatedEntry.equals("") ? updatedEntry.getContent()
							: oldEntry.getContent());
		}
		jEntryService.saveJEntry(oldEntry);

		return oldEntry;
	}
	
	

}
