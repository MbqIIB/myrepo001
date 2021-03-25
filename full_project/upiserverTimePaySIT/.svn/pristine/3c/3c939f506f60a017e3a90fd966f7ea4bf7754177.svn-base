package com.npst.upiserver.dao;

import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.ReqMandate;
import com.npst.upiserver.npcischema.ReqMandateConfirmation;
import com.npst.upiserver.npcischema.ReqPay;
import com.npst.upiserver.npcischema.RespMandate;
import com.npst.upiserver.npcischema.RespPay;

public interface MandateTxnsDao {

	void updateMandatesTxns(RespPay respPay);

	void insertMandatesHistory(ReqMandate reqMandate, Ack ack, ReqResp reqJson);

	void updateRespMandateHistory(RespMandate respMandate);

	void insertSuccessCustomerMandate(ReqMandateConfirmation reqMandateConfirmation);

	void updateCustomerMandateHistory(ReqMandateConfirmation reqMandateConfirmation);

	boolean checkIfTransactionIsProcessed(String mandateUmn);

	void insertReqpayAndAck(ReqPay reqpay, Ack ack);
}
