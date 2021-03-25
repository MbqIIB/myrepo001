package com.npst.mobileservice.cache.impl;

import java.util.Collection;

import com.google.common.cache.CacheLoader;
import com.npst.mobileservice.cache.Cache;

/**
 * Central cache manager
 * 
 * @author npst
 *
 */
public interface CacheManager {
	/**
	 * Return the cache associated with the given name.
	 * 
	 * @param name
	 *            the cache identifier (must not be {@code null}), loader, config
	 * @return the associated cache, or create one if none found and associates with
	 *         given name
	 */
	Cache<Object, Object> getReloadableCache(final String name, final CacheLoader<Object, Object> loader,
			final String cacheBuilderSpec);

	/**
	 * Return the cache associated with the given name.
	 * 
	 * @param name
	 *            the cache identifier (must not be {@code null})
	 * @return the associated cache, or create one if none found and associates with
	 *         given name
	 */
	Cache<Object, Object> getCache(final String name, final String cacheBuilderSpec);

	/**
	 * Return a collection of the cache names known by this manager.
	 * 
	 * @return the names of all caches known by the cache manager
	 */
	Collection<String> getCacheNames();
}
