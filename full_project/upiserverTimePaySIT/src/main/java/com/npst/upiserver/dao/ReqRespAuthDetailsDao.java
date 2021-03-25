package com.npst.upiserver.dao;

import java.util.Date;
import java.util.List;

import com.npst.upiserver.entity.ReqRespAuthDetails;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.ReqAuthDetails;
import com.npst.upiserver.npcischema.ReqTxnConfirmation;
import com.npst.upiserver.npcischema.RespAuthDetails;
import com.npst.upiserver.npcischema.RespTxnConfirmation;

public interface ReqRespAuthDetailsDao {
	ReqRespAuthDetails getOnTxnId(String txnId);
	
	void insertReq(ReqAuthDetails reqAuthDetails, RespAuthDetails respAuthDetails, Ack ack, Date reqDate,
			Date respDate);
	
	void updateTxnConfirmation(ReqTxnConfirmation reqTxnConfirmation, RespTxnConfirmation respTxnConfirmation, Ack ack);
	
	List<ReqRespAuthDetails> getOnIdReqRespAuthDetails();
	
	List<ReqRespAuthDetails> getOnPayeeAddr(String payeeAddr, Long regId);
	
	void updateResp(RespAuthDetails respAuthDetails, Ack ack);
	
	void insertReq(ReqAuthDetails reqAuthDetails);
	
	List<ReqRespAuthDetails> getAllByCollectAndRespInsertIsNull();
}
