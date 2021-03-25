package com.npst.upiserver.acquirer.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.MobListKeysService;
import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.MobReqRespJsonIdDao;
import com.npst.upiserver.dao.MobUpiReqRespJsonIdDao;
import com.npst.upiserver.dao.RegistrationDao;
import com.npst.upiserver.dao.ReqRespListKeysDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.MobUpiReqRespJson;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.CredSubType;
import com.npst.upiserver.npcischema.CredType;
import com.npst.upiserver.npcischema.CredsType;
import com.npst.upiserver.npcischema.CredsType.Cred;
import com.npst.upiserver.npcischema.CredsType.Cred.Data;
import com.npst.upiserver.service.NpciUpiRestConService;
import com.npst.upiserver.npcischema.HeadType;
import com.npst.upiserver.npcischema.PayConstant;
import com.npst.upiserver.npcischema.PayTrans;
import com.npst.upiserver.npcischema.ReqListKeys;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.JsonMan;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.Util;

@Service
public class MobListKeysServiceImpl implements MobListKeysService {
	private static final Logger	log	= LoggerFactory.getLogger(MobListKeysServiceImpl.class);
	
	@Autowired
	MobReqRespJsonIdDao			mobReqRespJsonIdDao;
	@Autowired
	NpciUpiRestConService		npciService;
	@Autowired
	MobUpiReqRespJsonIdDao		mobUpiReqRespJsonIdDao;
	@Autowired
	RegistrationDao				registrationDao;
	@Autowired
	ReqRespListKeysDao			reqRespListKeysDao;
	
	@Override
	public void procAndSendNpci(MobUpiReqRespJson mobUpiReqRespJson) {
		try {
			ReqResp reqJson = JsonMan.getReqResp(mobUpiReqRespJson.getReq());
			String ts = Util.getTS();
			String txnId = Util.uuidGen();
			String msgId = Util.uuidGen();
			log.info("msgid {}, idpk {} ",msgId,mobUpiReqRespJson.getIdPk().longValue());
			mobReqRespJsonIdDao.updateMsgId(msgId, mobUpiReqRespJson.getIdPk().longValue());
			ReqListKeys reqListKeys = new ReqListKeys();
			reqListKeys.setHead(setHeadTypeDetaisl(msgId,ts));
			PayTrans txn=setPayTransDetails(txnId, reqJson.getTxnNote(), reqJson.getTxnRefId(), reqJson.getTxnRefUrl(), ts);
			if (ConstantI.GET_TOKEN.equalsIgnoreCase(reqJson.getTxnType())) {
				txn.setType(PayConstant.GET_TOKEN);
				CredsType creds = new CredsType();
				List<Cred> credList = creds.getCred();
				credList.add(setCredDetails(reqJson.getPayerDeviceId(),reqJson.getPayerDeviceAppId(),reqJson.getPayerDeviceMobile(),reqJson.getTokenChallenge(),reqJson.getTokenType()));
				reqListKeys.setCreds(creds);
			} else if (ConstantI.TRANS_TYPE_LIST_KEYS.equalsIgnoreCase(reqJson.getTxnType())) {
				//txn.setPspOrgId("400005");
				txn.setType(PayConstant.LIST_KEYS);
			}
			reqListKeys.setTxn(txn);
			String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(reqListKeys).toString());
			log.info("xmlStr is as {} ",xmlStr);
			Ack ack = npciService.send(xmlStr, ConstantI.REQ_LIST_KEYS, txnId);
			if (null != ack.getErr() || 0 != ack.getErrorMessages().size()) {
				try {
					mobUpiReqRespJsonIdDao.updateDb(ack, mobUpiReqRespJson, reqListKeys.getTxn().getType().toString());
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				try {
					registrationDao.delete(reqJson);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
			try {
				reqRespListKeysDao.insertReq(reqListKeys, ack);
				log.info("Req Inserted ");
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	private HeadType setHeadTypeDetaisl(final String msgId,final String ts)  throws Exception{
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
	private PayTrans setPayTransDetails(final String txnId,final String txnNote,final String txnRefId,final String txnRefUrl,final String ts)  throws Exception{
		PayTrans txn = new PayTrans();
		try {
			txn.setId(txnId);
			txn.setNote(txnNote);
			txn.setRefId(txnRefId);
			txn.setRefUrl( txnRefUrl);
			txn.setTs(ts);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return txn;
	}
	
	private Cred setCredDetails(final String payerDeviceId,final String payerDeviceAppId,final String payerDeviceMob,final String payerTokenChallenge,final String tokenType) throws Exception {
		Cred cred = new Cred();
		try {
			Data data = new Data();
			data.setCode(ConstantI.NPCI);
			data.setValue(payerDeviceId + ConstantI.CONST_SPCL_PIPE + payerDeviceAppId + ConstantI.CONST_SPCL_PIPE
					+ payerDeviceMob+ ConstantI.CONST_SPCL_PIPE + payerTokenChallenge);
			data.setKi("20170303"); 
			cred.setData(data);
			cred.setSubType(CredSubType.fromValue(tokenType));
			cred.setType(CredType.CHALLENGE);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return cred;
	}
}
