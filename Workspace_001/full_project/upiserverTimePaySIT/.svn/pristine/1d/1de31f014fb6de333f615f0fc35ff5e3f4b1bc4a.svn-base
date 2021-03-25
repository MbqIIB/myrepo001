package com.npst.upiserver.acquirer.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.UpiRespListKeysService;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.MobReqRespJsonDao;
import com.npst.upiserver.dao.MobReqRespJsonIdDao;
import com.npst.upiserver.dao.MobUpiReqRespJsonIdDao;
import com.npst.upiserver.dao.ReqRespListKeysDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.npcischema.PayConstant;
import com.npst.upiserver.npcischema.RespListKeys;
import com.npst.upiserver.npcischema.ResultType;

@Service
public class UpiRespListKeysServiceImpl implements UpiRespListKeysService {
	private static final Logger log = LoggerFactory.getLogger(UpiRespListKeysServiceImpl.class);

	@Autowired
	MobUpiReqRespJsonIdDao mobReqRespJsonIdDao;
	
	@Autowired
	MobReqRespJsonDao mobReqRespJsonDao;
	
	@Autowired
	ReqRespListKeysDao reqRespListKeysDao;
	
	@Override
	public void acquirerProcess(final RespListKeys respListKeys,final String message) {
		log.debug("respListKeys {}", respListKeys);
		try {
			Long idmobreqrespjsonid = mobReqRespJsonIdDao.getMobReqRespJsonId(respListKeys.getResp().getReqMsgId());
			if (idmobreqrespjsonid == 0) {
				log.warn("idPk not found for ReqMsgId={}", respListKeys.getResp().getReqMsgId());
			} else {
				updateDb(idmobreqrespjsonid, respListKeys, message);
			}
			
			reqRespListKeysDao.updateResp(respListKeys);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void updateDb(long idPk, RespListKeys respListKeys, String xmlMsg) {
		try {
			ReqResp respJson = new ReqResp();
			respJson.setTxnType(respListKeys.getTxn().getType().toString());
			if (ConstantI.GET_TOKEN.equalsIgnoreCase(respListKeys.getTxn().getType().value())
					&& ResultType.SUCCESS.toString().equals(respListKeys.getResp().getResult())) {
				respJson.setTokenStr(respListKeys.getKeyList().getKey().get(0).getKeyValue().toString());
				respJson.setMsg(ResultType.SUCCESS.toString());
				respJson.setMsgId(ConstantI.CODE_SUCCESS);
			} else if ((ConstantI.API_LIST_KEYS.equalsIgnoreCase(respListKeys.getTxn().getType().value())
					|| PayConstant.LIST_PSP_KEYS.toString().equalsIgnoreCase(respListKeys.getTxn().getType().value()))
					&& ResultType.SUCCESS.toString().equals(respListKeys.getResp().getResult())) {
				respJson.setListKeys(xmlMsg);
				respJson.setMsgId(ConstantI.MSG_ID_SUCCESS);
				respJson.setMsg(ResultType.SUCCESS.toString());
			} else {
				respJson.setMsgId(ConstantI.MSG_ID_FAILURE);
				respJson.setMsg(respListKeys.getResp().getErrCode());
			}
			mobReqRespJsonDao.finalDbUpdate(respJson, idPk);
		} catch (Exception e) {
			log.error("updateDb  respListKeys error {}", e);
//			ErrorLog.sendError(e, MobReqRespJsonDaoImpl.class);
		}

	}

}
