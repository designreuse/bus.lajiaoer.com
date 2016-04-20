package com.demo.db.dao.impl;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.db.dao.DriverDao;
import com.demo.db.druid.DbHelper;
import com.demo.model.Driver;
import com.demo.utils.StringUtils;

public class DriverDaoImpl implements DriverDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(DriverDaoImpl.class);

	private static final String[] DRIVER_COLUMN_ARRAY = { "id", "name", "cell", "password", "actived", "used", "sfz_a", "sfz_b", "jsz_a", "xsz_a", "license_plate",
			"driver_place_id", "created_time", "updated_time" };

	private static final String DRIVER_COLUMN_STRING = StringUtils.join(DRIVER_COLUMN_ARRAY, ",");

	private DbHelper dbHelper = new DbHelper();

	@Override
	public Driver get(Long id) {
		QueryRunner queryRunner = dbHelper.getRunner();
		Driver driver = null;
		try {
			BeanProcessor beanProcessor = new GenerousBeanProcessor();
			RowProcessor rowProcessor = new BasicRowProcessor(beanProcessor);
			driver = queryRunner.query("select " + DRIVER_COLUMN_STRING + " from demo_driver where id = ? limit 1", new BeanHandler<Driver>(Driver.class, rowProcessor), id);
		} catch (SQLException e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			LOGGER.error("{}方法发生异常，参数{}", methodName, id);
			throw new RuntimeException(e);
		}
		return driver;
	}

	@Override
	public Driver get(String cell) {
		QueryRunner queryRunner = dbHelper.getRunner();
		Driver driver = null;
		try {
			BeanProcessor beanProcessor = new GenerousBeanProcessor();
			RowProcessor rowProcessor = new BasicRowProcessor(beanProcessor);
			driver = queryRunner.query("select " + DRIVER_COLUMN_STRING + " from demo_driver where cell = ? limit 1", new BeanHandler<Driver>(Driver.class, rowProcessor), cell);
		} catch (SQLException e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			LOGGER.error("{}方法发生异常，参数{}", methodName, cell);
			throw new RuntimeException(e);
		}
		return driver;
	}

	@Override
	public Driver insert(Driver driver) {
		QueryRunner queryRunner = dbHelper.getRunner();
		try {
			Long id = queryRunner.insert(dbHelper.getConnection(),
					"insert into demo_driver(cell, password, name, sfz_a, sfz_b, jsz_a, xsz_a, license_plate) values(?, ?, ?, ?, ?, ?, ?, ?)", new ScalarHandler<Long>(),
					new Object[] { driver.getCell(), DigestUtils.md5Hex(driver.getPassword()), driver.getName(), driver.getSfzA(), driver.getSfzB(), driver.getJszA(),
							driver.getXszA(), driver.getLicensePlate() });
			if (id != null && id.longValue() > 0) {
				driver.setId(id);
			}
		} catch (SQLException e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			LOGGER.error("{}方法发生异常，参数{}", methodName, driver);
			throw new RuntimeException("插入用户发生异常", e);
		}
		return driver;
	}

	@Override
	public int updateByCell(Driver driver) {
		QueryRunner queryRunner = dbHelper.getRunner();

		try {
			int rows = queryRunner.update(dbHelper.getConnection(), "update demo_driver set password=? where cell=?", DigestUtils.md5Hex(driver.getPassword()), driver.getCell());
			return rows;
		} catch (SQLException e) {
			LOGGER.error("更新密码失败， 参数{}", driver);
			throw new RuntimeException("更新密码失败", e);
		}
	}

	@Override
	public int updateById(Driver driver) {
		QueryRunner queryRunner = dbHelper.getRunner();

		try {
			List<String> columnList = new LinkedList<String>();
			columnList.add("name");
			for (int i = 0; i < columnList.size(); i++) {
				String strColumn = columnList.get(i);
				columnList.set(i, strColumn + "=?");
			}
			final String strUpdateSet = StringUtils.join(columnList.toArray(), ",");
			if (StringUtils.isBlank(strUpdateSet)) {
				throw new RuntimeException("没有列");
			}
			StringBuilder sb = new StringBuilder();
			sb.append("update demo_driver set ");
			sb.append(strUpdateSet);
			sb.append(" where id=?");
			int rows = queryRunner.update(dbHelper.getConnection(), sb.toString(), driver.getName(), driver.getId());
			return rows;
		} catch (SQLException e) {
			LOGGER.error("更新激活失败， 参数{}", driver);
			throw new RuntimeException("更新激活失败", e);
		}
	}

	@Override
	public int update(Driver driver) {
		QueryRunner queryRunner = dbHelper.getRunner();

		try {
			List<String> columnList = new LinkedList<String>();
			List<String> valueList = new LinkedList<String>();
			if (StringUtils.isNotBlank(driver.getName())) {
				columnList.add("name");
				valueList.add(driver.getName());
			}
			if (StringUtils.isNotBlank(driver.getSfzA())) {
				columnList.add("sfz_a");
				valueList.add(driver.getSfzA());
			}
			if (StringUtils.isNotBlank(driver.getSfzB())) {
				columnList.add("sfz_b");
				valueList.add(driver.getSfzB());
			}
			if (StringUtils.isNotBlank(driver.getJszA())) {
				columnList.add("jsz_a");
				valueList.add(driver.getJszA());
			}
			if (StringUtils.isNotBlank(driver.getXszA())) {
				columnList.add("xsz_a");
				valueList.add(driver.getXszA());
			}
			if (StringUtils.isNotBlank(driver.getLicensePlate())) {
				columnList.add("license_plate");
				valueList.add(driver.getLicensePlate());
			}
			if (columnList.size() == 0) {
				return 0;
			}
			for (int i = 0; i < columnList.size(); i++) {
				String strColumn = columnList.get(i);
				columnList.set(i, strColumn + "=?");
			}
			final String strUpdateSet = StringUtils.join(columnList.toArray(), ",");
			if (StringUtils.isBlank(strUpdateSet)) {
				throw new RuntimeException("没有列");
			}
			StringBuilder sb = new StringBuilder();
			sb.append("update demo_driver set ");
			sb.append(strUpdateSet);
			sb.append(" where cell=?");
			valueList.add(driver.getCell());
			int rows = queryRunner.update(dbHelper.getConnection(), sb.toString(), valueList.toArray());
			return rows;
		} catch (SQLException e) {
			LOGGER.error("更新司机信息失败， 参数{}", driver);
			throw new RuntimeException("更新司机信息失败", e);
		}
	}

}
