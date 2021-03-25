package com.npst.upiserver.dao;

import java.util.List;

import com.npst.upiserver.dto.ReqJson;
import com.npst.upiserver.entity.ListAccount;
import com.npst.upiserver.entity.Regvpa;

public interface RegVpaDao {
	void delete(ReqJson reqJson);

	List<Regvpa> findName(String virtualId);

	String getEmail(String mobileNo);
	
	Regvpa getGCMToken(List<ListAccount> listaccounts);
	
	String getGCMToken(String mobileNo);
	
	boolean isActiveUser(List<ListAccount> listaccounts);
}
