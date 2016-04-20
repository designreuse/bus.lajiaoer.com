package com.demo.db.dao;

import com.demo.model.Driver;

public interface DriverDao {

	Driver get(Long id);

	Driver get(String cell);

	Driver insert(Driver driver);

	int updateByCell(Driver driver);

	int updateById(Driver driver);

	int update(Driver driver);
}
