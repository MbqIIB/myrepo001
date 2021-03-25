package com.npst.upiserver.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.npst.upiserver.dao.ReqRespDebitCreditPayeesDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.ReqRespDebitCreditPayeesEntity;
import com.npst.upiserver.npcischema.AccountDetailType;
import com.npst.upiserver.npcischema.AccountType.Detail;
import com.npst.upiserver.npcischema.DeviceTagNameType;
import com.npst.upiserver.npcischema.DeviceType.Tag;
import com.npst.upiserver.npcischema.PayeeType;
import com.npst.upiserver.npcischema.PayeesType;
import com.npst.upiserver.repo.ReqRespDebitCreditPayeesRepo;
import com.npst.upiserver.util.ErrorLog;

@Component
public class ReqRespDebitCreditPayeesDaoImpl implements ReqRespDebitCreditPayeesDao {
	private static final Logger log = LoggerFactory.getLogger(ReqRespDebitCreditPayeesDaoImpl.class);

	@Autowired
	private ReqRespDebitCreditPayeesRepo debitCreditPayeesRepo;

	@Override
	public void insertPayees(PayeesType payeesType, String txnId, String msgId, long id) {
		// TODO Auto-generated method stub
		try {
			if (null != payeesType) {
				List<PayeeType> payeeList = payeesType.getPayee();
				for (PayeeType payeeType : payeeList) {
					ReqRespDebitCreditPayeesEntity dbPayee = new ReqRespDebitCreditPayeesEntity();
					dbPayee.setIdReqRespDebitCredit(id);
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
						if (0 < deviceTagPayee.size()) {
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
					if (null != payeeType.getAmount()) {
						dbPayee.setAmountCrr(payeeType.getAmount().getCurr());
						dbPayee.setAmountVal(payeeType.getAmount().getValue());
					}
					debitCreditPayeesRepo.save(dbPayee);
				}

			}
		} catch (Exception e) {
			log.error("error {}", e);
			ErrorLog.sendError(e, ReqRespDebitCreditPayeesDaoImpl.class);
		}
	}

	@Override
	public void insertPayees(ReqResp reqResp, String txnId, String msgId) {
		try {
			ReqRespDebitCreditPayeesEntity dbPayee = new ReqRespDebitCreditPayeesEntity();
			dbPayee.setTxnId(txnId);
			dbPayee.setHeadMsgId(msgId);
			dbPayee.setPayeeAddr(reqResp.getPayeeAddr());
			dbPayee.setPayeeHandal(reqResp.getPayeeAddr() == null || reqResp.getPayeeAddr().trim().isEmpty() ? null
					: reqResp.getPayeeAddr().substring(reqResp.getPayeeAddr().indexOf("@")));

			String accAddrTypeDetail1 = "", accAddrTypeDetail2 = "", accAddrTypeDetail3 = "";
			if ("ACCOUNT".equalsIgnoreCase(reqResp.getPayeeAddrType())) {
				accAddrTypeDetail1 = AccountDetailType.IFSC.toString() + "|" + reqResp.getPayeeIfsc().toUpperCase();
				accAddrTypeDetail2 = AccountDetailType.ACTYPE.toString() + "|" + reqResp.getPayeeAcType();
				accAddrTypeDetail3 = AccountDetailType.ACNUM.toString() + "|" + reqResp.getPayeeAcNum();
			} else if ("MOBILE".equalsIgnoreCase(reqResp.getPayeeAddrType())) {
				accAddrTypeDetail1 = AccountDetailType.MMID.toString() + "|" + reqResp.getPayeeMmid();
				accAddrTypeDetail3 = AccountDetailType.MOBNUM + "|"
						+ (reqResp.getPayeeMobileNo() == null ? reqResp.getPayeeDeviceMobile()
								: reqResp.getPayeeMobileNo());
			}
			dbPayee.setAcAddrTypeDetail1(accAddrTypeDetail1);
			dbPayee.setAcAddrTypeDetail2(accAddrTypeDetail2);
			dbPayee.setAcAddrTypeDetail3(accAddrTypeDetail3);
			dbPayee.setAmountVal(reqResp.getPayeeAmount());
			debitCreditPayeesRepo.save(dbPayee);
		} catch (Exception e) {
			log.error("error {}", e);
			ErrorLog.sendError(e, ReqRespDebitCreditPayeesDaoImpl.class);
		}

	}

}
