//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.12.14 at 11:52:34 AM IST 
//


package com.npst.upiserver.npcischema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Head" type="{http://npci.org/upi/schema/}headType"/>
 *         &lt;element name="Txn" type="{http://npci.org/upi/schema/}payTrans"/>
 *         &lt;element name="TxnConfirmation" type="{http://npci.org/upi/schema/}txnConfDtl"/>
 *         &lt;element name="Mandate" type="{http://npci.org/upi/schema/}mandateType"/>
 *         &lt;element name="Signature" type="{http://npci.org/upi/schema/}mandateSign"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "head",
    "txn",
    "txnConfirmation",
    "mandate",
    "signature"
})
@XmlRootElement(name = "ReqMandateConfirmation")
public class ReqMandateConfirmation {

    @XmlElement(name = "Head", required = true)
    protected HeadType head;
    @XmlElement(name = "Txn", required = true)
    protected PayTrans txn;
    @XmlElement(name = "TxnConfirmation", required = true)
    protected TxnConfDtl txnConfirmation;
    @XmlElement(name = "Mandate", required = true)
    protected MandateType mandate;
    @XmlElement(name = "Signature", required = true)
    protected MandateSign signature;

    /**
     * Gets the value of the head property.
     * 
     * @return
     *     possible object is
     *     {@link HeadType }
     *     
     */
    public HeadType getHead() {
        return head;
    }

    /**
     * Sets the value of the head property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeadType }
     *     
     */
    public void setHead(HeadType value) {
        this.head = value;
    }

    /**
     * Gets the value of the txn property.
     * 
     * @return
     *     possible object is
     *     {@link PayTrans }
     *     
     */
    public PayTrans getTxn() {
        return txn;
    }

    /**
     * Sets the value of the txn property.
     * 
     * @param value
     *     allowed object is
     *     {@link PayTrans }
     *     
     */
    public void setTxn(PayTrans value) {
        this.txn = value;
    }

    /**
     * Gets the value of the txnConfirmation property.
     * 
     * @return
     *     possible object is
     *     {@link TxnConfDtl }
     *     
     */
    public TxnConfDtl getTxnConfirmation() {
        return txnConfirmation;
    }

    /**
     * Sets the value of the txnConfirmation property.
     * 
     * @param value
     *     allowed object is
     *     {@link TxnConfDtl }
     *     
     */
    public void setTxnConfirmation(TxnConfDtl value) {
        this.txnConfirmation = value;
    }

    /**
     * Gets the value of the mandate property.
     * 
     * @return
     *     possible object is
     *     {@link MandateType }
     *     
     */
    public MandateType getMandate() {
        return mandate;
    }

    /**
     * Sets the value of the mandate property.
     * 
     * @param value
     *     allowed object is
     *     {@link MandateType }
     *     
     */
    public void setMandate(MandateType value) {
        this.mandate = value;
    }

    /**
     * Gets the value of the signature property.
     * 
     * @return
     *     possible object is
     *     {@link MandateSign }
     *     
     */
    public MandateSign getSignature() {
        return signature;
    }

    /**
     * Sets the value of the signature property.
     * 
     * @param value
     *     allowed object is
     *     {@link MandateSign }
     *     
     */
    public void setSignature(MandateSign value) {
        this.signature = value;
    }

}
