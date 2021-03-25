package com.npst.upiserver.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.ReqRespListAccPvdDao;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.HeadType;
import com.npst.upiserver.npcischema.PayConstant;
import com.npst.upiserver.npcischema.PayTrans;
import com.npst.upiserver.npcischema.ReqListAccPvd;
import com.npst.upiserver.service.NpciUpiRestConService;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.Util;

@Component
public class ListAccountPvdScheduler {

	private static final Logger log = LoggerFactory.getLogger(ListAccountPvdScheduler.class);

	@Autowired
	private NpciUpiRestConService npciUpiRestConService;

	@Autowired
	private ReqRespListAccPvdDao reqRespListAccPvdDao;

	@Scheduled(initialDelayString = "${LISTACC_PVD_INITIALDELAY}", fixedDelayString = "${LIST_ACCPVD_FIXEDDELAY}")
	public void reqHbtScheduler() {
		log.info("start ListAccountPvd Scheduler ");
		reqAccountProviders();
		log.info("end ListAccountPvd Scheduler");
	}

	void reqAccountProviders() {
		try {
			String ts = Util.getTS();
			String txnId = Util.uuidGen();
			String msgId = Util.uuidGen();
			ReqListAccPvd reqListAccPvd = new ReqListAccPvd();
			HeadType head = new HeadType();
			PayTrans txn = new PayTrans();
			head.setMsgId(msgId);
			head.setOrgId(Constant.orgId);
			head.setTs(ts);
			head.setVer(Constant.headVer);
			reqListAccPvd.setHead(head);
			txn.setId(txnId);
			txn.setNote(Constant.REQLISTACCPVDNOTE);
			txn.setRefId(txnId);
			txn.setRefUrl(Constant.ReqTXNREFURL);
			txn.setTs(ts);
			txn.setType(PayConstant.LIST_ACC_PVD);
			reqListAccPvd.setTxn(txn);
			reqRespListAccPvdDao.insert(reqListAccPvd);
			String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(reqListAccPvd).toString());
			Ack ack = npciUpiRestConService.send(xmlStr, ConstantI.ReqListAccPvd, txnId);
			log.info("ack {}", ack);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error :{}", e);
			ErrorLog.sendError(e, ListAccountPvdScheduler.class);
		}
	}
}
