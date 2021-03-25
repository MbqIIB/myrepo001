package com.npst.mobileservice.cache.impl;

import java.util.concurrent.TimeUnit;

public class CacheConfig {

	private long maximumSize;
	private long refreshAfterWriteDuration;
	private TimeUnit refreshAfterWriteUnit;
	private boolean allowNullValues;

	public void setAllowNullValues(boolean allowNullValues) {
		this.allowNullValues = allowNullValues;
	}

	public boolean isAllowNullValues() {
		return allowNullValues;
	}

	public CacheConfig() {
		super();
		this.allowNullValues = true;
	}

	public CacheConfig(long maximumSize, long refreshAfterWriteDuration, TimeUnit refreshAfterWriteUnit,
			boolean allowNullValues) {
		super();
		this.allowNullValues = allowNullValues;
		this.maximumSize = maximumSize;
		this.refreshAfterWriteDuration = refreshAfterWriteDuration;
		this.refreshAfterWriteUnit = refreshAfterWriteUnit;
	}

	public long getMaximumSize() {
		return maximumSize;
	}

	public void setMaximumSize(long maximumSize) {
		this.maximumSize = maximumSize;
	}

	public long getRefreshAfterWriteDuration() {
		return refreshAfterWriteDuration;
	}

	public void setRefreshAfterWriteDuration(long refreshAfterWriteDuration) {
		this.refreshAfterWriteDuration = refreshAfterWriteDuration;
	}

	public TimeUnit getRefreshAfterWriteUnit() {
		return refreshAfterWriteUnit;
	}

	public void setRefreshAfterWriteUnit(TimeUnit refreshAfterWriteUnit) {
		this.refreshAfterWriteUnit = refreshAfterWriteUnit;
	}
}
