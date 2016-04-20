package com.demo.admin.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.demo.admin.dao.AdminDriverDao;
import com.demo.model.Driver;
import com.demo.model.DriverAudit;
import com.demo.model.DriverPlace;
import com.demo.utils.StringUtils;

public class AdminDriverDaoImpl extends AdminBaseDaoImpl implements AdminDriverDao {

	@Override
	public List<Driver> queryByPage(Short offset, Short limit) {
		QueryRunner queryRunner = dbHelper.getRunner();
		List<Driver> drivers = null;
		try {
			BeanProcessor beanProcessor = new GenerousBeanProcessor();
			RowProcessor rowProcessor = new BasicRowProcessor(beanProcessor);
			drivers = queryRunner.query("select * from demo_driver order by actived asc, id asc limit ?, ?", new BeanListHandler<Driver>(Driver.class, rowProcessor), offset,
					limit);
		} catch (SQLException e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			logger.error("{}方法发生异常，参数{},{}", methodName, offset, limit);
			throw new RuntimeException(e);
		}
		return drivers;
	}

	@Override
	public Driver getDriverById(Long id) {
		QueryRunner queryRunner = dbHelper.getRunner();
		Driver driver = null;
		BeanProcessor beanProcessor = new GenerousBeanProcessor();
		RowProcessor rowProcessor = new BasicRowProcessor(beanProcessor);
		try {
			driver = queryRunner.query("select * from demo_driver where id=? limit 1", new BeanHandler<Driver>(Driver.class, rowProcessor), id);
		} catch (SQLException e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			logger.error("{}方法发生异常，参数{},{}", methodName, id);
			throw new RuntimeException(e);
		}
		return driver;
	}

	@Override
	public int updateActivedById(Long id) {
		QueryRunner queryRunner = dbHelper.getRunner();
		try {
			int rows = queryRunner.update(dbHelper.getConnection(), "update demo_driver set actived=1 where id=?", id);
			return rows;
		} catch (SQLException e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			logger.error("{}方法发生异常，参数{},{}", methodName, id);
			throw new RuntimeException(e);
		}
	}

	@Override
	public int updateUsedById(Long id, short used) {
		QueryRunner queryRunner = dbHelper.getRunner();
		try {
			int rows = queryRunner.update(dbHelper.getConnection(), "update demo_driver set used=? where id=?", used, id);
			return rows;
		} catch (SQLException e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			logger.error("{}方法发生异常，参数{},{}", methodName, id);
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<DriverPlace> queryDriverPlaceByPage(Short offset, Short limit) {
		QueryRunner queryRunner = dbHelper.getRunner();
		List<DriverPlace> listDriverPlace = null;
		try {
			BeanProcessor beanProcessor = new GenerousBeanProcessor();
			RowProcessor rowProcessor = new BasicRowProcessor(beanProcessor);
			if (null != offset && null != limit) {
				listDriverPlace = queryRunner.query("select * from demo_driver_place limit ?, ?", new BeanListHandler<DriverPlace>(DriverPlace.class, rowProcessor), offset, limit);
			} else {
				logger.error("offset={}, limit={}", offset, limit);
				throw new RuntimeException("offset or limit's value is not correct.");
			}
		} catch (SQLException e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			logger.error("{}方法发生异常，参数{},{}", methodName, offset, limit);
			throw new RuntimeException(e);
		}
		return listDriverPlace;
	}

	@Override
	public DriverPlace getDriverPlace(Long driverPlaceId) {
		QueryRunner queryRunner = dbHelper.getRunner();
		DriverPlace driverPlace = null;
		BeanProcessor beanProcessor = new GenerousBeanProcessor();
		RowProcessor rowProcessor = new BasicRowProcessor(beanProcessor);
		try {
			driverPlace = queryRunner.query("select * from demo_driver_place where id=? limit 1", new BeanHandler<DriverPlace>(DriverPlace.class, rowProcessor), driverPlaceId);
		} catch (SQLException e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			logger.error("{}方法发生异常，参数{}", methodName, driverPlaceId);
			throw new RuntimeException(e);
		}
		return driverPlace;
	}

	@Override
	public int updateDriverPlaceId(Long id, Long driverPlaceId) {
		QueryRunner queryRunner = dbHelper.getRunner();
		try {
			int rows = queryRunner.update(dbHelper.getConnection(), "update demo_driver set driver_place_id = ? where id=?", driverPlaceId, id);
			return rows;
		} catch (SQLException e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			logger.error("{}方法发生异常，参数{},{}", methodName, id, driverPlaceId);
			throw new RuntimeException(e);
		}
	}

	@Override
	public DriverAudit getDriverAudit(Long driverId) {
		QueryRunner queryRunner = dbHelper.getRunner();
		DriverAudit driverAudit = null;
		BeanProcessor beanProcessor = new GenerousBeanProcessor();
		RowProcessor rowProcessor = new BasicRowProcessor(beanProcessor);
		try {
			driverAudit = queryRunner.query(
					"select HEX(uuid) as uuid,name,cell,sfz_a,sfz_b,jsz_a,xsz_a,driver_id,driver_place_id,license_plate,name_reject,cell_reject,sfz_a_reject,sfz_b_reject,jsz_a_reject,xsz_a_reject,license_plate_reject,created_time,updated_time from demo_driver_audit where driver_id=? limit 1",
					new BeanHandler<DriverAudit>(DriverAudit.class, rowProcessor), driverId);
		} catch (SQLException e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			logger.error("{}方法发生异常，参数{}", methodName, driverId);
			throw new RuntimeException(e);
		}
		return driverAudit;
	}

	@Override
	public int updateDriverAudit(DriverAudit driverAudit) {
		QueryRunner queryRunner = dbHelper.getRunner();
		try {
			List<String> setList = new ArrayList<String>();
			List<Object> paramList = new ArrayList<Object>();
			// name
			if (null != driverAudit.getName() && driverAudit.getName() != 0) {
				setList.add("name=?");
				paramList.add(driverAudit.getName());
				if (driverAudit.getName() == 2) {
					setList.add("name_reject=?");
					paramList.add(driverAudit.getNameReject());
				}
			}
			// cell
			if (null != driverAudit.getCell() && driverAudit.getCell() != 0) {
				setList.add("cell=?");
				paramList.add(driverAudit.getCell());
				if (driverAudit.getCell() == 2) {
					setList.add("cell_reject=?");
					paramList.add(driverAudit.getCellReject());
				}
			}
			// driver_place_id
			if (null != driverAudit.getDriverPlaceId() && driverAudit.getDriverPlaceId() != 0) {
				setList.add("driver_place_id=?");
				paramList.add(driverAudit.getDriverPlaceId());
			}
			// sfz_a
			if (null != driverAudit.getSfzA() && driverAudit.getSfzA() != 0) {
				setList.add("sfz_a=?");
				paramList.add(driverAudit.getSfzA());
				if (driverAudit.getSfzA() == 2) {
					setList.add("sfz_a_reject=?");
					paramList.add(driverAudit.getSfzAReject());
				}
			}
			// sfz_b
			if (null != driverAudit.getSfzB() && driverAudit.getSfzB() != 0) {
				setList.add("sfz_b=?");
				paramList.add(driverAudit.getSfzB());
				if (driverAudit.getSfzB() == 2) {
					setList.add("sfz_b_reject=?");
					paramList.add(driverAudit.getSfzBReject());
				}
			}
			// jsz_a
			if (null != driverAudit.getJszA() && driverAudit.getJszA() != 0) {
				setList.add("jsz_a=?");
				paramList.add(driverAudit.getJszA());
				if (driverAudit.getJszA() == 2) {
					setList.add("jsz_a_reject=?");
					paramList.add(driverAudit.getJszAReject());
				}
			}
			// xsz_a
			if (null != driverAudit.getXszA() && driverAudit.getXszA() != 0) {
				setList.add("xsz_a=?");
				paramList.add(driverAudit.getXszA());
				if (driverAudit.getXszA() == 2) {
					setList.add("xsz_a_reject=?");
					paramList.add(driverAudit.getXszAReject());
				}
			}
			// license_plate
			if (null != driverAudit.getLicensePlate() && driverAudit.getLicensePlate() != 0) {
				setList.add("license_plate=?");
				paramList.add(driverAudit.getLicensePlate());
				if (driverAudit.getLicensePlate() == 2) {
					setList.add("license_plate_reject=?");
					paramList.add(driverAudit.getLicensePlateReject());
				}
			}
			// driver_id
			// if (null != driverAudit.getDriverId()) {
			// setList.add("driver_id=?");
			// paramList.add(driverAudit.getDriverId());
			// }
			paramList.add(driverAudit.getDriverId());
			int rows = queryRunner.update(dbHelper.getConnection(), "update demo_driver_audit set " + StringUtils.join(setList, ",") + " where driver_id=?", paramList.toArray());
			return rows;
		} catch (SQLException e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			logger.error("{}方法发生异常，参数{}", methodName, driverAudit);
			throw new RuntimeException(e);
		}
	}

}
