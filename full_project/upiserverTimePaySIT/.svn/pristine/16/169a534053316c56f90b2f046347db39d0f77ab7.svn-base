package com.npst.upiserver.acquirer.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.MobListAccPvdService;
import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.MobReqRespJsonIdDao;
import com.npst.upiserver.dao.MobUpiReqRespJsonIdDao;
import com.npst.upiserver.entity.MobUpiReqRespJson;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.HeadType;
import com.npst.upiserver.npcischema.PayConstant;
import com.npst.upiserver.npcischema.PayTrans;
import com.npst.upiserver.npcischema.ReqListAccPvd;
import com.npst.upiserver.service.NpciUpiRestConService;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.Util;

@Service
public class MobListAccPvdServiceImpl implements MobListAccPvdService {
	private static final Logger	log	= LoggerFactory.getLogger(MobListAccPvdServiceImpl.class);
	
	@Autowired
	MobReqRespJsonIdDao			mobReqRespJsonIdDao;
	@Autowired
	NpciUpiRestConService		npciService;
	@Autowired
	MobUpiReqRespJsonIdDao		mobUpiReqRespJsonIdDao;
	
	@Override
	public void procAndSendNpci(MobUpiReqRespJson mobUpiReqRespJson) {
		try {
			String ts = Util.getTS();
			String txnId = Util.uuidGen();
			String msgId = Util.uuidGen();
			mobReqRespJsonIdDao.updateMsgId(msgId, mobUpiReqRespJson.getIdPk().longValue());
			ReqListAccPvd reqListAccPvd = new ReqListAccPvd();
			reqListAccPvd.setHead(setHeadTypeDetails(msgId, ts));
			PayTrans txn = new PayTrans();
			txn.setId(txnId);
			txn.setNote(Constant.REQLISTACCPVDNOTE);
			txn.setRefId(Constant.REQLISTACCPVDTXNREFID);
			txn.setRefUrl(Constant.REQLISTACCPVDTXNREFURL);
			txn.setTs(ts);
			txn.setType(PayConstant.LIST_ACC_PVD);
			reqListAccPvd.setTxn(txn);
			String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(reqListAccPvd).toString());
			Ack ack = npciService.send(xmlStr, ConstantI.API_REQ_LIST_ACC_PVD, txnId);
			if (null != ack.getErr() || 0 != ack.getErrorMessages().size()) {
				try {
					mobUpiReqRespJsonIdDao.updateDb(ack, mobUpiReqRespJson,
							reqListAccPvd.getTxn().getType().toString());
				} catch (Exception e) {
					log.error(e.getMessage(), e);
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
			log.error(e.getMessage(),e);
		}
		return head;
	}
}
