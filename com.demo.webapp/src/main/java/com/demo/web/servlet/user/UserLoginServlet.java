package com.demo.web.servlet.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.utils.StringUtils;
import com.demo.web.servlet.BaseServlet;

@WebServlet(asyncSupported = true, urlPatterns = { "/login" })
public class UserLoginServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static final String LOGIN_PATH = "/WEB-INF/jsp/user/login.jsp";
	private static final String DEFAULT_REDIRECT_PATH = "/waiting/place";
	private static final Logger logger = LoggerFactory.getLogger(UserLoginServlet.class);
	private static final String KEY_REF = "ref";
	// private UserService userService = UserServiceImpl.getInstance();

	public UserLoginServlet() {
		super();
	}

	@Override
	protected void doJspGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("登录进入开始");
		String ref = getJspParameter(request, KEY_REF);
		if (StringUtils.isNotBlank(ref)) {
			setJspAttribute(request, KEY_REF, ref);
		}
		setJspDispatcher(request, response, LOGIN_PATH);
		logger.debug("登录进入结束");
	}

	@Override
	protected void doJspPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("登录提交开始");

		final String ref = getJspParameter(request, KEY_REF);
		if (StringUtils.isNotBlank(ref)) {
			response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
			response.setHeader("location", ref);
		} else {
			setJspRedirect(request, response, DEFAULT_REDIRECT_PATH);
		}
		logger.debug("登录提交结束");
	}

}
