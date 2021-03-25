package com.npst.upiserver.dao;

import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.MobUpiReqRespJson;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.ReqTxnConfirmation;
import com.npst.upiserver.npcischema.RespBalEnq;
import com.npst.upiserver.npcischema.RespListAccPvd;
import com.npst.upiserver.npcischema.RespListAccount;
import com.npst.upiserver.npcischema.RespListKeys;
import com.npst.upiserver.npcischema.RespOtp;
import com.npst.upiserver.npcischema.RespPay;
import com.npst.upiserver.npcischema.RespPendingMsg;
import com.npst.upiserver.npcischema.RespRegMob;
import com.npst.upiserver.npcischema.RespSetCre;
import com.npst.upiserver.npcischema.RespValAdd;

public interface MobReqRespJsonDao {
//	void updateDb(Long idmobreqrespjson, ReqTxnConfirmation reqTxnConfirmation, String message);
//	void updateDb(Long idmobreqrespjson, RespBalEnq respBalEnq, String message);
//	void updateDb(Long idmobreqrespjson, RespListAccount respListAccount, String message);
//	void updateDb(Long idmobreqrespjson, RespListAccPvd respListAccPvd, String message);
//	void updateDb(Long idmobreqrespjson, RespListKeys respListKeys, String message);
//	void updateDb(Long idmobreqrespjson, RespOtp respOtp, String message);
//	void updateDb(Long idmobreqrespjson, RespPay respPay, String message);
//	void updateDb(Long idmobreqrespjson, RespPendingMsg respPendingMsg, String message);
//	void updateDb(Long idmobreqrespjson, RespRegMob respRegMob, String message);
//	void updateDb(Long idmobreqrespjson, RespSetCre respSetCre, String message);
//	void updateDb(Long idmobreqrespjson, RespValAdd respValAdd, String message);
	long getIdPkByReqMsgId(String reqMsgId);
	void finalDbUpdate(ReqResp respJson, long idPk);
	boolean isPreApprovedTxn(long idPk);
	MobUpiReqRespJson getByIdPk(long idPk);
	void updateMsgId(String msgId, long idPk);
	void updateDb(Ack ack, MobUpiReqRespJson mobreqrespjson, String string);
	void updateMobUpiReqRespJsonIdEntity(long idpk, String msgId);
	public void updateMobUpiReqRespJsonIdEntity(long idpk, int flag);
}
