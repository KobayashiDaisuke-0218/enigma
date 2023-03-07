package com.example.enigma.form;

import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EncryptionStringForm {
	
	@Id
	private Integer id;
	
	@NotNull
	private String sentence;
	

}
