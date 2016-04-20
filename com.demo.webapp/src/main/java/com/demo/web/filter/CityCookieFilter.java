package com.demo.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.model.constants.CookieConstants;
import com.demo.utils.ConfigUtils;
import com.demo.utils.CookieUtils;
import com.demo.utils.StringUtils;

@WebFilter(asyncSupported = true, urlPatterns = { "/*" }, initParams = { @WebInitParam(name = "cityKey", value = "cityid"), @WebInitParam(name = "defaultCityId", value = "1") })
public class CityCookieFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(CityCookieFilter.class);

	private FilterConfig fConfig = null;

	/**
	 * Default constructor.
	 */
	public CityCookieFilter() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		logger.debug("销毁城市cookie过滤");
		this.fConfig = null;
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// place your code here
		final String requestUri = ((HttpServletRequest) request).getRequestURI();
		if (StringUtils.startsWithAny(requestUri, "/css", "/js", "/img", "/fonts", "/font-awesome", "/email_templates", "/upload")) {
			logger.debug("exclude path={}", requestUri);
			chain.doFilter(request, response);
			return;
		}

		// pass the request along the filter chain
		final String strCityKey = this.fConfig.getInitParameter("cityKey");
		final String strDefaultCityId = this.fConfig.getInitParameter("defaultCityId");
		Cookie cookie = CookieUtils.findCookieByName(((HttpServletRequest) request), strCityKey);
		if (null == cookie) {
			CookieUtils.addCookie((HttpServletResponse) response, ConfigUtils.getStringValue(CookieConstants.COOKIE_DOMAIN), "/", strCityKey, strDefaultCityId, 30 * 24 * 60 * 60,
					false, false);
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		logger.debug("初始化城市cookie过滤");
		this.fConfig = fConfig;
	}

}
