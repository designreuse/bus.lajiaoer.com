package com.demo.web.servlet.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.demo.web.servlet.AdminBaseServlet;

@WebServlet(asyncSupported = true, urlPatterns = { "/admin/drivers" })
public class AdminDriversServlet extends AdminBaseServlet {
	private static final long serialVersionUID = 1L;
	private static final String MAIN_PATH = "/WEB-INF/jsp/admin/drivers/drivers.jsp";

	/**
	 * Default constructor.
	 */
	public AdminDriversServlet() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doJspGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setJspDispatcher(request, response, MAIN_PATH);
	}

	@Override
	protected void doJspPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

}
