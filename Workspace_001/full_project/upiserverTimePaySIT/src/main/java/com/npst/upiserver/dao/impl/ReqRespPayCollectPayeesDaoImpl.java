package com.npst.upiserver.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.npst.upiserver.npcischema.AccountDetailType;
import com.npst.upiserver.npcischema.AccountType.Detail;
import com.npst.upiserver.npcischema.DeviceTagNameType;
import com.npst.upiserver.npcischema.DeviceType.Tag;
import com.npst.upiserver.npcischema.PayeeType;
import com.npst.upiserver.npcischema.PayeesType;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.ReqRespPayCollectPayeesDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.ReqRespPayCollectPayeesEntity;
import com.npst.upiserver.repo.ReqRespPayCollectPayeesRepo;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.NpciSchemaUtil;

@Component
public class ReqRespPayCollectPayeesDaoImpl implements ReqRespPayCollectPayeesDao {
	private static final Logger log = LoggerFactory.getLogger(ReqRespPayCollectPayeesDaoImpl.class);
	@Autowired
	private ReqRespPayCollectPayeesRepo reqRespPayCollectPayeesRepo;

	@Override
	public void insertPayees(PayeesType payeesType, String txnId, String msgId, long idReqRespPayCollect) {
		// TODO Auto-generated method stub
		try {
			if (null != payeesType) {
				List<PayeeType> payeeList = payeesType.getPayee();
				for (PayeeType payeeType : payeeList) {
					ReqRespPayCollectPayeesEntity dbPayee = new ReqRespPayCollectPayeesEntity();
					dbPayee.setIdReqRespPayCollect(idReqRespPayCollect);
					dbPayee.setTxnId(txnId);
					dbPayee.setHeadMsgId(msgId);
					dbPayee.setPayeeAddr(payeeType.getAddr());
					dbPayee.setPayeeCode(payeeType.getCode());
					dbPayee.setPayeeHandal(NpciSchemaUtil.getHandler(payeeType.getAddr()));
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
							String acaddrTypeDetailPayee1 = ConstantI.CONST_BLANK,
									acaddrTypeDetailPayee2 = ConstantI.CONST_BLANK,
									acaddrTypeDetailPayee3 = ConstantI.CONST_BLANK;
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
					reqRespPayCollectPayeesRepo.save(dbPayee);
				}
			}
		} catch (Exception e) {
			log.error("error {}", e);
			ErrorLog.sendError(e, ReqRespPayCollectPayeesDaoImpl.class);
		}

	}

	@Override
	public void insertPayees(ReqResp reqResp, String txnId, String msgId) {
		try {
			ReqRespPayCollectPayeesEntity dbPayee = new ReqRespPayCollectPayeesEntity();
			dbPayee.setTxnId(txnId);
			dbPayee.setHeadMsgId(msgId);
			dbPayee.setPayeeAddr(reqResp.getPayeeAddr());
			dbPayee.setPayeeHandal(
					reqResp.getPayeeAddr().substring(reqResp.getPayeeAddr().indexOf(ConstantI.CONST_SPCL_AT_RATE)));
			String accAddrTypeDetail1 = ConstantI.CONST_BLANK, accAddrTypeDetail2 = ConstantI.CONST_BLANK,
					accAddrTypeDetail3 = ConstantI.CONST_BLANK;
			if (ConstantI.ACCOUNT.equalsIgnoreCase(reqResp.getPayeeAddrType())) {
				accAddrTypeDetail1 = AccountDetailType.IFSC.toString() + ConstantI.CONST_SPCL_PIPE
						+ reqResp.getPayeeIfsc().toUpperCase();
				accAddrTypeDetail2 = AccountDetailType.ACTYPE.toString() + ConstantI.CONST_SPCL_PIPE
						+ reqResp.getPayeeAcType();
				accAddrTypeDetail3 = AccountDetailType.ACNUM.toString() + ConstantI.CONST_SPCL_PIPE
						+ reqResp.getPayeeAcNum();
			} else if (ConstantI.MOBILE.equalsIgnoreCase(reqResp.getPayeeAddrType())) {
				accAddrTypeDetail1 = AccountDetailType.MMID.toString() + ConstantI.CONST_SPCL_PIPE
						+ reqResp.getPayeeMmid();
				accAddrTypeDetail3 = AccountDetailType.MOBNUM + ConstantI.CONST_SPCL_PIPE
						+ (reqResp.getPayeeMobileNo() == null ? reqResp.getPayeeDeviceMobile()
								: reqResp.getPayeeMobileNo());
			}
			dbPayee.setAcAddrTypeDetail1(accAddrTypeDetail1);
			dbPayee.setAcAddrTypeDetail2(accAddrTypeDetail2);
			dbPayee.setAcAddrTypeDetail3(accAddrTypeDetail3);
			dbPayee.setAmountVal(reqResp.getPayeeAmount());
			reqRespPayCollectPayeesRepo.save(dbPayee);
		} catch (Exception e) {
			log.error("error {}", e);
			ErrorLog.sendError(e, ReqRespPayCollectPayeesDaoImpl.class);
		}

	}

}
