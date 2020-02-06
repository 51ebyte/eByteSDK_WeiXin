package com.ebyte.weixin.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {

	/**
	 * 缓存最大个数
	 */
	private static final Integer CACHE_MAX_NUMBER = 1000;
	/**
	 * 当前缓存个数
	 */
	private static Integer CURRENT_SIZE = 0;
	/**
	 * 时间一分钟
	 */
	static final Long ONE_MINUTE = 60 * 1000L;
	/**
	 * 缓存对象
	 */
	private static final Map<String, CacheObj> CACHE_OBJECT_MAP = new ConcurrentHashMap<>();
	/**
	 * 这个记录了缓存使用的最后一次的记录，最近使用的在最前面
	 */
	private static final List<String> CACHE_USE_LOG_LIST = new LinkedList<>();
	/**
	 * 清理过期缓存是否在运行
	 */
	private static volatile Boolean CLEAN_THREAD_IS_RUN = false;

	/**
	 * 设置缓存
	 * 
	 * @param key
	 * @param value
	 * @param time  缓存时长
	 */
	public static void set(String key, Object value, long time) {
		Long ttlTime = null;
		if (time <= 0L) {
			if (time == -1L) {
				ttlTime = -1L;
			} else {
				return;
			}
		}
		checkSize();
		saveCacheUseLog(key);
		CURRENT_SIZE = CURRENT_SIZE + 1;
		if (ttlTime == null) {
			ttlTime = System.currentTimeMillis() + time;
		}
		CacheObj cacheObj = new CacheObj(value, ttlTime);
		CACHE_OBJECT_MAP.put(key, cacheObj);
	}

	/**
	 * 设置缓存
	 * 
	 * @param Key
	 * @param value
	 */
	public static void set(String Key, Object value) {
		set(Key, value, -1L);
	}

	/**
	 * 获取缓存
	 * 
	 * @param key
	 * @return
	 */
	public static Object get(String key) {
		startCleanThread();
		if (check(key)) {
			saveCacheUseLog(key);
			return CACHE_OBJECT_MAP.get(key).getCacheValue();
		}
		return null;
	}

	/**
	 * 缓存是否存在
	 * 
	 * @param cacheKey
	 * @return
	 */
	public static boolean isExist(String cacheKey) {
		return check(cacheKey);
	}

	/**
	 * 删除所有缓存
	 */
	public static void clear() {
		CACHE_OBJECT_MAP.clear();
		CURRENT_SIZE = 0;
	}

	/**
	 * 删除某个缓存
	 * 
	 * @param key
	 */
	public static void clear(String key) {
		Object cacheValue = CACHE_OBJECT_MAP.remove(key);
		if (cacheValue != null) {
			CURRENT_SIZE = CURRENT_SIZE - 1;
		}
	}

	/**
	 * 判断缓存在不在,过没过期
	 * 
	 * @param key
	 * @return
	 */
	private static boolean check(String key) {
		CacheObj cacheObj = CACHE_OBJECT_MAP.get(key);
		if (cacheObj == null) {
			return false;
		}
		if (cacheObj.getTtlTime() == -1L) {
			return true;
		}
		if (cacheObj.getTtlTime() < System.currentTimeMillis()) {
			clear(key);
			return false;
		}
		return true;
	}

	/**
	 * 删除最近最久未使用的缓存
	 */
	private static void deleteLRU() {
		String cacheKey = null;
		synchronized (CACHE_USE_LOG_LIST) {
			if (CACHE_USE_LOG_LIST.size() >= CACHE_MAX_NUMBER - 10) {
				cacheKey = CACHE_USE_LOG_LIST.remove(CACHE_USE_LOG_LIST.size() - 1);
			}
		}
		if (cacheKey != null) {
			clear(cacheKey);
		}
	}

	/**
	 * 删除过期的缓存
	 */
	static void deleteTimeOut() {
		List<String> deleteKeyList = new LinkedList<>();
		for (Map.Entry<String, CacheObj> entry : CACHE_OBJECT_MAP.entrySet()) {
			if (entry.getValue().getTtlTime() < System.currentTimeMillis() && entry.getValue().getTtlTime() != -1L) {
				deleteKeyList.add(entry.getKey());
			}
		}
		for (String deleteKey : deleteKeyList) {
			clear(deleteKey);
		}
	}

	/**
	 * 检查大小 当当前大小如果已经达到最大大小 首先删除过期缓存，如果过期缓存删除过后还是达到最大缓存数目 删除最久未使用缓存
	 */
	private static void checkSize() {
		if (CURRENT_SIZE >= CACHE_MAX_NUMBER) {
			deleteTimeOut();
		}
		if (CURRENT_SIZE >= CACHE_MAX_NUMBER) {
			deleteLRU();
		}
	}

	/**
	 * 保存缓存的使用记录
	 */
	private static synchronized void saveCacheUseLog(String cacheKey) {
		synchronized (CACHE_USE_LOG_LIST) {
			CACHE_USE_LOG_LIST.remove(cacheKey);
			CACHE_USE_LOG_LIST.add(0, cacheKey);
		}
	}

	/**
	 * 设置清理线程的运行状态为正在运行
	 */
	static void setCleanThreadRun() {
		CLEAN_THREAD_IS_RUN = true;
	}

	/**
	 * 开启清理过期缓存的线程
	 */
	private static void startCleanThread() {
		if (!CLEAN_THREAD_IS_RUN) {
			CleanTimeOutThread cleanTimeOutThread = new CleanTimeOutThread();
			Thread thread = new Thread(cleanTimeOutThread);
			// 设置为后台守护线程
			thread.setDaemon(true);
			thread.start();
		}
	}

}

class CacheObj {
	/**
	 * 缓存对象
	 */
	private Object CacheValue;
	/**
	 * 缓存过期时间
	 */
	private Long ttlTime;

	CacheObj(Object cacheValue, Long ttlTime) {
		CacheValue = cacheValue;
		this.ttlTime = ttlTime;
	}

	Object getCacheValue() {
		return CacheValue;
	}

	Long getTtlTime() {
		return ttlTime;
	}

	@Override
	public String toString() {
		return "CacheObj {" + "CacheValue = " + CacheValue + ", ttlTime = " + ttlTime + '}';
	}
}

/**
 * 每一分钟清理一次过期缓存
 */
class CleanTimeOutThread implements Runnable {
	@Override
	public void run() {
		Cache.setCleanThreadRun();
		while (true) {
			Cache.deleteTimeOut();
			try {
				Thread.sleep(Cache.ONE_MINUTE);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}