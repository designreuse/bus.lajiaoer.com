package com.demo.web.servlet.landing;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.demo.web.servlet.BaseServlet;

@WebServlet(asyncSupported = true, urlPatterns = { "/index" })
public class IndexServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static final String LANDING_IINDEX_PATH = "/WEB-INF/jsp/landing/index.jsp";

	public IndexServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doJspGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		setJspDispatcher(request, response, LANDING_IINDEX_PATH);
	}

	@Override
	protected void doJspPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

}
