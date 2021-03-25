package com.npst.upiserver.issuer.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.constant.ErrorCode;
import com.npst.upiserver.constant.InitiationMode;
import com.npst.upiserver.constant.MandateStatus;
import com.npst.upiserver.dao.MandatesDao;
import com.npst.upiserver.dao.ReqRespDebitCreditDao;
import com.npst.upiserver.dao.TransServerDao;
import com.npst.upiserver.dto.MerchantsTxnDto;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.FirInfo;
import com.npst.upiserver.entity.MandatesEntity;
import com.npst.upiserver.issuer.service.UpiReqPayService;
import com.npst.upiserver.npcischema.AccountDetailType;
import com.npst.upiserver.npcischema.AccountType;
import com.npst.upiserver.npcischema.AccountType.Detail;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.AmtRuleType;
import com.npst.upiserver.npcischema.CredSubType;
import com.npst.upiserver.npcischema.CredType;
import com.npst.upiserver.npcischema.CredsType.Cred;
import com.npst.upiserver.npcischema.DeviceType.Tag;
import com.npst.upiserver.npcischema.HeadType;
import com.npst.upiserver.npcischema.ListedAccountType;
import com.npst.upiserver.npcischema.PayeeType;
import com.npst.upiserver.npcischema.PayerType;
import com.npst.upiserver.npcischema.Ref;
import com.npst.upiserver.npcischema.RefType;
import com.npst.upiserver.npcischema.ReqPay;
import com.npst.upiserver.npcischema.RespPay;
import com.npst.upiserver.npcischema.ResultType;
import com.npst.upiserver.service.IdGeneratorService;
import com.npst.upiserver.service.MiddlewareRestConService;
import com.npst.upiserver.service.NotificationService;
import com.npst.upiserver.service.NpciUpiRestConService;
import com.npst.upiserver.service.PayAccTypeValidationService;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.Util;

@Service
public class UpiReqPayServiceImpl implements UpiReqPayService {
	private static final Logger log = LoggerFactory.getLogger(UpiReqPayServiceImpl.class);
   private static boolean isFIR_ALLOW = Constant.IS_FIR_ALLOW;
	@Autowired	
	private MiddlewareRestConService switchCom;
	@Autowired
	private NpciUpiRestConService npciUpiRestConService;


	@Autowired
	private ReqRespDebitCreditDao reqRespDebitCreditDao;
	@Autowired
	private NotificationService notificationService;
	
    @Autowired
	private TransServerDao transServerDao;
    
    @Autowired
	private IdGeneratorService idGeneratorService;
    
    @Autowired
	private PayAccTypeValidationService payAccTypeValidationService;
	
	
	@Autowired
	private MandatesDao mandatesDao;
	private static String merchantBankId = Util.getProperty("MERCHANT_BANK_ID");

	@Override
	public void issuerProcess(final ReqPay reqPay) {
		
		
		if (reqPay == null) {
			return;
		}
        boolean flag=true;
		String sTNote = null;
		String sRefId = null;
		String sRefURL = null;
		ReqResp req = null;
		String revType = "";
		try {
		
			log.info("reqPay {}", reqPay);
			Date reqDate = new Date();
			RespPay respPay = new RespPay();
			respPay.setHead(getHead());
			respPay.setTxn(reqPay.getTxn());
			respPay.getTxn().setRules(null);
			RespPay.Resp resp = new RespPay.Resp();
			resp.setReqMsgId(reqPay.getHead().getMsgId());
			List<Ref> refs = resp.getRef();
			Ref ref = null;
			req = new ReqResp();
			String cbsErrorCode = ConstantI.CONST_BLANK;
			Ack ack = null;
			Date respDate = null;
			req.setTxnNote(reqPay.getTxn().getNote());
			req.setTxnType(reqPay.getTxn().getType().value());
			req.setRrn(reqPay.getTxn().getCustRef());
			req.setTxnId(reqPay.getTxn().getId());
			req.setBkPrf(reqPay.getTxn().getId().substring(0, 3));
			req.setTxnRefId(reqPay.getTxn().getRefId());
			req.setTxnRefUrl(reqPay.getTxn().getRefUrl());
            req.setInitiationMode(reqPay.getTxn().getInitiationMode());
			req.setField11(idGeneratorService.getRrn());
			req.setIsUPI2("1");
			req.setTxnPurpose(reqPay.getTxn().getPurpose());
			if (null != reqPay.getPayer()) {
				req=setPayerParam(reqPay, req);
			}
			if (null != reqPay.getPayees()) {
				req=setPayeeParam(reqPay, req);
			}
			
			if (ConstantI.DEBIT.equalsIgnoreCase(reqPay.getTxn().getType().value())) {
				
				//check for same accnt of payer and payee
				boolean b=checkForSameAccntOfPayeeAndPayer(req);

				if(b) {
				req.setCbsErrorCode(getB3_B3_Code(reqPay.getTxn().getType().value()));//TXN NOT ALLOWD IN ACCOUNT
				req.setRespCode(getB3_B3_Code(reqPay.getTxn().getType().value()));
				log.info("both payer and payee accnt no. same that is checkForSameAccntOfPayeeAndPayer(req)=true");
				resp.setResult(ResultType.FAILURE);
				}
				ref = new Ref();
				ref.setAddr(reqPay.getPayer().getAddr());
				ref.setRegName(reqPay.getPayer().getName());
				ref.setType(RefType.PAYER);
				ref.setSeqNum(reqPay.getPayer().getSeqNum());
				ref.setSettCurrency(reqPay.getPayer().getAmount().getCurr());
				ref.setOrgAmount(null);
                ref.setCode(reqPay.getPayer().getCode());
				PayerType payer=reqPay.getPayer();
				for(AccountType.Detail deatil : payer.getAc().getDetail()) {
					if(AccountDetailType.ACNUM==deatil.getName()) {
						ref.setAcNum(deatil.getValue());
					}
					else if(AccountDetailType.IFSC==deatil.getName()) {
						ref.setIFSC(deatil.getValue());
					}
					else if(AccountDetailType.ACTYPE==deatil.getName()) {
						ref.setAccType(setAccount(deatil));
				}
			}
				sTNote = reqPay.getTxn().getNote();
				sRefId = reqPay.getTxn().getRefId();
				sRefURL = reqPay.getTxn().getRefUrl();
				
				if (InitiationMode.MANDATE.getMode().equalsIgnoreCase(reqPay.getTxn().getInitiationMode())||InitiationMode.MANDATEQR.getMode().equals(reqPay.getTxn().getInitiationMode())) {
					req=mandateTxn(reqPay,req);//10/07/2019
				}
			} else if (ConstantI.CREDIT.equalsIgnoreCase(reqPay.getTxn().getType().value())) {
				//FIR 20-08-2019  checked by Shujath
				if(reqPay.getTxn().getInitiationMode()!=null && reqPay.getTxn().getInitiationMode().equalsIgnoreCase(ConstantI.INITIATION_MODE_FIR)) {
	                req.setIsFIR(ConstantI.TRUE);
	                String firInfo= saveFirInfo(reqPay);
	                log.info("UpiReqPay Credit FIR :  {} ", firInfo);
	                req.setFirInfo(firInfo);
	                req.setInitiationMode(reqPay.getTxn().getInitiationMode());
	                req.setMccCode(req.getPayeeCode());
				}
				
				if (null != reqPay.getPayees()) {
					List<PayeeType> payees = reqPay.getPayees().getPayee();
					for (PayeeType payeeType : payees) {
						ref = new Ref();
						ref.setRegName(payeeType.getName());
						ref.setAddr(payeeType.getAddr());
						ref.setType(RefType.PAYEE);
						ref.setSeqNum(payeeType.getSeqNum());
						ref.setSettAmount(payeeType.getAmount().getValue());
						ref.setSettCurrency(payeeType.getAmount().getCurr());
                        ref.setCode(payeeType.getCode());
						for(AccountType.Detail deatil:payeeType.getAc().getDetail()) {
							if(AccountDetailType.ACNUM==deatil.getName()) {
								ref.setAcNum(deatil.getValue());
							}else if(AccountDetailType.IFSC==deatil.getName()) {
								ref.setIFSC(deatil.getValue());
							}
							else if(AccountDetailType.ACTYPE==deatil.getName()) {
								ref.setAccType(setAccount(deatil));
						}
						// ref.setOrgAmount(payee.getAmount().getValue());
					}
						ref.setCode(payeeType.getCode());
						sTNote = reqPay.getTxn().getNote();
						sRefId = reqPay.getTxn().getRefId();
						sRefURL = reqPay.getTxn().getRefUrl();
					}
				}
			} else if (ConstantI.REVERSAL.equalsIgnoreCase(reqPay.getTxn().getType().value())) {
// FLAG CREDIT REVERSAL
				req.setBkPrf(reqPay.getTxn().getOrgTxnId().substring(0, 3));
				req.setOrgRespCode(reqPay.getTxn().getOrgRespCode());
				req.setOrgTxnId(reqPay.getTxn().getOrgTxnId());
				if (null != reqPay.getPayees()) {
					revType = ConstantI.CREDIT;
					req.setOrgTxnType(ConstantI.CREDIT);
					List<PayeeType> payees = reqPay.getPayees().getPayee();
					for (PayeeType payeeType : payees) {
						ref = new Ref();
						ref.setAddr(payeeType.getAddr());
						ref.setType(RefType.PAYEE);
						ref.setRegName(payeeType.getName());
						ref.setSeqNum(payeeType.getSeqNum());
						ref.setSettAmount(payeeType.getAmount().getValue());
						ref.setSettCurrency(payeeType.getAmount().getCurr());
                        ref.setCode(payeeType.getCode());
						for(AccountType.Detail deatil:payeeType.getAc().getDetail()) {
							
							if(AccountDetailType.ACNUM==deatil.getName()) {
								ref.setAcNum(deatil.getValue());
							}else if(AccountDetailType.IFSC==deatil.getName()) {
								ref.setIFSC(deatil.getValue());
							}
							else if(AccountDetailType.ACTYPE==deatil.getName()) {
								ref.setAccType(setAccount(deatil));
						}
					ref.setOrgAmount(payeeType.getAmount().getValue());
                	}
					
				}
				}// FLAG DEBIT REVERSAL
				if (null != reqPay.getPayer()) {
					revType = ConstantI.DEBIT;
					req.setOrgTxnType(ConstantI.DEBIT);
					ref = new Ref();
					ref.setAddr(reqPay.getPayer().getAddr());
					ref.setRegName(reqPay.getPayer().getName());
					ref.setType(RefType.PAYER);
					ref.setSeqNum(reqPay.getPayer().getSeqNum());
					ref.setSettAmount(reqPay.getPayer().getAmount().getValue());
					ref.setSettCurrency(reqPay.getPayer().getAmount().getCurr());
					PayerType payer=reqPay.getPayer();
					ref.setCode(reqPay.getPayer().getCode());
					for(AccountType.Detail deatil : payer.getAc().getDetail()) {
						if(AccountDetailType.ACNUM==deatil.getName()) {
							ref.setAcNum(deatil.getValue());
						}
						else if(AccountDetailType.IFSC==deatil.getName()) {
							ref.setIFSC(deatil.getValue());
						}
						else if(AccountDetailType.ACTYPE==deatil.getName()) {
							ref.setAccType(setAccount(deatil));
					}
					// ref.setOrgAmount(reqPay.getPayer().getAmount().getValue());
						if (InitiationMode.MANDATE.getMode().equalsIgnoreCase(reqPay.getTxn().getInitiationMode())||InitiationMode.MANDATEQR.getMode().equals(reqPay.getTxn().getInitiationMode())) {
							req=mandateTxn(reqPay,req);//10/07/2019
						}
				}
			 }
			} else {
                // TODO txn type not matched
				log.error("new TXN_TYPE detected in ReqPay");
				ErrorLog.sendError("new TXN_TYPE detected in ReqPay ie.", reqPay.getTxn().getType().value(),
						UpiReqPayServiceImpl.class);
				return;
			}
		
			if((ConstantI.MANDATE_INIT_MODE.equalsIgnoreCase(reqPay.getTxn().getInitiationMode())||InitiationMode.MANDATEQR.getMode().equals(reqPay.getTxn().getInitiationMode()))&&(ConstantI.DEBIT.equalsIgnoreCase(reqPay.getTxn().getType().value())||
					ConstantI.DEBIT.equalsIgnoreCase(revType))) {
				
				if(ConstantI.MANDATE_CODE_SUCCESS.equalsIgnoreCase(req.getRespCode())) {
					flag=true;
				}
				else {
					flag=false;
				}
			}
			
				if(flag) {
				
				if(payAccTypeValidationService.isAccTypeValid(req)){
					
					boolean fl = true;
					if (isFIR(req.getInitiationMode())) {
						fl = isFIR_ALLOW;
						//log.info
						if (!fl) {
							log.info("FIR NOT ALLOW {} :", fl);
							req.setCbsErrorCode(getNE_NE_Code(reqPay.getTxn().getType().value()));
							req.setRespCode(getNE_NE_Code(reqPay.getTxn().getType().value()));

						}
					} else if (isUPIMandate(reqPay.getTxn().getInitiationMode())&&(ConstantI.DEBIT.equalsIgnoreCase(reqPay.getTxn().getType().value())||
							ConstantI.DEBIT.equalsIgnoreCase(revType))) {
						req.setTxnType(ConstantI.MANDATE_TXN_TYPE);
						req.setOperation(ConstantI.MANDTAE_OPERATION);
						req.setSubOperation(ConstantI.MANDTAE_SUB_OPER_DEBIT);
						
						if(ConstantI.REVERSAL.equalsIgnoreCase(reqPay.getTxn().getType().value())) 
						{
							log.debug("reversal sub Operation is {}",ConstantI.MANDTAE_SUB_OPER_REV);
						req.setSubOperation(ConstantI.MANDTAE_SUB_OPER_REV);
						}
						log.info("UPIMandate Request>>>>>>>{} :",req.getSubOperation());
					}

					if (fl) {
						req.setField11(idGeneratorService.getRrn()); 
						req.setIsUPI2("1");
						req = switchCom.send(req);
						req.setTxnType(reqPay.getTxn().getType().value());// CHANGES AGAIN TXN TYPE FOR NORMAL TXN FLOW
						
						log.info("Response received from middlware :  {} ", req);
					}
				} else {
					// TODO if acc types are not valid to transfer fund then respCode
					
					req.setCbsErrorCode(getB3_B3_Code(reqPay.getTxn().getType().value()));//TXN NOT ALLOWD IN ACCOUNT
					req.setRespCode(getB3_B3_Code(reqPay.getTxn().getType().value()));
					log.info("Pay accType validations are failed ie isAccTypeValid(req)=false" );
				}
			}
				else {
					req.setRespCode(req.getRespCode());
					req.setCbsErrorCode(req.getRespCode());
				}
				
			
			cbsErrorCode = req.getCbsErrorCode();
			if (ConstantI.CODE_SUCCESS.equalsIgnoreCase(req.getRespCode().trim())) {
				resp.setResult(ResultType.SUCCESS);
				ref.setRespCode(ConstantI.CODE_00);
				if (ConstantI.REVERSAL.equalsIgnoreCase(reqPay.getTxn().getType().value())) {
					if (ConstantI.DEBIT.equals(revType)) {
						ref.setSettAmount(req.getPayerAmount());
					} else {
						ref.setSettAmount(req.getPayeeAmount());
					}
				} else if (ConstantI.DEBIT.equalsIgnoreCase(reqPay.getTxn().getType().value())) {
					ref.setSettAmount(req.getPayerAmount());
				} else if (ConstantI.CREDIT.equalsIgnoreCase(reqPay.getTxn().getType().value())) {
					ref.setSettAmount(req.getPayeeAmount());
				}
				ref.setApprovalNum(reqPay.getTxn().getCustRef().substring(6));
				// TODO discussion
			} else {
				resp.setResult(ResultType.FAILURE);
				resp.setErrCode(req.getRespCode().trim());
				ref.setRespCode(req.getRespCode().trim());
				ref.setSettAmount(ConstantI.MIN_AMT);
			}
			if (ConstantI.REVERSAL.equalsIgnoreCase(reqPay.getTxn().getType().value())) {
				respPay.getTxn().setOrgRespCode(null);
				respPay.getTxn().setRefId(req.getTxnRefId());
				respPay.getTxn().setRefUrl(req.getTxnRefUrl());
				respPay.getTxn().setNote(req.getTxnNote());
				respPay.getTxn().setRefId(sRefId);
				respPay.getTxn().setRefUrl(sRefURL);
				respPay.getTxn().setNote(sTNote);
			}
			refs.add(ref);
			respPay.setResp(resp);
			String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(respPay).toString());
			if (null == xmlStr || ConstantI.CONST_BLANK.equalsIgnoreCase(xmlStr)) {
				log.error("Digital Sign Gives the Upi RespPay xmlStr is null");
				req.setRespCode(ConstantI.UKN);
			}
			log.info("UpiReqPay Sending to NPCI  :  {} ", xmlStr);
			respDate = new Date();
		
			if (!ConstantI.UKN.equals(req.getRespCode())) {
				
				ack = sendRespToNpci(respPay, xmlStr);
			}
			reqRespDebitCreditDao.insertReqResp(reqPay, respPay, ack, reqDate,
					(respDate == null ? new Date() : respDate), revType, cbsErrorCode, req.getField11());
	if(isUPIMandate(reqPay.getTxn().getInitiationMode())
					&&(ConstantI.CODE_SUCCESS.equalsIgnoreCase(req.getRespCode().trim())
							||ConstantI.CODE_UKN.equalsIgnoreCase(req.getRespCode().trim()))) {
				
				mandatesDao.UpdateMandate(reqPay,reqDate,req);
				log.info("Mandtae Updated Successfuly...");
			
			}
			
			} catch (Exception e) {
			if (reqPay.getTxn() != null) {
				log.error("ERROR_3 in UpiReqPay  TxnType={} ,TxnId={} ,RRN={} ,error={}", reqPay.getTxn().getType(),
						reqPay.getTxn().getId(), reqPay.getTxn().getCustRef(), e.getMessage());
				ErrorLog.sendError("ERROR_3 in UpiReqPay for TxnType,TxnId,RRN ,error",
						new String[] { String.valueOf(reqPay.getTxn().getType()), reqPay.getTxn().getId(),
								reqPay.getTxn().getCustRef(), e.getMessage() },
						UpiReqPayServiceImpl.class);
			}
			log.error("ERROR_3 in UpiReqPay {}", e);
			log.error("ERROR_3 in UpiReqPay of ReqXML ={}", reqPay);
			ErrorLog.sendError(e, UpiReqPayServiceImpl.class);

		}finally {
			// put trans_server code in this module also
			//ashish
			transServerDao.insertTransServer(req, reqPay.getHead().getMsgId(), revType);
		}
	}

	private boolean checkForSameAccntOfPayeeAndPayer(ReqResp req) {
		log.info("payerAccntNo , payeeAccntNo {} ",req.getPayerAcNum(),req.getPayeeAcNum());
		if(req.getPayerAcNum().equalsIgnoreCase(req.getPayeeAcNum())) {
			return true;
		} else {
		return false;
		}
	}
	
	private ListedAccountType setAccount(Detail deatil) {
		if(deatil.getValue().equals(ConstantI.TYPE_NRE)) {
			return ListedAccountType.NRE;
		}
		else if(deatil.getValue().equals(ConstantI.TYPE_NRO)) {
			return ListedAccountType.NRO;
		}
		else if(deatil.getValue().equals(ConstantI.TYPE_SAVINGS)) {
			return ListedAccountType.SAVINGS;
		}
		else if(deatil.getValue().equals(ConstantI.TYPE_CURRENT)) {
			return ListedAccountType.CURRENT;
		}
		else if(deatil.getValue().equals(ConstantI.TYPE_CREDIT)) {
			return ListedAccountType.CREDIT;
		}
		else if(deatil.getValue().equals(ConstantI.TYPE_PPIWALLET)) {
			return ListedAccountType.PPIWALLET;
		}
		else if(deatil.getValue().equals(ConstantI.TYPE_UOD)) {
			return ListedAccountType.UOD;
		}
		else if(deatil.getValue().equals(ConstantI.TYPE_SOD)) {
			return ListedAccountType.SOD;
		}
		return ListedAccountType.SAVINGS;
	}

	private HeadType getHead() {
		HeadType head = new HeadType();
		head.setMsgId(Util.uuidGen());
		head.setOrgId(Constant.orgId);
		head.setTs(Util.getTS());
		head.setVer(Constant.headVer);
		return head;
	}

	private Ack sendRespToNpci(RespPay respPay, String xmlStr) {
		Ack ack = null;
		if (ConstantI.DEBIT.equalsIgnoreCase(respPay.getTxn().getType().value())) {
			ack = npciUpiRestConService.send(xmlStr, ConstantI.API_RESP_PAY, respPay.getTxn().getId());

		} else if (ConstantI.CREDIT.equalsIgnoreCase(respPay.getTxn().getType().value())) {
			ack = npciUpiRestConService.send(xmlStr, ConstantI.API_RESP_PAY, respPay.getTxn().getId());

		} else if (ConstantI.REVERSAL.equalsIgnoreCase(respPay.getTxn().getType().value())) {
			ack = npciUpiRestConService.send(xmlStr, ConstantI.API_RESP_PAY, respPay.getTxn().getOrgTxnId());
		}
		return ack;
	}
	/*private void checkMerchantTxn(ReqResp req, String txnType) {
		if (PayerConstant.ENTITY.toString().equals(req.getPayeeType())
				&& !ConstantI.REVERSAL.equalsIgnoreCase(txnType)) {
			req.setMerchTxn(true);
			if (req.getPayeeAddr().substring(req.getPayeeAddr().lastIndexOf("@")).equalsIgnoreCase(BANK_HANDAL)) {
				req.setMccCode(req.getPayeeCode());
				req.setInternalM(true);
				req.setBharatQR(merchantDao.isBharatQR(req.getPayeeAddr()));
			}
		}	
	}*/
	private ReqResp mandateTxn(final ReqPay reqPay,ReqResp req) {
		log.info("Mandate txn type {}",reqPay.getTxn().getType().value());
		// TODO discussion
		final String umn = reqPay.getPayer().getAddr();
		final MandatesEntity mandates = mandatesDao.findByUmn(umn);
		String mandatecreatedAcno=mandates.getAcAddrTypeDetail3();

		if (mandates == null || StringUtils.isEmpty(mandates.getMandateUmn())||MandateStatus.MANDATE_FAILED.getStatus()==mandates.getStatus()) {
			req.setRespCode(ErrorCode.MANDATE_ISSUER_VF.getUpiCode());
		} 
		
		else if("REVERSAL".equalsIgnoreCase(reqPay.getTxn().getType().value())) {
			log.info("Mandate Reversal {}",reqPay.getTxn().getType().toString());
			req.setiPOName(mandates.getMandateName());
			req.setCbsMandateNo(mandates.getCbsMandateNo());
			req.setInitiationMode(mandates.getTxnInitiationMode());
			req.setBidReferenceNumber(mandates.getBidRefNo());
			req.setRespCode(ConstantI.MANDATE_CODE_SUCCESS);
			req.setUnblockStatus(mandates.getUnblockStatus());
			req.setValidityStart(mandates.getMandateValidityStart());// use in case of Rev for making lin Acc again
			req.setValidityEnd(mandates.getMandateValidityEnd());// use in case of Rev for making lin Acc again
		}
		else {
			String base64MandateSign = null;
			log.info("Amount of payer {} and mandate amount is {} and rule is {}",reqPay.getPayer().getAmount().getValue(),mandates.getMandateAmountvalue(),mandates.getMandateAmountrule());
			List<Cred> creds = reqPay.getPayer().getCreds().getCred();
			for (Cred cred : creds) {
				if (CredType.UPI_MANDATE.toString().equals(cred.getType().toString())
						&& CredSubType.DS.toString().equals(cred.getSubType().toString())) {
					base64MandateSign =  cred.getData().getValue();
					break;
				}
			}
			if (base64MandateSign == null || !base64MandateSign.equals(mandates.getMandateSignValue())) { 
				
				log.info("Mandate Sign Authentication Fiald ");
				req.setRespCode(ErrorCode.MANDATE_ISSUER_VH.getUpiCode());
			} 
			
			else if (MandateStatus.MANDATE_REVOKED.getStatus() == mandates.getStatus()) {
			
				req.setRespCode(ErrorCode.MANDATE_ISSUER_VA.getUpiCode());
				
				
			}  
			
			else if (AmtRuleType.EXACT.toString().equals(mandates.getMandateAmountrule())
					&& !reqPay.getPayer().getAmount().getValue().equals(mandates.getMandateAmountvalue())
					|| AmtRuleType.MAX.toString().equals(mandates.getMandateAmountrule())
							&& (Double.valueOf(reqPay.getPayer().getAmount().getValue()) > Double
									.valueOf(mandates.getMandateAmountvalue()))) {
				
				req.setRespCode(ErrorCode.MANDATE_ISSUER_VD.getUpiCode());

				

			} /*else if (!MandateUtil.checkForTxnDayAndRecPattern(mandates)) {//NEED TO CHECK
				req.setRespCode(ErrorCode.MANDATE_ISSUER_VI.getUpiCode());

			} */
			
			/*else if(!reqPay.getPayer().getAmount().getValue().equals(mandates.getMandateAmountvalue())) {
				req.setRespCode(ErrorCode.CBS_R_XD.getUpiCode());
			}*/
			
			else if(!mandatecreatedAcno.equalsIgnoreCase(req.getPayerAcNum())) {
				req.setRespCode(ErrorCode.MANDATE_ISSUER_VJ.getUpiCode());
			}
			
			else {
				req.setiPOName(mandates.getMandateName());
				req.setCbsMandateNo(mandates.getCbsMandateNo());
				req.setInitiationMode(mandates.getTxnInitiationMode());
				req.setBidReferenceNumber(mandates.getBidRefNo());
				req.setRespCode(ConstantI.MANDATE_CODE_SUCCESS);
			}
		}
		return req;
	}

	private ReqResp setPayeeParam(final ReqPay reqPay, ReqResp req) {
		List<PayeeType> payees = reqPay.getPayees().getPayee();
		for (PayeeType payeeType : payees) {
			req.setPayeeAmount(payeeType.getAmount().getValue());
			req.setPayeeAddr(payeeType.getAddr());
			req.setPayeeName(payeeType.getName());
			req.setPayeeCode(payeeType.getCode());
			req.setPayeeType(payeeType.getType().value());
			if (null != payeeType.getDevice()) {
				for (Tag tag : payeeType.getDevice().getTag()) {
					if (ConstantI.MOBILE.equalsIgnoreCase(tag.getName().value())) {
						req.setPayeeDeviceMobile(tag.getValue());
					}
					if (ConstantI.TYPE.equalsIgnoreCase(tag.getName().value())) {
						req.setPayeeDeviceType(tag.getValue());
					}
				}
			}
			AccountType ac = payeeType.getAc();
			req.setPayeeAddrType(ac.getAddrType().value());
			if (ConstantI.ACCOUNT.equalsIgnoreCase(ac.getAddrType().value())) {
				//req.setAddrType(ac.getAddrType().value());
				List<Detail> details = ac.getDetail();
				for (Detail detail : details) {
					if (ConstantI.IFSC.equalsIgnoreCase(detail.getName().value())) {
						req.setPayeeIfsc(detail.getValue());
					}
					if (ConstantI.ACNUM.equalsIgnoreCase(detail.getName().value())) {
						req.setPayeeAcNum(detail.getValue());
					}
					if (ConstantI.ACTYPE.equalsIgnoreCase(detail.getName().value())) {
						req.setPayeeAcType(detail.getValue());
					}
				}
			}
			else if (ConstantI.MOBILE.equalsIgnoreCase(ac.getAddrType().value())) {
				List<Detail> details = ac.getDetail();
				for (Detail detail : details) {
					if (ConstantI.MOBNUM.equalsIgnoreCase(detail.getName().value())) {
						req.setPayeeMobileNo(detail.getValue());
					}
					if (ConstantI.MMID.equalsIgnoreCase(detail.getName().value())) {
						req.setPayeeMmid(detail.getValue());
					}
				}
			}
			else if (ConstantI.AADHAAR.equalsIgnoreCase(ac.getAddrType().value())) {
				List<Detail> details = ac.getDetail();
				for (Detail detail : details) {
					if (ConstantI.IIN.equalsIgnoreCase(detail.getName().value())) {
						req.setPayeeIin(detail.getValue());
					}
					if (ConstantI.UIDNUM.equalsIgnoreCase(detail.getName().value())) {
						req.setPayeeUidNum(detail.getValue());
					}
				}
			}
		}
		return req;
	}

	private ReqResp setPayerParam(final ReqPay reqPay, ReqResp req) {
		req.setPayerAmount(reqPay.getPayer().getAmount().getValue());
		req.setPayerAddr(reqPay.getPayer().getAddr());
		req.setPayerName(reqPay.getPayer().getName());
		req.setPayerCode(reqPay.getPayer().getCode());
		req.setPayerType(reqPay.getPayer().getType().toString());
		if (null != reqPay.getPayer().getDevice()) {
			for (Tag tag : reqPay.getPayer().getDevice().getTag()) {
				if (ConstantI.MOBILE.equalsIgnoreCase(tag.getName().value())) {
					req.setPayerDeviceMobile(tag.getValue());
				}
				if (ConstantI.TYPE.equalsIgnoreCase(tag.getName().value())) {
					req.setPayerDeviceType(tag.getValue());
				}
			}
		}
		if (null != reqPay.getPayer().getCreds()) {
			List<Cred> credList = reqPay.getPayer().getCreds().getCred();
			for (Cred cred : credList) {
				req.setCredMpin(cred.getData().getValue());
			}
		}
		AccountType ac = reqPay.getPayer().getAc();
		req.setPayerAddrType(ac.getAddrType().value());
		
		if (ConstantI.ACCOUNT.equalsIgnoreCase(ac.getAddrType().value())) {
			//req.setAddrType(ac.getAddrType().value());
			List<Detail> details = ac.getDetail();
			for (Detail detail : details) {
				if (ConstantI.IFSC.equalsIgnoreCase(detail.getName().value())) {
					req.setPayerIfsc(detail.getValue());
				}
				if (ConstantI.ACNUM.equalsIgnoreCase(detail.getName().value())) {
					req.setPayerAcNum(detail.getValue());
				//	req.setPayerAccNum(detail.getValue());
				}
				if (ConstantI.ACTYPE.equalsIgnoreCase(detail.getName().value())) {
					req.setPayerAcType(detail.getValue());
				}
			}
		}
		else if (ConstantI.MOBILE.equalsIgnoreCase(ac.getAddrType().value())) {
			List<Detail> details = ac.getDetail();
			for (Detail detail : details) {
				if (ConstantI.MOBNUM.equalsIgnoreCase(detail.getName().value())) {
					req.setPayerMobileNo(detail.getValue());
				}
				if (ConstantI.MMID.equalsIgnoreCase(detail.getName().value())) {
					req.setPayerMmid(detail.getValue());
				}
			}
		}
		else if (ConstantI.AADHAAR.equalsIgnoreCase(ac.getAddrType().value())) {

			List<Detail> details = ac.getDetail();
			for (Detail detail : details) {
				if (ConstantI.IIN.equalsIgnoreCase(detail.getName().value())) {
					req.setPayerIin(detail.getValue());
				}
				if (ConstantI.UIDNUM.equalsIgnoreCase(detail.getName().value())) {
					req.setPayerUidNum(detail.getValue());
				}
			}
		}
		return req;
	}

	private static MerchantsTxnDto mapMerchantsTxnDto(final ReqResp reqResp, Date txnDate) {
		try {
			MerchantsTxnDto ob = new MerchantsTxnDto();
			ob.setAmount(reqResp.getPayerAmount());
			ob.setApprovalNo(reqResp.getRefApprovalNum());
			ob.setBankId(merchantBankId);
			ob.setBharatQR(reqResp.isBharatQR());
			ob.setChannel(reqResp.getTxnNote());
			ob.setCustomerRefId(reqResp.getRrn());
			ob.setErrorCode(reqResp.getCbsErrorCode());
			ob.setNarration(reqResp.getTxnNote());
			ob.setNpciErrorCode(reqResp.getRespCode());
			ob.setPayeeAccountNo(reqResp.getPayeeAcNum());
			ob.setPayeeActype(reqResp.getPayeeAcType());
			ob.setPayeeAdd(reqResp.getPayeeAddr());
			ob.setPayeeCode(reqResp.getPayeeCode());
			ob.setPayeeIfsc(reqResp.getPayeeIfsc());
			ob.setPayeeMobileNo(reqResp.getPayeeMobileNo());
			ob.setPayeeName(reqResp.getPayeeName());
			ob.setPayerAccountNo(reqResp.getPayerAcNum());
			ob.setPayerCode(reqResp.getPayerCode());
			ob.setPayerIfscCode(reqResp.getPayerIfsc());
			ob.setPayerMobileNo(reqResp.getPayerDeviceMobile());
			ob.setPayerName(reqResp.getPayerName());
			ob.setPayerVpa(reqResp.getPayerAddr());
			if (ConstantI.CODE_SUCCESS.equalsIgnoreCase(reqResp.getRespCode())) {
				ob.setStatus(ConstantI.SUCCESS);
			} else {
				ob.setStatus(ConstantI.FAILURE);
			}
			ob.setTxnId(reqResp.getTxnId());
			ob.setTxnType(reqResp.getTxnType());
			ob.setPayeeAccountName(ob.getPayeeName());
			ob.setSettlementStatus(ob.getStatus());
			ob.setCreatedAt(txnDate);
			return ob;
		} catch (Exception e) {
			log.error("error in mapMerchantsTxnDto {}", e);
			ErrorLog.sendError(e, UpiReqPayServiceImpl.class);
		}
		return null;
	}
	private boolean isFIR(String inMode) {
		// initiationMode="00|01|02|03|04|05|06|07|08|09|10|11|12|13|14|15"
		// 00=Default ,01=QR Code ,02=Secure QR Code ,03=Bharat QR Code
		// ,04=Intent ,05=Secure Intent ,06=NFC(Near Field Communication)
		// ,07=BLE (Bluetooth) ,08=UHF(Ultra High Frequency) ,09=Aadhaar
		// ,10=SDK (Software Development Kit) 11=UPI-Mandate ,12= FIR (Foreign Inward
		// Remittance) ,13= QR Mandate
		// ,14= BBPS ,15= SEBI ,16 and 17..future use
		if ("12".equals(inMode)) {
			return true;
		}
		return false;
	}

	private boolean isUPIMandate(String inMode) {
		if ("11".equals(inMode)||"13".equals(inMode)) {
			return true;
		}
		return false;
	}
	
	private String getB3_B3_Code(String txnType) {
		if ("CREDIT".equalsIgnoreCase(txnType)) {
			return "B3";
		} else {
			return "B3";
		}
	}

	private String getSA_SA_Code(String txnType) {
		if ("CREDIT".equalsIgnoreCase(txnType)) {
			return "SA";
		} else {
			return "SA";
		}
	}
	//NRI not Supported
	private String getNE_NE_Code(String txnType) {
		if ("CREDIT".equalsIgnoreCase(txnType)) {
			return "NE";
		} else {
			return "NE";
		}
	}
	
	String saveFirInfo(ReqPay reqPay) {
		try {
			FirInfo firinfo=new FirInfo();
			firinfo.setAddressCity(reqPay.getPayer().getInstitution().getOriginator().getAddress().getCity());
			firinfo.setAddressCountry(reqPay.getPayer().getInstitution().getOriginator().getAddress().getCountry());
			firinfo.setAddressGeocode(reqPay.getPayer().getInstitution().getOriginator().getAddress().getGeocode());
			firinfo.setAddressLocation(reqPay.getPayer().getInstitution().getOriginator().getAddress().getLocation());
			firinfo.setBeneficiaryName(reqPay.getPayer().getInstitution().getBeneficiary().getName());
			firinfo.setInstitutionRoute(reqPay.getPayer().getInstitution().getRoute().value());
			firinfo.setInstitutionType(reqPay.getPayer().getInstitution().getType().toString());
			firinfo.setNameAcNum(reqPay.getPayer().getInstitution().getName().getAcNum());
			firinfo.setNameValue(reqPay.getPayer().getInstitution().getName().getValue());
			firinfo.setOriginatorName(reqPay.getPayer().getInstitution().getOriginator().getName());
			firinfo.setOriginatorRefNo(reqPay.getPayer().getInstitution().getOriginator().getRefNo());
			firinfo.setOriginatorType(reqPay.getPayer().getInstitution().getOriginator().getType().value());
			firinfo.setPurposeCode(reqPay.getPayer().getInstitution().getPurpose().getCode());
			firinfo.setPurposeNote(reqPay.getPayer().getInstitution().getPurpose().getNote());
			firinfo.setIdentityType(reqPay.getPayer().getInfo().getIdentity().getType().value());
			firinfo.setIdentityValue(reqPay.getPayer().getInfo().getIdentity().getId());
			firinfo.setReqHeadMsgId(reqPay.getHead().getMsgId());
			firinfo.setTxnCustRef(reqPay.getTxn().getCustRef());
			firinfo.setTxnId(reqPay.getTxn().getId());
			firinfo.setTxnTs(reqPay.getTxn().getTs());
			firinfo.setPayerName(reqPay.getPayer().getName());
			String detail=reqRespDebitCreditDao.insertReq(firinfo);
			return detail;
		} catch (Exception e) {
			log.error("Error in Initiation Mode FIR {}", e);
			ErrorLog.sendError(e, UpiReqPayServiceImpl.class);
		}
		return null;
	}
	private String getXB_XC_Code(String txnType) {
		if (ConstantI.CREDIT.equalsIgnoreCase(txnType)) {
			return "XC";
		} else {
			return "XB";
		}
	}
	
	private boolean accTypeValidations(ReqResp req) {
        log.info("inside start accTypeValidations");
		boolean isValid = false;
		if (ConstantI.DEBIT.equalsIgnoreCase(req.getTxnType())) {
			if (ConstantI.TYPE_PERSON.equalsIgnoreCase(req.getPayerType())) {
				isValid = validateDebitIfPayerPerson(req);
			} else if (ConstantI.ENTITY.equalsIgnoreCase(req.getPayerType())) {
				isValid = validateDebitIfPayerEntity(req);
			} else {
				// not found required condition.
			}
		} else if (ConstantI.CREDIT.equalsIgnoreCase(req.getTxnType())) {
			if (ConstantI.TYPE_PERSON.equalsIgnoreCase(req.getPayeeType())) {
				isValid = validateCreditIfPayeePerson(req);
			} else if (ConstantI.ENTITY.equalsIgnoreCase(req.getPayeeType())) {
				isValid = validateCreditIfPayeeEntity(req);
			} else {
				// not found required condition.
			}
		} else if (ConstantI.REVERSAL.equalsIgnoreCase(req.getTxnType())){
					isValid = true;
			}
        log.info("inside end accTypeValidations ,isValid={}",isValid);
		return isValid;
	}
	
	private boolean validateDebitIfPayerPerson(ReqResp req) {
		boolean isValid = false;
		if (ConstantI.SAVINGS.equalsIgnoreCase(req.getPayerAcType())||ConstantI.SOD.equalsIgnoreCase(req.getPayerAcType())
				||ConstantI.UOD.equalsIgnoreCase(req.getPayerAcType())) {
			// check payee acc
			if (ConstantI.SAVINGS.equalsIgnoreCase(req.getPayeeAcType()) || ConstantI.CURRENT.equalsIgnoreCase(req.getPayeeAcType())
					|| ConstantI.SOD.equalsIgnoreCase(req.getPayeeAcType()) || ConstantI.UOD.equalsIgnoreCase(req.getPayeeAcType())) {
				isValid = true;
			}
		} else if (ConstantI.CURRENT.equalsIgnoreCase(req.getPayerAcType())) {
			// check payee acc
			if (ConstantI.SAVINGS.equalsIgnoreCase(req.getPayeeAcType()) || ConstantI.CURRENT.equalsIgnoreCase(req.getPayeeAcType())
					|| ConstantI.SOD.equalsIgnoreCase(req.getPayeeAcType()) || ConstantI.UOD.equalsIgnoreCase(req.getPayeeAcType())) {
				isValid = true;
			}
		} else if (ConstantI.DEFAULT.equalsIgnoreCase(req.getPayerAcType())) {

		} else if (ConstantI.CREDIT.equalsIgnoreCase(req.getPayerAcType())) {

		} else if (ConstantI.NRE.equalsIgnoreCase(req.getPayerAcType())) {

		} else if (ConstantI.NRO.equalsIgnoreCase(req.getPayerAcType())) {

		} else if (ConstantI.PPIWALLET.equalsIgnoreCase(req.getPayerAcType())) {

		} else if (ConstantI.BANKWALLET.equalsIgnoreCase(req.getPayerAcType())) {

		} else {

		}
		return isValid;
	}

	private boolean validateDebitIfPayerEntity(ReqResp req) {
		boolean isValid = false;
		if (ConstantI.SAVINGS.equalsIgnoreCase(req.getPayerAcType())||ConstantI.SOD.equalsIgnoreCase(req.getPayerAcType())) {
			// check payee acc
			if (ConstantI.SAVINGS.equalsIgnoreCase(req.getPayeeAcType()) || ConstantI.CURRENT.equalsIgnoreCase(req.getPayeeAcType())
					|| ConstantI.SOD.equalsIgnoreCase(req.getPayeeAcType()) || ConstantI.UOD.equalsIgnoreCase(req.getPayeeAcType())) {
				isValid = true;
			}
		} else if (ConstantI.CURRENT.equalsIgnoreCase(req.getPayerAcType())) {

		} else if (ConstantI.DEFAULT.equalsIgnoreCase(req.getPayerAcType())) {

		} else if (ConstantI.CREDIT.equalsIgnoreCase(req.getPayerAcType())) {

		} else if (ConstantI.NRE.equalsIgnoreCase(req.getPayerAcType())) {

		} else if (ConstantI.NRO.equalsIgnoreCase(req.getPayerAcType())) {

		} else if (ConstantI.UOD.equalsIgnoreCase(req.getPayerAcType())) {
			// check payee acc
			if (ConstantI.CURRENT.equalsIgnoreCase(req.getPayeeAcType()) && ConstantI.ENTITY.equalsIgnoreCase(req.getPayeeType())) {
				isValid = true;
			}
		} else if (ConstantI.PPIWALLET.equalsIgnoreCase(req.getPayerAcType())) {

		} else if (ConstantI.BANKWALLET.equalsIgnoreCase(req.getPayerAcType())) {

		} else {

		}
		return isValid;
	}

	private boolean validateCreditIfPayeePerson(ReqResp req) {
		boolean isValid = false;
		if (ConstantI.SAVINGS.equalsIgnoreCase(req.getPayeeAcType())||ConstantI.SOD.equalsIgnoreCase(req.getPayeeAcType())
				||ConstantI.UOD.equalsIgnoreCase(req.getPayeeAcType())) {
			// check payer acc
			if (ConstantI.SAVINGS.equalsIgnoreCase(req.getPayerAcType()) || ConstantI.CURRENT.equalsIgnoreCase(req.getPayerAcType())
					|| ConstantI.SOD.equalsIgnoreCase(req.getPayerAcType())) {
				isValid = true;
			}
		} else if (ConstantI.CURRENT.equalsIgnoreCase(req.getPayeeAcType())) {
			// check payer acc
			if (ConstantI.SAVINGS.equalsIgnoreCase(req.getPayerAcType()) || ConstantI.CURRENT.equalsIgnoreCase(req.getPayerAcType())
					|| ConstantI.SOD.equalsIgnoreCase(req.getPayerAcType())) {
				isValid = true;
			}
		} else if (ConstantI.DEFAULT.equalsIgnoreCase(req.getPayeeAcType())) {

		} else if (ConstantI.CREDIT.equalsIgnoreCase(req.getPayeeAcType())) {

		} else if (ConstantI.NRE.equalsIgnoreCase(req.getPayeeAcType())) {

		} else if (ConstantI.NRO.equalsIgnoreCase(req.getPayeeAcType())) {

		} else if (ConstantI.PPIWALLET.equalsIgnoreCase(req.getPayeeAcType())) {

		} else if (ConstantI.BANKWALLET.equalsIgnoreCase(req.getPayeeAcType())) {

		} else {

		}
		return isValid;
	}

	private boolean validateCreditIfPayeeEntity(ReqResp req) {
		boolean isValid = false;
		if (ConstantI.SAVINGS.equalsIgnoreCase(req.getPayeeAcType())||ConstantI.SOD.equalsIgnoreCase(req.getPayeeAcType())
				||ConstantI.UOD.equalsIgnoreCase(req.getPayeeAcType())) {
			// check payer acc
			if (ConstantI.SAVINGS.equalsIgnoreCase(req.getPayerAcType()) || ConstantI.CURRENT.equalsIgnoreCase(req.getPayerAcType())
					|| ConstantI.SOD.equalsIgnoreCase(req.getPayerAcType())) {
				isValid = true;
			}
		} else if (ConstantI.CURRENT.equalsIgnoreCase(req.getPayeeAcType())) {
			// check payer acc
			if (ConstantI.SAVINGS.equalsIgnoreCase(req.getPayerAcType()) || ConstantI.CURRENT.equalsIgnoreCase(req.getPayerAcType())
					|| ConstantI.SOD.equalsIgnoreCase(req.getPayerAcType()) || ConstantI.UOD.equalsIgnoreCase(req.getPayerAcType())) {
				isValid = true;
			}
		} else if (ConstantI.DEFAULT.equalsIgnoreCase(req.getPayeeAcType())) {

		} else if (ConstantI.CREDIT.equalsIgnoreCase(req.getPayeeAcType())) {

		} else if (ConstantI.NRE.equalsIgnoreCase(req.getPayeeAcType())) {

		} else if (ConstantI.NRO.equalsIgnoreCase(req.getPayeeAcType())) {

		} else if (ConstantI.PPIWALLET.equalsIgnoreCase(req.getPayeeAcType())) {

		} else if (ConstantI.BANKWALLET.equalsIgnoreCase(req.getPayeeAcType())) {

		} else {

		}
		return isValid;
	}
	
	private void mandateTxn(final ReqPay reqPay, RespPay.Resp resp, Ref ref) {
		// TODO discussion
		/*final String umn = reqPay.getPayer().getAddr();
		final MandatesEntity mandates = mandatesDao.findByUmn(umn);

		if (mandates == null || StringUtils.isEmpty(mandates.getMandateUmn())) {
			resp.setResult(ResultType.FAILURE);
			resp.setErrCode(ErrorCode.MANDATE_ISSUER_VF.getUpiCode());
			ref.setRespCode(ErrorCode.MANDATE_ISSUER_VF.getUpiCode());
		} else {
			String base64MandateSign = null;
			List<Cred> creds = reqPay.getPayer().getCreds().getCred();
			for (Cred cred : creds) {
				if (CredType.UPI_MANDATE.toString().equals(cred.getType().toString())
						&& CredSubType.DS.toString().equals(cred.getSubType().toString())) {
					base64MandateSign = cred.getData().getValue();
					break;
				}
			}

			if (base64MandateSign == null || !base64MandateSign.equals(mandates.getMandateSignValue())) {
				// failed
				resp.setResult(ResultType.FAILURE);
				resp.setErrCode(ErrorCode.MANDATE_ISSUER_VH.getUpiCode());
				ref.setRespCode(ErrorCode.MANDATE_ISSUER_VH.getUpiCode());
			} else if (MandateStatus.MANDATE_REVOKED.getStatus() == mandates.getStatus()) {
				resp.setResult(ResultType.FAILURE);
				resp.setErrCode(ErrorCode.MANDATE_ISSUER_VA.getUpiCode());
				ref.setRespCode(ErrorCode.MANDATE_ISSUER_VA.getUpiCode());
			} else if (!reqPay.getPayer().getAddr().equals(mandates.getPayerAddr())) {
				resp.setResult(ResultType.FAILURE);
				resp.setErrCode(ErrorCode.MANDATE_ISSUER_VG.getUpiCode());
				ref.setRespCode(ErrorCode.MANDATE_ISSUER_VG.getUpiCode());

			} else if (AmtRuleType.EXACT.toString().equals(mandates.getMandateAmountrule())
					&& !reqPay.getPayer().getAmount().toString().equals(mandates.getMandateAmountvalue())
					|| AmtRuleType.MAX.toString().equals(mandates.getMandateAmountrule())
							&& (Double.valueOf(reqPay.getPayer().getAmount().getValue()) > Double
									.valueOf(mandates.getMandateAmountvalue()))) {
				resp.setResult(ResultType.FAILURE);
				resp.setErrCode(ErrorCode.MANDATE_ISSUER_VD.getUpiCode());
				ref.setRespCode(ErrorCode.MANDATE_ISSUER_VD.getUpiCode());

			} else if (!MandateUtil.checkForTxnDayAndRecPattern(mandates)) {
				resp.setResult(ResultType.FAILURE);
				resp.setErrCode(ErrorCode.MANDATE_ISSUER_VI.getUpiCode());
				ref.setRespCode(ErrorCode.MANDATE_ISSUER_VI.getUpiCode());
			} else {
				resp.setResult(ResultType.SUCCESS);
			}
		}*/
		//resp.setResult(ResultType.SUCCESS);
		/*resp.setResult(ResultType.FAILURE);
		resp.setErrCode(Util.getProperty("ERROR_CODE_TESTING"));
		ref.setRespCode(Util.getProperty("ERROR_CODE_TESTING"));*/
		resp.setResult(ResultType.SUCCESS);
	}
}
