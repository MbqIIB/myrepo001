/*package com.npst.upiserver.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ONUS_MOB_REQ_RESP_JSON_ID")
public class OnusMobReqRespJsonIdEntity implements java.io.Serializable {

	private static final long serialVersionUID = -7400723640669546058L;
	private long idonusmobreqrespjsonid;
	private Integer flag;
	private String apiNameFlag;
	
	@Column(name = "ApiNameFlag")
	public String getApiNameFlag() {
		return this.apiNameFlag;
	}
	@Column(name = "Flag")
	public Integer getFlag() {
		return this.flag;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDONUSMOBREQRESPJSONID", unique = true, nullable = false)
	public long getIdonusmobreqrespjsonid() {
		return this.idonusmobreqrespjsonid;
	}

	public void setApiNameFlag(String apiNameFlag) {
		this.apiNameFlag = apiNameFlag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public void setIdonusmobreqrespjsonid(long idonusmobreqrespjsonid) {
		this.idonusmobreqrespjsonid = idonusmobreqrespjsonid;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Onusmobreqrespjsonid [idonusmobreqrespjsonid=").append(idonusmobreqrespjsonid).append(", ");
		if (flag != null) {
			builder.append("flag=").append(flag).append(", ");
		}
		if (apiNameFlag != null) {
			builder.append("apiNameFlag=").append(apiNameFlag);
		}
		builder.append("]");
		return builder.toString();
	}

}*/