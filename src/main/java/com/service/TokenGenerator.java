package com.service;

import org.springframework.stereotype.Service;

@Service
public class TokenGenerator {

	public String generateToken(int length) {
		String data = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder token = new StringBuilder("");
		for (int i = 1; i <= length; i++) {
			int index = (int) (Math.random() * data.length()); // 0.958676849
			token.append(data.charAt(index));
		}
		return token.toString(); 
	}
}
