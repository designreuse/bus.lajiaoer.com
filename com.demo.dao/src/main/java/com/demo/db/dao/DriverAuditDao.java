package com.demo.db.dao;

import com.demo.model.DriverAudit;

public interface DriverAuditDao {

	DriverAudit insert(DriverAudit driverAudit);

	int update(DriverAudit driverAudit);

	DriverAudit getByDriverId(Long driverId);
}
