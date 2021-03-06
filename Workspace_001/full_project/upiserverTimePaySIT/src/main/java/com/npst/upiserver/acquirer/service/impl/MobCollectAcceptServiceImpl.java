package com.npst.upiserver.acquirer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.MobCollectAcceptService;
import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.CustomerTxnsDao;
import com.npst.upiserver.dao.MobReqRespJsonIdDao;
import com.npst.upiserver.dao.MobUpiReqRespJsonIdDao;
import com.npst.upiserver.dao.ReqRespAuthDetailsDao;
import com.npst.upiserver.dao.ReqRespAuthDetailsPayeesDao;
import com.npst.upiserver.dto.CredJson;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.MobUpiReqRespJson;
import com.npst.upiserver.entity.ReqRespAuthDetails;
import com.npst.upiserver.entity.ReqRespAuthDetailsPayees;
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
import com.npst.upiserver.npcischema.DeviceTagNameType;
import com.npst.upiserver.npcischema.DeviceType;
import com.npst.upiserver.npcischema.DeviceType.Tag;
import com.npst.upiserver.service.NpciUpiRestConService;
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
import com.npst.upiserver.npcischema.RespAuthDetails;
import com.npst.upiserver.npcischema.RespType;
import com.npst.upiserver.npcischema.WhiteListedConstant;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.JsonMan;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.Util;

@Service
public class MobCollectAcceptServiceImpl implements MobCollectAcceptService {
	private static final Logger	log	= LoggerFactory.getLogger(MobCollectAcceptServiceImpl.class);
	
	@Autowired
	ReqRespAuthDetailsDao		reqRespAuthDetailsDao;
	@Autowired
	ReqRespAuthDetailsPayeesDao	reqRespAuthDetDao;
	@Autowired
	MobReqRespJsonIdDao			mobReqRespJsonIdDao;
	@Autowired
	NpciUpiRestConService		npciService;
	@Autowired
	MobUpiReqRespJsonIdDao		mobUpiReqRespJsonIdDao;
	@Autowired
	CustomerTxnsDao				custTxnDao;
	
	@Override
	public void procAndSendNpci(MobUpiReqRespJson mobUpiReqRespJson) {
		try {
			ReqResp reqJson = JsonMan.getReqResp(mobUpiReqRespJson.getReq());
			ReqRespAuthDetails reqrespauthdetails = reqRespAuthDetailsDao.getOnTxnId(reqJson.getTxnId());
			log.info("Collect Accept get data from ReqRespAuthDetails {}",reqrespauthdetails);
			if (reqrespauthdetails != null) {
				String ts = Util.getTS();
				String txnId = reqJson.getTxnId();
				String msgId = Util.uuidGen();
				log.info("going to insert collect accept txnid {} and idpk {}",txnId,mobUpiReqRespJson.getIdPk());
				mobReqRespJsonIdDao.updateMsgId(txnId, mobUpiReqRespJson.getIdPk().longValue());
				RespAuthDetails respAuthDetails = new RespAuthDetails();
				respAuthDetails.setHead(setHeadTypeDetails(msgId, ts));
				respAuthDetails.setTxn(setPayTransDetails(txnId, reqrespauthdetails.getTxnNote()==null?"NA":reqrespauthdetails.getTxnNote(), reqJson.getTxnRefId(),
						reqJson.getTxnRefUrl(), ts, reqJson.getTxnType(), reqrespauthdetails.getTxnCustRef(),reqJson.getInitiationMode() == null || reqJson.getInitiationMode().isEmpty()
								? Constant.DEF_INITIATION_MODE
										: reqJson.getInitiationMode(),
								reqJson.getTxnPurpose() == null || reqJson.getTxnPurpose().isEmpty() ? Constant.DEFAULT_PURPOSE
										: reqJson.getTxnPurpose()));
				
				PayerType payer = setPayerTypeDetails(reqJson.getPayerAddr(), reqJson.getPayerCode(),
						reqJson.getPayerName(), reqJson.getPayerSeqNum(), reqJson.getPayerType(),
						reqJson.getPayerAddrType(), reqJson.getPayerAcNum(), reqJson.getPayerMobileNo(),
						reqJson.getPayerUidNum(), reqJson.getPayerDeviceMobile(), reqJson.getPayerDeviceGeoCode(),
						reqJson.getPayerDeviceLocation(), reqJson.getPayerDeviceIp(), reqJson.getPayerDeviceType(),
						reqJson.getPayerDeviceId(), reqJson.getPayerDeviceOsType(), reqJson.getPayerDeviceAppId(),
						reqJson.getPayerDeviceCapability(), reqJson.getPayerIfsc(), reqJson.getPayerAcType(),
						reqJson.getPayerMmid(), reqJson.getPayerIin(), reqJson.getPayerCardNum(),
						reqJson.getPayerAmount(), reqJson.getCredJsons());
				respAuthDetails.setPayer(payer);
				ReqRespAuthDetailsPayees dbPayee = reqRespAuthDetDao
						.getPayees(reqrespauthdetails.getIdReqRespAuthDetails().longValue());
				PayeesType payees = new PayeesType();
				List<PayeeType> payeeList = payees.getPayee();
				PayeeType payee = setPayeeTypeDetails(reqJson.getPayeeAddr(), reqJson.getPayeeName(),
						reqJson.getPayeeSeqNum(), reqJson.getPayeeType(), reqJson.getPayeeCode());
				payee.setAmount(setAmountTypeDetails(reqJson.getPayeeAmount()));
				payee.setInfo(setPayeeInfoType(dbPayee.getInfoIdType(), dbPayee.getInfoIdVerifiedName(),
						dbPayee.getInfoId()));
				
				//OC 76
				if(ConstantI.ENTITY.equalsIgnoreCase(dbPayee.getPayeeType())){
					payee.setMerchant(getMerchantType(dbPayee));
				}
				
				payee.setAc(setAccountTypeDetailsPayee(dbPayee.getAcAddrType(), dbPayee.getAcAddrTypeDetail1(),
						dbPayee.getAcAddrTypeDetail2(), dbPayee.getAcAddrTypeDetail3()));
				payeeList.add(payee);
				respAuthDetails.setPayees(payees);
				
				RespType resp = new RespType();
				resp.setReqMsgId(reqJson.getHeadReqMsgId());
				resp.setResult(ConstantI.SUCCESS);
				
				respAuthDetails.setResp(resp);
				String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(respAuthDetails).toString());
				log.info("before going to npci for collect accept respauth");
				Ack ack = npciService.send(xmlStr, ConstantI.RESP_AUTH_DETAILS, txnId);
				if (null != ack.getErr() || 0 != ack.getErrorMessages().size()) {
					try {
						mobUpiReqRespJsonIdDao.updateDb(ack, mobUpiReqRespJson,
								respAuthDetails.getTxn().getType().toString());
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
					reqRespAuthDetailsDao.updateResp(respAuthDetails, ack);
					custTxnDao.update(respAuthDetails, ack);
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
	
	private PayerType setPayerTypeDetails(final String payerAddr, final String payerCode, final String payerName,
			final String payerSeqNum, final String payerType, final String payerAddrType, final String payerAcNum,
			final String payerMobileNo, final String payerUidNum, final String payerDeviceMobile,
			final String payerDeviceGeoCode, final String payerDeviceLocation, final String payerDeviceIp,
			final String payerDeviceType, final String payerDeviceId, final String payerDeviceOsType,
			final String payerDeviceAppId, final String payerDeviceCapability, final String payerIfsc,
			final String payerAcType, final String payerMmid, final String payerIin, final String payerCardNum,
			final String payerAmount, final List<CredJson> credJsons) throws Exception {
		PayerType payer = null;
		try {
			payer = setPayerTypeDetails(payerAddr, payerCode, payerName, payerSeqNum, payerType);
			payer.setInfo(setInfoTypeDetails(payerAddrType, payerAcNum, payerMobileNo, payerUidNum, payerName));
			payer.setDevice(setDeviceTypeDetails(payerDeviceMobile, payerDeviceGeoCode, payerDeviceLocation,
					payerDeviceIp, payerDeviceType, payerDeviceId, payerDeviceOsType, payerDeviceAppId,
					payerDeviceCapability));
			AccountType ac = setAccountTypeDetails(payerAddrType, payerIfsc, payerAcType, payerAcNum, payerMmid,
					payerMobileNo, payerIin, payerUidNum, payerCardNum);
			payer.setAc(ac);
			payer.setCreds(setCredsTypeDetails(credJsons));
			payer.setAmount(setAmountTypeDetails(payerAmount));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return payer;
	}
	
	private AccountType setAccountTypeDetails(final String payerAddrType, final String payerIfsc,
			final String payerAcType, final String payerAcNum, final String payerMmid, final String payerMobileNo,
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
				detail.setValue(payerMobileNo);
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
	
	private AccountType setAccountTypeDetailsPayee(final String acAddrType, final String acAddrTypeDetail1,
			final String acAddrTypeDetail2, final String acAddrTypeDetail3) throws Exception {
		AccountType ac = new AccountType();
		try {
			ac.setAddrType(AddressType.fromValue(acAddrType));
			List<AccountType.Detail> payeeDetails = ac.getDetail();
			ArrayList<String> detailList1 = Util.strSplit(acAddrTypeDetail1, ConstantI.CHAR_CONST_PIPE);
			Detail detail = new Detail();
			detail.setName(
					AccountDetailType.fromValue(Util.strSplit(detailList1.get(0), ConstantI.CHAR_CONST_EQUAL).get(0)));
			detail.setValue(Util.strSplit(detailList1.get(0), ConstantI.CHAR_CONST_EQUAL).get(1));
			payeeDetails.add(detail);
			ArrayList<String> detailList2 = Util.strSplit(acAddrTypeDetail2, ConstantI.CHAR_CONST_PIPE);
			detail = new Detail();
			detail.setName(
					AccountDetailType.fromValue(Util.strSplit(detailList2.get(0), ConstantI.CHAR_CONST_EQUAL).get(0)));
			detail.setValue(Util.strSplit(detailList2.get(0), ConstantI.CHAR_CONST_EQUAL).get(1));
			payeeDetails.add(detail);
			if (null != acAddrTypeDetail3) {
				ArrayList<String> detailList3 = Util.strSplit(acAddrTypeDetail3, ConstantI.CHAR_CONST_PIPE);
				detail = new Detail();
				detail.setName(AccountDetailType
						.fromValue(Util.strSplit(detailList3.get(0), ConstantI.CHAR_CONST_EQUAL).get(0)));
				detail.setValue(Util.strSplit(detailList3.get(0), ConstantI.CHAR_CONST_EQUAL).get(1));
				payeeDetails.add(detail);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ac;
	}
	
	private CredsType setCredsTypeDetails(final List<CredJson> credsJsons) throws Exception {
		CredsType creds = new CredsType();
		try {
			List<CredJson> list = credsJsons;
			for (CredJson object : list) {
				Cred cred = new Cred();
				CredsType.Cred.Data data = new CredsType.Cred.Data();
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
	
	private AmountType setAmountTypeDetails(final String amount) {
		AmountType payerAmount = new AmountType();
		try {
			payerAmount.setValue(amount);
			payerAmount.setCurr(ConstantI.INR);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return payerAmount;
	}
	
	private InfoType setInfoTypeDetails(final String payerAddrType, final String payerAcNum, final String payerMobileNo,
			final String payerUidNum, final String payerName) throws Exception {
		InfoType info = new InfoType();
		try {
			IdentityType identity = new IdentityType();
			RatingType rating = new RatingType();
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
			rating.setVerifiedAddress(WhiteListedConstant.TRUE);
			info.setRating(rating);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return info;
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
	
	private InfoType setPayeeInfoType(final String infoIdType, final String infoIdVerifiedName,
			final String infoIdRatingvaddr) throws Exception {
		InfoType info = new InfoType();
		try {
			IdentityType identity = new IdentityType();
			identity.setType(IdentityConstant.fromValue(infoIdType));
			identity.setVerifiedName(infoIdVerifiedName);
			identity.setId(infoIdRatingvaddr);
			info.setIdentity(identity);
			RatingType rating = new RatingType();
			rating.setVerifiedAddress(WhiteListedConstant.TRUE);
			info.setRating(rating);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return info;
	}
	
	private PayeeType setPayeeTypeDetails(final String payeeAddr, final String payeeName, final String payeeSeqNum,
			final String payeeType, String payeeCode) throws Exception {
		PayeeType payee = new PayeeType();
		try {
			payee.setAddr(payeeAddr);
			payee.setName(payeeName);
			payee.setSeqNum(payeeSeqNum);
			payee.setType(PayerConstant.fromValue(payeeType));//  ////"ENTITY"
			payee.setCode(payeeCode);//payeeCode //"7299"
		
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return payee;
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
			final String txnRefUrl, final String ts, final String txnType, final String txnCustRef,String initiationMode,
			String purpose) throws Exception {
		PayTrans txn = new PayTrans();
		try {
			txn.setId(txnId);
			txn.setNote(txnNote);
			txn.setRefId(txnRefId);
			txn.setRefUrl(txnRefUrl);
			txn.setTs(ts);
			txn.setType(PayConstant.fromValue(txnType));
			txn.setCustRef(txnCustRef);
			txn.setInitiationMode(initiationMode); // To do Verification
			txn.setPurpose(purpose);  // To do Verification
		//	txn.setInitiationMode(Constant.DEF_INITIATION_MODE); // To do Verification
		//	txn.setPurpose(Constant.DEFAULT_PURPOSE); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return txn;
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
	
	
	//OC 76 SET Merchant

		private MerchantType getMerchantType(ReqRespAuthDetailsPayees dbPayeeMerchant) {
			MerchantType merchant = new MerchantType();
			IdentifierType iden = new IdentifierType();
			iden.setMerchantGenre(MerchantGenreType.valueOf(dbPayeeMerchant.getMerchantGenreType()));
			iden.setMerchantType(MerchantIdentifierType.valueOf(dbPayeeMerchant.getMerchantType()));
			iden.setMid(String.valueOf(dbPayeeMerchant.getMid()));
			iden.setOnBoardingType(MerchantOnBoardingType.BANK);
			iden.setSid(dbPayeeMerchant.getSid());
			iden.setSubCode(dbPayeeMerchant.getPayeeCode());
			iden.setTid(dbPayeeMerchant.getTid());
			merchant.setIdentifier(iden);
			NameType nameType = new NameType();
			
			if(dbPayeeMerchant.getMerachantName().length() > ConstantI.NAME_Length) {
				nameType.setBrand(dbPayeeMerchant.getMerachantName().substring(0, 14));
				nameType.setLegal(dbPayeeMerchant.getMerachantName().substring(0, 14));
				nameType.setFranchise(dbPayeeMerchant.getMerachantName().substring(0, 14));
			}
			else {
				nameType.setBrand(dbPayeeMerchant.getMerachantName());
				nameType.setLegal(dbPayeeMerchant.getMerachantName());
				nameType.setFranchise(dbPayeeMerchant.getMerachantName());
				}
			merchant.setName(nameType);
			MerchantOwnership ownership = new MerchantOwnership();
			ownership.setType(OwnershipType.OTHERS);
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
