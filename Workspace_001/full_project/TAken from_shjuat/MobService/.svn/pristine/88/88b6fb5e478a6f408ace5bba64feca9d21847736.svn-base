package com.npst.mobileservice.service;
import java.util.Date;

import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;


import com.npst.mobileservice.dao.MandateMobupireqrespjsonidHome;
import com.npst.upi.hor.MobMandateReqRespJsonEntity;
import com.npst.upi.hor.MobMandateReqRespJsonIdEntity;

public class MandateMobupireqrespjsonidService {
	private static final Logger				log	= Logger.getLogger(MandateMobupireqrespjsonidService.class);
	private static MandateMobupireqrespjsonidHome mandateMobupireqrespjsonidHome=null;
	
	public MobMandateReqRespJsonIdEntity findById(Long id) {
		log.info("Execution start of findById");
		try {
			if (null == mandateMobupireqrespjsonidHome) {
				mandateMobupireqrespjsonidHome = new MandateMobupireqrespjsonidHome();
			}
			return mandateMobupireqrespjsonidHome.findById(id);
		} catch (Exception ex) {
			log.info(ex.getMessage(),ex);
			
		}
		return null;
		
	}
	
	public void save(MobMandateReqRespJsonEntity mobupireqrespjson) {
		log.info("Execution start of save(mobupireqrespjson)");
		log.info("Execution date time of save(mobupireqrespjson)"+new Date());
		try {
			if (null == mandateMobupireqrespjsonidHome) {
				mandateMobupireqrespjsonidHome = new MandateMobupireqrespjsonidHome();
			}
			MobMandateReqRespJsonIdEntity mandateMobupireqrespjsonid = new MobMandateReqRespJsonIdEntity();
			mandateMobupireqrespjsonid.setFlag(mobupireqrespjson.getFlag());
			mandateMobupireqrespjsonid.setId(mobupireqrespjson.getIdPk());
			mandateMobupireqrespjsonid.setServiceType("UPI_MANDATE");
			mandateMobupireqrespjsonidHome.save(mandateMobupireqrespjsonid);
			log.info("Date & time of after save(mobupireqrespjson)"+new Date());
			log.info("in mobupireqrespjsonid Idmobreqrespjsonid= "+mandateMobupireqrespjsonid.getId());
		} catch (Exception ex) {
			log.info("Some error occured while Save mobupireqrespjson:{}"+ex);
			
		}
		
	}
}
