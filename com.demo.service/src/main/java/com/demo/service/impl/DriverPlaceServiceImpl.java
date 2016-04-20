package com.demo.service.impl;

import java.util.List;

import com.demo.core.dynamic.DynamicInvocation;
import com.demo.db.dao.DriverPlaceDao;
import com.demo.db.dao.impl.DriverPlaceDaoImpl;
import com.demo.model.DriverPlace;
import com.demo.service.DriverPlaceService;

public class DriverPlaceServiceImpl implements DriverPlaceService {

	private DriverPlaceDao driverPlaceDao = new DynamicInvocation(DriverPlaceDaoImpl.class).getProxy();

	@Override
	public List<DriverPlace> queryDriverPlace(DriverPlace driverPlace) {
		return driverPlaceDao.queryDriverPlace(driverPlace);
	}

}
