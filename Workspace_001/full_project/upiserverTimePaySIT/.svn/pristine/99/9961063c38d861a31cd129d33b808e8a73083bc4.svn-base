package com.npst.upiserver.acquirer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.npst.upiserver.acquirer.service.MobRespMandateAuth;
import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.constant.InitiationMode;
import com.npst.upiserver.constant.UpiApiName;
import com.npst.upiserver.dao.BlockedByCustomerDao;
import com.npst.upiserver.dao.CustomerAccountDao;
import com.npst.upiserver.dao.MandatesHistoryDao;
import com.npst.upiserver.dao.MobMandateReqRespJsonDao;
import com.npst.upiserver.dao.ReqRespAuthMandateDao;
import com.npst.upiserver.dao.ReqRespAuthMandatePayeesDao;
import com.npst.upiserver.dto.CredJson;
import com.npst.upiserver.dto.CustomerAccountDto;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.MobMandateReqRespJsonEntity;
import com.npst.upiserver.entity.ReqRespAuthMandateEntity;
import com.npst.upiserver.entity.ReqRespAuthMandatePayeesEntity;
import com.npst.upiserver.npcischema.AccountDetailType;
import com.npst.upiserver.npcischema.AccountType;
import com.npst.upiserver.npcischema.AccountType.Detail;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.AddressType;
import com.npst.upiserver.npcischema.AmtRuleType;
import com.npst.upiserver.npcischema.BlockFund;
import com.npst.upiserver.npcischema.CredSubType;
import com.npst.upiserver.npcischema.CredType;
import com.npst.upiserver.npcischema.CredsType;
import com.npst.upiserver.npcischema.CredsType.Cred;
import com.npst.upiserver.npcischema.HeadType;
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
import com.npst.upiserver.npcischema.RecurrenceType.Rule;
import com.npst.upiserver.npcischema.RespAuthMandate;
import com.npst.upiserver.npcischema.RespType;
import com.npst.upiserver.npcischema.ResultType;
import com.npst.upiserver.npcischema.RevokeableType;
import com.npst.upiserver.npcischema.ShareToPayee;
import com.npst.upiserver.npcischema.WhiteListedConstant;
import com.npst.upiserver.service.IdGeneratorService;
import com.npst.upiserver.service.NpciUpiRestConService;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.JsonMan;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.NpciSchemaUtil;
import com.npst.upiserver.util.Util;

@Component
public class MobRespMandateAuthImpl implements MobRespMandateAuth {
	private static final Logger log = LoggerFactory.getLogger(MobRespMandateAuthImpl.class);
	@Autowired
	private NpciUpiRestConService npciUpiRestConService;
	/*@Autowired
	private IdGeneratorService idGeneratorService;
*/
	@Autowired
	private BlockedByCustomerDao blockedByCustomerDao;
	@Autowired
	private CustomerAccountDao customerAccountDao;
	@Autowired
	private MobMandateReqRespJsonDao mobMandateReqRespJsonDao;

	@Autowired
	private ReqRespAuthMandateDao reqRespAuthMandateDao;

	@Autowired
	private ReqRespAuthMandatePayeesDao reqRespAuthMandatePayeesDao;

	@Autowired
	private MandatesHistoryDao mandatesHistoryDao;
	
	@Autowired
	private IdGeneratorService idGeneratorService;
	

	@Override
	public void procAndSendNpci(MobMandateReqRespJsonEntity mobMandateReqRespJson) {
		try {
			log.debug("mobMandateReqRespJson {}", mobMandateReqRespJson);
			ReqResp reqJson = JsonMan.getReqResp(mobMandateReqRespJson.getReq());
			ReqRespAuthMandateEntity reqRespAuthMandate = reqRespAuthMandateDao.getByTxnId(reqJson.getTxnId());
			//ReqRespAuthMandateEntity reqRespAuthMandate = reqRespAuthMandateDao.getByTxnId(reqJson.getTxnId());
			log.info("get record from reqRespAuthMandateDao {}",reqRespAuthMandate.toString());
			if (null != reqRespAuthMandate) {
				log.info("inside req for reqRespAuthMandate !=null");
				String ts = Util.getTS();
				String txnId = reqJson.getTxnId();
				String msgId = Util.uuidGen();
				mobMandateReqRespJsonDao.updateMsgId(txnId, mobMandateReqRespJson.getIdPk());
				RespAuthMandate respAuthMandate = new RespAuthMandate();
				log.info("after updateMsgId");
				/**
				 * head
				 */
				HeadType head = new HeadType();
				head.setMsgId(msgId);
				head.setOrgId(Constant.orgId);
				head.setTs(ts);
				head.setVer(Constant.headVer);
				respAuthMandate.setHead(head);
				/**
				 * txn
				 */
				PayTrans txn = new PayTrans();
				txn.setId(txnId);
				txn.setNote(reqJson.getTxnNote());
				txn.setRefId(reqRespAuthMandate.getTxnRefid());
				txn.setRefUrl(reqJson.getTxnRefUrl());
				txn.setTs(ts);
				txn.setType(PayConstant.CREATE);
				if("PAYEE".equalsIgnoreCase(reqJson.getTxnInitiatedBy())&&"UPDATE".equalsIgnoreCase(reqJson.getMandate().getMandateType()))//for UPDATE will add value from front end
				{
					log.info("request reached 1");
					txn.setOrgTxnId(Util.getProperty("ORGTXNIDUPDATE"));
					txn.setType(PayConstant.UPDATE);
				}
				
				//txn.setType(PayConstant.CREATE);
				log.info("request reached 2");
				txn.setCustRef(reqRespAuthMandate.getTxnCustRef());
				txn.setInitiatedBy(InitiatedByType.fromValue(reqJson.getTxnInitiatedBy()));
				log.info("request reached 3");
				txn.setInitiationMode("00");
		     	txn.setPurpose(Util.getProperty("PURPOSE"));
		     	log.info("request reached 4");
				respAuthMandate.setTxn(txn);
				/**
				 * payer
				 */
				PayerType payer = new PayerType();
				payer.setAddr(reqJson.getPayerAddr());
				payer.setName(reqJson.getPayerName());
				payer.setSeqNum(reqJson.getPayerSeqNum());
				log.info("request reached 5");
				payer.setType(PayerConstant.fromValue(reqJson.getPayerType()));
				log.info("request reached 6");
				payer.setCode(reqJson.getPayerCode());
				/**
				 * payer info
				 */
				InfoType info = new InfoType();
				IdentityType identity = new IdentityType();
				RatingType rating = new RatingType();
				/*if ("ACCOUNT".equalsIgnoreCase(reqJson.getPayerAddrType())) {
					log.info("request reached 7");
					identity.setId(reqJson.getPayerAcNum());
					identity.setType(IdentityConstant.ACCOUNT);
				} else if ("MOBILE".equalsIgnoreCase(reqJson.getPayerAddrType())) {
					log.info("request reached 8");
					identity.setId(reqJson.getPayerMobileNo());
					identity.setType(IdentityConstant.ACCOUNT);
				}*/
				 if ("MOBILE".equalsIgnoreCase(reqJson.getPayerAddrType())) {
					log.info("request reached 7");
					identity.setId(reqJson.getPayerMobileNo());
					identity.setType(IdentityConstant.ACCOUNT);
				}else  {
					log.info("request reached 8");
					identity.setId(reqJson.getPayerAcNum());
					identity.setType(IdentityConstant.ACCOUNT);
				}
				identity.setVerifiedName(reqJson.getPayerName());
				info.setIdentity(identity);
				log.info("request reached 9");
				rating.setVerifiedAddress(WhiteListedConstant.TRUE);
				info.setRating(rating);
				payer.setInfo(info);

				payer.setDevice(NpciSchemaUtil.getPayerDeviceType(reqJson));
				payer.setAc(NpciSchemaUtil.getPayerAccountType(reqJson));//test
				if ("CREATE".equalsIgnoreCase(reqJson.getTxnType())||"UPDATE".equalsIgnoreCase(reqJson.getTxnType())) {
					//payer.setAc(NpciSchemaUtil.getPayerAccountType(reqJson));
					/**
					 * payer creds
					 */
					CredsType creds = new CredsType();

					List<CredJson> list = reqJson.getCredJsons();
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
					payer.setCreds(creds);
				}
			
				respAuthMandate.setPayer(payer);
				log.info("ID of payee db {}",reqRespAuthMandate.getIdReqRespAuthMandate());
				ReqRespAuthMandatePayeesEntity dbPayee = reqRespAuthMandatePayeesDao
						.getByIdReqRespAuthMandate(reqRespAuthMandate.getIdReqRespAuthMandate());
				
				log.info("get payee db details{}",dbPayee.toString());

				/**
				 * payees
				 */
				PayeesType payees = new PayeesType();
				List<PayeeType> payeeList = payees.getPayee();
				PayeeType payee = new PayeeType();
				payee.setAddr(reqJson.getPayeeAddr());
				payee.setName(reqJson.getPayeeName());
				payee.setSeqNum(reqJson.getPayeeSeqNum());
				log.info("",reqJson.getPayeeType());
				payee.setType(PayerConstant.fromValue(reqJson.getPayeeType()));
				log.info("request reached 13");
				payee.setCode(reqJson.getPayeeCode());

				/**
				 * payees info
				 */
				info = new InfoType();
				identity = new IdentityType();
				identity.setType(IdentityConstant.fromValue(dbPayee.getInfoIdType()));
				identity.setVerifiedName(dbPayee.getInfoIdVerifiedName());
				identity.setId(dbPayee.getInfoId());
				info.setIdentity(identity);
				rating.setVerifiedAddress(WhiteListedConstant.TRUE);
				info.setRating(rating);
				log.info("request reached 14");
				payee.setInfo(info);
				/**
				 * payees account
				 */
				AccountType ac = new AccountType();
				ac.setAddrType(AddressType.fromValue(dbPayee.getAcAddrType()));
				List<AccountType.Detail> payeeDetails = ac.getDetail();
			
				/*ArrayList<String> detailList1 = Util.strSplit(dbPayee.getAcAddrTypeDetail1(), '|');
				Detail detail = null;
				if (!detailList1.isEmpty()) {
					detail = new Detail();
					detail.setName(AccountDetailType.IFSC);
					detail.setValue(Util.strSplit(detailList1.get(0), '=').get(1));
					payeeDetails.add(detail);
				}

				ArrayList<String> detailList2 = Util.strSplit(dbPayee.getAcAddrTypeDetail2(), '|');
				if (!detailList2.isEmpty()) {
					detail = new Detail();
					detail.setName(AccountDetailType.fromValue(Util.strSplit(detailList2.get(0), '=').get(0)));
					detail.setValue(Util.strSplit(detailList2.get(0), '=').get(1));
					payeeDetails.add(detail);
				}

				if (null != dbPayee.getAcAddrTypeDetail3()) {
					ArrayList<String> detailList3 = Util.strSplit(dbPayee.getAcAddrTypeDetail3(), '|');
					detail = new Detail();
					detail.setName(AccountDetailType.fromValue(Util.strSplit(detailList3.get(0), '=').get(0)));
					detail.setValue(Util.strSplit(detailList3.get(0), '=').get(1));
					payeeDetails.add(detail);
				}
*/
				
				log.info("Payee ******** Info IFSC {} : AccNo {} :",reqJson.getPayeeIfsc(),reqJson.getPayeeAcNum());
				
				Detail detail = new Detail();
				detail.setName(AccountDetailType.IFSC);
				log.info("request reached 15");
				detail.setValue(reqJson.getPayeeIfsc());
				log.info("IFSC: {}" ,reqJson.getPayeeIfsc());
				payeeDetails.add(detail);
				detail = new Detail();
				detail.setName(AccountDetailType.ACTYPE);
				detail.setValue(reqJson.getPayeeAcType());
				payeeDetails.add(detail);
				detail = new Detail();
				detail.setName(AccountDetailType.ACNUM);
				detail.setValue(reqJson.getPayeeAcNum());
				log.info("AC NO: {}" ,reqJson.getPayeeAcNum());

				payeeDetails.add(detail);
				
				payee.setAc(ac);

				/**
				 * Merchants tags
				 */
				if (PayerConstant.ENTITY.toString().equalsIgnoreCase(reqJson.getPayeeType())) {
					MerchantType merchantType = new MerchantType();
					IdentifierType identifierType = new IdentifierType();
					identifierType.setSubCode(dbPayee.getMerchantSubCode());
					identifierType.setMid(dbPayee.getMerchantMid());
					identifierType.setSid(dbPayee.getMerchantSid());
					identifierType.setTid(dbPayee.getMerchantTid());
					identifierType.setMerchantType(MerchantIdentifierType.fromValue(dbPayee.getMerchantType()));
					identifierType.setMerchantGenre(MerchantGenreType.fromValue(dbPayee.getMerchantGenre()));
					identifierType
							.setOnBoardingType(MerchantOnBoardingType.fromValue(dbPayee.getMerchantOnboardingType()));

					NameType nameType = new NameType();
					nameType.setBrand(dbPayee.getMerchantBrandName());
					nameType.setFranchise(dbPayee.getMerchantFranchiseName());
					nameType.setLegal(dbPayee.getMerchantLegalName());

					MerchantOwnership merchantOwnership = new MerchantOwnership();
					merchantOwnership.setType(OwnershipType.fromValue(dbPayee.getMerchantOwnershipType()));

					merchantType.setIdentifier(identifierType);
					merchantType.setName(nameType);
					merchantType.setOwnership(merchantOwnership);
					payee.setMerchant(merchantType);
				}
				/**
				 * end
				 */

				payeeList.add(payee);
				respAuthMandate.setPayees(payees);
				RespType resp = new RespType();
				resp.setReqMsgId(reqRespAuthMandate.getReqHeadMsgId());
				resp.setResult(ResultType.SUCCESS.toString());
				if ("CREATE".equalsIgnoreCase(reqJson.getTxnType())) {//ACCEPT
					resp.setResult(ResultType.SUCCESS.toString());
				} else if ("REJECT".equalsIgnoreCase(reqJson.getTxnType())) {
					resp.setResult(ResultType.FAILURE.toString());
					resp.setErrCode("ZA");//REJECT The MAndate
					//txn.setOrgRespCode("");
				} else if ("BLOCK".equalsIgnoreCase(reqJson.getTxnType())) {
					resp.setResult(ResultType.FAILURE.toString());
					resp.setErrCode("QQ");
					storeBlockListdata(reqJson);
					//txn.setOrgRespCode("");
				}/* else if ("DECLINE".equalsIgnoreCase(reqJson.getTxnType())) {
					resp.setResult(ResultType.FAILURE.toString());
					resp.setErrCode("DC");
					txn.setOrgRespCode("");
				}*/

				respAuthMandate.setResp(resp);

				/**
				 * Mandates
				 */
				MandateType mandateType = new MandateType();
				Amount mandateAmount = new Amount();
				mandateAmount.setRule(AmtRuleType.fromValue(reqRespAuthMandate.getMandateAmountrule()));
				mandateAmount.setValue(reqRespAuthMandate.getMandateAmountvalue());
				mandateType.setAmount(mandateAmount);
				mandateType.setName(reqRespAuthMandate.getMandateName());
				RecurrenceType recurrence = new RecurrenceType();
                	mandateType.setBlockFund(BlockFund.Y);
				recurrence.setPattern(RecurrencePatternType.fromValue("ONETIME"));
			/*	Rule recurrenceRule = new Rule();
				if (reqRespAuthMandate.getMandateRecurrenceRuletype() != null) {
					recurrenceRule
							.setType(RecurrenceRuleType.fromValue(reqRespAuthMandate.getMandateRecurrenceRuletype()));
				}

				recurrenceRule.setValue(reqRespAuthMandate.getMandateRecurrenceRuletype());
				recurrence.setRule(recurrenceRule);*/
				mandateType.setRecurrence(recurrence);
				/*if (reqRespAuthMandate.getMandateRevokeable() != null)
					mandateType.setRevokeable(RevokeableType.fromValue(reqRespAuthMandate.getMandateRevokeable()));
				if (reqRespAuthMandate.getMandateShareToPayee() != null)
					mandateType.setShareToPayee(ShareToPayee.fromValue(reqRespAuthMandate.getMandateShareToPayee()));*/
				mandateType.setRevokeable(RevokeableType.fromValue("Y"));
				mandateType.setTs(dbPayee.getMandateTs());
				mandateType.setTxnId(txnId);
				mandateType.setType(reqRespAuthMandate.getMandateType());
				if("UPDATE".equalsIgnoreCase(reqRespAuthMandate.getMandateType())) {
					mandateType.setUmn(reqJson.getMandate().getMandateUmn());//Remove

				}
				else {
					if("SUCCESS".equalsIgnoreCase(resp.getResult())) {
						mandateType.setUmn(idGeneratorService.getUmn());

				}
				//mandateType.setUmn(idGeneratorService.getUmn());//if else add
				
				Validity validity = new Validity();
				validity.setStart(reqRespAuthMandate.getMandateValidityStart());
				validity.setEnd(reqRespAuthMandate.getMandateValidityEnd());
				mandateType.setValidity(validity);
				
				
				if("REVOKE".equalsIgnoreCase(reqRespAuthMandate.getMandateType())) {
					mandateType.setBlockFund(BlockFund.N);
				}
				
				respAuthMandate.setMandate(mandateType);
                log.info("before sending to NPCI {}",respAuthMandate.toString());
				String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(respAuthMandate).toString());
				log.info("ReqXml is {}",xmlStr);
				Ack ack = null;
				if (StringUtils.isNotBlank(xmlStr)) {
					ack = npciUpiRestConService.send(xmlStr, UpiApiName.RESP_MANDATE_AUTH.getName(), txnId);
					if ((ack != null && null != ack.getErr()) || (ack != null && !ack.getErrorMessages().isEmpty())||("REJECT".equalsIgnoreCase(reqJson.getTxnType())
							|| ("BLOCK".equalsIgnoreCase(reqJson.getTxnType())))) {
						log.info("before going to update flag");
						updateDb(ack, mobMandateReqRespJson, respAuthMandate.getTxn().getType().toString());
						log.info("after going to update flag");
					}
				}
				log.info("before going to update reqauth 1");
				reqRespAuthMandateDao.updateResp(respAuthMandate, ack);
				if("REJECT".equalsIgnoreCase(reqJson.getTxnType())||"BLOCK".equalsIgnoreCase(reqJson.getTxnType())) { // New Method ####
					mandatesHistoryDao.update(respAuthMandate, ack,reqJson);
				}
				else {
					mandatesHistoryDao.update(respAuthMandate, ack);
				}
			} }else {
				updateDbFail(mobMandateReqRespJson);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error: {}", e);
			ErrorLog.sendError(e, MobRespMandateAuthImpl.class);
		}

	}

	private void updateDb(Ack ack, MobMandateReqRespJsonEntity mobMandateReqRespJson, String txnType) {
		try {
			ReqResp respJson = new ReqResp();
			respJson.setTxnType(txnType);
			if (null != ack.getErr() || (ack.getErrorMessages() != null && !ack.getErrorMessages().isEmpty())) {
				respJson.setUpiErrorCode(JsonMan.getJSONStr(ack));
				respJson.setMsg(Constant.ACK_ERROR);
				respJson.setMsgId(ConstantI.MSG_ID_FAILURE);
			} else {
				respJson.setMsg(ResultType.SUCCESS.toString());
				respJson.setMsgId(ConstantI.MSG_ID_SUCCESS);
			}
			mobMandateReqRespJson.setResp(JsonMan.getJSONStr(respJson));
			mobMandateReqRespJson.setRespDate(new java.util.Date());
			mobMandateReqRespJson.setFlag(ConstantI.STATUS_3);
			mobMandateReqRespJsonDao.finalDbUpdate(mobMandateReqRespJson);
		} catch (Exception e) {
			log.error("error in updateDb {}", e);
			ErrorLog.sendError(e, MobRespMandateAuthImpl.class);
		}

	}

	private void updateDbFail(MobMandateReqRespJsonEntity mobMandateReqRespJson) {
		final ReqResp respJson = new ReqResp();
		try {
			respJson.setMsgId(ConstantI.MSG_ID_FAILURE);
			respJson.setMsg(ConstantI.MSG_ID_FAILURE);
			mobMandateReqRespJson.setResp(JsonMan.getJSONStr(respJson));
			mobMandateReqRespJson.setFlag(ConstantI.STATUS_2);
			mobMandateReqRespJsonDao.finalDbUpdate(mobMandateReqRespJson);
		} catch (final Exception e) {
			log.error("Error : {}", e);
			ErrorLog.sendError(e, MobRespMandateAuthImpl.class);
		}

	}
	
	private void storeBlockListdata(ReqResp reqJson) {
		Long regId = 0l;
		String payerName = "";
		List<CustomerAccountDto> customeraccounts = customerAccountDao.getCustAccDtoForMobValAdd(reqJson.getPayerAddr());
		for (CustomerAccountDto customeraccount : customeraccounts) {
			payerName = customeraccount.getName();
			regId = customeraccount.getRegid();
			log.info("",regId);
			break;
		}
		reqJson.setPayerName(payerName);
		blockedByCustomerDao.insertPayeeAddr(reqJson.getPayeeAddr(), regId);
	}
}