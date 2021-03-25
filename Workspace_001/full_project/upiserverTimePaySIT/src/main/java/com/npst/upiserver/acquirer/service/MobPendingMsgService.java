package com.npst.upiserver.acquirer.service;

import com.npst.upiserver.entity.MobUpiReqRespJson;

public interface MobPendingMsgService {
	void procAndSendNpci(MobUpiReqRespJson mobUpiReqRespJson);
}
