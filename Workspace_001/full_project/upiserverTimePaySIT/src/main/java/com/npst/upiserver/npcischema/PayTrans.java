//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.12.14 at 11:57:36 AM IST 
//


package com.npst.upiserver.npcischema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for payTrans complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="payTrans">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RiskScores" type="{http://npci.org/upi/schema/}riskScoresType" minOccurs="0"/>
 *         &lt;element name="Rules" type="{http://npci.org/upi/schema/}rulesType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="note" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="refId" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="refUrl" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ts" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="type" type="{http://npci.org/upi/schema/}payConstant" />
 *       &lt;attribute name="orgMsgId" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="orgTxnId" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="orgRespCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="custRef" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="orgTxnDate" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="umn" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="initiationMode" type="{http://npci.org/upi/schema/}initiationModeCode" />
 *       &lt;attribute name="subType" type="{http://npci.org/upi/schema/}txnSubType" />
 *       &lt;attribute name="initiatedBy" type="{http://npci.org/upi/schema/}initiatedByType" />
 *       &lt;attribute name="pspOrgId" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="purpose" type="{http://npci.org/upi/schema/}txnPurpose" />
 *       &lt;attribute name="refCategory" type="{http://npci.org/upi/schema/}txnRefCategory" />
 *       &lt;attribute name="orgRrn" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "payTrans", propOrder = {
    "riskScores",
    "rules"
})
public class PayTrans {

    @XmlElement(name = "RiskScores")
    protected RiskScoresType riskScores;
    @XmlElement(name = "Rules")
    protected RulesType rules;
    @XmlAttribute(name = "id")
    protected String id;
    @XmlAttribute(name = "note")
    protected String note;
    @XmlAttribute(name = "refId")
    protected String refId;
    @XmlAttribute(name = "refUrl")
    protected String refUrl;
    @XmlAttribute(name = "ts")
    protected String ts;
    @XmlAttribute(name = "type")
    protected PayConstant type;
    @XmlAttribute(name = "orgMsgId")
    protected String orgMsgId;
    @XmlAttribute(name = "orgTxnId")
    protected String orgTxnId;
    @XmlAttribute(name = "orgRespCode")
    protected String orgRespCode;
    @XmlAttribute(name = "custRef")
    protected String custRef;
    @XmlAttribute(name = "orgTxnDate")
    protected String orgTxnDate;
    @XmlAttribute(name = "umn")
    protected String umn;
    @XmlAttribute(name = "initiationMode")
    protected String initiationMode;
    @XmlAttribute(name = "subType")
    protected TxnSubType subType;
    @XmlAttribute(name = "initiatedBy")
    protected InitiatedByType initiatedBy;
    @XmlAttribute(name = "pspOrgId")
    protected String pspOrgId;
    @XmlAttribute(name = "purpose")
    protected String purpose;
    @XmlAttribute(name = "refCategory")
    protected String refCategory;
    @XmlAttribute(name = "orgRrn")
    protected String orgRrn;

    /**
     * Gets the value of the riskScores property.
     * 
     * @return
     *     possible object is
     *     {@link RiskScoresType }
     *     
     */
    public RiskScoresType getRiskScores() {
        return riskScores;
    }

    /**
     * Sets the value of the riskScores property.
     * 
     * @param value
     *     allowed object is
     *     {@link RiskScoresType }
     *     
     */
    public void setRiskScores(RiskScoresType value) {
        this.riskScores = value;
    }

    /**
     * Gets the value of the rules property.
     * 
     * @return
     *     possible object is
     *     {@link RulesType }
     *     
     */
    public RulesType getRules() {
        return rules;
    }

    /**
     * Sets the value of the rules property.
     * 
     * @param value
     *     allowed object is
     *     {@link RulesType }
     *     
     */
    public void setRules(RulesType value) {
        this.rules = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the note property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets the value of the note property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNote(String value) {
        this.note = value;
    }

    /**
     * Gets the value of the refId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefId() {
        return refId;
    }

    /**
     * Sets the value of the refId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefId(String value) {
        this.refId = value;
    }

    /**
     * Gets the value of the refUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefUrl() {
        return refUrl;
    }

    /**
     * Sets the value of the refUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefUrl(String value) {
        this.refUrl = value;
    }

    /**
     * Gets the value of the ts property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTs() {
        return ts;
    }

    /**
     * Sets the value of the ts property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTs(String value) {
        this.ts = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link PayConstant }
     *     
     */
    public PayConstant getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link PayConstant }
     *     
     */
    public void setType(PayConstant value) {
        this.type = value;
    }

    /**
     * Gets the value of the orgMsgId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgMsgId() {
        return orgMsgId;
    }

    /**
     * Sets the value of the orgMsgId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgMsgId(String value) {
        this.orgMsgId = value;
    }

    /**
     * Gets the value of the orgTxnId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgTxnId() {
        return orgTxnId;
    }

    /**
     * Sets the value of the orgTxnId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgTxnId(String value) {
        this.orgTxnId = value;
    }

    /**
     * Gets the value of the orgRespCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgRespCode() {
        return orgRespCode;
    }

    /**
     * Sets the value of the orgRespCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgRespCode(String value) {
        this.orgRespCode = value;
    }

    /**
     * Gets the value of the custRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustRef() {
        return custRef;
    }

    /**
     * Sets the value of the custRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustRef(String value) {
        this.custRef = value;
    }

    /**
     * Gets the value of the orgTxnDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgTxnDate() {
        return orgTxnDate;
    }

    /**
     * Sets the value of the orgTxnDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgTxnDate(String value) {
        this.orgTxnDate = value;
    }

    /**
     * Gets the value of the umn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUmn() {
        return umn;
    }

    /**
     * Sets the value of the umn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUmn(String value) {
        this.umn = value;
    }

    /**
     * Gets the value of the initiationMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInitiationMode() {
        return initiationMode;
    }

    /**
     * Sets the value of the initiationMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInitiationMode(String value) {
        this.initiationMode = value;
    }

    /**
     * Gets the value of the subType property.
     * 
     * @return
     *     possible object is
     *     {@link TxnSubType }
     *     
     */
    public TxnSubType getSubType() {
        return subType;
    }

    /**
     * Sets the value of the subType property.
     * 
     * @param value
     *     allowed object is
     *     {@link TxnSubType }
     *     
     */
    public void setSubType(TxnSubType value) {
        this.subType = value;
    }

    /**
     * Gets the value of the initiatedBy property.
     * 
     * @return
     *     possible object is
     *     {@link InitiatedByType }
     *     
     */
    public InitiatedByType getInitiatedBy() {
        return initiatedBy;
    }

    /**
     * Sets the value of the initiatedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link InitiatedByType }
     *     
     */
    public void setInitiatedBy(InitiatedByType value) {
        this.initiatedBy = value;
    }

    /**
     * Gets the value of the pspOrgId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPspOrgId() {
        return pspOrgId;
    }

    /**
     * Sets the value of the pspOrgId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPspOrgId(String value) {
        this.pspOrgId = value;
    }

    /**
     * Gets the value of the purpose property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * Sets the value of the purpose property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPurpose(String value) {
        this.purpose = value;
    }

    /**
     * Gets the value of the refCategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefCategory() {
        return refCategory;
    }

    /**
     * Sets the value of the refCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefCategory(String value) {
        this.refCategory = value;
    }

    /**
     * Gets the value of the orgRrn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgRrn() {
        return orgRrn;
    }

    /**
     * Sets the value of the orgRrn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgRrn(String value) {
        this.orgRrn = value;
    }

}