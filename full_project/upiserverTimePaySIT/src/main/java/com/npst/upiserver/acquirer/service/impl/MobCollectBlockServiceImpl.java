package com.npst.upiserver.acquirer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.MobCollectBlockService;
import com.npst.upiserver.acquirer.service.RejectOnPayeeAddrService;
import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.BlockedByCustomerDao;
import com.npst.upiserver.dao.CustomerAccountDao;
import com.npst.upiserver.dao.CustomerTxnsDao;
import com.npst.upiserver.dao.MobUpiReqRespJsonIdDao;
import com.npst.upiserver.dao.ReqRespAuthDetailsDao;
import com.npst.upiserver.dao.ReqRespAuthDetailsPayeesDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.CustomerAccount;
import com.npst.upiserver.entity.MobUpiReqRespJson;
import com.npst.upiserver.entity.ReqRespAuthDetails;
import com.npst.upiserver.entity.ReqRespAuthDetailsPayees;
import com.npst.upiserver.npcischema.AccountDetailType;
import com.npst.upiserver.npcischema.AccountType;
import com.npst.upiserver.npcischema.AccountType.Detail;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.AddressType;
import com.npst.upiserver.npcischema.AmountType;
import com.npst.upiserver.npcischema.DeviceTagNameType;
import com.npst.upiserver.npcischema.DeviceType;
import com.npst.upiserver.npcischema.DeviceType.Tag;
import com.npst.upiserver.service.NpciUpiRestConService;
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
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.JsonMan;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.Util;


@Service
public class MobCollectBlockServiceImpl implements MobCollectBlockService {
	private static final Logger	log	= LoggerFactory.getLogger(MobCollectBlockServiceImpl.class);
	
	@Autowired
	CustomerAccountDao			custAccDao;
	@Autowired
	ReqRespAuthDetailsDao		reqRespAuthDetailsDao;
	@Autowired
	ReqRespAuthDetailsPayeesDao	reqRespAuthDetDao;
	@Autowired
	NpciUpiRestConService		npciService;
	@Autowired
	MobUpiReqRespJsonIdDao		mobUpiReqRespJsonIdDao;
	@Autowired
	CustomerTxnsDao				custTxnDao;
	@Autowired
	BlockedByCustomerDao		blockedByCustDao;
	@Autowired
	RejectOnPayeeAddrService	rejectOnPayyeAddService;
	
	@Override
	public void procAndSendNpci(MobUpiReqRespJson mobUpiReqRespJson) {
		try {
			ReqResp reqJson = JsonMan.getReqResp(mobUpiReqRespJson.getReq());
			String payerName = "";
			Long regId = 0l;
			List<CustomerAccount> customeraccounts = custAccDao.findActiveAccounts(reqJson.getPayerAddr(), "RR");
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
				respAuthDetails.setHead(setHeadTypeDetails(msgId, ts));
				respAuthDetails.setTxn(setPayTransDetails(txnId, reqrespauthdetails.getTxnNote()==null?"NA":reqrespauthdetails.getTxnNote(), reqJson.getTxnRefId(),
						reqJson.getTxnRefUrl(), ts, reqJson.getRrn(), reqJson.getTxnType(),reqJson.getInitiationMode() == null || reqJson.getInitiationMode().isEmpty()
						? Constant.DEF_INITIATION_MODE
								: reqJson.getInitiationMode(),
						reqJson.getTxnPurpose() == null || reqJson.getTxnPurpose().isEmpty() ? Constant.DEFAULT_PURPOSE
								: reqJson.getTxnPurpose()));
				PayerType payer = setPayerTypeDetails(reqJson.getPayerAddr(), payerName, reqJson.getPayerSeqNum(),
						reqJson.getPayerType(), reqJson.getPayerCode());
				payer.setInfo(setInfoTypeDetails(payerName, reqJson.getPayerAcNum()));
				payer.setDevice(setDeviceTypeDetails(reqJson.getPayerDeviceMobile(), reqJson.getPayerDeviceGeoCode(),
						reqJson.getPayerDeviceLocation(), reqJson.getPayerDeviceIp(), reqJson.getPayerDeviceType(),
						reqJson.getPayerDeviceId(), reqJson.getPayerDeviceOsType(), reqJson.getPayerDeviceAppId(),
						reqJson.getPayerDeviceCapability()));
				payer.setAmount(setAmountDetails(reqJson.getPayerAmount()));
				respAuthDetails.setPayer(payer);
				ReqRespAuthDetailsPayees dbPayee = reqRespAuthDetDao
						.getPayees(reqrespauthdetails.getIdReqRespAuthDetails().longValue());
				PayeesType payees = new PayeesType();
				List<PayeeType> payeeList = payees.getPayee();
				PayeeType payee = setPayeeTypeDetails(reqJson.getPayeeAddr(), reqJson.getPayeeName(),
						reqJson.getPayeeSeqNum(), reqJson.getPayeeType(), reqJson.getPayeeCode(),
						reqJson.getPayerAmount());
				payee.setInfo(
						setInfoType(dbPayee.getInfoIdType(), dbPayee.getInfoIdVerifiedName(), dbPayee.getInfoId()));
				payee.setAc(setAccountTypeDetails(dbPayee.getAcAddrType(), dbPayee.getAcAddrTypeDetail1(),
						dbPayee.getAcAddrTypeDetail2(), dbPayee.getAcAddrTypeDetail3()));
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
				try {
					reqRespAuthDetailsDao.updateResp(respAuthDetails, ack);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				try {
					custTxnDao.update(respAuthDetails, ack);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				if (null == ack.getErr() || 0 == ack.getErrorMessages().size()) {
					blockedByCustDao.insert(reqJson.getPayeeAddr(), regId.longValue());
					rejectOnPayyeAddService.create(reqJson.getPayeeAddr(), regId, reqJson);
				}
			} else {
				mobUpiReqRespJsonIdDao.updateDb(mobUpiReqRespJson);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	private HeadType setHeadTypeDetails(final String msgId, String ts) throws Exception {
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
	
	private PayTrans setPayTransDetails(final String txnId, final String txnNote, final String txnRefId,
			final String txnRefUrl, final String ts, final String rrn, final String txnType,String initiationMode,
			String purpose) throws Exception {
		PayTrans txn = new PayTrans();
		try {
			txn.setId(txnId);
			txn.setNote(txnNote);
			txn.setRefId(txnRefId);
			txn.setRefUrl(txnRefUrl);
			txn.setTs(ts);
			txn.setCustRef(rrn);
			txn.setType(PayConstant.fromValue(txnType));
			txn.setInitiationMode(initiationMode); // To do Verification
			txn.setPurpose(purpose); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return txn;
	}
	
	PayerType setPayerTypeDetails(final String payerAddr, final String payerName, final String payerSeqNum,
			final String payerType, final String payerCode) throws Exception {
		PayerType payer = new PayerType();
		try {
			payer.setAddr(payerAddr);
			payer.setName(payerName);
			payer.setSeqNum(payerSeqNum);
			payer.setType(PayerConstant.fromValue(payerType));
			payer.setCode(payerCode);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return payer;
	}
	
	InfoType setInfoTypeDetails(final String payerName, final String payerAccNum) throws Exception {
		InfoType info = new InfoType();
		try {
			RatingType rating = new RatingType();
			rating.setVerifiedAddress(WhiteListedConstant.TRUE);
			IdentityType identity = new IdentityType();
			identity.setType(IdentityConstant.ACCOUNT);
			identity.setVerifiedName(payerName);
			identity.setId(payerAccNum);
			info.setIdentity(identity);
			info.setRating(rating);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return info;
	}
	
	DeviceType setDeviceTypeDetails(final String payerDeviceMobile, final String payerDeviceGeoCode,
			final String payerDeviceLocation, final String payerDeviceIp, final String payerDeviceType,
			final String payerDeviceId, final String payerDeviceOsType, final String payerDeviceAppId,
			final String payerDeviceCapability) throws Exception {
		DeviceType device = new DeviceType();
		try {
			List<Tag> tags = device.getTag();
			{
				{
					Tag tag = new Tag();
					tag.setName(DeviceTagNameType.MOBILE);
					tag.setValue(payerDeviceMobile);
					tags.add(tag);
					tag = new Tag();
					tag.setName(DeviceTagNameType.GEOCODE);
					tag.setValue(payerDeviceGeoCode);
					tags.add(tag);
					tag = new Tag();
					tag.setName(DeviceTagNameType.LOCATION);
					tag.setValue(payerDeviceLocation);
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
	
	private AmountType setAmountDetails(final String payerAmount) throws Exception {
		AmountType amount = new AmountType();
		try {
			amount.setValue(payerAmount);
			amount.setCurr(ConstantI.INR);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return amount;
	}
	
	PayeeType setPayeeTypeDetails(final String payeeAddress, final String payeeName, final String payeeSeqNum,
			final String payeeType, final String payeeCode, final String payerAmount) throws Exception {
		PayeeType payee = new PayeeType();
		try {
			payee.setAddr(payeeAddress);
			payee.setName(payeeName);
			payee.setSeqNum(payeeSeqNum);
			payee.setType(PayerConstant.fromValue(payeeType));
			payee.setCode(payeeCode);
			payee.setAmount(setAmountDetails(payerAmount));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return payee;
	}
	
	private InfoType setInfoType(final String infoIdType, final String infoIdVerName, final String infoId)
			throws Exception {
		InfoType info = new InfoType();
		try {
			IdentityType identity = new IdentityType();
			identity.setType(IdentityConstant.fromValue(infoIdType));
			identity.setVerifiedName(infoIdVerName);
			identity.setId(infoId);
			info.setIdentity(identity);
			RatingType rating = new RatingType();
			rating.setVerifiedAddress(WhiteListedConstant.TRUE);
			info.setRating(rating);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return info;
	}
	
	private AccountType setAccountTypeDetails(final String accAddrType, final String acAddrTypeDetails1,
			final String acAddrTypeDetails2, final String acAddrTypeDetails3) throws Exception {
		AccountType ac = new AccountType();
		try {
			Detail detail = new Detail();
			List<Detail> details = ac.getDetail();
			ac.setAddrType(AddressType.fromValue(accAddrType));
			details = ac.getDetail();
			ArrayList<String> detailList1 = Util.strSplit(acAddrTypeDetails1, ConstantI.CHAR_CONST_PIPE);
			detail = new Detail();
			detail.setName(
					AccountDetailType.fromValue(Util.strSplit(detailList1.get(0), ConstantI.CHAR_CONST_EQUAL).get(0)));
			detail.setValue(Util.strSplit(detailList1.get(0), ConstantI.CHAR_CONST_EQUAL).get(1));
			details.add(detail);
			ArrayList<String> detailList2 = Util.strSplit(acAddrTypeDetails2, ConstantI.CHAR_CONST_PIPE);
			detail = new Detail();
			detail.setName(
					AccountDetailType.fromValue(Util.strSplit(detailList2.get(0), ConstantI.CHAR_CONST_EQUAL).get(0)));
			detail.setValue(Util.strSplit(detailList2.get(0), ConstantI.CHAR_CONST_EQUAL).get(1));
			details.add(detail);
			if (null != acAddrTypeDetails3) {
				ArrayList<String> detailList3 = Util.strSplit(acAddrTypeDetails3, ConstantI.CHAR_CONST_PIPE);
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
	
	private RespType setRespTypeDetails(final String headReqMsgId) throws Exception {
		RespType resp = new RespType();
		try {
			resp.setReqMsgId(headReqMsgId);
			resp.setResult("FAILURE");
			resp.setErrCode(Constant.BLOCKBYCUSTOMER);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return resp;
	}
}
