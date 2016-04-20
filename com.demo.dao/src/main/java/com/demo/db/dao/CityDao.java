package com.demo.db.dao;

import java.util.List;

import com.demo.model.City;

public interface CityDao {

	City get(short id);

	List<City> queryAllCities();
}
