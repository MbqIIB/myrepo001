/**
 * 
 */
package com.npst.upi.hor;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author npst
 *
 */
@Entity
@Table(name = "crashanalyticsinfo")
public class CrashAnalyticsInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created")
	private Date created;
	@Column(name = "app_package")
	private String appPackage;
	@Column(name = "os_name")
	private String osName;
	@Column(name = "app_version")
	private String appVersion;
	@Column(name = "os_version")
	private String osVersion;
	@Column(name = "exception", columnDefinition = "LONGTEXT")
	private String exception;
	@Column(name = "handset_name")
	private String handsetName;
	@Column(name = "exception_type")
	private String exceptionType;
	@Column(name = "status")
	private int status;
	@Column(name = "problem_category")
	private int problemCategory;
	@Column(name = "problem_severity")
	private int problemSeverity;
	@Column(name = "remarks")
	private String remarks;
	@Column(name = "device_id")
	private String deviceId;
	@Column(name = "mobile_number")
	private String mobileNumber;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated")
	private Date updated;

	public CrashAnalyticsInfo() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAppPackage() {
		return appPackage;
	}

	public void setAppPackage(String appPackage) {
		this.appPackage = appPackage;
	}

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getHandsetName() {
		return handsetName;
	}

	public void setHandsetName(String handsetName) {
		this.handsetName = handsetName;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getProblemCategory() {
		return problemCategory;
	}

	public void setProblemCategory(int problemCategory) {
		this.problemCategory = problemCategory;
	}

	public int getProblemSeverity() {
		return problemSeverity;
	}

	public void setProblemSeverity(int problemSeverity) {
		this.problemSeverity = problemSeverity;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	@Override
	public String toString() {
		return "CrashAnalyticsInfo [created=" + created + ", appPackage=" + appPackage + ", osName=" + osName
				+ ", appVersion=" + appVersion + ", osVersion=" + osVersion + ", exception=" + exception
				+ ", handsetName=" + handsetName + ", exceptionType=" + exceptionType + ", status=" + status
				+ ", problemCategory=" + problemCategory + ", problemSeverity=" + problemSeverity + ", remarks="
				+ remarks + ", deviceId=" + deviceId + ", mobileNumber=" + mobileNumber + ", updated=" + updated + "]";
	}

}
