package com.demo.core.builder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.model.constants.CookieConstants;
import com.demo.utils.ConfigUtils;
import com.demo.utils.CookieUtils;

public class LogoutCookieBuilder extends CookieBuilder {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogoutCookieBuilder.class);

	public LogoutCookieBuilder(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	@Override
	void buildCookie() {
		try {
			final String strCookieDomain = ConfigUtils.getStringValue(CookieConstants.COOKIE_DOMAIN);
			final String strCookiePath = ConfigUtils.getStringValue(CookieConstants.COOKIE_LOGIN_PATH);
			final String strCookieEmail = ConfigUtils.getStringValue(CookieConstants.COOKIE_LOGIN_EMAIL);
			final String strCookieEmailPwd = ConfigUtils.getStringValue(CookieConstants.COOKIE_LOGIN_EMAILPWD);
			final String strCookieCell = ConfigUtils.getStringValue(CookieConstants.COOKIE_LOGIN_CELL);
			final String strCookieCellPwd = ConfigUtils.getStringValue(CookieConstants.COOKIE_LOGIN_CELLPWD);
			CookieUtils.clearCookieByName(request, response, strCookieEmail, strCookieDomain, strCookiePath);
			CookieUtils.clearCookieByName(request, response, strCookieEmailPwd, strCookieDomain, strCookiePath);
			CookieUtils.clearCookieByName(request, response, strCookieCell, strCookieDomain, strCookiePath);
			CookieUtils.clearCookieByName(request, response, strCookieCellPwd, strCookieDomain, strCookiePath);
			LOGGER.info("logout成功，email={}，emailpwd={}，cell={}，cellpwd={}", strCookieEmail, strCookieEmailPwd, strCookieCell, strCookieCellPwd);
		} catch (Exception e) {
			LOGGER.error("logout异常，{}", e.getMessage());
		}
	}

}
