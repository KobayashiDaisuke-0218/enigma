package com.example.enigma.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.enigma.entry.SettingEnigma;
import com.example.enigma.repository.SettingEnigmaRepository;


@Service
public class EnigmaServiceImpl implements EnigmaService {
	
	@Autowired
	SettingEnigmaRepository settingRepository;
	
	@Override
	public Iterable<SettingEnigma> showAll() {
		
		return settingRepository.findAll();
		
	}

	@Override
	public Optional<SettingEnigma> choiceOneStting(Integer id) {
		return settingRepository.findById(id);
	}

	@Override
	public void insert(SettingEnigma set) {
		settingRepository.save(set);
	}

	

}
