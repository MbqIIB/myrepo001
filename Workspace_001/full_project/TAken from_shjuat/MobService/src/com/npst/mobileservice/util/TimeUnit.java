/**
 * 
 */
package com.npst.mobileservice.util;

/**
 * @author npst
 *
 */
public enum TimeUnit {

	MILLISECONDS("MILLISECONDS", java.util.concurrent.TimeUnit.MILLISECONDS), SECONDS("SECONDS",
			java.util.concurrent.TimeUnit.SECONDS), MINUTES("MINUTES", java.util.concurrent.TimeUnit.MINUTES), HOURS(
					"HOURS", java.util.concurrent.TimeUnit.HOURS), DAYS("DAYS", java.util.concurrent.TimeUnit.DAYS);

	private final String type;
	private final java.util.concurrent.TimeUnit unit;

	private TimeUnit(final String type, java.util.concurrent.TimeUnit unit) {
		this.type = type;
		this.unit = unit;
	}

	public String getType() {
		return type;
	}

	public java.util.concurrent.TimeUnit getUnit() {
		return unit;
	}

	public static java.util.concurrent.TimeUnit getUnit(final String type) {
		for (TimeUnit unit : TimeUnit.values()) {
			if (type.equals(unit.type))
				return unit.unit;
		}
		return null;
	}

}
