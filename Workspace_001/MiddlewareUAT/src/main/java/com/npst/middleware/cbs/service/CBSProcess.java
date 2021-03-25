package com.npst.middleware.cbs.service;

import com.npst.middleware.obj.ReqResp;

public interface CBSProcess {

	ReqResp balEnq(final ReqResp reqResp);

	ReqResp creditAccount(final ReqResp reqResp);

	ReqResp creditReversalAccount(final ReqResp reqResp);
	
	ReqResp creditReversalFIRAccount(final ReqResp reqResp);
	ReqResp debitAccount(final ReqResp reqResp);

	ReqResp debitReversalAccount(final ReqResp reqResp);

	ReqResp fatchAccounts(final ReqResp reqResp, final String custId, final String custName, final String mobileNo);

	ReqResp creditAadhar(final ReqResp reqResp);

	ReqResp creditReversalAadhar(final ReqResp reqResp);
	
	String getIfscCode(final ReqResp reqResp,final String custAccNo);
	
	ReqResp accountVerification(final ReqResp reqResp);
	//ReqResp creditAccountFir(final ReqResp reqResp);
	String  accountVerificationForListAccount(ReqResp reqResp, final String custAccNum) ;
}
