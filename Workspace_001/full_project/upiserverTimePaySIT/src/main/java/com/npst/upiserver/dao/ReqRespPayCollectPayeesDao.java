package com.npst.upiserver.dao;

import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.npcischema.PayeesType;

public interface ReqRespPayCollectPayeesDao {

	void insertPayees(PayeesType payeesType, String txnId, String msgId, long idReqRespPayCollect);

	void insertPayees(ReqResp reqResp, String txnId, String msgId);

}
