//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB)
// Reference Implementation, vhudson-jaxb-ri-2.2-147
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source
// schema.
// Generated on: 2017.08.09 at 01:53:13 PM IST
//

package com.npst.middleware.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for HeaderType complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="HeaderType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RequestHeader" type="{http://www.finacle.com/fixml}RequestHeaderType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HeaderType", propOrder = {
		"requestHeader"
})
public class HeaderType {
	
	@XmlElement(name = "RequestHeader", required = true)
	protected RequestHeaderType requestHeader;
	
	public HeaderType() {
		super();
	}
	
	public HeaderType(RequestHeaderType requestHeader) {
		super();
		this.requestHeader = requestHeader;
	}
	
	/**
	 * Gets the value of the requestHeader property.
	 *
	 * @return
	 * 		possible object is
	 *         {@link RequestHeaderType }
	 */
	public RequestHeaderType getRequestHeader() {
		return requestHeader;
	}
	
	/**
	 * Sets the value of the requestHeader property.
	 *
	 * @param value
	 *            allowed object is
	 *            {@link RequestHeaderType }
	 */
	public void setRequestHeader(RequestHeaderType value) {
		this.requestHeader = value;
	}


	
}
