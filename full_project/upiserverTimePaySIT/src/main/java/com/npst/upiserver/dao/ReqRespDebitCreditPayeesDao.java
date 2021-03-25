package com.npst.upiserver.dao;

import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.npcischema.PayeesType;

public interface ReqRespDebitCreditPayeesDao {
	void insertPayees(PayeesType payeesType, String txnId, String msgId, long id);

	void insertPayees(ReqResp reqResp, String txnId, String msgId);

}
