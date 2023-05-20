package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.entity.UserEntity;
import com.repository.UserRepository;

@RestController
public class SessionController {

	@Autowired
	UserRepository userRepo;
	
	//signup 
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody UserEntity user){
		
		userRepo.save(user);//insert 
		
		return ResponseEntity.ok(user);
	}
}
