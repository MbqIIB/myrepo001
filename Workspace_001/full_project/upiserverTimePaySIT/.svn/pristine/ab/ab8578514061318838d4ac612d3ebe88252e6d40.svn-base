package com.npst.upiserver.dao.impl;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.constant.ErrorCode;
import com.npst.upiserver.constant.MandateStatus;
import com.npst.upiserver.dao.CustomerAccountDao;
import com.npst.upiserver.dao.MandatesDao;
import com.npst.upiserver.dao.RegistrationDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.CustomerMandatesEntity;
import com.npst.upiserver.entity.MandatesEntity;
import com.npst.upiserver.entity.MandatesHistoryEntity;
import com.npst.upiserver.entity.ReqRespMandatesEntity;
import com.npst.upiserver.entity.ReqRespMandatesPayeesEntity;
import com.npst.upiserver.npcischema.AccountType;
import com.npst.upiserver.npcischema.AccountType.Detail;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.CredsType;
import com.npst.upiserver.npcischema.DeviceTagNameType;
import com.npst.upiserver.npcischema.InitiatedByType;
import com.npst.upiserver.npcischema.DeviceType.Tag;
import com.npst.upiserver.npcischema.MandateSign;
import com.npst.upiserver.npcischema.MandateType;
import com.npst.upiserver.npcischema.PayConstant;
import com.npst.upiserver.npcischema.PayeeType;
import com.npst.upiserver.npcischema.PayeesType;
import com.npst.upiserver.npcischema.PayerConstant;
import com.npst.upiserver.npcischema.Ref;
import com.npst.upiserver.npcischema.ReqMandate;
import com.npst.upiserver.npcischema.ReqMandateConfirmation;
import com.npst.upiserver.npcischema.ReqPay;
import com.npst.upiserver.npcischema.RespMandate;
import com.npst.upiserver.npcischema.ResultType;
import com.npst.upiserver.repo.CustomerMandatesRepo;
import com.npst.upiserver.repo.MandatesHistoryRepo;
import com.npst.upiserver.repo.MandatesRepo;
import com.npst.upiserver.repo.ReqRespMandatesPayeesRepo;
import com.npst.upiserver.repo.ReqRespMandatesRepo;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.JsonMan;
import com.npst.upiserver.util.NpciSchemaUtil;
import com.npst.upiserver.util.Util;
@Component
public class MandatesDaoImpl implements MandatesDao {
	private static final Logger log = LoggerFactory.getLogger(MandatesDaoImpl.class);

	@Autowired
	private MandatesRepo mandatesRepo;
	@Autowired
	private ReqRespMandatesRepo reqRespMandatesRepo;
	@Autowired
	private ReqRespMandatesPayeesRepo reqRespMandatesPayeesRepo;
	@Autowired
	private CustomerMandatesRepo customerMandatesRepo;
	
	@Autowired
	private RegistrationDao registrationDao;

	@Autowired
	private MandatesHistoryRepo mandatesHistoryRepo;
	
	@Autowired
	private CustomerAccountDao customerAccountDao;

	@Override
	public MandatesEntity findByUmn(String umn) {
		try {
			List<MandatesEntity> list = mandatesRepo.findByMandateUmn(umn);
			if (list != null && list.size() >= 1) {
				if (list.size() == 1) {
					return list.get(0);
				} else {
					log.warn("more than one umn entry found in MandatesEntity ie umn={},size={}", umn, list.size());
				}
			}
		} catch (Exception e) {
			log.error("error while  findByUmn {}", e);
			ErrorLog.sendError(e, MandatesDaoImpl.class);
		}
		return null;
	}

	CustomerMandatesEntity getCustomerMandatesEntityByUmn(String mandateUmn) {
		List<CustomerMandatesEntity> list = customerMandatesRepo.findByMandateUmnOrderByIdDesc(mandateUmn);
		if (list.size() == 1) {
			return list.get(0);
		} else if (list.size() == 0) {
			log.warn("CustomerMandatesEntity not found for MandateUmn={}", mandateUmn);
		} else {
			log.warn("More than one CustomerMandatesEntity found for MandateUmn={}", mandateUmn);
		}
		return null;
	}

	@Override
	public void createUpdateMandate(ReqMandate reqMandate, Date reqDate,MandateSign mandateSign,ReqResp req) {
		try {
			if (PayConstant.CREATE.toString().equalsIgnoreCase(reqMandate.getTxn().getType().toString())) {
				MandatesEntity mandate = new MandatesEntity();
				mandate.setStatus(MandateStatus.MANDATE_SUCCESS.getStatus());
				mandate.setReqInsert(reqDate);
				mandate.setSign(mandateSign.getValue());
				mandate.setMandateSignId(mandateSign.getId().toString());
				mandate.setMandateSignValue(mandateSign.getValue());
				/**
				 * txn
				 */
				mandate.setTxnId(reqMandate.getTxn().getId());
				mandate.setTxnNote(reqMandate.getTxn().getNote());
				mandate.setTxnCustRef(reqMandate.getTxn().getCustRef());
				mandate.setTxnRefid(reqMandate.getTxn().getRefId());
				mandate.setTxnType(reqMandate.getTxn().getType().value());
				mandate.setTxnOrgId(reqMandate.getTxn().getOrgTxnId());
				mandate.setTxnInitiationMode(req.getInitiationMode());
				mandate.setPurpose(reqMandate.getTxn().getPurpose());
				mandate.setCbsMandateNo(req.getCbsMandateNo());
				mandate.setBidRefNo(req.getBidReferenceNumber());
				/**
				 * mandate
				 */
				mandate.setAmountBlock(reqMandate.getMandate().getBlockFund().toString());
				mandate.setMandateName(reqMandate.getMandate().getName());
				mandate.setMandateTxnId(reqMandate.getMandate().getTxnId());
				mandate.setMandateUmn(reqMandate.getMandate().getUmn());
				mandate.setMandateTs(reqMandate.getMandate().getTs());
				mandate.setMandateRevokeable(reqMandate.getMandate().getRevokeable().value());//
				
				if(null!=reqMandate.getMandate().getShareToPayee()) {
					mandate.setMandateShareToPayee(reqMandate.getMandate().getShareToPayee().value());// IF PAYEE INITIATED REQ This Will not code
				}
				
				mandate.setMandateType(reqMandate.getMandate().getType());
				mandate.setMandateValidityStart(reqMandate.getMandate().getValidity().getStart());
				mandate.setMandateValidityEnd(reqMandate.getMandate().getValidity().getEnd());
				mandate.setMandateAmountvalue(reqMandate.getMandate().getAmount().getValue());//
				mandate.setMandateAmountrule(reqMandate.getMandate().getAmount().getRule().value());//
				if (reqMandate.getMandate().getRecurrence() != null) {
					mandate.setMandateRecurrencepattern(reqMandate.getMandate().getRecurrence().getPattern().value());//

					if (reqMandate.getMandate().getRecurrence().getRule() != null)
						mandate.setMandateRecurrenceRulevalue(
								reqMandate.getMandate().getRecurrence().getRule().getValue());
					if (reqMandate.getMandate().getRecurrence().getRule() != null
							&& reqMandate.getMandate().getRecurrence().getRule().getType() != null)
						mandate.setMandateRecurrenceRuletype(
								reqMandate.getMandate().getRecurrence().getRule().getType().value());

				}

				/**
				 * payer
				 */
				mandate.setPayerAddr(reqMandate.getPayer().getAddr());
				mandate.setPayerName(reqMandate.getPayer().getName());
				mandate.setPayerSeqNum(reqMandate.getPayer().getSeqNum());
				mandate.setPayerType(reqMandate.getPayer().getType().value());
				mandate.setPayerCode(reqMandate.getPayer().getCode());
				mandate.setPayerHandal(reqMandate.getPayer().getAddr()
						.substring(reqMandate.getPayer().getAddr().indexOf(ConstantI.CONST_SPCL_AT_RATE)));

				/**
				 * account details
				 */
			//	List<Detail> acDetails = reqMandate.getPayer().getAc().getDetail();
				String acAddrTypeDetail1 = ConstantI.CONST_BLANK, AcAddrTypeDetail2 = ConstantI.CONST_BLANK,
						AcAddrTypeDetail3 = ConstantI.CONST_BLANK;
				
				
				
				
				AccountType ac = reqMandate.getPayer().getAc();
				req.setPayerAddrType(ac.getAddrType().value());
				if (ConstantI.ACCOUNT.equalsIgnoreCase(ac.getAddrType().value())) {
					List<Detail> details = ac.getDetail();
					for (Detail detail : details) {
						if (ConstantI.IFSC.equalsIgnoreCase(detail.getName().value())) {
							AcAddrTypeDetail2=detail.getValue();
						}
						if (ConstantI.ACNUM.equalsIgnoreCase(detail.getName().value())) {
							AcAddrTypeDetail3=detail.getValue();
						}
						if (ConstantI.ACTYPE.equalsIgnoreCase(detail.getName().value())) {
							acAddrTypeDetail1=detail.getValue();
						}
					}
				}
				mandate.setAcAddrType(reqMandate.getPayer().getAc().getAddrType().value());
				mandate.setAcAddrTypeDetail1(acAddrTypeDetail1);

				mandate.setAcAddrTypeDetail2(AcAddrTypeDetail2);
				mandate.setAcAddrTypeDetail3(AcAddrTypeDetail3);
				if (null != reqMandate.getPayer().getCreds()) {
					List<CredsType.Cred> creadsList = reqMandate.getPayer().getCreds().getCred();
					if (!creadsList.isEmpty()) {
						String creadsSubType = ConstantI.CONST_BLANK;
						String creadsType = ConstantI.CONST_BLANK;
						for (int i = 0; i < creadsList.size(); i++) {
							creadsSubType.concat(creadsList.get(i).getSubType().value() + ConstantI.CONST_SPCL_PIPE);
							creadsType.concat(creadsList.get(i).getType().value() + ConstantI.CONST_SPCL_PIPE);
						}
						mandate.setCredSubType(creadsSubType);
						mandate.setCredType(creadsType);
					}	
				}

				/*
				 * Payees Info
				 */
				PayeesType payeesType = reqMandate.getPayees();
				List<PayeeType> payeeList = payeesType.getPayee();
				PayeeType payeeType = payeeList.get(0);
				mandate.setPayeeAddr(payeeType.getAddr());
				mandate.setPayeeCode(payeeType.getCode());
				mandate.setPayeeHandal(payeeType.getAddr().substring(payeeType.getAddr().indexOf('@')));
				mandate.setPayeeName(payeeType.getName());
				mandate.setPayeeSeqNum(payeeType.getSeqNum());
				mandate.setPayeeType(payeeType.getType().value());

				if (null != payeeType.getInfo()) {
					mandate.setInfoId(payeeType.getInfo().getIdentity().getId());
					if (payeeType.getInfo().getRating().getVerifiedAddress() != null)
						mandate.setInfoIdRatingvaddr(payeeType.getInfo().getRating().getVerifiedAddress().value());
					mandate.setInfoIdType(payeeType.getInfo().getIdentity().getType().value());
					mandate.setInfoIdVerifiedName(payeeType.getInfo().getIdentity().getVerifiedName());
				}

				/*if (null != payeeType.getAc()) {
					List<Detail> acDetailsPayee = payeeType.getAc().getDetail();
					if (!acDetailsPayee.isEmpty()) {
						String acaddrTypeDetailPayee1 = "", acaddrTypeDetailPayee2 = "", acaddrTypeDetailPayee3 = "";
						for (int i = 0; i < acDetailsPayee.size(); i++) {
							String temp = acDetailsPayee.get(i).getName().value() + "="
									+ acDetailsPayee.get(i).getValue();
							if (0 == i) {
								acaddrTypeDetailPayee1 = temp;
							} else if (1 == i) {
								acaddrTypeDetailPayee2 = temp;
							} else {
								acaddrTypeDetailPayee3 = temp;
							}
						}
						mandate.setAcAddrType(payeeType.getAc().getAddrType().value());
						mandate.setAcAddrTypeDetail1(acaddrTypeDetailPayee1);
						mandate.setAcAddrTypeDetail2(acaddrTypeDetailPayee2);
						mandate.setAcAddrTypeDetail3(acaddrTypeDetailPayee3);
					}
				}
*/
				if (payeeType.getMerchant() != null) {
					if (payeeType.getMerchant().getIdentifier() != null) {
						mandate.setMerchantSubCode(payeeType.getMerchant().getIdentifier().getSubCode());
						mandate.setMerchantMid(payeeType.getMerchant().getIdentifier().getMid());
						mandate.setMerchantSid(payeeType.getMerchant().getIdentifier().getSid());
						mandate.setMerchantTid(payeeType.getMerchant().getIdentifier().getTid());
						mandate.setMerchantType(payeeType.getMerchant().getIdentifier().getMerchantType().value());
						mandate.setMerchantGenre(payeeType.getMerchant().getIdentifier().getMerchantGenre().value());
						mandate.setMerchantOnboardingType(
								payeeType.getMerchant().getIdentifier().getOnBoardingType().value());
					}
					if (payeeType.getMerchant().getName() != null) {
						mandate.setMerchantBrandName(payeeType.getMerchant().getName().getBrand());
						mandate.setMerchantLegalName(payeeType.getMerchant().getName().getLegal());
						mandate.setMerchantFranchiseName(payeeType.getMerchant().getName().getFranchise());
					}
					if (payeeType.getMerchant().getOwnership() != null) {
						mandate.setMerchantOwnershipType(payeeType.getMerchant().getOwnership().getType().value());
					}

				}
				mandatesRepo.save(mandate);
				//log.debug("After Save mandates: {} ", JsonMan.getJSONStr(mandate));
				mandate = null;
			} else if (PayConstant.UPDATE.toString().equalsIgnoreCase(reqMandate.getTxn().getType().toString())) {
				MandatesEntity mandatesObject = findByUmn(reqMandate.getMandate().getUmn());
					mandatesObject.setStatus(MandateStatus.MANDATE_SUCCESS.getStatus());
					mandatesObject.setMandateValidityStart(reqMandate.getMandate().getValidity().getStart());
					mandatesObject.setMandateValidityEnd(reqMandate.getMandate().getValidity().getEnd());
					mandatesObject.setMandateAmountvalue(reqMandate.getMandate().getAmount().getValue());
					mandatesObject.setMandateAmountrule(reqMandate.getMandate().getAmount().getRule().value());
					mandatesObject.setTxnType(PayConstant.UPDATE.toString());
					mandatesObject.setSign(mandateSign.getValue());
					mandatesObject.setMandateSignValue(mandateSign.getValue());
					
					mandatesRepo.save(mandatesObject);
			} else if (PayConstant.REVOKE.toString().equalsIgnoreCase(reqMandate.getTxn().getType().toString())) {
				MandatesEntity mandatesObject = findByUmn(reqMandate.getMandate().getUmn());
					mandatesObject.setStatus(MandateStatus.MANDATE_REVOKED.getStatus());
					mandatesObject.setTxnType(PayConstant.REVOKE.toString());
					mandatesRepo.save(mandatesObject);
			}
			
			
			
		} catch (Exception e) {
			log.error("Error: {}", e);
			System.out.println("************"+e);
			ErrorLog.sendError(e, MandatesDaoImpl.class);
		}
		// TODO need discussion
	}

	@Override
	public ReqResp insertReqRespMandate(ReqMandate reqMandate,ReqResp req,MandateSign mandateSign) {
		try {
			
			ReqRespMandatesEntity mandate = new ReqRespMandatesEntity();
			mandate.setStatus(MandateStatus.MANDATE_INITIATED.getStatus());
			mandate.setReqInsert(new Date());
			mandate.setTxnId(reqMandate.getTxn().getId());
			mandate.setTxnNote(reqMandate.getTxn().getNote());
			mandate.setTxnCustRef(reqMandate.getTxn().getCustRef());
			mandate.setTxnRefid(reqMandate.getTxn().getRefId());
			mandate.setTxnRefurl(reqMandate.getTxn().getRefUrl());
			mandate.setTxnTs(reqMandate.getTxn().getTs());
			mandate.setTxnType(reqMandate.getTxn().getType().value());
			mandate.setTxnOrgId(reqMandate.getTxn().getOrgTxnId());
			mandate.setTxnInitiationMode(req.getInitiationMode());
			mandate.setPurpose(reqMandate.getTxn().getPurpose());
			mandate.setMandateSignId(mandateSign.getId().toString());
			mandate.setMandateSignValue(mandateSign.getValue());
			mandate.setBidRefNo(req.getBidReferenceNumber());
			/**
			 * mandate
			 */
			mandate.setMandateName(reqMandate.getMandate().getName());
			mandate.setMandateTxnId(reqMandate.getMandate().getTxnId());
			mandate.setMandateUmn(reqMandate.getMandate().getUmn());
			mandate.setMandateTs(reqMandate.getMandate().getTs());
			mandate.setMandateRevokeable(reqMandate.getMandate().getRevokeable().value());
			
			if(null!=reqMandate.getMandate().getShareToPayee()) {
				mandate.setMandateShareToPayee(reqMandate.getMandate().getShareToPayee().value());// IF PAYEE INITIATED REQ This Will not code
			}
			mandate.setMandateType(reqMandate.getMandate().getType());
			mandate.setMandateValidityStart(reqMandate.getMandate().getValidity().getStart());
			mandate.setMandateValidityEnd(reqMandate.getMandate().getValidity().getEnd());
			mandate.setMandateAmountvalue(reqMandate.getMandate().getAmount().getValue());
			mandate.setMandateAmountrule(reqMandate.getMandate().getAmount().getRule().value());
			if (reqMandate.getMandate().getRecurrence() != null) {
				if (reqMandate.getMandate().getRecurrence().getPattern() != null)
					mandate.setMandateRecurrencepattern(reqMandate.getMandate().getRecurrence().getPattern().value());
				if (reqMandate.getMandate().getRecurrence().getRule() != null
						&& reqMandate.getMandate().getRecurrence().getRule().getType() != null)
					mandate.setMandateRecurrenceRuletype(
							reqMandate.getMandate().getRecurrence().getRule().getType().value());
				if (reqMandate.getMandate().getRecurrence().getRule() != null)
					mandate.setMandateRecurrenceRulevalue(reqMandate.getMandate().getRecurrence().getRule().getValue());
			}

			/**
			 * payer
			 */
			mandate.setPayerAddr(reqMandate.getPayer().getAddr());
			mandate.setPayerName(reqMandate.getPayer().getName());
			mandate.setPayerSeqNum(reqMandate.getPayer().getSeqNum());
			mandate.setPayerType(reqMandate.getPayer().getType().value());
			mandate.setPayerCode(reqMandate.getPayer().getCode());
			mandate.setPayerHandal(reqMandate.getPayer().getAddr()
					.substring(reqMandate.getPayer().getAddr().indexOf(ConstantI.CONST_SPCL_AT_RATE)));

			/**
			 * device
			 */
			if (null != reqMandate.getPayer().getDevice()) {
				List<Tag> deviceTag = reqMandate.getPayer().getDevice().getTag();

				for (int i = 0; i < deviceTag.size(); i++) {
					if (DeviceTagNameType.APP.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
						mandate.setDeviceApp(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.CAPABILITY.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
						mandate.setDeviceCapability(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.GEOCODE.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
						mandate.setDeviceGeocode(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.ID.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
						mandate.setDeviceId(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.IP.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
						mandate.setDeviceIp(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.LOCATION.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
						mandate.setDeviceLocation(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.MOBILE.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
						mandate.setDeviceMobile(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.OS.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
						mandate.setDeviceOs(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.TYPE.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
						mandate.setDeviceType(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.TELECOM.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
						mandate.setPayerDeviceTelecom(deviceTag.get(i).getValue());
					}
				}
			}

			/**
			 * account details
			 */
		//	List<Detail> acDetails = reqMandate.getPayer().getAc().getDetail();
			String acAddrTypeDetail1 = ConstantI.CONST_BLANK, AcAddrTypeDetail2 = ConstantI.CONST_BLANK,
					AcAddrTypeDetail3 = ConstantI.CONST_BLANK;
			AccountType ac = reqMandate.getPayer().getAc();
			req.setPayerAddrType(ac.getAddrType().value());
			if (ConstantI.ACCOUNT.equalsIgnoreCase(ac.getAddrType().value())) {
				List<Detail> details = ac.getDetail();
				for (Detail detail : details) {
					if (ConstantI.IFSC.equalsIgnoreCase(detail.getName().value())) {
						AcAddrTypeDetail2=detail.getValue();
					}
					if (ConstantI.ACNUM.equalsIgnoreCase(detail.getName().value())) {
						AcAddrTypeDetail3=detail.getValue();
					}
					if (ConstantI.ACTYPE.equalsIgnoreCase(detail.getName().value())) {
						acAddrTypeDetail1=detail.getValue();
					}
				}
			}
			mandate.setAcAddrType(reqMandate.getPayer().getAc().getAddrType().value());
			mandate.setAcAddrTypeDetail1(acAddrTypeDetail1);
			mandate.setAcAddrTypeDetail2(AcAddrTypeDetail2);
			mandate.setAcAddrTypeDetail3(AcAddrTypeDetail3);
				/*if (null != reqMandate.getPayer().getCreds()) {
				List<CredsType.Cred> creadsList = reqMandate.getPayer().getCreds().getCred();
				if (!creadsList.isEmpty()) {
					String creadsSubType = ConstantI.CONST_BLANK;
					String creadsType = ConstantI.CONST_BLANK;
					for (int i = 0; i < creadsList.size(); i++) {
						creadsSubType.concat(creadsList.get(i).getSubType().value() + ConstantI.CONST_SPCL_PIPE);
						creadsType.concat(creadsList.get(i).getType().value() + ConstantI.CONST_SPCL_PIPE);
					}
					mandate.setCredSubType(creadsSubType);
					mandate.setCredType(creadsType);
				}
			}*/

			//mandate.setRespResult(ResultType.SUCCESS.toString());
			//mandate.setAck(JsonMan.getJSONStr(ack));
			//mandate.setRespErrCode(respMandate.getResp().getErrCode());

		/*	List<Ref> refList = respMandate.getResp().getRef();
			if (null != refList && !refList.isEmpty()) {
				String refType = ConstantI.CONST_BLANK, refSeqNum = ConstantI.CONST_BLANK,
						refAddr = ConstantI.CONST_BLANK, refRegName = ConstantI.CONST_BLANK,
						refSettAmount = ConstantI.CONST_BLANK, refReversalRespCode = ConstantI.CONST_BLANK,
						refOrgAmount = ConstantI.CONST_BLANK, refSettCurrency = ConstantI.CONST_BLANK,
						refAcNum = ConstantI.CONST_BLANK, refApprovalNum = ConstantI.CONST_BLANK,
						refRespCode = ConstantI.CONST_BLANK;

				for (Ref ref : refList) {
					if (ref != null) {
						refType = ref.getType() + ConstantI.CONST_SPCL_PIPE;
						refSeqNum = ref.getSeqNum() + ConstantI.CONST_SPCL_PIPE;
						refAddr = ref.getAddr() + ConstantI.CONST_SPCL_PIPE;
						refRegName = ref.getRegName() + ConstantI.CONST_SPCL_PIPE;
						refSettAmount = ref.getSettAmount() + ConstantI.CONST_SPCL_PIPE;//to do
						refSettCurrency = ref.getSettCurrency() + ConstantI.CONST_SPCL_PIPE;
						refApprovalNum = ref.getApprovalNum() + ConstantI.CONST_SPCL_PIPE;//todo
						refRespCode = ref.getRespCode() + ConstantI.CONST_SPCL_PIPE;//todo
						refReversalRespCode = ref.getReversalRespCode() + ConstantI.CONST_SPCL_PIPE;//todo
						refOrgAmount = ref.getOrgAmount() + ConstantI.CONST_SPCL_PIPE;//tpdo
					}

				}
				mandate.setRefType(refType);
				mandate.setRefSeqNum(refSeqNum);
				mandate.setRefAddr(refAddr);
				mandate.setRefRegName(refRegName);
				mandate.setRefSettAmount(refSettAmount);
				mandate.setRefSettCurrency(refSettCurrency);
				mandate.setRefAcNum(refAcNum);
				mandate.setRefApprovalNum(refApprovalNum);
				mandate.setRefRespCode(refRespCode);
				mandate.setRefReversalRespCode(refReversalRespCode);
				mandate.setRefOrgAmount(refOrgAmount);
				if (respMandate.getSignature() != null) {
					mandate.setMandateSignId(respMandate.getSignature().getId().toString());
					mandate.setMandateSignValue(respMandate.getSignature().getValue());
				}
			}*/
			reqRespMandatesRepo.save(mandate);
			
			log.debug("After Save mandates: {} ", JsonMan.getJSONStr(mandate));
			insertReqRespMandatesPayees(reqMandate.getPayees(), reqMandate.getTxn().getId(),
					reqMandate.getHead().getMsgId(), mandate.getIdMandates());
			mandate = null;
			MandatesEntity mandates = findByUmn(reqMandate.getMandate().getUmn());
			
			if (PayConstant.CREATE.toString().equalsIgnoreCase(reqMandate.getTxn().getType().toString())){
			
			if (null != mandates) {
				//return ErrorCode.QN.getUpiCode();
				
				req.setCbsErrorCode(ErrorCode.QN.getUpiCode());
				return req;

			}
			else {
				req.setCbsErrorCode(ConstantI.MANDATE_SUCCESS);
				return req;
			}
			
			/*if (ConstantI.SAVING.equalsIgnoreCase(Util.getAccpuntType(reqMandate))) {//need to check
				return ErrorCode.MANDATE_ISSUER_VM.getUpiCode();
			}*/
		}	
			else if(PayConstant.UPDATE.toString().equalsIgnoreCase(reqMandate.getTxn().getType().toString())
					||PayConstant.REVOKE.toString().equalsIgnoreCase(reqMandate.getTxn().getType().toString())) 
			{
				if (null == mandates) {
					//return ErrorCode.QN.getUpiCode();
					
					req.setCbsErrorCode(ErrorCode.VF.getUpiCode());
					return req;
				}
				
			if(!mandates.getPayerAddr().toString().trim().equalsIgnoreCase(reqMandate.getPayer().getAddr().toString().trim())) {
					
				req.setCbsErrorCode(ErrorCode.MANDATE_ISSUER_VG.getUpiCode());
					return req;
			}		
			
			if(ConstantI.STATUS_3==mandates.getStatus()) {
				req.setCbsErrorCode(ErrorCode.MANDATE_ISSUER_IB.getUpiCode());
				return req;// Amount is unblock
			}
			if(Util.isMandateExpired(mandates)){
				
				req.setCbsErrorCode(ErrorCode.VU.getUpiCode());

				return req;
			 }
			else {
				req.setCbsErrorCode(ConstantI.MANDATE_SUCCESS);
				req.setCbsMandateNo(mandates.getCbsMandateNo());
				return req;
			}
			}	
		} catch (Exception e) {
			log.error("error {}", e);
			ErrorLog.sendError(e, MandatesDaoImpl.class);
		}
		return null;
	}

	
	
	@Override
	public void insertReqRespMandatesPayees(PayeesType payeesType, String txnId, String msgId, Long idMandates) {
		// TODO need small methods
		try {
			if (null != payeesType) {
				List<PayeeType> payeeList = payeesType.getPayee();
				for (PayeeType payeeType : payeeList) {
					ReqRespMandatesPayeesEntity mandatePayeeObject = new ReqRespMandatesPayeesEntity();
					mandatePayeeObject.setIdMandates(idMandates);
					mandatePayeeObject.setTxnId(txnId);
					mandatePayeeObject.setHeadMsgId(msgId);
					mandatePayeeObject.setPayeeAddr(payeeType.getAddr());
					mandatePayeeObject.setPayeeCode(payeeType.getCode());
					mandatePayeeObject.setPayeeHandal(payeeType.getAddr().substring(payeeType.getAddr().indexOf('@')));
					mandatePayeeObject.setPayeeName(payeeType.getName());
					mandatePayeeObject.setPayeeSeqNum(payeeType.getSeqNum());
					mandatePayeeObject.setPayeeType(payeeType.getType().value());

					if (null != payeeType.getInfo()) {
						mandatePayeeObject.setInfoId(payeeType.getInfo().getIdentity().getId());
						if (payeeType.getInfo().getRating().getVerifiedAddress() != null)
							mandatePayeeObject
									.setInfoIdRatingvaddr(payeeType.getInfo().getRating().getVerifiedAddress().value());
						mandatePayeeObject.setInfoIdType(payeeType.getInfo().getIdentity().getType().value());
						mandatePayeeObject.setInfoIdVerifiedName(payeeType.getInfo().getIdentity().getVerifiedName());
					}
					if (null != payeeType.getDevice()) {
						List<Tag> deviceTagPayee = payeeType.getDevice().getTag();
						if (!deviceTagPayee.isEmpty()) {
							for (int i = 0; i < deviceTagPayee.size(); i++) {
								if (DeviceTagNameType.APP.value().equals(deviceTagPayee.get(i).getName().value())) {
									mandatePayeeObject.setDeviceApp(deviceTagPayee.get(i).getValue());
								}
								if (DeviceTagNameType.CAPABILITY.value()
										.equals(deviceTagPayee.get(i).getName().value())) {
									mandatePayeeObject.setDeviceCapability(deviceTagPayee.get(i).getValue());
								}
								if (DeviceTagNameType.GEOCODE.value().equals(deviceTagPayee.get(i).getName().value())) {
									mandatePayeeObject.setDeviceGeocode(deviceTagPayee.get(i).getValue());
								}
								if (DeviceTagNameType.ID.value().equals(deviceTagPayee.get(i).getName().value())) {
									mandatePayeeObject.setDeviceId(deviceTagPayee.get(i).getValue());
								}
								if (DeviceTagNameType.IP.value().equals(deviceTagPayee.get(i).getName().value())) {
									mandatePayeeObject.setDeviceIp(deviceTagPayee.get(i).getValue());
								}
								if (DeviceTagNameType.LOCATION.value()
										.equals(deviceTagPayee.get(i).getName().value())) {
									mandatePayeeObject.setDeviceLocation(deviceTagPayee.get(i).getValue());
								}
								if (DeviceTagNameType.MOBILE.value().equals(deviceTagPayee.get(i).getName().value())) {
									mandatePayeeObject.setDeviceMobile(deviceTagPayee.get(i).getValue());
								}
								if (DeviceTagNameType.OS.value().equals(deviceTagPayee.get(i).getName().value())) {
									mandatePayeeObject.setDeviceOs(deviceTagPayee.get(i).getValue());
								}
								if (DeviceTagNameType.TYPE.value().equals(deviceTagPayee.get(i).getName().value())) {
									mandatePayeeObject.setDeviceType(deviceTagPayee.get(i).getValue());
								}
								if (DeviceTagNameType.TELECOM.value().equals(deviceTagPayee.get(i).getName().value())) {
									mandatePayeeObject.setPayeeDeviceTelecom(deviceTagPayee.get(i).getValue());
								}
							}
						}
					}
					if (null != payeeType.getAc()) {
						List<Detail> acDetailsPayee = payeeType.getAc().getDetail();
						if (!acDetailsPayee.isEmpty()) {
							String acaddrTypeDetailPayee1 = "", acaddrTypeDetailPayee2 = "",
									acaddrTypeDetailPayee3 = "";
							for (int i = 0; i < acDetailsPayee.size(); i++) {
								String temp = acDetailsPayee.get(i).getName().value() + "="
										+ acDetailsPayee.get(i).getValue();
								if (0 == i) {
									acaddrTypeDetailPayee1 = temp;
								} else if (1 == i) {
									acaddrTypeDetailPayee2 = temp;
								} else {
									acaddrTypeDetailPayee3 = temp;
								}
							}
							mandatePayeeObject.setAcAddrType(payeeType.getAc().getAddrType().value());
							mandatePayeeObject.setAcAddrTypeDetail1(acaddrTypeDetailPayee1);
							mandatePayeeObject.setAcAddrTypeDetail2(acaddrTypeDetailPayee2);
							mandatePayeeObject.setAcAddrTypeDetail3(acaddrTypeDetailPayee3);
						}
					}

					if (payeeType.getMerchant() != null) {
						if (payeeType.getMerchant().getIdentifier() != null) {
							mandatePayeeObject.setMerchantSubCode(payeeType.getMerchant().getIdentifier().getSubCode());
							mandatePayeeObject.setMerchantMid(payeeType.getMerchant().getIdentifier().getMid());
							mandatePayeeObject.setMerchantSid(payeeType.getMerchant().getIdentifier().getSid());
							mandatePayeeObject.setMerchantTid(payeeType.getMerchant().getIdentifier().getTid());
							mandatePayeeObject
									.setMerchantType(payeeType.getMerchant().getIdentifier().getMerchantType().value());
							mandatePayeeObject.setMerchantGenre(
									payeeType.getMerchant().getIdentifier().getMerchantGenre().value());
							mandatePayeeObject.setMerchantOnboardingType(
									payeeType.getMerchant().getIdentifier().getOnBoardingType().value());
						}
						if (payeeType.getMerchant().getName() != null) {
							mandatePayeeObject.setMerchantBrandName(payeeType.getMerchant().getName().getBrand());
							mandatePayeeObject.setMerchantLegalName(payeeType.getMerchant().getName().getLegal());
							mandatePayeeObject
									.setMerchantFranchiseName(payeeType.getMerchant().getName().getFranchise());
						}
						if (payeeType.getMerchant().getOwnership() != null) {
							mandatePayeeObject
									.setMerchantOwnershipType(payeeType.getMerchant().getOwnership().getType().value());
						}

					}
					reqRespMandatesPayeesRepo.save(mandatePayeeObject);
				}
			}
		} catch (Exception e) {
			log.error("Error: {}", e);
			ErrorLog.sendError(e, MandatesDaoImpl.class);
		}

	}
	
	

	@Override
	public void UpdateMandate(ReqPay reqPay,Date reqDate,ReqResp req){
		
		if (PayConstant.DEBIT.toString().equalsIgnoreCase(reqPay.getTxn().getType().toString())) {
			MandatesEntity mandatesObject = findByUmn(reqPay.getPayer().getAddr());
		
				mandatesObject.setStatus(MandateStatus.MANDATE_REVOKED.getStatus());
				mandatesObject.setTxnType(PayConstant.DEBIT.toString());
			
                	mandatesObject.setRespInsert(reqDate);
				mandatesRepo.save(mandatesObject);
			}else if (PayConstant.REVERSAL.toString().equalsIgnoreCase(reqPay.getTxn().getType().toString())) {
				MandatesEntity mandatesObject = findByUmn(reqPay.getPayer().getAddr());
				mandatesObject.setStatus(MandateStatus.MANDATE_SUCCESS.getStatus());
				mandatesObject.setTxnType(PayConstant.REVERSAL.toString());
				mandatesObject.setRespInsert(reqDate);
			//	mandatesObject.setCbsMandateNo(req.getCbsMandateNo());
				mandatesRepo.save(mandatesObject);
			}
			
		}
		
		

	@Override
	public void insertReqMandate(ReqMandate reqMandate, Ack ack) {
		// TODO Auto-generated method stub

		try {
			ReqRespMandatesEntity reqrespmandate = new ReqRespMandatesEntity();
			reqrespmandate.setReqInsert(new Date());
			reqrespmandate.setReqHeadMsgId(reqMandate.getHead().getMsgId());
			reqrespmandate.setReqHeadOrgId(reqMandate.getHead().getOrgId());
			reqrespmandate.setReqHeadTs(reqMandate.getHead().getTs());
			reqrespmandate.setTxnCustRef(reqMandate.getTxn().getCustRef());
			reqrespmandate.setTxnId(reqMandate.getTxn().getId());
			reqrespmandate.setTxnIdPrf(NpciSchemaUtil.getTxnIdPrefix(reqMandate.getTxn().getId()));
			reqrespmandate.setTxnNote(reqMandate.getTxn().getNote());
			reqrespmandate.setTxnRefid(reqMandate.getTxn().getRefId());
			reqrespmandate.setTxnRefurl(reqMandate.getTxn().getRefUrl());
			reqrespmandate.setTxnTs(reqMandate.getTxn().getTs());
			reqrespmandate.setTxnType(reqMandate.getTxn().getType().value());
			reqrespmandate.setTxnInitiationMode(reqMandate.getTxn().getInitiationMode());
			reqrespmandate.setPayerAddr(reqMandate.getPayer().getAddr());
			reqrespmandate.setPayerCode(reqMandate.getPayer().getCode());
			reqrespmandate.setPayerSeqNum(reqMandate.getPayer().getSeqNum());
			reqrespmandate.setPayerType(reqMandate.getPayer().getType().value());
			reqrespmandate.setPayerHandal(NpciSchemaUtil.getHandler(reqMandate.getPayer().getAddr()));
			reqrespmandate.setPayerName(reqMandate.getPayer().getName());

			// reqrespmandate.setAmountCrr(reqMandate.getPayer().getAmount().getCurr());
			// reqrespmandate.setAmountVal(reqMandate.getPayer().getAmount().getValue());
			if (null != reqMandate.getPayer().getAc()) {

				List<Detail> acDetails = reqMandate.getPayer().getAc().getDetail();
				if (acDetails != null && !acDetails.isEmpty()) {
					reqrespmandate.setAcAddrType(reqMandate.getPayer().getAc().getAddrType().value());
					String acAddrTypeDetail1 = "", AcAddrTypeDetail2 = "", AcAddrTypeDetail3 = "";
					for (int i = 0; i < acDetails.size(); i++) {
						String temp = acDetails.get(i).getName().value() + "=" + acDetails.get(i).getValue();
						if (0 == i) {

							acAddrTypeDetail1 = temp;
						} else if (1 == i) {
							AcAddrTypeDetail2 = temp;
						} else {
							AcAddrTypeDetail3 = temp;
						}
					}
					reqrespmandate.setAcAddrTypeDetail1(acAddrTypeDetail1);
					reqrespmandate.setAcAddrTypeDetail2(AcAddrTypeDetail2);
					reqrespmandate.setAcAddrTypeDetail3(AcAddrTypeDetail3);
				}
			}
			if (null != reqMandate.getPayer().getCreds()) {
				List<CredsType.Cred> creadsList = reqMandate.getPayer().getCreds().getCred();
				if (0 < creadsList.size()) {
					String creadsSubType = "", creadsType = "";
					for (int i = 0; i < creadsList.size(); i++) {
						creadsSubType += creadsList.get(i).getSubType().value() + "|";
						creadsType += creadsList.get(i).getType().value() + "|";
					}
					reqrespmandate.setCredSubType(creadsSubType);
					reqrespmandate.setCredType(creadsType);
				}
			}
			if (null != reqMandate.getPayer().getDevice()) {
				List<Tag> deviceTag = reqMandate.getPayer().getDevice().getTag();
				if (!deviceTag.isEmpty()) {
					for (int i = 0; i < deviceTag.size(); i++) {
						if (DeviceTagNameType.APP.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
							reqrespmandate.setDeviceApp(deviceTag.get(i).getValue());
						}
						if (DeviceTagNameType.CAPABILITY.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
							reqrespmandate.setDeviceCapability(deviceTag.get(i).getValue());
						}
						if (DeviceTagNameType.GEOCODE.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
							reqrespmandate.setDeviceGeocode(deviceTag.get(i).getValue());
						}
						if (DeviceTagNameType.ID.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
							reqrespmandate.setDeviceId(deviceTag.get(i).getValue());
						}
						if (DeviceTagNameType.IP.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
							reqrespmandate.setDeviceIp(deviceTag.get(i).getValue());
						}
						if (DeviceTagNameType.LOCATION.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
							reqrespmandate.setDeviceLocation(deviceTag.get(i).getValue());
						}
						if (DeviceTagNameType.MOBILE.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
							reqrespmandate.setDeviceMobile(deviceTag.get(i).getValue());
						}
						if (DeviceTagNameType.OS.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
							reqrespmandate.setDeviceOs(deviceTag.get(i).getValue());
						}
						if (DeviceTagNameType.TYPE.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
							reqrespmandate.setDeviceType(deviceTag.get(i).getValue());
						}
						if (DeviceTagNameType.TELECOM.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
							reqrespmandate.setPayerDeviceTelecom(deviceTag.get(i).getValue());
						}
					}
				}
			}
			if (null != reqMandate.getPayer().getInfo()) {
				reqrespmandate.setInfoId(reqMandate.getPayer().getInfo().getIdentity().getId());
				reqrespmandate
						.setInfoIdRatingvaddr(reqMandate.getPayer().getInfo().getRating().getVerifiedAddress().value());
				reqrespmandate.setInfoIdType(reqMandate.getPayer().getInfo().getIdentity().getType().value());
				reqrespmandate.setInfoIdVerifiedName(reqMandate.getPayer().getInfo().getIdentity().getVerifiedName());
			}

			reqrespmandate.setAck(JsonMan.getJSONStr(ack));

			/**
			 * Setting mandate related info here...
			 */
			final MandateType mandateType = reqMandate.getMandate();
			reqrespmandate.setMandateName(mandateType.getName());
			reqrespmandate.setMandateAmountrule(mandateType.getAmount().getRule().value());
			reqrespmandate.setMandateAmountvalue(mandateType.getAmount().getValue());
			reqrespmandate.setMandateRecurrencepattern(mandateType.getRecurrence().getPattern().value());
			reqrespmandate.setMandateRecurrenceRuletype(mandateType.getRecurrence().getRule().getType().value());
			reqrespmandate.setMandateRecurrenceRulevalue(mandateType.getRecurrence().getRule().getValue());
			reqrespmandate.setMandateRevokeable(mandateType.getRevokeable().value());
			reqrespmandate.setMandateShareToPayee(mandateType.getShareToPayee().value());
			reqrespmandate.setMandateTs(mandateType.getTs());
			reqrespmandate.setMandateTxnId(mandateType.getTxnId());
			reqrespmandate.setMandateType(mandateType.getType());
			reqrespmandate.setMandateUmn(mandateType.getUmn());
			reqrespmandate.setMandateValidityEnd(mandateType.getValidity().getEnd());
			reqrespmandate.setMandateValidityStart(mandateType.getValidity().getStart());
			reqRespMandatesRepo.save(reqrespmandate);
			insertPayees(reqMandate.getPayees(), reqMandate.getTxn().getId(), reqMandate.getHead().getMsgId(),
					reqrespmandate.getIdMandates());
		} catch (Exception e) {
			log.error("Error: {}", e);
			ErrorLog.sendError(e, MandatesDaoImpl.class);
		}

	}

	void insertPayees(PayeesType payeesType, String txnId, String msgId, long idReqRespMandates) {

		try {
			if (null != payeesType) {
				List<PayeeType> payeeList = payeesType.getPayee();
				for (PayeeType payeeType : payeeList) {
					ReqRespMandatesPayeesEntity dbPayee = new ReqRespMandatesPayeesEntity();
					dbPayee.setIdMandates(idReqRespMandates);

					dbPayee.setTxnId(txnId);

					dbPayee.setHeadMsgId(msgId);

					dbPayee.setPayeeAddr(payeeType.getAddr());
					dbPayee.setPayeeCode(payeeType.getCode());

					dbPayee.setPayeeHandal(payeeType.getAddr().substring(payeeType.getAddr().indexOf("@")));

					dbPayee.setPayeeName(payeeType.getName());

					dbPayee.setPayeeSeqNum(payeeType.getSeqNum());

					dbPayee.setPayeeType(payeeType.getType().value());

					if (null != payeeType.getInfo()) {
						dbPayee.setInfoId(payeeType.getInfo().getIdentity().getId());
						dbPayee.setInfoIdRatingvaddr(payeeType.getInfo().getRating().getVerifiedAddress().value());
						dbPayee.setInfoIdType(payeeType.getInfo().getIdentity().getType().value());
						dbPayee.setInfoIdVerifiedName(payeeType.getInfo().getIdentity().getVerifiedName());
					}
					if (null != payeeType.getDevice()) {
						List<Tag> deviceTagPayee = payeeType.getDevice().getTag();
						if (!deviceTagPayee.isEmpty()) {
							for (int i = 0; i < deviceTagPayee.size(); i++) {
								if (DeviceTagNameType.APP.value().equals(deviceTagPayee.get(i).getName().value())) {
									dbPayee.setDeviceApp(deviceTagPayee.get(i).getValue());
								}
								if (DeviceTagNameType.CAPABILITY.value()
										.equals(deviceTagPayee.get(i).getName().value())) {
									dbPayee.setDeviceCapability(deviceTagPayee.get(i).getValue());
								}
								if (DeviceTagNameType.GEOCODE.value().equals(deviceTagPayee.get(i).getName().value())) {
									dbPayee.setDeviceGeocode(deviceTagPayee.get(i).getValue());
								}
								if (DeviceTagNameType.ID.value().equals(deviceTagPayee.get(i).getName().value())) {
									dbPayee.setDeviceId(deviceTagPayee.get(i).getValue());
								}
								if (DeviceTagNameType.IP.value().equals(deviceTagPayee.get(i).getName().value())) {
									dbPayee.setDeviceIp(deviceTagPayee.get(i).getValue());
								}
								if (DeviceTagNameType.LOCATION.value()
										.equals(deviceTagPayee.get(i).getName().value())) {
									dbPayee.setDeviceLocation(deviceTagPayee.get(i).getValue());
								}
								if (DeviceTagNameType.MOBILE.value().equals(deviceTagPayee.get(i).getName().value())) {
									dbPayee.setDeviceMobile(deviceTagPayee.get(i).getValue());
								}
								if (DeviceTagNameType.OS.value().equals(deviceTagPayee.get(i).getName().value())) {
									dbPayee.setDeviceOs(deviceTagPayee.get(i).getValue());
								}
								if (DeviceTagNameType.TYPE.value().equals(deviceTagPayee.get(i).getName().value())) {
									dbPayee.setDeviceType(deviceTagPayee.get(i).getValue());
								}
							}
						}
					}
					if (null != payeeType.getAc()) {
						List<Detail> acDetailsPayee = payeeType.getAc().getDetail();
						if (0 < acDetailsPayee.size()) {
							String acaddrTypeDetailPayee1 = "", acaddrTypeDetailPayee2 = "",
									acaddrTypeDetailPayee3 = "";
							for (int i = 0; i < acDetailsPayee.size(); i++) {
								String temp = acDetailsPayee.get(i).getName().value() + "="
										+ acDetailsPayee.get(i).getValue();
								if (0 == i) {
									acaddrTypeDetailPayee1 = temp;
								} else if (1 == i) {
									acaddrTypeDetailPayee2 = temp;
								} else {
									acaddrTypeDetailPayee3 = temp;
								}
							}
							dbPayee.setAcAddrType(payeeType.getAc().getAddrType().value());
							dbPayee.setAcAddrTypeDetail1(acaddrTypeDetailPayee1);
							dbPayee.setAcAddrTypeDetail2(acaddrTypeDetailPayee2);
							dbPayee.setAcAddrTypeDetail3(acaddrTypeDetailPayee3);
						}
					}

					// Save the Merchant Details in DB Dated::18-12-18
					if (payeeType.getMerchant() != null) {
						if (payeeType.getMerchant().getIdentifier() != null) {
							dbPayee.setMerchantSubCode(payeeType.getMerchant().getIdentifier().getSubCode());
							dbPayee.setMerchantMid(payeeType.getMerchant().getIdentifier().getMid());
							dbPayee.setMerchantSid(payeeType.getMerchant().getIdentifier().getSid());
							dbPayee.setMerchantTid(payeeType.getMerchant().getIdentifier().getTid());
							dbPayee.setMerchantType(payeeType.getMerchant().getIdentifier().getMerchantType().value());
							dbPayee.setMerchantGenre(
									payeeType.getMerchant().getIdentifier().getMerchantGenre().value());
							dbPayee.setMerchantOnboardingType(
									payeeType.getMerchant().getIdentifier().getOnBoardingType().value());
						}
						if (payeeType.getMerchant().getName() != null) {
							dbPayee.setMerchantBrandName(payeeType.getMerchant().getName().getBrand());
							dbPayee.setMerchantLegalName(payeeType.getMerchant().getName().getLegal());
							dbPayee.setMerchantFranchiseName(payeeType.getMerchant().getName().getFranchise());
						}
						if (payeeType.getMerchant().getOwnership() != null) {
							dbPayee.setMerchantOwnershipType(payeeType.getMerchant().getOwnership().getType().value());
						}

					}
					reqRespMandatesPayeesRepo.save(dbPayee);
				}
			}
		} catch (Exception e) {
			log.error("Error: {}", e);
			ErrorLog.sendError(e, MandatesDaoImpl.class);
		}
	}

	@Override
	public void updateRespMandate(RespMandate respMandate,Ack ack) {
		// TODO Auto-generated method stub
		try {
			ReqRespMandatesEntity reqrespmandate = getReqRespMandatesEntityByTxnId(respMandate.getTxn().getId());
			reqrespmandate.setRespHeadTs(respMandate.getHead().getTs());
			reqrespmandate.setRespHeadOrgId(respMandate.getHead().getOrgId());
			reqrespmandate.setRespHeadMsgId(respMandate.getHead().getMsgId());
			reqrespmandate.setRespInsert(new Date());
			reqrespmandate.setRespResult(respMandate.getResp().getResult());
			reqrespmandate.setRespErrCode(respMandate.getResp().getErrCode());
			List<Ref> refList = respMandate.getResp().getRef();
			String refType = ConstantI.CONST_BLANK, refSeqNum = ConstantI.CONST_BLANK, refAddr = ConstantI.CONST_BLANK,
					refRegName = ConstantI.CONST_BLANK, refSettAmount = ConstantI.CONST_BLANK,
					refReversalRespCode = ConstantI.CONST_BLANK, refOrgAmount = ConstantI.CONST_BLANK,
					refSettCurrency = ConstantI.CONST_BLANK, refAcNum = ConstantI.CONST_BLANK,
					refApprovalNum = ConstantI.CONST_BLANK, refRespCode = ConstantI.CONST_BLANK;

			for (Ref ref : refList) {
				refSettAmount += ref.getSettAmount() != null ? ref.getSettAmount() + ConstantI.CONST_SPCL_PIPE
						: ConstantI.CONST_BLANK + ConstantI.CONST_SPCL_PIPE;
				refApprovalNum += ref.getApprovalNum() != null ? ref.getApprovalNum() + ConstantI.CONST_SPCL_PIPE
						: ConstantI.CONST_BLANK + ConstantI.CONST_SPCL_PIPE;
				refRespCode += ref.getRespCode() != null ? ref.getRespCode() + ConstantI.CONST_SPCL_PIPE
						: ConstantI.CONST_BLANK + ConstantI.CONST_SPCL_PIPE;
				refReversalRespCode += ref.getReversalRespCode() != null
						? ref.getReversalRespCode() + ConstantI.CONST_SPCL_PIPE
						: ConstantI.CONST_BLANK + ConstantI.CONST_SPCL_PIPE;
				refOrgAmount += ref.getOrgAmount() != null ? ref.getOrgAmount() + ConstantI.CONST_SPCL_PIPE
						: ConstantI.CONST_BLANK + ConstantI.CONST_SPCL_PIPE;
				refRegName += ref.getRegName() != null ? ref.getRegName() + ConstantI.CONST_SPCL_PIPE
						: ConstantI.CONST_BLANK + ConstantI.CONST_SPCL_PIPE;
				refAddr += ref.getAddr() != null ? ref.getAddr() + ConstantI.CONST_SPCL_PIPE
						: ConstantI.CONST_BLANK + ConstantI.CONST_SPCL_PIPE;
				refSeqNum += ref.getSeqNum() != null ? ref.getSeqNum() + ConstantI.CONST_SPCL_PIPE
						: ConstantI.CONST_BLANK + ConstantI.CONST_SPCL_PIPE;
				refType += ref.getType() != null ? ref.getType() + ConstantI.CONST_SPCL_PIPE
						: ConstantI.CONST_BLANK + ConstantI.CONST_SPCL_PIPE;
				refSettCurrency += ref.getSettCurrency() != null ? ref.getSettCurrency() + ConstantI.CONST_SPCL_PIPE
						: ConstantI.CONST_BLANK + ConstantI.CONST_SPCL_PIPE;
			}
			reqrespmandate.setRefType(refType);
			reqrespmandate.setRefSeqNum(refSeqNum);
			reqrespmandate.setRefAddr(refAddr);
			reqrespmandate.setRefRegName(refRegName);
			reqrespmandate.setRefSettAmount(refSettAmount);
			reqrespmandate.setRefSettCurrency(refSettCurrency);
			reqrespmandate.setRefAcNum(refAcNum);
			reqrespmandate.setRefApprovalNum(refApprovalNum);
			reqrespmandate.setRefRespCode(refRespCode);
			reqrespmandate.setRefReversalRespCode(refReversalRespCode);
			reqrespmandate.setRefOrgAmount(refOrgAmount);
			reqrespmandate.setAck(JsonMan.getJSONStr(ack));
			reqRespMandatesRepo.save(reqrespmandate);
		} catch (Exception e) {
			log.error("Error: {}", e);
			ErrorLog.sendError(e, MandatesDaoImpl.class);
		}

	}

	private ReqRespMandatesEntity getReqRespMandatesEntityByTxnId(String txnId) {
		// TODO Auto-generated method stub
		List<ReqRespMandatesEntity> list = reqRespMandatesRepo.findByTxnId(txnId);
		if (list.size() == 1) {
			return list.get(0);
		} else if (list.size() == 0) {
			log.warn("ReqRespMandatesEntity not found for txnId={}", txnId);
		} else {
			log.warn("More than one ReqRespMandatesEntity found for txnId={}", txnId);
		}
		return null;
	}

	private MandatesHistoryEntity getMandatesHistoryEntityByTxnId(String txnId) {
		// TODO Auto-generated method stub
		List<MandatesHistoryEntity> list = mandatesHistoryRepo.findByTxnId(txnId);
		if (list.size() == 1) {
			return list.get(0);
		} else if (list.size() == 0) {
			log.warn("MandatesHistoryEntity not found for txnId={}", txnId);
		} else {
			log.warn("More than one MandatesHistoryEntity found for txnId={}", txnId);
		}
		return null;
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
	
	@Override
	public void insertSuccessCustomerMandates(RespMandate respMandate) {
		// TODO Auto-generated method stub
		try {
			
			log.info("INSIDE CUSTOMER MANDATE SAME Method");
			MandatesHistoryEntity mandateHistory = null;
			
			mandateHistory=getmandateHitory(mandateHistory,respMandate);
			
			//MandatesHistoryEntity mandateHistory = getMandatesHistoryByUMN(respMandate.getMandate().getUmn());

			//log.info("Get the data...");
			log.info("AFTER MMERCHANT HISTORY");
			MandateSign signature = respMandate.getSignature();
			CustomerMandatesEntity customerMandates = setCustomerMandates(mandateHistory, respMandate);
				//	respMandate.getTxn().getType().toString(), respMandate.getMandate().getUmn(),
				//	respMandate.getTxn().getInitiatedBy().toString());
            customerMandates.setCustis(respMandate.getTxn().getInitiatedBy().toString().equalsIgnoreCase(ConstantI.PAYEE)?ConstantI.PAYEE:ConstantI.PAYER);//"PAYER"
			if (customerMandates != null && signature != null && !StringUtils.isEmpty(signature.getValue())) {
				customerMandates.setMandateSignId(signature.getId().toString());
				customerMandates.setMandateSignValue(signature.getValue());
			}
			customerMandatesRepo.save(customerMandates);
		} catch (Exception e) {
			log.error("Error: {}", e);
			ErrorLog.sendError(e, MandatesDaoImpl.class);
		}

	}
	
	private MandatesHistoryEntity getmandateHitory(MandatesHistoryEntity mandateHistory, RespMandate respMandate) {
		if(InitiatedByType.PAYER.toString().equals(respMandate.getTxn().getInitiatedBy().toString())
				&&"CREATE".equalsIgnoreCase(respMandate.getTxn().getType().toString())) {
			mandateHistory = getMandatesHistoryEntityByTxnIdandType(respMandate.getTxn().getId(),ConstantI.CREATE_SEND);
			log.info("IN {} ",ConstantI.CREATE_SEND);

		}
		else if(InitiatedByType.PAYER.toString().equals(respMandate.getTxn().getInitiatedBy().toString())
				&&"UPDATE".equalsIgnoreCase(respMandate.getTxn().getType().toString())) {
			
			mandateHistory = getMandatesHistoryEntityByTxnIdandType(respMandate.getTxn().getId(),ConstantI.UPDATE_SEND);
			log.info("IN {} ",ConstantI.UPDATE_SEND);
		}
		else if(InitiatedByType.PAYER.toString().equals(respMandate.getTxn().getInitiatedBy().toString())
				&&"REVOKE".equalsIgnoreCase(respMandate.getTxn().getType().toString())) {
			
			mandateHistory = getMandatesHistoryEntityByTxnIdandType(respMandate.getTxn().getId(),ConstantI.REVOKE_SEND);

			log.info("IN {} ",ConstantI.REVOKE_SEND);
		}
		else if(InitiatedByType.PAYEE.toString().equals(respMandate.getTxn().getInitiatedBy().toString())
				&&"CREATE".equalsIgnoreCase(respMandate.getTxn().getType().toString())) {
			
			mandateHistory = getMandatesHistoryEntityByTxnIdandType(respMandate.getTxn().getId(),ConstantI.REQMANDATE);
			mandateHistory.setMandateUmn(respMandate.getMandate().getUmn());//ADD THIS NEW
			log.info("IN {} ",ConstantI.REQMANDATE);
		}
		else if(InitiatedByType.PAYEE.toString().equals(respMandate.getTxn().getInitiatedBy().toString())
				&&"REVOKE".equalsIgnoreCase(respMandate.getTxn().getType().toString())) {
			
			mandateHistory = getMandatesHistoryEntityByTxnIdandType(respMandate.getTxn().getId(),ConstantI.REQ_REVOKE);
			log.info("IN {} ",ConstantI.REQ_REVOKE);
		}
		return mandateHistory;
		
	}

	/*String umn=reqMandateConfirmation.getMandate().getUmn();
	String intiatedby=reqMandateConfirmation.getTxn().getInitiatedBy().toString();
	String txnType=reqMandateConfirmation.getTxn().getType().toString();
	String payeevpaAdd=reqMandateConfirmation.getTxnConfirmation().getRef().get(0).getAddr();*/
	private CustomerMandatesEntity setCustomerMandates(MandatesHistoryEntity mandateHistory, ReqMandateConfirmation reqMandateConfirmation) {
	if(ConstantI.PAYEE.equalsIgnoreCase(reqMandateConfirmation.getTxnConfirmation().getRef().get(0).getAddr())) {
			//needs to get payer VPA
			
		}
		CustomerMandatesEntity customerMandates = null;
		if (PayConstant.CREATE.toString().equalsIgnoreCase(reqMandateConfirmation.getTxn().getType().toString())) {
			customerMandates = new CustomerMandatesEntity();
			customerMandates.setStatus(2);//mandateHistory.getStatus()
long regvpaId = getRegIdByVpa(reqMandateConfirmation.getTxnConfirmation().getRef().get(0).getAddr());
	if (0 == regvpaId) {
					/* return; */ }
	
	if("PAYER".equalsIgnoreCase(reqMandateConfirmation.getTxn().getInitiatedBy().toString())
			||"PAYEE".equalsIgnoreCase(reqMandateConfirmation.getTxn().getInitiatedBy().toString())) {
		List<Ref> ref = reqMandateConfirmation.getTxnConfirmation().getRef();
		for(Ref refs: ref) {
			if(refs.getType().toString().equalsIgnoreCase("PAYEE")){
				customerMandates.setPayeeAccNo(refs.getAcNum());
				customerMandates.setPayeeAccIFSC(refs.getIFSC());
				customerMandates.setPayeeAcType(refs.getAccType().toString());
			}
		}
	}
			customerMandates.setRegId(regvpaId);
			customerMandates.setTxnType(PayConstant.CREATE.toString());
			customerMandates.setTxnId(mandateHistory.getTxnId());
			customerMandates.setTxncustRef(mandateHistory.getTxncustRef());
			// customerMandates.setMandateExpiry(mandateHistory.getMandateExpiry());
			customerMandates.setPayerVpa(mandateHistory.getPayerVpa());
			customerMandates.setMandateNote(mandateHistory.getTxnNote());

			customerMandates.setPayerName(mandateHistory.getPayerName());
			customerMandates.setPayerAccNo(mandateHistory.getPayerAccNo());
			customerMandates.setPayerAccIFSC(mandateHistory.getPayerAccIFSC());
			customerMandates.setPayerMobileNo(mandateHistory.getPayerMobileNo());
			customerMandates.setPayerMMID(mandateHistory.getPayerMMID());
			customerMandates.setPayerAcType(mandateHistory.getPayerAcType());
			customerMandates.setPayerBankName(mandateHistory.getPayerBankName());
			customerMandates.setPayeeName(mandateHistory.getPayeeName());
			customerMandates.setPayeeVpa(mandateHistory.getPayeeVpa());
			customerMandates.setPayeeMobileNo(mandateHistory.getPayeeMobileNo());
			customerMandates.setPayeeMMID(mandateHistory.getPayeeMMID());
			customerMandates.setPayeeBankName(mandateHistory.getPayeeBankName());
			customerMandates.setReqDate(mandateHistory.getReqDate());
			customerMandates.setRespDate(new Date());
			customerMandates.setPayerType(mandateHistory.getPayerType());
			customerMandates.setPayeeType(mandateHistory.getPayeeType());
			customerMandates.setPayeeUidNum(mandateHistory.getPayeeUidNum());
			customerMandates.setPayeeIin(mandateHistory.getPayeeIin());
			customerMandates.setPayerUidNum(mandateHistory.getPayerUidNum());
			customerMandates.setPayerIin(mandateHistory.getPayerIin());
			customerMandates.setMandateName(mandateHistory.getMandateName());
			customerMandates.setMandateTxnId(mandateHistory.getMandateTxnId());
			customerMandates.setMandateUmn(reqMandateConfirmation.getMandate().getUmn());
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
		} else if (PayConstant.REVOKE.toString().equalsIgnoreCase(reqMandateConfirmation.getTxn().getType().toString())) {
			//customerMandates=findByCustUMN(reqMandateConfirmation.getMandate().getUmn());
			
			String umn=reqMandateConfirmation.getMandate().getUmn();
			String vpa=reqMandateConfirmation.getTxnConfirmation().getRef().get(0).getAddr();
			customerMandates=findbyCustUMNandPayeevpa(umn,vpa);

			
			if(null==customerMandates) {
				log.info("Nomandate found on UMN {} :" ,reqMandateConfirmation.getMandate().getUmn());
			}
			else {
				//customerMandates = getCustomerMandatesEntityByUmn(umn);
				customerMandates.setStatus(3);
				customerMandates.setMandateType("REVOKE");
				customerMandates.setStatus(MandateStatus.MANDATE_REVOKED.getStatus());
			}
		} else if (PayConstant.UPDATE.toString().equalsIgnoreCase(reqMandateConfirmation.getTxn().getType().toString())) {
		//	customerMandates=findByCustUMN(umn);
			String umn=reqMandateConfirmation.getMandate().getUmn();
			String vpa=reqMandateConfirmation.getTxnConfirmation().getRef().get(0).getAddr();
			customerMandates=findbyCustUMNandPayeevpa(umn,vpa);
			if(null==customerMandates) {
				log.info("Nomandate found on UMN {} :" ,umn);
			}
			else {
			//customerMandates = getCustomerMandatesEntityByUmn(umn);
			customerMandates.setMandateAmountrule(mandateHistory.getMandateAmountrule());
			customerMandates.setStatus(2);
			customerMandates.setMandateAmountvalue(mandateHistory.getMandateAmountvalue());
			customerMandates.setMandateName(mandateHistory.getMandateName());
			customerMandates.setMandateValidityStart(mandateHistory.getMandateValidityStart());
			customerMandates.setMandateValidityEnd(mandateHistory.getMandateValidityEnd());
			}
			}
		return customerMandates;
	}
	
	private CustomerMandatesEntity setCustomerMandates(MandatesHistoryEntity mandateHistory, RespMandate respMandate) {
		
		
			CustomerMandatesEntity customerMandates = null;
			if (PayConstant.CREATE.toString().equalsIgnoreCase(respMandate.getTxn().getType().toString())) {
				
				customerMandates = new CustomerMandatesEntity();
				
				if("PAYER".equalsIgnoreCase(respMandate.getTxn().getInitiatedBy().toString())
						||"PAYEE".equalsIgnoreCase(respMandate.getTxn().getInitiatedBy().toString())) {
					List<Ref> ref = respMandate.getResp().getRef();
					for(Ref refs: ref) {
						if(refs.getType().toString().equalsIgnoreCase("PAYEE")){
							customerMandates.setPayeeAccNo(refs.getAcNum());
							customerMandates.setPayeeAccIFSC(refs.getIFSC());
							customerMandates.setPayeeAcType(refs.getAccType().toString());
						}
					}
				}
			
			customerMandates.setStatus(2);
			customerMandates.setRegId(mandateHistory.getRegId());			
			customerMandates.setTxnType(PayConstant.CREATE.toString());
			customerMandates.setTxnId(mandateHistory.getTxnId());
			customerMandates.setTxncustRef(mandateHistory.getTxncustRef());
			//customerMandates.setMandateExpiry(mandateHistory.getMandateExpiry());
			customerMandates.setPayerVpa(mandateHistory.getPayerVpa());
			customerMandates.setMandateNote(mandateHistory.getTxnNote());
			customerMandates.setPayerName(mandateHistory.getPayerName());
			customerMandates.setPayerAccNo(mandateHistory.getPayerAccNo());
			customerMandates.setPayerAccIFSC(mandateHistory.getPayerAccIFSC());
			customerMandates.setPayerMobileNo(mandateHistory.getPayerMobileNo());
			customerMandates.setPayerMMID(mandateHistory.getPayerMMID());
			customerMandates.setPayerAcType(mandateHistory.getPayerAcType());
			customerMandates.setPayerBankName(mandateHistory.getPayerBankName());
			customerMandates.setPayeeName(mandateHistory.getPayeeName());
			customerMandates.setPayeeVpa(mandateHistory.getPayeeVpa());
			customerMandates.setPayeeMobileNo(mandateHistory.getPayeeMobileNo());
			customerMandates.setPayeeMMID(mandateHistory.getPayeeMMID());
			customerMandates.setPayeeBankName(mandateHistory.getPayeeBankName());
			customerMandates.setReqDate(mandateHistory.getReqDate());
			customerMandates.setRespDate(new Date());
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
		} else if (PayConstant.REVOKE.toString().equalsIgnoreCase(respMandate.getTxn().getType().toString())) {
			//customerMandates=findByCustUMN(umn);
			
			String umn=respMandate.getMandate().getUmn();
			String vpa=respMandate.getResp().getRef().get(0).getAddr();
			customerMandates=findbyCustUMNandPayervpa(umn,vpa);
			
			if(null==customerMandates) {
				log.info("Nomandate found on UMN {} :" ,umn);
			}
			else {
				//customerMandates = getCustomerMandatesEntityByUmn(umn);
				customerMandates.setStatus(3);
				customerMandates.setMandateType("REVOKE");
				customerMandates.setStatus(MandateStatus.MANDATE_REVOKED.getStatus());
			}
			
		} else if (PayConstant.UPDATE.toString().equalsIgnoreCase(respMandate.getTxn().getType().toString())) {
			//customerMandates=findByCustUMN(umn);
String umn=respMandate.getMandate().getUmn();
			String vpa=respMandate.getResp().getRef().get(0).getAddr();
			customerMandates=findbyCustUMNandPayervpa(umn,vpa);
			if(null==customerMandates) {
				log.info("Nomandate found on UMN {} :" ,umn);
			}
			else {
			//customerMandates = getCustomerMandatesEntityByUmn(umn);
			customerMandates.setMandateAmountrule(mandateHistory.getMandateAmountrule());
			customerMandates.setStatus(2);//mandateHistory.getStatus()
			customerMandates.setMandateAmountvalue(mandateHistory.getMandateAmountvalue());
			customerMandates.setMandateName(mandateHistory.getMandateName());
			customerMandates.setMandateValidityStart(mandateHistory.getMandateValidityStart());
			customerMandates.setMandateValidityEnd(mandateHistory.getMandateValidityEnd());
		}
		}
		return customerMandates;
	}
	
	private CustomerMandatesEntity findbyCustUMNandPayervpa(String umn, String vpa) {
		try {
			List<CustomerMandatesEntity> list = customerMandatesRepo.findByMandateUmnandpayervpa(umn,ConstantI.PAYER);
			if (list != null && list.size() >= 1) {
				if (list.size() == 1) {
					return list.get(0);
				} else {
					//log.warn("more than one umn entry found in MandatesEntity ie umn={},size={}", umn, list.size());
				}
			}
		} catch (Exception e) {
			log.error("error while  findByUmn {}", e);
			e.printStackTrace();
			ErrorLog.sendError(e, MandatesDaoImpl.class);
		}
		return null;
	}
	
	
	private CustomerMandatesEntity findbyCustUMNandPayeevpa(String umn, String vpa) {
		try {
			List<CustomerMandatesEntity> list = customerMandatesRepo.findByMandateUmnandpayeevpa(umn,ConstantI.PAYEE);
			if (list != null && list.size() >= 1) {
				if (list.size() == 1) {
					return list.get(0);
				} else {
					log.warn("more than one umn entry found in MandatesEntity ie umn={},size={}", umn, list.size());
				}
			}
		} catch (Exception e) {
			log.error("error while  findByUmn {}", e);
			ErrorLog.sendError(e, MandatesDaoImpl.class);
		}
		return null;
	}
	
/*	private List<CustomerMandatesEntity> findbyCustUMN(String mandateUmn) {
		try {
			List<CustomerMandatesEntity> list = customerMandatesRepo.findByMandateUmn(umn);
			
			return list;
		} catch (Exception e) {
			log.error("error while  findByUmn {}", e);
			ErrorLog.sendError(e, MandatesDaoImpl.class);
		}
		return null;
	}

	*/
	

	private CustomerMandatesEntity findByCustUMN(String mandateUmn) {

		try {
			List<CustomerMandatesEntity> list = customerMandatesRepo.findByMandateUmn(mandateUmn);
			if (list != null && list.size() >= 1) {
				if (list.size() == 1) {
					return list.get(0);
				} else {
					log.warn("more than one umn entry found in MandatesEntity ie umn={},size={}", mandateUmn, list.size());
				}
			}
		} catch (Exception e) {
			log.error("error while  findByUmn {}", e);
			ErrorLog.sendError(e, MandatesDaoImpl.class);
		}
		return null;
	
	}

	@Override
	public void insertReqRespMandate(ReqMandate reqMandate, RespMandate respMandate, Ack ack) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRespMandate(RespMandate respMandate) {
		MandatesEntity mandatesObject = findByUmn(respMandate.getMandate().getUmn());
		mandatesObject.setStatus(MandateStatus.MANDATE_FAILED.getStatus());
		 mandatesRepo.save(mandatesObject);	
	}

	@Override
	public void insertSuccessCustomerMandates(ReqMandateConfirmation reqMandateConfirmation) {
		log.info("INSIDE CUSTOMER MANDATE SAVE Method");
		MandatesHistoryEntity mandateHistory = null;
		
		mandateHistory=getmandateHitory(mandateHistory,reqMandateConfirmation);
		log.info("AFTER MMERCHANT HISTORY");
		CustomerMandatesEntity customerMandates = setCustomerMandates(mandateHistory,reqMandateConfirmation);
		
		
		log.info("Bustomet mandates #### {} , data {}",customerMandates.toString(),customerMandates.getMandateName());
		customerMandates.setCustis(reqMandateConfirmation.getTxn().getInitiatedBy().toString().equalsIgnoreCase(ConstantI.PAYEE)?ConstantI.PAYER:ConstantI.PAYEE);//"PAYER"
		log.info("MAndate Sign value #### {}",reqMandateConfirmation.getSignature());
		if(reqMandateConfirmation.getTxn().getInitiatedBy().toString().equalsIgnoreCase(ConstantI.PAYEE)
				&&ConstantI.M_CREATE.equalsIgnoreCase(reqMandateConfirmation.getTxn().getType().toString())) {
			customerMandates.setMandateSignId(reqMandateConfirmation.getSignature().getId().toString());
			customerMandates.setMandateSignValue(reqMandateConfirmation.getSignature().getValue());
		}
		//log.info("MAndate Sign value #### {}",reqMandateConfirmation.getSignature());
		/*if (customerMandates != null&& !StringUtils.isEmpty(reqMandateConfirmation.getSignature().getValue())) {//need to put condition here
			customerMandates.setMandateSignId(reqMandateConfirmation.getSignature().getId().toString());
			customerMandates.setMandateSignValue(reqMandateConfirmation.getSignature().getValue());
		}*/
		/*if("PAYER".equalsIgnoreCase(reqMandateConfirmation.getTxn())) {
			
		}*/
		customerMandatesRepo.save(customerMandates);
	}

	
	private MandatesHistoryEntity getmandateHitory(MandatesHistoryEntity mandateHistory, ReqMandateConfirmation reqMandateConfirmation) {
		 if(InitiatedByType.PAYEE.toString().equals(reqMandateConfirmation.getTxn().getInitiatedBy().toString())
					&&"CREATE".equalsIgnoreCase(reqMandateConfirmation.getTxn().getType().toString())) {
				
			 mandateHistory = getMandatesHistoryEntityByTxnIdandType(reqMandateConfirmation.getTxn().getId(),ConstantI.REQRECVMANDATE);
				log.info("IN {} ",ConstantI.REQRECVMANDATE);
			
			}
			else if(InitiatedByType.PAYEE.toString().equals(reqMandateConfirmation.getTxn().getInitiatedBy().toString())
					&&"REVOKE".equalsIgnoreCase(reqMandateConfirmation.getTxn().getType().toString())) {
				
				mandateHistory = getMandatesHistoryEntityByTxnIdandType(reqMandateConfirmation.getTxn().getId(),ConstantI.REVOKE_REQRECV);
				log.info("IN {} ",ConstantI.REVOKE_REQRECV);
			}
			
			else if(InitiatedByType.PAYER.toString().equals(reqMandateConfirmation.getTxn().getInitiatedBy().toString())
					&&"CREATE".equalsIgnoreCase(reqMandateConfirmation.getTxn().getType().toString())) {
				
				mandateHistory = getMandatesHistoryEntityByTxnIdandType(reqMandateConfirmation.getTxn().getId(),ConstantI.CREATE_RECEIVE);
				log.info("IN {} ",ConstantI.CREATE_RECEIVE);

			}
			
			else if(InitiatedByType.PAYER.toString().equals(reqMandateConfirmation.getTxn().getInitiatedBy().toString())
					&&"UPDATE".equalsIgnoreCase(reqMandateConfirmation.getTxn().getType().toString())) {
				
				mandateHistory = getMandatesHistoryEntityByTxnIdandType(reqMandateConfirmation.getTxn().getId(),ConstantI.UPDATE_RECEIVE);
				log.info("IN {} ",ConstantI.UPDATE_RECEIVE);

			}
			 
			else if(InitiatedByType.PAYER.toString().equals(reqMandateConfirmation.getTxn().getInitiatedBy().toString())
					&&"REVOKE".equalsIgnoreCase(reqMandateConfirmation.getTxn().getType().toString())) {
				
				mandateHistory = getMandatesHistoryEntityByTxnIdandType(reqMandateConfirmation.getTxn().getId(),ConstantI.REVOKE_RECEIVE);

				log.info("IN {} ",ConstantI.REVOKE_RECEIVE);

			}
		return mandateHistory;
		
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
	
	private MandatesHistoryEntity getMandatesHistoryEntityByTxnIdandType(String txnId, String type) {
		try {
			List<MandatesHistoryEntity> list = mandatesHistoryRepo.findByMandateTxnIdAndTxnType(txnId,type);
			//log.info("not");
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
	public List<MandatesEntity> findByAccountAndStatus(String accntNo, String status,String status2) {
		try {
			List<MandatesEntity> mandateList = mandatesRepo.findByacAddrTypeDetail3AndTxnTypeOrTxnType(accntNo, status,status2);
					return mandateList;
		} catch (Exception e) {
			log.error("error while  findByAccntNoAndStatus {}", e);
			ErrorLog.sendError(e, MandatesDaoImpl.class);
		}
		return null;
	}

	
}
