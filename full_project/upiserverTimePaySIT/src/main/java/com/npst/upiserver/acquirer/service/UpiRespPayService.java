package com.npst.upiserver.acquirer.service;

import com.npst.upiserver.npcischema.RespPay;

public interface UpiRespPayService {
	void acquirerProcess(RespPay respPay,final String message);
}
