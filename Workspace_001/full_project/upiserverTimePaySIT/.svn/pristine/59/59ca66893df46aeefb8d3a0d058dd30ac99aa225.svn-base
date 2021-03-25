package com.npst.upiserver.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.UpiReqValAddService;
import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.issuer.service.IssuerUpiReqValAddService;
import com.npst.upiserver.npcischema.ReqValAdd;
import com.npst.upiserver.service.CommonUpiReqValAddService;
import com.npst.upiserver.util.ErrorLog;

@Service
public class CommonUpiReqValAddServiceImpl implements CommonUpiReqValAddService {
	private static final Logger log = LoggerFactory.getLogger(CommonUpiReqValAddServiceImpl.class);
	@Autowired
	private IssuerUpiReqValAddService issuerUpiReqValAddService;
	@Autowired
	private UpiReqValAddService upiReqValAddService;

	@Override
	public void commonProcess(final ReqValAdd reqValAdd) {
		log.debug("common reqValAdd {}", reqValAdd);
		if (reqValAdd == null) {
			return;
		}
		try {
			String vpa = reqValAdd.getPayee().getAddr();
			log.debug("Vpa is as for valAdd {} ",vpa);
			if (vpa != null && !vpa.isEmpty()) {
				vpa = vpa.toLowerCase().trim();
				log.debug("Vpa is as for valAdd {} ",vpa.trim());
				log.debug("BANK_HANDAL={} ",Constant.BANK_HANDAL);
				log.debug("TIMEPAY_HANDAL={} ",Constant.TIMEPAY_HANDAL);
				if (vpa.trim().contains(Constant.BANK_HANDAL)||vpa.trim().contains(Constant.TIMEPAY_HANDAL)) {
					log.info("In Bank Handle");
					upiReqValAddService.acquirerProcess(reqValAdd);
				} else {
					log.info("else ");
					issuerUpiReqValAddService.issuerProcess(reqValAdd);
				}

			} else {
				ErrorLog.sendError("reqValAdd payeeAddr or Vpa",vpa,CommonUpiReqValAddServiceImpl.class);
				log.error("reqValAdd payeeAddr={}", vpa);
			}

		} catch (Exception e) {
			ErrorLog.sendError(e,CommonUpiReqValAddServiceImpl.class);
			log.error("error in reqValAdd  {}", e);
		}

	}

}
