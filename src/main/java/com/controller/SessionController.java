package com.controller;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.LoginDto;
import com.entity.RoleEntity;
import com.entity.UserEntity;
import com.exception.ExpAppException;
import com.repository.RoleRepository;
import com.repository.UserRepository;
import com.service.SessionService;

@RestController
@RequestMapping("/api/v1/public/")
public class SessionController {

	@Autowired
	UserRepository userRepo;

	@Autowired
	SessionService sessionService;

	@Autowired
	RoleRepository roleRepo;

	// signup
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody UserEntity user) {
		RoleEntity role = roleRepo.findById(1).get();
		user.setRole(role);
		userRepo.save(user);// insert

		return ResponseEntity.ok(user);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDto login) {

		try {
			UserEntity loggedInUser = sessionService.authenticateUser(login);

			HashMap<String, Object> data = new HashMap<>();
			data.put("data", loggedInUser);
			data.put("msg", "Login success");
			return ResponseEntity.status(HttpStatus.OK).body(data);

		} catch (ExpAppException e) {
			return ResponseEntity.ok(e);
		}

	}
}
