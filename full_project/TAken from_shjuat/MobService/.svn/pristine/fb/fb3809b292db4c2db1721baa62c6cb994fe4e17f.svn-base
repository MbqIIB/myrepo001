/**
 * 
 */
package com.npst.mobileservice.cache.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.google.common.cache.CacheLoader;
import com.npst.mobileservice.cache.Cache;

/**
 * {@link CacheManager} implementation backed by {@link GuavaCache}.
 * 
 * @author npst
 * @since 1.0
 */
public class GuavaCacheManager implements CacheManager {

	private final ConcurrentMap<String, Cache<Object, Object>> _cacheMap = new ConcurrentHashMap<>();
	private static CacheManager cacheManager = null;

	private GuavaCacheManager() {
		// Making singleton, need to discuss here
	}

	@Override
	public Cache<Object, Object> getReloadableCache(final String name, final CacheLoader<Object, Object> loader,
			final String cacheBuilderSpec) {
		Cache<Object, Object> cache = this._cacheMap.get(name);
		if (cache == null) {
			synchronized (_cacheMap) {
				cache = this._cacheMap.get(name);
				if (cache == null) {
					cache = new GuavaCache(name, loader, cacheBuilderSpec, true);
					this._cacheMap.put(name, cache);
				}
			}
		}
		return cache;
	}

	public static CacheManager getInstance() {
		if (null == cacheManager) {
			synchronized (GuavaCacheManager.class) {
				if (null == cacheManager) {
					cacheManager = new GuavaCacheManager();
				}
			}
		}
		return cacheManager;
	}

	@Override
	public Cache<Object, Object> getCache(final String name, final String cacheBuilderSpec) {
		Cache<Object, Object> cache = this._cacheMap.get(name);
		if (cache == null) {
			synchronized (_cacheMap) {
				cache = this._cacheMap.get(name);
				if (cache == null) {
					cache = new GuavaCache(name, cacheBuilderSpec, true);
					this._cacheMap.put(name, cache);
				}
			}
		}
		return cache;
	}

	@Override
	public Collection<String> getCacheNames() {
		return Collections.unmodifiableSet(this._cacheMap.keySet());
	}

}
