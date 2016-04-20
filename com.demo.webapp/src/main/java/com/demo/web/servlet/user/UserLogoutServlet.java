package com.demo.web.servlet.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.core.builder.CookieBuilder;
import com.demo.core.builder.CookieWorker;
import com.demo.core.builder.LogoutCookieBuilder;
import com.demo.web.servlet.BaseServlet;

@WebServlet(asyncSupported = true, urlPatterns = { "/logout" })
public class UserLogoutServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_LOGOUT_REDIRECT_PATH = "/";
	private static final Logger logger = LoggerFactory.getLogger(UserLogoutServlet.class);

	public UserLogoutServlet() {
		super();
	}

	@Override
	protected void doJspGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("注销开始");

		// // 清理cookie
		// String strCookieDomain = ConfigUtils.getStringValue("cookie.domain");
		// String strCookiePath = ConfigUtils.getStringValue("cookie.login.path");
		// String strCookieEmail = ConfigUtils.getStringValue("cookie.login.email");
		// String strCookieEmailPwd = ConfigUtils.getStringValue("cookie.login.emailpwd");
		// CookieUtils.clearCookieByName(request, response, strCookieEmail, strCookieDomain, strCookiePath);
		// CookieUtils.clearCookieByName(request, response, strCookieEmailPwd, strCookieDomain, strCookiePath);
		CookieBuilder cookieBuilder = new LogoutCookieBuilder(request, response);
		CookieWorker cookieWorker = new CookieWorker();
		cookieWorker.build(cookieBuilder);

		// 成功注销后跳转
		String ref = getJspParameter(request, "ref");
		if (StringUtils.isNotBlank(ref)) {
			response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
			response.setHeader("location", ref);
		} else {
			setJspRedirect(request, response, DEFAULT_LOGOUT_REDIRECT_PATH);
		}

		logger.info("注销结束");
	}

	@Override
	protected void doJspPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
