package com.npst.middleware.cbs.service;

import com.npst.middleware.obj.ReqResp;

public interface ATMProcess {
	ReqResp getFullCardNumber(final ReqResp reqResp);

	ReqResp verifyATMPIN(final ReqResp reqResp);

}
