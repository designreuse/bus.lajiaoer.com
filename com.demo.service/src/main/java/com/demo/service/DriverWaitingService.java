package com.demo.service;

import java.util.List;
import java.util.Map;

import com.demo.model.DriverWaiting;

public interface DriverWaitingService {

	DriverWaiting insert(DriverWaiting driverWaiting);

	DriverWaiting get(Long driverId);

	int updateStop(DriverWaiting driverWaiting);

	List<Map<String, Object>> queryMaxDriverWaiting(Short aCityId);
}
