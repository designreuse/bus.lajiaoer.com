package com.demo.rest.front;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.core.builder.CookieBuilder;
import com.demo.core.builder.CookieWorker;
import com.demo.core.builder.DriverLoginCookieBuilder;
import com.demo.core.dynamic.DynamicInvocation;
import com.demo.enums.DriverLoginResultTypeEnum;
import com.demo.enums.RestResultTypeEnum;
import com.demo.model.Driver;
import com.demo.model.DriverAudit;
import com.demo.rest.BaseRestService;
import com.demo.service.DriverService;
import com.demo.service.impl.DriverServiceImpl;
import com.demo.utils.RedisUtils;
import com.demo.utils.StringUtils;
import com.demo.vo.RestResult;

/**
 * 司机注册rest
 * 
 * @author xuzhongliang
 *
 */
@Path("/driver")
public class DriverRestService extends BaseRestService {

	private static final Logger logger = LoggerFactory.getLogger(DriverRestService.class);
	private DriverService driverService = new DynamicInvocation(DriverServiceImpl.class).getProxy();
	// private UserSignupEmailService signupEmailService = UserSignupEmailServiceImpl.getInstance();

	/**
	 * 司机注册
	 * 
	 * @param cell
	 * @param name
	 * @param password
	 * @param sfzA
	 * @param sfzB
	 * @param jszA
	 * @param alias
	 * @param captcha
	 * @param licensePlate
	 * @param xszA
	 * @param response
	 * @param request
	 * @return
	 */
	@POST
	@Path("/signup")
	@Produces(MediaType.APPLICATION_JSON)
	public RestResult validSignupDriver(@FormParam("cell") final String cell, @FormParam("name") final String name, @FormParam("password") final String password,
			@FormParam("sfza") final String sfzA, @FormParam("sfzb") final String sfzB, @FormParam("jsza") final String jszA, @FormParam("alias") final String alias,
			@FormParam("captcha") final String captcha, @FormParam("license") final String licensePlate, @FormParam("xsza") final String xszA,
			@Context HttpServletResponse response, @Context HttpServletRequest request) {
		RestResult restResult = new RestResult();
		// this.enabledCrossDomain(response);

		if (StringUtils.isBlank(cell)) {
			restResult.setCode(DriverLoginResultTypeEnum.CELL_IS_BLANK.getCode());
			restResult.setMsg(DriverLoginResultTypeEnum.CELL_IS_BLANK.getMsg());
			logger.error("错误原因：{}", restResult);
			return restResult;
		}

		if (StringUtils.isBlank(name)) {
			restResult.setCode(DriverLoginResultTypeEnum.NAME_IS_BLANK.getCode());
			restResult.setMsg(DriverLoginResultTypeEnum.NAME_IS_BLANK.getMsg());
			logger.error("错误原因：{}", restResult);
			return restResult;
		}

		if (StringUtils.isBlank(password)) {
			restResult.setCode(DriverLoginResultTypeEnum.PASSWORD_IS_BLANK.getCode());
			restResult.setMsg(DriverLoginResultTypeEnum.PASSWORD_IS_BLANK.getMsg());
			logger.error("错误原因：{}", restResult);
			return restResult;
		}

		if (StringUtils.isBlank(sfzA)) {
			restResult.setCode(DriverLoginResultTypeEnum.SFZ_A_IS_BLANK.getCode());
			restResult.setMsg(DriverLoginResultTypeEnum.SFZ_A_IS_BLANK.getMsg());
			logger.error("错误原因：{}", restResult);
			return restResult;
		}

		if (StringUtils.isBlank(sfzB)) {
			restResult.setCode(DriverLoginResultTypeEnum.SFZ_B_IS_BLANK.getCode());
			restResult.setMsg(DriverLoginResultTypeEnum.SFZ_B_IS_BLANK.getMsg());
			logger.error("错误原因：{}", restResult);
			return restResult;
		}

		if (StringUtils.isBlank(jszA)) {
			restResult.setCode(DriverLoginResultTypeEnum.JSZ_A_IS_BLANK.getCode());
			restResult.setMsg(DriverLoginResultTypeEnum.JSZ_A_IS_BLANK.getMsg());
			logger.error("错误原因：{}", restResult);
			return restResult;
		}

		if (StringUtils.isBlank(xszA)) {
			restResult.setCode(DriverLoginResultTypeEnum.XSZ_A_IS_BLANK.getCode());
			restResult.setMsg(DriverLoginResultTypeEnum.XSZ_A_IS_BLANK.getMsg());
			logger.error("错误原因：{}", restResult);
			return restResult;
		}

		if (StringUtils.isBlank(licensePlate)) {
			restResult.setCode(DriverLoginResultTypeEnum.LICENSE_PLATE_IS_BLANK.getCode());
			restResult.setMsg(DriverLoginResultTypeEnum.LICENSE_PLATE_IS_BLANK.getMsg());
			logger.error("错误原因：{}", restResult);
			return restResult;
		}

		Driver user = driverService.get(cell);
		if (null != user) {
			restResult.setCode(DriverLoginResultTypeEnum.CELL_IS_EXIST.getCode());
			restResult.setMsg(DriverLoginResultTypeEnum.CELL_IS_EXIST.getMsg());
			logger.error("错误原因：{}，邮箱：{}", restResult, cell);
			return restResult;
		}

		if (StringUtils.isBlank(alias) || StringUtils.isBlank(captcha)) {
			resultWrapper(restResult, DriverLoginResultTypeEnum.CAPTCHA_IS_BLANK);
			// restResult.setCode(DriverLoginResultTypeEnum.CAPTCHA_IS_BLANK.getCode());
			// restResult.setMsg(DriverLoginResultTypeEnum.CAPTCHA_IS_BLANK.getMsg());
			logger.error("错误原因：{}", restResult);
			return restResult;
		}

		final String redisToken = RedisUtils.get(alias);
		if (StringUtils.isBlank(redisToken)) {
			resultWrapper(restResult, DriverLoginResultTypeEnum.CAPTCHA_IS_EXPIRED);
			logger.error("错误原因：{}", restResult);
			return restResult;
		}

		if (!StringUtils.equalsIgnoreCase(redisToken, captcha)) {
			resultWrapper(restResult, DriverLoginResultTypeEnum.CAPTCHA_IS_WRONG);
			logger.error("错误原因：{}", restResult);
			return restResult;
		}

		logger.info("strEmail={}，strPassword={}，strDrivername={}", cell, password, name);

		Driver driverSignup = new Driver();
		driverSignup.setCell(cell);
		driverSignup.setPassword(password);
		driverSignup.setName(name);
		driverSignup.setSfzA(sfzA);
		driverSignup.setSfzB(sfzB);
		driverSignup.setJszA(jszA);
		driverSignup.setXszA(xszA);
		driverSignup.setLicensePlate(licensePlate.toUpperCase());

		logger.info("userSignup={}", driverSignup);

		Driver driverSaved = driverService.signup(driverSignup);
		Long sDriverId = driverSaved.getId();
		if (sDriverId.longValue() > 0) {
			logger.info("注册成功，司机id={}", sDriverId);
			// UserSignupEmail signupEmail = new UserSignupEmail();
			// try {
			// final String strTime = String.valueOf(Calendar.getInstance().getTime());
			// final String strToken = DigestUtils.md5Hex(strTime + ":" + cell + ":" + name);
			// signupEmail.setToken(strToken);
			// signupEmail.setUserId(sUserId);
			// signupEmailService.insert(signupEmail);
			// } catch (Exception e) {
			// logger.error("插入注册邮件信息失败，signupEmail={}，异常={}", signupEmail, e);
			// }
		} else {
			logger.error("注册失败，参数{}，结果{}", driverSignup, driverSaved);
			restResult.setCode(RestResultTypeEnum.ERROR.getCode());
			restResult.setMsg(RestResultTypeEnum.ERROR.getMsg());
		}

		return restResult;
	}

	/**
	 * 修改司机注册信息
	 * 
	 * @param cell
	 * @param name
	 * @param sfzA
	 * @param sfzB
	 * @param jszA
	 * @param licensePlate
	 * @param xszA
	 * @param response
	 * @param request
	 * @return
	 */
	@PUT
	@Path("/signup")
	@Produces(MediaType.APPLICATION_JSON)
	public RestResult updateSignupDriver(@FormParam("cell") final String cell, @FormParam("name") final String name, @FormParam("sfza") final String sfzA,
			@FormParam("sfzb") final String sfzB, @FormParam("jsza") final String jszA, @FormParam("license") final String licensePlate, @FormParam("xsza") final String xszA,
			@Context HttpServletResponse response, @Context HttpServletRequest request) {
		RestResult restResult = new RestResult();

		if (StringUtils.isBlank(cell)) {
			restResult.setCode(DriverLoginResultTypeEnum.CELL_IS_BLANK.getCode());
			restResult.setMsg(DriverLoginResultTypeEnum.CELL_IS_BLANK.getMsg());
			logger.error("错误原因：{}", restResult);
			return restResult;
		}

		if (StringUtils.isBlank(name)) {
			restResult.setCode(DriverLoginResultTypeEnum.NAME_IS_BLANK.getCode());
			restResult.setMsg(DriverLoginResultTypeEnum.NAME_IS_BLANK.getMsg());
			logger.error("错误原因：{}", restResult);
			return restResult;
		}

		if (StringUtils.isBlank(sfzA)) {
			restResult.setCode(DriverLoginResultTypeEnum.SFZ_A_IS_BLANK.getCode());
			restResult.setMsg(DriverLoginResultTypeEnum.SFZ_A_IS_BLANK.getMsg());
			logger.error("错误原因：{}", restResult);
			return restResult;
		}

		if (StringUtils.isBlank(sfzB)) {
			restResult.setCode(DriverLoginResultTypeEnum.SFZ_B_IS_BLANK.getCode());
			restResult.setMsg(DriverLoginResultTypeEnum.SFZ_B_IS_BLANK.getMsg());
			logger.error("错误原因：{}", restResult);
			return restResult;
		}

		if (StringUtils.isBlank(jszA)) {
			restResult.setCode(DriverLoginResultTypeEnum.JSZ_A_IS_BLANK.getCode());
			restResult.setMsg(DriverLoginResultTypeEnum.JSZ_A_IS_BLANK.getMsg());
			logger.error("错误原因：{}", restResult);
			return restResult;
		}

		if (StringUtils.isBlank(xszA)) {
			restResult.setCode(DriverLoginResultTypeEnum.XSZ_A_IS_BLANK.getCode());
			restResult.setMsg(DriverLoginResultTypeEnum.XSZ_A_IS_BLANK.getMsg());
			logger.error("错误原因：{}", restResult);
			return restResult;
		}

		if (StringUtils.isBlank(licensePlate)) {
			restResult.setCode(DriverLoginResultTypeEnum.LICENSE_PLATE_IS_BLANK.getCode());
			restResult.setMsg(DriverLoginResultTypeEnum.LICENSE_PLATE_IS_BLANK.getMsg());
			logger.error("错误原因：{}", restResult);
			return restResult;
		}

		Driver user = driverService.get(cell);
		DriverAudit driverAudit = new DriverAudit();
		final short reset = 0;
		short uCount = 0;
		Driver driverSignup = new Driver();
		driverSignup.setCell(cell);
		if (!StringUtils.equals(name, user.getName())) {
			driverSignup.setName(name);
			driverAudit.setName(reset);
			uCount++;
		}
		if (!StringUtils.equals(sfzA, user.getSfzA())) {
			driverSignup.setSfzA(sfzA);
			driverAudit.setSfzA(reset);
			uCount++;
		}
		if (!StringUtils.equals(sfzB, user.getSfzB())) {
			driverSignup.setSfzB(sfzB);
			driverAudit.setSfzB(reset);
			uCount++;
		}
		if (!StringUtils.equals(jszA, user.getJszA())) {
			driverSignup.setJszA(jszA);
			driverAudit.setJszA(reset);
			uCount++;
		}
		if (!StringUtils.equals(xszA, user.getXszA())) {
			driverSignup.setXszA(xszA);
			driverAudit.setXszA(reset);
			uCount++;
		}
		if (!StringUtils.equals(licensePlate.toUpperCase(), user.getLicensePlate())) {
			driverSignup.setLicensePlate(licensePlate.toUpperCase());
			driverAudit.setLicensePlate(reset);
			uCount++;
		}

		logger.debug("userSignup={}", driverSignup);

		try {
			if (uCount > 0) {
				driverService.updateSignup(driverSignup, driverAudit);
				logger.info("修改司机信息注册成功");
			} else {
				logger.info("修改司机信息无改动");
				resultWrapper(restResult, DriverLoginResultTypeEnum.DRIVER_NOT_EDIT);
			}
		} catch (Exception e) {
			logger.error("修改司机信息注册失败，参数driverSignup={}，driverAudit={}", driverSignup, driverAudit);
			resultWrapper(restResult, DriverLoginResultTypeEnum.DRIVER_EDIT_FAILURE);
		}

		return restResult;
	}

	@POST
	@Path("/signup/cell")
	@Produces(MediaType.APPLICATION_JSON)
	public RestResult validSignupEmail(@FormParam("cell") final String cell, @Context HttpServletResponse response) {
		RestResult restResult = new RestResult();
		// this.enabledCrossDomain(response);

		if (StringUtils.isBlank(cell)) {
			restResult.setCode(DriverLoginResultTypeEnum.CELL_IS_BLANK.getCode());
			restResult.setMsg(DriverLoginResultTypeEnum.CELL_IS_BLANK.getMsg());
			logger.error("错误原因：{}", restResult);
			return restResult;
		}

		Driver driver = driverService.get(cell);
		if (null != driver) {
			restResult.setCode(DriverLoginResultTypeEnum.CELL_IS_EXIST.getCode());
			restResult.setMsg(DriverLoginResultTypeEnum.CELL_IS_EXIST.getMsg());
			logger.error("错误原因：{}，邮箱：{}", restResult, cell);
			return restResult;
		}

		return restResult;

	}

	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public RestResult validLoginDriver(@FormParam("cell") final String cell, @FormParam("password") final String password, @Context HttpServletResponse response) {
		RestResult restResult = new RestResult();
		// this.enabledCrossDomain(response);
		logger.debug("入参cell={},password={}", cell, password);

		try {
			if (StringUtils.isBlank(cell)) {
				restResult.setCode(DriverLoginResultTypeEnum.CELL_IS_BLANK.getCode());
				restResult.setMsg(DriverLoginResultTypeEnum.CELL_IS_BLANK.getMsg());
				logger.error("错误原因：{}", restResult);
				return restResult;
			}
			logger.debug("cell is not blank");

			if (StringUtils.isBlank(password)) {
				restResult.setCode(DriverLoginResultTypeEnum.PASSWORD_IS_BLANK.getCode());
				restResult.setMsg(DriverLoginResultTypeEnum.PASSWORD_IS_BLANK.getMsg());
				logger.error("错误原因：{}", restResult);
				return restResult;
			}
			logger.debug("password is not blank");

			Driver driver = driverService.get(cell);
			if (null == driver) {
				restResult.setCode(DriverLoginResultTypeEnum.CELL_IS_NOT_EXIST.getCode());
				restResult.setMsg(DriverLoginResultTypeEnum.CELL_IS_NOT_EXIST.getMsg());
				logger.error("错误原因：{}，邮箱：{}", restResult, cell);
				return restResult;
			}
			logger.debug("driver is not null");

			if (driver.getUsed() == 0) {
				restResult.setCode(DriverLoginResultTypeEnum.CELL_IS_NOT_USED.getCode());
				restResult.setMsg(DriverLoginResultTypeEnum.CELL_IS_NOT_USED.getMsg());
				logger.error("错误原因：{}，邮箱：{}", restResult, cell);
				return restResult;
			}
			logger.debug("driver's used = {}", driver.getUsed());

			// if (driver.getActived() == 0) {
			// restResult.setCode(DriverLoginResultTypeEnum.CELL_IS_NOT_ACTIVED.getCode());
			// restResult.setMsg(DriverLoginResultTypeEnum.CELL_IS_NOT_ACTIVED.getMsg());
			// logger.error("错误原因：{}，邮箱：{}", restResult, cell);
			// return restResult;
			// }
			// logger.debug("driver's actived = {}", driver.getActived());

			final String strEncryptPwd = DigestUtils.md5Hex(password);
			if (!strEncryptPwd.equalsIgnoreCase(driver.getPassword())) {
				restResult.setCode(DriverLoginResultTypeEnum.PASSWORD_IS_WRONG.getCode());
				restResult.setMsg(DriverLoginResultTypeEnum.PASSWORD_IS_WRONG.getMsg());
				logger.error("错误原因：{}，密码：{}", restResult, password);
				return restResult;
			}
			logger.debug("driver's strEncryptPwd = {}", strEncryptPwd);

			Driver driverParam = new Driver();
			driverParam.setPassword(driver.getPassword());
			driverParam.setCell(cell);
			CookieBuilder cookieBuilder = new DriverLoginCookieBuilder(driverParam, response);
			CookieWorker cookieWorker = new CookieWorker();
			logger.debug("开始设置司机登录cookie");
			cookieWorker.build(cookieBuilder);
			logger.debug("结束设置司机登录cookie");

		} catch (Exception e) {
			restResult.setCode(RestResultTypeEnum.ERROR.getCode());
			restResult.setMsg(RestResultTypeEnum.ERROR.getMsg());
			logger.error("错误原因：{}，错误异常：{}", restResult, e);
			return restResult;
		}

		logger.info("校验登录用户结果：{}", restResult);

		return restResult;
	}
}
