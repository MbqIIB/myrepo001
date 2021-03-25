package com.npst.upiserver.acquirer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.MobCollectRejectService;
import com.npst.upiserver.acquirer.service.MobSpamCollectReject;
import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.CustomerAccountDao;
import com.npst.upiserver.dao.CustomerTxnsDao;
import com.npst.upiserver.dao.MobReqRespJsonIdDao;
import com.npst.upiserver.dao.MobUpiReqRespJsonIdDao;
import com.npst.upiserver.dao.ReqRespAuthDetailsDao;
import com.npst.upiserver.dao.ReqRespAuthDetailsPayeesDao;
import com.npst.upiserver.dao.SpamVpaDao;
import com.npst.upiserver.dao.SpamVpaRuleDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.CustomerAccount;
import com.npst.upiserver.entity.MobUpiReqRespJson;
import com.npst.upiserver.entity.ReqRespAuthDetails;
import com.npst.upiserver.entity.ReqRespAuthDetailsPayees;
import com.npst.upiserver.entity.SpamVpa;
import com.npst.upiserver.npcischema.AccountDetailType;
import com.npst.upiserver.npcischema.AccountType;
import com.npst.upiserver.npcischema.AccountType.Detail;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.AddressType;
import com.npst.upiserver.npcischema.AmountType;
import com.npst.upiserver.npcischema.DeviceTagNameType;
import com.npst.upiserver.npcischema.DeviceType;
import com.npst.upiserver.npcischema.DeviceType.Tag;
import com.npst.upiserver.npcischema.HeadType;
import com.npst.upiserver.npcischema.IdentityConstant;
import com.npst.upiserver.npcischema.IdentityType;
import com.npst.upiserver.npcischema.InfoType;
import com.npst.upiserver.npcischema.PayConstant;
import com.npst.upiserver.npcischema.PayTrans;
import com.npst.upiserver.npcischema.PayeeType;
import com.npst.upiserver.npcischema.PayeesType;
import com.npst.upiserver.npcischema.PayerConstant;
import com.npst.upiserver.npcischema.PayerType;
import com.npst.upiserver.npcischema.RatingType;
import com.npst.upiserver.npcischema.RespAuthDetails;
import com.npst.upiserver.npcischema.RespType;
import com.npst.upiserver.npcischema.WhiteListedConstant;
import com.npst.upiserver.service.NpciUpiRestConService;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.JsonMan;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.Util;

@Service
public class MobCollectRejectServiceImpl implements MobCollectRejectService {
	private static final Logger	log	= LoggerFactory.getLogger(MobCollectRejectServiceImpl.class);
	
	@Autowired
	ReqRespAuthDetailsDao		reqRespAuthDetailsDao;
	@Autowired
	MobReqRespJsonIdDao			mobReqRespJsonIdDao;
	@Autowired
	ReqRespAuthDetailsPayeesDao	reqRespAuthDetDao;
	@Autowired
	NpciUpiRestConService		npciService;
	@Autowired
	MobUpiReqRespJsonIdDao		mobUpiReqRespJsonIdDao;
	@Autowired
	CustomerTxnsDao				custTxnDao;
	@Autowired
	CustomerAccountDao			custAccDao;
	@Autowired
	SpamVpaDao					spamVpaDao;
	@Autowired
	SpamVpaRuleDao				spamVpaRuleDao;
	@Autowired
	MobSpamCollectReject		mobSpamCollectReject;
	
	@Override
	public void procAndSendNpci(MobUpiReqRespJson mobUpiReqRespJson) {
		try {
			ReqResp reqJson = JsonMan.getReqResp(mobUpiReqRespJson.getReq());
			String payerName = "";
			Long regId = 0l;
			List<CustomerAccount> customeraccounts = custAccDao.findActiveAccounts(reqJson.getPayerAddr(),
					ConstantI.RR);
			for (CustomerAccount customeraccount : customeraccounts) {
				payerName = customeraccount.getName();
				regId = customeraccount.getRegid();
				break;
			}
			reqJson.setPayerName(payerName);
			ReqRespAuthDetails reqrespauthdetails = reqRespAuthDetailsDao.getOnTxnId(reqJson.getTxnId());
			if (null != reqrespauthdetails) {
				String ts = Util.getTS();
				String txnId = reqJson.getTxnId();
				String msgId = Util.uuidGen();
				RespAuthDetails respAuthDetails = new RespAuthDetails();
				respAuthDetails.setHead(setHeadType(msgId, ts));
				respAuthDetails.setTxn(setPayTrans(txnId, reqrespauthdetails.getTxnNote()==null?"NA":reqrespauthdetails.getTxnNote(), reqJson.getTxnRefId(),
						reqJson.getTxnRefUrl(), ts, reqJson.getRrn(), reqJson.getTxnType(),reqJson.getInitiationMode() == null || reqJson.getInitiationMode().isEmpty()
						? Constant.DEF_INITIATION_MODE
								: reqJson.getInitiationMode(),
						reqJson.getTxnPurpose() == null || reqJson.getTxnPurpose().isEmpty() ? Constant.DEFAULT_PURPOSE
								: reqJson.getTxnPurpose()));//reqrespauthdetails.getTxnNote()==null?"NA":reqrespauthdetails.getTxnNote()
				PayerType payer = setPayerTypeDetails(reqJson.getPayerAddr(), payerName, reqJson.getPayerType(),
						reqJson.getPayerCode(), reqJson.getPayerAcNum(), reqJson.getPayerDeviceMobile(),
						reqJson.getPayerDeviceGeoCode(), reqJson.getPayerDeviceLocation(), reqJson.getPayerDeviceIp(),
						reqJson.getPayerDeviceType(), reqJson.getPayerDeviceId(), reqJson.getPayerDeviceOsType(),
						reqJson.getPayerDeviceAppId(), reqJson.getPayerDeviceCapability(), reqJson.getPayerAmount());
				respAuthDetails.setPayer(payer);
				ReqRespAuthDetailsPayees dbPayee = reqRespAuthDetDao
						.getPayees(reqrespauthdetails.getIdReqRespAuthDetails().longValue());
				PayeesType payees = new PayeesType();
				List<PayeeType> payeeList = payees.getPayee();
				PayeeType payee = setPayeeType(reqJson.getPayeeAddr(), reqJson.getPayeeName(), reqJson.getPayeeSeqNum(),
						reqJson.getPayeeType(), reqJson.getPayeeCode(), reqJson.getPayerAmount(),
						dbPayee.getInfoIdType(), dbPayee.getInfoIdVerifiedName(), dbPayee.getInfoId(),
						dbPayee.getAcAddrType(), dbPayee.getAcAddrTypeDetail1(), dbPayee.getAcAddrTypeDetail2(),
						dbPayee.getAcAddrTypeDetail3());
				payeeList.add(payee);
				respAuthDetails.setPayees(payees);
				respAuthDetails.setResp(setRespTypeDetails(reqJson.getHeadReqMsgId()));
				String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(respAuthDetails).toString());
				Ack ack = npciService.send(xmlStr, ConstantI.RESP_AUTH_DETAILS, txnId);
				try {
					mobUpiReqRespJsonIdDao.updateDb(ack, mobUpiReqRespJson,
							respAuthDetails.getTxn().getType().toString());
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				if (null == ack.getErr() || 0 == ack.getErrorMessages().size()) {
					reqRespAuthDetailsDao.updateResp(respAuthDetails, ack);
					custTxnDao.update(respAuthDetails, ack);
					if (spamVpaDao.insert(reqJson.getPayeeAddr(), regId.longValue(), reqJson.getTxnId())) {
						List<SpamVpa> spamVpaList = spamVpaDao.select(reqJson.getPayeeAddr());
						if (5 <= spamVpaList.size()) {
							spamVpaRuleDao.insert(reqJson.getPayeeAddr());
							mobSpamCollectReject.create(reqJson.getPayeeAddr());
						}
					}
				} else {
					reqRespAuthDetailsDao.updateResp(respAuthDetails, null);
					custTxnDao.update(respAuthDetails, null);
				}
			} else {
				mobUpiReqRespJsonIdDao.updateDb(mobUpiReqRespJson);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	private PayerType setPayerTypeDetails(final String payerAddr, final String payerName, final String payerType,
			final String payerCode, final String payerAcNum, final String payerDeviceMobile,
			final String payerDeviceGeoCode, final String payerDeviceLocation, final String payerDeviceIp,
			final String payerDeviceType, final String payerDeviceId, final String payerDeviceOsType,
			final String payerDeviceAppId, final String payerDeviceCapability, final String payerAmount)
			throws Exception {
		PayerType payer = new PayerType();
		try {
			payer = setPayerType(payerAddr, payerName, payerType, payerCode);
			InfoType info = new InfoType();
			info.setIdentity(setIdentityType(payerName, payerAcNum));
			info.setRating(setRatingType());
			payer.setInfo(info);
			payer.setDevice(setDevice(payerDeviceMobile, payerDeviceGeoCode, payerDeviceLocation, payerDeviceIp,
					payerDeviceType, payerDeviceId, payerDeviceOsType, payerDeviceAppId, payerDeviceCapability));
			AmountType amount = new AmountType();
			amount.setValue(payerAmount);
			amount.setCurr(ConstantI.INR);
			payer.setAmount(amount);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return payer;
	}
	
	private PayTrans setPayTrans(final String txnId, final String txnNote, final String txnRefId,
			final String txnRefUrl, final String ts, final String rrn, final String txnType,String initiationMode,
			String purpose)  throws Exception {
		PayTrans txn = new PayTrans();
		try {
			txn.setId(txnId);
			txn.setNote(txnNote);
			txn.setRefId(txnRefId);
			txn.setRefUrl(txnRefUrl);
			txn.setTs(ts);
			txn.setCustRef(rrn);
			txn.setInitiationMode(initiationMode); // To do Verification
			txn.setPurpose(purpose); 
			txn.setType(PayConstant.fromValue(txnType));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return txn;
	}
	
	private PayerType setPayerType(final String payerAddr, final String payerName, final String payerType,
			final String payerCode)  throws Exception {
		PayerType payer = new PayerType();
		try {
			payer.setAddr(payerAddr);
			payer.setName(payerName);
			payer.setSeqNum("0");
			payer.setType(PayerConstant.fromValue(payerType));
			payer.setCode(payerCode);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return payer;
	}
	
	private IdentityType setIdentityType(final String payerName, final String payerAccNum) throws Exception  {
		IdentityType identity = new IdentityType();
		try {
			identity.setType(IdentityConstant.ACCOUNT);
			identity.setVerifiedName(payerName);
			identity.setId(payerAccNum);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return identity;
	}
	
	private IdentityType setIdentityType(final String infoIdType, final String infoIdVerName, final String infoId)  throws Exception {
		IdentityType identity = new IdentityType();
		try {
			identity.setType(IdentityConstant.fromValue(infoIdType));
			identity.setVerifiedName(infoIdVerName);
			identity.setId(infoId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return identity;
	}
	
	private RatingType setRatingType()  throws Exception {
		RatingType rating = new RatingType();
		rating.setVerifiedAddress(WhiteListedConstant.TRUE);
		return rating;
	}
	
	private DeviceType setDevice(final String payerDeviceMob, final String payerDeviceGeoCode,
			final String payerDeviceLoc, final String payerDeviceIp, final String payerDeviceType,
			final String payerDeviceId, final String payerDeviceOsType, final String payerDeviceAppId,
			final String payerDeviceCapability)  throws Exception {
		DeviceType device = new DeviceType();
		try {
			List<Tag> tags = device.getTag();
			{
				{
					Tag tag = new Tag();
					tag.setName(DeviceTagNameType.MOBILE);
					tag.setValue(payerDeviceMob);
					tags.add(tag);
					tag = new Tag();
					tag.setName(DeviceTagNameType.GEOCODE);
					tag.setValue(payerDeviceGeoCode);
					tags.add(tag);
					tag = new Tag();
					tag.setName(DeviceTagNameType.LOCATION);
					tag.setValue(payerDeviceLoc);
					tags.add(tag);
					tag = new Tag();
					tag.setName(DeviceTagNameType.IP);
					tag.setValue(payerDeviceIp);
					tags.add(tag);
					tag = new Tag();
					tag.setName(DeviceTagNameType.TYPE);
					tag.setValue(payerDeviceType);
					tags.add(tag);
					tag = new Tag();
					tag.setName(DeviceTagNameType.ID);
					tag.setValue(payerDeviceId);
					tags.add(tag);
					tag = new Tag();
					tag.setName(DeviceTagNameType.OS);
					tag.setValue(payerDeviceOsType);
					tags.add(tag);
					tag = new Tag();
					tag.setName(DeviceTagNameType.APP);
					tag.setValue(payerDeviceAppId);
					tags.add(tag);
					tag = new Tag();
					tag.setName(DeviceTagNameType.CAPABILITY);
					tag.setValue(payerDeviceCapability);
					tags.add(tag);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return device;
	}
	
	private PayeeType setPayeeType(final String payeeAddress, final String payeeName, final String payeeSeqNum,
			final String payeeType, final String payeeCode, final String payerAmount, final String infoIdType,
			final String infoIdVerifiedName, final String infoId, final String dbPayeeAcAddrType,
			final String accAddrTypeDetail1, final String accAddrTypeDetail2, final String accAddrTypeDetail3)  throws Exception {
		PayeeType payee = new PayeeType();
		try {
			payee.setAddr(payeeAddress);
			payee.setName(payeeName);
			payee.setSeqNum(payeeSeqNum);
			payee.setType(PayerConstant.fromValue(payeeType));
			payee.setCode(payeeCode);
			
			AmountType amtType = new AmountType();
			amtType.setValue(payerAmount);
			amtType.setCurr(ConstantI.INR);
			payee.setAmount(amtType);
			
			InfoType info = new InfoType();
			info.setIdentity(setIdentityType(infoIdType, infoIdVerifiedName, infoId));
			payee.setInfo(info);
			payee.setAc(setAccountTypeDetails(dbPayeeAcAddrType, accAddrTypeDetail1, accAddrTypeDetail2,
					accAddrTypeDetail3));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return payee;
	}
	
	private AccountType setAccountTypeDetails(final String acAddrType, final String acAddrTypeDetail1,
			final String acAddrTypeDetail2, final String acAddrTypeDetail3)  throws Exception {
		AccountType ac = new AccountType();
		try {
			Detail detail = new Detail();
			List<Detail> details = ac.getDetail();
			ac.setAddrType(AddressType.fromValue(acAddrType));
			details = ac.getDetail();
			ArrayList<String> detailList1 = Util.strSplit(acAddrTypeDetail1, ConstantI.CHAR_CONST_PIPE);
			detail = new Detail();
			detail.setName(
					AccountDetailType.fromValue(Util.strSplit(detailList1.get(0), ConstantI.CHAR_CONST_EQUAL).get(0)));
			detail.setValue(Util.strSplit(detailList1.get(0), ConstantI.CHAR_CONST_EQUAL).get(1));
			details.add(detail);
			ArrayList<String> detailList2 = Util.strSplit(acAddrTypeDetail2, ConstantI.CHAR_CONST_PIPE);
			detail = new Detail();
			detail.setName(
					AccountDetailType.fromValue(Util.strSplit(detailList2.get(0), ConstantI.CHAR_CONST_EQUAL).get(0)));
			detail.setValue(Util.strSplit(detailList2.get(0), ConstantI.CHAR_CONST_EQUAL).get(1));
			details.add(detail);
			if (null != acAddrTypeDetail3) {
				ArrayList<String> detailList3 = Util.strSplit(acAddrTypeDetail3, ConstantI.CHAR_CONST_PIPE);
				detail = new Detail();
				detail.setName(AccountDetailType
						.fromValue(Util.strSplit(detailList3.get(0), ConstantI.CHAR_CONST_EQUAL).get(0)));
				detail.setValue(Util.strSplit(detailList3.get(0), ConstantI.CHAR_CONST_EQUAL).get(1));
				details.add(detail);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ac;
	}
	
	private HeadType setHeadType(final String msgId, final String ts)  throws Exception {
		HeadType head = new HeadType();
		try {
			head.setMsgId(msgId);
			head.setOrgId(Constant.orgId);
			head.setTs(ts);
			head.setVer(Constant.headVer);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return head;
	}
	
	private RespType setRespTypeDetails(final String headReqMsgId) throws Exception {
		RespType resp = new RespType();
		try {
			resp.setReqMsgId(headReqMsgId);
			resp.setResult(ConstantI.FAILURE);
			resp.setErrCode(Constant.TRANSACTIONDECLINEDBYCUSTOMER);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return resp;
	}
	
}
