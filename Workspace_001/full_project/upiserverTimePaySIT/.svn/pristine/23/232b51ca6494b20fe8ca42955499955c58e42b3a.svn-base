package com.npst.upiserver.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.npst.upiserver.dao.ReqRespAuthMandatePayeesDao;
import com.npst.upiserver.entity.ReqRespAuthMandatePayeesEntity;
import com.npst.upiserver.npcischema.AccountType.Detail;
import com.npst.upiserver.npcischema.DeviceTagNameType;
import com.npst.upiserver.npcischema.DeviceType.Tag;
import com.npst.upiserver.npcischema.PayeeType;
import com.npst.upiserver.npcischema.PayeesType;
import com.npst.upiserver.repo.ReqRespAuthMandatePayeesRepo;
import com.npst.upiserver.util.ErrorLog;

@Component
public class ReqRespAuthMandatePayeesDaoImpl implements ReqRespAuthMandatePayeesDao {

	private static final Logger log = LoggerFactory.getLogger(ReqRespAuthMandatePayeesDaoImpl.class);

	@Autowired
	private ReqRespAuthMandatePayeesRepo reqRespAuthMandatePayeesRepo;

	@Override
	public void insertPayees(PayeesType payeesType, String txnId, String msgId, Long idReqRespAuthMandate) {
		try {
			if (null != payeesType) {
				List<PayeeType> payeeList = payeesType.getPayee();
				for (PayeeType payeeType : payeeList) {
					ReqRespAuthMandatePayeesEntity dbPayee = new ReqRespAuthMandatePayeesEntity();
					dbPayee.setIdReqRespAuthMandate(idReqRespAuthMandate);
					dbPayee.setTxnId(txnId);
					dbPayee.setHeadMsgId(msgId);
					dbPayee.setPayeeAddr(payeeType.getAddr());
					dbPayee.setPayeeCode(payeeType.getCode());
					dbPayee.setPayeeHandal(payeeType.getAddr().substring(payeeType.getAddr().indexOf('@')));
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

								if (DeviceTagNameType.TELECOM.value().equals(deviceTagPayee.get(i).getName().value())) {
									dbPayee.setDeviceTelecom(deviceTagPayee.get(i).getValue());
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
							//	String temp = acDetailsPayee.get(i).getName().value() + "="
									//	+ acDetailsPayee.get(i).getValue();
	String temp = acDetailsPayee.get(i).getValue();
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
					/*
					 * if (null != payeeType.getAmount()) {
					 * dbPayee.setAmountCrr(payeeType.getAmount().getCurr());
					 * dbPayee.setAmountVal(payeeType.getAmount().getValue()); }
					 */
					// TODO Add payee merchant details in db
					/**
					 * Merchant data
					 */
					// Save the Merchant Details in DB Dated::18-12-18
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
					reqRespAuthMandatePayeesRepo.save(dbPayee);
				}
			}
		} catch (Exception e) {
			log.error("Error: {}", e);
			ErrorLog.sendError(e, ReqRespAuthMandatePayeesDaoImpl.class);
		}
	}

	@Override
	public ReqRespAuthMandatePayeesEntity getByIdReqRespAuthMandate(long idReqRespAuthMandate) {
		try {
			List<ReqRespAuthMandatePayeesEntity> list = reqRespAuthMandatePayeesRepo
					.findByIdReqRespAuthMandate(idReqRespAuthMandate);
			if (list.size() > 0) {//list.size() == 1
				return list.get(0);
			} else if (list.size() == 0) {
				log.warn("ReqRespAuthMandatePayeesEntity not found for idReqRespAuthMandate={}", idReqRespAuthMandate);
			} else {
				log.warn("more than one ReqRespAuthMandatePayeesEntity found for idReqRespAuthMandate={}",
						idReqRespAuthMandate);
			}
		} catch (Exception e) {
			log.error("Error: {}", e);
			ErrorLog.sendError(e, ReqRespAuthMandatePayeesDaoImpl.class);
		}
		return null;
	}

}
