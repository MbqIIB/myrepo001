package com.npst.upiserver.issuer.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.ReqRespRegMobDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.issuer.service.UpiReqRegMobService;
import com.npst.upiserver.npcischema.AccountType;
import com.npst.upiserver.npcischema.AccountType.Detail;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.CredsType.Cred;
import com.npst.upiserver.npcischema.DeviceType;
import com.npst.upiserver.npcischema.DeviceType.Tag;
import com.npst.upiserver.npcischema.HeadType;
import com.npst.upiserver.npcischema.ReqRegMob;
import com.npst.upiserver.npcischema.ReqRegMob.RegDetails;
import com.npst.upiserver.npcischema.RespRegMob;
import com.npst.upiserver.npcischema.RespType;
import com.npst.upiserver.service.IdGeneratorService;
import com.npst.upiserver.service.impl.MiddlewareRestConServiceImpl;
import com.npst.upiserver.service.impl.NpciUpiRestConServiceImpl;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.Util;

@Service
public class UpiReqRegMobServiceImpl implements UpiReqRegMobService {
	private static final Logger log = LoggerFactory.getLogger(UpiReqRegMobServiceImpl.class);

	@Autowired
	ReqRespRegMobDao reqRespRegMobDao;
	
	@Autowired
	MiddlewareRestConServiceImpl	middlewareService;
	
	@Autowired
	NpciUpiRestConServiceImpl		npciResponseService;
	
	@Autowired
	private IdGeneratorService idGeneratorService;
	
	@Override
	public void issuerProcess(final ReqRegMob reqRegMob) {
		log.debug("reqRegMob {}", reqRegMob);
		try {
			String ts = Util.getTS();
			String msgId = Util.uuidGen();
			Date reqDate = new Date();
			RespRegMob respRegMob = new RespRegMob();
			respRegMob.setHead(setHeadTypeDetails(msgId, ts));
			respRegMob.setTxn(reqRegMob.getTxn());
			RespType resp = new RespType();
			resp.setReqMsgId(reqRegMob.getHead().getMsgId());
			ReqResp req = new ReqResp();
			try {
				setReqRespParam(reqRegMob, req);
				req.setField11(idGeneratorService.getRrn());
				req.setAtmCardFiled11(idGeneratorService.getRrn());
				req = middlewareService.send(req);
				if (ConstantI.CODE_SUCCESS.equalsIgnoreCase(req.getRespCode())) {
					resp.setResult(ConstantI.SUCCESS);
				} else {
					resp.setResult(ConstantI.FAILURE);
					if (ConstantI.UKN.equalsIgnoreCase(req.getRespCode())) {
						resp.setErrCode(ConstantI.XB);
					} else {
						resp.setErrCode(req.getRespCode());
					}
				}
			} catch (Exception e) {
				log.error("error {}", e);
				resp.setErrCode(ConstantI.XB);
				resp.setResult(ConstantI.FAILURE);
				//ErrorLog.sendError(e, UpiReqRegMobServiceImpl.class);
			}
			respRegMob.setResp(resp);
			String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(respRegMob).toString());
			log.info("XML: {} ",xmlStr);		
			Ack ack = npciResponseService.send(xmlStr,ConstantI.API_RESP_REG_MOB, reqRegMob.getTxn().getId());
			
			if (!Constant.bKPrf.equalsIgnoreCase(reqRegMob.getTxn().getId().substring(0, 3))) {
				reqRespRegMobDao.insertReqResp(reqRegMob, respRegMob, ack, reqDate);
			}
		} catch (Exception e) {
			log.error("error  last {}", e);
			//ErrorLog.sendError(e, UpiReqRegMobServiceImpl.class);
		}
	}

	private void setReqRespParam(final ReqRegMob reqRegMob, ReqResp req) {
		req.setTxnType(reqRegMob.getTxn().getType().value());
		req.setBkPrf(reqRegMob.getTxn().getId().substring(0, 3));
		req.setPayerAddr(reqRegMob.getPayer().getAddr());
		req.setTxnId(reqRegMob.getTxn().getId());
		if (null != reqRegMob.getPayer().getDevice()) {
			for (Tag tag : reqRegMob.getPayer().getDevice().getTag()) {
				if (ConstantI.MOBILE.equalsIgnoreCase(tag.getName().value())) {
					req.setPayerDeviceMobile(tag.getValue());
					req.setDeviceMobile(tag.getValue());
				}
				if (ConstantI.TYPE.equalsIgnoreCase(tag.getName().value())) {
					req.setPayerDeviceType(tag.getValue());
				}
			}
		}
		req.setRegDetailsType(reqRegMob.getRegDetails().getType());

		List<RegDetails.Detail> details = reqRegMob.getRegDetails().getDetail();
		for (RegDetails.Detail detail : details) {
			if (ConstantI.MOBILE.equalsIgnoreCase(detail.getName().value())) {
				req.setRegMobile(detail.getValue());
			}
			if (ConstantI.CARDDIGITS.equalsIgnoreCase(detail.getName().value())) {
				req.setRegCardDigits(detail.getValue());
				req.setAtmCardFiled11(req.getRegCardDigits());
			}
			if (ConstantI.EXPDATE.equalsIgnoreCase(detail.getName().value())) {
				req.setRegExpDate(detail.getValue());
			}
		}
		req.setPayerName(reqRegMob.getPayer().getName());
		AccountType ac = reqRegMob.getPayer().getAc();
		List<Detail> detailAcs = ac.getDetail();
		req.setPayerAddrType(ac.getAddrType().value());
		for (Detail detail : detailAcs) {
			if (ConstantI.ACNUM.equalsIgnoreCase(detail.getName().value())) {
				req.setPayerAcNum(detail.getValue());
			}
			if (ConstantI.IFSC.equalsIgnoreCase(detail.getName().value())) {
				req.setPayerIfsc(detail.getValue());
			}
			if (ConstantI.ACTYPE.equalsIgnoreCase(detail.getName().value())) {
				req.setPayerAcType(detail.getValue());
			}
		}
		List<Cred> creds = reqRegMob.getRegDetails().getCreds().getCred();
		creds = reqRegMob.getRegDetails().getCreds().getCred();
		for (Cred cred : creds) {
			if (ConstantI.PIN.equalsIgnoreCase(cred.getType().value())
					&& ConstantI.ATMPIN.equalsIgnoreCase(cred.getSubType().value())) {
				req.setCredAtmpin(cred.getData().getValue());
				req.setAtmCardFiled11(idGeneratorService.getRrn());
			} else if (ConstantI.PIN.equalsIgnoreCase(cred.getType().value())
					&& ConstantI.MPIN.equalsIgnoreCase(cred.getSubType().value())) {
				req.setCredMpin(cred.getData().getValue());
			} else if (ConstantI.TYPE_OTP.equalsIgnoreCase(cred.getType().value())) {
				req.setCredOtp(cred.getData().getValue());
			} else {
				log.error("invalid credType={} Or credSubType={}", cred.getType().value(), cred.getSubType().value());
				//ErrorLog.sendError("invalid credType ie", cred.getType().value(), UpiReqRegMobServiceImpl.class);
			}
		}
	}
	private HeadType setHeadTypeDetails(final String msgId, final String ts)  throws Exception{
		HeadType head = new HeadType();
		try {
			head.setMsgId(msgId);
			head.setOrgId(Constant.orgId);
			head.setTs(ts);
			head.setVer(Constant.headVer);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return head;
	}
}
