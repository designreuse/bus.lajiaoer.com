package com.demo.db.dao;

import java.util.List;

import com.demo.model.UserSignupEmail;
import com.demo.vo.UserVo;

public interface UserSignupEmailDao {

	UserSignupEmail insert(UserSignupEmail signupEmail);

	UserSignupEmail get(Long id);

	UserSignupEmail get(String token);

	int updateSended(Long id);

	int updateSendCount(Long id);

	List<UserVo> querySchedulerByLimit(int limit);
}
