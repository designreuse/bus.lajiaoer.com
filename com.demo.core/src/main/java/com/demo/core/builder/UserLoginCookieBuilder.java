package com.demo.core.builder;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.model.User;
import com.demo.model.constants.CookieConstants;
import com.demo.utils.AesUtils;
import com.demo.utils.ConfigUtils;
import com.demo.utils.CookieUtils;

public class UserLoginCookieBuilder extends CookieBuilder {

	private static final Logger logger = LoggerFactory.getLogger(UserLoginCookieBuilder.class);
	private User user = null;

	private static final String SPLIT_EMAIL_PWD = "*****";

	public UserLoginCookieBuilder(User user, HttpServletResponse response) {
		this.user = user;
		this.response = response;
	}

	@Override
	void buildCookie() {
		if (null == user) {
			logger.error("cookie builder中的user为空");
			throw new RuntimeException("cookie builder中的user为空");
		}
		// 放入cookie
		// 全局cookie 变量
		final String strCookieDomain = ConfigUtils.getStringValue(CookieConstants.COOKIE_DOMAIN);
		final String strCookieLoginPath = ConfigUtils.getStringValue(CookieConstants.COOKIE_LOGIN_PATH);
		Integer iCookieLoginExpire = ConfigUtils.getIntegerValue(CookieConstants.COOKIE_LOGIN_EXPIRE);
		boolean isSecure = false;
		boolean isHttpOnly = true;

		// // 用户名+密码组合的md5值
		final String email = user.getEmail();
		// final String password = user.getPassword();
		final String strCryptedPassword = user.getPassword();// DigestUtils.md5Hex(password);
		final String strCookieLoginEmailPwd = ConfigUtils.getStringValue(CookieConstants.COOKIE_LOGIN_EMAILPWD);
		final String strEncryptEmailPwd = DigestUtils.md5Hex(email + SPLIT_EMAIL_PWD + strCryptedPassword);
		CookieUtils.addCookie(response, strCookieDomain, strCookieLoginPath, strCookieLoginEmailPwd, strEncryptEmailPwd, iCookieLoginExpire, isHttpOnly, isSecure);

		final String strEncryptLoginEmail = ConfigUtils.getStringValue(CookieConstants.LOGIN_ENCRYPT_CODE);
		final String strCookieLoginEmail = ConfigUtils.getStringValue(CookieConstants.COOKIE_LOGIN_EMAIL);
		final String strEncryptEmail = AesUtils.getInstance(strEncryptLoginEmail).encryptAES(email);
		CookieUtils.addCookie(response, strCookieDomain, strCookieLoginPath, strCookieLoginEmail, strEncryptEmail, iCookieLoginExpire, isHttpOnly, isSecure);
	}

}
