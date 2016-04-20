package com.demo.web.servlet.driver;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.web.servlet.BaseServlet;

@WebServlet(asyncSupported = true, urlPatterns = { "/driver/waiting" })
public class DriverWaitingServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static final String DRIVER_WAITING_PATH = "/WEB-INF/jsp/driver/driver_waiting.jsp";

	private static final Logger LOGGER = LoggerFactory.getLogger(DriverWaitingServlet.class);

	public DriverWaitingServlet() {
		super();
	}

	@Override
	protected void doJspGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.info("do get method start...");
		setJspDispatcher(request, response, DRIVER_WAITING_PATH);
		LOGGER.info("do get method end...");
	}

	@Override
	protected void doJspPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doJspGet(request, response);
	}

}
