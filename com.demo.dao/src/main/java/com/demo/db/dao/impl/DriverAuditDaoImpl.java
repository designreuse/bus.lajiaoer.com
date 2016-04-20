package com.demo.db.dao.impl;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.db.dao.DriverAuditDao;
import com.demo.db.druid.DbHelper;
import com.demo.model.DriverAudit;
import com.demo.utils.StringUtils;

public class DriverAuditDaoImpl implements DriverAuditDao {
	private static final Logger logger = LoggerFactory.getLogger(DriverAuditDaoImpl.class);
	private DbHelper dbHelper = new DbHelper();

	@Override
	public DriverAudit insert(DriverAudit driverAudit) {
		QueryRunner queryRunner = dbHelper.getRunner();
		try {
			queryRunner.insert(dbHelper.getConnection(), "insert into demo_driver_audit(uuid, driver_id) values(UNHEX(?), ?)", new ScalarHandler<String>(),
					new Object[] { driverAudit.getUuid(), driverAudit.getDriverId() });
		} catch (SQLException e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			logger.error("{}方法发生异常，参数{}", methodName, driverAudit);
			throw new RuntimeException("插入用户发生异常", e);
		}
		return driverAudit;
	}

	@Override
	public int update(DriverAudit driverAudit) {
		QueryRunner queryRunner = dbHelper.getRunner();

		try {
			List<String> columnList = new LinkedList<String>();
			List<Object> valueList = new LinkedList<Object>();
			if (null != driverAudit.getName() && driverAudit.getName() == 0) {
				columnList.add("name");
				valueList.add(driverAudit.getName());
				columnList.add("name_reject");
				valueList.add("");
			}
			if (null != driverAudit.getSfzA() && driverAudit.getSfzA() == 0) {
				columnList.add("sfz_a");
				valueList.add(driverAudit.getSfzA());
				columnList.add("sfz_a_reject");
				valueList.add("");
			}
			if (null != driverAudit.getSfzB() && driverAudit.getSfzB() == 0) {
				columnList.add("sfz_b");
				valueList.add(driverAudit.getSfzB());
				columnList.add("sfz_b_reject");
				valueList.add("");
			}
			if (null != driverAudit.getJszA() && driverAudit.getJszA() == 0) {
				columnList.add("jsz_a");
				valueList.add(driverAudit.getJszA());
				columnList.add("jsz_a_reject");
				valueList.add("");
			}
			if (null != driverAudit.getXszA() && driverAudit.getXszA() == 0) {
				columnList.add("xsz_a");
				valueList.add(driverAudit.getXszA());
				columnList.add("xsz_a_reject");
				valueList.add("");
			}
			if (null != driverAudit.getLicensePlate() && driverAudit.getLicensePlate() == 0) {
				columnList.add("license_plate");
				valueList.add(driverAudit.getLicensePlate());
				columnList.add("license_plate_reject");
				valueList.add("");
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
			valueList.add(driverAudit.getDriverId());
			StringBuilder sb = new StringBuilder();
			sb.append("update demo_driver_audit set ");
			sb.append(strUpdateSet);
			sb.append(" where cell=?");
			int rows = queryRunner.update(dbHelper.getConnection(), sb.toString(), valueList.toArray());
			return rows;
		} catch (SQLException e) {
			logger.error("更新司机审核信息失败， 参数{}", driverAudit);
			throw new RuntimeException("更新司机审核信息失败", e);
		}
	}

	@Override
	public DriverAudit getByDriverId(Long driverId) {
		QueryRunner queryRunner = dbHelper.getRunner();

		DriverAudit driverAudit = null;
		BeanProcessor beanProcessor = new GenerousBeanProcessor();
		RowProcessor rowProcessor = new BasicRowProcessor(beanProcessor);
		try {
			driverAudit = queryRunner.query("select * from demo_driver_audit where driver_id = ?", new BeanHandler<DriverAudit>(DriverAudit.class, rowProcessor), driverId);
			return driverAudit;
		} catch (SQLException e) {
			logger.error("获取司机审核信息失败， 参数{}", driverId);
			throw new RuntimeException("获取司机审核信息失败", e);
		}
	}
}
