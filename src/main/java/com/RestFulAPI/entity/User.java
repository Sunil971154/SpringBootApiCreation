package com.RestFulAPI.entity;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@Document(collection = "userTable")
public class User {
	@Id
	private ObjectId id; // isko string bhi le sakte hai spring data mongo db hamare liye convert kardega

	@NonNull // e lombok ki anotation hai
	@Indexed(unique = true)
	private String userName;
	@NonNull
	private String password;

	@DBRef // je ka referance create kar rahe user k ander
	private List<JournalEntry> journalEntries = new ArrayList(); // jaise hi ek user initioalise hoga user null nahi
																	// hoga khali hoga
	@NonNull
	private List<String> roles;

}
