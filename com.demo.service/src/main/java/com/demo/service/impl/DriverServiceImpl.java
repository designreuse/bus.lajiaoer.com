package com.demo.service.impl;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.core.dynamic.DynamicInvocation;
import com.demo.db.dao.DriverAuditDao;
import com.demo.db.dao.DriverDao;
import com.demo.db.dao.impl.DriverAuditDaoImpl;
import com.demo.db.dao.impl.DriverDaoImpl;
import com.demo.db.druid.DbHelper;
import com.demo.model.Driver;
import com.demo.model.DriverAudit;
import com.demo.service.DriverService;
import com.demo.utils.TimeBasedUuidUtils;

public class DriverServiceImpl implements DriverService {

	private static final Logger logger = LoggerFactory.getLogger(DriverServiceImpl.class);

	private DriverDao driverDao = new DynamicInvocation(DriverDaoImpl.class).getProxy();
	private DriverAuditDao driverAuditDao = new DynamicInvocation(DriverAuditDaoImpl.class).getProxy();

	@Override
	public Driver get(Long id) {
		return driverDao.get(id);
	}

	@Override
	public Driver get(String email) {
		return driverDao.get(email);
	}

	@Override
	public Driver insert(Driver driver) {
		return driverDao.insert(driver);
	}

	@Override
	public Driver signup(Driver driverSignup) {
		DbHelper dbHelper = new DbHelper();
		Driver driver = null;
		try {
			dbHelper.beginTransaction();

			driver = insert(driverSignup);
			DriverAudit driverAudit = new DriverAudit();
			driverAudit.setDriverId(driver.getId());
			driverAudit.setUuid(TimeBasedUuidUtils.getOrderedUuid());
			driverAuditDao.insert(driverAudit);

			dbHelper.commit();
		} catch (SQLException e) {
			try {
				dbHelper.rollback();
			} catch (SQLException e1) {
				logger.error("rollback异常");
			}
			throw new RuntimeException("创建driver异常");
		} finally {
			try {
				dbHelper.close();
			} catch (SQLException e) {
				logger.error("close异常");
			}
		}

		return driver;
	}

	@Override
	public int updateByCell(Driver driver) {
		return driverDao.updateByCell(driver);
	}

	@Override
	public int updateById(Driver driver) {
		return driverDao.updateById(driver);
	}

	@Override
	public void updateSignup(Driver driver, DriverAudit driverAudit) {
		DbHelper dbHelper = new DbHelper();
		try {
			dbHelper.beginTransaction();

			int effectCount = driverDao.update(driver);
			if (effectCount == 0) {
				throw new Exception("司机信息无更新");
			}
			Driver dv = driverDao.get(driver.getCell());

			driverAudit.setDriverId(dv.getId());
			effectCount = driverAuditDao.update(driverAudit);
			if (effectCount == 0) {
				throw new Exception("司机审核信息无更新");
			}
			dbHelper.commit();
		} catch (Exception e) {
			try {
				dbHelper.rollback();
			} catch (SQLException e1) {
				logger.error("rollback异常");
			}
			throw new RuntimeException("司机更新异常");
		} finally {
			try {
				dbHelper.close();
			} catch (SQLException e) {
				logger.error("close异常");
			}
		}

	}

}
