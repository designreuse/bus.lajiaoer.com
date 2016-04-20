package com.demo.web.servlet.driver;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.core.dynamic.DynamicInvocation;
import com.demo.model.Driver;
import com.demo.model.constants.CookieConstants;
import com.demo.service.DriverService;
import com.demo.service.impl.DriverServiceImpl;
import com.demo.utils.AesUtils;
import com.demo.utils.ConfigUtils;
import com.demo.utils.CookieUtils;
import com.demo.utils.StringUtils;
import com.demo.web.servlet.BaseServlet;

@WebServlet(asyncSupported = true, urlPatterns = { "/driver/login" })
public class DriverLoginServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static final String DRIVER_LOGIN_PATH = "/WEB-INF/jsp/driver/driver_login.jsp";
	private static final String DEFAULT_REDIRECT_PATH = "/driver/waiting";
	private static final Logger logger = LoggerFactory.getLogger(DriverLoginServlet.class);
	private static final String KEY_REF = "ref";
	private DriverService driverService = new DynamicInvocation(DriverServiceImpl.class).getProxy();

	public DriverLoginServlet() {
		super();
	}

	@Override
	protected void doJspGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("登录进入开始");
		String ref = getJspParameter(request, KEY_REF);
		if (StringUtils.isNotBlank(ref)) {
			setJspAttribute(request, KEY_REF, ref);
		}
		setJspDispatcher(request, response, DRIVER_LOGIN_PATH);
		logger.info("登录进入结束");
	}

	@Override
	protected void doJspPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("登录提交开始");
		Cookie cookie = CookieUtils.findCookieByName(request, ConfigUtils.getStringValue(CookieConstants.COOKIE_LOGIN_CELL));
		// 无手机号cookie，跳转到登录
		if (null == cookie) {
			logger.error("cookie is null, 跳转至登录页面");
			setJspRedirect(request, response, "/driver/login");
			return;
		}

		final String strCell = cookie.getValue();
		final String strRealCell = AesUtils.getInstance(ConfigUtils.getStringValue(CookieConstants.LOGIN_ENCRYPT_CODE)).decryptAES(strCell);
		Driver driver = driverService.get(strRealCell);
		if (null == driver) {
			throw new RuntimeException("司机不存在");
		}

		if (driver.getActived() == 0) {
			setJspRedirect(request, response, "/driver/signup/edit");
			return;
		}

		final String ref = getJspParameter(request, KEY_REF);
		if (StringUtils.isNotBlank(ref)) {
			response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
			response.setHeader("location", ref);
		} else {
			setJspRedirect(request, response, DEFAULT_REDIRECT_PATH);
		}
		logger.info("登录提交结束");
	}

}
