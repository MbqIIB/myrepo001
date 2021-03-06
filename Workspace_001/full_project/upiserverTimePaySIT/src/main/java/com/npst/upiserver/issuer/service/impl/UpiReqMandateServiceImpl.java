package com.npst.upiserver.issuer.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import com.npst.upiserver.constant.UpiApiName;
import com.npst.upiserver.dao.MandatesDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.MandatesEntity;
import com.npst.upiserver.issuer.service.UpiReqMandateService;
import com.npst.upiserver.npcischema.AccountType;
import com.npst.upiserver.npcischema.AccountType.Detail;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.CredSubType;
import com.npst.upiserver.npcischema.CredType;
import com.npst.upiserver.npcischema.CredsType.Cred;
import com.npst.upiserver.npcischema.DeviceType.Tag;
import com.npst.upiserver.npcischema.HeadType;
import com.npst.upiserver.npcischema.MandateSign;
import com.npst.upiserver.npcischema.MandateSignId;
import com.npst.upiserver.npcischema.PayConstant;
import com.npst.upiserver.npcischema.PayeeType;
import com.npst.upiserver.npcischema.Ref;
import com.npst.upiserver.npcischema.RefType;
import com.npst.upiserver.npcischema.ReqMandate;
import com.npst.upiserver.npcischema.RespMandate;
import com.npst.upiserver.npcischema.RespType;
import com.npst.upiserver.npcischema.ResultType;
import com.npst.upiserver.service.IdGeneratorService;
import com.npst.upiserver.service.MiddlewareRestConService;
import com.npst.upiserver.service.NpciUpiRestConService;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.Util;

@Service
public class UpiReqMandateServiceImpl implements UpiReqMandateService {
	private static final Logger log = LoggerFactory.getLogger(UpiReqMandateServiceImpl.class);
	private static boolean isMandate_ALLOW = Constant.IS_MANDATE_ALLOW;
	@Autowired
	private MandatesDao mandatesDao;
	
	@Autowired
	private MiddlewareRestConService switchCom;
	
	@Autowired
	private NpciUpiRestConService npciUpiRestConService;
	
	@Autowired
	private IdGeneratorService idGeneratorService;
	
	@Override
	public void issuerProcess(final ReqMandate reqMandate) {
		
		log.info("reqMandate {}", reqMandate.toString());
		try {
			log.debug("reqMandate {}", reqMandate);
			// TODO discussion

			RespMandate respMandate = null;
			Ack ack = null;
			Date reqDate = new Date();
			ReqResp req = new ReqResp();

			req.setTxnNote(reqMandate.getTxn().getNote());
	     	req.setTxnType(ConstantI.MANDATE_TXN_TYPE);
        	req.setRrn(reqMandate.getTxn().getCustRef());
			req.setTxnId(reqMandate.getTxn().getId());
			req.setBkPrf(reqMandate.getTxn().getId().substring(0, 3));
			req.setTxnRefId(reqMandate.getTxn().getRefId());
			req.setTxnRefUrl(reqMandate.getTxn().getRefUrl());
			req.setPayerAmount(reqMandate.getMandate().getAmount().getValue());
			req.setIdentity(reqMandate.getPayer().getInfo().getIdentity().getId());
			req.setValidityEndDate(reqMandate.getMandate().getValidity().getEnd());
			log.debug("RRN is {}",idGeneratorService.getRrn());
			req.setField11(idGeneratorService.getRrn());// for local remove it
			
			if (ConstantI.PURPOSE_MANDATE.equalsIgnoreCase(reqMandate.getTxn().getPurpose())) {
				req.setiPOName(reqMandate.getMandate().getName());
				// TODO hardcode
				req.setBidReferenceNumber(reqMandate.getTxn().getRefId());// BID REF NO
				req.setInitiationMode(ConstantI.SABA_INIT);
			} else {
				req.setInitiationMode(ConstantI.NON_SABA_INIT);
				req.setiPOName(ConstantI.MANDATE_NA);
				req.setBidReferenceNumber(ConstantI.MANDATE_NA);// BID REF NO
			}
			
			if (null != reqMandate.getPayer()) {
				setPayerParam(reqMandate, req);
			}
			if (null != reqMandate.getPayees()) {
				setPayeeParam(reqMandate, req);
			}
			respMandate = new RespMandate();
			respMandate.setHead(getNewHeadType());
			String reqMsgId = reqMandate.getHead().getMsgId();
			respMandate.setTxn(reqMandate.getTxn());
			respMandate.getTxn().setRules(reqMandate.getTxn().getRules());
			respMandate.setMandate(reqMandate.getMandate());
			RespType resp = new RespType();
			resp.setReqMsgId(reqMsgId);
			List<Ref> refs = resp.getRef();
			Ref ref = null;
			ref = new Ref();
			ref.setAddr(reqMandate.getPayer().getAddr());
			ref.setRegName(reqMandate.getPayer().getName());
			ref.setType(RefType.PAYER);
			ref.setSeqNum(reqMandate.getPayer().getSeqNum());
		//	ref.setSettCurrency(ConstantI.CURR);
			AccountType ac = reqMandate.getPayer().getAc();
			if (ConstantI.ACCOUNT.equalsIgnoreCase(ac.getAddrType().value())) {
				List<Detail> details = ac.getDetail();
				for (Detail detail : details) {
					if (ConstantI.IFSC.equalsIgnoreCase(detail.getName().value())) {
						ref.setIFSC(detail.getValue());
					}
					if (ConstantI.ACNUM.equalsIgnoreCase(detail.getName().value())) {
						ref.setAcNum(detail.getValue());
					}	
				}
			}
			ref.setCode(ConstantI.CODE_0000);
			refs.add(ref);
			resp.setResult(ResultType.SUCCESS.toString());
			//refs.add(ref);
			respMandate.setResp(resp);
			try {
				String signblock = DigitalSignUtil
						.signMandate(MarshalUpi.marshal(reqMandate.getMandate()).toString());
				log.info(MarshalUpi.marshal(reqMandate.getMandate()).toString());
					
				MandateSign mandateSign = new MandateSign();
				mandateSign.setId(MandateSignId.MANDATE);
				mandateSign.setValue(signblock);
				respMandate.setSignature(mandateSign);
				
				//if(PreChkMan.precheck(reqMandate)) {

	/*if("CREATE".equalsIgnoreCase(reqMandate.getTxn().getType().toString())|| "UPDATE".equalsIgnoreCase(reqMandate.getTxn().getType().toString())
						||"REVOKE".equalsIgnoreCase(reqMandate.getTxn().getType().toString())){
					
					resp.setResult(ResultType.FAILURE.toString());
					resp.setErrCode("VM");
					ref.setSettAmount(ConstantI.CODE_00);
					ref.setRespCode("VM");
					
				}*/
				 req = mandatesDao.insertReqRespMandate(reqMandate,req,mandateSign);
				 if(!ConstantI.MANDATE_SUCCESS.equalsIgnoreCase(req.getCbsErrorCode()))
				 {
					resp.setResult(ResultType.FAILURE.toString());
					resp.setErrCode(req.getCbsErrorCode());
				//	ref.setSettAmount(ConstantI.CODE_00);
					ref.setRespCode(req.getCbsErrorCode());
				} else {
					if (upiPinValidate(req,reqMandate)) {
						boolean mandateAllowed = false;
						if (PayConstant.REVOKE.toString().equalsIgnoreCase(reqMandate.getTxn().getType().toString())) {
							String LineId = getCBSLIENId(reqMandate);
							log.debug("Lien Id for Revoke is {}",LineId);
							if(checkBlockFund(reqMandate)) {		
							req.setOperation(ConstantI.MANDTAE_OPERATION);
							req.setSubOperation(ConstantI.MANDTAE_SUB_OPER_UNBLOCK);
							if (null != LineId) {
								req.setCbsMandateNo(LineId);
							}
							req = SendtoCBS(req);
							if (ConstantI.CODE_SUCCESS.equalsIgnoreCase(req.getRespCode())) {
								mandateAllowed = true;
								log.info("Got Responce FOR REVOKE FUND {}", req.getRespCode());
							}
						}	
							 else {
									mandateAllowed = true;
								}
							
								
						} else if ((PayConstant.CREATE.toString().equalsIgnoreCase(reqMandate.getTxn().getType().toString()))) {
							if (ConstantI.BLOCK_FUND.equalsIgnoreCase(reqMandate.getMandate().getBlockFund().toString())) {
								List<MandatesEntity> mandateEntity=getListOfCBSLIENId(reqMandate);
								log.info("size of cbLien " ,mandateEntity.size());
								String mndtStartDate=null;
								if(!mandateEntity.isEmpty()) {
									req.setOperation(ConstantI.MANDTAE_OPERATION);
									if (null != mandateEntity) {
										for (MandatesEntity mandatesEntity : mandateEntity) {
											log.info("More than one CustomerAccntNo found for accntno={ ", mandatesEntity.getAcAddrTypeDetail3()+"}" , " and the mandateList is " ,mandateEntity);
											req.setCbsMandateNo(mandatesEntity.getCbsMandateNo());
											mndtStartDate=oldersStartDate(mandateEntity);
										}
									}
									    String previousAmount = getLienAmount(reqMandate);
										if (!StringUtils.isEmpty(previousAmount)) {
											req.setPreviousAmount(previousAmount);
										} else {
											req.setPreviousAmount(String.valueOf(0));
										}
									log.info(" mndtStartDate " ,mndtStartDate , " previousAmnt ",previousAmount , " for the account no. " ,req.getCbsMandateNo());									
									log.info("GET PAYER AMOUUUUUUUUUUUNT{}",req.getPayerAmount());
									req.setSubOperation(ConstantI.MANDTAE_SUB_OPER_MODIFY);
									// for cbs issue
									//req.setValidityStart(mndtStartDate);
									LocalDate today = LocalDate.now();
									DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("ddMMyyyy");
									// LocalDate formatDateTime = LocalDate.parse(today.toString(),formatter);
									String date = formatter2.format(today);
									req.setValidityStart(date);
									log.info("after changes the date accrding to CBS in update {}", date);
									req.setValidityEnd(reqMandate.getMandate().getValidity().getEnd());
								}else {
								req.setOperation(ConstantI.MANDTAE_OPERATION);
								req.setSubOperation(ConstantI.MANDTAE_SUB_OPER_BLOCK);
								req.setValidityStart(reqMandate.getMandate().getValidity().getStart());
								req.setValidityEnd(reqMandate.getMandate().getValidity().getEnd());
								}
								log.info("Final request send to middleware in create {} ", req.getValidityStart());
								log.info("Amount send to middleware in create {} ", req.getPayerAmount());
								log.info("Final request send to middleware {} ", req.toString());
								req = SendtoCBS(req);
								if (ConstantI.CODE_SUCCESS.equalsIgnoreCase(req.getRespCode())) {
									mandateAllowed = true;
									log.info("Got Responce FOR BLOCK FUND {}", req.getRespCode());
								}
							} else {
								mandateAllowed = true;
							}
						} else if ((PayConstant.UPDATE.toString().equalsIgnoreCase(reqMandate.getTxn().getType().toString()))) {
							if (ConstantI.BLOCK_FUND.equalsIgnoreCase(reqMandate.getMandate().getBlockFund().toString())) {
								List<MandatesEntity> mandateEntity = getListOfCBSLIENId(reqMandate);
								log.info("size of cbLien in update ", mandateEntity.size());
								log.info("mandateEntity list {} ", mandateEntity.toString());
								String mndtStartDate = null;
								String cbsMandateNo = null;
								if (!mandateEntity.isEmpty()) {
									req.setOperation(ConstantI.MANDTAE_OPERATION);
									if (null != mandateEntity) {
										mndtStartDate = oldersStartDate(mandateEntity);
										for (MandatesEntity mandatesEntity : mandateEntity) {
											if (mandatesEntity.getMandateUmn()
													.equalsIgnoreCase(reqMandate.getMandate().getUmn())) {
												cbsMandateNo = mandatesEntity.getCbsMandateNo();
												break;
											}
										}
									}
//									log.info("updated amount, mndtStartDate, previousAmnt, for the account no. ",updatedAmount, mndtStartDate, previousAmount, req.getCbsMandateNo());
									log.info("mndtstart date in update case ", mndtStartDate);
									String previousAmount = getLienAmount(reqMandate);
									if (!StringUtils.isEmpty(previousAmount)) {
										req.setPreviousAmount(previousAmount);
									} else {
										req.setPreviousAmount(String.valueOf(0));
									}
									
									log.info("payer amount and previous amnt in update case  ", req.getPayerAmount());
									log.info("and previous amnt in update case  ", req.getPreviousAmount());
									req.setSubOperation(ConstantI.MANDTAE_SUB_OPER_MODIFY);
								//	req.setValidityStart(mndtStartDate);
									LocalDate today = LocalDate.now();
									DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("ddMMyyyy");
									// LocalDate formatDateTime = LocalDate.parse(today.toString(),formatter);
									String date = formatter2.format(today);
									req.setValidityStart(date);
									log.info("after changes the date accrding to CBS in update {}", date);
									
									req.setValidityEnd(reqMandate.getMandate().getValidity().getEnd());
								}
								
								req.setOperation(ConstantI.MANDTAE_OPERATION);
							//	String LineId = getCBSLIENId(reqMandate);
								log.debug("Lien Id for Update is {}",cbsMandateNo);
								if (null != cbsMandateNo) {
									req.setCbsMandateNo(cbsMandateNo);
								}
								String previousAmount = getLienAmount(reqMandate);
								if (!StringUtils.isEmpty(previousAmount)) {
									req.setPreviousAmount(previousAmount);
								}
								req.setSubOperation(ConstantI.MANDTAE_SUB_OPER_MODIFY);
							// cbs	req.setValidityStart(mndtStartDate);
								LocalDate today = LocalDate.now();
								DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("ddMMyyyy");
								// LocalDate formatDateTime = LocalDate.parse(today.toString(),formatter);
								String date = formatter2.format(today);
			
								req.setValidityStart(date);
								log.info("after changes the date accrding to CBS in MANDTAE_SUB_OPER_MODIFY update {}", String.valueOf(new Date()));
								req.setValidityEnd(reqMandate.getMandate().getValidity().getEnd());
								log.info("previousAmnt, valityStartDate, ValidityEnddate before going to CBS {} ", previousAmount , req.getValidityStart() , req.getValidityEnd());
								log.info("valityStartDate before going to CBS {} ", req.getValidityStart(), mndtStartDate);
								log.info("ValidityEnddate before going to CBS {} ", req.getValidityEnd());
								req = SendtoCBS(req);
								if (ConstantI.CODE_SUCCESS.equalsIgnoreCase(req.getRespCode())) {
									mandateAllowed = true;
									log.info("Got Responce FOR UPDATE FUND {}", req.getRespCode());
								}
							} else {
								mandateAllowed = true;
							}
						}
						if (mandateAllowed) {
							mandatesDao.createUpdateMandate(reqMandate, reqDate, mandateSign,req);
							ref.setRespCode(ConstantI.CODE_00);
							resp.setResult(ResultType.SUCCESS.toString());
							ref.setApprovalNum(reqMandate.getTxn().getCustRef().substring(6)); // in fail no Aproval No
						} else {
							resp.setResult(ResultType.FAILURE.toString());
							resp.setErrCode(req.getRespCode());
							ref.setSettAmount(ConstantI.MIN_AMT);
							ref.setRespCode(req.getRespCode());
						}
					} else {
						resp.setResult(ResultType.FAILURE.toString());
						resp.setErrCode(ConstantI.UPIPIN_INVALID);
						//ref.setSettAmount(ConstantI.MIN_AMT);
						ref.setRespCode(ConstantI.UPIPIN_INVALID);
					}
				}
			
		}
				catch (Exception e) {
				log.error("Error: {}", e);
				ErrorLog.sendError(e, UpiReqMandateServiceImpl.class);
				resp.setResult(ResultType.FAILURE.toString());
				resp.setErrCode("");
			}
			// TODO discussion
			String result = null;
			respMandate.setResp(resp);
			String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(respMandate).toString());
			if (StringUtils.isNoneBlank(xmlStr)) {
				if (!ConstantI.CODE_UKN.equals(req.getRespCode())) {
					ack = npciUpiRestConService.send(xmlStr, UpiApiName.RESP_MANDATE.getName(),
							respMandate.getTxn().getId());
				}
			} else {
				log.error("Digital Sign RespMandate xmlStr is null, xmlStr: {}", xmlStr);
				result = ConstantI.UKN;
			}
			mandatesDao.updateRespMandate(respMandate, ack);
		} catch (Exception e) {
			log.error("error {}", e);
			ErrorLog.sendError(e, UpiReqMandateServiceImpl.class);
		}
	}

	private boolean checkBlockFund(ReqMandate reqMandate) {
		final MandatesEntity mandates = mandatesDao.findByUmn(reqMandate.getMandate().getUmn());
		if(ConstantI.BLOCK_FUND.equalsIgnoreCase(mandates.getAmountBlock())&&ConstantI.STATUS_2==mandates.getStatus()) {
			return true;
		}
		return false;
	}
	
	
	private String getCBSLIENId(ReqMandate reqMandate) {
		final MandatesEntity mandates = mandatesDao.findByUmn(reqMandate.getMandate().getUmn());
		if(null != mandates) {
		    log.debug("mandate lien no is {}",mandates.getCbsMandateNo());
			return mandates.getCbsMandateNo();
		}
		return null;
	}

	private String getLienAmount(ReqMandate reqMandate) {
		final MandatesEntity mandates = mandatesDao.findByUmn(reqMandate.getMandate().getUmn());
		if(null != mandates) {
		    log.debug("mandate lien no is {}",mandates.getCbsMandateNo());
			return mandates.getMandateAmountvalue();
		}
		return null;
	}
	// add payer In Req
	private void setPayerParam(final ReqMandate reqmandate,final ReqResp req) {
		try {
			req.setPayerAmount(reqmandate.getMandate().getAmount().getValue()); // changes Required
			req.setPayerAddr(reqmandate.getPayer().getAddr());
			req.setPayerName(reqmandate.getPayer().getName());
			req.setPayerCode(reqmandate.getPayer().getCode());
			req.setPayerType(reqmandate.getPayer().getType().toString());
			if (null != reqmandate.getPayer().getDevice()) {
				for (Tag tag : reqmandate.getPayer().getDevice().getTag()) {
					if (ConstantI.MOBILE.equalsIgnoreCase(tag.getName().value())) {
						req.setPayerDeviceMobile(tag.getValue());
					}
					if (ConstantI.TYPE.equalsIgnoreCase(tag.getName().value())) {
						req.setPayerDeviceType(tag.getValue());
					}
				}
			}
			if (null != reqmandate.getPayer().getCreds()) {
				List<Cred> credList = reqmandate.getPayer().getCreds().getCred();
				for (Cred cred : credList) {
					req.setCredMpin(cred.getData().getValue());
				}
			}
			AccountType ac = reqmandate.getPayer().getAc();
			req.setPayerAddrType(ac.getAddrType().value());
			if (ConstantI.ACCOUNT.equalsIgnoreCase(ac.getAddrType().value())) {
				List<Detail> details = ac.getDetail();
				for (Detail detail : details) {
					if (ConstantI.IFSC.equalsIgnoreCase(detail.getName().value())) {
						req.setPayerIfsc(detail.getValue());
					}
					if (ConstantI.ACNUM.equalsIgnoreCase(detail.getName().value())) {
						req.setPayerAcNum(detail.getValue());
					}
					if (ConstantI.ACTYPE.equalsIgnoreCase(detail.getName().value())) {
						req.setPayerAcType(detail.getValue());
					}
				}
			}

		} catch (Exception e) {
			log.error("error {}", e);
			e.printStackTrace();
		}
	}

	// Add Payee In Req

	private void setPayeeParam(final ReqMandate reqmandate, ReqResp req) {
		try {

			List<PayeeType> payees = reqmandate.getPayees().getPayee();
			for (PayeeType payeeType : payees) {
				req.setPayeeAmount(reqmandate.getMandate().getAmount().getValue());
				req.setPayeeAddr(payeeType.getAddr());
				req.setPayeeName(payeeType.getName());
				req.setPayeeCode(payeeType.getCode());
				req.setPayeeType(payeeType.getType().value());
				if (null != payeeType.getDevice()) {
					for (Tag tag : payeeType.getDevice().getTag()) {
						if (ConstantI.MOBILE.equalsIgnoreCase(tag.getName().value())) {
							// req.setDeviceMobile(tag.getValue());
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
					// req.setAddrType(ac.getAddrType().value());
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

			}

		} catch (Exception e) {
			log.error("error {}", e);
			e.printStackTrace();
		}
	}

	private boolean upiPinValidate(ReqResp req) {
		try {
			log.info("Req Mandate For Validate PIN");
			req.setTxnType(ConstantI.TXNTYPE_PV);
			
			log.info("TXN TYPE  PIN CHECK################### {}", req.getTxnType());

			req = switchCom.send(req);
			//req.setRespCode("0");
			log.info("PIN RESP CODE*********** {}, TXN TYPE {}",req.getRespCode(),req.getTxnType());
			
			req.setTxnType(ConstantI.MANDATE_TXN_TYPE);
			if (ConstantI.CODE_SUCCESS.equalsIgnoreCase(req.getRespCode())) {
				log.info("Got SUCCESS in PIN validation :{}", req.getRespCode());
				return true;//true
			} else {
				return false;//false needed 
			}

		} catch (Exception e) {
			log.error("ERROR {}", e);
		}
		return false;// true in case of bypass
	}

	
	private boolean upiPinValidate(ReqResp req, ReqMandate reqMandate) {
		try {
			if(ConstantI.PAYEE.equalsIgnoreCase(reqMandate.getTxn().getInitiatedBy().toString())
					&&ConstantI.M_REVOKE.equalsIgnoreCase(reqMandate.getTxn().getType().toString())) {
			
				log.info("chack for DS Bz initiated by PAYEE");
				
				final MandatesEntity mandates = mandatesDao.findByUmn(reqMandate.getMandate().getUmn());

				String base64MandateSign = null;
				List<Cred> creds = reqMandate.getPayer().getCreds().getCred();
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
					return false;
				} 
				return true;
			}
			log.info("Req Mandate For Validate PIN");
			req.setTxnType(ConstantI.TXNTYPE_PV);
			
			log.info("TXN TYPE  PIN CHECK################### {}", req.getTxnType());
			req = switchCom.send(req); //pin by pass
			log.info("PIN RESP CODE*********** {}, TXN TYPE {}",req.getRespCode(),req.getTxnType());
		//	req.setRespCode("0");
			req.setTxnType(ConstantI.MANDATE_TXN_TYPE);
			if (ConstantI.CODE_SUCCESS.equalsIgnoreCase(req.getRespCode())) {
				log.info("Got SUCCESS in PIN validation :{}", req.getRespCode());
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			log.error("ERROR {}", e);
		}
		return false;// true in case of bypass
	}
	
	
	
	
	
	
	
	private ReqResp SendtoCBS(ReqResp req) {
		try {


			log.info("Sending CBS to BLOCK FUND");
			
			req.setTxnType(ConstantI.MANDATE_TXN_TYPE);
			log.info("TXN TYPE  CREATE/UPDATE######### {}", req.getTxnType());
			log.info("before middleware {}", req.getTxnType());
			req = switchCom.send(req);
			log.info("ater middleware {}", req.getTxnType());
			req.setCbsMandateNo(req.getCbsMandateNo());
			req.setRespCode(req.getRespCode());
			log.info("RESP FOR CREATE , UPDATE MODIFY ######## {} , TXN TYPE {}",req.getRespCode(),req.getTxnType());
			return req;
		} catch (Exception e) {
			log.error("ERROR BLKUND {}", e);
		}
		return null;
	}

	public static HeadType getNewHeadType() {
		HeadType head = new HeadType();
		head.setMsgId(Util.uuidGen());
		head.setOrgId(Constant.orgId);
		head.setTs(Util.getTS());
		head.setVer(Constant.headVer);
		return head;
	}
	
	// by ashish
		private List<MandatesEntity> getListOfCBSLIENId(ReqMandate reqMandate) {
			String accNumber=null;
			List<MandatesEntity> cbsLinenIdList=new ArrayList<MandatesEntity>();
			AccountType ac=reqMandate.getPayer().getAc();
			List<Detail> details=ac.getDetail();
			for (Detail detail : details) {
				if (ConstantI.ACNUM.equalsIgnoreCase(detail.getName().value())) {
					accNumber=detail.getValue();
				}
			}
			
			final List<MandatesEntity> mandates = mandatesDao.findByAccountAndStatus(accNumber,"CREATE","UPDATE");
			if(null != mandates) {
			    log.debug("mandate lien no is {}",mandates.size());
			    for (MandatesEntity mandatesEntity : mandates) {
			    	cbsLinenIdList.add(mandatesEntity); 
				}
				return cbsLinenIdList;
			}
			return null;
		}	

		private String oldersStartDate(List<MandatesEntity> mandateEntity) {
			String finalstartdate = null;
			LocalDate today = LocalDate.now();
			for (MandatesEntity mandateEntity1 : mandateEntity) {
				String validityStart = mandateEntity1.getMandateValidityStart();
				log.info("validitstsrt date in oldersStartDate from mandate table{} ", validityStart);
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
				LocalDate validateStartParsing = LocalDate.parse(validityStart, formatter);

				log.info("validitstsrt date after parsing{} ", validateStartParsing);
				if (validateStartParsing.isBefore(today)) {
					finalstartdate = validityStart;
					today = validateStartParsing;
					log.info("finalstartdate date in if{} ", finalstartdate);
				} else {
					log.info("finalstartdate date return{} ", finalstartdate);
					DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("ddMMyyyy");
					// LocalDate formatDateTime = LocalDate.parse(today.toString(),formatter);
					String date = formatter2.format(today);
					// finalstartdate=today.toString();
					finalstartdate = date;
				}
				// TODO date check
			}
			log.info("finalstartdate date in else{} ", finalstartdate);
			return finalstartdate;
		}	
		
}
