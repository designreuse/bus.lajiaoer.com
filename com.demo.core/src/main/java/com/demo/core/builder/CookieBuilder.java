package com.demo.core.builder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class CookieBuilder {

	protected HttpServletResponse response;
	protected HttpServletRequest request;

	abstract void buildCookie();
}
