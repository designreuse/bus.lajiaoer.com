package com.demo.web.servlet.driver;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.support.json.JSONUtils;
import com.demo.core.dynamic.DynamicInvocation;
import com.demo.rpc.service.CaptchaRpcService;
import com.demo.rpc.service.impl.CaptchaRpcServiceImpl;
import com.demo.utils.IpUtils;
import com.demo.utils.RedisUtils;
import com.demo.web.servlet.BaseServlet;

@WebServlet(asyncSupported = true, urlPatterns = { "/driver/captcha" })
public class DriverCaptchaServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(DriverCaptchaServlet.class);

	private CaptchaRpcService captchaRpcService = new DynamicInvocation(CaptchaRpcServiceImpl.class).getProxy();

	public DriverCaptchaServlet() {
		super();
	}

	@Override
	protected void doJspGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> captchaMap = captchaRpcService.generateCaptcha();
		final String strIp = IpUtils.getIpAddr(request);
		StringBuilder sb = new StringBuilder(strIp);
		final String strTimeMills = String.valueOf(System.currentTimeMillis());
		sb.append(strTimeMills);
		final String strToken = captchaMap.get("token");
		sb.append(strToken);
		final String key = DigestUtils.md5Hex(sb.toString());
		RedisUtils.setex(key, 300L, strToken);
		captchaMap.put("alias", key);
		LOGGER.info("Driver 验证码={}", captchaMap);
		final String strCaptchaJson = JSONUtils.toJSONString(captchaMap);
		response.getWriter().print(strCaptchaJson);
		response.getWriter().flush();
	}

	@Override
	protected void doJspPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doJspGet(request, response);
	}

}
