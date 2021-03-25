package com.npst.middleware.service.impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.middleware.cache.CBSErrorCache;
import com.npst.middleware.cbs.service.CBSProcess;
import com.npst.middleware.obj.ReqResp;
import com.npst.middleware.service.AccountVerification;
import com.npst.middleware.util.ConstantNew;
import com.npst.middleware.util.Util;

@Service
public class AccountVerificationImpl implements AccountVerification {
	private static final Logger LOG = LoggerFactory.getLogger(AccountVerificationImpl.class);
	private static final String ACCOUNT_VERICATION_ALLOW_CONSTANT =  Util.getProperty(ConstantNew.ACCOUNT_VERICATION_ALLOW_CONSTANT);
	@Autowired
	public CBSProcess			cbsProcess;

	@Override
	public ReqResp perform(ReqResp reqResp) {
		LOG.trace("Inside the Account Verification Method[" + reqResp + "]");
		try {
				if(!ConstantNew.CONST_Y.equalsIgnoreCase(ACCOUNT_VERICATION_ALLOW_CONSTANT)) {
					reqResp.setRespCode("XK"); 
					return reqResp;
				}else {
					reqResp = cbsProcess.accountVerification(reqResp);
				}
			
				if (reqResp.getRespCode().equalsIgnoreCase(ConstantNew.SUCCESS_CODE)) {
					reqResp.setRespCode(ConstantNew.SUCCESS_CODE_UPISERVER);
				} else if (reqResp.getRespCode().equalsIgnoreCase("DWN")) {
					reqResp.setRespCode("XY");
				}else if (reqResp.getRespCode().equalsIgnoreCase("XH")) {
					reqResp.setRespCode("XH");
				}else{
					String npcicode = CBSErrorCache.listAccoutErrorCode.get(reqResp.getRespCode());
				   	if(npcicode!=null){
				   		reqResp.setRespCode(npcicode);	
				   	}else{
				   		reqResp.setRespCode("XY"); 
				   	}
		        }
				LOG.debug("Getting Success Response from CBS--->"+reqResp);
				return reqResp;
			
		} catch (Exception e) {
			LOG.error("Exception caught inside the Account Verification method " + e);
		}
		LOG.info("end the Account Verification Method[" + reqResp + "]");
		return reqResp;
	}

}
