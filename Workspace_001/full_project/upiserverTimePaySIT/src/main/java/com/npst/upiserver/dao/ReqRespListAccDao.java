package com.npst.upiserver.dao;

import java.util.Date;

import com.npst.upiserver.entity.Registration;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.ReqListAccount;
import com.npst.upiserver.npcischema.RespListAccount;

public interface ReqRespListAccDao {
	void insertReqResp(ReqListAccount reqListAccount, RespListAccount respListAccount, Ack ack, Date reqDate);
	void updateResp(RespListAccount respListAccount);
	boolean checkRiskListAccReq(Long regId,Date lastlogindt);
	void insertReq(ReqListAccount reqListAccount, Ack ack);
	void insertUpdateRiskCount(Registration regDetails);
}
