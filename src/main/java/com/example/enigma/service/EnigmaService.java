package com.example.enigma.service;

import java.util.Optional;

import com.example.enigma.entry.EncryptionString;
import com.example.enigma.entry.SettingEnigma;

public interface EnigmaService {
	
	//データベース内の設定を全件取得する//
	Iterable<EncryptionString> showAll();
	
	//idをキーに設定を1件取得する
	Optional<SettingEnigma> choiceOneStting(Integer id);
	
	//idをキーに暗号化された文章を1件取得する
	Optional<EncryptionString> shoiceOneEncryption(Integer id);
	
	//暗号化された文章と設定を登録する
	void insert(SettingEnigma set, EncryptionString encry);

}
