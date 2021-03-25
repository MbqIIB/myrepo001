package com.npst.upiserver.dao;

import com.npst.upiserver.entity.ReqRespAuthMandateEntity;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.ReqAuthMandate;
import com.npst.upiserver.npcischema.ReqMandateConfirmation;
import com.npst.upiserver.npcischema.RespAuthMandate;
import com.npst.upiserver.npcischema.RespMandateConfirmation;

public interface ReqRespAuthMandateDao {

	void updateMandateConfirmation(ReqMandateConfirmation reqMandateConfirmation,
			RespMandateConfirmation respMandateConfirmation, Ack ack);

	void insertReq(ReqAuthMandate reqAuthMandate);

	void updateResp(RespAuthMandate respAuthMandate, Ack ack);

	ReqRespAuthMandateEntity getByTxnId(String txnId);

}
