package com.npst.upiserver.dao.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.npst.upiserver.dao.RiskXHDao;
import com.npst.upiserver.entity.RiskXH;
import com.npst.upiserver.repo.RiskXHRepository;

@Component
public class RiskXHDaoImpl implements RiskXHDao {
	
	private static final Logger	log	= LoggerFactory.getLogger(RiskXHDaoImpl.class);
	@Autowired
	RiskXHRepository riskXHRepo;

	
	@Override
	public boolean saveUpdateRiskDetails(RiskXH riskDetails) {
		log.debug("riskDetails {}", riskDetails);
		try {
			riskXHRepo.save(riskDetails);
			return true;
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}
	

	@Override
	public long getXHCountByRegId(long regId,Date lastlogindt) {
		log.debug("regId {} ", regId);
		Long count=0l;
		try {
			count=riskXHRepo.findCountByRegidAndCreateUpdDate(regId,lastlogindt);
			return count;
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
			return count;
		}
	}

	@Override
	public RiskXH getXHDetailsByRegId(long regId) {
		log.debug("regId {} ", regId);
		RiskXH xhDetails=null;
		try {
			xhDetails=riskXHRepo.findByRegid(regId);
			return xhDetails;
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}	
}