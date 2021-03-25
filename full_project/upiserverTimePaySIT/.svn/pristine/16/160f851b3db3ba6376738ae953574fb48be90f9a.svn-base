package com.npst.upiserver.service;

import com.npst.upiserver.dto.MerchantsTxnDto;
import com.npst.upiserver.dto.Message;
import com.npst.upiserver.dto.RegDto;
import com.npst.upiserver.entity.CustomerTxns;
import com.npst.upiserver.entity.MandatesHistoryEntity;
import com.npst.upiserver.npcischema.ReqAuthDetails;
import com.npst.upiserver.npcischema.ReqAuthMandate;
import com.npst.upiserver.npcischema.ReqMandateConfirmation;
import com.npst.upiserver.npcischema.RespMandate;

public interface NotificationService {
	void sendString(String message, String queueName);

	void convertInJsonAndSend(Object message, String queueName);

	void sendNotification(Message message);

	void sendNotification(String mobileno, String osType, String message, String module);

	void sendNotification(String mobileno, String osType, String message, String module, String sms);

	void sendSMS(Message message);

	void sendOTP(Message message);

	void sendSMS(String mobileno, String message, String msgType);

	void sendNoti(ReqAuthDetails reqAuthDetails, RegDto regvpa);

	void sendNoti(CustomerTxns customerTxns);

	void sendMerchantTxn(MerchantsTxnDto merchantsTxnDto);

	void sendNoti(ReqMandateConfirmation customerMandates,RegDto regvpa);
	void sendNoti(ReqAuthMandate reqAuthMandate, RegDto regvpa);
}
