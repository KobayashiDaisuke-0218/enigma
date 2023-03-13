package com.example.enigma.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.enigma.entry.SettingEnigma;
import com.example.enigma.form.EncryptionStringForm;
import com.example.enigma.service.EnigmaServiceImpl;
import com.example.enigma.settingData.SettingData;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/encryption")
public class EncryptionStringController {
	
	@Autowired
	EnigmaServiceImpl service;
	
	
	@ModelAttribute("encriptionStringForm")
	public EncryptionStringForm getString() {
		return new EncryptionStringForm();
	}
	
	
	@PostMapping
	public String encryptionString(EncryptionStringForm encryptStr, Model model, HttpSession session) {
		
		Scanner sc = new Scanner(encryptStr.getSentence());
		
		ArrayList<String> getLine = new ArrayList<>();
		
		SettingEnigma settingEnigma = (SettingEnigma)session.getAttribute("settingEnigma");
		
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
		
		//入力がない場合元の画面に戻す
		if(getLine.size() == 0) {
			return "enigma";
		}
		
		
		/*エラーが発生しなかった場合
		 * 暗号化を開始する
		 * */
		
		ArrayList<String> encryptSentence = encrypt(getLine, settingEnigma);
		
		StringBuilder bd = new StringBuilder();
		
		for(int i = 0; i < encryptSentence.size(); i++) {
			//暗号化した文字列と改行文字を加える
			bd.append(encryptSentence.get(i) + "\n");
		}
		
		String encrypt = bd.toString();

		
		//データベースに格納する
		service.insert(settingEnigma);
		
		//文字列をモデルに格納する
		model.addAttribute("encryptSentence", encrypt);
		return "enigma";
	}
	
	
	//入力値が小文字のアルファベットのみか確認
	private boolean checkString(String line) {
		if(line.matches("[a-z\s]*")) {
			return true;
		} else {
			return false;
		}
	}
	
	
	private ArrayList<String> encrypt(ArrayList<String> getLine, @SessionAttribute("settingEnimga")SettingEnigma nowSet) {
		
		//文字数をカウントする
		int count = 0;
		
		SettingData st = new SettingData();
		
		ArrayList<String> returnSentence = new ArrayList<>();
		
		//文字列の数ぶんループさせる
		for(int i = 0; i < getLine.size(); i++) {
			StringBuilder bd = new StringBuilder();
			//i番目の文字列の文字数分ループさせる
			for(int j = 0; j < getLine.get(i).length(); j++) {
				
				//i番目の文字列のj番目の文字
				String nowString = getLine.get(i).substring(j, j + 1);
				
				//文字が空欄だった時
				if(nowString.equals(" ")) {
					//空欄をそのまま代入
					bd.append(" ");
				
				//空欄ではない→アルファベットだった時
				} else {
					
					//i番目の文字列のj番目の文字をアルファベットを0～25の番号に変換
					Integer alphabetNumber = Arrays.asList(st.getAlphabet()).indexOf(nowString);
					//プラグボードに通して変換する
					alphabetNumber = nowSet.getPlugBoardE()[alphabetNumber];
					
					//1番目のルーターに通して変換
					alphabetNumber = st.getRouterSetting()[nowSet.getRouter()[0]][(alphabetNumber + nowSet.getRouterStartPoint()[0]+ (count % 26)) % 26] ;
					System.out.println(alphabetNumber);
					//2番目のルーターに通して変換
					alphabetNumber = st.getRouterSetting()[nowSet.getRouter()[1]][(alphabetNumber + nowSet.getRouterStartPoint()[1] + ((count / 26) % 26)) % 26];
					System.out.println(alphabetNumber);
					//3番目のルーターに通して変換
					alphabetNumber = st.getRouterSetting()[nowSet.getRouter()[2]][(alphabetNumber + nowSet.getRouterStartPoint()[2] + (count / (26 * 26))) % 26];
					System.out.println(alphabetNumber);
					//反転ルーターに通す
					alphabetNumber = st.getReverseRouter()[nowSet.getReverseRouterE()][alphabetNumber];
				
					
					
					//逆順で処理していく
					//3番目のルーターに通す
					alphabetNumber = (Arrays.asList(st.getRouterSetting()[nowSet.getRouter()[2]]).indexOf(alphabetNumber) + 26 - nowSet.getRouterStartPoint()[2] + (26 - count / (26 * 26))) % 26;
					//2番目のルーターに通す
					alphabetNumber = (Arrays.asList(st.getRouterSetting()[nowSet.getRouter()[1]]).indexOf(alphabetNumber) + 26 - nowSet.getRouterStartPoint()[1] + (26 - (count / 26) % 26)) % 26;
					//1番目のルーターに通す
					
					alphabetNumber = (Arrays.asList(st.getRouterSetting()[nowSet.getRouter()[0]]).indexOf(alphabetNumber) + ((26 - nowSet.getRouterStartPoint()[0] + 26 - count % 26) % 26)) % 26;
					
					//プラグボードに通して変換する
					alphabetNumber = nowSet.getPlugBoardE()[alphabetNumber];
					bd.append(st.getAlphabet()[alphabetNumber]);
					
					count++;
					if(count >= 26 * 26 * 26) {
						count = 0;
					}
				}
			}
			//リストに暗号化した文章を格納する
			returnSentence.add(bd.toString());
			
		}
		return returnSentence;
	}
	
	
	
	

}
