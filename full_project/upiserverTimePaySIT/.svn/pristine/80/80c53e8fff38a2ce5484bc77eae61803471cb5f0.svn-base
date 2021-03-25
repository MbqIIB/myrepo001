package com.npst.upiserver.acquirer.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.UpiReqTxnConfirmationService;
import com.npst.upiserver.constant.Constant;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.CustomerMandatesDao;
import com.npst.upiserver.dao.CustomerTxnsDao;
import com.npst.upiserver.dao.MobReqRespJsonDao;
import com.npst.upiserver.dao.MobReqRespJsonIdDao;
import com.npst.upiserver.dao.MobUpiReqRespJsonIdDao;
import com.npst.upiserver.dao.ReqRespAuthDetailsDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.CustomerMandatesEntity;
import com.npst.upiserver.npcischema.Ack;
import com.npst.upiserver.npcischema.HeadType;
import com.npst.upiserver.npcischema.Ref;
import com.npst.upiserver.npcischema.ReqTxnConfirmation;
import com.npst.upiserver.npcischema.RespTxnConfirmation;
import com.npst.upiserver.npcischema.RespType;
import com.npst.upiserver.npcischema.ResultType;
import com.npst.upiserver.repo.CustomerMandatesRepo;
import com.npst.upiserver.service.impl.NpciUpiRestConServiceImpl;
import com.npst.upiserver.util.DigitalSignUtil;
import com.npst.upiserver.util.ErrorLog;
import com.npst.upiserver.util.MarshalUpi;
import com.npst.upiserver.util.Util;

@Service
public class UpiReqTxnConfirmationServiceImpl implements UpiReqTxnConfirmationService {
	private static final Logger log = LoggerFactory.getLogger(UpiReqTxnConfirmationServiceImpl.class);

	
	@Autowired
	MobReqRespJsonIdDao mobReqRespJsonId;
	
	@Autowired
	MobUpiReqRespJsonIdDao mobReqRespJsonIdDao;
	
	@Autowired
	MobReqRespJsonDao mobReqRespJson;
	
	@Autowired
	private CustomerTxnsDao customerTxnsDao;
	@Autowired
	ReqRespAuthDetailsDao reqRespAuth;
    /*@Autowired
	private AcqTxnLimitDao acqTxnLimitDao;*/
	
	@Autowired
	NpciUpiRestConServiceImpl		npciResponseService;
	
	@Autowired
	CustomerMandatesDao customerMandatesDao;
	
	@Autowired
	private CustomerMandatesRepo customerMandatesRepo;
	
	
	@Override
	public void acquirerProcess(final ReqTxnConfirmation reqTxnConfirmation,final String message) {
		log.debug("reqTxnConfirmation {}", reqTxnConfirmation);
		try {
			String msgId = Util.uuidGen();
			String ts = Util.getTS();
			RespType resp = new RespType();
			
			resp.setReqMsgId(reqTxnConfirmation.getHead().getMsgId());
			resp.setResult(ConstantI.SUCCESS);

			RespTxnConfirmation respTxnConfirmation = new RespTxnConfirmation();
			HeadType head = new HeadType();
			head.setMsgId(msgId);
			head.setOrgId(Constant.orgId);
			head.setTs(ts);
			head.setVer(Constant.headVer);
			respTxnConfirmation.setHead(head);
			respTxnConfirmation.setResp(resp);
			if (reqTxnConfirmation != null && reqTxnConfirmation.getTxn() != null) {
			    reqTxnConfirmation.getTxn().setCustRef(null);
			   }
			   respTxnConfirmation.setTxn(reqTxnConfirmation.getTxn());
			respTxnConfirmation.setTxn(reqTxnConfirmation.getTxn());

			String xmlStr = DigitalSignUtil.signXML(MarshalUpi.marshal(respTxnConfirmation).toString());
			Ack ack = npciResponseService.send(xmlStr, ConstantI.API_RESP_TXN_CONFIRM, respTxnConfirmation.getTxn().getId());
			if(ack!=null) {
				/*log.info("before going to getmobreqrespjsonid {}",reqTxnConfirmation.getTxn().getOrgTxnId());
				Long idmobreqrespjsonid = mobReqRespJsonIdDao.getMobReqRespJsonId(reqTxnConfirmation.getTxn().getOrgTxnId());
				log.info("after found idpk {}",idmobreqrespjsonid);
				if(idmobreqrespjsonid!=null && idmobreqrespjsonid!=0 && idmobreqrespjsonid>0) {
				  updateDb(idmobreqrespjsonid, reqTxnConfirmation);
				}
				else {
					log.info("idmobreqrespjsonid is null ");
				}*/
				
				
				if (ConstantI.COLLECT.equalsIgnoreCase(reqTxnConfirmation.getTxnConfirmation().getType().value())) {
					long idPk = mobReqRespJsonIdDao.getMobReqRespJsonId(reqTxnConfirmation.getTxn().getOrgTxnId());
					
					log.info("FROM CONFIRMATION IDPK IS :  {}",idPk);
					if (idPk != 0) {
						updateDb(idPk, reqTxnConfirmation);
					}

				}
				reqRespAuth.updateTxnConfirmation(reqTxnConfirmation, respTxnConfirmation, ack);
				customerTxnsDao.update(reqTxnConfirmation);
			}
			

			if(ConstantI.MANDATE_INIT_MODE.equalsIgnoreCase(reqTxnConfirmation.getTxn().getInitiationMode())||"13".equalsIgnoreCase(reqTxnConfirmation.getTxn().getInitiationMode())){
				//Get List of Mandate from CUSTOMET_MANDATEs TABLE baased on UMN and TYPE(CREATE or UPDATE) if more then one record will code in list the update one by one forch each loop;
				String umn = reqTxnConfirmation.getTxnConfirmation().getRef().get(0).getAddr();
				log.info("conformation umn no {}", umn);
				List<CustomerMandatesEntity> mandates = customerMandatesDao.findByUmnAndType(umn);
				for (CustomerMandatesEntity customerMandatesEntity : mandates) {
					if (reqTxnConfirmation.getTxnConfirmation().getOrgStatus().equals("SUCCESS")) {
						customerMandatesEntity.setTxnType("DEBITED");
						customerMandatesEntity.setStatus(3);
						customerMandatesEntity.setMandateType("DEBITED");
						try {
							customerMandatesRepo.save(customerMandatesEntity);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}else {
						customerMandatesEntity.setTxnType("DEBITFAIL");
						customerMandatesEntity.setStatus(1);
						customerMandatesEntity.setMandateType("DEBITFAIL");
						try {
							customerMandatesRepo.save(customerMandatesEntity);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				// if success then put Type  sttus "3" and Mandate type "DEBITED",  if fail then status 1 and  Mandate type  "DEBITFAIL"
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void updateDb(long idPk, ReqTxnConfirmation reqTxnConfirmation) {
		try {
			ReqResp respJson = new ReqResp();
			String respCode = ConstantI.CONST_BLANK;
			respJson.setTxnType(reqTxnConfirmation.getTxn().getType().toString() + ConstantI.CONST_SPCL_PIPE
					+ reqTxnConfirmation.getTxnConfirmation().getType().toString());
			respJson.setRespMsg(reqTxnConfirmation.getTxnConfirmation().getOrgStatus().toString());
			List<Ref> apNo = reqTxnConfirmation.getTxnConfirmation().getRef();
			for (Ref ref : apNo) {
				if (ConstantI.PAYEE.equalsIgnoreCase(ref.getType().value())) {
					respJson.setRefApprovalNum(ref.getApprovalNum());
					respJson.setRespCode(ref.getRespCode());
				}
			}
			respJson.setTxnId(reqTxnConfirmation.getTxn().getId());
			log.info("IXN ID IN RESP {}",respJson.getTxnId());
			respJson.setRrn(reqTxnConfirmation.getTxn().getCustRef());
			if (ResultType.SUCCESS.toString()
					.equals(reqTxnConfirmation.getTxnConfirmation().getOrgStatus().toString())) {
				respJson.setMsgId(ConstantI.MSG_ID_SUCCESS);
				respJson.setMsg(ResultType.SUCCESS.toString());
			} else if (ResultType.PARTIAL.toString()
					.equals(reqTxnConfirmation.getTxnConfirmation().getOrgStatus().toString())) {
				respCode = reqTxnConfirmation.getTxnConfirmation().getOrgErrCode();
				String settAmount = ConstantI.CONST_BLANK;
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
			} else if (ResultType.DEEMED.toString()
					.equals(reqTxnConfirmation.getTxnConfirmation().getOrgStatus().toString())) {
				respCode = reqTxnConfirmation.getTxnConfirmation().getOrgErrCode();
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

				respCode = reqTxnConfirmation.getTxnConfirmation().getOrgErrCode();
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
				respJson.setMsgId(ConstantI.CONSTANT_1);
			}
			log.info("before gointg to final update{}",idPk);
			mobReqRespJson.finalDbUpdate(respJson, idPk);
/*acqTxnLimitDao.updateFailure(reqTxnConfirmation.getTxn().getId(), reqTxnConfirmation.getTxnConfirmation().getOrgErrCode(),
					reqTxnConfirmation.getTxnConfirmation().getOrgStatus());*/
		} catch (Exception e) {
			log.error("error {}", e);
			ErrorLog.sendError(e, UpiReqTxnConfirmationServiceImpl.class);
		}

	}
}