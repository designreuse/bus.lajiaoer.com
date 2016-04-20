package com.demo.service;

import com.demo.model.User;

public interface UserService {

	User get(Long id);

	User get(String email);

	User insert(User user);

	User signup(User userSignup);

	int updateByEmail(User user);

	int updateById(User user);
}
