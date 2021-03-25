package com.npst.upiserver.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.npst.upiserver.dao.SpamVpaDao;
import com.npst.upiserver.entity.SpamVpa;
import com.npst.upiserver.repo.SpamVpaRepository;

@Component
public class SpamVpaDaoImpl implements SpamVpaDao {
	private static final Logger log = LoggerFactory.getLogger(SpamVpaDaoImpl.class);
	
	@Autowired
	SpamVpaRepository spamVpaRepo;
	
	@Override
	public boolean insert(String payeeAddr, Long regId, String txnId) {
		try {
			SpamVpa spamvpa = new SpamVpa();
			spamvpa.setAddeddate(new Date());
			spamvpa.setRegid(regId.longValue());
			spamvpa.setTxnid(txnId);
			spamvpa.setSpamvirtualid(payeeAddr);
			spamVpaRepo.save(spamvpa);
			return true;
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
			return false;
		}
	}
	
	@Override
	public List<SpamVpa> select(String spamvirtualid) {
		List<SpamVpa> spamVpaList=new ArrayList<>();
	try {
		spamVpaList=spamVpaRepo.findBySpamvirtualid(spamvirtualid);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	return spamVpaList;
	}
	
}
