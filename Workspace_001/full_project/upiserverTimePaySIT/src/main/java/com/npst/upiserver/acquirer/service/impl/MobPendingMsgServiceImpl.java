package com.npst.upiserver.acquirer.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.MobPendingMsgService;
import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.MobReqRespJsonIdDao;
import com.npst.upiserver.dao.MobUpiReqRespJsonIdDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.MobUpiReqRespJson;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.HeadType;
import com.npst.upiserver.npcischema.PayConstant;
import com.npst.upiserver.npcischema.PayTrans;
import com.npst.upiserver.npcischema.ReqMsgType;
import com.npst.upiserver.npcischema.ReqPendingMsg;
import com.npst.upiserver.npcischema.ReqPendingMsg.ReqMsg;
import com.npst.upiserver.service.NpciUpiRestConService;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.JsonMan;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.Util;

@Service
public class MobPendingMsgServiceImpl implements MobPendingMsgService {
	private static final Logger log = LoggerFactory.getLogger(MobPendingMsgServiceImpl.class);
	
	@Autowired
	MobReqRespJsonIdDao			mobReqRespJsonIdDao;
	@Autowired
	NpciUpiRestConService		npciService;
	@Autowired
	MobUpiReqRespJsonIdDao		mobUpiReqRespJsonIdDao;
	@Override
	public void procAndSendNpci(MobUpiReqRespJson mobUpiReqRespJson) {
		try {
			ReqResp reqJson = JsonMan.getReqResp(mobUpiReqRespJson.getReq());
			String ts = Util.getTS();
			String txnId = Util.uuidGen();
			String msgId = Util.uuidGen();
			ReqPendingMsg reqPendingMsg = new ReqPendingMsg();
			mobReqRespJsonIdDao.updateMsgId(msgId, mobUpiReqRespJson.getIdPk().longValue());
			reqPendingMsg.setHead(setHeadTypeDetails(msgId, ts));
			reqPendingMsg.setTxn(setPayTransDetails(txnId,reqJson.getTxnNote(),reqJson.getTxnRefId(),reqJson.getTxnRefUrl(),ts,reqJson.getTxnType()));
			ReqMsg reqMsg = new ReqMsg();
			reqMsg.setAddr(reqJson.getPayerAddr());
			reqMsg.setType(ReqMsgType.fromValue(reqJson.getPayerAddrType()));
			if (ConstantI.MOBILE.equalsIgnoreCase(reqJson.getPayerAddrType())) {
				reqMsg.setValue(reqJson.getPayerMobileNo());
			} else if (ConstantI.AADHAAR.equalsIgnoreCase(reqJson.getPayerAddrType())) {
				reqMsg.setValue(reqJson.getPayerUidNum());
			}
			reqPendingMsg.setReqMsg(reqMsg);
			String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(reqPendingMsg).toString());
			Ack ack = npciService.send(xmlStr,ConstantI.API_REQ_PNDING_MSG, txnId);
			if (null != ack.getErr() || 0 != ack.getErrorMessages().size()) {
				try {
					mobUpiReqRespJsonIdDao.updateDb(ack, mobUpiReqRespJson, reqPendingMsg.getTxn().getType().toString());
				} catch (Exception e) {
					log.error(e.getMessage(),e);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	private HeadType setHeadTypeDetails(final String msgId,final String ts) throws Exception {
		HeadType head = new HeadType();
		try {
			head.setMsgId(msgId);
			head.setOrgId(Constant.orgId);
			head.setTs(ts);
			head.setVer(Constant.headVer);
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return head;
	}
	private PayTrans setPayTransDetails(final String txnId,final String txnNote,final String txnRefId,final String txnRefUrl,final String ts,final String txnType) throws Exception {
		PayTrans txn = new PayTrans();
		try {
			txn.setId(txnId);
			txn.setNote(txnNote);
			txn.setRefId(txnRefId);
			txn.setRefUrl(txnRefUrl);
			txn.setTs(ts);
			txn.setType(PayConstant.fromValue(txnType));
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return txn;
	}
}