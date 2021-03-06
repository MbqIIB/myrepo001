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
 * <p>Java class for recurrencePatternType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="recurrencePatternType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ONETIME"/>
 *     &lt;enumeration value="DAILY"/>
 *     &lt;enumeration value="WEEKLY"/>
 *     &lt;enumeration value="FORTNIGHTLY"/>
 *     &lt;enumeration value="BIMONTHLY"/>
 *     &lt;enumeration value="MONTHLY"/>
 *     &lt;enumeration value="QUARTERLY"/>
 *     &lt;enumeration value="HALFYEARLY"/>
 *     &lt;enumeration value="YEARLY"/>
 *     &lt;enumeration value="ASPRESENTED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "recurrencePatternType")
@XmlEnum
public enum RecurrencePatternType {

    ONETIME,
    DAILY,
    WEEKLY,
    FORTNIGHTLY,
    BIMONTHLY,
    MONTHLY,
    QUARTERLY,
    HALFYEARLY,
    YEARLY,
    ASPRESENTED;

    public String value() {
        return name();
    }

    public static RecurrencePatternType fromValue(String v) {
        return valueOf(v);
    }

}
