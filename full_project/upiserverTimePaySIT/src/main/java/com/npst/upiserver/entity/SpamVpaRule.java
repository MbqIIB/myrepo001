package com.npst.upiserver.entity;


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
@Table(name = "spamvparule")
public class SpamVpaRule implements java.io.Serializable {

	private Long idspamvparule;
	private String spamvpa;
	private String rule;
	private Date ruleapplieddate;
	private Date ruleexpireddate;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idspamvparule", unique = true, nullable = false)
	public Long getIdspamvparule() {
		return this.idspamvparule;
	}

	@Column(name = "rule", nullable = false, length = 5)
	public String getRule() {
		return this.rule;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ruleapplieddate", nullable = false, length = 10)
	public Date getRuleapplieddate() {
		return this.ruleapplieddate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ruleexpireddate", nullable = false, length = 10)
	public Date getRuleexpireddate() {
		return this.ruleexpireddate;
	}

	@Column(name = "spamvpa", nullable = false, length = 100)
	public String getSpamvpa() {
		return this.spamvpa;
	}

	public void setIdspamvparule(Long idspamvparule) {
		this.idspamvparule = idspamvparule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public void setRuleapplieddate(Date ruleapplieddate) {
		this.ruleapplieddate = ruleapplieddate;
	}

	public void setRuleexpireddate(Date ruleexpireddate) {
		this.ruleexpireddate = ruleexpireddate;
	}

	public void setSpamvpa(String spamvpa) {
		this.spamvpa = spamvpa;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Spamvparule [");
		if (idspamvparule != null) {
			builder.append("idspamvparule=").append(idspamvparule).append(", ");
		}
		if (spamvpa != null) {
			builder.append("spamvpa=").append(spamvpa).append(", ");
		}
		if (rule != null) {
			builder.append("rule=").append(rule).append(", ");
		}
		if (ruleapplieddate != null) {
			builder.append("ruleapplieddate=").append(ruleapplieddate).append(", ");
		}
		if (ruleexpireddate != null) {
			builder.append("ruleexpireddate=").append(ruleexpireddate);
		}
		builder.append("]");
		return builder.toString();
	}

}