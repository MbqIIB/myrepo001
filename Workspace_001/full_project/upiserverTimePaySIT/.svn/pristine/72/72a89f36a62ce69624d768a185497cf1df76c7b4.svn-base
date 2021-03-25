package com.npst.upiserver.dao;

import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.MobUpiReqRespJson;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.ReqPay;

public interface MobUpiReqRespJsonIdDao {
	 void updateFail(MobUpiReqRespJson mobreqrespjson);
	 void updateDb(Ack ack, MobUpiReqRespJson mobreqrespjson, String txnType);
	 void updateDb(MobUpiReqRespJson mobreqrespjson);
	 void updateDb(MobUpiReqRespJson mobreqrespjson,ReqResp respJson);
	 void updateDb(ReqPay reqpay, Ack ack, MobUpiReqRespJson mobreqrespjson);
	 Long getMobReqRespJsonId(String msgId);
}
