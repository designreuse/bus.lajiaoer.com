package com.demo.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class ConfigUtils {

	private static final Map<String, String> configMap = new ConcurrentHashMap<String, String>();

	static {
		try {
			String path = ConfigUtils.class.getClassLoader().getResource("config").getFile();
			path = URLDecoder.decode(path, "UTF-8");
			File configFolder = new File(path);
			File[] propFiles = configFolder.listFiles();
			Configuration configuration = null;
			for (File propFile : propFiles) {
				configuration = new PropertiesConfiguration(propFile);
				if (!configuration.isEmpty()) {
					Iterator<String> it = configuration.getKeys();
					while (it.hasNext()) {
						String key = it.next();
						String val = configuration.getString(key);
						configMap.put(key, val);
					}
				}
			}
		} catch (ConfigurationException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static String getStringValue(final String key) {
		return configMap.get(key);
	}

	public static Integer getIntegerValue(final String key) {
		String strValue = configMap.get(key);
		return Integer.valueOf(strValue);
	}

	public static void main(String[] args) {
		File f = new File("D:\\微云同步盘\\83206266\\workspace\\com.demo\\com.demo.webapp\\build\\resources\\main\\config");
		System.out.println(f.isDirectory());
	}
}
