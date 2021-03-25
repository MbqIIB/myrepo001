package com.npst.upiserver.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.ReqRespVaePspKeysDao;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.HeadType;
import com.npst.upiserver.npcischema.PayConstant;
import com.npst.upiserver.npcischema.PayTrans;
import com.npst.upiserver.npcischema.ReqListPsp;
import com.npst.upiserver.service.NpciUpiRestConService;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.Util;

@Component
public class ListPspScheduler {
	private static final Logger log = LoggerFactory.getLogger(ListAccountPvdScheduler.class);

	@Autowired
	private NpciUpiRestConService npciUpiRestConService;

	@Autowired
	private ReqRespVaePspKeysDao reqRespVaePspKeysDao;

	@Scheduled(initialDelayString = "${LIST_PSP_INITIALDELAY}", fixedDelayString = "${LIST_PSP_FIXEDDELAY}")
	public void reqHbtScheduler() {
		log.info("start ListAccountPvd Scheduler ");
		reqListPsp();
		log.info("end ListAccountPvd Scheduler");
	}

	void reqListPsp() {
		try {
			log.trace("");
			String ts = Util.getTS();
			String txnId = Util.uuidGen();
			String msgId = Util.uuidGen();
			ReqListPsp reqListPsp = new ReqListPsp();
			HeadType head = new HeadType();
			PayTrans txn = new PayTrans();
			head.setMsgId(msgId);
			head.setOrgId(Constant.orgId);
			head.setTs(ts);
			head.setVer(Constant.headVer);
			reqListPsp.setHead(head);
			txn.setId(txnId);
			txn.setNote(ConstantI.LISTPSP_TXN_NOTE);
			txn.setRefId(txnId);
			txn.setRefUrl(Constant.ReqTXNREFURL);
			txn.setTs(ts);
			txn.setType(PayConstant.LIST_PSP);
			reqListPsp.setTxn(txn);
			reqRespVaePspKeysDao.insertReq(reqListPsp);
			String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(reqListPsp).toString());
			Ack ack = npciUpiRestConService.send(xmlStr, ConstantI.ReqListPsp, reqListPsp.getTxn().getId());

		} catch (Exception e) {
			e.printStackTrace();
			log.error("error :{}", e);
			ErrorLog.sendError(e,ListPspScheduler.class);
		}
	}
}
