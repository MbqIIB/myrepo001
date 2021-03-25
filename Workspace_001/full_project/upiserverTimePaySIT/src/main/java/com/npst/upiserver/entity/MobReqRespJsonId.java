package com.npst.upiserver.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mobreqrespjsonid")
public class MobReqRespJsonId implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long idmobreqrespjsonid;
	private Integer flag;
	private String msgid;

	public MobReqRespJsonId() {
	}

	public MobReqRespJsonId(Long idmobreqrespjsonid) {
		this.idmobreqrespjsonid = idmobreqrespjsonid;
	}

	public MobReqRespJsonId(Long idmobreqrespjsonid, Integer flag, String msgid) {
		this.idmobreqrespjsonid = idmobreqrespjsonid;
		this.flag = flag;
		this.msgid = msgid;
	}

	@Column(name = "flag")
	public Integer getFlag() {
		return this.flag;
	}

	@Id
	@Column(name = "idmobreqrespjsonid", unique = true, nullable = false)
	public Long getIdmobreqrespjsonid() {
		return this.idmobreqrespjsonid;
	}

	@Column(name = "msgid")
	public String getMsgid() {
		return msgid;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public void setIdmobreqrespjsonid(Long idmobreqrespjsonid) {
		this.idmobreqrespjsonid = idmobreqrespjsonid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

}
