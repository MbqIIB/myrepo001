/**
 * 
 */
package com.npst.upiserver.constant;

/**
 * @author npst
 *
 */
public enum UpiApiName {
	
	REQ_MANDATE("ReqMandate"),
	RESP_MANDATE_AUTH("RespAuthMandate"),
	RESP_MANDATE_CONFIRMATION("RespMandateConfirmation"), 
	RESP_MANDATE("RespMandate");
	
	private final String name;
	
	UpiApiName(final String name) {
		this.name=name;
	}

	public String getName() {
		return name;
	}

}
