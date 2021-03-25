package com.npst.upiserver.acquirer.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.MobSetCreService;
import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.CustomerTxnsDao;
import com.npst.upiserver.dao.MobReqRespJsonIdDao;
import com.npst.upiserver.dao.MobUpiReqRespJsonIdDao;
import com.npst.upiserver.dao.ReqRespSetCreDao;
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
import com.npst.upiserver.npcischema.PayConstant;
import com.npst.upiserver.npcischema.PayTrans;
import com.npst.upiserver.npcischema.PayerConstant;
import com.npst.upiserver.npcischema.PayerType;
import com.npst.upiserver.npcischema.ReqSetCre;
import com.npst.upiserver.service.NpciUpiRestConService;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.JsonMan;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.NpciSchemaUtil;
import com.npst.upiserver.util.Util;

@Service
public class MobSetCreServiceImpl implements MobSetCreService {
	private static final Logger log = LoggerFactory.getLogger(MobSetCreServiceImpl.class);

	@Autowired
	MobReqRespJsonIdDao mobReqRespJsonIdDao;
	@Autowired
	NpciUpiRestConService npciService;
	@Autowired
	MobUpiReqRespJsonIdDao mobUpiReqRespJsonIdDao;
	@Autowired
	ReqRespSetCreDao reqRespSetCreDao;
	@Autowired
	CustomerTxnsDao custTxnDao;

	@Override
	public void procAndSendNpci(MobUpiReqRespJson mobUpiReqRespJson) {
		try {
			ReqResp reqJson = JsonMan.getReqResp(mobUpiReqRespJson.getReq());
			log.trace("");
			String ts = Util.getTS();
			String txnId = reqJson.getTxnId();
			String msgId = Util.uuidGen();
			mobReqRespJsonIdDao.updateMsgId(msgId, mobUpiReqRespJson.getIdPk().longValue());
			ReqSetCre reqSetCre = new ReqSetCre();
			reqSetCre.setHead(setHeadTypeDetails(msgId, ts));
			reqSetCre.setTxn(setPayTransDetails(txnId, reqJson.getTxnNote(), reqJson.getTxnRefId(),
					reqJson.getTxnRefUrl(), ts, reqJson.getTxnType()));
			PayerType payer = setPayerTypeDetails(reqJson.getPayerAddr(), reqJson.getPayerCode(),
					reqJson.getPayerName(), reqJson.getPayerSeqNum(), reqJson.getPayerType());
			payer.setAc(setAccountTypeDetails(reqJson.getPayerAddrType(), reqJson.getPayerIfsc(),
					reqJson.getPayerAcType(), reqJson.getPayerAcNum()));
			/*payer.setDevice(setDeviceTypeDetails(reqJson.getPayerDeviceMobile(), reqJson.getPayerDeviceGeoCode(),
					reqJson.getPayerDeviceLocation(), reqJson.getPayerDeviceIp(), reqJson.getPayerDeviceType(),
					reqJson.getPayerDeviceId(), reqJson.getPayerDeviceOsType(), reqJson.getPayerDeviceAppId(),
					reqJson.getPayerDeviceCapability().trim(),
					reqJson.getPayerDeviceTelecom() != null && !reqJson.getPayerDeviceTelecom().isEmpty()
					? reqJson.getPayerDeviceTelecom()
					: null));*/
			payer.setDevice(NpciSchemaUtil.getPayerDeviceType(reqJson));
			CredsType creds = new CredsType();
			CredsType newCreds = new CredsType();
			List<Cred> newCredList = newCreds.getCred();
			List<Cred> credList = creds.getCred();
			List<CredJson> list = reqJson.getCredJsons();
			for (CredJson object : list) {
				if (ConstantI.MPIN.equalsIgnoreCase(object.getSubType())) {
					Cred cred = new Cred();
					Data data = new Data();
					data.setCode(object.getData().getCode());
					data.setValue(object.getData().getEncryptedBase64String());
					data.setKi(object.getData().getKi());
					cred.setData(data);
					cred.setSubType(CredSubType.fromValue(object.getSubType()));
					cred.setType(CredType.fromValue(object.getType()));
					credList.add(cred);
				} else if (ConstantI.NMPIN.equalsIgnoreCase(object.getSubType())) {
					Cred newCred = new Cred();
					Data newData = new Data();
					newData.setCode(object.getData().getCode());
					newData.setValue(object.getData().getEncryptedBase64String());
					newData.setKi(object.getData().getKi());
					newCred.setData(newData);
					newCred.setSubType(CredSubType.MPIN);
					newCred.setType(CredType.fromValue(object.getType()));
					newCredList.add(newCred);
				}
			}
			payer.setCreds(creds);
			payer.setNewCred(newCreds);
			reqSetCre.setPayer(payer);
			String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(reqSetCre).toString());
			log.info("xmlStr is as {}", xmlStr);
			Ack ack = npciService.send(xmlStr, ConstantI.API_REQ_SET_CRE, txnId);
			if (null != ack.getErr() || 0 != ack.getErrorMessages().size()) {
				try {
					mobUpiReqRespJsonIdDao.updateDb(ack, mobUpiReqRespJson, reqSetCre.getTxn().getType().toString());
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
			try {
				reqRespSetCreDao.insertReq(reqSetCre, ack);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			try {
				custTxnDao.insert(reqSetCre, ack);

			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private PayerType setPayerTypeDetails(final String payerAddr, final String payerCode, final String payerName,
			final String payerSeqNum, final String payerType) throws Exception {
		PayerType payer = new PayerType();
		try {
			payer.setAddr(payerAddr);
			payer.setCode(payerCode);
			payer.setName(payerName);
			payer.setSeqNum(payerSeqNum);
			payer.setType(PayerConstant.fromValue(payerType));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return payer;
	}

	private AccountType setAccountTypeDetails(final String payerAddrType, final String payerIfsc,
			final String payerAcType, final String payerAcNum) throws Exception {
		AccountType ac = new AccountType();
		try {
			ac.setAddrType(AddressType.fromValue(payerAddrType));
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
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ac;
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
			final String txnRefUrl, final String ts, final String txnType) throws Exception {
		PayTrans txn = new PayTrans();
		try {
			txn.setId(txnId);
			txn.setNote(txnNote);
			txn.setRefId(txnRefId);
			txn.setRefUrl(txnRefUrl);
			txn.setTs(ts);
			txn.setType(PayConstant.fromValue(txnType));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return txn;
	}

	private DeviceType setDeviceTypeDetails(final String payerDeviceMobile, final String payerDeviceGeoCode,
			final String payerDeviceLocation, final String payerDeviceIp, final String payerDeviceType,
			final String payerDeviceId, final String payerDeviceOsType, final String payerDeviceAppId,
			final String payerDeviceCapability,final String payerDeviceTelecom) throws Exception {
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
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return device;
	}
}