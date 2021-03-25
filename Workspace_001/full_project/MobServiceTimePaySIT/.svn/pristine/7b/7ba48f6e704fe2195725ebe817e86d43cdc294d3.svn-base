package com.npst.upi.hor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mobupireqrespjsonid")
public class Mobupireqrespjsonid implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int idmobreqrespjsonid;
	private Integer flag;
	private String msgid;
	private String              tpId;
	private String              source;
	private String              node;
	public Mobupireqrespjsonid() {
	}

	@Column(name = "flag")
	public Integer getFlag() {
		return this.flag;
	}

	@Id
	@Column(name = "idmobreqrespjsonid", unique = true, nullable = false)
	public int getIdmobreqrespjsonid() {
		return this.idmobreqrespjsonid;
	}

	@Column(name = "msgid", length = 40)
	public String getMsgid() {
		return msgid;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public void setIdmobreqrespjsonid(int idmobreqrespjsonid) {
		this.idmobreqrespjsonid = idmobreqrespjsonid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	@Column(name = "TPID", length = 50)
	public String getTpId() {
		return tpId;
	}

	public void setTpId(String tpId) {
		this.tpId = tpId;
	}
	
	@Column(name = "SOURCE", length = 99)
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	@Column(name = "NODE", length = 99)
	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
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
		if (tpId != null) {
			builder.append("tpId=");
			builder.append(tpId);
		}
		if (source != null) {
			builder.append("source=");
			builder.append(source);
		}
		if (node != null) {
			builder.append("node=");
			builder.append(node);
		}
		builder.append("]");
		return builder.toString();
	}

}
