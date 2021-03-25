package com.npst.upiserver.dao;

import java.util.Date;
import java.util.List;

import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.MandatesEntity;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.MandateSign;
import com.npst.upiserver.npcischema.PayeesType;
import com.npst.upiserver.npcischema.ReqMandate;
import com.npst.upiserver.npcischema.ReqMandateConfirmation;
import com.npst.upiserver.npcischema.ReqPay;
import com.npst.upiserver.npcischema.RespMandate;

public interface MandatesDao {

	MandatesEntity findByUmn(String umn);

	void createUpdateMandate(ReqMandate reqMandate, Date reqDate,MandateSign mandateSign,ReqResp req);
	
	ReqResp insertReqRespMandate(ReqMandate reqMandate,ReqResp req,MandateSign mandateSign);
	void UpdateMandate(ReqPay reqPay,Date reqDate,ReqResp req);
	//void UpdateMandate1(ReqPay reqPay,Date reqDate);
	void insertReqRespMandate(ReqMandate reqMandate, RespMandate respMandate, Ack ack);

	void insertReqRespMandatesPayees(PayeesType payeesType, String txnId, String msgId, Long idMandates);

	void insertReqMandate(ReqMandate reqMandate, Ack ack);

	void updateRespMandate(RespMandate respMandate, Ack ack);
	void updateRespMandate(RespMandate respMandate);
	void insertSuccessCustomerMandates(RespMandate respMandate);
	//void insertSuccessCustomerMandates()
	void insertSuccessCustomerMandates(ReqMandateConfirmation reqMandateConfirmation);

	List<MandatesEntity> findByAccountAndStatus(String accntNo, String status, String status2);

}
