package com.npst.upiserver.issuer.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.ReqRespOtpDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.issuer.service.UpiReqOtpService;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.DeviceType;
import com.npst.upiserver.npcischema.DeviceType.Tag;
import com.npst.upiserver.npcischema.HeadType;
import com.npst.upiserver.npcischema.PayConstant;
import com.npst.upiserver.npcischema.ReqOtp;
import com.npst.upiserver.npcischema.RespOtp;
import com.npst.upiserver.npcischema.RespType;
import com.npst.upiserver.service.IdGeneratorService;
import com.npst.upiserver.service.impl.MiddlewareRestConServiceImpl;
import com.npst.upiserver.service.impl.NpciUpiRestConServiceImpl;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.Util;

@Service
public class UpiReqOtpServiceImpl implements UpiReqOtpService {

	private static final Logger log = LoggerFactory.getLogger(UpiReqOtpServiceImpl.class);

	@Autowired
	ReqRespOtpDao reqRespOtpDao;
	
	@Autowired
	MiddlewareRestConServiceImpl  middlewareService;
	
	@Autowired
	NpciUpiRestConServiceImpl  npciResponseService;
	
	@Autowired
	private IdGeneratorService idGeneratorService;
	
	@Override
	public void issuerProcess(final ReqOtp reqOtp) {
		log.info("reqOtp received from NPCI  {}", reqOtp);
		try {
			try {
				Date reqDate = new Date();
				String ts = Util.getTS();
				String msgId = Util.uuidGen();
				RespOtp respOtp = new RespOtp();
				respOtp.setHead(setHeadTypeDetails(msgId, ts));
				RespType resp = new RespType();
				resp.setReqMsgId(reqOtp.getHead().getMsgId());
				if(Util.getProperty("HTTPS_URL")!=null && !Util.getProperty("HTTPS_URL").equalsIgnoreCase("NA")) {
					resp.setSecurePinUrl(Util.getProperty("HTTPS_URL")); // to be removed
				}
				
				
				try {
					if (null == reqOtp.getTxn().getType()) {
						reqOtp.getTxn().setType(PayConstant.OTP);
					}
					ReqResp req = new ReqResp();
				//	if(checkUSDCDevice(reqOtp))
					if(true) {
						req.setRrn(idGeneratorService.getRrn());
						req.setTxnId(reqOtp.getTxn().getId());
						req.setBkPrf(reqOtp.getTxn().getId().substring(0, 3));
						req.setTxnType(reqOtp.getTxn().getType().value());
						req.setPayerAddr(reqOtp.getPayer().getAddr());
						List<Tag> tags = reqOtp.getPayer().getDevice().getTag();
						for (Tag tag : tags) {
							if (ConstantI.MOBILE.equalsIgnoreCase(tag.getName().value())) {
								req.setPayerDeviceMobile(tag.getValue());
							}
							if (ConstantI.TYPE.equalsIgnoreCase(tag.getName().value())) {
								req.setPayerDeviceType(tag.getValue());
							}
						}
						req.setIsUPI2("1");
						req = middlewareService.send(req);
						req.setRespCode(ConstantI.CONSTANT_0);
						if (ConstantI.CONSTANT_0.equalsIgnoreCase(req.getRespCode())) {
							resp.setResult(ConstantI.SUCCESS);
						} else {
							resp.setErrCode(req.getRespCode());
							resp.setResult(ConstantI.FAILURE);
						}
					}
					else {
						// If not getting telecom Tag
						resp.setResult(ConstantI.FAILURE);
						resp.setErrCode(ConstantI.XB);
					}
				} catch (Exception e) {
					log.error(e.getMessage(),e);
					resp.setErrCode(ConstantI.RESP_ZD);
					resp.setResult(ConstantI.FAILURE);
				}
				respOtp.setResp(resp);
				respOtp.setTxn(reqOtp.getTxn());
				String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(respOtp).toString());
				log.info("XML: {} ",xmlStr);
				Ack ack = npciResponseService.send(xmlStr, ConstantI.API_RESP_OTP, reqOtp.getTxn().getId());
				try {
					if (!Constant.bKPrf.equalsIgnoreCase(reqOtp.getTxn().getId().substring(0, 3))) {
						reqRespOtpDao.insertReqResp(reqOtp, respOtp, ack, reqDate);
					}
				} catch (Exception e) {
					log.error(e.getMessage(),e);
				}
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private boolean checkUSDCDevice(final ReqOtp reqOtp) throws Exception {
		boolean f = false;
		boolean f2 = false;

		DeviceType deviceType = reqOtp.getPayer().getDevice();
		if (deviceType == null) {
			return true;
		}
		for (Tag tag : deviceType.getTag()) {
			if (ConstantI.TYPE.equalsIgnoreCase(tag.getName().name().toString())) {
				if (ConstantI.USDC.equalsIgnoreCase(tag.getValue())) {
					f = true;
				}
			}
			if (f && ConstantI.TELECOM.equalsIgnoreCase(tag.getName().name()) && !tag.getValue().equalsIgnoreCase("")) {
				f2 = true;
				break;
			}
		}
		if (f) {
			return f2;
		} else {
			return true;
		}
	}
	
	private HeadType setHeadTypeDetails(final String msgId, final String ts) throws Exception {
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
