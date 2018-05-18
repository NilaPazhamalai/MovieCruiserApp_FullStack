package com.cts.moviecruiser.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.moviecruiser.domain.User;
import com.cts.moviecruiser.exception.UserAlreadyExistsException;
import com.cts.moviecruiser.exception.UserNotFoundException;
import com.cts.moviecruiser.service.SecurityTokenGenerator;
import com.cts.moviecruiser.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService service;
	@Autowired
	private SecurityTokenGenerator generator;

	/*@Autowired
	public UserController(UserService service, SecurityTokenGenerator generator) {
		super();
		this.service = service;
		this.generator = generator;
	}*/

	@PostMapping("/register")
	public ResponseEntity<?> saveNewUser(@RequestBody User user) {
		ResponseEntity<?> response;
		try {
			boolean success = this.service.saveUser(user);
			if (success) {
				response = new ResponseEntity<User>(user, HttpStatus.CREATED);
			} else {
				response = new ResponseEntity<String>("{ \"message\" : \"Server Error\"}",
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (UserAlreadyExistsException exception) {
			response = new ResponseEntity<String>("{ \"message\" : \"" + exception.getMessage() + "\"}",
					HttpStatus.CONFLICT);
		}
		return response;
	}

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody User loginUser) {
		ResponseEntity<?> response;

		if (isBlank(loginUser.getUserId()) || isBlank(loginUser.getPassword())) {
			response = new ResponseEntity<String>("{ \"message\" : \"UserId and password cannot be empty\"}",
					HttpStatus.UNAUTHORIZED);
		} else {
			try {
				User user = this.service.findByUserIdAndPassword(loginUser.getUserId(), loginUser.getPassword());
				if (user == null) {
					return new ResponseEntity<String>("{ \"message\" : \"Incorrect Password\"}", HttpStatus.UNAUTHORIZED);
				}else{
					if(!loginUser.getPassword().equals(user.getPassword())){
						return new ResponseEntity<String>("{ \"message\" : \"Password does not match\"}", HttpStatus.UNAUTHORIZED);
					}else{
						// success
						Map<String , String> jwt = this.generator.generateToken(user);
						return new ResponseEntity<Map<String , String>>(jwt, HttpStatus.ACCEPTED);
					}
				}
			} catch (UserNotFoundException exception) {
				response = new ResponseEntity<String>("{ \"message\" : \"" + exception.getMessage() + "\"}",
						HttpStatus.CONFLICT);
			} 
		}
		return response;
	}

	boolean isBlank(String str) {
		if (str == null || str.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
}
