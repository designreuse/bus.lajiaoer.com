package com.demo.admin.service;

import java.util.List;

import com.demo.model.Driver;
import com.demo.model.DriverAudit;
import com.demo.model.DriverPlace;

public interface AdminDriverService {

	List<Driver> queryByPage(Short offset, Short limit);

	Driver getDriverById(Long id);

	int updateActivedById(Long id);

	int forbidById(Long id);

	int usedById(Long id);

	List<DriverPlace> queryDriverPlaceByPage(Short offset, Short limit);

	DriverPlace getDriverPlace(Long driverPlaceId);

	int updateDriverPlaceId(Long id, Long driverPlaceId);

	void updateDriverPlaceAndAudit(Long id, Long driverPlaceId);

	DriverAudit getDriverAudit(Long driverId);

	int updateDriverAudit(DriverAudit driverAudit);
}
