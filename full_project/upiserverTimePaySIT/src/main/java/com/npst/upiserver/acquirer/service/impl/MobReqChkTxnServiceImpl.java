package com.npst.upiserver.acquirer.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.MobReqChkTxnService;
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
import com.npst.upiserver.npcischema.ReqChkTxn;
import com.npst.upiserver.service.NpciUpiRestConService;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.JsonMan;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.Util;

@Service
public class MobReqChkTxnServiceImpl implements MobReqChkTxnService{
	private static final Logger	log	= LoggerFactory.getLogger(MobReqChkTxnServiceImpl.class);
	
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
			mobReqRespJsonIdDao.updateMsgId(msgId, mobUpiReqRespJson.getIdPk().longValue());
			ReqChkTxn reqChkTxn = new ReqChkTxn();
			HeadType head = new HeadType();
			head.setMsgId(msgId);
			head.setOrgId(Constant.orgId);
			head.setTs(ts);
			head.setVer(Constant.headVer);
			reqChkTxn.setHead(head);
			PayTrans txn = new PayTrans();
			txn.setId(txnId);
			txn.setNote(reqJson.getTxnNote());
			txn.setRefId(reqJson.getTxnRefId());
			txn.setRefUrl(reqJson.getTxnRefUrl());
			txn.setTs(ts);
			txn.setOrgTxnId(reqJson.getOrgTxnId());
			txn.setType(PayConstant.CHK_TXN);
			reqChkTxn.setTxn(txn);
			String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(reqChkTxn).toString());
			Ack ack = npciService.send(xmlStr, ConstantI.API_REQ_CHK_TRANS, txnId);
			if (null != ack.getErr() || 0 != ack.getErrorMessages().size()) {
				log.debug("null != ack.getErr() || 0 != ack.getErrorMessages().size()");
				try {
					mobUpiReqRespJsonIdDao.updateDb(ack, mobUpiReqRespJson, reqChkTxn.getTxn().getType().toString());
				} catch (Exception e) {
					log.error(e.getMessage(),e);
				}
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
}
