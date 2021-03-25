package com.npst.upiserver.acquirer.service;

import com.npst.upiserver.npcischema.ReqAuthMandate;

public interface UpiReqAuthMandateService {
	void acquirerProcess(ReqAuthMandate reqAuthMandate);
}
