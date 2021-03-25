/**
 * 
 */
package com.npst.mobileservice.util;

import java.util.List;

import org.apache.log4j.Logger;

import com.google.common.cache.CacheLoader;
import com.npst.mobileservice.cache.impl.Caches;
import com.npst.mobileservice.dao.NegativeMobileNumberDao;
import com.npst.mobileservice.service.TimePayPropertyService;

/**
 * @author npst
 *
 */
public final class CoreCacheLoader extends CacheLoader<Object, Object> {
	private static final Logger log = Logger.getLogger(CoreCacheLoader.class);
	private static NegativeMobileNumberDao negativeMobileNumberDao = null;
	private static TimePayPropertyService timePayPropertyService = null;

	@Override
	public final List load(final Object key) throws Exception {
		final String k = (String) key;
		if (null == negativeMobileNumberDao)
			negativeMobileNumberDao = new NegativeMobileNumberDao();
		log.info(k + " is loaded.");
		if (Caches.NEGATIVE_MOBILE_NUMBER_CACHE.getKey().equals(k))
			return negativeMobileNumberDao.findAll();
		else if (Caches.RESERVED_VPA_CACHE.getKey().equals(k))
			return null;
		else if (Caches.TIME_PAY_PROPERTY_CACHE.getKey().equals(k))
			return timePayPropertyService.timePayProperties();
		return null;
	}

}
