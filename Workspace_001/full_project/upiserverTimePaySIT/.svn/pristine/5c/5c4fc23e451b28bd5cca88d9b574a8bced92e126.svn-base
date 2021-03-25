package com.npst.upiserver.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.ReqRespVaePspKeysDao;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.HeadType;
import com.npst.upiserver.npcischema.PayConstant;
import com.npst.upiserver.npcischema.PayTrans;
import com.npst.upiserver.npcischema.ReqListKeys;
import com.npst.upiserver.service.NpciUpiRestConService;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.Util;

@Component
public class ReqListKeysPspAllScheduler {
	private static final Logger log = LoggerFactory.getLogger(ReqListKeysPspAllScheduler.class);
	@Autowired
	private NpciUpiRestConService npciUpiRestConService;

	@Autowired
	private ReqRespVaePspKeysDao reqRespVaePspKeysDao;

	@Value("${IS_PSPKEYS_ENABLE}")
	private boolean isPspkeysEnable;

	@Scheduled(initialDelayString = "${LIST_PSPKEYS_INITIALDELAY}", fixedDelayString = "${LIST_PSPKEYS_FIXEDDELAY}")
	public void reqListKeysPspAllScheduler() {
		log.info("start reqListKeysPspAll Scheduler ");
		log.info("IS_PSPKEYS_ENABLE={}", isPspkeysEnable);
		if (isPspkeysEnable) {
			reqListKeysPspAll();
		}
		log.info("end reqListKeysPspAll Scheduler");
	}

	void reqListKeysPspAll() {
		try {
			String ts = Util.getTS();
			String txnId = Util.uuidGen();
			String msgId = Util.uuidGen();
			ReqListKeys reqListKeys = new ReqListKeys();
			HeadType head = new HeadType();
			PayTrans txn = new PayTrans();
			head.setMsgId(msgId);
			head.setOrgId(Constant.orgId);
			head.setTs(ts);
			head.setVer(Constant.headVer);
			reqListKeys.setHead(head);
			txn.setId(txnId);
			txn.setNote("REQ LIST ALL PSP KEYS");
			txn.setRefId(txnId);
			txn.setRefUrl(Constant.ReqTXNREFURL);
			txn.setTs(ts);
		
			txn.setType(PayConstant.LIST_PSP_KEYS);
			
			reqListKeys.setTxn(txn);
			reqRespVaePspKeysDao.insertReq(reqListKeys);
			String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(reqListKeys).toString());
			Ack ack = npciUpiRestConService.send(xmlStr,ConstantI.ReqListKeys, txnId);
		} catch (Exception e) {
			log.error("error :{}", e);
			e.printStackTrace();
			ErrorLog.sendError(e,ReqListKeysPspAllScheduler.class);
		}
	}
}
