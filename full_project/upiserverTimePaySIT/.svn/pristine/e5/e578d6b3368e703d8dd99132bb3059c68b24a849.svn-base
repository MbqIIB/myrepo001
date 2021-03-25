/*package com.npst.upiserver.acquirer.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.npst.upiserver.acquirer.service.MobOnusPayService;
import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.CustomerTxnsDao;
import com.npst.upiserver.dao.MobOnusReqRespJsonDao;
import com.npst.upiserver.dao.ReqRespDebitCreditDao;
import com.npst.upiserver.dao.ReqRespPayCollectDao;
import com.npst.upiserver.dao.TransServerDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.OnusMobReqRespJsonEntity;
import com.npst.upiserver.npcischema.ResultType;
import com.npst.upiserver.service.IdGeneratorService;
import com.npst.upiserver.service.MiddlewareRestConService;
import com.npst.upiserver.service.PayAccTypeValidationService;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.JsonMan;

@Component
public class MobOnusPayServiceImpl implements MobOnusPayService {
	private static final Logger log = LoggerFactory.getLogger(MobOnusPayServiceImpl.class);

	@Autowired
	private MiddlewareRestConService switchCom;
	@Autowired
	private MobOnusReqRespJsonDao mobOnusReqRespJsonDao;
	@Autowired
	private IdGeneratorService idGeneratorService;
	@Autowired
	private ReqRespPayCollectDao reqRespPayCollectDao;
	@Autowired
	private CustomerTxnsDao customerTxnsDao;
	@Autowired
	private ReqRespDebitCreditDao reqRespDebitCreditDao;
	@Autowired
	private TransServerDao transServerDao;
	
	@Autowired
	private PayAccTypeValidationService payAccTypeValidationService;
	
	@Override
	public void processOnusPay(OnusMobReqRespJsonEntity onusMobReqRespJson) {
		try {
			log.debug("onusMobReqRespJson {}", onusMobReqRespJson);
			ReqResp reqJson = JsonMan.getReqResp(onusMobReqRespJson.getReq());
			ReqResp req = new ReqResp();
			req.setTxnId(reqJson.getTxnId());
			req.setTxnRefId(reqJson.getTxnRefId() == null || reqJson.getTxnRefId().trim().isEmpty() ? req.getTxnId()
					: reqJson.getTxnRefId());
			req.setCredMpin(reqJson.getCredMpin());
			req.setPayerAddr(reqJson.getPayerAddr());
			req.setPayerAddrType(reqJson.getPayerAddrType());
			req.setPayerAcType(reqJson.getPayerAcType());
			req.setPayerIfsc(reqJson.getPayerIfsc());
			req.setPayerName(reqJson.getPayerName());
			req.setPayerAcNum(reqJson.getPayerAcNum());
			req.setPayeeName(reqJson.getPayeeName());
			req.setPayeeAddr(reqJson.getPayeeAddr());
			req.setPayeeAddrType(reqJson.getPayeeAddrType());
			req.setPayeeAcType(reqJson.getPayeeAcType());
			req.setPayeeAcNum(reqJson.getPayeeAcNum());
			req.setPayeeIfsc(reqJson.getPayeeIfsc());
			req.setPayeeMobileNo(reqJson.getPayeeMobileNo());
			req.setPayeeMmid(reqJson.getPayeeMmid());
			if (reqJson.getPayeeAmount() == null || reqJson.getPayeeAmount().trim().isEmpty()) {
				reqJson.setPayeeAmount(reqJson.getPayerAmount());
			}
			req.setPayerAmount(reqJson.getPayerAmount());
			req.setPayeeAmount(reqJson.getPayeeAmount());
            
			req.setTxnNote(reqJson.getTxnNote());
			req.setPayerDeviceGeoCode(reqJson.getPayerDeviceGeoCode());
			req.setPayerDeviceIp(reqJson.getPayerDeviceIp());
			req.setPayerDeviceLocation(reqJson.getPayerDeviceLocation());
			req.setPayerDeviceMobile(reqJson.getPayerDeviceMobile());
			req.setPayerDeviceOsType(reqJson.getPayerDeviceOsType());

			req.setOnUs(true);
			req.setField11(idGeneratorService.getStan());
			req.setRrn(idGeneratorService.getRrn());
			req.setTxnType(Constant.ONUS_FUNDTRANSFER);
			req.setOnusType(onusMobReqRespJson.getType());
			Date reqDate = new Date();
			ReqResp reqResp = null;
//			if(!payAccTypeValidationService.isAccTypeValid(req)) {
//				req.setRespCode("XB");
//				log.info("isAccTypeValid=flase");
//			}else {
				reqResp = switchCom.send(req);
				req.setCbsErrorCode(reqResp.getCbsErrorCode());
				req.setRespCode(reqResp.getRespCode());
				req.setRespMsg(reqResp.getRespMsg());
//			}

			Date respDate = new Date();
			onusMobReqRespJson.setRespDate(respDate);
			if (!ConstantI.UKN.equals(req.getRespCode())) {
				updateMobFundResponse(onusMobReqRespJson, req);
			}
			reqRespDebitCreditDao.insertOnusAndPreApproed(req, reqDate, respDate);
			reqRespPayCollectDao.insertReqRespOnus(req);
			customerTxnsDao.insertOnusPay(req, reqDate, respDate);
			transServerDao.insertTransServer(reqResp==null?req:reqResp, null, null);
		} catch (Exception e) {
			log.error("error {}", e);
			ErrorLog.sendError(e, MobOnusPayServiceImpl.class);
		}

	}

	private void updateMobFundResponse(OnusMobReqRespJsonEntity onusMobReqRespJson, ReqResp reqResp) {
		try {
			ReqResp respJson = new ReqResp();
			if (ConstantI.zero.equals(reqResp.getRespCode())) {
				respJson.setMsg(ResultType.SUCCESS.toString());
				respJson.setMsgId(ConstantI.MSG_ID_SUCCESS);
			} else {
				respJson.setMsgId(ConstantI.MSG_ID_FAILURE);
				respJson.setMsg(reqResp.getRespCode());
			}
			respJson.setOnUs(true);
			respJson.setTxnId(reqResp.getTxnId());
			respJson.setRrn(reqResp.getRrn());
			onusMobReqRespJson.setRespDate(new Date());
			onusMobReqRespJson.setResp(JsonMan.getJSONStr(respJson));
			onusMobReqRespJson.setFlag(ConstantI.STATUS_3);
			mobOnusReqRespJsonDao.finalDbUpdate(onusMobReqRespJson);
		} catch (Exception e) {
			log.error("error :{}", e);
			ErrorLog.sendError(e, MobOnusPayServiceImpl.class);
		}

	}

}
*/