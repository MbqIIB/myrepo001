package com.npst.upiserver.dao.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.MobMandateReqRespJsonDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.MobMandateReqRespJsonEntity;
import com.npst.upiserver.entity.MobMandateReqRespJsonIdEntity;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.ReqMandate;
import com.npst.upiserver.npcischema.ResultType;
import com.npst.upiserver.repo.MobMandateReqRespJsonIdRepo;
import com.npst.upiserver.repo.MobMandateReqRespJsonRepo;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.JsonMan;

@Component
public class MobMandateReqRespJsonDaoImpl implements MobMandateReqRespJsonDao {
	private static final Logger log = LoggerFactory.getLogger(MobMandateReqRespJsonDaoImpl.class);

	@Autowired
	private MobMandateReqRespJsonRepo mobMandateReqRespJsonRepo;
	@Autowired
	private MobMandateReqRespJsonIdRepo mobMandateReqRespJsonIdRepo;

	@Override
	public void finalDbUpdate(ReqResp respJson, long idPk) {
		try {
			MobMandateReqRespJsonEntity mobMandateReqRespJson = mobMandateReqRespJsonRepo.findByIdPk(idPk);
			if (mobMandateReqRespJson == null) {
				log.error("MobMandateReqRespJsonEntity not found for idPk={} ,ReqResp={}", idPk,
						JsonMan.getJSONStr(respJson));
//				ErrorLog.sendError("MobMandateReqRespJsonEntity not found for idPk ",
//						new String[] { String.valueOf(idPk), JsonMan.getJSONStr(respJson) },
//						MobReqRespJsonDaoImpl.class);
			} else {
				mobMandateReqRespJson.setResp(JsonMan.getJSONStr(respJson));
				mobMandateReqRespJson.setRespDate(new Date());
				mobMandateReqRespJson.setFlag(ConstantI.STATUS_3);
				mobMandateReqRespJsonRepo.save(mobMandateReqRespJson);
				updateMobMandateReqRespJsonIdEntity(mobMandateReqRespJson.getIdPk(), mobMandateReqRespJson.getFlag());
			}
		} catch (Exception e) {
			log.error("finalDbUpdate  MobMandateReqRespJsonDaoImpl error {}", e);
			ErrorLog.sendError(e, MobMandateReqRespJsonDaoImpl.class);
		}

	}

	@Override
	public void finalDbUpdate(MobMandateReqRespJsonEntity mobMandateReqRespJson) {
		try {
			mobMandateReqRespJsonRepo.save(mobMandateReqRespJson);
			updateMobMandateReqRespJsonIdEntity(mobMandateReqRespJson.getIdPk(), mobMandateReqRespJson.getFlag());
		} catch (Exception e) {
			log.error("finalDbUpdate MobMandateReqRespJsonDaoImpl error {}", e);
			ErrorLog.sendError(e, MobMandateReqRespJsonDaoImpl.class);
		}

	}

	@Override
	public void updateFail(MobMandateReqRespJsonEntity mobMandateReqRespJson) {
		// TODO Auto-generated method stub
		log.info("mobMandateReqRespJson {}", mobMandateReqRespJson);

	}

	@Override
	public void updateMsgId(String msgId, long idPk) {
		updateMobMandateReqRespJsonIdEntity(idPk, msgId);
	}

	@Override
	public void updateDb(ReqMandate reqMandate, Ack ack, MobMandateReqRespJsonEntity mobMandateReqRespJson) {
		// TODO Auto-generated method stub
		try {
			ReqResp respJson = new ReqResp();
			respJson.setRrn(reqMandate.getTxn().getCustRef());
			respJson.setTxnId(reqMandate.getTxn().getId());
			// respJson.setTxnType(txnType);
			if (null != ack.getErr() || 0 != ack.getErrorMessages().size()) {
				respJson.setUpiErrorCode(JsonMan.getJSONStr(ack));
				respJson.setMsg(Constant.ACK_ERROR);
				respJson.setMsgId(ConstantI.MSG_ID_FAILURE);
			} else {
				respJson.setMsg(ResultType.SUCCESS.toString());
				respJson.setMsgId(ConstantI.MSG_ID_SUCCESS);
			}
			mobMandateReqRespJson.setResp(JsonMan.getJSONStr(respJson));
			mobMandateReqRespJson.setRespDate(new Date());
			mobMandateReqRespJson.setFlag(ConstantI.STATUS_3);
			mobMandateReqRespJsonRepo.save(mobMandateReqRespJson);
			updateMobMandateReqRespJsonIdEntity(mobMandateReqRespJson.getIdPk(), mobMandateReqRespJson.getFlag());
		} catch (Exception e) {
			log.error("update MobMandateReqRespJsonEntity error {}", e);
			ErrorLog.sendError(e, MobMandateReqRespJsonDaoImpl.class);
		}

	}

	@Override
	public void updateMobMandateReqRespJsonIdEntity(long idPk, int flag) {
		try {
			// we can update by native query where idpk
			MobMandateReqRespJsonIdEntity en = mobMandateReqRespJsonIdRepo.findById(idPk);
			if (en != null) {
				en.setFlag(flag);
				mobMandateReqRespJsonIdRepo.save(en);
			} else {
				log.warn(" MobMandateReqRespJsonIdEntity is not found for idpk={}", idPk);
			}
		} catch (Exception e) {
			log.error("update MobMandateReqRespJsonIdEntity error {}", e);
			ErrorLog.sendError(e, MobMandateReqRespJsonDaoImpl.class);
		}

	}

	@Override
	public void updateMobMandateReqRespJsonIdEntity(long idPk, String msgId) {
		try {
			// we can update by native query where idpk
			MobMandateReqRespJsonIdEntity en = mobMandateReqRespJsonIdRepo.findById(idPk);
			if (en != null) {
				en.setMsgid(msgId);
				mobMandateReqRespJsonIdRepo.save(en);
			} else {
				log.warn(" MobMandateReqRespJsonIdEntity is not found for idpk={}", idPk);
			}
		} catch (Exception e) {
			log.error("update MobMandateReqRespJsonIdEntity error {}", e);
			ErrorLog.sendError(e, MobMandateReqRespJsonDaoImpl.class);
		}
	}

	@Override
	public long getIdPkByReqMsgId(String reqMsgId) {

		long idpk = 0;
		try {
			// TODO we can get by native
			List<MobMandateReqRespJsonIdEntity> enList = mobMandateReqRespJsonIdRepo.findByMsgid(reqMsgId);
			if (enList != null) {
				if (enList.size() == 1) {
					idpk = enList.get(0).getId();
				} else if (enList.size() == 0) {
					log.warn("MobMandateReqRespJsonIdEntity not found for reqMsgId={}", reqMsgId);
				} else {
					// TODO discussion
					log.error("more than one MobMandateReqRespJsonIdEntity found for reqMsgId={}", reqMsgId);
				}
			} else {
				log.warn("MobMandateReqRespJsonIdEntity not found for reqMsgId={}", reqMsgId);
			}
		} catch (Exception e) {
			log.error("get idpk from MobMandateReqRespJsonIdEntity error {}", e);
			ErrorLog.sendError(e, MobMandateReqRespJsonDaoImpl.class);
		}
		return idpk;
	}

	@Override
	public long getIdPkByTxnId(String txnId) {
		// TODO Auto-generated method stub
		log.warn("not implemented");
		return 0;
	}

	@Override
	public void updateDb(Ack ack, MobMandateReqRespJsonEntity mobMandateReqRespJson, String string) {
		// TODO Auto-generated method stub
		
	}
}
