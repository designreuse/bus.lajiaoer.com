package com.demo.db.druid;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;

public class DruidConnection {

	private static DruidConnection connection = null;
	private static DruidDataSource dataSource = null;
	private static final ThreadLocal<DruidPooledConnection> connectionThreadLocal = new ThreadLocal<DruidPooledConnection>();

	static {
		try {
			Configuration configuration = new PropertiesConfiguration(DruidConnection.class.getClassLoader().getResource("config/db.properties"));
			Iterator<String> it = configuration.getKeys();
			Map<String, String> properties = new HashMap<String, String>();
			while (it.hasNext()) {
				String key = it.next();
				String value = configuration.getString(key);
				properties.put(key, value);
			}
			dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);

			Slf4jLogFilter slf4jLogFilter = new Slf4jLogFilter();
			slf4jLogFilter.setResultSetLogEnabled(true);

			List<Filter> listFilter = new ArrayList<Filter>();
			listFilter.add(slf4jLogFilter);
			dataSource.setProxyFilters(listFilter);
		} catch (Exception e) {
			throw new RuntimeException("DruidDataSource happens exception.", e);
		}
	}

	private DruidConnection() {

	}

	public static synchronized DruidConnection getInstance() {
		if (null == connection) {
			connection = new DruidConnection();
		}
		return connection;
	}

	public DruidDataSource getDruidDataSource() {
		return dataSource;
	}

	public DruidPooledConnection getDruidConnection() throws SQLException {

		DruidPooledConnection dpc = connectionThreadLocal.get();
		if (null == dpc) {
			dpc = dataSource.getConnection();
			connectionThreadLocal.set(dpc);
		}
		return dpc;
	}

	public DruidPooledConnection closeDruidConnection() throws SQLException {

		DruidPooledConnection dpc = connectionThreadLocal.get();
		if (null != dpc) {
			dpc.close();
			connectionThreadLocal.remove();
		}
		return dpc;
	}
}
