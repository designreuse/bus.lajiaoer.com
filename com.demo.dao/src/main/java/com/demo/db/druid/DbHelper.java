package com.demo.db.druid;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

public class DbHelper {
	private DataSource dataSource = null;
	private QueryRunner queryRunner = null;

	public DbHelper() {
		this.dataSource = DruidConnection.getInstance().getDruidDataSource();
		if (null == this.dataSource) {
			throw new RuntimeException("DbHelper's dataSource exception.");
		}
		this.queryRunner = new QueryRunner(this.dataSource);
	}

	public DbHelper(DataSource dataSource) {
		this.dataSource = dataSource;
		this.queryRunner = new QueryRunner(this.dataSource);
	}

	public QueryRunner getRunner() {
		return this.queryRunner;
	}

	public Connection getConnection() throws SQLException {
		return DruidConnection.getInstance().getDruidConnection();
	}

	public void beginTransaction() throws SQLException {
		Connection conn = getConnection();
		conn.setAutoCommit(false);
	}

	public void rollback() throws SQLException {
		Connection conn = getConnection();
		conn.rollback();
		conn.setAutoCommit(true);
	}

	public void commit() throws SQLException {
		Connection conn = getConnection();
		conn.commit();
		conn.setAutoCommit(true);
	}

	public void close() throws SQLException {
		DruidConnection.getInstance().closeDruidConnection();
	}
}
