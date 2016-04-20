package com.demo.db.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.db.dao.DriverPlaceDao;
import com.demo.db.druid.DbHelper;
import com.demo.model.DriverPlace;

public class DriverPlaceDaoImpl implements DriverPlaceDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(DriverDaoImpl.class);

	private DbHelper dbHelper = new DbHelper();

	@Override
	public List<DriverPlace> queryDriverPlace(DriverPlace driverPlace) {
		QueryRunner queryRunner = dbHelper.getRunner();
		List<DriverPlace> listDriverPlace = null;
		try {
			BeanProcessor beanProcessor = new GenerousBeanProcessor();
			RowProcessor rowProcessor = new BasicRowProcessor(beanProcessor);
			listDriverPlace = queryRunner.query("select * from demo_driver_place dp where dp.a_city_id = ?", new BeanListHandler<DriverPlace>(DriverPlace.class, rowProcessor),
					driverPlace.getaCityId());
		} catch (SQLException e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			LOGGER.error("{}方法发生异常，参数{}", methodName, driverPlace);
			throw new RuntimeException(e);
		}
		return listDriverPlace;
	}

}
