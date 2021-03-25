package com.npst.upiserver.dao;

import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.ReqAuthMandate;
import com.npst.upiserver.npcischema.RespAuthMandate;

public interface MandatesHistoryDao {

	void insert(ReqAuthMandate reqAuthMandate);

	void update(RespAuthMandate respAuthMandate, Ack ack);
	void update(RespAuthMandate respAuthMandate, Ack ack, ReqResp reqJson);

}
