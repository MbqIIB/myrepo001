package com.npst.upiserver.acquirer.service;

import com.npst.upiserver.entity.MobUpiReqRespJson;

public interface MobBalReqService {
	void procAndSendNpci(MobUpiReqRespJson mobUpiReqRespJson);
}
