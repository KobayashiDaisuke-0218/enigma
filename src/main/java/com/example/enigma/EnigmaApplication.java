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
	}
	
	private void setup() {
		SettingEnigma setting1 = new SettingEnigma();
		
		
		setting1 = settingRepository.save(setting1);
		
		
		
		SettingEnigma setting2 = new SettingEnigma(null, new Integer[] {2, 4, 1}, new Integer[] {0, 13, 22}, 2, new Integer[] {0, 10, 8, 22, 16, 7, 4, 14, 19, 23, 24, 2, 11, 18, 1, 5, 12, 17, 15, 13, 20, 6, 3, 21, 9});
		
		setting2 = settingRepository.save(setting2);
		
		
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
