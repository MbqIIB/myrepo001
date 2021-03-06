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
 * <p>Java class for respTypeValAddr complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="respTypeValAddr">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Merchant" type="{http://npci.org/upi/schema/}merchantType" minOccurs="0"/>
 *         &lt;element name="FeatureSupported" type="{http://npci.org/upi/schema/}featureSupportedType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="reqMsgId" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="result" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="errCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="maskName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="code" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="IFSC" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="IIN" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="pType" type="{http://npci.org/upi/schema/}respPType" />
 *       &lt;attribute name="accType" type="{http://npci.org/upi/schema/}listedAccountType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "respTypeValAddr", propOrder = {
    "merchant",
    "featureSupported"
})
public class RespTypeValAddr {

    @XmlElement(name = "Merchant")
    protected MerchantType merchant;
    @XmlElement(name = "FeatureSupported")
    protected FeatureSupportedType featureSupported;
    @XmlAttribute(name = "reqMsgId")
    protected String reqMsgId;
    @XmlAttribute(name = "result")
    protected String result;
    @XmlAttribute(name = "errCode")
    protected String errCode;
    @XmlAttribute(name = "maskName")
    protected String maskName;
    @XmlAttribute(name = "code")
    protected String code;
    @XmlAttribute(name = "type")
    protected String type;
    @XmlAttribute(name = "IFSC")
    protected String ifsc;
    @XmlAttribute(name = "IIN")
    protected String iin;
    @XmlAttribute(name = "pType")
    protected RespPType pType;
    @XmlAttribute(name = "accType")
    protected ListedAccountType accType;

    /**
     * Gets the value of the merchant property.
     * 
     * @return
     *     possible object is
     *     {@link MerchantType }
     *     
     */
    public MerchantType getMerchant() {
        return merchant;
    }

    /**
     * Sets the value of the merchant property.
     * 
     * @param value
     *     allowed object is
     *     {@link MerchantType }
     *     
     */
    public void setMerchant(MerchantType value) {
        this.merchant = value;
    }

    /**
     * Gets the value of the featureSupported property.
     * 
     * @return
     *     possible object is
     *     {@link FeatureSupportedType }
     *     
     */
    public FeatureSupportedType getFeatureSupported() {
        return featureSupported;
    }

    /**
     * Sets the value of the featureSupported property.
     * 
     * @param value
     *     allowed object is
     *     {@link FeatureSupportedType }
     *     
     */
    public void setFeatureSupported(FeatureSupportedType value) {
        this.featureSupported = value;
    }

    /**
     * Gets the value of the reqMsgId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReqMsgId() {
        return reqMsgId;
    }

    /**
     * Sets the value of the reqMsgId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReqMsgId(String value) {
        this.reqMsgId = value;
    }

    /**
     * Gets the value of the result property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResult(String value) {
        this.result = value;
    }

    /**
     * Gets the value of the errCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrCode() {
        return errCode;
    }

    /**
     * Sets the value of the errCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrCode(String value) {
        this.errCode = value;
    }

    /**
     * Gets the value of the maskName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaskName() {
        return maskName;
    }

    /**
     * Sets the value of the maskName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaskName(String value) {
        this.maskName = value;
    }

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the ifsc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIFSC() {
        return ifsc;
    }

    /**
     * Sets the value of the ifsc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIFSC(String value) {
        this.ifsc = value;
    }

    /**
     * Gets the value of the iin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIIN() {
        return iin;
    }

    /**
     * Sets the value of the iin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIIN(String value) {
        this.iin = value;
    }

    /**
     * Gets the value of the pType property.
     * 
     * @return
     *     possible object is
     *     {@link RespPType }
     *     
     */
    public RespPType getPType() {
        return pType;
    }

    /**
     * Sets the value of the pType property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespPType }
     *     
     */
    public void setPType(RespPType value) {
        this.pType = value;
    }

    /**
     * Gets the value of the accType property.
     * 
     * @return
     *     possible object is
     *     {@link ListedAccountType }
     *     
     */
    public ListedAccountType getAccType() {
        return accType;
    }

    /**
     * Sets the value of the accType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListedAccountType }
     *     
     */
    public void setAccType(ListedAccountType value) {
        this.accType = value;
    }

}
