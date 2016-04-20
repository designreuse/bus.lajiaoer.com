package com.demo.core.builder;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.model.Driver;
import com.demo.model.constants.CookieConstants;
import com.demo.utils.AesUtils;
import com.demo.utils.ConfigUtils;
import com.demo.utils.CookieUtils;

public class DriverLoginCookieBuilder extends CookieBuilder {

	private static final Logger logger = LoggerFactory.getLogger(DriverLoginCookieBuilder.class);
	private Driver driver = null;

	private static final String SPLIT_CELL_PWD = "*****";

	public DriverLoginCookieBuilder(Driver driver, HttpServletResponse response) {
		this.driver = driver;
		this.response = response;
	}

	@Override
	void buildCookie() {
		if (null == driver) {
			logger.error("cookie builder中的driver为空");
			throw new RuntimeException("cookie builder中的user为空");
		}

		try {
			// 放入cookie
			// 全局cookie 变量
			final String strCookieDomain = ConfigUtils.getStringValue(CookieConstants.COOKIE_DOMAIN);
			final String strCookieLoginPath = ConfigUtils.getStringValue(CookieConstants.COOKIE_LOGIN_PATH);
			Integer iCookieLoginExpire = ConfigUtils.getIntegerValue(CookieConstants.COOKIE_LOGIN_EXPIRE);
			boolean isSecure = false;
			boolean isHttpOnly = true;

			// // 用户名+密码组合的md5值
			final String cell = driver.getCell();
			// final String password = user.getPassword();
			final String strCryptedPassword = driver.getPassword();// DigestUtils.md5Hex(password);
			final String strCookieLoginCellPwd = ConfigUtils.getStringValue(CookieConstants.COOKIE_LOGIN_CELLPWD);
			final String strEncryptCellPwd = DigestUtils.md5Hex(cell + SPLIT_CELL_PWD + strCryptedPassword);
			CookieUtils.addCookie(response, strCookieDomain, strCookieLoginPath, strCookieLoginCellPwd, strEncryptCellPwd, iCookieLoginExpire, isHttpOnly, isSecure);
			logger.debug("保存cookie,name={},value={}", strCookieLoginCellPwd, strCookieLoginCellPwd);

			final String strEncryptLoginCode = ConfigUtils.getStringValue(CookieConstants.LOGIN_ENCRYPT_CODE);
			final String strCookieLoginCell = ConfigUtils.getStringValue(CookieConstants.COOKIE_LOGIN_CELL);
			final String strEncryptCell = AesUtils.getInstance(strEncryptLoginCode).encryptAES(cell);
			CookieUtils.addCookie(response, strCookieDomain, strCookieLoginPath, strCookieLoginCell, strEncryptCell, iCookieLoginExpire, isHttpOnly, isSecure);
			logger.debug("保存cookie,name={},value={}", strCookieLoginCell, strEncryptCell);
		} catch (Exception e) {
			logger.error("司机cookie保存异常", e);
		}
	}

}
