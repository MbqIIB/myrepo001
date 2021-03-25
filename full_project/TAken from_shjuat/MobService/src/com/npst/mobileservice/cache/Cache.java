package com.npst.mobileservice.cache;

public interface Cache<K, V> {

	public default void put(K key, V value) {

	}

	public V get(K key);

	public default void clear() {

	}

	public default void evict(K key) {

	}

	public default String getName() {
		return null;
	}
}
