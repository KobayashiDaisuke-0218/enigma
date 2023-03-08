package com.example.enigma.form;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SettingEnigmaForm {
	
	@Id
	private Integer id;
	
	private Integer router1;
	
	private Integer router2;
	
	private Integer router3;
	
	private Integer routerStartPoint1;
	private Integer routerStartPoint2;
	private Integer routerStartPoint3;
	
	private Integer reverseRouter;
	
	
	
	private Integer[] plugBoard = new Integer[26];
	
	
}
