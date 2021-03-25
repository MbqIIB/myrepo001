/**
 * 
 */
package com.npst.mobileservice.object;

import java.io.Serializable;

/**
 * @author npst
 */
public class VirtualIdVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String virtualId;
	private String isDefault;
	private String name;
	private int status;

	public VirtualIdVO(String virtualId, String isDefault, String name, int status) {
		super();
		this.virtualId = virtualId;
		this.isDefault = isDefault;
		this.name = name;
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public VirtualIdVO() {
	}

	public VirtualIdVO(String virtualId, String isDefault, String name) {
		super();
		this.virtualId = virtualId;
		this.isDefault = isDefault;
		this.name = name;
	}

	public String getVirtualId() {
		return virtualId;
	}

	public void setVirtualId(String virtualId) {
		this.virtualId = virtualId;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VirtualIdVO other = (VirtualIdVO) obj;
		if (virtualId == null) {
			if (other.virtualId != null)
				return false;
		} else if (!virtualId.equals(other.virtualId))
			return false;
		return true;
	}

}
