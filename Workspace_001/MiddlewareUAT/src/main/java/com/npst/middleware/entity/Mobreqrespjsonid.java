package com.npst.middleware.entity;
// Generated 5 Mar, 2017 5:02:39 PM by Hibernate Tools 5.2.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Mobreqrespjsonid generated by hbm2java
 */
@Entity
@Table(name = "mobreqrespjsonid")
public class Mobreqrespjsonid implements java.io.Serializable {
	
	/**
	 *
	 */
	private static final long	serialVersionUID	= 1L;
	private int					idmobreqrespjsonid;
	private Integer				flag;
	private String				msgid;
	
	public Mobreqrespjsonid() {
	}
	
	public Mobreqrespjsonid(int idmobreqrespjsonid) {
		this.idmobreqrespjsonid = idmobreqrespjsonid;
	}
	
	public Mobreqrespjsonid(int idmobreqrespjsonid, Integer flag, String msgid) {
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
	public int getIdmobreqrespjsonid() {
		return this.idmobreqrespjsonid;
	}
	
	@Column(name = "msgid")
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
	
}