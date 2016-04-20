package com.demo.web.servlet.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.utils.StringUtils;
import com.demo.web.servlet.BaseServlet;

@WebServlet(asyncSupported = true, urlPatterns = { "/password/success" })
public class UserPasswordSuccessServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	private static final String PASSWORD_SUCCESS_PATH = "/WEB-INF/jsp/user/password_success.jsp";

	private static final Logger LOGGER = LoggerFactory.getLogger(UserPasswordSuccessServlet.class);

	@Override
	protected void doJspGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		LOGGER.info("do get method start...");
		String strG = getJspParameter(request, "g");
		String strEmail = new String(Base64.decodeBase64(strG));
		String strEmailHost = StringUtils.split(strEmail, "@")[1];
		setJspAttribute(request, "pMailUrl", "http://mail." + strEmailHost);
		setJspDispatcher(request, response, PASSWORD_SUCCESS_PATH);
		LOGGER.info("do get method end...");
	}

	@Override
	protected void doJspPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
