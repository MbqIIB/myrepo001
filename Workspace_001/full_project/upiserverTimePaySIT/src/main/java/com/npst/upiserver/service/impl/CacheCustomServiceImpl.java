package com.npst.upiserver.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.npst.upiserver.dao.ReqRespListAccPvdDao;
import com.npst.upiserver.service.CacheCustomService;
import com.npst.upiserver.util.ErrorLog;

@Component
public class CacheCustomServiceImpl implements CacheCustomService {
	private static final Logger log = LoggerFactory.getLogger(CacheCustomServiceImpl.class);
	@Autowired
	private ReqRespListAccPvdDao reqRespListAccPvdDao;

	@Override
	public String getBankNameByIfsc(String ifsc) {
		if (ifsc == null) {
			return null;
		} else {
			ifsc = ifsc.toUpperCase();
		}
		try {
			if(reqRespListAccPvdDao.getIfscAndNameCache().keySet()!=null) {
				for (String ifscPre : reqRespListAccPvdDao.getIfscAndNameCache().keySet()) {
					if (ifsc.startsWith(ifscPre)) {
						return reqRespListAccPvdDao.getIfscAndNameCache().get(ifscPre);
					}
				}
			}
			else {
				return ""; // to do 
			}
		} catch (Exception e) {
			ErrorLog.sendError(e, CacheCustomServiceImpl.class);
			log.error("error in cache getBankNameByIfsc  {}", e);
		}
		return null;
	}

	@Override
	public void refreshIfscAndBankNameCache() {
		// call from scheduler

	}

	@Override
	public String getListKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void refreshListKeys() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getListPSPKeys(String pspOrgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getListPSPKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void refreshListPSPKeys() {
		// TODO Auto-generated method stub

	}
}
