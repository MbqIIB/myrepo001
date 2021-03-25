package com.npst.upiserver.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.npst.upiserver.entity.Registration;
import com.npst.upiserver.npcischema.ReqAuthDetails;
import com.npst.upiserver.service.GCMService;

@Service
public class GCMServiceImpl implements GCMService {
	private static final Logger	log	= LoggerFactory.getLogger(GCMServiceImpl.class);
	
	
	@Override
	public void sendMessage(ReqAuthDetails reqAuthDetails, Registration regVpa) {
		try {
			// to do notofication code here
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
}
