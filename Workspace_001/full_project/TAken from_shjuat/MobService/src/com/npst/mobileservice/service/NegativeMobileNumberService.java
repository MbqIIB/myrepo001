/**
 * 
 */
package com.npst.mobileservice.service;

import java.util.Collections;
import java.util.List;

import com.google.common.cache.CacheLoader;
import com.npst.mobileservice.cache.Cache;
import com.npst.mobileservice.cache.impl.CacheManager;
import com.npst.mobileservice.cache.impl.Caches;
import com.npst.mobileservice.cache.impl.GuavaCacheManager;
import com.npst.mobileservice.dao.NegativeMobileNumberDao;
import com.npst.mobileservice.util.CoreCacheLoader;
import com.npst.mobileservice.util.Util;

/**
 * @author npst
 *
 */
public class NegativeMobileNumberService {

	private static NegativeMobileNumberDao negativeMobileNumberDao = null;
	private static Cache<Object, Object> cache = null;
	private static CacheManager cacheManager = GuavaCacheManager.getInstance();
	private static final String NEGATIVE_MOBILE_ENTRY_KEY = Caches.NEGATIVE_MOBILE_NUMBER_CACHE.getKey();

	static {
		final String cacheBuilderSpec = Util.getProperty(Caches.NEGATIVE_MOBILE_NUMBER_CACHE.getPropKey());
		CacheLoader<Object, Object> CACHE_LOADER = new CoreCacheLoader();
		cache = cacheManager.getReloadableCache(Caches.NEGATIVE_MOBILE_NUMBER_CACHE.getName(), CACHE_LOADER,
				cacheBuilderSpec);
	}

	public List<String> findAll() {
		if (null == negativeMobileNumberDao)
			negativeMobileNumberDao = new NegativeMobileNumberDao();
		if (cache.get(NEGATIVE_MOBILE_ENTRY_KEY) != null) {
			return (List<String>) cache.get(NEGATIVE_MOBILE_ENTRY_KEY);
		}
		List<String> list = negativeMobileNumberDao.findAll();
		cache.put(NEGATIVE_MOBILE_ENTRY_KEY, list);
		return list;
	}

	public boolean isExist(final String mobileNumber) {
		return Collections.binarySearch(findAll(), mobileNumber) >= 0 ? true : false;
	}
}
