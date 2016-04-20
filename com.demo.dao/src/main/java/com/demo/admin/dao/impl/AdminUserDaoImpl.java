package com.demo.admin.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.demo.admin.dao.AdminUserDao;
import com.demo.model.admin.AdminUser;

public class AdminUserDaoImpl extends AdminBaseDaoImpl implements AdminUserDao {

	@Override
	public AdminUser getAdminUser(Long id) {
		QueryRunner queryRunner = dbHelper.getRunner();
		BeanProcessor beanProcessor = new GenerousBeanProcessor();
		RowProcessor rowProcessor = new BasicRowProcessor(beanProcessor);
		AdminUser user = null;
		try {
			user = queryRunner.query("select * from demo_admin_user where id = ? limit 1", new BeanHandler<AdminUser>(AdminUser.class, rowProcessor), id);
		} catch (SQLException e) {
			logger.error("getAdminUser id={} 异常{}", id, e);
		}
		return user;
	}

	@Override
	public AdminUser getAdminUser(String name) {
		QueryRunner queryRunner = dbHelper.getRunner();
		BeanProcessor beanProcessor = new GenerousBeanProcessor();
		RowProcessor rowProcessor = new BasicRowProcessor(beanProcessor);
		AdminUser user = null;
		try {
			user = queryRunner.query("select * from demo_admin_user where name = ? limit 1", new BeanHandler<AdminUser>(AdminUser.class, rowProcessor), name);
		} catch (SQLException e) {
			logger.error("getAdminUser name={} 异常{}", name, e);
		}
		return user;
	}

}
