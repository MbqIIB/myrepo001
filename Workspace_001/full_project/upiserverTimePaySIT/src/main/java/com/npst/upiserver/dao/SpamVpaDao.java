package com.npst.upiserver.dao;

import java.util.List;

import com.npst.upiserver.entity.SpamVpa;

public interface SpamVpaDao {
	boolean insert(String payeeAddr, Long regId, String txnId);
	List<SpamVpa> select(String spamvirtualid);
	
}
