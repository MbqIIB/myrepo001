package com.npst.upiserver.acquirer.service.impl;

import java.util.List;

import org.hibernate.dialect.FirebirdDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.npst.upiserver.acquirer.service.UpiRespPayService;
import com.npst.upiserver.constant.ConstantI;
import com.npst.upiserver.dao.CustomerMandatesDao;
import com.npst.upiserver.dao.CustomerTxnsDao;
import com.npst.upiserver.dao.MobReqRespJsonDao;
import com.npst.upiserver.dao.MobReqRespJsonIdDao;
import com.npst.upiserver.dao.MobUpiReqRespJsonIdDao;
import com.npst.upiserver.dao.ReqRespPayCollectDao;
import com.npst.upiserver.dto.ReqResp;
import com.npst.upiserver.entity.CustomerMandatesEntity;
import com.npst.upiserver.npcischema.PayeeType;
import com.npst.upiserver.npcischema.Ref;
import com.npst.upiserver.npcischema.RefType;
import com.npst.upiserver.npcischema.RespPay;
import com.npst.upiserver.npcischema.ResultType;
import com.npst.upiserver.repo.CustomerMandatesRepo;
import com.npst.upiserver.util.ErrorLog;

@Service
public class UpiRespPayServiceImpl implements UpiRespPayService {
	private static final Logger log = LoggerFactory.getLogger(UpiRespPayServiceImpl.class);

	@Autowired
	MobReqRespJsonIdDao mobReqRespJsonIdDao;
	
	@Autowired
	MobReqRespJsonDao mobReqRespJsonDao;
	
	@Autowired
	ReqRespPayCollectDao reqRespPayCollect;
	
	@Autowired
	CustomerTxnsDao custTxnDao;
	
	@Autowired
	MobUpiReqRespJsonIdDao mobUpiReqRespJsonIdDao;
	
	@Autowired
	CustomerMandatesDao customerMandatesDao;
	
	@Autowired
	private CustomerMandatesRepo customerMandatesRepo;
	
	@Override
	public void acquirerProcess(final RespPay respPay,String message) {
		log.debug("respPay {}", respPay);
		try {
			Long idmobreqrespjsonid=0l;
			if (ConstantI.PAY.equalsIgnoreCase(respPay.getTxn().getType().value())) {
				idmobreqrespjsonid = mobUpiReqRespJsonIdDao.getMobReqRespJsonId(respPay.getResp().getReqMsgId());
				if (0 != idmobreqrespjsonid) {
					updateDb(idmobreqrespjsonid, respPay);
				}
			}
			else if (ConstantI.COLLECT.equalsIgnoreCase(respPay.getTxn().getType().value())) {
				idmobreqrespjsonid = mobUpiReqRespJsonIdDao.getMobReqRespJsonId(respPay.getResp().getReqMsgId());
				if (0 != idmobreqrespjsonid) {
					updateDb(idmobreqrespjsonid, respPay);
				}
			}
			if(0 != idmobreqrespjsonid) {
				reqRespPayCollect.updateRespPay(respPay);
			}
			custTxnDao.update(respPay);
			
			if(ConstantI.MANDATE_INIT_MODE.equalsIgnoreCase(respPay.getTxn().getInitiationMode())){
				//Get List of Mandate from CUSTOMET_MANDATEs TABLE baased on UMN and TYPE(CREATE or UPDATE) if more then one record will code in list the update one by one forch each loop;
				//Ref umn = respPay.getResp().getRef().get(1);
				String umn = null;
				List<Ref> ref = respPay.getResp().getRef();
				log.debug("GetData for ref tag");
				for (Ref ref2 : ref) {
					log.info("Ref type is{}",ref2.getType());
					if ("PAYER".equalsIgnoreCase(ref2.getType().toString())) {
						log.info("inside loop resp pay umn no{}",ref2.getAddr());
						umn=ref2.getAddr();
						break;
					}
				}
				log.info("resp pay umn no{}", umn);
				List<CustomerMandatesEntity> mandates = null;
				if (null != umn) {
					 mandates = customerMandatesDao.findByUmnAndType(umn);
				}
				if (null !=  mandates) {
				for (CustomerMandatesEntity customerMandatesEntity : mandates) {
					if ("SUCCESS".equalsIgnoreCase(respPay.getResp().getResult().toString())) {
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
				}
				// if success then put Type  sttus "3" and Mandate type "DEBITED",  if fail then status 1 and  Mandate type  "DEBITFAIL"
			}
		}
		catch (Exception e) {
		 log.error(e.getMessage(),e);
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
			}else{
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
