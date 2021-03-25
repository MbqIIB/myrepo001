package com.npst.upiserver.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "MANDATE_REQ_RESP_JSON_ID")
public class MobMandateReqRespJsonIdEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Integer flag;
	private String msgid;

	@Column(name = "ServiceType")
	private String serviceType;

	@Column(name = "flag")
	public Integer getFlag() {
		return this.flag;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MANDATE_REQRESP_JSONID_SEQ")
//	@SequenceGenerator(sequenceName = "MANDATE_REQRESP_JSONID_SEQ", allocationSize = 1, name = "MANDATE_REQRESP_JSONID_SEQ")
	@Column(name = "id")
	public Long getId() {
		return this.id;
	}

	@Column(name = "msgid", length = 40)
	public String getMsgid() {
		return msgid;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MandateMobupireqrespjsonid [id=");
		builder.append(id);
		builder.append(", ");
		if (flag != null) {
			builder.append("flag=");
			builder.append(flag);
			builder.append(", ");
		}
		if (msgid != null) {
			builder.append("msgid=");
			builder.append(msgid);
		}
		builder.append("]");
		return builder.toString();
	}

}