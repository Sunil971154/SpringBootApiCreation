package com.RestFulAPI.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.RestFulAPI.entity.JournalEntry;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
	
	private Map<Long, JournalEntry> journalEntries = new HashMap<>();

	@GetMapping // variable ya table se data lawo or dikhwo
	public List<JournalEntry> getSimpalAll() 
	{
		return new ArrayList<>(journalEntries.values());

	}

	@PostMapping // variable daaal do me data dalo ya teble me
	public boolean createEntry(@RequestBody JournalEntry journalEntry) {
		journalEntries.put(journalEntry.getId(), journalEntry);
		return true;
	}

	@GetMapping("id/{myId}")
	public JournalEntry getJournalEntrybyId(@PathVariable Long myId) {

		return journalEntries.get(myId);

	}

	@DeleteMapping("id/{myId}")
	public JournalEntry deljournalEntry(@PathVariable Long myId) {
		return journalEntries.remove(myId);
	}

	@PutMapping("id/{id}")
	public JournalEntry updatJournalEntryByEntryId(@PathVariable Long id, @RequestBody JournalEntry myEntry) {
		return journalEntries.put(id, myEntry);
	}
	
	

}
