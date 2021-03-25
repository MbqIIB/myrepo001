package com.npst.upiserver.acquirer.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.UpiReqAuthMandateService;
import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.constant.ErrorCode;
import com.npst.upiserver.constant.MandateStatus;
import com.npst.upiserver.constant.UpiApiName;
import com.npst.upiserver.dao.BlockedByCustomerDao;
import com.npst.upiserver.dao.CustomerMandatesDao;
import com.npst.upiserver.dao.CustomerTxnsDao;
import com.npst.upiserver.dao.MandatesHistoryDao;
import com.npst.upiserver.dao.RegistrationDao;
import com.npst.upiserver.dao.ReqRespAuthMandateDao;
import com.npst.upiserver.dto.CredJson;
import com.npst.upiserver.dto.RegDto;
import com.npst.upiserver.entity.CustomerAccount;
import com.npst.upiserver.entity.CustomerMandatesEntity;
import com.npst.upiserver.npcischema.AccountDetailType;
import com.npst.upiserver.npcischema.AccountType;
import com.npst.upiserver.npcischema.AccountType.Detail;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.AddressType;
import com.npst.upiserver.npcischema.AmtRuleType;
import com.npst.upiserver.npcischema.CredSubType;
import com.npst.upiserver.npcischema.CredType;
import com.npst.upiserver.npcischema.CredsType;
import com.npst.upiserver.npcischema.CredsType.Cred;
import com.npst.upiserver.npcischema.CredsType.Cred.Data;
import com.npst.upiserver.npcischema.HeadType;
import com.npst.upiserver.npcischema.IdentityConstant;
import com.npst.upiserver.npcischema.IdentityType;
import com.npst.upiserver.npcischema.InfoType;
import com.npst.upiserver.npcischema.InitiatedByType;
import com.npst.upiserver.npcischema.MandateType;
import com.npst.upiserver.npcischema.PayeeType;
import com.npst.upiserver.npcischema.PayeesType;
import com.npst.upiserver.npcischema.PayerConstant;
import com.npst.upiserver.npcischema.PayerType;
import com.npst.upiserver.npcischema.RatingType;
import com.npst.upiserver.npcischema.ReqAuthMandate;
import com.npst.upiserver.npcischema.RespAuthMandate;
import com.npst.upiserver.npcischema.RespType;
import com.npst.upiserver.npcischema.ResultType;
import com.npst.upiserver.npcischema.WhiteListedConstant;

import com.npst.upiserver.repo.CustomerAccountRepository;
import com.npst.upiserver.service.IdGeneratorService;
import com.npst.upiserver.service.NotificationService;
import com.npst.upiserver.service.NpciUpiRestConService;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.MandateUtil;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.NpciSchemaUtil;
import com.npst.upiserver.util.Util;

@Service
public class UpiReqAuthMandateServiceImpl implements UpiReqAuthMandateService {
	private static final DateTimeFormatter formatter_ddMMyyyy = DateTimeFormatter.ofPattern("ddMMyyyy");

	private static final Logger log = LoggerFactory.getLogger(UpiReqAuthMandateServiceImpl.class);
	@Autowired
	private NpciUpiRestConService npciUpiRestConService;
	@Autowired
	private CustomerAccountRepository customerAccountRepo;
	@Autowired
	CustomerMandatesDao customerMandatesDao;
	@Autowired
	private RegistrationDao registrationDao;
	@Autowired
	private MandatesHistoryDao mandatesHistoryDao;
	@Autowired
	private ReqRespAuthMandateDao reqRespAuthMandateDao;
	@Autowired
	private BlockedByCustomerDao blockedByCustomerDao;
	@Autowired
	CustomerTxnsDao	customerTxnsDao;
	
	@Autowired
	private NotificationService notiService;
	@SuppressWarnings("unused")
	@Override
	public void acquirerProcess(final ReqAuthMandate reqAuthMandate) {
		log.debug("reqAuthMandate {}", reqAuthMandate);
		try {
			//Thread.sleep(120000); //TO REMOVE
			String ts = Util.getTS();
			String msgId = Util.uuidGen();
			RespAuthMandate respAuthMandate = new RespAuthMandate();
			HeadType head = new HeadType();
			head.setMsgId(msgId);
			head.setOrgId(Constant.orgId);
			head.setTs(ts);
			String reqmsgid=reqAuthMandate.getHead().getMsgId();
			head.setVer(reqAuthMandate.getHead().getVer());
			respAuthMandate.setHead(head);
			respAuthMandate.setTxn(reqAuthMandate.getTxn());
			RespType resp = new RespType();
			resp.setReqMsgId(reqmsgid);
			mandatesHistoryDao.insert(reqAuthMandate);
			reqRespAuthMandateDao.insertReq(reqAuthMandate);
			if (InitiatedByType.PAYEE.toString().equals(reqAuthMandate.getTxn().getInitiatedBy().toString())) {
				// Payee
				
				
				if("REVOKE".equalsIgnoreCase(reqAuthMandate.getTxn().getType().toString())) //||"UPDATE".equalsIgnoreCase(reqAuthMandate.getTxn().getType().toString()
				{
					processRevokeReq(reqAuthMandate,respAuthMandate,resp);
				 }
				
				else if("UPDATE".equalsIgnoreCase(reqAuthMandate.getTxn().getType().toString())) 
				{
					
					processUpdateReq(reqAuthMandate,respAuthMandate,resp);
				}		
				else {
					
					processCreateReq(reqAuthMandate,respAuthMandate,resp);
				}
			}
			
			else if (InitiatedByType.PAYER.toString().equals(reqAuthMandate.getTxn().getInitiatedBy().toString())) {// Payer
																														// initiated
				PayeesType payees = reqAuthMandate.getPayees();
				PayeesType payeesNew = new PayeesType();
				List<PayeeType> payeeList = payees.getPayee();
				List<PayeeType> payeeListNew = payeesNew.getPayee();
				Iterator<PayeeType> iterator = payeeList.iterator();
				while (iterator.hasNext()) {
					boolean flag = false;
					PayeeType payee = iterator.next();
					PayeeType payeeNew = new PayeeType();
					List<CustomerAccount> listaccounts = customerAccountRepo
							.findByAccvirtualid(payee.getAddr().trim().toLowerCase());
					if (listaccounts.isEmpty()||0 == listaccounts.size()) {
						resp.setResult(ConstantI.FAILURE);
						resp.setErrCode(ConstantI.RESP_ZH);// TODO
					} else {

						boolean isActive = registrationDao.isActiveRegistration(listaccounts);
						if (isActive) {
							// if list size is 0 it means we don't have the VPA
							// with ANY account
							int i = 1;
							for (CustomerAccount listaccount : listaccounts) {
								if (1 == listaccount.getDefaccount() && 1 == listaccount.getStatus()) {
									if (ConstantI.MIGRATED_M.equalsIgnoreCase(listaccount.getMigrated())) {
										log.info("ReqAuthMandate for VPA={}", listaccount.getAccvirtualid());
										resp.setResult(ConstantI.FAILURE);
										resp.setErrCode(Constant.NOTACTIVEVIRTUALADDRESS);
										break;
									}
									resp.setResult(ConstantI.SUCCESS);
									resp.setErrCode(null);
									payeeNew.setDevice(payee.getDevice());
									payeeNew.setMerchant(payee.getMerchant());
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
									payeeNew.setAc(ac);
									payeeListNew.add(payeeNew);
									flag = true;
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
							resp.setResult(ConstantI.FAILURE);
							resp.setErrCode(Constant.EXPIREDVIRTUALADDRESS);
						}
					}
					if (!flag) {
						payeeListNew.add(payee);
					}
				}
				payees = payeesNew;
				respAuthMandate.setResp(resp);
				respAuthMandate.setPayees(payees);
				respAuthMandate.setPayer(reqAuthMandate.getPayer());
				
				//For Remove MA_MT_PR_11
			/*	MandateType mandate =reqAuthMandate.getMandate();
				if(null!=mandate.getUmn()) {
					
					mandate.setUmn("");
				}*/
				
				respAuthMandate.setMandate(reqAuthMandate.getMandate());
				

				String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(respAuthMandate).toString());
				Ack ack = null;
				if (StringUtils.isNotBlank(xmlStr)) {
					//Thread.sleep(120000);
					ack = npciUpiRestConService.send(xmlStr, UpiApiName.RESP_MANDATE_AUTH.getName(),
						reqAuthMandate.getTxn().getId());
				}
				reqRespAuthMandateDao.updateResp(respAuthMandate, ack);
				//mandatesHistoryDao.update(respAuthMandate, ack);
			}
			
			
		} catch (Exception e) {
			log.error("Error: {}", e);
			ErrorLog.sendError(e, UpiReqAuthMandateServiceImpl.class);
		}

	}
	
	
	private void processCreateReq(ReqAuthMandate reqAuthMandate, RespAuthMandate respAuthMandate, RespType resp) {
		List<CustomerAccount> listaccounts = customerAccountRepo
				.findByAccvirtualid(reqAuthMandate.getPayer().getAddr().toLowerCase());
		
		List<PayeeType> payee = reqAuthMandate.getPayees().getPayee();
		log.info("payee vpa is {}",payee);
		// TODO discussion
		String payeeVpa = "";
		
		for (PayeeType payeeType : payee) {
			payeeVpa = payeeType.getAddr().toLowerCase();
			log.info("payee vpa is {}",payeeVpa);
			
		}
		log.info("before going to check block chk");
		if (blockedByCustomerDao.isBlockedByCustomer(payeeVpa, listaccounts.get(0).getRegid())) {
			resp.setResult(ConstantI.FAILURE);
			resp.setErrCode(Constant.BLOCKBYCUSTOMER);
		} else {
			for (CustomerAccount listaccount : listaccounts) {
				if (1 == listaccount.getDefaccount() && 1 == listaccount.getStatus()) {
					RegDto regvpa = registrationDao.getGCMToken(listaccount.getRegid());
						log.info("ReqAuthMandate for VPA={}", listaccount.getAccvirtualid());
						resp.setResult(ConstantI.SUCCESS);
						notiService.sendNoti(reqAuthMandate, regvpa);
						break;
				} else if (4 == listaccount.getStatus()) {
					resp.setResult(ConstantI.FAILURE);
					resp.setErrCode(Constant.NOTPERMITTED);
				} else {
					resp.setResult(ConstantI.FAILURE);
					resp.setErrCode(ConstantI.RESP_ZG);
				}
			}
		}
		
		
		respAuthMandate.setPayer(reqAuthMandate.getPayer());
		// MandateType mandate = reqAuthMandate.getMandate();
		respAuthMandate.setPayees(reqAuthMandate.getPayees());
		respAuthMandate.setResp(resp);
		respAuthMandate.setMandate(reqAuthMandate.getMandate());

		if (ResultType.FAILURE.toString().equalsIgnoreCase(resp.getResult())) {  //FAILURE will be 
			Ack ack = null;
			String xmlStr;
			try {
				xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(respAuthMandate).toString());
				ack=npciUpiRestConService.send(xmlStr, UpiApiName.RESP_MANDATE_AUTH.getName(),
						respAuthMandate.getTxn().getId());
					reqRespAuthMandateDao.updateResp(respAuthMandate, ack);
					mandatesHistoryDao.update(respAuthMandate, ack);
					
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			/*Ack ack = null;
			String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(respAuthMandate).toString());
			ack = npciUpiRestConService.send(xmlStr, UpiApiName.RESP_MANDATE_AUTH.getName(),
				respAuthMandate.getTxn().getId());
             reqRespAuthMandateDao.updateResp(respAuthMandate, ack);*/

		}
		
	}
	@SuppressWarnings("null")
	private void processUpdateReq(ReqAuthMandate reqAuthMandate, RespAuthMandate respAuthMandate, RespType resp) {
		String payerVpa = reqAuthMandate.getMandate().getUmn();
		String umn = payerVpa;
		CustomerMandatesEntity customerMandates = customerMandatesDao.findByUmn(umn);

		LocalDateTime today = LocalDateTime.now();
		String validityend = customerMandates.getMandateValidityEnd();
		LocalDateTime validityenddate = LocalDateTime.parse(validityend, formatter_ddMMyyyy);
		
		
		
		// it will be find by payer VPA
		if (customerMandates!=null) {//MandateStatus.MANDATE_PAUSED.getStatus() == customerMandates.getStatus()
			
			if(MandateStatus.MANDATE_PAUSED.getStatus() == customerMandates.getStatus()) {
				resp.setResult(ResultType.FAILURE.toString());
				resp.setErrCode(ErrorCode.MANDATE_PSP_QA.getUpiCode());
			}
				
			 else if (MandateStatus.MANDATE_REVOKED.getStatus() == customerMandates.getStatus()) {
				resp.setResult(ResultType.FAILURE.toString());
				resp.setErrCode(ErrorCode.MANDATE_PSP_QC.getUpiCode());
			}
			 else if (today.isBefore(validityenddate)) {
				resp.setResult(ResultType.FAILURE.toString());
				resp.setErrCode(ErrorCode.MANDATE_PSP_QD.getUpiCode());
			}
			
			/* else if() {
				 
			 }*/
			
			/*if (Double.valueOf(customerMandates.getMandateAmountrule()) < Double
						.valueOf(respAuthMandate.getPayer().getAmount().getValue())
						|| (AmtRuleType.EXACT.toString().equals(customerMandates.getMandateAmountrule())
								&& !respAuthMandate.getPayer().getAmount().getValue()
										.equals(customerMandates.getMandateAmountvalue())
								|| AmtRuleType.MAX.toString().equals(customerMandates.getMandateAmountrule())
										&& (Double
												.valueOf(respAuthMandate.getPayer().getAmount().getValue()) > Double
														.valueOf(customerMandates.getMandateAmountvalue())))) {
					resp.setResult(ResultType.FAILURE.toString());
					resp.setErrCode(ErrorCode.MANDATE_PSP_QH.getUpiCode());
				}*/

				else if (!customerMandates.getPayeeVpa()
						.equalsIgnoreCase(respAuthMandate.getPayees().getPayee().get(0).getAddr())) {
					resp.setResult(ResultType.FAILURE.toString());
					resp.setErrCode(ErrorCode.MANDATE_PSP_QI.getUpiCode());
				} else if (customerTxnsDao.isHonouredTxn(customerMandates.getMandateUmn(),
						respAuthMandate.getPayer().getAddr())) {
					resp.setResult(ResultType.FAILURE.toString());
					resp.setErrCode(ErrorCode.MANDATE_PSP_QK.getUpiCode());
				} else if (MandateUtil.checkForTxnDayAndRecPattern(customerMandates)) {
					resp.setResult(ResultType.FAILURE.toString());
					resp.setErrCode(ErrorCode.MANDATE_PSP_QR.getUpiCode());
				} else {
					//TODO Send Notification For Create	
			
				}
		}
		else {
			resp.setResult(ConstantI.FAILURE);
			resp.setErrCode("VF");
		}
		
		respAuthMandate.setPayees(reqAuthMandate.getPayees());
		// MandateType mandate = reqAuthMandate.getMandate();
		respAuthMandate.setPayees(reqAuthMandate.getPayees());
		respAuthMandate.setResp(resp);
		respAuthMandate.setMandate(reqAuthMandate.getMandate());
		
		if (ResultType.FAILURE.toString().equalsIgnoreCase(resp.getResult())) {  //FAILURE will be 
			Ack ack = null;
			String xmlStr;
			try {
				xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(respAuthMandate).toString());
				ack=npciUpiRestConService.send(xmlStr, UpiApiName.RESP_MANDATE_AUTH.getName(),
						respAuthMandate.getTxn().getId());
					reqRespAuthMandateDao.updateResp(respAuthMandate, ack);
					mandatesHistoryDao.update(respAuthMandate, ack);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
	}
	private void processRevokeReq(ReqAuthMandate reqAuthMandate, RespAuthMandate respAuthMandate, RespType resp) {

		//need to add checks here for failure 
		CustomerMandatesEntity customerMandates = customerMandatesDao.findByUmnAndCustIs(reqAuthMandate.getMandate().getUmn());
		if(null!=customerMandates) {
			if(ConstantI.STATUS_3==customerMandates.getStatus()) {
		resp.setResult(ConstantI.FAILURE);
		resp.setErrCode(Constant.NOTPERMITTED);
		}
		else {
		log.info("DATA FOR AUTH MANDATE SIGN VALUE  {}",customerMandates.getMandateSignValue());
		CredsType creds = new CredsType();
		Cred cred = new Cred();
		Data data = new Data();
		data.setCode(ConstantI.NPCI);
		data.setValue(customerMandates.getMandateSignValue());
		data.setKi(ConstantI.KI_NPCI);
		cred.setData(data);
		cred.setSubType(CredSubType.fromValue(CredSubType.DS.toString()));
		cred.setType(CredType.fromValue("UPI-Mandate"));// need to remove it...
		creds.getCred().add(cred);
		reqAuthMandate.getPayer().setCreds(creds);
		
		List<CustomerAccount> listaccounts = customerAccountRepo
				.findByAccvirtualid(reqAuthMandate.getPayer().getAddr().toLowerCase());
		
		for (CustomerAccount listaccount : listaccounts) {
			if (1 == listaccount.getDefaccount() && 1 == listaccount.getStatus()) {
				if (ConstantI.MIGRATED_M.equalsIgnoreCase(listaccount.getMigrated())) {
					log.info("ReqAuthMandate for VPA={}", listaccount.getAccvirtualid());
					resp.setResult(ConstantI.FAILURE);
					resp.setErrCode(Constant.NOTACTIVEVIRTUALADDRESS);
					break;
				}
				PayerType payer = new PayerType();
				payer.setCreds(creds);
				resp.setResult(ConstantI.SUCCESS);
				resp.setErrCode(null);
				payer.setDevice(NpciSchemaUtil.getPayerDeviceTypetest());
				payer.setType(PayerConstant.fromValue("PERSON"));
				payer.setAddr(reqAuthMandate.getPayer().getAddr());
				payer.setName("ABC");
				payer.setSeqNum("1");
				payer.setCode("0000");
				InfoType info = new InfoType();
				RatingType rating = new RatingType();
				rating.setVerifiedAddress(WhiteListedConstant.TRUE);
				info.setRating(rating);
				IdentityType identity = new IdentityType();
				identity.setId(listaccount.getAccrefnumber());
				identity.setType(IdentityConstant.ACCOUNT);
				identity.setVerifiedName(listaccount.getName().toUpperCase());
				info.setIdentity(identity);
				payer.setInfo(info);
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
				payer.setAc(ac);
				respAuthMandate.setPayer(payer);
				break;
			} else if (4 == listaccount.getStatus()) {
				resp.setResult(ConstantI.FAILURE);
				resp.setErrCode(Constant.NOTPERMITTED);
			} else {
				resp.setResult(ConstantI.FAILURE);
				resp.setErrCode(ConstantI.RESP_ZG);
			}
		}
	}
}			
	else {
		resp.setResult(ConstantI.FAILURE);
		resp.setErrCode("VF");
	}
	respAuthMandate.setPayees(reqAuthMandate.getPayees());
	// MandateType mandate = reqAuthMandate.getMandate();
	 respAuthMandate.setPayees(reqAuthMandate.getPayees());
		respAuthMandate.setResp(resp);
		respAuthMandate.setMandate(reqAuthMandate.getMandate());
		//FAILURE will be 
			Ack ack = null;
			String xmlStr;
			try {
				xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(respAuthMandate).toString());
				if (StringUtils.isNotBlank(xmlStr)) {
					ack=npciUpiRestConService.send(xmlStr, UpiApiName.RESP_MANDATE_AUTH.getName(),
						respAuthMandate.getTxn().getId());
					reqRespAuthMandateDao.updateResp(respAuthMandate, ack);
					mandatesHistoryDao.update(respAuthMandate, ack);
				
					}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
}
