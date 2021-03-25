package com.npst.upiserver.issuer.service;

import com.npst.upiserver.npcischema.ReqChkTxn;

public interface UpiReqChkTxnService {
	void issuerProcess(ReqChkTxn reqChkTxn);
}
