package com.demo.service.impl;

import com.demo.core.dynamic.DynamicInvocation;
import com.demo.db.dao.UserDao;
import com.demo.db.dao.impl.UserDaoImpl;
import com.demo.model.User;
import com.demo.service.UserService;

public class UserServiceImpl implements UserService {

	private UserDao userDao = new DynamicInvocation(UserDaoImpl.class).getProxy();

	@Override
	public User get(Long id) {
		return userDao.get(id);
	}

	@Override
	public User get(String email) {
		return userDao.get(email);
	}

	@Override
	public User insert(User user) {
		return userDao.insert(user);
	}

	@Override
	public User signup(User userSignup) {
		return insert(userSignup);
	}

	@Override
	public int updateByEmail(User user) {
		return userDao.updateByEmail(user);
	}

	@Override
	public int updateById(User user) {
		return userDao.updateById(user);
	}

}
