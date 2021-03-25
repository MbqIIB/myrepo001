package com.npst.upiserver.acquirer.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.MobValAddService;
import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.CustomerAccountDao;
import com.npst.upiserver.dao.MobReqRespJsonIdDao;
import com.npst.upiserver.dao.MobUpiReqRespJsonIdDao;
import com.npst.upiserver.dao.ReqRespValAddDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.CustomerAccount;
import com.npst.upiserver.entity.MobUpiReqRespJson;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.DeviceTagNameType;
import com.npst.upiserver.npcischema.DeviceType;
import com.npst.upiserver.npcischema.HeadType;
import com.npst.upiserver.npcischema.IdentityConstant;
import com.npst.upiserver.npcischema.IdentityType;
import com.npst.upiserver.npcischema.InfoType;
import com.npst.upiserver.npcischema.PayConstant;
import com.npst.upiserver.npcischema.PayTrans;
import com.npst.upiserver.npcischema.PayeeType;
import com.npst.upiserver.npcischema.PayerConstant;
import com.npst.upiserver.npcischema.PayerType;
import com.npst.upiserver.npcischema.RatingType;
import com.npst.upiserver.npcischema.ReqValAdd;
import com.npst.upiserver.npcischema.WhiteListedConstant;
import com.npst.upiserver.npcischema.DeviceType.Tag;
import com.npst.upiserver.service.NpciUpiRestConService;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.JsonMan;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.NpciSchemaUtil;
import com.npst.upiserver.util.Util;

@Service
public class MobValAddServiceImpl implements MobValAddService {
	private static final Logger log = LoggerFactory.getLogger(MobValAddServiceImpl.class);

	@Autowired
	MobReqRespJsonIdDao mobReqRespJsonIdDao;
	@Autowired
	NpciUpiRestConService npciService;
	@Autowired
	MobUpiReqRespJsonIdDao mobUpiReqRespJsonIdDao;
	@Autowired
	CustomerAccountDao custAccDao;
	@Autowired
	ReqRespValAddDao reqRespValAdd;

	@Override
	public void procAndSendNpci(MobUpiReqRespJson mobUpiReqRespJson) {
		try {
			ReqResp reqJson = JsonMan.getReqResp(mobUpiReqRespJson.getReq());
			String ts = Util.getTS();
			String txnId = Util.uuidGen();
			String msgId = Util.uuidGen();
			try {
				mobReqRespJsonIdDao.updateMsgId(msgId, mobUpiReqRespJson.getIdPk().longValue());
			} catch (Exception e) {
				log.error("Not Found");
			}
			ReqValAdd reqValAdd = new ReqValAdd();
			reqValAdd.setHead(setHeadTypeDetails(msgId, ts));
			reqValAdd.setTxn(setPayTransDetails(txnId, reqJson.getTxnNote(), reqJson.getTxnRefId(),
					reqJson.getTxnRefUrl(), ts, reqJson.getTxnType()));
			PayerType payer = setPayerTypeDetails(reqJson.getPayerAddr(), reqJson.getPayerCode(),
					reqJson.getPayerName(), reqJson.getPayerSeqNum(), reqJson.getPayerType());
			List<CustomerAccount> listAcc = custAccDao.findActiveAccounts(reqJson.getPayerAddr(), ConstantI.CONST_RR);
			log.info("listAcc size {} ",listAcc);
			for (CustomerAccount listaccount : listAcc) {
				InfoType info = new InfoType();
				IdentityType identity = new IdentityType();
				RatingType rating = new RatingType();
				identity.setType(IdentityConstant.ACCOUNT);
				identity.setVerifiedName(listaccount.getName());
				identity.setId(listaccount.getAccrefnumber());
				info.setIdentity(identity);
				payer.setName(listaccount.getName());// TODO Testing Change no revert
				rating.setVerifiedAddress(WhiteListedConstant.TRUE);
				info.setRating(rating);
				payer.setInfo(info);
			}
			log.info("Payer details is set now ");
			/*payer.setDevice(setDeviceType(reqJson.getPayerDeviceMobile(), reqJson.getPayerDeviceGeoCode(),
					reqJson.getPayerDeviceLocation(), reqJson.getPayerDeviceIp(), reqJson.getPayerDeviceType(),
					reqJson.getPayerDeviceId(), reqJson.getPayerDeviceOsType(), reqJson.getPayerDeviceAppId(),
					reqJson.getPayerDeviceCapability(),
					reqJson.getPayerDeviceTelecom() != null && !reqJson.getPayerDeviceTelecom().isEmpty()
							? reqJson.getPayerDeviceTelecom()
							: null));*/
			payer.setDevice(NpciSchemaUtil.getPayerDeviceTypeHdd(reqJson));
			reqValAdd.setPayer(payer);
			PayeeType payee = new PayeeType();
			payee.setAddr(reqJson.getPayeeAddr());
            //payee.setAddr("COBdefghijklmnopqrstuvwxyz123456@cosmos");
			payee.setSeqNum(reqJson.getPayeeSeqNum());
			reqValAdd.setPayee(payee);
			log.info("Payee details is set now ");
			String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(reqValAdd).toString());
			log.info("This is reqval add string ",xmlStr);
		//	log.info("Val Add XML {} and validation {} ", xmlStr,StringUtils.isNotBlank(xmlStr));
			Ack ack = null;
			if(true) {
				log.info("Sending to NPCI");
				ack = npciService.send(xmlStr, ConstantI.API_REQ_VALADD, txnId);
				log.info("Received from NPCI",ack);
				log.info("Acknowledgement from NPCI {} ",ack);
				if (null != ack.getErr() || 0 != ack.getErrorMessages().size()) {
					try {

						mobUpiReqRespJsonIdDao.updateDb(ack, mobUpiReqRespJson, reqValAdd.getTxn().toString());
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
				}
			}
		/*	else {
				log.info("Blank String");
			}*/
			try {
				reqRespValAdd.insertReq(reqValAdd, ack);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private DeviceType setDeviceType(final String payerDeviceMobile, final String payerDeviceGeoCode,
			final String payerDeviceLocation, final String payerDeviceIp, final String payerDeviceType,
			final String payerDeviceId, final String payerDeviceOsType, final String payerDeviceAppId,
			final String payerDeviceCapability, final String payerDeviceTelecom) throws Exception {
		DeviceType device = new DeviceType();
		try {
			List<Tag> tags = device.getTag();
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
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return device;
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
}