package com.npst.upiserver.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mobupireqrespjsonid")
public class Mobupireqrespjsonid implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long idmobreqrespjsonid;
	private Integer flag;
	private String msgid;
	@Column(name = "ServiceType")
	private String serviceType;
	public Mobupireqrespjsonid() {
	}

	@Column(name = "flag")
	public Integer getFlag() {
		return this.flag;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idmobreqrespjsonid", unique = true, nullable = false)
	public Long getIdmobreqrespjsonid() {
		return this.idmobreqrespjsonid;
	}

	@Column(name = "msgid", length = 40)
	public String getMsgid() {
		return msgid;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public void setIdmobreqrespjsonid(Long idmobreqrespjsonid) {
		this.idmobreqrespjsonid = idmobreqrespjsonid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Mobupireqrespjsonid [idmobreqrespjsonid=");
		builder.append(idmobreqrespjsonid);
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
