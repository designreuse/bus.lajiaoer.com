package com.demo.web.filter.admin;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.utils.CookieUtils;
import com.demo.utils.StringUtils;

@WebFilter(asyncSupported = true, urlPatterns = { "/admin/*" })
public class AdminWebSecurityFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(AdminWebSecurityFilter.class);

	/**
	 * Default constructor.
	 */
	public AdminWebSecurityFilter() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		logger.debug("销毁过滤");
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// place your code here
		final String requestUri = ((HttpServletRequest) request).getRequestURI();
		if (StringUtils.startsWithAny(requestUri, "/admin/login")) {
			chain.doFilter(request, response);
			return;
		}
		// pass the request along the filter chain
		Cookie cookie = CookieUtils.findCookieByName(((HttpServletRequest) request), "ad_token");
		if (null == cookie) {
			Cookie adNameCookie = CookieUtils.findCookieByName(((HttpServletRequest) request), "ad_name");
			if (null != adNameCookie) {
				CookieUtils.clearCookieByName(((HttpServletRequest) request), ((HttpServletResponse) response), "ad_name", "admin.lajiaoer.com", "/");
			}
			((HttpServletResponse) response).sendRedirect("/admin/login");
			return;
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		logger.debug("初始化过滤");
	}

}
