package com.demo.web.servlet.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.demo.model.admin.AdminUser;
import com.demo.web.servlet.AdminBaseServlet;

@WebServlet(asyncSupported = true, urlPatterns = { "/admin/main" })
public class AdminMainServlet extends AdminBaseServlet {
	private static final long serialVersionUID = 1L;
	private static final String MAIN_PATH = "/WEB-INF/jsp/admin/main.jsp";

	/**
	 * Default constructor.
	 */
	public AdminMainServlet() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doJspGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminUser user = getSessionFromRedis(request);
		logger.info("用户信息，user={}", user);
		setJspDispatcher(request, response, MAIN_PATH);
	}

	@Override
	protected void doJspPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

}
