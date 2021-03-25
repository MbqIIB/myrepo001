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
 * <p>Java class for listedAccountType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="listedAccountType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SAVINGS"/>
 *     &lt;enumeration value="CURRENT"/>
 *     &lt;enumeration value="DEFAULT"/>
 *     &lt;enumeration value="NRE"/>
 *     &lt;enumeration value="NRO"/>
 *     &lt;enumeration value="CREDIT"/>
 *     &lt;enumeration value="PPIWALLET"/>
 *     &lt;enumeration value="BANKWALLET"/>
 *     &lt;enumeration value="SOD"/>
 *     &lt;enumeration value="UOD"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "listedAccountType")
@XmlEnum
public enum ListedAccountType {

    SAVINGS,
    CURRENT,
    DEFAULT,
    NRE,
    NRO,
    CREDIT,
    PPIWALLET,
    BANKWALLET,
    SOD,
    UOD;

    public String value() {
        return name();
    }

    public static ListedAccountType fromValue(String v) {
        return valueOf(v);
    }

}
