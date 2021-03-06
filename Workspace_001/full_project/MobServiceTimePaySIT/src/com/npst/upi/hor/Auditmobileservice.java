package com.npst.upi.hor;
// Generated 16 Nov, 2017 4:45:25 PM by Hibernate Tools 5.2.3.Final

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
 * Auditmobileservice generated by hbm2java
 */
@Entity
@Table(name = "auditmobileservice")
public class Auditmobileservice implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6526882830249882843L;
	private Integer auditid;
	private String reqjson;
	private String respjson;
	private Date reqjsondt;
	private Date respjsondt;
	private String apiname;
	private String authtoken;
	private String source;
	private String node;

	public Auditmobileservice() {
	}

	public Auditmobileservice(String reqjson, String respjson, Date reqjsondt, Date respjsondt, String apiname,
			String authtoken) {
		this.reqjson = reqjson;
		this.respjson = respjson;
		this.reqjsondt = reqjsondt;
		this.respjsondt = respjsondt;
		this.apiname = apiname;
		this.authtoken = authtoken;
	}

	@Column(name = "APINAME", length = 50)
	public String getApiname() {
		return this.apiname;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AUDITID", unique = true, nullable = false)
	public Integer getAuditid() {
		return this.auditid;
	}

	@Column(name = "AUTHTOKEN", length = 200)
	public String getAuthtoken() {
		return this.authtoken;
	}

	@Column(name = "REQJSON")
	public String getReqjson() {
		return this.reqjson;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REQJSONDT", length = 19)
	public Date getReqjsondt() {
		return this.reqjsondt;
	}

	@Column(name = "RESPJSON")
	public String getRespjson() {
		return this.respjson;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RESPJSONDT", length = 19)
	public Date getRespjsondt() {
		return this.respjsondt;
	}

	public void setApiname(String apiname) {
		this.apiname = apiname;
	}

	public void setAuditid(Integer auditid) {
		this.auditid = auditid;
	}

	public void setAuthtoken(String authtoken) {
		this.authtoken = authtoken;
	}

	public void setReqjson(String reqjson) {
		this.reqjson = reqjson;
	}

	public void setReqjsondt(Date reqjsondt) {
		this.reqjsondt = reqjsondt;
	}

	public void setRespjson(String respjson) {
		this.respjson = respjson;
	}

	public void setRespjsondt(Date respjsondt) {
		this.respjsondt = respjsondt;
	}
	@Column(name="SOURCE" , length = 20)
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name="NODE" , length = 10)
	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Auditmobileservice [");
		if (auditid != null) {
			builder.append("auditid=");
			builder.append(auditid);
			builder.append(", ");
		}
		if (reqjson != null) {
			builder.append("reqjson=");
			builder.append(reqjson);
			builder.append(", ");
		}
		if (respjson != null) {
			builder.append("respjson=");
			builder.append(respjson);
			builder.append(", ");
		}
		if (reqjsondt != null) {
			builder.append("reqjsondt=");
			builder.append(reqjsondt);
			builder.append(", ");
		}
		if (respjsondt != null) {
			builder.append("respjsondt=");
			builder.append(respjsondt);
			builder.append(", ");
		}
		if (apiname != null) {
			builder.append("apiname=");
			builder.append(apiname);
			builder.append(", ");
		}
		if (authtoken != null) {
			builder.append("authtoken=");
			builder.append(authtoken);
		}
		if (source != null) {
			builder.append("source=");
			builder.append(source);
			builder.append(", ");
		}
		if (node != null) {
			builder.append("node=");
			builder.append(node);
		}
		builder.append("]");
		return builder.toString();
	}

}
