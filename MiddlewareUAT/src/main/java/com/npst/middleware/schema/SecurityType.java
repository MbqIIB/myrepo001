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
 * Java class for SecurityType complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="SecurityType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Token" type="{http://www.finacle.com/fixml}TokenType"/>
 *         &lt;element name="FICertToken" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RealUserLoginSessionId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RealUser" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RealUserPwd" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SSOTransferToken" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SecurityType", propOrder = {
		"token", "fiCertToken", "realUserLoginSessionId", "realUser", "realUserPwd", "ssoTransferToken"
})
public class SecurityType {
	
	@XmlElement(name = "Token", required = true)
	protected TokenType	token;
	@XmlElement(name = "FICertToken", required = true)
	protected String	fiCertToken;
	@XmlElement(name = "RealUserLoginSessionId", required = true)
	protected String	realUserLoginSessionId;
	@XmlElement(name = "RealUser", required = true)
	protected String	realUser;
	@XmlElement(name = "RealUserPwd", required = true)
	protected String	realUserPwd;
	@XmlElement(name = "SSOTransferToken", required = true)
	protected String	ssoTransferToken;
	
	public SecurityType() {
		super();
	}
	
	public SecurityType(TokenType token, String fiCertToken, String realUserLoginSessionId, String realUser,
			String realUserPwd, String ssoTransferToken) {
		super();
		this.token = token;
		this.fiCertToken = fiCertToken;
		this.realUserLoginSessionId = realUserLoginSessionId;
		this.realUser = realUser;
		this.realUserPwd = realUserPwd;
		this.ssoTransferToken = ssoTransferToken;
	}
	
	/**
	 * Gets the value of the fiCertToken property.
	 *
	 * @return
	 * 		possible object is
	 *         {@link String }
	 */
	public String getFICertToken() {
		return fiCertToken;
	}
	
	/**
	 * Gets the value of the realUser property.
	 *
	 * @return
	 * 		possible object is
	 *         {@link String }
	 */
	public String getRealUser() {
		return realUser;
	}
	
	/**
	 * Gets the value of the realUserLoginSessionId property.
	 *
	 * @return
	 * 		possible object is
	 *         {@link String }
	 */
	public String getRealUserLoginSessionId() {
		return realUserLoginSessionId;
	}
	
	/**
	 * Gets the value of the realUserPwd property.
	 *
	 * @return
	 * 		possible object is
	 *         {@link String }
	 */
	public String getRealUserPwd() {
		return realUserPwd;
	}
	
	/**
	 * Gets the value of the ssoTransferToken property.
	 *
	 * @return
	 * 		possible object is
	 *         {@link String }
	 */
	public String getSSOTransferToken() {
		return ssoTransferToken;
	}
	
	/**
	 * Gets the value of the token property.
	 *
	 * @return
	 * 		possible object is
	 *         {@link TokenType }
	 */
	public TokenType getToken() {
		return token;
	}
	
	/**
	 * Sets the value of the fiCertToken property.
	 *
	 * @param value
	 *            allowed object is
	 *            {@link String }
	 */
	public void setFICertToken(String value) {
		this.fiCertToken = value;
	}
	
	/**
	 * Sets the value of the realUser property.
	 *
	 * @param value
	 *            allowed object is
	 *            {@link String }
	 */
	public void setRealUser(String value) {
		this.realUser = value;
	}
	
	/**
	 * Sets the value of the realUserLoginSessionId property.
	 *
	 * @param value
	 *            allowed object is
	 *            {@link String }
	 */
	public void setRealUserLoginSessionId(String value) {
		this.realUserLoginSessionId = value;
	}
	
	/**
	 * Sets the value of the realUserPwd property.
	 *
	 * @param value
	 *            allowed object is
	 *            {@link String }
	 */
	public void setRealUserPwd(String value) {
		this.realUserPwd = value;
	}
	
	/**
	 * Sets the value of the ssoTransferToken property.
	 *
	 * @param value
	 *            allowed object is
	 *            {@link String }
	 */
	public void setSSOTransferToken(String value) {
		this.ssoTransferToken = value;
	}
	
	/**
	 * Sets the value of the token property.
	 *
	 * @param value
	 *            allowed object is
	 *            {@link TokenType }
	 */
	public void setToken(TokenType value) {
		this.token = value;
	}
	
}
