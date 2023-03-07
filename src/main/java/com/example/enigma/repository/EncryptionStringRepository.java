package com.example.enigma.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.enigma.entry.EncryptionString;

public interface EncryptionStringRepository extends CrudRepository<EncryptionString, Integer> {

}
