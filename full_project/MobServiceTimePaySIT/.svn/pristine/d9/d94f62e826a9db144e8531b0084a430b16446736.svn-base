/**
 * 
 */
package com.npst.mobileservice.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.common.cache.CacheLoader;
import com.npst.mobileservice.cache.Cache;
import com.npst.mobileservice.cache.impl.CacheManager;
import com.npst.mobileservice.cache.impl.Caches;
import com.npst.mobileservice.cache.impl.GuavaCacheManager;
import com.npst.mobileservice.dao.TimePayPropertyDao;
import com.npst.mobileservice.object.TimePayPropertyVO;
import com.npst.mobileservice.util.CoreCacheLoader;
import com.npst.mobileservice.util.Util;
import com.npst.upi.hor.TimePayProperty;

/**
 * @author npst
 *
 */
public class TimePayPropertyService {

	private static final Logger log = Logger.getLogger(TimePayPropertyService.class);
	private static TimePayPropertyDao timePayPropertyDao = null;

	private static Cache<Object, Object> cache = null;
	private static CacheManager cacheManager = GuavaCacheManager.getInstance();
	private static final String TIME_PAY_PROPERTY_CACHE_ENTRY_KEY = Caches.TIME_PAY_PROPERTY_CACHE.getKey();

	static {
		final String cacheBuilderSpec = Util.getProperty(Caches.TIME_PAY_PROPERTY_CACHE.getPropKey());
		CacheLoader<Object, Object> CACHE_LOADER = new CoreCacheLoader();
		cache = cacheManager.getReloadableCache(Caches.TIME_PAY_PROPERTY_CACHE.getName(),
				CACHE_LOADER, cacheBuilderSpec);
	}

	@SuppressWarnings("unchecked")
	public List<TimePayPropertyVO> timePayProperties() {
		List<TimePayProperty> properties = null;
		List<TimePayPropertyVO> propertiesVos = new ArrayList<>();
		try {
			if (null == timePayPropertyDao)
				timePayPropertyDao = new TimePayPropertyDao();
			if (null != cache && cache.get(TIME_PAY_PROPERTY_CACHE_ENTRY_KEY) != null) {
				return (List<TimePayPropertyVO>) cache.get(TIME_PAY_PROPERTY_CACHE_ENTRY_KEY);
			}
			properties = timePayPropertyDao.timePayProperties();
			if (properties != null && properties.size() > 0) {
				for (TimePayProperty timepay : properties) {
					propertiesVos.add(new TimePayPropertyVO(timepay.getKey(), timepay.getValue()));
				}
			}
			if (null != propertiesVos && propertiesVos.size() > 0)
				cache.put(TIME_PAY_PROPERTY_CACHE_ENTRY_KEY, propertiesVos);
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
		}

		return propertiesVos;
	}

}
