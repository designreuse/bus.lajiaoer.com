package com.demo.job.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.quartz.Job;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.job.city.CityToRedisJob;

@WebListener
public class ServerStartListener implements ServletContextListener {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Job cityToRedisJob = null;
		try {
			cityToRedisJob = new CityToRedisJob();
			cityToRedisJob.execute(null);
			logger.info("服务器启动，执行city to redis job成功");
		} catch (JobExecutionException e) {
			logger.error("服务器启动，执行job异常", e);
		}

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
