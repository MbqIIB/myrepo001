package com.npst.upiserver.dao.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.CustomerAccountDao;
import com.npst.upiserver.dao.RegistrationDao;
import com.npst.upiserver.dao.ReqRespAuthMandateDao;
import com.npst.upiserver.dao.ReqRespAuthMandatePayeesDao;
import com.npst.upiserver.entity.ReqRespAuthMandateEntity;
import com.npst.upiserver.npcischema.AccountType.Detail;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.CredsType;
import com.npst.upiserver.npcischema.DeviceTagNameType;
import com.npst.upiserver.npcischema.DeviceType.Tag;
import com.npst.upiserver.npcischema.ExpireRuleConstant;
import com.npst.upiserver.npcischema.InitiatedByType;
import com.npst.upiserver.npcischema.MandateType;
import com.npst.upiserver.npcischema.Ref;
import com.npst.upiserver.npcischema.ReqAuthMandate;
import com.npst.upiserver.npcischema.ReqMandateConfirmation;
import com.npst.upiserver.npcischema.RespAuthMandate;
import com.npst.upiserver.npcischema.RespMandateConfirmation;
import com.npst.upiserver.npcischema.RulesType.Rule;
import com.npst.upiserver.repo.ReqRespAuthMandateRepo;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.JsonMan;

@Component
public class ReqRespAuthMandateDaoImpl implements ReqRespAuthMandateDao {
	private static final Logger log = LoggerFactory.getLogger(ReqRespAuthMandateDaoImpl.class);

	@Autowired
	private ReqRespAuthMandateRepo reqRespAuthMandateRepo;
	@Autowired
	private CustomerAccountDao customerAccountDao;
	@Autowired
	private RegistrationDao registrationDao;
	@Autowired
	private ReqRespAuthMandatePayeesDao reqRespAuthMandatePayeesDao;

	@Override
	public void updateMandateConfirmation(ReqMandateConfirmation reqMandateConfirmation,
			RespMandateConfirmation respMandateConfirmation, Ack ack) {
		// TODO test
		try {
			ReqRespAuthMandateEntity reqrespauthmandates = getByTxnId(reqMandateConfirmation.getTxn().getId());
			if (reqrespauthmandates == null) {
				reqrespauthmandates = new ReqRespAuthMandateEntity();
			}
			reqrespauthmandates.setRespErrCode(reqMandateConfirmation.getTxnConfirmation().getOrgErrCode());
			reqrespauthmandates.setRespResult(reqMandateConfirmation.getTxnConfirmation().getOrgStatus());
			reqrespauthmandates.setRespInsert(new Date());
			reqrespauthmandates.setTxnConfOrgErrCode(reqMandateConfirmation.getTxnConfirmation().getOrgErrCode());
			reqrespauthmandates.setTxnConfOrgStatus(reqMandateConfirmation.getTxnConfirmation().getOrgStatus());
			List<Ref> refList = reqMandateConfirmation.getTxnConfirmation().getRef();
			String refSettAmount = ConstantI.CONST_BLANK, refReversalRespCode = ConstantI.CONST_BLANK,
					refOrgAmount = ConstantI.CONST_BLANK, refApprovalNum = ConstantI.CONST_BLANK,
					refRespCode = ConstantI.CONST_BLANK, refAddr = ConstantI.CONST_BLANK,
					refRegName = ConstantI.CONST_BLANK, refSeqNum = ConstantI.CONST_BLANK,
					refType = ConstantI.CONST_BLANK, refSettCurrency = ConstantI.CONST_BLANK;
			for (Ref ref : refList) {
				refSettAmount += ref.getSettAmount() != null ? ref.getSettAmount()
						: ConstantI.CONST_BLANK + ConstantI.CONST_SPCL_PIPE;
				refApprovalNum += ref.getApprovalNum() != null ? ref.getApprovalNum()
						: ConstantI.CONST_BLANK + ConstantI.CONST_SPCL_PIPE;
				refRespCode += ref.getRespCode() != null ? ref.getRespCode()
						: ConstantI.CONST_BLANK + ConstantI.CONST_SPCL_PIPE;
				refReversalRespCode += ref.getReversalRespCode() != null ? ref.getReversalRespCode()
						: ConstantI.CONST_BLANK + ConstantI.CONST_SPCL_PIPE;
				refOrgAmount += ref.getOrgAmount() != null ? ref.getOrgAmount()
						: ConstantI.CONST_BLANK + ConstantI.CONST_SPCL_PIPE;
				refRegName += ref.getRegName() != null ? ref.getRegName()
						: ConstantI.CONST_BLANK + ConstantI.CONST_SPCL_PIPE;
				refAddr += ref.getAddr() != null ? ref.getAddr() : ConstantI.CONST_BLANK + ConstantI.CONST_SPCL_PIPE;
				refSeqNum += ref.getSeqNum() != null ? ref.getSeqNum()
						: ConstantI.CONST_BLANK + ConstantI.CONST_SPCL_PIPE;
				refType += ref.getType() != null ? ref.getType() : ConstantI.CONST_BLANK + ConstantI.CONST_SPCL_PIPE;
				refSettCurrency += ref.getSettCurrency() != null ? ref.getSettCurrency()
						: ConstantI.CONST_BLANK + ConstantI.CONST_SPCL_PIPE;
			}

			reqrespauthmandates.setRefApprovalNum(refApprovalNum);
			reqrespauthmandates.setRefOrgAmount(refOrgAmount);
			reqrespauthmandates.setRefRespCode(refRespCode);
			reqrespauthmandates.setRefReversalRespCode(refReversalRespCode);
			reqrespauthmandates.setRefSettAmount(refSettAmount);
			reqrespauthmandates.setRefAddr(refAddr);
			reqrespauthmandates.setRefRegName(refRegName);
			reqrespauthmandates.setRefSeqNum(refSeqNum);
			reqrespauthmandates.setRefType(refType);
			reqrespauthmandates.setRefSettCurrency(refSettCurrency);
			reqrespauthmandates.setTxnConfAck(JsonMan.getJSONStr(ack));
			reqRespAuthMandateRepo.save(reqrespauthmandates);
		} catch (Exception e) {
			log.error("error {}", e);
			ErrorLog.sendError(e, ReqRespAuthMandateDaoImpl.class);
		}

	}

	@Override
	public ReqRespAuthMandateEntity getByTxnId(String txnId) {
		try {
			List<ReqRespAuthMandateEntity> list = reqRespAuthMandateRepo.findByTxnId(txnId);
			if (list.size() == 1) {
				return list.get(0);
			} else if (list.size() == 0) {
				log.warn("ReqRespAuthMandateEntity not found for txnId={}", txnId);
				log.debug("ReqRespAuthMandateEntity not found for txnId={}", txnId);
			} else {
				log.warn("More than one ReqRespAuthMandateEntity found for txnId={}", txnId);
				log.debug("More than one ReqRespAuthMandateEntity found for txnId={}", txnId);
			}
		} catch (Exception e) {
			log.error("error {}", e);
			ErrorLog.sendError(e, ReqRespAuthMandateDaoImpl.class);
		}
		return null;
	}

	@Override
	public void insertReq(ReqAuthMandate reqAuthMandate) {
		// TODO Auto-generated method stub
		try {

			long regId = getRegIdByVpa(reqAuthMandate.getPayer().getAddr());
			ReqRespAuthMandateEntity reqrespauthmandate = new ReqRespAuthMandateEntity();
			reqrespauthmandate.setReqInsert(new Date());
			reqrespauthmandate.setRegid(regId);
			reqrespauthmandate.setReqHeadMsgId(reqAuthMandate.getHead().getMsgId());
			reqrespauthmandate.setReqHeadOrgId(reqAuthMandate.getHead().getOrgId());
			reqrespauthmandate.setReqHeadTs(reqAuthMandate.getHead().getTs());
			reqrespauthmandate.setTxnCustRef(reqAuthMandate.getTxn().getCustRef());
			reqrespauthmandate.setTxnId(reqAuthMandate.getTxn().getId());
			reqrespauthmandate.setTxnIdPrf(reqAuthMandate.getTxn().getId().substring(0, 3));
			reqrespauthmandate.setTxnNote(reqAuthMandate.getTxn().getNote());
			reqrespauthmandate.setTxnRefid(reqAuthMandate.getTxn().getRefId());
			reqrespauthmandate.setTxnRefurl(reqAuthMandate.getTxn().getRefUrl());
			reqrespauthmandate.setTxnTs(reqAuthMandate.getTxn().getTs());
			reqrespauthmandate.setTxnType(reqAuthMandate.getTxn().getType().value());
			reqrespauthmandate.setTxnInitiatedBy(reqAuthMandate.getTxn().getInitiatedBy().toString());
			reqrespauthmandate.setPayerAddr(reqAuthMandate.getPayer().getAddr());
			reqrespauthmandate.setPayerCode(reqAuthMandate.getPayer().getCode());
			reqrespauthmandate.setPayerSeqNum(reqAuthMandate.getPayer().getSeqNum());
			// reqrespauthdetails.setPayerType(reqAuthDetails.getPayer().getType().value());
			reqrespauthmandate.setPayerHandal(
					reqAuthMandate.getPayer().getAddr().substring(reqAuthMandate.getPayer().getAddr().indexOf('@')));
			if (null != reqAuthMandate.getTxn().getRules()) {
				
				List<Rule> ruleList = reqAuthMandate.getTxn().getRules().getRule();
				for (int i = 0; i < ruleList.size(); i++) {
					if (ExpireRuleConstant.EXPIREAFTER.value().equalsIgnoreCase((ruleList.get(i).getName().toString()))) {
						reqrespauthmandate.setRuleExpireAfter(ruleList.get(i).getValue());
					}
					if (ExpireRuleConstant.MINAMOUNT.value().equalsIgnoreCase(ruleList.get(i).getName().toString())) {
						reqrespauthmandate.setRuleMinAmount(ruleList.get(i).getValue());
					}
				}
			}
			

			// if
			// ("PAY".equalsIgnoreCase(reqAuthDetails.getTxn().getType().value()))
			// {
			if (null != reqAuthMandate.getPayer().getType()) {
				reqrespauthmandate.setPayerType(reqAuthMandate.getPayer().getType().value());
			}
			if (null != reqAuthMandate.getPayer().getDevice()) {
				List<Tag> deviceTag = reqAuthMandate.getPayer().getDevice().getTag();
				for (int i = 0; i < deviceTag.size(); i++) {
					if (DeviceTagNameType.APP.value().equals(deviceTag.get(i).getName().value())) {
						reqrespauthmandate.setDeviceApp(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.CAPABILITY.value().equals(deviceTag.get(i).getName().value())) {
						reqrespauthmandate.setDeviceCapability(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.GEOCODE.value().equals(deviceTag.get(i).getName().value())) {
						reqrespauthmandate.setDeviceGeocode(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.ID.value().equals(deviceTag.get(i).getName().value())) {
						reqrespauthmandate.setDeviceId(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.IP.value().equals(deviceTag.get(i).getName().value())) {
						reqrespauthmandate.setDeviceIp(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.LOCATION.value().equals(deviceTag.get(i).getName().value())) {
						reqrespauthmandate.setDeviceLocation(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.MOBILE.value().equals(deviceTag.get(i).getName().value())) {
						reqrespauthmandate.setDeviceMobile(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.OS.value().equals(deviceTag.get(i).getName().value())) {
						reqrespauthmandate.setDeviceOs(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.TYPE.value().equals(deviceTag.get(i).getName().value())) {
						reqrespauthmandate.setDeviceType(deviceTag.get(i).getValue());
					}
				}
			}
			if (null != reqAuthMandate.getPayer().getInfo()) {
				reqrespauthmandate.setInfoId(reqAuthMandate.getPayer().getInfo().getIdentity().getId());
				reqrespauthmandate.setInfoIdRatingvaddr(
						reqAuthMandate.getPayer().getInfo().getRating().getVerifiedAddress().value());
				reqrespauthmandate.setInfoIdType(reqAuthMandate.getPayer().getInfo().getIdentity().getType().value());
				reqrespauthmandate
						.setInfoIdVerifiedName(reqAuthMandate.getPayer().getInfo().getIdentity().getVerifiedName());
			}
			if (null != reqAuthMandate.getPayer().getCreds()) {
				List<CredsType.Cred> creadsList = reqAuthMandate.getPayer().getCreds().getCred();
				if (0 < creadsList.size()) {
					String creadsSubType = "", creadsType = "";
					for (int i = 0; i < creadsList.size(); i++) {
						creadsSubType += creadsList.get(i).getSubType().value() + "|";
						creadsType += creadsList.get(i).getType().value() + "|";
					}

					reqrespauthmandate.setCredSubType(creadsSubType);
					reqrespauthmandate.setCredType(creadsType);
				}
			}

			// reqrespauthmandate.setAmountCrr(reqAuthMandate.getPayer().getAmount().getCurr());
			// reqrespauthmandate.setAmountVal(reqAuthMandate.getPayer().getAmount().getValue());
			List<Detail> acDetails = reqAuthMandate.getPayer().getAc() != null
					? reqAuthMandate.getPayer().getAc().getDetail()
					: null;
			if (acDetails != null && !acDetails.isEmpty()) {
				String acAddrTypeDetail1 = "", acaddrTypeDetail2 = "", acaddrTypeDetail3 = "";
				for (int i = 0; i < acDetails.size(); i++) {
					String temp = acDetails.get(i).getName().value() + "=" + acDetails.get(i).getValue();
					if (0 == i) {
						acAddrTypeDetail1 = temp;
					} else if (1 == i) {
						acaddrTypeDetail2 = temp;
					} else {
						acaddrTypeDetail3 = temp;
					}
				}
				reqrespauthmandate.setAcAddrTypeDetail1(acAddrTypeDetail1);
				reqrespauthmandate.setAcAddrTypeDetail2(acaddrTypeDetail2);
				reqrespauthmandate.setAcAddrTypeDetail3(acaddrTypeDetail3);
			}

			/**
			 * Setting mandate related info here...//FIXME
			 */
			final MandateType mandateType = reqAuthMandate.getMandate();
			reqrespauthmandate.setMandateName(mandateType.getName());
			reqrespauthmandate.setMandateAmountrule(mandateType.getAmount().getRule().value());
			reqrespauthmandate.setMandateAmountvalue(mandateType.getAmount().getValue());
			reqrespauthmandate.setMandateBlockFund(mandateType.getBlockFund().value());
			reqrespauthmandate.setMandateRecurrencepattern(mandateType.getRecurrence().getPattern().value());
			if (mandateType.getRecurrence().getRule() != null
					&& mandateType.getRecurrence().getRule().getType() != null)
				reqrespauthmandate
						.setMandateRecurrenceRuletype(mandateType.getRecurrence().getRule().getType().value());
			if (mandateType.getRecurrence().getRule() != null)
				reqrespauthmandate.setMandateRecurrenceRulevalue(mandateType.getRecurrence().getRule().getValue());
			reqrespauthmandate.setMandateRevokeable(mandateType.getRevokeable().value());
			if (mandateType.getShareToPayee() != null)
				reqrespauthmandate.setMandateShareToPayee(mandateType.getShareToPayee().value());
			reqrespauthmandate.setMandateTs(mandateType.getTs());
			reqrespauthmandate.setMandateTxnId(mandateType.getTxnId());
			reqrespauthmandate.setMandateType(mandateType.getType());
			reqrespauthmandate.setMandateUmn(mandateType.getUmn());
			reqrespauthmandate.setMandateValidityEnd(mandateType.getValidity().getEnd());
			reqrespauthmandate.setMandateValidityStart(mandateType.getValidity().getStart());
			reqRespAuthMandateRepo.save(reqrespauthmandate);
			reqRespAuthMandatePayeesDao.insertPayees(reqAuthMandate.getPayees(), reqAuthMandate.getTxn().getId(),
					reqAuthMandate.getHead().getMsgId(), reqrespauthmandate.getIdReqRespAuthMandate());
		} catch (Exception e) {
			log.error("Error: {}", e);
			ErrorLog.sendError(e, ReqRespAuthMandateDaoImpl.class);
		}

	}

	@Override
	public void updateResp(RespAuthMandate respAuthMandate, Ack ack) {
		try {
			ReqRespAuthMandateEntity reqrespauthmandate = getByTxnId(respAuthMandate.getTxn().getId());
			if (null == reqrespauthmandate) {
				reqrespauthmandate = new ReqRespAuthMandateEntity();
			}

			if ("PAYEE".equalsIgnoreCase(respAuthMandate.getTxn().getInitiatedBy().toString())
					&&"CREATE".equalsIgnoreCase(respAuthMandate.getTxn().getType().toString())) {
				if (respAuthMandate.getPayer() != null) {
					reqrespauthmandate.setPayerName(respAuthMandate.getPayer().getName());
					if (respAuthMandate.getPayer().getType() != null) {
						reqrespauthmandate.setPayerType(respAuthMandate.getPayer().getType().value());
					}

				}
			}

			reqrespauthmandate.setRespInsert(new Date());
			reqrespauthmandate.setRespHeadMsgId(respAuthMandate.getHead().getMsgId());
			reqrespauthmandate.setRespHeadOrgId(respAuthMandate.getHead().getOrgId());
			reqrespauthmandate.setRespHeadTs(respAuthMandate.getHead().getTs());
			reqrespauthmandate.setRespResult(respAuthMandate.getResp().getResult());
			List<Tag> deviceTag = respAuthMandate.getPayer() != null && respAuthMandate.getPayer().getDevice() != null
					? respAuthMandate.getPayer().getDevice().getTag()
					: null;
			if (deviceTag != null && !deviceTag.isEmpty()) {
				for (int i = 0; i < deviceTag.size(); i++) {
					if (DeviceTagNameType.APP.value().equals(deviceTag.get(i).getName().value())) {
						reqrespauthmandate.setDeviceApp(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.CAPABILITY.value().equals(deviceTag.get(i).getName().value())) {
						reqrespauthmandate.setDeviceCapability(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.GEOCODE.value().equals(deviceTag.get(i).getName().value())) {
						reqrespauthmandate.setDeviceGeocode(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.ID.value().equals(deviceTag.get(i).getName().value())) {
						reqrespauthmandate.setDeviceId(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.IP.value().equals(deviceTag.get(i).getName().value())) {
						reqrespauthmandate.setDeviceIp(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.LOCATION.value().equals(deviceTag.get(i).getName().value())) {
						reqrespauthmandate.setDeviceLocation(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.MOBILE.value().equals(deviceTag.get(i).getName().value())) {
						reqrespauthmandate.setDeviceMobile(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.OS.value().equals(deviceTag.get(i).getName().value())) {
						reqrespauthmandate.setDeviceOs(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.TYPE.value().equals(deviceTag.get(i).getName().value())) {
						reqrespauthmandate.setDeviceType(deviceTag.get(i).getValue());
					}
				}
			}
			if (null != respAuthMandate.getPayer().getInfo()) {
				reqrespauthmandate.setInfoId(respAuthMandate.getPayer().getInfo().getIdentity().getId());
				reqrespauthmandate.setInfoIdRatingvaddr(
						respAuthMandate.getPayer().getInfo().getRating().getVerifiedAddress().value());
				reqrespauthmandate.setInfoIdType(respAuthMandate.getPayer().getInfo().getIdentity().getType().value());
				reqrespauthmandate
						.setInfoIdVerifiedName(respAuthMandate.getPayer().getInfo().getIdentity().getVerifiedName());
			}
			
			if (null != respAuthMandate.getPayer().getAc()) {
				List<Detail> acDetails = respAuthMandate.getPayer().getAc().getDetail();
				if (!acDetails.isEmpty()) {
					String acAddrTypeDetail1 = "", acaddrTypeDetail2 = "", acaddrTypeDetail3 = "";
					for (int i = 0; i < acDetails.size(); i++) {
						String temp = acDetails.get(i).getName().value() + "=" + acDetails.get(i).getValue();
						if (0 == i) {
							acAddrTypeDetail1 = temp;
						} else if (1 == i) {
							acaddrTypeDetail2 = temp;
						} else {
							acaddrTypeDetail3 = temp;
						}
					}
					reqrespauthmandate.setAcAddrTypeDetail1(acAddrTypeDetail1);
					reqrespauthmandate.setAcAddrTypeDetail2(acaddrTypeDetail2);
					reqrespauthmandate.setAcAddrTypeDetail3(acaddrTypeDetail3);
				}
			}
			
			if (null != respAuthMandate.getPayer().getCreds()) {
				List<CredsType.Cred> creadsList = respAuthMandate.getPayer().getCreds().getCred();
				if (!creadsList.isEmpty()) {
					String creadsSubType = "", creadsType = "";
					for (int i = 0; i < creadsList.size(); i++) {
						creadsSubType += creadsList.get(i).getSubType().value() + "|";
						creadsType += creadsList.get(i).getType().value() + "|";
					}
					reqrespauthmandate.setCredSubType(creadsSubType);
					reqrespauthmandate.setCredType(creadsType);
				}
			}
			// if (null != ack.getErr() || null != ack.getErrorMessages()) {
			reqrespauthmandate.setRespAuthAck(JsonMan.getJSONStr(ack));
			// }

			/**
			 * Setting mandate related info here...
			 */
			final MandateType mandateType = respAuthMandate.getMandate();
			reqrespauthmandate.setMandateName(mandateType.getName());
			reqrespauthmandate.setMandateAmountrule(mandateType.getAmount().getRule().value());
			reqrespauthmandate.setMandateAmountvalue(mandateType.getAmount().getValue());
			if (mandateType.getBlockFund() != null)
				reqrespauthmandate.setMandateBlockFund(mandateType.getBlockFund().value());

			if (mandateType.getRecurrence() != null) {
				reqrespauthmandate.setMandateRecurrencepattern(mandateType.getRecurrence().getPattern().value());
				if (mandateType.getRecurrence().getRule() != null) {
					if (mandateType.getRecurrence().getRule().getType() != null)
						reqrespauthmandate
								.setMandateRecurrenceRuletype(mandateType.getRecurrence().getRule().getType().value());
					reqrespauthmandate.setMandateRecurrenceRulevalue(mandateType.getRecurrence().getRule().getValue());
				}

			}
			if (mandateType.getRevokeable() != null)
				reqrespauthmandate.setMandateRevokeable(mandateType.getRevokeable().value());
			if (mandateType.getShareToPayee() != null)
				reqrespauthmandate.setMandateShareToPayee(mandateType.getShareToPayee().value());
			reqrespauthmandate.setMandateTs(mandateType.getTs());
			reqrespauthmandate.setMandateTxnId(mandateType.getTxnId());
			reqrespauthmandate.setMandateType(mandateType.getType());
			reqrespauthmandate.setMandateUmn(mandateType.getUmn());
			if (mandateType.getValidity() != null) {
				reqrespauthmandate.setMandateValidityEnd(mandateType.getValidity().getEnd());
				reqrespauthmandate.setMandateValidityStart(mandateType.getValidity().getStart());
			}
			log.info("Before going to save respauth");
			reqRespAuthMandateRepo.save(reqrespauthmandate);
			log.info("After going to save respauth");
		} catch (Exception e) {
			log.info("Error in save data");
			e.printStackTrace();
			log.error("Error: {}", e);
			ErrorLog.sendError(e, ReqRespAuthMandateDaoImpl.class);
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
}
