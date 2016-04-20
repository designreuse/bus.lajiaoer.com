package com.demo.web.servlet.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.core.builder.CookieBuilder;
import com.demo.core.builder.CookieWorker;
import com.demo.core.builder.UserLoginCookieBuilder;
import com.demo.core.dynamic.DynamicInvocation;
import com.demo.model.User;
import com.demo.model.UserSignupEmail;
import com.demo.service.UserSignupEmailService;
import com.demo.service.UserService;
import com.demo.service.impl.UserSignupEmailServiceImpl;
import com.demo.service.impl.UserServiceImpl;
import com.demo.web.servlet.BaseServlet;

@WebServlet(asyncSupported = true, urlPatterns = { "/signup/confirm" })
public class UserSignupConfirmServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_PATH = "/";

	private static final Logger LOGGER = LoggerFactory.getLogger(UserSignupConfirmServlet.class);

	private UserService userService = new DynamicInvocation(UserServiceImpl.class).getProxy();
	private UserSignupEmailService signupEmailService = UserSignupEmailServiceImpl.getInstance();

	public UserSignupConfirmServlet() {
		super();
	}

	@Override
	protected void doJspGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		LOGGER.info("do get method start...");
		final String strToken = getJspParameter(request, "t");
		UserSignupEmail signupEmail = signupEmailService.get(strToken);
		if (null == signupEmail) {
			throw new RuntimeException("注册确认操作异常");
		}

		User user = new User();
		user.setId(signupEmail.getUserId());
		user.setActived((short) 1);
		User activedUser = userService.get(signupEmail.getUserId());
		if (activedUser.getActived() == 1) {
			LOGGER.error("账号已经激活， user={}, activedUser={}", user, activedUser);
			throw new RuntimeException("账号已经激活");
		}

		int count = userService.updateById(user);
		if (count == 1) {
			User userParam = new User();
			userParam.setPassword(activedUser.getPassword());
			userParam.setEmail(activedUser.getEmail());
			CookieBuilder cookieBuilder = new UserLoginCookieBuilder(userParam, response);
			CookieWorker cookieWorker = new CookieWorker();
			cookieWorker.build(cookieBuilder);

			setJspRedirect(request, response, DEFAULT_PATH);
		} else {
			LOGGER.error("更新激活数量不正确，count={}, user={}", count, user);
			throw new RuntimeException("账号激活失败");
		}
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

		setJspRedirect(request, response, "/signup/success");
	}

}
