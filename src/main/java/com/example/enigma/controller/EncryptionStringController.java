package com.example.enigma.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.enigma.entry.SettingEnigma;
import com.example.enigma.form.EncryptionStringForm;

@Controller
@SessionAttributes("brforeEncryption")
@RequestMapping("encryption")
public class EncryptionStringController {
	
	
	@ModelAttribute("encriptionStringForm")
	public EncryptionStringForm getString() {
		return new EncryptionStringForm();
	}
	
	
	@GetMapping
	public String encryptionString(EncryptionStringForm encryptStr, Model model) {
		
		Scanner sc = new Scanner(encryptStr.getSentence());
		
		ArrayList<String> getLine = new ArrayList<>();
		
		try {
			while(sc.hasNext()) {
				String line = (sc.nextLine());
				
				if(checkString(line)) {
					getLine.add(line);
				} else {
					/*
					 * 入力文字列に小文字のアルファベット以外があった場合
					 * エラーメッセージをつけて元の画面へ戻る
					 * */
					model.addAttribute("scannerError", "小文字のアルファベットの未入力してください");
					return "enigma";
				}
			}
			
		//入力値にエラーが出れば対応	
		} catch(InputMismatchException e) {
			model.addAttribute("scannerError", "小文字のアルファベットの未入力してください");
			return "enigmaSetting";
		}
		
		
		/*エラーが発生しなかった場合
		 * 暗号化を開始する
		 * */
		
		
	}
	
	//入力値が小文字のアルファベットのみか確認
	private boolean checkString(String line) {
		if(line.matches("[a-z]*")) {
			return true;
		} else {
			return false;
		}
	}
	
	
	private ArrayList<String> encrypt(ArrayList<String> getLine, SettingEnigma nowSet) {
		
		//文字数をカウントする
		int count = 0;
		
		//文字列の数ぶんループさせる
		for(int i = 0; i < getLine.size(); i++) {
			StringBuilder bd = new StringBuilder();
			//i番目の文字列の文字数分ループさせる
			for(int j = 0; j < getLine.get(i).length(); j++) {
				
				Integer alphabetNumber = Arrays.asList(SettingData.ROUTER_SETTING)getLine.get(i).substring(j, j + 1)
				
			}
		}
		
	}
	
	

}
