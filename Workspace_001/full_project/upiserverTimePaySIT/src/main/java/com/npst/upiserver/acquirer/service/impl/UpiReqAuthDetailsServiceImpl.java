package com.npst.upiserver.acquirer.service.impl;

import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.UpiReqAuthDetailsService;
import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.constant.ErrorCode;
import com.npst.upiserver.constant.InitiationMode;
import com.npst.upiserver.constant.MandateStatus;
import com.npst.upiserver.dao.BlockedByCustomerDao;
import com.npst.upiserver.dao.CustomerAccountDao;
import com.npst.upiserver.dao.CustomerMandatesDao;
import com.npst.upiserver.dao.CustomerTxnsDao;
import com.npst.upiserver.dao.RegistrationDao;
import com.npst.upiserver.dao.ReqRespAuthDetailsDao;
import com.npst.upiserver.dao.SpamVpaRuleDao;
import com.npst.upiserver.dto.RegDto;
import com.npst.upiserver.entity.CustomerAccount;
import com.npst.upiserver.entity.CustomerMandatesEntity;
import com.npst.upiserver.npcischema.AccountDetailType;
import com.npst.upiserver.npcischema.AccountType;
import com.npst.upiserver.npcischema.AccountType.Detail;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.AddressType;
import com.npst.upiserver.npcischema.CredSubType;
import com.npst.upiserver.npcischema.CredType;
import com.npst.upiserver.npcischema.CredsType;
import com.npst.upiserver.npcischema.CredsType.Cred;
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
import com.npst.upiserver.npcischema.PayeeType;
import com.npst.upiserver.npcischema.PayeesType;
import com.npst.upiserver.npcischema.PayerConstant;
import com.npst.upiserver.npcischema.PayerType;
import com.npst.upiserver.npcischema.RatingType;
import com.npst.upiserver.npcischema.ReqAuthDetails;
import com.npst.upiserver.npcischema.RespAuthDetails;
import com.npst.upiserver.npcischema.RespType;
import com.npst.upiserver.npcischema.ResultType;
import com.npst.upiserver.npcischema.WhiteListedConstant;
import com.npst.upiserver.repo.CustomerAccountRepository;
import com.npst.upiserver.service.NotificationService;
import com.npst.upiserver.service.NpciUpiRestConService;
import com.npst.upiserver.service.impl.QrIntantServiceImpl;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.NpciSchemaUtil;
import com.npst.upiserver.util.Util;

@Service
public class UpiReqAuthDetailsServiceImpl implements UpiReqAuthDetailsService {
	private static final Logger log = LoggerFactory.getLogger(UpiReqAuthDetailsServiceImpl.class);
	private static final DateTimeFormatter formatter_ddMMyyyy = DateTimeFormatter.ofPattern("ddMMyyyy");
	private static final long COLLECTLIMIT2K = Long.parseLong(Util.getProperty("COLLECTLIMIT2K"));

	@Autowired
	private NpciUpiRestConService npciUpiRestConService;
	@Autowired
	private CustomerAccountRepository customerAccountRepo;
	@Autowired
	private RegistrationDao registrationDao;
	@Autowired
	private ReqRespAuthDetailsDao reqRespAuthDetailsDao;
	@Autowired
	private CustomerTxnsDao customerTxnsDao;
	@Autowired
	private SpamVpaRuleDao spamVpaRuleDao;
	@Autowired
	private CustomerMandatesDao customerMandatesDao;
	@Autowired
	private NotificationService notiService;
	@Autowired
	private QrIntantServiceImpl lstVAEMerchant;
	
	@Autowired
	CustomerAccountDao			custAccDao;

	@Autowired
	BlockedByCustomerDao		blockedByCustDao;
	
	@Override
	public void acquirerProcess(final ReqAuthDetails reqAuthDetails) {
		log.info("reqAuthDetails {}", reqAuthDetails);
		try {
			String ts = Util.getTS();
			String msgId = Util.uuidGen();
			customerTxnsDao.insert(reqAuthDetails);
			reqRespAuthDetailsDao.insertReq(reqAuthDetails);//will do it latter
			RespAuthDetails respAuthDetails = new RespAuthDetails();
			HeadType head = new HeadType();
			head.setMsgId(msgId);
			head.setOrgId(Constant.orgId);
			head.setTs(ts);
			head.setVer(Constant.headVer);
			respAuthDetails.setHead(head);
			log.info("Txn Type {}", reqAuthDetails.getTxn().getType().value());
			respAuthDetails.setTxn(reqAuthDetails.getTxn());
			if (ConstantI.COLLECT.equalsIgnoreCase(reqAuthDetails.getTxn().getType().value())) {
				// TODO need discussion
				log.info("Initiation Mode {}",reqAuthDetails.getTxn().getInitiationMode());
				if (InitiationMode.MANDATE.getMode().equals(reqAuthDetails.getTxn().getInitiationMode()) || InitiationMode.MANDATEQR.getMode().equals(reqAuthDetails.getTxn().getInitiationMode())) {
					//procCollect(reqAuthDetails, respAuthDetails);

					procMandateCollect(reqAuthDetails, respAuthDetails);
				} else {
					procCollect(reqAuthDetails, respAuthDetails);
				}
			} else if (ConstantI.PAY.equalsIgnoreCase(reqAuthDetails.getTxn().getType().value()) || ConstantI.PAY.equalsIgnoreCase(reqAuthDetails.getTxn().getSubType().value())) // to be discussed 
			{
				procPay(reqAuthDetails, respAuthDetails);
			} else {
				log.error("TxnType mismatch neither COLLECT nor PAY");
				// Validation error ZD OR XB
				// respAuthDetails.setResp(resp);
			}

		} catch (Exception e) {
			log.error("inside acquirerProcess {}", e);
			ErrorLog.sendError(e, UpiReqAuthDetailsServiceImpl.class);
		}

	}

	private void procPay(final ReqAuthDetails reqAuthDetails, final RespAuthDetails respAuthDetails) {
		try {
			log.info("procPay  ");
			RespType resp = new RespType();
			resp.setReqMsgId(reqAuthDetails.getHead().getMsgId());
			PayeesType payees = reqAuthDetails.getPayees();
			PayeesType payeesNew = new PayeesType();
			List<PayeeType> payeeList = payees.getPayee();
			List<PayeeType> payeeListNew = payeesNew.getPayee();
			Iterator<PayeeType> iterator = payeeList.iterator();
			// TODO discussion
			while (iterator.hasNext()) {
				boolean flag = false;
				PayeeType payee = iterator.next();
				PayeeType payeeNew = new PayeeType();
				List<CustomerAccount> listaccounts = customerAccountRepo
						.findByAccvirtualid(payee.getAddr().trim().toLowerCase());
				if (0 == listaccounts.size()) {
					resp.setResult(ConstantI.FAILURE);
					resp.setErrCode(ConstantI.RESP_ZH);
				} else {
					//Validation Requred for SA error code.
					boolean isActive = registrationDao.isActiveRegistration(listaccounts);
					log.info("isActive is as {}",isActive);
					if (isActive) {
		
						int i = 1;
						for (CustomerAccount listaccount : listaccounts) {
							if (1 == listaccount.getDefaccount() && 1 == listaccount.getStatus()) {
								/*if (ConstantI.MIGRATED_M.equalsIgnoreCase(listaccount.getMigrated())) {
									log.warn(
											"ReqAuth PAY MIGRATED_M USER RESPONSE , NOTACTIVEVIRTUALADDRESS for VPA={}",
											listaccount.getAccvirtualid());
									resp.setResult(ConstantI.FAILURE);
									resp.setErrCode(Constant.NOTACTIVEVIRTUALADDRESS);
									log.info("inside MIGRATED_M");
									break;
								}*/
								String PayerAcType=payerAcType(reqAuthDetails);
								log.info("PayerAcType is {}",PayerAcType);
								if (!accTypeValidations(listaccount,PayerAcType)){
									log.info("account validations");
									resp.setResult(ConstantI.FAILURE);
									resp.setErrCode(ConstantI.TXN_NOT_ALLW_SA);
									break;
								}
								// payeeListNew = new ArrayList<>();
								resp.setResult(ConstantI.SUCCESS);
								resp.setErrCode(null);
								payeeNew.setAddr(payee.getAddr());
								payeeNew.setAmount(payee.getAmount());
								payeeNew.setCode(listaccount.getCustcode());
								payeeNew.setSeqNum(i + ConstantI.CONST_BLANK);
								payeeNew.setType(PayerConstant.fromValue(listaccount.getCusttype()));
								payeeNew.setName(payee.getName());
								InfoType info = new InfoType();
								RatingType rating = new RatingType();
								rating.setVerifiedAddress(WhiteListedConstant.TRUE);
								info.setRating(rating);
								IdentityType identity = new IdentityType();
								identity.setId(listaccount.getAccrefnumber());
								identity.setType(IdentityConstant.ACCOUNT);
								identity.setVerifiedName(listaccount.getName().toUpperCase());
								info.setIdentity(identity);
								payeeNew.setInfo(info);
								AccountType ac = new AccountType();
								ac.setAddrType(AddressType.ACCOUNT);
								List<Detail> details = ac.getDetail();
								Detail detail = new Detail();
								detail.setName(AccountDetailType.IFSC);
								detail.setValue(listaccount.getIfsc().toUpperCase());
								details.add(detail);
								detail = new Detail();
								detail.setName(AccountDetailType.ACTYPE);
								detail.setValue(listaccount.getAcctype());
								details.add(detail);
								detail = new Detail();
								detail.setName(AccountDetailType.ACNUM);
								detail.setValue(listaccount.getAccrefnumber());
								details.add(detail);
								if(ConstantI.ENTITY.equalsIgnoreCase(payee.getType().toString())) {
									payeeNew.setMerchant(getMerchantType(listaccount));
								}
									payeeNew.setAc(ac);
									payeeListNew.add(payeeNew);
									flag = true;
									i++;
									break;
								} else if (4 == listaccount.getStatus()) {
									resp.setResult(ConstantI.FAILURE);
									resp.setErrCode(Constant.NOTPERMITTED);
								} else {
									resp.setResult(ConstantI.FAILURE);
									resp.setErrCode(ConstantI.RESP_ZG);
								}
							}
						} else {
							log.info("inside last");
							resp.setResult(ConstantI.FAILURE);
							resp.setErrCode(Constant.EXPIREDVIRTUALADDRESS);
						}
					}
				
				if (!flag) {
					
				
					
					payeeListNew.add(payee);
				}
			}
			payees = payeesNew;
			
			
			respAuthDetails.setResp(resp);
			respAuthDetails.setPayees(payees);
			respAuthDetails.setPayer(reqAuthDetails.getPayer());
			String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(respAuthDetails).toString());
			log.info("response is as {} ",xmlStr);
			Ack ack = npciUpiRestConService.send(xmlStr, ConstantI.RESP_AUTH_DETAILS, reqAuthDetails.getTxn().getId());
			reqRespAuthDetailsDao.updateResp(respAuthDetails, ack);
			customerTxnsDao.update(respAuthDetails, ack);
		} catch (Exception e) {
			log.error("error in procPay {}", e);
			ErrorLog.sendError(e, UpiReqAuthDetailsServiceImpl.class);
		}

//add

	}

	private void procCollect(final ReqAuthDetails reqAuthDetails, final RespAuthDetails respAuthDetails) {
		log.info("inside procCollect:{}",reqAuthDetails.toString());
		try {
			RespType resp = new RespType();
			resp.setReqMsgId(reqAuthDetails.getHead().getMsgId());
			List<PayeeType> payee = reqAuthDetails.getPayees().getPayee();
			// TODO discussion
			String payeeVpa = "";
			List<String> spamvparules = null;
			for (PayeeType payeeType : payee) {
				payeeVpa = payeeType.getAddr().toLowerCase();
				spamvparules = spamVpaRuleDao.getSpamVpa(payeeVpa);
			}
			log.info("before chk vpa");
			if (0 == spamvparules.size()) {
				List<CustomerAccount> listaccounts = customerAccountRepo.findByAccvirtualid(reqAuthDetails.getPayer().getAddr().toLowerCase());
				if (0 == listaccounts.size()) {
					log.info("after chk vpa");
					resp.setResult(ConstantI.FAILURE);
					resp.setErrCode(Constant.INVALIDVIRTUALADDRESS);
				} else {
					Long regId = custAccDao.getRegIdByVPA(reqAuthDetails.getPayer().getAddr());
					log.debug("reg Id is {}",regId);
					if (0 == regId) {
						resp = setRespTypeCodes(resp, ConstantI.FAILURE, Constant.INVALIDVIRTUALADDRESS);
					} 
					else {
						boolean isActive = registrationDao.isActiveUser(regId);
						if (isActive) {
							log.debug("going to chk block vpa");
							boolean isBlocked = blockedByCustDao.isBlockedVpa(payeeVpa, regId);
							log.debug("is blocked vpa {}",isBlocked);
							if (isBlocked) {
								resp.setResult(ConstantI.FAILURE);
								resp.setErrCode(Constant.BLOCKBYCUSTOMER);
							} else {
								log.info("Payer Detail for requestAuthdetail:{}",reqAuthDetails.getPayer().toString());
								log.info("Payer Amount for requestAuthdetail:{}",Util.convertAmountInPaisa(reqAuthDetails.getPayer().getAmount().getValue()));
								log.info("Payee Detail for requestAuthdetail:{}",reqAuthDetails.getPayees().getPayee().get(0).toString());
								if(Util.convertAmountInPaisa(reqAuthDetails.getPayer().getAmount().getValue())>COLLECTLIMIT2K) {
									log.info("Payee type for requestAuthdetail:{}",reqAuthDetails.getPayees().getPayee().get(0).getType().toString());
									if(!ConstantI.ENTITY.equalsIgnoreCase(reqAuthDetails.getPayees().getPayee().get(0).getType().toString())) {
										log.info("PAYEE is NOT ENTITY and Amount is more than 2K ");
										resp.setResult(ConstantI.FAILURE);
										resp.setErrCode(Constant.NOTPERMITTED_2KLIMIT_CUST);
									}
									else {
									try {
										//List<ListVaeDto> lstmerchant = lstVAEMerchant.findByAddr(payeeVpa);
										log.info("checking merchant verification: {}",payeeVpa);
										if(lstVAEMerchant.isNotMerchantISVarifyed(payeeVpa)) {
											log.info("PAYEE is ENTITY But not varifyed MER ");
											resp.setResult(ConstantI.FAILURE);
											resp.setErrCode(Constant.NOTPERMITTED_NOTVERIFY_MER);
										}
									}catch(Exception e) {
										log.error("in procCollect {}", e);
										ErrorLog.sendError(e, UpiReqAuthDetailsServiceImpl.class);
										e.printStackTrace();
										
									}
									}	
								}
								
								// END of 76
								else if(isMCCLimit(reqAuthDetails)) {
									log.info("PAYEE CODE is RESTICTED ");
									resp.setResult(ConstantI.FAILURE);
									resp.setErrCode(Constant.NOTPERMITTED_FOR_MCC);// need to chenge Error code
								}
								else {
									for (CustomerAccount listaccount : listaccounts) {
										
										if (1 == listaccount.getDefaccount() && 1 == listaccount.getStatus()) {
											RegDto regvpa = registrationDao.getGCMToken(listaccount.getRegid());
											notiService.sendNoti(reqAuthDetails, regvpa);
											resp.setResult(ConstantI.SUCCESS);
											break;
										} else if (4 == listaccount.getStatus()) {
											
											resp.setResult(ConstantI.FAILURE);
											resp.setErrCode(Constant.NOTPERMITTED);
										} else {
											resp.setResult(ConstantI.FAILURE);
											resp.setErrCode(Constant.NOTACTIVEVIRTUALADDRESS);
										}
									}
								}
							}
							
							//OC 76 82
							
						} else {
							resp.setResult(ConstantI.FAILURE);
							resp.setErrCode(Constant.EXPIREDVIRTUALADDRESS);
						}
					}
					
				}
			} else {
				String rule = ConstantI.CONST_BLANK;
				for (String str : spamvparules) {
					rule = str;
					break;
				}
				resp.setResult(ConstantI.FAILURE);
				resp.setErrCode(rule);
			}
			
			
			if (ConstantI.FAILURE.equalsIgnoreCase(resp.getResult())) {
				respAuthDetails.setResp(resp);
				respAuthDetails.setPayees(reqAuthDetails.getPayees());
				respAuthDetails.setPayer(reqAuthDetails.getPayer());
				String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(respAuthDetails).toString());
				Ack ack = npciUpiRestConService.send(xmlStr, ConstantI.RESP_AUTH_DETAILS,
						reqAuthDetails.getTxn().getId());
				reqRespAuthDetailsDao.updateResp(respAuthDetails, ack);
				customerTxnsDao.update(respAuthDetails, ack);
			}

		} catch (Exception e) {
			log.error("in procCollect {}", e);
			ErrorLog.sendError(e, UpiReqAuthDetailsServiceImpl.class);
		}

	}

		void procMandateCollect(final ReqAuthDetails reqAuthDetails, final RespAuthDetails respAuthDetails) {
		try {
			RespType resp = new RespType();
			resp.setReqMsgId(reqAuthDetails.getHead().getMsgId());
			String payerVpa = reqAuthDetails.getPayer().getAddr();
			String umn = payerVpa;
			CustomerMandatesEntity customerMandates = customerMandatesDao.findByUmnAndSignValue(umn);
			
			//CustomerMandatesEntity customerMandates1 = customerMandatesDao.findByUmnAndSignValuenull(umn);
			log.info("get data from CustomerMandatesEntity {}",customerMandates);
			log.info("get data from payer name {}",customerMandates.getPayerName());
			if (customerMandates != null) {
			
			// FOR BAY PASS
				if (MandateStatus.MANDATE_PAUSED.getStatus() == customerMandates.getStatus()) {
					resp.setResult(ResultType.FAILURE.toString());
					resp.setErrCode(ErrorCode.MANDATE_PSP_QA.getUpiCode());
				} else if (MandateStatus.MANDATE_REVOKED.getStatus() == customerMandates.getStatus()) {
					resp.setResult(ResultType.FAILURE.toString());
					resp.setErrCode(ErrorCode.MANDATE_PSP_QC.getUpiCode());
				} else {
					/*LocalDateTime today = LocalDateTime.now();
					String validityend = customerMandates.getMandateValidityEnd();
					LocalDateTime validityenddate = LocalDateTime.parse(validityend, formatter_ddMMyyyy);
					if (today.isBefore(validityenddate)) {
						resp.setResult(ResultType.FAILURE.toString());
						resp.setErrCode(ErrorCode.MANDATE_PSP_QD.getUpiCode());
					}*/
					if (Double.valueOf(customerMandates.getMandateAmountvalue()) < Double
							.valueOf(reqAuthDetails.getPayer().getAmount().getValue())) { 
						/*  Infide If block
						|| (AmtRuleType.EXACT.toString().equals(customerMandates.getMandateAmountrule())
								&& !reqAuthDetails.getPayer().getAmount().getValue()
										.equals(customerMandates.getMandateAmountvalue())
								|| AmtRuleType.MAX.toString().equals(customerMandates.getMandateAmountrule())
										&& (Double
												.valueOf(reqAuthDetails.getPayer().getAmount().getValue()) > Double
														.valueOf(customerMandates.getMandateAmountvalue())))*/
						resp.setResult(ResultType.FAILURE.toString());
						resp.setErrCode(ErrorCode.MANDATE_PSP_QH.getUpiCode());
					}

					else if (!customerMandates.getPayeeVpa()
							.equalsIgnoreCase(reqAuthDetails.getPayees().getPayee().get(0).getAddr())) {
						resp.setResult(ResultType.FAILURE.toString());
						resp.setErrCode(ErrorCode.MANDATE_PSP_QI.getUpiCode());
					} /*else if (customerTxnsDao.isHonouredTxn(customerMandates.getMandateUmn(),
							reqAuthDetails.getPayer().getAddr())) {
						resp.setResult(ResultType.FAILURE.toString());
						resp.setErrCode(ErrorCode.MANDATE_PSP_QK.getUpiCode());
					}*//* else if (MandateUtil.checkForTxnDayAndRecPattern(customerMandates)) {
						resp.setResult(ResultType.FAILURE.toString());
						resp.setErrCode(ErrorCode.MANDATE_PSP_QR.getUpiCode());
					}*/ else {
						PayerType payer = reqAuthDetails.getPayer();//new PayerType();
						setPayerdetails(payer,customerMandates,reqAuthDetails);
							CredsType creds = new CredsType();
							Cred cred = new Cred();
							CredsType.Cred.Data data = new CredsType.Cred.Data();
								data.setCode("NPCI");
								data.setValue(customerMandates.getMandateSignValue());
								data.setKi("20150822");
								cred.setData(data);
								cred.setSubType(CredSubType.fromValue("DS"));
								cred.setType(CredType.fromValue("UPI-Mandate"));
								creds.getCred().add(cred);
								reqAuthDetails.getPayer().setCreds(creds);
								resp.setResult(ResultType.SUCCESS.toString());
						}
				}
		}
			else {
				resp.setResult(ResultType.FAILURE.toString());
				resp.setErrCode(ErrorCode.MANDATE_PSP_QJ.getUpiCode());
			}
			respAuthDetails.setPayer(reqAuthDetails.getPayer());
			respAuthDetails.setPayees(reqAuthDetails.getPayees());
			respAuthDetails.setResp(resp);
			if (!ConstantI.UKN.equals(resp.getErrCode())) {
				String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(respAuthDetails).toString());
				Ack ack = npciUpiRestConService.send(xmlStr, ConstantI.RESP_AUTH_DETAILS,
						reqAuthDetails.getTxn().getId());
				reqRespAuthDetailsDao.updateResp(respAuthDetails, ack);
				customerTxnsDao.update(respAuthDetails, ack);
			}
		} catch (Exception e) {
			log.error("error in proc CollectMandate {}", e);
			ErrorLog.sendError(e, UpiReqAuthDetailsServiceImpl.class);
		}

	}
	
		private boolean isMCCLimit(ReqAuthDetails reqAuthDetails) {
			boolean flag=false;
			String mccc_code=reqAuthDetails.getPayees().getPayee().get(0).getCode();
			String[] temp=(Util.getProperty("MCC_RESTRICTED")).trim().split("~");   //6540~4812~4814~7675
			for (String mccvalue : temp) {
				if(mccc_code.equals(mccvalue)) {
					flag=true;
					break;
				}
			}
			
			return flag;
		}

	private RespType setRespTypeCodes(RespType resp, final String respResult, final String respErrorCode)
			throws Exception {
		resp.setResult(ConstantI.FAILURE);
		resp.setErrCode(Constant.EXPIREDVIRTUALADDRESS);
		return resp;
	}
	
private MerchantType getMerchantType(CustomerAccount listaccount) {
		
		MerchantType merchant = new MerchantType();
		IdentifierType iden = new IdentifierType();
		iden.setMerchantGenre(MerchantGenreType.OFFLINE);
		iden.setMerchantType(MerchantIdentifierType.SMALL);
		iden.setMid(String.valueOf(listaccount.getRegid()));
		iden.setOnBoardingType(MerchantOnBoardingType.BANK);
		iden.setSid(ConstantI.MID_CONT);
		iden.setSubCode(listaccount.getCustcode());
		iden.setTid(ConstantI.MID_CONT);
		merchant.setIdentifier(iden);
		NameType nameType = new NameType();
		
		
		if(listaccount.getName().length() > ConstantI.NAME_Length) {
			
			nameType.setBrand(listaccount.getName().substring(0, 14));
			nameType.setLegal(listaccount.getName().substring(0, 14));
			nameType.setFranchise(listaccount.getName().substring(0, 14));
			
		}
		
		else {
			nameType.setBrand(listaccount.getName());
			nameType.setLegal(listaccount.getName());
			nameType.setFranchise(listaccount.getName());
			}

		nameType.setBrand(listaccount.getName());
		nameType.setLegal(listaccount.getName());
		nameType.setFranchise(listaccount.getName());
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

private boolean accTypeValidations(CustomerAccount listaccount, String payerAcType) {
	boolean isValid = false;	
	if("UOD".equalsIgnoreCase(payerAcType)) {
			
		if("UOD".equalsIgnoreCase(listaccount.getAcctype())|| "SAVINGS".equalsIgnoreCase(listaccount.getAcctype())
				|| "NRE".equalsIgnoreCase(listaccount.getAcctype())|| "NRO".equalsIgnoreCase(listaccount.getAcctype())) {
			return isValid;
		}
		
		else if ("CURRENT".equalsIgnoreCase(listaccount.getAcctype())) {

		
		if(ConstantI.ENTITY.equalsIgnoreCase(listaccount.getCusttype())) {
				isValid = true;
			}
		else if(ConstantI.TYPE_PERSON.equalsIgnoreCase(listaccount.getCusttype())) {
			return isValid;
		}
		}
}

	else if("SAVINGS".equalsIgnoreCase(payerAcType) || "NRE".equalsIgnoreCase(payerAcType) || "NRO".equalsIgnoreCase(payerAcType)) {
		
	if ("CURRENT".equalsIgnoreCase(listaccount.getAcctype()) || "NRE".equalsIgnoreCase(listaccount.getAcctype()) ||"DEFAULT".equalsIgnoreCase(listaccount.getAcctype())
			|| "NRO".equalsIgnoreCase(listaccount.getAcctype()) || "SAVINGS".equalsIgnoreCase(listaccount.getAcctype())) {
		isValid = true;
}
}

	else if("CURRENT".equalsIgnoreCase(payerAcType)) {

	isValid = true;//NEED to DISCUSSED with NPCI
}

	else if("NRE".equalsIgnoreCase(payerAcType) || "NRO".equalsIgnoreCase(payerAcType)) {
		isValid = true;
	
}

	return isValid;

}

private String payerAcType(ReqAuthDetails reqAuthDetails) {
	for(AccountType.Detail deatil : reqAuthDetails.getPayer().getAc().getDetail()) {
		if(AccountDetailType.ACNUM==deatil.getName()) {
		}
		else if(AccountDetailType.IFSC==deatil.getName()) {
		}
		else if(AccountDetailType.ACTYPE==deatil.getName()) {
			return deatil.getValue();
		}
	
	}
	return null;
}

//For MANDATE Collect EXECUTE
private void setPayerdetails(PayerType payer, CustomerMandatesEntity customerMandates, ReqAuthDetails reqAuthDetails) {
	payer.setAddr(reqAuthDetails.getPayer().getAddr());//""Util.getProperty("PAYER_UMN_ADDERSS")
	log.info("payer name {}",customerMandates.getPayerName());
	payer.setName(customerMandates.getPayerName());
	payer.setSeqNum("1");
	payer.setType(PayerConstant.fromValue("PERSON"));//TODO customerMandates.getPayerType()
	payer.setCode("0000");//customerMandates.getPayerCode()
	InfoType info = new InfoType();
	IdentityType identity = new IdentityType();
	RatingType rating = new RatingType();
	if (ConstantI.ACCOUNT.equalsIgnoreCase("ACCOUNT")) {
		identity.setId(customerMandates.getPayerAccNo());
		identity.setType(IdentityConstant.ACCOUNT);
	}
	identity.setVerifiedName(customerMandates.getPayerName());
	info.setIdentity(identity);
	rating.setVerifiedAddress(WhiteListedConstant.TRUE);
	info.setRating(rating);
	payer.setInfo(info);
	payer.setDevice(NpciSchemaUtil.getPayerDeviceTypetest());//Will see later
	AccountType ac = new AccountType();
	setAcMandateExcute(ac,customerMandates);
	payer.setAc(ac);	
}
private void setAcMandateExcute(AccountType ac, CustomerMandatesEntity customerMandates) {
	// TODO Auto-generated method stub
	ac.setAddrType(AddressType.ACCOUNT);
	List<Detail> details = ac.getDetail();
	//if (ConstantI.ACCOUNT.equalsIgnoreCase(reqResp.getPayerAddrType())) {
		Detail detail = new Detail();
		detail.setName(AccountDetailType.IFSC);
		//detail.setValue(reqResp.getPayerIfsc().toUpperCase());
		detail.setValue(customerMandates.getPayerAccIFSC());
		details.add(detail);
		detail = new Detail();
		detail.setName(AccountDetailType.ACTYPE);
		detail.setValue(customerMandates.getPayerAcType());
		details.add(detail);
		detail = new Detail();
		detail.setName(AccountDetailType.ACNUM);
		detail.setValue(customerMandates.getPayerAccNo());
		details.add(detail);
}



}
