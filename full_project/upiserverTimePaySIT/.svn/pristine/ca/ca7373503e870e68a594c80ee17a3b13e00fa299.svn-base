package com.npst.upiserver.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.HeadType;
import com.npst.upiserver.npcischema.PayConstant;
import com.npst.upiserver.npcischema.PayTrans;
import com.npst.upiserver.npcischema.ReqChkTxn;
import com.npst.upiserver.npcischema.TxnSubType;
import com.npst.upiserver.service.NpciUpiRestConService;
import com.npst.upiserver.service.impl.NpciUpiRestConServiceImpl;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.Util;
@Component
public class ReqChkTxnProcess {
	private static final Logger log = LoggerFactory.getLogger(ReqChkTxnProcess.class.getName());


	@Autowired
	private NpciUpiRestConService npciUpiRestConService;
	
	//@Scheduled(initialDelayString ="120000", fixedDelayString = "120000")
	public void  ReqChkTxn() {
		log.info("CHECK TXN STARTED");
		try {
			
		String msgId = Util.uuidGen();
		String ts = Util.getTS();
		String txnId = Util.uuidGen();
		ReqChkTxn reqChkTxn = new ReqChkTxn();
		HeadType head = new HeadType();
		head.setMsgId(msgId);
		head.setOrgId(Constant.orgId);
		head.setTs(ts);
		head.setVer(Constant.headVer);
		reqChkTxn.setHead(head);
		PayTrans txn = new PayTrans();
		txn.setId(txnId);
		txn.setNote("Check Txn");
		txn.setRefId(txnId);
		txn.setRefUrl("https://www.cosmosbank.in/");
		txn.setTs(ts);
		txn.setOrgMsgId(Util.getProperty("REQ_CHK_TXN_ORG_MSGID"));
		txn.setSubType(TxnSubType.MANDATE);
		txn.setOrgRrn(Util.getProperty("ORG_RRN"));
		txn.setOrgTxnId(Util.getProperty("ORG_TXN_CHK_ID"));
		txn.setOrgTxnDate(Util.getProperty("OrgTxnDate"));
		txn.setType(PayConstant.CHK_TXN);
		txn.setPurpose(Util.getProperty("PURPOSE"));
		txn.setUmn(Util.getProperty("ORG_UMN"));
		txn.setInitiationMode("00");
		reqChkTxn.setTxn(txn);
		String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(reqChkTxn).toString());
		log.info("MobReqChkTxn Sending request to NPCI :  {} ", xmlStr);
		
		System.out.println("Req"+reqChkTxn);
		Ack ack = npciUpiRestConService.send(xmlStr, ConstantI.API_REQ_CHK_TRANS, txnId);
		if (null != ack.getErr() || 0 != ack.getErrorMessages().size()) {
			log.debug("null != ack.getErr() || 0 != ack.getErrorMessages().size()");
			try {
				//mobupireqrespjsonidHome.updateDb(ack, mobreqrespjson, reqChkTxn.getTxn().getType().toString());
			} catch (Exception e) {
				e.printStackTrace();
				log.error("error {}", e);
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
		log.error("error {}", e);
	}
}

}
