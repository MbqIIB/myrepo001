package com.npst.upiserver.dao;

import java.util.List;

import com.npst.upiserver.entity.SpamVpaRule;

public interface SpamVpaRuleDao {
	List<SpamVpaRule> selectSpamVpa(String payeeAddr);
	void insert(String payeeAddr);
	List<SpamVpaRule> select(String payeeAddr);
	List<String> getSpamVpa(String vpa);
}
