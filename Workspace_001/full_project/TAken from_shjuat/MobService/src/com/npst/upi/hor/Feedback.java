package com.npst.upi.hor;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "feedback")
public class Feedback implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "feedback_category")
	private Integer feedbackCategory;
	@Column(name = "feedback_severity")
	private Integer feedbackSeverity;
	@Column(name = "remarks")
	private String remarks;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "feedback_date")
	private Date feedback_date;
	@Column(name = "status")
	private Integer status;
	@Column(name = "screen_path")
	private String screenPath;
	@Column(name = "attachment_path")
	private String attachmentPath;
	@Column(name = "feedback_text")
	private String feedbackText;
	@Column(name = "regId")
	private Integer regId;
	@Column(name = "email")
	private String email;
	@Column(name = "os_name")
	private String os_name;
	@Column(name = "os_version")
	private String os_version;
	@Column(name = "app_version")
	private String app_version;
	@Column(name = "handset_name")
	private String handset_name;
	@Column(name = "updated_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated_on;
	@Column(name = "updated_by")
	private String updated_by;
	@Column(name = "mobile_no")
	private String mobile_no;
	@Column(name = "imagesName")
	private String imagesName;

	public String getScreenPath() {
		return screenPath;
	}

	public void setScreenPath(String screenPath) {
		this.screenPath = screenPath;
	}

	public String getOs_name() {
		return os_name;
	}

	public void setOs_name(String os_name) {
		this.os_name = os_name;
	}

	public String getOs_version() {
		return os_version;
	}

	public void setOs_version(String os_version) {
		this.os_version = os_version;
	}

	public String getApp_version() {
		return app_version;
	}

	public void setApp_version(String app_version) {
		this.app_version = app_version;
	}

	public String getHandset_name() {
		return handset_name;
	}

	public void setHandset_name(String handset_name) {
		this.handset_name = handset_name;
	}

	public Date getUpdated_on() {
		return updated_on;
	}

	public void setUpdated_on(Date updated_on) {
		this.updated_on = updated_on;
	}

	public String getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}

	public String getMobile_no() {
		return mobile_no;
	}

	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}

	public String getImagesName() {
		return imagesName;
	}

	public void setImagesName(String imagesName) {
		this.imagesName = imagesName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getFeedbackCategory() {
		return feedbackCategory;
	}

	public void setFeedbackCategory(Integer feedbackCategory) {
		this.feedbackCategory = feedbackCategory;
	}

	public Integer getFeedbackSeverity() {
		return feedbackSeverity;
	}

	public void setFeedbackSeverity(Integer feedbackSeverity) {
		this.feedbackSeverity = feedbackSeverity;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getFeedback_date() {
		return feedback_date;
	}

	public void setFeedback_date(Date feedback_date) {
		this.feedback_date = feedback_date;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAttachmentPath() {
		return attachmentPath;
	}

	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}

	public String getFeedbackText() {
		return feedbackText;
	}

	public void setFeedbackText(String feedbackText) {
		this.feedbackText = feedbackText;
	}

	public Integer getRegId() {
		return regId;
	}

	public void setRegId(Integer regId) {
		this.regId = regId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
