/*package com.npst.upiserver.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ONUS_MOB_REQ_RESP_JSON")
public class OnusMobReqRespJsonEntity implements java.io.Serializable {
	private static final long serialVersionUID = 117972893841031L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDONUSMOBREQRESPJSON")
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ONUSMOBREQRESPJSON_SEQ")
//	@SequenceGenerator(name = "ONUSMOBREQRESPJSON_SEQ", sequenceName = "ONUSMOBREQRESPJSON_SEQ", allocationSize = 1)
	private Long idonusmobreqrespjson;

	@Lob
	@Column(name = "Req")
	private String req;

	@Lob
	@Column(name = "Resp")
	private String resp;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ReqDate", length = 19)
	private Date reqDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RespDate", length = 19)
	private Date respDate;

	@Column(name = "Type", length = 20)
	private String type;

	@Column(name = "Flag")
	private Integer flag;

	public Long getIdonusmobreqrespjson() {
		return this.idonusmobreqrespjson;
	}

	public String getReq() {
		return this.req;
	}

	public Date getReqDate() {
		return this.reqDate;
	}

	public String getResp() {
		return this.resp;
	}

	public Date getRespDate() {
		return this.respDate;
	}

	public String getType() {
		return this.type;
	}

	public Integer getFlag() {
		return this.flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public void setIdonusmobreqrespjson(Long idonusmobreqrespjson) {
		this.idonusmobreqrespjson = idonusmobreqrespjson;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Onusmobreqrespjson [");
		if (idonusmobreqrespjson != null) {
			builder.append("idonusmobreqrespjson=").append(idonusmobreqrespjson).append(", ");
		}
		if (req != null) {
			builder.append("req=").append(req).append(", ");
		}
		if (resp != null) {
			builder.append("resp=").append(resp).append(", ");
		}
		if (reqDate != null) {
			builder.append("reqDate=").append(reqDate).append(", ");
		}
		if (respDate != null) {
			builder.append("respDate=").append(respDate).append(", ");
		}
		if (type != null) {
			builder.append("type=").append(type).append(", ");
		}
		if (flag != null) {
			builder.append("flag=").append(flag);
		}
		builder.append("]");
		return builder.toString();
	}

}
*/