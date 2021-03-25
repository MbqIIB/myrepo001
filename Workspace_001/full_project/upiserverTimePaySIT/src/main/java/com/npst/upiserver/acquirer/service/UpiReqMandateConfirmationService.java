package com.npst.upiserver.acquirer.service;

import com.npst.upiserver.npcischema.ReqMandateConfirmation;

public interface UpiReqMandateConfirmationService {
	void acquirerProcess(ReqMandateConfirmation reqMandateConfirmation);
}
