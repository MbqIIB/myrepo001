package com.npst.upiserver.issuer.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.ReqRespSetCreDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.issuer.service.UpiReqSetCreService;
import com.npst.upiserver.npcischema.AccountType;
import com.npst.upiserver.npcischema.AccountType.Detail;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.CredsType.Cred;
import com.npst.upiserver.npcischema.DeviceType;
import com.npst.upiserver.npcischema.DeviceType.Tag;
import com.npst.upiserver.npcischema.HeadType;
import com.npst.upiserver.npcischema.ReqSetCre;
import com.npst.upiserver.npcischema.RespSetCre;
import com.npst.upiserver.npcischema.RespType;
import com.npst.upiserver.service.IdGeneratorService;
import com.npst.upiserver.service.impl.MiddlewareRestConServiceImpl;
import com.npst.upiserver.service.impl.NpciUpiRestConServiceImpl;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.Util;

@Service
public class UpiReqSetCreServiceImpl implements UpiReqSetCreService {
	private static final Logger		log	= LoggerFactory.getLogger(UpiReqSetCreServiceImpl.class);
	
	@Autowired
	ReqRespSetCreDao				reqRespSetCreDao;
	
	@Autowired
	MiddlewareRestConServiceImpl	middlewareService;
	
	@Autowired
	NpciUpiRestConServiceImpl		npciResponseService;
	
	@Autowired
	private IdGeneratorService idGeneratorService;
	
	@Override
	public void issuerProcess(final ReqSetCre reqSetCre) {
		log.debug("reqSetCre {}", reqSetCre);
		try {
			ReqResp req = new ReqResp();
			String ts = Util.getTS();
			String msgId = Util.uuidGen();
			Date reqDate = new Date();
			RespSetCre respSetCre = new RespSetCre();
			respSetCre.setHead(setHeadTypeDetails(msgId, ts));
			RespType resp = new RespType();
			resp.setReqMsgId(reqSetCre.getHead().getMsgId());
			try {
				//ReqResp req=setReqRespDetails(reqSetCre.getTxn().getId(), reqSetCre.getTxn().getType().value(), reqSetCre.getPayer().getAddr(), reqSetCre.getPayer().getCode(), reqSetCre.getPayer().getDevice().getTag(), reqSetCre.getPayer().getAc(), reqSetCre.getPayer().getCreds().getCred(), reqSetCre.getPayer().getNewCred().getCred().get(0).getData().getValue());
				setReqRespParam(reqSetCre, req);
				req.setRrn(idGeneratorService.getRrn());
				req.setTxnType("SetCre");
				if(checkUSDCDevice(reqSetCre)) {
					req = middlewareService.send(req);
				//	req.setRespCode(ConstantI.CODE_SUCCESS); // To do 
					if (ConstantI.CODE_SUCCESS.equals(req.getRespCode())) {
						resp.setResult(ConstantI.SUCCESS);
					} else {
						resp.setResult(ConstantI.FAILURE);
						resp.setErrCode(req.getRespCode());
						if (ConstantI.UKN.equalsIgnoreCase(resp.getErrCode())) {
							resp.setErrCode(ConstantI.XB);
						}
					}
				}
				else {
					resp.setResult(ConstantI.FAILURE);
					resp.setErrCode(ConstantI.XB);
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				resp.setResult(ConstantI.FAILURE);
				resp.setErrCode(ConstantI.RESP_ZD);
				ErrorLog.sendError(e, UpiReqSetCreServiceImpl.class);
			}
			respSetCre.setResp(resp);
			respSetCre.setTxn(reqSetCre.getTxn());
			String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(respSetCre).toString());
			log.info("xmlStr of ReqSetCre  is as {} "+xmlStr);
			Ack ack = npciResponseService.send(xmlStr, ConstantI.API_RESP_SET_CRE, reqSetCre.getTxn().getId());
			try {
				if (!Constant.bKPrf.equalsIgnoreCase(reqSetCre.getTxn().getId().substring(0, 3))) {
					reqRespSetCreDao.insertReqResp(reqSetCre, respSetCre, ack, reqDate);
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	private boolean checkUSDCDevice(final ReqSetCre reqSetCre) throws Exception {
		boolean f = false;
		boolean f2 = false;

		DeviceType deviceType = reqSetCre.getPayer().getDevice();
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
	private ReqResp setReqRespDetails(final String txnId,final String txnType,final String payerAddr,final String payerCode,final List<DeviceType.Tag> tags,final AccountType ac,List<Cred> credList,String credMPin)  throws Exception {
		ReqResp req = new ReqResp();
		try {
			req.setBkPrf(txnId.substring(0, 3));
			req.setTxnType(txnType);
			req.setPayerAddr(payerAddr);
			req.setPayerCode(payerCode);
			req.setRrn(idGeneratorService.getRrn());
			req.setTxnId(txnId);
			if (null != tags) {
				for (Tag tag : tags) {
					if (ConstantI.MOBILE.equalsIgnoreCase(tag.getName().value())) {
						req.setPayerDeviceMobile(tag.getValue());
					}
					if (ConstantI.TYPE.equalsIgnoreCase(tag.getName().value())) {
						req.setPayerDeviceType(tag.getValue());
					}
				}
			}
			req.setPayerAddrType(ac.getAddrType().value());
			if (ConstantI.ACCOUNT.equalsIgnoreCase(ac.getAddrType().value())) {
				List<Detail> details = ac.getDetail();
				for (Detail detail : details) {
					if (ConstantI.IFSC.equalsIgnoreCase(detail.getName().value())) {
						req.setPayerIfsc(detail.getValue());
					}
					if (ConstantI.ACNUM.equalsIgnoreCase(detail.getName().value())) {
						req.setPayerAccNum(detail.getValue());
					}
				}
			}
			req.setCredNMpin(credMPin);
			for (Cred cred : credList) {
				req.setCredMpin(cred.getData().getValue());
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return req;
	}
	
	
	private void setReqRespParam(final ReqSetCre reqSetCre, ReqResp req) {
		req.setBkPrf(reqSetCre.getTxn().getId().substring(0, 3));
		req.setTxnType(reqSetCre.getTxn().getType().value());
		req.setPayerAddr(reqSetCre.getPayer().getAddr());
		req.setPayerCode(reqSetCre.getPayer().getCode());
		req.setTxnId(reqSetCre.getTxn().getId());
		if (null != reqSetCre.getPayer().getDevice()) {
			List<Tag> tags = reqSetCre.getPayer().getDevice().getTag();
			for (Tag tag : tags) {
				if (ConstantI.MOBILE.equalsIgnoreCase(tag.getName().value())) {
					req.setPayerDeviceMobile(tag.getValue());
				}
				if (ConstantI.TYPE.equalsIgnoreCase(tag.getName().value())) {
					req.setPayerDeviceType(tag.getValue());
				}
			}
		}
		AccountType ac = reqSetCre.getPayer().getAc();
		req.setPayerAddrType(ac.getAddrType().value());
		if (ConstantI.ACCOUNT.equalsIgnoreCase(ac.getAddrType().value())) {
			List<Detail> details = ac.getDetail();
			for (Detail detail : details) {
				if (ConstantI.IFSC.equalsIgnoreCase(detail.getName().value())) {
					req.setPayerIfsc(detail.getValue());
				}
				if (ConstantI.ACNUM.equalsIgnoreCase(detail.getName().value())) {
					req.setPayerAcNum(detail.getValue());
				}
			}
		}
		req.setCredNMpin(reqSetCre.getPayer().getNewCred().getCred().get(0).getData().getValue());
		List<Cred> credList = reqSetCre.getPayer().getCreds().getCred();
		for (Cred cred : credList) {
			req.setCredMpin(cred.getData().getValue());
		}
	}
}
