package com.npst.upiserver.acquirer.service;

import com.npst.upiserver.entity.MobUpiReqRespJson;

public interface MobListAccountService {
	void procAndSendNpci(MobUpiReqRespJson mobUpiReqRespJson);
}
