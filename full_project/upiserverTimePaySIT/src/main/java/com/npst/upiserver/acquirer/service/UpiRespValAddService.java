package com.npst.upiserver.acquirer.service;

import com.npst.upiserver.npcischema.RespValAdd;

public interface UpiRespValAddService {
	void acquirerProcess(RespValAdd respValAdd,String message);
}
