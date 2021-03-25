package com.npst.upiserver.acquirer.service;

import com.npst.upiserver.npcischema.RespPendingMsg;

public interface UpiRespPendingMsgService {
	void acquirerProcess(RespPendingMsg respPendingMsg, String message);
}
