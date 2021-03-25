package com.npst.upiserver.acquirer.service;

import com.npst.upiserver.entity.MobUpiReqRespJson;

public interface MobListKeysService {
	void procAndSendNpci(MobUpiReqRespJson mobUpiReqRespJson);
}
