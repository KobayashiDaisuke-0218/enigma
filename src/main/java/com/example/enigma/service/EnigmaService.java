package com.example.enigma.service;

import java.util.Optional;

import com.example.enigma.entry.SettingEnigma;

public interface EnigmaService {
	
	//データベース内の設定を全件取得する//
	Iterable<SettingEnigma> showAll();
	
	//idをキーに設定を1件取得する
	Optional<SettingEnigma> choiceOneStting(Integer id);
	
	
	//暗号化された設定を登録する
	void insert(SettingEnigma set);

}
