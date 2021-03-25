package com.npst.upiserver.dao;

import com.npst.upiserver.entity.ReqRespAuthMandatePayeesEntity;
import com.npst.upiserver.npcischema.PayeesType;

public interface ReqRespAuthMandatePayeesDao {

	void insertPayees(PayeesType payeesType, String txnId, String msgId, Long idReqRespAuthMandate);

	ReqRespAuthMandatePayeesEntity getByIdReqRespAuthMandate(long idReqRespAuthMandate);

}
