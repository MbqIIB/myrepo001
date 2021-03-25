package com.npst.upiserver.acquirer.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.UpiRespOtpService;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.CustomerTxnsDao;
import com.npst.upiserver.dao.MobReqRespJsonDao;
import com.npst.upiserver.dao.MobReqRespJsonIdDao;
import com.npst.upiserver.dao.MobUpiReqRespJsonIdDao;
import com.npst.upiserver.dao.ReqRespOtpDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.npcischema.RespOtp;
import com.npst.upiserver.npcischema.ResultType;

@Service
public class UpiRespOtpServiceImpl implements UpiRespOtpService {

	private static final Logger log = LoggerFactory.getLogger(UpiRespOtpServiceImpl.class);

	@Autowired
	MobReqRespJsonIdDao mobReqRespJsonIdDao;
	
	@Autowired
	MobReqRespJsonDao mobReqRespJsonDao;
	
	@Autowired
	ReqRespOtpDao reqRespOtpDao;
	
	@Autowired
	CustomerTxnsDao custTxnDao;
	
	@Autowired
	MobUpiReqRespJsonIdDao mobUpiReqRespJsonIdDao;
	
	@Override
	public void acquirerProcess(final RespOtp respOtp,final String message) {
		log.info("respOtp {}", respOtp);
		try {
			Long idmobreqrespjsonid = mobUpiReqRespJsonIdDao.getMobReqRespJsonId(respOtp.getResp().getReqMsgId());
			if(idmobreqrespjsonid>0) {
				ReqResp respJson = new ReqResp();
				respJson.setTxnType(respOtp.getTxn().getType().toString());
				if (ResultType.SUCCESS.toString().equals(respOtp.getResp().getResult())) {
					respJson.setMsgId(ConstantI.MSG_ID_SUCCESS);
					respJson.setMsg(ResultType.SUCCESS.toString());
				} else {
					respJson.setMsgId(ConstantI.MSG_ID_FAILURE);
					respJson.setMsg(respOtp.getResp().getErrCode());
				}
				mobReqRespJsonDao.finalDbUpdate(respJson, idmobreqrespjsonid);
				log.info("Response OTP is as {} ",respOtp);
				reqRespOtpDao.updateResp(respOtp);
			}
			custTxnDao.update(respOtp);
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
