package com.demo.job.city;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.core.dynamic.DynamicInvocation;
import com.demo.model.City;
import com.demo.service.CityService;
import com.demo.service.impl.CityServiceImpl;
import com.demo.utils.RedisUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CityToRedisJob implements Job {

	private static final Logger logger = LoggerFactory.getLogger(CityToRedisJob.class);
	private CityService cityService = new DynamicInvocation(CityServiceImpl.class).getProxy();

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		long startTime = System.currentTimeMillis();

		List<City> listCity = cityService.queryAllCities();
		if (null == listCity) {
			logger.debug("listcity is null");
			return;
		}

		try {
			for (City city : listCity) {
				ObjectMapper mapper = new ObjectMapper();
				RedisUtils.setex("city:" + city.getId(), 30 * 24 * 60 * 60, mapper.writeValueAsString(city));
			}
			logger.info("执行存入完成");
		} catch (JsonProcessingException e) {
			logger.error("执行发生异常", e);
		}

		long endTime = System.currentTimeMillis();
		logger.debug("执行时间：{}", endTime - startTime);
	}

}
