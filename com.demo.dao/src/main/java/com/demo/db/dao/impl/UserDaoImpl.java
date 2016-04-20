package com.demo.db.dao.impl;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.db.dao.UserDao;
import com.demo.db.druid.DbHelper;
import com.demo.model.User;
import com.demo.utils.StringUtils;

public class UserDaoImpl implements UserDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserDao.class);

	private DbHelper dbHelper = new DbHelper();

	@Override
	public User get(Long id) {
		QueryRunner queryRunner = dbHelper.getRunner();
		User user = null;
		try {
			BeanProcessor beanProcessor = new GenerousBeanProcessor();
			RowProcessor rowProcessor = new BasicRowProcessor(beanProcessor);
			user = queryRunner.query("select * from demo_user where id = ? limit 1", new BeanHandler<User>(User.class, rowProcessor), id);
		} catch (SQLException e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			LOGGER.error("{}方法发生异常，参数{}", methodName, id);
			throw new RuntimeException(e);
		}
		return user;
	}

	@Override
	public User get(String email) {
		QueryRunner queryRunner = dbHelper.getRunner();
		User user = null;
		try {
			BeanProcessor beanProcessor = new GenerousBeanProcessor();
			RowProcessor rowProcessor = new BasicRowProcessor(beanProcessor);
			user = queryRunner.query("select * from demo_user where email = ? limit 1", new BeanHandler<User>(User.class, rowProcessor), email);
		} catch (SQLException e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			LOGGER.error("{}方法发生异常，参数{}", methodName, email);
			throw new RuntimeException(e);
		}
		return user;
	}

	@Override
	public User insert(User user) {
		QueryRunner queryRunner = dbHelper.getRunner();
		try {
			Long id = queryRunner.insert(dbHelper.getConnection(), "insert into demo_user(email, password, username, created_time) values(?, ?, ?, now())",
					new ScalarHandler<Long>(), new Object[] { user.getEmail(), DigestUtils.md5Hex(user.getPassword()), user.getUserName() });
			if (id != null && id.longValue() > 0) {
				user.setId(id);
			}
		} catch (SQLException e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			LOGGER.error("{}方法发生异常，参数{}", methodName, user);
			throw new RuntimeException("插入用户发生异常", e);
		}
		return user;
	}

	@Override
	public int updateByEmail(User user) {
		QueryRunner queryRunner = dbHelper.getRunner();

		try {
			int rows = queryRunner.update(dbHelper.getConnection(), "update demo_user set password=? where email=?", DigestUtils.md5Hex(user.getPassword()), user.getEmail());
			return rows;
		} catch (SQLException e) {
			LOGGER.error("更新密码失败， 参数{}", user);
			throw new RuntimeException("更新密码失败", e);
		}
	}

	@Override
	public int updateById(User user) {
		QueryRunner queryRunner = dbHelper.getRunner();

		try {
			List<String> columnList = new LinkedList<String>();
			columnList.add("actived");
			for (int i = 0; i < columnList.size(); i++) {
				String strColumn = columnList.get(i);
				columnList.set(i, strColumn + "=?");
			}
			final String strUpdateSet = StringUtils.join(columnList.toArray(), ",");
			if (StringUtils.isBlank(strUpdateSet)) {
				throw new RuntimeException("没有列");
			}
			StringBuilder sb = new StringBuilder();
			sb.append("update demo_user set ");
			sb.append(strUpdateSet);
			sb.append(" where id=?");
			int rows = queryRunner.update(dbHelper.getConnection(), sb.toString(), user.getActived(), user.getId());
			return rows;
		} catch (SQLException e) {
			LOGGER.error("更新激活失败， 参数{}", user);
			throw new RuntimeException("更新激活失败", e);
		}
	}

}
