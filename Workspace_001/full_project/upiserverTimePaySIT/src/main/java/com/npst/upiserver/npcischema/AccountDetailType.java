//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.12.14 at 11:57:36 AM IST 
//


package com.npst.upiserver.npcischema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for accountDetailType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="accountDetailType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="IIN"/>
 *     &lt;enumeration value="UIDNUM"/>
 *     &lt;enumeration value="IFSC"/>
 *     &lt;enumeration value="ACTYPE"/>
 *     &lt;enumeration value="ACNUM"/>
 *     &lt;enumeration value="MMID"/>
 *     &lt;enumeration value="MOBNUM"/>
 *     &lt;enumeration value="CARDNUM"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "accountDetailType")
@XmlEnum
public enum AccountDetailType {

    IIN,
    UIDNUM,
    IFSC,
    ACTYPE,
    ACNUM,
    MMID,
    MOBNUM,
    CARDNUM;

    public String value() {
        return name();
    }

    public static AccountDetailType fromValue(String v) {
        return valueOf(v);
    }

}
