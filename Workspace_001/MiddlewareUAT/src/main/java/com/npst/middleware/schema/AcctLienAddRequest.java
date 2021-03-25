package com.npst.middleware.schema;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.npst.middleware.schema.AcctLienAddRq.AcctId;
import com.npst.middleware.schema.AcctLienAddRq.LienDtls;

import scala.annotation.meta.setter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AcctLienAddRequest", propOrder = {
    "acctLienAddRq"
})
public class AcctLienAddRequest {

    @XmlElement(name = "AcctLienAddRq", required = true)
    protected AcctLienAddRq acctLienAddRq;
    
   
	public AcctLienAddRequest(AcctLienAddRq acctLienAddRq) {
		super();
		this.acctLienAddRq = acctLienAddRq;
	}

	/**
     * Gets the value of the acctLienAddRq property.
     * 
     * @return
     *     possible object is
     *     {@link FIXML.Body.AcctLienAddRequest.AcctLienAddRq }
     *     
     */
    public AcctLienAddRq getAcctLienAddRq(AcctId acctId, String moduleType, LienDtls lienDtls) {
        return new AcctLienAddRq( acctId,  moduleType,  lienDtls);
    }
    
    public void setAcctLienAddRq(AcctLienAddRq value) {
        this.acctLienAddRq = value;
    }
       
}
