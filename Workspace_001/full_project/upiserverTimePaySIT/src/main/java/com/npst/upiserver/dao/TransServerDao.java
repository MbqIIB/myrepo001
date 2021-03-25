package com.npst.upiserver.dao;

import com.npst.upiserver.dto.ReqResp;

public interface TransServerDao {

	void insertTransServer(final ReqResp reqResp, String reqMsgId,String revType);
}