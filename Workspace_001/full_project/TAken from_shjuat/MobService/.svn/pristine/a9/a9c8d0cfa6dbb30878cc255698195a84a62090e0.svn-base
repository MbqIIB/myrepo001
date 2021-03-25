package com.npst.upi.hor;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "DEVICE_REGISTER_LIMIT")
public class DeviceRegLimitEntity {

	@Id
	@Column(name = "DEVICE_ID", length = 80)
	private String deviceId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "COUNT")
	private int count;

	@Column(name = "ACTIVE")
	private boolean active;

	@Column(name = "DATE_STR")
	private String dateStr;

	@Column(name = "MOB_NUMBERS", length = 500)
	private String mobNumbers;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public String getMobNumbers() {
		return mobNumbers;
	}

	public void setMobNumbers(String mobNumbers) {
		this.mobNumbers = mobNumbers;
	}
}