package com.npst.upiserver.acquirer.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.UpiRespRegMobService;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.CustomerTxnsDao;
import com.npst.upiserver.dao.MobReqRespJsonDao;
import com.npst.upiserver.dao.MobReqRespJsonIdDao;
import com.npst.upiserver.dao.MobUpiReqRespJsonIdDao;
import com.npst.upiserver.dao.ReqRespRegMobDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.npcischema.RespRegMob;
import com.npst.upiserver.npcischema.ResultType;

@Service
public class UpiRespRegMobServiceImpl implements UpiRespRegMobService {

	private static final Logger log = LoggerFactory.getLogger(UpiRespRegMobServiceImpl.class);

	@Autowired
	MobReqRespJsonIdDao mobReqRespJsonIdDao;
	
	@Autowired
	MobReqRespJsonDao mobReqRespJsonDao;
	
	@Autowired
	ReqRespRegMobDao reqRespRegMobDao;
	
	@Autowired
	CustomerTxnsDao custTxnDao;
	
	@Autowired
	MobUpiReqRespJsonIdDao mobUpiReqRespJsonIdDao;
	
	@Override
	public void acquirerProcess(final RespRegMob respRegMob,final String message) {
		log.debug("respRegMob {}", respRegMob);
		try {
			Long idmobreqrespjsonid = mobUpiReqRespJsonIdDao.getMobReqRespJsonId(respRegMob.getResp().getReqMsgId());
			if(idmobreqrespjsonid>0) {
				updateDb(idmobreqrespjsonid,respRegMob);
				reqRespRegMobDao.updateRespPay(respRegMob);
			}
			custTxnDao.update(respRegMob);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void updateDb(long idPk, RespRegMob respRegMob) {
		ReqResp respJson = new ReqResp();
		respJson.setTxnType(respRegMob.getTxn().getType().toString());
		if (ResultType.SUCCESS.toString().equals(respRegMob.getResp().getResult())) {
			respJson.setMsg(ResultType.SUCCESS.toString());
			respJson.setMsgId(ConstantI.MSG_ID_SUCCESS);
		} else {
			respJson.setMsg(respRegMob.getResp().getErrCode());
			respJson.setMsgId(ConstantI.MSG_ID_FAILURE);
		}
		mobReqRespJsonDao.finalDbUpdate(respJson, idPk);
	}

}
