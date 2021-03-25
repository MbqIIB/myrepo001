package com.npst.upiserver.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.MobBalReqService;
import com.npst.upiserver.acquirer.service.MobCollectAcceptService;
import com.npst.upiserver.acquirer.service.MobCollectBlockService;
import com.npst.upiserver.acquirer.service.MobCollectRejectService;
import com.npst.upiserver.acquirer.service.MobListAccPvdService;
import com.npst.upiserver.acquirer.service.MobListAccountService;
import com.npst.upiserver.acquirer.service.MobListKeysService;
import com.npst.upiserver.acquirer.service.MobOtpService;
import com.npst.upiserver.acquirer.service.MobPayCollectService;
import com.npst.upiserver.acquirer.service.MobPendingMsgService;
import com.npst.upiserver.acquirer.service.MobRegMobService;
import com.npst.upiserver.acquirer.service.MobReqChkTxnService;
import com.npst.upiserver.acquirer.service.MobReqMandateService;
import com.npst.upiserver.acquirer.service.MobRespMandateAuth;
import com.npst.upiserver.acquirer.service.MobSetCreService;
import com.npst.upiserver.acquirer.service.MobValAddService;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.MobMandateReqRespJsonDao;
import com.npst.upiserver.dao.MobUpiReqRespJsonIdDao;
import com.npst.upiserver.entity.MobMandateReqRespJsonEntity;
import com.npst.upiserver.entity.MobUpiReqRespJson;
import com.npst.upiserver.service.MobIncomingService;
import com.npst.upiserver.util.ErrorLog;


@Service
public class MobIncomingServiceImpl implements MobIncomingService {

	private static final Logger log = LoggerFactory.getLogger(MobIncomingServiceImpl.class);
	@Autowired
	private MobMandateReqRespJsonDao mobMandateReqRespJsonIdDao;
	

	@Autowired
	private MobBalReqService mobBalEnqService;
	@Autowired
	private MobCollectAcceptService mobCollectAcceptService;
	@Autowired
	private MobCollectRejectService mobCollectRejectService;
	@Autowired
	private MobCollectBlockService mobCollectBlockService;
	@Autowired
	private MobListAccountService mobListAccountService;
	@Autowired
	private MobListKeysService mobListKeysService;
	@Autowired
	private MobOtpService mobOTPService;
	@Autowired
	private MobPayCollectService mobPayCollectService;
	@Autowired
	private MobRegMobService mobRegMobService;
	@Autowired
	private MobPendingMsgService mobPendMsgService;
	@Autowired
	private MobSetCreService mobSetCreService;
	@Autowired
	private MobValAddService mobValAddService;
	@Autowired
	private MobListAccPvdService mobListAccPvdService;
	@Autowired
	private MobReqChkTxnService mobReqChkTxnService;
	@Autowired
	private MobUpiReqRespJsonIdDao mobUpiReqRespJsonIdDao;
	
	// Mandates
	@Autowired
	private MobReqMandateService mobReqMandateService;
	@Autowired
	private MobRespMandateAuth mobRespMandateAuth;
	
	
	@Override
	public void proMobReq(MobUpiReqRespJson jsonObj) {
		try {
			log.info("type is as {} ",jsonObj.getType());
			switch (jsonObj.getType()) {
				case ConstantI.TYPE_BAL_ENQ:
					mobBalEnqService.procAndSendNpci(jsonObj);
					break;
				case ConstantI.TYPE_COLLECT_ACCEPT:
					mobCollectAcceptService.procAndSendNpci(jsonObj);
					break;
				case ConstantI.TYPE_COLLECT_REJ:
					mobCollectRejectService.procAndSendNpci(jsonObj);
					break;
				case ConstantI.TYPE_COLLECT_BLOCK:
					mobCollectBlockService.procAndSendNpci(jsonObj);
					break;
				case ConstantI.TYPE_LIST_ACC:
					mobListAccountService.procAndSendNpci(jsonObj);	
					break;
				case ConstantI.TYPE_LIST_KEYS:
					mobListKeysService.procAndSendNpci(jsonObj);
					break;
				case ConstantI.TYPE_OTP:
					mobOTPService.procAndSendNpci(jsonObj);	
					break;
				case ConstantI.TYPE_PAY_COLLECT:
					mobPayCollectService.procAndSendNpci(jsonObj);
					break;
				case ConstantI.TYPE_REG_MOB:
					mobRegMobService.procAndSendNpci(jsonObj);	
					break;
				case ConstantI.TYPE_PENDINGMSG:
					mobPendMsgService.procAndSendNpci(jsonObj);
					break;
				case ConstantI.TYPE_SETCRE:
					mobSetCreService.procAndSendNpci(jsonObj);
					break;
				case ConstantI.TYPE_VALADD:
					mobValAddService.procAndSendNpci(jsonObj);
					break;
				case ConstantI.TYPE_REQLISTACCPVD:
					mobListAccPvdService.procAndSendNpci(jsonObj);
					break;
				case ConstantI.TYPE_CHKTXN:
					mobReqChkTxnService.procAndSendNpci(jsonObj);
					break;
					
				default:
					mobUpiReqRespJsonIdDao.updateFail(jsonObj);
					log.error("NOT FOUND" + jsonObj.getType());
					break;
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	@Override
	public void proMobReq(String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void proMandates(MobMandateReqRespJsonEntity mobMandateReqRespJson) {
		try {
			if (ConstantI.TYPE_REQ_MANDATE.equalsIgnoreCase(mobMandateReqRespJson.getType())) {
				mobReqMandateService.procAndSendNpci(mobMandateReqRespJson);
			} else if (ConstantI.TYPE_REC_MANDATE.equalsIgnoreCase(mobMandateReqRespJson.getType())) {
				mobRespMandateAuth.procAndSendNpci(mobMandateReqRespJson);
			} else {
				log.error("MobMandate Type NOT FOUND : {} ", mobMandateReqRespJson.getType());
				mobMandateReqRespJsonIdDao.updateFail(mobMandateReqRespJson);
			}
		} catch (Exception e) {
			log.error("error {}", e);
			ErrorLog.sendError(e, MobIncomingServiceImpl.class);
			mobMandateReqRespJsonIdDao.updateFail(mobMandateReqRespJson);

		}

	}
	
	@Override
	public void proOnus(String msg) {
		
	}
	
}
