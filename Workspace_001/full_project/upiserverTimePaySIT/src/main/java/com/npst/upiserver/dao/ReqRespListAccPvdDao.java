package com.npst.upiserver.dao;

import java.util.concurrent.ConcurrentMap;

import com.npst.upiserver.npcischema.ReqListAccPvd;
import com.npst.upiserver.npcischema.RespListAccPvd;

public interface ReqRespListAccPvdDao {

	void insert(ReqListAccPvd reqListAccPvd);

	void updateResp(RespListAccPvd respListAccPvd);
	
	ConcurrentMap<String, String> getIfscAndNameCache();
	void refreshIfscAndBankNameCache();
}
