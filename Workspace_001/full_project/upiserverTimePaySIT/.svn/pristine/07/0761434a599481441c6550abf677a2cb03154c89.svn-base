package com.npst.upiserver.acquirer.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.MobOtpService;
import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.CustomerTxnsDao;
import com.npst.upiserver.dao.MobReqRespJsonIdDao;
import com.npst.upiserver.dao.MobUpiReqRespJsonIdDao;
import com.npst.upiserver.dao.ReqRespOtpDao;
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
import com.npst.upiserver.npcischema.ReqOtp;
import com.npst.upiserver.npcischema.ReqOtp.RegDetails;
import com.npst.upiserver.service.NpciUpiRestConService;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.JsonMan;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.Util;

@Service
public class MobOtpServiceImpl implements MobOtpService {
	private static final Logger log = LoggerFactory.getLogger(MobOtpServiceImpl.class);

	@Autowired
	MobReqRespJsonIdDao mobReqRespJsonIdDao;
	@Autowired
	NpciUpiRestConService npciService;
	@Autowired
	MobUpiReqRespJsonIdDao mobUpiReqRespJsonIdDao;
	@Autowired
	ReqRespOtpDao reqRespOtpDao;
	@Autowired
	CustomerTxnsDao custTxnDao;

	@Override
	public void procAndSendNpci(MobUpiReqRespJson mobUpiReqRespJson) {
		try {
			ReqResp reqJson = JsonMan.getReqResp(mobUpiReqRespJson.getReq());
			String ts = Util.getTS();
			String txnId = Util.uuidGen();
			String msgId = Util.uuidGen();
			mobReqRespJsonIdDao.updateMsgId(msgId, mobUpiReqRespJson.getIdPk().longValue());
			ReqOtp reqOtp = new ReqOtp();
			reqOtp.setHead(setHeadTypeDetails(msgId, ts));

			reqOtp.setTxn(setPayTransDetails(txnId, reqJson.getTxnNote(), reqJson.getTxnRefId(), reqJson.getTxnRefUrl(),
					ts, reqJson.getTxnType()));

			PayerType payer = setPayerTypeDetails(reqJson.getPayerAddr(), reqJson.getPayerName(),
					reqJson.getPayerSeqNum(), reqJson.getPayerType(), reqJson.getPayerCode());

			payer.setAc(
					setAccountTypeDetails(reqJson.getPayerIfsc(), reqJson.getPayerAcType(), reqJson.getPayerAcNum()));

			payer.setDevice(setDeviceType(reqJson.getPayerDeviceMobile(), reqJson.getPayerDeviceGeoCode(),
					reqJson.getPayerDeviceLocation(), reqJson.getPayerDeviceIp(), reqJson.getPayerDeviceType(),
					reqJson.getPayerDeviceId(), reqJson.getPayerDeviceOsType(), reqJson.getPayerDeviceAppId(),
					reqJson.getPayerDeviceCapability(),
					reqJson.getPayerDeviceTelecom() != null && !reqJson.getPayerDeviceTelecom().isEmpty()
							? reqJson.getPayerDeviceTelecom()
							: null));
			reqOtp.setPayer(payer);
			String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(reqOtp).toString());
			log.info("xmlStr is as {} ", xmlStr);
			Ack ack = npciService.send(xmlStr, ConstantI.API_REQ_OTP, txnId);
			try {
				mobUpiReqRespJsonIdDao.updateDb(ack, mobUpiReqRespJson, reqOtp.getTxn().getType().toString());
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			try {
				reqRespOtpDao.insertReq(reqOtp, ack);
				log.info("OTP request inserted in reqrespOtp table ");
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			try {
				custTxnDao.insert(reqOtp, ack);
				log.info("OTP request inserted in custTxnDao table ");
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	// to be removed

	private ReqOtp.RegDetails.Detail setDetails(final MobRegDetailsNameType name, final String value) throws Exception {
		ReqOtp.RegDetails.Detail detail = new ReqOtp.RegDetails.Detail();
		try {
			detail.setName(name);
			detail.setValue(value);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return detail;
	}

	private CredsType setCredsTypeDetails(final List<CredJson> credJsons) throws Exception {
		CredsType creds = new CredsType();
		try {
			List<CredJson> list = credJsons;
			List<Cred> credList = creds.getCred();
			Data data = new Data();
			for (CredJson object : list) {
				if (ConstantI.PIN.equalsIgnoreCase(object.getType())
						&& (ConstantI.NMPIN.equalsIgnoreCase(object.getSubType())
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
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return creds;
	}

	private HeadType setHeadTypeDetails(final String msgId, final String ts) throws Exception {
		HeadType head = new HeadType();
		head.setMsgId(msgId);
		head.setOrgId(Constant.orgId);
		head.setTs(ts);
		head.setVer(Constant.headVer);
		return head;
	}

	PayTrans setPayTransDetails(final String txnId, final String txnNote, final String txnRefId, final String txnRefUrl,
			final String ts, final String txnType) throws Exception {
		PayTrans txn = new PayTrans();
		txn.setId(txnId);
		txn.setNote(txnNote);
		txn.setRefId(txnRefId);
		txn.setRefUrl(txnRefUrl);
		txn.setTs(ts);
		txn.setType(PayConstant.fromValue(txnType));
		return txn;
	}

	private AccountType setAccountTypeDetails(final String payerIfsc, final String payerAcType, final String payerAcNum)
			throws Exception {
		AccountType ac = new AccountType();
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
		ac.setAddrType(AddressType.ACCOUNT);
		return ac;
	}

	private PayerType setPayerTypeDetails(final String payerAddress, final String payerName, final String payerSeqNum,
			final String payerType, final String payerCode) throws Exception {
		PayerType payer = new PayerType();
		payer.setAddr(payerAddress);
		payer.setName(payerName);
		payer.setSeqNum(payerSeqNum);
		payer.setType(PayerConstant.fromValue(payerType));
		payer.setCode(payerCode);
		return payer;
	}

	private DeviceType setDeviceType(final String payerDeviceMobile, final String payerDeviceGeoCode,
			final String payerDeviceLocation, final String payerDeviceIp, final String payerDeviceType,
			final String payerDeviceId, final String payerDeviceOsType, final String payerDeviceAppId,
			final String payerDeviceCapability,final String payerDeviceTelecom) throws Exception {
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
				if (payerDeviceTelecom != null && !payerDeviceTelecom.isEmpty()) {
					tag = new Tag();
					tag.setName(DeviceTagNameType.TELECOM);
					tag.setValue(payerDeviceTelecom);
					tags.add(tag);
				}
			}
		}
		return device;
	}
}
