package com.demo.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.enums.ResultType;
import com.demo.vo.RestResult;

public class BaseRestService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected void resultWrapper(RestResult restResult, ResultType resultType) {

		restResult.setCode(resultType.getCode());

		restResult.setMsg(resultType.getMsg());
	}

	// protected void enabledCrossDomain(HttpServletResponse response) {
	// response.setHeader("Access-Control-Allow-Origin", "http://bus.lajiaoer.com");
	// response.setHeader("Access-Control-Allow-Credentials", "true");
	// response.setHeader("Access-Control-Allow-Methods", "GET");
	// }
}
