package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.AccountEntity;
import com.entity.RoleEntity;
import com.entity.UserEntity;
import com.repository.AccountRepository;
import com.repository.RoleRepository;
import com.repository.UserRepository;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;

@RestController
@RequestMapping("/api/v1/users/")
public class UserController {

	@Autowired
	UserRepository userRepo;

	@Autowired
	RoleRepository roleRepo;

	@Autowired
	AccountRepository accountRepo;

	@GetMapping("/all")
	public ResponseEntity<?> getAllUsers(HttpServletRequest request) {

		String token = request.getHeader("token");
		System.out.println(token);

		Optional<UserEntity> optionalUserEntity = userRepo.findByToken(token);
		if (optionalUserEntity.isPresent() == false) {
			HashMap<String, Object> data = new HashMap<>();
			data.put("token", token);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(data);
		} else {

			List<UserEntity> users = userRepo.findAll();
			return ResponseEntity.ok(users);
		}
	}

	// get user by id
	// select * from users where userId = :id
	@GetMapping("/byid/{userId}")
	public ResponseEntity<?> getUserById(@PathVariable("userId") Integer userId) {

		Optional<UserEntity> userOptional = userRepo.findById(userId);

		if (userOptional.isPresent() == false) {
			// invalid id
			Map<String, Object> resp = new HashMap<String, Object>();
			resp.put("data", userId);
			resp.put("msg", "Invalid UserId");
			return ResponseEntity.accepted().body(resp);
		} else {
			return ResponseEntity.ok(userOptional.get());
		}
	}

	// get user by email
	// select * from users where email = :id
	@GetMapping("{email}")
	public ResponseEntity<?> getUserByEmail(@PathVariable("email") String email) {

		Optional<UserEntity> userOptional = userRepo.findByEmail(email);

		if (userOptional.isPresent() == false) {
			// invalid email
			Map<String, Object> resp = new HashMap<String, Object>();
			resp.put("data", email);
			resp.put("msg", "Email Not Present");
			return ResponseEntity.accepted().body(resp);
		} else {
			return ResponseEntity.ok(userOptional.get());
		}
	}

	// delete from users where userId = :id

	@DeleteMapping("{userId}")
	public ResponseEntity<?> deleteUserById(@PathVariable("userId") Integer userId) {
		Map<String, Object> resp = new HashMap<>();

		try {
			userRepo.deleteById(userId);
			resp.put("msg", "User Deleted");
			resp.put("userId", userId);
			return ResponseEntity.ok(resp);
		} catch (Exception e) {
			resp.put("msg", "SMW");
			resp.put("userId", userId);
			resp.put("errorMsg", e.getMessage());
			return ResponseEntity.ok(resp);

		}
	}

	//
	@PutMapping
	public ResponseEntity<?> updateUser(@RequestBody UserEntity user) {
		userRepo.save(user); // insert update -> by userid -> pk if id is present then update else insert ->
								// upsert
		return ResponseEntity.ok(user);
	}

	@PostMapping("/add")
	public ResponseEntity<?> signup(@RequestBody UserEntity user) {
		RoleEntity role = roleRepo.findById(user.getRole().getRoleId()).get();
		user.setRole(role);
		userRepo.save(user);// insert

		return ResponseEntity.ok(user);
	}

	@GetMapping("/myaccounts/{userId}")
	public ResponseEntity<?> myAccounts(@PathVariable("userId") Integer userId) {
		UserEntity user = userRepo.findById(userId).get();
		return ResponseEntity.ok(user.getAccounts());
	}

	@GetMapping("/myaccounts2/{userId}")
	public ResponseEntity<?> myAccounts2(@PathVariable("userId") Integer userId) {

		List<AccountEntity> accounts = accountRepo.findByUser(userId);
		return ResponseEntity.ok(accounts);
	}

}
