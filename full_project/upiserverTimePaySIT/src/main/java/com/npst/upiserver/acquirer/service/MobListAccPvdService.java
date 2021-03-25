package com.npst.upiserver.acquirer.service;

import com.npst.upiserver.entity.MobUpiReqRespJson;

public interface MobListAccPvdService {
	void procAndSendNpci(MobUpiReqRespJson mobUpiReqRespJson);
}
