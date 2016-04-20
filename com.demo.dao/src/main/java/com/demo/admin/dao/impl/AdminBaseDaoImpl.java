package com.demo.admin.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.admin.dao.AdminBaseDao;
import com.demo.db.druid.DbHelper;

public class AdminBaseDaoImpl implements AdminBaseDao {
	final Logger logger = LoggerFactory.getLogger(getClass());
	protected DbHelper dbHelper = new DbHelper();
}
