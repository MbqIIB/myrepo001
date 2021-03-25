package com.npst.upiserver.acquirer.service;

import com.npst.upiserver.entity.MobUpiReqRespJson;

public interface MobReqChkTxnService {
	void procAndSendNpci(MobUpiReqRespJson mobUpiReqRespJson);
}
