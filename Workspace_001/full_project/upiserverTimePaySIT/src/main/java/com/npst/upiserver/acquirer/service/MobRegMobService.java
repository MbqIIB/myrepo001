package com.npst.upiserver.acquirer.service;

import com.npst.upiserver.entity.MobUpiReqRespJson;

public interface MobRegMobService {
	void procAndSendNpci(MobUpiReqRespJson mobUpiReqRespJson);
}
