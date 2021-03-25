package com.npst.upiserver.acquirer.service;

import com.npst.upiserver.npcischema.RespBalEnq;

public interface UpiRespBalEnqService {
	void acquirerProcess(RespBalEnq respBalEnq,String message);
}
