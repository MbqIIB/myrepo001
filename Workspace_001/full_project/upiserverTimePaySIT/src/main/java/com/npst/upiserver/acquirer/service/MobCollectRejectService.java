package com.npst.upiserver.acquirer.service;

import com.npst.upiserver.entity.MobUpiReqRespJson;

public interface MobCollectRejectService {
	void procAndSendNpci(MobUpiReqRespJson mobUpiReqRespJson);
}
