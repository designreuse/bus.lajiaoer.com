package com.demo.service;

import com.demo.model.Driver;
import com.demo.model.DriverAudit;

public interface DriverService {

	Driver get(Long id);

	Driver get(String cell);

	Driver insert(Driver user);

	Driver signup(Driver userSignup);

	int updateByCell(Driver user);

	int updateById(Driver user);

	void updateSignup(Driver driver, DriverAudit driverAudit);
}
