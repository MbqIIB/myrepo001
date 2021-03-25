package com.npst.upiserver.service;

import com.npst.upiserver.entity.Registration;
import com.npst.upiserver.npcischema.ReqAuthDetails;

public interface GCMService {
	void sendMessage(ReqAuthDetails reqAuthDetails, Registration regVpa);
}
