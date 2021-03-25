package com.npst.upiserver.dao;


public interface MobReqRespJsonIdDao {
	Long getMobReqRespJsonId(String msgId);
	void updateRecords(Long idmobreqrespjsonid, int flag);
	void updateMsgId(String msgId, Long idmobreqrespjsonid);
}
