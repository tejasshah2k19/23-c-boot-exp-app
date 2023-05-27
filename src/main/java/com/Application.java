package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.google.gson.Gson;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	//Gson -> server -> singleton 
	@Bean
	public Gson getGson() {
		System.out.println("Gson Init....");
		return new Gson();
	}
	
}
