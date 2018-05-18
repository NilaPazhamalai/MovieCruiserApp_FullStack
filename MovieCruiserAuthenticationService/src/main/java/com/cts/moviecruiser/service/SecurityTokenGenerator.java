package com.cts.moviecruiser.service;

import java.util.Map;

import com.cts.moviecruiser.domain.User;

public interface SecurityTokenGenerator {

	public Map<String, String> generateToken(User user);
}
