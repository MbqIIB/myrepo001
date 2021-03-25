package com.npst.upiserver.entity;

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
@Table(name = "mobreqrespjson")
public class MobReqRespJson implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Long idmobreqrespjson;
	private String req;
	private String resp;
	private Date reqDate;
	private Date respDate;
	private String type;
	private Integer flag;

	public MobReqRespJson() {
	}

	public MobReqRespJson(String req, String resp, Date reqDate, Date respDate, String type, Integer flag) {
		this.req = req;
		this.resp = resp;
		this.reqDate = reqDate;
		this.respDate = respDate;
		this.type = type;
		this.flag = flag;
	}

	@Column(name = "flag")
	public Integer getFlag() {
		return this.flag;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idmobreqrespjson", unique = true, nullable = false)
	public Long getIdmobreqrespjson() {
		return this.idmobreqrespjson;
	}

	@Column(name = "req", length = 16777215)
	public String getReq() {
		return this.req;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "reqdate", length = 19)
	public Date getReqDate() {
		return this.reqDate;
	}

	@Column(name = "resp", length = 16777215)
	public String getResp() {
		return this.resp;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "respdate", length = 19)
	public Date getRespDate() {
		return this.respDate;
	}

	@Column(name = "type", length = 20)
	public String getType() {
		return this.type;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public void setIdmobreqrespjson(Long idmobreqrespjson) {
		this.idmobreqrespjson = idmobreqrespjson;
	}

	public void setReq(String req) {
		this.req = req;
	}

	public void setReqDate(Date reqDate) {
		this.reqDate = reqDate;
	}

	public void setResp(String resp) {
		this.resp = resp;
	}

	public void setRespDate(Date respDate) {
		this.respDate = respDate;
	}

	public void setType(String type) {
		this.type = type;
	}
}
