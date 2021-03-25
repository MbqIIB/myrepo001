package com.npst.middleware.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import com.npst.middleware.schema.AcctLienAddRq.LienDtls.LienDt;
import com.npst.middleware.schema.AcctLienAddRq.LienDtls.NewLienAmt;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AcctLienAddRq", propOrder = {
    "acctId",
    "moduleType",
    "lienDtls"
})
public  class AcctLienAddRq {

    @XmlElement(name = "AcctId", required = true)
    protected AcctId acctId;
    @XmlElement(name = "ModuleType", required = true)
    protected String moduleType;
    @XmlElement(name = "LienDtls", required = true)
    protected LienDtls lienDtls;

    public AcctLienAddRq(AcctId acctId, String moduleType, LienDtls lienDtls) {
		super();
		this.acctId = acctId;
		this.moduleType = moduleType;
		this.lienDtls = lienDtls;
	}

	/**
     * Gets the value of the acctId property.
     * 
     * @return
     *     possible object is
     *     {@link FIXML.Body.AcctLienAddRequest.AcctLienAddRq.AcctIds }
     *     
     */
    public AcctLienAddRq.AcctId getAcctId() {
        return new AcctId();
    }

    /**
     * Sets the value of the acctId property.
     * 
     * @param value
     *     allowed object is
     *     {@link FIXML.Body.AcctLienAddRequest.AcctLienAddRq.AcctIds }
     *     
     */
    public void setAcctId(AcctLienAddRq.AcctId value) {
        this.acctId = value;
    }

    /**
     * Gets the value of the moduleType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModuleType() {
        return moduleType;
    }

    /**
     * Sets the value of the moduleType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModuleType(String value) {
        this.moduleType = value;
    }

    /**
     * Gets the value of the lienDtls property.
     * 
     * @return
     *     possible object is
     *     {@link FIXML.Body.AcctLienAddRequest.AcctLienAddRq.LienDtlss }
     *     
     */
    public AcctLienAddRq.LienDtls getLienDtls(NewLienAmt newLienAmt, LienDt lienDt, String reasonCode) {
        return new LienDtls(newLienAmt, lienDt, reasonCode);
    }

    /**
     * Sets the value of the lienDtls property.
     * 
     * @param value
     *     allowed object is
     *     {@link FIXML.Body.AcctLienAddRequest.AcctLienAddRq.LienDtlss }
     *     
     */
    public void setLienDtls(AcctLienAddRq.LienDtls value) {
        this.lienDtls = value;
    }

    
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
     *         &lt;element name="AcctId" type="{http://www.w3.org/2001/XMLSchema}long"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "AcctId", propOrder = {
        "acctId"
    })
    public static class AcctId {

        @XmlElement(name = "AcctId")
        protected String acctId;

        /**
         * Gets the value of the acctId property.
         * 
         */
        public String getAcctId() {
            return acctId;
        }

        /**
         * Sets the value of the acctId property.
         * 
         */
        public void setAcctId(String accNo) {
            this.acctId = accNo;
        }

    }
    
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "LienDtls", propOrder = {
        "newLienAmt",
        "lienDt",
        "reasonCode",
        "lienId"
    })
    public static class LienDtls {

        @XmlElement(name = "NewLienAmt", required = true)
        protected NewLienAmt newLienAmt;
        @XmlElement(name = "LienDt", required = true)
        protected LienDt lienDt;
        @XmlElement(name = "ReasonCode")
        protected String reasonCode;
        
        @XmlElement(name = "LienId")
        protected String lienId;

        
        public LienDtls(NewLienAmt newLienAmt, LienDt lienDt, String reasonCode) {
			super();
			this.newLienAmt = newLienAmt;
			this.lienDt = lienDt;
			this.reasonCode = reasonCode;
			
		}

        public LienDtls(NewLienAmt newLienAmt,  String reasonCode) {
			super();
			this.newLienAmt = newLienAmt;
			
			this.reasonCode = reasonCode;
			
		}
		/**
         * Gets the value of the newLienAmt property.
         * 
         * @return
         *     possible object is
         *     {@link FIXML.Body.AcctLienAddRequest.AcctLienAddRq.LienDtlss.NewLienAmts }
         *     
         */
        public NewLienAmt getNewLienAmt() {
            return new NewLienAmt();
        }

        /**
         * Sets the value of the newLienAmt property.
         * 
         * @param value
         *     allowed object is
         *     {@link FIXML.Body.AcctLienAddRequest.AcctLienAddRq.LienDtlss.NewLienAmts }
         *     
         */
        public void setNewLienAmt(NewLienAmt value) {
            this.newLienAmt = value;
        }

        /**
         * Gets the value of the reasonCode property.
         * 
         */
        public String getReasonCode() {
            return reasonCode;
        }

        /**
         * Sets the value of the reasonCode property.
         * 
         */
        public void setReasonCode(String value) {
            this.reasonCode = value;
        }

		public String getLienId() {
			return lienId;
		}

		public void setLienId(String string) {
			this.lienId = string;
		}

		public LienDt getLienDt() {
			return new LienDt() ;
		}

		public void setLienDt(LienDt lienDt) {
			this.lienDt = lienDt;
		}
		
		 @XmlAccessorType(XmlAccessType.FIELD)
		    @XmlType(name = "NewLienAmt", propOrder = {
		        "amountValue",
		        "currencyCode"
		    })
		    public static class NewLienAmt {

		        protected String amountValue;
		        @XmlElement(required = true)
		        protected String currencyCode;

		        /**
		         * Gets the value of the amountValue property.
		         * 
		         */
		        public String getAmountValue() {
		            return amountValue;
		        }

		        /**
		         * Sets the value of the amountValue property.
		         * 
		         */
		        public void setAmountValue(String value) {
		            this.amountValue = value;
		        }

		        /**
		         * Gets the value of the currencyCode property.
		         * 
		         * @return
		         *     possible object is
		         *     {@link String }
		         *     
		         */
		        public String getCurrencyCode() {
		            return currencyCode;
		        }

		        /**
		         * Sets the value of the currencyCode property.
		         * 
		         * @param value
		         *     allowed object is
		         *     {@link String }
		         *     
		         */
		        public void setCurrencyCode(String value) {
		            this.currencyCode = value;
		        }
		    }
		    
		    @XmlAccessorType(XmlAccessType.FIELD)
		    @XmlType(name = "LienDt", propOrder = {
		        "startDt",
		        "endDt"
		    })
		    public static class LienDt {

		        @XmlElement(name = "StartDt", required = true)
		        @XmlSchemaType(name = "dateTime")
		        protected String startDt;
		        @XmlElement(name = "EndDt", required = true)
		        @XmlSchemaType(name = "dateTime")
		        protected String endDt;

		        /**
		         * Gets the value of the startDt property.
		         * 
		         * @return
		         *     possible object is
		         *     {@link XMLGregorianCalendar }
		         *     
		         */
		        public String getStartDt() {
		            return startDt;
		        }

		        /**
		         * Sets the value of the startDt property.
		         * 
		         * @param value
		         *     allowed object is
		         *     {@link XMLGregorianCalendar }
		         *     
		         */
		        public void setStartDt(String value) {
		            this.startDt = value;
		        }

		        /**
		         * Gets the value of the endDt property.
		         * 
		         * @return
		         *     possible object is
		         *     {@link XMLGregorianCalendar }
		         *     
		         */
		        public String getEndDt() {
		            return endDt;
		        }

		        /**
		         * Sets the value of the endDt property.
		         * 
		         * @param value
		         *     allowed object is
		         *     {@link XMLGregorianCalendar }
		         *     
		         */
		        public void setEndDt(String value) {
		            this.endDt = value;
		        }

		    }

    }
    

}