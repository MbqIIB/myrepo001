//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB)
// Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source
// schema.
// Generated on: 2017.02.01 at 09:58:09 PM IST
//

package org.npci.upi.schema;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for riskScoresType complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="riskScoresType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Score" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="provider" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "riskScoresType", propOrder = { "score" })
public class RiskScoresType {

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
	 *       &lt;attribute name="provider" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *       &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "")
	public static class Score {

		@XmlAttribute(name = "provider", required = true)
		protected String provider;
		@XmlAttribute(name = "type", required = true)
		protected String type;
		@XmlAttribute(name = "value", required = true)
		protected String value;

		/**
		 * Gets the value of the provider property.
		 *
		 * @return possible object is {@link String }
		 */
		public String getProvider() {
			return provider;
		}

		/**
		 * Gets the value of the type property.
		 *
		 * @return possible object is {@link String }
		 */
		public String getType() {
			return type;
		}

		/**
		 * Gets the value of the value property.
		 *
		 * @return possible object is {@link String }
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Sets the value of the provider property.
		 *
		 * @param value
		 *            allowed object is {@link String }
		 */
		public void setProvider(String value) {
			this.provider = value;
		}

		/**
		 * Sets the value of the type property.
		 *
		 * @param value
		 *            allowed object is {@link String }
		 */
		public void setType(String value) {
			this.type = value;
		}

		/**
		 * Sets the value of the value property.
		 *
		 * @param value
		 *            allowed object is {@link String }
		 */
		public void setValue(String value) {
			this.value = value;
		}

	}

	@XmlElement(name = "Score")
	protected List<RiskScoresType.Score> score;

	/**
	 * Gets the value of the score property.
	 * <p>
	 * This accessor method returns a reference to the live list, not a snapshot.
	 * Therefore any modification you make to the returned list will be present
	 * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
	 * for the score property.
	 * <p>
	 * For example, to add a new item, do as follows:
	 *
	 * <pre>
	 * getScore().add(newItem);
	 * </pre>
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link RiskScoresType.Score }
	 */
	public List<RiskScoresType.Score> getScore() {
		if (score == null) {
			score = new ArrayList<RiskScoresType.Score>();
		}
		return this.score;
	}

}