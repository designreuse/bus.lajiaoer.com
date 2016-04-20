package com.demo.db.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.db.dao.DriverWaitingDao;
import com.demo.db.druid.DbHelper;
import com.demo.model.DriverPlace;
import com.demo.model.DriverWaiting;

public class DriverWaitingDaoImpl implements DriverWaitingDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(DriverDaoImpl.class);

	private DbHelper dbHelper = new DbHelper();

	@Override
	public DriverWaiting insert(DriverWaiting driverWaiting) {
		QueryRunner queryRunner = dbHelper.getRunner();
		try {
			Long id = queryRunner.insert(dbHelper.getConnection(), "insert into demo_driver_waiting(driver_id, driver_place_id, start_time, end_time) values(?, ?, ?, ?)",
					new ScalarHandler<Long>(),
					new Object[] { driverWaiting.getDriverId(), driverWaiting.getDriverPlaceId(), driverWaiting.getStartTime(), driverWaiting.getEndTime() });
			if (id != null && id.longValue() > 0) {
				driverWaiting.setId(id);
			}
		} catch (SQLException e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			LOGGER.error("{}方法发生异常，参数{}", methodName, driverWaiting);
			throw new RuntimeException("插入用户发生异常", e);
		}
		return driverWaiting;
	}

	@Override
	public DriverWaiting get(DriverWaiting driverWaiting) {
		QueryRunner queryRunner = dbHelper.getRunner();
		DriverWaiting dw = null;
		try {
			BeanProcessor beanProcessor = new GenerousBeanProcessor();
			RowProcessor rowProcessor = new BasicRowProcessor(beanProcessor);
			dw = queryRunner.query("select * from demo_driver_waiting where driver_id = ? and end_time > ? and start_time > ? and is_stop = 0 order by end_time desc limit 1",
					new BeanHandler<DriverWaiting>(DriverWaiting.class, rowProcessor), driverWaiting.getDriverId(), driverWaiting.getEndTime(), driverWaiting.getStartTime());
		} catch (SQLException e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			LOGGER.error("{}方法发生异常，参数{}", methodName, driverWaiting);
			throw new RuntimeException(e);
		}
		return dw;
	}

	@Override
	public int updateStop(DriverWaiting driverWaiting) {
		QueryRunner queryRunner = dbHelper.getRunner();
		try {
			int count = queryRunner.update(dbHelper.getConnection(), "update demo_driver_waiting set is_stop=1 where id = ?", driverWaiting.getId());
			return count;
		} catch (SQLException e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			LOGGER.error("{}方法发生异常，参数{}", methodName, driverWaiting);
			throw new RuntimeException("插入用户发生异常", e);
		}
	}

	@Override
	public List<Map<String, Object>> queryMaxDriverWaiting(DriverWaiting driverWaiting, DriverPlace driverPlace) {
		QueryRunner queryRunner = dbHelper.getRunner();
		List<Map<String, Object>> dw = null;
		try {
			BeanProcessor beanProcessor = new GenerousBeanProcessor();
			RowProcessor rowProcessor = new BasicRowProcessor(beanProcessor);
			StringBuilder sb = new StringBuilder();
			sb.append(
					"select dw.id,dw.driver_id,dw.driver_place_id ,dw.is_stop,dw.created_time,dw.updated_time,dw.start_time ,dw.end_time,COUNT(dw.driver_id) as 'total_waiting' ");
			sb.append("from demo_driver_waiting dw left join demo_driver_place dp on dw.driver_place_id = dp.id ");
			sb.append("where dp.a_city_id =? and ((dw.start_time > ? and dw.end_time > ?) and dw.is_stop=0) ");
			sb.append("group by dw.driver_place_id ");
			sb.append("order by dw.end_time");
			dw = queryRunner.query(sb.toString(), new MapListHandler(rowProcessor), driverPlace.getaCityId(), driverWaiting.getStartTime(), driverWaiting.getEndTime());
		} catch (SQLException e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			LOGGER.error("{}方法发生异常，参数{}", methodName, driverWaiting);
			throw new RuntimeException(e);
		}
		return dw;
	}

}
