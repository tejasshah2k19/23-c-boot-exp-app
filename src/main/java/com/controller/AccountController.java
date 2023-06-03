package com.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.AccountEntity;
import com.entity.UserEntity;
import com.repository.AccountRepository;
import com.repository.UserRepository;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

	@Autowired
	AccountRepository accountRepo;

	@Autowired
	UserRepository userRepo;

	@PostMapping
	public ResponseEntity<?> addAccount(@Valid @RequestBody AccountEntity account, BindingResult result) {

		System.out.println("--> " + result);
		System.out.println("--> " + result.getAllErrors());
		System.out.println("--> " + result.getErrorCount());
		System.out.println("--> " + result.getFieldError());
		System.out.println("--> " + result.getFieldError("name"));

		if (result.hasErrors()) {
			return ResponseEntity.ok(result.getAllErrors());
		} else {
			accountRepo.save(account);
			return ResponseEntity.ok(account);
		}
	}

}
