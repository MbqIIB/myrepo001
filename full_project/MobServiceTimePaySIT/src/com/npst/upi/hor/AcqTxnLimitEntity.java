package com.npst.upi.hor;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ACQUIRER_LIMIT_TXNS")
public class AcqTxnLimitEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "MOBILE_NO", length = 12)
	private long mobileNo;

	@Column(name = "USER_TYPE")
	private int userType;

	@Column(name = "CREATE_DATE")
	private Date createDate;

	@Column(name = "PAY_CNT")
	private int payCount;

	@Column(name = "COLLECT_CNT")
	private int collectCount;

	@Column(name = "DATE_STR", length = 12)
	private String dateStr;

	@Column(name = "TXNID", length = 50)
	private String txnId;

	@Column(name = "IDPK")
	private long idPk;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "FIRST_PAY_S_TXNDATE")
	private Date firstPaySTxnDate;
	
	public Long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getPayCount() {
		return payCount;
	}

	public void setPayCount(int payCount) {
		this.payCount = payCount;
	}

	public int getCollectCount() {
		return collectCount;
	}

	public void setCollectCount(int collectCount) {
		this.collectCount = collectCount;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public long getIdPk() {
		return idPk;
	}

	public void setIdPk(long idPk) {
		this.idPk = idPk;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getCountPayAndCollect() {
		return payCount + collectCount;

	}

	public Date getFirstPaySTxnDate() {
		return firstPaySTxnDate;
	}

	public void setFirstPaySTxnDate(Date firstPaySTxnDate) {
		this.firstPaySTxnDate = firstPaySTxnDate;
	}
}