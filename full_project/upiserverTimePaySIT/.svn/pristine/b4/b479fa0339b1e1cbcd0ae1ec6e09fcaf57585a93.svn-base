package com.npst.upiserver.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.npst.upiserver.dao.ReqRespAuthDetailsPayeesDao;
import com.npst.upiserver.entity.ReqRespAuthDetailsPayees;
import com.npst.upiserver.npcischema.AccountType.Detail;
import com.npst.upiserver.npcischema.DeviceTagNameType;
import com.npst.upiserver.npcischema.DeviceType.Tag;
import com.npst.upiserver.npcischema.PayeeType;
import com.npst.upiserver.npcischema.PayeesType;
import com.npst.upiserver.repo.ReqRespAuthDetailsPayeesRepository;
import com.npst.upiserver.util.ErrorLog;

@Component
public class ReqRespAuthDetailsPayeesDaoImpl implements ReqRespAuthDetailsPayeesDao {
	private static final Logger log = LoggerFactory.getLogger(ReqRespAuthDetailsPayeesDaoImpl.class);
	
	@Autowired
	ReqRespAuthDetailsPayeesRepository reqRespAuthDetailsPayees;
	
	@Override
	public ReqRespAuthDetailsPayees getPayees(Long idReqRespAuthDetails) {
		List<ReqRespAuthDetailsPayees> reqRespAuthDetPayees=null;
		try {
			reqRespAuthDetPayees=reqRespAuthDetailsPayees.findByIdReqRespAuthDetails(idReqRespAuthDetails);
			if(reqRespAuthDetPayees!=null && reqRespAuthDetPayees.size()>0) {
				return reqRespAuthDetPayees.get(0);
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return null;
	}

	@Override
	public ReqRespAuthDetailsPayees getPayeesByTxnId(String payeeAddr, String txnId) {
		ReqRespAuthDetailsPayees reqRespAuthDetPayees=null;
		try {
			reqRespAuthDetPayees=reqRespAuthDetailsPayees.findByPayeeAddrAndTxnId(payeeAddr,txnId);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return reqRespAuthDetPayees;
	}
	
	@Override
	public void insertPayees(PayeesType payees, String txnId, String msgId, long idReqRespAuthDetails) {
		try {
			if (null != payees) {
				List<PayeeType> payeeList = payees.getPayee();
				for (PayeeType payeeType : payeeList) {
					ReqRespAuthDetailsPayees dbPayee = new ReqRespAuthDetailsPayees();
					dbPayee.setIdReqRespAuthDetails(idReqRespAuthDetails);
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
					reqRespAuthDetailsPayees.save(dbPayee);
				}
			}
		} catch (Exception e) {
			log.error("error cause= {}", e.getMessage());
			ErrorLog.sendError(e, ReqRespAuthDetailsPayeesDaoImpl.class);
		}
		
	}

	@Override
	public ReqRespAuthDetailsPayees getByPayeesVpaAndIdReqRespAuthDetails(String payeeVpa,
			Long idReqRespAuthDetails) {
		try {
			List<ReqRespAuthDetailsPayees> list = reqRespAuthDetailsPayees.findByPayeeAddrAndIdReqRespAuthDetails(payeeVpa, idReqRespAuthDetails);
			if (list != null && list.size() > 0) {
				if (list.size() == 1) {
					return list.get(0);
				} else {
					log.error(
							"getting more than one ReqRespAuthDetailsPayeesEntity by payeeVpa={} , idReqRespAuthDetails={}",
							payeeVpa, idReqRespAuthDetails);
				}
			} else {
				log.warn(" ReqRespAuthDetailsPayeesEntity not found for payeeVpa={} ,and  idReqRespAuthDetails={}",
						payeeVpa, idReqRespAuthDetails);
			}
		} catch (Exception e) {
			log.error("error cause={}", e.getMessage());
			ErrorLog.sendError(e, ReqRespAuthDetailsPayeesDaoImpl.class);
		}
		return null;
	}
	
}
