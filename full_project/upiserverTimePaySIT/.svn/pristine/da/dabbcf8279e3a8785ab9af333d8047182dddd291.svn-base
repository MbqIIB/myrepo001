package com.npst.upiserver.acquirer.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.UpiRespMandateService;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.MandateTxnsDao;
import com.npst.upiserver.dao.MandatesDao;
import com.npst.upiserver.dao.MobMandateReqRespJsonDao;
import com.npst.upiserver.dao.RegistrationDao;
import com.npst.upiserver.dto.RegDto;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.CustomerAccount;
import com.npst.upiserver.npcischema.PayConstant;
import com.npst.upiserver.npcischema.Ref;
import com.npst.upiserver.npcischema.RespMandate;
import com.npst.upiserver.npcischema.ResultType;
import com.npst.upiserver.npcischema.ShareToPayee;
import com.npst.upiserver.repo.CustomerAccountRepository;
import com.npst.upiserver.service.NotificationService;
import com.npst.upiserver.util.ErrorLog;

@Service
public class UpiRespMandateServiceImpl implements UpiRespMandateService{

	private static final Logger log = LoggerFactory.getLogger(UpiRespMandateServiceImpl.class);

	@Autowired
	private MobMandateReqRespJsonDao mobMandateReqRespJsonDao;

	@Autowired
	private MandatesDao mandatesDao;
	@Autowired
	private MandateTxnsDao mandateTxnsDao;
	
	@Autowired
	private NotificationService notiService;
	
	@Autowired
	private CustomerAccountRepository customerAccountRepo;
	@Autowired
	private RegistrationDao registrationDao;

	@Override
	public void acquirerProcess(final RespMandate respMandate) {
		try {
			String respCode = "";
			log.info("InSide Mandate Resp Process Method **** ");
			log.debug("respMandate {}", respMandate);
			//long idPk = mobMandateReqRespJsonDao.getIdPkByReqMsgId(respMandate.getResp().getReqMsgId());
			long idPk = mobMandateReqRespJsonDao.getIdPkByReqMsgId(respMandate.getResp().getReqMsgId());
			//long idPk = mobMandateReqRespJsonDao.getIdPkByReqMsgId(respMandate.getTxn().getId());	
			log.info("PKID {}",idPk);
			if (0 != idPk) {
				log.info("Inside PKID {}",idPk);
				ReqResp respJson = new ReqResp();
				if (ResultType.SUCCESS.toString().equals(respMandate.getResp().getResult())) {
					log.info("SUCCESS RESPONsE");
					respJson.setMsg(ResultType.SUCCESS.toString());
					respJson.setMsgId("0");
					setResp(respMandate,respJson);
					
				} else {
					log.info("FAULURE RESPONsE");
					respCode = respMandate.getResp().getErrCode();
					List<Ref> apNo = respMandate.getResp().getRef();
					for (Ref ref : apNo) {
						if (ConstantI.PAYER.equalsIgnoreCase(ref.getType().value())) {
							if (null != ref.getRespCode()) {
								respCode = respCode + ConstantI.CONST_SPCL_TILD + ref.getRespCode();
							}
						}
						if (ConstantI.PAYEE.equalsIgnoreCase(ref.getType().value())) {
							if (null != ref.getRespCode()) {
								respCode = respCode + ConstantI.CONST_SPCL_TILD + ref.getRespCode();
							}
						}
					}
					respJson.setMsgId("1");
					respJson.setMsg(respMandate.getResp().getErrCode());
					respJson.setMsg(respCode);
				//	respJson.setMsg(ResultType.FAILURE.toString());
				}
				
				mobMandateReqRespJsonDao.finalDbUpdate(respJson, idPk);
				log.info("Updation In table mobMandateReqRespJsonDao Done**** ");

			}
			
			if (ResultType.SUCCESS.toString().equalsIgnoreCase(respMandate.getResp().getResult())){
				//	&&"PAYEE".equalsIgnoreCase(respMandate.getTxn().getInitiatedBy().toString())) {// success and payer
				/*List<Ref> ref = respMandate.getResp().getRef();
				log.debug("GetData for ref tag 1");
				String vpa=null;
				for (Ref ref2 : ref) {
					log.info("Ref type is{}",ref2.getType());
					if ("PAYEE".equalsIgnoreCase(ref2.getType().toString())) {
						log.info("inside loop resp pay umn no{}",ref2.getAddr());
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
						if("Y".equalsIgnoreCase(respMandate.getMandate().getShareToPayee().toString()) && PayConstant.CREATE.toString().equalsIgnoreCase(respMandate.getMandate().getType())){
							log.info("before going to send notification");
							notiService.sendNoti(respMandate , regvpa);
							log.info("after going to send notification");
						}
						break;
					}
				}*/
				
				log.info("Inserting in Customer_mandates  table Done**** ");

				mandatesDao.insertSuccessCustomerMandates(respMandate);
				log.info("Done  in Customer_mandates  table **** ");
			}

			/*if (ResultType.FAILURE.toString().equalsIgnoreCase(respMandate.getResp().getResult())) {
				mandatesDao.updateRespMandate(respMandate);	
			}*/
			mandateTxnsDao.updateRespMandateHistory(respMandate);
		} catch (Exception e) {
			log.error("Error: {}", e);
			ErrorLog.sendError(e, UpiRespMandateServiceImpl.class);
		}
	}
	private void setResp(RespMandate respMandate, ReqResp respJson) {
		respJson.setRrn(respMandate.getTxn().getCustRef());
		respJson.setTxnType(respMandate.getTxn().getType().toString());
		respJson.setTxnId(respMandate.getTxn().getId());
		respJson.setRespMsg(ResultType.SUCCESS.toString());
		respJson.setUmn(respMandate.getMandate().getUmn());
	}
}
