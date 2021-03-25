package com.npst.middleware.core.service.impl;

import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.npst.middleware.core.service.HsmRemoteService;
import com.npst.middleware.obj.CryptoResp;
import com.npst.middleware.obj.RemoteHsmReq;
import com.npst.middleware.util.Constant;
import com.npst.middleware.util.Util;

@Service
public class HsmRemoteServiceImpl implements HsmRemoteService {
	private static final Logger log = LoggerFactory.getLogger(HsmRemoteServiceImpl.class);
	@Autowired
	private RestTemplate restTemp;
	// @Value("${REMOTE_HSM_URL}")
	private static final String remoteHsmUrl = Util.getProperty("REMOTE_HSM_URL");

	@Override
	public CryptoResp decrypt(String base64) {
		CryptoResp resp = null;
		try {
			RemoteHsmReq req = new RemoteHsmReq();
			req.setBase64Data(base64);
			ResponseEntity<CryptoResp> httpResp = restTemp.postForEntity(remoteHsmUrl, req, CryptoResp.class);
			if (HttpStatus.OK == httpResp.getStatusCode()) {
				resp = httpResp.getBody();
				if (Constant.SUCCESS_STATUS_ZERO.equals(resp.getStatus())) {
					resp.setData(new String(Base64.getDecoder().decode(resp.getData().getBytes()), "UTF-8"));
				}
			} else {
				log.trace("");
				resp = new CryptoResp();
				resp.setRespMsg("FAILURE");
				resp.setStatus(Constant.FAILURE_STATUS_ONE);
			}
		} catch (Exception e) {
			resp = new CryptoResp();
			resp.setRespMsg("FAILURE");
			resp.setStatus(Constant.FAILURE_STATUS_ONE);
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return resp;
	}

}
