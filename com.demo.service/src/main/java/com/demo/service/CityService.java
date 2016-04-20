package com.demo.service;

import java.util.List;

import com.demo.model.City;

public interface CityService {
	City get(short id);

	List<City> queryAllCities();
}
