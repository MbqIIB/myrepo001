package com.npst.upiserver.issuer.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.constant.InitiationMode;
import com.npst.upiserver.dao.CustomerTxnsDao;
import com.npst.upiserver.dao.MandateTxnsDao;
import com.npst.upiserver.dao.ReqRespPayCollectDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.issuer.service.SwitchMandateCollectReqService;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.AmountType;
import com.npst.upiserver.npcischema.HeadType;
import com.npst.upiserver.npcischema.IdentityConstant;
import com.npst.upiserver.npcischema.IdentityType;
import com.npst.upiserver.npcischema.InfoType;
import com.npst.upiserver.npcischema.InitiatedByType;
import com.npst.upiserver.npcischema.PayConstant;
import com.npst.upiserver.npcischema.PayTrans;
import com.npst.upiserver.npcischema.PayeeType;
import com.npst.upiserver.npcischema.PayeesType;
import com.npst.upiserver.npcischema.PayerConstant;
import com.npst.upiserver.npcischema.PayerType;
import com.npst.upiserver.npcischema.RatingType;
import com.npst.upiserver.npcischema.ReqPay;
import com.npst.upiserver.npcischema.WhiteListedConstant;
import com.npst.upiserver.service.IdGeneratorService;
import com.npst.upiserver.service.NpciUpiRestConService;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.NpciSchemaUtil;
import com.npst.upiserver.util.Util;

@Service
public class SwitchMandateCollectReqServiceImpl implements SwitchMandateCollectReqService {
	private static final Logger log = LoggerFactory.getLogger(SwitchMandateCollectReqServiceImpl.class);

	@Autowired
	private NpciUpiRestConService npciUpiRestConService;
	/*@Autowired
	private IdGeneratorService idGeneratorService;*/
	@Autowired
	private ReqRespPayCollectDao reqRespPayCollectDao;
	@Autowired
	private CustomerTxnsDao customerTxnsDao;
	@Autowired
	private MandateTxnsDao mandateTxnsDao;
	
	@Autowired
	private IdGeneratorService idGeneratorService;

	@Override
	public void sendToNpci(final ReqResp reqJson) {
		try {
			String ts = Util.getTS();
			String rrn = idGeneratorService.getRrn();
			String txnId = reqJson.getTxnId();
			String msgId = Util.uuidGen();
			ReqPay reqpay = new ReqPay();
			HeadType head = new HeadType();
			head.setMsgId(msgId);
			head.setOrgId(Constant.orgId);
			head.setTs(ts);
			head.setVer(Constant.headVer);
			PayTrans txn = new PayTrans();
			txn.setId(txnId);
			txn.setNote(reqJson.getTxnNote());
			txn.setRefId(reqJson.getTxnRefId());
			txn.setRefUrl(reqJson.getTxnRefUrl());
			txn.setTs(ts);
			txn.setCustRef(rrn);
			txn.setInitiatedBy(InitiatedByType.PAYEE);
			txn.setInitiationMode(InitiationMode.MANDATE.getMode());
			txn.setPurpose("11");
			txn.setType(PayConstant.fromValue(reqJson.getTxnType()));
			AmountType amount = new AmountType();
			amount.setValue(reqJson.getPayerAmount());
			amount.setCurr(ConstantI.INR);
			// FLAG REQPAYER PAY
			Ack ack = null;
			PayeesType payees = new PayeesType();
			List<PayeeType> payeeList = payees.getPayee();
			PayeeType payee = new PayeeType();
			payee.setAddr(reqJson.getPayeeAddr());
			payee.setName(reqJson.getPayeeName());
			payee.setSeqNum(reqJson.getPayeeSeqNum());
			payee.setCode(reqJson.getPayeeCode());
			payee.setType(PayerConstant.fromValue(reqJson.getPayeeType()));
			InfoType info = new InfoType();
			IdentityType identity = new IdentityType();
			RatingType rating = new RatingType();
			rating.setVerifiedAddress(WhiteListedConstant.TRUE);
			info.setRating(rating);
			if (ConstantI.ACCOUNT.equalsIgnoreCase(reqJson.getPayeeAddrType())) {
				identity.setId(reqJson.getPayeeAcNum());
				identity.setType(IdentityConstant.ACCOUNT);
			} else if (ConstantI.MOBILE.equalsIgnoreCase(reqJson.getPayeeAddrType())) {
				identity.setId(reqJson.getPayeeMobileNo());
				identity.setType(IdentityConstant.ACCOUNT);
			} else if (ConstantI.AADHAAR.equalsIgnoreCase(reqJson.getPayeeAddrType())) {
				identity.setId(reqJson.getPayeeUidNum());
				identity.setType(IdentityConstant.AADHAAR);
			}
			identity.setVerifiedName(reqJson.getPayeeName());
			info.setIdentity(identity);
			payee.setInfo(info);
			payee.setDevice(NpciSchemaUtil.getPayeeDeviceType(reqJson));
			payee.setAc(NpciSchemaUtil.getPayeeAccountType(reqJson));
			payee.setAmount(amount);
			payeeList.add(payee);
			PayerType payer = new PayerType();
			payer.setAddr(reqJson.getPayerAddr());
			payer.setName(reqJson.getPayerName());
			payer.setSeqNum(reqJson.getPayerSeqNum());
			payer.setType(PayerConstant.fromValue(reqJson.getPayerType()));
			payer.setCode(reqJson.getPayerCode());
			payer.setAmount(amount);
			reqpay.setHead(head);
			reqpay.setPayer(payer);
			reqpay.setTxn(txn);
			reqpay.setPayees(payees);
			String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(reqpay).toString());
			if (StringUtils.isNotBlank(xmlStr)) {
				ack = npciUpiRestConService.send(xmlStr, ConstantI.API_REQ_PAY, txnId);
			}
			reqRespPayCollectDao.insertReq(reqpay, ack);
			customerTxnsDao.insert(reqpay, ack);
			mandateTxnsDao.insertReqpayAndAck(reqpay, ack);
		} catch (Exception e) {
			log.error("Error: {}", e);
			ErrorLog.sendError(e, SwitchMandateCollectReqServiceImpl.class);
		}
	}
}
