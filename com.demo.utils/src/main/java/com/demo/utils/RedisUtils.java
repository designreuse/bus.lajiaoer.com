package com.demo.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisConnectionPool;
import com.lambdaworks.redis.api.sync.RedisCommands;

/**
 * redis辅助文件， 重试3次，超时时间默认
 * 
 * @author xuzhongliang
 *
 */
public class RedisUtils {
	private static final RedisClient redisClient;
	private static final RedisConnectionPool<RedisCommands<String, String>> pool;
	private static final Logger logger = LoggerFactory.getLogger(RedisUtils.class);

	static {
		List<String> searchList = new ArrayList<String>(4);
		searchList.add("{redis.scheme}");
		searchList.add("{redis.host}");
		searchList.add("{redis.port}");
		searchList.add("{redis.db.num}");

		List<String> replacementList = new ArrayList<String>(4);
		replacementList.add(ConfigUtils.getStringValue("redis.scheme"));
		replacementList.add(ConfigUtils.getStringValue("redis.host"));
		replacementList.add(ConfigUtils.getStringValue("redis.port"));
		replacementList.add(ConfigUtils.getStringValue("redis.db.num"));

		String redisUri = StringUtils.replaceEach("{redis.scheme}://{redis.host}:{redis.port}/{redis.db.num}", searchList.toArray(new String[] {}),
				replacementList.toArray(new String[] {}));
		redisClient = RedisClient.create(redisUri);
		pool = redisClient.pool();
	}

	public static boolean hset(final String key, final String member, final String val) {
		RedisCommands<String, String> cmd = null;
		boolean bool = false;
		short i = 0;
		while (i < 3) {
			try {
				cmd = pool.allocateConnection();
				bool = cmd.hset(key, member, val);
				break;
			} catch (Exception e) {
				logger.error("method hset exception, {}", e);
			} finally {
				if (null != cmd) {
					pool.freeConnection(cmd);
				}
			}
		}

		return bool;
	}

	public static String hget(final String key, final String member) {
		RedisCommands<String, String> cmd = null;
		String str = null;
		short i = 0;
		while (i < 3) {
			try {
				cmd = pool.allocateConnection();
				str = cmd.hget(key, member);
				break;
			} catch (Exception e) {
				logger.error("method hset exception, {}", e);
			} finally {
				if (null != cmd) {
					pool.freeConnection(cmd);
				}
			}
		}

		return str;
	}

	public static long hdel(final String key, final String member) {
		RedisCommands<String, String> cmd = null;
		long count = 0l;
		short i = 0;
		while (i < 3) {
			try {
				cmd = pool.allocateConnection();
				count = cmd.hdel(key, member);
				break;
			} catch (Exception e) {
				logger.error("method hset exception, {}", e);
			} finally {
				if (null != cmd) {
					pool.freeConnection(cmd);
				}
			}
		}

		return count;
	}

	/**
	 * 设置key和val
	 * 
	 * @param key
	 * @param val
	 * @return
	 */
	public static String set(final String key, final String val) {
		RedisCommands<String, String> cmd = null;
		String str = null;
		short i = 0;
		while (i < 3) {
			try {
				cmd = pool.allocateConnection();
				str = cmd.set(key, val);
				break;
			} catch (Exception e) {
				logger.error("method set exception, {}", e);
			} finally {
				if (null != cmd) {
					pool.freeConnection(cmd);
				}
			}
		}

		return str;
	}

	/**
	 * 设置key和val过期时间
	 * 
	 * @param key
	 * @param seconds
	 * @param val
	 * @return
	 */
	public static String setex(final String key, final long seconds, final String val) {
		RedisCommands<String, String> cmd = null;
		String str = null;
		short i = 0;
		while (i < 3) {
			try {
				cmd = pool.allocateConnection();
				str = cmd.setex(key, seconds, val);
				break;
			} catch (Exception e) {
				logger.error("method setex exception, {}", e);
			} finally {
				if (null != cmd) {
					pool.freeConnection(cmd);
				}
			}
		}

		return str;
	}

	/**
	 * 获取key
	 * 
	 * @param key
	 * @return
	 */
	public static String get(final String key) {
		RedisCommands<String, String> cmd = null;
		String str = null;
		short i = 0;
		while (i < 3) {
			try {
				cmd = pool.allocateConnection();
				str = cmd.get(key);
				break;
			} catch (Exception e) {
				logger.error("method get exception, {}", e);
			} finally {
				if (null != cmd) {
					pool.freeConnection(cmd);
				}
			}
		}

		return str;
	}

}
