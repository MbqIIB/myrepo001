package com.npst.upiserver.dao.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.constant.MandateStatus;
import com.npst.upiserver.dao.CustomerAccountDao;
import com.npst.upiserver.dao.MandatesHistoryDao;
import com.npst.upiserver.dao.RegistrationDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.CustomerAccount;
import com.npst.upiserver.entity.MandatesHistoryEntity;
import com.npst.upiserver.npcischema.AccountDetailType;
import com.npst.upiserver.npcischema.AccountType.Detail;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.ExpireRuleConstant;
import com.npst.upiserver.npcischema.InitiatedByType;
import com.npst.upiserver.npcischema.MandateType;
import com.npst.upiserver.npcischema.PayeeType;
import com.npst.upiserver.npcischema.PayerType;
import com.npst.upiserver.npcischema.ReqAuthMandate;
import com.npst.upiserver.npcischema.RespAuthMandate;
import com.npst.upiserver.npcischema.ResultType;
import com.npst.upiserver.npcischema.RulesType.Rule;
import com.npst.upiserver.repo.MandatesHistoryRepo;
import com.npst.upiserver.service.CacheCustomService;
import com.npst.upiserver.util.ErrorLog;

@Component
public class MandatesHistoryDaoImpl implements MandatesHistoryDao {
	private static final Logger log = LoggerFactory.getLogger(MandatesHistoryDaoImpl.class);
	MandatesHistoryEntity mandatesHistory = null;
	@Autowired
	private CacheCustomService cacheCustomService;
	@Autowired
	private MandatesHistoryRepo mandatesHistoryRepo;
	@Autowired
	private CustomerAccountDao customerAccountDao;
	@Autowired
	private RegistrationDao registrationDao;

	@Override
	public void insert(ReqAuthMandate reqAuthMandate) {
		try {
			/*mandatesHistory = getMandatesHistoryEntityByTxnId(reqAuthMandate.getTxn().getId());
			 if (mandatesHistory != null) {
				 log.info("Data was there in TXN {} ",reqAuthMandate.getTxn().getId());
			}
			 else {*/
			log.info("Inside ReqAuthMandate req Inset");
			mandatesHistory = new MandatesHistoryEntity();
			mandatesHistory.setTxnType(setType(reqAuthMandate));
			mandatesHistory.setReqDate(new Date());
			mandatesHistory.setTxncustRef(reqAuthMandate.getTxn().getCustRef());
			mandatesHistory.setTxnId(reqAuthMandate.getTxn().getId());
			mandatesHistory.setTxnNote(reqAuthMandate.getTxn().getNote());
			mandatesHistory.setStatus(MandateStatus.MANDATE_INITIATED.getStatus());
			mandatesHistory.setTxnInitiatedBy(reqAuthMandate.getTxn().getInitiatedBy().toString());
			mandatesHistory.setMandateAmountrule(reqAuthMandate.getMandate().getAmount().getRule().toString());
			mandatesHistory.setMandateAmountvalue(reqAuthMandate.getMandate().getAmount().getValue());
			mandatesHistory.setMandateName(reqAuthMandate.getMandate().getName());
			mandatesHistory.setMandateRecurrencepattern(reqAuthMandate.getMandate().getRecurrence().getPattern().value());
			if (reqAuthMandate.getMandate().getRecurrence().getRule() != null
					&& reqAuthMandate.getMandate().getRecurrence().getRule().getType() != null)
				mandatesHistory
						.setMandateRecurrenceRuletype(reqAuthMandate.getMandate().getRecurrence().getRule().getType().value());
			if (reqAuthMandate.getMandate().getRecurrence().getRule() != null)
				mandatesHistory.setMandateRecurrenceRulevalue(reqAuthMandate.getMandate().getRecurrence().getRule().getValue());
			mandatesHistory.setMandateRevokeable(reqAuthMandate.getMandate().getRevokeable().value());
			if (reqAuthMandate.getMandate().getShareToPayee() != null)
				mandatesHistory.setMandateShareToPayee(reqAuthMandate.getMandate().getShareToPayee().value());
			mandatesHistory.setMandateTs(reqAuthMandate.getMandate().getTs());
			mandatesHistory.setMandateTxnId(reqAuthMandate.getMandate().getTxnId());
			mandatesHistory.setMandateType(reqAuthMandate.getMandate().getType());
			mandatesHistory.setMandateUmn(reqAuthMandate.getMandate().getUmn());
			mandatesHistory.setMandateValidityEnd(reqAuthMandate.getMandate().getValidity().getEnd());
			mandatesHistory.setMandateValidityStart(reqAuthMandate.getMandate().getValidity().getStart());
			
			
			if (InitiatedByType.PAYER.toString()
					.equalsIgnoreCase(reqAuthMandate.getTxn().getInitiatedBy().toString())) {
				mandatesHistory.setMandateType("CREATE");
				mandatesHistory.setCustomerHistory(reqAuthMandate.getPayer().getAddr()
						+ ConstantI.CONST_CREATE_MANDATE_TO + reqAuthMandate.getPayees().getPayee().get(0).getAddr());
				long regvpaId = getRegIdByVpa(reqAuthMandate.getPayees().getPayee().get(0).getAddr());
				
				log.info("INSIDE PAYER  {}  REG ID {}",reqAuthMandate.getTxn().getInitiatedBy().toString(),regvpaId);

				if (0 == regvpaId) {
					/* return; */ }
				mandatesHistory.setRegId(regvpaId);
			} else if (InitiatedByType.PAYEE.toString()
					.equalsIgnoreCase(reqAuthMandate.getTxn().getInitiatedBy().toString())) {
				mandatesHistory.setCustomerHistory(reqAuthMandate.getPayees().getPayee().get(0).getAddr()
						+ ConstantI.CONST_REQUEST_MANDATE_TO + reqAuthMandate.getPayer().getAddr());
				//long regvpaId = getRegIdByVpa(reqAuthMandate.getPayer().getAddr());
				CustomerAccount customeraccount =getDetailsByVpa(reqAuthMandate.getPayer().getAddr());
				
				log.info(" IN SIDE PAYEEEE {}  REG ID {}",reqAuthMandate.getPayer().getAddr());

				 if (0 == customeraccount.getRegid()) {
						}
					mandatesHistory.setRegId(customeraccount.getRegid());
					mandatesHistory.setMandateType(ConstantI.REQRECVMANDATE);
					List<Rule> ruleList = reqAuthMandate.getTxn().getRules().getRule();
					for (int i = 0; i < ruleList.size(); i++) {
						if (ExpireRuleConstant.EXPIREAFTER.toString()
								.equalsIgnoreCase(ruleList.get(i).getName().toString())) {
							mandatesHistory.setMandateExpiry(ruleList.get(i).getValue());
						}
					}
					mandatesHistory.setPayerAccNo(customeraccount.getAccrefnumber());
					mandatesHistory.setPayerAccIFSC(customeraccount.getIfsc());
					mandatesHistory.setPayerName(customeraccount.getName());
					mandatesHistory.setPayerAcType(customeraccount.getAcctype());
					mandatesHistory.setPayerMobileNo(customeraccount.getMobileno());
			}
			PayerType payer = reqAuthMandate.getPayer();
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
			List<PayeeType> payeeList = reqAuthMandate.getPayees().getPayee();
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
			mandatesHistoryRepo.save(mandatesHistory);
		} catch (Exception e) {
			log.error("Error: {}", e);
			e.printStackTrace();
			ErrorLog.sendError(e, MandatesHistoryDaoImpl.class);

		}

	}

	@Override
	public void update(RespAuthMandate respAuthMandate, Ack ack) {
	//	final MandateType mandateType = respAuthMandate.getMandate();
		try {
			final MandateType mandateType = respAuthMandate.getMandate();

			if (InitiatedByType.PAYER.toString().equalsIgnoreCase(respAuthMandate.getTxn().getInitiatedBy().value())) {
				/**
				 * Setting mandate related info here...
				 */
				mandatesHistory = getMandatesHistoryEntityByTxnIdandType(respAuthMandate.getTxn().getId(),ConstantI.REQRECVMANDATE);
				if (mandatesHistory == null) {
					mandatesHistory = new MandatesHistoryEntity();
				}
				mandatesHistory.setTxnId(respAuthMandate.getTxn().getId());
				//final MandateType mandateType = respAuthMandate.getMandate();
				mandatesHistory.setMandateName(mandateType.getName());
				mandatesHistory.setMandateAmountrule(mandateType.getAmount().getRule().value());
				mandatesHistory.setMandateAmountvalue(mandateType.getAmount().getValue());
				if (mandateType.getRecurrence() != null) {
					mandatesHistory.setMandateRecurrencepattern(mandateType.getRecurrence().getPattern().value());
					if (mandateType.getRecurrence().getRule() != null) {
						if (mandateType.getRecurrence().getRule().getType() != null)
							mandatesHistory.setMandateRecurrenceRuletype(
									mandateType.getRecurrence().getRule().getType().value());
						mandatesHistory.setMandateRecurrenceRulevalue(mandateType.getRecurrence().getRule().getValue());
					}

				}
				if (mandateType.getRevokeable() != null) {
					mandatesHistory.setMandateRevokeable(mandateType.getRevokeable().value());
				}
				if (mandateType.getShareToPayee() != null) {
					mandatesHistory.setMandateShareToPayee(mandateType.getShareToPayee().value());
				}

				mandatesHistory.setMandateTs(mandateType.getTs());
				mandatesHistory.setMandateTxnId(mandateType.getTxnId());
				mandatesHistory.setMandateType(mandateType.getType());
				mandatesHistory.setMandateUmn(mandateType.getUmn());
				mandatesHistory.setMandateValidityEnd(mandateType.getValidity().getEnd());
				mandatesHistory.setMandateValidityStart(mandateType.getValidity().getStart());

				List<PayeeType> payeeList = respAuthMandate.getPayees().getPayee();
				if (null != payeeList) {
					for (PayeeType payeeType : payeeList) {
						// Save the Merchant Details in DB Dated::18-12-18
						if (payeeType.getMerchant() != null) {
							if (payeeType.getMerchant().getIdentifier() != null) {
								mandatesHistory
										.setMerchantSubCode(payeeType.getMerchant().getIdentifier().getSubCode());
								mandatesHistory.setMerchantMid(payeeType.getMerchant().getIdentifier().getMid());
								mandatesHistory.setMerchantSid(payeeType.getMerchant().getIdentifier().getSid());
								mandatesHistory.setMerchantTid(payeeType.getMerchant().getIdentifier().getTid());
								mandatesHistory.setMerchantType(
										payeeType.getMerchant().getIdentifier().getMerchantType().value());
								mandatesHistory.setMerchantGenre(
										payeeType.getMerchant().getIdentifier().getMerchantGenre().value());
								mandatesHistory.setMerchantOnboardingType(
										payeeType.getMerchant().getIdentifier().getOnBoardingType().value());
							}
							if (payeeType.getMerchant().getName() != null) {
								mandatesHistory.setMerchantBrandName(payeeType.getMerchant().getName().getBrand());
								mandatesHistory.setMerchantLegalName(payeeType.getMerchant().getName().getLegal());
								mandatesHistory
										.setMerchantFranchiseName(payeeType.getMerchant().getName().getFranchise());
							}
							if (payeeType.getMerchant().getOwnership() != null) {
								mandatesHistory.setMerchantOwnershipType(
										payeeType.getMerchant().getOwnership().getType().value());
							}

						}
						mandatesHistory.setPayeeName(payeeType.getName());
						mandatesHistory.setPayeeVpa(payeeType.getAddr());
						if (null != payeeType.getAc()) {
							List<Detail> acDetailsPayee = payeeType.getAc().getDetail();
							for (Detail detail : acDetailsPayee) {
								if (AccountDetailType.ACNUM.value().equalsIgnoreCase(detail.getName().value())) {
									if (null != mandatesHistory.getPayeeAccNo()
											|| !ConstantI.CONST_BLANK.equals(mandatesHistory.getPayeeAccNo())) {
										mandatesHistory.setPayeeAccNo(detail.getValue());
									}
								}
								if (AccountDetailType.ACTYPE.value().equalsIgnoreCase(detail.getName().value())) {
									if (null != mandatesHistory.getPayeeAcType()
											|| !ConstantI.CONST_BLANK.equals(mandatesHistory.getPayeeAcType())) {
										mandatesHistory.setPayeeAcType(detail.getValue());
									}
								}
								if (AccountDetailType.IFSC.value().equalsIgnoreCase(detail.getName().value())) {
									if (null != mandatesHistory.getPayeeAccIFSC()
											|| !ConstantI.CONST_BLANK.equals(mandatesHistory.getPayeeAccIFSC())) {
										mandatesHistory.setPayeeAccIFSC(detail.getValue());
										mandatesHistory.setPayeeBankName(
												cacheCustomService.getBankNameByIfsc(detail.getValue()));
									}
								}
								if (AccountDetailType.MMID.value().equalsIgnoreCase(detail.getName().value())) {
									if (null != mandatesHistory.getPayeeMMID()
											|| !ConstantI.CONST_BLANK.equals(mandatesHistory.getPayeeMMID())) {
										mandatesHistory.setPayeeMMID(detail.getValue());
									}
								}
								if (AccountDetailType.MOBNUM.value().equalsIgnoreCase(detail.getName().value())) {
									if (null != mandatesHistory.getPayeeMobileNo()
											|| !ConstantI.CONST_BLANK.equals(mandatesHistory.getPayeeMobileNo())) {
										mandatesHistory.setPayeeMobileNo(detail.getValue());
									}
								}
							}
						}
					}
				}

			}

			else if (InitiatedByType.PAYEE.toString()
					.equalsIgnoreCase(respAuthMandate.getTxn().getInitiatedBy().value())) {
				//mandatesHistory = getMandatesHistoryEntityByTxnId(respAuthMandate.getTxn().getId());////respAuthMandate.getTxn().getOrgTxnId()
				mandatesHistory = getMandatesHistoryEntityByTxnIdandType(respAuthMandate.getTxn().getId(),getTxnType(respAuthMandate));

				if (mandatesHistory == null) {
					mandatesHistory = new MandatesHistoryEntity();
				}
				PayerType payer = respAuthMandate.getPayer();
				if (null != payer) {
					mandatesHistory.setPayerName(payer.getName());
					mandatesHistory.setPayerVpa(payer.getAddr());
					if (null != payer.getAc()) {
						List<Detail> acDetailsPayer = payer.getAc().getDetail();
						for (Detail detail : acDetailsPayer) {
							if (AccountDetailType.ACNUM.value().equalsIgnoreCase(detail.getName().value())) {
								if (null != mandatesHistory.getPayerAccNo()
										|| !ConstantI.CONST_BLANK.equals(mandatesHistory.getPayerAccNo())) {
									mandatesHistory.setPayerAccNo(detail.getValue());
								}
							}
							if (AccountDetailType.ACTYPE.value().equalsIgnoreCase(detail.getName().value())) {
								if (null != mandatesHistory.getPayerAcType()
										|| !ConstantI.CONST_BLANK.equals(mandatesHistory.getPayerAcType())) {
									mandatesHistory.setPayerAcType(detail.getValue());
								}
							}
							if (AccountDetailType.IFSC.value().equalsIgnoreCase(detail.getName().value())) {
								if (null != mandatesHistory.getPayerAccIFSC()
										|| !ConstantI.CONST_BLANK.equals(mandatesHistory.getPayerAccIFSC())) {
									mandatesHistory.setPayerAccIFSC(detail.getValue());
									//mandatesHistory.setPayerBankName(cacheCustomService.getBankNameByIfsc(detail.getValue()));
								}
							}
							if (AccountDetailType.MMID.value().equalsIgnoreCase(detail.getName().value())) {
								if (null != mandatesHistory.getPayerMMID()
										|| !ConstantI.CONST_BLANK.equals(mandatesHistory.getPayerMMID())) {
									mandatesHistory.setPayerMMID(detail.getValue());
								}
							}
							if (AccountDetailType.MOBNUM.value().equalsIgnoreCase(detail.getName().value())) {
								if (null != mandatesHistory.getPayerMobileNo()
										|| !ConstantI.CONST_BLANK.equals(mandatesHistory.getPayerMobileNo())) {
									mandatesHistory.setPayerMobileNo(detail.getValue());
								}
							}
							
							
						}
					}
				}
				
				if (null != respAuthMandate.getMandate().getUmn()) {
					log.info("Payee umn ="+respAuthMandate.getMandate().getUmn());
					mandatesHistory.setMandateUmn(respAuthMandate.getMandate().getUmn());
				}
			}
			
			mandatesHistory.setMandateUmn(mandateType.getUmn());
			mandatesHistory.setRespDate(new Date());
			if (ResultType.SUCCESS.toString().equalsIgnoreCase(respAuthMandate.getResp().getResult())) {
				mandatesHistory.setStatus(MandateStatus.MANDATE_SUCCESS.getStatus());// psp auth success not txn
																						// success
			} else {
				mandatesHistory.setErrorCode(respAuthMandate.getResp().getErrCode());
				if (ConstantI.U69.equals(respAuthMandate.getResp().getErrCode())) {
					mandatesHistory.setStatus(MandateStatus.MANDATE_FAILED.getStatus());
				} else if (respAuthMandate.getResp().getErrCode().matches(ConstantI.MANDATE_ERROR_RESP_DECLINED)) {
					mandatesHistory.setStatus(MandateStatus.MANDATE_DECLINED.getStatus());
				} else if (respAuthMandate.getResp().getErrCode().matches(ConstantI.MANDATE_ERROR_RESP_BLOCKED)) {
					mandatesHistory.setStatus(MandateStatus.MANDATE_BLOCKED.getStatus());
				} else if (respAuthMandate.getResp().getErrCode().matches(ConstantI.MANDATE_ERROR_RESP_SPAM)) {
					mandatesHistory.setStatus(MandateStatus.MANDATE_SPAMMED.getStatus());
				} else {
					mandatesHistory.setStatus(3);
				}
			}
			if ((ack != null && null != ack.getErr())
					|| (ack != null && ack.getErrorMessages() != null && !ack.getErrorMessages().isEmpty())) {
				mandatesHistory.setStatus(MandateStatus.MANDATE_FAILED.getStatus());
				mandatesHistory.setErrorCode(Constant.ACK_ERROR);
			}
			mandatesHistoryRepo.save(mandatesHistory);
		} catch (Exception e) {
			log.error("error{}", e);
			ErrorLog.sendError(e, MandatesHistoryDaoImpl.class);
		}

	}

	private MandatesHistoryEntity getMandatesHistoryEntityByTxnId(String txnId) {
		try {
			List<MandatesHistoryEntity> list = mandatesHistoryRepo.findByTxnId(txnId);
			if (list.size() > 0) {
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

	private long getRegIdByVpa(String vpa) {
		long regId = 0;
		regId = customerAccountDao.getRegIdOfActiveAccByVpa(vpa);
		// TODO discussion
		if (regId == 0) {
			regId = registrationDao.getActiveRegIdByDefVpa(vpa);
		}
		return regId;
	}

	
	
	private CustomerAccount getDetailsByVpa(String addr) {
		CustomerAccount customerAc=null;
		customerAc=customerAccountDao.getAccDetailsByAccvirtualidAndStatus(addr);
		return customerAc;
	}
	
	
	private MandatesHistoryEntity getMandatesHistoryEntityByTxnIdandType(String txnId, String type) {
		try {
			
			List<MandatesHistoryEntity> list = mandatesHistoryRepo.findByMandateTxnIdAndTxnType(txnId,type);
			log.info("DATA BASED ON TXNID and TYPE {} ,{}",txnId,type);
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
	public void update(RespAuthMandate respAuthMandate, Ack ack, ReqResp reqJson) {
		try {
			mandatesHistory = getMandatesHistoryEntityByTxnIdandType(respAuthMandate.getTxn().getId(),ConstantI.REQRECVMANDATE);
			if (mandatesHistory == null) {
				mandatesHistory = new MandatesHistoryEntity();
			}
			PayerType payer = respAuthMandate.getPayer();
			if (null != payer) {
				mandatesHistory.setPayerName(payer.getName());
				mandatesHistory.setPayerVpa(payer.getAddr());
				if (null != payer.getAc()) {
					List<Detail> acDetailsPayer = payer.getAc().getDetail();
					for (Detail detail : acDetailsPayer) {
						if (AccountDetailType.ACNUM.value().equalsIgnoreCase(detail.getName().value())) {
							if (null != mandatesHistory.getPayerAccNo()
									|| !ConstantI.CONST_BLANK.equals(mandatesHistory.getPayerAccNo())) {
								mandatesHistory.setPayerAccNo(detail.getValue());
							}
						}
						if (AccountDetailType.ACTYPE.value().equalsIgnoreCase(detail.getName().value())) {
							if (null != mandatesHistory.getPayerAcType()
									|| !ConstantI.CONST_BLANK.equals(mandatesHistory.getPayerAcType())) {
								mandatesHistory.setPayerAcType(detail.getValue());
							}
						}
						if (AccountDetailType.IFSC.value().equalsIgnoreCase(detail.getName().value())) {
							if (null != mandatesHistory.getPayerAccIFSC()
									|| !ConstantI.CONST_BLANK.equals(mandatesHistory.getPayerAccIFSC())) {
								mandatesHistory.setPayerAccIFSC(detail.getValue());
								mandatesHistory
										.setPayerBankName(cacheCustomService.getBankNameByIfsc(detail.getValue()));
							}
						}
						if (AccountDetailType.MMID.value().equalsIgnoreCase(detail.getName().value())) {
							if (null != mandatesHistory.getPayerMMID()
									|| !ConstantI.CONST_BLANK.equals(mandatesHistory.getPayerMMID())) {
								mandatesHistory.setPayerMMID(detail.getValue());
							}
						}
						if (AccountDetailType.MOBNUM.value().equalsIgnoreCase(detail.getName().value())) {
							if (null != mandatesHistory.getPayerMobileNo()
									|| !ConstantI.CONST_BLANK.equals(mandatesHistory.getPayerMobileNo())) {
								mandatesHistory.setPayerMobileNo(detail.getValue());
							}
						}
					}
				}
			}
			
		if	("REJECT".equalsIgnoreCase(reqJson.getTxnType())){
			mandatesHistory.setMandateType(ConstantI.REJECT);
		}
		else if("BLOCK".equalsIgnoreCase(reqJson.getTxnType())){
			mandatesHistory.setMandateType(ConstantI.BLOCK);
		}
			mandatesHistory.setRespDate(new Date());
			mandatesHistory.setErrorCode(respAuthMandate.getResp().getErrCode());
			mandatesHistory.setStatus(3);
			mandatesHistory.setTxncustRef(respAuthMandate.getTxn().getCustRef());
			mandatesHistoryRepo.save(mandatesHistory);

	}
	catch (Exception e) {
		log.error("error{}", e);
		ErrorLog.sendError(e, MandatesHistoryDaoImpl.class);
		}
	}
	private String setType(ReqAuthMandate reqAuthMandate) {
		
		if(InitiatedByType.PAYER.toString().equals(reqAuthMandate.getTxn().getInitiatedBy().toString())
				&&"CREATE".equalsIgnoreCase(reqAuthMandate.getTxn().getType().toString())) {
			
			return ConstantI.CREATE_RECEIVE;
		}
		
		else if(InitiatedByType.PAYER.toString().equals(reqAuthMandate.getTxn().getInitiatedBy().toString())
				&&"UPDATE".equalsIgnoreCase(reqAuthMandate.getTxn().getType().toString())) {
			
			return ConstantI.UPDATE_RECEIVE;
		}
		else if(InitiatedByType.PAYER.toString().equals(reqAuthMandate.getTxn().getInitiatedBy().toString())
				&&"REVOKE".equalsIgnoreCase(reqAuthMandate.getTxn().getType().toString())) {
			
			return ConstantI.REVOKE_RECEIVE;
		}
		else if(InitiatedByType.PAYEE.toString().equals(reqAuthMandate.getTxn().getInitiatedBy().toString())
				&&"CREATE".equalsIgnoreCase(reqAuthMandate.getTxn().getType().toString())) {
			
			return ConstantI.REQRECVMANDATE;
		}
		else if(InitiatedByType.PAYEE.toString().equals(reqAuthMandate.getTxn().getInitiatedBy().toString())
				&&"REVOKE".equalsIgnoreCase(reqAuthMandate.getTxn().getType().toString())) {
			
			return ConstantI.REVOKE_REQRECV;
		}
		return null;
	}
	private String getTxnType(RespAuthMandate respAuthMandate) {
		
		if(InitiatedByType.PAYER.toString().equals(respAuthMandate.getTxn().getInitiatedBy().toString())
				&&"CREATE".equalsIgnoreCase(respAuthMandate.getTxn().getType().toString())) {
			
			return ConstantI.CREATE_RECEIVE;
		}
		
		else if(InitiatedByType.PAYER.toString().equals(respAuthMandate.getTxn().getInitiatedBy().toString())
				&&"UPDATE".equalsIgnoreCase(respAuthMandate.getTxn().getType().toString())) {
			
			return ConstantI.UPDATE_RECEIVE;
		}
		else if(InitiatedByType.PAYER.toString().equals(respAuthMandate.getTxn().getInitiatedBy().toString())
				&&"REVOKE".equalsIgnoreCase(respAuthMandate.getTxn().getType().toString())) {
			
			return ConstantI.REVOKE_RECEIVE;
		}
		else if(InitiatedByType.PAYEE.toString().equals(respAuthMandate.getTxn().getInitiatedBy().toString())
				&&"CREATE".equalsIgnoreCase(respAuthMandate.getTxn().getType().toString())) {
			
			return ConstantI.REQRECVMANDATE;
		}
		else if(InitiatedByType.PAYEE.toString().equals(respAuthMandate.getTxn().getInitiatedBy().toString())
				&&"REVOKE".equalsIgnoreCase(respAuthMandate.getTxn().getType().toString())) {
			
			return ConstantI.REVOKE_REQRECV;
		}
		return null;
	}
}
