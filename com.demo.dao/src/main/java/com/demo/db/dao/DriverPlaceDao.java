package com.demo.db.dao;

import java.util.List;

import com.demo.model.DriverPlace;

public interface DriverPlaceDao {

	List<DriverPlace> queryDriverPlace(DriverPlace driverPlace);
}
