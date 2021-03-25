package com.npst.upiserver.acquirer.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.MobRegMobService;
import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.CustomerTxnsDao;
import com.npst.upiserver.dao.MobReqRespJsonIdDao;
import com.npst.upiserver.dao.MobUpiReqRespJsonIdDao;
import com.npst.upiserver.dao.ReqRespRegMobDao;
import com.npst.upiserver.dto.CredJson;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.MobUpiReqRespJson;
import com.npst.upiserver.npcischema.AccountDetailType;
import com.npst.upiserver.npcischema.AccountType;
import com.npst.upiserver.npcischema.AccountType.Detail;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.AddressType;
import com.npst.upiserver.npcischema.CredSubType;
import com.npst.upiserver.npcischema.CredType;
import com.npst.upiserver.npcischema.CredsType;
import com.npst.upiserver.npcischema.CredsType.Cred;
import com.npst.upiserver.npcischema.CredsType.Cred.Data;
import com.npst.upiserver.npcischema.DeviceTagNameType;
import com.npst.upiserver.npcischema.DeviceType;
import com.npst.upiserver.npcischema.DeviceType.Tag;
import com.npst.upiserver.npcischema.HeadType;
import com.npst.upiserver.npcischema.MobRegDetailsNameType;
import com.npst.upiserver.npcischema.PayConstant;
import com.npst.upiserver.npcischema.PayTrans;
import com.npst.upiserver.npcischema.PayerConstant;
import com.npst.upiserver.npcischema.PayerType;
import com.npst.upiserver.npcischema.ReqRegMob;
import com.npst.upiserver.npcischema.ReqRegMob.RegDetails;
import com.npst.upiserver.service.NpciUpiRestConService;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.JsonMan;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.Util;

@Service
public class MobRegMobServiceImpl implements MobRegMobService {
	private static final Logger	log	= LoggerFactory.getLogger(MobRegMobServiceImpl.class);
	
	@Autowired
	MobReqRespJsonIdDao			mobReqRespJsonIdDao;
	@Autowired
	NpciUpiRestConService		npciService;
	@Autowired
	MobUpiReqRespJsonIdDao		mobUpiReqRespJsonIdDao;
	@Autowired
	ReqRespRegMobDao			reqRespRegMob;
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
			ReqRegMob reqRegMob = new ReqRegMob();
			reqRegMob.setHead(setHeadType(msgId, ts));
			PayTrans txn=setPayTrans(txnId,reqJson.getTxnNote(),reqJson.getTxnRefId(),reqJson.getTxnRefUrl(),ts);
			reqRegMob.setTxn(txn);
			PayerType payer = new PayerType();
			payer.setDevice(setDeviceTypeDetails(reqJson.getPayerDeviceMobile(),reqJson.getPayerDeviceGeoCode(),reqJson.getPayerDeviceLocation(),reqJson.getPayerDeviceIp(),
					reqJson.getPayerDeviceType(),reqJson.getPayerDeviceId(),reqJson.getPayerDeviceOsType(),reqJson.getPayerDeviceAppId(),reqJson.getPayerDeviceCapability(),reqJson.getPayerDeviceTelecom()!=null?reqJson.getPayerDeviceTelecom():""
					));
			payer.setAc(setAccountTypeDetails(payer.getAc(), reqJson.getPayerAddrType(), reqJson.getPayerIfsc(), reqJson.getPayerAcType(), reqJson.getPayerAcNum(), reqJson.getPayerMmid(), reqJson.getPayerMobileNo(), reqJson.getPayerIin(), reqJson.getPayerUidNum(), reqJson.getPayerCardNum()));
			payer.setAddr(reqJson.getPayerAddr());
			payer.setCode(reqJson.getPayerCode());
			payer.setName(reqJson.getPayerName());
			payer.setSeqNum(reqJson.getPayerSeqNum());
			payer.setType(PayerConstant.fromValue(reqJson.getPayerType()));
			reqRegMob.setPayer(payer);
			RegDetails regDetails = new RegDetails();
			regDetails.setType(reqJson.getRegDetailsType());
			regDetails.setCreds(setCredsTypeDetails(reqJson.getCredJsons()));
			List<ReqRegMob.RegDetails.Detail> details = regDetails.getDetail();
			details.add(setDetails(MobRegDetailsNameType.MOBILE, reqJson.getRegMobile()));
			details.add(setDetails(MobRegDetailsNameType.CARDDIGITS, reqJson.getRegCardDigits()));
			details.add(setDetails(MobRegDetailsNameType.EXPDATE, reqJson.getRegExpDate()));
			reqRegMob.setRegDetails(regDetails);
			String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(reqRegMob).toString());
			log.info("ReqRegMob XML {} ",xmlStr);
			Ack ack = npciService.send(xmlStr, ConstantI.API_REG_MOB, txnId);
			if (null != ack.getErr() || 0 != ack.getErrorMessages().size()) {
				log.debug("null != ack.getErr() || 0 != ack.getErrorMessages().size()");
				try {
					mobUpiReqRespJsonIdDao.updateDb(ack, mobUpiReqRespJson, reqRegMob.getTxn().getType().toString());
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
			try {
				reqRespRegMob.insertReq(reqRegMob, ack);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			try {
				custTxnDao.insert(reqRegMob, ack);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	private HeadType setHeadType(final String msgId,final String ts) throws Exception {
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
	
	private PayTrans setPayTrans(final String txnId,final String txnNote,final String txnRefId,final String txnRefUrl,final String ts) throws Exception{
		PayTrans txn = new PayTrans();
		try {
			txn.setId(txnId);
			txn.setNote(txnNote);
			txn.setRefId(txnRefId);
			txn.setRefUrl(txnRefUrl);
			txn.setTs(ts);
			txn.setType(PayConstant.REQ_REG_MOB);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return txn;
	}

	private DeviceType setDeviceTypeDetails(final String payerDeviceMobile,final String payerDeviceGeoCode,final String payerDeviceLocation,final String payerDeviceIp,final String payerDeviceType,final String payerDeviceId,final String payerDeviceOsType,final String payerDeviceAppId,final String payerDeviceCapability,final String payerDeviceTelecom) throws Exception{
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
					if (payerDeviceTelecom != null && !payerDeviceTelecom.isEmpty()) {
						tag = new Tag();
						tag.setName(DeviceTagNameType.TELECOM);
						tag.setValue(payerDeviceTelecom);
						tags.add(tag);
					}
				}
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return device;
	}
	
	private AccountType setAccountTypeDetails(AccountType ac ,final String payerAddrType,final String payerIfsc,final String payerAcType,
			final String payerAcNum,final String payerMmid,final String payerMobileNo,final String payerIin,final String payerUidNum,final String payerCardNum) throws Exception{
		try {
			if(ac==null) {
				ac=new AccountType();
			}
			//log.info("payerAddrType is as {} ",payerAddrType);
			if (ConstantI.ACCOUNT.equalsIgnoreCase(payerAddrType)) {
				ac.setAddrType(AddressType.ACCOUNT);
				List<Detail> details = ac.getDetail();
				Detail detail = new Detail();
				detail.setName(AccountDetailType.IFSC);
				detail.setValue(payerIfsc.toUpperCase());
				details.add(detail);
				detail = new Detail();
				detail.setName(AccountDetailType.ACTYPE);
				detail.setValue(payerAcType);
				details.add(detail);
				detail = new Detail();
				detail.setName(AccountDetailType.ACNUM);
				detail.setValue(payerAcNum);
				details.add(detail);
			} else if (ConstantI.MOBILE.equalsIgnoreCase(payerAddrType)) {
				List<Detail> details = ac.getDetail();
				ac.setAddrType(AddressType.MOBILE);
				Detail detail = new Detail();
				detail.setName(AccountDetailType.MMID);
				detail.setValue(payerMmid);
				details.add(detail);
				detail = new Detail();
				detail.setName(AccountDetailType.MOBNUM);
				detail.setValue(payerMobileNo);
				details.add(detail);
			} else if (ConstantI.AADHAAR.equalsIgnoreCase(payerAddrType)) {
				List<Detail> details = ac.getDetail();
				ac.setAddrType(AddressType.AADHAAR);
				Detail detail = new Detail();
				detail.setName(AccountDetailType.IIN);
				detail.setValue(payerIin);
				details.add(detail);
				detail = new Detail();
				detail.setName(AccountDetailType.UIDNUM);
				detail.setValue(payerUidNum);
				details.add(detail);
			} else if (ConstantI.CARD.equalsIgnoreCase(payerAddrType)) {
				List<Detail> details = ac.getDetail();
				ac.setAddrType(AddressType.CARD);
				Detail detail = new Detail();
				detail.setName(AccountDetailType.ACTYPE);
				detail.setValue(payerAcType);
				details.add(detail);
				detail = new Detail();
				detail.setName(AccountDetailType.CARDNUM);
				detail.setValue(payerCardNum);
				details.add(detail);
			} else {
				log.debug("Nothing");
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return ac;
	}
	
	private CredsType setCredsTypeDetails(final List<CredJson> credJsons) throws Exception{
		CredsType creds = new CredsType();
		try {
			List<CredJson> list = credJsons;
			List<Cred> credList = creds.getCred();
			Data data = new Data();
			for (CredJson object : list) {
				if (ConstantI.PIN.equalsIgnoreCase(object.getType()) && (ConstantI.NMPIN.equalsIgnoreCase(object.getSubType())
						|| ConstantI.MPIN.equalsIgnoreCase(object.getSubType()))) {
					log.debug(
							"\"PIN\".equalsIgnoreCase(object.getType()) && (\"NMPIN\".equalsIgnoreCase(object.getSubType())\r\n"
									+ "						|| \"MPIN\".equalsIgnoreCase(object.getSubType()))");
					Cred cred = new Cred();
					data = new Data();
					data.setCode(object.getData().getCode());
					data.setValue(object.getData().getEncryptedBase64String());
					data.setKi(object.getData().getKi());
					cred.setData(data);
					cred.setSubType(CredSubType.MPIN);
					cred.setType(CredType.fromValue(object.getType()));
					credList.add(cred);
				}
				if (ConstantI.SMS.equalsIgnoreCase(object.getSubType())) {
					log.debug("\"SMS\".equalsIgnoreCase(object.getSubType())");
					Cred cred = new Cred();
					data = new Data();
					data.setCode(object.getData().getCode());
					data.setValue(object.getData().getEncryptedBase64String());
					data.setKi(object.getData().getKi());
					cred.setData(data);
					cred.setSubType(CredSubType.fromValue(object.getSubType()));
					cred.setType(CredType.fromValue(object.getType()));
					credList.add(cred);
				}
				if (ConstantI.ATMPIN.equalsIgnoreCase(object.getSubType())) {
					log.debug("\"ATMPIN\".equalsIgnoreCase(object.getSubType())");
					Cred cred = new Cred();
					data = new Data();
					data.setCode(object.getData().getCode());
					data.setValue(object.getData().getEncryptedBase64String());
					data.setKi(object.getData().getKi());
					cred.setData(data);
					cred.setSubType(CredSubType.fromValue(object.getSubType()));
					cred.setType(CredType.fromValue(object.getType()));
					credList.add(cred);
				}
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return creds;
	}
	
	private ReqRegMob.RegDetails.Detail setDetails(final MobRegDetailsNameType name,final String value) throws Exception{
		ReqRegMob.RegDetails.Detail detail = new ReqRegMob.RegDetails.Detail();
		try {
			detail.setName(name);
			detail.setValue(value);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return detail;
	}
}