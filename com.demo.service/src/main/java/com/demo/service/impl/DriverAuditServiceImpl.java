package com.demo.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.core.dynamic.DynamicInvocation;
import com.demo.db.dao.DriverAuditDao;
import com.demo.db.dao.impl.DriverAuditDaoImpl;
import com.demo.model.DriverAudit;
import com.demo.service.DriverAuditService;

public class DriverAuditServiceImpl implements DriverAuditService {

	private static final Logger logger = LoggerFactory.getLogger(DriverAuditServiceImpl.class);
	private DriverAuditDao driverAuditDao = new DynamicInvocation(DriverAuditDaoImpl.class).getProxy();

	@Override
	public DriverAudit getDriverAuditByDriverId(Long driverId) {
		return driverAuditDao.getByDriverId(driverId);
	}
}
