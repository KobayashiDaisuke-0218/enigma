package com.example.enigma.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.example.enigma.entry.SettingEnigma;
import com.example.enigma.form.EncryptionStringForm;
import com.example.enigma.form.SettingEnigmaForm;
import com.example.enigma.service.EnigmaService;

@Controller
@RequestMapping("/enigma")
public class EnigmaController {
	
	private final HashMap<Integer, String> alphabet = new HashMap<>(){{put(0, "A"); put(1, "B"); put(2, "C"); put(3, "D");  put(4, "E"); put(5, "F"); put(6, "G"); put(7, "H"); put(8, "I"); put(9, "J"); put(10, "K"); put(11, "L"); put(12, "M"); put(13, "N"); put(14, "O"); put(15, "P"); put(16, "Q"); put(17, "R"); put(18, "S"); put(19, "T"); put(20, "U"); put(21, "V"); put(22, "W"); put(23, "X"); put(24, "Y"); put(25, "Z");}};
	
	
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
	
	@ModelAttribute("alphabet")
	public String[] setAlphabet(SettingEnigma nowset) {
		String[] alpha = new String[26];
		
		for(int i = 0; i < 26; i++) {
			alpha[i] = this.alphabet.get(nowset.getPlugBoardE()[i]);
		}
		
		return alpha;
	}
	
	@GetMapping
	public String defaultPage() {
		return "enigma"; 
	}
	
	@GetMapping("/enigmaSetting")
	public String settingPage() {
		return "enigmaSetting";
	}
	
	
	
	
	
	
	@PostMapping("/insert")
	public String setupSetting(@ModelAttribute("settingEnigmaForm")SettingEnigmaForm settingEnigmaForm, @ModelAttribute("settingEnigma")SettingEnigma settingEnigma, Model model, RedirectAttributesModelMap redirect) {
		
		//に登録する
		SettingEnigma setting = new SettingEnigma();
		
		
		
		//ルーターの値をセットする
		Integer[] setRouter = settingRouter(settingEnigma, settingEnigmaForm);
		
		//ルーターの値に重複があればエラーメッセージを入力しもう一度設定画面へ進む
		if(setRouter == null) {
			model.addAttribute("routerSameError", "ルーターの値が重複しています");
			return settingPage();
			
		} else {
			setting.setRouter(setRouter);
		}
		
		
		//ルーターの開始位置の設定
		setting.setRouterStartPoint(setStartPoint(settingEnigmaForm, settingEnigma));
		
		
		
		//反転ルーターの設定
		if(settingEnigmaForm.getReverseRouter() != 0) {
			setting.setReverseRouterE(settingEnigmaForm.getReverseRouter() - 1);
		}
		
		
		//プラグボードの設定
		//ダミーの配列を作り現在の入力値を代入する
		Integer[] DammyPlugBoard = makePlugBoard(settingEnigma, settingEnigmaForm);
		
		
		//ダミーの配列が問題なければ設定する
		if(DammyPlugBoard != null) {
			setting.setPlugBoardE(DammyPlugBoard);
			System.out.println("o");
		} else {
			//配列がおかしければ設定画面へ戻る
			model.addAttribute("plugError", "プラグボードの入力値が不適切です");
			
			//プラグボード以前の問題ない設定のみセッションで保存
			model.addAttribute("settingEnigma", setting);
			System.out.println("s");
			//設定画面へ戻る
			return settingPage();
		}
		
		
		model.addAttribute("settingEnigma", setting);
		redirect.addFlashAttribute("checkSetting", "エニグマの設定を変更しました");
		System.out.println("ok");
		return defaultPage();
		
	}
	
	
	/*
	 * SettingEnigmaFormのなかのルーターの値をチェックし重複があればnull
	 * 重複がなければInteger[]の配列に入れて返す
	 * */
	private Integer[] settingRouter(SettingEnigma nowSet, SettingEnigmaForm setForm) {		
		//セッションにセットするための配列
		Integer[] router = nowSet.getRouter().clone();
		
		
		/*
		 * SettingEnigmaFormの入力値が0→何も入力されていないとき現在の設定値のまま引き継ぐ
		 * 
		 * */
		//ルーター1の入力確認
		if(setForm.getRouter1() != 0) {
			router[0] = setForm.getRouter1() - 1;
		}
		
		//ルーター2の入力確認
		if(setForm.getRouter2() != 0) {
			router[1] = setForm.getRouter2() - 1;
		}
		
		//ルーター3の入力確認
		if(setForm.getRouter3() != 0) {
			router[2] = setForm.getRouter3() - 1;
		}
		
		//ルーターの値が重複していないか確認
		if(router[0] != router[1] && router[1] != router[2] && router[0] != router[2]) {
			return router;
		} else {
			//重複していれば値をnullにする
			return null;
		}
		
	}
	
	
	
	//プラグボードの整合性を確認
	//文字の対称性が保証されていればtrueを間違っていればfalseを返す
	private Boolean checkPlugBoard(Integer[] plugBoard) {
		for(int i = 0; i < 26; i++) {
			if(plugBoard[plugBoard[i]] != i) {
				System.out.println(i + " " + plugBoard[i]);
				return false;
			}
		}
		return true;
	}
	
	//formからプラグボードを作成する
	private Integer[] makePlugBoard(SettingEnigma nowSet, SettingEnigmaForm setForm) {
		
		
		
		//戻り値用の配列に現在設定済みの配列をコピーする
		Integer[] returnPlug = nowSet.getPlugBoardE();
		
		for(int i = 0; i < 26; i++) {
			System.out.println(returnPlug[i]);
		}
		
		
		
		for(int i = 0; i < 26; i++) {
			if(setForm.getPlugBoard()[i] != 0) {
				returnPlug[i] = (setForm.getPlugBoard()[i] - 1);
			}
		}
		
		//プラグボードの整合性を確認し問題なければ配列を返す
		if(checkPlugBoard(returnPlug)) {
			return returnPlug;
		} else {
			//整合性に問題があればnullを返す
			return null;
		}
		
	}
	
	private Integer[] setStartPoint(SettingEnigmaForm setForm, SettingEnigma nowSet) {
		Integer[] returnPoint = new Integer[3];
		if(setForm.getRouterStartPoint1() == null) {
			returnPoint[0] = nowSet.getRouterStartPoint()[0];
		} else {
			returnPoint[0] = setForm.getRouterStartPoint1() - 1;
		}
		
		if(setForm.getRouterStartPoint2() == null) {
			returnPoint[1] = nowSet.getRouterStartPoint()[1];
		} else {
			returnPoint[1] = setForm.getRouterStartPoint2() - 1;
		}
		
		if(setForm.getRouterStartPoint3() == null) {
			returnPoint[2] = nowSet.getRouterStartPoint()[2];
		} else {
			returnPoint[2] = setForm.getRouterStartPoint3() - 1;
		}
		
		return returnPoint;
	}
	
	

	
	
	
	
	

}
