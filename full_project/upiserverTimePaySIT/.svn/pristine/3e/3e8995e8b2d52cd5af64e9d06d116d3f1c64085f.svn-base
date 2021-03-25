package com.npst.upiserver.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.ReqRespHbtDao;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.HbtMsgType;
import com.npst.upiserver.npcischema.HeadType;
import com.npst.upiserver.npcischema.PayConstant;
import com.npst.upiserver.npcischema.PayTrans;
import com.npst.upiserver.npcischema.ReqHbt;
import com.npst.upiserver.npcischema.ReqHbt.HbtMsg;
import com.npst.upiserver.service.NpciUpiRestConService;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.Util;

@Component
public class ReqHbtScheduler {

	private static final Logger log = LoggerFactory.getLogger(ReqHbtScheduler.class);

	@Value("${HBTMSGTYPE}")
	private String HBTMSGTYPE;
	@Value("${HBTMSGVALUE}")
	private String HBTMSGVALUE;
	@Value("${TXNNOTE}")
	private String TXNNOTE;
	@Value("${TXNREFID}")
	private String TXNREFID;
	@Value("${TXNTYPE}")
	private String TXNTYPE;
	@Value("${TXNREFURL}")
	private String TXNREFURL;

	@Autowired
	private NpciUpiRestConService npciUpiRestConService;

	@Autowired
	private ReqRespHbtDao reqRespHbtDao;

	@Scheduled(initialDelayString = "${REQHBT_INITIALDELAY}", fixedDelayString = "${REQHBT_FIXEDDELAY}")
	public void reqHbtScheduler() {
		log.info("start reqHbt Scheduler ");
		reqHbtToNpci();
		log.info("end reqHbt Scheduler");
	}

	void reqHbtToNpci() {
		try {
			log.trace("");
			String ts = Util.getTS();
			String txnId = Util.uuidGen();
			String msgId = Util.uuidGen();
			ReqHbt reqHbt = new ReqHbt();
			HeadType head = new HeadType();
			PayTrans txn = new PayTrans();
			HbtMsg hbtMsg = new HbtMsg();
			head.setMsgId(msgId);
			head.setOrgId(Constant.orgId);
			head.setTs(ts);
			head.setVer(Constant.headVer);
			hbtMsg.setType(HbtMsgType.ALIVE);
			hbtMsg.setValue(HBTMSGVALUE);
			txn.setId(txnId);
			txn.setNote(TXNNOTE);
			txn.setRefId(txnId);
			txn.setType(PayConstant.HBT);
			txn.setRefUrl(TXNREFURL);
			txn.setTs(ts);
			reqHbt.setHbtMsg(hbtMsg);
			reqHbt.setHead(head);
			reqHbt.setTxn(txn);
			Ack ack = null;
			String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(reqHbt).toString());
			log.info("ReqHBT XML {}",xmlStr);
			if (xmlStr != null) {
				ack = npciUpiRestConService.send(xmlStr, ConstantI.ReqHbt, txnId);
			}
			reqRespHbtDao.insertReq(reqHbt, ack);
		} catch (Exception e) {
			log.error("ReqHbt Scheduler Error {}", e);
			e.printStackTrace();
			ErrorLog.sendError(e,ReqHbtScheduler.class);
		}
	}

}
