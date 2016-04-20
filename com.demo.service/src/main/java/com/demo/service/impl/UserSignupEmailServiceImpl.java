package com.demo.service.impl;

import java.util.List;

import com.demo.db.dao.UserSignupEmailDao;
import com.demo.db.dao.impl.UserSignupEmailDaoImpl;
import com.demo.model.UserSignupEmail;
import com.demo.service.UserSignupEmailService;
import com.demo.vo.UserVo;

public class UserSignupEmailServiceImpl implements UserSignupEmailService {

	private static UserSignupEmailServiceImpl instance = new UserSignupEmailServiceImpl();

	private UserSignupEmailDao signupEmailDao = UserSignupEmailDaoImpl.getInstance();

	private UserSignupEmailServiceImpl() {

	}

	public static UserSignupEmailServiceImpl getInstance() {
		return instance;
	}

	@Override
	public UserSignupEmail insert(UserSignupEmail signupEmail) {
		return signupEmailDao.insert(signupEmail);
	}

	@Override
	public UserSignupEmail get(Long id) {
		return signupEmailDao.get(id);
	}

	@Override
	public UserSignupEmail get(String token) {
		return signupEmailDao.get(token);
	}

	@Override
	public List<UserVo> querySchedulerByLimit(int limit) {
		return signupEmailDao.querySchedulerByLimit(limit);
	}

	@Override
	public int updateSended(Long id) {
		return signupEmailDao.updateSended(id);
	}

	@Override
	public int updateSendCount(Long id) {
		return signupEmailDao.updateSendCount(id);
	}

}
