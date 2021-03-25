package com.npst.upiserver.dao;

import com.npst.upiserver.npcischema.RespChkTxn;

public interface MobUpiReqRespJsonDao {
	void updateDb(Long id, RespChkTxn respChkTxn, String message);
}
