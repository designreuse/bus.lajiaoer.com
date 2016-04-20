package com.demo.db.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.db.dao.CityDao;
import com.demo.db.druid.DbHelper;
import com.demo.model.City;

public class CityDaoImpl implements CityDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(CityDaoImpl.class);

	private DbHelper dbHelper = new DbHelper();

	@Override
	public City get(short id) {

		QueryRunner queryRunner = dbHelper.getRunner();
		City city = null;
		try {
			BeanProcessor beanProcessor = new GenerousBeanProcessor();
			RowProcessor rowProcessor = new BasicRowProcessor(beanProcessor);
			city = queryRunner.query("select * from demo_data_city where id = ? limit 1", new BeanHandler<City>(City.class, rowProcessor), id);
		} catch (SQLException e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			LOGGER.error("{}方法发生异常，参数{}", methodName, id);
			throw new RuntimeException(e);
		}
		return city;
	}

	@Override
	public List<City> queryAllCities() {
		QueryRunner queryRunner = dbHelper.getRunner();
		List<City> listCity = null;
		try {
			BeanProcessor beanProcessor = new GenerousBeanProcessor();
			RowProcessor rowProcessor = new BasicRowProcessor(beanProcessor);
			listCity = queryRunner.query("select * from demo_data_city", new BeanListHandler<City>(City.class, rowProcessor));
		} catch (SQLException e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			LOGGER.error("{}方法发生异常，参数{}", methodName);
			throw new RuntimeException(e);
		}
		return listCity;
	}

}
