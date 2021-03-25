package com.npst.upiserver.acquirer.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.PreApprovedReversalService;
import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.MobReqRespJsonDao;
import com.npst.upiserver.dao.ReqRespDebitCreditDao;
import com.npst.upiserver.dao.TransServerDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.MobUpiReqRespJson;
import com.npst.upiserver.npcischema.RespPay;
import com.npst.upiserver.service.IdGeneratorService;
import com.npst.upiserver.service.MiddlewareRestConService;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.JsonMan;
import com.npst.upiserver.util.Util;

@Service
public class PreApprovedReversalServiceImpl implements PreApprovedReversalService {

	private static final Logger log = LoggerFactory.getLogger(PreApprovedReversalServiceImpl.class);

	@Autowired
	private MiddlewareRestConService switchCom;
	@Autowired
	private MobReqRespJsonDao mobReqRespJsonDao;
	/*@Autowired
	private IdGeneratorService idGeneratorService;*/
	@Autowired
	private ReqRespDebitCreditDao reqRespDebitCreditDao;
	@Autowired
	private TransServerDao transServerDao;

	@Autowired
	private IdGeneratorService idGeneratorService;
	
	@Override
	public void preApprovedReversal(long idPk, RespPay respPay) {
		if (ConstantI.FAILURE.equalsIgnoreCase(respPay.getResp().getResult().value())) {
			// check preApproved and get org mob reqresp
			MobUpiReqRespJson mobupireqrespjson = mobReqRespJsonDao.getByIdPk(idPk);
			if (mobupireqrespjson == null) {
				log.info("MobUpiReqRespJsonEntity not found for idpk={}", idPk);
				return;
			}
			if (ConstantI.PREAPPROVED.equalsIgnoreCase(mobupireqrespjson.getType())) {
				log.info("PREAPPROVED MobUpiReqRespJsonEntity for idpk ={}", idPk);
				ReqResp reqresp = JsonMan.getReqResp(mobupireqrespjson.getReq());
				preApprovedReversalProc(respPay, reqresp);
			}
		} else {
			log.warn("respPay RespResult={}", respPay.getResp().getResult().value());
			return;
		}

	}

	private void preApprovedReversalProc(RespPay respPay, ReqResp reqResp) {
		try {
			ReqResp req = new ReqResp();
			Date reqDate = new Date(), respDate = null;

			req.setTxnNote(reqResp.getTxnNote());
			req.setTxnType(ConstantI.REVERSAL);
			// TODO azad sir review
			req.setRrn(respPay.getTxn().getCustRef());
			req.setField11(idGeneratorService.getRrn());
			req.setTxnId(Util.uuidGen());
			req.setBkPrf(Constant.bKPrf);

			req.setOrgRespCode(respPay.getResp().getErrCode());
			req.setOrgTxnId(respPay.getTxn().getId());
			req.setOrgTxnType(ConstantI.DEBIT);
			req.setPayerAddr(reqResp.getPayerAddr());
			req.setPayerAddrType(reqResp.getPayerAddrType());
			req.setPayerAmount(reqResp.getPayerAmount());
			req.setPayerCode(reqResp.getPayerCode());
			req.setPayeeAmount(req.getPayeeAmount());
			req.setPayeeCode(reqResp.getPayeeCode());
			req.setPayerAcNum(reqResp.getPayerAcNum());
			req.setPayerAcType(reqResp.getPayerAcType());
			req.setPayerIfsc(reqResp.getPayerIfsc());
			req.setPayerMmid(reqResp.getPayerMmid());
			req.setPayerMobileNo(reqResp.getPayerMobileNo());
			req.setPayerDeviceMobile(reqResp.getPayerDeviceMobile());

			req.setPayerIin(req.getPayerIin());
			req.setPayerUidNum(req.getPayerUidNum());
			req.setOnUs(true);
			ReqResp resp = null;
			resp = switchCom.send(req);
			respDate = new Date();
			req.setRespCode(resp.getRespCode());
			req.setRespMsg(resp.getRespMsg());
			req.setCbsErrorCode(resp.getCbsErrorCode());
			transServerDao.insertTransServer(resp, null, ConstantI.DEBIT);
			reqRespDebitCreditDao.updatePreApprovedReversal(req, respPay, reqDate, respDate);
		} catch (Exception e) {
			log.error("error :{}", e);
			ErrorLog.sendError(e, PreApprovedReversalServiceImpl.class);
		}

	}
}
