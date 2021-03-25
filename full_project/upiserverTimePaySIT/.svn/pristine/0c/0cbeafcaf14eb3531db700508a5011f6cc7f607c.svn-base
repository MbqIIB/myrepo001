package com.npst.upiserver.acquirer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.RejectOnPayeeAddrService;
import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.CustomerTxnsDao;
import com.npst.upiserver.dao.ReqRespAuthDetailsDao;
import com.npst.upiserver.dao.ReqRespAuthDetailsPayeesDao;
import com.npst.upiserver.dto.ReqResp;
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
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.Util;

@Service
public class RejectOnPayeeAddrServiceImpl implements RejectOnPayeeAddrService {
	private static final Logger	log	= LoggerFactory.getLogger(RejectOnPayeeAddrServiceImpl.class);
	
	@Autowired
	ReqRespAuthDetailsDao		reqRespAuthDetailsDao;
	@Autowired
	NpciUpiRestConService		npciService;
	@Autowired
	CustomerTxnsDao				custTxnDao;
	@Autowired
	ReqRespAuthDetailsPayeesDao	reqRespAuthDetailsPayeesDao;
	
	@Override
	public void create(String payeeAddr, Long regId, ReqResp reqJson) {
		try {
			List<ReqRespAuthDetails> listAll = reqRespAuthDetailsDao.getOnPayeeAddr(payeeAddr, (long) regId);
			for (ReqRespAuthDetails reqrespauthdetails : listAll) {
				
				String ts = Util.getTS();
				String txnId = reqrespauthdetails.getTxnId();
				String msgId = Util.uuidGen();
				RespAuthDetails respAuthDetails = new RespAuthDetails();
				respAuthDetails.setHead(setHeadTypeDetails(msgId, ts));
				respAuthDetails.setTxn(setPayTransDetails(txnId, reqrespauthdetails.getTxnNote(),
						reqrespauthdetails.getTxnRefid(), reqrespauthdetails.getTxnRefurl(), ts,
						reqrespauthdetails.getTxnType(), reqrespauthdetails.getTxnCustRef()));
				
				PayerType payer = setPayerTypeDetails(reqJson.getPayerAddr(), reqJson.getPayerName(),
						reqJson.getPayerSeqNum(), reqJson.getPayerType(), reqJson.getPayerCode(),
						reqJson.getPayerAcNum(), reqJson.getPayerDeviceMobile(), reqJson.getPayerDeviceGeoCode(),
						reqJson.getPayerDeviceLocation(), reqJson.getPayerDeviceIp(), reqJson.getPayerDeviceType(),
						reqJson.getPayerDeviceId(), reqJson.getPayerDeviceOsType(), reqJson.getPayerDeviceAppId(),
						reqJson.getPayerDeviceCapability(), reqJson.getPayerAmount());
				
				respAuthDetails.setPayer(payer);
				ReqRespAuthDetailsPayees dbPayee = reqRespAuthDetailsPayeesDao.getPayeesByTxnId(payeeAddr,
						reqrespauthdetails.getIdReqRespAuthDetails().toString());
				
				PayeesType payees=setPayeesTypeDetails(payeeAddr, dbPayee.getPayeeName(), dbPayee.getPayeeSeqNum(),
						dbPayee.getPayeeType(), dbPayee.getPayeeCode(), reqJson.getPayerAmount(), dbPayee.getInfoIdType(),
						dbPayee.getInfoIdVerifiedName(), dbPayee.getInfoId(), dbPayee.getAcAddrType(),
						dbPayee.getAcAddrTypeDetail1(), dbPayee.getAcAddrTypeDetail2(), dbPayee.getAcAddrTypeDetail3());
				
				respAuthDetails.setPayees(payees);
				respAuthDetails.setResp(setRespTypeDetails(reqrespauthdetails.getReqHeadMsgId()));
				String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(respAuthDetails).toString());
				Ack ack = npciService.send(xmlStr, ConstantI.RESP_AUTH_DETAILS, txnId);
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
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	private RespType setRespTypeDetails(final String msgId) throws Exception {
		RespType resp = new RespType();
		try {
			resp.setReqMsgId(msgId);
			resp.setResult(ConstantI.FAILURE);
			resp.setErrCode(Constant.BLOCKBYCUSTOMER);
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return resp;
	}
	
	private PayeesType setPayeesTypeDetails(final String payeeAddr, final String payeeName, final String payeeSeqNum,
			final String payeeType, final String payeeCode, final String payeeAmount, final String infoIdType,
			final String infoIdVerifiedName, final String infoId,
			final String acAddrType, final String acAddrTypeDetail1, final String acAddrTypeDetail2,
			final String acAddrTypeDetail3) throws Exception {
		PayeesType payees = new PayeesType();
		try {
			List<PayeeType> payeeList = payees.getPayee();
			PayeeType payee = new PayeeType();
			payee.setAddr(payeeAddr);
			payee.setName(payeeName);
			payee.setSeqNum(payeeSeqNum);
			payee.setType(PayerConstant.fromValue(payeeType));
			payee.setCode(payeeCode);
			AmountType amount = new AmountType();
			amount.setValue(payeeAmount);
			amount.setCurr(ConstantI.INR);
			payee.setAmount(amount);
			InfoType info = new InfoType();
			IdentityType identity = new IdentityType();
			identity = new IdentityType();
			identity.setType(IdentityConstant.fromValue(infoIdType));
			identity.setVerifiedName(infoIdVerifiedName);
			identity.setId(infoId);
			info.setIdentity(identity);
			
			RatingType rating = new RatingType();
			rating.setVerifiedAddress(WhiteListedConstant.TRUE);
			info.setRating(rating);
			payee.setInfo(info);
			AccountType ac = new AccountType();
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
			payee.setAc(ac);
			payeeList.add(payee);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return payees;
	}
	
	private PayerType setPayerTypeDetails(final String payerAddr, final String payerName, final String payerSeqNum,
			final String payerType, final String payerCode, final String payerAcNum, final String payerDeviceMobile,
			final String payerDeviceGeoCode, final String payerDeviceLocation, final String payerDeviceIp,
			final String payerDeviceType, final String payerDeviceId, final String payerDeviceOsType,
			final String payerDeviceAppId, final String payerDeviceCapability, final String payerAmount)
			throws Exception {
		PayerType payer = new PayerType();
		try {
			payer.setAddr(payerAddr);
			payer.setName(payerName);
			payer.setSeqNum(payerSeqNum);
			payer.setType(PayerConstant.fromValue(payerType));
			payer.setCode(payerCode);
			InfoType info = new InfoType();
			IdentityType identity = new IdentityType();
			RatingType rating = new RatingType();
			identity.setType(IdentityConstant.ACCOUNT);
			identity.setVerifiedName(payerName);
			identity.setId(payerAcNum);
			info.setIdentity(identity);
			rating.setVerifiedAddress(WhiteListedConstant.TRUE);
			info.setRating(rating);
			payer.setInfo(info);
			DeviceType device = new DeviceType();
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
			payer.setDevice(device);
			AmountType amount = new AmountType();
			amount.setValue(payerAmount);
			amount.setCurr(ConstantI.INR);
			payer.setAmount(amount);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return payer;
	}
	
	private HeadType setHeadTypeDetails(final String msgId, final String ts) throws Exception {
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
			final String txnRefUrl, final String ts, final String txnType, final String txnCustRef) throws Exception {
		PayTrans txn = new PayTrans();
		try {
			txn.setId(txnId);
			txn.setNote(txnNote);
			txn.setRefId(txnRefId);
			txn.setRefUrl(txnRefUrl);
			txn.setTs(ts);
			txn.setType(PayConstant.fromValue(txnType));
			txn.setCustRef(txnCustRef);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return txn;
	}
}
