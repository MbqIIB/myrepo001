/**
 * 
 */
package com.npst.mobileservice.cache.impl;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheBuilderSpec;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.npst.mobileservice.cache.Cache;

/**
 * Cache implementation backed by google guava cache
 * 
 * @author npst
 *
 */
public class GuavaCache implements Cache<Object, Object> {

	private static final Logger log = Logger.getLogger(GuavaCache.class);
	private static final Object NULL_HOLDER = new NullHolder();

	private final String name;

	private final com.google.common.cache.Cache<Object, Object> store;

	private final boolean allowNullValues;

	/**
	 * Creates cache with refreshing or reloading strategy
	 * 
	 * @param name
	 * @param loader
	 * @param config
	 */
	public GuavaCache(final String name, final CacheLoader<Object, Object> loader, final String cacheBuilderSpec,
			final boolean allowNullValues) {
		this.name = name;
		this.allowNullValues = allowNullValues;
		store = createCache(loader, cacheBuilderSpec);
	}

	/**
	 * Creates simple cache without refreshing or reloading strategy
	 * 
	 * @param name
	 */
	public GuavaCache(final String name, final String cacheBuilderSpec, final boolean allowNullValues) {
		this.name = name;
		this.allowNullValues = allowNullValues;
		final CacheBuilderSpec spec = CacheBuilderSpec.parse(cacheBuilderSpec);
		CacheBuilder.newBuilder();
		store = CacheBuilder.from(spec).build();
	}

	public com.google.common.cache.Cache<Object, Object> createCache(final CacheLoader<Object, Object> loader,
			final String cacheBuilderSpec) {
		final CacheBuilderSpec spec = CacheBuilderSpec.parse(cacheBuilderSpec);
		CacheBuilder.newBuilder();
		final LoadingCache<Object, Object> cache = CacheBuilder.from(spec)
				.removalListener(new RemovalListener<Object, Object>() {
					@Override
					public void onRemoval(RemovalNotification<Object, Object> notification) {
						log.info(notification.getKey() + " unloaded from cache.");
					}
				}).build(loader);
		return cache;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public boolean isAllowNullValues() {
		return allowNullValues;
	}

	@Override
	public Object get(Object key) {
		final Object value = this.store.getIfPresent(key);
		return value != null ? fromStoreValue(value) : null;
	}

	@Override
	public void put(Object key, Object value) {
		this.store.put(key, toStoreValue(value));
	}

	@Override
	public void evict(Object key) {
		this.store.invalidate(key);
	}

	@Override
	public void clear() {
		this.store.invalidateAll();
	}

	/**
	 * Convert the given value from the internal store to a user value returned from
	 * the get method (adapting {@code null}).
	 * 
	 * @param storeValue
	 *            the store value
	 * @return the value to return to the user
	 */
	protected Object fromStoreValue(Object storeValue) {
		if (this.allowNullValues && storeValue == NULL_HOLDER) {
			return null;
		}
		return storeValue;
	}

	/**
	 * Convert the given user value, as passed into the put method, to a value in
	 * the internal store (adapting {@code null}).
	 * 
	 * @param userValue
	 *            the given user value
	 * @return the value to store
	 */
	protected Object toStoreValue(Object userValue) {
		if (this.allowNullValues && userValue == null) {
			return NULL_HOLDER;
		}
		return userValue;
	}

	@SuppressWarnings("serial")
	private static class NullHolder implements Serializable {

	}

}
