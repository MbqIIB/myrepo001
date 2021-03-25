package com.npst.upiserver.acquirer.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.UpiRespSetCreService;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.CustomerTxnsDao;
import com.npst.upiserver.dao.MobReqRespJsonDao;
import com.npst.upiserver.dao.MobReqRespJsonIdDao;
import com.npst.upiserver.dao.MobUpiReqRespJsonIdDao;
import com.npst.upiserver.dao.ReqRespSetCreDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.npcischema.RespSetCre;
import com.npst.upiserver.npcischema.ResultType;

@Service
public class UpiRespSetCreServiceImpl implements UpiRespSetCreService {

	private static final Logger log = LoggerFactory.getLogger(UpiRespSetCreServiceImpl.class);

	@Autowired
	MobUpiReqRespJsonIdDao mobReqRespJsonIdDao;
	
	@Autowired
	MobReqRespJsonDao mobReqRespJsonDao;
	
	@Autowired
	ReqRespSetCreDao reqRespSetCreDao;
	
	@Autowired
	CustomerTxnsDao custTxnDao;
	
	@Override
	public void acquirerProcess(final RespSetCre respSetCre,final String message) {
		log.debug("respSetCre {}", respSetCre);
		try {
			Long idmobreqrespjsonid = mobReqRespJsonIdDao.getMobReqRespJsonId(respSetCre.getResp().getReqMsgId());
			if(idmobreqrespjsonid>0) {
				ReqResp respJson = new ReqResp();
				respJson.setTxnType(respSetCre.getTxn().getType().toString());
				if (ResultType.SUCCESS.toString().equals(respSetCre.getResp().getResult())) {
					respJson.setMsg(ResultType.SUCCESS.toString());
					respJson.setMsgId(ConstantI.MSG_ID_SUCCESS);
				} else {
					respJson.setMsgId(ConstantI.MSG_ID_FAILURE);
					respJson.setMsg(respSetCre.getResp().getErrCode());
				}
				mobReqRespJsonDao.finalDbUpdate(respJson, idmobreqrespjsonid);
				reqRespSetCreDao.updateReqResp(respSetCre);
			}
			custTxnDao.update(respSetCre);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	private void updateDb(long idPk, RespSetCre respSetCre) {
		ReqResp respJson = new ReqResp();
		respJson.setTxnType(respSetCre.getTxn().getType().toString());
		if (ResultType.SUCCESS.toString().equals(respSetCre.getResp().getResult())) {
			respJson.setMsg(ResultType.SUCCESS.toString());
			respJson.setMsgId(ConstantI.MSG_ID_SUCCESS);
		} else {
			respJson.setMsgId(ConstantI.MSG_ID_FAILURE);
			respJson.setMsg(respSetCre.getResp().getErrCode());
		}
		mobReqRespJsonDao.finalDbUpdate(respJson, idPk);
	}
}
