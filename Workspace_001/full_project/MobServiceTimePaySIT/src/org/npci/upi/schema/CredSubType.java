//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB)
// Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source
// schema.
// Generated on: 2017.02.01 at 09:58:09 PM IST
//

package org.npci.upi.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for credSubType.
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * <p>
 *
 * <pre>
 * &lt;simpleType name="credSubType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="IIR"/>
 *     &lt;enumeration value="FMR"/>
 *     &lt;enumeration value="FIR"/>
 *     &lt;enumeration value="OTP"/>
 *     &lt;enumeration value="SMS"/>
 *     &lt;enumeration value="EMAIL"/>
 *     &lt;enumeration value="HOTP"/>
 *     &lt;enumeration value="TOTP"/>
 *     &lt;enumeration value="MPIN"/>
 *     &lt;enumeration value="ATMPIN"/>
 *     &lt;enumeration value="CVV1"/>
 *     &lt;enumeration value="CVV2"/>
 *     &lt;enumeration value="EMV"/>
 *     &lt;enumeration value="initial"/>
 *     &lt;enumeration value="reset"/>
 *     &lt;enumeration value="rotate"/>
 *     &lt;enumeration value="NA"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "credSubType")
@XmlEnum
public enum CredSubType {

	IIR("IIR"), FMR("FMR"), FIR("FIR"), OTP("OTP"), SMS("SMS"), EMAIL("EMAIL"), HOTP("HOTP"), TOTP("TOTP"), MPIN(
			"MPIN"), ATMPIN("ATMPIN"), @XmlEnumValue("CVV1")
	CVV_1("CVV1"), @XmlEnumValue("CVV2")
	CVV_2("CVV2"), EMV("EMV"), @XmlEnumValue("initial")
	INITIAL("initial"), @XmlEnumValue("reset")
	RESET("reset"), @XmlEnumValue("rotate")
	ROTATE("rotate"), NA("NA");
	public static CredSubType fromValue(String v) {
		for (CredSubType c : CredSubType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}

	private final String value;

	CredSubType(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

}
