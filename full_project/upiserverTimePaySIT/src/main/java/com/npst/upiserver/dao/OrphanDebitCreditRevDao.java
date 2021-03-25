package com.npst.upiserver.dao;

import java.util.Date;

import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.ReqPay;
import com.npst.upiserver.npcischema.RespPay;

public interface OrphanDebitCreditRevDao {

	void insertReversal(ReqPay reqPay, RespPay respPay, Ack ack, Date reqDate, Date respDate, String revType,
			String cbsErrorCode, String cbsrrn);
	
	void insertRevPreApproved(ReqResp reqResp, RespPay respPay, Date reqDate, Date respDate);

}
