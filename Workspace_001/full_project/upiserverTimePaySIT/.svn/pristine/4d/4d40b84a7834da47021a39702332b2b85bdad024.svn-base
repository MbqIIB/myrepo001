package com.npst.upiserver.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.constant.InitiationMode;
import com.npst.upiserver.dao.CustomerAccountDao;
import com.npst.upiserver.dao.CustomerMandatesDao;
import com.npst.upiserver.dao.PayeesDao;
import com.npst.upiserver.dao.ReqRespAuthDetailsDao;
import com.npst.upiserver.dao.ReqRespAuthDetailsPayeesDao;
import com.npst.upiserver.entity.CustomerMandatesEntity;
import com.npst.upiserver.entity.ReqRespAuthDetails;
import com.npst.upiserver.npcischema.AccountDetailType;
import com.npst.upiserver.npcischema.AccountType.Detail;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.CredsType;
import com.npst.upiserver.npcischema.DeviceTagNameType;
import com.npst.upiserver.npcischema.DeviceType.Tag;
import com.npst.upiserver.npcischema.ExpireRuleConstant;
import com.npst.upiserver.npcischema.Ref;
import com.npst.upiserver.npcischema.RulesType.Rule;
import com.npst.upiserver.npcischema.ReqAuthDetails;
import com.npst.upiserver.npcischema.ReqTxnConfirmation;
import com.npst.upiserver.npcischema.RespAuthDetails;
import com.npst.upiserver.npcischema.RespTxnConfirmation;
import com.npst.upiserver.repo.ReqrespauthdetailsRepository;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.JsonMan;

@Component
public class ReqRespAuthDetailsDaoImpl implements ReqRespAuthDetailsDao {

	private static final Logger log = LoggerFactory.getLogger(ReqRespAuthDetailsDaoImpl.class);

	@Autowired
	ReqrespauthdetailsRepository reqRespAuthDetailsRepo;

	@Autowired
	PayeesDao payyeDao;

	@Autowired
	private CustomerAccountDao customerAccountDao;
	
	@Autowired
	private ReqRespAuthDetailsPayeesDao reqRespAuthDetailsPayeesDao;
	
	@Autowired
	private CustomerMandatesDao customerMandatesDao;

	
	@Override
	public ReqRespAuthDetails getOnTxnId(String txnId) {
		ReqRespAuthDetails reqRespAuth=null;
		try {
			reqRespAuth=reqRespAuthDetailsRepo.findByTxnIdAndRespInsertIsNull(txnId);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return reqRespAuth;
	}

	@Override
	public void insertReq(ReqAuthDetails reqAuthDetails) {
		try {
			long regId=0l;
			if(reqAuthDetails.getTxn().getInitiationMode().equals("11")||InitiationMode.MANDATEQR.getMode().equals(reqAuthDetails.getTxn().getInitiationMode())) {
				log.info("Mandate COLLECT");
				CustomerMandatesEntity customerMandates = customerMandatesDao.findByUmnAndSignValue(reqAuthDetails.getPayer().getAddr());
				regId=customerMandates.getRegId();
			}
			else {
				 regId = customerAccountDao.getRegIdOfActiveAccByVpa(reqAuthDetails.getPayer().getAddr());
			}
			ReqRespAuthDetails reqrespauthdetails = new ReqRespAuthDetails();
			reqrespauthdetails.setReqInsert(new Date());
			reqrespauthdetails.setRegid(regId);
			reqrespauthdetails.setReqHeadMsgId(reqAuthDetails.getHead().getMsgId());
			reqrespauthdetails.setReqHeadOrgId(reqAuthDetails.getHead().getOrgId());
			reqrespauthdetails.setReqHeadTs(reqAuthDetails.getHead().getTs());
			reqrespauthdetails.setTxnCustRef(reqAuthDetails.getTxn().getCustRef());
			reqrespauthdetails.setTxnId(reqAuthDetails.getTxn().getId());
			reqrespauthdetails.setTxnIdPrf(reqAuthDetails.getTxn().getId().substring(0, 3));
			reqrespauthdetails.setTxnNote(reqAuthDetails.getTxn().getNote());
			reqrespauthdetails.setTxnRefid(reqAuthDetails.getTxn().getRefId());
			reqrespauthdetails.setTxnRefurl(reqAuthDetails.getTxn().getRefUrl());
			reqrespauthdetails.setTxnTs(reqAuthDetails.getTxn().getTs());
			reqrespauthdetails.setTxnType(reqAuthDetails.getTxn().getType().value());
			reqrespauthdetails.setPayerAddr(reqAuthDetails.getPayer().getAddr());
			reqrespauthdetails.setPayerCode(reqAuthDetails.getPayer().getCode());
			reqrespauthdetails.setPayerSeqNum(reqAuthDetails.getPayer().getSeqNum());
			reqrespauthdetails.setIniationMode(reqAuthDetails.getTxn().getInitiationMode());
			reqrespauthdetails.setPurpose(reqAuthDetails.getTxn().getPurpose());

			// TODO
			// reqrespauthdetails.setRefCategory(reqAuthDetails.getTxn().getRefCategory());
			// reqrespauthdetails.setPayerType(reqAuthDetails.getPayer().getType().value());
			reqrespauthdetails.setPayerHandal(
					reqAuthDetails.getPayer().getAddr().substring(reqAuthDetails.getPayer().getAddr().indexOf("@")));
			if (null != reqAuthDetails.getTxn().getRules()) {
				List<Rule> ruleList = reqAuthDetails.getTxn().getRules().getRule();
				for (int i = 0; i < ruleList.size(); i++) {
					if (ExpireRuleConstant.EXPIREAFTER.value()
							.equalsIgnoreCase((ruleList.get(i).getName().toString()))) {
						reqrespauthdetails.setRuleExpireAfter(Integer.parseInt(ruleList.get(i).getValue()));
					}
					if (ExpireRuleConstant.MINAMOUNT.value().equalsIgnoreCase(ruleList.get(i).getName().toString())) {
						reqrespauthdetails.setRuleMinAmount(ruleList.get(i).getValue());
					}
				}
			}

			if (null != reqAuthDetails.getPayer().getType()) {
				reqrespauthdetails.setPayerType(reqAuthDetails.getPayer().getType().value());
			}
			if (null != reqAuthDetails.getPayer().getDevice()) {
				List<Tag> deviceTag = reqAuthDetails.getPayer().getDevice().getTag();
				for (int i = 0; i < deviceTag.size(); i++) {
					if (DeviceTagNameType.APP.value().equals(deviceTag.get(i).getName().value())) {
						reqrespauthdetails.setDeviceApp(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.CAPABILITY.value().equals(deviceTag.get(i).getName().value())) {
						reqrespauthdetails.setDeviceCapability(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.GEOCODE.value().equals(deviceTag.get(i).getName().value())) {
						reqrespauthdetails.setDeviceGeocode(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.ID.value().equals(deviceTag.get(i).getName().value())) {
						reqrespauthdetails.setDeviceId(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.IP.value().equals(deviceTag.get(i).getName().value())) {
						reqrespauthdetails.setDeviceIp(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.LOCATION.value().equals(deviceTag.get(i).getName().value())) {
						reqrespauthdetails.setDeviceLocation(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.MOBILE.value().equals(deviceTag.get(i).getName().value())) {
						reqrespauthdetails.setDeviceMobile(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.OS.value().equals(deviceTag.get(i).getName().value())) {
						reqrespauthdetails.setDeviceOs(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.TYPE.value().equals(deviceTag.get(i).getName().value())) {
						reqrespauthdetails.setDeviceType(deviceTag.get(i).getValue());
					}
				}
			}
			if (null != reqAuthDetails.getPayer().getInfo()) {
				reqrespauthdetails.setInfoId(reqAuthDetails.getPayer().getInfo().getIdentity().getId());
				reqrespauthdetails.setInfoIdRatingvaddr(
						reqAuthDetails.getPayer().getInfo().getRating().getVerifiedAddress().value());
				reqrespauthdetails.setInfoIdType(reqAuthDetails.getPayer().getInfo().getIdentity().getType().value());
				reqrespauthdetails
						.setInfoIdVerifiedName(reqAuthDetails.getPayer().getInfo().getIdentity().getVerifiedName());
			}
			if (null != reqAuthDetails.getPayer().getCreds()) {
				List<CredsType.Cred> creadsList = reqAuthDetails.getPayer().getCreds().getCred();
				if (0 < creadsList.size()) {
					String creadsSubType = ConstantI.CONST_BLANK, creadsType = ConstantI.CONST_BLANK;
					for (int i = 0; i < creadsList.size(); i++) {
						creadsSubType += creadsList.get(i).getSubType().value() + ConstantI.CONST_SPCL_PIPE;
						creadsType += creadsList.get(i).getType().value() + ConstantI.CONST_SPCL_PIPE;
					}

					reqrespauthdetails.setCredSubType(creadsSubType);
					reqrespauthdetails.setCredType(creadsType);
				}
			}

			reqrespauthdetails.setAmountCrr(reqAuthDetails.getPayer().getAmount().getCurr());
			reqrespauthdetails.setAmountVal(reqAuthDetails.getPayer().getAmount().getValue());
			List<Detail> acDetails =null;
			if (reqAuthDetails.getPayer().getAc() != null) {
				acDetails = reqAuthDetails.getPayer().getAc().getDetail();
			}
			
			
			//List<Detail> acDetails = reqAuthDetails.getPayer().getAc().getDetail();
			
			if (acDetails!=null && 0 < acDetails.size()) {

				reqrespauthdetails.setAcAddrType(reqAuthDetails.getPayer().getAc().getAddrType().value());

				for (Detail detail : acDetails) { // @uday updated on 31-07-18
					if (ConstantI.ACCOUNT.equalsIgnoreCase(reqAuthDetails.getPayer().getAc().getAddrType().value())) {
						if (AccountDetailType.ACNUM.equals(detail.getName())) {
							reqrespauthdetails.setAcAddrTypeDetail1(
									AccountDetailType.ACNUM.value() + ConstantI.CONST_SPCL_EQUAL + detail.getValue());
						} else if (AccountDetailType.ACTYPE.equals(detail.getName())) {
							reqrespauthdetails.setAcAddrTypeDetail2(
									AccountDetailType.ACTYPE.value() + ConstantI.CONST_SPCL_EQUAL + detail.getValue());
						} else if (AccountDetailType.IFSC.equals(detail.getName())) {
							reqrespauthdetails.setAcAddrTypeDetail3(
									AccountDetailType.IFSC.value() + ConstantI.CONST_SPCL_EQUAL + detail.getValue());
						}
					} else if (ConstantI.MOBILE
							.equalsIgnoreCase(reqAuthDetails.getPayer().getAc().getAddrType().value())) {
						if (AccountDetailType.MOBNUM.equals(detail.getName())) {
							reqrespauthdetails.setAcAddrTypeDetail1(
									AccountDetailType.MOBNUM.value() + ConstantI.CONST_SPCL_EQUAL + detail.getValue());
						} else if (AccountDetailType.MMID.equals(detail.getName())) {
							reqrespauthdetails.setAcAddrTypeDetail2(
									AccountDetailType.MMID.value() + ConstantI.CONST_SPCL_EQUAL + detail.getValue());
						}
					} else if (ConstantI.AADHAAR
							.equalsIgnoreCase(reqAuthDetails.getPayer().getAc().getAddrType().value())) {
						if (AccountDetailType.UIDNUM.equals(detail.getName())) {
							reqrespauthdetails.setAcAddrTypeDetail1(
									AccountDetailType.UIDNUM.value() + ConstantI.CONST_SPCL_EQUAL + detail.getValue());
						} else if (AccountDetailType.IIN.equals(detail.getName())) {
							reqrespauthdetails.setAcAddrTypeDetail2(
									AccountDetailType.IIN.value() + ConstantI.CONST_SPCL_EQUAL + detail.getValue());
						}
					} else if (ConstantI.CARD
							.equalsIgnoreCase(reqAuthDetails.getPayer().getAc().getAddrType().value())) {
						if (AccountDetailType.CARDNUM.equals(detail.getName())) {
							reqrespauthdetails.setAcAddrTypeDetail1(
									AccountDetailType.CARDNUM.value() + ConstantI.CONST_SPCL_EQUAL + detail.getValue());
						} else if (AccountDetailType.ACTYPE.equals(detail.getName())) {
							reqrespauthdetails.setAcAddrTypeDetail2(
									AccountDetailType.ACTYPE.value() + ConstantI.CONST_SPCL_EQUAL + detail.getValue());
						}
					}
				}
			}
			reqRespAuthDetailsRepo.save(reqrespauthdetails);
			// log.debug("After commit {}", JsonMan.getJSONStr(reqrespauthdetails));
			reqRespAuthDetailsPayeesDao.insertPayees(reqAuthDetails.getPayees(), reqAuthDetails.getTxn().getId(),
					reqAuthDetails.getHead().getMsgId(), reqrespauthdetails.getIdReqRespAuthDetails());
		} catch (Exception e) {
			log.error("error {}", e);
			ErrorLog.sendError(e, ReqRespAuthDetailsDaoImpl.class);
		}

	}

	@Override
	public void insertReq(ReqAuthDetails reqAuthDetails, RespAuthDetails respAuthDetails, Ack ack, Date reqDate,
			Date respDate) {

		try {
			ReqRespAuthDetails reqrespauthdetails = new ReqRespAuthDetails();
			reqrespauthdetails.setReqInsert(reqDate);
			reqrespauthdetails.setRespInsert(respDate);
			reqrespauthdetails.setReqHeadMsgId(reqAuthDetails.getHead().getMsgId());
			reqrespauthdetails.setReqHeadOrgId(reqAuthDetails.getHead().getOrgId());
			reqrespauthdetails.setReqHeadTs(reqAuthDetails.getHead().getTs());

			reqrespauthdetails.setRespHeadMsgId(respAuthDetails.getHead().getMsgId());
			reqrespauthdetails.setRespHeadOrgId(respAuthDetails.getHead().getOrgId());
			reqrespauthdetails.setRespHeadTs(respAuthDetails.getHead().getTs());

			reqrespauthdetails.setTxnCustRef(reqAuthDetails.getTxn().getCustRef());
			reqrespauthdetails.setTxnId(reqAuthDetails.getTxn().getId());
			reqrespauthdetails.setTxnIdPrf(reqAuthDetails.getTxn().getId().substring(0, 3));
			reqrespauthdetails.setTxnNote(reqAuthDetails.getTxn().getNote());
			reqrespauthdetails.setTxnRefid(reqAuthDetails.getTxn().getRefId());
			reqrespauthdetails.setTxnRefurl(reqAuthDetails.getTxn().getRefUrl());
			reqrespauthdetails.setTxnTs(reqAuthDetails.getTxn().getTs());
			reqrespauthdetails.setTxnType(reqAuthDetails.getTxn().getType().value());
			reqrespauthdetails.setPayerAddr(reqAuthDetails.getPayer().getAddr());
			reqrespauthdetails.setPayerCode(reqAuthDetails.getPayer().getCode());
			reqrespauthdetails.setPayerSeqNum(reqAuthDetails.getPayer().getSeqNum());
			reqrespauthdetails.setPayerType(reqAuthDetails.getPayer().getType().value());

			List<Tag> deviceTag = reqAuthDetails.getPayer().getDevice().getTag();
			for (int i = 0; i < deviceTag.size(); i++) {
				if (DeviceTagNameType.APP.value().equals(deviceTag.get(i).getName().value())) {
					reqrespauthdetails.setDeviceApp(deviceTag.get(i).getValue());
				}
				if (DeviceTagNameType.CAPABILITY.value().equals(deviceTag.get(i).getName().value())) {
					reqrespauthdetails.setDeviceCapability(deviceTag.get(i).getValue());
				}
				if (DeviceTagNameType.GEOCODE.value().equals(deviceTag.get(i).getName().value())) {
					reqrespauthdetails.setDeviceGeocode(deviceTag.get(i).getValue());
				}
				if (DeviceTagNameType.ID.value().equals(deviceTag.get(i).getName().value())) {
					reqrespauthdetails.setDeviceId(deviceTag.get(i).getValue());
				}
				if (DeviceTagNameType.IP.value().equals(deviceTag.get(i).getName().value())) {
					reqrespauthdetails.setDeviceIp(deviceTag.get(i).getValue());
				}
				if (DeviceTagNameType.LOCATION.value().equals(deviceTag.get(i).getName().value())) {
					reqrespauthdetails.setDeviceLocation(deviceTag.get(i).getValue());
				}
				if (DeviceTagNameType.MOBILE.value().equals(deviceTag.get(i).getName().value())) {
					reqrespauthdetails.setDeviceMobile(deviceTag.get(i).getValue());
				}
				if (DeviceTagNameType.OS.value().equals(deviceTag.get(i).getName().value())) {
					reqrespauthdetails.setDeviceOs(deviceTag.get(i).getValue());
				}
				if (DeviceTagNameType.TYPE.value().equals(deviceTag.get(i).getName().value())) {
					reqrespauthdetails.setDeviceType(deviceTag.get(i).getValue());
				}
			}
			reqrespauthdetails.setInfoId(reqAuthDetails.getPayer().getInfo().getIdentity().getId());
			reqrespauthdetails
					.setInfoIdRatingvaddr(reqAuthDetails.getPayer().getInfo().getRating().getVerifiedAddress().value());
			reqrespauthdetails.setInfoIdType(reqAuthDetails.getPayer().getInfo().getIdentity().getType().value());
			reqrespauthdetails
					.setInfoIdVerifiedName(reqAuthDetails.getPayer().getInfo().getIdentity().getVerifiedName());

			reqrespauthdetails.setAmountCrr(reqAuthDetails.getPayer().getAmount().getCurr());
			reqrespauthdetails.setAmountVal(reqAuthDetails.getPayer().getAmount().getValue());
			List<Detail> acDetails = reqAuthDetails.getPayer().getAc().getDetail();
			String acAddrTypeDetail1 = ConstantI.CONST_BLANK, acaddrTypeDetail2 = ConstantI.CONST_BLANK, acaddrTypeDetail3 = ConstantI.CONST_BLANK;
			for (int i = 0; i < acDetails.size(); i++) {
				String temp = acDetails.get(i).getName().value() +  ConstantI.CONST_SPCL_EQUAL + acDetails.get(i).getValue();
				if (0 == i) {
					acAddrTypeDetail1 = temp;
				} else if (1 == i) {
					acaddrTypeDetail2 = temp;
				} else {
					acaddrTypeDetail3 = temp;
				}
			}
			reqrespauthdetails.setAcAddrTypeDetail1(acAddrTypeDetail1);
			reqrespauthdetails.setAcAddrTypeDetail2(acaddrTypeDetail2);
			reqrespauthdetails.setAcAddrTypeDetail3(acaddrTypeDetail3);
			List<CredsType.Cred> creadsList = reqAuthDetails.getPayer().getCreds().getCred();
			String creadsSubType = ConstantI.CONST_BLANK, creadsType = ConstantI.CONST_BLANK;
			for (int i = 0; i < creadsList.size(); i++) {
				creadsSubType += creadsList.get(i).getSubType().value() + ConstantI.CONST_SPCL_PIPE;
				creadsType += creadsList.get(i).getType().value() + ConstantI.CONST_SPCL_PIPE;
			}

			reqrespauthdetails.setCredSubType(creadsSubType);
			reqrespauthdetails.setCredType(creadsType);

			payyeDao.insertPayees(reqAuthDetails.getPayees(), reqAuthDetails.getTxn().getId(),
					reqAuthDetails.getHead().getMsgId());

			reqrespauthdetails.setRespAuthAck(JsonMan.getJSONStr(ack));
			reqRespAuthDetailsRepo.save(reqrespauthdetails);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}


	@Override
	public void updateTxnConfirmation(ReqTxnConfirmation reqTxnConfirmation, RespTxnConfirmation respTxnConfirmation,
			Ack ack) {
		try {
			ReqRespAuthDetails reqrespauthdetails= reqRespAuthDetailsRepo.findByTxnIdAndRespInsertIsNull(reqTxnConfirmation.getTxn().getId());
			
			if (null != reqrespauthdetails) {
				reqrespauthdetails.setTxnConfOrgErrCode(reqTxnConfirmation.getTxnConfirmation().getOrgErrCode());
				reqrespauthdetails.setTxnConfOrgStatus(reqTxnConfirmation.getTxnConfirmation().getOrgStatus());

				List<Ref> refList = reqTxnConfirmation.getTxnConfirmation().getRef();
				String refSettAmount = ConstantI.CONST_BLANK, refReversalRespCode = ConstantI.CONST_BLANK, refOrgAmount = ConstantI.CONST_BLANK, refApprovalNum = ConstantI.CONST_BLANK,
						refRespCode = ConstantI.CONST_BLANK;

				for (Ref ref : refList) {
					refSettAmount += ref.getSettAmount() + ConstantI.CONST_SPCL_PIPE;
					refApprovalNum += ref.getApprovalNum() + ConstantI.CONST_SPCL_PIPE;
					refRespCode += ref.getRespCode() + ConstantI.CONST_SPCL_PIPE;
					refReversalRespCode += ref.getReversalRespCode() + ConstantI.CONST_SPCL_PIPE;
					refOrgAmount += ref.getOrgAmount() + ConstantI.CONST_SPCL_PIPE;
				}
				reqrespauthdetails.setRefApprovalNum(refApprovalNum);
				reqrespauthdetails.setRefOrgAmount(refOrgAmount);
				reqrespauthdetails.setRefRespCode(refRespCode);
				reqrespauthdetails.setRefReversalRespCode(refReversalRespCode);
				reqrespauthdetails.setRefSettAmount(refSettAmount);
				reqrespauthdetails.setTxnConfAck(JsonMan.getJSONStr(ack));
				reqRespAuthDetailsRepo.save(reqrespauthdetails);
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	@Override
	public List<ReqRespAuthDetails> getOnIdReqRespAuthDetails() {
		List<ReqRespAuthDetails> reqRespList=new ArrayList<>();
		try {
			reqRespList=reqRespAuthDetailsRepo.findByTxnTypeAndRespInsertIsNull(ConstantI.COLLECT);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return reqRespList;
	}

	@Override
	public List<ReqRespAuthDetails> getOnPayeeAddr(String payeeAddr, Long regId) {
		List<ReqRespAuthDetails> reqRespList=new ArrayList<>();
		try {
			reqRespList=reqRespAuthDetailsRepo.getOnPayeeAddr(payeeAddr,String.valueOf(regId));
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return reqRespList;
	}


	@Override
	public void updateResp(RespAuthDetails respAuthDetails, Ack ack) {
		ReqRespAuthDetails reqrespauthdetails=null;
		try {
			reqrespauthdetails = getOnTxnId(respAuthDetails.getTxn().getId());
			if (null == reqrespauthdetails) {
				reqrespauthdetails = new ReqRespAuthDetails();
			}
			if (ConstantI.COLLECT.equalsIgnoreCase(respAuthDetails.getTxn().getType().value())) {
				try {
					reqrespauthdetails.setPayerName(respAuthDetails.getPayer().getName());
				} catch (Exception e) {
				}
				try {
					reqrespauthdetails.setPayerType(respAuthDetails.getPayer().getType().value());
				} catch (Exception e) {
				}
			}

			reqrespauthdetails.setRespInsert(new Date());
			reqrespauthdetails.setRespHeadMsgId(respAuthDetails.getHead().getMsgId());
			reqrespauthdetails.setRespHeadOrgId(respAuthDetails.getHead().getOrgId());
			reqrespauthdetails.setRespHeadTs(respAuthDetails.getHead().getTs());
			reqrespauthdetails.setRespResult(respAuthDetails.getResp().getResult());
			if (null != respAuthDetails.getPayer().getDevice()) {
				List<Tag> deviceTag = respAuthDetails.getPayer().getDevice().getTag();
				if (0 < deviceTag.size()) {
					for (int i = 0; i < deviceTag.size(); i++) {
						if (DeviceTagNameType.APP.value().equals(deviceTag.get(i).getName().value())) {
							reqrespauthdetails.setDeviceApp(deviceTag.get(i).getValue());
						}
						if (DeviceTagNameType.CAPABILITY.value().equals(deviceTag.get(i).getName().value())) {
							reqrespauthdetails.setDeviceCapability(deviceTag.get(i).getValue());
						}
						if (DeviceTagNameType.GEOCODE.value().equals(deviceTag.get(i).getName().value())) {
							reqrespauthdetails.setDeviceGeocode(deviceTag.get(i).getValue());
						}
						if (DeviceTagNameType.ID.value().equals(deviceTag.get(i).getName().value())) {
							reqrespauthdetails.setDeviceId(deviceTag.get(i).getValue());
						}
						if (DeviceTagNameType.IP.value().equals(deviceTag.get(i).getName().value())) {
							reqrespauthdetails.setDeviceIp(deviceTag.get(i).getValue());
						}
						if (DeviceTagNameType.LOCATION.value().equals(deviceTag.get(i).getName().value())) {
							reqrespauthdetails.setDeviceLocation(deviceTag.get(i).getValue());
						}
						if (DeviceTagNameType.MOBILE.value().equals(deviceTag.get(i).getName().value())) {
							reqrespauthdetails.setDeviceMobile(deviceTag.get(i).getValue());
						}
						if (DeviceTagNameType.OS.value().equals(deviceTag.get(i).getName().value())) {
							reqrespauthdetails.setDeviceOs(deviceTag.get(i).getValue());
						}
						if (DeviceTagNameType.TYPE.value().equals(deviceTag.get(i).getName().value())) {
							reqrespauthdetails.setDeviceType(deviceTag.get(i).getValue());
						}
					}
				}
			}
			if (null != respAuthDetails.getPayer().getInfo()) {
				reqrespauthdetails.setInfoId(respAuthDetails.getPayer().getInfo().getIdentity().getId());
				reqrespauthdetails.setInfoIdRatingvaddr(
						respAuthDetails.getPayer().getInfo().getRating().getVerifiedAddress().value());
				reqrespauthdetails.setInfoIdType(respAuthDetails.getPayer().getInfo().getIdentity().getType().value());
				reqrespauthdetails
						.setInfoIdVerifiedName(respAuthDetails.getPayer().getInfo().getIdentity().getVerifiedName());
			}
			reqrespauthdetails.setAmountCrr(respAuthDetails.getPayer().getAmount().getCurr());
			reqrespauthdetails.setAmountVal(respAuthDetails.getPayer().getAmount().getValue());
			if (null != respAuthDetails.getPayer().getAc()) {
				List<Detail> acDetails = respAuthDetails.getPayer().getAc().getDetail();
				if (0 < acDetails.size()) {
					reqrespauthdetails.setAcAddrType(respAuthDetails.getPayer().getAc().getAddrType().value());
					for (Detail detail : acDetails) {
						if (ConstantI.ACCOUNT.equalsIgnoreCase(respAuthDetails.getPayer().getAc().getAddrType().value())) {
							if (AccountDetailType.ACNUM.equals(detail.getName())) {
								reqrespauthdetails.setAcAddrTypeDetail1(detail.getValue());
							} else if (AccountDetailType.ACTYPE.equals(detail.getName())) {
								reqrespauthdetails.setAcAddrTypeDetail2(detail.getValue());
							} else if (AccountDetailType.IFSC.equals(detail.getName())) {
								reqrespauthdetails.setAcAddrTypeDetail3(detail.getValue());
							}
						} else if (ConstantI.MOBILE.equalsIgnoreCase(respAuthDetails.getPayer().getAc().getAddrType().value())) {
							if (AccountDetailType.MOBNUM.equals(detail.getName())) {
								reqrespauthdetails.setAcAddrTypeDetail1(detail.getValue());
							} else if (AccountDetailType.MMID.equals(detail.getName())) {
								reqrespauthdetails.setAcAddrTypeDetail2(detail.getValue());
							}
						} else if (ConstantI.AADHAAR.equalsIgnoreCase(respAuthDetails.getPayer().getAc().getAddrType().value())) {
							if (AccountDetailType.UIDNUM.equals(detail.getName())) {
								reqrespauthdetails.setAcAddrTypeDetail1(detail.getValue());
							} else if (AccountDetailType.IIN.equals(detail.getName())) {
								reqrespauthdetails.setAcAddrTypeDetail2(detail.getValue());
							}
						} else if (ConstantI.CARD.equalsIgnoreCase(respAuthDetails.getPayer().getAc().getAddrType().value())) {
							if (AccountDetailType.CARDNUM.equals(detail.getName())) {
								reqrespauthdetails.setAcAddrTypeDetail1(detail.getValue());
							} else if (AccountDetailType.ACTYPE.equals(detail.getName())) {
								reqrespauthdetails.setAcAddrTypeDetail2(detail.getValue());
							}
						}
					}
				}
			}
			if (null != respAuthDetails.getPayer().getCreds()) {
				List<CredsType.Cred> creadsList = respAuthDetails.getPayer().getCreds().getCred();
				if (0 < creadsList.size()) {
					String creadsSubType = ConstantI.CONST_BLANK, creadsType = ConstantI.CONST_BLANK;
					for (int i = 0; i < creadsList.size(); i++) {
						creadsSubType += creadsList.get(i).getSubType().value() + ConstantI.CONST_SPCL_PIPE;
						creadsType += creadsList.get(i).getType().value() + ConstantI.CONST_SPCL_PIPE;
					}
					reqrespauthdetails.setCredSubType(creadsSubType);
					reqrespauthdetails.setCredType(creadsType);
				}
			}
			if(ack!=null) {
				reqrespauthdetails.setRespAuthAck(JsonMan.getJSONStr(ack));
			}
			else {
				log.info("ACK is null for txn id {} ",reqrespauthdetails.getTxnId());
			}
			reqRespAuthDetailsRepo.save(reqrespauthdetails);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	@Override
	public List<ReqRespAuthDetails>	 getAllByCollectAndRespInsertIsNull() {
		try {
			// "SELECT * FROM req_resp_auth_details where TxnType='COLLECT' and RespInsert
			// is null"
			return reqRespAuthDetailsRepo.findByTxnTypeAndRespInsertIsNull("COLLECT");
		} catch (Exception e) {
			log.error("error cause={}", e.getMessage());
			ErrorLog.sendError(e, ReqRespAuthDetailsDaoImpl.class);
		}
		return new ArrayList<>();
	}
	
}
