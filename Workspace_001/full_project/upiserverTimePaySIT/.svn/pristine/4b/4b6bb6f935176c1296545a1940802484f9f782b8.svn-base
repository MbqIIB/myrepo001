package com.npst.upiserver.dao.impl;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.ReqRespOtpDao;
import com.npst.upiserver.entity.ReqRespOtp;
import com.npst.upiserver.npcischema.AccountType.Detail;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.DeviceTagNameType;
import com.npst.upiserver.npcischema.DeviceType.Tag;
import com.npst.upiserver.npcischema.ReqOtp;
import com.npst.upiserver.npcischema.RespOtp;
import com.npst.upiserver.repo.ReqRespOtpRepository;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.JsonMan;

@Component
public class ReqRespOTPDaoImpl implements ReqRespOtpDao {
	private static final Logger log = LoggerFactory.getLogger(ReqRespOTPDaoImpl.class);
	
	@Autowired
	ReqRespOtpRepository reqRespOtpRepo;
	
	@Override
	public void insertReqResp(ReqOtp reqOtp, RespOtp respOtp, Ack ack, Date reqDate) {
		try {

			ReqRespOtp reqrespotp = new ReqRespOtp();
			reqrespotp.setReqHeadTs(reqOtp.getHead().getTs());
			reqrespotp.setReqHeadOrgId(reqOtp.getHead().getOrgId());
			reqrespotp.setReqHeadMsgId(reqOtp.getHead().getMsgId());
			
			reqrespotp.setRespHeadTs(respOtp.getHead().getTs());
			reqrespotp.setRespHeadOrgId(respOtp.getHead().getOrgId());
			reqrespotp.setRespHeadMsgId(respOtp.getHead().getMsgId());
			
			reqrespotp.setTxnId(reqOtp.getTxn().getId());
			reqrespotp.setTxnIdPrf(reqOtp.getTxn().getId().substring(0, 3));
			reqrespotp.setTxnNote(reqOtp.getTxn().getNote());
			reqrespotp.setTxnRefid(reqOtp.getTxn().getRefId());
			reqrespotp.setTxnRefurl(reqOtp.getTxn().getRefUrl());
			reqrespotp.setTxnTs(reqOtp.getTxn().getTs());
			reqrespotp.setTxnType(reqOtp.getTxn().getType().value());
			
			reqrespotp.setPayerAddr(reqOtp.getPayer().getAddr());
			reqrespotp.setPayerName(reqOtp.getPayer().getName());
			reqrespotp.setPayerSeqNum(reqOtp.getPayer().getSeqNum());
			reqrespotp.setPayerType(reqOtp.getPayer().getType().value());
			reqrespotp.setPayerCode(reqOtp.getPayer().getCode());
			
			if (null != reqOtp.getPayer().getAc()) {
				List<Detail> acDetails = reqOtp.getPayer().getAc().getDetail();
				reqrespotp.setAcAddrType(reqOtp.getPayer().getAc().getAddrType().value());
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
				reqrespotp.setAcAddrTypeDetail1(acAddrTypeDetail1);
				reqrespotp.setAcAddrTypeDetail2(AcAddrTypeDetail2);
				reqrespotp.setAcAddrTypeDetail3(AcAddrTypeDetail3);
			}
			
			if (null != reqOtp.getPayer().getDevice()) {
				List<Tag> deviceTag = reqOtp.getPayer().getDevice().getTag();
				
				for (int i = 0; i < deviceTag.size(); i++) {
					if (DeviceTagNameType.APP.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
						reqrespotp.setDeviceApp(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.CAPABILITY.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
						reqrespotp.setDeviceCapability(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.GEOCODE.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
						reqrespotp.setDeviceGeocode(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.ID.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
						reqrespotp.setDeviceId(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.IP.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
						reqrespotp.setDeviceIp(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.LOCATION.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
						reqrespotp.setDeviceLocation(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.MOBILE.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
						reqrespotp.setDeviceMobile(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.OS.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
						reqrespotp.setDeviceOs(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.TYPE.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
						reqrespotp.setDeviceType(deviceTag.get(i).getValue());
					}
				}
			}
			reqrespotp.setReqInsertDate(reqDate);
			reqrespotp.setResInsertDate(new Date());
			reqrespotp.setRespResult(respOtp.getResp().getResult());
			reqrespotp.setRespErrCode(respOtp.getResp().getErrCode());
			reqrespotp.setAckResp(JsonMan.getJSONStr(ack));
			reqRespOtpRepo.save(reqrespotp);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	@Override
	public void updateResp(RespOtp respOtp) {
		try {
			ReqRespOtp reqrespotp = getByTxnId(respOtp.getTxn().getId());
			if (reqrespotp == null) {
				return;
			}
			reqrespotp.setRespHeadTs(respOtp.getHead().getTs());
			reqrespotp.setRespHeadOrgId(respOtp.getHead().getOrgId());
			reqrespotp.setRespHeadMsgId(respOtp.getHead().getMsgId());
			reqrespotp.setResInsertDate(new Date());
			reqrespotp.setRespResult(respOtp.getResp().getResult());
			reqrespotp.setRespErrCode(respOtp.getResp().getErrCode());
			reqRespOtpRepo.save(reqrespotp);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	@Override
	public void insertReq(ReqOtp reqOtp, Ack ack) {
		try {
			ReqRespOtp reqrespotp = new ReqRespOtp();
			reqrespotp.setReqHeadTs(reqOtp.getHead().getTs());
			reqrespotp.setReqHeadOrgId(reqOtp.getHead().getOrgId());
			reqrespotp.setReqHeadMsgId(reqOtp.getHead().getMsgId());

			reqrespotp.setTxnId(reqOtp.getTxn().getId());
			reqrespotp.setTxnIdPrf(reqOtp.getTxn().getId().substring(0, 3));
			reqrespotp.setTxnNote(reqOtp.getTxn().getNote());
			reqrespotp.setTxnRefid(reqOtp.getTxn().getRefId());
			reqrespotp.setTxnRefurl(reqOtp.getTxn().getRefUrl());
			reqrespotp.setTxnTs(reqOtp.getTxn().getTs());
			reqrespotp.setTxnType(reqOtp.getTxn().getType().value());

			reqrespotp.setPayerAddr(reqOtp.getPayer().getAddr());
			reqrespotp.setPayerName(reqOtp.getPayer().getName());
			reqrespotp.setPayerSeqNum(reqOtp.getPayer().getSeqNum());
			reqrespotp.setPayerType(reqOtp.getPayer().getType().value());
			reqrespotp.setPayerCode(reqOtp.getPayer().getCode());

			if (null != reqOtp.getPayer().getAc()) {
				List<Detail> acDetails = reqOtp.getPayer().getAc().getDetail();
				reqrespotp.setAcAddrType(reqOtp.getPayer().getAc().getAddrType().value());
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
				reqrespotp.setAcAddrTypeDetail1(acAddrTypeDetail1);
				reqrespotp.setAcAddrTypeDetail2(AcAddrTypeDetail2);
				reqrespotp.setAcAddrTypeDetail3(AcAddrTypeDetail3);
			}

			if (null != reqOtp.getPayer().getDevice()) {
				List<Tag> deviceTag = reqOtp.getPayer().getDevice().getTag();

				for (int i = 0; i < deviceTag.size(); i++) {
					if (DeviceTagNameType.APP.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
						reqrespotp.setDeviceApp(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.CAPABILITY.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
						reqrespotp.setDeviceCapability(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.GEOCODE.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
						reqrespotp.setDeviceGeocode(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.ID.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
						reqrespotp.setDeviceId(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.IP.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
						reqrespotp.setDeviceIp(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.LOCATION.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
						reqrespotp.setDeviceLocation(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.MOBILE.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
						reqrespotp.setDeviceMobile(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.OS.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
						reqrespotp.setDeviceOs(deviceTag.get(i).getValue());
					}
					if (DeviceTagNameType.TYPE.value().equalsIgnoreCase(deviceTag.get(i).getName().value())) {
						reqrespotp.setDeviceType(deviceTag.get(i).getValue());
					}
				}
			}
			reqrespotp.setReqInsertDate(new Date());
			if (null != ack) {
				reqrespotp.setAckReq(JsonMan.getJSONStr(ack));
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private ReqRespOtp getByTxnId(String txnId) {
		try {
			List<ReqRespOtp> list = reqRespOtpRepo.findByTxnId(txnId);
			if (list.size() == 1) {
				return list.get(0);
			} else if (list.size() == 0) {
				log.warn("No ReqRespOtpEntity found for txnId={}", txnId);
			} else {
				log.warn("Found more than one ReqRespOtpEntity for txnId={}", txnId);
			}
		} catch (Exception e) {
			log.error("error {}", e);
			ErrorLog.sendError(e, ReqRespOTPDaoImpl.class);
		}
		return null;
	}
}
