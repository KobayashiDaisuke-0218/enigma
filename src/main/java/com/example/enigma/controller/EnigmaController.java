package com.example.enigma.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.example.enigma.entry.SettingEnigma;
import com.example.enigma.form.EncryptionStringForm;
import com.example.enigma.form.SettingEnigmaForm;
import com.example.enigma.service.EnigmaService;

@Controller
@SessionAttributes("settingEnigma")
public class EnigmaController {
	
	@Autowired
	EnigmaService sercvice;
	

	
	
	//"EncryptionStringForm"でmodelの中に格納
	@ModelAttribute("encryptionEnigmaForm")
	public EncryptionStringForm createEncryption() {
		return new EncryptionStringForm();
	}
	
	
	//"settingEnigmaForm"でmodelの中に格納
	@ModelAttribute("settingEnigmaForm")
	public SettingEnigmaForm createSetting() {
		return new SettingEnigmaForm();
	}
	
	@ModelAttribute("settingEnigma")
	public SettingEnigma set() {
		return new SettingEnigma();
	}
	
	
	@GetMapping("/enigma")
	public String dafault(Model model) {
		model.addAttribute(new SettingEnigma());
		return "enigma"; 
	}
	
	@GetMapping("/enigmaSetting")
	public String settingPage() {
		return "enigmaSetting";
	}
	
	
	
	@PostMapping("/insert")
	public String setupSetting(@Validated SettingEnigma settingEnigma, @Validated SettingEnigmaForm setForm, BindingResult bindingResult, Model model, RedirectAttributesModelMap redirect) {
		
		SettingEnigma setting = new SettingEnigma();
		if(bindingResult.hasErrors()) {
			System.out.println("n");
			return settingPage();
		}
		//ルーターの値をセットする
		Integer[] setRouter = settingRouter(settingEnigma, setForm);
		
		//ルーターの値に重複があればエラーメッセージを入力しもう一度設定画面へ進む
		if(setRouter == null) {
			redirect.addFlashAttribute("routerNullError", "ルーターの値が重複しています");
			System.out.println("重複");
			return "enigmaSetting";
			
		} else {
			setting.setRouter(setRouter);
		}
		
		
		//開始位置を設定
		setting.setRouterStatrPoint(settingRouterStartPoint(settingEnigma, setForm));
		
		
		//反転ルーターの設定
		if(setForm.getReverseRouter() != 0) {
			setting.setReverseRouter(setForm.getReverseRouter());
		}
		
		
		Integer[] DammyPlugBoardB = makePlugBoard(settingEnigma, setForm);
		
		Integer[] DammyPlugBoardA = null;
		
		if(checkPlugBoard(DammyPlugBoardB)) {
			DammyPlugBoardA = makePlugBoard(DammyPlugBoardB);
		}
		
		if(DammyPlugBoardA != null) {
			settingEnigma.setPlugBoardA(DammyPlugBoardA);
			settingEnigma.setPlugBoardB(DammyPlugBoardB);
		}
		
		if(bindingResult.hasErrors()) {
			redirect.addFlashAttribute("setError", "入力値に問題があります");
			System.out.println("入力");
			return "enigmaSetting";
		} else {
			model.addAttribute("settingEnigma", setting);
			redirect.addFlashAttribute("checkSetting", "エニグマの設定を変更しました");
			return "enigma";
		}
		
	}
	
	
	/*
	 * SettingEnigmaFormのなかのルーターの値をチェックし重複があればnull
	 * 重複がなければInteger[]の配列に入れて返す
	 * */
	private Integer[] settingRouter(SettingEnigma nowSet, SettingEnigmaForm setForm) {		
		//セッションにセットするための配列
		Integer[] router = nowSet.getRouter();
		
		
		/*
		 * SettingEnigmaFormの入力値が0→何も入力されていないとき現在の設定値のまま引き継ぐ
		 * 
		 * */
		//ルーター1の入力確認
		if(setForm.getRouter1() != 0) {
			router[0] = setForm.getRouter1();
		}
		
		//ルーター2の入力確認
		if(setForm.getRouter2() != 0) {
			router[1] = setForm.getRouter2();
		}
		
		//ルーター3の入力確認
		if(setForm.getRouter3() != 0) {
			router[2] = setForm.getRouter3();
		}
		
		//ルーターの値が重複していないか確認
		if(router[0] != router[1] && router[1] != router[2] && router[0] != router[2]) {
			return router;
		} else {
			//重複していれば値をnullにする
			return null;
		}
		
	}
	
	private Integer[] settingRouterStartPoint(SettingEnigma nowSet, SettingEnigmaForm setForm) {
		Integer[] startPoint = nowSet.getRouterStatrPoint();
		
		if(setForm.getRouterStartPoint1() != 0) {
			startPoint[0] = setForm.getRouterStartPoint1();
		}
		
		if(setForm.getRouterStartPoint2() != 0) {
			startPoint[1] = setForm.getRouterStartPoint2();
		}
		
		if(setForm.getRouterStartPoint3() != 0) {
			startPoint[2] = setForm.getRouterStartPoint3();
		}
		
		return startPoint;
	}
	
	//プラグボードの整合性を確認
	private Boolean checkPlugBoard(Integer[] plugBoard) {
		
	}
	
	//formからプラグボードBを作成する
	private Integer[] makePlugBoard(SettingEnigma nowSet, SettingEnigmaForm setForm) {
		Integer[] returnPlug = nowSet.getPlugBoardB();
		for(int i = 0; i < 26; i++) {
			if(setForm.getPlugBoard()[i] != 0) {
				returnPlug[i] = setForm.getPlugBoard()[i];
			} else {
				returnPlug[i] = nowSet.getPlugBoardB()[i];
			}
		}
		return returnPlug;
	}
	
	
	//プラグボードBを引数にプラグボードAを作成する
	private Integer[] makePlugBoard(Integer[] plugBoard) {
		Integer[] damPlugBoard = new Integer[26];
		
		Arrays.fill(damPlugBoard, 0);
		
		for(int i = 0; i < 26; i++) {
			if(damPlugBoard[plugBoard[i] - 1] == 0) {
				damPlugBoard[plugBoard[i] - 1] = i + 1;
			} else {
				return null;
			}
		}
		return damPlugBoard;
	}
	
	
	
	
	

}
