package com.npst.upiserver.acquirer.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.npst.upiserver.acquirer.service.MobPayCollectService;
import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.CustomerAccountDao;
import com.npst.upiserver.dao.CustomerTxnsDao;
import com.npst.upiserver.dao.MobReqRespJsonIdDao;
import com.npst.upiserver.dao.MobUpiReqRespJsonIdDao;
import com.npst.upiserver.dao.ReqRespOtpDao;
import com.npst.upiserver.dao.ReqRespPayCollectDao;
import com.npst.upiserver.dto.CredJson;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.dto.ValAddrCustomerDto;
import com.npst.upiserver.entity.CustomerAccount;
import com.npst.upiserver.entity.MobUpiReqRespJson;
import com.npst.upiserver.npcischema.AccountDetailType;
import com.npst.upiserver.npcischema.AccountType;
import com.npst.upiserver.npcischema.AccountType.Detail;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.AddressType;
import com.npst.upiserver.npcischema.AmountType;
import com.npst.upiserver.npcischema.CredSubType;
import com.npst.upiserver.npcischema.CredType;
import com.npst.upiserver.npcischema.CredsType;
import com.npst.upiserver.npcischema.CredsType.Cred;
import com.npst.upiserver.npcischema.CredsType.Cred.Data;
import com.npst.upiserver.npcischema.DeviceTagNameType;
import com.npst.upiserver.npcischema.DeviceType;
import com.npst.upiserver.npcischema.DeviceType.Tag;
import com.npst.upiserver.npcischema.ExpireRuleConstant;
import com.npst.upiserver.npcischema.HeadType;
import com.npst.upiserver.npcischema.IdentifierType;
import com.npst.upiserver.npcischema.IdentityConstant;
import com.npst.upiserver.npcischema.IdentityType;
import com.npst.upiserver.npcischema.InfoType;
import com.npst.upiserver.npcischema.MerchantGenreType;
import com.npst.upiserver.npcischema.MerchantIdentifierType;
import com.npst.upiserver.npcischema.MerchantOnBoardingType;
import com.npst.upiserver.npcischema.MerchantOwnership;
import com.npst.upiserver.npcischema.MerchantType;
import com.npst.upiserver.npcischema.NameType;
import com.npst.upiserver.npcischema.OwnershipType;
import com.npst.upiserver.npcischema.PayConstant;
import com.npst.upiserver.npcischema.PayTrans;
import com.npst.upiserver.npcischema.PayeeType;
import com.npst.upiserver.npcischema.PayeesType;
import com.npst.upiserver.npcischema.PayerConstant;
import com.npst.upiserver.npcischema.PayerType;
import com.npst.upiserver.npcischema.RatingType;
import com.npst.upiserver.npcischema.ReqPay;
import com.npst.upiserver.npcischema.RulesType;
import com.npst.upiserver.npcischema.RulesType.Rule;
import com.npst.upiserver.npcischema.WhiteListedConstant;
import com.npst.upiserver.service.IdGeneratorService;
import com.npst.upiserver.service.NpciUpiRestConService;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.JsonMan;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.Util;

@Service
public class MobPayCollectServiceImpl implements MobPayCollectService {
	private static final Logger log = LoggerFactory.getLogger(MobPayCollectServiceImpl.class);

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
	
	@Autowired
	CustomerAccountDao		custAccDao;
	
	@Autowired
	ReqRespPayCollectDao reqRespPayCollectDao;
	
	@Autowired
	private IdGeneratorService idGeneratorService;
	
	@Autowired
	private CustomerAccountDao customerAccountDao;

	@Override
	public void procAndSendNpci(MobUpiReqRespJson mobUpiReqRespJson) {
		try {
			ReqResp reqJson = JsonMan.getReqResp(mobUpiReqRespJson.getReq());
			String ts = Util.getTS();
			String rrn = idGeneratorService.getRrn();
			String txnId = reqJson.getTxnId();
			String msgId = Util.uuidGen();
			ReqPay reqpay = new ReqPay();
			reqpay.setHead(setHeadTypeDetails(msgId, ts));
			PayTrans txn = setPayTransDetails(txnId, reqJson.getTxnNote(), reqJson.getTxnRefId(),
					reqJson.getTxnRefUrl(), ts, rrn, reqJson.getTxnType(),
					reqJson.getInitiationMode() == null || reqJson.getInitiationMode().isEmpty()
							? Constant.DEF_INITIATION_MODE
							: reqJson.getInitiationMode(),
					reqJson.getTxnPurpose() == null || reqJson.getTxnPurpose().isEmpty() ? Constant.DEFAULT_PURPOSE
							: reqJson.getTxnPurpose());
			//txn.setInitiationMode(reqJson.getInitiationMode()); // To do Verification//Constant.DEF_INITIATION_MODE
			//txn.setPurpose(reqJson.getTxnPurpose()); //Constant.DEFAULT_PURPOSE
			reqpay.setTxn(txn);

			Ack ack = null;
			if (ConstantI.PAY.equalsIgnoreCase(reqJson.getTxnType())) {
				mobReqRespJsonIdDao.updateMsgId(msgId, mobUpiReqRespJson.getIdPk().longValue());
				PayerType payer = setPayerTypeTxnTypePay(reqJson.getPayerAddr(), reqJson.getPayerName(),
						reqJson.getPayerSeqNum(), reqJson.getPayerType(), reqJson.getPayerCode(),
						reqJson.getPayerAmount(), reqJson.getPayerAddrType(), reqJson.getPayerAcNum(),
						reqJson.getPayerMobileNo(), reqJson.getPayerUidNum(), reqJson.getPayerDeviceMobile(),
						reqJson.getPayerDeviceGeoCode(), reqJson.getPayerDeviceLocation(), reqJson.getPayerDeviceIp(),
						reqJson.getPayerDeviceType(), reqJson.getPayerDeviceId(), reqJson.getPayerDeviceOsType(),
						reqJson.getPayerDeviceAppId(), reqJson.getPayerDeviceCapability(), reqJson.getPayerIfsc(),
						reqJson.getPayerAcType(), reqJson.getPayerMmid(), reqJson.getPayerMobileNo(),
						reqJson.getPayerIin(), reqJson.getPayerCardNum(), reqJson.getCredJsons());
				payer.setType(PayerConstant.PERSON);
				payer.setCode(ConstantI.CODE_0000);
				reqpay.setPayer(payer);
				PayeesType payees = new PayeesType();
				
				List<PayeeType> payeeList = payees.getPayee();
				payeeList.add(setPayeeTypeDetails(reqJson.getPayeeAddr(), reqJson.getPayeeName(), reqJson.getPayeeSeqNum(),
								reqJson.getPayeeType(), reqJson.getPayeeCode(),reqJson.getPayerAmount()));
				
				reqpay.setPayees(payees);
				String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(reqpay).toString());
				log.info("PAY Request XML " + xmlStr);
				ack = npciService.send(xmlStr, ConstantI.API_REQ_PAY, txnId);
				if (null != ack.getErr() || 0 != ack.getErrorMessages().size()) {
					try {
						mobUpiReqRespJsonIdDao.updateDb(reqpay, ack, mobUpiReqRespJson);
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
				}
			} else if (ConstantI.COLLECT.equalsIgnoreCase(reqJson.getTxnType())) {
				txn.setRules(setRulesTypeDetails(reqJson.getRuleExpireAfter(), reqJson.getRuleMinAmount()));
				PayeesType payees = new PayeesType();
				List<PayeeType> payeeList = payees.getPayee();
				PayeeType payee = setPayeesTypeTxnTypeCollect(reqJson.getPayeeAddr(), reqJson.getPayeeName(),
						reqJson.getPayeeSeqNum(), reqJson.getPayeeCode(), reqJson.getPayeeType(),
						reqJson.getPayeeAddrType(), reqJson.getPayeeAcNum(), reqJson.getPayeeMobileNo(),
						reqJson.getPayeeUidNum(), reqJson.getPayeeDeviceMobile(), reqJson.getPayeeDeviceGeoCode(),
						reqJson.getPayeeDeviceLocation(), reqJson.getPayeeDeviceIp(), reqJson.getPayeeDeviceType(),
						reqJson.getPayeeDeviceId(), reqJson.getPayeeDeviceOsType(), reqJson.getPayeeDeviceAppId(),
						reqJson.getPayeeDeviceCapability(), reqJson.getPayeeIfsc(), reqJson.getPayeeAcType(),
						reqJson.getPayeeMmid(), reqJson.getPayeeIin(), reqJson.getPayeeCardNum(),
						reqJson.getPayerAmount());
				log.info("Payee Details for ReqPay",payee.toString());
				payeeList.add(payee);
				reqpay.setPayer(
						setPayerTypeCollect(reqJson.getPayerAddr(), reqJson.getPayerName(), reqJson.getPayerSeqNum(),
								reqJson.getPayerType(), reqJson.getPayerCode(), reqJson.getPayerAmount()));
				log.info("Payer Details for ReqPay",reqpay.getPayer().toString());
				reqpay.setTxn(txn);
				reqpay.setPayees(payees);
				String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(reqpay).toString());
				log.info("PAY Collect XML " + xmlStr);
				ack = npciService.send(xmlStr, ConstantI.API_REQ_PAY, txnId);
				try {
					if("11".equals(txn.getInitiationMode()) || "13".equals(txn.getInitiationMode())) {
						mobReqRespJsonIdDao.updateMsgId(msgId, mobUpiReqRespJson.getIdPk().longValue());

						log.info("Collect for Mandate");
					}
					else {
						mobUpiReqRespJsonIdDao.updateDb(reqpay, ack, mobUpiReqRespJson);

					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
			try {
				reqRespPayCollectDao.insertReq(reqpay, ack);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			try {
				custTxnDao.insert(reqpay, ack);

			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private PayeeType setPayeesTypeTxnTypeCollect(final String payeeAddr, final String payeeName,
			final String payeeSeqNum, final String payeeCode, final String payeeType, final String payeeAddrType,
			final String payeeAcNum, final String payeeMobileNo, final String payeeUidNum,
			final String payeeDeviceMobile, final String payeeDeviceGeoCode, final String payeeDeviceLocation,
			final String payeeDeviceIp, final String payeeDeviceType, final String payeeDeviceId,
			final String payeeDeviceOsType, final String payeeDeviceAppId, final String payeeDeviceCapability,
			final String payeeIfsc, final String payeeAcType, final String payeeMmid, final String payeeIin,
			final String payeeCardNum, final String payerAmount) throws Exception {
		PayeeType payee = new PayeeType();
		try {
			payee = setPayeeTypeCollect(payeeAddr, payeeName, payeeSeqNum, payeeCode, payeeType);
			payee.setInfo(setInfoTypeCollect(payeeAddrType, payeeAcNum, payeeMobileNo, payeeUidNum, payeeName));
			payee.setDevice(setDeviceTypeCollect(payeeDeviceMobile, payeeDeviceGeoCode, payeeDeviceLocation,
					payeeDeviceIp, payeeDeviceType, payeeDeviceId, payeeDeviceOsType, payeeDeviceAppId,
					payeeDeviceCapability));
			payee.setAc(setAccountTypeCollectDetails(payeeAddrType, payeeIfsc, payeeAcType, payeeAcNum, payeeMmid,
					payeeMobileNo, payeeIin, payeeUidNum, payeeCardNum));
			payee.setAmount(setAmountTypeDetails(payerAmount));
		
					
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return payee;
	}

	private PayerType setPayerTypeTxnTypePay(final String payerAddr, final String payerName, final String payerSeqNum,
			final String payerType, final String payerCode, final String payerAmount, final String payerAddrType,
			final String payerAcNum, final String payerMobileNo, final String payerUidNum,
			final String payerDeviceMobile, final String payerDeviceGeoCode, final String payerDeviceLocation,
			final String payerDeviceIp, final String payerDeviceType, final String payerDeviceId,
			final String payerDeviceOsType, final String payerDeviceAppId, final String payerDeviceCapability,
			final String payerIfsc, final String payerAcType, final String payerMmid, final String payerMobNo,
			final String payerIin, final String payerCardNum, final List<CredJson> credJsonList) throws Exception {
		PayerType payer = new PayerType();
		try {
			payer = setPayerTypeDetails(payerAddr, payerName, payerSeqNum, payerType, payerCode);
			payer.setAmount(setAmountTypeDetails(payerAmount));
			payer.setInfo(setInfoTypeDetails(payerAddrType, payerAcNum, payerMobileNo, payerUidNum, payerName));
			payer.setDevice(setDeviceTypeDetails(payerDeviceMobile, payerDeviceGeoCode, payerDeviceLocation,
					payerDeviceIp, payerDeviceType, payerDeviceId, payerDeviceOsType, payerDeviceAppId,
					payerDeviceCapability.trim()));
		
			payer.setAc(setAccountTypeDetails(payerAddrType, payerIfsc, payerAcType, payerAcNum, payerMmid, payerMobNo,
					payerIin, payerUidNum, payerCardNum));
			payer.setAmount(setAmountTypeDetails(payerAmount));
			payer.setCreds(setCredsTypeDetails(credJsonList));
			//ValAddrCustomerDto dto = customerAccountDao.getValAddrCustomerDto(payer.getAddr());
			//payer.setMerchant(getMerchantType(dto));
			
			

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
			final String txnRefUrl, final String ts, final String rrn, String txnType, String initiationMode,
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
			txn.setPurpose(purpose);  // To do Verification
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return txn;
	}

	private InfoType setInfoTypeDetails(final String payerAddrType, final String payerAcNum, final String payerMobileNo,
			final String payerUidNum, final String payerName) throws Exception {
		InfoType info = new InfoType();
		try {
			IdentityType identity = new IdentityType();
			RatingType rating = new RatingType();
			rating.setVerifiedAddress(WhiteListedConstant.TRUE);
			info.setRating(rating);

			if (ConstantI.ACCOUNT.equalsIgnoreCase(payerAddrType)) {
				identity.setId(payerAcNum);
				identity.setType(IdentityConstant.ACCOUNT);
			} else if (ConstantI.MOBILE.equalsIgnoreCase(payerAddrType)) {
				identity.setId(payerMobileNo);
				identity.setType(IdentityConstant.ACCOUNT);
			} else if (ConstantI.AADHAAR.equalsIgnoreCase(payerAddrType)) {
				identity.setId(payerUidNum);
				identity.setType(IdentityConstant.AADHAAR);
			}
			identity.setVerifiedName(payerName);
			info.setIdentity(identity);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return info;
	}

	AmountType setAmountTypeDetails(final String payerAmount) throws Exception {
		AmountType amount = new AmountType();
		try {
			amount.setValue(payerAmount);
			amount.setCurr(ConstantI.INR);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return amount;
	}

	PayerType setPayerTypeDetails(final String payerAddr, final String payerName, final String payerSeqNum,
			final String payerType, final String payerCode) throws Exception {
		PayerType payer = new PayerType();
		try {
			payer.setAddr(payerAddr);
			payer.setName(payerName);
			payer.setSeqNum(payerSeqNum);
			payer.setType(PayerConstant.fromValue(payerType));//Util.getProperty("Payer_Type")
			payer.setCode(payerCode);//Util.getProperty("Payer_code")
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return payer;
	}

	private DeviceType setDeviceTypeDetails(final String payerDeviceMobile, final String payerDeviceGeoCode,
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
					tag.setValue(payerDeviceCapability.trim());
					tags.add(tag);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return device;
	}

	private AccountType setAccountTypeDetails(final String payerAddrType, final String payerIfsc,
			final String payerAcType, final String payerAcNum, final String payerMmid, final String payerMobNo,
			final String payerIin, final String payerUidNum, final String payerCardNum) throws Exception {
		AccountType ac = new AccountType();
		try {
			ac.setAddrType(AddressType.fromValue(payerAddrType));
			List<Detail> details = ac.getDetail();
			if (ConstantI.ACCOUNT.equalsIgnoreCase(payerAddrType)) {
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
				Detail detail = new Detail();
				detail.setName(AccountDetailType.MMID);
				detail.setValue(payerMmid);
				details.add(detail);
				detail = new Detail();
				detail.setName(AccountDetailType.MOBNUM);
				detail.setValue(payerMobNo);
				details.add(detail);
			} else if (ConstantI.AADHAAR.equalsIgnoreCase(payerAddrType)) {
				Detail detail = new Detail();
				detail.setName(AccountDetailType.IIN);
				detail.setValue(payerIin);
				details.add(detail);
				detail = new Detail();
				detail.setName(AccountDetailType.UIDNUM);
				detail.setValue(payerUidNum);
				details.add(detail);
			} else if (ConstantI.CARD.equalsIgnoreCase(payerAddrType)) {
				Detail detail = new Detail();
				detail.setName(AccountDetailType.ACTYPE);
				detail.setValue(payerAcType);
				details.add(detail);
				detail = new Detail();
				detail.setName(AccountDetailType.CARDNUM);
				detail.setValue(payerCardNum);
				details.add(detail);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ac;
	}

	private CredsType setCredsTypeDetails(List<CredJson> credJsonList) throws Exception {
		CredsType creds = new CredsType();
		try {
			List<CredJson> list = credJsonList;
			for (Object element : list) {
				Cred cred = new Cred();
				Data data = new Data();
				CredJson object = (CredJson) element;
				data.setCode(object.getData().getCode());
				data.setValue(object.getData().getEncryptedBase64String());
				data.setKi(object.getData().getKi());
				cred.setData(data);
				cred.setSubType(CredSubType.fromValue(object.getSubType()));
				cred.setType(CredType.fromValue(object.getType()));
				creds.getCred().add(cred);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return creds;
	}

	private PayeeType setPayeeTypeDetails(final String payeeAddr, final String payeeName, final String payeeSeqNum,
			final String payeeType, final String payeeCode, final String payerAmount) throws Exception {
		PayeeType payee = new PayeeType();
		try {
			payee.setAddr(payeeAddr);
			payee.setName(payeeName);
			payee.setSeqNum(payeeSeqNum);
			payee.setType(PayerConstant.fromValue(payeeType));////Util.getProperty("Payee_Type")
			payee.setCode(payeeCode);////Util.getProperty("Payee_code")
			payee.setAmount(setAmountTypeDetails(payerAmount));
			List<CustomerAccount> resultListaccount = custAccDao.findActiveAccounts(payeeAddr,ConstantI.RR);
			//From front end not getting value
			if (resultListaccount== null || 0 == resultListaccount.size()) {
				payee.setAddr(payeeAddr);
				payee.setName(payeeName);
				payee.setSeqNum(payeeSeqNum);
				payee.setType(PayerConstant.fromValue(payeeType));////Util.getProperty("Payee_Type")
				payee.setCode(payeeCode);////Util.getProperty("Payee_code")
				payee.setAmount(setAmountTypeDetails(payerAmount));
				// to do
				/*resp.setMaskName("Testing Har");
				resp.setCode("0000");
				resp.setIIN(Constant.BANK_IIN);
				resp.setIFSC("COSB0000000");
				resp.setType("PERSON");// PERSON/ENTITY
				resp.setAccType(ListedAccountType.SAVINGS);
				resp.setResult(ConstantI.SUCCESS);
				log.info("if case ");*/

			} else {
				for (CustomerAccount listaccount : resultListaccount) {
					if(ConstantI.ENTITY.equalsIgnoreCase(listaccount.getCusttype())) { //for dabble check
						payee.setType(PayerConstant.ENTITY);
						log.info("Cust code is {}",listaccount.getCustcode());
						payee.setCode(listaccount.getCustcode());
						payee.setMerchant(getMerchantType(listaccount));
					}
					else {
						payee.setType(PayerConstant.PERSON);
						payee.setCode(ConstantI.CODE_0000);
					}
				}}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return payee;
	}

	private RulesType setRulesTypeDetails(final String ruleExpireAfter, final String ruleMinAmount) throws Exception {
		RulesType rules = new RulesType();
		try {
			List<Rule> ruleList = rules.getRule();
			Rule rule = new Rule();
			rule.setName(ExpireRuleConstant.EXPIREAFTER);
			rule.setValue(ruleExpireAfter);
			ruleList.add(rule);
			rule = new Rule();

			if (null != ruleMinAmount) {
				rule.setName(ExpireRuleConstant.MINAMOUNT);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return rules;
	}

	private PayeeType setPayeeTypeCollect(final String payeeAddr, final String payeeName, final String payeeSeqNum,
			final String payeeCode, final String payeeType) throws Exception {
		PayeeType payee = new PayeeType();
		try {
			payee.setAddr(payeeAddr);
			payee.setName(payeeName);
			payee.setSeqNum(payeeSeqNum);
			payee.setCode(payeeCode);
			payee.setType(PayerConstant.fromValue(payeeType));
			//Entity
			/*payee.setCode("7200");
			payee.setType(PayerConstant.ENTITY);*/
			log.info("Request Payee Address:{}",payee.getAddr());
			ValAddrCustomerDto dto = customerAccountDao.getValAddrCustomerDto(payee.getAddr());//for dabble check
			log.info("Payee Details FROM DB for COLLECT:{}",dto.getCustType());
			if(ConstantI.ENTITY.equalsIgnoreCase(dto.getCustType())) { //for dabble check
				payee.setType(PayerConstant.ENTITY);
				payee.setCode(dto.getCustCode());
				payee.setMerchant(getMerchantType(dto));
			}
			else {
				payee.setType(PayerConstant.PERSON);
				payee.setCode(ConstantI.CODE_0000);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return payee;
	}

	private InfoType setInfoTypeCollect(final String payeeAddrType, final String payeeAcNum, final String payeeMobileNo,
			final String payeeUidNum, final String payeeName) throws Exception {
		InfoType info = new InfoType();
		try {
			IdentityType identity = new IdentityType();
			RatingType rating = new RatingType();
			rating.setVerifiedAddress(WhiteListedConstant.TRUE);
			info.setRating(rating);
			if (ConstantI.ACCOUNT.equalsIgnoreCase(payeeAddrType)) {
				identity.setId(payeeAcNum);
				identity.setType(IdentityConstant.ACCOUNT);
			} else if (ConstantI.MOBILE.equalsIgnoreCase(payeeAddrType)) {
				identity.setId(payeeMobileNo);
				identity.setType(IdentityConstant.ACCOUNT);
			} else if (ConstantI.AADHAAR.equalsIgnoreCase(payeeAddrType)) {
				identity.setId(payeeUidNum);
				identity.setType(IdentityConstant.AADHAAR);
			}
			identity.setVerifiedName(payeeName);
			info.setIdentity(identity);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return info;
	}

	private DeviceType setDeviceTypeCollect(final String payeeDeviceMobile, final String payeeDeviceGeoCode,
			final String payeeDeviceLocation, final String payeeDeviceIp, final String payeeDeviceType,
			final String payeeDeviceId, final String payeeDeviceOsType, final String payeeDeviceAppId,
			final String payeeDeviceCapability) throws Exception {
		DeviceType device = new DeviceType();
		try {
			List<Tag> tags = device.getTag();
			{
				{
					Tag tag = new Tag();
					tag.setName(DeviceTagNameType.MOBILE);
					tag.setValue(payeeDeviceMobile);
					tags.add(tag);
					tag = new Tag();
					tag.setName(DeviceTagNameType.GEOCODE);
					tag.setValue(payeeDeviceGeoCode);
					tags.add(tag);
					tag = new Tag();
					tag.setName(DeviceTagNameType.LOCATION);
					tag.setValue(payeeDeviceLocation);
					tags.add(tag);
					tag = new Tag();
					tag.setName(DeviceTagNameType.IP);
					tag.setValue(payeeDeviceIp);
					tags.add(tag);
					tag = new Tag();
					tag.setName(DeviceTagNameType.TYPE);
					tag.setValue(payeeDeviceType);
					tags.add(tag);
					tag = new Tag();
					tag.setName(DeviceTagNameType.ID);
					tag.setValue(payeeDeviceId);
					tags.add(tag);
					tag = new Tag();
					tag.setName(DeviceTagNameType.OS);
					tag.setValue(payeeDeviceOsType);
					tags.add(tag);
					tag = new Tag();
					tag.setName(DeviceTagNameType.APP);
					tag.setValue(payeeDeviceAppId);
					tags.add(tag);
					tag = new Tag();
					tag.setName(DeviceTagNameType.CAPABILITY);
					tag.setValue(payeeDeviceCapability.trim()
							);
					tags.add(tag);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return device;
	}

	private AccountType setAccountTypeCollectDetails(final String payeeAddrType, final String payeeIfsc,
			final String payeeAcType, final String payeeAcNum, final String payeeMmid, final String payeeMobileNo,
			final String payeeIin, final String payeeUidNum, final String payeeCardNum) throws Exception {
		AccountType ac = new AccountType();
		try {
			ac.setAddrType(AddressType.fromValue(payeeAddrType));
			List<Detail> details = ac.getDetail();
			if (ConstantI.ACCOUNT.equalsIgnoreCase(payeeAddrType)) {
				Detail detail = new Detail();
				detail.setName(AccountDetailType.IFSC);
				detail.setValue(payeeIfsc.toUpperCase());
				details.add(detail);
				detail = new Detail();
				detail.setName(AccountDetailType.ACTYPE);
				detail.setValue(payeeAcType);
				details.add(detail);
				detail = new Detail();
				detail.setName(AccountDetailType.ACNUM);
				detail.setValue(payeeAcNum);
				details.add(detail);
			} else if (ConstantI.MOBILE.equalsIgnoreCase(payeeAddrType)) {
				Detail detail = new Detail();
				detail.setName(AccountDetailType.MMID);
				detail.setValue(payeeMmid);
				details.add(detail);
				detail = new Detail();
				detail.setName(AccountDetailType.MOBNUM);
				detail.setValue(payeeMobileNo);
				details.add(detail);
			} else if (ConstantI.AADHAAR.equalsIgnoreCase(payeeAddrType)) {
				Detail detail = new Detail();
				detail.setName(AccountDetailType.IIN);
				detail.setValue(payeeIin);
				details.add(detail);
				detail = new Detail();
				detail.setName(AccountDetailType.UIDNUM);
				detail.setValue(payeeUidNum);
				details.add(detail);
			} else if (ConstantI.CARD.equalsIgnoreCase(payeeAddrType)) {
				Detail detail = new Detail();
				detail.setName(AccountDetailType.ACTYPE);
				detail.setValue(payeeAcType);
				details.add(detail);
				detail = new Detail();
				detail.setName(AccountDetailType.CARDNUM);
				detail.setValue(payeeCardNum);
				details.add(detail);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ac;
	}

	private PayerType setPayerTypeCollect(final String payerAddr, final String payerName, final String payerSeqNum,
			final String payerType, final String payerCode, final String payerAmount) throws Exception {
		PayerType payer = new PayerType();
		try {
			payer.setAddr(payerAddr);
			payer.setName(payerName);
			payer.setSeqNum(payerSeqNum);
			payer.setType(PayerConstant.fromValue(payerType));
			payer.setCode(payerCode);
			payer.setAmount(setAmountTypeDetails(payerAmount));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return payer;
	}
	
	
	private MerchantType getMerchantType(CustomerAccount dto) {
		MerchantType merchant = new MerchantType();
		IdentifierType iden = new IdentifierType();
		iden.setMerchantGenre(MerchantGenreType.OFFLINE);
		iden.setMerchantType(MerchantIdentifierType.SMALL);
		iden.setMid(String.valueOf(dto.getRegid()));
		iden.setOnBoardingType(MerchantOnBoardingType.BANK);
		iden.setSid(ConstantI.MID_CONT);
		iden.setSubCode(dto.getCustcode());
		iden.setTid(ConstantI.MID_CONT);
		merchant.setIdentifier(iden);
		NameType nameType = new NameType();
		
		if(dto.getName().length() > ConstantI.NAME_Length) {
			nameType.setBrand(dto.getName().substring(0, 14));
			nameType.setLegal(dto.getName().substring(0, 14));
			nameType.setFranchise(dto.getName().substring(0, 14));
		}
		else {
			nameType.setBrand(dto.getName());
			nameType.setLegal(dto.getName());
			nameType.setFranchise(dto.getName());
			}
		merchant.setName(nameType);
		MerchantOwnership ownership = new MerchantOwnership();
		ownership.setType(OwnershipType.PRIVATE);
		merchant.setOwnership(ownership);
		return merchant;
	}
	
	private MerchantType getMerchantType(ValAddrCustomerDto dto) {
		MerchantType merchant = new MerchantType();
		IdentifierType iden = new IdentifierType();
		iden.setMerchantGenre(MerchantGenreType.OFFLINE);
		iden.setMerchantType(MerchantIdentifierType.SMALL);
		iden.setMid(String.valueOf(dto.getRegId()));
		iden.setOnBoardingType(MerchantOnBoardingType.BANK);
		iden.setSid(ConstantI.MID_CONT);
		iden.setSubCode(dto.getCustCode());
		iden.setTid(ConstantI.MID_CONT);
		merchant.setIdentifier(iden);
		NameType nameType = new NameType();
		
		if(dto.getName().length() > ConstantI.NAME_Length) {
			nameType.setBrand(dto.getName().substring(0, 14));
			nameType.setLegal(dto.getName().substring(0, 14));
			nameType.setFranchise(dto.getName().substring(0, 14));
		}
		else {
			nameType.setBrand(dto.getName());
			nameType.setLegal(dto.getName());
			nameType.setFranchise(dto.getName());
			}
		merchant.setName(nameType);
		MerchantOwnership ownership = new MerchantOwnership();
		ownership.setType(OwnershipType.PRIVATE);
		merchant.setOwnership(ownership);
		return merchant;
	}
	
	
private static MerchantType setMerchant() {
		
		
		MerchantType merchant=new MerchantType(); 
		IdentifierType iden=new IdentifierType();
		iden.setMerchantGenre(MerchantGenreType.OFFLINE);
		iden.setMerchantType(MerchantIdentifierType.SMALL);
		iden.setMid("6889951");
		iden.setOnBoardingType(MerchantOnBoardingType.AGGREGATOR);
		iden.setSid("1324561");
		iden.setSubCode("7299");
		iden.setTid("56789");
		merchant.setIdentifier(iden);
		NameType nameType=new NameType();
		nameType.setBrand("nps");
		nameType.setLegal("nps ltd.");
		nameType.setFranchise("zz");
		merchant.setName(nameType);
		MerchantOwnership ownership=new MerchantOwnership();
		ownership.setType(OwnershipType.PRIVATE);
		merchant.setOwnership(ownership);
		return merchant;
	}
}
