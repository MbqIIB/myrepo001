package com.npst.upiserver.acquirer.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.npst.upiserver.acquirer.service.MobReqMandateService;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.constant.UpiApiName;
import com.npst.upiserver.dao.MandateTxnsDao;
import com.npst.upiserver.dao.MandatesDao;
import com.npst.upiserver.dao.MobMandateReqRespJsonDao;
import com.npst.upiserver.dto.CredJson;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.CustomerAccount;
import com.npst.upiserver.entity.MobMandateReqRespJsonEntity;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.AmountSplitConstant;
import com.npst.upiserver.npcischema.AmountType;
import com.npst.upiserver.npcischema.AmtRuleType;
import com.npst.upiserver.npcischema.BlockFund;
import com.npst.upiserver.npcischema.CredSubType;
import com.npst.upiserver.npcischema.CredType;
import com.npst.upiserver.npcischema.CredsType;
import com.npst.upiserver.npcischema.CredsType.Cred;
import com.npst.upiserver.npcischema.CredsType.Cred.Data;
import com.npst.upiserver.npcischema.ExpireRuleConstant;
import com.npst.upiserver.npcischema.IdentifierType;
import com.npst.upiserver.npcischema.IdentityConstant;
import com.npst.upiserver.npcischema.IdentityType;
import com.npst.upiserver.npcischema.InfoType;
import com.npst.upiserver.npcischema.InitiatedByType;
import com.npst.upiserver.npcischema.MandateType;
import com.npst.upiserver.npcischema.MandateType.Amount;
import com.npst.upiserver.npcischema.MandateType.Validity;
import com.npst.upiserver.npcischema.MerchantGenreType;
import com.npst.upiserver.npcischema.MerchantIdentifierType;
import com.npst.upiserver.npcischema.MerchantOnBoardingType;
import com.npst.upiserver.npcischema.MerchantOwnership;
import com.npst.upiserver.npcischema.MerchantType;
import com.npst.upiserver.npcischema.MetaTagNameType;
import com.npst.upiserver.npcischema.NameType;
import com.npst.upiserver.npcischema.OwnershipType;
import com.npst.upiserver.npcischema.PayConstant;
import com.npst.upiserver.npcischema.PayTrans;
import com.npst.upiserver.npcischema.PayeeType;
import com.npst.upiserver.npcischema.PayeesType;
import com.npst.upiserver.npcischema.PayerConstant;
import com.npst.upiserver.npcischema.PayerType;
import com.npst.upiserver.npcischema.RatingType;
import com.npst.upiserver.npcischema.RecurrencePatternType;
import com.npst.upiserver.npcischema.RecurrenceRuleType;
import com.npst.upiserver.npcischema.RecurrenceType;

import com.npst.upiserver.npcischema.ReqMandate;
import com.npst.upiserver.npcischema.RevokeableType;
import com.npst.upiserver.npcischema.RulesType;
import com.npst.upiserver.npcischema.RulesType.Rule;
import com.npst.upiserver.npcischema.ShareToPayee;
import com.npst.upiserver.npcischema.WhiteListedConstant;
import com.npst.upiserver.repo.CustomerAccountRepository;
import com.npst.upiserver.service.IdGeneratorService;
import com.npst.upiserver.service.NpciUpiRestConService;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.JsonMan;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.NpciSchemaUtil;
import com.npst.upiserver.util.Util;

@Component
public class MobReqMandateServiceImpl implements MobReqMandateService {
	private static final Logger log = LoggerFactory.getLogger(MobReqMandateServiceImpl.class);
	private final static String endTsFormat = "%s-%s-%sT23:59:59+05:30";

	@Autowired
	private NpciUpiRestConService npciUpiRestConService;
	/*@Autowired
	private IdGeneratorService idGeneratorService;*/

	@Autowired
	private MobMandateReqRespJsonDao mobMandateReqRespJsonDao;

	@Autowired
	private MandatesDao mandatesDao;
	@Autowired
	private MandateTxnsDao mandateTxnsDao;
	
	@Autowired
	private IdGeneratorService idGeneratorService;
	
	@Autowired
	private CustomerAccountRepository customerAccountRepo;
	

	@Override
	public void procAndSendNpci(MobMandateReqRespJsonEntity mobMandateReqRespJson) {
		// TODO Auto-generated method stub
		log.info("mobMandateReqRespJson {}", mobMandateReqRespJson);

		try {
			ReqResp reqJson = JsonMan.getReqResp(mobMandateReqRespJson.getReq());

			String ts = Util.getTS();
			String rrn = idGeneratorService.getRrn();
			String txnId = reqJson.getTxnId();
			String msgId = Util.uuidGen();
			mobMandateReqRespJsonDao.updateMsgId(msgId, mobMandateReqRespJson.getIdPk());
			ReqMandate reqMandate = new ReqMandate();
			reqMandate.setHead(NpciSchemaUtil.getNewHeadType(msgId, ts));
			PayTrans txn = new PayTrans();
			txn.setId(txnId);
			txn.setNote(reqJson.getTxnNote());
			txn.setRefId(reqJson.getTxnRefId());
			//txn.setRefId("MANDATE12313");
			if("MAX".equalsIgnoreCase(reqJson.getMandate().getMandateAmountRule())) {
					txn.setRefId("MANDATE"+rrn.substring(0, 5));
			}
			txn.setRefUrl(reqJson.getTxnRefUrl());
			txn.setTs(ts);
			txn.setCustRef(rrn);
			txn.setType(PayConstant.fromValue(reqJson.getTxnType()));
			txn.setInitiationMode(reqJson.getInitiationMode());//will be 00 only
			txn.setInitiatedBy(InitiatedByType.fromValue(reqJson.getTxnInitiatedBy()));
			txn.setType(PayConstant.fromValue(reqJson.getTxnType()));// For UPDATE HDD
			log.info(reqJson.getTxnInitiatedBy());
			
			if("PAYEE".equalsIgnoreCase(reqJson.getTxnInitiatedBy())) {
				txn.setRules(setRules());

			}
			
			if (PayConstant.UPDATE.toString().equalsIgnoreCase(reqJson.getTxnType())||
					PayConstant.REVOKE.toString().equalsIgnoreCase(reqJson.getTxnType())) {
	            txn.setOrgTxnId(reqJson.getOrgTxnId());
			//	txn.setOrgTxnId(Util.getProperty("ORG_TXNID_REVOKE"));
			}
			//txn.setType(PayConstant.fromValue("UPDATE"));
			txn.setInitiationMode("00");//will be 00 only
			//txn.setInitiatedBy(InitiatedByType.fromValue(reqJson.getTxnInitiatedBy()));
			txn.setPurpose(Util.getProperty("PURPOSE"));//For ASBA "01" 03, 09 can happen in other case

			
			reqMandate.setTxn(txn);
			// TODO discussion on meta tag
			reqMandate.setMeta(getMetaType(ts, endTsFromMandateValEnd(reqJson.getMandate().getValidityEnd())));

			/*if(reqJson.getTxnType().equalsIgnoreCase("UPDATE")||reqJson.getTxnType().equalsIgnoreCase("REVOKE")) { // For UPDATE
				txn.setOrgTxnId(reqJson.getOrgTxnId());
			}*/
			PayerType payer = new PayerType();
			payer.setAddr(reqJson.getPayerAddr());//
			payer.setName(reqJson.getPayerName());
			payer.setSeqNum(reqJson.getPayerSeqNum());
			payer.setType(PayerConstant.fromValue(reqJson.getPayerType()));
			payer.setCode(reqJson.getPayerCode());

			PayeesType payees = new PayeesType();
			List<PayeeType> payeeList = payees.getPayee();
			PayeeType payee = new PayeeType();
			payee.setAddr(reqJson.getPayeeAddr());
			payee.setName(reqJson.getPayeeName());
			payee.setSeqNum("1");//reqJson.getPayeeSeqNum()
			payee.setCode("0000");//reqJson.getPayeeCode()
			payee.setType(PayerConstant.fromValue("PERSON"));
			payeeList.add(payee);

			/*AmountType amount = new AmountType();
			amount.setValue(reqJson.getMandate().getMandateAmountValue());
			amount.setCurr("INR");
			payer.setAmount(amount);*/ //AS PER NPCI it WILL NOT GO

			// If initiated by payer
			if (InitiatedByType.PAYER.toString().equalsIgnoreCase(reqJson.getTxnInitiatedBy())) {
				/**
				 * Info
				 */
				InfoType info = new InfoType();
				IdentityType identity = new IdentityType();
				RatingType rating = new RatingType();
				rating.setVerifiedAddress(WhiteListedConstant.TRUE);
				info.setRating(rating);
				if ("ACCOUNT".equalsIgnoreCase(reqJson.getPayerAddrType())) {
					identity.setId(reqJson.getPayerAcNum());
					identity.setType(IdentityConstant.ACCOUNT);
				} else if ("MOBILE".equalsIgnoreCase(reqJson.getPayerAddrType())) {
					identity.setId(reqJson.getPayerMobileNo());
					identity.setType(IdentityConstant.ACCOUNT);
				}

				identity.setVerifiedName(reqJson.getPayerName());
				info.setIdentity(identity);
				payer.setInfo(info);
				payer.setDevice(NpciSchemaUtil.getPayerDeviceType(reqJson));
				payer.setAc(NpciSchemaUtil.getPayerAccountType(reqJson));
				

				/**
				 * Creds
				 */
				CredsType creds = new CredsType();
				List<CredJson> list = reqJson.getCredJsons();

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
				payer.setCreds(creds);
			} 
			
			
			else if (InitiatedByType.PAYEE.toString().equalsIgnoreCase(reqJson.getTxnInitiatedBy())) {
				
				if(!PayConstant.CREATE.toString().equalsIgnoreCase(reqJson.getTxnType())) {
					String accountPayee=getPayeeAc(payee);
					reqJson.setPayeeAcNum(accountPayee);
				}
				InfoType info = new InfoType();
				IdentityType identity = new IdentityType();
				RatingType rating = new RatingType();
				rating.setVerifiedAddress(WhiteListedConstant.TRUE);
				info.setRating(rating);
				if ("ACCOUNT".equalsIgnoreCase(reqJson.getPayeeAddrType())) {
					identity.setId(reqJson.getPayeeAcNum());
					identity.setType(IdentityConstant.ACCOUNT);
				} else if ("MOBILE".equalsIgnoreCase(reqJson.getPayeeAddrType())) {
					identity.setId(reqJson.getPayeeMobileNo());
					identity.setType(IdentityConstant.ACCOUNT);
				}
				identity.setVerifiedName(reqJson.getPayeeName());
				info.setIdentity(identity);
				payee.setInfo(info);
				//payee.setDevice(NpciSchemaUtil.getPayeeDeviceType(reqJson));
				payee.setDevice(NpciSchemaUtil.getPayerDeviceTypetest());
				payee.setAc(NpciSchemaUtil.getPayeeAccountType(reqJson));

				/**
				 * Merchants tags
				 */
				if (PayerConstant.ENTITY.toString().equalsIgnoreCase(reqJson.getPayeeType())) {
					payee.setMerchant(getMerchantPayee(reqJson));
				}
				/**
				 * end
				 */
			//	payee.setAmount(amount);
			}

			reqMandate.setPayer(payer);
			reqMandate.setPayees(payees);
			/**
			 * Mandates related fields
			 */
			reqMandate.setMandate(getMandateType(reqJson, ts, txnId));
			Ack ack = null;
			String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(reqMandate).toString());
			if (StringUtils.isNotBlank(xmlStr)) {
				if(InitiatedByType.PAYEE.toString().equalsIgnoreCase(reqJson.getTxnInitiatedBy())&&!ConstantI.M_REVOKE.equalsIgnoreCase(reqJson.getTxnType())) {
					ack = npciUpiRestConService.send(xmlStr, UpiApiName.REQ_MANDATE.getName(), txnId);
					if (StringUtils.isNotBlank(xmlStr)) {
						mobMandateReqRespJsonDao.updateDb(reqMandate, ack, mobMandateReqRespJson);
						log.info("data UPDATED in MANDATEJSON");
				}
			}	
					else {
						ack = npciUpiRestConService.send(xmlStr, UpiApiName.REQ_MANDATE.getName(), txnId);
						if (ack != null && null != ack.getErr() || !ack.getErrorMessages().isEmpty()) {
							mobMandateReqRespJsonDao.updateDb(reqMandate, ack, mobMandateReqRespJson);
							log.info("data UPDATED in MANDATEJSON");
					}
				
				
				}
			}
		//	mandatesDao.insertReqMandate(reqMandate, ack);
			mandateTxnsDao.insertMandatesHistory(reqMandate,ack,reqJson);

		} catch (Exception e) {
			log.error("Error: {}", e);
			ErrorLog.sendError(e, MobReqMandateServiceImpl.class);
		}
	}
	private String getPayeeAc(PayeeType payee) {
		List<CustomerAccount> listaccounts = customerAccountRepo
				.findByAccvirtualid(payee.getAddr().trim().toLowerCase());
		
		for (CustomerAccount listaccount : listaccounts) {
			if (1 == listaccount.getDefaccount() && 1 == listaccount.getStatus()) {
				return 	listaccount.getAccrefnumber();
			}
				
		}
		
		return null;
	}
	private RulesType setRules() {
		RulesType  rules=new RulesType();
		List<Rule> ruleList=rules.getRule();
		Rule rule = new Rule();
		rule.setName(ExpireRuleConstant.EXPIREAFTER);
		rule.setValue("10");
		ruleList.add(rule);
		return rules;
	}
	private static MerchantType getMerchantPayee(final ReqResp reqJson) {
		MerchantType merchantType = new MerchantType();
		IdentifierType identifierType = new IdentifierType();
		identifierType.setSubCode(reqJson.getMerchantSubCode());
		identifierType.setMid(reqJson.getMerchantMid());
		identifierType.setSid(reqJson.getMerchantSid());
		identifierType.setTid(reqJson.getMerchantTid());
		identifierType.setMerchantType(MerchantIdentifierType.fromValue(reqJson.getMerchantType()));
		identifierType.setMerchantGenre(MerchantGenreType.fromValue(reqJson.getMerchantGenre()));
		identifierType.setOnBoardingType(MerchantOnBoardingType.fromValue(reqJson.getMerchantOnboardingType()));

		NameType nameType = new NameType();
		nameType.setBrand(reqJson.getMerchantBrandName());
		nameType.setFranchise(reqJson.getMerchantFranchiseName());
		nameType.setLegal(reqJson.getMerchantLegalName());

		MerchantOwnership merchantOwnership = new MerchantOwnership();
		merchantOwnership.setType(OwnershipType.fromValue(reqJson.getMerchantOwnershipType()));

		merchantType.setIdentifier(identifierType);
		merchantType.setName(nameType);
		merchantType.setOwnership(merchantOwnership);
		return merchantType;
	}

	private MandateType getMandateType(final ReqResp reqJson, String ts, String txnId) {
		MandateType mandateType = new MandateType();
		Amount mandateAmount = new Amount();
		mandateAmount.setRule(AmtRuleType.fromValue(reqJson.getMandate().getMandateAmountRule()));
		mandateAmount.setValue(reqJson.getMandate().getMandateAmountValue());
		mandateType.setAmount(mandateAmount);
		mandateType.setName(reqJson.getMandate().getMandateName());
		RecurrenceType recurrence = new RecurrenceType();
		recurrence.setPattern(RecurrencePatternType.fromValue("ONETIME"));////DAILY  reqJson.getMandate().getRecurrencePattern()
		mandateType.setRecurrence(recurrence);
		mandateType.setRevokeable(RevokeableType.fromValue(reqJson.getMandate().getMandateRevokeable()));//reqJson.getMandate().getMandateRevokeable()
		// ShareToPayee.fromValue(reqJson.getMandate().getMandateShareToPayee()).  if payer will initiate mandate time Y will come else N will be 
		mandateType.setTs(ts);
		mandateType.setTxnId(txnId);
		//mandateType.setType("UPDATE");// FORM UPDATE 
		mandateType.setType(reqJson.getTxnType());
		//mandateType.setType("UPDATE");
		if (InitiatedByType.PAYER.toString().equalsIgnoreCase(reqJson.getTxnInitiatedBy())) {
			if (PayConstant.CREATE.toString().equalsIgnoreCase(reqJson.getMandate().getMandateType())) {
				mandateType.setUmn(idGeneratorService.getUmn());
				//mandateType.setUmn(Util.getProperty("UMN"));
				//mandateType.setUmn(reqJson.getMandate().getMandateUmn()); //FOR BYPASS
				mandateType.setBlockFund(BlockFund.Y);
				mandateType.setShareToPayee(ShareToPayee.fromValue(reqJson.getMandate().getMandateShareToPayee()));//MAMTPR_7  mandateType.setShareToPayee(ShareToPayee.fromValue("Y"));
			}
			else {
				mandateType.setUmn(reqJson.getMandate().getMandateUmn());
				if (PayConstant.UPDATE.toString().equalsIgnoreCase(reqJson.getMandate().getMandateType())) {
					mandateType.setBlockFund(BlockFund.Y);
					mandateType.setShareToPayee(ShareToPayee.fromValue(reqJson.getMandate().getMandateShareToPayee()));
					
				}
				else {
					mandateType.setBlockFund(BlockFund.N);
					mandateType.setShareToPayee(ShareToPayee.fromValue(reqJson.getMandate().getMandateShareToPayee()));// UNCOMMENT latter. 
				}

			}
		}
		else {
			mandateType.setBlockFund(BlockFund.Y);
			if("REVOKE".equalsIgnoreCase(reqJson.getTxnType())) {
				mandateType.setBlockFund(BlockFund.N);
				//mandateType.setUmn(Util.getProperty("ORG_UMN"));// by pass for testing 
				
				mandateType.setUmn(reqJson.getMandate().getMandateUmn());
				
			}
			
			if("UPDATE".equalsIgnoreCase(reqJson.getTxnType())) {
				mandateType.setBlockFund(BlockFund.Y);
			//	mandateType.setUmn(Util.getProperty("ORG_UMN"));
			mandateType.setUmn(reqJson.getMandate().getMandateUmn());	
			}
			//mandateType.setShareToPayee(ShareToPayee.fromValue("N"));

		}
		Validity validity = new Validity();
		validity.setStart(reqJson.getMandate().getValidityStart());
		validity.setEnd(reqJson.getMandate().getValidityEnd());
		mandateType.setValidity(validity);
		/**
		 * If asba/ipo or one time mandate
		 */
		/*
		 * if ("ASBA".equalsIgnoreCase(reqJson.getMandate().getMandateType()) ||
		 * RecurrencePatternType.ONETIME.toString()
		 * .equalsIgnoreCase(reqJson.getMandate().getRecurrencePattern())) {
		 * recurrence.setRule(null); mandateType.setBlockFund(BlockFund.Y); }
		 */
		return mandateType;

	}

	private static ReqMandate.Meta getMetaType(String startTs, String endTs) {
		ReqMandate.Meta metaTag = new ReqMandate.Meta();
		List<ReqMandate.Meta.Tag> metaList = metaTag.getTag();
		ReqMandate.Meta.Tag mtag = new ReqMandate.Meta.Tag();
		mtag.setName(MetaTagNameType.PAYREQSTART);
		mtag.setValue(startTs);
		metaList.add(mtag);
		mtag = new ReqMandate.Meta.Tag();
		mtag.setName(MetaTagNameType.PAYREQEND);
		mtag.setValue(endTs);
		metaList.add(mtag);
		return metaTag;
	}

	private static String endTsFromMandateValEnd(String mandateValEnd) {
		return String.format(endTsFormat, mandateValEnd.substring(4, 8), mandateValEnd.substring(2, 4),
				mandateValEnd.substring(0, 2));
	}
}