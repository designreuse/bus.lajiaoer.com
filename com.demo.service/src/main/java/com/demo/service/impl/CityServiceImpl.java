package com.demo.service.impl;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.core.dynamic.DynamicInvocation;
import com.demo.db.dao.CityDao;
import com.demo.db.dao.impl.CityDaoImpl;
import com.demo.model.City;
import com.demo.service.CityService;
import com.demo.utils.RedisUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CityServiceImpl implements CityService {

	private CityDao cityDao = new DynamicInvocation(CityDaoImpl.class).getProxy();

	private static final Logger LOGGER = LoggerFactory.getLogger(CityServiceImpl.class);

	@Override
	public City get(short id) {
		LOGGER.debug("获取city in redis");
		final String strRedisKey = "city:" + id;
		LOGGER.info("redis key is {}", strRedisKey);
		final String strCityJson = RedisUtils.get(strRedisKey);
		ObjectMapper mapper = new ObjectMapper();
		try {
			City city = mapper.readValue(strCityJson, City.class);
			LOGGER.info("city mapper object {}", city);
			return city;
		} catch (IOException e) {
			LOGGER.error("获取redis城市异常，信息{}", e);
		}
		return null;
	}

	@Override
	public List<City> queryAllCities() {

		return cityDao.queryAllCities();
	}

}
