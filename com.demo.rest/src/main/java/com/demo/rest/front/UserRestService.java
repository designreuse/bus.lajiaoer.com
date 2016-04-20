package com.demo.rest.front;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.core.builder.CookieBuilder;
import com.demo.core.builder.CookieWorker;
import com.demo.core.builder.UserLoginCookieBuilder;
import com.demo.core.dynamic.DynamicInvocation;
import com.demo.enums.RestResultTypeEnum;
import com.demo.enums.UserLoginResultTypeEnum;
import com.demo.model.User;
import com.demo.model.UserSignupEmail;
import com.demo.rest.BaseRestService;
import com.demo.service.UserService;
import com.demo.service.UserSignupEmailService;
import com.demo.service.impl.UserServiceImpl;
import com.demo.service.impl.UserSignupEmailServiceImpl;
import com.demo.utils.StringUtils;
import com.demo.vo.RestResult;

@Path("/ajax/user/")
public class UserRestService extends BaseRestService {

	private static final Logger logger = LoggerFactory.getLogger(UserRestService.class);
	private UserService userService = new DynamicInvocation(UserServiceImpl.class).getProxy();
	private UserSignupEmailService signupEmailService = UserSignupEmailServiceImpl.getInstance();

	@POST
	@Path("/valid/signup")
	@Produces(MediaType.APPLICATION_JSON)
	public RestResult validSignupUser(@FormParam("email") final String email, @FormParam("username") final String userName, @FormParam("password") final String password,
			@Context HttpServletResponse response, @Context HttpServletRequest request) {
		RestResult ajaxResult = new RestResult();
		// this.enabledCrossDomain(response);

		if (StringUtils.isBlank(email)) {
			ajaxResult.setCode(UserLoginResultTypeEnum.EMAIL_IS_BLANK.getCode());
			ajaxResult.setMsg(UserLoginResultTypeEnum.EMAIL_IS_BLANK.getMsg());
			logger.error("错误原因：{}", ajaxResult);
			return ajaxResult;
		}

		if (StringUtils.isBlank(userName)) {
			ajaxResult.setCode(UserLoginResultTypeEnum.USERNAME_IS_BLANK.getCode());
			ajaxResult.setMsg(UserLoginResultTypeEnum.USERNAME_IS_BLANK.getMsg());
			logger.error("错误原因：{}", ajaxResult);
			return ajaxResult;
		}

		if (StringUtils.isBlank(password)) {
			ajaxResult.setCode(UserLoginResultTypeEnum.PASSWORD_IS_BLANK.getCode());
			ajaxResult.setMsg(UserLoginResultTypeEnum.PASSWORD_IS_BLANK.getMsg());
			logger.error("错误原因：{}", ajaxResult);
			return ajaxResult;
		}

		User user = userService.get(email);
		if (null != user) {
			ajaxResult.setCode(UserLoginResultTypeEnum.EMAIL_IS_EXIST.getCode());
			ajaxResult.setMsg(UserLoginResultTypeEnum.EMAIL_IS_EXIST.getMsg());
			logger.error("错误原因：{}，邮箱：{}", ajaxResult, email);
			return ajaxResult;
		}

		logger.info("strEmail={}，strPassword={}，strUsername={}", email, password, userName);

		User userSignup = new User();
		userSignup.setEmail(email);
		userSignup.setPassword(password);
		userSignup.setUserName(userName);

		logger.info("userSignup={}", userSignup);

		User userSaved = userService.signup(userSignup);
		Long sUserId = userSaved.getId();
		if (sUserId.longValue() > 0) {
			logger.info("注册成功，用户id={}", sUserId);
			UserSignupEmail signupEmail = new UserSignupEmail();
			try {
				final String strTime = String.valueOf(Calendar.getInstance().getTime());
				final String strToken = DigestUtils.md5Hex(strTime + ":" + email + ":" + userName);
				signupEmail.setToken(strToken);
				signupEmail.setUserId(sUserId);
				signupEmailService.insert(signupEmail);
			} catch (Exception e) {
				logger.error("插入注册邮件信息失败，signupEmail={}，异常={}", signupEmail, e);
			}
		} else {
			logger.error("注册失败，参数{}，结果{}", userSignup, userSaved);
			ajaxResult.setCode(RestResultTypeEnum.ERROR.getCode());
			ajaxResult.setMsg(RestResultTypeEnum.ERROR.getMsg());
		}

		return ajaxResult;
	}

	@POST
	@Path("/valid/signup/email")
	@Produces(MediaType.APPLICATION_JSON)
	public RestResult validSignupEmail(@FormParam("email") final String email, @Context HttpServletResponse response) {
		RestResult ajaxResult = new RestResult();
		// this.enabledCrossDomain(response);

		if (StringUtils.isBlank(email)) {
			ajaxResult.setCode(UserLoginResultTypeEnum.EMAIL_IS_BLANK.getCode());
			ajaxResult.setMsg(UserLoginResultTypeEnum.EMAIL_IS_BLANK.getMsg());
			logger.error("错误原因：{}", ajaxResult);
			return ajaxResult;
		}

		User user = userService.get(email);
		if (null != user) {
			ajaxResult.setCode(UserLoginResultTypeEnum.EMAIL_IS_EXIST.getCode());
			ajaxResult.setMsg(UserLoginResultTypeEnum.EMAIL_IS_EXIST.getMsg());
			logger.error("错误原因：{}，邮箱：{}", ajaxResult, email);
			return ajaxResult;
		}

		return ajaxResult;

	}

	@POST
	@Path("/valid/login")
	@Produces(MediaType.APPLICATION_JSON)
	public RestResult validLoginUser(@FormParam("email") final String email, @FormParam("password") final String password, @Context HttpServletResponse response) {
		RestResult ajaxResult = new RestResult();
		// this.enabledCrossDomain(response);

		try {
			if (StringUtils.isBlank(email)) {
				ajaxResult.setCode(UserLoginResultTypeEnum.EMAIL_IS_BLANK.getCode());
				ajaxResult.setMsg(UserLoginResultTypeEnum.EMAIL_IS_BLANK.getMsg());
				logger.error("错误原因：{}", ajaxResult);
				return ajaxResult;
			}

			if (StringUtils.isBlank(password)) {
				ajaxResult.setCode(UserLoginResultTypeEnum.PASSWORD_IS_BLANK.getCode());
				ajaxResult.setMsg(UserLoginResultTypeEnum.PASSWORD_IS_BLANK.getMsg());
				logger.error("错误原因：{}", ajaxResult);
				return ajaxResult;
			}

			User user = userService.get(email);
			if (null == user) {
				ajaxResult.setCode(UserLoginResultTypeEnum.EMAIL_IS_NOT_EXIST.getCode());
				ajaxResult.setMsg(UserLoginResultTypeEnum.EMAIL_IS_NOT_EXIST.getMsg());
				logger.error("错误原因：{}，邮箱：{}", ajaxResult, email);
				return ajaxResult;
			}

			if (user.getUsed() == 0) {
				ajaxResult.setCode(UserLoginResultTypeEnum.EMAIL_IS_NOT_USED.getCode());
				ajaxResult.setMsg(UserLoginResultTypeEnum.EMAIL_IS_NOT_USED.getMsg());
				logger.error("错误原因：{}，邮箱：{}", ajaxResult, email);
				return ajaxResult;
			}

			if (user.getActived() == 0) {
				ajaxResult.setCode(UserLoginResultTypeEnum.EMAIL_IS_NOT_ACTIVED.getCode());
				ajaxResult.setMsg(UserLoginResultTypeEnum.EMAIL_IS_NOT_ACTIVED.getMsg());
				logger.error("错误原因：{}，邮箱：{}", ajaxResult, email);
				return ajaxResult;
			}

			final String strEncryptPwd = DigestUtils.md5Hex(password);
			if (!strEncryptPwd.equalsIgnoreCase(user.getPassword())) {
				ajaxResult.setCode(UserLoginResultTypeEnum.PASSWORD_IS_WRONG.getCode());
				ajaxResult.setMsg(UserLoginResultTypeEnum.PASSWORD_IS_WRONG.getMsg());
				logger.error("错误原因：{}，密码：{}", ajaxResult, password);
				return ajaxResult;
			}

			User userParam = new User();
			userParam.setPassword(user.getPassword());
			userParam.setEmail(email);
			CookieBuilder cookieBuilder = new UserLoginCookieBuilder(userParam, response);
			CookieWorker cookieWorker = new CookieWorker();
			cookieWorker.build(cookieBuilder);

		} catch (Exception e) {
			ajaxResult.setCode(RestResultTypeEnum.ERROR.getCode());
			ajaxResult.setMsg(RestResultTypeEnum.ERROR.getMsg());
			logger.error("错误原因：{}，错误异常：{}", ajaxResult, e);
			return ajaxResult;
		}

		logger.info("校验登录用户结果：{}", ajaxResult);
		// // 放入cookie
		// // 全局cookie 变量
		// final String strCookieDomain = ConfigUtils.getStringValue("cookie.domain");
		// final String strCookieLoginPath = ConfigUtils.getStringValue("cookie.login.path");
		// Integer iCookieLoginExpire = ConfigUtils.getIntegerValue("cookie.login.expire");
		// boolean isSecure = false;
		// boolean isHttpOnly = true;
		//
		// // // 用户名+密码组合的md5值
		// final String strCryptedPassword = DigestUtils.md5Hex(password);
		// final String strCookieLoginEmailPwd = ConfigUtils.getStringValue("cookie.login.emailpwd");
		// final String strEncryptEmailPwd = DigestUtils.md5Hex(email + "*****" + strCryptedPassword);
		// CookieUtils.addCookie(response, strCookieDomain, strCookieLoginPath, strCookieLoginEmailPwd, strEncryptEmailPwd, iCookieLoginExpire, isHttpOnly, isSecure);
		//
		// final String strEncryptLoginEmail = ConfigUtils.getStringValue("login.email");
		// final String strCookieLoginEmail = ConfigUtils.getStringValue("cookie.login.email");
		// final String strEncryptEmail = AesUtils.getInstance(strEncryptLoginEmail).encryptAES(email);
		// CookieUtils.addCookie(response, strCookieDomain, strCookieLoginPath, strCookieLoginEmail, strEncryptEmail, iCookieLoginExpire, isHttpOnly, isSecure);

		return ajaxResult;
	}
}
