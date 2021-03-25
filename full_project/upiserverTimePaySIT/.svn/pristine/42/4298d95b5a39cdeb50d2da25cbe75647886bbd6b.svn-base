package com.npst.upiserver.acquirer.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.UpiReqMandateConfirmationService;
import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.constant.UpiApiName;
import com.npst.upiserver.dao.MandateTxnsDao;
import com.npst.upiserver.dao.MandatesDao;
import com.npst.upiserver.dao.MobMandateReqRespJsonDao;
import com.npst.upiserver.dao.RegistrationDao;
import com.npst.upiserver.dao.ReqRespAuthMandateDao;
import com.npst.upiserver.dto.RegDto;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.CustomerAccount;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.HeadType;
import com.npst.upiserver.npcischema.InitiatedByType;
import com.npst.upiserver.npcischema.PayConstant;
import com.npst.upiserver.npcischema.Ref;
import com.npst.upiserver.npcischema.ReqMandateConfirmation;
import com.npst.upiserver.npcischema.RespMandateConfirmation;
import com.npst.upiserver.npcischema.RespType;
import com.npst.upiserver.npcischema.ResultType;
import com.npst.upiserver.repo.CustomerAccountRepository;
import com.npst.upiserver.service.NotificationService;
import com.npst.upiserver.service.NpciUpiRestConService;
import com.npst.upiserver.service.impl.MiddlewareRestConServiceImpl;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.Util;

@Service
public class UpiReqMandateConfirmationServiceImpl implements UpiReqMandateConfirmationService {

	private static final Logger log = LoggerFactory.getLogger(UpiReqMandateConfirmationServiceImpl.class);

	@Autowired
	private NpciUpiRestConService npciUpiRestConService;
	@Autowired
	private MobMandateReqRespJsonDao mobMandateReqRespJsonDao;

	@Autowired
	private MandateTxnsDao mandateTxnsDao;
	@Autowired
	private MandatesDao mandatesDao;
	@Autowired
	private ReqRespAuthMandateDao reqRespAuthMandateDao;
	@Autowired
	MiddlewareRestConServiceImpl switchCom;
	
	@Autowired
	private NotificationService notiService;
	
	@Autowired
	private CustomerAccountRepository customerAccountRepo;
	@Autowired
	private RegistrationDao registrationDao;

	@Override
	public void acquirerProcess(final ReqMandateConfirmation reqMandateConfirmation) {
		log.debug("reqMandateConfirmation {}", reqMandateConfirmation);
		
		try {
			String msgId = Util.uuidGen();
			String ts = Util.getTS();
			Ack ack = null;
			RespType resp = new RespType();
			resp.setReqMsgId(reqMandateConfirmation.getHead().getMsgId());
			resp.setResult("SUCCESS");
			/*HeadType head = new HeadType();
			head.setMsgId(msgId);
			head.setOrgId(Constant.orgId);
			head.setTs(ts);
			head.setVer(Constant.headVer);*/
			RespMandateConfirmation respMandateConfirmation = new RespMandateConfirmation();
			respMandateConfirmation.setHead(getHead());
			respMandateConfirmation.setResp(resp);
			respMandateConfirmation.setTxn(reqMandateConfirmation.getTxn());
			String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(respMandateConfirmation).toString());
			ack = npciUpiRestConService.send(xmlStr, UpiApiName.RESP_MANDATE_CONFIRMATION.getName(),
					respMandateConfirmation.getTxn().getId());
			if (InitiatedByType.PAYEE.equals(reqMandateConfirmation.getTxn().getInitiatedBy())) {
				//it means its a callected Req
				log.info("inside mandate conformation txn id to get idpk{}",reqMandateConfirmation.getTxn().getId());
				long idPk = mobMandateReqRespJsonDao.getIdPkByReqMsgId(reqMandateConfirmation.getTxn().getId());
				log.info("inside mandate conformation {}",idPk);
				if (idPk != 0) {
					updateDb(idPk, reqMandateConfirmation);
				}
			}
			if(ResultType.SUCCESS.toString().equalsIgnoreCase(reqMandateConfirmation.getTxnConfirmation().getOrgStatus())){
				
				List<Ref> ref = reqMandateConfirmation.getTxnConfirmation().getRef();
				log.debug("GetData for ref tag 1");
				String vpa=null;
				for (Ref ref2 : ref) {
					log.info("Ref type is{}",ref2.getType());
					if ("PAYEE".equalsIgnoreCase(ref2.getType().toString())) {
						log.info("inside loop resp payee vpa{}",ref2.getAddr());
						vpa=ref2.getAddr();
						break;
					}
				}
				log.info("Payer vpa is {}",vpa);
				List<CustomerAccount> listaccounts = customerAccountRepo
						.findByAccvirtualid(vpa);
				for (CustomerAccount customerAccount : listaccounts) {
					if (1 == customerAccount.getDefaccount() && 1 == customerAccount.getStatus()) {
						RegDto regvpa = registrationDao.getGCMToken(customerAccount.getRegid());
						log.info("befor going to if block");
						if("Y".equalsIgnoreCase(reqMandateConfirmation.getMandate().getShareToPayee().toString()) && PayConstant.CREATE.toString().equalsIgnoreCase(reqMandateConfirmation.getMandate().getType()) && "PAYER".equalsIgnoreCase(reqMandateConfirmation.getTxn().getInitiatedBy().toString())){
							log.info("before going to send notification");
							notiService.sendNoti(reqMandateConfirmation , regvpa);
							log.info("after going to send notification");
						}
						break;
					}
				}
				
				mandatesDao.insertSuccessCustomerMandates(reqMandateConfirmation);
			}
			
			reqRespAuthMandateDao.updateMandateConfirmation(reqMandateConfirmation, respMandateConfirmation, ack);
			mandateTxnsDao.updateCustomerMandateHistory(reqMandateConfirmation);

		} catch (Exception e) {
			log.error("Error: {}", e);
			ErrorLog.sendError(e, UpiReqMandateConfirmationServiceImpl.class);
		}

	}
	private HeadType getHead() {
		HeadType head = new HeadType();
		head.setMsgId(Util.uuidGen());
		head.setOrgId(Constant.orgId);
		head.setTs(Util.getTS());
		head.setVer(Constant.headVer);
		return head;
	}
	private void updateDb(long idPk, ReqMandateConfirmation reqMandateConfirmation) {
		// TODO test
		try {
			final ReqResp respJson = new ReqResp();
			String respCode = "";
			respJson.setRespMsg(reqMandateConfirmation.getTxnConfirmation().getOrgStatus().toString());
			final List<Ref> apNo = reqMandateConfirmation.getTxnConfirmation().getRef();
			for (final Ref ref : apNo) {
				if ("PAYEE".equalsIgnoreCase(ref.getType().value())) {
					respJson.setRefApprovalNum(ref.getApprovalNum());
					respJson.setRespCode(ref.getRespCode());
				}
			}
			respJson.setTxnId(reqMandateConfirmation.getTxn().getId());
			respJson.setRrn(reqMandateConfirmation.getTxn().getCustRef());
			respJson.setUmn(reqMandateConfirmation.getMandate().getUmn());

			if (ResultType.SUCCESS.toString()
					.equals(reqMandateConfirmation.getTxnConfirmation().getOrgStatus().toString())) {
				respJson.setMsgId("0");
				respJson.setMsg(ResultType.SUCCESS.toString());
			} else if (ResultType.PARTIAL.toString()
					.equals(reqMandateConfirmation.getTxnConfirmation().getOrgStatus().toString())) {
				respCode = reqMandateConfirmation.getTxnConfirmation().getOrgErrCode();
				final String settAmount = "";
				for (final Ref ref : apNo) {
					if ("PAYER".equalsIgnoreCase(ref.getType().value())) {
						if (null != ref.getRespCode()) {
							respCode = respCode + "~" + ref.getRespCode();
							settAmount.concat("PAYER=" + ref.getSettAmount());
						}
					}
					if ("PAYEE".equalsIgnoreCase(ref.getType().value())) {
						if (null != ref.getRespCode()) {
							respCode = respCode + "~" + ref.getRespCode();
							settAmount.concat("PAYEE=" + ref.getSettAmount());
						}
					}
				}
				respJson.setSettAmount(settAmount);
				respJson.setMsg(respCode);
				respJson.setMsgId("1");
			} else if (ResultType.DEEMED.toString()
					.equals(reqMandateConfirmation.getTxnConfirmation().getOrgStatus().toString())) {
				respCode = reqMandateConfirmation.getTxnConfirmation().getOrgErrCode();
				final String settAmount = "";
				for (final Ref ref : apNo) {
					if ("PAYER".equalsIgnoreCase(ref.getType().value())) {
						if (null != ref.getRespCode()) {
							respCode = respCode + "~" + ref.getRespCode();
							settAmount.concat("PAYER=" + ref.getSettAmount());
						}
					}
					if ("PAYEE".equalsIgnoreCase(ref.getType().value())) {
						if (null != ref.getRespCode()) {
							respCode = respCode + "~" + ref.getRespCode();
							settAmount.concat("PAYEE=" + ref.getSettAmount());
						}
					}
				}
				respJson.setSettAmount(settAmount);
				respJson.setMsg(respCode);
				respJson.setMsgId("1");
			} else {

				respCode = reqMandateConfirmation.getTxnConfirmation().getOrgErrCode();
				for (final Ref ref : apNo) {
					if ("PAYER".equalsIgnoreCase(ref.getType().value())) {
						if (null != ref.getRespCode()) {
							respCode = respCode + "~" + ref.getRespCode();
						}
					}
					if ("PAYEE".equalsIgnoreCase(ref.getType().value())) {
						if (null != ref.getRespCode()) {
							respCode = respCode + "~" + ref.getRespCode();
						}
					}
				}
				respJson.setMsg(respCode);
				respJson.setMsgId("1");
			}
			mobMandateReqRespJsonDao.finalDbUpdate(respJson, idPk);
		} catch (Exception e) {
			log.error("Error: {}", e);
			ErrorLog.sendError(e, UpiReqMandateConfirmationServiceImpl.class);
		}
	}
	
	private ReqResp SendtoCBS(ReqResp req) {
		try {

			log.info("Sending CBS to UNBLOCK FROM CONF ");
			
			req.setTxnType(ConstantI.MANDATE_TXN_TYPE);
			log.info("TXN TYPE  CREATE/UPDATE######### {}", req.getTxnType());
			req = switchCom.send(req);
			//req.setCbsMandateNo("6");//req.getCbsMandateNo()
			req.setRespCode("0");//req.getCbsMandateNo()
			log.info("RESP FOR CREATE , UPDATE MODIFY ######## {} , TXN TYPE {}",req.getRespCode(),req.getTxnType());
			return req;
		} catch (Exception e) {
			log.error("ERROR BLKUND {}", e);
		}
		return null;
	}
}
