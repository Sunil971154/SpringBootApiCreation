package com.RestFulAPI.entity;




import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

//yah ek Pojo class  Hai

@Document(collection = "jetable")
@Data
@NoArgsConstructor
public class JournalEntry {
	@Id
	private ObjectId id;

	private String title;
	private String content;
	private LocalDateTime localDateTime;
	
	

	

	

}
