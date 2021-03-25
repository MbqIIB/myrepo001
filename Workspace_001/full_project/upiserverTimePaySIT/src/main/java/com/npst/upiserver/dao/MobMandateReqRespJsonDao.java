package com.npst.upiserver.dao;

import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.MobMandateReqRespJsonEntity;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.ReqMandate;

public interface MobMandateReqRespJsonDao {

	void finalDbUpdate(ReqResp respJson, long idPk);

	void finalDbUpdate(MobMandateReqRespJsonEntity mobMandateReqRespJson);

	void updateFail(MobMandateReqRespJsonEntity mobMandateReqRespJson);

	void updateMsgId(String msgId, long idPk);

	void updateDb(ReqMandate reqMandate, Ack ack, MobMandateReqRespJsonEntity mobMandateReqRespJson);

	void updateMobMandateReqRespJsonIdEntity(long idPk, int flag);

	void updateMobMandateReqRespJsonIdEntity(long idpk, String msgId);

	long getIdPkByReqMsgId(String reqMsgId);

	long getIdPkByTxnId(String txnId);

	void updateDb(Ack ack, MobMandateReqRespJsonEntity mobMandateReqRespJson, String string);

}
