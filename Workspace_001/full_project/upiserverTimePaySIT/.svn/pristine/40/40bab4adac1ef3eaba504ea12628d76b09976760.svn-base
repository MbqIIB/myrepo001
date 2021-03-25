package com.npst.upiserver.acquirer.service.impl;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.npst.upiserver.acquirer.service.MobBalEnqService;
import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.CustomerTxnsDao;
import com.npst.upiserver.dao.MobReqRespJsonIdDao;
import com.npst.upiserver.dao.MobUpiReqRespJsonIdDao;
import com.npst.upiserver.dao.ReqRespBalEnqDao;
import com.npst.upiserver.dto.CredJson;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.MobUpiReqRespJson;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.CredSubType;
import com.npst.upiserver.npcischema.CredType;
import com.npst.upiserver.npcischema.CredsType;
import com.npst.upiserver.npcischema.CredsType.Cred;
import com.npst.upiserver.npcischema.IdentityConstant;
import com.npst.upiserver.npcischema.IdentityType;
import com.npst.upiserver.npcischema.InfoType;
import com.npst.upiserver.npcischema.PayConstant;
import com.npst.upiserver.npcischema.PayTrans;
import com.npst.upiserver.npcischema.PayerConstant;
import com.npst.upiserver.npcischema.PayerType;
import com.npst.upiserver.npcischema.RatingType;
import com.npst.upiserver.npcischema.ReqBalEnq;
import com.npst.upiserver.npcischema.WhiteListedConstant;
import com.npst.upiserver.service.NpciUpiRestConService;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.JsonMan;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.NpciSchemaUtil;
import com.npst.upiserver.util.Util;

@Service
public class MobBalEnqServiceImpl implements MobBalEnqService {
	private static final Logger	log	= LoggerFactory.getLogger(MobBalEnqServiceImpl.class);
	
	@Autowired
	MobReqRespJsonIdDao			mobReqRespJsonIdDao;
	
	@Autowired
	MobUpiReqRespJsonIdDao		mobUpiReqRespJsonIdDao;
	
	@Autowired
	NpciUpiRestConService		npciService;
	
	@Autowired
	ReqRespBalEnqDao			reqRespBalEnqDao;
	
	@Autowired
	CustomerTxnsDao				custTxnDao;
	
	
	@Override
	public void procAndSendNpci(MobUpiReqRespJson mobUpiReqRespJson) {
		try {
			ReqResp reqJson = JsonMan.getReqResp(mobUpiReqRespJson.getReq());
			String ts = Util.getTS();
			String txnId = reqJson.getTxnId();
			String msgId = Util.uuidGen();
			mobReqRespJsonIdDao.updateMsgId(msgId, mobUpiReqRespJson.getIdPk().longValue());
			ReqBalEnq reqBalEnq = new ReqBalEnq();
			reqBalEnq.setHead(NpciSchemaUtil.getNewHeadType(msgId, ts));
			PayTrans txn = new PayTrans();
			txn.setId(txnId);
			txn.setNote(reqJson.getTxnNote());
			txn.setRefId(reqJson.getTxnRefId());
			txn.setRefUrl(Constant.ReqTXNREFURL);
			txn.setTs(ts);
			txn.setType(PayConstant.fromValue(reqJson.getTxnType()));
			PayerType payer = new PayerType();
			payer.setAddr(reqJson.getPayerAddr());
			payer.setName(reqJson.getPayerName());
			payer.setSeqNum(reqJson.getPayerSeqNum());
			payer.setType(PayerConstant.fromValue(reqJson.getPayerType()));
			payer.setCode(reqJson.getPayerCode());
			InfoType info = new InfoType();
			IdentityType identity = new IdentityType();
			RatingType rating = new RatingType();
			rating.setVerifiedAddress(WhiteListedConstant.TRUE);
			info.setRating(rating);
			identity.setId(reqJson.getPayerAcNum());
			identity.setType(IdentityConstant.fromValue(reqJson.getPayerAddrType()));
			identity.setVerifiedName(reqJson.getPayerName());
			info.setIdentity(identity);
			payer.setInfo(info);
			reqBalEnq.setPayer(payer);
			payer.setDevice(NpciSchemaUtil.getPayerDeviceType(reqJson));
			payer.setAc(NpciSchemaUtil.getPayerAccountType(reqJson));
			CredsType creds = new CredsType();
			List<CredsType.Cred> creadsList = creds.getCred();
			List<CredJson> list = reqJson.getCredJsons();
			for (CredJson object : list) {
				Cred cred = new Cred();
				CredsType.Cred.Data data = new CredsType.Cred.Data();
				data.setCode(object.getData().getCode());
				data.setValue(object.getData().getEncryptedBase64String());
				data.setKi(object.getData().getKi());
				cred.setData(data);
				cred.setSubType(CredSubType.fromValue(object.getSubType()));
				cred.setType(CredType.fromValue(object.getType()));
				creadsList.add(cred);
			}
			payer.setCreds(creds);
			reqBalEnq.setTxn(txn);
			//payer.setAmount(setPayerAmount()); // To do 
			reqBalEnq.setPayer(payer);
			Ack ack = null;
			String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(reqBalEnq).toString());
			if (StringUtils.isNotBlank(xmlStr)) {
				ack = npciService.send(xmlStr, ConstantI.REQ_BALENQ, txnId);
				if (ack != null && (null != ack.getErr() || 0 != ack.getErrorMessages().size())) {
					mobUpiReqRespJsonIdDao.updateDb(ack, mobUpiReqRespJson, reqBalEnq.getTxn().getType().toString());
				}
			}
				reqRespBalEnqDao.insertReq(reqBalEnq, ack);
				custTxnDao.insert(reqBalEnq, ack);
		} 
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
}
