package com.npst.middleware.core.service.impl;

import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.npst.middleware.core.service.CryptoService;
import com.npst.middleware.core.service.HsmRemoteService;

import com.npst.middleware.hsm.HSMUtil;
import com.npst.middleware.obj.CryptoResp;
import com.npst.middleware.util.Constant;
import com.npst.middleware.util.Util;


@Service
public class CryptoServiceImpl implements CryptoService {
	private static final Logger log = LoggerFactory.getLogger(CryptoServiceImpl.class);
	final static boolean isHsm = "YES".equalsIgnoreCase(Util.getProperty("HSM_PRODUCTION"));
	final static boolean isHSM_REMOTE ="YES".equalsIgnoreCase(Util.getProperty("IS_HSM_REMOTE"));

	@Autowired
	HsmRemoteService hsmRemoteService;

	@Override
	public CryptoResp decrypt(String base64Data) {
		log.trace("");
		CryptoResp decryptResp = null;
		String temp = null;
		try {
			if (isHsm) {
				log.debug("<----------HSM ENABLE and PIN set by HSM------>");
				decryptResp = new CryptoResp();
				temp = HSMUtil.decryptData(Base64.getDecoder().decode(base64Data));
				if (temp == null || temp.isEmpty()) {
					decryptResp.setStatus(Constant.FAILURE_STATUS_ONE);
					decryptResp.setRespMsg("FAILURE");
				} else {
					decryptResp.setStatus(Constant.SUCCESS_STATUS_ZERO);
					decryptResp.setData(temp);
				}
			} else if (isHSM_REMOTE) {
				log.debug("-----<REMOTE_HSM ENABLED----->");
				decryptResp = hsmRemoteService.decrypt(base64Data);

			} else {
				log.debug("<-----UPIPIN Without HSM----->");
				decryptResp = new CryptoResp();
				temp = Util.decrypt(Base64.getDecoder().decode(base64Data));
				if (temp == null || temp.isEmpty()) {
					decryptResp.setStatus(Constant.FAILURE_STATUS_ONE);
					decryptResp.setRespMsg("FAILURE");
				} else {
					decryptResp.setStatus(Constant.SUCCESS_STATUS_ZERO);
					decryptResp.setData(temp);
				}

			}
		} catch (Exception e) {
			log.error("{}", e);
			decryptResp = new CryptoResp();
			decryptResp.setStatus(Constant.FAILURE_STATUS_ONE);
			decryptResp.setRespMsg("FAILURE");

		}
		return decryptResp;
	}
}
