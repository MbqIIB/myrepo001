/*package com.npst.upiserver.acquirer.service.impl;

import java.util.Base64;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.npst.upiserver.acquirer.service.MobOnusBalService;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.CustomerTxnsDao;
import com.npst.upiserver.dao.MobOnusReqRespJsonDao;
import com.npst.upiserver.dao.ReqRespBalEnqDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.OnusMobReqRespJsonEntity;
import com.npst.upiserver.npcischema.PayConstant;
import com.npst.upiserver.npcischema.ResultType;
import com.npst.upiserver.service.IdGeneratorService;
import com.npst.upiserver.service.MiddlewareRestConService;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.JsonMan;

@Component
public class MobOnusBalServiceImpl implements MobOnusBalService {
	private static final Logger log = LoggerFactory.getLogger(MobOnusBalServiceImpl.class);

	@Autowired
	private MiddlewareRestConService switchCom;
	@Autowired
	private MobOnusReqRespJsonDao mobOnusReqRespJsonDao;
	@Autowired
	private IdGeneratorService idGeneratorService;

	@Autowired
	private CustomerTxnsDao customerTxnsDao;

	@Autowired
	private ReqRespBalEnqDao reqRespBalDao;

	@Override
	public void processOnusBalEnq(OnusMobReqRespJsonEntity onusMobReqRespJson) {

		try {
			log.debug("onusMobReqRespJson {}", onusMobReqRespJson);
			ReqResp reqJson = JsonMan.getReqResp(onusMobReqRespJson.getReq());
			ReqResp req = new ReqResp();
			req.setPayerAcNum(reqJson.getPayerAcNum());
			req.setTxnId(reqJson.getTxnId());
			req.setTxnNote(reqJson.getTxnNote());
			req.setTxnType(PayConstant.BAL_ENQ.value());
			req.setPayerAddr(reqJson.getPayerAddr());
			req.setPayerCode(reqJson.getPayerCode());
			req.setPayerAddrType(reqJson.getPayerAddrType());
			req.setPayerIfsc(reqJson.getPayerIfsc());
			req.setPayerAcType(reqJson.getPayerAcType());
			req.setPayerDeviceGeoCode(reqJson.getPayerDeviceGeoCode());
			req.setPayerDeviceIp(reqJson.getPayerDeviceIp());
			req.setPayerDeviceLocation(reqJson.getPayerDeviceLocation());
			req.setPayerDeviceMobile(reqJson.getPayerDeviceMobile());
			req.setPayerDeviceOsType(reqJson.getPayerDeviceOsType());
			req.setCredMpin(reqJson.getCredMpin());
			req.setField11(idGeneratorService.getStan());
			req.setOnUs(true);
			req.setOnusType(onusMobReqRespJson.getType());
			ReqResp reqResp = null;
			reqResp = switchCom.send(req);
			req.setRespBal(reqResp.getRespBal());
			req.setCbsErrorCode(reqResp.getCbsErrorCode());
			req.setRespCode(reqResp.getRespCode());
			req.setRespMsg(reqResp.getRespMsg());

			if (!ConstantI.UKN.equals(req.getRespCode())) {
				updateMobOnusBalResponse(onusMobReqRespJson, req);
			}
			reqRespBalDao.insertReqRespOnus(req);
			customerTxnsDao.insertOnusBalReqResp(req);
		} catch (Exception e) {
			log.error("error {}", e);
			ErrorLog.sendError(e, MobOnusBalServiceImpl.class);
		}

	}

	private void updateMobOnusBalResponse(OnusMobReqRespJsonEntity onusMobReqRespJson, ReqResp reqResp) {
		try {
			ReqResp respJson = new ReqResp();
			if (ConstantI.CONSTANT_0.equalsIgnoreCase(reqResp.getRespCode().trim())) {
				try {
					respJson.setRespBal(Base64.getEncoder().encodeToString(reqResp.getRespBal().getBytes()));
					respJson.setMsg(ResultType.SUCCESS.toString());
					respJson.setMsgId(ConstantI.CONSTANT_0);
				} catch (Exception e) {
					respJson.setMsgId(ConstantI.CONSTANT_1);
					respJson.setMsg(ConstantI.EXP1);
					e.printStackTrace();
					log.error("error :{}", e);
				}
			} else {
				respJson.setMsgId(ConstantI.CONSTANT_1);
				respJson.setMsg(reqResp.getRespCode());
			}
			respJson.setOnUs(true);
			onusMobReqRespJson.setRespDate(new Date());
			onusMobReqRespJson.setFlag(ConstantI.STATUS_3);
			onusMobReqRespJson.setResp(JsonMan.getJSONStr(respJson));
			mobOnusReqRespJsonDao.finalDbUpdate(onusMobReqRespJson);
		} catch (Exception e) {
			log.error("error {}", e);
			ErrorLog.sendError(e, MobOnusBalServiceImpl.class);
		}
	}

}
*/