package com.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.entity.UserEntity;
import com.google.gson.Gson;
import com.repository.UserRepository;

@Component
public class TokenFilter implements Filter {

	@Autowired
	UserRepository userRepo;

	@Autowired
	Gson gson;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;

		System.out.println("InSide TokenFilter....");
		if (req.getRequestURL().toString().contains("/public/")) {
			chain.doFilter(request, response);// go ahead
		} else {
			String token = req.getHeader("token");
			System.out.println(token);
			if (token == null) {
				HashMap<String, Object> data = new HashMap<>();
				data.put("token", token);
				data.put("msg", "Invalid Token");

				PrintWriter out = response.getWriter();
				response.setContentType("application/json");
				// data -> json

//				out.print(data);

				// out.print("{\"token\":\""+token+"\"}");
				String json = gson.toJson(data);
				out.print(json);
				 	
				//return;
			} else {
				Optional<UserEntity> optionalUserEntity = userRepo.findByToken(token);
				if (optionalUserEntity.isPresent() == false) {
					HashMap<String, Object> data = new HashMap<>();
					data.put("token", token);
					data.put("msg", "Invalid Token");

					PrintWriter out = response.getWriter();
					response.setContentType("application/json");
					// data -> json

//				out.print(data);

					// out.print("{\"token\":\""+token+"\"}");
					String json = gson.toJson(data);

					out.print(json);
				} else {
					chain.doFilter(request, response);// go ahead
				}
			}
		}
	}
}
