package com.npst.upiserver.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.SpamVpaRuleDao;
import com.npst.upiserver.entity.SpamVpaRule;
import com.npst.upiserver.repo.SpamVpaRuleRepository;
import com.npst.upiserver.util.ErrorLog;

@Component
public class SpamVpaRuleDaoImpl implements SpamVpaRuleDao {
	private static final Logger log = LoggerFactory.getLogger(SpamVpaRuleDaoImpl.class);
	
	@Autowired
	SpamVpaRuleRepository spamVpaRule;
	
	@Override
	public List<String> getSpamVpa(String vpa) {
		try {
			return spamVpaRule.getSpamvpaRule(vpa, new Date());
		} catch (Exception e) {
			log.error("error in fetching SpamVpa from spam_Vpa_Rule {}", e);
			ErrorLog.sendError(e, SpamVpaRuleDaoImpl.class);
		}
		return null;
	}
	
	@Override
	public List<SpamVpaRule> selectSpamVpa(String payeeAddr) {
		List<SpamVpaRule> spamList=new ArrayList<>();
		try {
			spamList=spamVpaRule.findBySpamvpa(payeeAddr);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return spamList;
	}

	@Override
	public void insert(String payeeAddr) {
		try {
			SpamVpaRule spamvparule = new SpamVpaRule();
			spamvparule.setRuleapplieddate(new Date());
			spamvparule.setSpamvpa(payeeAddr);
			List<SpamVpaRule> result = select(payeeAddr);
			Calendar c = Calendar.getInstance();
			if (0 < result.size()) {
				for (SpamVpaRule spamvparule2 : result) {
					if (ConstantI.CONST_SPAM_1.equalsIgnoreCase(spamvparule2.getRule())) {
						spamvparule.setRule(ConstantI.CONST_SPAM_2);
						c.setTime(new Date());
						c.add(Calendar.DATE, 7);
						spamvparule.setRuleexpireddate(c.getTime());
					} else if (ConstantI.CONST_SPAM_2.equalsIgnoreCase(spamvparule2.getRule())) {
						spamvparule.setRule(ConstantI.CONST_SPAM_3);
						c.setTime(new Date());
						c.add(Calendar.DATE, 180);
						spamvparule.setRuleexpireddate(c.getTime());
					}
				}
			} else {
				spamvparule.setRule(ConstantI.CONST_SPAM_1);
				c.setTime(new Date());
				c.add(Calendar.DATE, 1);
				spamvparule.setRuleexpireddate(c.getTime());
			}
			spamVpaRule.save(spamvparule);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	@Override
	public List<SpamVpaRule> select(String payeeAddr) {
		List<SpamVpaRule> spamList=new ArrayList<>();
		try {
			spamList=spamVpaRule.findBySpamvpa(payeeAddr);
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return spamList;
	}
	
	
}
