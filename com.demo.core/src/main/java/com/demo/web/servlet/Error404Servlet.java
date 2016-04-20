package com.demo.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(asyncSupported = true, urlPatterns = { "/404" })
public class Error404Servlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(Error404Servlet.class);
	private static final String ERROR_404_PATH = "/WEB-INF/jsp/error/404.jsp";

	public Error404Servlet() {
		super();
	}

	@Override
	protected void doJspGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
		String message = (String) request.getAttribute("javax.servlet.error.message");
		if (servletName == null) {
			servletName = "Unknown";
		}
		String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
		if (requestUri == null) {
			requestUri = "Unknown";
		}

		if (null != throwable) {
			logger.error("throwable={}", throwable.getMessage());
			final String strDetailMessage = throwable.getMessage();
			setJspAttribute(request, "detailMessage", strDetailMessage);
		}
		logger.error("statusCode={}", statusCode);
		logger.error("servletName={}", servletName);
		logger.error("message={}", message);
		logger.error("requestUri={}", requestUri);

		// String strHeader = response.getHeader("x-requested-with");
		// if (StringUtils.isNotBlank(strHeader) && StringUtils.equalsIgnoreCase(strHeader, "XMLHttpRequest")) {// ajax request
		// RestResult restResult = new RestResult();
		// restResult.setCode(RestResultTypeEnum.ERROR.getCode());
		// restResult.setMsg(RestResultTypeEnum.ERROR.getMsg());
		// String strJson = new ObjectMapper().writeValueAsString(restResult);
		// response.setContentType("application/json");
		// response.setCharacterEncoding("UTF-8");
		// response.setStatus(HttpServletResponse.SC_OK);
		// response.getWriter().write(strJson);
		// } else if (StringUtils.containsAny(request.getQueryString(), "jsoncallback=", "callback=")) {
		// String strCallback = request.getParameter("jsoncallback");
		// if (StringUtils.isBlank(strCallback)) {
		// strCallback = request.getParameter("callback");
		// if (StringUtils.isBlank(strCallback)) {
		// request.getRequestDispatcher("/500").forward(request, response);
		// return;
		// }
		// }
		//
		// RestResult restResult = new RestResult();
		// restResult.setCode(RestResultTypeEnum.ERROR.getCode());
		// restResult.setMsg(RestResultTypeEnum.ERROR.getMsg());
		// String strJson = new ObjectMapper().writeValueAsString(restResult);
		// response.setContentType("application/javascript");
		// response.setCharacterEncoding("UTF-8");
		// response.setStatus(HttpServletResponse.SC_OK);
		// response.getWriter().write(strCallback + "(" + strJson + ")");
		// } else {
		setJspDispatcher(request, response, ERROR_404_PATH);
		// }
	}

	@Override
	protected void doJspPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doJspGet(request, response);
	}

}
