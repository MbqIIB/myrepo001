//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB)
// Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source
// schema.
// Generated on: 2017.02.01 at 09:58:09 PM IST
//

package org.npci.upi.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Head" type="{http://npci.org/upi/schema/}headType"/>
 *         &lt;element name="Txn" type="{http://npci.org/upi/schema/}payTrans"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "head", "txn" })
@XmlRootElement(name = "ReqChkTxn")
public class ReqChkTxn {

	@XmlElement(name = "Head", required = true)
	protected HeadType head;
	@XmlElement(name = "Txn", required = true)
	protected PayTrans txn;

	/**
	 * Gets the value of the head property.
	 *
	 * @return possible object is {@link HeadType }
	 */
	public HeadType getHead() {
		return head;
	}

	/**
	 * Gets the value of the txn property.
	 *
	 * @return possible object is {@link PayTrans }
	 */
	public PayTrans getTxn() {
		return txn;
	}

	/**
	 * Sets the value of the head property.
	 *
	 * @param value
	 *            allowed object is {@link HeadType }
	 */
	public void setHead(HeadType value) {
		this.head = value;
	}

	/**
	 * Sets the value of the txn property.
	 *
	 * @param value
	 *            allowed object is {@link PayTrans }
	 */
	public void setTxn(PayTrans value) {
		this.txn = value;
	}

}
