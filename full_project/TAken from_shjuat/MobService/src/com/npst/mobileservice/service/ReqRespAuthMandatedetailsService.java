package com.npst.mobileservice.service;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.npst.mobileservice.dao.ReqRespAuthMandatedetailsHome;
import com.npst.mobileservice.dao.ReqRespAuthMandatedetailsPayeesHome;
import com.npst.mobileservice.object.ReqJson;
import com.npst.mobileservice.object.ReqRespAuthMandatePayeesdetailsVO;
import com.npst.mobileservice.object.ReqRespAuthMandatedetailsVO;
import com.npst.mobileservice.util.ConstantI;
import com.npst.mobileservice.util.JSONConvert;
import com.npst.upi.hor.ReqRespAuthMandateEntity;
import com.npst.upi.hor.ReqRespAuthMandatePayeesEntity;


public class ReqRespAuthMandatedetailsService {
	private static final Logger					log							= Logger.getLogger(ReqRespAuthMandatedetailsService.class);
	
	ReqRespAuthMandatedetailsHome reqRespAuthMandatedetailsHome=null;
	ReqRespAuthMandatedetailsPayeesHome reqRespAuthMandatedetailsPayeesHome=null;
	
	public List<ReqRespAuthMandatedetailsVO> getPendingMandateCollect(ReqJson reqJson) {
		log.info("execution start of getPendingMandateCollect {}"+"");
		List<ReqRespAuthMandateEntity> reqRespAuthMandateslist = null;
		List<ReqRespAuthMandatePayeesEntity> reqRespAuthMandatePayees = null;
		List<ReqRespAuthMandatedetailsVO> reqRespAuthMandatedetailsVO = new ArrayList<>();
		List<ReqRespAuthMandatePayeesdetailsVO> respAuthMandatePayeeslist=new ArrayList<>();
		String txnId = "";
		try {
			if (null == reqRespAuthMandatedetailsHome) {
				reqRespAuthMandatedetailsHome = new ReqRespAuthMandatedetailsHome();
			}
			reqRespAuthMandateslist = reqRespAuthMandatedetailsHome.getPendingMandateCollect(Long.parseLong(reqJson.getRegId()),reqJson.getType());
			if (null!=reqRespAuthMandateslist && !reqRespAuthMandateslist.isEmpty()) {
				for (ReqRespAuthMandateEntity reqrespauthmandate : reqRespAuthMandateslist) {
					txnId = reqrespauthmandate.getTxnId();
					if (null == reqRespAuthMandatedetailsPayeesHome) {
						reqRespAuthMandatedetailsPayeesHome = new ReqRespAuthMandatedetailsPayeesHome();
					}
					reqRespAuthMandatePayees = reqRespAuthMandatedetailsPayeesHome.getPayee(txnId);
					long reqDateInMins = reqrespauthmandate.getReqInsert().getTime();
					Date collectTime = new Date(reqDateInMins + (Integer.valueOf(reqrespauthmandate.getRuleExpireAfter()) * ConstantI.MILLIS_IN_MINUTE));
					ReqRespAuthMandatedetailsVO reqrespauthdetailsVO2 = new ReqRespAuthMandatedetailsVO(reqrespauthmandate);
					reqrespauthdetailsVO2.setCollectTime(collectTime);
					reqrespauthdetailsVO2.setPayeedetails(reqrespauthdetailsVO2,reqRespAuthMandatePayees);
					//respAuthMandatePayeeslist = reqRespAuthMandatePayees.stream().map(item -> mapEntityToVO(item)).collect(Collectors.toList());
					//reqrespauthdetailsVO2.setPayees(respAuthMandatePayeeslist);
					reqRespAuthMandatedetailsVO.add(reqrespauthdetailsVO2);
					Collections.sort(reqRespAuthMandatedetailsVO, new ReqRespAuthMandatedetailsVO());
				}
			}

		} catch (Exception ex) {
			log.info(ex.getMessage(),ex);
			ex.printStackTrace();
		} 
		return reqRespAuthMandatedetailsVO;
	}

	private ReqRespAuthMandatePayeesdetailsVO mapEntityToVO(ReqRespAuthMandatePayeesEntity item) {
		ReqRespAuthMandatePayeesdetailsVO vo = new ReqRespAuthMandatePayeesdetailsVO();
		try {
			BeanUtils.copyProperties(vo, item);
		} catch (IllegalAccessException | InvocationTargetException ex) {
			log.info(ex.getMessage(),ex);
		}
		return vo;
	}

	public int getPendingMandateCollectCountByRegId(long regId) {
		log.info("execution start of getPendingMandateCollectCountByRegId {}"+"");
		if (null == reqRespAuthMandatedetailsHome) {
			reqRespAuthMandatedetailsHome = new ReqRespAuthMandatedetailsHome();
		}
		log.info("fetching count of pendingcollect for regid {}"+regId);
		return reqRespAuthMandatedetailsHome.getPendingMandateCollectCountByRegId(regId);
	}
}
