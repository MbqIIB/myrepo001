package com.npst.upi.hor;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "MANDATE_REQ_RESP_JSON")
public class MobMandateReqRespJsonEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long idPk;
	private String req;
	private String resp;
	private Date reqDate;
	private Date respDate;
	private String type;
	private Integer flag;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MANDATE_REQRESP_JSON_SEQ")
//	@SequenceGenerator(sequenceName = "MANDATE_REQRESP_JSON_SEQ", allocationSize = 1, name = "MANDATE_REQRESP_JSON_SEQ")
	@Column(name = "IdPk")
	public Long getIdPk() {
		return idPk;
	}

	@Lob
	@Column(name = "Req")
	public String getReq() {
		return this.req;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ReqDate", length = 19)
	public Date getReqDate() {
		return this.reqDate;
	}

	@Lob
	@Column(name = "Resp")
	public String getResp() {
		return this.resp;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RespDate", length = 19)
	public Date getRespDate() {
		return this.respDate;
	}

	@Column(name = "Type", length = 20)
	public String getType() {
		return this.type;
	}

	@Column(name = "Flag")
	public Integer getFlag() {
		return this.flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public void setIdPk(Long idPk) {
		this.idPk = idPk;
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
		builder.append("Mobupireqrespjson [");
		if (idPk != null) {
			builder.append("idPk=");
			builder.append(idPk);
			builder.append(", ");
		}
		if (req != null) {
			builder.append("req=");
			builder.append(req);
			builder.append(", ");
		}
		if (resp != null) {
			builder.append("resp=");
			builder.append(resp);
			builder.append(", ");
		}
		if (reqDate != null) {
			builder.append("reqDate=");
			builder.append(reqDate);
			builder.append(", ");
		}
		if (respDate != null) {
			builder.append("respDate=");
			builder.append(respDate);
			builder.append(", ");
		}
		if (type != null) {
			builder.append("type=");
			builder.append(type);
			builder.append(", ");
		}
		if (flag != null) {
			builder.append("flag=");
			builder.append(flag);
		}
		builder.append("]");
		return builder.toString();
	}
}