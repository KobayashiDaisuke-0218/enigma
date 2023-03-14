package com.example.enigma;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.enigma.entry.SettingEnigma;
import com.example.enigma.repository.SettingEnigmaRepository;

@SpringBootApplication
public class EnigmaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnigmaApplication.class, args).getBean(EnigmaApplication.class).execute();
		
		
		
	}
	
	//設定情報を登録するリポジトリー
	@Autowired
	SettingEnigmaRepository settingRepository;
	

	
	
	private void execute() {
		
		setup();
		showAll();
		
		
	}
	
	private void setup() {
		
		
		System.out.println("登録完了");
	}
	
	private void showAll() {
		Iterable<SettingEnigma> settings = settingRepository.findAll();
		
		for(SettingEnigma set : settings) {
			Integer nowId = set.getId();
			System.out.print(settingRepository.findById(nowId));
		}
	}
	
	private void choiceOne(Integer id) {
		System.out.print(settingRepository.findById(id));
	}
	
	

}
