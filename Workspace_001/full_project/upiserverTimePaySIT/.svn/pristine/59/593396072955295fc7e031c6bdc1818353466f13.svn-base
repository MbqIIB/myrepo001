package com.npst.upiserver.dao.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.MobReqRespJsonDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.MobUpiReqRespJson;
import com.npst.upiserver.entity.Mobupireqrespjsonid;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.ResultType;
import com.npst.upiserver.repo.MobUpiReqRespJsonIdRepository;
import com.npst.upiserver.repo.MobUpiReqRespJsonRepository;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.JsonMan;

@Component
public class MobReqRespJsonDaoImpl implements MobReqRespJsonDao {
	private static final Logger log = LoggerFactory.getLogger(MobReqRespJsonDaoImpl.class);

	@Autowired
	private MobUpiReqRespJsonRepository mobUpiReqRespJsonRepo;
	@Autowired
	private MobUpiReqRespJsonIdRepository mobUpiReqRespJsonIdRepo;

	@Override
	public void updateMsgId(String msgId, long idPk) {
		updateMobUpiReqRespJsonIdEntity(idPk, msgId);
	}

	@Override
	public void finalDbUpdate(ReqResp respJson, long idPk) {
		try {
			MobUpiReqRespJson mobreqrespjson = mobUpiReqRespJsonRepo.findByIdPk(idPk);
			if (mobreqrespjson == null) {
				log.error("mobreqrespjson not found for idPk={} ,ReqResp={}", idPk, JsonMan.getJSONStr(respJson));
				ErrorLog.sendError("mobreqrespjson not found for idPk ",
						new String[] { String.valueOf(idPk), JsonMan.getJSONStr(respJson) },
						MobReqRespJsonDaoImpl.class);
			} else {
				mobreqrespjson.setResp(JsonMan.getJSONStr(respJson));
				mobreqrespjson.setRespDate(new Date());
				mobreqrespjson.setFlag(ConstantI.STATUS_3);
				mobUpiReqRespJsonRepo.save(mobreqrespjson);
				updateMobUpiReqRespJsonIdEntity(mobreqrespjson.getIdPk(), mobreqrespjson.getFlag());
			}
		} catch (Exception e) {
			log.error("finalDbUpdate error {}", e);
			ErrorLog.sendError(e, MobReqRespJsonDaoImpl.class);
		}

	}
	/*
@Override
public void finalDbUpdate(MobUpiReqRespJson mobreqrespjson,) {
try {
mobUpiReqRespJsonRepo.save(mobreqrespjson);
updateMobUpiReqRespJsonIdEntity(mobreqrespjson.getIdPk(), mobreqrespjson.getFlag());
} catch (Exception e) {
log.error("finalDbUpdate error {}", e);
ErrorLog.sendError(e, MobReqRespJsonDaoImpl.class);
}

}*/

	@Override
	public void updateDb(Ack ack, MobUpiReqRespJson mobreqrespjson, String txnType) {
		try {
			ReqResp respJson = new ReqResp();
			respJson.setTxnType(txnType);
			if (null != ack.getErr() || 0 != ack.getErrorMessages().size()) {
				respJson.setUpiErrorCode(JsonMan.getJSONStr(ack));
				respJson.setMsg(Constant.ACK_ERROR);
				respJson.setMsgId(ConstantI.MSG_ID_FAILURE);
			} else {
				respJson.setMsg(ResultType.SUCCESS.toString());
				respJson.setMsgId(ConstantI.MSG_ID_SUCCESS);
			}
			mobreqrespjson.setResp(JsonMan.getJSONStr(respJson));
			mobreqrespjson.setRespDate(new Date());
			mobreqrespjson.setFlag(ConstantI.STATUS_3);
			mobUpiReqRespJsonRepo.save(mobreqrespjson);
			updateMobUpiReqRespJsonIdEntity(mobreqrespjson.getIdPk(), mobreqrespjson.getFlag());
		} catch (Exception e) {
			log.error("error {}", e);
			ErrorLog.sendError(e, MobReqRespJsonDaoImpl.class);
		}

	}

	/*@Override
public void updateFail(MobUpiReqRespJson mobreqrespjson) {
try {
ReqResp respJson = new ReqResp();
respJson.setMsgId(ConstantI.MSG_ID_FAILURE);
respJson.setMsg(ConstantI.MSG_ID_FAILURE);
mobreqrespjson.setResp(JsonMan.getJSONStr(respJson));
mobreqrespjson.setFlag(2);
mobUpiReqRespJsonRepo.save(mobreqrespjson);
updateMobUpiReqRespJsonIdEntity(mobreqrespjson.getIdPk(), mobreqrespjson.getFlag());
} catch (Exception e) {
log.error("error cause={}", e.getMessage());
ErrorLog.sendError(e, MobReqRespJsonDaoImpl.class);
}

}*/

	@Override
	public void updateMobUpiReqRespJsonIdEntity(long idpk, int flag) {
		try {
			// we can update by native query where idpk
			Mobupireqrespjsonid en = mobUpiReqRespJsonIdRepo.findByIdmobreqrespjsonid(idpk);
			if (en != null) {
				en.setFlag(flag);
				mobUpiReqRespJsonIdRepo.save(en);
			} else {
				log.warn(" MobUpiReqRespJsonIdEntity is not found for idpk={}", idpk);
			}
		} catch (Exception e) {
			log.error("updateMobUpiReqRespJsonIdEntity error {}", e);
			ErrorLog.sendError(e, MobReqRespJsonDaoImpl.class);
		}

	}

	@Override
	public void updateMobUpiReqRespJsonIdEntity(long idpk, String msgId) {
		try {
			// we can update by native query where idpk
			Mobupireqrespjsonid en = mobUpiReqRespJsonIdRepo.findByIdmobreqrespjsonid(idpk);
			if (en != null) {
				en.setMsgid(msgId);
				mobUpiReqRespJsonIdRepo.save(en);
			} else {
				log.warn(" MobUpiReqRespJsonIdEntity is not found for idpk={}", idpk);
			}
		} catch (Exception e) {
			log.error("error {}", e);
			ErrorLog.sendError(e, MobReqRespJsonDaoImpl.class);
		}

	}

	/*@Override
public void updateRespCacheListKeys(MobUpiReqRespJson mobreqrespjson) {
// TODO Auto-generated method stub

}

@Override
public void updateRespCacheListPSPKeys(MobUpiReqRespJsonEntity mobreqrespjson) {
// TODO Auto-generated method stub

}*/

	@Override
	public long getIdPkByReqMsgId(String reqMsgId) {

		long idpk = 0;
		try {
			// TODO we can get by native
			List<Mobupireqrespjsonid> enList = mobUpiReqRespJsonIdRepo.findByMsgid(reqMsgId);
			if (enList != null) {
				if (enList.size() == 1) {
					idpk = enList.get(0).getIdmobreqrespjsonid();
				} else if (enList.size() == 0) {
					log.warn("MobUpiReqRespJsonIdEntity not found for reqMsgId={}", reqMsgId);
				} else {
					// TODO discussion
					log.error("more than one MobUpiReqRespJsonIdEntity found for reqMsgId={}", reqMsgId);
				}
			} else {
				log.warn("MobUpiReqRespJsonIdEntity not found for reqMsgId={}", reqMsgId);
			}
		} catch (Exception e) {
			log.error("error {}", e);
			ErrorLog.sendError(e, MobReqRespJsonDaoImpl.class);
		}
		return idpk;
	}

	@Override
	public MobUpiReqRespJson getByIdPk(long idPk) {
		try {
			return mobUpiReqRespJsonRepo.findByIdPk(idPk);
		} catch (Exception e) {
			log.error("error {}", e);
			ErrorLog.sendError(e, MobReqRespJsonDaoImpl.class);
		}
		return null;
	}

	@Override
	public boolean isPreApprovedTxn(long idPk) {
		try {
			if (mobUpiReqRespJsonRepo.countByIdPkAndTypeIgnoreCase(idPk, ConstantI.PREAPPROVED) == 1) {
				return true;
			}
		} catch (Exception e) {
			log.error("error {}", e);
			ErrorLog.sendError(e, MobReqRespJsonDaoImpl.class);
		}
		return false;
	}

}