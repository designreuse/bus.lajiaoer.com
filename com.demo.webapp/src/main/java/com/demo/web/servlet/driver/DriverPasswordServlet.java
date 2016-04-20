package com.demo.web.servlet.driver;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.mail.EmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.core.dynamic.DynamicInvocation;
import com.demo.model.User;
import com.demo.service.UserService;
import com.demo.service.impl.UserServiceImpl;
import com.demo.utils.ConfigUtils;
import com.demo.utils.EmailUtils;
import com.demo.utils.StringUtils;
import com.demo.web.servlet.BaseServlet;

@WebServlet(asyncSupported = true, urlPatterns = { "/driver/password" })
public class DriverPasswordServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	private static final String DRIVER_PASSWORD_PATH = "/WEB-INF/jsp/driver/driver_password.jsp";

	private static final Logger LOGGER = LoggerFactory.getLogger(DriverPasswordServlet.class);

	private UserService userService = new DynamicInvocation(UserServiceImpl.class).getProxy();

	@Override
	protected void doJspGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		LOGGER.info("do get method start...");
		setJspDispatcher(request, response, DRIVER_PASSWORD_PATH);
		LOGGER.info("do get method end...");
	}

	@Override
	protected void doJspPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strEmail = getJspParameter(request, "email");
		User existUser = userService.get(strEmail);
		if (null == existUser) {
			LOGGER.error("邮箱不存在，错误邮箱为{}", strEmail);
			throw new RuntimeException("邮箱不存在");
		}
		try {
			String strNewPassword = StringUtils.randomNumberString(8);
			User paramUser = new User();
			paramUser.setEmail(strEmail);
			paramUser.setPassword(strNewPassword);
			int rows = userService.updateByEmail(paramUser);
			if (rows == 1) {
				EmailUtils.sendEmail(strEmail, "[" + ConfigUtils.getStringValue("web.title") + "]您的密码已经被重置", "您的新密码为" + strNewPassword + "，请登录后修改。");
				setJspRedirect(request, response, "/password/success?g=" + Base64.encodeBase64String(String.valueOf(strEmail).getBytes("UTF-8")));
			} else if (rows > 1) {
				LOGGER.error("这不科学，更新影响数量{}，email={}", rows, strEmail);
			}
		} catch (EmailException e) {
			LOGGER.error("找回密码失败，失败异常日志{}", e);
			throw new RuntimeException("找回密码失败", e);
		}
	}

}
