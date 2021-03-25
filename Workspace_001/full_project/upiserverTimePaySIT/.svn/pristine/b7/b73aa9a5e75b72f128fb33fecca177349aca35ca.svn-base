/*package com.npst.upiserver.dao.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.MobOnusReqRespJsonDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.OnusMobReqRespJsonEntity;
import com.npst.upiserver.entity.OnusMobReqRespJsonIdEntity;
import com.npst.upiserver.npcischema.ResultType;
import com.npst.upiserver.repo.OnusMobReqRespJsonIdRepo;
import com.npst.upiserver.repo.OnusMobReqRespJsonRepo;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.JsonMan;

@Component
public class MobOnusReqRespJsonDaoImpl implements MobOnusReqRespJsonDao {
	private static final Logger log = LoggerFactory.getLogger(MobOnusReqRespJsonDaoImpl.class);

	@Autowired
	private OnusMobReqRespJsonRepo onusMobReqRespJsonRepo;
	@Autowired
	private OnusMobReqRespJsonIdRepo onusMobReqRespJsonIdRepo;

	@Override
	public void updateFail(OnusMobReqRespJsonEntity onusMobReqRespJson) {
		try {
			ReqResp respJson = new ReqResp();
			respJson.setMsg(ResultType.FAILURE.toString());
			respJson.setMsgId(ConstantI.MSG_ID_FAILURE);
			onusMobReqRespJson.setResp(JsonMan.getJSONStr(respJson));
			onusMobReqRespJson.setRespDate(new Date());
			onusMobReqRespJson.setFlag(ConstantI.STATUS_3);
			finalDbUpdate(onusMobReqRespJson);
		} catch (Exception e) {
			log.error("error :{}", e);
			ErrorLog.sendError(e, MobOnusReqRespJsonDaoImpl.class);
		}

	}

	@Override
	public void finalDbUpdate(OnusMobReqRespJsonEntity onusMobReqRespJson) {
		try {
			onusMobReqRespJsonRepo.save(onusMobReqRespJson);
			updateOnusMobReqRespJsonIdEntity(onusMobReqRespJson.getIdonusmobreqrespjson(),
					onusMobReqRespJson.getFlag());
		} catch (Exception e) {
			log.error("finalDbUpdate error {}", e);
			ErrorLog.sendError(e, MobOnusReqRespJsonDaoImpl.class);
		}

	}

	@Override
	public void updateOnusMobReqRespJsonIdEntity(long idpk, int flag) {
		try {
			// we can update by native query where idpk
			OnusMobReqRespJsonIdEntity en = onusMobReqRespJsonIdRepo.findByIdonusmobreqrespjsonid(idpk);
			if (en != null) {
				en.setFlag(flag);
				onusMobReqRespJsonIdRepo.save(en);
			} else {
				log.warn(" OnusMobReqRespJsonIdEntity is not found for idpk={}", idpk);
			}
		} catch (Exception e) {
			log.error("updateOnusMobReqRespJsonIdEntity error {}", e);
			ErrorLog.sendError(e, MobOnusReqRespJsonDaoImpl.class);
		}

	}
}
*/