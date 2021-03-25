package com.npst.upiserver.acquirer.service;

import com.npst.upiserver.entity.MobUpiReqRespJson;

public interface MobCollectBlockService {
	void procAndSendNpci(MobUpiReqRespJson mobUpiReqRespJson);
}
