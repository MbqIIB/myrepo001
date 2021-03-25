package com.npst.upiserver.acquirer.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.UpiRespListAccountService;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.CustomerTxnsDao;
import com.npst.upiserver.dao.MobReqRespJsonDao;
import com.npst.upiserver.dao.MobReqRespJsonIdDao;
import com.npst.upiserver.dao.MobUpiReqRespJsonIdDao;
import com.npst.upiserver.dao.ReqRespListAccDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.npcischema.RespListAccount;
import com.npst.upiserver.npcischema.ResultType;

@Service
public class UpiRespListAccountServiceImpl implements UpiRespListAccountService {
	private static final Logger log = LoggerFactory.getLogger(UpiRespListAccountServiceImpl.class);
	
	@Autowired
	MobUpiReqRespJsonIdDao mobReqRespJsonIdDao;
	
	@Autowired
	MobReqRespJsonDao mobReqRespJsonDao;
	
	
	@Autowired
	ReqRespListAccDao reqRespListAccDao;
	
	@Autowired
	CustomerTxnsDao			custTxnDao;
	
	@Override
	public void acquirerProcess(final RespListAccount respListAccount,final String message) {
		log.debug("respListAccount {}", respListAccount);
		try {
			Long idmobreqrespjsonid = mobReqRespJsonIdDao.getMobReqRespJsonId(respListAccount.getResp().getReqMsgId());
			if(idmobreqrespjsonid>0) {
			updateDb(idmobreqrespjsonid, respListAccount);
				reqRespListAccDao.updateResp(respListAccount);
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void updateDb(long idPk, RespListAccount respListAccount) {
		ReqResp respJson = new ReqResp();
		respJson.setTxnType(respListAccount.getTxn().getType().toString());
		if (ResultType.SUCCESS.toString().equalsIgnoreCase(respListAccount.getResp().getResult())) {
			respJson.setUpiAccount(respListAccount.getAccountList().getAccount());
			respJson.setMsg(respListAccount.getResp().getResult());
			respJson.setMsgId(ConstantI.MSG_ID_SUCCESS);
		} else {
			respJson.setMsg(respListAccount.getResp().getErrCode());
			respJson.setMsgId(ConstantI.MSG_ID_FAILURE);
		}
		mobReqRespJsonDao.finalDbUpdate(respJson, idPk);

	}
}
