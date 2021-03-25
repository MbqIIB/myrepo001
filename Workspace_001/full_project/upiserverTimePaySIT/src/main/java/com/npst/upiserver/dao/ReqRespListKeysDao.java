package com.npst.upiserver.dao;

import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.ReqListKeys;
import com.npst.upiserver.npcischema.RespListKeys;

public interface ReqRespListKeysDao {
	//void updateResp(RespListKeys respListKeys);
	void insertReq(ReqListKeys reqListKeys, Ack ack);

	void updateResp(RespListKeys respListKeys);
}
