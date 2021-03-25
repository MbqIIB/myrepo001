package com.npst.upiserver.service.impl;

import java.io.StringReader;

import javax.xml.bind.JAXB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.UpiReqAuthDetailsService;
import com.npst.upiserver.acquirer.service.UpiReqAuthMandateService;
import com.npst.upiserver.acquirer.service.UpiReqMandateConfirmationService;
import com.npst.upiserver.acquirer.service.UpiReqTxnConfirmationService;
import com.npst.upiserver.acquirer.service.UpiRespBalEnqService;
import com.npst.upiserver.acquirer.service.UpiRespChkTxnService;
import com.npst.upiserver.acquirer.service.UpiRespListAccPvdService;
import com.npst.upiserver.acquirer.service.UpiRespListAccountService;
import com.npst.upiserver.acquirer.service.UpiRespListKeysService;
import com.npst.upiserver.acquirer.service.UpiRespListPspService;
import com.npst.upiserver.acquirer.service.UpiRespListVaeService;
import com.npst.upiserver.acquirer.service.UpiRespMandateService;
import com.npst.upiserver.acquirer.service.UpiRespOtpService;
import com.npst.upiserver.acquirer.service.UpiRespPayService;
import com.npst.upiserver.acquirer.service.UpiRespPendingMsgService;
import com.npst.upiserver.acquirer.service.UpiRespRegMobService;
import com.npst.upiserver.acquirer.service.UpiRespSetCreService;
import com.npst.upiserver.acquirer.service.UpiRespValAddService;
import com.npst.upiserver.constant.ApiSearchConst;
import com.npst.upiserver.issuer.service.UpiReqBalEnqService;
import com.npst.upiserver.issuer.service.UpiReqChkTxnService;
import com.npst.upiserver.issuer.service.UpiReqHbtService;
import com.npst.upiserver.issuer.service.UpiReqListAccountService;
import com.npst.upiserver.issuer.service.UpiReqMandateService;
import com.npst.upiserver.issuer.service.UpiReqOtpService;
import com.npst.upiserver.issuer.service.UpiReqPayService;
import com.npst.upiserver.issuer.service.UpiReqPendingMsgService;
import com.npst.upiserver.issuer.service.UpiReqRegMobService;
import com.npst.upiserver.issuer.service.UpiReqSetCreService;
import com.npst.upiserver.issuer.service.UpiRespHbtService;
import com.npst.upiserver.npcischema.ReqBalEnq;
import com.npst.upiserver.npcischema.ReqChkTxn;
import com.npst.upiserver.npcischema.ReqOtp;
import com.npst.upiserver.npcischema.ReqPay;
import com.npst.upiserver.npcischema.ReqRegMob;
import com.npst.upiserver.npcischema.ReqSetCre;
import com.npst.upiserver.npcischema.ReqValAdd;
import com.npst.upiserver.npcischema.RespPay;
import com.npst.upiserver.npcischema.*;
import com.npst.upiserver.service.CommonUpiReqValAddService;
import com.npst.upiserver.service.NpciIncomingApiService;
import com.npst.upiserver.util.ErrorLog;

@Service
public class NpciIncomingApiServiceImpl implements NpciIncomingApiService {
	private static final Logger log = LoggerFactory.getLogger(NpciIncomingApiServiceImpl.class);

	@Override
	public void checkApiAndProcess(String msg) {
		log.info("ReqResp received from NPCI  is: {} ", msg);
		try {
			if (msg.contains(ApiSearchConst.ReqPay_)) {
				upiReqPayService.issuerProcess(unmarshal(msg, ReqPay.class));
			} else if (msg.contains(ApiSearchConst.RespPay_)) {
				upiRespPayService.acquirerProcess(unmarshal(msg, RespPay.class),msg);
			} else if (msg.contains(ApiSearchConst.ReqBalEnq_)) {
				upiReqBalEnqService.issuerProcess(unmarshal(msg, ReqBalEnq.class));
			} else if (msg.contains(ApiSearchConst.ReqSetCre_)) {
				upiReqSetCreService.issuerProcess(unmarshal(msg, ReqSetCre.class));
			} else if (msg.contains(ApiSearchConst.ReqRegMob_)) {
				upiReqRegMobService.issuerProcess(unmarshal(msg, ReqRegMob.class));
			} else if (msg.contains(ApiSearchConst.ReqOtp_)) {
				upiReqOtpService.issuerProcess(unmarshal(msg, ReqOtp.class));
			} else if (msg.contains(ApiSearchConst.ReqChkTxn_)) {
				upiReqChkTxnService.issuerProcess(unmarshal(msg, ReqChkTxn.class));
			} else if (msg.contains(ApiSearchConst.ReqValAdd_)) {
				commonUpiReqValAddService.commonProcess(unmarshal(msg, ReqValAdd.class));
			} else if (msg.contains(ApiSearchConst.ReqListAccount_)) {
				log.info("before sending request to UpiReqListAccountServiceImpl ");
				upiReqListAccountService.issuerProcess(unmarshal(msg, ReqListAccount.class));
				log.info("after sending request to UpiReqListAccountServiceImpl ");
			} else if (msg.contains(ApiSearchConst.ReqMandate_)) {
				upiReqMandateService.issuerProcess(unmarshal(msg, ReqMandate.class));
			} else if (msg.contains(ApiSearchConst.RespBalEnq_)) {
				upiRespBalEnqService.acquirerProcess(unmarshal(msg, RespBalEnq.class),msg);
			} else if (msg.contains(ApiSearchConst.ReqTxnConfirmation_)) {
				mobUpiReqTxnConfirmationService.acquirerProcess(unmarshal(msg, ReqTxnConfirmation.class),msg);
			} else if (msg.contains(ApiSearchConst.ReqAuthDetails_)) {
				upiReqAuthDetailsService.acquirerProcess(unmarshal(msg, ReqAuthDetails.class));
			} else if (msg.contains(ApiSearchConst.RespListAccount_)) {
				log.info("for response list account request");
				upiRespListAccountService.acquirerProcess(unmarshal(msg, RespListAccount.class),msg);
			} else if (msg.contains(ApiSearchConst.RespListKeys_)) {
				upiRespListKeysService.acquirerProcess(unmarshal(msg, RespListKeys.class),msg);
			} else if (msg.contains(ApiSearchConst.RespValAdd_)) {
				upiRespValAddService.acquirerProcess(unmarshal(msg, RespValAdd.class),msg);
			} else if (msg.contains(ApiSearchConst.RespSetCre_)) {
				upiRespSetCreService.acquirerProcess(unmarshal(msg, RespSetCre.class),msg);
			} else if (msg.contains(ApiSearchConst.RespRegMob_)) {
				upiRespRegMobService.acquirerProcess(unmarshal(msg, RespRegMob.class),msg);
			} else if (msg.contains(ApiSearchConst.RespOtp_)) {
				upiRespOtpService.acquirerProcess(unmarshal(msg, RespOtp.class),msg);
			} else if (msg.contains(ApiSearchConst.ReqAuthMandate_)) {
				upiReqAuthMandateService.acquirerProcess(unmarshal(msg, ReqAuthMandate.class));
			} else if (msg.contains(ApiSearchConst.ReqMandateConfirmation_)) {
				upiReqMandateConfirmationService.acquirerProcess(unmarshal(msg, ReqMandateConfirmation.class));
			} else if (msg.contains(ApiSearchConst.RespMandate_)) {
				upiRespMandateService.acquirerProcess(unmarshal(msg, RespMandate.class));
			} else if (msg.contains(ApiSearchConst.RespHbt_)) {
				upiRespHbtService.issuerProcess(unmarshal(msg, RespHbt.class));
			} else if (msg.contains(ApiSearchConst.RespListAccPvd_)) {
				upiRespListAccPvdService.acquirerProcess(unmarshal(msg, RespListAccPvd.class),msg);
			} else if (msg.contains(ApiSearchConst.RespListPsp_)) {
				upiRespListPspService.acquirerProcess(unmarshal(msg, RespListPsp.class));
			} else if (msg.contains(ApiSearchConst.RespListVae_)) {
				upiRespListVaeService.acquirerProcess(unmarshal(msg, RespListVae.class));
			} else if (msg.contains(ApiSearchConst.RespChkTxn_)) {
				upiRespChkTxnService.acquirerProcess(unmarshal(msg, RespChkTxn.class),msg);
			} else if (msg.contains(ApiSearchConst.ReqHbt_)) {
				upiReqHbtService.issuerProcess(unmarshal(msg, ReqHbt.class));
			} else if (msg.contains(ApiSearchConst.ReqPendingMsg_)) {
				reqPendingMsgService.issuerProcess(unmarshal(msg, ReqPendingMsg.class));
			} else if (msg.contains(ApiSearchConst.RespPendingMsg_)) {
				upiRespPendingMsgService.acquirerProcess(unmarshal(msg, RespPendingMsg.class),msg);
			} else {
				log.error("Unable to identify npci incoming api name ,strXml={}", msg);
			}
		}
		catch (Exception e) {
			ErrorLog.sendError(e, msg, NpciIncomingApiServiceImpl.class);
		}
		
	}

	private static <T> T unmarshal(String xmlStr, Class<T> t) throws Exception {
		try (StringReader sr = new StringReader(xmlStr);) {
			return JAXB.unmarshal(sr, t);
		} catch (Exception e) {
			log.error("error while Unmarsheling {}", e);
			log.error("error while Unmarshalling apiClass={} ,errorMsg={} ,xmlStr={} ", t.getName(), e.getMessage(),
					xmlStr);
			e.printStackTrace();
			ErrorLog.sendError(e, NpciIncomingApiServiceImpl.class);
			throw e;
		}
	}

	@Autowired
	private UpiRespListVaeService upiRespListVaeService;
	@Autowired
	private UpiRespListPspService upiRespListPspService;
	@Autowired
	private UpiRespMandateService upiRespMandateService;
	@Autowired
	private UpiReqMandateConfirmationService upiReqMandateConfirmationService;
	@Autowired
	private UpiReqAuthMandateService upiReqAuthMandateService;
	@Autowired
	private UpiReqMandateService upiReqMandateService;
	@Autowired
	private UpiRespChkTxnService upiRespChkTxnService;
	@Autowired
	private UpiRespListAccPvdService upiRespListAccPvdService;
	@Autowired
	private UpiRespPendingMsgService upiRespPendingMsgService;
	@Autowired
	private UpiRespBalEnqService upiRespBalEnqService;
	@Autowired
	private UpiRespOtpService upiRespOtpService;
	@Autowired
	private UpiRespRegMobService upiRespRegMobService;
	@Autowired
	private UpiRespSetCreService upiRespSetCreService;
	@Autowired
	private UpiRespValAddService upiRespValAddService;
	@Autowired
	private UpiRespListKeysService upiRespListKeysService;
	@Autowired
	private UpiRespPayService upiRespPayService;
	@Autowired
	private UpiRespListAccountService upiRespListAccountService;
	@Autowired
	private UpiReqOtpService upiReqOtpService;
	@Autowired
	private UpiReqChkTxnService upiReqChkTxnService;
	@Autowired
	private UpiReqPayService upiReqPayService;
	@Autowired
	private UpiReqTxnConfirmationService mobUpiReqTxnConfirmationService;
	@Autowired
	private UpiReqAuthDetailsService upiReqAuthDetailsService;
	@Autowired
	private UpiReqListAccountService upiReqListAccountService;
	@Autowired
	private CommonUpiReqValAddService commonUpiReqValAddService;
	@Autowired
	private UpiReqSetCreService upiReqSetCreService;
	@Autowired
	private UpiReqRegMobService upiReqRegMobService;
	@Autowired
	private UpiReqBalEnqService upiReqBalEnqService;
	@Autowired
	private UpiReqHbtService upiReqHbtService;
	@Autowired
	private UpiRespHbtService upiRespHbtService;
	@Autowired
	private UpiReqPendingMsgService reqPendingMsgService;
}
