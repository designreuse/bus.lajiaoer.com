package com.demo.web.servlet.driver;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.web.servlet.BaseServlet;

@WebServlet(asyncSupported = true, urlPatterns = { "/driver/signup" })
public class DriverSignupServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static final String DRIVER_SIGNUP_PATH = "/WEB-INF/jsp/driver/driver_signup.jsp";

	private static final Logger LOGGER = LoggerFactory.getLogger(DriverSignupServlet.class);

	public DriverSignupServlet() {
		super();
	}

	@Override
	protected void doJspGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		LOGGER.info("do get method start...");
		setJspDispatcher(request, response, DRIVER_SIGNUP_PATH);
		LOGGER.info("do get method end...");
	}

	@Override
	protected void doJspPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// String strEmail = getJspParameter(request, "email");
		// String strPassword = getJspParameter(request, "password");
		// String strUsername = getJspParameter(request, "username");
		//
		// LOGGER.info("strEmail={}", strEmail);
		// LOGGER.info("strPassword={}", strPassword);
		// LOGGER.info("strUsername={}", strUsername);
		//
		// User userSignup = new User();
		// userSignup.setEmail(strEmail);
		// userSignup.setPassword(strPassword);
		// userSignup.setUserName(strUsername);
		//
		// LOGGER.info("userSignup={}", userSignup);
		//
		// User userSaved = userService.signup(userSignup);
		// Long sUserId = userSaved.getId();
		// if (sUserId.longValue() > 0) {
		// LOGGER.info("注册成功，用户id={}", sUserId);
		// setJspRedirect(request, response, "/");
		// } else {
		// LOGGER.error("注册失败，参数{}，结果{}", userSignup, userSaved);
		// throw new ServletException("注册失败");
		// }

		setJspRedirect(request, response, "/driver/signup/success");
	}

}
