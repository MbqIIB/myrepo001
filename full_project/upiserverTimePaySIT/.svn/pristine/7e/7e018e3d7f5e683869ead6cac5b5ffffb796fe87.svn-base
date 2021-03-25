package com.npst.upiserver.dao.impl;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.constant.MandateStatus;
import com.npst.upiserver.dao.CustomerAccountDao;
import com.npst.upiserver.dao.MandateTxnsDao;
import com.npst.upiserver.dao.RegistrationDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.CustomerMandatesEntity;
import com.npst.upiserver.entity.MandateTxnsEntity;
import com.npst.upiserver.entity.MandatesHistoryEntity;
import com.npst.upiserver.npcischema.AccountDetailType;
import com.npst.upiserver.npcischema.AccountType.Detail;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.ExpireRuleConstant;
import com.npst.upiserver.npcischema.InitiatedByType;
import com.npst.upiserver.npcischema.MandateSign;
import com.npst.upiserver.npcischema.MandateType;
import com.npst.upiserver.npcischema.MandateType.Amount;
import com.npst.upiserver.npcischema.PayConstant;
import com.npst.upiserver.npcischema.PayeeType;
import com.npst.upiserver.npcischema.PayerConstant;
import com.npst.upiserver.npcischema.PayerType;
import com.npst.upiserver.npcischema.RecurrenceType;
import com.npst.upiserver.npcischema.Ref;
import com.npst.upiserver.npcischema.ReqMandate;
import com.npst.upiserver.npcischema.ReqMandateConfirmation;
import com.npst.upiserver.npcischema.ReqPay;
import com.npst.upiserver.npcischema.RespMandate;
import com.npst.upiserver.npcischema.RespPay;
import com.npst.upiserver.npcischema.ResultType;
import com.npst.upiserver.npcischema.RulesType.Rule;
import com.npst.upiserver.repo.CustomerMandatesRepo;
import com.npst.upiserver.repo.MandateTxnsRepo;
import com.npst.upiserver.repo.MandatesHistoryRepo;
import com.npst.upiserver.service.CacheCustomService;
import com.npst.upiserver.service.NotificationService;
import com.npst.upiserver.util.ErrorLog;

@Component
public class MandateTxnsDaoImpl implements MandateTxnsDao {
	private static final Logger log = LoggerFactory.getLogger(MandateTxnsDaoImpl.class);
	@Autowired
	private MandateTxnsRepo mandateTxnsRepo;
	@Autowired
	private MandatesHistoryRepo mandatesHistoryRepo;
	@Autowired
	private CacheCustomService cacheCustomService;

	@Autowired
	private CustomerAccountDao customerAccountDao;

	@Autowired
	private RegistrationDao registrationDao;
	@Autowired
	private NotificationService notiService;
	@Autowired
	private CustomerMandatesRepo customerMandatesRepo;

	@Override
	public void updateMandatesTxns(RespPay respPay) {
		// TODO Auto-generated method stub
		try {
			MandateTxnsEntity mandateTxns = getByTxnId(respPay.getTxn().getId());
			if (mandateTxns == null) {
				mandateTxns = new MandateTxnsEntity();
			}
			mandateTxns.setRespDate(new Date());
			if (ResultType.SUCCESS.toString().equalsIgnoreCase(respPay.getResp().getResult().value())) {
				mandateTxns.setStatus(ConstantI.STATUS_2);
			} else if (ResultType.PARTIAL.toString().equalsIgnoreCase(respPay.getResp().getResult().value())) {
				mandateTxns.setStatus(ConstantI.STATUS_4);
			} else if (ResultType.DEEMED.toString().equalsIgnoreCase(respPay.getResp().getResult().value())) {
				mandateTxns.setStatus(ConstantI.STATUS_5);
			} else {
				mandateTxns.setStatus(ConstantI.STATUS_3);

				String respCode = respPay.getResp().getErrCode();
				List<Ref> apNo = respPay.getResp().getRef();
				for (Ref ref : apNo) {
					if (ConstantI.PAYER.equalsIgnoreCase(ref.getType().value())) {
						if (null != ref.getRespCode()) {
							respCode = respCode + ConstantI.CONST_SPCL_TILD + ref.getRespCode();
						}
					}
					if (ConstantI.PAYEE.equalsIgnoreCase(ref.getType().value())) {
						if (null != ref.getRespCode()) {
							respCode = respCode + ConstantI.CONST_SPCL_TILD + ref.getRespCode();
						}
					}
				}
				mandateTxns.setErrorCode(respCode);
				if (ConstantI.U69.equals(respCode)) {
					mandateTxns.setStatus(6);
				} else if (respCode.matches(ConstantI.ERROR_RESP_DECLINED)) {
					mandateTxns.setStatus(ConstantI.COLLECT_DECLINE);
				} else if (respCode.matches(ConstantI.ERROR_RESP_BLOCKED)) {
					mandateTxns.setStatus(ConstantI.COLLECT_BLOCK);
				} else if (respCode.matches(ConstantI.ERROR_RESP_SPAM)) {
					mandateTxns.setStatus(ConstantI.COLLECT_SPAM);
				} else {
					mandateTxns.setStatus(3);
				}
			}
			mandateTxnsRepo.save(mandateTxns);
		} catch (Exception e) {
			log.error("error{}", e);
			ErrorLog.sendError(e, MandateTxnsDaoImpl.class);
		}

	}

	private MandateTxnsEntity getByTxnId(String txnId) {
		try {
			List<MandateTxnsEntity> list = mandateTxnsRepo.findByTxnId(txnId);
			if (list.size() == 1) {
				return list.get(0);
			} else if (list.size() == 0) {
				log.warn("MandateTxnsEntity not found for TxnId={}", txnId);
			} else {
				log.warn("More than one MandateTxnsEntity found for TxnId={}", txnId);
			}

		} catch (Exception e) {
			log.error("error{}", e);
			ErrorLog.sendError(e, MandateTxnsDaoImpl.class);
		}
		return null;
	}

	@Override
	public void insertMandatesHistory(ReqMandate reqMandate, Ack ack, ReqResp reqJson) {
		// TODO Auto-generated method stub

		try {

			MandatesHistoryEntity mandatesHistory = new MandatesHistoryEntity();
			mandatesHistory.setReqDate(new Date());
			mandatesHistory.setTxncustRef(reqMandate.getTxn().getCustRef());
			mandatesHistory.setTxnId(reqMandate.getTxn().getId());
			mandatesHistory.setTxnNote(reqMandate.getTxn().getNote());
			mandatesHistory.setPayeeType(reqMandate.getPayees().getPayee().get(0).getType().value());
			

			
			if(InitiatedByType.PAYER.toString().equals(reqMandate.getTxn().getInitiatedBy().toString())
					&&"CREATE".equalsIgnoreCase(reqMandate.getTxn().getType().toString())) {
				
				mandatesHistory.setTxnType(ConstantI.CREATE_SEND);
			}
			
			else if(InitiatedByType.PAYER.toString().equals(reqMandate.getTxn().getInitiatedBy().toString())
					&&"UPDATE".equalsIgnoreCase(reqMandate.getTxn().getType().toString())) {
				
				mandatesHistory.setTxnType(ConstantI.UPDATE_SEND);
			}
			else if(InitiatedByType.PAYER.toString().equals(reqMandate.getTxn().getInitiatedBy().toString())
					&&"REVOKE".equalsIgnoreCase(reqMandate.getTxn().getType().toString())) {
				
				mandatesHistory.setTxnType(ConstantI.REVOKE_SEND);
			}
			else if(InitiatedByType.PAYEE.toString().equals(reqMandate.getTxn().getInitiatedBy().toString())
					&&"CREATE".equalsIgnoreCase(reqMandate.getTxn().getType().toString())) {
				
				mandatesHistory.setTxnType(ConstantI.REQMANDATE);
			}
			else if(InitiatedByType.PAYEE.toString().equals(reqMandate.getTxn().getInitiatedBy().toString())
					&&"REVOKE".equalsIgnoreCase(reqMandate.getTxn().getType().toString())) {
				
				mandatesHistory.setTxnType(ConstantI.REQ_REVOKE);
			}
			mandatesHistory.setTxnInitiationMode(reqMandate.getTxn().getInitiationMode());
			mandatesHistory.setMandateAmountrule(reqMandate.getMandate().getAmount().getRule().toString());
			mandatesHistory.setMandateAmountvalue(reqMandate.getMandate().getAmount().getValue());
			mandatesHistory.setMandateExpiry(reqMandate.getMandate().getValidity().getEnd());
			mandatesHistory.setMandateRecurrencepattern(reqJson.getMandate().getRecurrencePattern());
			mandatesHistory.setMandateRecurrenceRuletype(reqJson.getMandate().getRecurrenceRuleType());
			
			//if (null != ack.getErr() || !ack.getErrorMessages().isEmpty()||null==ack) {//||null==ack for bypass pupose
				
			if (null==ack) {
			mandatesHistory.setStatus(3);
				mandatesHistory.setErrorCode(Constant.ACK_ERROR);
			} else {
				mandatesHistory.setStatus(1);
			}

			if (InitiatedByType.PAYER.toString().equals(reqMandate.getTxn().getInitiatedBy().toString())) {
				mandatesHistory.setCustomerHistory(reqMandate.getPayer().getAddr() + ConstantI.CONST_CREATE_MANDATE_TO
						+ reqMandate.getPayees().getPayee().get(0).getAddr());
				long regvpaId = getRegIdByVpa(reqMandate.getPayer().getAddr());

				mandatesHistory.setRegId(regvpaId);
				mandatesHistory.setMandateType(reqMandate.getMandate().getType().toString());
			}
			if (InitiatedByType.PAYEE.toString().equals(reqMandate.getTxn().getInitiatedBy().toString())) {
				mandatesHistory.setCustomerHistory(reqMandate.getPayees().getPayee().get(0).getAddr()
						+ ConstantI.CONST_REQ_MANDATE_TO + reqMandate.getPayer().getAddr());
				long regvpaId = getRegIdByVpa(reqMandate.getPayees().getPayee().get(0).getAddr());

				mandatesHistory.setRegId(regvpaId);
				mandatesHistory.setMandateType(reqMandate.getMandate().getType().toString());
				/*List<Rule> ruleList = reqMandate.getTxn().getRules().getRule();

				for (int i = 0; i < ruleList.size(); i++) {
					if (ExpireRuleConstant.EXPIREAFTER.toString()
							.equalsIgnoreCase(ruleList.get(i).getName().toString())) {
						mandatesHistory.setMandateExpiry(ruleList.get(i).getValue());
						Calendar c = Calendar.getInstance();
						c.setTime(new Date());
						c.add(Calendar.DATE, 1);
					}
				}*/
			}
			PayerType payer = reqMandate.getPayer();
			if (null != payer) {
				mandatesHistory.setPayerName(payer.getName());
				mandatesHistory.setPayerVpa(payer.getAddr());
				if (null != payer.getAc()) {
					List<Detail> acDetailsPayer = payer.getAc().getDetail();
					for (Detail detail : acDetailsPayer) {
						if (AccountDetailType.ACNUM.value().equalsIgnoreCase(detail.getName().value())) {
							mandatesHistory.setPayerAccNo(detail.getValue());
						}
						if (AccountDetailType.ACTYPE.value().equalsIgnoreCase(detail.getName().value())) {
							mandatesHistory.setPayerAcType(detail.getValue());
						}
						if (AccountDetailType.IFSC.value().equalsIgnoreCase(detail.getName().value())) {
							mandatesHistory.setPayerAccIFSC(detail.getValue());
							mandatesHistory.setPayerBankName(cacheCustomService.getBankNameByIfsc(detail.getValue()));
						}
						if (AccountDetailType.MMID.value().equalsIgnoreCase(detail.getName().value())) {
							mandatesHistory.setPayerMMID(detail.getValue());
						}
						if (AccountDetailType.MOBNUM.value().equalsIgnoreCase(detail.getName().value())) {
							mandatesHistory.setPayerMobileNo(detail.getValue());
						}
					}
				}
			}
			List<PayeeType> payeeList = reqMandate.getPayees().getPayee();
			log.info("psyee is here ");
			if (null != payeeList) {
				for (PayeeType payeeType : payeeList) {
					mandatesHistory.setPayeeName(payeeType.getName());
					mandatesHistory.setPayeeVpa(payeeType.getAddr());
					if (null != payeeType.getAc()) {
						List<Detail> acDetailsPayee = payeeType.getAc().getDetail();
						for (Detail detail : acDetailsPayee) {
							if (AccountDetailType.ACNUM.value().equalsIgnoreCase(detail.getName().value())) {
								mandatesHistory.setPayeeAccNo(detail.getValue());
							}
							if (AccountDetailType.ACTYPE.value().equalsIgnoreCase(detail.getName().value())) {
								mandatesHistory.setPayeeAcType(detail.getValue());
							}
							if (AccountDetailType.IFSC.value().equalsIgnoreCase(detail.getName().value())) {
								mandatesHistory.setPayeeAccIFSC(detail.getValue());
								mandatesHistory
										.setPayeeBankName(cacheCustomService.getBankNameByIfsc(detail.getValue()));
							}
							if (AccountDetailType.MMID.value().equalsIgnoreCase(detail.getName().value())) {
								mandatesHistory.setPayeeMMID(detail.getValue());
							}
							if (AccountDetailType.MOBNUM.value().equalsIgnoreCase(detail.getName().value())) {
								mandatesHistory.setPayeeMobileNo(detail.getValue());
							}
						}
					}

					if (payeeType.getMerchant() != null) {
						if (payeeType.getMerchant().getIdentifier() != null) {
							mandatesHistory.setMerchantSubCode(payeeType.getMerchant().getIdentifier().getSubCode());
							mandatesHistory.setMerchantMid(payeeType.getMerchant().getIdentifier().getMid());
							mandatesHistory.setMerchantSid(payeeType.getMerchant().getIdentifier().getSid());
							mandatesHistory.setMerchantTid(payeeType.getMerchant().getIdentifier().getTid());
							mandatesHistory
									.setMerchantType(payeeType.getMerchant().getIdentifier().getMerchantType().value());
							mandatesHistory.setMerchantGenre(
									payeeType.getMerchant().getIdentifier().getMerchantGenre().value());
							mandatesHistory.setMerchantOnboardingType(
									payeeType.getMerchant().getIdentifier().getOnBoardingType().value());
						}
						if (payeeType.getMerchant().getName() != null) {
							mandatesHistory.setMerchantBrandName(payeeType.getMerchant().getName().getBrand());
							mandatesHistory.setMerchantLegalName(payeeType.getMerchant().getName().getLegal());
							mandatesHistory.setMerchantFranchiseName(payeeType.getMerchant().getName().getFranchise());
						}
						if (payeeType.getMerchant().getOwnership() != null) {
							mandatesHistory
									.setMerchantOwnershipType(payeeType.getMerchant().getOwnership().getType().value());
						}

					}
				}
			}

			if (mandatesHistory.getPayeeVpa().endsWith(ConstantI.CONST_URL_IFSC_NPCI)) {
				mandatesHistory.setPayeeAccNo(mandatesHistory.getPayeeVpa().substring(0,
						mandatesHistory.getPayeeVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE)));
				mandatesHistory.setPayeeAccIFSC(mandatesHistory.getPayeeVpa().substring(
						mandatesHistory.getPayeeVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE) + 1,
						mandatesHistory.getPayeeVpa().length() - 10));

			} else if (mandatesHistory.getPayeeVpa().endsWith(ConstantI.CONST_URL_IIN_NPCI)) {
				mandatesHistory.setPayeeUidNum(mandatesHistory.getPayeeVpa().substring(0,
						mandatesHistory.getPayeeVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE)));
				mandatesHistory.setPayeeIin(mandatesHistory.getPayeeVpa().substring(
						mandatesHistory.getPayeeVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE) + 1,
						mandatesHistory.getPayeeVpa().length() - 9));

			} else if (mandatesHistory.getPayeeVpa().endsWith(ConstantI.CONST_URL_AADHAR_NPCI)) {
				mandatesHistory.setPayeeUidNum(mandatesHistory.getPayeeVpa().substring(0,
						mandatesHistory.getPayeeVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE)));

			} else if (mandatesHistory.getPayeeVpa().endsWith(ConstantI.CONST_URL_MMID_NPCI)) {
				mandatesHistory.setPayeeMobileNo(mandatesHistory.getPayeeVpa().substring(0,
						mandatesHistory.getPayeeVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE)));
				mandatesHistory.setPayeeMMID(mandatesHistory.getPayeeVpa().substring(
						mandatesHistory.getPayeeVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE) + 1,
						mandatesHistory.getPayeeVpa().length() - 10));
			}

			if (mandatesHistory.getPayerVpa().endsWith(ConstantI.CONST_URL_IFSC_NPCI)) {
				mandatesHistory.setPayerAccNo(mandatesHistory.getPayerVpa().substring(0,
						mandatesHistory.getPayerVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE)));
				mandatesHistory.setPayerAccIFSC(mandatesHistory.getPayerVpa().substring(
						mandatesHistory.getPayerVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE) + 1,
						mandatesHistory.getPayerVpa().length() - 10));

			} else if (mandatesHistory.getPayerVpa().endsWith(ConstantI.CONST_URL_IIN_NPCI)) {
				mandatesHistory.setPayerUidNum(mandatesHistory.getPayerVpa().substring(0,
						mandatesHistory.getPayerVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE)));
				mandatesHistory.setPayerIin(mandatesHistory.getPayerVpa().substring(
						mandatesHistory.getPayerVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE) + 1,
						mandatesHistory.getPayerVpa().length() - 9));

			} else if (mandatesHistory.getPayerVpa().endsWith(ConstantI.CONST_URL_AADHAR_NPCI)) {
				mandatesHistory.setPayerUidNum(mandatesHistory.getPayerVpa().substring(0,
						mandatesHistory.getPayerVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE)));

			} else if (mandatesHistory.getPayerVpa().endsWith(ConstantI.CONST_URL_MMID_NPCI)) {
				mandatesHistory.setPayerMobileNo(mandatesHistory.getPayerVpa().substring(0,
						mandatesHistory.getPayerVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE)));
				mandatesHistory.setPayerMMID(mandatesHistory.getPayerVpa().substring(
						mandatesHistory.getPayerVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE) + 1,
						mandatesHistory.getPayerVpa().length() - 10));
			}

			// mandatesHistory.setAmount(reqMandate.getPayer().getAmount().getValue());

			/**
			 * Set mandate specific fields here
			 */
			/**
			 * Mandates related fields
			 */
			MandateType mandateType = reqMandate.getMandate();
			Amount mandateAmount = mandateType.getAmount();
			mandatesHistory.setMandateAmountrule(mandateAmount.getRule().toString());
			mandatesHistory.setMandateAmountvalue(mandateAmount.getValue());
			mandatesHistory.setMandateName(mandateType.getName());
			//RecurrenceType recurrence = mandateType.getRecurrence();
		/*	mandatesHistory.setMandateRecurrencepattern(recurrence.getPattern().toString());
			mandatesHistory.setMandateRecurrenceRuletype(recurrence.getRule().getType().toString());
			mandatesHistory.setMandateRecurrenceRulevalue(recurrence.getRule().getValue());*/
			mandatesHistory.setMandateRevokeable(mandateType.getRevokeable().toString());
			//mandatesHistory.setMandateShareToPayee(mandateType.getShareToPayee().toString());
			mandatesHistory.setMandateShareToPayee(reqJson.getMandate().getMandateShareToPayee());
			mandatesHistory.setMandateTs(mandateType.getTs());
			mandatesHistory.setMandateTxnId(mandateType.getTxnId());
			//mandatesHistory.setMandateType(mandateType.getType());
			mandatesHistory.setMandateUmn(mandateType.getUmn());
			mandatesHistory.setMandateValidityStart(mandateType.getValidity().getStart());
			mandatesHistory.setMandateValidityEnd(mandateType.getValidity().getEnd());
			mandatesHistory.setTxnInitiatedBy(reqMandate.getTxn().getInitiatedBy().toString());
			// mandatesHistory.setMandateStatus();
			mandatesHistory.setStatus(1);
			mandatesHistoryRepo.save(mandatesHistory);
			// notification ??????
		} catch (Exception e) {
			log.error("error{}", e);
			ErrorLog.sendError(e, MandateTxnsDaoImpl.class);
		}

	}

	private long getRegIdByVpa(String vpa) {
		long regId = 0;
		regId = customerAccountDao.getRegIdOfActiveAccByVpa(vpa);
		// TODO discussion
		if (regId == 0) {
			regId = registrationDao.getActiveRegIdByDefVpa(vpa);
		}
		return regId;
	}

	@Override
	public void updateRespMandateHistory(RespMandate respMandate) {
		// TODO Auto-generated method stub
		try {
			//if(PayConstant.CREATE.toString().equalsIgnoreCase(respMandate.getTxn().getType().toString())) {
				
			MandatesHistoryEntity customerMandates =null;
			
			/*if(respMandate.getTxn().getInitiatedBy().toString().equalsIgnoreCase(ConstantI.PAYEE)
					&&respMandate.getTxn().getType().toString().equalsIgnoreCase(ConstantI.M_CREATE)) {
				log.info("INSIDE PAYEE CREATE");
			customerMandates = getMandatesHistoryEntityByTxnIdandType(respMandate.getTxn().getId(),ConstantI.REQMANDATE);
			}
			else if(respMandate.getTxn().getInitiatedBy().toString().equalsIgnoreCase(ConstantI.PAYER)){
				 customerMandates = getMandatesHistoryEntityByTxnId(respMandate.getTxn().getId());
					log.info("INSIDE PAYER CREATE");
			}
			else {
				 customerMandates = getMandatesHistoryEntityByTxnId(respMandate.getTxn().getId());
					log.info("INSIDE Else BLOCK");
			}*/
			
			if(InitiatedByType.PAYER.toString().equals(respMandate.getTxn().getInitiatedBy().toString())
					&&"CREATE".equalsIgnoreCase(respMandate.getTxn().getType().toString())) {
				customerMandates = getMandatesHistoryEntityByTxnIdandType(respMandate.getTxn().getId(),ConstantI.CREATE_SEND);
				log.info("IN {} ",ConstantI.CREATE_SEND);

			}
			
			else if(InitiatedByType.PAYER.toString().equals(respMandate.getTxn().getInitiatedBy().toString())
					&&"UPDATE".equalsIgnoreCase(respMandate.getTxn().getType().toString())) {
				
				customerMandates = getMandatesHistoryEntityByTxnIdandType(respMandate.getTxn().getId(),ConstantI.UPDATE_SEND);
				log.info("IN {} ",ConstantI.UPDATE_SEND);

			}
			else if(InitiatedByType.PAYER.toString().equals(respMandate.getTxn().getInitiatedBy().toString())
					&&"REVOKE".equalsIgnoreCase(respMandate.getTxn().getType().toString())) {
				
				customerMandates = getMandatesHistoryEntityByTxnIdandType(respMandate.getTxn().getId(),ConstantI.REVOKE_SEND);

				log.info("IN {} ",ConstantI.REVOKE_SEND);

			}
			else if(InitiatedByType.PAYEE.toString().equals(respMandate.getTxn().getInitiatedBy().toString())
					&&"CREATE".equalsIgnoreCase(respMandate.getTxn().getType().toString())) {
				
				customerMandates = getMandatesHistoryEntityByTxnIdandType(respMandate.getTxn().getId(),ConstantI.REQMANDATE);
				log.info("IN {} ",ConstantI.REQMANDATE);

			
			}
			else if(InitiatedByType.PAYEE.toString().equals(respMandate.getTxn().getInitiatedBy().toString())
					&&"REVOKE".equalsIgnoreCase(respMandate.getTxn().getType().toString())) {
				
				customerMandates = getMandatesHistoryEntityByTxnIdandType(respMandate.getTxn().getId(),ConstantI.REQ_REVOKE);
				log.info("IN {} ",ConstantI.REQ_REVOKE);

				
			}
			
			/*if(InitiatedByType.PAYEE.toString().equals(respMandate.getTxn().getInitiatedBy().toString())
					&&"CREATE".equalsIgnoreCase(respMandate.getTxn().getType().toString())) {
				
				customerMandates = getMandatesHistoryEntityByTxnIdandType(respMandate.getTxn().getId(),ConstantI.CREATE_RECEIVE);
			}*/
			
			/*else if(InitiatedByType.PAYER.toString().equals(respMandate.getTxn().getInitiatedBy().toString())
					&&"UPDATE".equalsIgnoreCase(respMandate.getTxn().getType().toString())) {
				
				customerMandates = getMandatesHistoryEntityByTxnIdandType(respMandate.getTxn().getId(),ConstantI.UPDATE_RECEIVE);

				
			}*/
			
			
			customerMandates.setRespDate(new Date());
			if (ResultType.SUCCESS.toString().equalsIgnoreCase(respMandate.getResp().getResult())) {
				customerMandates.setStatus(MandateStatus.MANDATE_SUCCESS.getStatus());
			} else {
				String respCode = respMandate.getResp().getErrCode();
				List<Ref> apNo = respMandate.getResp().getRef();
				for (Ref ref : apNo) {
					if (ConstantI.PAYER.equalsIgnoreCase(ref.getType().value())) {
						if (null != ref.getRespCode()) {
							respCode = respCode + ConstantI.CONST_SPCL_TILD + ref.getRespCode();
						}
					}
					if (ConstantI.PAYEE.equalsIgnoreCase(ref.getType().value())) {
						if (null != ref.getRespCode()) {
							respCode = respCode + ConstantI.CONST_SPCL_TILD + ref.getRespCode();
						}
					}
				}
				customerMandates.setErrorCode(respCode);
				customerMandates.setStatus(3);

				if(respMandate.getResp().getErrCode().equalsIgnoreCase(ConstantI.MANDATE_EXPIRD)) {
					customerMandates.setMandateType(ConstantI.MANDATE_EXP);
				}
				else if(respMandate.getResp().getErrCode().equalsIgnoreCase(ConstantI.MANDATE_DECLINE)) {
					customerMandates.setMandateType(ConstantI.REJECT);
				}
				else if (ConstantI.U69.equals(respCode)) {// TODO
					customerMandates.setMandateType(ConstantI.DECLINED);
				} else if (respCode.matches(ConstantI.MANDATE_ERROR_RESP_DECLINED)) {
					
					customerMandates.setMandateType(ConstantI.DECLINED);
					
				} else if (respCode.matches(ConstantI.MANDATE_ERROR_RESP_BLOCKED)) {
					
					customerMandates.setMandateType(ConstantI.BLOCK);
				}else {
					customerMandates.setMandateType(ConstantI.DECLINED);
				}
			}
			mandatesHistoryRepo.save(customerMandates);
			//notiService.sendNoti(customerMandates); TODO
			
			/*}
			else if(PayConstant.REVOKE.toString().equalsIgnoreCase(respMandate.getTxn().getType().toString())
					&&ResultType.SUCCESS.toString().equalsIgnoreCase(respMandate.getResp().getResult())) {
				MandatesHistoryEntity customerMandates = getMandatesHistoryByUMN(respMandate.getMandate().getUmn());
				customerMandates.setTxnType(respMandate.getTxn().getType().toString());
				customerMandates.setTxnId(respMandate.getTxn().getId());
				customerMandates.setRespDate(new Date());
				customerMandates.setStatus(MandateStatus.MANDATE_REVOKED.getStatus());
				customerMandates.setTxncustRef(respMandate.getTxn().getCustRef());
				mandatesHistoryRepo.save(customerMandates);
			}
			else if(PayConstant.UPDATE.toString().equalsIgnoreCase(respMandate.getTxn().getType().toString())
					&&ResultType.SUCCESS.toString().equalsIgnoreCase(respMandate.getResp().getResult())){
				MandatesHistoryEntity customerMandates = getMandatesHistoryByUMN(respMandate.getMandate().getUmn());
				customerMandates.setTxnType(respMandate.getTxn().getType().toString());
				customerMandates.setTxnId(respMandate.getTxn().getId());
				customerMandates.setRespDate(new Date());
				customerMandates.setTxncustRef(respMandate.getTxn().getCustRef());
				customerMandates.setMandateAmountvalue(respMandate.getMandate().getAmount().getValue());
				customerMandates.setMandateExpiry(respMandate.getMandate().getValidity().getEnd());
				customerMandates.setMandateExpiry(respMandate.getMandate().getValidity().getStart());
				mandatesHistoryRepo.save(customerMandates);
			}*/
		} catch (Exception e) {
			log.error("error{}", e);
			ErrorLog.sendError(e, MandateTxnsDaoImpl.class);
		}

	}

	private MandatesHistoryEntity getMandatesHistoryByUMN(String umn) {
		
		try {
			List<MandatesHistoryEntity> list = mandatesHistoryRepo.findByMandateUmn(umn);
			if (list.size() == 1) {
				return list.get(0);
			} else if (list.size() == 0) {
				log.warn("MandatesHistoryEntity not found for UNM={}", umn);
			} else {
				log.warn("More than one MandatesHistoryEntity found for UMN={}", umn);
			}
		} catch (Exception e) {
			log.error("error{}", e);
			ErrorLog.sendError(e, MandateTxnsDaoImpl.class);
		}
		
		return null;
	}

	private MandatesHistoryEntity getMandatesHistoryEntityByTxnId(String txnId) {
		try {
			List<MandatesHistoryEntity> list = mandatesHistoryRepo.findByTxnId(txnId);
			if (list.size() == 1) {
				return list.get(0);
			} else if (list.size() == 0) {
				log.warn("MandatesHistoryEntity not found for txnId={}", txnId);
			} else {
				log.warn("More than one MandatesHistoryEntity found for txnId={}", txnId);
			}
		} catch (Exception e) {
			log.error("error{}", e);
			ErrorLog.sendError(e, MandateTxnsDaoImpl.class);
		}
		return null;
	}

	private MandatesHistoryEntity getMandatesHistoryEntityByTxnIdandType(String txnId, String type) {
		try {
			List<MandatesHistoryEntity> list = mandatesHistoryRepo.findByMandateTxnIdAndTxnType(txnId,type);
			if (list.size() == 1) {
				return list.get(0);
			} else if (list.size() == 0) {
				log.warn("MandatesHistoryEntity not found for txnId={}", txnId);
			} else {
				log.warn("More than one MandatesHistoryEntity found for txnId={}", txnId);
			}
		} catch (Exception e) {
			log.error("error {}", e);
			ErrorLog.sendError(e, MandatesHistoryDaoImpl.class);
		}
		return null;
	}
	@Override
	public void insertSuccessCustomerMandate(ReqMandateConfirmation reqMandateConfirmation) {
		// TODO test
		try {
			MandatesHistoryEntity mandateHistory = getMandatesHistoryEntityByTxnId(
					reqMandateConfirmation.getTxn().getId());
			MandateSign signature = reqMandateConfirmation.getSignature();
			CustomerMandatesEntity customer = setCustomerMandates(mandateHistory,
					reqMandateConfirmation.getTxn().getType().toString(), reqMandateConfirmation.getMandate().getUmn(),
					reqMandateConfirmation.getTxn().getInitiatedBy() != null
							? reqMandateConfirmation.getTxn().getInitiatedBy().toString()
							: null);
			if (signature != null && StringUtils.isNotBlank(signature.getValue())) {
				customer.setMandateSignId(signature.getId().toString());
				customer.setMandateSignValue(signature.getValue());
			}
			customerMandatesRepo.save(customer);
		} catch (Exception e) {
			log.error("error{}", e);
			ErrorLog.sendError(e, MandateTxnsDaoImpl.class);
		}

	}

	private CustomerMandatesEntity setCustomerMandates(MandatesHistoryEntity mandateHistory, String txnType, String umn,
			String txnInitiatedBy) {
		CustomerMandatesEntity customerMandates = null;
		if (PayConstant.CREATE.toString().equalsIgnoreCase(txnType)) {
			customerMandates = new CustomerMandatesEntity();
			customerMandates.setStatus(mandateHistory.getStatus());
			customerMandates.setRegId(mandateHistory.getRegId());
			customerMandates.setTxnType(mandateHistory.getTxnType());
			customerMandates.setTxnId(mandateHistory.getTxnId());
			customerMandates.setTxncustRef(mandateHistory.getTxncustRef());
			// customerMandates.setMandateExpiry(mandateHistory.getMandateExpiry());
			customerMandates.setPayerVpa(mandateHistory.getPayerVpa());
			customerMandates.setPayerName(mandateHistory.getPayerName());
			customerMandates.setPayerAccNo(mandateHistory.getPayerAccNo());
			customerMandates.setPayerAccIFSC(mandateHistory.getPayerAccIFSC());
			customerMandates.setPayerMobileNo(mandateHistory.getPayerMobileNo());
			customerMandates.setPayerMMID(mandateHistory.getPayerMMID());
			customerMandates.setPayerAcType(mandateHistory.getPayerAcType());
			customerMandates.setPayerBankName(mandateHistory.getPayerBankName());
			customerMandates.setPayeeName(mandateHistory.getPayeeName());
			customerMandates.setPayeeVpa(mandateHistory.getPayeeVpa());
			customerMandates.setPayeeAccNo(mandateHistory.getPayeeAccNo());
			customerMandates.setPayeeAccIFSC(mandateHistory.getPayeeAccIFSC());
			customerMandates.setPayeeMobileNo(mandateHistory.getPayeeMobileNo());
			customerMandates.setPayeeMMID(mandateHistory.getPayeeMMID());
			customerMandates.setPayeeAcType(mandateHistory.getPayeeAcType());
			customerMandates.setPayeeBankName(mandateHistory.getPayeeBankName());
			customerMandates.setReqDate(mandateHistory.getReqDate());
			customerMandates.setRespDate(mandateHistory.getRespDate());
			customerMandates.setPayerType(mandateHistory.getPayerType());
			customerMandates.setPayeeType(mandateHistory.getPayeeType());
			customerMandates.setPayeeUidNum(mandateHistory.getPayeeUidNum());
			customerMandates.setPayeeIin(mandateHistory.getPayeeIin());
			customerMandates.setPayerUidNum(mandateHistory.getPayerUidNum());
			customerMandates.setPayerIin(mandateHistory.getPayerIin());
			customerMandates.setMandateName(mandateHistory.getMandateName());
			customerMandates.setMandateTxnId(mandateHistory.getMandateTxnId());
			customerMandates.setMandateUmn(mandateHistory.getMandateUmn());
			customerMandates.setMandateTs(mandateHistory.getMandateTs());
			customerMandates.setMandateRevokeable(mandateHistory.getMandateRevokeable());
			customerMandates.setMandateShareToPayee(mandateHistory.getMandateShareToPayee());
			customerMandates.setMandateType(mandateHistory.getMandateType());
			customerMandates.setMandateValidityStart(mandateHistory.getMandateValidityStart());
			customerMandates.setMandateValidityEnd(mandateHistory.getMandateValidityEnd());
			customerMandates.setMandateAmountvalue(mandateHistory.getMandateAmountvalue());
			customerMandates.setMandateAmountrule(mandateHistory.getMandateAmountrule());
			customerMandates.setMandateRecurrencepattern(mandateHistory.getMandateRecurrencepattern());
			customerMandates.setMandateRecurrenceRulevalue(mandateHistory.getMandateRecurrenceRulevalue());
			customerMandates.setMandateRecurrenceRuletype(mandateHistory.getMandateRecurrenceRuletype());
			customerMandates.setTxnInitiatedBy(mandateHistory.getTxnInitiatedBy());

			/**
			 * Merchants tags
			 */
			if (PayerConstant.ENTITY.toString().equalsIgnoreCase(mandateHistory.getPayeeType())) {
				customerMandates.setMerchantSubCode(mandateHistory.getMerchantSubCode());
				customerMandates.setMerchantMid(mandateHistory.getMerchantMid());
				customerMandates.setMerchantSid(mandateHistory.getMerchantSid());
				customerMandates.setMerchantTid(mandateHistory.getMerchantTid());
				customerMandates.setMerchantType(mandateHistory.getMerchantType());
				customerMandates.setMerchantGenre(mandateHistory.getMerchantGenre());
				customerMandates.setMerchantOnboardingType(mandateHistory.getMerchantOnboardingType());

				customerMandates.setMerchantBrandName(mandateHistory.getMerchantBrandName());
				customerMandates.setMerchantFranchiseName(mandateHistory.getMerchantFranchiseName());
				customerMandates.setMerchantLegalName(mandateHistory.getMerchantLegalName());

				customerMandates.setMerchantOwnershipType(mandateHistory.getMerchantOwnershipType());

			}
			/**
			 * end
			 */

		} else if (PayConstant.REVOKE.toString().equalsIgnoreCase(txnType)) {
			customerMandates = findByMandatsUmn(umn);
			customerMandates.setStatus(mandateHistory.getStatus());
			customerMandates.setStatus(MandateStatus.MANDATE_REVOKED.getStatus());
		} else if (PayConstant.UPDATE.toString().equalsIgnoreCase(txnType)) {
			customerMandates = findByMandatsUmn(umn);
			customerMandates.setMandateAmountrule(mandateHistory.getMandateAmountrule());
			customerMandates.setStatus(mandateHistory.getStatus());
			customerMandates.setMandateAmountvalue(mandateHistory.getMandateAmountvalue());
			customerMandates.setMandateName(mandateHistory.getMandateName());
			customerMandates.setMandateValidityStart(mandateHistory.getMandateValidityStart());
			customerMandates.setMandateValidityEnd(mandateHistory.getMandateValidityEnd());
		}
		return customerMandates;
	}

	private CustomerMandatesEntity findByMandatsUmn(String umn) {
		try {
			List<CustomerMandatesEntity> list = customerMandatesRepo.findByMandateUmnOrderByIdDesc(umn);
			if (list.size() == 1) {
				return list.get(0);
			} else if (list.size() == 0) {
				log.warn("CustomerMandatesEntity not found for umn={}", umn);
			} else {
				log.warn("More than one CustomerMandatesEntity found for umn={}", umn);
			}
		} catch (Exception e) {
			log.error("error{}", e);
			ErrorLog.sendError(e, MandateTxnsDaoImpl.class);
		}
		return null;
	}

	@Override
	public void updateCustomerMandateHistory(ReqMandateConfirmation reqMandateConfirmation) {
		// TODO test
		try {
			
			MandatesHistoryEntity mandatesHistory=null;
			/*if(reqMandateConfirmation.getTxn().getInitiatedBy().toString().equalsIgnoreCase(ConstantI.PAYEE)
					&&reqMandateConfirmation.getTxn().getType().toString().equalsIgnoreCase(ConstantI.M_CREATE)) {
				mandatesHistory = getMandatesHistoryEntityByTxnIdandType(
						reqMandateConfirmation.getTxn().getId(),ConstantI.REQRECVMANDATE);
			}
			else if(reqMandateConfirmation.getTxn().getInitiatedBy().toString().equalsIgnoreCase(ConstantI.PAYER)) {
				mandatesHistory = getMandatesHistoryEntityByTxnId(
						reqMandateConfirmation.getTxn().getId());
			}
			
			else {
				mandatesHistory = getMandatesHistoryEntityByTxnId(
						reqMandateConfirmation.getTxn().getId());
				
			}
			
		 mandatesHistory = getMandatesHistoryEntityByTxnId(
					reqMandateConfirmation.getTxn().getId());*/
			
			 if(InitiatedByType.PAYEE.toString().equals(reqMandateConfirmation.getTxn().getInitiatedBy().toString())
					&&"CREATE".equalsIgnoreCase(reqMandateConfirmation.getTxn().getType().toString())) {
				
				mandatesHistory = getMandatesHistoryEntityByTxnIdandType(reqMandateConfirmation.getTxn().getId(),ConstantI.REQRECVMANDATE);
				log.info("IN {} ",ConstantI.REQRECVMANDATE);
			
			}
			else if(InitiatedByType.PAYEE.toString().equals(reqMandateConfirmation.getTxn().getInitiatedBy().toString())
					&&"REVOKE".equalsIgnoreCase(reqMandateConfirmation.getTxn().getType().toString())) {
				
				mandatesHistory = getMandatesHistoryEntityByTxnIdandType(reqMandateConfirmation.getTxn().getId(),ConstantI.REVOKE_REQRECV);
				log.info("IN {} ",ConstantI.REVOKE_REQRECV);
			}
			
			else if(InitiatedByType.PAYER.toString().equals(reqMandateConfirmation.getTxn().getInitiatedBy().toString())
					&&"CREATE".equalsIgnoreCase(reqMandateConfirmation.getTxn().getType().toString())) {
				
				mandatesHistory = getMandatesHistoryEntityByTxnIdandType(reqMandateConfirmation.getTxn().getId(),ConstantI.CREATE_RECEIVE);
				log.info("IN {} ",ConstantI.CREATE_RECEIVE);

			}
			
			else if(InitiatedByType.PAYER.toString().equals(reqMandateConfirmation.getTxn().getInitiatedBy().toString())
					&&"UPDATE".equalsIgnoreCase(reqMandateConfirmation.getTxn().getType().toString())) {
				
				mandatesHistory = getMandatesHistoryEntityByTxnIdandType(reqMandateConfirmation.getTxn().getId(),ConstantI.UPDATE_RECEIVE);
				log.info("IN {} ",ConstantI.UPDATE_RECEIVE);

			}
			 
			else if(InitiatedByType.PAYER.toString().equals(reqMandateConfirmation.getTxn().getInitiatedBy().toString())
					&&"REVOKE".equalsIgnoreCase(reqMandateConfirmation.getTxn().getType().toString())) {
				
				mandatesHistory = getMandatesHistoryEntityByTxnIdandType(reqMandateConfirmation.getTxn().getId(),ConstantI.REVOKE_RECEIVE);

				log.info("IN {} ",ConstantI.REVOKE_RECEIVE);

			}
			mandatesHistory.setRespDate(new Date());

			if (ResultType.SUCCESS.value()
					.equalsIgnoreCase(reqMandateConfirmation.getTxnConfirmation().getOrgStatus())) {
				mandatesHistory.setStatus(2);
			} else {
				String respCode = reqMandateConfirmation.getTxnConfirmation().getOrgErrCode();
				List<Ref> apNo = reqMandateConfirmation.getTxnConfirmation().getRef();
				for (Ref ref : apNo) {
					if (ConstantI.PAYER.equalsIgnoreCase(ref.getType().value())) {
						if (null != ref.getRespCode()) {
							respCode = respCode + ConstantI.CONST_SPCL_TILD + ref.getRespCode();
						}
					}
					if (ConstantI.PAYEE.equalsIgnoreCase(ref.getType().value())) {
						if (null != ref.getRespCode()) {
							respCode = respCode + ConstantI.CONST_SPCL_TILD + ref.getRespCode();
						}
					}
				}
				mandatesHistory.setErrorCode(respCode);
				mandatesHistory.setStatus(3);

				if(respCode.equalsIgnoreCase(ConstantI.MANDATE_EXPIRD)) {
					mandatesHistory.setMandateType(ConstantI.MANDATE_EXP);
				}
				else if(respCode.equalsIgnoreCase(ConstantI.MANDATE_DECLINE)) {
					mandatesHistory.setMandateType(ConstantI.REJECT);
				}
				else if (ConstantI.U69.equals(respCode)) {// TODO
					mandatesHistory.setMandateType(ConstantI.DECLINED);
				} else if (respCode.matches(ConstantI.MANDATE_ERROR_RESP_DECLINED)) {
					
					mandatesHistory.setMandateType(ConstantI.DECLINED);
					
				} else if (respCode.matches(ConstantI.MANDATE_ERROR_RESP_BLOCKED)) {
					
					mandatesHistory.setMandateType(ConstantI.BLOCK);
				}else {
					mandatesHistory.setMandateType(ConstantI.DECLINED);
				}
			}
			mandatesHistoryRepo.save(mandatesHistory);
			//notiService.sendNoti(mandatesHistory);
		} catch (Exception e) {
			log.error("error{}", e);
			ErrorLog.sendError(e, MandateTxnsDaoImpl.class);
		}

	}

	@Override
	public boolean checkIfTransactionIsProcessed(String mandateUmn) {
		// TODO  discussion 
		try {
			log.warn("not implemented");
			//FIND_MANDATE_TXN_BY_UMN_QUERY = "SELECT * FROM Mandate_Txns txn WHERE MandateUmn=:MandateUmn AND to_char(txn.reqDate, 'ddMMyyyy') = to_char(sysdate, 'ddMMyyyy')";
		} catch (Exception e) {
			log.error("Error: {}", e);
			ErrorLog.sendError(e, MandateTxnsDaoImpl.class);
		}
		return false;
	}

	@Override
	public void insertReqpayAndAck(ReqPay reqPay, Ack ack) {
		try {
			MandateTxnsEntity mandateTxns = new MandateTxnsEntity();
			mandateTxns.setReqDate(new Date());
			mandateTxns.setTxncustRef(reqPay.getTxn().getCustRef());
			mandateTxns.setTxnId(reqPay.getTxn().getId());
			mandateTxns.setPayeeType(reqPay.getPayees().getPayee().get(0).getType().value());
			if (null != ack.getErr() || !ack.getErrorMessages().isEmpty()) {
				mandateTxns.setStatus(3);// Failed
				mandateTxns.setErrorCode(Constant.ACK_ERROR);
			} else {
				mandateTxns.setStatus(1);// Pending
			}
			long regId = getRegIdByVpa(reqPay.getPayees().getPayee().get(0).getAddr());
			mandateTxns.setRegId(regId);
			PayerType payer = reqPay.getPayer();
			if (null != payer) {
				mandateTxns.setPayerName(payer.getName());
				mandateTxns.setPayerVpa(payer.getAddr());
				if (null != payer.getAc()) {
					List<Detail> acDetailsPayer = payer.getAc().getDetail();
					for (Detail detail : acDetailsPayer) {
						if (AccountDetailType.ACNUM.value().equalsIgnoreCase(detail.getName().value())) {
							mandateTxns.setPayerAccNo(detail.getValue());
						}
						if (AccountDetailType.ACTYPE.value().equalsIgnoreCase(detail.getName().value())) {
							mandateTxns.setPayerAcType(detail.getValue());
						}
						if (AccountDetailType.IFSC.value().equalsIgnoreCase(detail.getName().value())) {
							mandateTxns.setPayerAccIFSC(detail.getValue());

						}
						if (AccountDetailType.MMID.value().equalsIgnoreCase(detail.getName().value())) {
							mandateTxns.setPayerMMID(detail.getValue());
						}
						if (AccountDetailType.MOBNUM.value().equalsIgnoreCase(detail.getName().value())) {
							mandateTxns.setPayerMobileNo(detail.getValue());
						}
					}
				}
			}
			List<PayeeType> payeeList = reqPay.getPayees().getPayee();
			if (null != payeeList) {
				for (PayeeType payeeType : payeeList) {
					mandateTxns.setPayeeName(payeeType.getName());
					mandateTxns.setPayeeVpa(payeeType.getAddr());
					if (null != payeeType.getAc()) {
						List<Detail> acDetailsPayee = payeeType.getAc().getDetail();
						for (Detail detail : acDetailsPayee) {
							if (AccountDetailType.ACNUM.value().equalsIgnoreCase(detail.getName().value())) {
								mandateTxns.setPayeeAccNo(detail.getValue());
							}
							if (AccountDetailType.ACTYPE.value().equalsIgnoreCase(detail.getName().value())) {
								mandateTxns.setPayeeAcType(detail.getValue());
							}
							if (AccountDetailType.IFSC.value().equalsIgnoreCase(detail.getName().value())) {
								mandateTxns.setPayeeAccIFSC(detail.getValue());

							}
							if (AccountDetailType.MMID.value().equalsIgnoreCase(detail.getName().value())) {
								mandateTxns.setPayeeMMID(detail.getValue());
							}
							if (AccountDetailType.MOBNUM.value().equalsIgnoreCase(detail.getName().value())) {
								mandateTxns.setPayeeMobileNo(detail.getValue());
							}
						}
					}
				}
			}

			if (mandateTxns.getPayeeVpa().endsWith(ConstantI.CONST_URL_IFSC_NPCI)) {
				mandateTxns.setPayeeAccNo(mandateTxns.getPayeeVpa().substring(0,
						mandateTxns.getPayeeVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE)));
				mandateTxns.setPayeeAccIFSC(mandateTxns.getPayeeVpa().substring(
						mandateTxns.getPayeeVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE) + 1,
						mandateTxns.getPayeeVpa().length() - 10));

			} else if (mandateTxns.getPayeeVpa().endsWith(ConstantI.CONST_URL_IIN_NPCI)) {
				mandateTxns.setPayeeUidNum(mandateTxns.getPayeeVpa().substring(0,
						mandateTxns.getPayeeVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE)));
				mandateTxns.setPayeeIin(mandateTxns.getPayeeVpa().substring(
						mandateTxns.getPayeeVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE) + 1,
						mandateTxns.getPayeeVpa().length() - 9));

			} else if (mandateTxns.getPayeeVpa().endsWith(ConstantI.CONST_URL_AADHAR_NPCI)) {
				mandateTxns.setPayeeUidNum(mandateTxns.getPayeeVpa().substring(0,
						mandateTxns.getPayeeVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE)));

			} else if (mandateTxns.getPayeeVpa().endsWith(ConstantI.CONST_URL_MMID_NPCI)) {
				mandateTxns.setPayeeMobileNo(mandateTxns.getPayeeVpa().substring(0,
						mandateTxns.getPayeeVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE)));
				mandateTxns.setPayeeMMID(mandateTxns.getPayeeVpa().substring(
						mandateTxns.getPayeeVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE) + 1,
						mandateTxns.getPayeeVpa().length() - 10));
			}

			if (mandateTxns.getPayerVpa().endsWith(ConstantI.CONST_URL_IFSC_NPCI)) {
				mandateTxns.setPayerAccNo(mandateTxns.getPayerVpa().substring(0,
						mandateTxns.getPayerVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE)));
				mandateTxns.setPayerAccIFSC(mandateTxns.getPayerVpa().substring(
						mandateTxns.getPayerVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE) + 1,
						mandateTxns.getPayerVpa().length() - 10));

			} else if (mandateTxns.getPayerVpa().endsWith(ConstantI.CONST_URL_IIN_NPCI)) {
				mandateTxns.setPayerUidNum(mandateTxns.getPayerVpa().substring(0,
						mandateTxns.getPayerVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE)));
				mandateTxns.setPayerIin(mandateTxns.getPayerVpa().substring(
						mandateTxns.getPayerVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE) + 1,
						mandateTxns.getPayerVpa().length() - 9));

			} else if (mandateTxns.getPayerVpa().endsWith(ConstantI.CONST_URL_AADHAR_NPCI)) {
				mandateTxns.setPayerUidNum(mandateTxns.getPayerVpa().substring(0,
						mandateTxns.getPayerVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE)));

			} else if (mandateTxns.getPayerVpa().endsWith(ConstantI.CONST_URL_MMID_NPCI)) {
				mandateTxns.setPayerMobileNo(mandateTxns.getPayerVpa().substring(0,
						mandateTxns.getPayerVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE)));
				mandateTxns.setPayerMMID(mandateTxns.getPayerVpa().substring(
						mandateTxns.getPayerVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE) + 1,
						mandateTxns.getPayerVpa().length() - 10));
			}

			mandateTxns.setAmount(reqPay.getPayer().getAmount().getValue());
			mandateTxnsRepo.save(mandateTxns);
		} catch (Exception e) {
			log.error("Error: {}", e);
			ErrorLog.sendError(e, MandateTxnsDaoImpl.class);
		}
	}
}