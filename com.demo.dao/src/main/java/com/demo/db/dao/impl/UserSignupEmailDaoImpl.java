package com.demo.db.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.db.dao.UserSignupEmailDao;
import com.demo.db.druid.DbHelper;
import com.demo.model.UserSignupEmail;
import com.demo.vo.UserVo;

public class UserSignupEmailDaoImpl implements UserSignupEmailDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserSignupEmailDaoImpl.class);

	private static UserSignupEmailDaoImpl instance = new UserSignupEmailDaoImpl();

	private UserSignupEmailDaoImpl() {
	}

	public static UserSignupEmailDaoImpl getInstance() {
		return instance;
	}

	private DbHelper dbHelper = new DbHelper();

	@Override
	public UserSignupEmail insert(UserSignupEmail signupEmail) {
		QueryRunner queryRunner = dbHelper.getRunner();
		try {
			Long id = queryRunner.insert("insert into demo_user_signup_email(user_id, token) values(?, ?)", new ScalarHandler<Long>(),
					new Object[] { signupEmail.getUserId(), signupEmail.getToken() });
			if (id != null && id.longValue() > 0) {
				signupEmail.setId(id);
			}
		} catch (SQLException e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			LOGGER.error("{}方法发生异常，参数{}", methodName, signupEmail);
			throw new RuntimeException("插入用户发生异常", e);
		}
		return signupEmail;
	}

	@Override
	public UserSignupEmail get(Long id) {
		QueryRunner queryRunner = dbHelper.getRunner();
		UserSignupEmail signupEmail = null;
		try {
			BeanProcessor beanProcessor = new GenerousBeanProcessor();
			RowProcessor rowProcessor = new BasicRowProcessor(beanProcessor);
			signupEmail = queryRunner.query("select * from demo_user_signup_email where id = ? limit 1", new BeanHandler<UserSignupEmail>(UserSignupEmail.class, rowProcessor), id);
		} catch (SQLException e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			LOGGER.error("{}方法发生异常，参数{}", methodName, id);
			throw new RuntimeException(e);
		}
		return signupEmail;
	}

	@Override
	public UserSignupEmail get(String token) {
		QueryRunner queryRunner = dbHelper.getRunner();
		UserSignupEmail signupEmail = null;
		try {
			BeanProcessor beanProcessor = new GenerousBeanProcessor();
			RowProcessor rowProcessor = new BasicRowProcessor(beanProcessor);
			signupEmail = queryRunner.query("select * from demo_user_signup_email where token = ? limit 1", new BeanHandler<UserSignupEmail>(UserSignupEmail.class, rowProcessor), token);
		} catch (SQLException e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			LOGGER.error("{}方法发生异常，参数{}", methodName, token);
			throw new RuntimeException(e);
		}
		return signupEmail;
	}

	@Override
	public List<UserVo> querySchedulerByLimit(int limit) {
		QueryRunner queryRunner = dbHelper.getRunner();
		List<UserVo> listUser = null;
		try {
			BeanProcessor beanProcessor = new GenerousBeanProcessor();
			RowProcessor rowProcessor = new BasicRowProcessor(beanProcessor);
			listUser = queryRunner.query(
					"select u.email, u.username, se.token, se.id as seid from demo_user u right join demo_user_signup_email se on u.id = se.user_id where u.actived = 0 and se.sended = 0 and se.send_count < 3 limit ?",
					new BeanListHandler<UserVo>(UserVo.class, rowProcessor), limit);
		} catch (SQLException e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			LOGGER.error("{}方法发生异常，参数{}", methodName, limit);
			throw new RuntimeException(e);
		}
		return listUser;
	}

	@Override
	public int updateSended(Long id) {
		QueryRunner queryRunner = dbHelper.getRunner();

		try {
			int rows = queryRunner.update("update demo_user_signup_email set sended=?, send_count=send_count+1 where id=?", 1, id);
			return rows;
		} catch (SQLException e) {
			LOGGER.error("更新密码失败， 参数{}", id);
			throw new RuntimeException("更新密码失败", e);
		}
	}

	@Override
	public int updateSendCount(Long id) {
		QueryRunner queryRunner = dbHelper.getRunner();

		try {
			int rows = queryRunner.update("update demo_user_signup_email set send_count=send_count+1 where id=?", id);
			return rows;
		} catch (SQLException e) {
			LOGGER.error("更新密码失败， 参数{}", id);
			throw new RuntimeException("更新密码失败", e);
		}
	}

}
