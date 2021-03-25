package com.npst.upi.hor;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "initiateRequest")
public class InitiateRequest {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "createddate", nullable = false)
	private Date createddate;
	
	@Column(name = "deviceid", nullable = false)
	private String	deviceid;
	
	@Column(name = "requestId", nullable = false)
	private String requestId;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getCreateddate() {
		return createddate;
	}
	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public InitiateRequest(Integer id, Date createddate, String deviceid, String requestId) {
		super();
		this.id = id;
		this.createddate = createddate;
		this.deviceid = deviceid;
		this.requestId = requestId;
	}
	public InitiateRequest() {
		super();
	}
	@Override
	public String toString() {
		return "InitiateRequest [id=" + id + ", createddate=" + createddate + ", deviceid=" + deviceid + ", requestId="
				+ requestId + "]";
	}
	
}
