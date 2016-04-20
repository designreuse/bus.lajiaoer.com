package com.demo.web.servlet;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.model.admin.AdminUser;
import com.demo.utils.CookieUtils;
import com.demo.utils.RedisUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AdminBaseServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected abstract void doJspGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

	protected abstract void doJspPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

	@Override
	protected final void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doJspGet(req, resp);
	}

	@Override
	protected final void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doJspPost(req, resp);
	}

	@Override
	protected final long getLastModified(HttpServletRequest req) {
		return super.getLastModified(req);
	}

	@Override
	protected final void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doHead(req, resp);
	}

	@Override
	protected final void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPut(req, resp);
	}

	@Override
	protected final void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doDelete(req, resp);
	}

	@Override
	protected final void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doOptions(req, resp);
	}

	@Override
	protected final void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doTrace(req, resp);
	}

	@Override
	protected final void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.service(req, resp);
	}

	@Override
	public final void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.service(req, res);
	}

	protected void setJspDispatcher(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
		if (StringUtils.isBlank(path)) {
			throw new IllegalArgumentException("path不能为空");
		}
		getServletContext().getRequestDispatcher(path).forward(request, response);
	}

	protected void setJspRedirect(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
		if (StringUtils.isBlank(path)) {
			throw new IllegalArgumentException("path不能为空");
		}
		response.sendRedirect(path);
	}

	protected void setJspAttribute(HttpServletRequest request, String key, Object val) {
		if (StringUtils.isBlank(key)) {
			throw new IllegalArgumentException("key不能为空");
		}
		request.setAttribute(key, val);
	}

	protected void setJspParameters(HttpServletRequest request, Map<String, String> parameters) {
		if (MapUtils.isEmpty(parameters)) {
			throw new IllegalArgumentException("不能为空");
		}
		Iterator<String> it = parameters.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			if (null == key || "".equals(key)) {
				continue;
			}
			String val = parameters.get(key);
			setJspAttribute(request, key, val);
		}
	}

	protected String getJspParameter(HttpServletRequest request, String key) {
		if (StringUtils.isBlank(key)) {
			throw new IllegalArgumentException("key不能为空");
		}
		String val = request.getParameter(key);

		return val;
	}

	protected AdminUser getSessionFromRedis(HttpServletRequest request) {
		Cookie cookie = CookieUtils.findCookieByName(request, "ad_name");
		try {
			String strAdName = cookie.getValue();
			String strSessionJson = RedisUtils.hget("admin:session", "user:" + strAdName);
			ObjectMapper mapper = new ObjectMapper();
			AdminUser user = mapper.readValue(strSessionJson, AdminUser.class);
			return user;
		} catch (Exception e) {
			logger.error("doGet异常", e);
			throw new RuntimeException("异常", e);
		}
	}

	protected void setSessionToRedis(AdminUser user) {
		ObjectMapper mapper = new ObjectMapper();
		String strUserJson = null;
		try {
			strUserJson = mapper.writeValueAsString(user);
		} catch (JsonProcessingException e) {
			logger.error("json处理异常", e);
			throw new RuntimeException("json处理异常", e);
		}

		try {
			RedisUtils.hset("admin:session", "user:" + user.getName(), strUserJson);
		} catch (Exception e) {
			logger.error("admin's session保存异常，strUserJson={}", strUserJson);
			throw new RuntimeException("admin's session保存异常");
		}
	}

	protected void delSessionFromRedis(HttpServletRequest request) {
		Cookie cookie = CookieUtils.findCookieByName(request, "ad_name");
		String strAdName = cookie.getValue();
		RedisUtils.hdel("admin:session", "user:" + strAdName);
	}

}
