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
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.utils.StringUtils;

/**
 * Servlet Filter implementation class CharacterEncodingFilter
 */
@WebFilter(asyncSupported = true, urlPatterns = { "/*" }, initParams = { @WebInitParam(name = "encoding", value = "UTF-8") })
public class CharacterEncodingFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(CharacterEncodingFilter.class);

	private static final String DEFAULT_ENCODING = "UTF-8";
	private FilterConfig fConfig = null;

	/**
	 * Default constructor.
	 */
	public CharacterEncodingFilter() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		logger.info("销毁字符集过滤");
		this.fConfig = null;
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// place your code here
		String encoding = this.fConfig.getInitParameter("encoding");
		final String requestUri = ((HttpServletRequest) request).getRequestURI();
		if (StringUtils.startsWithAny(requestUri, "/css", "/js", "/img", "/fonts", "/font-awesome", "/email_templates", "/upload")) {
			chain.doFilter(request, response);
			return;
		}

		if (StringUtils.isNotBlank(encoding)) {
			request.setCharacterEncoding(encoding);
		} else {
			request.setCharacterEncoding(DEFAULT_ENCODING);
		}
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		logger.info("初始化字符集过滤");
		this.fConfig = fConfig;
	}

}
