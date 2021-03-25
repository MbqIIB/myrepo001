package com.npst.upiserver.service;

import com.npst.upiserver.dto.ReqResp;

public interface PayAccTypeValidationService {
	boolean isAccTypeValid(ReqResp req);
}