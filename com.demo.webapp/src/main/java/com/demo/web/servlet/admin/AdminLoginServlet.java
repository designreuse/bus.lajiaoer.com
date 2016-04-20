package com.demo.web.servlet.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.demo.admin.service.AdminUserService;
import com.demo.admin.service.impl.AdminUserServiceImpl;
import com.demo.core.builder.AdminLoginCookieBuilder;
import com.demo.core.builder.CookieBuilder;
import com.demo.core.builder.CookieWorker;
import com.demo.core.dynamic.DynamicInvocation;
import com.demo.model.admin.AdminUser;
import com.demo.utils.StringUtils;
import com.demo.web.servlet.AdminBaseServlet;

@WebServlet(asyncSupported = true, urlPatterns = { "/admin/login" })
public class AdminLoginServlet extends AdminBaseServlet {
	private static final long serialVersionUID = 1L;
	private static final String MAIN_PATH = "/WEB-INF/jsp/admin/login.jsp";

	private AdminUserService userService = new DynamicInvocation(AdminUserServiceImpl.class).getProxy();

	/**
	 * Default constructor.
	 */
	public AdminLoginServlet() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doJspGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setJspDispatcher(request, response, MAIN_PATH);
	}

	@Override
	protected void doJspPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = getJspParameter(request, "username");
		String passWord = getJspParameter(request, "password");

		if (StringUtils.isAnyBlank(userName, passWord)) {
			setJspAttribute(request, "error", "用户名或密码必须填写");
			doJspGet(request, response);
			return;
		}

		boolean success = false;
		try {
			success = userService.login(userName, passWord);
		} catch (Exception e) {
			throw new ServletException("系统异常");
		}

		if (success) {
			AdminUser user = userService.getAdminUser(userName);
			CookieBuilder cookieBuilder = new AdminLoginCookieBuilder(user, response);
			CookieWorker cookieWorker = new CookieWorker();
			cookieWorker.build(cookieBuilder);
			setSessionToRedis(user);
			setJspRedirect(request, response, "/admin/main");
		} else {
			setJspAttribute(request, "username", userName);
			setJspAttribute(request, "password", passWord);
			setJspAttribute(request, "error", "用户名或密码错误");
			doJspGet(request, response);
		}
	}

}
