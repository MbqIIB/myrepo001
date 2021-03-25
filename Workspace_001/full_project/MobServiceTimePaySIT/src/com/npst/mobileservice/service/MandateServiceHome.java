package com.npst.mobileservice.service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;

import com.npst.mobileservice.dao.MandateHome;
import com.npst.mobileservice.object.MandatesHistory;
import com.npst.mobileservice.object.ReqJson;
import com.npst.mobileservice.util.JSONConvert;
import com.npst.upi.hor.MandatesHistoryEntity;

public class MandateServiceHome {
	private static final Logger					log							= Logger.getLogger(MandateServiceHome.class);
	MandateHome mandatehome = null;

	@SuppressWarnings("finally")
	public List<MandatesHistory> getAllMandatesReq(ReqJson reqJson) {
		// TODO Auto-generated method stub
		log.info("execution start of GetMandate {}"+"");
		List<MandatesHistoryEntity> list = null;
		List<MandatesHistory> reqrespauthdetailsVO = new ArrayList<>();
		try {
			if (null == mandatehome) {
				mandatehome = new MandateHome();
			}
			list = mandatehome.getAllmandates(Long.parseLong(reqJson.getRegId()));
			if (null!=list && !list.isEmpty()) {
				for (MandatesHistoryEntity mandates : list) {
					
					if(com.npst.mobileservice.util.Util.isMandateExpired(mandates)){
						MandatesHistory mandatesTxn=new MandatesHistory(mandates);
						reqrespauthdetailsVO.add(mandatesTxn);
						Collections.sort(reqrespauthdetailsVO, new MandatesHistory());
					 }	
			}
		}
		}
		catch (Exception ex) {
			log.info(ex.getMessage(),ex);
		} finally {
		return reqrespauthdetailsVO;
	}

}

	public List<MandatesHistory> getPendingMandatesReq(ReqJson reqStr) {
		// TODO Auto-generated method stub
		return null;
	}
}

