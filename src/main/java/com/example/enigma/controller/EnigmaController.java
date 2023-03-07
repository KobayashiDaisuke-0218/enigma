package com.example.enigma.controller;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.example.enigma.entry.SettingEnigma;
import com.example.enigma.form.EncryptionStringForm;
import com.example.enigma.form.SettingEnigmaForm;
import com.example.enigma.service.EnigmaService;

@Controller
@RequestMapping("/enigma")
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
	
	
	@GetMapping
	public String dafault(Model model) {
		model.addAttribute(new SettingEnigma());
		return "enigma"; 
	}
	
	@GetMapping("/enigmaSetting")
	public String settingPage() {
		return "enigmaSetting";
	}
	
	
	
	@PostMapping("/insert")
	public String setupSetting(@Validated SettingEnigma set, @Validated SettingEnigmaForm setForm, BindingResult bindingResult, Model model, RedirectAttributesModelMap redirect) {
		
		SettingEnigma setting = new SettingEnigma();
		if(bindingResult.hasErrors()) {
			return settingPage();
		}
		//ルーターの値をセットする
		Integer[] setRouter = settingRouter(set, setForm);
		
		//ルーターの値に重複があればエラーメッセージを入力しもう一度設定画面へ進む
		if(setRouter == null) {
			redirect.addFlashAttribute("routerNullError", "ルーターの値が重複しています");
			return "enigmaSetting";
		} else {
			setting.setRouter(setRouter);
		}
		
		
		//開始位置を設定
		setting.setRouterStatrPoint(settingRouterStartPoint(set, setForm));
		
		
		//反転ルーターの設定
		if(setForm.getReverseRouter() != null) {
			setting.setReverseRouter(setForm.getReverseRouter());
		}
		
		
		if(checkPlugBoard(setForm)) {
			setting.setPlugBoardA(makePlugBoard(setForm));
			setting.setPlugBoardB(setForm.getPlugBoard());
			model.addAttribute("settingEnigma", setting);
		} else {
			setting.setPlugBoardA(set.getPlugBoardA());
			setting.setPlugBoardB(set.getPlugBoardB());
			model.addAttribute("settingEnigma", setting);
			redirect.addFlashAttribute("plugBoardError", "プラグボードの入力値にエラーがあります");
			return "enigmaSetting";
		}
		
		if(bindingResult.hasErrors()) {
			redirect.addFlashAttribute("setError", "入力値に問題があります");
			return "enigmaSetting";
		} else {
			model.addAttribute("settingEnigma", setting);
			redirect.addFlashAttribute("checkSetting", "エニグマの設定を変更しました");
			return "enigma";
		}
		
	}
	
	
	private Integer[] settingRouter(SettingEnigma nowSet, SettingEnigmaForm setForm) {		
		//セッションにセットするための配列
		Integer[] router = nowSet.getRouter();
		
		//ルーター1の入力確認
		if(setForm.getRouter1() != null) {
			router[0] = setForm.getRouter1();
		}
		
		//ルーター2の入力確認
		if(setForm.getRouter2() != null) {
			router[1] = setForm.getRouter2();
		}
		
		//ルーター3の入力確認
		if(setForm.getRouter3() != null) {
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
		
		if(setForm.getRouterStartPoint1() != null) {
			startPoint[0] = setForm.getRouterStartPoint1();
		}
		
		if(setForm.getRouterStartPoint2() != null) {
			startPoint[1] = setForm.getRouterStartPoint2();
		}
		
		if(setForm.getRouterStartPoint3() != null) {
			startPoint[2] = setForm.getRouterStartPoint3();
		}
		
		return startPoint;
	}
	
	//プラグボードの整合性を確認
	private Boolean checkPlugBoard(SettingEnigmaForm setForm) {
		HashSet<Integer> set = new HashSet<>();
		for(int i = 0; i < 25; i++) {
			set.add(setForm.getPlugBoard()[i]);
		}
		if(set.size() == 26) {
			return true;
		} else {
			return false;
		}
	}
	
	//formからプラグボードAを作成する
	private Integer[] makePlugBoard(SettingEnigmaForm setForm) {
		Integer[] returnPlug = new Integer[26];
		
		for(int i = 0; i < 26; i++) {
			returnPlug[setForm.getPlugBoard()[i]] = i;
		}
		return returnPlug;
	}
	
	
	
	
	
	
	

}
