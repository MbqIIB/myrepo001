package com.npst.upiserver.acquirer.service;

import com.npst.upiserver.dto.ReqResp;

public interface RejectOnPayeeAddrService {
	 void create(String payeeAddr, Long regId, ReqResp reqJson);
}
