package com.npst.middleware.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.middleware.obj.ReqResp;
import com.npst.middleware.service.PinCheckService;
import com.npst.middleware.upipin.service.UpiPinProcess;
import com.npst.middleware.util.ConstantNew;

@Service
public class PinCheckServiceImpl implements PinCheckService {
	private static final Logger LOG = LoggerFactory.getLogger(PinCheckServiceImpl.class);

	@Autowired
	private UpiPinProcess upiPinProcess;

	@Override
	public ReqResp perform(ReqResp reqResp) {
		LOG.trace("Inside the pinCheckService  Method {}", reqResp);
		try {
			String upiErrorCode = upiPinProcess.upiPinValidate(reqResp.getCredMpin(), reqResp.getPayerDeviceMobile(),
					reqResp.getPayerAcNum());
			if (ConstantNew.SUCCESS_CODE.equalsIgnoreCase(upiErrorCode)) {
				reqResp.setRespCode(ConstantNew.SUCCESS_CODE_UPISERVER);
			} else {
				reqResp.setRespCode(upiErrorCode);
			}
		} catch (Exception e) {
			LOG.error("Exception caught inside the pinCheckService method {}", e);
		} finally {
			LOG.info("End the pinCheckService fetch Method{}", reqResp);
		}

		return reqResp;
	}

}