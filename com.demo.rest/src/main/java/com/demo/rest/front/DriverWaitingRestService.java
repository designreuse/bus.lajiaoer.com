package com.demo.rest.front;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.JSONP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.core.dynamic.DynamicInvocation;
import com.demo.db.dao.DriverDao;
import com.demo.db.dao.impl.DriverDaoImpl;
import com.demo.enums.DriverWaitingResultTypeEnum;
import com.demo.enums.RestResultTypeEnum;
import com.demo.model.Driver;
import com.demo.model.DriverWaiting;
import com.demo.model.constants.CookieConstants;
import com.demo.rest.BaseRestService;
import com.demo.service.DriverWaitingService;
import com.demo.service.impl.DriverWaitingServiceImpl;
import com.demo.utils.AesUtils;
import com.demo.utils.ConfigUtils;
import com.demo.utils.CookieUtils;
import com.demo.vo.RestResult;

@Path("/driver/waiting")
public class DriverWaitingRestService extends BaseRestService {

	private DriverDao driverService = new DynamicInvocation(DriverDaoImpl.class).getProxy();
	private DriverWaitingService driverWaitingService = new DynamicInvocation(DriverWaitingServiceImpl.class).getProxy();

	private static final Logger logger = LoggerFactory.getLogger(DriverWaitingRestService.class);

	@PUT
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public RestResult stop(@FormParam("id") final Long id, @Context HttpServletRequest request, @Context HttpServletResponse response) {
		RestResult restResult = new RestResult();
		// this.enabledCrossDomain(response);

		DriverWaiting driverWaiting = new DriverWaiting();
		driverWaiting.setId(id);
		driverWaitingService.updateStop(driverWaiting);
		return restResult;
	}

	@GET
	@JSONP(queryParam = "jsoncallback", callback = "jsoncallback")
	@Produces({ "application/javascript;charset=UTF-8" })
	public RestResult get(@Context HttpServletRequest request, @Context HttpServletResponse response) throws UnsupportedEncodingException {
		RestResult restResult = new RestResult();
		// this.enabledCrossDomain(response);

		final String strCookieLoginCell = ConfigUtils.getStringValue(CookieConstants.COOKIE_LOGIN_CELL);
		final Cookie cookie = CookieUtils.findCookieByName(request, strCookieLoginCell);
		if (null == cookie) {
			resultWrapper(restResult, DriverWaitingResultTypeEnum.COOKIE_CELL_IS_BLANK);
			logger.error("参数错误，strCookieLoginCell={}，错误信息={}", strCookieLoginCell, restResult);
			return restResult;
		}

		final String strCookieLoginCellValue = cookie.getValue();
		final String strEncryptLoginCode = ConfigUtils.getStringValue(CookieConstants.LOGIN_ENCRYPT_CODE);
		final String strCell = AesUtils.getInstance(strEncryptLoginCode).decryptAES(strCookieLoginCellValue);

		Driver driver = driverService.get(strCell);
		if (null == driver) {
			resultWrapper(restResult, DriverWaitingResultTypeEnum.CELL_IS_NOT_EXIST);
			logger.error("参数错误，strCell={}，错误信息={}", strCell, restResult);
			return restResult;
		}
		final Long driverId = driver.getId();

		DriverWaiting driverWaiting = driverWaitingService.get(driverId);
		if (null == driverWaiting || driverWaiting.getIsStop() == 1) {
			resultWrapper(restResult, DriverWaitingResultTypeEnum.DRIVER_WAITING_IS_NOT_EXIST);
			logger.error("参数错误，driverId={}，错误信息={}", driverId, restResult);
			return restResult;
		}

		restResult.setData(driverWaiting);
		return restResult;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public RestResult begin(@Context HttpServletRequest request, @Context HttpServletResponse response) {
		RestResult restResult = new RestResult();
		// this.enabledCrossDomain(response);

		try {
			final String strCookieLoginCell = ConfigUtils.getStringValue(CookieConstants.COOKIE_LOGIN_CELL);
			final Cookie cookie = CookieUtils.findCookieByName(request, strCookieLoginCell);
			if (null == cookie) {
				resultWrapper(restResult, DriverWaitingResultTypeEnum.COOKIE_CELL_IS_BLANK);
				logger.error("参数错误，cookie={}，错误信息={}", cookie, restResult);
				return restResult;
			}

			final String strCookieLoginCellValue = cookie.getValue();
			final String strEncryptLoginCode = ConfigUtils.getStringValue(CookieConstants.LOGIN_ENCRYPT_CODE);
			final String strCell = AesUtils.getInstance(strEncryptLoginCode).decryptAES(strCookieLoginCellValue);

			Driver driver = driverService.get(strCell);
			if (null == driver) {
				resultWrapper(restResult, DriverWaitingResultTypeEnum.CELL_IS_NOT_EXIST);
				logger.error("参数错误，driver={}，错误信息={}", driver, restResult);
				return restResult;
			}

			if (null == driver.getDriverPlaceId() || driver.getDriverPlaceId() == 0L) {
				resultWrapper(restResult, DriverWaitingResultTypeEnum.DRIVER_WAITING_IS_PENDING);
				logger.error("参数错误，driver={}，错误信息={}", driver, restResult);
				return restResult;
			}

			final Long driverId = driver.getId();

			DriverWaiting existDriverWaiting = driverWaitingService.get(driverId);
			if (null != existDriverWaiting) {
				resultWrapper(restResult, DriverWaitingResultTypeEnum.DRIVER_WAITING_IS_EXIST);
				logger.error("参数错误，driverId={}，错误信息={}", driverId, restResult);
				return restResult;
			}

			DriverWaiting driverWaiting = new DriverWaiting();
			driverWaiting.setDriverId(driverId);
			driverWaiting.setDriverPlaceId(driver.getDriverPlaceId());
			driverWaitingService.insert(driverWaiting);

			restResult.setData(driverWaiting);

		} catch (Exception e) {
			resultWrapper(restResult, RestResultTypeEnum.ERROR);
			logger.error("司机等待发送错误，异常={}", e);
		}
		return restResult;
	}
}
