package com.npst.upiserver.service;

import com.npst.upiserver.entity.MobMandateReqRespJsonEntity;
import com.npst.upiserver.entity.MobUpiReqRespJson;


public interface MobIncomingService {
	void proMobReq(MobUpiReqRespJson jsonObj);
	void proMobReq(String msg);
	void proOnus(String msg);
	void proMandates(MobMandateReqRespJsonEntity mobMandateReqRespJson);
}
