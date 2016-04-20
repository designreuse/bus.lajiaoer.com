package com.demo.rpc.service.impl;

import java.util.Map;

import javax.ws.rs.client.Client;

import org.glassfish.jersey.client.JerseyClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.rpc.service.CaptchaRpcService;
import com.demo.utils.ConfigUtils;
import com.demo.vo.RestResult;

public class CaptchaRpcServiceImpl implements CaptchaRpcService {

	private static final Logger logger = LoggerFactory.getLogger(CaptchaRpcServiceImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> generateCaptcha() {

		final String strHost = ConfigUtils.getStringValue("rest.host");
		Client jerseyClient = JerseyClientBuilder.newClient();
		RestResult restResult = jerseyClient.target(strHost).path("/captcha").path("/generate").request().get(RestResult.class);
		// Client client = ClientBuilder.newClient();
		// RestResult restResult = client.target("http://www.test.com/rest").path("/captcha").path("/generate").request().get(RestResult.class);
		logger.info("rest result={}", restResult);
		Map<String, String> resultMap = (Map<String, String>) restResult.getData();

		return resultMap;
	}

}
