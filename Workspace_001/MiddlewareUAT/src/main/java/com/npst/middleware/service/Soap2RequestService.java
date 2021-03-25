package com.npst.middleware.service;

import com.npst.middleware.obj.ReqResp;

public interface Soap2RequestService {
	String getParseFixmlReq(final ReqResp reqResp,final String mobileNo);
	
	String getParseFixmlReqForLienAdd(final ReqResp reqResp,final String accNo);
	
	String getParseFixmlReqForLienModify(final ReqResp reqResp,final String accNo);
	
	String getParseFixmlReqForLienRevok(final ReqResp reqResp,final String accNo);
	
	String getParseFixmlReqForLienInq(final ReqResp reqResp,final String accNo);
}
