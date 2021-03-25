/**
 * 
 */
package com.npst.upi.hor;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author npst
 *
 */
@Entity
@Table(name = "negative_mob_no")
public class NegativeMobileNumber implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7689537155266744833L;

	@Id
	@Column(name = "MOBILE_NUMBER")
	private String mobileNumber;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "NegativeMobileNumber [mobileNumber=" + mobileNumber + ", createdDate=" + createdDate + "]";
	}

}
