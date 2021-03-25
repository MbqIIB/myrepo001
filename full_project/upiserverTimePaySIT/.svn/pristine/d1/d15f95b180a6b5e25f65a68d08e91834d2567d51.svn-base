package com.npst.upiserver.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.PayeesDao;
import com.npst.upiserver.entity.Payees;
import com.npst.upiserver.npcischema.AccountType.Detail;
import com.npst.upiserver.npcischema.DeviceTagNameType;
import com.npst.upiserver.npcischema.DeviceType.Tag;
import com.npst.upiserver.npcischema.PayeeType;
import com.npst.upiserver.npcischema.PayeesType;
import com.npst.upiserver.repo.PayeesRepository;


@Component
public class PayeesDaoImpl implements PayeesDao {
	private static final Logger log = LoggerFactory.getLogger(PayeesDaoImpl.class);
	
	@Autowired
	PayeesRepository payeeRepo;
	
	@Override
	public void insertPayees(PayeesType payeesType, String txnId, String msgId) {
		log.trace("");
		try {
			if (null != payeesType) {
				List<PayeeType> payeeList = payeesType.getPayee();
				for (PayeeType payeeType : payeeList) {
					Payees dbPayee = new Payees();
					dbPayee.setPayeeAddr(payeeType.getAddr());
					dbPayee.setPayeeCode(payeeType.getCode());
					dbPayee.setPayeeHandal(payeeType.getAddr().substring(payeeType.getAddr().indexOf(ConstantI.CONST_SPCL_AT_RATE)));
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
						for (int i = 0; i < deviceTagPayee.size(); i++) {
							if (DeviceTagNameType.APP.value().equals(deviceTagPayee.get(i).getName().value())) {
								dbPayee.setDeviceApp(deviceTagPayee.get(i).getValue());
							}
							if (DeviceTagNameType.CAPABILITY.value().equals(deviceTagPayee.get(i).getName().value())) {
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
							if (DeviceTagNameType.LOCATION.value().equals(deviceTagPayee.get(i).getName().value())) {
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
					if (null != payeeType.getAc()) {
						List<Detail> acDetailsPayee = payeeType.getAc().getDetail();
						String acaddrTypeDetailPayee1 = ConstantI.CONST_BLANK, acaddrTypeDetailPayee2 = ConstantI.CONST_BLANK, acaddrTypeDetailPayee3 = ConstantI.CONST_BLANK;
						for (int i = 0; i < acDetailsPayee.size(); i++) {
							String temp = acDetailsPayee.get(i).getName().value() + ConstantI.CONST_SPCL_EQUAL
									+ acDetailsPayee.get(i).getValue();
							if (0 == i) {
								acaddrTypeDetailPayee1 = temp;
							} else if (1 == i) {
								acaddrTypeDetailPayee2 = temp;
							} else {
								acaddrTypeDetailPayee3 = temp;
							}
						}
						dbPayee.setAcAddrTypeDetail1(acaddrTypeDetailPayee1);
						dbPayee.setAcAddrTypeDetail2(acaddrTypeDetailPayee2);
						dbPayee.setAcAddrTypeDetail3(acaddrTypeDetailPayee3);
					}
					if (null != payeeType.getAmount()) {
						dbPayee.setAmountCrr(payeeType.getAmount().getCurr());
						dbPayee.setAmountVal(payeeType.getAmount().getValue());
					}
					dbPayee.setTxnId(txnId);
					dbPayee.setHeadMsgId(msgId);
					payeeRepo.save(dbPayee);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	@Override
	public void insertPayeesForDebitCredit(PayeesType payeesType, String txnId, String msgId,
			Long idReqRespDebitCredit) {
		try {
			if (null != payeesType) {
				List<PayeeType> payeeList = payeesType.getPayee();
				Payees dbPayee = null;
				for (PayeeType payeeType : payeeList) {
					dbPayee = new Payees();
					dbPayee.setPayeeAddr(payeeType.getAddr());
					dbPayee.setPayeeCode(payeeType.getCode());
					dbPayee.setPayeeHandal(payeeType.getAddr().substring(payeeType.getAddr().indexOf(ConstantI.CONST_SPCL_AT_RATE)));
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
						for (int i = 0; i < deviceTagPayee.size(); i++) {
							if (DeviceTagNameType.APP.value().equals(deviceTagPayee.get(i).getName().value())) {
								dbPayee.setDeviceApp(deviceTagPayee.get(i).getValue());
							}
							if (DeviceTagNameType.CAPABILITY.value().equals(deviceTagPayee.get(i).getName().value())) {
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
							if (DeviceTagNameType.LOCATION.value().equals(deviceTagPayee.get(i).getName().value())) {
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
					if (null != payeeType.getAc()) {
						List<Detail> acDetailsPayee = payeeType.getAc().getDetail();
						String acaddrTypeDetailPayee1 = ConstantI.CONST_BLANK, acaddrTypeDetailPayee2 = ConstantI.CONST_BLANK, acaddrTypeDetailPayee3 = ConstantI.CONST_BLANK;
						for (int i = 0; i < acDetailsPayee.size(); i++) {
							String temp = acDetailsPayee.get(i).getName().value() + ConstantI.CONST_SPCL_EQUAL
									+ acDetailsPayee.get(i).getValue();
							if (0 == i) {
								acaddrTypeDetailPayee1 = temp;
							} else if (1 == i) {
								acaddrTypeDetailPayee2 = temp;
							} else {
								acaddrTypeDetailPayee3 = temp;
							}
						}
						dbPayee.setAcAddrTypeDetail1(acaddrTypeDetailPayee1);
						dbPayee.setAcAddrTypeDetail2(acaddrTypeDetailPayee2);
						dbPayee.setAcAddrTypeDetail3(acaddrTypeDetailPayee3);
					}
					if (null != payeeType.getAmount()) {
						dbPayee.setAmountCrr(payeeType.getAmount().getCurr());
						dbPayee.setAmountVal(payeeType.getAmount().getValue());
					}
					dbPayee.setTxnId(txnId);
					dbPayee.setHeadMsgId(msgId);
					//dbPayee.setIdReqRespDebitCredit(idReqRespDebitCredit);
					payeeRepo.save(dbPayee);
					dbPayee = null;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
	}
}
