/**
 * 
 */
package com.npst.mobileservice.object;

import java.io.Serializable;

/**
 * @author npst
 *
 */
public class TimePayPropertyVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String key;
	private String value;

	public TimePayPropertyVO(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
