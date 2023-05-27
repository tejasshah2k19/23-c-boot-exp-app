package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.RoleEntity;
import com.repository.RoleRepository;

@RestController
@RequestMapping("/api/v1/role")
public class RoleCotnroller {

	@Autowired
	RoleRepository roleRepo;

	@PostMapping("/save")
	public ResponseEntity<?> addRole(@RequestBody RoleEntity role) {
		System.out.println("save Role");
		roleRepo.save(role);
		return ResponseEntity.ok(role);
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllRoles() {
		return ResponseEntity.ok(roleRepo.findAll());
	}
}
