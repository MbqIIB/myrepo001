package com.npst.upiserver.dao;

import java.util.Date;

import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.FirInfo;
import com.npst.upiserver.entity.ReqRespDebitCredit;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.ReqChkTxn;
import com.npst.upiserver.npcischema.ReqPay;
import com.npst.upiserver.npcischema.RespPay;

public interface ReqRespDebitCreditDao {
	ReqRespDebitCredit getOnTxnIdAndTxnType(ReqChkTxn reqChkTxn);
	void insertReqResp(ReqPay reqPay, RespPay respPay, Ack ack, Date reqDate, Date respDate, String revType,String cbsErrorCode, String cbsrrn);
	String insertReq(FirInfo firinfo);
	void updatePreApprovedReversal(ReqResp req, RespPay respPay, Date reqDate, Date respDate);
	void insertOnusAndPreApproed(ReqResp req, Date reqDate, Date respDate);


}
