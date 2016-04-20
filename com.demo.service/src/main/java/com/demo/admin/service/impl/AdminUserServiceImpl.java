package com.demo.admin.service.impl;

import org.apache.commons.codec.digest.DigestUtils;

import com.demo.admin.dao.AdminUserDao;
import com.demo.admin.dao.impl.AdminUserDaoImpl;
import com.demo.admin.service.AdminUserService;
import com.demo.core.dynamic.DynamicInvocation;
import com.demo.model.admin.AdminUser;
import com.demo.utils.StringUtils;

public class AdminUserServiceImpl implements AdminUserService {

	private AdminUserDao userDao = new DynamicInvocation(AdminUserDaoImpl.class).getProxy();

	@Override
	public boolean login(String name, String password) {
		AdminUser user = userDao.getAdminUser(name);
		if (null == user) {// 用户不存在
			return false;
		}

		String strPwd = user.getPassword();
		if (!StringUtils.equalsIgnoreCase(strPwd, DigestUtils.md5Hex(password))) {// 密码不正确
			return false;
		}

		return true;
	}

	@Override
	public AdminUser getAdminUser(String name) {
		return userDao.getAdminUser(name);
	}

}
