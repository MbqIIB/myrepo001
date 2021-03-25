/**
 * 
 */
package com.npst.mobileservice.util;

/**
 * @author npst
 *
 */
public enum TimePayProperties {

	ACCOUNT_MAX_PERMIT_LIMIT_AMOUNT("acc_max_permit_limit_amount"), ACCOUNT_DEFAULT_LIMIT_AMOUNT(
			"account_default_limit_amount");

	private final String key;

	TimePayProperties(final String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}
