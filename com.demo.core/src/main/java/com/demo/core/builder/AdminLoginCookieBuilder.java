package com.demo.core.builder;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.model.admin.AdminUser;
import com.demo.utils.CookieUtils;

public class AdminLoginCookieBuilder extends CookieBuilder {
	private static final Logger logger = LoggerFactory.getLogger(AdminLoginCookieBuilder.class);
	private AdminUser user = null;

	public AdminLoginCookieBuilder(AdminUser user, HttpServletResponse response) {
		this.user = user;
		this.response = response;
	}

	@Override
	void buildCookie() {
		logger.debug("加入admin cookie开始");
		CookieUtils.addCookie(response, "admin.lajiaoer.com", "/", "ad_name", user.getName(), -1, false, false);
		CookieUtils.addCookie(response, "admin.lajiaoer.com", "/", "ad_token", (DigestUtils.md5Hex(user.getName() + ".lajiaoer." + user.getPassword())), -1, true, false);
		logger.debug("加入admin cookie成功");
	}

}
