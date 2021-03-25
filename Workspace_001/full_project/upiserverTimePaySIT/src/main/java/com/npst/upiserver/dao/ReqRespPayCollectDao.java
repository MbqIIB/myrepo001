package com.npst.upiserver.dao;

import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.ReqPay;
import com.npst.upiserver.npcischema.RespPay;

public interface ReqRespPayCollectDao {
	void updateRespPay(RespPay respPay);
	void insertReq(ReqPay reqPay, Ack ack);
	void insertReqRespOnus(ReqResp req);
}
