package com.npst.upiserver.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.MobUpiReqRespJsonDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.MobUpiReqRespJson;
import com.npst.upiserver.entity.Mobupireqrespjsonid;
import com.npst.upiserver.npcischema.RespChkTxn;
import com.npst.upiserver.npcischema.ResultType;
import com.npst.upiserver.repo.MobUpiReqRespJsonIdRepository;
import com.npst.upiserver.repo.MobUpiReqRespJsonRepository;
import com.npst.upiserver.util.JsonMan;

@Component
public class MobUpiReqRespJsonDaoImpl implements MobUpiReqRespJsonDao {
	private static final Logger log = LoggerFactory.getLogger(MobUpiReqRespJsonDaoImpl.class);
	@Autowired
	MobUpiReqRespJsonRepository mobUpiReqRespJsonRepo;
	
	@Autowired
	MobUpiReqRespJsonIdRepository mobUpiReqRespJsonIdRepo;
	
	@Override
	public void updateDb(Long id, RespChkTxn respChkTxn, String message) {
		try {
			ReqResp respJson = new ReqResp();
			respJson.setTxnType(respChkTxn.getTxn().getType().toString());
			if (ResultType.SUCCESS.toString().equalsIgnoreCase(respChkTxn.getResp().getResult())) {
				respJson.setRefCheckTxn(respChkTxn.getResp().getRef());
				respJson.setMsg(respChkTxn.getResp().getResult());
				respJson.setMsgId(ConstantI.MSG_ID_SUCCESS);
			} else {
				respJson.setMsg(respChkTxn.getResp().getErrCode());
				respJson.setMsgId(ConstantI.MSG_ID_FAILURE);
			}
			update(respJson, id);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	private void update(ReqResp respJson, Long id) {
		MobUpiReqRespJson mobUpireqrespjson =null;
		try {
			mobUpireqrespjson =mobUpiReqRespJsonRepo.findByIdPk(id);
			if (null != mobUpireqrespjson) {
				mobUpireqrespjson.setResp(JsonMan.getJSONStr(respJson));
				mobUpireqrespjson.setRespDate(new java.util.Date());
				mobUpireqrespjson.setFlag(3);
				mobUpiReqRespJsonRepo.save(mobUpireqrespjson);
				Mobupireqrespjsonid logmobrecid=mobUpiReqRespJsonIdRepo.findByIdmobreqrespjsonid(mobUpireqrespjson.getIdPk());
				if(logmobrecid!=null) {
					logmobrecid.setFlag(mobUpireqrespjson.getFlag());
					mobUpiReqRespJsonIdRepo.save(logmobrecid);
				}
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
}
