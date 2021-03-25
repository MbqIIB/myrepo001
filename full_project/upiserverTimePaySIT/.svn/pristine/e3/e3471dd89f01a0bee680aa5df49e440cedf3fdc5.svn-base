package com.npst.upiserver.scheduler;

import org.apache.commons.lang3.StringUtils;
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
import com.npst.upiserver.npcischema.PayConstant;
import com.npst.upiserver.npcischema.PayTrans;
import com.npst.upiserver.npcischema.ReqListPsp;
import com.npst.upiserver.npcischema.ReqListVae;
import com.npst.upiserver.service.NpciUpiRestConService;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.NpciSchemaUtil;

@Component
public class ListPspAndVaeScheduler {
	private static final Logger log = LoggerFactory.getLogger(ListAccountPvdScheduler.class);

	@Autowired
	private NpciUpiRestConService npciUpiRestConService;

	@Autowired
	private ReqRespVaePspKeysDao reqRespVaePspKeysDao;
	
	@Value("${IS_LISTPSP_ENABLE}")
	private boolean isListPspEnable;
	@Value("${IS_LISTVAE_ENABLE}")
	private boolean isListVaeEnable;

	@Scheduled(initialDelayString = "${LIST_PSPVAE_INITIALDELAY}", fixedDelayString = "${LIST_PSPVAE_FIXEDDELAY}")
	public void reqHbtScheduler() {
		log.info("start ListPspAndVae Scheduler ");
		log.info("IS_LISTPSP_ENABLE={}", isListPspEnable);
		log.info("IS_LISTVAE_ENABLE={}", isListVaeEnable);
		if (isListPspEnable) {
			reqListPsp();
		}
		if (isListVaeEnable) {
			reqListVae();
		}
		log.info("end ListPspAndVae Scheduler");
	}

	void reqListPsp() {
		try {
			log.info("ReqListPsp calling ");
			ReqListPsp reqListPsp = new ReqListPsp();
			PayTrans txn = new PayTrans();
			reqListPsp.setHead(NpciSchemaUtil.getNewHeadType());
			txn.setId(reqListPsp.getHead().getMsgId());
			txn.setNote(ConstantI.LISTPSP_TXN_NOTE);
			txn.setRefId(reqListPsp.getHead().getMsgId());
			txn.setRefUrl(Constant.ReqTXNREFURL);
			txn.setTs(reqListPsp.getHead().getTs());
			txn.setType(PayConstant.LIST_PSP);
			reqListPsp.setTxn(txn);
			reqRespVaePspKeysDao.insertReq(reqListPsp);
			String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(reqListPsp).toString());
			if (StringUtils.isNotBlank(xmlStr)) {
				Ack ack = npciUpiRestConService.send(xmlStr, ConstantI.ReqListPsp, reqListPsp.getTxn().getId());
			}
		} catch (Exception e) {
			log.error("error :{}", e);
			ErrorLog.sendError(e, ListPspAndVaeScheduler.class);
		}
	}

	private void reqListVae() {
		try {
			log.info("ReqListVae calling ");
			ReqListVae reqListVae = new ReqListVae();
			PayTrans txn = new PayTrans();
			reqListVae.setHead(NpciSchemaUtil.getNewHeadType());
			txn.setId(reqListVae.getHead().getMsgId());
			txn.setNote(ConstantI.LISTVAE_TXN_NOTE);
			txn.setRefId(reqListVae.getHead().getMsgId());
			txn.setRefUrl(Constant.ReqTXNREFURL);
			txn.setTs(reqListVae.getHead().getTs());
			txn.setType(PayConstant.LIST_VAE);
			reqListVae.setTxn(txn);
			reqRespVaePspKeysDao.insertReq(reqListVae);
			String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(reqListVae).toString());
			log.info("Request XML for VAE is ",xmlStr);
			if (StringUtils.isNotBlank(xmlStr)) {
				Ack ack = npciUpiRestConService.send(xmlStr, ConstantI.ReqListVae, reqListVae.getTxn().getId());
			}
		} catch (Exception e) {
			log.error("error :{}", e);
			ErrorLog.sendError(e, ListPspAndVaeScheduler.class);
		}

	}
}
