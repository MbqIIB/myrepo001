package com.npst.upiserver.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.npst.upiserver.dao.MobReqRespJsonIdDao;
import com.npst.upiserver.entity.Mobupireqrespjsonid;
import com.npst.upiserver.repo.MobReqRespJsonIdRepository;
import com.npst.upiserver.repo.MobUpiReqRespJsonIdRepository;

@Component
public class MobReqRespJsonIdDaoImpl implements MobReqRespJsonIdDao {

	private static final Logger log = LoggerFactory.getLogger(MobReqRespJsonIdDaoImpl.class);
			
	@Autowired
	MobReqRespJsonIdRepository mobReqRespJsonId;
	
	@Autowired
	MobUpiReqRespJsonIdRepository mobUpiReqRespJsonIdRepo;
	
	@Override
	public Long getMobReqRespJsonId(String msgId) {
		Long mobReqRespJsonIdpK=0l;
		try {
			log.info("msgId is as {} ",msgId);
			mobReqRespJsonIdpK=mobReqRespJsonId.getIdmobreqrespjsonidByMsgid(msgId);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return mobReqRespJsonIdpK;
	}

	@Override
	public void updateRecords(Long idmobreqrespjsonid, int flag) {
		try {
			log.info("second update");
			Mobupireqrespjsonid logmobrecid=mobUpiReqRespJsonIdRepo.findByIdmobreqrespjsonid(idmobreqrespjsonid);
			if (null != logmobrecid) {
				logmobrecid.setFlag(flag);
				mobUpiReqRespJsonIdRepo.save(logmobrecid);
				log.info("updated");
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	@Override
	public void updateMsgId(String msgId, Long idmobreqrespjsonid) {
		try {
			Mobupireqrespjsonid logmobrecid=mobUpiReqRespJsonIdRepo.findByIdmobreqrespjsonid(idmobreqrespjsonid);
			if(logmobrecid!=null) {
				logmobrecid.setMsgid(msgId);
				mobUpiReqRespJsonIdRepo.save(logmobrecid);
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
}
