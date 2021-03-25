package com.npst.upiserver.dao.impl;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.ReqRespBalEnqDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.ReqRespBal;
import com.npst.upiserver.npcischema.AccountType.Detail;
import com.npst.upiserver.npcischema.AccountDetailType;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.CredType;
import com.npst.upiserver.npcischema.CredsType;
import com.npst.upiserver.npcischema.DeviceTagNameType;
import com.npst.upiserver.npcischema.DeviceType.Tag;
import com.npst.upiserver.npcischema.ReqBalEnq;
import com.npst.upiserver.npcischema.RespBalEnq;
import com.npst.upiserver.repo.ReqRespBalEnqRepository;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.JsonMan;

@Component
public class ReqRespBalEnqDaoImpl implements ReqRespBalEnqDao {

	private static final Logger log = LoggerFactory.getLogger(ReqRespBalEnqDaoImpl.class);
	
	@Autowired
	ReqRespBalEnqRepository reqRespBalEnqRepo;
	
	@Override
	public void insertReqResp(ReqBalEnq reqBalEnq, RespBalEnq respBalEnq, Ack ack) {
		log.trace("");
		try {
			ReqRespBal reqrespbal = new ReqRespBal();
			reqrespbal.setReqHeadMsgId(reqBalEnq.getHead().getMsgId());
			reqrespbal.setReqHeadOrgId(reqBalEnq.getHead().getOrgId());
			reqrespbal.setReqHeadTs(reqBalEnq.getHead().getTs());

			reqrespbal.setTxnId(reqBalEnq.getTxn().getId());
			reqrespbal.setTxnIdPrf(reqBalEnq.getTxn().getId().substring(0, 3));
			reqrespbal.setTxnNote(reqBalEnq.getTxn().getNote());
			reqrespbal.setTxnRefid(reqBalEnq.getTxn().getRefId());
			reqrespbal.setTxnRefurl(reqBalEnq.getTxn().getRefUrl());
			reqrespbal.setTxnTs(reqBalEnq.getTxn().getTs());
			reqrespbal.setTxnType(reqBalEnq.getTxn().getType().value());

			reqrespbal.setPayerAddr(reqBalEnq.getPayer().getAddr());
			reqrespbal.setPayerCode(reqBalEnq.getPayer().getCode());
			reqrespbal.setPayerName(reqBalEnq.getPayer().getName());
			reqrespbal.setPayerSeqNum(Integer.parseInt(reqBalEnq.getPayer().getSeqNum()));
			reqrespbal.setPayerType(reqBalEnq.getPayer().getType().value());

			reqrespbal.setAcAddrType(reqBalEnq.getPayer().getAc().getAddrType().value());
			List<Detail> acDetails = reqBalEnq.getPayer().getAc().getDetail();

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
			reqrespbal.setAcAddrTypeDetail1(acAddrTypeDetail1);
			reqrespbal.setAcAddrTypeDetail2(AcAddrTypeDetail2);
			reqrespbal.setAcAddrTypeDetail3(AcAddrTypeDetail3);

			reqrespbal.setCredSubType(reqBalEnq.getPayer().getCreds().getCred().get(0).getSubType().value());
			reqrespbal.setCredType(reqBalEnq.getPayer().getCreds().getCred().get(0).getType().value());

			List<Tag> deviceTag = reqBalEnq.getPayer().getDevice().getTag();
			for (int i = 0; i < deviceTag.size(); i++) {
				if (DeviceTagNameType.APP.value().equals(deviceTag.get(i).getName().value())) {
					reqrespbal.setDeviceApp(deviceTag.get(i).getValue());
				}
				if (DeviceTagNameType.CAPABILITY.value().equals(deviceTag.get(i).getName().value())) {
					reqrespbal.setDeviceCapability(deviceTag.get(i).getValue());
				}
				if (DeviceTagNameType.GEOCODE.value().equals(deviceTag.get(i).getName().value())) {
					reqrespbal.setDeviceGeocode(deviceTag.get(i).getValue());
				}
				if (DeviceTagNameType.ID.value().equals(deviceTag.get(i).getName().value())) {
					reqrespbal.setDeviceId(deviceTag.get(i).getValue());
				}
				if (DeviceTagNameType.IP.value().equals(deviceTag.get(i).getName().value())) {
					reqrespbal.setDeviceIp(deviceTag.get(i).getValue());
				}
				if (DeviceTagNameType.LOCATION.value().equals(deviceTag.get(i).getName().value())) {
					reqrespbal.setDeviceLocation(deviceTag.get(i).getValue());
				}
				if (DeviceTagNameType.MOBILE.value().equals(deviceTag.get(i).getName().value())) {
					reqrespbal.setDeviceMobile(deviceTag.get(i).getValue());
				}
				if (DeviceTagNameType.OS.value().equals(deviceTag.get(i).getName().value())) {
					reqrespbal.setDeviceOs(deviceTag.get(i).getValue());
				}
				if (DeviceTagNameType.TYPE.value().equals(deviceTag.get(i).getName().value())) {
					reqrespbal.setDeviceType(deviceTag.get(i).getValue());
				}
			}
			reqrespbal.setInfoId(reqBalEnq.getPayer().getInfo().getIdentity().getId());
			if (null != reqBalEnq.getPayer().getInfo().getRating()) {
				reqrespbal.setInfoIdRatingvaddr(reqBalEnq.getPayer().getInfo().getRating().getVerifiedAddress().value());
			}
			reqrespbal.setInfoIdType(reqBalEnq.getPayer().getInfo().getIdentity().getType().value());
			reqrespbal.setInfoIdVerifiedName(reqBalEnq.getPayer().getInfo().getIdentity().getVerifiedName());
			reqrespbal.setReqInsertDate(new Date());
			reqrespbal.setRespErrCode(respBalEnq.getResp().getErrCode());
			reqrespbal.setRespResult(respBalEnq.getResp().getResult());
			reqrespbal.setResInsertDate(new Date());
			reqrespbal.setRespBalData(respBalEnq.getPayer().getBal().getData().getValue());
			reqrespbal.setRespHeadMsgId(respBalEnq.getHead().getMsgId());
			reqrespbal.setRespHeadOrgId(respBalEnq.getHead().getOrgId());
			reqrespbal.setRespHeadTs(respBalEnq.getHead().getTs());
			try {
				reqrespbal.setAckResp(JsonMan.getJSONStr(ack));
				reqRespBalEnqRepo.save(reqrespbal);
			} catch (Exception ee) {
				log.error(ee.getMessage(),ee);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	@Override
	public void updateResp(RespBalEnq respBalEnq) {
		try {
			ReqRespBal reqrespbal = getByTxnId(respBalEnq.getTxn().getId());
			if (null == reqrespbal) {
				log.info("reqrespbal not found for txnId={} ",respBalEnq.getTxn().getId());
				return ;
			}
			reqrespbal.setRespHeadTs(respBalEnq.getHead().getTs());
			reqrespbal.setRespHeadOrgId(respBalEnq.getHead().getOrgId());
			reqrespbal.setRespHeadMsgId(respBalEnq.getHead().getMsgId());
			reqrespbal.setRespBalData(respBalEnq.getPayer().getBal().getData().getValue());
			reqrespbal.setResInsertDate(new Date());
			reqrespbal.setRespResult(respBalEnq.getResp().getResult());
			reqrespbal.setRespErrCode(respBalEnq.getResp().getErrCode());
			reqRespBalEnqRepo.save(reqrespbal);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	private ReqRespBal getByTxnId(String txnId) {
		// TODO Auto-generated method stub
		try {
			List<ReqRespBal> list = reqRespBalEnqRepo.findByTxnId(txnId);
			if (list.size() == 1) {
				return list.get(0);
			} else if (list.size() == 0) {
				log.warn("ReqRespBalEnqEntity not found for txnId={}", txnId);
			} else {
				log.warn("More than one ReqRespBalEnqEntity found for txnId={}", txnId);
			}
		} catch (Exception e) {
			log.error("error {}", e);
			ErrorLog.sendError(e, ReqRespBalEnqDaoImpl.class);
		}
		return null;
	}
	@Override
	public void insertReq(ReqBalEnq reqBalEnq, Ack ack) {
		try {
			ReqRespBal reqrespbal = new ReqRespBal();
			reqrespbal.setReqHeadMsgId(reqBalEnq.getHead().getMsgId());
			reqrespbal.setReqHeadOrgId(reqBalEnq.getHead().getOrgId());
			reqrespbal.setReqHeadTs(reqBalEnq.getHead().getTs());

			reqrespbal.setTxnId(reqBalEnq.getTxn().getId());
			reqrespbal.setTxnIdPrf(reqBalEnq.getTxn().getId().substring(0, 3));
			reqrespbal.setTxnNote(reqBalEnq.getTxn().getNote());
			reqrespbal.setTxnRefid(reqBalEnq.getTxn().getRefId());
			reqrespbal.setTxnRefurl(reqBalEnq.getTxn().getRefUrl());
			reqrespbal.setTxnTs(reqBalEnq.getTxn().getTs());
			reqrespbal.setTxnType(reqBalEnq.getTxn().getType().value());

			reqrespbal.setPayerAddr(reqBalEnq.getPayer().getAddr());
			reqrespbal.setPayerCode(reqBalEnq.getPayer().getCode());
			reqrespbal.setPayerName(reqBalEnq.getPayer().getName());
			reqrespbal.setPayerSeqNum(Integer.parseInt(reqBalEnq.getPayer().getSeqNum()));
			reqrespbal.setPayerType(reqBalEnq.getPayer().getType().value());
			reqrespbal.setPayerHandal(
					reqBalEnq.getPayer().getAddr().substring(reqBalEnq.getPayer().getAddr().indexOf(ConstantI.CONST_SPCL_AT_RATE)));

			reqrespbal.setAcAddrType(reqBalEnq.getPayer().getAc().getAddrType().value());
			List<Detail> acDetails = reqBalEnq.getPayer().getAc().getDetail();

			String acAddrTypeDetail1 = ConstantI.CONST_BLANK, AcAddrTypeDetail2 =  ConstantI.CONST_BLANK, AcAddrTypeDetail3 =  ConstantI.CONST_BLANK;
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
			reqrespbal.setAcAddrTypeDetail1(acAddrTypeDetail1);
			reqrespbal.setAcAddrTypeDetail2(AcAddrTypeDetail2);
			reqrespbal.setAcAddrTypeDetail3(AcAddrTypeDetail3);

			List<CredsType.Cred> creadsList = reqBalEnq.getPayer().getCreds().getCred();
			String creadsSubType = ConstantI.CONST_BLANK, creadsType = ConstantI.CONST_BLANK;
			for (int i = 0; i < creadsList.size(); i++) {
				creadsSubType += creadsList.get(i).getSubType().value() + ConstantI.CONST_SPCL_PIPE;
				creadsType += creadsList.get(i).getType().value() + ConstantI.CONST_SPCL_PIPE;
			}

			reqrespbal.setCredSubType(creadsSubType);
			reqrespbal.setCredType(creadsType);

			List<Tag> deviceTag = reqBalEnq.getPayer().getDevice().getTag();
			for (int i = 0; i < deviceTag.size(); i++) {
				if (DeviceTagNameType.APP.value().equals(deviceTag.get(i).getName().value())) {
					reqrespbal.setDeviceApp(deviceTag.get(i).getValue());
				}
				if (DeviceTagNameType.CAPABILITY.value().equals(deviceTag.get(i).getName().value())) {
					reqrespbal.setDeviceCapability(deviceTag.get(i).getValue());
				}
				if (DeviceTagNameType.GEOCODE.value().equals(deviceTag.get(i).getName().value())) {
					reqrespbal.setDeviceGeocode(deviceTag.get(i).getValue());
				}
				if (DeviceTagNameType.ID.value().equals(deviceTag.get(i).getName().value())) {
					reqrespbal.setDeviceId(deviceTag.get(i).getValue());
				}
				if (DeviceTagNameType.IP.value().equals(deviceTag.get(i).getName().value())) {
					reqrespbal.setDeviceIp(deviceTag.get(i).getValue());
				}
				if (DeviceTagNameType.LOCATION.value().equals(deviceTag.get(i).getName().value())) {
					reqrespbal.setDeviceLocation(deviceTag.get(i).getValue());
				}
				if (DeviceTagNameType.MOBILE.value().equals(deviceTag.get(i).getName().value())) {
					reqrespbal.setDeviceMobile(deviceTag.get(i).getValue());
				}
				if (DeviceTagNameType.OS.value().equals(deviceTag.get(i).getName().value())) {
					reqrespbal.setDeviceOs(deviceTag.get(i).getValue());
				}
				if (DeviceTagNameType.TYPE.value().equals(deviceTag.get(i).getName().value())) {
					reqrespbal.setDeviceType(deviceTag.get(i).getValue());
				}
			}
			reqrespbal.setInfoId(reqBalEnq.getPayer().getInfo().getIdentity().getId());
			reqrespbal.setInfoIdRatingvaddr(reqBalEnq.getPayer().getInfo().getRating().getVerifiedAddress().value());
			reqrespbal.setInfoIdType(reqBalEnq.getPayer().getInfo().getIdentity().getType().value());
			reqrespbal.setInfoIdVerifiedName(reqBalEnq.getPayer().getInfo().getIdentity().getVerifiedName());
			reqrespbal.setReqInsertDate(new Date());
			if (null !=ack && null != ack.getErr() || null != ack.getErrorMessages()) {
				reqrespbal.setAckReq(ack.getErr());
			}
			reqRespBalEnqRepo.save(reqrespbal);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		
	}

	@Override
	public void insertReqRespOnus(ReqResp reqResp) {
		try {
			ReqRespBal reqrespbal = new ReqRespBal();
			reqrespbal.setTxnId(reqResp.getTxnId());
			reqrespbal.setTxnIdPrf(reqResp.getTxnId().substring(0, 3));
			reqrespbal.setTxnNote(reqResp.getTxnNote());
			reqrespbal.setTxnType(Constant.ONUS_BALANCE);
			reqrespbal.setPayerAddr(reqResp.getPayerAddr());
			reqrespbal.setPayerCode(reqResp.getPayerCode());

			reqrespbal.setAcAddrType(reqResp.getPayerAddrType());
			reqrespbal.setAcAddrTypeDetail1(AccountDetailType.IFSC.toString() + ConstantI.CONST_SPCL_PIPE
					+ reqResp.getPayerIfsc().toUpperCase());
			reqrespbal.setAcAddrTypeDetail2(
					AccountDetailType.ACTYPE.toString() + ConstantI.CONST_SPCL_PIPE + reqResp.getPayerAcType());
			reqrespbal.setAcAddrTypeDetail3(
					AccountDetailType.ACNUM.toString() + ConstantI.CONST_SPCL_PIPE + reqResp.getPayerAcNum());
			reqrespbal.setCredType(CredType.PRE_APPROVED.toString());
			reqrespbal.setDeviceGeocode(reqResp.getPayerDeviceGeoCode());
			reqrespbal.setDeviceIp(reqResp.getPayerDeviceIp());
			reqrespbal.setDeviceLocation(reqResp.getPayerDeviceLocation());
			reqrespbal.setDeviceMobile(reqResp.getPayerDeviceMobile());
			reqrespbal.setDeviceOs(reqResp.getPayerDeviceOsType());
			reqrespbal.setReqInsertDate(new Date());
			reqrespbal.setRespErrCode(reqResp.getRespCode());
			reqrespbal.setResInsertDate(new Date());
			reqrespbal.setRespBalData(reqResp.getRespBal());
			if (ConstantI.CODE_SUCCESS.equals(reqResp.getRespCode())) {
				reqrespbal.setRespResult(ConstantI.SUCCESS);
			} else {
				reqrespbal.setRespResult(ConstantI.FAILURE);
			}
			reqRespBalEnqRepo.save(reqrespbal);
		} catch (Exception e) {
			log.error("error {}", e);
			ErrorLog.sendError(e, ReqRespBalEnqDaoImpl.class);
		}
		
	}
}
