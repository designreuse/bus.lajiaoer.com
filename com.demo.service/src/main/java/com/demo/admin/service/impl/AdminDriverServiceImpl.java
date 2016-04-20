package com.demo.admin.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.demo.admin.dao.AdminDriverDao;
import com.demo.admin.dao.impl.AdminDriverDaoImpl;
import com.demo.admin.service.AdminDriverService;
import com.demo.core.dynamic.DynamicInvocation;
import com.demo.db.druid.DbHelper;
import com.demo.model.Driver;
import com.demo.model.DriverAudit;
import com.demo.model.DriverPlace;

public class AdminDriverServiceImpl implements AdminDriverService {
	private AdminDriverDao driverDao = new DynamicInvocation(AdminDriverDaoImpl.class).getProxy();

	@Override
	public List<Driver> queryByPage(Short offset, Short limit) {
		return driverDao.queryByPage(offset, limit);
	}

	@Override
	public Driver getDriverById(Long id) {
		return driverDao.getDriverById(id);
	}

	@Override
	public int updateActivedById(Long id) {
		return driverDao.updateActivedById(id);
	}

	@Override
	public int forbidById(Long id) {
		return driverDao.updateUsedById(id, (short) 0);
	}

	@Override
	public int usedById(Long id) {
		return driverDao.updateUsedById(id, (short) 1);
	}

	@Override
	public List<DriverPlace> queryDriverPlaceByPage(Short offset, Short limit) {
		return driverDao.queryDriverPlaceByPage(offset, limit);
	}

	@Override
	public DriverPlace getDriverPlace(Long driverPlaceId) {
		return driverDao.getDriverPlace(driverPlaceId);
	}

	@Override
	public int updateDriverPlaceId(Long id, Long driverPlaceId) {
		return driverDao.updateDriverPlaceId(id, driverPlaceId);
	}

	@Override
	public DriverAudit getDriverAudit(Long driverId) {
		return driverDao.getDriverAudit(driverId);
	}

	@Override
	public int updateDriverAudit(DriverAudit driverAudit) {
		return driverDao.updateDriverAudit(driverAudit);
	}

	@Override
	public void updateDriverPlaceAndAudit(Long id, Long driverPlaceId) {
		DbHelper dbHelper = new DbHelper();
		try {
			dbHelper.beginTransaction();
			driverDao.updateDriverPlaceId(id, driverPlaceId);
			DriverAudit audit = new DriverAudit();
			audit.setDriverPlaceId(DriverAudit.approve());
			audit.setDriverId(id);
			driverDao.updateDriverAudit(audit);
			dbHelper.commit();
			// throw new SQLException();
		} catch (SQLException e) {
			try {
				dbHelper.rollback();
			} catch (SQLException e1) {
			}
			throw new RuntimeException();
		} finally {
			try {
				dbHelper.close();
			} catch (SQLException e) {
			}
		}

	}
}
