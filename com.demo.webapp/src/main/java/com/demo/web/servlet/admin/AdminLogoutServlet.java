package com.demo.web.servlet.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.demo.core.builder.AdminLogoutCookieBuilder;
import com.demo.core.builder.CookieBuilder;
import com.demo.core.builder.CookieWorker;
import com.demo.web.servlet.AdminBaseServlet;

@WebServlet(asyncSupported = true, urlPatterns = { "/admin/logout" })
public class AdminLogoutServlet extends AdminBaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public AdminLogoutServlet() {
	}

	@Override
	protected void doJspGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CookieBuilder cookieBuilder = new AdminLogoutCookieBuilder(request, response);
		CookieWorker cookieWorker = new CookieWorker();
		cookieWorker.build(cookieBuilder);
		delSessionFromRedis(request);
		setJspRedirect(request, response, "/admin/login");
	}

	@Override
	protected void doJspPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
