package com.npst.upiserver.acquirer.service.impl;

import java.io.StringWriter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.MobListAccountService;
import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.CustomerTxnsDao;
import com.npst.upiserver.dao.MobReqRespJsonIdDao;
import com.npst.upiserver.dao.MobUpiReqRespJsonIdDao;
import com.npst.upiserver.dao.RegistrationDao;
import com.npst.upiserver.dao.ReqRespListAccDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.MobUpiReqRespJson;
import com.npst.upiserver.entity.Registration;
import com.npst.upiserver.npcischema.AadhaarConsentConstant;
import com.npst.upiserver.npcischema.AccountDetailType;
import com.npst.upiserver.npcischema.AccountType;
import com.npst.upiserver.npcischema.AccountType.Detail;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.AddressType;
import com.npst.upiserver.npcischema.DeviceTagNameType;
import com.npst.upiserver.npcischema.DeviceType;
import com.npst.upiserver.npcischema.DeviceType.Tag;
import com.npst.upiserver.npcischema.HeadType;
import com.npst.upiserver.npcischema.LinkType;
import com.npst.upiserver.npcischema.PayConstant;
import com.npst.upiserver.npcischema.PayTrans;
import com.npst.upiserver.npcischema.PayerConstant;
import com.npst.upiserver.npcischema.PayerType;
import com.npst.upiserver.npcischema.ReqListAccount;
import com.npst.upiserver.npcischema.ReqListAccount.Link;
import com.npst.upiserver.npcischema.RespBalEnq.Payer;
import com.npst.upiserver.service.NpciUpiRestConService;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.JsonMan;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.Util;

@Service
public class MobListAccountServiceImpl implements MobListAccountService {
	
	private static final Logger log = LoggerFactory.getLogger(MobListAccountServiceImpl.class);
	@Autowired
	RegistrationDao regDao;
	@Autowired
	ReqRespListAccDao reqRespListAccDao;
	@Autowired
	MobReqRespJsonIdDao mobReqRespJsonIdDao;
	@Autowired
	NpciUpiRestConService npciService;
	@Autowired
	MobUpiReqRespJsonIdDao mobUpiReqRespJsonIdDao;
	@Autowired
	CustomerTxnsDao custTxnDao;
	
	@Override
	public void procAndSendNpci(MobUpiReqRespJson mobUpiReqRespJson) {
		try {
			ReqResp reqJson = JsonMan.getReqResp(mobUpiReqRespJson.getReq());
			final String linktype = reqJson.getLinkType();
			final String linkvalue = reqJson.getLinkValue();
			String mobno = null;
			String aadhar = null;
			if (ConstantI.MOBILE.equalsIgnoreCase(linktype)) {
				mobno = linkvalue;
			} else if (ConstantI.AADHAAR.equalsIgnoreCase(linktype)) {
				aadhar = linkvalue;
			}
			String linkValue = LinkType.MOBILE + "=" + reqJson.getLinkValue();
			log.info("Going to validate txn for " + linkValue);
			final Registration registration = regDao.findActiveRegByMobno(mobno);
		// To do 
			// boolean isValidReq = reqRespListAccDao.checkRiskListAccReq(registration.getRegid().longValue(),registration.getLastlogindt());
			boolean isValidReq=true;
			if (isValidReq) {
				String ts = Util.getTS();
				String txnId = Util.uuidGen();
				String msgId = Util.uuidGen();
				try {
					mobReqRespJsonIdDao.updateMsgId(msgId, mobUpiReqRespJson.getIdPk().longValue());
				}
				catch (Exception e) {
					log.error(e.getMessage(),e);
				}
				ReqListAccount reqListAccount = new ReqListAccount();
				reqListAccount.setHead(setHeadTypeDetails(msgId, ts));
				reqListAccount.setTxn(setPayTransDetails(txnId, reqJson.getTxnNote(), reqJson.getTxnRefId(), reqJson.getTxnRefUrl(), ts, reqJson.getTxnType()));
				reqListAccount.setLink(setLinkDetails(reqJson.getLinkType(), reqJson.getLinkValue()));
				AccountType ac = new AccountType();
				
				List<Detail> details = ac.getDetail();
				details.add(setDetailDetails(reqJson.getPayerIfsc()));
				ac.setAddrType(AddressType.ACCOUNT);
				reqListAccount.setPayer(setPayerTypeDetails(ac,reqJson.getPayerAadhaarConsent(), reqJson.getPayerAddr(), reqJson.getPayerCode(), reqJson.getPayerName(), reqJson.getPayerSeqNum(), reqJson.getPayerType(),reqJson));
				StringWriter sw = MarshalUpi.marshal(reqListAccount);
				String xmlStr = DigitalSignUtil.signXML(sw.toString());
				log.info("listaccount xml for psp to upi : " + xmlStr);
				Ack ack = npciService.send(xmlStr, ConstantI.API_REQ_LIST_ACC, txnId);
				if (null != ack.getErr() || 0 != ack.getErrorMessages().size()) {
					try {
						log.info("Ack found");
						mobUpiReqRespJsonIdDao.updateDb(ack, mobUpiReqRespJson, reqListAccount.getTxn().getType().toString());
						log.info("After Update");
					} catch (Exception e) {
						log.error(e.getMessage(),e);
					}
				}
				try {
					reqRespListAccDao.insertReq(reqListAccount, ack);
				} catch (Exception e) {
					log.error(e.getMessage(),e);
				}
				try {
					custTxnDao.insert(reqListAccount, ack);

				} catch (Exception e) {
					log.error(e.getMessage(),e);
				}
			}
			else {
				final ReqResp respJson = new ReqResp();
				respJson.setTxnType(reqJson.getTxnType());
				respJson.setMsgId(Constant.CONST_FAILURE);
				respJson.setMsg(Constant.ERROR_CODE_XHH);
				mobUpiReqRespJsonIdDao.updateDb(mobUpiReqRespJson, respJson);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	private HeadType setHeadTypeDetails(final String msgId,final String ts) throws Exception{
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
	PayTrans setPayTransDetails(final String txnId,final String txnNote,final String txnRefId,final String txnRefUrl,final String ts,final String txnType) throws Exception {
		PayTrans txn = new PayTrans();
		try {
			txn.setId(txnId);
			txn.setNote(txnNote);
			txn.setRefId(txnRefId);
			txn.setRefUrl(txnRefUrl);
			txn.setTs(ts);
			txn.setType(PayConstant.fromValue(txnType));
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return txn;
	}
	private Link setLinkDetails(final String linkType,final String linkValue)  throws Exception{
		Link link = new Link();
		try {
			if (ConstantI.MOBILE.equalsIgnoreCase(linkType)) {
				link.setType(LinkType.MOBILE);
				link.setValue(linkValue);
			} else if (ConstantI.AADHAAR.equalsIgnoreCase(linkType)) {
				link.setType(LinkType.AADHAAR);
				link.setValue(linkValue);
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return link;
	}
	
	private PayerType setPayerTypeDetails(final AccountType ac,final String aadhaarConsent,final String payerAddr,final String payerCode,final String payerName,final String payerSeqNum,final String payerType,final ReqResp reqResp) throws Exception {
		PayerType payer = new PayerType();
		try {
			payer.setAc(ac);
			payer.setAddr(payerAddr);
			if (aadhaarConsent != null
					&& aadhaarConsent.equalsIgnoreCase(AadhaarConsentConstant.Y.toString())) {
				payer.setAadhaarConsent(AadhaarConsentConstant.Y);
			} else {
				payer.setAadhaarConsent(AadhaarConsentConstant.N);
			}
			payer.setCode(payerCode);
			payer.setName(payerName);
			payer.setSeqNum(payerSeqNum);
			payer.setType(PayerConstant.fromValue(payerType));
			payer.setDevice(Util.getPayerDeviceTypeDefault(reqResp));
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return payer;
	}
	
	private Detail setDetailDetails(final String payerIfsc) throws Exception {
		Detail detail = new Detail();
		try {
			detail.setName(AccountDetailType.IFSC);
			detail.setValue(payerIfsc.toUpperCase() + Constant.REQLISTIFSC);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return detail;
	}
	
}