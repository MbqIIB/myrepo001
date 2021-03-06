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
 * Java class for PasswordTokenType complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="PasswordTokenType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UserId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Password" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PasswordTokenType", propOrder = {
		"userId", "password"
})
public class PasswordTokenType {
	
	@XmlElement(name = "UserId", required = true)
	protected String	userId;
	@XmlElement(name = "Password", required = true)
	protected String	password;
	
	public PasswordTokenType() {
		super();
	}
	
	public PasswordTokenType(String userId, String password) {
		super();
		this.userId = userId;
		this.password = password;
	}
	
	/**
	 * Gets the value of the password property.
	 *
	 * @return
	 * 		possible object is
	 *         {@link String }
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Gets the value of the userId property.
	 *
	 * @return
	 * 		possible object is
	 *         {@link String }
	 */
	public String getUserId() {
		return userId;
	}
	
	/**
	 * Sets the value of the password property.
	 *
	 * @param value
	 *            allowed object is
	 *            {@link String }
	 */
	public void setPassword(String value) {
		this.password = value;
	}
	
	/**
	 * Sets the value of the userId property.
	 *
	 * @param value
	 *            allowed object is
	 *            {@link String }
	 */
	public void setUserId(String value) {
		this.userId = value;
	}
	
}
