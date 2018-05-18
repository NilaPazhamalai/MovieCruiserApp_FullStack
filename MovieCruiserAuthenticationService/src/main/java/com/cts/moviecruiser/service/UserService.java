package com.cts.moviecruiser.service;


import com.cts.moviecruiser.domain.User;
import com.cts.moviecruiser.exception.UserAlreadyExistsException;
import com.cts.moviecruiser.exception.UserCredentialsValidationException;
import com.cts.moviecruiser.exception.UserNotFoundException;

public interface UserService {
	
	public boolean saveUser(User user) throws UserAlreadyExistsException;
	public User findByUserIdAndPassword(String userId, String password) throws UserNotFoundException;

}
