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
import com.demo.model.DriverAudit;
import com.demo.model.constants.CookieConstants;
import com.demo.service.DriverAuditService;
import com.demo.service.DriverService;
import com.demo.service.impl.DriverAuditServiceImpl;
import com.demo.service.impl.DriverServiceImpl;
import com.demo.utils.AesUtils;
import com.demo.utils.ConfigUtils;
import com.demo.utils.CookieUtils;
import com.demo.web.servlet.BaseServlet;

/**
 * 司机注册信息修改
 * 
 * @author xuzhongliang
 *
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/driver/signup/edit" })
public class DriverSignupEditServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static final String DRIVER_SIGNUP_EDIT_PATH = "/WEB-INF/jsp/driver/driver_signup_edit.jsp";

	private static final Logger logger = LoggerFactory.getLogger(DriverSignupEditServlet.class);
	private DriverService driverService = new DynamicInvocation(DriverServiceImpl.class).getProxy();
	private DriverAuditService driverAuditService = new DynamicInvocation(DriverAuditServiceImpl.class).getProxy();

	public DriverSignupEditServlet() {
		super();
	}

	@Override
	protected void doJspGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("do get method start...");
		Cookie cookie = CookieUtils.findCookieByName(request, ConfigUtils.getStringValue(CookieConstants.COOKIE_LOGIN_CELL));
		if (null == cookie) {
			setJspRedirect(request, response, "/driver/login");
			return;
		}
		final String strCookieCell = cookie.getValue();
		final String strCell = AesUtils.getInstance(ConfigUtils.getStringValue(CookieConstants.LOGIN_ENCRYPT_CODE)).decryptAES(strCookieCell);
		logger.debug("strCookieCell={},strCell={}", strCookieCell, strCell);

		Driver driver = driverService.get(strCell);
		logger.debug("driver={}", driver);
		DriverAudit driverAudit = driverAuditService.getDriverAuditByDriverId(driver.getId());
		logger.debug("driverAudit={}", driverAudit);
		boolean isPass = true;
		if (driverAudit.getName() != 1 || driverAudit.getLicensePlate() != 1 || driverAudit.getSfzA() != 1 || driverAudit.getSfzB() != 1 || driverAudit.getJszA() != 1
				|| driverAudit.getXszA() != 1 || driverAudit.getCell() != 1) {
			isPass = false;
		}

		setJspAttribute(request, "driver", driver);
		setJspAttribute(request, "driverAudit", driverAudit);
		setJspAttribute(request, "pass", isPass);
		setJspDispatcher(request, response, DRIVER_SIGNUP_EDIT_PATH);
		logger.info("do get method end...");
	}

	@Override
	protected void doJspPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		setJspRedirect(request, response, "/driver/signup/edit");
	}

}
