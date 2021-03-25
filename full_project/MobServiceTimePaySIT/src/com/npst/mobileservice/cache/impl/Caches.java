/**
 * 
 */
package com.npst.mobileservice.cache.impl;

/**
 * @author npst
 *
 */
public enum Caches {

	NEGATIVE_MOBILE_NUMBER_CACHE("NEGATIVE_MOBILE_NUMBER_CACHE_SPEC", "NEGATIVE_MOBILE_NUMBER_CACHE_KEY",
			"NEGATIVE_MOBILE_NUMBER_CACHE"), RESERVED_VPA_CACHE("RESERVED_VPA_CACHE_SPEC", "RESERVED_VPA_CACHE_KEY",
					"RESERVED_VPA_CACHE"), TIME_PAY_PROPERTY_CACHE("TIME_PAY_PROPERTY_CACHE_SPEC",
							"TIME_PAY_PROPERTY_CACHE_KEY", "TIME_PAY_PROPERTY_CACHE");

	private final String propKey;
	private final String key;
	private final String name;

	Caches(final String propKey, final String key, final String name) {
		this.propKey = propKey;
		this.key = key;
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public String getName() {
		return name;
	}

	public String getPropKey() {
		return propKey;
	}
}