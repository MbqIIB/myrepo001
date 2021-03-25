package com.npst.mobileservice.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.npst.mobileservice.dao.ReqrespauthdetailsHome;
import com.npst.mobileservice.dao.ReqrespauthdetailsPayeesHome;
import com.npst.mobileservice.object.ReqJson;
import com.npst.mobileservice.object.ReqrespauthdetailsVO;
import com.npst.mobileservice.util.ConstantI;
import com.npst.mobileservice.util.JSONConvert;
import com.npst.mobileservice.util.Util;
import com.npst.upi.hor.Reqrespauthdetails;
import com.npst.upi.hor.ReqrespauthdetailsPayees;

public class ReqrespauthdetailsService {
	private static final Logger		log						= Logger.getLogger(ReqrespauthdetailsService.class);
	ReqrespauthdetailsHome			reqrespauthdetailsHome	= null;
	ReqrespauthdetailsPayeesHome	payeeHome				= null;
	SimpleDateFormat				dtFormat				= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public Integer getPendingCollectCount(ReqJson reqJson) {
		Integer count = 0;
		try {
			if (null == reqrespauthdetailsHome) {
				reqrespauthdetailsHome = new ReqrespauthdetailsHome();
			}
			count = reqrespauthdetailsHome.getPendingCollectCount(Integer.parseInt(reqJson.getRegId()));
		} catch (Exception e) {
			log.info(e.getMessage(), e);
		} finally {
		}
		return count;
	}
	
	public List<ReqrespauthdetailsVO> getPendingCollect(ReqJson reqJson) {
		List<Reqrespauthdetails> list = null;
		List<ReqrespauthdetailsPayees> listPayees = null;
		List<ReqrespauthdetailsVO> reqrespauthdetailsVO = new ArrayList<>();
		String txnId = "";
		try {
			if (null == reqrespauthdetailsHome) {
				reqrespauthdetailsHome = new ReqrespauthdetailsHome();
			}
			list = reqrespauthdetailsHome.getPendingCollect(Integer.parseInt(reqJson.getRegId()));
			if (0 < list.size()) {
				for (Reqrespauthdetails reqrespauthdetails : list) {
					
					long reqDateInMins = reqrespauthdetails.getReqInsert().getTime();
					Date collectTime = new Date(
							reqDateInMins + (reqrespauthdetails.getRuleExpireAfter() * ConstantI.MILLIS_IN_MINUTE));
					int dateDiff = Util.compareDateWithCurrntDate(collectTime);
					if (dateDiff >= 0) {
						txnId = reqrespauthdetails.getTxnId();
						if (null == payeeHome) {
							payeeHome = new ReqrespauthdetailsPayeesHome();
						}
						listPayees = payeeHome.getPayee(txnId);
						ReqrespauthdetailsVO reqrespauthdetailsVO2 = new ReqrespauthdetailsVO(reqrespauthdetails);
						reqrespauthdetailsVO2.setCollectTime(dtFormat.format(collectTime));
						reqrespauthdetailsVO2.setPayees(listPayees);
						//for C-170 ,will manage for verified merchant
						reqrespauthdetailsVO2.setTxnNote("");
						reqrespauthdetailsVO.add(reqrespauthdetailsVO2);
					}
				}
			}
			
		} catch (Exception e) {
			StringWriter s;
			e.printStackTrace(new PrintWriter(s = new StringWriter()));
			e.printStackTrace();
			log.info(s);
		} finally {
			
		}
		return reqrespauthdetailsVO;
	}
	
}
