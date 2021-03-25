package com.npst.upiserver.dao;

import com.npst.upiserver.entity.ReqRespAuthDetailsPayees;
import com.npst.upiserver.npcischema.PayeesType;

public interface ReqRespAuthDetailsPayeesDao {
	ReqRespAuthDetailsPayees getPayees(Long idReqRespAuthDetails);
	ReqRespAuthDetailsPayees getPayeesByTxnId(String payeeAddr, String txnId);
	void insertPayees(PayeesType payees, String txnId, String msgId, long id);
	ReqRespAuthDetailsPayees getByPayeesVpaAndIdReqRespAuthDetails(String payeeVpa,Long idReqRespAuthDetails);

	
}
