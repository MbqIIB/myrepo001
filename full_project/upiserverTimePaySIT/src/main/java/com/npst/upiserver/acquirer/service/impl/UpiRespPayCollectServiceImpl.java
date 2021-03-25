package com.npst.upiserver.acquirer.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.PreApprovedReversalService;
import com.npst.upiserver.acquirer.service.UpiRespPayCollectService;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.constant.InitiationMode;
import com.npst.upiserver.dao.AcqTxnLimitDao;
import com.npst.upiserver.dao.CustomerTxnsDao;
import com.npst.upiserver.dao.MandateTxnsDao;
import com.npst.upiserver.dao.MobReqRespJsonDao;
import com.npst.upiserver.dao.ReqRespPayCollectDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.npcischema.Ref;
import com.npst.upiserver.npcischema.RespPay;
import com.npst.upiserver.npcischema.ResultType;
import com.npst.upiserver.util.ErrorLog;

@Service
public class UpiRespPayCollectServiceImpl implements UpiRespPayCollectService {
	private static final Logger log = LoggerFactory.getLogger(UpiRespPayCollectServiceImpl.class);
	@Autowired
	private MobReqRespJsonDao mobReqRespJsonDao;
	@Autowired
	private CustomerTxnsDao customerTxnsDao;
	@Autowired
	private ReqRespPayCollectDao reqRespPayCollectDao;
	@Autowired
	private MandateTxnsDao mandateTxnsDao;
	
	@Autowired
	private PreApprovedReversalService preApprovedReversalService;
	
	@Autowired
	private TaskExecutor taskExecutor;
	
	@Autowired
	private AcqTxnLimitDao acqTxnLimitDao;

	@Override
	public void acquirerProcess(final RespPay respPay) {
		try {
			log.debug("respPay {}", respPay);
			if (ConstantI.PAY.equalsIgnoreCase(respPay.getTxn().getType().value())) {
				long idPk = mobReqRespJsonDao.getIdPkByReqMsgId(respPay.getResp().getReqMsgId());
				log.info("Final resp idpk is {}",idPk);
				updateDb(idPk, respPay);
			}else if(ConstantI.COLLECT.equalsIgnoreCase(respPay.getTxn().getType().value())
					&& (InitiationMode.MANDATE.getMode().equalsIgnoreCase(respPay.getTxn().getInitiationMode())
							||"13".equalsIgnoreCase(respPay.getTxn().getInitiationMode()))) {
				long idPk = mobReqRespJsonDao.getIdPkByReqMsgId(respPay.getResp().getReqMsgId());
				log.info("Final resp idpk is COLLECT {}",idPk);
				updateDb(idPk, respPay);
			}
			reqRespPayCollectDao.updateRespPay(respPay);
			customerTxnsDao.updateRespPay(respPay);
			if (InitiationMode.MANDATE.toString().equalsIgnoreCase(respPay.getTxn().getInitiationMode())||InitiationMode.MANDATEQR.toString().equalsIgnoreCase(respPay.getTxn().getInitiationMode())) {
				mandateTxnsDao.updateMandatesTxns(respPay);
			}
		} catch (Exception e) {
			log.error("error :{}", e);
			ErrorLog.sendError(e, UpiRespPayCollectServiceImpl.class);
		}

	}

	private void updateDb(long idPk, RespPay respPay) {
		try {
			ReqResp respJson = new ReqResp();
			respJson.setTxnType(respPay.getTxn().getType().toString());
			String respCode = "";
			respJson.setRespMsg(respPay.getResp().getResult().toString());
			List<Ref> apNo = respPay.getResp().getRef();
			for (Ref ref : apNo) {
				if (ConstantI.PAYEE.equalsIgnoreCase(ref.getType().value())) {
					respJson.setRefApprovalNum(ref.getApprovalNum());
					respJson.setRespCode(ref.getRespCode());
				}
			}
			respJson.setTxnId(respPay.getTxn().getId());
			respJson.setRrn(respPay.getTxn().getCustRef());
			if (ResultType.SUCCESS.toString().equals(respPay.getResp().getResult().value())) {
				respJson.setMsgId(ConstantI.CODE_SUCCESS);
				respJson.setMsg(ResultType.SUCCESS.toString());
			} else if (ResultType.PARTIAL.toString().equals(respPay.getResp().getResult().value())) {
				respCode = respPay.getResp().getErrCode();
				String settAmount = "";
				for (Ref ref : apNo) {
					if (ConstantI.PAYER.equalsIgnoreCase(ref.getType().value())) {
						if (null != ref.getRespCode()) {
							respCode = respCode + ConstantI.CONST_SPCL_TILD + ref.getRespCode();
							settAmount.concat(ConstantI.CONST_PAYER_EQ + ref.getSettAmount());
						}
					}
					if (ConstantI.PAYEE.equalsIgnoreCase(ref.getType().value())) {
						if (null != ref.getRespCode()) {
							respCode = respCode + ConstantI.CONST_SPCL_TILD + ref.getRespCode();
							settAmount.concat(ConstantI.CONST_PAYEE_EQ + ref.getSettAmount());
						}
					}
				}
				respJson.setSettAmount(settAmount);
				respJson.setMsg(respCode);
				respJson.setMsgId(ConstantI.MSG_ID_FAILURE);
			} else if (ResultType.DEEMED.toString().equals(respPay.getResp().getResult().value())) {
				respCode = respPay.getResp().getErrCode();
				String settAmount = "";
				for (Ref ref : apNo) {
					if (ConstantI.PAYER.equalsIgnoreCase(ref.getType().value())) {
						if (null != ref.getRespCode()) {
							respCode = respCode + ConstantI.CONST_SPCL_TILD + ref.getRespCode();
							settAmount.concat(ConstantI.CONST_PAYER_EQ + ref.getSettAmount());
						}
					}
					if (ConstantI.PAYEE.equalsIgnoreCase(ref.getType().value())) {
						if (null != ref.getRespCode()) {
							respCode = respCode + ConstantI.CONST_SPCL_TILD + ref.getRespCode();
							settAmount.concat(ConstantI.CONST_PAYEE_EQ + ref.getSettAmount());
						}
					}
				}
				respJson.setSettAmount(settAmount);
				respJson.setMsg(respCode);
				respJson.setMsgId(ConstantI.MSG_ID_FAILURE);
			} else {
					acqTxnLimitDao.updateFailure(respPay.getTxn().getId(), respPay.getResp().getErrCode(),
							ResultType.FAILURE.toString());
					
				respCode = respPay.getResp().getErrCode();
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
				respJson.setMsg(respCode);
				respJson.setMsgId(ConstantI.MSG_ID_FAILURE);
			}
			mobReqRespJsonDao.finalDbUpdate(respJson, idPk);
		} catch (Exception e) {
			log.error("error :{}", e);
			ErrorLog.sendError(e, UpiRespPayCollectServiceImpl.class);
		}
	}

}
