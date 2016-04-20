package com.demo.admin.dao;

import java.util.List;

import com.demo.model.Driver;
import com.demo.model.DriverAudit;
import com.demo.model.DriverPlace;

public interface AdminDriverDao extends AdminBaseDao {

	List<Driver> queryByPage(Short offset, Short limit);

	Driver getDriverById(Long id);

	int updateActivedById(Long id);

	int updateUsedById(Long id, short used);

	int updateDriverPlaceId(Long id, Long driverPlaceId);

	List<DriverPlace> queryDriverPlaceByPage(Short offset, Short limit);

	DriverPlace getDriverPlace(Long driverPlaceId);

	DriverAudit getDriverAudit(Long driverId);

	int updateDriverAudit(DriverAudit driverAudit);
}
