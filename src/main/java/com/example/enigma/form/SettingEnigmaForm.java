package com.example.enigma.form;

import java.util.HashMap;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SettingEnigmaForm {
	
	private final HashMap<Integer, String> alphabet = new HashMap<>(){{put(0, "A"); put(1, "B"); put(2, "C"); put(3, "D");  put(4, "E"); put(5, "F"); put(6, "G"); put(7, "H"); put(8, "I"); put(9, "J"); put(10, "K"); put(11, "L"); put(12, "M"); put(13, "N"); put(14, "O"); put(15, "P"); put(16, "Q"); put(17, "R"); put(18, "S"); put(19, "T"); put(20, "U"); put(21, "V"); put(22, "W"); put(23, "X"); put(24, "Y"); put(25, "Z");}};
	
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
