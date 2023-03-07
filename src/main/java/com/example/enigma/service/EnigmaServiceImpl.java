package com.example.enigma.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.enigma.entry.EncryptionString;
import com.example.enigma.entry.SettingEnigma;
import com.example.enigma.repository.EncryptionStringRepository;
import com.example.enigma.repository.SettingEnigmaRepository;


@Service
public class EnigmaServiceImpl implements EnigmaService {
	
	@Autowired
	EncryptionStringRepository encryptionRepository;
	
	@Autowired
	SettingEnigmaRepository settingRepository;
	
	@Override
	public Iterable<SettingEnigma> showAllSetting() {
		
		return settingRepository.findAll();
		
	}

	@Override
	public Optional<SettingEnigma> choiceOneStting(Integer id) {
		return settingRepository.findById(id);
	}

	@Override
	public Optional<EncryptionString> shoiceOneEncryption(Integer id) {
		return encryptionRepository.findById(id);
	}

	@Override
	public void insert(SettingEnigma set, EncryptionString encry) {
		encryptionRepository.save(encry);
		settingRepository.save(set);
	}

	

}
