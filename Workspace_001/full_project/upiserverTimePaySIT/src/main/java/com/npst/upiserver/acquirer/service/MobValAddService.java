package com.npst.upiserver.acquirer.service;

import com.npst.upiserver.entity.MobUpiReqRespJson;

public interface MobValAddService {
	void procAndSendNpci(MobUpiReqRespJson mobUpiReqRespJson);
}
