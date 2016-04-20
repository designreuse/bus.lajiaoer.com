package com.demo.db.dao;

import com.demo.model.User;

public interface UserDao {

	static final String TABLE_USER_NAME = "demo_user";

	User get(Long id);

	User get(String email);

	User insert(User user);

	int updateByEmail(User user);

	int updateById(User user);
}
