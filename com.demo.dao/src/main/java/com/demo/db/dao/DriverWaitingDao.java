package com.demo.db.dao;

import java.util.List;
import java.util.Map;

import com.demo.model.DriverPlace;
import com.demo.model.DriverWaiting;

public interface DriverWaitingDao {

	DriverWaiting insert(DriverWaiting driverWaiting);

	DriverWaiting get(DriverWaiting driverWaiting);

	int updateStop(DriverWaiting driverWaiting);

	List<Map<String, Object>> queryMaxDriverWaiting(DriverWaiting driverWaiting, DriverPlace driverPlace);
}
