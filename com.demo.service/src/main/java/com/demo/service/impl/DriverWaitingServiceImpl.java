package com.demo.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.demo.core.dynamic.DynamicInvocation;
import com.demo.db.dao.DriverWaitingDao;
import com.demo.db.dao.impl.DriverWaitingDaoImpl;
import com.demo.model.DriverPlace;
import com.demo.model.DriverWaiting;
import com.demo.service.DriverWaitingService;

public class DriverWaitingServiceImpl implements DriverWaitingService {

	private DriverWaitingDao driverWaitingDao = new DynamicInvocation(DriverWaitingDaoImpl.class).getProxy();

	@Override
	public DriverWaiting insert(DriverWaiting driverWaiting) {
		Calendar cal = Calendar.getInstance();
		driverWaiting.setStartTime(cal.getTime());
		cal.add(Calendar.MINUTE, 5);
		driverWaiting.setEndTime(cal.getTime());
		return driverWaitingDao.insert(driverWaiting);
	}

	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		System.out.println(cal.getTime());
		cal.add(Calendar.MINUTE, 5);
		System.out.println(cal.getTime());
	}

	@Override
	public DriverWaiting get(Long driverId) {
		DriverWaiting driverWaiting = new DriverWaiting();
		driverWaiting.setDriverId(driverId);
		Calendar calendar = Calendar.getInstance();
		driverWaiting.setEndTime(calendar.getTime());
		calendar.add(Calendar.MINUTE, -5);
		driverWaiting.setStartTime(calendar.getTime());
		return driverWaitingDao.get(driverWaiting);
	}

	@Override
	public int updateStop(DriverWaiting driverWaiting) {
		return driverWaitingDao.updateStop(driverWaiting);
	}

	@Override
	public List<Map<String, Object>> queryMaxDriverWaiting(Short aCityId) {
		DriverWaiting driverWaiting = new DriverWaiting();
		Calendar cal = Calendar.getInstance();
		driverWaiting.setEndTime(cal.getTime());
		cal.add(Calendar.MINUTE, -5);
		driverWaiting.setStartTime(cal.getTime());

		DriverPlace driverPlace = new DriverPlace();
		driverPlace.setaCityId(aCityId);
		return driverWaitingDao.queryMaxDriverWaiting(driverWaiting, driverPlace);
	}
}
