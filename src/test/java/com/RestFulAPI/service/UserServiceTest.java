package com.RestFulAPI.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.RestFulAPI.entity.JournalEntry;
import com.RestFulAPI.repo.UserRepo;



@SpringBootTest
public class UserServiceTest {

	@Autowired
	private  UserRepo userRepo;
	
	//@Autowired
	//private JournalEntry journalEntry;
	
	@Test
	public void findByUserName() {
		//userRepo.findByUserName("ram");
		//assertEquals(4, 2+2 );
		assertNotNull(userRepo.findByUserName("ram") );
		
		
	}
	
}
