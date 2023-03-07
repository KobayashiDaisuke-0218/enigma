package com.example.enigma.entry;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//暗号化された文章を保持するクラス

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EncryptionString")
public class EncryptionString {
	
	@Id
	//@Column(value = "id")
	private Integer id;
	
	//暗号化された文章
	//@Column(value = "sentence")
	private String sentence;
}
