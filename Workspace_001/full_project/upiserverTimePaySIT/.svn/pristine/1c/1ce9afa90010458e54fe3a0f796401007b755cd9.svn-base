package com.npst.upiserver.issuer.service.impl;

import java.util.Base64;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.ReqRespBalEnqDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.issuer.service.UpiReqBalEnqService;
import com.npst.upiserver.npcischema.AccountType;
import com.npst.upiserver.npcischema.AccountType.Detail;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.AmountType;
import com.npst.upiserver.npcischema.CredsType.Cred;
import com.npst.upiserver.npcischema.DeviceType;
import com.npst.upiserver.npcischema.DeviceType.Tag;
import com.npst.upiserver.npcischema.HeadType;
import com.npst.upiserver.npcischema.ReqBalEnq;
import com.npst.upiserver.npcischema.RespBalEnq;
import com.npst.upiserver.npcischema.RespBalEnq.Payer;
import com.npst.upiserver.npcischema.RespBalEnq.Payer.Bal;
import com.npst.upiserver.npcischema.RespBalEnq.Payer.Bal.Data;
import com.npst.upiserver.npcischema.RespType;
import com.npst.upiserver.service.IdGeneratorService;
import com.npst.upiserver.service.impl.MiddlewareRestConServiceImpl;
import com.npst.upiserver.service.impl.NpciUpiRestConServiceImpl;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.Util;

@Service
public class UpiReqBalEnqServiceImpl implements UpiReqBalEnqService {

	private static final Logger log = LoggerFactory.getLogger(UpiReqBalEnqServiceImpl.class);
	public static boolean isBalSubStr=ConstantI.YES.equalsIgnoreCase(Util.getProperty("IS_BAL_SUBSTR_ENABLE"));
	private static int startIdx=Integer.parseInt(Util.getProperty("BALCHK_CBSBAL_START_INDEX")),endIdx=Integer.parseInt(Util.getProperty("BALCHK_CBSBAL_END_INDEX"));
	
	@Autowired
	ReqRespBalEnqDao reqRespBalEnq;
	
	@Autowired
	MiddlewareRestConServiceImpl  middlewareService;
	
	@Autowired
	NpciUpiRestConServiceImpl  npciResponseService;
	
	@Autowired
	private IdGeneratorService idGeneratorService;
	
	@Override
	public void issuerProcess(final ReqBalEnq reqBalEnq) {
		try {
			log.debug("reqBalEnq {}", reqBalEnq);
			ReqResp req = new ReqResp();
			String ts = Util.getTS();
			String msgId = Util.uuidGen();
			RespBalEnq respBalEnq = new RespBalEnq();
			respBalEnq.setHead(setHeadTypeDetails(msgId, ts));
			respBalEnq.setTxn(reqBalEnq.getTxn());
			Payer payer=setPayerDetails(reqBalEnq.getPayer().getAddr(),reqBalEnq.getPayer().getName());
			RespType resp = new RespType();
			resp.setReqMsgId(reqBalEnq.getHead().getMsgId());
			Bal bal = new Bal();
			Data data = new Data();
			try {
				//ReqResp req=setReqRespDetails(reqBalEnq.getTxn().getId(),reqBalEnq.getTxn().getType().value(),
					//reqBalEnq.getPayer().getAddr(),reqBalEnq.getPayer().getDevice().getTag(),reqBalEnq.getPayer().getAc(),reqBalEnq.getPayer().getCreds().getCred());
				setReqRespPram(reqBalEnq, req);
				req.setRrn(idGeneratorService.getRrn());
				req.setTxnType("BalEnq");
				boolean isValidReq=checkUSDCDevice(reqBalEnq);
				
				if(isValidReq) {
					log.info("Before Middleware Account Number is as {} ",req.getPayerAccNum());
					req.setIsUPI2("1");
					
					req = middlewareService.send(req);
					String respBal = "NA";
					if (ConstantI.MSG_ID_SUCCESS.equalsIgnoreCase(req.getRespCode().trim())) {
						if (ConstantI.BalChk.equalsIgnoreCase(req.getTxnType())) {
							if (checkAvailBal(req.getRespBal(), reqBalEnq.getPayer().getAmount())) {
								respBal = ConstantI.Y;
								resp.setResult(ConstantI.SUCCESS);
							} else {
								respBal = ConstantI.N;
								resp.setResult(ConstantI.FAILURE);
							}
						} else {
							if (isBalSubStr) {
								respBal = Base64.getEncoder().encodeToString(
										(req.getRespBal().substring(0, 20) + req.getRespBal().substring(0, 20)).getBytes());
							} else {
								//respBal = Util.formBal(req.getRespBal().trim());
								respBal = Base64.getEncoder().encodeToString(req.getRespBal().getBytes());
							}
							log.info("BAL RESP{}",respBal);
							resp.setResult(ConstantI.SUCCESS);
						}
						data.setValue(respBal);
						bal.setData(data);
						payer.setBal(bal);
					} else {
						if (ConstantI.BalChk.equalsIgnoreCase(req.getTxnType())) {
							respBal = ConstantI.N;
						}
						resp.setErrCode(req.getRespCode());
						data.setValue(respBal);
						bal.setData(data);
						resp.setResult(ConstantI.FAILURE);
						payer.setBal(bal);
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
				log.error(e.getMessage(),e);
				data.setValue(ConstantI.CONST_EXCP_MSG);
				bal.setData(data);
				resp.setErrCode(ConstantI.XB);
				resp.setResult(ConstantI.FAILURE);
				payer.setBal(bal);
				ErrorLog.sendError(e, UpiReqBalEnqServiceImpl.class);
			}
			respBalEnq.setResp(resp);
			respBalEnq.setPayer(payer);
			String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(respBalEnq).toString());
			log.info("response XML {}",xmlStr);
			Ack ack = npciResponseService.send(xmlStr, ConstantI.API_RESP_BAL_ENQ, reqBalEnq.getTxn().getId());
			try {
				if (!Constant.bKPrf.equalsIgnoreCase(reqBalEnq.getTxn().getId().substring(0, 3))) {
					reqRespBalEnq.insertReqResp(reqBalEnq, respBalEnq, ack);
				}
			} catch (Exception e) {
				log.error(e.getMessage(),e);
				ErrorLog.sendError(e, UpiReqBalEnqServiceImpl.class);
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
			ErrorLog.sendError(e, UpiReqBalEnqServiceImpl.class);
		}
	}
	
	private static boolean checkAvailBal(String cbsBalString, AmountType amountType) {
		if (ConstantI.INR.equalsIgnoreCase(amountType.getCurr()) && StringUtils.isNotBlank(amountType.getValue())) {
			long npciBal = Util.convertAmountInPaisa(amountType.getValue());
			if (npciBal <= Long.parseLong(cbsBalString.substring(startIdx, endIdx))) {
				return true;
			}
		}
		return false;
	}
	
	private boolean checkUSDCDevice(final ReqBalEnq reqBalEnq) throws Exception {
		boolean f = false;
		boolean f2 = false;

		DeviceType deviceType = reqBalEnq.getPayer().getDevice();
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
	
	private HeadType setHeadTypeDetails(final String msgId,final String ts) throws Exception {
		HeadType head = new HeadType();
		try {
			head.setMsgId(msgId);
			head.setOrgId(Constant.orgId);
			head.setTs(ts);
			head.setVer(Constant.headVer);	
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return head;
	}
	
	private Payer setPayerDetails(final String payerAddr,final String payerName) throws Exception {
		Payer payer = new Payer();
		try {
			payer.setAddr(payerAddr);
			payer.setName(payerName);
			payer.setSeqNum(ConstantI.CONSTANT_SEQ_NUM_1);
			payer.setType(ConstantI.TYPE_PERSON);
			payer.setCode(ConstantI.CODE_0000);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return payer;
	}
	
	private ReqResp setReqRespDetails(final String txnId,final String txnType,final String payerAddr,final List<DeviceType.Tag> tags,AccountType ac,List<Cred> credList) throws Exception {
		ReqResp req = new ReqResp();
		try {
			req.setBkPrf(txnId.substring(0, 3));
			req.setTxnType(txnType);
			req.setPayerAddr(payerAddr);
			req.setRrn(idGeneratorService.getRrn());
			req.setTxnId(txnId);
			for (Tag tag : tags) {
				if (ConstantI.MOBILE.equalsIgnoreCase(tag.getName().toString())) {
					req.setDeviceMobile(tag.getValue());
				}
				if (ConstantI.TYPE.equalsIgnoreCase(tag.getName().value())) {
					req.setDeviceType(tag.getValue());
				}
			}
			log.info("Addr Type {} ",ac.getAddrType().value());
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
			log.info("Account Number is as {} ",req.getPayerAccNum());
			if (ConstantI.MOBILE.equalsIgnoreCase(ac.getAddrType().value())) {
				List<Detail> details = ac.getDetail();
				for (Detail detail : details) {
					if (ConstantI.MOBNUM.equalsIgnoreCase(detail.getName().value())) {
						req.setPayerMobileNo(detail.getValue());
					}
					if (ConstantI.MMID.equalsIgnoreCase(detail.getName().value())) {
						req.setPayerMmid(detail.getValue());
					}
				}
			}
			for (Cred cred : credList) {
				req.setCredMpin(cred.getData().getValue());
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return req;
	}
	
	private void setReqRespPram(final ReqBalEnq reqBalEnq, ReqResp req) {
		req.setBkPrf(reqBalEnq.getTxn().getId().substring(0, 3));
		req.setTxnType(reqBalEnq.getTxn().getType().value());
		req.setPayerAddr(reqBalEnq.getPayer().getAddr());
		req.setTxnId(reqBalEnq.getTxn().getId());
		List<Tag> tags = reqBalEnq.getPayer().getDevice().getTag();
		for (Tag tag : tags) {
			if (ConstantI.MOBILE.equalsIgnoreCase(tag.getName().toString())) {
				req.setPayerDeviceMobile(tag.getValue());
			}
			if (ConstantI.TYPE.equalsIgnoreCase(tag.getName().value())) {
				req.setPayerDeviceType(tag.getValue());
			}
		}
		AccountType ac = reqBalEnq.getPayer().getAc();
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
		} else if (ConstantI.MOBILE.equalsIgnoreCase(ac.getAddrType().value())) {
			List<Detail> details = ac.getDetail();
			for (Detail detail : details) {
				if (ConstantI.MOBNUM.equalsIgnoreCase(detail.getName().value())) {
					req.setPayerMobileNo(detail.getValue());
				}
				if (ConstantI.MMID.equalsIgnoreCase(detail.getName().value())) {
					req.setPayerMmid(detail.getValue());
				}
			}
		}
		List<Cred> credList = reqBalEnq.getPayer().getCreds().getCred();
		for (Cred cred : credList) {
			req.setCredMpin(cred.getData().getValue());
		}
		req.setTxnType(ConstantI.TYPE_BAL_ENQ);
		req.setSubType(reqBalEnq.getTxn().getType().value());
	}
	
}
