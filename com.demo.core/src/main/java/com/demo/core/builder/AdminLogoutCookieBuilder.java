package com.demo.core.builder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.utils.CookieUtils;

public class AdminLogoutCookieBuilder extends CookieBuilder {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminLogoutCookieBuilder.class);

	public AdminLogoutCookieBuilder(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	@Override
	void buildCookie() {
		try {
			CookieUtils.clearCookieByName(request, response, "ad_name", "admin.lajiaoer.com", "/");
			CookieUtils.clearCookieByName(request, response, "ad_token", "admin.lajiaoer.com", "/");
		} catch (Exception e) {
			LOGGER.error("logout异常，{}", e.getMessage());
		}
	}

}
