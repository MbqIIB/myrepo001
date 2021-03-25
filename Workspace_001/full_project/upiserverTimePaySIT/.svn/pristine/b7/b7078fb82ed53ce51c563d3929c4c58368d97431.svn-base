package com.npst.upiserver.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.constant.InitiationMode;
import com.npst.upiserver.dao.CustomerAccountDao;
import com.npst.upiserver.dao.CustomerMandatesDao;
import com.npst.upiserver.dao.CustomerTxnsDao;
import com.npst.upiserver.dao.RegistrationDao;
import com.npst.upiserver.dao.ReqRespListAccDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.CustomerMandatesEntity;
import com.npst.upiserver.entity.CustomerTxns;
import com.npst.upiserver.entity.Registration;
import com.npst.upiserver.npcischema.AccountDetailType;
import com.npst.upiserver.npcischema.AccountType.Detail;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.ExpireRuleConstant;
import com.npst.upiserver.npcischema.PayeeType;
import com.npst.upiserver.npcischema.PayerType;
import com.npst.upiserver.npcischema.Ref;
import com.npst.upiserver.npcischema.RefType;
import com.npst.upiserver.npcischema.ReqAuthDetails;
import com.npst.upiserver.npcischema.ReqBalEnq;
import com.npst.upiserver.npcischema.ReqListAccount;
import com.npst.upiserver.npcischema.ReqOtp;
import com.npst.upiserver.npcischema.ReqPay;
import com.npst.upiserver.npcischema.ReqRegMob;
import com.npst.upiserver.npcischema.ReqSetCre;
import com.npst.upiserver.npcischema.ReqTxnConfirmation;
import com.npst.upiserver.npcischema.RespAuthDetails;
import com.npst.upiserver.npcischema.RespBalEnq;
import com.npst.upiserver.npcischema.RespListAccount;
import com.npst.upiserver.npcischema.RespOtp;
import com.npst.upiserver.npcischema.RespPay;
import com.npst.upiserver.npcischema.RespRegMob;
import com.npst.upiserver.npcischema.RespSetCre;
import com.npst.upiserver.npcischema.ResultType;
import com.npst.upiserver.npcischema.RulesType.Rule;
import com.npst.upiserver.repo.CustomerTxnsRepository;
import com.npst.upiserver.service.CacheCustomService;
import com.npst.upiserver.service.NotificationService;
import com.npst.upiserver.util.ErrorLog;

@Component
public class CustomerTxnsDaoImpl implements CustomerTxnsDao {
	
	private static final Logger	log	= LoggerFactory.getLogger(CustomerTxnsDaoImpl.class);
	
	@Autowired
	CustomerTxnsRepository			custTxnRepo;
	@Autowired
	CustomerAccountDao custAccDao;
	
	@Autowired
	RegistrationDao regDao;
	
	@Autowired
	ReqRespListAccDao reqRespListAccDao;
	
	@Autowired
	private NotificationService notiService;
	
	@Autowired
	private CacheCustomService cacheCustomService;
	
		@Autowired
	private CustomerMandatesDao customerMandatesDao;
	
	@Override
	public void insert(ReqAuthDetails reqAuthDetails) {
		try {
			log.debug("{}", reqAuthDetails);
			CustomerTxns customerTxns = new CustomerTxns();
			customerTxns.setReqDate(new Date());
			customerTxns.setTxncustRef(reqAuthDetails.getTxn().getCustRef());
			customerTxns.setTxnId(reqAuthDetails.getTxn().getId());
			customerTxns.setTxnNote(reqAuthDetails.getTxn().getNote());
			customerTxns.setStatus(1);
			if (ConstantI.PAY.equalsIgnoreCase(reqAuthDetails.getTxn().getType().value())) {
				customerTxns.setTxnType(Constant.PAY_RECEIVE);
				customerTxns.setCustomerHistory(reqAuthDetails.getPayer().getAddr() + ConstantI.CONST_SEND_MONEY_TO
						+ reqAuthDetails.getPayees().getPayee().get(0).getAddr());
				long regvpaId = getRegIdByVpa(reqAuthDetails.getPayees().getPayee().get(0).getAddr());
				if (0 == regvpaId) {
					return;
				}
				customerTxns.setRegId(regvpaId);
			} else if (ConstantI.COLLECT.equalsIgnoreCase(reqAuthDetails.getTxn().getType().value())) {
				long regvpaId =0l;
				customerTxns.setTxnType(Constant.COLLECT_RECEIVE_TYPE);
				customerTxns.setCustomerHistory(reqAuthDetails.getPayees().getPayee().get(0).getAddr()
						+ ConstantI.CONST_REQ_MONEY_TO + reqAuthDetails.getPayer().getAddr());
				if("11".equals(reqAuthDetails.getTxn().getInitiationMode())||InitiationMode.MANDATEQR.getMode().equals(reqAuthDetails.getTxn().getInitiationMode())) {
					 regvpaId = getRegIdByumn(reqAuthDetails.getPayer().getAddr());

				}
				else {
					 regvpaId = getRegIdByVpa(reqAuthDetails.getPayer().getAddr());

				}
				
				
				if (0 == regvpaId) {
					return;
				}
				customerTxns.setRegId(regvpaId);
				List<Rule> ruleList = reqAuthDetails.getTxn().getRules().getRule();
				for (int i = 0; i < ruleList.size(); i++) {
					if (ExpireRuleConstant.EXPIREAFTER.value().toString()
							.equalsIgnoreCase(ruleList.get(i).getName().toString())) {
						customerTxns.setCollectExpiry(ruleList.get(i).getValue());
					}
				}
			}
			PayerType payer = reqAuthDetails.getPayer();
			if (null != payer) {

				customerTxns.setPayerName(payer.getName());
				customerTxns.setPayerVpa(payer.getAddr());
				if (null != payer.getAc()) {
					List<Detail> acDetailsPayer = payer.getAc().getDetail();
					for (Detail detail : acDetailsPayer) {
						if (AccountDetailType.ACNUM.value().equalsIgnoreCase(detail.getName().value())) {
							customerTxns.setPayerAccNo(detail.getValue());
						}
						if (AccountDetailType.ACTYPE.value().equalsIgnoreCase(detail.getName().value())) {
							customerTxns.setPayerAcType(detail.getValue());
						}
						if (AccountDetailType.IFSC.value().equalsIgnoreCase(detail.getName().value())) {
							customerTxns.setPayerAccIFSC(detail.getValue());
							customerTxns.setPayerBankName(cacheCustomService.getBankNameByIfsc(detail.getValue())!=null?cacheCustomService.getBankNameByIfsc(detail.getValue()):"");
						}
						if (AccountDetailType.MMID.value().equalsIgnoreCase(detail.getName().value())) {
							customerTxns.setPayerMMID(detail.getValue());
						}
						if (AccountDetailType.MOBNUM.value().equalsIgnoreCase(detail.getName().value())) {
							customerTxns.setPayerMobileNo(detail.getValue());
						}
					}
				}
			}
			List<PayeeType> payeeList = reqAuthDetails.getPayees().getPayee();
			if (null != payeeList) {
				for (PayeeType payeeType : payeeList) {
					customerTxns.setPayeeName(payeeType.getName());
					customerTxns.setPayeeVpa(payeeType.getAddr());

					//refUrl set into db
					//List<ListVaeEntity> isMerchant = listVaeRepo.findByAddr(payeeType.getAddr());
					if (null != reqAuthDetails.getTxn().getRefCategory() && reqAuthDetails.getTxn().getRefCategory().equalsIgnoreCase(ConstantI.REF_CATEGORY)) { 
						if (null != reqAuthDetails.getTxn().getRefUrl()) {

							customerTxns.setRefUrl(reqAuthDetails.getTxn().getRefUrl());
						}
					}
					
					if (null != payeeType.getAc()) {
						List<Detail> acDetailsPayee = payeeType.getAc().getDetail();
						for (Detail detail : acDetailsPayee) {
							if (AccountDetailType.ACNUM.value().equalsIgnoreCase(detail.getName().value())) {
								customerTxns.setPayeeAccNo(detail.getValue());
							}
							if (AccountDetailType.ACTYPE.value().equalsIgnoreCase(detail.getName().value())) {
								customerTxns.setPayeeAcType(detail.getValue());
							}
							if (AccountDetailType.IFSC.value().equalsIgnoreCase(detail.getName().value())) {
								customerTxns.setPayeeAccIFSC(detail.getValue());
								customerTxns.setPayeeBankName(cacheCustomService.getBankNameByIfsc(detail.getValue()));
							}
							if (AccountDetailType.MMID.value().equalsIgnoreCase(detail.getName().value())) {
								customerTxns.setPayeeMMID(detail.getValue());
							}
							if (AccountDetailType.MOBNUM.value().equalsIgnoreCase(detail.getName().value())) {
								customerTxns.setPayeeMobileNo(detail.getValue());
							}
						}
					}
				}
			}
			customerTxns.setAmount(reqAuthDetails.getPayer().getAmount().getValue());
			custTxnRepo.save(customerTxns);
		} catch (Exception e) {
			log.error("error in insert {}", e);
			ErrorLog.sendError(e, CustomerTxnsDaoImpl.class);
		}

	}
	
	private long getRegIdByVpa(String vpa) {
		long regId = 0;
		try {
			regId = custAccDao.getRegIdOfActiveAccByVpa(vpa);
			// TODO discussion
			if (regId == 0) {
				regId = regDao.getActiveRegIdByDefVpa(vpa);
			}
		} catch (Exception e) {
			log.error("error{}", e);
			ErrorLog.sendError(e, CustomerTxnsDaoImpl.class);
		}
		return regId;
	}
	
	
		private long getRegIdByumn(String addr) {
		long regid=0l;
		CustomerMandatesEntity customerMandates = customerMandatesDao.findByUmnAndSignValue(addr);
		regid=customerMandates.getRegId();
		return regid;
	}
	private Long getRegVpaIdOnVpa(String vpa) {
		Long regVpaId = 0l;
		try {
			regVpaId=custAccDao.getRegIdByVPA(vpa);
			if(regVpaId==0) {
				Registration regDetails=regDao.getRegistrationDetails(regVpaId);
				regVpaId=regDetails.getRegid().longValue();
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return regVpaId;
	}
	@Override
	public boolean update(RespAuthDetails respAuthDetails, Ack ack) {
		boolean updated = false;
		try {
			CustomerTxns customerTxns = null;
			if (ConstantI.PAY.equalsIgnoreCase(respAuthDetails.getTxn().getType().value())) {
				customerTxns = custTxnRepo.findByTxnIdAndTxnType(respAuthDetails.getTxn().getId(),
						Constant.PAY_RECEIVE);
				if (customerTxns != null) {
					List<PayeeType> payeeList = respAuthDetails.getPayees().getPayee();
					if (null != payeeList) {
						for (PayeeType payeeType : payeeList) {
							customerTxns.setPayeeName(payeeType.getName());
							customerTxns.setPayeeVpa(payeeType.getAddr());
							if (null != payeeType.getAc()) {
								List<Detail> acDetailsPayee = payeeType.getAc().getDetail();
								for (Detail detail : acDetailsPayee) {
									if (AccountDetailType.ACNUM.value().equalsIgnoreCase(detail.getName().value())) {
										if (null != customerTxns.getPayeeAccNo()
												|| !ConstantI.CONST_BLANK.equals(customerTxns.getPayeeAccNo())) {
											customerTxns.setPayeeAccNo(detail.getValue());
										}
									}
									if (AccountDetailType.ACTYPE.value().equalsIgnoreCase(detail.getName().value())) {
										if (null != customerTxns.getPayeeAcType()
												|| !ConstantI.CONST_BLANK.equals(customerTxns.getPayeeAcType())) {
											customerTxns.setPayeeAcType(detail.getValue());
										}
									}
									if (AccountDetailType.IFSC.value().equalsIgnoreCase(detail.getName().value())) {
										if (null != customerTxns.getPayeeAccIFSC()
												|| !ConstantI.CONST_BLANK.equals(customerTxns.getPayeeAccIFSC())) {
											customerTxns.setPayeeAccIFSC(detail.getValue());
										customerTxns.setPayeeBankName(
												cacheCustomService.getBankNameByIfsc(detail.getValue()));
									}
								}
								if (AccountDetailType.MMID.value().equalsIgnoreCase(detail.getName().value())) {
									if (null != customerTxns.getPayeeMMID()
											|| !ConstantI.CONST_BLANK.equals(customerTxns.getPayeeMMID())) {
										customerTxns.setPayeeMMID(detail.getValue());
									}
								}
								if (AccountDetailType.MOBNUM.value().equalsIgnoreCase(detail.getName().value())) {
									if (null != customerTxns.getPayeeMobileNo()
											|| !ConstantI.CONST_BLANK.equals(customerTxns.getPayeeMobileNo())) {
										customerTxns.setPayeeMobileNo(detail.getValue());
									}
								}
							}
						}
					}
				}

			}
				
			}
			else if (ConstantI.COLLECT.equalsIgnoreCase(respAuthDetails.getTxn().getType().value())) {
				customerTxns = getCustTxnsByTxnIdAndType(respAuthDetails.getTxn().getId(),
						Constant.COLLECT_RECEIVE_TYPE);
				PayerType payer = respAuthDetails.getPayer();
				if (null != payer) {
					customerTxns.setPayerName(payer.getName());
					customerTxns.setPayerVpa(payer.getAddr());
					if (null != payer.getAc()) {
						List<Detail> acDetailsPayer = payer.getAc().getDetail();
						for (Detail detail : acDetailsPayer) {
							if (AccountDetailType.ACNUM.value().equalsIgnoreCase(detail.getName().value())) {
								if (null != customerTxns.getPayerAccNo()
										|| !ConstantI.CONST_BLANK.equals(customerTxns.getPayerAccNo())) {
									customerTxns.setPayerAccNo(detail.getValue());
								}
							}
							if (AccountDetailType.ACTYPE.value().equalsIgnoreCase(detail.getName().value())) {
								if (null != customerTxns.getPayerAcType()
										|| !ConstantI.CONST_BLANK.equals(customerTxns.getPayerAcType())) {
									customerTxns.setPayerAcType(detail.getValue());
								}
							}
							if (AccountDetailType.IFSC.value().equalsIgnoreCase(detail.getName().value())) {
								if (null != customerTxns.getPayerAccIFSC()
										|| !ConstantI.CONST_BLANK.equals(customerTxns.getPayerAccIFSC())) {
									customerTxns.setPayerAccIFSC(detail.getValue());
									customerTxns
									.setPayerBankName(cacheCustomService.getBankNameByIfsc(detail.getValue()));
								}
							}
							if (AccountDetailType.MMID.value().equalsIgnoreCase(detail.getName().value())) {
								if (null != customerTxns.getPayerMMID()
										|| !ConstantI.CONST_BLANK.equals(customerTxns.getPayerMMID())) {
									customerTxns.setPayerMMID(detail.getValue());
								}
							}
							if (AccountDetailType.MOBNUM.value().equalsIgnoreCase(detail.getName().value())) {
								if (null != customerTxns.getPayerMobileNo()
										|| !ConstantI.CONST_BLANK.equals(customerTxns.getPayerMobileNo())) {
									customerTxns.setPayerMobileNo(detail.getValue());
								}
							}
						}
					}
				}
			} else {
				log.error("TxnType not found {}", respAuthDetails.getTxn().getType().value());
				ErrorLog.sendError("TxnType not found ie ", respAuthDetails.getTxn().getType().value(),
						CustomerTxnsDaoImpl.class);
			}
			
			customerTxns.setRespDate(new Date());
			if (ResultType.SUCCESS.toString().equalsIgnoreCase(respAuthDetails.getResp().getResult())) {
				customerTxns.setStatus(12); 
			} else if (ResultType.PARTIAL.toString().equalsIgnoreCase(respAuthDetails.getResp().getResult())) {
				customerTxns.setStatus(4);
			} else if (ResultType.DEEMED.toString().equalsIgnoreCase(respAuthDetails.getResp().getResult())) {
				customerTxns.setStatus(5);
			} else {
				customerTxns.setStatus(3);
				customerTxns.setErrorCode(respAuthDetails.getResp().getErrCode());
				if (ConstantI.U69.equals(respAuthDetails.getResp().getErrCode())) {
					customerTxns.setStatus(6);
				} else if (respAuthDetails.getResp().getErrCode().matches(ConstantI.CONST_RESP_ERROR_CODES)) {
					customerTxns.setStatus(7);
				} else {
					customerTxns.setStatus(3);
				}
			}
			if (null != ack.getErr() || 0 != ack.getErrorMessages().size()) {
				customerTxns.setStatus(3);
				customerTxns.setErrorCode(Constant.ACK_ERROR);
			}
			custTxnRepo.save(customerTxns);
            notiService.sendNoti(customerTxns);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return updated;
	}

	@Override
	public boolean update(RespBalEnq respBalEnq) {
		try {
			CustomerTxns customerTxns = custTxnRepo.findByTxnIdAndTxnType(respBalEnq.getTxn().getId(),Constant.BAL_ENQ);
			customerTxns.setRespDate(new Date());
			if (ResultType.SUCCESS.toString().equalsIgnoreCase(respBalEnq.getResp().getResult())) {
				customerTxns.setStatus(2);
			} else if (ResultType.PARTIAL.toString().equalsIgnoreCase(respBalEnq.getResp().getResult())) {
				customerTxns.setStatus(4);
			} else {
				customerTxns.setStatus(3);
				customerTxns.setErrorCode(respBalEnq.getResp().getErrCode());
			}
			if (customerTxns.getStatus() != 2) {
				// to do here
			//	NotiMan.sendNoti(customerTxns);
			}
			custTxnRepo.save(customerTxns);
			return true;
		}
		catch(Exception e) {
			log.error(e.getMessage(),e);
			return false;
		}
	}

	@Override
	public void update(RespOtp respOtp) {
		try {
			CustomerTxns customerTxns=custTxnRepo.findByTxnIdAndTxnType(respOtp.getTxn().getId(),Constant.REQ_OTP);
			if(customerTxns!=null) {
				customerTxns.setRespDate(new Date());
				if (ResultType.SUCCESS.toString().equalsIgnoreCase(respOtp.getResp().getResult())) {
					customerTxns.setStatus(2);
				} else if (ResultType.PARTIAL.toString().equalsIgnoreCase(respOtp.getResp().getResult())) {
					customerTxns.setStatus(4);
				} else {
					customerTxns.setStatus(3);
					customerTxns.setErrorCode(respOtp.getResp().getErrCode());
				}
				custTxnRepo.save(customerTxns);
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	@Override
	public void update(RespPay respPay) {
		CustomerTxns customerTxns=null;
		try {
			if (ConstantI.PAY.equalsIgnoreCase(respPay.getTxn().getType().value())) {
				customerTxns=custTxnRepo.findByTxnIdAndTxnType(respPay.getTxn().getId(),Constant.PAY_SEND);
			} else if (ConstantI.COLLECT.equalsIgnoreCase(respPay.getTxn().getType().value())) {
				customerTxns=custTxnRepo.findByTxnIdAndTxnType(respPay.getTxn().getId(),Constant.COLLECT_SEND);
			} else {
				customerTxns = new CustomerTxns();
			}
			customerTxns.setRespDate(new Date());
			if (ResultType.SUCCESS.toString().equalsIgnoreCase(respPay.getResp().getResult().value())) {
				customerTxns.setStatus(2);
			} else if (ResultType.PARTIAL.toString().equalsIgnoreCase(respPay.getResp().getResult().value())) {
				customerTxns.setStatus(4);
			} else if (ResultType.DEEMED.toString().equalsIgnoreCase(respPay.getResp().getResult().value())) {
				customerTxns.setStatus(5);
			} else {
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
				customerTxns.setErrorCode(respCode);
				if (ConstantI.U69.equals(respCode)) {
					customerTxns.setStatus(6);
				} else if (respCode.matches("U19~ZA|ZA|U29~TM|TM")) {
					customerTxns.setStatus(7);
				} else {
					customerTxns.setStatus(3);
				}
			}
			custTxnRepo.save(customerTxns);
			// To Do
			//NotiMan.sendNoti(customerTxns);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	@Override
	public void update(RespRegMob respRegMob) {
		try {
			CustomerTxns customerTxns = custTxnRepo.findByTxnIdAndTxnType(respRegMob.getTxn().getId(),Constant.REG_MOB);
			customerTxns.setRespDate(new Date());
			if (ResultType.SUCCESS.toString().equalsIgnoreCase(respRegMob.getResp().getResult())) {
				customerTxns.setStatus(2);
			} else {
				customerTxns.setStatus(3);
				customerTxns.setErrorCode(respRegMob.getResp().getErrCode());
			}
			custTxnRepo.save(customerTxns);
		// To Do
			//NotiMan.sendNoti(customerTxns);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	@Override
	public void update(RespSetCre respSetCre) {
		try {
			CustomerTxns customerTxns = custTxnRepo.findByTxnIdAndTxnType(respSetCre.getTxn().getId(),Constant.SET_PIN);
			customerTxns.setRespDate(new Date());
			if (ResultType.SUCCESS.toString().equalsIgnoreCase(respSetCre.getResp().getResult())) {
				customerTxns.setStatus(2);
			} else {
				customerTxns.setStatus(3);
				customerTxns.setErrorCode(respSetCre.getResp().getErrCode());
			}
			custTxnRepo.save(customerTxns);
		// To Do
			//NotiMan.sendNoti(customerTxns);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
// To do
	@Override
	public void insert(ReqBalEnq reqBalEnq, Ack ack) {
		try {
			CustomerTxns customerTxns = new CustomerTxns();
			customerTxns.setReqDate(new Date());
			customerTxns.setTxncustRef(reqBalEnq.getTxn().getCustRef());
			customerTxns.setTxnId(reqBalEnq.getTxn().getId());
			customerTxns.setTxnNote(reqBalEnq.getTxn().getNote());
			if (null != ack.getErr() || 0 != ack.getErrorMessages().size()) {
				customerTxns.setStatus(3);
				customerTxns.setErrorCode(Constant.ACK_ERROR);
			} else {
				customerTxns.setStatus(1);
			}
			customerTxns.setTxnType(Constant.BAL_ENQ);
			Long regvpaId = getRegVpaIdOnVpa(reqBalEnq.getPayer().getAddr());
			customerTxns.setRegId(regvpaId);
			PayerType payer = reqBalEnq.getPayer();
			if (null != payer) {
				customerTxns.setPayerName(payer.getName());
				customerTxns.setPayerVpa(payer.getAddr());
				if (null != payer.getAc()) {
					List<Detail> acDetailsPayer = payer.getAc().getDetail();
					for (Detail detail : acDetailsPayer) {
						if (AccountDetailType.ACNUM.value().equalsIgnoreCase(detail.getName().value())) {
							customerTxns.setPayerAccNo(detail.getValue());
							customerTxns.setCustomerHistory(ConstantI.CONST_BAL_ENQ_TEXT + detail.getValue());
						}
						if (AccountDetailType.ACTYPE.value().equalsIgnoreCase(detail.getName().value())) {
							customerTxns.setPayerAcType(detail.getValue());
						}
						if (AccountDetailType.IFSC.value().equalsIgnoreCase(detail.getName().value())) {
							customerTxns.setPayerAccIFSC(detail.getValue());
						}
						if (AccountDetailType.MMID.value().equalsIgnoreCase(detail.getName().value())) {
							customerTxns.setPayerMMID(detail.getValue());
						}
						if (AccountDetailType.MOBNUM.value().equalsIgnoreCase(detail.getName().value())) {
							customerTxns.setPayerMobileNo(detail.getValue());
						}
					}
				}
			}
			custTxnRepo.save(customerTxns);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	@Override
	public void insert(ReqListAccount reqListAccount, Ack ack) {
		try {
			CustomerTxns customerTxns = new CustomerTxns();
			customerTxns.setReqDate(new Date());
			customerTxns.setTxncustRef(reqListAccount.getTxn().getCustRef());
			customerTxns.setTxnId(reqListAccount.getTxn().getId());
			customerTxns.setTxnNote(reqListAccount.getTxn().getNote());
			if (null != ack.getErr() || 0 != ack.getErrorMessages().size()) {
				customerTxns.setStatus(3);
				customerTxns.setErrorCode(Constant.ACK_ERROR);
			} else {
				customerTxns.setStatus(1);
			}

			customerTxns.setTxnType(Constant.LIST_ACCOUNT);

			Long regvpaId = getRegVpaIdOnVpa(reqListAccount.getPayer().getAddr());
			customerTxns.setRegId(regvpaId);
			customerTxns.setPayerMobileNo(reqListAccount.getLink().getValue());
			PayerType payer = reqListAccount.getPayer();
			if (null != payer) {
				customerTxns.setPayerName(payer.getName());
				customerTxns.setPayerVpa(payer.getAddr());
				if (null != payer.getAc()) {
					List<Detail> acDetailsPayer = payer.getAc().getDetail();
					String accNoIfsc = "";
					for (Detail detail : acDetailsPayer) {
						if (AccountDetailType.IFSC.value().equalsIgnoreCase(detail.getName().value())) {
							customerTxns.setPayerAccIFSC(detail.getValue());
							accNoIfsc = accNoIfsc + " " + detail.getValue();
						}

					}
					customerTxns.setCustomerHistory(
							ConstantI.CONST_REQ_ACC_TEXT + accNoIfsc + ConstantI.CONST_WITH + reqListAccount.getLink().getValue());
				}
			}
			custTxnRepo.save(customerTxns);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	@Override
	public void insert(ReqOtp reqOtp, Ack ack) {
		try {
			CustomerTxns customerTxns = new CustomerTxns();
			customerTxns.setReqDate(new Date());
			customerTxns.setTxncustRef(reqOtp.getTxn().getCustRef());
			customerTxns.setTxnId(reqOtp.getTxn().getId());
			customerTxns.setTxnNote(reqOtp.getTxn().getNote());
			if (null != ack.getErr() || 0 != ack.getErrorMessages().size()) {
				customerTxns.setStatus(3);
				customerTxns.setErrorCode(Constant.ACK_ERROR);
			} else {
				customerTxns.setStatus(1);
			}
			customerTxns.setTxnType(Constant.REQ_OTP);
			Long regvpaId = getRegVpaIdOnVpa(reqOtp.getPayer().getAddr());
			customerTxns.setRegId(regvpaId);
			PayerType payer = reqOtp.getPayer();
			if (null != payer) {
				customerTxns.setPayerName(payer.getName());
				customerTxns.setPayerVpa(payer.getAddr());
				if (null != payer.getAc()) {
					List<Detail> acDetailsPayer = payer.getAc().getDetail();
					String accNoIfsc = ConstantI.CONST_BLANK;
					for (Detail detail : acDetailsPayer) {
						if (AccountDetailType.IFSC.value().equalsIgnoreCase(detail.getName().value())) {
							customerTxns.setPayerAccIFSC(detail.getValue());
							accNoIfsc = accNoIfsc + ConstantI.CONST_SINGLE_BLANK + detail.getValue();
						}
						if (AccountDetailType.ACNUM.value().equalsIgnoreCase(detail.getName().value())) {
							customerTxns.setPayerAccNo(detail.getValue());
							accNoIfsc = accNoIfsc + ConstantI.CONST_SINGLE_BLANK + detail.getValue();
						}
					}
					customerTxns.setCustomerHistory(ConstantI.CONST_REQ_ACC_TEXT + accNoIfsc);
				}
			}
			custTxnRepo.save(customerTxns);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	@Override
	public void insert(ReqPay reqPay, Ack ack) {
		try {
			CustomerTxns customerTxns = new CustomerTxns();
			customerTxns.setReqDate(new Date());
			customerTxns.setTxncustRef(reqPay.getTxn().getCustRef());
			customerTxns.setTxnId(reqPay.getTxn().getId());
			customerTxns.setTxnNote(reqPay.getTxn().getNote());
			customerTxns.setPayeeType(reqPay.getPayees().getPayee().get(0).getType().value());
			if (null != ack.getErr() || 0 != ack.getErrorMessages().size()) {
				customerTxns.setStatus(3);
				customerTxns.setErrorCode(Constant.ACK_ERROR);
			} else {
				customerTxns.setStatus(1);
			}
			if (ConstantI.PAY.equalsIgnoreCase(reqPay.getTxn().getType().value())) {
				customerTxns.setTxnType(Constant.PAY_SEND);
				customerTxns.setCustomerHistory(reqPay.getPayer().getAddr() + ConstantI.CONST_SEND_MONEY_TO
						+ reqPay.getPayees().getPayee().get(0).getAddr());
				Long regvpaId = getRegVpaIdOnVpa(reqPay.getPayer().getAddr());
				customerTxns.setRegId(regvpaId);

			} else if (ConstantI.COLLECT.equalsIgnoreCase(reqPay.getTxn().getType().value())) {

				if (InitiationMode.MANDATE.toString().equals(reqPay.getTxn().getInitiationMode())) {
					customerTxns.setMandateUmn(reqPay.getPayer().getAddr());
				}

				customerTxns.setTxnType(Constant.COLLECT_SEND);
				customerTxns.setCustomerHistory(reqPay.getPayees().getPayee().get(0).getAddr() + ConstantI.CONST_REQ_MONEY_TO
						+ reqPay.getPayer().getAddr());
				Long regvpaId = getRegVpaIdOnVpa(reqPay.getPayees().getPayee().get(0).getAddr());
				customerTxns.setRegId(regvpaId);
				List<Rule> ruleList = reqPay.getTxn().getRules().getRule();
				for (int i = 0; i < ruleList.size(); i++) {
					if (ExpireRuleConstant.EXPIREAFTER.value().toString()
							.equalsIgnoreCase(ruleList.get(i).getName().toString())) {
						customerTxns.setCollectExpiry(ruleList.get(i).getValue());
						Calendar c = Calendar.getInstance();
						c.setTime(new Date());
						c.add(Calendar.DATE, 1);

					}
				}
			}
			PayerType payer = reqPay.getPayer();
			if (null != payer) {
				customerTxns.setPayerName(payer.getName());
				customerTxns.setPayerVpa(payer.getAddr());
				if (null != payer.getAc()) {
					List<Detail> acDetailsPayer = payer.getAc().getDetail();
					for (Detail detail : acDetailsPayer) {
						if (AccountDetailType.ACNUM.value().equalsIgnoreCase(detail.getName().value())) {
							customerTxns.setPayerAccNo(detail.getValue());
						}
						if (AccountDetailType.ACTYPE.value().equalsIgnoreCase(detail.getName().value())) {
							customerTxns.setPayerAcType(detail.getValue());
						}
						if (AccountDetailType.IFSC.value().equalsIgnoreCase(detail.getName().value())) {
							customerTxns.setPayerAccIFSC(detail.getValue());
							customerTxns.setPayerBankName(cacheCustomService.getBankNameByIfsc(detail.getValue()));
						}
						if (AccountDetailType.MMID.value().equalsIgnoreCase(detail.getName().value())) {
							customerTxns.setPayerMMID(detail.getValue());
						}
						if (AccountDetailType.MOBNUM.value().equalsIgnoreCase(detail.getName().value())) {
							customerTxns.setPayerMobileNo(detail.getValue());
						}
					}
				}
			}
			List<PayeeType> payeeList = reqPay.getPayees().getPayee();
			if (null != payeeList) {
				for (PayeeType payeeType : payeeList) {
					customerTxns.setPayeeName(payeeType.getName());
					customerTxns.setPayeeVpa(payeeType.getAddr());
					if (null != payeeType.getAc()) {
						List<Detail> acDetailsPayee = payeeType.getAc().getDetail();
						for (Detail detail : acDetailsPayee) {
							if (AccountDetailType.ACNUM.value().equalsIgnoreCase(detail.getName().value())) {
								customerTxns.setPayeeAccNo(detail.getValue());
							}
							if (AccountDetailType.ACTYPE.value().equalsIgnoreCase(detail.getName().value())) {
								customerTxns.setPayeeAcType(detail.getValue());
							}
							if (AccountDetailType.IFSC.value().equalsIgnoreCase(detail.getName().value())) {
								customerTxns.setPayeeAccIFSC(detail.getValue());
								customerTxns.setPayeeBankName(cacheCustomService.getBankNameByIfsc(detail.getValue()));
							}
							if (AccountDetailType.MMID.value().equalsIgnoreCase(detail.getName().value())) {
								customerTxns.setPayeeMMID(detail.getValue());
							}
							if (AccountDetailType.MOBNUM.value().equalsIgnoreCase(detail.getName().value())) {
								customerTxns.setPayeeMobileNo(detail.getValue());
							}
						}
					}
				}
			}

			if (customerTxns.getPayeeVpa().endsWith(ConstantI.CONST_URL_IFSC_NPCI)) {
				customerTxns.setPayeeAccNo(
						customerTxns.getPayeeVpa().substring(0, customerTxns.getPayeeVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE)));
				customerTxns.setPayeeAccIFSC(customerTxns.getPayeeVpa().substring(
						customerTxns.getPayeeVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE) + 1, customerTxns.getPayeeVpa().length() - 10));

			} else if (customerTxns.getPayeeVpa().endsWith(ConstantI.CONST_URL_IIN_NPCI)) {
				customerTxns.setPayeeUidNum(
						customerTxns.getPayeeVpa().substring(0, customerTxns.getPayeeVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE)));
				customerTxns.setPayeeIin(customerTxns.getPayeeVpa().substring(
						customerTxns.getPayeeVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE) + 1, customerTxns.getPayeeVpa().length() - 9));

			} else if (customerTxns.getPayeeVpa().endsWith(ConstantI.CONST_URL_AADHAR_NPCI)) {
				customerTxns.setPayeeUidNum(
						customerTxns.getPayeeVpa().substring(0, customerTxns.getPayeeVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE)));

			} else if (customerTxns.getPayeeVpa().endsWith(ConstantI.CONST_URL_MMID_NPCI)) {
				customerTxns.setPayeeMobileNo(
						customerTxns.getPayeeVpa().substring(0, customerTxns.getPayeeVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE)));
				customerTxns.setPayeeMMID(customerTxns.getPayeeVpa().substring(
						customerTxns.getPayeeVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE) + 1, customerTxns.getPayeeVpa().length() - 10));
			}

			if (customerTxns.getPayerVpa().endsWith(ConstantI.CONST_URL_IFSC_NPCI)) {
				customerTxns.setPayerAccNo(
						customerTxns.getPayerVpa().substring(0, customerTxns.getPayerVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE)));
				customerTxns.setPayerAccIFSC(customerTxns.getPayerVpa().substring(
						customerTxns.getPayerVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE) + 1, customerTxns.getPayerVpa().length() - 10));

			} else if (customerTxns.getPayerVpa().endsWith(ConstantI.CONST_URL_IIN_NPCI)) {
				customerTxns.setPayerUidNum(
						customerTxns.getPayerVpa().substring(0, customerTxns.getPayerVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE)));
				customerTxns.setPayerIin(customerTxns.getPayerVpa().substring(
						customerTxns.getPayerVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE) + 1, customerTxns.getPayerVpa().length() - 9));

			} else if (customerTxns.getPayerVpa().endsWith(ConstantI.CONST_URL_AADHAR_NPCI)) {
				customerTxns.setPayerUidNum(
						customerTxns.getPayerVpa().substring(0, customerTxns.getPayerVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE)));

			} else if (customerTxns.getPayerVpa().endsWith(ConstantI.CONST_URL_MMID_NPCI)) {
				customerTxns.setPayerMobileNo(
						customerTxns.getPayerVpa().substring(0, customerTxns.getPayerVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE)));
				customerTxns.setPayerMMID(customerTxns.getPayerVpa().substring(
						customerTxns.getPayerVpa().indexOf(ConstantI.CHAR_CONST_AT_RATE) + 1, customerTxns.getPayerVpa().length() - 10));
			}

			customerTxns.setAmount(reqPay.getPayer().getAmount().getValue());
			custTxnRepo.save(customerTxns);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	@Override
	public void insert(ReqRegMob reqRegMob, Ack ack) {
		try {
			CustomerTxns customerTxns = new CustomerTxns();
			customerTxns.setReqDate(new Date());
			customerTxns.setTxncustRef(reqRegMob.getTxn().getCustRef());
			customerTxns.setTxnId(reqRegMob.getTxn().getId());
			customerTxns.setTxnNote(reqRegMob.getTxn().getNote());
			if (null != ack.getErr() || 0 != ack.getErrorMessages().size()) {
				customerTxns.setStatus(3);
				customerTxns.setErrorCode(Constant.ACK_ERROR);
			} else {
				customerTxns.setStatus(1);
			}
			customerTxns.setTxnType(Constant.REG_MOB);

			Long regvpaId = getRegVpaIdOnVpa(reqRegMob.getPayer().getAddr());
			customerTxns.setRegId(regvpaId);
			PayerType payer = reqRegMob.getPayer();
			if (null != payer) {
				customerTxns.setPayerName(payer.getName());
				customerTxns.setPayerVpa(payer.getAddr());
				if (null != payer.getAc()) {
					List<Detail> acDetailsPayer = payer.getAc().getDetail();
					String accNoIfsc = "";
					for (Detail detail : acDetailsPayer) {
						if (AccountDetailType.IFSC.value().equalsIgnoreCase(detail.getName().value())) {
							customerTxns.setPayerAccIFSC(detail.getValue());
							accNoIfsc = accNoIfsc + " " + detail.getValue();
						}
						if (AccountDetailType.ACNUM.value().equalsIgnoreCase(detail.getName().value())) {
							customerTxns.setPayerAccNo(detail.getValue());
							accNoIfsc = accNoIfsc + " " + detail.getValue();
						}

					}
					customerTxns.setCustomerHistory(ConstantI.CONST_REQ_REGMOB_TEXT + accNoIfsc);
				}
			}
			custTxnRepo.save(customerTxns);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	@Override
	public void insert(ReqSetCre reqSetCre, Ack ack) {
		try {
			CustomerTxns customerTxns = new CustomerTxns();
			customerTxns.setReqDate(new Date());
			customerTxns.setTxncustRef(reqSetCre.getTxn().getCustRef());
			customerTxns.setTxnId(reqSetCre.getTxn().getId());
			customerTxns.setTxnNote(reqSetCre.getTxn().getNote());
			if (null != ack.getErr() || 0 != ack.getErrorMessages().size()) {
				customerTxns.setStatus(3);
				customerTxns.setErrorCode(Constant.ACK_ERROR);
			} else {
				customerTxns.setStatus(1);
			}

			customerTxns.setTxnType(Constant.SET_PIN);

			Long regvpaId = getRegVpaIdOnVpa(reqSetCre.getPayer().getAddr());
			customerTxns.setRegId(regvpaId);
			PayerType payer = reqSetCre.getPayer();
			if (null != payer) {
				customerTxns.setPayerName(payer.getName());
				customerTxns.setPayerVpa(payer.getAddr());
				if (null != payer.getAc()) {
					List<Detail> acDetailsPayer = payer.getAc().getDetail();
					String accNoIfsc = "";
					for (Detail detail : acDetailsPayer) {
						if (AccountDetailType.IFSC.value().equalsIgnoreCase(detail.getName().value())) {
							customerTxns.setPayerAccIFSC(detail.getValue());
							accNoIfsc = accNoIfsc + " " + detail.getValue();
						}
						if (AccountDetailType.ACNUM.value().equalsIgnoreCase(detail.getName().value())) {
							customerTxns.setPayerAccNo(detail.getValue());
							accNoIfsc = accNoIfsc + " " + detail.getValue();
						}

					}
					customerTxns.setCustomerHistory(ConstantI.CONST_REQ_REGMOB_TEXT + accNoIfsc);
				}
			}
			custTxnRepo.save(customerTxns);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	@Override
	public boolean update(RespListAccount respListAccount) {
		Registration regDetails= null;
		try {
			CustomerTxns customerTxns = custTxnRepo.findByTxnIdAndTxnType(respListAccount.getTxn().getId(),Constant.LIST_ACCOUNT);
			customerTxns.setRespDate(new Date());
			if (ResultType.SUCCESS.toString().equalsIgnoreCase(respListAccount.getResp().getResult())) {
				customerTxns.setStatus(2);
			} else if (ResultType.PARTIAL.toString().equalsIgnoreCase(respListAccount.getResp().getResult())) {
				customerTxns.setStatus(4);
			} else {
				customerTxns.setStatus(3);
				customerTxns.setErrorCode(respListAccount.getResp().getErrCode());
			}
			custTxnRepo.save(customerTxns);
			if(respListAccount.getResp().getErrCode().equalsIgnoreCase(Constant.ERROR_CODE_XH)) {

				regDetails=regDao.getRegistrationDetails(customerTxns.getRegId().longValue());
				if(regDetails!=null) {
					reqRespListAccDao.insertUpdateRiskCount(regDetails);
				}
			}
			return true;
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
			return false;
		}
	}
	
	@Override
	public boolean isHonouredTxn(String mandateUmn, String vpa) {
		try {
			//long c = custTxnRepo.countByMandateUmnAndPayerVpa(mandateUmn, vpa, ConstantI.STATUS_5, new Date());
			long c = 0l;
			if (c >= 1) {
				return true;
			}
		} catch (Exception e) {
			log.error("Error: {}", e);
			ErrorLog.sendError(e, CustomerTxnsDaoImpl.class);
		}
		return false;
	}

	@Override
	public void updateRespPay(RespPay respPay) {
		try {
			CustomerTxns customerTxns = null;
			if (ConstantI.PAY.equalsIgnoreCase(respPay.getTxn().getType().value())) {
				customerTxns = getCustTxnsByTxnIdAndType(respPay.getTxn().getId(), Constant.PAY_SEND);
			} else if (ConstantI.COLLECT.equalsIgnoreCase(respPay.getTxn().getType().value())) {
				customerTxns = getCustTxnsByTxnIdAndType(respPay.getTxn().getId(), Constant.COLLECT_SEND);
			} else {
				log.warn("unknown txnType ie={}", respPay.getTxn().getType().value());
			}
			if (customerTxns == null) {
				customerTxns = new CustomerTxns();
			}
			customerTxns.setRespDate(new Date());
			if (ResultType.SUCCESS.toString().equalsIgnoreCase(respPay.getResp().getResult().value())) {
				customerTxns.setStatus(2);
			} else if (ResultType.PARTIAL.toString().equalsIgnoreCase(respPay.getResp().getResult().value())) {
				customerTxns.setStatus(4);
			} else if (ResultType.DEEMED.toString().equalsIgnoreCase(respPay.getResp().getResult().value())) {
				customerTxns.setStatus(5);
			} else {
				// customerTxns.setStatus(3);
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
				customerTxns.setErrorCode(respCode);
				if (ConstantI.U69.equals(respCode)) {
					customerTxns.setStatus(6);
				} else if (respCode.matches(ConstantI.ERROR_RESP_DECLINED)) {
					customerTxns.setStatus(ConstantI.COLLECT_DECLINE);
				} else if (respCode.matches(ConstantI.ERROR_RESP_BLOCKED)) {
					customerTxns.setStatus(ConstantI.COLLECT_BLOCK);
				} else if (respCode.matches(ConstantI.ERROR_RESP_SPAM)) {
					customerTxns.setStatus(ConstantI.COLLECT_SPAM);
				} else {
					customerTxns.setStatus(ConstantI.STATUS_3);
				}
			}
			custTxnRepo.save(customerTxns);
			notiService.sendNoti(customerTxns);
		} catch (Exception e) {
			log.error("error in update respPay CustomerTxns {}", e);
			ErrorLog.sendError(e, CustomerTxnsDaoImpl.class);
		}
	}
	
	private CustomerTxns getCustTxnsByTxnIdAndType(String txnId, int txnType) {
		try {
			List<CustomerTxns> list = custTxnRepo.findByTxnIdAndTxnType(txnId, txnType);
			if (list.size() == 1) {
				return list.get(0);
			} else if (list.size() == 0) {
				log.warn("No CustomerTxnsEntity found for txnId={} and type={}", txnId, txnType);
			} else {
				log.warn("Found more than  CustomerTxnsEntity for txnId={} and type={}", txnId, txnType);
			}
		} catch (Exception e) {
			log.error("error{}", e);
			ErrorLog.sendError(e, CustomerTxnsDaoImpl.class);
		}
		return null;
	}

	@Override
	public void insertOnusBalReqResp(ReqResp reqResp) {
		try {
			CustomerTxns customerTxns = new CustomerTxns();
			customerTxns.setReqDate(new Date());
			customerTxns.setTxnId(reqResp.getTxnId());
			customerTxns.setTxnNote(reqResp.getTxnNote());
			customerTxns.setStatus(3);
			customerTxns.setTxnType(Constant.BAL_ENQ);
			long regvpaId = getRegIdByVpa(reqResp.getPayerAddr());
			customerTxns.setRegId(regvpaId);
			customerTxns.setPayerName(reqResp.getPayerName());
			customerTxns.setPayerVpa(reqResp.getPayerAddr());
			customerTxns.setPayerAccNo(reqResp.getPayerAcNum());
			customerTxns.setCustomerHistory(ConstantI.CONST_BAL_ENQ_TEXT + reqResp.getPayerAcNum());
			customerTxns.setPayerAcType(reqResp.getPayerAddrType());
			customerTxns.setPayerAccIFSC(reqResp.getPayerIfsc());
			customerTxns.setPayerMMID(reqResp.getPayerMmid());
			customerTxns.setPayerMobileNo(
					reqResp.getPayerMobileNo() == null ? reqResp.getPayerDeviceMobile() : reqResp.getPayerMobileNo());
			customerTxns.setRespDate(new Date());
			if (ConstantI.CODE_SUCCESS.equals(reqResp.getRespCode())) {
				customerTxns.setStatus(2);
			} else {
				customerTxns.setStatus(3);
				customerTxns.setErrorCode(reqResp.getRespCode());
			}
			custTxnRepo.save(customerTxns);

		} catch (Exception e) {
			log.error("error{}", e);
			ErrorLog.sendError(e, CustomerTxnsDaoImpl.class);
		}

		
	}

	@Override
	public void insertOnusPay(ReqResp reqResp, Date reqDate, Date respDate) {
		try {
			CustomerTxns customerTxns = new CustomerTxns();
			customerTxns.setReqDate(reqDate);
			customerTxns.setRespDate(respDate);
			customerTxns.setTxncustRef(reqResp.getRrn());
			customerTxns.setTxnId(reqResp.getTxnId());
			customerTxns.setTxnNote(reqResp.getTxnNote());
			customerTxns.setPayeeType(reqResp.getPayeeType());
			customerTxns.setTxnType(Constant.PAY_SEND);
			customerTxns.setCustomerHistory(
					reqResp.getPayerAddr() + ConstantI.CONST_SEND_MONEY_TO + reqResp.getPayeeAddr());
			long regvpaId = getRegIdByVpa(reqResp.getPayerAddr());
			customerTxns.setRegId(regvpaId);
			customerTxns.setAmount(reqResp.getPayeeAmount());
			customerTxns.setPayerName(reqResp.getPayerName());
			customerTxns.setPayerVpa(reqResp.getPayerAddr());

			customerTxns.setPayerAccNo(reqResp.getPayerAcNum());
			customerTxns.setPayerAcType(reqResp.getPayerAcType());
			customerTxns.setPayerAccIFSC(reqResp.getPayerIfsc());
			customerTxns.setPayerMMID(reqResp.getPayerMmid());
			customerTxns.setPayerMobileNo(
					reqResp.getPayerMobileNo() == null ? reqResp.getPayerDeviceMobile() : reqResp.getPayerMobileNo());

			// reqResp.getPayeeAddrType();
			customerTxns.setPayeeVpa(reqResp.getPayeeAddr());
			customerTxns.setPayeeName(reqResp.getPayeeName());
			customerTxns.setPayeeAccIFSC(reqResp.getPayeeIfsc());
			customerTxns.setPayeeAccNo(reqResp.getPayeeAcNum());
			customerTxns.setPayeeAcType(reqResp.getPayeeAcType());
			customerTxns.setPayeeMMID(reqResp.getPayeeMmid());
			customerTxns.setPayeeMobileNo(
					reqResp.getPayeeMobileNo() == null ? reqResp.getPayeeDeviceMobile() : reqResp.getPayeeMobileNo());
			if (ConstantI.CONSTANT_0.equals(reqResp.getRespCode())) {
				// success
				customerTxns.setStatus(ConstantI.STATUS_2);
			} else {
				customerTxns.setStatus(ConstantI.STATUS_3);
			}
			customerTxns.setErrorCode(reqResp.getRespCode());
			custTxnRepo.save(customerTxns);
		} catch (Exception e) {
			log.error("error{}", e);
			ErrorLog.sendError(e, CustomerTxnsDaoImpl.class);
		}
		
	}
	
	@Override
	public void update(ReqTxnConfirmation reqTxnConfirmation) {
		try {
			CustomerTxns customerTxns = null;
			// List<Ref> refs =
			// reqTxnConfirmation.getTxnConfirmation().getRef();

			if (ConstantI.PAY.equalsIgnoreCase(reqTxnConfirmation.getTxnConfirmation().getType().value())) {
				customerTxns = getCustTxnsByTxnIdAndType(reqTxnConfirmation.getTxn().getOrgTxnId(),
						Constant.PAY_RECEIVE);

			} else if (ConstantI.COLLECT.equalsIgnoreCase(reqTxnConfirmation.getTxnConfirmation().getType().value())) {
				customerTxns = getCustTxnsByTxnIdAndType(reqTxnConfirmation.getTxn().getOrgTxnId(),
						Constant.COLLECT_RECEIVE_TYPE);
			}
			customerTxns.setRespDate(new Date());

			if (ResultType.SUCCESS.value().equalsIgnoreCase(reqTxnConfirmation.getTxnConfirmation().getOrgStatus())) {
				customerTxns.setStatus(2);
			} else if (ResultType.PARTIAL.toString()
					.equalsIgnoreCase(reqTxnConfirmation.getTxnConfirmation().getOrgStatus())) {
				List<Ref> refs = reqTxnConfirmation.getTxnConfirmation().getRef();
				for (Ref ref : refs) {
					if (RefType.PAYER.equals(ref.getType())) {
						customerTxns.setAmount(ref.getSettAmount());
					}
				}
				customerTxns.setStatus(4);
			} else if (ResultType.DEEMED.toString()
					.equalsIgnoreCase(reqTxnConfirmation.getTxnConfirmation().getOrgStatus())) {
				customerTxns.setStatus(5);
			} else {
				String respCode = reqTxnConfirmation.getTxnConfirmation().getOrgErrCode();
				List<Ref> apNo = reqTxnConfirmation.getTxnConfirmation().getRef();
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
				customerTxns.setErrorCode(respCode);
				if (ConstantI.U69.equals(respCode)) {
					customerTxns.setStatus(6);
				} else if (respCode.matches(ConstantI.ERROR_RESP_DECLINED)) {
					customerTxns.setStatus(ConstantI.COLLECT_DECLINE);
				} else if (respCode.matches(ConstantI.ERROR_RESP_BLOCKED)) {
					customerTxns.setStatus(ConstantI.COLLECT_BLOCK);
				} else if (respCode.matches(ConstantI.ERROR_RESP_SPAM)) {
					customerTxns.setStatus(ConstantI.COLLECT_SPAM);
				} else {
					customerTxns.setStatus(3);
				}
				// customerTxns.setErrorCode(reqTxnConfirmation.getTxnConfirmation().getOrgErrCode());
			}
			custTxnRepo.save(customerTxns);
			//notiService.sendNoti(customerTxns);
		} catch (Exception e) {
			log.error("error in update respPay CustomerTxns {}", e);
			ErrorLog.sendError(e, CustomerTxnsDaoImpl.class);
		}

	}

}
