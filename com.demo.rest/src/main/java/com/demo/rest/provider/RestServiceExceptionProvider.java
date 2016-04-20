package com.demo.rest.provider;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.enums.RestResultTypeEnum;
import com.demo.vo.RestResult;

@Provider
public class RestServiceExceptionProvider implements ExceptionMapper<Throwable> {

	private static final Logger logger = LoggerFactory.getLogger(RestServiceExceptionProvider.class);

	@Override
	public Response toResponse(Throwable exception) {
		logger.error("rest服务错误：", exception);
		RestResult restResult = new RestResult(RestResultTypeEnum.ERROR);
		return Response.status(Status.OK).entity(restResult).type(MediaType.APPLICATION_JSON + ";charset=UTF-8").build();
	}

}
