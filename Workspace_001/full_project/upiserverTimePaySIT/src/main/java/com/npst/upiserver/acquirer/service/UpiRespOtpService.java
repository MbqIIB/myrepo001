package com.npst.upiserver.acquirer.service;

import com.npst.upiserver.npcischema.RespOtp;

public interface UpiRespOtpService {
	void acquirerProcess(RespOtp respOtp,String message);
}
