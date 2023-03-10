package com.example.enigma.settingData;

public class SettingData {
	
	
	/*
	 * 各ルーター、リフレクター、アルファベットのデータを格納するクラス
	 * */
	
	 private final String[] ALPHABET = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
	 
	 private final Integer[][] ROUTER_SETTING = {
			 //routerA
			 {6, 7, 24, 20, 21, 16, 23, 5, 22, 19, 4, 0, 14, 18, 25, 17, 15, 2, 12, 8, 3, 11, 9, 13, 10, 1},
			 //routerB
			 {20, 7, 21, 0, 9, 18, 11, 16, 10, 4, 19, 15, 13, 2, 5, 12, 23, 25, 6, 24, 1, 8, 14, 22, 17, 3},
			 //routerC
			 {5, 16, 21, 22, 11, 8, 23, 4, 15, 13, 1, 14, 20, 3, 6, 24, 19, 0, 17, 25, 2, 12, 10, 7, 18, 9},
			 //routerD
			 {18, 6, 3, 12, 15, 1, 17, 23, 0, 4, 20, 9, 8, 13, 16, 14, 11, 5, 19, 2, 25, 21, 24, 22, 7, 10},
			 //routerE
			 {14, 25, 15, 17, 2, 1, 5, 16, 9, 4, 19, 6, 10, 12, 23, 18, 8, 22, 13, 20, 3, 24, 21, 7, 11, 0}
			 
			 
	 };
	 
	 private final Integer[][] REVERSE_ROUTER = {
			 //reverseRouterF
			 {19, 22, 20, 15, 16, 9, 21, 12, 14, 5, 24, 18, 7, 17, 8, 3, 4, 13, 11, 0, 2, 6, 1, 25, 10, 23},
			 //reverseRouterG
			 {2, 16, 0, 9, 7, 17, 23, 4, 21, 3, 12, 20, 10, 22, 25, 19, 1, 5, 24, 15, 11, 8, 13, 6, 18, 14}
	 };
	 
	 public String[] getAlphabet() {
		 return this.ALPHABET;
	 }
	 
	 public Integer[][] getRouterSetting() {
		 return this.ROUTER_SETTING;
	 }
	 
	 public Integer[][] getReverseRouter() {
		 return this.REVERSE_ROUTER;
	 }
	 

}
