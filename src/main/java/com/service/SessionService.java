package com.service;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dto.LoginDto;
import com.entity.UserEntity;
import com.enums.ExpAppError;
import com.exception.ExpAppException;
import com.repository.UserRepository;

@Service
public class SessionService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	TokenGenerator tokenGenerator;

	public UserEntity authenticateUser(LoginDto login) throws ExpAppException {

		Optional<UserEntity> userOptional = userRepo.findByEmail(login.getEmail());
		if (userOptional.isPresent() == false) {
			throw new ExpAppException(ExpAppError.INVALID_CREDENTIALS);
		} else {
			UserEntity loggedInUser = userOptional.get();
			if (loggedInUser.getPassword().equals(login.getPassword())) {
				// success

				loggedInUser.setToken(tokenGenerator.generateToken(16));
				userRepo.save(loggedInUser);

				return loggedInUser;
			} else {
				return null;
			}
		}

	}
}
