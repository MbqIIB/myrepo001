package com.npst.upiserver.dao.impl;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.ReqRespRegMobDao;
import com.npst.upiserver.entity.ReqRespRegMob;
import com.npst.upiserver.npcischema.AccountType.Detail;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.CredsType;
import com.npst.upiserver.npcischema.DeviceTagNameType;
import com.npst.upiserver.npcischema.DeviceType.Tag;
import com.npst.upiserver.npcischema.ReqRegMob;
import com.npst.upiserver.npcischema.RespRegMob;
import com.npst.upiserver.repo.ReqRespRegMobRepository;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.JsonMan;

@Component
public class ReqRespRegMobDaoImpl implements ReqRespRegMobDao{

	private static final Logger log = LoggerFactory.getLogger(ReqRespRegMobDaoImpl.class);
	
	@Autowired
	ReqRespRegMobRepository reqRespRegMob;
	
	@Override
	public void insertReqResp(ReqRegMob reqRegMob, RespRegMob respRegMob, Ack ack, Date reqDate) {
		try {
			ReqRespRegMob reqrespregmob = new ReqRespRegMob();
			reqrespregmob.setReqHeadMsgId(reqRegMob.getHead().getMsgId());
			reqrespregmob.setReqHeadOrgId(reqRegMob.getHead().getOrgId());
			reqrespregmob.setReqHeadTs(reqRegMob.getHead().getTs());
			
			reqrespregmob.setRespHeadMsgId(respRegMob.getHead().getMsgId());
			reqrespregmob.setRespHeadOrgId(respRegMob.getHead().getOrgId());
			reqrespregmob.setRespHeadTs(respRegMob.getHead().getTs());
			
			reqrespregmob.setTxnId(reqRegMob.getTxn().getId());
			reqrespregmob.setTxnIdPrf(reqRegMob.getTxn().getId().substring(0, 3));
			reqrespregmob.setTxnNote(reqRegMob.getTxn().getNote());
			reqrespregmob.setTxnRefid(reqRegMob.getTxn().getRefId());
			reqrespregmob.setTxnRefurl(reqRegMob.getTxn().getRefUrl());
			reqrespregmob.setTxnTs(reqRegMob.getTxn().getTs());
			reqrespregmob.setTxnType(reqRegMob.getTxn().getType().value());
			
			reqrespregmob.setPayerAddr(reqRegMob.getPayer().getAddr());
			reqrespregmob.setPayerCode(reqRegMob.getPayer().getCode());
			reqrespregmob.setPayerSeqNum(reqRegMob.getPayer().getSeqNum());
			reqrespregmob.setPayerType(reqRegMob.getPayer().getType().value());
			
			List<Tag> deviceTag = reqRegMob.getPayer().getDevice().getTag();
			for (int i = 0; i < deviceTag.size(); i++) {
				if (DeviceTagNameType.APP.value().equals(deviceTag.get(i).getName().value())) {
					reqrespregmob.setDeviceApp(deviceTag.get(i).getValue());
				}
				if (DeviceTagNameType.CAPABILITY.value().equals(deviceTag.get(i).getName().value())) {
					reqrespregmob.setDeviceCapability(deviceTag.get(i).getValue());
				}
				if (DeviceTagNameType.GEOCODE.value().equals(deviceTag.get(i).getName().value())) {
					reqrespregmob.setDeviceGeocode(deviceTag.get(i).getValue());
				}
				if (DeviceTagNameType.ID.value().equals(deviceTag.get(i).getName().value())) {
					reqrespregmob.setDeviceId(deviceTag.get(i).getValue());
				}
				if (DeviceTagNameType.IP.value().equals(deviceTag.get(i).getName().value())) {
					reqrespregmob.setDeviceIp(deviceTag.get(i).getValue());
				}
				if (DeviceTagNameType.LOCATION.value().equals(deviceTag.get(i).getName().value())) {
					reqrespregmob.setDeviceLocation(deviceTag.get(i).getValue());
				}
				if (DeviceTagNameType.MOBILE.value().equals(deviceTag.get(i).getName().value())) {
					reqrespregmob.setDeviceMobile(deviceTag.get(i).getValue());
				}
				if (DeviceTagNameType.OS.value().equals(deviceTag.get(i).getName().value())) {
					reqrespregmob.setDeviceOs(deviceTag.get(i).getValue());
				}
				if (DeviceTagNameType.TYPE.value().equals(deviceTag.get(i).getName().value())) {
					reqrespregmob.setDeviceType(deviceTag.get(i).getValue());
				}
			}
			List<Detail> acDetails = reqRegMob.getPayer().getAc().getDetail();
			String acAddrTypeDetail1 = ConstantI.CONST_BLANK, AcAddrTypeDetail2 = ConstantI.CONST_BLANK, AcAddrTypeDetail3 = ConstantI.CONST_BLANK;
			for (int i = 0; i < acDetails.size(); i++) {
				String temp = acDetails.get(i).getName().value() + ConstantI.CONST_SPCL_EQUAL + acDetails.get(i).getValue();
				if (0 == i) {
					acAddrTypeDetail1 = temp;
				} else if (1 == i) {
					AcAddrTypeDetail2 = temp;
				} else {
					AcAddrTypeDetail3 = temp;
				}
			}
			reqrespregmob.setAcAddrTypeDetail1(acAddrTypeDetail1);
			reqrespregmob.setAcAddrTypeDetail2(AcAddrTypeDetail2);
			reqrespregmob.setAcAddrTypeDetail3(AcAddrTypeDetail3);
			
			List<CredsType.Cred> creadsList = reqRegMob.getRegDetails().getCreds().getCred();
			String creadsSubType = ConstantI.CONST_BLANK, creadsType = ConstantI.CONST_BLANK;
			for (int i = 0; i < creadsList.size(); i++) {
				creadsSubType += creadsList.get(i).getSubType().value() + ConstantI.CONST_SPCL_PIPE;
				creadsType += creadsList.get(i).getType().value() + ConstantI.CONST_SPCL_PIPE;
			}
			
			reqrespregmob.setCredsubType(creadsSubType);
			reqrespregmob.setCredtype(creadsType);
			
			reqrespregmob.setRegDetailstype(reqRegMob.getRegDetails().getType());
			String regDetail1 = ConstantI.CONST_BLANK, regDetail2 = ConstantI.CONST_BLANK, regDetail3 = ConstantI.CONST_BLANK;
			List<com.npst.upiserver.npcischema.ReqRegMob.RegDetails.Detail> regDetailsList = reqRegMob.getRegDetails()
					.getDetail();
			for (int i = 0; i < regDetailsList.size(); i++) {
				String temp = regDetailsList.get(i).getName().value() + ConstantI.CONST_SPCL_EQUAL + regDetailsList.get(i).getValue();
				if (0 == i) {
					regDetail1 = temp;
				} else if (1 == i) {
					regDetail2 = temp;
				} else {
					regDetail3 = temp;
				}
			}
			reqrespregmob.setRegDetail1(regDetail1);
			reqrespregmob.setRegDetail2(regDetail2);
			reqrespregmob.setRegDetail3(regDetail3);
			reqrespregmob.setReqInsertDate(reqDate);
			reqrespregmob.setResInsertDate(new Date());
			reqrespregmob.setRespResult(respRegMob.getResp().getResult());
			reqrespregmob.setRespErrCode(respRegMob.getResp().getErrCode());
			reqrespregmob.setAckResp(JsonMan.getJSONStr(ack));
			reqRespRegMob.save(reqrespregmob);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	@Override
	public void updateRespPay(RespRegMob respRegMob) {
		try {
			ReqRespRegMob reqrespregmob = getByTxnId(respRegMob.getTxn().getId());
			if (null != reqrespregmob) {
				reqrespregmob.setRespHeadMsgId(respRegMob.getHead().getMsgId());
				reqrespregmob.setRespHeadOrgId(respRegMob.getHead().getOrgId());
				reqrespregmob.setRespHeadTs(respRegMob.getHead().getTs());
				reqrespregmob.setResInsertDate(new Date());
				reqrespregmob.setRespResult(respRegMob.getResp().getResult());
				reqrespregmob.setRespErrCode(respRegMob.getResp().getErrCode());
				reqRespRegMob.save(reqrespregmob);
			}
		}
		catch (Exception e) {
		 log.error(e.getMessage(),e);
		}
	}

	private ReqRespRegMob getByTxnId(String txnId) {
		try {
			List<ReqRespRegMob> list = reqRespRegMob.findByTxnId(txnId);
			if (list.size() == 1) {
				return list.get(0);
			} else if (list.size() == 0) {
				log.warn("ReqRespRegMobEntity not found for TxnId={}", txnId);
			} else {
				log.warn("More than one ReqRespRegMobEntity found for TxnId={}", txnId);
			}
		} catch (Exception e) {
			log.error("error {}", e);
			ErrorLog.sendError(e, ReqRespRegMobDaoImpl.class);
		}
		return null;
	}
	@Override
	public void insertReq(ReqRegMob reqRegMob, Ack ack) {
		try {
			ReqRespRegMob reqrespregmob = new ReqRespRegMob();
			try {
				reqrespregmob.setReqHeadMsgId(reqRegMob.getHead().getMsgId());
			} catch(NullPointerException npe) {}
			try {
				reqrespregmob.setReqHeadOrgId(reqRegMob.getHead().getOrgId());
			} catch(NullPointerException npe) {}
			try {
				reqrespregmob.setReqHeadTs(reqRegMob.getHead().getTs());
				
			} catch(NullPointerException npe) {}
			try {
				reqrespregmob.setTxnId(reqRegMob.getTxn().getId());
			} catch(NullPointerException npe) {}
			try {
				reqrespregmob.setTxnIdPrf(reqRegMob.getTxn().getId().substring(0, 3));
			} catch(NullPointerException npe) {}
			try {
				reqrespregmob.setTxnNote(reqRegMob.getTxn().getNote());
			} catch(NullPointerException npe) {}
			try {
				reqrespregmob.setTxnRefid(reqRegMob.getTxn().getRefId());
			} catch(NullPointerException npe) {}
			try {
				reqrespregmob.setTxnRefurl(reqRegMob.getTxn().getRefUrl());
			} catch(NullPointerException npe) {}
			try {
				reqrespregmob.setTxnTs(reqRegMob.getTxn().getTs());
			} catch(NullPointerException npe) {}
			try {
				reqrespregmob.setTxnType(reqRegMob.getTxn().getType().value());
				
			} catch(NullPointerException npe) {}
			try {
				reqrespregmob.setPayerAddr(reqRegMob.getPayer().getAddr());
			} catch(NullPointerException npe) {}
			try {
				reqrespregmob.setPayerCode(reqRegMob.getPayer().getCode());
			} catch(NullPointerException npe) {}
			try {
				reqrespregmob.setPayerSeqNum(reqRegMob.getPayer().getSeqNum());
			} catch(NullPointerException npe) {}
			try {
				reqrespregmob.setPayerType(reqRegMob.getPayer().getType().value());
			} catch(NullPointerException npe) {}
			try {
				reqrespregmob.setPayerHandal(
						reqRegMob.getPayer().getAddr().substring(reqRegMob.getPayer().getAddr().indexOf(ConstantI.CONST_SPCL_AT_RATE)));
			} catch(Exception npe) {}
			List<Tag> deviceTag = reqRegMob.getPayer().getDevice().getTag();
			if (0 < deviceTag.size()) {
				for (int i = 0; i < deviceTag.size(); i++) {
					if (DeviceTagNameType.APP.value().equals(deviceTag.get(i).getName().value())) {
						reqrespregmob.setDeviceApp(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.CAPABILITY.value().equals(deviceTag.get(i).getName().value())) {
						reqrespregmob.setDeviceCapability(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.GEOCODE.value().equals(deviceTag.get(i).getName().value())) {
						reqrespregmob.setDeviceGeocode(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.ID.value().equals(deviceTag.get(i).getName().value())) {
						reqrespregmob.setDeviceId(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.IP.value().equals(deviceTag.get(i).getName().value())) {
						reqrespregmob.setDeviceIp(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.LOCATION.value().equals(deviceTag.get(i).getName().value())) {
						reqrespregmob.setDeviceLocation(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.MOBILE.value().equals(deviceTag.get(i).getName().value())) {
						reqrespregmob.setDeviceMobile(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.OS.value().equals(deviceTag.get(i).getName().value())) {
						reqrespregmob.setDeviceOs(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.TYPE.value().equals(deviceTag.get(i).getName().value())) {
						reqrespregmob.setDeviceType(deviceTag.get(i).getValue());
					}
				}
			}
			List<Detail> acDetails = reqRegMob.getPayer().getAc().getDetail();
			String acAddrTypeDetail1 = ConstantI.CONST_BLANK, AcAddrTypeDetail2 = ConstantI.CONST_BLANK, AcAddrTypeDetail3 = ConstantI.CONST_BLANK;
			if (0 < acDetails.size()) {
				for (int i = 0; i < acDetails.size(); i++) {
					String temp = acDetails.get(i).getName().value() + ConstantI.CONST_SPCL_EQUAL + acDetails.get(i).getValue();
					if (0 == i) {
						acAddrTypeDetail1 = temp;
					} else if (1 == i) {
						AcAddrTypeDetail2 = temp;
					} else {
						AcAddrTypeDetail3 = temp;
					}
				}
			}
			reqrespregmob.setAcAddrTypeDetail1(acAddrTypeDetail1);
			reqrespregmob.setAcAddrTypeDetail2(AcAddrTypeDetail2);
			reqrespregmob.setAcAddrTypeDetail3(AcAddrTypeDetail3);

			List<CredsType.Cred> creadsList = reqRegMob.getRegDetails().getCreds().getCred();
			String creadsSubType = ConstantI.CONST_BLANK, creadsType = ConstantI.CONST_BLANK;
			for (int i = 0; i < creadsList.size(); i++) {
				creadsSubType += creadsList.get(i).getSubType().value() + ConstantI.CONST_SPCL_PIPE;
				creadsType += creadsList.get(i).getType().value() + ConstantI.CONST_SPCL_PIPE;
			}

			reqrespregmob.setCredsubType(creadsSubType);
			reqrespregmob.setCredtype(creadsType);

			reqrespregmob.setRegDetailstype(reqRegMob.getRegDetails().getType());
			String regDetail1 = ConstantI.CONST_BLANK, regDetail2 = ConstantI.CONST_BLANK, regDetail3 = ConstantI.CONST_BLANK;
			List<com.npst.upiserver.npcischema.ReqRegMob.RegDetails.Detail> regDetailsList = reqRegMob.getRegDetails()
					.getDetail();
			for (int i = 0; i < regDetailsList.size(); i++) {
				String temp = regDetailsList.get(i).getName().value() + ConstantI.CONST_SPCL_EQUAL + regDetailsList.get(i).getValue();
				if (0 == i) {
					regDetail1 = temp;
				} else if (1 == i) {
					regDetail2 = temp;
				} else {
					regDetail3 = temp;
				}
			}

			reqrespregmob.setRegDetail1(regDetail1);
			reqrespregmob.setRegDetail2(regDetail2);
			reqrespregmob.setRegDetail3(regDetail3);

			reqrespregmob.setReqInsertDate(new Date());
			if(ack!=null) {
				reqrespregmob.setAckReq(JsonMan.getJSONStr(ack));
			}
			reqRespRegMob.save(reqrespregmob);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
}
