package com.npst.upiserver.dao;

import java.util.Date;

import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.ReqAuthDetails;
import com.npst.upiserver.npcischema.ReqBalEnq;
import com.npst.upiserver.npcischema.ReqListAccount;
import com.npst.upiserver.npcischema.ReqOtp;
import com.npst.upiserver.npcischema.ReqPay;
import com.npst.upiserver.npcischema.ReqRegMob;
import com.npst.upiserver.npcischema.ReqSetCre;
import com.npst.upiserver.npcischema.ReqTxnConfirmation;
import com.npst.upiserver.npcischema.RespAuthDetails;
import com.npst.upiserver.npcischema.RespBalEnq;
import com.npst.upiserver.npcischema.RespListAccount;
import com.npst.upiserver.npcischema.RespOtp;
import com.npst.upiserver.npcischema.RespPay;
import com.npst.upiserver.npcischema.RespRegMob;
import com.npst.upiserver.npcischema.RespSetCre;

public interface CustomerTxnsDao {
	void insert(ReqAuthDetails reqAuthDetails);
	boolean update(RespAuthDetails respAuthDetails, Ack ack);
	boolean update(RespBalEnq respBalEnq);
	void update(RespOtp respOtp);
	void update(RespPay respPay);
	void update(RespRegMob respRegMob);
	void update(RespSetCre respSetCre);
	void insert(ReqBalEnq reqBalEnq, Ack ack);
	void insert(ReqListAccount reqListAccount, Ack ack);
	void insert(ReqOtp reqOtp, Ack ack);
	void insert(ReqPay reqPay, Ack ack) ;
	void insert(ReqRegMob reqRegMob, Ack ack);
	void insert(ReqSetCre reqSetCre, Ack ack);
	boolean update(RespListAccount respListAccount);
	boolean isHonouredTxn(String mandateUmn, String addr);
	void updateRespPay(RespPay respPay);
	void insertOnusBalReqResp(ReqResp req);
	void insertOnusPay(ReqResp req, Date reqDate, Date respDate);
	void update(ReqTxnConfirmation reqTxnConfirmation);
}
