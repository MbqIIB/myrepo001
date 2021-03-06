package com.npst.upi.hor;
// Generated 3 Nov, 2017 12:26:49 PM by Hibernate Tools 5.2.3.Final

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
 * Silentsms generated by hbm2java
 */
@Entity
@Table(name = "silentsms")
public class Silentsms implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 276313966314634529L;
	private Integer smsid;
	private String header;
	private String mobno;
	private String smstext;
	private String imei;
	private String circle;
	private String sentdate;
	private String operator;
	private String deviceid;
	private Date logdate;
	private int status;
	
	private String tid;
	private String source;
	private String node;
	
	public Silentsms() {
	}

	public Silentsms(String circle) {
		this.circle = circle;
	}

	public Silentsms(Integer smsid, String header, String mobno, String smstext, String imei, String circle,
			String sentdate, String operator, String deviceid, Date logdate, String tid) {
		super();
		this.smsid = smsid;
		this.header = header;
		this.mobno = mobno;
		this.smstext = smstext;
		this.imei = imei;
		this.circle = circle;
		this.sentdate = sentdate;
		this.operator = operator;
		this.deviceid = deviceid;
		this.logdate = logdate;
		//this.tid = tid;
	}

	@Column(name = "CIRCLE", nullable = false, length = 100)
	public String getCircle() {
		return this.circle;
	}

	@Column(name = "DEVICEID", length = 100)
	public String getDeviceid() {
		return this.deviceid;
	}

	@Column(name = "HEADER", length = 100)
	public String getHeader() {
		return this.header;
	}

	@Column(name = "IMEI", length = 100)
	public String getImei() {
		return this.imei;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOGDATE", length = 19)
	public Date getLogdate() {
		return this.logdate;
	}

	@Column(name = "MOBNO", length = 12)
	public String getMobno() {
		return this.mobno;
	}

	@Column(name = "OPERATOR", length = 20)
	public String getOperator() {
		return this.operator;
	}

	@Column(name = "SENTDATE", length = 200)
	public String getSentdate() {
		return this.sentdate;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SMSID", unique = true, nullable = false)
	public Integer getSmsid() {
		return this.smsid;
	}

	@Column(name = "SMSTEXT", length = 200)
	public String getSmstext() {
		return this.smstext;
	}
	
	@Column(name = "TID", length = 200)	
	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public void setCircle(String circle) {
		this.circle = circle;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public void setLogdate(Date logdate) {
		this.logdate = logdate;
	}

	public void setMobno(String mobno) {
		this.mobno = mobno;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public void setSentdate(String sentdate) {
		this.sentdate = sentdate;
	}

	public void setSmsid(Integer smsid) {
		this.smsid = smsid;
	}

	public void setSmstext(String smstext) {
		this.smstext = smstext;
	}
	
	@Column(name = "status", nullable = false)
	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	@Column(name="SOURCE" , length = 20)
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	@Override
	public String toString() {
		return "Silentsms [smsid=" + smsid + ", header=" + header + ", mobno=" + mobno + ", smstext=" + smstext
				+ ", imei=" + imei + ", circle=" + circle + ", sentdate=" + sentdate + ", operator=" + operator
				+ ", deviceid=" + deviceid + ", logdate=" + logdate + ", status=" + status + ", tid=" + tid
				+ ", source=" + source + ", node=" + node + "]";
	}
}
